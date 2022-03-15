package com.autel.sdksample.evo.mission.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


/**
 * Created by A15387 on 2016/5/17.
 */
public class MapImageUtil {

    public static Bitmap drawableToBitmap(int imgId) {
        //get the bitmap from drawable
        return BitmapFactory.decodeResource(AutelConfigManager.instance().getAppContext().getResources(),
                imgId);
    }

    public static Bitmap getDroneCombineBitmap(Bitmap drone,Bitmap droneDir){
        return Bitmap.createBitmap(drone.getWidth(),drone.getHeight()+droneDir.getHeight(),Bitmap.Config.ARGB_8888);
    }

    public static float dip2px(float paramFloat) {
        return (int)(0.5F + paramFloat * AutelConfigManager.instance().getAppContext().getResources().getDisplayMetrics().density);
    }
}
