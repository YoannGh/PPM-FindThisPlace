package fr.upmc.m2sar.findthisplace.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;

import fr.upmc.m2sar.findthisplace.R;
import fr.upmc.m2sar.findthisplace.game.GameDifficulty;

public class GameMapActivity extends FragmentActivity implements
        OnMapReadyCallback,
        OnStreetViewPanoramaReadyCallback,
        GoogleMap.OnMapClickListener {

    private static String TAG = "GameMapActivity";

    private GoogleMap map;
    private StreetViewPanorama streetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_map);

        ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        ((StreetViewPanoramaFragment) getFragmentManager().findFragmentById(R.id.streetviewpanorama)).getStreetViewPanoramaAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady");
        this.map = googleMap;
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        Log.d(TAG, "onStreetViewPanoramaReady");
        this.streetView = streetViewPanorama;

        streetView.setPosition(new LatLng(48.599556, 2.266724));
        GameDifficulty gd = GameDifficulty.values()[1];
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Log.d(TAG, "onMapClick");
    }

    @Override
    public void onStop() {
        super.onStop();
        // TODO: sauvegarder le score
    }
}
