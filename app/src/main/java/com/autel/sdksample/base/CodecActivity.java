package com.autel.sdksample.base;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.autel.common.error.AutelError;
import com.autel.sdk.product.BaseProduct;
import com.autel.sdk.video.AutelCodec;
import com.autel.sdk.video.AutelCodecListener;
import com.autel.sdk.widget.AutelCodecView;
import com.autel.sdksample.R;

public class CodecActivity extends BaseActivity<AutelCodec> {

    private RelativeLayout content_layout;
    private boolean isCodecing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Codec");
    }

    @Override
    protected AutelCodec initController(BaseProduct product) {
        return product.getCodec();
    }

    @Override
    protected int getCustomViewResId() {
        return R.layout.ac_codec;
    }

    @Override
    protected void initUi() {
        content_layout = (RelativeLayout) findViewById(R.id.content_layout);

        isCodecing = false;
        initClick();
    }

    public void testAutelCodecView(View v) {

    }

    /**
     * Use AutelCodecView to display the video stream from camera simply.
     */
    private void initClick() {
        findViewById(R.id.testAutelCodecView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCodecing = true;

                final AutelCodecView autelCodecView = new AutelCodecView(CodecActivity.this);
                content_layout.setOnClickListener(null);
                content_layout.setVisibility(View.VISIBLE);
                content_layout.addView(autelCodecView);

                LinearLayout btn_layout = new LinearLayout(CodecActivity.this);
                btn_layout.setOrientation(LinearLayout.VERTICAL);

                Button btn_exp = new Button(CodecActivity.this);
                btn_exp.setText("Exposure");
                btn_exp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        autelCodecView.setOverExposure(!autelCodecView.isOverExposureEnabled(), R.mipmap.expo2560);
//                        startActivity(new Intent(CodecActivity.this, XStarRemoteControllerActivity.class));
                    }
                });

                Button btn_pause = new Button(CodecActivity.this);
                btn_pause.setText("Pause");
                btn_pause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        autelCodecView.pause();
                    }
                });

                Button btn_resume = new Button(CodecActivity.this);
                btn_resume.setText("Resume");
                btn_resume.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        autelCodecView.resume();
                    }
                });

                Button btn_checkOverExposureEnabled = new Button(CodecActivity.this);
                btn_checkOverExposureEnabled.setText("isOverExposureEnabled");
                btn_checkOverExposureEnabled.setAllCaps(false);
                btn_checkOverExposureEnabled.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(CodecActivity.this, "isOverExposureEnabled == " + autelCodecView.isOverExposureEnabled(), Toast.LENGTH_SHORT).show();
                    }
                });

                btn_layout.addView(btn_exp);
                btn_layout.addView(btn_pause);
                btn_layout.addView(btn_resume);
                btn_layout.addView(btn_checkOverExposureEnabled);

                content_layout.addView(btn_layout);
            }
        });

        /**
         * The H264 video stream data for developer to deal with
         */
        findViewById(R.id.testAutelCodec).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCodecing = true;

                final TextView logTV = new TextView(CodecActivity.this);
                AutelCodecView autelCodecView = new AutelCodecView(CodecActivity.this);
                content_layout.setOnClickListener(null);
                content_layout.setVisibility(View.VISIBLE);
                content_layout.addView(autelCodecView);
                if (null != mController) {
                    mController.setCodecListener(new AutelCodecListener() {
                        @Override
                        public void onFrameStream(final byte[] videoBuffer, final boolean isIFrame, final int size, final long pts) {
                            Log.d("onFrameStream"," onFrameStream size "+size);
                            if(null == videoBuffer) return;
                            logOut("isValid == " + (videoBuffer.length == size) + "\nisIFrame == " + isIFrame + "\nsize == " + size + "\npts == " + pts);
                        }

                        @Override
                        public void onCanceled() {
//                            logTV.post(new Runnable() {
//                                @Override
//                                public void run() {
                                    logOut("onCandeled");
//                                }
//                            });
                        }

                        @Override
                        public void onFailure(final AutelError error) {
//                            logTV.post(new Runnable() {
//                                @Override
//                                public void run() {
                                    logOut(error.getDescription());
//                                }
//                            });
                        }
                    }, null);
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (isCodecing) {
            isCodecing = false;

            content_layout.removeAllViews();
            content_layout.setVisibility(View.GONE);

            if (null != mController) {
                mController.cancel();
                mController.setCodecListener(null, null);
            }

            return;
        }

        super.onBackPressed();
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
