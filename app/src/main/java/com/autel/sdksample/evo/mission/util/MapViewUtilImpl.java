package com.autel.sdksample.evo.mission.util;

import android.os.Bundle;
import android.view.View;

import com.autel.sdksample.evo.mission.fragment.MapFragment;


/**
 * Created by A15387 on 2017/9/1.
 */

@SuppressWarnings("DefaultFileTemplate")
public interface MapViewUtilImpl {

    void initMap(MapFragment.OnMapReadyCallBack onMapReadyCallback);
    MapModelImpl getMapModel();
    void onCreate(Bundle savedInstanceState);
    void onResume();
    void onPause();
    void onDestroy();
    View getMapView();

}
