package com.autel.sdksample.evo.mission.util;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.autel.sdksample.evo.mission.fragment.MapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;

import java.lang.reflect.Field;

/**
 * Created by A15387 on 2017/9/1.
 */

@SuppressWarnings("DefaultFileTemplate")
public class GmapViewUtilImpl implements MapViewUtilImpl {

    private MapView mapView;
    private GoogleMap gMap;
    private MapModelImpl mapModel;

    public GmapViewUtilImpl(Context mContext) {
        mapView = new MapView(mContext);
    }

    @Override
    public void initMap(final MapFragment.OnMapReadyCallBack onMapReadyCallBack) {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;
                gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                UiSettings gmsSetting = gMap.getUiSettings();
                gmsSetting.setZoomControlsEnabled(false);
                gmsSetting.setCompassEnabled(false);
                gmsSetting.setRotateGesturesEnabled(false);
                gmsSetting.setMapToolbarEnabled(false);
                mapModel = new GMapModel(googleMap);
                onMapReadyCallBack.onMapReady();
            }
        });
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
        try{
            //TODO:记得在更新googlesdk的时候检查下是否有内存泄露，没有的话去掉这里。
            Field contextField = MapView.class.getDeclaredField("zzbox");
            contextField.setAccessible(true);
            contextField.set(mapView,null);
            Field contextField1 = MapView.class.getDeclaredField("mContext");
            contextField1.setAccessible(true);
            contextField1.set(mapView,null);
        }catch (Exception e){
            e.printStackTrace();
        }
        mapView = null;
    }

    @Override
    public View getMapView() {
        return mapView;
    }
}
