package com.autel.sdksample.base.camera;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autel.common.CallbackWithTwoParams;
import com.autel.common.camera.CameraProduct;
import com.autel.common.error.AutelError;
import com.autel.common.video.OnRenderFrameInfoListener;
import com.autel.sdk.camera.AutelBaseCamera;
import com.autel.sdk.camera.AutelCameraManager;
import com.autel.sdk.product.BaseProduct;
import com.autel.sdk.widget.AutelCodecView;
import com.autel.sdksample.R;
import com.autel.sdksample.TestApplication;
import com.autel.sdksample.base.camera.fragment.CameraNotConnectFragment;
import com.autel.sdksample.base.camera.fragment.CameraR12Fragment;
import com.autel.sdksample.base.camera.fragment.CameraXB015Fragment;
import com.autel.sdksample.base.camera.fragment.CameraXT701Fragment;
import com.autel.sdksample.base.camera.fragment.CameraXT705Fragment;
import com.autel.sdksample.base.camera.fragment.CameraXT706Fragment;
import com.autel.sdksample.base.camera.fragment.CameraXT709Fragment;


public class CameraActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    TextView cameraType;
    TextView cameraLogOutput;
    AutelBaseCamera currentCamera;
    AutelCameraManager autelCameraManager;
    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String text = (String) msg.obj;
            if (null != cameraLogOutput) {
                cameraLogOutput.setText(text);
            }
        }
    };
    AutelCodecView codecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Camera");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_camera);
        cameraType = (TextView) findViewById(R.id.camera_type);
        cameraLogOutput = (TextView) findViewById(R.id.camera_log_output);
        BaseProduct product = ((TestApplication) getApplicationContext()).getCurrentProduct();
        if (null != product) {
            autelCameraManager = product.getCameraManager();
        }
        changePage(CameraNotConnectFragment.class);
//        initListener();
        codecView = (AutelCodecView) findViewById(R.id.codecView);

        findViewById(R.id.camera_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSize();
            }
        });

    }

    boolean fullScreen = true;
    private void changeSize() {
        RelativeLayout.LayoutParams params = fullScreen ? new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) :
                new RelativeLayout.LayoutParams(300, 150);
        codecView.setLayoutParams(params);
        fullScreen = !fullScreen;

//        Log.v("videoDecodeBug","playstatus "+ CodecManager.getInstance().getPlayStatus());
    }

    private void initListener() {
        if (null == autelCameraManager) {
            return;
        }
        autelCameraManager.setCameraChangeListener(new CallbackWithTwoParams<CameraProduct, AutelBaseCamera>() {
            @Override
            public void onSuccess(final CameraProduct data1, final AutelBaseCamera data2) {
                Log.v(TAG, "initListener onSuccess connect " + data1);
                if (currentCamera == data2) {
                    return;
                }
                currentCamera = data2;
                cameraType.setText(data1.toString());
                switch (data1) {
                    case R12:
                        changePage(CameraR12Fragment.class);
                        break;
                    case XB015:
                        changePage(CameraXB015Fragment.class);
                        break;
                    case XT701:
                        changePage(CameraXT701Fragment.class);
                        break;
                    case XT705:
                        changePage(CameraXT705Fragment.class);
                        break;
                    case XT706:
                        changePage(CameraXT706Fragment.class);
                        break;
                    case XT709:
                        changePage(CameraXT709Fragment.class);
                        break;

                    default:
                        changePage(CameraNotConnectFragment.class);
                }

            }

            @Override
            public void onFailure(AutelError error) {
                Log.v(TAG, "initListener onFailure error " + error.getDescription());
                cameraType.setText("currentCamera connect broken  " + error.getDescription());
            }
        });
    }

    boolean state;

    public void onResume() {
        super.onResume();
        initListener();
        state = false;
        codecView.resume();
        codecView.setOnRenderFrameInfoListener(new OnRenderFrameInfoListener() {
            @Override
            public void onRenderFrameTimestamp(long pts) {
                if (!state) {
                    state = true;
                    codecView.setOverExposure(false, R.drawable.expo1280);
                }
            }

            @Override
            public void onRenderFrameSizeChanged(int width, int height) {

            }
        });
        codecView.setOverExposure(false, R.drawable.expo1280);
    }

    public void onPause() {
        super.onPause();
        if (null == autelCameraManager) {
            return;
        }
        codecView.pause();
        codecView.setOnRenderFrameInfoListener(null);
        autelCameraManager.setCameraChangeListener(null);
    }

    private void changePage(Class page) {
        try {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, (Fragment) page.newInstance()).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AutelBaseCamera getCurrentCamera() {
        return currentCamera;
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void logOut(String log) {
        Log.v(TAG, log);
        Message msg = handler.obtainMessage();
        msg.obj = log;
        handler.sendMessage(msg);
    }

}
