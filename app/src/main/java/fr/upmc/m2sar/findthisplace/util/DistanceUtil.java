package fr.upmc.m2sar.findthisplace.util;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;

/**
 * Created by Yo on 27/01/2018.
 */

public class DistanceUtil {

    private static final DecimalFormat df = new DecimalFormat(".##");

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

    public static String distanceToString(double distanceInKilometers) {
        return df.format(distanceInKilometers) + "km";
    }
}
