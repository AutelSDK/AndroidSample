package com.autel.sdksample.evo2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.autel.internal.video.core.RtmpPushHelper;
import com.autel.sdksample.R;
import com.autel.sdksample.util.ThreadUtils;
import com.autel.util.log.AutelLog;
import com.autel.video.engine.VideoIpConst;

import org.apache.http.impl.cookie.DateUtils;

import java.util.Date;

public class LivePushActivity extends AppCompatActivity implements RtmpPushHelper.OnRtmpPushListener {

    private static final String TAG = "LivePush-ddl";

    private TextView mTvMessage1;
    private TextView mTvMessage2;
    private TextView mTvMessage3;

    private static final int REQUEST_CODE = 317;
    private RtmpPushHelper mRtmpPushHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_push);
        mTvMessage1 = (TextView) findViewById(R.id.tv_message1);
        mTvMessage2 = (TextView) findViewById(R.id.tv_message2);
        mTvMessage3 = (TextView) findViewById(R.id.tv_message3);
        mRtmpPushHelper = new RtmpPushHelper();
        mRtmpPushHelper.init();
        mRtmpPushHelper.setOnRtmpPushListener(this);
        if (!isGrantAudioPermission()) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_CODE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRtmpPushHelper != null) {
            mRtmpPushHelper.destroy();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            Toast.makeText(this, isGrantAudioPermission() ? "已获取录音权限, 点击start push按钮开始推流" : "获取录音权限失败", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isGrantAudioPermission() {
        return ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
    }

    public void startLivePush(View view) {
        mTvMessage3.setText(VideoIpConst.getRtspHostAddr());
        ThreadUtils.runOnNonUIthread(() -> {
            if (mRtmpPushHelper != null) {
                if (ActivityCompat.checkSelfPermission(LivePushActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(LivePushActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_CODE);
                    return;
                }
                final boolean startRtmpPush = mRtmpPushHelper.startRtmpPush(LivePushActivity.this.getApplication(), mRtmpUrl);
                ThreadUtils.runOnUiThread(() -> mTvMessage1.setText("rtmpStart: " + startRtmpPush+" url:"+mRtmpUrl));
            }
        });
    }

    //    private String mRtmpUrl = "rtmp://live-push.bilivideo.com/live-bvc/?streamname=live_652190260_63475914&key=a63ce429871800d968ccc1b5306957e7&schedule=rtmp&pflag=1";
//    private String mRtmpUrl = "rtmp://live-push.bilivideo.com/live-bvc/?streamname=live_2928967_83731862&key=b39fc9d32e8aa37745bf94f0a00d5d34&schedule=rtmp&pflag=1";

//    private String mRtmpUrl = "rtmp://a.rtmp.youtube.com/live2/vkzh-1udz-ak4j-mscv-29s4";
//    private String mRtmpUrl = "rtmp://a.rtmp.youtube.com/live2/rcvy-tasb-ej66-yquk-arpv";
//    private String mRtmpUrl = "rtmp://a.rtmp.youtube.com/live2/aw1s-t077-vaj4-hae4-b27f";//"rtmp://a.rtmp.youtube.com/live2/rcvy-tasb-ej66-yquk-arpv";//直播推流地址，记住录音权限一定要打开
    private String mRtmpUrl = "rtmp://a.rtmp.youtube.com/live2/83f9-vgme-8hf2-gqkr-1st5";//"rtmp://a.rtmp.youtube.com/live2/rcvy-tasb-ej66-yquk-arpv";//直播推流地址，记住录音权限一定要打开
    public void stopLivePush(View view) {
        ThreadUtils.runOnNonUIthread(() -> {
            if (mRtmpPushHelper != null) {
                final boolean stopRtmpPush = mRtmpPushHelper.stopRtmpPush();
                ThreadUtils.runOnUiThread(() -> mTvMessage1.setText("rtmpStop: " + stopRtmpPush));
            }
        });
    }

    @Override
    public void onRtmpPushSuccess() {
        ThreadUtils.runOnUiThread(() -> Toast.makeText(LivePushActivity.this, "start live push", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onRtmpPushFailure() {
        ThreadUtils.runOnUiThread(() -> Toast.makeText(LivePushActivity.this, "live push failure", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onRtmpPushing(int i, int i1, int i2) {
        AutelLog.d(TAG, "===>>>onPlayerRtmpVideoAttr, bit rate: " + i + ", FPS: " + i1 + ", Audio bit rate: " + i2);
        ThreadUtils.runOnUiThread(() -> mTvMessage2.setText(DateUtils.formatDate(new Date()) + ", bit rate: " + i + ", FPS: " + i1 + ", Audio bit rate: " + i2));
    }

}