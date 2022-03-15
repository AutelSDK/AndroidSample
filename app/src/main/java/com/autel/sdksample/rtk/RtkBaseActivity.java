package com.autel.sdksample.rtk;

import android.os.Bundle;

import com.autel.common.CallbackWithOneParam;
import com.autel.common.error.AutelError;
import com.autel.common.flycontroller.AuthInfo;
import com.autel.sdk.flycontroller.AutelFlyController;
import com.autel.sdk.flycontroller.Evo2FlyController;
import com.autel.sdk.product.BaseProduct;
import com.autel.sdk.product.Evo2Aircraft;
import com.autel.sdksample.base.BaseActivity;
import com.autel.util.log.AutelLog;


public abstract class RtkBaseActivity extends BaseActivity<AutelFlyController> {
    protected final String TAG = getClass().getSimpleName();
    protected Evo2FlyController flyController;
    protected AuthInfo authInfo;
    public  String GGA = "$GPGGA,000001,3112.518576,N,12127.901251,E,1,8,1,0,M,-32,M,3,0*4B";

    @Override
    protected AutelFlyController initController(BaseProduct product) {
        flyController = ((Evo2Aircraft) product).getFlyController();
        getGGA();
        return flyController;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("RTK测试");
    }

    void getGGA(){
        if(null != flyController)
            flyController.setRtkGGAListener( new CallbackWithOneParam<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                GGA = new String(bytes);
                AutelLog.debug_i("RTK", "onSuccess data.len:" + GGA);
//                GGA = "$GPGGA,054514.000,2338.5260,N,11501.9686,E,1,7,1.27,89.2,M,-2.3,M,,*7F";
            }

            @Override
            public void onFailure(AutelError autelError) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != flyController)
        flyController.setRtkGGAListener(null);
    }
}
