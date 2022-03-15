package com.autel.sdksample.evo.mission.util;

import android.location.Location;

/**
 * Created by A13087 on 2016/5/25.
 */
@SuppressWarnings("DefaultFileTemplate")
public class DistanceUtils {

	public static int distanceBetweenPoints(double startLat, double startLng, double endLat, double endLng) {

		float[] results = new float[3];
		Location.distanceBetween(startLat, startLng, endLat, endLng, results);
		return (int) results[0];
	}

	public static float distanceBetweenPointsAsFloat(double startLat, double startLng, double endLat, double endLng) {
		float[] results = new float[3];
		Location.distanceBetween(startLat, startLng, endLat, endLng, results);
		return results[0];
	}


}