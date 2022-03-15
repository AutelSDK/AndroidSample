package com.autel.sdksample.premium;

import com.autel.sdk.flycontroller.AutelFlyController;
import com.autel.sdk.product.BaseProduct;
import com.autel.sdk.product.XStarPremiumAircraft;
import com.autel.sdksample.xstar.XStarFlyControllerActivity;

/**
 * Created by A16343 on 2017/9/6.
 */

public class XStarPremiumFlyControllerActivity extends XStarFlyControllerActivity {
    @Override
    protected AutelFlyController initController(BaseProduct product) {
        mXStarFlyController = ((XStarPremiumAircraft) product).getFlyController();
        return mXStarFlyController;
    }

}
