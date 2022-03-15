package com.autel.sdksample.base.mission.fragment;

import android.annotation.SuppressLint;

import com.autel.common.mission.xstar.Waypoint;
import com.autel.sdksample.base.mission.AutelLatLng;
import com.autel.sdksample.base.mission.MapOperator;
import com.autel.sdksample.base.mission.MapRectifyUtil;
import com.autel.sdksample.base.mission.widget.WaypointSettingDialog;


public abstract class WaypointMissionFragment extends MissionFragment {

    @SuppressLint("ValidFragment")
    public WaypointMissionFragment(MapOperator mMapOperator) {
        super(mMapOperator);
    }

    public WaypointMissionFragment() {
    }


    @Override
    public void onMapClick(double lat, double lot) {
        AutelLatLng latLng = MapRectifyUtil.gcj2wgs(new AutelLatLng(lat, lot));
        mMapOperator.addWayPointMarker(lat, lot);
        waypointAdded(latLng);
    }

    @Override
    public void onMarkerClick(int position) {
        Waypoint waypoint = getWaypoint(position);
        if(null == waypoint){
            return;
        }

        WaypointSettingDialog waypointSettingDialog = new WaypointSettingDialog(this.getActivity(), position, waypoint);
        waypointSettingDialog.showDialog();
    }

    protected abstract Waypoint getWaypoint(int index);

    protected abstract void waypointAdded(AutelLatLng latLng);
}
