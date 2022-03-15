package com.autel.sdksample.evo.mission.bean;

import com.autel.common.mission.AutelCoordinate3D;
import com.autel.sdksample.base.mission.AutelLatLng;
import com.autel.sdksample.base.mission.MapRectifyUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by A15387 on 2017/9/18.
 */

@SuppressWarnings("DefaultFileTemplate")
public class AutelGPSLatLng {

    private double lat;
    private double lng;

    private AutelGPSLatLng(){
    }

    private void setLat(double lat) {
        this.lat = lat;
    }

    private void setLng(double lng) {
        this.lng = lng;
    }

    public static AutelGPSLatLng createFromMap(LatLng latLng, GoogleMap googleMap){
        AutelGPSLatLng autelGPSLatLng = new AutelGPSLatLng();
        if(googleMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL){
            AutelLatLng autelLatLng = new AutelLatLng(latLng.latitude,latLng.longitude);
            AutelLatLng gpsLatLng = MapRectifyUtil.gcj2wgs(autelLatLng);
            autelGPSLatLng.setLat(gpsLatLng.getLatitude());
            autelGPSLatLng.setLng(gpsLatLng.getLongitude());
        }else{
            autelGPSLatLng.setLat(latLng.latitude);
            autelGPSLatLng.setLng(latLng.longitude);
        }
        return autelGPSLatLng;
    }

    public static AutelGPSLatLng createFromMap(com.amap.api.maps.model.LatLng latLng){
        AutelGPSLatLng autelGPSLatLng = new AutelGPSLatLng();
        AutelLatLng autelLatLng = new AutelLatLng(latLng.latitude,latLng.longitude);
        AutelLatLng gpsLatLng = MapRectifyUtil.gcj2wgs(autelLatLng);
        autelGPSLatLng.setLat(gpsLatLng.getLatitude());
        autelGPSLatLng.setLng(gpsLatLng.getLongitude());
        return autelGPSLatLng;
    }



    public static AutelGPSLatLng createFromDrone(AutelCoordinate3D coord3D){
        AutelGPSLatLng autelGPSLatLng = new AutelGPSLatLng();
        autelGPSLatLng.setLat(coord3D.getLatitude());
        autelGPSLatLng.setLng(coord3D.getLongitude());
        return autelGPSLatLng;
    }

    public double getLat4Map(boolean isAmapOrIsGoogleMapNormal){
        AutelLatLng autelLatLng = new AutelLatLng(this.lat,this.lng);
        if(!isAmapOrIsGoogleMapNormal){
            return lat;
        }
        AutelLatLng autelLatLngGcj = MapRectifyUtil.wgs2gcj(autelLatLng);
        return  autelLatLngGcj.getLatitude();
    }

    public double getLng4Map(boolean isAmapOrIsGoogleMapNormal){
        AutelLatLng autelLatLng = new AutelLatLng(this.lat,this.lng);
        if(!isAmapOrIsGoogleMapNormal){
            return lng;
        }
        AutelLatLng autelLatLngGcj = MapRectifyUtil.wgs2gcj(autelLatLng);
        return  autelLatLngGcj.getLongitude();
    }

    public double getLat4Drone(){
        return lat;
    }

    public double getLng4Drone(){
        return lng;
    }

}
