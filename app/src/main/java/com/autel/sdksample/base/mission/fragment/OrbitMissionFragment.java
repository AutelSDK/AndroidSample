package com.autel.sdksample.base.mission.fragment;

import com.autel.sdksample.base.mission.AutelLatLng;
import com.autel.sdksample.base.mission.MapOperator;
import com.autel.sdksample.base.mission.MapRectifyUtil;

public abstract class OrbitMissionFragment extends MissionFragment {
    public OrbitMissionFragment(){
        super();
    }

    public OrbitMissionFragment(MapOperator mMapOperator){
        super(mMapOperator);
    }
    @Override
    public void onMapClick(double lat, double lot) {
        autelLatLng = new AutelLatLng(lat, lot);
        mMapOperator.updateOrbit(lat,lot);
    }

    AutelLatLng autelLatLng = null;

    public AutelLatLng getOrbitPoint() {
        AutelLatLng latLng = MapRectifyUtil.gcj2wgs(autelLatLng);
        return latLng;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        autelLatLng = null;
    }

    @Override
    public void onMarkerClick(int position) {

    }
}
