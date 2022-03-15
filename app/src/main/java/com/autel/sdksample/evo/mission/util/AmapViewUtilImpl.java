package com.autel.sdksample.evo.mission.util;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.autel.sdksample.evo.mission.fragment.MapFragment;

/**
 * Created by A15387 on 2017/9/1.
 */

@SuppressWarnings("DefaultFileTemplate")
public class AmapViewUtilImpl implements MapViewUtilImpl {


    private final MapView mapView;
    private AMap aMap;
    private MapModelImpl mapModel;

    public AmapViewUtilImpl(Context mContext) {
        mapView = new MapView(mContext);
    }

    @Override
    public void initMap(MapFragment.OnMapReadyCallBack onMapReadyCallBack) {
        aMap = mapView.getMap();
        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
        UiSettings amapSettings = aMap.getUiSettings();
        amapSettings.setZoomControlsEnabled(false);
        amapSettings.setRotateGesturesEnabled(false);
        amapSettings.setCompassEnabled(false);
        amapSettings.setScaleControlsEnabled(true);
        amapSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);
        mapModel = new AMapModel(aMap);
        onMapReadyCallBack.onMapReady();
    }

    @Override
    public MapModelImpl getMapModel() {
        return mapModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        mapView.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
    }

    @Override
    public View getMapView() {
        return mapView;
    }
}
