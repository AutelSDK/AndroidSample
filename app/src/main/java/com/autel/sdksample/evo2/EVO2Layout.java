package com.autel.sdksample.evo2;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.autel.sdksample.R;
import com.autel.sdksample.base.CodecActivity;
import com.autel.sdksample.base.album.AlbumActivity;
import com.autel.sdksample.base.camera.CameraActivity;
import com.autel.sdksample.base.mission.MissionActivity;
import com.autel.sdksample.evo.G2RemoteControllerActivity;
import com.autel.sdksample.evo2.mission.Evo2MissionActivity;
import com.autel.sdksample.rtk.Evo2RTKActivity;

/**
 * Created by A16343 on 2017/9/5.
 */

public class EVO2Layout {
    private View mLayout;
    private Context mContext;

    public EVO2Layout(Context context) {
        mContext = context;
        mLayout = View.inflate(mContext, R.layout.activity_evo2, null);
        initUI();
    }

    public View getLayout() {
        return mLayout;
    }

    private void initUI() {
        mLayout.findViewById(R.id.rcTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, G2RemoteControllerActivity.class));
            }
        });
        mLayout.findViewById(R.id.fcTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, Evo2FlyControllerActivity.class));
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
                mContext.startActivity(new Intent(mContext, Evo2DspActivity.class));
            }
        });
        mLayout.findViewById(R.id.missionTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, MissionActivity.class));
            }
        });

        mLayout.findViewById(R.id.evo2MissionTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, Evo2MissionActivity.class));
            }
        });

        mLayout.findViewById(R.id.BatteryTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, Evo2BatteryActivity.class));
            }
        });
        mLayout.findViewById(R.id.GimbalTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, Evo2GimbalActivity.class));
            }
        });
        mLayout.findViewById(R.id.AlbumTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, AlbumActivity.class));
            }
        });
        mLayout.findViewById(R.id.rtktest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, Evo2RTKActivity.class));
            }
        });
        mLayout.findViewById(R.id.tvPush).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, LivePushActivity.class));
            }
        });
    }
}
