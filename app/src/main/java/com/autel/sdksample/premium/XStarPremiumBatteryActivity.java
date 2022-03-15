package com.autel.sdksample.premium;

import com.autel.sdk.battery.AutelBattery;
import com.autel.sdk.product.BaseProduct;
import com.autel.sdk.product.XStarPremiumAircraft;
import com.autel.sdksample.xstar.XStarBatteryActivity;


public class XStarPremiumBatteryActivity extends XStarBatteryActivity {

    @Override
    protected AutelBattery initController(BaseProduct product) {
        mXStarBattery = ((XStarPremiumAircraft) product).getBattery();
        return mXStarBattery;
    }
}
