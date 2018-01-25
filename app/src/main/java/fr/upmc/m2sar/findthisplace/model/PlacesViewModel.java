package fr.upmc.m2sar.findthisplace.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Yo on 25/01/2018.
 */

/**
 * La classe ViewModel permet de stocker des donneés qui seront
 * capables de survivre aux changements de configuration de l'application
 * tels que les rotations d'écran
 */
public class PlacesViewModel extends ViewModel {

    private static final String TAG = "PlacesViewModel";

    private LiveData<List<LatLng>> places;

    public void update(List<LatLng> places) {
        if(this.places != null) {
            return;
        }
        MutableLiveData<List<LatLng>> data = new MutableLiveData<List<LatLng>>();
        data.setValue(places);
        this.places = data;
    }

    public List<LatLng> getPlaces() {
        return places.getValue();
    }

}
