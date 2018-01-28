package fr.upmc.m2sar.findthisplace.activities;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.google.android.gms.nearby.messages.Distance;

import java.util.Date;

import fr.upmc.m2sar.findthisplace.R;
import fr.upmc.m2sar.findthisplace.game.CircumferenceBasedScoreCalculator;
import fr.upmc.m2sar.findthisplace.game.GameDifficulty;
import fr.upmc.m2sar.findthisplace.game.GameMode;
import fr.upmc.m2sar.findthisplace.game.IScoreCalculatorStrategy;
import fr.upmc.m2sar.findthisplace.model.PlacesViewModel;
import fr.upmc.m2sar.findthisplace.model.Score;
import fr.upmc.m2sar.findthisplace.model.ScoreViewModel;
import fr.upmc.m2sar.findthisplace.model.StaticPlaces;
import fr.upmc.m2sar.findthisplace.util.DistanceUtil;

public class GameMapActivity extends FragmentActivity implements
        OnMapReadyCallback,
        OnStreetViewPanoramaReadyCallback,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener {

    private static String TAG = "GameMapActivity";

    private static int NUMBER_OF_PLACES_TO_GUESS = 3;

    private GoogleMap map;
    private StreetViewPanorama streetView;

    private PlacesViewModel placesModel;
    private ScoreViewModel scoresModel;

    private GameDifficulty difficulty;
    private GameMode mode;
    private MarkerOptions currMarker;
    private StreetViewPanoramaLocation streetViewLocationToGuess;
    private long currScore = 0;
    private int currPlaceIndex = 0;
    private boolean isGameFinished = false;
    private boolean isGameStarted = false;
    private boolean isCurrentlyGuessing = false;
    private IScoreCalculatorStrategy scoreCalculator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_map);

        ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        ((StreetViewPanoramaFragment) getFragmentManager().findFragmentById(R.id.streetviewpanorama)).getStreetViewPanoramaAsync(this);

        Intent intent = getIntent();
        difficulty = (GameDifficulty) intent.getSerializableExtra(GameDifficulty.class.getName());
        mode = (GameMode) intent.getSerializableExtra(GameMode.class.getName());

        Log.d(TAG, "Difficulty: " + difficulty.name());
        Log.d(TAG, "Mode: " + mode.name());

        if(mode == GameMode.CLASSIC) {
            scoreCalculator = new CircumferenceBasedScoreCalculator();
        }
        // TODO: Les autres modes

        placesModel = ViewModelProviders.of(this).get(PlacesViewModel.class);
        placesModel.update(StaticPlaces.getRandomPlacesForDifficulty(difficulty, NUMBER_OF_PLACES_TO_GUESS));

        scoresModel = ViewModelProviders.of(this).get(ScoreViewModel.class);

        // rendre la status bar transparente pour une meilleure visibilité
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady");
        this.map = googleMap;
        this.map.setOnMapClickListener(this);
        this.map.setOnMarkerClickListener(this);
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        Log.d(TAG, "onStreetViewPanoramaReady");
        this.streetView = streetViewPanorama;
        if(difficulty  == GameDifficulty.EXPERT)
            this.streetView.setStreetNamesEnabled(false);
        else
            this.streetView.setStreetNamesEnabled(true);

        gameStart();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Log.d(TAG, "onMapClick");

        if(isCurrentlyGuessing) {
            map.clear();
            currMarker = new MarkerOptions().position(latLng).draggable(true);
            map.addMarker(currMarker);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if(isGameFinished) {
            scoresModel.getScores().getValue().add(new Score("toto", new Date(), difficulty.name(), currScore));
            scoresModel.saveData();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(currMarker == null || marker == null || !isGameStarted)
            return false;

        if(isCurrentlyGuessing) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage(R.string.validate_position_message).setCancelable(false);

            builder.setPositiveButton(R.string.validate_position_positive, (DialogInterface dialog, int which) -> {
                handleGuess(marker.getPosition());
            });

            builder.setNegativeButton(R.string.validate_position_negative, (DialogInterface dialog, int which) -> {
                dialog.cancel();
            });

            builder.show();
        }

        return isCurrentlyGuessing;
    }

    private void gameStart() {
        if(!isGameStarted) {
            isGameStarted = true;
            gameProgress();
        }
    }

    private void gameProgress() {
        if(stillPlacesToFind()) {
            isCurrentlyGuessing = true;
            streetView.setPosition(placesModel.getPlaces().get(currPlaceIndex), 50);
        } else {
            isGameFinished = true;
            handleEndOfGame();
        }
    }

    private boolean stillPlacesToFind() {
        return currPlaceIndex < placesModel.getPlaces().size();
    }

    private void handleGuess(LatLng guessCoordinates) {
        LatLng correctCoordinates = placesModel.getPlaces().get(currPlaceIndex);

        isCurrentlyGuessing = false;

        map.addMarker(new MarkerOptions()
                .position(correctCoordinates));

        map.addPolyline(new PolylineOptions()
                .add(guessCoordinates, correctCoordinates)
                .color(R.color.fbutton_color_pumpkin));

        double distance = DistanceUtil.distanceBetweenInKilometers(guessCoordinates, correctCoordinates);

        currScore += scoreCalculator.calculateScore(
                        correctCoordinates,
                        guessCoordinates,
                        distance,
                        difficulty
        );

        String resultPhrase = getResources().getString(R.string.result_phrase)
                .replace("XXX", DistanceUtil.distanceToString(distance));

        new AlertDialog.Builder(this).setMessage(resultPhrase)
                .setPositiveButton(R.string.OK, (DialogInterface dialog, int which) -> {
                    map.animateCamera(CameraUpdateFactory.zoomOut());
                    LatLngBounds bounds = LatLngBounds.builder()
                            .include(guessCoordinates)
                            .include(correctCoordinates)
                            .build();
                    map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 5));
        }).show();

        currPlaceIndex++;

        String scorePhrase = getResources().getString(R.string.snackbar_score) + " " + currScore;

        final ViewGroup contentView = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);

        Snackbar snackbar = Snackbar.make(contentView, scorePhrase, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.snackbar_next_place, view -> {
                    map.clear();
                    currMarker = null;
                    snackbar.dismiss();
                    gameProgress();
        });
        snackbar.show();
    }

    private void handleEndOfGame() {
        String endGamePhrase = getResources().getString(R.string.end_game_message) + " " + currScore;

        new AlertDialog.Builder(this)
                .setTitle(R.string.end_game_title)
                .setMessage(endGamePhrase)
                .setPositiveButton(R.string.OK, (DialogInterface dialog, int which) -> {
                    finish();
        }).show();
    }
}
