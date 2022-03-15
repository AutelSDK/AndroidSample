package com.autel.sdksample.base.mission;

/**
 *
 */

public interface MapOperator {
    void updateOrbit(double lat, double lot);
    void resetMap();
    void addWayPointMarker(double lat, double lot);
    void setLocationChangeListener(MapActivity.LocationChangeListener locationChangeListener);
}
