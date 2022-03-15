package com.autel.sdksample.evo.mission.fragment;

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
import com.autel.common.mission.evo.EvoOrbitMission;
import com.autel.common.mission.evo.OrbitEntryDirection;
import com.autel.common.mission.evo.OrbitHeadingDirection;
import com.autel.common.mission.evo.OrbitRotateDirection;
import com.autel.sdksample.R;
import com.autel.sdksample.base.mission.AutelLatLng;
import com.autel.sdksample.base.mission.MapOperator;
import com.autel.sdksample.base.mission.adapter.OrbitFinishActionAdapter;
import com.autel.sdksample.base.mission.fragment.OrbitMissionFragment;
import com.autel.sdksample.evo.mission.adapter.EvoEntryDirectionAdapter;
import com.autel.sdksample.evo.mission.adapter.EvoHeadingDirectionAdapter;
import com.autel.sdksample.evo.mission.adapter.EvoRotateDirectionAdapter;


public class EvoOrbitMissionFragment extends OrbitMissionFragment {
    private Spinner finishActionSpinner;
    private Spinner entryDirectionSpinner;
    private Spinner headingDirectionSpinner;
    private Spinner rotateDirectionSpinner;
    EditText orbitSpeed;
    EditText orbitReturnHeight;
    EditText orbitCount;
    EditText orbitRadius;
    EditText orbitHeight;
    EditText orbitRemainDegree;
    private OrbitFinishActionAdapter finishActionAdapter = null;
    private EvoEntryDirectionAdapter entryDirectionAdapter = null;
    private EvoHeadingDirectionAdapter headingDirectionAdapter = null;
    private EvoRotateDirectionAdapter rotateDirectionAdapter = null;
    private OrbitFinishedAction finishedAction = OrbitFinishedAction.HOVER;
    private OrbitEntryDirection entryDirection = OrbitEntryDirection.EAST;
    private OrbitHeadingDirection headingDirection = OrbitHeadingDirection.FACE_TO_POI;
    private OrbitRotateDirection rotateDirection = OrbitRotateDirection.Clockwise;

    public EvoOrbitMissionFragment() {
        super();
    }

    @SuppressLint("ValidFragment")
    public EvoOrbitMissionFragment(MapOperator mMapOperator) {
        super(mMapOperator);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = createView(R.layout.fragment_mission_menu_orbit);
        orbitSpeed = (EditText) view.findViewById(R.id.orbitSpeed);
        orbitReturnHeight = (EditText) view.findViewById(R.id.orbitReturnHeight);
        orbitCount = (EditText) view.findViewById(R.id.orbitCount);
        orbitRadius = (EditText) view.findViewById(R.id.orbitRadius);
        orbitHeight = (EditText) view.findViewById(R.id.orbitHeight);
        orbitRemainDegree = (EditText) view.findViewById(R.id.orbitRemainDegree);

        finishActionSpinner = (Spinner) view.findViewById(R.id.finishAction);
        finishActionAdapter = new OrbitFinishActionAdapter(getContext());
        finishActionSpinner.setAdapter(finishActionAdapter);
        finishActionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                finishedAction = (OrbitFinishedAction) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        entryDirectionSpinner = (Spinner) view.findViewById(R.id.entryDirection);
        entryDirectionAdapter = new EvoEntryDirectionAdapter(getContext());
        entryDirectionSpinner.setAdapter(entryDirectionAdapter);
        entryDirectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                entryDirection = (OrbitEntryDirection) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        headingDirectionSpinner = (Spinner) view.findViewById(R.id.headingDirection);
        headingDirectionAdapter = new EvoHeadingDirectionAdapter(getContext());
        headingDirectionSpinner.setAdapter(headingDirectionAdapter);
        headingDirectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                headingDirection = (OrbitHeadingDirection) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        rotateDirectionSpinner = (Spinner) view.findViewById(R.id.rotateDirection);
        rotateDirectionAdapter = new EvoRotateDirectionAdapter(getContext());
        rotateDirectionSpinner.setAdapter(rotateDirectionAdapter);
        rotateDirectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rotateDirection = (OrbitRotateDirection) parent.getAdapter().getItem(position);
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
        EvoOrbitMission orbitMission = EvoOrbitMission.createMission();
        orbitMission.latitude = autelLatLng.latitude;
        orbitMission.longitude = autelLatLng.longitude;

        String valueReturnHeight = orbitReturnHeight.getText().toString();
        orbitMission.finishReturnHeight = isEmpty(valueReturnHeight) ? 40 : Integer.valueOf(valueReturnHeight);
        orbitMission.finishedAction = finishedAction;
        String valueSpeed = orbitSpeed.getText().toString();
        orbitMission.speed = isEmpty(valueSpeed) ? 1 : Integer.valueOf(valueSpeed);
        String valueRound = orbitCount.getText().toString();
        orbitMission.cycles = isEmpty(valueRound) ? 3 : Integer.valueOf(valueRound);
        String valueRadius = orbitRadius.getText().toString();
        orbitMission.radius = isEmpty(valueRadius) ? 3 : Integer.valueOf(valueRadius);
        String valueHeight = orbitHeight.getText().toString();
        orbitMission.altitude = isEmpty(valueHeight) ? 3 : Integer.valueOf(valueHeight);
        String valueRemainDegree = orbitRemainDegree.getText().toString();
        orbitMission.remainDegree = isEmpty(valueRemainDegree) ? 3 : Integer.valueOf(valueRemainDegree);

        orbitMission.mRotateDirection = rotateDirection;
        orbitMission.mHeadingDirection = headingDirection;
        orbitMission.mEntryDirection = entryDirection;
        return orbitMission;
    }
}
