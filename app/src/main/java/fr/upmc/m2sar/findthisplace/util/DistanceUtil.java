package fr.upmc.m2sar.findthisplace.util;

import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;

public class DistanceUtil {

    public static final long EARTH_CIRCUMFERENCE = 40075;

    private static final DecimalFormat df = new DecimalFormat("#.##");

    public static double distanceBetweenInKilometers(LatLng coordA, LatLng coordB) {
        float distances[] = new float[1];
        Location.distanceBetween(
                coordA.latitude,
                coordA.longitude,
                coordB.latitude,
                coordB.longitude,
                distances);
        return distances[0]/1000.0;
    }

    @NonNull
    public static String distanceToString(double distanceInKilometers) {
        return df.format(distanceInKilometers) + "km";
    }

}
