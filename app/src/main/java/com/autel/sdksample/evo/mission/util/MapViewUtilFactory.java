package com.autel.sdksample.evo.mission.util;

import android.content.Context;
import android.content.pm.PackageManager;

import com.autel.internal.sdk.util.AutelSharedPreferencesUtils;
import com.autel.sdksample.evo.mission.bean.MapConstant;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by A15387 on 2017/9/1.
 */

@SuppressWarnings("DefaultFileTemplate")
public class MapViewUtilFactory {


        public static MapViewUtilImpl instanceMapViewUtil(Context mContext){
            final int playStatus = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(mContext);
            final boolean isValid = playStatus == ConnectionResult.SUCCESS;
            boolean isUseAmap = AutelSharedPreferencesUtils.getSpValueForBoolean(mContext, MapConstant.USE_AMAP_SP_KEY,false);
            final String googleServices = GoogleApiAvailability.GOOGLE_PLAY_SERVICES_PACKAGE;
            if (isApkInstalled(mContext, googleServices) && isValid && !isUseAmap) {
                return  new GmapViewUtilImpl(mContext);
            } else {
                return new AmapViewUtilImpl(mContext);
            }
        }


    /**
     *
     * 判断是否安装是否安装
     *
     * @param context
     * @param appName
     *            应用的包名。
     * @return true 安装 false 未安装
     */
    public static boolean isApkInstalled(Context context, String appName) {

        try {
            context.getPackageManager ().getPackageInfo (appName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
