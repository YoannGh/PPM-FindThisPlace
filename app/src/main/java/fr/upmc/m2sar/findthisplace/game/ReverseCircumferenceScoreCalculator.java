package fr.upmc.m2sar.findthisplace.game;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Yo on 28/01/2018.
 */

public class ReverseCircumferenceScoreCalculator implements IScoreCalculatorStrategy {

    @Override
    public long calculateScore(LatLng expected, LatLng guess, double distance, GameDifficulty difficulty) {
        return (long) distance + (difficulty.ordinal()*DIFFICULTY_MULTIPLIER_BONUS);
    }
}
