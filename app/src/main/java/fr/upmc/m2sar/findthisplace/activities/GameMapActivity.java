package fr.upmc.m2sar.findthisplace.activities;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Date;

import fr.upmc.m2sar.findthisplace.R;
import fr.upmc.m2sar.findthisplace.game.GameDifficulty;
import fr.upmc.m2sar.findthisplace.game.GameMode;
import fr.upmc.m2sar.findthisplace.game.IScoreCalculatorStrategy;
import fr.upmc.m2sar.findthisplace.model.PlacesViewModel;
import fr.upmc.m2sar.findthisplace.model.Score;
import fr.upmc.m2sar.findthisplace.model.ScoreViewModel;
import fr.upmc.m2sar.findthisplace.model.StaticPlaces;

public class GameMapActivity extends FragmentActivity implements
        OnMapReadyCallback,
        OnStreetViewPanoramaReadyCallback,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener {

    private static String TAG = "GameMapActivity";

    private GoogleMap map;
    private StreetViewPanorama streetView;

    private PlacesViewModel placesModel;
    private ScoreViewModel scoresModel;

    private GameDifficulty difficulty;
    private GameMode mode;
    private MarkerOptions currMarker;
    private long currScore = 0;
    private int currPlaceIndex = 0;
    private boolean isGameFinished = false;
    private boolean isGameStarted = false;
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

        placesModel = ViewModelProviders.of(this).get(PlacesViewModel.class);
        placesModel.update(StaticPlaces.getRandomPlacesForDifficulty(difficulty, 10));

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

        streetView.setPosition(new LatLng(48.85721, 2.34144));
        //gameStart();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Log.d(TAG, "onMapClick");

        /*MarkerOptions newMarker = new MarkerOptions().position(latLng).draggable(true);
        this.map.clear();
        this.map.addMarker(newMarker);
        this.currMarker = newMarker;*/
        isGameFinished = true;
        currScore++;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(isGameFinished) {
            scoresModel.getScores().getValue().add(new Score("toto", new Date().getTime(), difficulty.name(), currScore));
            scoresModel.saveData();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(currMarker == null || marker == null || !isGameStarted)
            return false;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.validate_position_message).setCancelable(false);

        builder.setPositiveButton(R.string.validate_position_positive, (DialogInterface dialog, int which) -> {
            handleGuess(marker.getPosition());
        });

        builder.setNegativeButton(R.string.validate_position_negative, (DialogInterface dialog, int which) -> {
            dialog.cancel();
        });

        builder.show();

        return true;
    }

    private void gameStart() {
        if(!isGameStarted) {
            isGameStarted = true;
            gameProgress();
        }
    }

    private void gameProgress() {
        if(stillPlacesToFind()) {
            streetView.setPosition(placesModel.getPlaces().get(currPlaceIndex));
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

        map.addPolyline(new PolylineOptions()
                .add(guessCoordinates, correctCoordinates)
                .color(R.color.fbutton_color_pumpkin));

        currScore += scoreCalculator.calculateScore(
                        correctCoordinates,
                        guessCoordinates,
                        difficulty
                    );
        currPlaceIndex++;
        gameProgress();
    }

    private void handleEndOfGame() {
        //TODO
    }
}
