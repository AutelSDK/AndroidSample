package com.autel.sdksample.evo.mission.fragment;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.autel.common.mission.AutelMission;
import com.autel.common.mission.FollowFinishedAction;
import com.autel.common.mission.xstar.FollowMission;
import com.autel.sdksample.R;
import com.autel.sdksample.base.mission.MapActivity;
import com.autel.sdksample.base.mission.MapOperator;
import com.autel.sdksample.base.mission.adapter.FollowFinishActionAdapter;
import com.autel.sdksample.base.mission.fragment.MissionFragment;


public class EvoFollowMissionFragment extends MissionFragment {
    private Spinner finishActionSpinner;
    FollowMission followMission;
    Location mLocation;
    private FollowFinishActionAdapter finishActionAdapter = null;
    private FollowFinishedAction finishedAction = FollowFinishedAction.RETURN_HOME;
    EditText followReturnHeight;

    public EvoFollowMissionFragment(){
        super();
    }
    @SuppressLint("ValidFragment")
    public EvoFollowMissionFragment(MapOperator mMapOperator){
        super(mMapOperator);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = createView(R.layout.fragment_mission_menu_follow);
        ((MapActivity) getActivity()).setLocationChangeListener(new MapActivity.LocationChangeListener() {
            @Override
            public void locationChanged(Location location) {
                mLocation = location;
                if (null != followMission) {
                    followMission.update(location);
                }
                Log.v("followTest", "location " + location);
            }
        });
        followReturnHeight = (EditText) view.findViewById(R.id.followReturnHeight);
        finishActionAdapter = new FollowFinishActionAdapter(getContext());
        finishActionSpinner = (Spinner) view.findViewById(R.id.finishAction);
        finishActionSpinner.setAdapter(finishActionAdapter);
        finishActionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                finishedAction = (FollowFinishedAction)parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    public AutelMission createAutelMission() {
        String valueHeight = followReturnHeight.getText().toString();
        followMission = FollowMission.create();
        followMission.location = mLocation;
        followMission.finishedAction = finishedAction;
        followMission.finishReturnHeight = isEmpty(valueHeight) ? 40 : Integer.valueOf(valueHeight);
        mMapOperator.setLocationChangeListener(new MapActivity.LocationChangeListener() {
            @Override
            public void locationChanged(Location location) {
                followMission.update(location);
            }
        });
        return followMission;
    }

    @Override
    public void onMapClick(double lat, double lot) {

    }

    @Override
    public void onMarkerClick(int position) {

    }

    public void onDestroy() {
        super.onDestroy();
        ((MapActivity) getActivity()).setLocationChangeListener(null);
    }
}
