package com.autel.sdksample.evo.mission.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.autel.common.remotecontroller.RemoteControllerCommandStickMode;
import com.autel.sdksample.R;
import com.autel.sdksample.evo.mission.bean.MapConstant;
import com.autel.sdksample.evo.mission.bean.OrbitAdvanceDataBean;
import com.autel.sdksample.evo.mission.view.OrbitDataSetView;
import com.autel.sdksample.evo.mission.view.OrbitExecuteView;
import com.autel.sdksample.evo.mission.view.OrbitPositionSetView;
import com.autel.sdksample.evo.mission.widge.MissionDropSelectView;
import com.autel.sdksample.evo.mission.widge.VisualDialogToast;


/**
 * Created by A15387 on 2017/10/19.
 */

public class OrbitFragment extends Fragment {

    public static final int POINT_WAY_AIRCRAFT = 0;
    public static final int POINT_WAY_PHONE = 1;
    public static final int POINT_WAY_MAP = 2;

    private boolean isExecuted = false;


    public static final String TAG = "OrbitFragment";
    private LayoutInflater inflater;
    private FrameLayout titleContainer;
    private FrameLayout bottomContainer;
    private FrameLayout contentContainer;
    private MissionDropSelectView missionDropSelectView;

    private int curPointSetWay = POINT_WAY_AIRCRAFT;
    private TextView directionBtn;
    private ViewGroup containerView;
    private OrbitPositionSetView orbitPositionSetView;
    private OrbitDataSetView orbitDataSetView;
    private OrbitExecuteView orbitExecutedView;
    private boolean isSlideHide;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.fragment_orbit_content,container,false);
        containerView = (ViewGroup) view.findViewById(R.id.orbit_view_container);
        orbitPositionSetView = new OrbitPositionSetView(container.getContext());
        orbitDataSetView = new OrbitDataSetView(container.getContext());
        orbitExecutedView = new OrbitExecuteView(container.getContext());


        orbitExecutedView.setOrbitExecuteViewListener(new OrbitExecuteView.OrbitExecuteViewListener() {
            @Override
            public void hideClick() {
                hideOrShowFragment();
            }

            @Override
            public void exitClick() {
                showExitOrbitModeDialog();
                //mRequestManager.exitOrbitMission();
            }

            @Override
            public void slideHide() {
                isSlideHide = true;
            }

            @Override
            public void pauseClick() {
                //mRequestManager.pauseOrbitMission();
            }
        });
        orbitPositionSetView.setOrbitPositionSetViewListener(new OrbitPositionSetView.OrbitPositionSetViewListener() {
            @Override
            public void exitClick() {
                exitFragment();
            }

            @Override
            public void positionSetWayItemClick(int position) {
                positionSetWayChoice(position);
            }

            @Override
            public void positionSetConfirmClick() {
                positionSetConfirm();
            }
        });
        orbitDataSetView.setOrbitSetViewListener(new OrbitDataSetView.OrbitSetViewListener() {
            @Override
            public void exitClick() {
                exitFragment();
            }

            @Override
            public void executeClick(int finishType) {
                showOrbitExecuteView();
               // mRequestManager.saveOrbitFiles();
               // mRequestManager.startOrbitMission(finishType);
                //ToastUtils.showToast("Save Success");
            }

            @Override
            public void basicClick() {
                //mRequestManager.enableRadiusSetFromDrone(true);
               // mRequestManager.setOrbitBeanData(orbitDataSetView.getBasicData(), OrbitAdvanceDataBean.TYPE_BASIC_MODE);
            }

            @Override
            public void advanceClick() {
                //mRequestManager.enableRadiusSetFromDrone(false);
                //mRequestManager.setOrbitBeanData(orbitDataSetView.getAdvanceData(),OrbitAdvanceDataBean.TYPE_ADVANCE_MODE);
            }

            @Override
            public void basicDataChange(OrbitAdvanceDataBean orbitAdvanceDataBean, int type) {
                //mRequestManager.setOrbitBeanData(orbitAdvanceDataBean,type);
            }

        });

        containerView.addView(orbitPositionSetView);
        return view;
    }

    private void showOrbitExecuteView() {
        isExecuted = true;
        containerView.removeAllViews();
        containerView.addView(orbitExecutedView);
    }

    private void positionSetConfirm() {
        if(curPointSetWay == POINT_WAY_MAP){
          /*  if(!mRequestManager.isOrbitAdd()){
                showToast(ResourcesUtils.getString(R.string.orbit_please_add_a_poi), ToastBeanIcon.ICON_WARN);
                return;
            }
            if(mRequestManager.addOrbitFromMap()){
                mRequestManager.enableGetPointFromMap(MapConstant.MAP_CLICK_MODE_NULL);
            }*/
            showOrbitDataSetView();
        }else if(curPointSetWay == POINT_WAY_PHONE){
           /* if(mRequestManager.addOrbitFromPhone()){
            }*/
            showOrbitDataSetView();
        }else if(curPointSetWay == POINT_WAY_AIRCRAFT){
           /* if(mRequestManager.addOrbitFromDrone()){
            }*/
            showOrbitDataSetView();
        }
    }

    private void showOrbitDataSetView() {
        containerView.removeAllViews();
        containerView.addView(orbitDataSetView);
        //mRequestManager.enableRadiusSetFromDrone(true);
    }

    private void positionSetWayChoice(int position) {
        switch (position){
            case 0:
                curPointSetWay = POINT_WAY_AIRCRAFT;
                //mRequestManager.enableGetPointFromMap(MapConstant.MAP_CLICK_MODE_NULL);
                ((MapFragment)getActivity().getSupportFragmentManager().findFragmentByTag(MapFragment.TAG)).setOnMapClickListener(MapConstant.MAP_CLICK_MODE_NULL);
                //mRequestManager.removeOrbitPoint();
                break;
            case 1:
                curPointSetWay = POINT_WAY_PHONE;
                //mRequestManager.enableGetPointFromMap(MapConstant.MAP_CLICK_MODE_NULL);
                ((MapFragment)getActivity().getSupportFragmentManager().findFragmentByTag(MapFragment.TAG)).setOnMapClickListener(MapConstant.MAP_CLICK_MODE_NULL);
                //mRequestManager.removeOrbitPoint();
                break;
            case 2:
                curPointSetWay = POINT_WAY_MAP;
                //mRequestManager.enableGetPointFromMap(MapConstant.MAP_CLICK_MODE_ORBIT);
                ((MapFragment)getActivity().getSupportFragmentManager().findFragmentByTag(MapFragment.TAG)).setOnMapClickListener(MapConstant.MAP_CLICK_MODE_ORBIT);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //mRequestManager.setMissionLimitCircle();
        //mRequestManager.fetchRemoteControlMode();
    }



    private void exitFragment() {
        //mRequestManager.clearOrbitMode();
        getActivity().getSupportFragmentManager().beginTransaction().remove(OrbitFragment.this).commit();
    }

    public void hideOrShowFragment() {
        if(this.isHidden()){
            getFragmentManager().beginTransaction().show(this).commit();
            if(isSlideHide){
                if(orbitExecutedView != null){
                    orbitExecutedView.setX(0);
                }
            }
        }else{

        }
    }


    private void clearView(){
        titleContainer.removeAllViews();
        contentContainer.removeAllViews();
        bottomContainer.removeAllViews();
    }

    public void showOrbitRadius(String orbitRadiusStr, int missionTextColor) {
        orbitDataSetView.showOrbitRadius(orbitRadiusStr,missionTextColor);
    }

    public void showRemoteControlView(RemoteControllerCommandStickMode remoteControllerCommandStickMode) {
        orbitExecutedView.showRemoteControllerCommandStickMode(remoteControllerCommandStickMode);
    }

    public boolean isOrbitExecuted() {
        return isExecuted;
    }

    public void showExitOrbitModeDialog() {
        VisualDialogToast trackingDialogToast = new VisualDialogToast(getContext(), new VisualDialogToast.OnDialogClickListener() {
            @Override
            public void onClickView1() {

            }

            @Override
            public void onClickView2() {
                exitFragment();
            }

            @Override
            public void onClickView3() {

            }
        });
        trackingDialogToast.setTextView1("",View.INVISIBLE);
        trackingDialogToast.show();
    }
}
