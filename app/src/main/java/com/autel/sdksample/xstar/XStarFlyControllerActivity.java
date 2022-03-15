package com.autel.sdksample.xstar;

import android.view.View;

import com.autel.common.CallbackWithOneParam;
import com.autel.common.error.AutelError;
import com.autel.common.flycontroller.FlyControllerInfo;
import com.autel.sdk.flycontroller.AutelFlyController;
import com.autel.sdk.flycontroller.XStarFlyController;
import com.autel.sdk.product.BaseProduct;
import com.autel.sdk.product.XStarAircraft;
import com.autel.sdksample.R;
import com.autel.sdksample.base.FlyControllerActivity;

/**
 * Created by A16343 on 2017/9/6.
 */

public class XStarFlyControllerActivity extends FlyControllerActivity {
    protected XStarFlyController mXStarFlyController;

    @Override
    protected AutelFlyController initController(BaseProduct product) {
        mXStarFlyController = ((XStarAircraft) product).getFlyController();
        return mXStarFlyController;
    }

    @Override
    protected int getCustomViewResId() {
        return R.layout.activity_xstar_fc;
    }




    public void setUltraSonicHeightInfoListener(View view) {
        mXStarFlyController.setUltraSonicHeightInfoListener(new CallbackWithOneParam<Float>() {
            @Override
            public void onFailure(AutelError error) {
                logOut("setUltraSonicHeightInfoListener error " + error.getDescription());
            }

            @Override
            public void onSuccess(Float result) {
                logOut("setUltraSonicHeightInfoListener onSuccess " + result);
            }
        });
    }

    public void resetUltraSonicHeightInfoListener(View view) {
        mXStarFlyController.setUltraSonicHeightInfoListener(null);
    }

    public void resetFCHeightInfoListener(View view) {
        mXStarFlyController.setUltraSonicHeightInfoListener(null);
    }

    public void setFlyControllerListener(View view) {
        mXStarFlyController.setFlyControllerInfoListener(new CallbackWithOneParam<FlyControllerInfo>() {
            @Override
            public void onSuccess(FlyControllerInfo data) {
                logOut("setFlyControllerListener data " + data);
            }

            @Override
            public void onFailure(AutelError error) {
                logOut("setFlyControllerListener " + error.getDescription());
            }
        });
    }

    public void resetFlyControllerListener(View view) {
        mXStarFlyController.setFlyControllerInfoListener(null);
    }


}
