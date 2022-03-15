package com.autel.sdksample.xstar;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.autel.sdksample.R;
import com.autel.sdksample.base.CodecActivity;
import com.autel.sdksample.base.album.AlbumActivity;
import com.autel.sdksample.base.camera.CameraActivity;
import com.autel.sdksample.base.mission.MissionActivity;

/**
 * Created by A16343 on 2017/9/5.
 */

public class XStarLayout {
    private View mLayout;
    private Context mContext;

    public XStarLayout(Context context) {
        mContext = context;
        mLayout = View.inflate(mContext, R.layout.activity_xstar, null);
        initUI();
    }

    public View getLayout() {
        return mLayout;
    }

    private void initUI() {
        mLayout.findViewById(R.id.rcTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, XStarRemoteControllerActivity.class));
            }
        });
        mLayout.findViewById(R.id.fcTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, XStarFlyControllerActivity.class));
            }
        });
        mLayout.findViewById(R.id.cameraTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, CameraActivity.class));
            }
        });
        mLayout.findViewById(R.id.codecTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, CodecActivity.class));
            }
        });
        mLayout.findViewById(R.id.DSPTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, XStarDspActivity.class));
            }
        });
        mLayout.findViewById(R.id.missionTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, MissionActivity.class));
            }
        });
        mLayout.findViewById(R.id.BatteryTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, XStarBatteryActivity.class));
            }
        });
        mLayout.findViewById(R.id.GimbalTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, XStarGimbalActivity.class));
            }
        });
        /*/
        mLayout.findViewById(R.id.AlbumTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, TestActivity.class));
            }
        });
        /*/
        mLayout.findViewById(R.id.AlbumTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, AlbumActivity.class));
            }
        });
        //*/
    }
}
