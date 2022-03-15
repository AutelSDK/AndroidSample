package com.autel.sdksample.premium;

import android.view.View;

import com.autel.sdk.dsp.AutelDsp;
import com.autel.sdk.dsp.XStarDsp;
import com.autel.sdk.product.BaseProduct;
import com.autel.sdk.product.XStarPremiumAircraft;
import com.autel.sdksample.R;
import com.autel.sdksample.xstar.XStarDspActivity;

/**
 * Created by A16343 on 2017/9/12.
 */

public class XStarPremiumDspActivity extends XStarDspActivity {
    XStarDsp mXStarDsp;

    @Override
    protected AutelDsp initController(BaseProduct product) {
        mXStarDsp = ((XStarPremiumAircraft) product).getDsp();
        return mXStarDsp;
    }

    @Override
    protected void initUi() {
        super.initUi();
        boolean usbEnable = mXStarDsp.isUSBEnable();
        findViewById(R.id.updateSSIDLayout).setVisibility(usbEnable ? View.GONE : View.VISIBLE);
        findViewById(R.id.getCurrentSSIDInfo).setVisibility(usbEnable ? View.GONE : View.VISIBLE);
        findViewById(R.id.resetWifi).setVisibility(usbEnable ? View.GONE : View.VISIBLE);
        findViewById(R.id.setCurrentRFLayout).setVisibility(usbEnable ? View.VISIBLE : View.GONE);
        findViewById(R.id.getCurrentRFLayout).setVisibility(usbEnable ? View.VISIBLE : View.GONE);
        findViewById(R.id.scanRFLayout).setVisibility(usbEnable ? View.VISIBLE : View.GONE);
    }
}
