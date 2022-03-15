package com.autel.sdksample.base.gimbal;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.autel.common.CallbackWithNoParam;
import com.autel.common.CallbackWithOneParam;
import com.autel.common.error.AutelError;
import com.autel.common.gimbal.GimbalVersionInfo;
import com.autel.common.gimbal.GimbalWorkMode;
import com.autel.sdk.gimbal.AutelGimbal;
import com.autel.sdksample.R;
import com.autel.sdksample.base.BaseActivity;
import com.autel.sdksample.base.gimbal.adapter.GimbalModeAdapter;


public abstract class GimbalActivity extends BaseActivity<AutelGimbal> {
    Spinner gimbalWorkModeList;
    GimbalWorkMode gimbalWorkMode = GimbalWorkMode.FPV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Gimbal");
    }

    @Override
    protected int getCustomViewResId() {
        return R.layout.ac_base_gimbal;
    }


    @Override
    protected void initUi() {


        gimbalWorkModeList = (Spinner) findViewById(R.id.gimbalWorkModeList);
        gimbalWorkModeList.setAdapter(new GimbalModeAdapter(this));
        gimbalWorkModeList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gimbalWorkMode = (GimbalWorkMode) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        initClick();
    }

    private void initClick() {
        findViewById(R.id.setGimbalWorkMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.setGimbalWorkMode(gimbalWorkMode, new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setGimbalWorkMode error " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("setGimbalWorkMode result   onSuccess");
                    }
                });
            }
        });
        findViewById(R.id.getGimbalWorkMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.getGimbalWorkMode(new CallbackWithOneParam<GimbalWorkMode>() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getGimbalWorkMode error " + error.getDescription());
                    }

                    @Override
                    public void onSuccess(GimbalWorkMode data) {
                        logOut("getGimbalWorkMode data " + data);
                    }
                });
            }
        });

        findViewById(R.id.getVersionInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.getVersionInfo(new CallbackWithOneParam<GimbalVersionInfo>() {
                    @Override
                    public void onSuccess(GimbalVersionInfo gimbalVersionInfo) {
                        logOut("getVersionInfo onSuccess {" + gimbalVersionInfo + "}");
                    }

                    @Override
                    public void onFailure(AutelError autelError) {
                        logOut("getVersionInfo onFailure " + autelError.getDescription());
                    }
                });
            }
        });
    }
}
