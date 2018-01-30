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

import com.google.android.gms.games.Game;
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
import fr.upmc.m2sar.findthisplace.game.CountryBasedScoreCalculator;
import fr.upmc.m2sar.findthisplace.game.GameDifficulty;
import fr.upmc.m2sar.findthisplace.game.GameMode;
import fr.upmc.m2sar.findthisplace.game.IScoreCalculatorStrategy;
import fr.upmc.m2sar.findthisplace.game.ReverseCircumferenceScoreCalculator;
import fr.upmc.m2sar.findthisplace.model.PlacesViewModel;
import fr.upmc.m2sar.findthisplace.model.PlayerProfile;
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

    private static final String STATE_PLAYERNAME = "playerName";
    private static final String STATE_DIFFICULTY = "difficulty";
    private static final String STATE_MODE = "mode";
    private static final String STATE_MARKER = "currMarker";
    private static final String STATE_GUESS_COORDS = "currGuessCoords";
    private static final String STATE_SCORE = "currScore";
    private static final String STATE_PLACE_INDEX = "currPlaceIndex";
    private static final String STATE_GAME_FINISHED = "isGameFinished";
    private static final String STATE_GAME_STARTED = "isGameStarted";
    private static final String STATE_GAME_GUESSING = "isCurrentlyGuessing";

    private String playerName;
    private GameDifficulty difficulty;
    private GameMode mode;
    private MarkerOptions currMarker;
    private LatLng currGuessCoords;
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
        playerName = intent.getStringExtra(PlayerProfile.class.getName());
        difficulty = (GameDifficulty) intent.getSerializableExtra(GameDifficulty.class.getName());
        mode = (GameMode) intent.getSerializableExtra(GameMode.class.getName());

        if(mode == GameMode.COUNTRY) {
            scoreCalculator = new CountryBasedScoreCalculator(this);
        }
        else if(mode == GameMode.REVERSE) {
            scoreCalculator = new ReverseCircumferenceScoreCalculator();
        }
        else {
            scoreCalculator = new CircumferenceBasedScoreCalculator();
        }

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

        // dans le cas où l'activité est restaurée (après rotation)
        // onMapReady est appelée et currMarker et currGuessCoords ne sont pas null
        // on replace donc les éléments sur la map
        if(currMarker != null) {
            map.addMarker(currMarker);
        }
        if(currGuessCoords != null) {
            showAnswer(currGuessCoords, placesModel.getPlaces().get(currPlaceIndex));
        }
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
            scoresModel.getScores().getValue().add(new Score(playerName, new Date(), difficulty.name(), mode.name(), currScore));
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
                currGuessCoords = marker.getPosition();
                handleGuess(currGuessCoords);
            });

            builder.setNegativeButton(R.string.validate_position_negative, (DialogInterface dialog, int which) -> {
                dialog.cancel();
            });

            builder.show();
        }

        return isCurrentlyGuessing;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putString(STATE_PLAYERNAME, playerName);
        savedInstanceState.putSerializable(STATE_DIFFICULTY, difficulty);
        savedInstanceState.putSerializable(STATE_MODE, mode);
        savedInstanceState.putParcelable(STATE_MARKER, currMarker);
        savedInstanceState.putParcelable(STATE_GUESS_COORDS, currGuessCoords);
        savedInstanceState.putLong(STATE_SCORE, currScore);
        savedInstanceState.putInt(STATE_PLACE_INDEX, currPlaceIndex);
        savedInstanceState.putBoolean(STATE_GAME_FINISHED, isGameFinished);
        savedInstanceState.putBoolean(STATE_GAME_STARTED, isGameStarted);
        savedInstanceState.putBoolean(STATE_GAME_GUESSING, isCurrentlyGuessing);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        playerName = savedInstanceState.getString(STATE_PLAYERNAME);
        difficulty = (GameDifficulty) savedInstanceState.getSerializable(STATE_DIFFICULTY);
        mode = (GameMode) savedInstanceState.getSerializable(STATE_MODE);
        currMarker = savedInstanceState.getParcelable(STATE_MARKER);
        currGuessCoords = savedInstanceState.getParcelable(STATE_GUESS_COORDS);
        currScore = savedInstanceState.getLong(STATE_SCORE);
        currPlaceIndex = savedInstanceState.getInt(STATE_PLACE_INDEX);
        isGameFinished = savedInstanceState.getBoolean(STATE_GAME_FINISHED);
        isGameStarted = savedInstanceState.getBoolean(STATE_GAME_STARTED);
        isCurrentlyGuessing = savedInstanceState.getBoolean(STATE_GAME_GUESSING);
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

        double distance = DistanceUtil.distanceBetweenInKilometers(guessCoordinates, correctCoordinates);

        currScore += scoreCalculator.calculateScore(
                        correctCoordinates,
                        guessCoordinates,
                        distance,
                        difficulty
        );

        showAnswer(guessCoordinates, correctCoordinates);

        String resultPhrase = getResources().getString(R.string.result_phrase)
                .replace("XXX", DistanceUtil.distanceToString(distance));

        new AlertDialog.Builder(this).setMessage(resultPhrase)
                .setPositiveButton(R.string.OK, (DialogInterface dialog, int which) -> {
                    map.animateCamera(CameraUpdateFactory.zoomOut());
                    LatLngBounds bounds = LatLngBounds.builder()
                            .include(guessCoordinates)
                            .include(correctCoordinates)
                            .build();
                    map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        }).show();
    }

    private void showAnswer(LatLng guessCoordinates, LatLng correctCoordinates) {
        map.addMarker(new MarkerOptions()
                .position(correctCoordinates));

        map.addPolyline(new PolylineOptions()
                .add(guessCoordinates, correctCoordinates)
                .color(R.color.fbutton_color_pumpkin));

        String scorePhrase = getResources().getString(R.string.snackbar_score) + " " + currScore;

        final ViewGroup contentView = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);

        Snackbar snackbar = Snackbar.make(contentView, scorePhrase, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.snackbar_next_place, view -> {
            map.clear();
            currMarker = null;
            currGuessCoords = null;
            currPlaceIndex++;
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
