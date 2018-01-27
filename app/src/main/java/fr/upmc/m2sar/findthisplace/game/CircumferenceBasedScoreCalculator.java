package fr.upmc.m2sar.findthisplace.game;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Yo on 27/01/2018.
 */

public class CircumferenceBasedScoreCalculator implements IScoreCalculatorStrategy {

    private static final long EARTH_CIRCUMFERENCE = 40075;
    private static final long MAX_SCORE = EARTH_CIRCUMFERENCE/2;
    private static final long DIFFICULTY_MULTIPLIER_BONUS = 1000;

    @Override
    public long calculateScore(LatLng expected, LatLng guess, double distance, GameDifficulty difficulty) {
        return MAX_SCORE - (long) distance + (difficulty.ordinal()*DIFFICULTY_MULTIPLIER_BONUS);
    }
}
