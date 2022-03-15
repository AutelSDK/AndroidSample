package com.autel.sdksample.evo.mission.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.autel.common.product.AutelProductType;
import com.autel.sdksample.R;
import com.autel.sdksample.evo.mission.bean.AutelGPSLatLng;
import com.autel.sdksample.evo.mission.bean.MapConstant;
import com.autel.sdksample.evo.mission.bean.MissionOrbitdBean;
import com.autel.sdksample.evo.mission.bean.MissionWaypointBean;
import com.autel.sdksample.evo.mission.bean.OrbitAdvanceDataBean;
import com.autel.sdksample.evo.mission.bean.PhoneGPS;
import com.autel.sdksample.evo.mission.bean.WaypointAdvanceDataBean;
import com.autel.sdksample.evo.mission.util.AutelConfigManager;
import com.autel.sdksample.evo.mission.util.MapModelImpl;
import com.autel.sdksample.evo.mission.util.MapViewUtilFactory;
import com.autel.sdksample.evo.mission.util.MapViewUtilImpl;
import com.autel.sdksample.evo.mission.widge.AutelCustomGestureOverlayView;
import com.autel.sdksample.evo.mission.widge.GestureCallbacks;

import java.util.List;


/**
 * Created by A15387 on 2017/8/31.
 */

@SuppressWarnings("DefaultFileTemplate")
public class MapFragment extends Fragment {
    public static final String TAG = "MapFragment";

    private MapViewUtilImpl mapViewUtil;
    private boolean isMapReadyState = false;
    private boolean isWaitForMapReadyState = false;
    private boolean isCompassOpen = false;

    AutelCustomGestureOverlayView mGestureView;

    private static final int MIN_DISTANCE = 0;
    private static final int MIN_TIME = 500;


    private static int curOrientation;
    private OrientationEventListener orientationDetector;
    private GpsStatus.Listener mGPSListener;
    private LocationListener mLocationListener;
    private String currentLocationProvider;
    private ViewGroup contentView;
    private List<String> coordinatedatas;

    private OnMapFragmentListener onMapFragmentListener;
    private LocationListener netLocationListener;
    private int curMapType = MapConstant.MAP_TYPE_SATELLITE;

    public void connect(AutelProductType type) {
        if(type != AutelProductType.UNKNOWN){
            /*if(mRequestManager != null){
                mRequestManager.enableLongListener(true);
            }*/
        }
    }

    public void disconnect() {
        /*if(mRequestManager != null){
            mRequestManager.enableLongListener(false);
        }*/
    }

    public interface OnMapFragmentListener{
        void onResumed();
    }

    public void setOnMapFragmentListener(OnMapFragmentListener onMapFragmentListener) {
        this.onMapFragmentListener = onMapFragmentListener;
    }

    private static class MyLocationListener implements LocationListener {


        public void removeMapRequest(){

        }

        public MyLocationListener(){

        }
        @Override
        public void onLocationChanged(Location location) {
            PhoneGPS.setPhoneGPS(location);
           /* if(mapRequest != null){
                mapRequest.setPhoneLocation(location);
                mapRequest.initMapToPhoneLocation();
                mapRequest.updateFollowLocation(location);
            }*/
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    private static class NetLocationListener implements LocationListener{


        public void removeMapRequest(){
        }

        public NetLocationListener(){
        }

        @Override
        public void onLocationChanged(Location location) {
            /*if(mapRequest != null){
                mapRequest.initMapToNetLocation(location);
            }*/
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        contentView = (ViewGroup) inflater.inflate(R.layout.fragment_map,null);
        FrameLayout map_container = (FrameLayout) contentView.findViewById(R.id.map_container);
        map_container.addView(mapViewUtil.getMapView());
        curOrientation = getDisplayRotate();
        Bundle bundle = getArguments();
        mGestureView = (AutelCustomGestureOverlayView) contentView.findViewById(R.id.map_customgestureoverlayview);
        mGestureView.init(getActivity());
        return contentView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapViewUtil = MapViewUtilFactory.instanceMapViewUtil(AutelConfigManager.instance().getAppContext());
        mapViewUtil.initMap(new OnMapReadyCallBack() {
            @Override
            public void onMapReady() {
                isMapReadyState = true;
                if(isWaitForMapReadyState){
                    /*if(mRequestManager != null){
                        initMapListener();
                    }*/
                }
                registerNetLocationGps();
            }
        });
        mapViewUtil.onCreate(savedInstanceState);
        initPhoneGPS();
        registerLowAccuracyGps();
        registerNetLocationGps();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MapConstant.LOCATION_PERMISSION_RESULT_CODE:
                //Check to see if permission was granted
                boolean granted = false;
                for(int result : grantResults){
                    if(result == PackageManager.PERMISSION_GRANTED){
                        granted = true;
                        break;
                    }
                }

                //If permission was granted start tracking gps
                if(granted){
                    if(permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)){
                        registerHighAccuracyGps();
                    }else{
                        registerLowAccuracyGps();
                    }
                }
                break;
        }
    }

    private int getDisplayRotate() {
        if(getActivity() == null){
            return 0;
        }
        return getActivity().getWindowManager().getDefaultDisplay().getRotation();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(null != onMapFragmentListener){
            onMapFragmentListener.onResumed();
            onMapFragmentListener = null;
        }

        mapViewUtil.onResume();
        /*if(mRequestManager != null){
            if(isMapReadyState){
                initMapListener();
            }else{
                isWaitForMapReadyState = true;
            }
        }*/
        initPhoneGPS();
        Activity activity = getActivity();
        registerHighAccuracyGps();
        registerNetLocationGps();
    }

    private void initMapListener() {
        mapViewUtil.getMapModel().setMarkerDragListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapViewUtil.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapViewUtil.onDestroy();
        removeGPSListeners();
    }

    public interface OnMapReadyCallBack{
        void onMapReady();
    }

    public void setDroneMarker(AutelGPSLatLng droneLocation, Bitmap bitmap) {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            mapModel.setDroneMarker(droneLocation,bitmap);
        }
    }

    public void updateFlyRoute(AutelGPSLatLng droneLocation) {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            mapModel.updateFlyRoute(droneLocation);
        }
    }

    public void updateDroneYaw(double yaw, boolean isCompassEnable) {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            mapModel.updateDroneYaw(yaw,isCompassEnable);
        }
    }

    public void setHomeMarker(AutelGPSLatLng homeLocation, Bitmap bitmap) {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            mapModel.setHomeMarker(homeLocation,bitmap);
        }
    }

    public void setPhoneLocation(AutelGPSLatLng phoneLocation, Bitmap bitmap) {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            mapModel.setPhoneLocation(phoneLocation,bitmap);
        }
    }

    public void initMapToPhoneLocation() {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            mapModel.initMapToPhoneLocation();
        }
    }

    public void changeLocationToDrone() {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            mapModel.changeLocationToDrone();
        }
    }

    public void changeLocationToHome() {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            mapModel.changeLocationToHome();
        }
    }

    public void clearFlyRoute() {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            mapModel.clearFlyRoute();
        }
    }

    public void updateFollowLine() {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            mapModel.updateFollowLine();
        }
    }

    public void setOnMapClickListener(int mapClickWay) {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            mapModel.setOnMapClickListener(mapClickWay);
        }
    }

    public boolean setMissionLimitCircle(int circleRadius) {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            return mapModel.setMissionLimitCircle(circleRadius);
        }
        return false;
    }

    public void removeOrbitPoint() {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            mapModel.removeOrbitPoint();
        }
    }

    public void clearOrbitMode() {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            mapModel.clearOrbitMode();
        }
    }

    public boolean isOrbitAdd() {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            return  mapModel.isOrbitAdd();
        }
        return false;
    }

    public boolean addOrbitFromDrone(AutelGPSLatLng droneLocation) {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            return mapModel.addOrbitFromDrone(droneLocation);
        }
        return false;
    }

    public AutelGPSLatLng getOrbitMarkerPosition() {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            return mapModel.getOrbitMarkerPosition();
        }
        return null;
    }

    public void setGestureCallbacks(GestureCallbacks gestureCallback) {
        if(gestureCallback != null){
            mGestureView.addGestureCallbacks(gestureCallback);
            mGestureView.enableGesture();
        }else {
            mGestureView.addGestureCallbacks(null);
            mGestureView.disableGesture();
        }
    }

    public void clearWaypoints() {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        mapModel.clearWaypoints();
    }

    public void projectMarker(List<AutelGPSLatLng> path) {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        mapModel.projectMarker(path);
    }

    public void setWaypointsListener(MapModelImpl.OnWaypointsChangeListener waypointsListener) {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        mapModel.setWaypointsListener(waypointsListener);
    }

    public void saveAlreadyUpdateFlyRoute() {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            mapModel.saveAlreadyUpdateFlyRoute();
        }
    }

    public MissionWaypointBean getMissionWaypointBean() {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
           return mapModel.getMissionWaypointBean();
        }
        return null;
    }

    public MissionOrbitdBean getMissionOrbitBean() {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            return mapModel.getMissionOrbitBean();
        }
        return null;
    }

    public void deleteJustProjectWaypoint() {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            mapModel.deleteJustProjectWaypoint();
        }
    }

    public void addWaypointUseDroneLocation() {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            mapModel.addWaypointUseDroneLocation();
        }
    }

    public void waypointDelete() {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            mapModel.waypointDelete();
        }
    }

    public void setWaypointAdvanceData(WaypointAdvanceDataBean waypointAdvanceData) {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            mapModel.setWaypointAdvanceData(waypointAdvanceData);
        }
    }

    public void setCurWaypointDataChange(WaypointAdvanceDataBean waypointAdvanceDataBean, int whichItem) {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            mapModel.setCurWaypointDataChange(waypointAdvanceDataBean,whichItem);
        }
    }

    public void showWaypointFileOnMap(MissionWaypointBean missionWaypointBean) {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            mapModel.showWaypointFileOnMap(missionWaypointBean);
        }
    }

    public void setCurOrbitDataChange(OrbitAdvanceDataBean orbitAdvanceDataBean, int type) {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            mapModel.setCurOrbitDataChange(orbitAdvanceDataBean,type);
        }
    }

    public void setWaypointProjectDataChange(WaypointAdvanceDataBean waypointProjectDataChange) {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if (mapModel != null) {
            mapModel.setWaypointProjectDataChange(waypointProjectDataChange);
        }
    }

    public void initMapToNetLocation(AutelGPSLatLng netLocation) {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            if(mapModel.initMapToNetLocation(netLocation)){
                removeNetGPSListeners();
            }
        }
    }

    public WaypointAdvanceDataBean getLastWaypointData() {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            return mapModel.getLastWaypointData();
        }
        return null;
    }

    public WaypointAdvanceDataBean getNextWaypointData() {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            return mapModel.getNextWaypointData();
        }
        return null;
    }

    public void clearFollowMapData() {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            mapModel.clearFollowLine();
        }
    }

    public void changeLocationToMe() {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            mapModel.changeLocationToMe();
        }
    }

    public void clearLimitCircle() {
        MapModelImpl mapModel = mapViewUtil.getMapModel();
        if(mapModel != null){
            mapModel.removeLimitCircle();
        }
    }

    private final ContentObserver contentObserver = new ContentObserver(null) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            registerLowAccuracyGps();
        }
    };

    private void initPhoneGPS() {

        getActivity().getContentResolver().registerContentObserver(Settings.Secure.getUriFor(Settings.System.LOCATION_PROVIDERS_ALLOWED), false, contentObserver);

        mGPSListener = new GpsStatus.Listener() {
            @Override
            public void onGpsStatusChanged(int event) {
                switch (event) {
                    case GpsStatus.GPS_EVENT_STARTED:
                        break;

                    case GpsStatus.GPS_EVENT_FIRST_FIX:
                        break;
                }
            }
        };

    }

    private void registerLowAccuracyGps() {
        if(isLocationPermissionAvailable(Manifest.permission.ACCESS_COARSE_LOCATION)){
            String provider = getLowAccuracyProvider();
            if(!provider.equals(currentLocationProvider)){
                currentLocationProvider = provider;
                requestLocationUpdatesForProvider(currentLocationProvider);
            }
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestLocationPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
        }
    }

    private void registerNetLocationGps(){

        if(isLocationPermissionAvailable(Manifest.permission.ACCESS_COARSE_LOCATION)){
            String provider = ((LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE)).getProvider(LocationManager.NETWORK_PROVIDER).getName();
            requestLastLocationUpdatesForProvider(provider);
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestLocationPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
        }
    }

    private void registerHighAccuracyGps(){
        if(isLocationPermissionAvailable(Manifest.permission.ACCESS_FINE_LOCATION)){
            String provider = getHighAccuracyProvider();
            if(!provider.equals(currentLocationProvider)){
                currentLocationProvider = provider;
                requestLocationUpdatesForProvider(currentLocationProvider);
            }
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestLocationPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }
    }

    private String getLowAccuracyProvider() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_LOW);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return ((LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE)).getBestProvider(criteria,true);
    }

    private String getHighAccuracyProvider(){
        return LocationManager.GPS_PROVIDER;
    }

    private boolean isLocationPermissionAvailable(String locationAccessType){
        return ActivityCompat.checkSelfPermission(getActivity(), locationAccessType) == PackageManager.PERMISSION_GRANTED;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestLocationPermission(String locationAccessType){
        requestPermissions(new String[]{locationAccessType}, MapConstant.LOCATION_PERMISSION_RESULT_CODE);
    }

    private void requestLocationUpdatesForProvider(String provider){
        LocationManager locationManager = (LocationManager)AutelConfigManager.instance().getAppContext().getSystemService(Context.LOCATION_SERVICE);
        if(locationManager != null && provider != null){
            if(locationManager.isProviderEnabled(provider)){
                try{
                    if(mLocationListener != null){
                        locationManager.removeUpdates(mLocationListener);//In case it's already registered for updates
                        locationManager.requestLocationUpdates(provider, MIN_TIME, MIN_DISTANCE, mLocationListener);
                    }
                }catch (SecurityException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void requestLastLocationUpdatesForProvider(String provider){
        LocationManager locationManager = (LocationManager)AutelConfigManager.instance().getAppContext().getSystemService(Context.LOCATION_SERVICE);
        if(locationManager != null && provider != null){
            if(locationManager.isProviderEnabled(provider)){
                try{

                }catch (SecurityException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void removeGPSListeners(){
        getActivity().getContentResolver().unregisterContentObserver(contentObserver);

        LocationManager locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        if(locationManager != null){
            locationManager.removeGpsStatusListener(mGPSListener);
            try{
                locationManager.removeUpdates(mLocationListener);
                locationManager.removeUpdates(netLocationListener);
                ((MyLocationListener)mLocationListener).removeMapRequest();
                ((NetLocationListener)netLocationListener).removeMapRequest();
            }catch (SecurityException e){
                e.printStackTrace();
            }
        }
    }

    private void removeNetGPSListeners(){
        LocationManager locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        if(locationManager != null){
            try{
                locationManager.removeUpdates(netLocationListener);
                ((NetLocationListener)netLocationListener).removeMapRequest();
            }catch (SecurityException e){
                e.printStackTrace();
            }
        }
    }

}
