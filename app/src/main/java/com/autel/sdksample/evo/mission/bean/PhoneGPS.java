package com.autel.sdksample.evo.mission.bean;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;


/**
 * Created by A15387 on 2016/5/30.
 */
@SuppressWarnings("DefaultFileTemplate")
public class PhoneGPS {

    private static Location mLocation;

    public static void setPhoneGPS(Location location){
        mLocation = location;
    }

    public static Location getLocation(){
        return mLocation;
    }

    /**
     * 判断手机GPS是否打开
     * @param context
     * @return
     */
    public static boolean checkPhoneGpsIsValid(Context context){
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if(locationManager == null){
            return  false;
        }
        try {
            return  locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }
    }

}
