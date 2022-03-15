package com.autel.sdksample.base;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.autel.common.CallbackWithNoParam;
import com.autel.common.CallbackWithOneParam;
import com.autel.common.CallbackWithTwoParams;
import com.autel.common.RangePair;
import com.autel.common.error.AutelError;
import com.autel.common.flycontroller.ARMWarning;
import com.autel.common.flycontroller.CalibrateCompassStatus;
import com.autel.common.flycontroller.FlyControllerParameterRangeManager;
import com.autel.common.flycontroller.FlyControllerVersionInfo;
import com.autel.common.flycontroller.LedPilotLamp;
import com.autel.common.flycontroller.MagnetometerState;
import com.autel.sdk.flycontroller.AutelFlyController;
import com.autel.sdksample.R;


public abstract class FlyControllerActivity extends BaseActivity<AutelFlyController> {
    protected final String TAG = getClass().getSimpleName();
    protected Switch beginnerSwitch;

    protected EditText fcMaxHeight;
    protected EditText fcMaxRange;
    protected EditText fcReturnHeight;
    protected EditText fcHorizontalSpeed;
    protected EditText locationAsHomePoint_la;
    protected EditText locationAsHomePoint_lon;
    protected Switch attiModeSwitch;
    protected Spinner fcLedPilotLamp;

    protected LedPilotLamp ledPilotLamp = LedPilotLamp.ALL_OFF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("FlyController");
    }

    @Override
    protected void initUi() {
        beginnerSwitch = (Switch) findViewById(R.id.beginnerSwitch);
        beginnerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setBeginnerModeState(isChecked);
            }
        });

        fcMaxHeight = (EditText) findViewById(R.id.fcMaxHeight);
        fcMaxRange = (EditText) findViewById(R.id.fcMaxRange);
        fcReturnHeight = (EditText) findViewById(R.id.fcReturnHeight);
        fcHorizontalSpeed = (EditText) findViewById(R.id.fcHorizontalSpeed);
        locationAsHomePoint_la = (EditText) findViewById(R.id.locationAsHomePoint_la);
        locationAsHomePoint_lon = (EditText) findViewById(R.id.locationAsHomePoint_lon);

        attiModeSwitch = (Switch) findViewById(R.id.attiModeSwitch);
        attiModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setAttiModeEnable(isChecked);
            }
        });
        fcLedPilotLamp = (Spinner) findViewById(R.id.fcLedPilotLamp);
        fcLedPilotLamp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ledPilotLamp = LedPilotLamp.ALL_OFF;
                        break;
                    case 1:
                        ledPilotLamp = LedPilotLamp.BACK_ONLY;
                        break;
                    case 2:
                        ledPilotLamp = LedPilotLamp.FRONT_ONLY;
                        break;
                    case 3:
                        ledPilotLamp = LedPilotLamp.ALL_ON;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final TextView returnHeightRangeNotify = (TextView) findViewById(R.id.returnHeightRangeNotify);
        fcReturnHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) {
                    return;
                }

                String value = returnHeightRangeNotify.getText().toString();
                if (isEmpty(value)) {
                    FlyControllerParameterRangeManager parameterRangeManager = mController.getParameterRangeManager();
                    if (null != parameterRangeManager) {
                        RangePair<Float> support = parameterRangeManager.getReturnHeightRange();
                        returnHeightRangeNotify.setText("return height range from " + support.getValueFrom() + "  to  " + support.getValueTo());
                    }
                }
            }
        });

        final TextView maxHeightRange = (TextView) findViewById(R.id.maxHeightRange);
        fcMaxHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) {
                    return;
                }

                String value = maxHeightRange.getText().toString();
                if (isEmpty(value)) {
                    FlyControllerParameterRangeManager parameterRangeManager = mController.getParameterRangeManager();
                    if (null != parameterRangeManager) {
                        RangePair<Float> support = parameterRangeManager.getHeightRange();
                        maxHeightRange.setText("max height range from " + support.getValueFrom() + "  to  " + support.getValueTo());
                    }
                }
            }
        });

        final TextView maxRangeRange = (TextView) findViewById(R.id.maxRangeRange);
        fcMaxRange.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) {
                    return;
                }

                String value = maxRangeRange.getText().toString();
                if (isEmpty(value)) {
                    FlyControllerParameterRangeManager parameterRangeManager = mController.getParameterRangeManager();
                    if (null != parameterRangeManager) {
                        RangePair<Float> support = parameterRangeManager.getRangeOfMaxRange();
                        maxRangeRange.setText("max Range range from " + support.getValueFrom() + "  to  " + support.getValueTo());
                    }
                }
            }
        });


        final TextView fcHorizontalSpeedValue = (TextView) findViewById(R.id.fcHorizontalSpeedValue);
        fcHorizontalSpeed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) {
                    return;
                }

                String value = fcHorizontalSpeedValue.getText().toString();
                if (isEmpty(value)) {
                    FlyControllerParameterRangeManager parameterRangeManager = mController.getParameterRangeManager();
                    if (null != parameterRangeManager) {
                        RangePair<Float> support = parameterRangeManager.getHorizontalSpeedRange();
                        fcHorizontalSpeedValue.setText("AscendSpeed Range range from " + support.getValueFrom() + "m/s  to  " + support.getValueTo() + "m/s");
                    }
                }
            }
        });
    }

    public void setBeginnerModeState(boolean enable) {
        mController.setBeginnerModeEnable(enable, new CallbackWithNoParam() {
            @Override
            public void onFailure(AutelError error) {
                logOut("setBeginnerModeEnable AutelError " + error.getDescription());
            }

            @Override
            public void onSuccess() {
                logOut("setBeginnerModeEnable onSuccess ");
            }
        });
    }

    public void getBeginnerModeState(View view) {
        mController.isBeginnerModeEnable(new CallbackWithOneParam<Boolean>() {
            @Override
            public void onFailure(AutelError error) {
                logOut("isBeginnerModeEnable AutelError " + error.getDescription());
            }

            @Override
            public void onSuccess(Boolean mode) {
                logOut("isBeginnerModeEnable  " + mode);
            }
        });
    }


    public void getMaxHeight(View view) {
        mController.getMaxHeight(new CallbackWithOneParam<Float>() {
            @Override
            public void onFailure(AutelError error) {
                logOut("getMaxHeight AutelError " + error.getDescription());
            }

            @Override
            public void onSuccess(Float maxHeight) {
                logOut("getMaxHeight  " + maxHeight);
            }
        });
    }

    public void setMaxHeight(View view) {
        String value = fcMaxHeight.getText().toString();

        mController.setMaxHeight(isEmpty(value) ? 80 : Integer.valueOf(value), new CallbackWithNoParam() {
            @Override
            public void onFailure(AutelError error) {
                logOut("setMaxHeight AutelError " + error.getDescription());
            }

            @Override
            public void onSuccess() {
                logOut("setMaxHeight onSuccess ");
            }


        });
    }

    public void getMaxRange(View view) {
        mController.getMaxRange(new CallbackWithOneParam<Float>() {
            @Override
            public void onFailure(AutelError error) {
                logOut("getMaxRange AutelError " + error.getDescription());
            }

            @Override
            public void onSuccess(Float maxRange) {
                logOut("getMaxRange onSuccess " + maxRange);
            }
        });
    }

    public void setMaxRange(View view) {
        String value = fcMaxRange.getText().toString();

        mController.setMaxRange(isEmpty(value) ? 500 : Integer.valueOf(value), new CallbackWithNoParam() {
            @Override
            public void onFailure(AutelError error) {
                logOut("setMaxRange AutelError " + error.getDescription());
            }

            @Override
            public void onSuccess() {
                logOut("setMaxRange onSuccess ");
            }


        });
    }

    public void getReturnHeight(View view) {
        mController.getReturnHeight(new CallbackWithOneParam<Float>() {
            @Override
            public void onFailure(AutelError error) {
                logOut("getReturnHeight AutelError " + error.getDescription());
            }

            @Override
            public void onSuccess(Float returnHeight) {
                logOut("getReturnHeight onSuccess " + returnHeight);
            }


        });
    }

    public void setReturnHeight(View view) {
        String value = fcReturnHeight.getText().toString();
        mController.setReturnHeight(isEmpty(value) ? 30 : Integer.valueOf(value), new CallbackWithNoParam() {
            @Override
            public void onFailure(AutelError error) {
                logOut("setReturnHeight AutelError " + error.getDescription());
            }

            @Override
            public void onSuccess() {
                logOut("setReturnHeight onSuccess ");
            }
        });
    }

    public void getHorizontalSpeed(View view) {
        mController.getMaxHorizontalSpeed(new CallbackWithOneParam<Float>() {
            @Override
            public void onFailure(AutelError error) {
                logOut("getMaxHorizontalSpeed AutelError " + error.getDescription());
            }

            @Override
            public void onSuccess(Float horizontalSpeed) {
                logOut("getMaxHorizontalSpeed onSuccess " + horizontalSpeed);
            }

        });
    }

    public void setHorizontalSpeed(View view) {
        String value = fcHorizontalSpeed.getText().toString();
        mController.setMaxHorizontalSpeed(isEmpty(value) ? 5 : Integer.valueOf(value), new CallbackWithNoParam() {
            @Override
            public void onFailure(AutelError error) {
                logOut("setMaxHorizontalSpeed AutelError " + error.getDescription());
            }

            @Override
            public void onSuccess() {
                logOut("setMaxHorizontalSpeed onSuccess ");
            }
        });
    }

    public void isAttiModeEnable(View view) {
        mController.isAttitudeModeEnable(new CallbackWithOneParam<Boolean>() {
            @Override
            public void onFailure(AutelError error) {
                logOut("isAttitudeModeEnable AutelError " + error.getDescription());
            }

            @Override
            public void onSuccess(Boolean result) {
                logOut("isAttitudeModeEnable " + result);
            }
        });
    }

    public void setAttiModeEnable(boolean enable) {
        mController.setAttitudeModeEnable(enable, new CallbackWithNoParam() {
            @Override
            public void onFailure(AutelError error) {
                logOut("setAttitudeModeEnable AutelError " + error.getDescription());
            }

            @Override
            public void onSuccess() {
                logOut("setAttitudeModeEnable onSuccess ");
            }

        });
    }

    public void getLedPilotLamp(View view) {
        mController.getLedPilotLamp(new CallbackWithOneParam<LedPilotLamp>() {
            @Override
            public void onFailure(AutelError error) {
                logOut("getLedPilotLamp AutelError " + error.getDescription());
            }

            @Override
            public void onSuccess(LedPilotLamp ledPilotLamp) {
                logOut("getLedPilotLamp onSuccess " + ledPilotLamp);
            }
        });
    }

    public void setLedPilotLamp(View view) {
        mController.setLedPilotLamp(ledPilotLamp, new CallbackWithNoParam() {
            @Override
            public void onFailure(AutelError error) {
                logOut("setLedPilotLamp AutelError " + error.getDescription());
            }

            @Override
            public void onSuccess() {
                logOut("setLedPilotLamp onSuccess ");
            }
        });
    }

    public void setLocationAsHomePoint(View view) {
        float lat = isEmpty(locationAsHomePoint_la.getText().toString()) ? 0 : Float.valueOf(locationAsHomePoint_la.getText().toString());
        float lon = isEmpty(locationAsHomePoint_lon.getText().toString()) ? 0 : Float.valueOf(locationAsHomePoint_lon.getText().toString());
        mController.setLocationAsHomePoint(lat, lon, new CallbackWithNoParam() {
            @Override
            public void onFailure(AutelError error) {
                logOut("setLocationAsHomePoint AutelError " + error.getDescription());
            }

            @Override
            public void onSuccess() {
                logOut("setLocationAsHomePoint onSuccess ");
            }
        });
    }

    public void setAircraftLocationAsHomePoint(View view) {
        mController.setAircraftLocationAsHomePoint(new CallbackWithNoParam() {
            @Override
            public void onFailure(AutelError error) {
                logOut("setAircraftLocationAsHomePoint AutelError " + error.getDescription());
            }

            @Override
            public void onSuccess() {
                logOut("setAircraftLocationAsHomePoint onSuccess ");
            }
        });
    }

    public void startCalibrateCompass(View view) {
        mController.startCalibrateCompass(new CallbackWithOneParam<CalibrateCompassStatus>() {
            @Override
            public void onSuccess(CalibrateCompassStatus calibrateCompassStatus) {
                logOut("startCalibrateCompass onSuccess " + calibrateCompassStatus);
            }

            @Override
            public void onFailure(AutelError autelError) {
                logOut("startCalibrateCompass autelError " + autelError.getDescription());
            }
        });
    }

    public void takeOff(View view) {
        mController.takeOff(new CallbackWithNoParam() {
            @Override
            public void onSuccess() {
                logOut("takeOff onSuccess ");
            }

            @Override
            public void onFailure(AutelError autelError) {
                logOut("takeOff AutelError " + autelError.getDescription());
            }
        });
    }

    public void land(View view) {
        mController.land(new CallbackWithNoParam() {
            @Override
            public void onSuccess() {
                logOut("land onSuccess ");
            }

            @Override
            public void onFailure(AutelError autelError) {
                logOut("land AutelError " + autelError.getDescription());
            }
        });
    }

    public void goHome(View view) {
        mController.goHome(new CallbackWithNoParam() {
            @Override
            public void onSuccess() {
                logOut("goHome onSuccess ");
            }

            @Override
            public void onFailure(AutelError autelError) {
                logOut("goHome AutelError " + autelError.getDescription());
            }
        });
    }

    public void cancelReturn(View view) {
        mController.cancelReturn(new CallbackWithNoParam() {
            @Override
            public void onSuccess() {
                logOut("cancelReturn onSuccess ");
            }

            @Override
            public void onFailure(AutelError autelError) {
                logOut("cancelReturn AutelError " + autelError.getDescription());
            }
        });
    }

    public void cancelLand(View view) {
        mController.cancelLand(new CallbackWithNoParam() {
            @Override
            public void onSuccess() {
                logOut("cancelLand onSuccess ");
            }

            @Override
            public void onFailure(AutelError autelError) {
                logOut("cancelLand AutelError " + autelError.getDescription());
            }
        });
    }

    public void getVersionInfo(View view) {
        mController.getVersionInfo(new CallbackWithOneParam<FlyControllerVersionInfo>() {
            @Override
            public void onSuccess(FlyControllerVersionInfo flyControllerVersionInfo) {
                logOut("getVersionInfo data {" + flyControllerVersionInfo + "}");
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
                logOut("getSerialNumber data " + serialNumber);
            }

            @Override
            public void onFailure(AutelError autelError) {
                logOut("getSerialNumber onFailure : " + autelError.getDescription());
            }
        });
    }

    public void setCalibrateCompassListener(View view) {
        mController.setCalibrateCompassListener(new CallbackWithOneParam<CalibrateCompassStatus>() {
            @Override
            public void onFailure(AutelError error) {
                logOut("setCalibrateCompassListener " + error.getDescription());
            }

            @Override
            public void onSuccess(CalibrateCompassStatus result) {
                logOut("setCalibrateCompassListener onSuccess " + result);
            }
        });
    }

    public void resetCalibrateCompassListener(View view) {
        mController.setCalibrateCompassListener(null);
    }

    public void setWarningListener(View view) {
        mController.setWarningListener(new CallbackWithTwoParams<ARMWarning, MagnetometerState>() {

            @Override
            public void onFailure(AutelError error) {
                logOut("setWarningListener " + error.getDescription());
            }

            @Override
            public void onSuccess(ARMWarning data1, MagnetometerState data2) {
                logOut("setWarningListener ARMWarning " + data1 + " MagnetometerState " + data2);
            }
        });
    }

    public void resetWarningListener(View view) {
        mController.setWarningListener(null);
    }
}
