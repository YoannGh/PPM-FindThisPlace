package fr.upmc.m2sar.findthisplace.game;

import com.google.android.gms.maps.model.LatLng;

import fr.upmc.m2sar.findthisplace.util.DistanceUtil;

public interface IScoreCalculatorStrategy {

    long MAX_SCORE_LVL0 = DistanceUtil.EARTH_CIRCUMFERENCE/2;
    long DIFFICULTY_MULTIPLIER_BONUS = 1000;

    long calculateScore(LatLng expected, LatLng guess, double distance, GameDifficulty difficulty);
}
