package fr.upmc.m2sar.findthisplace.game;

import com.google.android.gms.maps.model.LatLng;

public class ReverseCircumferenceScoreCalculator implements IScoreCalculatorStrategy {

    @Override
    public long calculateScore(LatLng expected, LatLng guess, double distance, GameDifficulty difficulty) {
        return (long) distance + (difficulty.ordinal()*DIFFICULTY_MULTIPLIER_BONUS);
    }
}
