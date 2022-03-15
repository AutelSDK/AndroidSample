package com.autel.sdksample.rtk;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.autel.AutelNet2.dsp.controller.DspRFManager2;
import com.autel.common.CallbackWithNoParam;
import com.autel.common.CallbackWithOneParam;
import com.autel.common.dsp.evo.AircraftRole;
import com.autel.common.error.AutelError;
import com.autel.common.flycontroller.AuthInfo;
import com.autel.common.flycontroller.RTKSignalType;
import com.autel.common.flycontroller.RtkCoordinateSystem;
import com.autel.common.flycontroller.evo.EvoFlyControllerInfo;
import com.autel.common.flycontroller.evo2.RTKInternal;
import com.autel.common.mission.AutelCoordinate3D;
import com.autel.sdksample.R;
import com.autel.sdksample.base.camera.fragment.adapter.RtkCoordinateAdapter;
import com.qxwz.sdk.configs.AccountInfo;
import com.qxwz.sdk.configs.OssConfig;
import com.qxwz.sdk.configs.SDKConfig;
import com.qxwz.sdk.core.CapInfo;
import com.qxwz.sdk.core.Constants;
import com.qxwz.sdk.core.IRtcmSDKCallback;
import com.qxwz.sdk.core.IRtcmSDKSetCoordCallback;
import com.qxwz.sdk.core.RtcmSDKManager;
import com.qxwz.sdk.types.KeyType;

import java.util.ArrayList;
import java.util.List;

import static com.qxwz.sdk.core.Constants.QXWZ_SDK_CAP_ID_NOSR;
import static com.qxwz.sdk.core.Constants.QXWZ_SDK_STAT_AUTH_SUCC;

/**
 * 说明：待飞机连接上后，先从飞机获取千寻账号信息{flyController.getRtkAuthInfo}，
 * 再调用千寻sdk初始化,成功后将回调{public void onData(int type, byte[] bytes)}后的差分数据
 * 通过{flyController.sendRtkData(bytes)}下发给飞行器
 */

public class Evo2RTKActivity extends RtkBaseActivity implements IRtcmSDKCallback {
    private static final String TAG = "Evo2RTKActivity";

    private boolean isStart = false;
    private Switch mSwitch;
    Spinner coordinateSpinner;
    String coordinateSystem = "WGS84";
    private EditText latitudeEditView, longitudeEditView, altitudeEditView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void initView() {

        latitudeEditView = (EditText) findViewById(R.id.lat);
        longitudeEditView = (EditText) findViewById(R.id.lng);
        altitudeEditView = (EditText) findViewById(R.id.alt);
        mSwitch = (Switch) findViewById(R.id.setUseRTKSwitch);

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                if (null != flyController)
                    flyController.setUseRTK(isChecked, new CallbackWithNoParam() {
                        @Override
                        public void onSuccess() {
                            logOut("setUseRTK success " + isChecked);
                        }

                        @Override
                        public void onFailure(AutelError autelError) {
                            logOut("setUseRTK onFailure" + autelError.getDescription());
                        }
                    });
            }
        });
        findViewById(R.id.getAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRtkAuthInfo();
            }
        });
        coordinateSpinner = (Spinner) findViewById(R.id.fcCoordinateSys);
        List<String> list = new ArrayList<>();
        list.add("WGS84");
        list.add("CGCS2000");
        coordinateSpinner.setAdapter(new RtkCoordinateAdapter(this, list));
        coordinateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                coordinateSystem = (String) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setFrequency(View view) {
        DspRFManager2.getInstance().bingAircraftToRemote(AircraftRole.SLAVER);
    }

    public void setBaseStationLocation(View view) {
        if (TextUtils.isEmpty(latitudeEditView.getText()) || TextUtils.isEmpty(longitudeEditView.getText())
                || TextUtils.isEmpty(altitudeEditView.getText())) {
            Toast.makeText(this, "输入的值不能为空", Toast.LENGTH_SHORT);
            return;
        }
        double lat = Double.parseDouble(latitudeEditView.getText().toString());
        double lng = Double.parseDouble(longitudeEditView.getText().toString());
        double alt = Double.parseDouble(altitudeEditView.getText().toString());
        flyController.setBaseStationLocation(new AutelCoordinate3D(lat, lng, alt), new CallbackWithNoParam() {
            @Override
            public void onSuccess() {
                logOut("setBaseStationLocation  onSuccess ");

            }

            @Override
            public void onFailure(AutelError autelError) {
                logOut("setBaseStationLocation  onFailure");

            }
        });
    }

    public void setBaseStationListener(View view) {
        flyController.setFlyControllerInfoListener(new CallbackWithOneParam<EvoFlyControllerInfo>() {
            @Override
            public void onSuccess(EvoFlyControllerInfo evoFlyControllerInfo) {
                logOut(evoFlyControllerInfo.getBaseStationInfo().toString());
            }

            @Override
            public void onFailure(AutelError autelError) {

            }
        });
    }

    public void setBaseStationLocationListener(View view) {
        flyController.setFlyControllerInfoListener(new CallbackWithOneParam<EvoFlyControllerInfo>() {
            @Override
            public void onSuccess(EvoFlyControllerInfo evoFlyControllerInfo) {
                logOut(evoFlyControllerInfo.getBaseStationLocation().toString());
            }

            @Override
            public void onFailure(AutelError autelError) {

            }
        });
    }

    protected void getRtkAuthInfo() {
        if (flyController != null) {
            flyController.getRtkAuthInfo(new CallbackWithOneParam<AuthInfo>() {
                @Override
                public void onSuccess(AuthInfo info) {
                    authInfo = info;
                    logOut("getRtkAuthInfo  " + info.key + " " + info.secret + " " + info.deviceId + " " + info.deviceType);
                    if (null != authInfo) {
                        init();
                    } else {
                        logOut("请使用RTK飞机，并在飞机上先烧录账号后重试");
                    }
                }

                @Override
                public void onFailure(AutelError autelError) {
                    logOut("getRtkAuthInfo onFailure " + autelError);
                }
            });
        }
    }

    private void init() {

        AccountInfo accountInfo = AccountInfo.builder().setKeyType(KeyType.QXWZ_SDK_KEY_TYPE_DSK).setKey(authInfo.key)
                .setSecret(authInfo.secret).setDeviceId(authInfo.deviceId).setDeviceType(authInfo.deviceType).build();

        OssConfig ossConfig = OssConfig.builder().setHeartbeatInterval(30).setRetryInterval(20).build();

        SDKConfig sdkConfig = SDKConfig.builder()
                .setAccountInfo(accountInfo).setRtcmSDKCallback(this)/*.setServerInfo(serverInfo)*/.setOssConfig(ossConfig)
                .build();
        int itCode = RtcmSDKManager.getInstance().init(sdkConfig);
        int authCode = RtcmSDKManager.getInstance().auth();

        Log.d(TAG, "itCode is " + itCode + " authCode is " + authCode);

    }


    @Override
    protected int getCustomViewResId() {
        return R.layout.activity_rtk;
    }

    @Override
    protected void initUi() {
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        isStart = false;
        RtcmSDKManager.getInstance().stop(QXWZ_SDK_CAP_ID_NOSR);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RtcmSDKManager.getInstance().cleanup();
        if (null != flyController)
            flyController.setRtkInfoListener(null);
    }

    @Override
    public void onData(int type, byte[] bytes) {
        logOut("接收到千寻差分数据 " + bytes.length);
//        Log.d(TAG, "rtcm data received, data length is " + bytes.length);
        if (null != flyController) {
            flyController.sendRtkData(bytes);
        }
    }

    @Override
    public void onStatus(int status) {
        Log.d(TAG, "status changed to " + status);
    }

    @Override
    public void onAuth(int code, List<CapInfo> caps) {
        if (code == QXWZ_SDK_STAT_AUTH_SUCC) {
            Log.d(TAG, "auth successfully.");
            for (CapInfo capInfo : caps) {
                Log.d(TAG, "capInfo:" + capInfo.toString());
            }
            /* if you want to call the start api in the callback function, you must invoke it in a new thread. */
            new Thread() {
                public void run() {
                    RtcmSDKManager.getInstance().start(QXWZ_SDK_CAP_ID_NOSR);
                }
            }.start();
        } else {
            Log.d(TAG, "failed to auth, code is " + code);
        }
    }

    @Override
    public void onStart(int code, int capId) {
        if (code == Constants.QXWZ_SDK_STAT_CAP_START_SUCC) {
            Log.d(TAG, "start successfully.");
            isStart = true;
            new Thread() {
                public void run() {
                    while (isStart) {
                        RtcmSDKManager.getInstance().sendGga(GGA);
                        SystemClock.sleep(1000);
                    }
                }
            }.start();
        } else {
            Log.d(TAG, "failed to start, code is " + code);
        }
    }


    public void setRtkInfoListener(View view) {
        if (null != flyController)
            flyController.setRtkInfoListener(new CallbackWithOneParam<RTKInternal>() {
                @Override
                public void onSuccess(RTKInternal rtkInternal) {
                    logOut(rtkInternal.altitude + " " + rtkInternal.altSigma + " " + rtkInternal.fixSat);
                }

                @Override
                public void onFailure(AutelError autelError) {

                }
            });
    }

    public void getRtkRecvType(View view) {
        if (null != flyController)
            flyController.getRtkRecvType(new CallbackWithOneParam<RTKSignalType>() {
                @Override
                public void onSuccess(RTKSignalType rtkSignalType) {
                    logOut("rtkSignalType " + rtkSignalType);
                }

                @Override
                public void onFailure(AutelError autelError) {

                }
            });
    }

    public void getRtkCoordinateSys(View view) {
        if (null != flyController)
            flyController.getRtkCoordinateSys(new CallbackWithOneParam<RtkCoordinateSystem>() {
                @Override
                public void onSuccess(RtkCoordinateSystem rtkCoordinateSystem) {
                    logOut("getRtkCoordinateSys " + rtkCoordinateSystem);
                }

                @Override
                public void onFailure(AutelError autelError) {
                    logOut("getRtkCoordinateSys onFailure" + autelError.getDescription());
                }
            });
    }

    public void getUseRTK(View view) {
        if (null != flyController)
            flyController.getUseRTK(new CallbackWithOneParam<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                    logOut("getUseRTK " + aBoolean);
                }

                @Override
                public void onFailure(AutelError autelError) {
                    logOut("getUseRTK onFailure" + autelError.getDescription());
                }
            });
    }

    public void setRtkCoordinateSys(View view) {

        int coordSysIndex = RtkCoordinateSystem.WGS84.getValue();
        if (coordinateSystem.equals("CGCS2000")) {
            coordSysIndex = 3;
        } else if (coordinateSystem.equals("WGS84")) {
            coordSysIndex = 2;
        }
        RtcmSDKManager.getInstance().setCoordSys(coordSysIndex, new IRtcmSDKSetCoordCallback() {

            @Override
            public void onResult(int i) {
                logOut("setRtkCoordinateSys " + i);
            }
        });
    }
}
