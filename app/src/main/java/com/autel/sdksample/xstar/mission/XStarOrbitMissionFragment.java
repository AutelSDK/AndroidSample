package com.autel.sdksample.xstar.mission;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.autel.common.mission.AutelMission;
import com.autel.common.mission.OrbitFinishedAction;
import com.autel.common.mission.xstar.OrbitMission;
import com.autel.sdksample.R;
import com.autel.sdksample.base.mission.AutelLatLng;
import com.autel.sdksample.base.mission.MapOperator;
import com.autel.sdksample.base.mission.adapter.OrbitFinishActionAdapter;
import com.autel.sdksample.base.mission.fragment.OrbitMissionFragment;


public class XStarOrbitMissionFragment extends OrbitMissionFragment {
    private Spinner finishActionSpinner;
    EditText orbitSpeed;
    EditText orbitReturnHeight;
    EditText orbitCount;
    EditText orbitRadius;
    private OrbitFinishActionAdapter finishActionAdapter = null;
    private OrbitFinishedAction finishedAction = OrbitFinishedAction.HOVER;
    @SuppressLint("ValidFragment")
    public XStarOrbitMissionFragment(MapOperator mMapOperator){
        super(mMapOperator);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = createView(R.layout.fragment_mission_menu_orbit);
        orbitSpeed = (EditText) view.findViewById(R.id.orbitSpeed);
        orbitReturnHeight = (EditText) view.findViewById(R.id.orbitReturnHeight);
        orbitCount = (EditText) view.findViewById(R.id.orbitCount);
        orbitRadius = (EditText) view.findViewById(R.id.orbitRadius);

        finishActionSpinner = (Spinner) view.findViewById(R.id.finishAction);
        finishActionAdapter = new OrbitFinishActionAdapter(getContext());
        finishActionSpinner.setAdapter(finishActionAdapter);
        finishActionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                finishedAction = (OrbitFinishedAction)parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    public AutelMission createAutelMission() {
        AutelLatLng autelLatLng = getOrbitPoint();
        if (null == autelLatLng) {
            return null;
        }
        OrbitMission orbitMission = new OrbitMission();
        orbitMission.lat = (float) autelLatLng.latitude;
        orbitMission.lng = (float) autelLatLng.longitude;

        String valueHeight = orbitReturnHeight.getText().toString();
        orbitMission.finishReturnHeight = isEmpty(valueHeight) ? 40 : Integer.valueOf(valueHeight);
        orbitMission.finishedAction = finishedAction;
        String valueSpeed = orbitSpeed.getText().toString();
        orbitMission.speed = isEmpty(valueSpeed) ? 1 : Float.valueOf(valueSpeed);
        String valueRound = orbitCount.getText().toString();
        orbitMission.laps = isEmpty(valueRound) ? 3 : Short.valueOf(valueRound);
        String valueRadius = orbitRadius.getText().toString();
        orbitMission.radius = isEmpty(valueRadius) ? 3 : Short.valueOf(valueRadius);
        return orbitMission;
    }
}
