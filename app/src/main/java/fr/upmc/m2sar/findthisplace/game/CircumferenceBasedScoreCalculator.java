package fr.upmc.m2sar.findthisplace.game;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import fr.upmc.m2sar.findthisplace.util.DistanceUtil;

/**
 * Created by Yo on 27/01/2018.
 */

public class CircumferenceBasedScoreCalculator implements IScoreCalculatorStrategy {

    @Override
    public long calculateScore(LatLng expected, LatLng guess, double distance, GameDifficulty difficulty) {
        return MAX_SCORE_LVL0 - (long) distance + (difficulty.ordinal()*DIFFICULTY_MULTIPLIER_BONUS);
    }
}
