package com.autel.sdksample.evo.mission.util;


import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.autel.common.mission.AutelCoordinate3D;
import com.autel.sdksample.R;
import com.autel.sdksample.base.mission.AutelLatLng;
import com.autel.sdksample.evo.mission.bean.AutelGPSLatLng;
import com.autel.sdksample.evo.mission.bean.MapConstant;
import com.autel.sdksample.evo.mission.bean.MissionConstant;
import com.autel.sdksample.evo.mission.bean.MissionOrbitdBean;
import com.autel.sdksample.evo.mission.bean.MissionWaypointBean;
import com.autel.sdksample.evo.mission.bean.OrbitAdvanceDataBean;
import com.autel.sdksample.evo.mission.bean.WaypointAdvanceDataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by A15387 on 2017/9/18.
 */

@SuppressWarnings("DefaultFileTemplate")
public class AMapModel implements MapModelImpl {

    private static final int[] SCALE = { 1, 5, 20, 50, 100, 200, 500, 1000, 2000,
            5000, 10000, 20000, 25000, 50000, 100000, 200000, 500000, 1000000,
            2000000, 5000000 };
    private static final int[] SCALE_EN = { 10 ,20 , 50, 100 ,200 , 500, 1000 , 2000 ,
            5280, 10560 , 26400 , 52800 , 105600,264000,528000,1056000 ,2640000,
            5280000 , 10560000
    };

    private final AMap aMap;
    private float compassRotateDegree;
    private float oldBearing;
    private MapModelImpl.OnScaleChangeListener onScaleChangeListener;
    private final Runnable onScaleIdleRun = new OnScaleIdleRun();
    private int mScaleMaxWidth;
    private int mScaleMaxHeight;

    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private Marker droneMarker;
    private Marker homeMarker;
    private Marker phoneMarker;
    private ArrayList<LatLng> recordPoints = new ArrayList<>();
    private ArrayList<Polyline> flylines = new ArrayList<>();
    private ArrayList<Marker> waypoints = new ArrayList<>();
    List<MissionWaypointBean.ParamsBean.WaypointsBean> waypointBeans = new ArrayList<>();
    private ArrayList<Polyline> waypointLines = new ArrayList<>();
    private boolean isFirstChangeToPhone = true;
    private boolean isFirstChangeToNet = true;
    private boolean isStartCameraMove;
    private boolean isMoveZoomAuto = true;
    private String curDescription;
    private int curWidth;
    private Polyline followLine;
    private Marker orbitMarker;
    private Circle limitCircle;
    private MapModelImpl.OnWaypointsChangeListener waypointsListener;
    private int clickPolylineIndex;
    private int curWaypointProjectIndex;
    private WaypointAdvanceDataBean waypointAdvanceData = new WaypointAdvanceDataBean();
    private MissionOrbitdBean missionOrbitdBean = new MissionOrbitdBean();
    private OrbitAdvanceDataBean orbitAdvanceDataBean = new OrbitAdvanceDataBean();
    private int curWaypointIndex;

    public AMapModel(AMap aMap) {
        this.aMap = aMap;
    }


    @Override
    public void resetMapRotate() {
        LatLng ll = new LatLng(TransformUtils.doubleFormat(aMap.getCameraPosition().target.latitude, 5), TransformUtils.doubleFormat(aMap.getCameraPosition().target.longitude, 5));
        CameraPosition cp = new CameraPosition(ll, aMap.getCameraPosition().zoom, aMap.getCameraPosition().tilt, 0);
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));
    }

    @Override
    public void setOnMapChangeListener(final MapModelImpl.OnMapRotateListener onMapRotateListener) {
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
            }
        });
    }

    @Override
    public void rotateMap(float degree) {
        if (aMap != null && aMap.getCameraPosition() != null && aMap.getCameraPosition().target != null) {
            LatLng ll = new LatLng(TransformUtils.doubleFormat(aMap.getCameraPosition().target.latitude, 5), TransformUtils.doubleFormat(aMap.getCameraPosition().target.longitude, 5));
            CameraPosition cp = new CameraPosition(ll, aMap.getCameraPosition().zoom,
                    aMap.getCameraPosition().tilt, degree);
            aMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));
                // 这是飞机旋转的角度是地图旋转角度与传过来无人机角度之和
            compassRotateDegree = degree;
        }
    }

    @Override
    public void setMapType(int mapType) {
        switch (mapType){
            case MapConstant.MAP_TYPE_NORMAL:
                aMap.setMapType(AMap.MAP_TYPE_NORMAL);
                break;
            case MapConstant.MAP_TYPE_SATELLITE:
                aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
                break;
            case MapConstant.MAP_TYPE_HYBRID:
                aMap.setMapType(AMap.MAP_TYPE_NIGHT);
                break;
        }
    }

    @Override
    public int getMapType() {
        int mapType = MapConstant.MAP_TYPE_NORMAL;
        switch (aMap.getMapType()){
            case AMap.MAP_TYPE_NORMAL:
                mapType = MapConstant.MAP_TYPE_NORMAL;
                break;
            case AMap.MAP_TYPE_SATELLITE:
                mapType = MapConstant.MAP_TYPE_SATELLITE;
                break;
            case AMap.MAP_TYPE_NIGHT:
                mapType = MapConstant.MAP_TYPE_HYBRID;
                break;
        }
        return mapType;
    }

    @Override
    public void setScaleChangeListener(MapModelImpl.OnScaleChangeListener onScaleChangeListener) {
        this.onScaleChangeListener = onScaleChangeListener;
    }

    @Override
    public void setDroneMarker(AutelGPSLatLng autelGPSLatLng, Bitmap droneBitmap) {
        if(droneMarker != null && checkMarkPositionChange(droneMarker,autelGPSLatLng)){
            return;
        }
        double lat = autelGPSLatLng.getLat4Map(true);
        double lng = autelGPSLatLng.getLng4Map(true);
        LatLng latLng = new LatLng(lat,lng);
        Point point = aMap.getProjection().toScreenLocation(latLng);

        if(droneMarker == null){
            droneMarker = addMarker(latLng, BitmapDescriptorFactory.fromBitmap(droneBitmap));
        }else{
            droneMarker.setPosition(latLng);
        }
    }

    private boolean checkMarkPositionChange(Marker droneMarker, AutelGPSLatLng autelGPSLatLng) {
        return DistanceUtils.distanceBetweenPointsAsFloat(droneMarker.getPosition().latitude,droneMarker.getPosition().longitude,
                autelGPSLatLng.getLat4Map(true),
                autelGPSLatLng.getLng4Map(true)) < 0.5;
    }


    public void animateMarker(final Marker marker, final LatLng nextPosition)
    {
        if (marker != null) {
            final LatLngInterpolator latLngInterpolator = new LatLngInterpolator.LinearFixed();
            ValueAnimator valueAnimator = new ValueAnimator();
            final LatLng startPosition = marker.getPosition();
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
            {
                @Override
                public void onAnimationUpdate(ValueAnimator animation)
                {
                    try {
                        if (marker == null) // oops... destroying map during animation...
                        {
                            return;
                        }
                        float v = animation.getAnimatedFraction();
                        LatLng newPosition = latLngInterpolator.interpolate(v, startPosition,nextPosition);
                        marker.setPosition(newPosition);
                    } catch (Exception ex) {
                        // I don't care atm..
                    }
                }
            });
            valueAnimator.setFloatValues(0, 1);
            valueAnimator.setDuration(100);
            valueAnimator.start();
        }
    }

    public Bitmap getWaypointBitmap(boolean isValid) {
        return MapMarkerIconUtil.getCombineWaypointMarkerIcon(waypoints.size() + 1,isValid);
    }

    public Bitmap getWaypointBitmap(int index, boolean isVailid){
        return MapMarkerIconUtil.getCombineWaypointMarkerIcon(index,isVailid);
    }

    public interface LatLngInterpolator
    {
        LatLng interpolate(float fraction, LatLng a, LatLng b);

        class Linear implements LatLngInterpolator
        {
            @Override
            public LatLng interpolate(float fraction, LatLng a, LatLng b)
            {
                double lat = (b.latitude - a.latitude) * fraction + a.latitude;
                double lng = (b.longitude - a.longitude) * fraction + a.longitude;
                return new LatLng(lat, lng);
            }
        }
        class LinearFixed implements LatLngInterpolator
        {
            @Override
            public LatLng interpolate(float fraction, LatLng a, LatLng b) {
                double lat = (b.latitude - a.latitude) * fraction + a.latitude;
                double lngDelta = b.longitude - a.longitude;
                // Take the shortest path across the 180th meridian.
                if (Math.abs(lngDelta) > 180) {
                    lngDelta -= Math.signum(lngDelta) * 360;
                }
                double lng = lngDelta * fraction + a.longitude;
                return new LatLng(lat, lng);
            }
        }
    }

    @Override
    public void updateDroneYaw(double yaw, boolean isCompassEnable) {
        if(droneMarker == null){
            return;
        }
        if (!isCompassEnable) { //当地图没有开启跟随指南针旋转的时候执行以下方法
            if (aMap.getCameraPosition() != null) {
                droneMarker.setRotateAngle((float) (yaw + aMap.getCameraPosition().bearing) * (-1));
            }
        } else {
            droneMarker.setRotateAngle((float) (compassRotateDegree - yaw));
        }
    }

    @Override
    public void setHomeMarker(AutelGPSLatLng homeLocation, Bitmap bitmap) {
        if(homeMarker != null && checkMarkPositionChange(homeMarker,homeLocation)){
            return;
        }
        if(homeMarker == null){
            homeMarker = addMarker(new LatLng(homeLocation.getLat4Map(true),
                    homeLocation.getLng4Map(true)), BitmapDescriptorFactory.fromBitmap(bitmap));
        }else if(DistanceUtils.distanceBetweenPoints(homeMarker.getPosition().latitude,homeMarker.getPosition().longitude,
                homeLocation.getLat4Map(true),homeLocation.getLng4Map(true)) > 2){
            homeMarker.setPosition(new LatLng(homeLocation.getLat4Map(true),
                    homeLocation.getLng4Map(true)));
        }

    }

    @Override
    public void setPhoneLocation(AutelGPSLatLng phoneLocation, Bitmap bitmap) {
        if(phoneMarker != null && checkMarkPositionChange(phoneMarker,phoneLocation)){
            return;
        }
        LatLng latLng = new LatLng(phoneLocation.getLat4Map(true),
                phoneLocation.getLng4Map(true));
        if(phoneMarker == null){
            phoneMarker = addMarker(latLng, BitmapDescriptorFactory.fromBitmap(bitmap));
        }else{
            animateMarker(phoneMarker,latLng);
        }
    }

    @Override
    public void initMapToPhoneLocation() {
        if (aMap == null || aMap.getCameraPosition() == null) {
            return;
        }
        if(isFirstChangeToPhone){
            if(phoneMarker != null){
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(phoneMarker.getPosition()));
                aMap.moveCamera(CameraUpdateFactory.zoomTo(MapConstant.MapInitZoomSize));
            }
            isFirstChangeToPhone = false;
        }

    }

    @Override
    public void changeLocationToDrone() {
        if(droneMarker != null){
            aMap.moveCamera(CameraUpdateFactory.newLatLng(droneMarker.getPosition()));
        }
    }

    @Override
    public void changeLocationToHome() {
        if(homeMarker != null){
            aMap.moveCamera(CameraUpdateFactory.newLatLng(homeMarker.getPosition()));
        }
    }

    @Override
    public void updateFlyRoute(AutelGPSLatLng droneLocation) {
        AutelLatLng autelLatLng = new AutelLatLng(droneLocation.getLat4Map(true),droneLocation.getLng4Map(true));
        addNewFlyLine(autelLatLng);
    }

    @Override
    public void clearFlyRoute() {
        if (flylines.size() > 0) {
            for (int i = 0; i < flylines.size(); i++) {
                flylines.get(i).remove();
            }
        }
        if (recordPoints.size() > 0) {
            recordPoints.clear();
        }
    }

    @Override
    public void updateFollowLine() {
        if(aMap == null){
            return;
        }
        if(followLine != null){
            followLine.remove();
        }
        if(droneMarker != null && phoneMarker != null){
            followLine = addPolyline(new PolylineOptions().add(droneMarker.getPosition(),phoneMarker.getPosition()), Color.GREEN,20);
        }
    }

    @Override
    public void setOnMapClickListener(final int mapClickWay) {
        if(aMap != null){
            aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    switch (mapClickWay){
                        case MapConstant.MAP_CLICK_MODE_ORBIT:
                            addOrbitFromMap(latLng);
                            break;
                        case MapConstant.MAP_CLICK_MODE_WAYPOINT:
                            if(isOnPolyLine(latLng)){
                                insertWaypoints(latLng);
                            }else{
                                addWaypointFromMap(latLng,false);
                            }
                            break;
                    }
                }
            });
        }

    }

    private void insertWaypoints(LatLng latLng) {
        int insertWaypointIndex = clickPolylineIndex + 1;
        Marker marker = addMarker(latLng, BitmapDescriptorFactory.fromBitmap(getWaypointBitmap(insertWaypointIndex,true)));
        marker.setAnchor(0.5f,1.0f);
        marker.setDraggable(true);

        List<Marker> firstList = waypoints.subList(0,insertWaypointIndex);
        PolylineOptions firstOptions = new PolylineOptions();
        firstOptions.add(firstList.get(firstList.size()-1).getPosition(),latLng);
        Polyline firstPolyline = addPolyline(firstOptions,AutelConfigManager.instance().getAppContext().getResources().getColor(R.color.green_light),10);

        firstList.add(marker);

        List<Marker> secondList = waypoints.subList(insertWaypointIndex,waypoints.size());
        PolylineOptions secondOptions = new PolylineOptions();
        secondOptions.add(latLng,secondList.get(1).getPosition());
        Polyline secondPolyline = addPolyline(secondOptions,AutelConfigManager.instance().getAppContext().getResources().getColor(R.color.green_light),10);

        for(Marker subMarker : secondList){
            subMarker.setIcon(BitmapDescriptorFactory.fromBitmap(getWaypointBitmap(++insertWaypointIndex,true)));
        }

        List<Polyline> firstPolylines = waypointLines.subList(0,clickPolylineIndex);
        firstPolylines.add(firstPolyline);
        firstPolylines.add(secondPolyline);

        List<Polyline> secondPolylines = waypointLines.subList(clickPolylineIndex,waypointLines.size());
        secondPolylines.get(2).remove();
        secondPolylines.remove(2);


    }

    private boolean isOnPolyLine(LatLng latLng) {
        for (Polyline polyline : waypointLines) {
            for(LatLng polyLineLocation : polyline.getPoints()){
                if(PolyUtil.isLocationOnPath(latLng,polyline.getPoints(),false,5)){
                    this.clickPolylineIndex = waypointLines.indexOf(polyline);
                    if(clickPolylineIndex == -1){
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private void addWaypointFromMap(LatLng latLng, boolean isFromFile) {
        if(homeMarker != null){
            int distance = DistanceUtils.distanceBetweenPoints(latLng.latitude,latLng.longitude,homeMarker.getPosition().latitude,homeMarker.getPosition().longitude);
            if(distance > limitCircle.getRadius()){
                return;
            }
        }
        Marker marker = addMarker(latLng, BitmapDescriptorFactory.fromBitmap(getWaypointBitmap(true)));
        marker.setAnchor(0.5f,1);
        marker.setDraggable(true);
        if(waypoints.size() >= 1){
            PolylineOptions options = new PolylineOptions();
            options.add(waypoints.get(waypoints.size()-1).getPosition(), latLng);
            Polyline polyline = addPolyline(options,AutelConfigManager.instance().getAppContext().getResources().getColor(R.color.green_light),10);
            waypointLines.add(polyline);
        }
        waypoints.add(marker);
        if(waypointsListener != null){
            waypointsListener.onWaypointsChange(waypoints.size(),getTotalDistance(waypoints));
        }

        if(!isFromFile){
            AutelGPSLatLng autelGPSLatLng = AutelGPSLatLng.createFromMap(marker.getPosition()) ;
            MissionWaypointBean.ParamsBean.WaypointsBean waypointBean = new MissionWaypointBean.ParamsBean.WaypointsBean();
            waypointBean.setWaypointId(waypoints.size() - 1);
            waypointBean.setLatitude((int) (autelGPSLatLng.getLat4Drone() * 10e6));
            waypointBean.setLongitude((int)(autelGPSLatLng.getLng4Drone() * 10e6));
            waypointBean.setAltitude(waypointAdvanceData.getAltitude());
            waypointBean.setSpeed(waypointAdvanceData.getSpeed());
            waypointBean.setBeizerParameter(1);
            waypointBean.setHeadingMode(waypointAdvanceData.getHeading());
            waypointBeans.add(waypointBean);
        }

        curWaypointIndex = waypoints.size();

    }

    private int getTotalDistance(List<Marker> waypoints) {
        int distance = 0;
        for(int i=1; i<waypoints.size(); i++){
            distance += DistanceUtils.distanceBetweenPoints(waypoints.get(i-1).getPosition().latitude,
                    waypoints.get(i-1).getPosition().longitude,
                    waypoints.get(i).getPosition().latitude,
                    waypoints.get(i).getPosition().longitude);
        }
        return distance;
    }


    @Override
    public boolean setMissionLimitCircle(int circleRadius) {
        if(homeMarker != null && homeMarker.getPosition() != null){
            if(limitCircle == null){
                limitCircle = addCircle(homeMarker.getPosition(),circleRadius,AutelConfigManager.instance().getAppContext().getResources().getColor(R.color.blue),5);
                return true;
            }else{
                limitCircle.setCenter(homeMarker.getPosition());
                return true;
            }
        }
        return false;
    }

    @Override
    public void removeOrbitPoint() {
        if(orbitMarker != null){
            orbitMarker.remove();
            orbitMarker = null;
        }
    }

    @Override
    public void clearOrbitMode() {
        removeOrbitPoint();
        removeLimitCircle();
    }

    @Override
    public boolean isOrbitAdd() {
        return orbitMarker != null;
    }

    @Override
    public boolean addOrbitFromDrone(AutelGPSLatLng droneLocation) {
        LatLng latLng = new LatLng(droneLocation.getLat4Map(true),droneLocation.getLng4Map(true));
        if(limitCircle != null){
            int tmpdistance = DistanceUtils.distanceBetweenPoints(limitCircle.getCenter().latitude, limitCircle.getCenter().longitude,
                    latLng.latitude, latLng.longitude);
            if(tmpdistance > limitCircle.getRadius()){
                return false;
            }
        }

        Bitmap bitmap = BitmapFactory.decodeResource(AutelConfigManager.instance().getAppContext().getResources(),
                R.mipmap.favor_marker_point);
        if(orbitMarker == null){
            orbitMarker = addMarker(latLng, BitmapDescriptorFactory.fromBitmap(bitmap));
        }else{
            orbitMarker.setPosition(latLng);
        }
        return true;
    }

    @Override
    public AutelGPSLatLng getOrbitMarkerPosition() {
        if(orbitMarker == null){
            return null;
        }
        return AutelGPSLatLng.createFromMap(orbitMarker.getPosition());
    }

    @Override
    public void clearWaypoints() {
        for (Marker marker : waypoints) {
            marker.remove();
        }
        waypoints.clear();
        for(Polyline polyline : waypointLines){
            polyline.remove();
        }
        waypointLines.clear();
    }

    @Override
    public void projectMarker(List<AutelGPSLatLng> path) {

        curWaypointProjectIndex = waypoints.size() == 0 ? 0 : waypoints.size() - 1;

        if(path != null){
            Projection pj = aMap.getProjection();
            ArrayList<LatLng> tempWaypoints = new ArrayList<LatLng>();

            int maxsize = 99;

            LatLng pre = waypoints.size() > 1 ? waypoints.get(waypoints.size() - 1).getPosition() : null;

            AutelLatLng homeLocation = null;

            if(waypoints.size() == 0 && path.size() > 0){
                Point point = new Point((int) path.get(0).getLat4Drone(), (int) path.get(0).getLng4Drone());
                LatLng homepoint = pj.fromScreenLocation(point);
                homeLocation = new AutelLatLng(homepoint.latitude,homepoint.longitude);
            }

            for (int i = 0; i < path.size(); i++) {
                Point point = new Point((int) path.get(i).getLat4Drone(), (int) path.get(i).getLng4Drone());
                LatLng ll = pj.fromScreenLocation(point);

                //Check validation of each latlng
                int temp =  1;
                if( temp >= 0){
                    pre = ll;
                    tempWaypoints.add(ll);
                }else{
                    return;
                }
            }

            //Add valide waypoints to  map
            for (LatLng tempLatLng : tempWaypoints){
                addWaypointFromMap(tempLatLng,false);
            }
        }
    }

    @Override
    public void setWaypointsListener(MapModelImpl.OnWaypointsChangeListener waypointsListener) {
        this.waypointsListener = waypointsListener;
    }

    @Override
    public void setMarkerDragListener() {
        aMap.setOnMarkerDragListener(new AMap.OnMarkerDragListener() {
            public LatLng startPosition;

            @Override
            public void onMarkerDragStart(Marker marker) {
                int curDragWaypoint = waypoints.indexOf(marker);
                AutelGPSLatLng autelGPSLatLng = AutelGPSLatLng.createFromDrone(new AutelCoordinate3D(waypointBeans.get(curDragWaypoint).getLatitude() * 1e-7,
                        waypointBeans.get(curDragWaypoint).getLongitude() * 1e-7));
                startPosition = new LatLng(autelGPSLatLng.getLat4Map(true),
                        autelGPSLatLng.getLng4Map(true));
                updatePolyline(marker,curDragWaypoint);
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                int curDragWaypoint = waypoints.indexOf(marker);
                if(homeMarker != null){
                    int distance = DistanceUtils.distanceBetweenPoints(homeMarker.getPosition().latitude,
                            homeMarker.getPosition().longitude,marker.getPosition().latitude,
                            marker.getPosition().longitude);
                    if(limitCircle != null && distance > limitCircle.getRadius()){
                        marker.setIcon(BitmapDescriptorFactory.fromBitmap(getWaypointBitmap(curDragWaypoint + 1,false)));
                    }else{
                        marker.setIcon(BitmapDescriptorFactory.fromBitmap(getWaypointBitmap(curDragWaypoint + 1, true)));
                    }
                }
                updatePolyline(marker,curDragWaypoint);
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                int curDragWaypoint = waypoints.indexOf(marker);
                if(homeMarker != null){
                    int distance = DistanceUtils.distanceBetweenPoints(homeMarker.getPosition().latitude,
                            homeMarker.getPosition().longitude,marker.getPosition().latitude,
                            marker.getPosition().longitude);
                    if(limitCircle != null && distance > limitCircle.getRadius()){
                        marker.setPosition(startPosition);
                        marker.setIcon(BitmapDescriptorFactory.fromBitmap(getWaypointBitmap(curDragWaypoint + 1,true)));
                    }else{
                        AutelGPSLatLng autelGPSLatLng = AutelGPSLatLng.createFromMap(marker.getPosition()) ;
                        MissionWaypointBean.ParamsBean.WaypointsBean waypointBean = waypointBeans.get(curDragWaypoint);
                        waypointBean.setLatitude((int) (autelGPSLatLng.getLat4Drone() * 10e6));
                        waypointBean.setLongitude((int)(autelGPSLatLng.getLng4Drone() * 10e6));
                    }
                }
                updatePolyline(marker,curDragWaypoint);
            }
        });
    }

    private void updatePolyline(Marker m, int position){
        List<LatLng> points;

        if(waypointLines.size() ==0) return;
        if(position == 0){
            points = waypointLines.get(0).getPoints();
            points.set(0, m.getPosition());
            waypointLines.get(0).setPoints(points);
        }else if(position == waypoints.size()-1){
            points = waypointLines.get(position-1).getPoints();
            points.set(1,m.getPosition());
            waypointLines.get(position-1).setPoints(points);
        }else{
            points = waypointLines.get(position-1).getPoints();
            points.set(1,m.getPosition());
            waypointLines.get(position-1).setPoints(points);

            points = waypointLines.get(position).getPoints();
            points.set(0,m.getPosition());
            waypointLines.get(position).setPoints(points);
        }
    }

    @Override
    public void removeLimitCircle() {
        if(limitCircle != null){
            limitCircle.remove();
            limitCircle = null;
        }
    }

    private void addOrbitFromMap(LatLng latLng) {
        // need to add multiple language support
       /* if (homeMarker == null || homeMarker.getPosition() == null) {
            return ;
        }
        if(limitCircle != null){
            int tmpdistance = DistanceUtils.distanceBetweenPoints(limitCircle.getCenter().latitude, limitCircle.getCenter().longitude,
                    latLng.latitude, latLng.longitude);
            if(tmpdistance > limitCircle.getRadius()){
                showToast(ResourcesUtils.getString(R.string.poi_outside_fly_zone),ToastBeanIcon.ICON_FAIL);
                return;
            }
        }*/
        int tmpdistance = DistanceUtils.distanceBetweenPoints(homeMarker.getPosition().latitude, homeMarker.getPosition().longitude,
                latLng.latitude, latLng.longitude);
        Bitmap bitmap = BitmapFactory.decodeResource(AutelConfigManager.instance().getAppContext().getResources(),
                R.mipmap.favor_marker_point);
        orbitMarker = addMarker(latLng, BitmapDescriptorFactory.fromBitmap(bitmap));
    }


    public void saveAlreadyUpdateFlyRoute() {
        if(recordPoints.size() > 0){
            recordPoints.clear();
        }
    }

    @Override
    public MissionWaypointBean getMissionWaypointBean() {
        MissionWaypointBean missionWaypointBean = new MissionWaypointBean();
        missionWaypointBean.setMethod("FMUMissionData");
        MissionWaypointBean.ParamsBean paramsBean = new MissionWaypointBean.ParamsBean();


        paramsBean.setMissionId(0);
        paramsBean.setFinishAction(waypointAdvanceData.getFinishType());
        paramsBean.setObstacleAvoidanceMode(waypointAdvanceData.getAvoidance());
        paramsBean.setLostControlAction(1);
        paramsBean.setNumberOfWaypoints(waypoints.size());
        paramsBean.setObstacleAvoidanceTimeout(0);
        paramsBean.setWaypoints(waypointBeans);

        missionWaypointBean.setParams(paramsBean);
        return missionWaypointBean;
    }

    @Override
    public void deleteJustProjectWaypoint() {
        for(int i = waypoints.size() -1 ; i >= (curWaypointProjectIndex == 0 ? 0 : curWaypointProjectIndex + 1); i--){
            waypoints.get(i).remove();
            waypoints.remove(i);
            waypointBeans.remove(i);
        }
        for(int i = waypointLines.size() - 1; i >= curWaypointProjectIndex; i--){
            waypointLines.get(i).remove();
            waypointLines.remove(i);
        }
    }

    @Override
    public void addWaypointUseDroneLocation() {
        if(droneMarker != null){
            addWaypointFromMap(droneMarker.getPosition(),false);
        }
    }

    @Override
    public void waypointDelete() {
        if(waypoints.size() > 0){
            waypoints.get(waypoints.size() - 1).remove();
            waypoints.remove(waypoints.size() - 1);
            waypointBeans.remove(waypoints.size() - 1);
        }
        if(waypointLines.size() > 0){
            waypointLines.get(waypoints.size() - 1).remove();
            waypointLines.remove(waypoints.size() - 1);
        }
    }

    @Override
    public void setWaypointAdvanceData(WaypointAdvanceDataBean waypointAdvanceData) {
        this.waypointAdvanceData = waypointAdvanceData;
    }

    @Override
    public void setCurWaypointDataChange(WaypointAdvanceDataBean waypointAdvanceDataBean, int whichItem) {
        if(waypointBeans.size() > 0){
            switch (whichItem){
                case WaypointAdvanceDataBean.TYPE_BASIC:
                    waypointAdvanceDataBean.setAvoidance(waypointAdvanceDataBean.getAvoidance());
                    waypointAdvanceDataBean.setFinishType(waypointAdvanceDataBean.getFinishType());
                    waypointAdvanceDataBean.setHeading(waypointAdvanceDataBean.getHeading());
                    break;
                case WaypointAdvanceDataBean.TYPE_ADVANCE:
                    waypointAdvanceDataBean.setAltitude(waypointAdvanceDataBean.getAltitude());
                    waypointAdvanceDataBean.setSpeed(waypointAdvanceDataBean.getSpeed());
                    waypointBeans.get(waypointBeans.size() - 1).setAltitude(waypointAdvanceDataBean.getAltitude());
                    waypointBeans.get(waypointBeans.size() - 1).setSpeed(waypointAdvanceDataBean.getSpeed());
                    break;
                case WaypointAdvanceDataBean.TYPE_AVOIDANCE:
                    waypointAdvanceDataBean.setAvoidance(waypointAdvanceDataBean.getAvoidance());
                    break;
                case WaypointAdvanceDataBean.TYPE_FINISH_ACTION:
                    waypointAdvanceDataBean.setFinishType(waypointAdvanceDataBean.getFinishType());
                    break;
                case WaypointAdvanceDataBean.TYPE_ALTITUDE:
                    waypointAdvanceDataBean.setAltitude(waypointAdvanceDataBean.getAltitude());
                    waypointBeans.get(curWaypointIndex - 1).setAltitude(waypointAdvanceDataBean.getAltitude());
                    break;
                case WaypointAdvanceDataBean.TYPE_SPEED:
                    waypointAdvanceDataBean.setSpeed(waypointAdvanceDataBean.getSpeed());
                    waypointBeans.get(curWaypointIndex - 1).setSpeed(waypointAdvanceDataBean.getSpeed());
                    break;
            }
        }
    }

    @Override
    public void showWaypointFileOnMap(MissionWaypointBean missionWaypointBean) {
        clearWaypoints();
        List<MissionWaypointBean.ParamsBean.WaypointsBean> waypointsBeans = missionWaypointBean.getParams().getWaypoints();
        this.waypointBeans = waypointsBeans;
        for(int i=0; i<waypointsBeans.size(); i++){
            AutelGPSLatLng autelGPSLatLng = AutelGPSLatLng.createFromDrone(new AutelCoordinate3D(waypointsBeans.get(i).getLatitude() * 1e-7 ,
                    waypointsBeans.get(i).getLongitude() * 1e-7,waypointsBeans.get(i).getAltitude()));
            addWaypointFromMap(new LatLng(autelGPSLatLng.getLat4Map(true),
                    autelGPSLatLng.getLng4Map(true)),true);
        }
    }

    @Override
    public MissionOrbitdBean getMissionOrbitBean() {
        MissionOrbitdBean missionOrbitdBean = new MissionOrbitdBean();
        missionOrbitdBean.setMethod("OrbitInfo");
        MissionOrbitdBean.ParamsBean paramsBean = new MissionOrbitdBean.ParamsBean();
        paramsBean.setMissionId(1);
        paramsBean.setWaypointId(1);
        if(orbitMarker != null){
            AutelGPSLatLng autelGPSLatLng = AutelGPSLatLng.createFromMap(orbitMarker.getPosition());
            paramsBean.setCenterLattidue((int) (autelGPSLatLng.getLat4Drone() * 1e7));
            paramsBean.setCenterLongitude((int) (autelGPSLatLng.getLng4Drone() * 1e7));
        }else if(phoneMarker != null){
            AutelGPSLatLng autelGPSLatLng = AutelGPSLatLng.createFromMap(phoneMarker.getPosition());
            paramsBean.setCenterLattidue((int) (autelGPSLatLng.getLat4Drone() * 1e7));
            paramsBean.setCenterLongitude((int) (autelGPSLatLng.getLng4Drone() * 1e7));
        }
        if(orbitAdvanceDataBean.isBasicMode()){
            paramsBean.setSpeed(orbitAdvanceDataBean.getSpeed());
            paramsBean.setRotateDirection(orbitAdvanceDataBean.getFlightDirection());
            paramsBean.setCenterAltitude(MissionConstant.getOrbitAltitudeDefault());
            if(orbitMarker != null && droneMarker != null){
                AutelGPSLatLng autelGPSLatLng = AutelGPSLatLng.createFromMap(orbitMarker.getPosition());
                AutelGPSLatLng dronePosition = AutelGPSLatLng.createFromMap(droneMarker.getPosition());
                paramsBean.setRadius(DistanceUtils.distanceBetweenPoints(autelGPSLatLng.getLat4Drone(),autelGPSLatLng.getLng4Drone(),
                        dronePosition.getLat4Drone(),dronePosition.getLng4Drone()));
            }else if(phoneMarker != null && droneMarker != null){
                AutelGPSLatLng autelGPSLatLng = AutelGPSLatLng.createFromMap(phoneMarker.getPosition());
                AutelGPSLatLng dronePosition = AutelGPSLatLng.createFromMap(droneMarker.getPosition());
                paramsBean.setRadius(DistanceUtils.distanceBetweenPoints(autelGPSLatLng.getLat4Drone(),autelGPSLatLng.getLng4Drone(),
                        dronePosition.getLat4Drone(),dronePosition.getLng4Drone()));
            }

        }else{
            paramsBean.setCenterAltitude(orbitAdvanceDataBean.getAltitude());
            paramsBean.setRadius(orbitAdvanceDataBean.getRadius());
            paramsBean.setSpeed(orbitAdvanceDataBean.getSpeed());
            paramsBean.setRemainDegree(orbitAdvanceDataBean.getRotation());
            paramsBean.setRotateDirection(orbitAdvanceDataBean.getFlightDirection());
            paramsBean.setHeadingDirection(orbitAdvanceDataBean.getHeading());
            paramsBean.setEntryDirection(orbitAdvanceDataBean.getEntryPoint());
        }
        missionOrbitdBean.setParams(paramsBean);
        return missionOrbitdBean;
    }

    @Override
    public void setCurOrbitDataChange(OrbitAdvanceDataBean orbitAdvanceDataBean, int whichItem) {
        switch (whichItem){
            case OrbitAdvanceDataBean.TYPE_BASIC_MODE:
                this.orbitAdvanceDataBean.setSpeed(orbitAdvanceDataBean.getSpeed());
                this.orbitAdvanceDataBean.setFlightDirection(orbitAdvanceDataBean.getFlightDirection());
                this.orbitAdvanceDataBean.setBasicMode(true);
                break;
            case OrbitAdvanceDataBean.TYPE_BASIC_SPEED_CHANGE:
                this.orbitAdvanceDataBean.setSpeed(orbitAdvanceDataBean.getSpeed());
                break;
            case OrbitAdvanceDataBean.TYPE_BASIC_FLIGHT_DIRECTION:
                this.orbitAdvanceDataBean.setFlightDirection(orbitAdvanceDataBean.getFlightDirection());
                break;
        }
    }

    @Override
    public void setWaypointProjectDataChange(WaypointAdvanceDataBean waypointProjectDataChange) {
        for (int i = waypoints.size() - 1; i >= (curWaypointProjectIndex == 0 ? 0 : curWaypointProjectIndex + 1); i--) {
            waypointBeans.get(i).setSpeed(waypointProjectDataChange.getSpeed());
            waypointBeans.get(i).setAltitude(waypointProjectDataChange.getAltitude());
        }
    }

    @Override
    public boolean initMapToNetLocation(AutelGPSLatLng netLocation) {
        if (aMap == null || aMap.getCameraPosition() == null) {
            return false;
        }
        if(isFirstChangeToNet){
            if(netLocation != null){
                CameraPosition cp = new CameraPosition(new LatLng(netLocation.getLat4Map(true),
                        netLocation.getLng4Map(true)),
                        MapConstant.MapInitZoomSize,
                        0,
                        0);
                aMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));
                //TODO:一下方法会在小地图的情况下定位不对
                //mGmap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mPhoneLocation.latitude,mPhoneLocation.longitude)));
                //mapView.getMap().moveCamera(CameraUpdateFactory.zoomTo(mapInitSize));
                return true;
            }
            isFirstChangeToNet = false;
        }else{
            return true;
        }
        return false;
    }

    @Override
    public WaypointAdvanceDataBean getLastWaypointData() {
        if(curWaypointIndex - 1 > 0 && curWaypointIndex - 1 < waypointBeans.size()){
            curWaypointIndex--;
            WaypointAdvanceDataBean waypointAdvanceDataBean = new WaypointAdvanceDataBean();
            waypointAdvanceDataBean.setAltitude(waypointBeans.get(curWaypointIndex -1).getAltitude());
            waypointAdvanceDataBean.setSpeed(waypointBeans.get(curWaypointIndex -1).getSpeed());
            waypointAdvanceDataBean.setWaypointIndex(curWaypointIndex);
            return waypointAdvanceDataBean;
        }
        return null;
    }

    @Override
    public WaypointAdvanceDataBean getNextWaypointData() {
        if(curWaypointIndex + 1  <= waypointBeans.size()){
            curWaypointIndex++;
            WaypointAdvanceDataBean waypointAdvanceDataBean = new WaypointAdvanceDataBean();
            waypointAdvanceDataBean.setAltitude(waypointBeans.get(curWaypointIndex-1).getAltitude());
            waypointAdvanceDataBean.setSpeed(waypointBeans.get(curWaypointIndex-1).getSpeed());
            waypointAdvanceDataBean.setWaypointIndex(curWaypointIndex);
            return waypointAdvanceDataBean;
        }
        return null;
    }

    @Override
    public void clearFollowLine() {
        if(followLine != null){
            followLine.remove();
        }
    }

    public void changeLocationToMe() {
        if(phoneMarker != null){
            aMap.moveCamera(CameraUpdateFactory.newLatLng(phoneMarker.getPosition()));
        }
    }

    private void addNewFlyLine(AutelLatLng autelLatLng) {
        LatLng tempLatLng;
        if (this.recordPoints.size() > 0) {
            tempLatLng = this.recordPoints.get(-1 + this.recordPoints.size());
        } else {
            tempLatLng = null;
        }
        if (tempLatLng != null && DistanceUtils.distanceBetweenPointsAsFloat(autelLatLng.getLatitude(), autelLatLng.getLongitude(), tempLatLng.latitude, tempLatLng.longitude) > 300) {
            this.recordPoints.clear();
        } else {
            if (tempLatLng == null || DistanceUtils.distanceBetweenPointsAsFloat(autelLatLng.getLatitude(), autelLatLng.getLongitude(), tempLatLng.latitude, tempLatLng.longitude) >= 0.1F) {
                this.recordPoints.add(new LatLng(autelLatLng.getLatitude(), autelLatLng.getLongitude()));
                int color = 0xee99C3C6;
                this.addFlylines(color);
            }
        }
    }

    private void addFlylines(int color) {
        if (this.recordPoints.size() > 1) {
            Polyline newPolyline = this.addPolyline(this.recordPoints, color);
            newPolyline.setWidth(6);
            int lastFlyline = -1 + this.flylines.size();
            if (this.recordPoints.size() > 800) {
                LatLng var6 = this.recordPoints.get(-1 + this.recordPoints.size());
                this.flylines.get(lastFlyline).remove();
                this.flylines.set(lastFlyline, newPolyline);
                this.recordPoints.clear();
                this.recordPoints.add(var6);
            } else if (lastFlyline >= 0 && this.recordPoints.size() > 2) {
                this.flylines.get(lastFlyline).remove();
                this.flylines.set(lastFlyline, newPolyline);
            } else {
                this.flylines.add(newPolyline);
            }
        }
    }

    private Polyline addPolyline(ArrayList<LatLng> var1, int var2) {
        PolylineOptions var3 = this.e(var2);
        var3.addAll(var1);
        return aMap.addPolyline(var3);
    }

    private PolylineOptions e(int color) {
        return this.d(color, 10);
    }

    private PolylineOptions d(int color, int width) {
        return (new PolylineOptions()).width((float) width).color(color).geodesic(true);
    }

    private class OnScaleIdleRun implements  Runnable{

        @Override
        public void run() {
            if(onScaleChangeListener != null){
                onScaleChangeListener.onScaleIdle();
            }
        }
    }

    private void showScaleView() {

        //获取设置默认屏幕宽度480
        int default_screen_width = 480;
        //获取设置默认屏幕高度800
        int default_screen_height = 800;
        //mScaleMaxWidth = 默认屏幕宽度四分之一
        mScaleMaxWidth = default_screen_width>>1;
        //mScaleMaxHeight = 默认屏幕高度二分之一
        mScaleMaxHeight = default_screen_height>>1;

        //转换为起点的经纬度GeoPoint
        LatLng fromGeopoint = aMap.getProjection().fromScreenLocation(new Point(0,mScaleMaxHeight));
        //转换为终点的经纬度GeoPoint
        LatLng toGeopoint = aMap.getProjection().fromScreenLocation(new Point(mScaleMaxWidth, mScaleMaxHeight));
        //通过getDistance函数得出两点间的真实距离
        double distance = DistanceUtils.distanceBetweenPoints(fromGeopoint.latitude,fromGeopoint.longitude,toGeopoint.latitude,toGeopoint.longitude);

        if(TransformUtils.isEnUnit()){
            distance = TransformUtils.meter2feet(distance,6);
        }

        String description;
        int dis = 0;
        int width;

        //真实距离和数组中相近的两个值循环比较，以小值为准，得出规定好的比例尺数值赋值给dis
        for (int j = 1; j <SCALE.length; j++) {
            if (SCALE[j - 1] <= distance
                    && distance <SCALE[j]) {
                dis = SCALE[j - 1];
                break;
            }
        }

        if(TransformUtils.isEnUnit()){
            for( int j = 1 ; j < SCALE_EN.length; j++){
                if(SCALE_EN[j - 1] <= distance && distance < SCALE_EN[j]){
                    dis = SCALE_EN[j-1];
                }
            }
        }
        //比例尺黑条的宽度 =（dis*默认屏幕宽度四分之一）/真实的距离
        width = (int) (dis * mScaleMaxWidth / distance);
      /*  }*/

        //如果比例尺数值大于1000，则description = 2500 km（公里），否则 为 900 m（米）
        if (dis >= 1000) {
            description = dis / 1000 + " km";
        } else {
            description = dis + " m";
        }
        if(TransformUtils.isEnUnit()){
            if(dis >= 5280){
                description = dis / 5280 + " mi";
            } else {
                description = dis + " ft";
            }
        }

        if(onScaleChangeListener != null){
            if(!description.equals(curDescription) || curWidth != width){
                this.curDescription = description;
                this.curWidth = width;
                onScaleChangeListener.onScaleChange(description,width);
            }
        }

    }

    private Marker addMarker(LatLng location, BitmapDescriptor icon){
        if(aMap == null){
            return null;
        }
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(location);
        markerOption.draggable(false);
        markerOption.icon(icon);
        markerOption.setFlat(true);
        markerOption.anchor(0.5f, 0.5f);
        return aMap.addMarker(markerOption);
    }

    private Polyline addPolyline(PolylineOptions polylineOptions, int color, int lineWidth){
        Polyline polyline = aMap.addPolyline(polylineOptions);
        polyline.setColor(color);
        polyline.setWidth(lineWidth);
        return polyline;
    }

    private Circle addCircle(LatLng latlng, int radius, int strokeColor, float strokeWidth){
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latlng);
        circleOptions.radius(radius);
        circleOptions.strokeColor(strokeColor);
        circleOptions.strokeWidth(strokeWidth);
        return aMap.addCircle(circleOptions);
    }

}
