package fr.upmc.m2sar.findthisplace.game;

import com.google.android.gms.maps.model.LatLng;

/**
 * Calcul du score en prenant comme score maximal la moitié de la circonférence de la Terre
 */
public class CircumferenceBasedScoreCalculator implements IScoreCalculatorStrategy {

    @Override
    public long calculateScore(LatLng expected, LatLng guess, double distance, GameDifficulty difficulty) {
        return MAX_SCORE_LVL0 - (long) distance + (difficulty.ordinal()*DIFFICULTY_MULTIPLIER_BONUS);
    }

}
