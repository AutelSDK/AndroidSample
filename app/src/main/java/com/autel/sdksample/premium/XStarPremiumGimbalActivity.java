package com.autel.sdksample.premium;

import android.view.View;

import com.autel.common.CallbackWithOneParam;
import com.autel.common.error.AutelError;
import com.autel.sdk.gimbal.AutelGimbal;
import com.autel.sdk.gimbal.XStarGimbal;
import com.autel.sdk.product.BaseProduct;
import com.autel.sdk.product.XStarPremiumAircraft;
import com.autel.sdksample.R;
import com.autel.sdksample.base.gimbal.GimbalActivity;

/**
 * Created by A16343 on 2017/9/6.
 */

public class XStarPremiumGimbalActivity extends GimbalActivity {
    private XStarGimbal mXStarGimbal;
    @Override
    protected AutelGimbal initController(BaseProduct product) {
        mXStarGimbal = ((XStarPremiumAircraft) product).getGimbal();
        return mXStarGimbal;
    }

    @Override
    protected int getCustomViewResId() {
        return R.layout.activity_xstar_gimbal;
    }

    @Override
    protected void initUi() {
        super.initUi();
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
    }
}
