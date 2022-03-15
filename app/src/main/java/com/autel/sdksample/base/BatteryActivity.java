package com.autel.sdksample.base;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.autel.common.CallbackWithNoParam;
import com.autel.common.CallbackWithOneParam;
import com.autel.common.RangePair;
import com.autel.common.battery.BatteryParameterRangeManager;
import com.autel.common.error.AutelError;
import com.autel.sdk.battery.AutelBattery;
import com.autel.sdk.product.BaseProduct;
import com.autel.sdksample.R;


public class BatteryActivity extends BaseActivity<AutelBattery> {

    protected EditText lowBatteryNotifyThreshold;
    protected EditText criticalBatteryNotifyThreshold;
    protected EditText dischargeDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Battery");
    }

    @Override
    protected AutelBattery initController(BaseProduct product) {
        return product.getBattery();
    }

    @Override
    protected int getCustomViewResId() {
        return R.layout.ac_base_battery;
    }

    @Override
    protected void initUi() {
        lowBatteryNotifyThreshold = (EditText) findViewById(R.id.lowBatteryNotifyThreshold);
        criticalBatteryNotifyThreshold = (EditText) findViewById(R.id.criticalBatteryNotifyThreshold);
        dischargeDay = (EditText) findViewById(R.id.dischargeDay);

        final TextView lowBatteryRange = (TextView) findViewById(R.id.lowBatteryRange);
        lowBatteryNotifyThreshold.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (isEmpty(lowBatteryRange.getText().toString())) {
                    BatteryParameterRangeManager batteryParameterRangeManager = mController.getParameterSupportManager();
                    RangePair<Float> support = batteryParameterRangeManager.getLowBattery();
                    lowBatteryRange.setText("low battery range from " + support.getValueFrom() + " to " + support.getValueTo());
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final TextView criticalBatteryRange = (TextView) findViewById(R.id.criticalBatteryRange);
        criticalBatteryNotifyThreshold.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (isEmpty(criticalBatteryRange.getText().toString())) {
                    BatteryParameterRangeManager batteryParameterRangeManager = mController.getParameterSupportManager();
                    RangePair<Float> support = batteryParameterRangeManager.getCriticalBattery();
                    criticalBatteryRange.setText("critical battery range from " + support.getValueFrom() + " to " + support.getValueTo());
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        final TextView dischargeDayRange = (TextView) findViewById(R.id.dischargeDayRange);
        dischargeDay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (isEmpty(dischargeDayRange.getText().toString())) {
                    BatteryParameterRangeManager batteryParameterRangeManager = mController.getParameterSupportManager();
                    RangePair<Integer> support = batteryParameterRangeManager.getDischargeDay();
                    dischargeDayRange.setText("discharge day range from " + support.getValueFrom() + " to " + support.getValueTo());
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        initClick();
    }


    private void initClick() {
        findViewById(R.id.getLowBatteryNotifyThreshold).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.getLowBatteryNotifyThreshold(new CallbackWithOneParam<Float>() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getLowBatteryNotifyThreshold  error :  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess(Float data) {
                        logOut("getLowBatteryNotifyThreshold  data :  " + data);
                    }
                });
            }
        });
        findViewById(R.id.setLowBatteryNotifyThreshold).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = lowBatteryNotifyThreshold.getText().toString();
                mController.setLowBatteryNotifyThreshold(isEmpty(value) ? 0.25f : Float.valueOf(value), new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setLowBatteryNotifyThreshold  error :  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("setLowBatteryNotifyThreshold   onSuccess ");
                    }
                });
            }
        });

        findViewById(R.id.getCriticalBatteryNotifyThreshold).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int index = 0; index <3;index++){
                    mController.getCriticalBatteryNotifyThreshold(new CallbackWithOneParam<Float>() {
                        @Override
                        public void onFailure(AutelError error) {
                            logOut("getCriticalBatteryNotifyThreshold  error :  " + error.getDescription()+"  time "+System.currentTimeMillis());
                        }

                        @Override
                        public void onSuccess(Float data) {
                            logOut("getCriticalBatteryNotifyThreshold  data :  " + data+"  time "+System.currentTimeMillis());
                        }
                    });
                }
            }
        });
        findViewById(R.id.setCriticalBatteryNotifyThreshold).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = criticalBatteryNotifyThreshold.getText().toString();
                mController.setCriticalBatteryNotifyThreshold(isEmpty(value) ? 0.25f : Float.valueOf(value), new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setCriticalBatteryNotifyThreshold  error :  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("setCriticalBatteryNotifyThreshold  onSuccess  ");
                    }
                });
            }
        });
        findViewById(R.id.getDischargeDay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.getDischargeDay(new CallbackWithOneParam<Integer>() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getDischargeDay  error :  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess(Integer data) {
                        logOut("getDischargeDay  data :  " + data);
                    }
                });
            }
        });
        findViewById(R.id.setDischargeDay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = dischargeDay.getText().toString();
                mController.setDischargeDay(isEmpty(value) ? 2 : Integer.valueOf(value), new CallbackWithNoParam() {
                    @Override
                    public void onSuccess() {

                        logOut("setDischargeDay  onSuccess  ");
                    }

                    @Override
                    public void onFailure(AutelError autelError) {
                        logOut("setDischargeDay  error :  " + autelError.getDescription());
                    }
                });
            }
        });
        findViewById(R.id.getDischargeCount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.getDischargeCount(new CallbackWithOneParam<Integer>() {
                    @Override
                    public void onSuccess(Integer data) {
                        logOut("getDischargeCount  " + data);
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getDischargeCount error : " + error.getDescription());
                    }
                });
            }
        });
        findViewById(R.id.getVersion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.getVersion(new CallbackWithOneParam<String>() {
                    @Override
                    public void onSuccess(String data) {
                        logOut("getVersion  " + data);
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getVersion  error : " + error.getDescription());
                    }
                });
            }
        });
        findViewById(R.id.getSerialNumber).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.getSerialNumber(new CallbackWithOneParam<String>() {
                    @Override
                    public void onSuccess(String data) {
                        logOut("getSerialNumber  " + data);
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getSerialNumber  error : " + error.getDescription());
                    }
                });
            }
        });
        findViewById(R.id.getFullChargeCapacity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.getFullChargeCapacity(new CallbackWithOneParam<Integer>() {
                    @Override
                    public void onSuccess(Integer data) {
                        logOut("getFullChargeCapacity  " + data);
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getFullChargeCapacity error : " + error.getDescription());
                    }
                });
            }
        });
        findViewById(R.id.getCellVoltageRange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.getParameterSupportManager().getBatteryCellVoltageRange(new CallbackWithOneParam<RangePair<Integer>>() {
                    @Override
                    public void onSuccess(RangePair<Integer> data) {
                        logOut("getBatteryCellVoltageRange  from " + data.getValueFrom() + " to " + data.getValueTo());
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getBatteryCellVoltageRange error : " + error.getDescription());
                    }
                });
            }
        });
    }
}
