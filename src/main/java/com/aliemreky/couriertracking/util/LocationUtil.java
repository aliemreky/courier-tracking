package com.aliemreky.couriertracking.util;

import org.springframework.stereotype.Component;

import static com.aliemreky.couriertracking.util.GeneralConstant.AVERAGE_RADIUS_OF_EARTH;

@Component
public class LocationUtil {

    private static LocationUtil instance;

    private LocationUtil() {
    }

    public static synchronized LocationUtil getInstance() {
        if (instance == null) {
            instance = new LocationUtil();
        }
        return instance;
    }

    public double calculateDistance(double startLatitude, double startLongitude, double endLatitude, double endLongitude) {

        double latDistance = Math.toRadians(startLatitude - endLatitude);
        double lngDistance = Math.toRadians(startLongitude - endLongitude);

        double a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)) +
                (Math.cos(Math.toRadians(startLatitude))) *
                        (Math.cos(Math.toRadians(endLatitude))) *
                        (Math.sin(lngDistance / 2)) *
                        (Math.sin(lngDistance / 2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return AVERAGE_RADIUS_OF_EARTH * c;
    }
}
