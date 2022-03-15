package com.autel.sdksample.xstar;

import com.autel.sdk.product.BaseProduct;
import com.autel.sdk.remotecontroller.AutelRemoteController;
import com.autel.sdksample.R;
import com.autel.sdksample.base.RemoteControllerActivity;


public class XStarRemoteControllerActivity extends RemoteControllerActivity {

    @Override
    protected AutelRemoteController initController(BaseProduct product) {
        return product.getRemoteController();
    }

    @Override
    protected int getCustomViewResId() {
        return R.layout.activity_rc;
    }
}
