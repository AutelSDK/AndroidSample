package com.autel.sdksample.base;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.autel.common.CallbackWithNoParam;
import com.autel.common.CallbackWithOneParam;
import com.autel.common.RangePair;
import com.autel.common.error.AutelError;
import com.autel.common.remotecontroller.RFPower;
import com.autel.common.remotecontroller.RemoteControllerCommandStickMode;
import com.autel.common.remotecontroller.RemoteControllerConnectState;
import com.autel.common.remotecontroller.RemoteControllerInfo;
import com.autel.common.remotecontroller.RemoteControllerLanguage;
import com.autel.common.remotecontroller.RemoteControllerNavigateButtonEvent;
import com.autel.common.remotecontroller.RemoteControllerParameterRangeManager;
import com.autel.common.remotecontroller.RemoteControllerParameterUnit;
import com.autel.common.remotecontroller.RemoteControllerStickCalibration;
import com.autel.common.remotecontroller.RemoteControllerVersionInfo;
import com.autel.common.remotecontroller.TeachingMode;
import com.autel.sdk.remotecontroller.AutelRemoteController;
import com.autel.sdksample.R;


public abstract class RemoteControllerActivity extends BaseActivity<AutelRemoteController> {
    final static String TAG = RemoteControllerActivity.class.getSimpleName();
    protected TextView log_output;
    protected Spinner languageSpinner;
    protected Spinner rfSpinner;
    protected Spinner teachingModeSpinner;
    protected Spinner lengthUnitSpinner;
    protected Spinner commandStickModeSpinner;
    protected EditText yawCoefficientValue;
    EditText dialAdjustSpeed;
    protected RemoteControllerLanguage remoteControllerLanguage = RemoteControllerLanguage.ENGLISH;
    protected RFPower rfPower = RFPower.FCC;
    protected TeachingMode teachingMode = TeachingMode.STUDENT;
    protected RemoteControllerParameterUnit parameterUnit = RemoteControllerParameterUnit.IMPERIAL;
    protected RemoteControllerCommandStickMode commandStickMode = RemoteControllerCommandStickMode.CHINA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("RemoteController");
    }

    @Override
    protected void initUi() {
        languageSpinner = (Spinner) findViewById(R.id.languageSpinner);
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                remoteControllerLanguage = 0 == position ? RemoteControllerLanguage.ENGLISH : RemoteControllerLanguage.CHINESE;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rfSpinner = (Spinner) findViewById(R.id.rfSpinner);
        rfSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rfPower = 0 == position ? RFPower.FCC : RFPower.CE;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        teachingModeSpinner = (Spinner) findViewById(R.id.teachingModeSpinner);
        teachingModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        teachingMode = TeachingMode.DISABLED;
                        break;
                    case 1:
                        teachingMode = TeachingMode.TEACHER;
                        break;
                    case 2:
                        teachingMode = TeachingMode.STUDENT;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lengthUnitSpinner = (Spinner) findViewById(R.id.lengthUnitSpinner);
        lengthUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        parameterUnit = RemoteControllerParameterUnit.METRIC;
                        break;
                    case 1:
                        parameterUnit = RemoteControllerParameterUnit.IMPERIAL;
                        break;
                    case 2:
                        parameterUnit = RemoteControllerParameterUnit.METRIC_KM_H;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        commandStickModeSpinner = (Spinner) findViewById(R.id.commandStickModeSpinner);
        commandStickModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        commandStickMode = RemoteControllerCommandStickMode.USA;
                        break;
                    case 1:
                        commandStickMode = RemoteControllerCommandStickMode.CHINA;
                        break;
                    case 2:
                        commandStickMode = RemoteControllerCommandStickMode.JAPAN;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final TextView yawCoefficientRange = (TextView) findViewById(R.id.yawCoefficientRange);
        yawCoefficientValue = (EditText) findViewById(R.id.yawCoefficientValue);
        yawCoefficientValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String value = yawCoefficientRange.getText().toString();
                if (isEmpty(value)) {
                    RemoteControllerParameterRangeManager parameterRangeManager = mController.getParameterRangeManager();
                    RangePair<Float> support = parameterRangeManager.getYawCoefficient();
                    yawCoefficientRange.setText("yawCoefficient from " + support.getValueFrom() + "  to  " + support.getValueTo());
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dialAdjustSpeed = (EditText) findViewById(R.id.dialAdjustSpeed);
        final TextView dialAdjustSpeedRange = (TextView) findViewById(R.id.dialAdjustSpeedRange);
        dialAdjustSpeed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (isEmpty(s.toString())) {
                    return;
                }

                if (isEmpty(dialAdjustSpeedRange.getText().toString())) {
                    RemoteControllerParameterRangeManager rangeManager = mController.getParameterRangeManager();
                    RangePair<Integer> support = rangeManager.getDialAdjustSpeed();
                    dialAdjustSpeedRange.setText("dial adjust speed from " + support.getValueFrom() + " to " + support.getValueTo());
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        findViewById(R.id.setGimbalDialAdjustSpeed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = dialAdjustSpeed.getText().toString();
                mController.setGimbalDialAdjustSpeed(isEmpty(value) ? 10 : Integer.valueOf(value), new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError rcError) {
                        logOut("setGimbalAngleWithSpeed error " + rcError.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("setGimbalAngleWithSpeed onSuccess ");
                    }
                });
            }
        });
//        findViewById(R.id.getGimbalDialAdjustSpeed).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mController.getGimbalDialAdjustSpeed(new CallbackWithOneParam<Integer>() {
//
//                    @Override
//                    public void onFailure(AutelError rcError) {
//                        logOut("getGimbalDialAdjustSpeed error " + rcError.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(Integer speed) {
//                        logOut("getGimbalDialAdjustSpeed onSuccess " + speed);
//                    }
//                });
//            }
//        });
    }


    public void setRCLanguage(View view) {
        mController.setLanguage(remoteControllerLanguage, new CallbackWithNoParam() {

            @Override
            public void onFailure(AutelError rcError) {
                logOut("setLanguage RCError " + rcError.getDescription());
            }

            @Override
            public void onSuccess() {
                logOut("setLanguage onSuccess ");
            }
        });
    }

    public void getRCLanguage(View view) {
        mController.getLanguage(new CallbackWithOneParam<RemoteControllerLanguage>() {
            @Override
            public void onFailure(AutelError rcError) {
                logOut("getLanguage RCError " + rcError.getDescription());
            }

            @Override
            public void onSuccess(RemoteControllerLanguage rcLanguage) {
                logOut("getLanguage onSuccess " + rcLanguage.toString());
            }
        });
    }

    public void enterBinding(View view) {
        mController.enterPairing(new CallbackWithNoParam() {
            @Override
            public void onFailure(AutelError rcError) {
                logOut("enterPairing RCError " + rcError.getDescription());
            }

            @Override
            public void onSuccess() {
                logOut("enterPairing onSuccess ");
            }
        });
    }

    public void exitBinding(View view) {
        mController.exitPairing();
    }

    public void setRFPower(View view) {
        mController.setRFPower(rfPower, new CallbackWithNoParam() {
            @Override
            public void onFailure(AutelError rcError) {
                logOut("setRFPower RCError " + rcError.getDescription());
            }

            @Override
            public void onSuccess() {
                logOut("setRFPower onSuccess ");
            }
        });
    }

    public void getRFPower(View view) {
        mController.getRFPower(new CallbackWithOneParam<RFPower>() {
            @Override
            public void onFailure(AutelError rcError) {
                logOut("getRFPower RCError " + rcError.getDescription());
            }

            @Override
            public void onSuccess(RFPower autelRFPower) {
                logOut("getRFPower onSuccess " + autelRFPower);
            }
        });
    }

    public void getTeacherStudentMode(View view) {
        mController.getTeachingMode(new CallbackWithOneParam<TeachingMode>() {
            @Override
            public void onFailure(AutelError rcError) {
                logOut("getTeachingMode RCError " + rcError.getDescription());
            }

            @Override
            public void onSuccess(TeachingMode autelTeachingMode) {
                logOut("getTeachingMode onSuccess " + autelTeachingMode);
            }
        });
    }

    public void setTeacherStudentMode(View view) {
        mController.setTeachingMode(teachingMode, new CallbackWithNoParam() {
            @Override
            public void onFailure(AutelError rcError) {
                logOut("setTeachingMode RCError " + rcError.getDescription());
            }

            @Override
            public void onSuccess() {
                logOut("setTeachingMode onSuccess ");
            }
        });
    }

    public void startCalibration(View view) {
        mController.setStickCalibration(RemoteControllerStickCalibration.START, new CallbackWithNoParam() {
            @Override
            public void onFailure(AutelError rcError) {
                logOut("setStickCalibration START RCError " + rcError.getDescription());
            }

            @Override
            public void onSuccess() {
                logOut("setStickCalibration START onSuccess  ");
            }
        });
    }

    public void saveCalibration(View view) {
        mController.setStickCalibration(RemoteControllerStickCalibration.COMPLETE, new CallbackWithNoParam() {
            @Override
            public void onFailure(AutelError rcError) {
                logOut("setStickCalibration COMPLETE RCError " + rcError.getDescription());
            }

            @Override
            public void onSuccess() {
                logOut("setStickCalibration COMPLETE onSuccess  ");
            }
        });
    }

//    public void exitCalibration(View view) {
//        mController.setStickCalibration(RemoteControllerStickCalibration.EXIT, new CallbackWithNoParam() {
//            @Override
//            public void onFailure(AutelError rcError) {
//                logOut("setStickCalibration EXIT RCError " + rcError.getDescription());
//            }
//
//            @Override
//            public void onSuccess() {
//                logOut("setStickCalibration EXIT onSuccess  ");
//            }
//        });
//    }

    public void getRCLengthUnit(View view) {
        mController.getLengthUnit(new CallbackWithOneParam<RemoteControllerParameterUnit>() {
            @Override
            public void onFailure(AutelError rcError) {
                logOut("getLengthUnit RCError " + rcError.getDescription());
            }

            @Override
            public void onSuccess(RemoteControllerParameterUnit autelRCLengthUnit) {
                logOut("getLengthUnit onSuccess " + autelRCLengthUnit);
            }
        });
    }

    public void setRCLengthUnit(View view) {
        mController.setParameterUnit(parameterUnit, new CallbackWithNoParam() {
            @Override
            public void onFailure(AutelError rcError) {
                logOut("setParameterUnit RCError " + rcError.getDescription());
            }

            @Override
            public void onSuccess() {
                logOut("setParameterUnit onSuccess  ");
            }

        });
    }

    public void setRCCommandStickMode(View view) {
        mController.setCommandStickMode(commandStickMode, new CallbackWithNoParam() {
            @Override
            public void onFailure(AutelError rcError) {
                logOut("setCommandStickMode RCError " + rcError.getDescription());
            }

            @Override
            public void onSuccess() {
                logOut("setCommandStickMode onSuccess ");
            }
        });
    }

    public void getRCCommandStickMode(View view) {
        mController.getCommandStickMode(new CallbackWithOneParam<RemoteControllerCommandStickMode>() {
            @Override
            public void onFailure(AutelError rcError) {
                logOut("getCommandStickMode RCError " + rcError.getDescription());
            }

            @Override
            public void onSuccess(RemoteControllerCommandStickMode mode) {
                logOut("getCommandStickMode onSuccess " + mode);
            }
        });
    }

    public void setYawCoefficient(View view) {
        String value = yawCoefficientValue.getText().toString();
        mController.setYawCoefficient(isEmpty(value) ? 0.3f : Float.valueOf(value), new CallbackWithNoParam() {
            @Override
            public void onSuccess() {
                logOut("setYawCoefficient onSuccess ");
            }

            @Override
            public void onFailure(AutelError autelError) {
                logOut("setYawCoefficient RCError " + autelError.getDescription());
            }
        });
    }

    public void getYawCoefficient(View view) {
        mController.getYawCoefficient(new CallbackWithOneParam<Float>() {
            @Override
            public void onSuccess(Float aDouble) {
                logOut("getYawCoefficient onSuccess " + aDouble);
            }

            @Override
            public void onFailure(AutelError autelError) {
                logOut("getYawCoefficient RCError " + autelError.getDescription());
            }
        });
    }

    public void getVersionInfo(View view) {
        mController.getVersionInfo(new CallbackWithOneParam<RemoteControllerVersionInfo>() {
            @Override
            public void onSuccess(RemoteControllerVersionInfo versionInfo) {
                logOut("getVersionInfo onSuccess {" + versionInfo.getRemoteControlVersion() + "}");
            }

            @Override
            public void onFailure(AutelError autelError) {
                logOut("getVersionInfo onFailure : " + autelError.getDescription());
            }
        });
    }

    public void getSerialNumber(View view) {
        mController.getSerialNumber(new CallbackWithOneParam<String>() {
            @Override
            public void onSuccess(String serialNumber) {
                logOut("getSerialNumber onSuccess " + serialNumber);
            }

            @Override
            public void onFailure(AutelError autelError) {
                logOut("getSerialNumber onFailure : " + autelError.getDescription());
            }
        });
    }


    public void setRemoteButtonControllerMonitor(View view) {
        mController.setRemoteButtonControllerListener(new CallbackWithOneParam<RemoteControllerNavigateButtonEvent>() {
            @Override
            public void onFailure(AutelError rcError) {
                logOut("setRemoteButtonControllerListener rcError " + rcError.getDescription());
            }

            @Override
            public void onSuccess(RemoteControllerNavigateButtonEvent rcControlBtnEvent) {
                logOut("setRemoteButtonControllerListener onSuccess " + rcControlBtnEvent);
            }
        });
    }

    public void resetRemoteButtonControllerMonitor(View view) {
        mController.setRemoteButtonControllerListener(null);
    }

    public void setRCInfoDataMonitor(View view) {
        mController.setInfoDataListener(new CallbackWithOneParam<RemoteControllerInfo>() {
            @Override
            public void onFailure(AutelError rcError) {
                logOut("setInfoDataListener rcError " + rcError.getDescription());
            }

            @Override
            public void onSuccess(RemoteControllerInfo data) {
                logOut("setInfoDataListener onSuccess " + data);
            }
        });
    }

    public void resetRCInfoDataMonitor(View view) {
        mController.setInfoDataListener(null);
    }

    public void setRemoteControllerConnectStateListener(View view) {
        mController.setConnectStateListener(new CallbackWithOneParam<RemoteControllerConnectState>() {
            @Override
            public void onFailure(AutelError error) {
                logOut("setConnectStateListener error " + error.getDescription());
            }

            public void onSuccess(RemoteControllerConnectState state) {
                logOut("setConnectStateListener " + state);
            }
        });
    }

    public void resetRemoteControllerConnectStateListener(View view) {
        mController.setConnectStateListener(null);
    }

    public void setControlMenuListener(View view) {
        mController.setControlMenuListener(new CallbackWithOneParam<int[]>() {
            @Override
            public void onSuccess(int[] data) {
                logOut("setControlMenuListener onSuccess " + data[0]+" "+data[1]+" "+ data[2]+" "+data[3]+ " "+ data[4]+" "+data[5]);
            }

            @Override
            public void onFailure(AutelError error) {
                logOut("setControlMenuListener onFailure ");
            }
        });
    }

    public void resetControlMenuListener(View view) {
        mController.setControlMenuListener(null);
    }
}
