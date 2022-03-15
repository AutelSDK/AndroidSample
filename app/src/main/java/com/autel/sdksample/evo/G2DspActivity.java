package com.autel.sdksample.evo;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.autel.common.CallbackWithNoParam;
import com.autel.common.CallbackWithOneParam;
import com.autel.common.dsp.evo.BandMode;
import com.autel.common.dsp.evo.Bandwidth;
import com.autel.common.dsp.evo.DeviceVersionInfo;
import com.autel.common.dsp.evo.EvoDspInfo;
import com.autel.common.dsp.evo.TransferMode;
import com.autel.common.error.AutelError;
import com.autel.sdk.dsp.AutelDsp;
import com.autel.sdk.dsp.EvoDsp;
import com.autel.sdk.product.BaseProduct;
import com.autel.sdk.product.EvoAircraft;
import com.autel.sdksample.R;
import com.autel.sdksample.base.DspActivity;
import com.autel.sdksample.evo.adapter.BandModeAdapter;
import com.autel.sdksample.evo.adapter.BandwidthAdapter;
import com.autel.sdksample.evo.adapter.TransferModeAdapter;

import java.util.List;

/**
 * Created by A16343 on 2017/9/12.
 */

public class G2DspActivity extends DspActivity {
    EvoDsp mXStarEvoDsp;
    BandMode selectedBandMode = BandMode.UNKNOWN;
    Bandwidth selectedBandwidth = Bandwidth.UNKNOWN;
    TransferMode selectedTransferMode = TransferMode.UNKNOWN;

    @Override
    protected AutelDsp initController(BaseProduct product) {
        mXStarEvoDsp = ((EvoAircraft) product).getDsp();
        return mXStarEvoDsp;
    }

    @Override
    protected int getCustomViewResId() {
        return R.layout.activity_g2_dsp;
    }

    @Override
    protected void initUi() {
        super.initUi();
        ((Spinner) findViewById(R.id.bandModeList)).setAdapter(new BandModeAdapter(this));
        ((Spinner) findViewById(R.id.bandModeList)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBandMode = (BandMode) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ((Spinner) findViewById(R.id.bandwidthList)).setAdapter(new BandwidthAdapter(this));
        ((Spinner) findViewById(R.id.bandwidthList)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBandwidth = (Bandwidth) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        findViewById(R.id.setBandwidthInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mXStarEvoDsp.setBandwidthInfo(selectedBandMode, selectedBandwidth);
            }
        });

        ((Spinner) findViewById(R.id.transferModeList)).setAdapter(new TransferModeAdapter(this));
        ((Spinner) findViewById(R.id.transferModeList)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTransferMode = (TransferMode) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        findViewById(R.id.setTransferMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mXStarEvoDsp.setTransferMode(selectedTransferMode, new CallbackWithNoParam() {
                    @Override
                    public void onSuccess() {
                        logOut("setTransferMode onSuccess");
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setTransferMode onFailure " + error.getDescription());
                    }
                });
            }
        });
        findViewById(R.id.getTransferMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mXStarEvoDsp.getTransferMode(new CallbackWithOneParam<TransferMode>() {
                    @Override
                    public void onSuccess(TransferMode data) {
                        logOut("getTransferMode onSuccess " + data);
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getTransferMode onFailure " + error.getDescription());
                    }
                });
            }
        });

        findViewById(R.id.setDspInfoListener).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mXStarEvoDsp.setDspInfoListener(new CallbackWithOneParam<EvoDspInfo>() {
                    @Override
                    public void onSuccess(EvoDspInfo g2DspInfo) {
                        logOut("setDspInfoListener onSuccess " + g2DspInfo);
                    }

                    @Override
                    public void onFailure(AutelError autelError) {
                        logOut("setDspInfoListener onFailure " + autelError.getDescription());
                    }
                });
            }
        });
        findViewById(R.id.resetDspInfoListener).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mXStarEvoDsp.setDspInfoListener(null);
            }
        });
        findViewById(R.id.getDeviceVersionInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mXStarEvoDsp.getDeviceVersionInfo(new CallbackWithOneParam<List<DeviceVersionInfo>>() {
                    @Override
                    public void onSuccess(List<DeviceVersionInfo> data) {
                        logOut("getDeviceVersionInfo onSuccess " + data.size());
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getDeviceVersionInfo onFailure " + error.getDescription());
                    }
                });
            }
        });
    }
}
