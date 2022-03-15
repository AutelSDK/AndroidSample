package com.autel.sdksample;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.autel.AutelNet2.dsp.controller.DspRFManager2;
import com.autel.common.dsp.evo.AircraftRole;
import com.autel.common.product.AutelProductType;
import com.autel.sdk.Autel;
import com.autel.sdk.ProductConnectListener;
import com.autel.sdk.product.BaseProduct;
import com.autel.sdksample.evo.G2Layout;
import com.autel.sdksample.evo2.EVO2Layout;
import com.autel.sdksample.premium.XStarPremiumLayout;
import com.autel.sdksample.util.FileUtils;
import com.autel.sdksample.xstar.XStarLayout;
import com.autel.video.NetWorkProxyJni;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.autel.internal.sdk.AutelBaseApplication.getAppContext;


public class ProductActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private int index;
    private long timeStamp;
    static AtomicBoolean hasInitProductListener = new AtomicBoolean(false);
    private String fileConfig1 = "/sdcard/anddev/autel288_7.cfg";
    private String fileConfig2 = "/sdcard/anddev/autel288_7_final.weights";
    private String fileConfig3 = "/sdcard/anddev/autel13.cfg";
    private String fileConfig4 = "/sdcard/anddev/autel13.backup";
    private AutelProductType currentType = AutelProductType.UNKNOWN;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        setContentView(createView(AutelProductType.EVO_2));
        Log.v("productType", "ProductActivity onCreate ");
        //*/
        Autel.setProductConnectListener(new ProductConnectListener() {
            @Override
            public void productConnected(BaseProduct product) {
                Log.v("productType", "product " + product.getType());
                currentType = product.getType();
                setTitle(currentType.toString());
                setContentView(createView(currentType));
                /**
                 * 避免从WiFi切换到USB时，重新弹起MainActivity界面
                 * Avoid MainAcitivity when switch from wifi to USB
                 */
                hasInitProductListener.compareAndSet(false, true);

                BaseProduct previous = ((TestApplication) getApplicationContext()).getCurrentProduct();
                ((TestApplication) getApplicationContext()).setCurrentProduct(product);
                /**
                 * 如果产品类型发生变化，退出到该界面下
                 * If product type has changed, go back to this Activity
                 */
                if (null != previous) {
                    if (previous.getType() != product.getType()) {
                        startActivity(new Intent(getApplicationContext(), ProductActivity.class));
                    }
                }
            }


            @Override
            public void productDisconnected() {
                Log.v("productType", "productDisconnected ");
                currentType = AutelProductType.UNKNOWN;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setTitle(currentType.toString());
                    }
                });
            }
        });
        /*/
        productSelector.productConnected(AutelProductType.X_STAR);
        //*/

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
        }
//        FileUtils.Initialize(getAppContext(),fileConfig1,"autel288_7.cfg");
//        FileUtils.Initialize(getAppContext(),fileConfig2,"autel288_7_final.weights");
//        FileUtils.Initialize(getAppContext(),fileConfig3,"autel13.cfg");
//        FileUtils.Initialize(getAppContext(),fileConfig4,"autel13.backup");
        //only for 7.9 pad need to setup
        NetWorkProxyJni.setType(1);
    }

    private View createView(AutelProductType productType) {
        switch (productType) {
            case X_STAR:
                return new XStarLayout(this).getLayout();
            case EVO:
                return new G2Layout(this).getLayout();
            case EVO_2:
                return new EVO2Layout(this).getLayout();
            case PREMIUM:
                return new XStarPremiumLayout(this).getLayout();

        }
        return new EVO2Layout(this).getLayout();
    }

    public void setFrequency(View view) {
        DspRFManager2.getInstance().bingAircraftToRemote(AircraftRole.SLAVER);
    }
    public void setMasterFequency(View view) {
        DspRFManager2.getInstance().bingAircraftToRemote(AircraftRole.MASTER);
    }

    public void onResume() {
        super.onResume();
        setTitle(currentType.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (needExit) {
            hasInitProductListener.set(false);
        }
    }

    public void finish() {
        super.finish();
        Log.v("productType", "activity finish ");
    }

    private boolean needExit = false;

    public void onBackPressed() {
        if (System.currentTimeMillis() - timeStamp < 1500) {
            needExit = true;
            super.onBackPressed();
        } else {
            timeStamp = System.currentTimeMillis();
        }
    }

    public static void receiveUsbStartCommand(Context context) {
        if (hasInitProductListener.compareAndSet(false, true)) {
            Intent i = new Intent();
            i.setClass(context, ProductActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
