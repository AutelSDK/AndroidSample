package com.autel.sdksample.xstar.mission;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.autel.common.mission.AutelCoordinate3D;
import com.autel.common.mission.AutelMission;
import com.autel.common.mission.xstar.Waypoint;
import com.autel.common.mission.xstar.WaypointFinishedAction;
import com.autel.common.mission.xstar.WaypointMission;
import com.autel.sdksample.R;
import com.autel.sdksample.base.mission.AutelLatLng;
import com.autel.sdksample.base.mission.MapActivity;
import com.autel.sdksample.base.mission.MapOperator;
import com.autel.sdksample.base.mission.adapter.WaypointFinishActionAdapter;
import com.autel.sdksample.base.mission.fragment.WaypointMissionFragment;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class XStarWaypointFragment extends WaypointMissionFragment {
    private Spinner finishActionSpinner;
    private EditText waypointSpeed;
    private EditText waypointReturnHeight;
    private EditText waypointHeight;
    private WaypointFinishActionAdapter finishActionAdapter = null;
    private WaypointFinishedAction finishedAction = WaypointFinishedAction.HOVER;
    List<Waypoint> wayPointList = new ArrayList<>();

    @SuppressLint("ValidFragment")
    public XStarWaypointFragment(MapOperator mMapOperator) {
        super(mMapOperator);
    }

    public XStarWaypointFragment() {
        super();
    }

    @Override
    protected Waypoint getWaypoint(int index) {
        if (index >= wayPointList.size()) {
            return null;
        }
        return wayPointList.get(index);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = createView(R.layout.fragment_mission_menu_waypoint);

        ((MapActivity) getActivity()).setWaypointHeightListener(new MapActivity.WaypointHeightListener() {
            @Override
            public int fetchHeight() {
                String valueHeight = waypointHeight.getText().toString();
                return isEmpty(valueHeight) ? 50 : Integer.valueOf(valueHeight);
            }
        });
        finishActionSpinner = (Spinner) view.findViewById(R.id.finishAction);
        finishActionAdapter = new WaypointFinishActionAdapter(getContext());
        finishActionSpinner.setAdapter(finishActionAdapter);
        finishActionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                finishedAction = (WaypointFinishedAction) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        waypointSpeed = (EditText) view.findViewById(R.id.waypointSpeed);
        waypointReturnHeight = (EditText) view.findViewById(R.id.waypointReturnHeight);
        waypointHeight = (EditText) view.findViewById(R.id.waypointHeight);

        return view;
    }

    @Override
    public AutelMission createAutelMission() {
        WaypointMission waypointMission = new WaypointMission();
        waypointMission.finishedAction = finishedAction;
        String valueSpeed = waypointSpeed.getText().toString();
        waypointMission.speed = isEmpty(valueSpeed) ? 4 : Integer.valueOf(valueSpeed);
        String valueReturnHeight = waypointReturnHeight.getText().toString();
        waypointMission.finishReturnHeight = isEmpty(valueReturnHeight) ? 40 : Integer.valueOf(valueReturnHeight);

        waypointMission.wpList = wayPointList;
        return waypointMission;
    }


    @Override
    protected void waypointAdded(AutelLatLng latLng) {
        String wHeight = waypointHeight.getText().toString();
        Waypoint waypoint = new Waypoint(new AutelCoordinate3D(latLng.latitude, latLng.longitude, isEmpty(wHeight) ? 0 : Double.valueOf(wHeight)));
        wayPointList.add(waypoint);
    }

    public void hideBar() {
        if (getActivity() == null) {
            return;
        }
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        getActivity().getWindow().setAttributes(params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        wayPointList = null;
    }
}
