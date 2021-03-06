package com.autel.sdksample.evo2.mission;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.autel.common.CallbackWithNoParam;
import com.autel.common.CallbackWithOneParam;
import com.autel.common.CallbackWithOneParamProgress;
import com.autel.common.CallbackWithTwoParams;
import com.autel.common.battery.evo.EvoBatteryInfo;
import com.autel.common.camera.CameraProduct;
import com.autel.common.camera.base.CameraPattern;
import com.autel.common.camera.base.MediaMode;
import com.autel.common.error.AutelError;
import com.autel.common.flycontroller.ARMWarning;
import com.autel.common.flycontroller.evo.EvoFlyControllerInfo;
import com.autel.common.mission.AutelCoordinate3D;
import com.autel.common.mission.AutelMission;
import com.autel.common.mission.MissionType;
import com.autel.common.mission.RealTimeInfo;
import com.autel.common.mission.evo.MissionActionType;
import com.autel.common.mission.evo.WaypointAction;
import com.autel.common.mission.evo.WaypointHeadingMode;
import com.autel.common.mission.evo.WaypointType;
import com.autel.common.mission.evo2.Evo2Waypoint;
import com.autel.common.mission.evo2.Evo2WaypointFinishedAction;
import com.autel.common.mission.evo2.Evo2WaypointMission;
import com.autel.common.mission.evo2.Poi;
import com.autel.common.product.AutelProductType;
import com.autel.common.remotecontroller.RemoteControllerInfo;
import com.autel.internal.sdk.mission.evo2.Evo2WaypointRealTimeInfoImpl;
import com.autel.sdk.battery.EvoBattery;
import com.autel.sdk.camera.AutelBaseCamera;
import com.autel.sdk.camera.AutelCameraManager;
import com.autel.sdk.camera.AutelXT701;
import com.autel.sdk.flycontroller.Evo2FlyController;
import com.autel.sdk.mission.MissionManager;
import com.autel.sdk.product.BaseProduct;
import com.autel.sdk.remotecontroller.AutelRemoteController;
import com.autel.sdksample.R;
import com.autel.sdksample.TestApplication;
import com.autel.sdksample.base.camera.fragment.CameraNotConnectFragment;
import com.autel.sdksample.base.camera.fragment.CameraR12Fragment;
import com.autel.sdksample.base.camera.fragment.CameraXB015Fragment;
import com.autel.sdksample.base.camera.fragment.CameraXT701Fragment;
import com.autel.sdksample.base.camera.fragment.CameraXT705Fragment;
import com.autel.sdksample.base.camera.fragment.CameraXT706Fragment;
import com.autel.sdksample.base.camera.fragment.CameraXT709Fragment;
import com.autel.sdksample.util.ThreadUtils;
import com.autel.util.log.AutelLog;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Evo2WayPointActivity extends AppCompatActivity implements View.OnClickListener {

    private Evo2WaypointMission autelMission;
    private Evo2FlyController mEvoFlyController;
    private EvoBattery battery;
    private AutelRemoteController remoteController;
    private MissionManager missionManager;
    private float lowBatteryPercent = 15f;
    private boolean isBatteryOk = false; //????????????????????????
    private boolean isCompassOk = false; //???????????????????????????OK
    private boolean isImuOk = false; //??????IMU??????OK
    private boolean isGpsOk = false; //??????gps??????OK
    private boolean isImageTransOk = false; //????????????????????????OK
    private boolean isCanTakeOff = false; //???????????????
    private String TAG = "Evo2WayPointActivity";
    private Spinner cameraPatternModeList;
    private CameraPattern cameraPattern = CameraPattern.FREE_FLIGHT;

    enum FlyState {
        Prepare, Start, Pause, None
    }

    private FlyState flyState = FlyState.None;

    private int id = 1;
    AutelCameraManager autelCameraManager;
    private AutelBaseCamera autelBaseCamera ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTitle("WayPoint");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evo2_waypoint);

        BaseProduct product = ((TestApplication) getApplicationContext()).getCurrentProduct();
        if (null != product) {
            autelCameraManager = product.getCameraManager();
            autelCameraManager.setCameraChangeListener(new CallbackWithTwoParams<CameraProduct, AutelBaseCamera>() {
                @Override
                public void onSuccess(final CameraProduct data1, final AutelBaseCamera data2) {
                    Log.v(TAG, "initListener onSuccess connect " + data1);
                    if (autelBaseCamera == data2) {
                        return;
                    }
                    autelBaseCamera = data2;


                }

                @Override
                public void onFailure(AutelError error) {
                    Log.v(TAG, "initListener onFailure error " + error.getDescription());
                }
            });
        }
        if (null != product && product.getType() == AutelProductType.EVO_2) {
            missionManager = product.getMissionManager();
            missionManager.setRealTimeInfoListener(new CallbackWithOneParam<RealTimeInfo>() {
                @Override
                public void onSuccess(RealTimeInfo realTimeInfo) {
                    Evo2WaypointRealTimeInfoImpl info = (Evo2WaypointRealTimeInfoImpl) realTimeInfo;
                    AutelLog.d("MissionRunning", "timeStamp:" + info.timeStamp + ",speed:" + info.speed + ",isArrived:" + info.isArrived +
                            ",isDirecting:" + info.isDirecting + ",waypointSequence:" + info.waypointSequence + ",actionSequence:" + info.actionSequence +
                            ",photoCount:" + info.photoCount + ",MissionExecuteState:" + info.executeState + ",remainFlyTime:" + info.remainFlyTime);
                }

                @Override
                public void onFailure(AutelError autelError) {

                }
            });

            battery = (EvoBattery) product.getBattery();
            battery.getLowBatteryNotifyThreshold(new CallbackWithOneParam<Float>() {
                @Override
                public void onSuccess(Float aFloat) {
                    lowBatteryPercent = aFloat;
                }

                @Override
                public void onFailure(AutelError autelError) {

                }
            });
            battery.setBatteryStateListener(new CallbackWithOneParam<EvoBatteryInfo>() {
                @Override
                public void onSuccess(EvoBatteryInfo batteryState) {
                    AutelLog.d(" batteryState "+batteryState.getRemainingPercent());
                    isBatteryOk = batteryState.getRemainingPercent() > lowBatteryPercent;
                }

                @Override
                public void onFailure(AutelError autelError) {

                }
            });

            mEvoFlyController = (Evo2FlyController) product.getFlyController();
            mEvoFlyController.setFlyControllerInfoListener(new CallbackWithOneParam<EvoFlyControllerInfo>() {
                @Override
                public void onSuccess(EvoFlyControllerInfo evoFlyControllerInfo) {
                    isCompassOk = evoFlyControllerInfo.getFlyControllerStatus().isCompassValid();
                    isCanTakeOff = evoFlyControllerInfo.getFlyControllerStatus().isTakeOffValid();

                    isImuOk = evoFlyControllerInfo.getFlyControllerStatus().getArmErrorCode() != ARMWarning.IMU_LOSS
                            && evoFlyControllerInfo.getFlyControllerStatus().getArmErrorCode() != ARMWarning.DISARM_IMU_LOSS;

                    isGpsOk = evoFlyControllerInfo.getFlyControllerStatus().isGpsValid();
                }

                @Override
                public void onFailure(AutelError autelError) {

                }
            });

            remoteController = product.getRemoteController();
            remoteController.setInfoDataListener(new CallbackWithOneParam<RemoteControllerInfo>() {
                @Override
                public void onSuccess(RemoteControllerInfo remoteControllerInfo) {
                    isImageTransOk = remoteControllerInfo.getDSPPercentage() >= 30;
                }

                @Override
                public void onFailure(AutelError autelError) {

                }
            });
        }
        AutelLog.d("init missionManager" + missionManager);
        initView();
        initData();
        cameraPatternModeList = (Spinner) findViewById(R.id.cameraPatternList);
        cameraPatternModeList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        cameraPattern = CameraPattern.FREE_FLIGHT;
                        break;
                    case 1:
                        cameraPattern = CameraPattern.MISSION_FLIGHT;
                        break;
                    case 2:
                        cameraPattern = CameraPattern.DELAYED_PHOTOGRAPHY;
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initView() {
        findViewById(R.id.prepare).setOnClickListener(this);
        findViewById(R.id.start).setOnClickListener(this);
        findViewById(R.id.pause).setOnClickListener(this);
        findViewById(R.id.resume).setOnClickListener(this);
        findViewById(R.id.cancel).setOnClickListener(this);
        findViewById(R.id.download).setOnClickListener(this);
        findViewById(R.id.cameraPattern).setOnClickListener(this);

    }

    private void initData() {
        autelMission = new Evo2WaypointMission();

        autelMission.missionId = id++; //??????id
        autelMission.missionType = MissionType.Waypoint; //????????????(Waypoint(??????)???RECTANGLE(??????)???POLYGON(?????????))
        autelMission.totalFlyTime = 351; //???????????????(??????s)
        autelMission.totalDistance = 897; //???????????????(??????m)
        autelMission.VerticalFOV = 53.6f; //??????????????????????????????
        autelMission.HorizontalFOV = 68.0f; //??????????????????????????????
        autelMission.PhotoIntervalMin = 1020;
        autelMission.altitudeType = 1;//0-???????????????1-????????????
        autelMission.MissionName = "Mission_1";
        autelMission.GUID = UUID.randomUUID().toString().replace("-", "");

        autelMission.missionAction = 1;//1-????????????????????????????????????????????????????????????????????????,0-????????????
        List<Evo2Waypoint> wpList = new ArrayList<>();

        //??????1?????????????????????
        Evo2Waypoint cruiserWaypoint1 = new Evo2Waypoint(new AutelCoordinate3D(22.5966492303896, 113.99885752564695, 60)); //??????????????????????????????
        cruiserWaypoint1.wSpeed = 5; //???????????????m/s???
        cruiserWaypoint1.poiIndex = -1; //??????????????????id
        cruiserWaypoint1.flyTime = 0; //?????????????????????s???
        cruiserWaypoint1.hoverTime = 0; //?????????0???????????????????????????????????????s???
        cruiserWaypoint1.flyDistance = 0;//?????????????????????m???
        cruiserWaypoint1.headingMode = WaypointHeadingMode.CUSTOM_DIRECTION; //???????????????
        cruiserWaypoint1.waypointType = WaypointType.STANDARD; //???????????? ?????????STANDARD??????????????????HOVER???
        //??????1?????????????????????0??????1???????????????
        List<WaypointAction> list1 = new ArrayList<>();
        //??????????????????
        WaypointAction action1 = new WaypointAction();
        action1.actionType = MissionActionType.START_RECORD; //????????????
        action1.parameters = new int[]{45, 50, 0, 0, 0, 0, 0, 20, 0, 0}; //??????????????????(??????1?????????pitch?????? ??????2????????????????????? ?????????????????????????????????????????????)
        list1.add(action1);
        cruiserWaypoint1.actions = list1;
        wpList.add(cruiserWaypoint1);

        //??????2?????????????????????
        Evo2Waypoint cruiserWaypoint2 = new Evo2Waypoint(new AutelCoordinate3D(22.59628621670881, 113.99741950976092, 60));
        cruiserWaypoint2.wSpeed = 5;
//        cruiserWaypoint2.poiIndex = 1; //??????????????????id?????????????????????????????????
        cruiserWaypoint2.poiIndex = -1;
        cruiserWaypoint2.flyTime = 40;
        cruiserWaypoint2.hoverTime = 110;
        cruiserWaypoint2.flyDistance = 153;
        cruiserWaypoint2.headingMode = WaypointHeadingMode.CUSTOM_DIRECTION;
        cruiserWaypoint2.waypointType = WaypointType.HOVER;
        //??????2????????????????????????0????????????????????????
        List<WaypointAction> list2 = new ArrayList<>();
        //??????????????????1 ????????? (??????????????????2s?????????????????????40s???
        WaypointAction point2Action1 = new WaypointAction();
        point2Action1.actionType = MissionActionType.START_TIME_LAPSE_SHOOT;
        point2Action1.parameters = new int[]{-45, 90, 2, 40, 0, 0, 0, 20, 0, 0};
        list2.add(point2Action1);
        wpList.add(cruiserWaypoint2);
        //??????????????????2 ???????????? (???????????????60s)
        WaypointAction point2Action2 = new WaypointAction();
        point2Action2.actionType = MissionActionType.START_RECORD;
        point2Action2.parameters = new int[]{-23, 90, 0, 0, 0, 60, 0, 20, 0, 0};
        list2.add(point2Action2);

        cruiserWaypoint2.actions = list2;
//        wpList.add(cruiserWaypoint2);

        //??????3?????????????????????
        Evo2Waypoint cruiserWaypoint3 = new Evo2Waypoint(new AutelCoordinate3D(22.59563928164338, 113.99866562877735, 60));
        cruiserWaypoint3.wSpeed = 5;
        cruiserWaypoint3.poiIndex = -1;
        cruiserWaypoint3.flyTime = 190;
        cruiserWaypoint3.hoverTime = 0;
        cruiserWaypoint3.flyDistance = 300;
        cruiserWaypoint3.headingMode = WaypointHeadingMode.CUSTOM_DIRECTION;
        cruiserWaypoint3.waypointType = WaypointType.STANDARD;
        List<WaypointAction> list3 = new ArrayList<>();
        WaypointAction point3Action1 = new WaypointAction();
        point3Action1.actionType = MissionActionType.TAKE_PHOTO; //??????
        point3Action1.parameters = new int[]{0, 90, 0, 0, 0, 0, 0, 20, 0, 0};
        list3.add(point3Action1);
        cruiserWaypoint3.actions = list3;
        wpList.add(cruiserWaypoint3);

        //??????4?????????????????????
        Evo2Waypoint cruiserWaypoint4 = new Evo2Waypoint(new AutelCoordinate3D(22.595273074299133, 113.9969537182374, 60));
        cruiserWaypoint4.wSpeed = 5;
        cruiserWaypoint4.poiIndex = -1;
        cruiserWaypoint4.flyTime = 234;
        cruiserWaypoint4.hoverTime = 0;
        cruiserWaypoint4.flyDistance = 481;
        cruiserWaypoint4.headingMode = WaypointHeadingMode.CUSTOM_DIRECTION;
        cruiserWaypoint4.waypointType = WaypointType.STANDARD;
        List<WaypointAction> list4 = new ArrayList<>();
        WaypointAction point4Action1 = new WaypointAction();
        point4Action1.actionType = MissionActionType.START_TIME_LAPSE_SHOOT; //????????????(2s??????)
        point4Action1.parameters = new int[]{0, 90, 2, 0, 0, 0, 0, 20, 0, 0};
        list4.add(point4Action1);
        cruiserWaypoint4.actions = list4;
//        wpList.add(cruiserWaypoint4);

        //??????5?????????????????????
        Evo2Waypoint cruiserWaypoint5 = new Evo2Waypoint(new AutelCoordinate3D(22.595157667753398, 113.99928502161195, 60));
        cruiserWaypoint5.wSpeed = 5;
        cruiserWaypoint5.poiIndex = -1;
        cruiserWaypoint5.flyTime = 295;
        cruiserWaypoint5.hoverTime = 0;
        cruiserWaypoint5.flyDistance = 722;
        cruiserWaypoint5.headingMode = WaypointHeadingMode.CUSTOM_DIRECTION;
        cruiserWaypoint5.waypointType = WaypointType.STANDARD;
        List<WaypointAction> list5 = new ArrayList<>();
        WaypointAction point5Action1 = new WaypointAction();
        point5Action1.actionType = MissionActionType.START_DISTANCE_SHOOT; //????????????(10m??????)
        point5Action1.parameters = new int[]{0, 90, 0, 0, 10, 0, 0, 20, 0, 0};
        list5.add(point5Action1);
        cruiserWaypoint5.actions = list5;
//        wpList.add(cruiserWaypoint5);

        //??????6?????????????????????
        Evo2Waypoint cruiserWaypoint6 = new Evo2Waypoint(new AutelCoordinate3D(22.59583649616868, 22.59583649616868, 60));
        cruiserWaypoint6.wSpeed = 5;
        cruiserWaypoint6.poiIndex = -1;
        cruiserWaypoint6.flyTime = 326;
        cruiserWaypoint6.hoverTime = 0;
        cruiserWaypoint6.flyDistance = 825;
        cruiserWaypoint6.headingMode = WaypointHeadingMode.CUSTOM_DIRECTION;
        cruiserWaypoint6.waypointType = WaypointType.STANDARD;
        List<WaypointAction> list6 = new ArrayList<>();
        WaypointAction point6Action1 = new WaypointAction();
        point6Action1.actionType = MissionActionType.START_RECORD; //????????????
        point6Action1.parameters = new int[]{0, 90, 0, 0, 0, 0, 0, 20, 0, 0};
        list6.add(point6Action1);
        cruiserWaypoint6.actions = list6;
//        wpList.add(cruiserWaypoint6);

        //??????7?????????????????????
        Evo2Waypoint cruiserWaypoint7 = new Evo2Waypoint(new AutelCoordinate3D(22.59583649616868, 22.59583649616868, 60));
        cruiserWaypoint7.wSpeed = 5;
        cruiserWaypoint7.poiIndex = -1;
        cruiserWaypoint7.flyTime = 351;
        cruiserWaypoint7.hoverTime = 0;
        cruiserWaypoint7.flyDistance = 897;
        cruiserWaypoint7.headingMode = WaypointHeadingMode.CUSTOM_DIRECTION;
        cruiserWaypoint7.waypointType = WaypointType.STANDARD;
        List<WaypointAction> list7 = new ArrayList<>();
        WaypointAction point7Action1 = new WaypointAction();
        point7Action1.actionType = MissionActionType.STOP_RECORD; //????????????
        point7Action1.parameters = new int[]{30, 90, 0, 0, 0, 0, 0, 20, 0, 0};
        list7.add(point7Action1);
        cruiserWaypoint7.actions = list7;
//        wpList.add(cruiserWaypoint7);


        //???????????????????????????0????????????????????????
        List<Poi> poiList = new ArrayList<>();
        Poi cruiserPoi1 = new Poi();
        cruiserPoi1.id = 0;
        cruiserPoi1.coordinate3D = new AutelCoordinate3D(22.59594093275901, 113.99941807396686, 60);
        poiList.add(cruiserPoi1);

        Poi cruiserPoi2 = new Poi();
        cruiserPoi2.id = 1;
        cruiserPoi2.coordinate3D = new AutelCoordinate3D(22.595821147877796, 113.99901208495906, 60);
        poiList.add(cruiserPoi2);

//        autelMission.wpoiList = poiList;
        autelMission.wpList = wpList;
        autelMission.finishedAction = Evo2WaypointFinishedAction.RETURN_HOME;
    }

    @Override
    public void onClick(View view) {
        final int id = view.getId();
        switch (id) {
            case R.id.prepare: {
                //????????????????????????????????????????????????
                if (!flyCheck()) {
                    return;
                }

                if (flyState != FlyState.None) {
                    Toast.makeText(Evo2WayPointActivity.this, "???????????????????????????", Toast.LENGTH_LONG).show();
                    return;
                }
                if (null != missionManager) {
                    missionManager.prepareMission(autelMission, new CallbackWithOneParamProgress<Boolean>() {
                        @Override
                        public void onProgress(float v) {

                        }

                        @Override
                        public void onSuccess(Boolean aBoolean) {
                            flyState = FlyState.Prepare;
                            AutelLog.d("prepareMission success");
                            Toast.makeText(Evo2WayPointActivity.this, "prepare success", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(AutelError autelError) {
                            AutelLog.d("prepareMission onFailure");
                            Toast.makeText(Evo2WayPointActivity.this, "prepare failed", Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }
            break;

            case R.id.start: {
                if (flyState != FlyState.Prepare) {
                    Toast.makeText(Evo2WayPointActivity.this, "???????????????????????????", Toast.LENGTH_LONG).show();
                    return;
                }
                if (null != missionManager) {
                    missionManager.startMission(new CallbackWithNoParam() {
                        @Override
                        public void onSuccess() {
                            flyState = FlyState.Start;
                            Toast.makeText(Evo2WayPointActivity.this, "start success", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(AutelError autelError) {

                        }
                    });
                }
            }
            break;

            case R.id.pause: {
                if (flyState != FlyState.Start) {
                    Toast.makeText(Evo2WayPointActivity.this, "???????????????????????????", Toast.LENGTH_LONG).show();
                    return;
                }
                if (null != missionManager) {
                    missionManager.pauseMission(new CallbackWithNoParam() {
                        @Override
                        public void onSuccess() {
                            flyState = FlyState.Pause;
                            Toast.makeText(Evo2WayPointActivity.this, "pause success", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(AutelError autelError) {

                        }
                    });
                }
            }
            break;

            case R.id.resume: {
                if (flyState != FlyState.Pause) {
                    Toast.makeText(Evo2WayPointActivity.this, "???????????????????????????", Toast.LENGTH_LONG).show();
                    return;
                }
                if (null != missionManager) {
                    missionManager.resumeMission(new CallbackWithNoParam() {
                        @Override
                        public void onSuccess() {
                            flyState = FlyState.Start;
                            Toast.makeText(Evo2WayPointActivity.this, "continue success", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(AutelError autelError) {

                        }
                    });
                }
            }
            break;

            case R.id.cancel: {
                if (flyState == FlyState.None) {
                    Toast.makeText(Evo2WayPointActivity.this, "???????????????????????????", Toast.LENGTH_LONG).show();
                    return;
                }
                if (null != missionManager) {
                    missionManager.cancelMission(new CallbackWithNoParam() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(Evo2WayPointActivity.this, "cancel success", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(AutelError autelError) {

                        }
                    });
                }
            }
            break;

            case R.id.download: {
                if (flyState == FlyState.None) {
                    Toast.makeText(Evo2WayPointActivity.this, "???????????????????????????", Toast.LENGTH_LONG).show();
                    return;
                }
                if (null != missionManager) {
                    missionManager.downloadMission(new CallbackWithOneParamProgress<AutelMission>() {
                        @Override
                        public void onProgress(float v) {

                        }

                        @Override
                        public void onSuccess(AutelMission autelMission) {
                            Toast.makeText(Evo2WayPointActivity.this, "download success", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(AutelError autelError) {

                        }
                    });
                }
            }
            break;
            case R.id.cameraPattern:
                if(null == autelBaseCamera){
                    Toast.makeText(getApplicationContext(),"???????????????",Toast.LENGTH_SHORT).show();
                    return;

                }
                autelBaseCamera.setCameraPattern(cameraPattern, new CallbackWithNoParam() {
                    @Override
                    public void onSuccess() {
                        ThreadUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"setCameraPattern success",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(AutelError autelError) {
                        ThreadUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"setCameraPattern onFailure",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                break;
        }
    }

    private boolean flyCheck() {
        if (!isBatteryOk) {
            Toast.makeText(Evo2WayPointActivity.this, "????????????????????????", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!isImuOk) {
            Toast.makeText(Evo2WayPointActivity.this, "IMU??????", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!isGpsOk) {
            Toast.makeText(Evo2WayPointActivity.this, "GPS??????", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!isCompassOk) {
            Toast.makeText(Evo2WayPointActivity.this, "???????????????", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!isImageTransOk) {
            Toast.makeText(Evo2WayPointActivity.this, "??????????????????", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!isCanTakeOff) {
            Toast.makeText(Evo2WayPointActivity.this, "?????????????????????", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
