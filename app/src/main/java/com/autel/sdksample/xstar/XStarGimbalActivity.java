package com.autel.sdksample.xstar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.autel.common.CallbackWithOneParam;
import com.autel.common.RangePair;
import com.autel.common.error.AutelError;
import com.autel.common.gimbal.GimbalRollAngleAdjust;
import com.autel.common.gimbal.GimbalState;
import com.autel.common.gimbal.xstar.XStarGimbalParameterRangeManager;
import com.autel.sdk.gimbal.AutelGimbal;
import com.autel.sdk.gimbal.XStarGimbal;
import com.autel.sdk.product.BaseProduct;
import com.autel.sdk.product.XStarAircraft;
import com.autel.sdksample.R;
import com.autel.sdksample.base.gimbal.GimbalActivity;
import com.autel.sdksample.base.gimbal.adapter.RollAdjustAdapter;

/**
 * Created by A16343 on 2017/9/6.
 */

public class XStarGimbalActivity extends GimbalActivity {
    private XStarGimbal mXStarGimbal;
    Spinner rollAdjustList;
    EditText gimbalAngle;
    EditText gimbalAngleWithFineTuning;
    GimbalRollAngleAdjust gimbalRollAngleAdjust = GimbalRollAngleAdjust.MINUS;

    @Override
    protected AutelGimbal initController(BaseProduct product) {
        mXStarGimbal = ((XStarAircraft) product).getGimbal();
        return mXStarGimbal;
    }

    @Override
    protected int getCustomViewResId() {
        return R.layout.activity_xstar_gimbal;
    }

    @Override
    protected void initUi() {
        super.initUi();


        gimbalAngle = (EditText) findViewById(R.id.gimbalAngle);

        rollAdjustList = (Spinner) findViewById(R.id.rollAdjustList);
        rollAdjustList.setAdapter(new RollAdjustAdapter(this));
        rollAdjustList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gimbalRollAngleAdjust = (GimbalRollAngleAdjust) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final TextView gimbalAngleRange = (TextView) findViewById(R.id.gimbalAngleRange);
        gimbalAngle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (isEmpty(s.toString())) {
                    return;
                }
                if (isEmpty(gimbalAngleRange.getText().toString())) {
                    XStarGimbalParameterRangeManager gimbalParameterRangeManager = (XStarGimbalParameterRangeManager) mController.getParameterRangeManager();
                    RangePair<Integer> support = gimbalParameterRangeManager.getAngle();
                    gimbalAngleRange.setText("angle from " + support.getValueFrom() + " to " + support.getValueTo());
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        findViewById(R.id.setRollAdjustData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mXStarGimbal.setRollAdjustData(gimbalRollAngleAdjust, new CallbackWithOneParam<Double>() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setRollAdjustData error " + error.getDescription());
                    }

                    @Override
                    public void onSuccess(Double data) {
                        logOut("setRollAdjustData data " + data);
                    }
                });
            }
        });

        findViewById(R.id.setGimbalAngleListener).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mXStarGimbal.setAngleListener(new CallbackWithOneParam<Integer>() {

                    @Override
                    public void onSuccess(Integer integer) {
                        logOut("setAngleListener onSuccess " + integer);
                    }

                    @Override
                    public void onFailure(AutelError autelError) {
                        logOut("setAngleListener error " + autelError.getDescription());
                    }
                });
            }
        });
        findViewById(R.id.resetGimbalAngleListener).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mXStarGimbal.setAngleListener(null);
            }
        });

        findViewById(R.id.setGimbalAngle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = gimbalAngle.getText().toString();
                mXStarGimbal.setGimbalAngle(isEmpty(value) ? 10 : Integer.valueOf(value));
            }
        });

        gimbalAngleWithFineTuning = (EditText) findViewById(R.id.gimbalAngleWithFineTuning);
        final TextView angleWithFineTuningRange = (TextView) findViewById(R.id.angleWithFineTuningRange);
        gimbalAngleWithFineTuning.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (isEmpty(s.toString())) {
                    return;
                }

                if (isEmpty(angleWithFineTuningRange.getText().toString())) {
                    XStarGimbalParameterRangeManager gimbalParameterRangeManager = (XStarGimbalParameterRangeManager) mController.getParameterRangeManager();
                    RangePair<Integer> support = gimbalParameterRangeManager.getAngleWithSpeed();
                    angleWithFineTuningRange.setText("angle with fine tuning from " + support.getValueFrom() + " to " + support.getValueTo());
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        findViewById(R.id.setGimbalAngleWithFineTuning).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = gimbalAngleWithFineTuning.getText().toString();
                mXStarGimbal.setGimbalAngleWithSpeed(isEmpty(value) ? -10 : Integer.valueOf(value));
            }
        });

        findViewById(R.id.setGimbalStateListener).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mXStarGimbal.setGimbalStateListener(new CallbackWithOneParam<GimbalState>() {
                    @Override
                    public void onSuccess(GimbalState gimbalState) {
                        logOut("setGimbalStateListener onSuccess " + gimbalState);
                    }

                    @Override
                    public void onFailure(AutelError autelError) {
                        logOut("setGimbalStateListener error " + autelError.getDescription());
                    }
                });
            }
        });
        findViewById(R.id.resetGimbalStateListener).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mXStarGimbal.setGimbalStateListener(null);
            }
        });
    }
}
