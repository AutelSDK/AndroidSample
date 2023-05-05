package com.autel.sdksample.evo;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.autel.common.CallbackWithNoParam;
import com.autel.common.CallbackWithOneParam;
import com.autel.common.camera.visual.VisualWarningInfo;
import com.autel.common.error.AutelError;
import com.autel.common.flycontroller.evo.EvoFlyControllerInfo;
import com.autel.common.flycontroller.visual.AvoidanceRadarInfo;
import com.autel.common.flycontroller.visual.VisualSettingInfo;
import com.autel.common.flycontroller.visual.VisualSettingSwitchblade;
import com.autel.sdk.flycontroller.AutelFlyController;
import com.autel.sdk.flycontroller.EvoFlyController;
import com.autel.sdk.product.BaseProduct;
import com.autel.sdk.product.EvoAircraft;
import com.autel.sdksample.R;
import com.autel.sdksample.base.FlyControllerActivity;
import com.autel.sdksample.evo.adapter.VisualSettingSwitchBladeAdapter;

/**
 * Created by A16343 on 2017/9/6.
 */

public class G2FlyControllerActivity extends FlyControllerActivity {
    private EvoFlyController mEvoFlyController;
  /*  LandingGearStateAdapter mLandingGearStateAdapter;
    LandingGearState selectedLandingGearState = LandingGearState.UNKNOWN;*/
    VisualSettingSwitchblade mVisualSettingSwitchblade = VisualSettingSwitchblade.UNKNOWN;
    private Switch visualSettingEnableState;

    @Override
    protected AutelFlyController initController(BaseProduct product) {
        mEvoFlyController = ((EvoAircraft) product).getFlyController();
        return mEvoFlyController;
    }

    @Override
    protected int getCustomViewResId() {
        return R.layout.activity_g2_fc;
    }

    @Override
    protected void initUi() {
        super.initUi();
      /*  mLandingGearStateAdapter = new LandingGearStateAdapter(this);
        ((Spinner) findViewById(R.id.landingGearStateList)).setAdapter(mLandingGearStateAdapter);
        ((Spinner) findViewById(R.id.landingGearStateList)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLandingGearState = (LandingGearState) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedLandingGearState = LandingGearState.UNKNOWN;
            }
        });*/

        visualSettingEnableState = (Switch) findViewById(R.id.visualSettingEnableState);

        ((Spinner) findViewById(R.id.visualSettingList)).setAdapter(new VisualSettingSwitchBladeAdapter(this));
        ((Spinner) findViewById(R.id.visualSettingList)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mVisualSettingSwitchblade = (VisualSettingSwitchblade) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
               // selectedLandingGearState = LandingGearState.UNKNOWN;
            }
        });


    }

    public void setFlyControllerListener(View view) {
        mEvoFlyController.setFlyControllerInfoListener(new CallbackWithOneParam<EvoFlyControllerInfo>() {
            @Override
            public void onSuccess(EvoFlyControllerInfo data) {
                logOut("setFlyControllerListener data " + data);
            }

            @Override
            public void onFailure(AutelError error) {
                logOut("setFlyControllerListener " + error.getDescription());
            }
        });

//        mEvoFlyController.setVisualViewPointCoordListener(new CallbackWithOneParam<ViewPointTargetArea>() {
//            @Override
//            public void onSuccess(ViewPointTargetArea data) {
//                logOut("setVisualViewPointCoordListener data " + data);
//            }
//
//            @Override
//            public void onFailure(AutelError error) {
//                logOut("setVisualViewPointCoordListener onFailure " );
//            }
//        });
    }

    public void resetFlyControllerListener(View view) {
        mEvoFlyController.setFlyControllerInfoListener(null);
    }

    public void setLandingGearState(View view) {
//        mEvoFlyController.setLandingGearState(selectedLandingGearState, new CallbackWithNoParam() {
//            @Override
//            public void onSuccess() {
//                logOut("setLandingGearState onSuccess " + selectedLandingGearState);
//            }
//
//            @Override
//            public void onFailure(AutelError error) {
//                logOut("setLandingGearState onFailure " + error.getDescription());
//            }
//        });
    }

    public void droneArmed(View view) {
        mEvoFlyController.droneArmed(new CallbackWithNoParam() {
            @Override
            public void onSuccess() {
                //logOut("droneArmed onSuccess " + selectedLandingGearState);
            }

            @Override
            public void onFailure(AutelError error) {
                logOut("droneArmed onFailure " + error.getDescription());
            }
        });
    }

    public void droneDisarmed(View view) {
        mEvoFlyController.droneDisarmed(new CallbackWithNoParam() {
            @Override
            public void onSuccess() {
               // logOut("droneDisarmed onSuccess " + selectedLandingGearState);
            }

            @Override
            public void onFailure(AutelError error) {
                logOut("droneDisarmed onFailure " + error.getDescription());
            }
        });
    }

    public void setVisualWarnListener(View view) {
        mEvoFlyController.setVisualWarnListener(new CallbackWithOneParam<VisualWarningInfo>() {
            @Override
            public void onSuccess(VisualWarningInfo warning) {
                logOut("setVisualWarnListener onSuccess " + warning);
            }

            @Override
            public void onFailure(AutelError error) {
                logOut("setVisualWarnListener onFailure " + error.getDescription());
            }
        });
    }

    public void resetVisualWarnListener(View view) {
        mEvoFlyController.setVisualWarnListener(null);
    }

    public void setRadarInfoListener(View view) {
        mEvoFlyController.setAvoidanceRadarInfoListener(new CallbackWithOneParam<AvoidanceRadarInfo>() {
            @Override
            public void onSuccess(AvoidanceRadarInfo radarInfo) {
                logOut("setRadarInfoListener onSuccess " + radarInfo);
            }

            @Override
            public void onFailure(AutelError error) {
                logOut("setRadarInfoListener onFailure " + error.getDescription());
            }
        });
    }

    public void setVisualSettingEnable(View view) {
        if(mVisualSettingSwitchblade == VisualSettingSwitchblade.SET_VIEW_POINT_COORD){

        }else {
            mEvoFlyController.setVisualSettingEnable(mVisualSettingSwitchblade, visualSettingEnableState.isEnabled(), new CallbackWithNoParam() {
                @Override
                public void onSuccess() {
                    logOut("setVisualSettingEnable onSuccess ");
                }

                @Override
                public void onFailure(AutelError error) {
                    logOut("setVisualSettingEnable onFailure " + error.getDescription());
                }
            });
        }
    }

    public void getVisualSettingEnable(View view) {
        mEvoFlyController.getVisualSettingInfo(new CallbackWithOneParam<VisualSettingInfo>() {
            @Override
            public void onSuccess(VisualSettingInfo data) {
                logOut("getVisualSettingEnable onSuccess " + data.toString());
            }

            @Override
            public void onFailure(AutelError error) {
                logOut("getVisualSettingEnable onFailure " + error.getDescription());
            }
        });
    }

    public void setVisualViewPointSpeed(View view) {
        String value = ((EditText) findViewById(R.id.VisualViewPointSpeed)).getText().toString();
        mEvoFlyController.setVisualViewPointSpeed(Float.valueOf(value), new CallbackWithNoParam() {
            @Override
            public void onSuccess() {
                logOut("setVisualViewPointSpeed onSuccess ");
            }

            @Override
            public void onFailure(AutelError error) {
                logOut("setVisualViewPointSpeed onFailure " + error.getDescription());
            }
        });
    }

    public void resetRadarInfoListener(View view) {
        mEvoFlyController.setAvoidanceRadarInfoListener(null);
    }

    public void setLeftControl() {
        if (mEvoFlyController == null) return;
        String leftHorValue = ((EditText) findViewById(R.id.left_hor_edit)).getText().toString();
        String leftVerValue = ((EditText) findViewById(R.id.left_ver_edit)).getText().toString();
        String rightHorValue = ((EditText) findViewById(R.id.right_hor_edit)).getText().toString();
        String rightVerValue = ((EditText) findViewById(R.id.right_ver_edit)).getText().toString();
        int rhorizontalValue = Integer.parseInt(rightHorValue);
        int rverticalValue = Integer.parseInt(rightVerValue);
        if (TextUtils.isEmpty(rightHorValue) || TextUtils.isEmpty(rightVerValue)) return;
        if (TextUtils.isEmpty(leftHorValue) || TextUtils.isEmpty(leftVerValue)) return;
        int horizontalValue = Integer.parseInt(leftHorValue);
        int verticalValue = Integer.parseInt(leftVerValue);
    }

    public void setRightControl() {
        if (mEvoFlyController == null) return;
        String rightHorValue = ((EditText) findViewById(R.id.right_hor_edit)).getText().toString();
        String rightVerValue = ((EditText) findViewById(R.id.right_ver_edit)).getText().toString();
        if (TextUtils.isEmpty(rightHorValue) || TextUtils.isEmpty(rightVerValue)) return;
        int horizontalValue = Integer.parseInt(rightHorValue);
        int verticalValue = Integer.parseInt(rightVerValue);
//        mEvoFlyController.setControlRightStick(horizontalValue, verticalValue, new CallbackWithNoParam() {
//            @Override
//            public void onSuccess() {
//                logOut("setRightControl onSuccess ");
//            }
//
//            @Override
//            public void onFailure(AutelError autelError) {
//                logOut("setRightControl onFailure " + autelError.getDescription());
//            }
//        });
    }
}
