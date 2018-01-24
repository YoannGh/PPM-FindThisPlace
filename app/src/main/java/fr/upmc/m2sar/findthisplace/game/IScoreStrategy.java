package fr.upmc.m2sar.findthisplace.game;

import com.google.android.gms.maps.model.LatLng;

public interface IScoreStrategy {

    long calculateScore(LatLng expected, LatLng guess);
}
