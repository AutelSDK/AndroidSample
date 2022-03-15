package com.autel.sdksample.base.mission;

import java.io.Serializable;

/*
 * autel的经纬度
 */
public class AutelLatLng implements Serializable {
    /**
     * 
     */
	public double latitude;// LatLng 经度
	public double longitude;// LatLng 纬度
	private float rotate = 0;
	private String time;

	public AutelLatLng(double latd, double longd) {
		set(latd,longd);
	}
	
	public AutelLatLng(AutelLatLng all){
		set(all.latitude,all.longitude);
	}
	public void set(double latitude, double longitude){
		this.latitude = latitude; 
		this.longitude = longitude;
	}
	
	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	@Override
	public boolean equals(Object o) {
		if(o == null){
			return false;
		}
		boolean isEqual = false;
		AutelLatLng all = (AutelLatLng) o;
		if(this.latitude == all.latitude && this.longitude == all.longitude){
			isEqual = true;
		}
		return isEqual;
	}

    public void setRotate(float rotate){

        this.rotate = rotate;
        
    }

    public float getRotate(){

        return rotate;
        
    }

    public void setTime(String time){

        this.time = time;
        
    }

    public String getTime(){

        return time;
        
    }

	public static boolean isPointValid(AutelLatLng autelLatLng) {
		return autelLatLng != null && autelLatLng.getLatitude() != 0 && autelLatLng.getLongitude() != 0;
	}
	
}
