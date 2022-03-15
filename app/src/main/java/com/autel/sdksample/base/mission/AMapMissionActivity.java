package com.autel.sdksample.base.mission;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.autel.common.flycontroller.AttitudeInfo;
import com.autel.sdksample.R;

import java.util.ArrayList;
import java.util.List;


public class AMapMissionActivity extends MapActivity {
    final String TAG = getClass().getSimpleName();
    MapView aMapView;
    private AMap mAmap;
    boolean isFirstChangeToPhone = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMapContentView(R.layout.ac_mission_amap);
        aMapView = (MapView) findViewById(R.id.aMapView);
        aMapView.onCreate(savedInstanceState);
        mAmap = aMapView.getMap();
        attachTapListener();
    }

    public void onResume() {
        super.onResume();
        aMapView.onResume();
    }

    public void onPause() {
        super.onPause();
        aMapView.onPause();
    }


    public void onDestroy() {
        super.onDestroy();
        detachTapListener();
        aMapView.onDestroy();
        resetUI();
    }

    private void resetUI() {
        mMarkerList.clear();
    }

    private void attachTapListener() {
        mAmap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                onAbsMapClick(latLng.latitude, latLng.longitude);
            }
        });
        mAmap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                int index = mMarkerList.indexOf(marker);
                if (index >= 0 && index < mMarkerList.size()) {
                    markerClick(index);
                }
                return true;
            }
        });
    }

    private void detachTapListener() {
        mAmap.setOnMapClickListener(null);
    }

    @Override
    protected void phoneLocationChanged(Location location) {
        AutelLatLng all = MapRectifyUtil.wgs2gcj(new AutelLatLng(location.getLatitude(), location.getLongitude()));
        LatLng latLng = new LatLng(all.getLatitude(), all.getLongitude());
        if (mAmap == null || mAmap.getCameraPosition() == null) {
            return;
        }
        if (isFirstChangeToPhone) {
            mAmap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
            mAmap.moveCamera(CameraUpdateFactory.zoomTo(MapInitZoomSize));
            isFirstChangeToPhone = false;
        }
        drawPhoneMarker(latLng);
    }

    @Override
    protected void updateAircraftLocation(double lat, double lot, AttitudeInfo info) {
        AutelLatLng latLng = MapRectifyUtil.wgs2gcj(new AutelLatLng(lat, lot));
        LatLng lng = new LatLng(latLng.latitude, latLng.longitude);
        drawDroneMarker(lng,info);
    }


    protected ArrayList<Marker> mMarkerList = new ArrayList<>();

    @Override
    public void addWayPointMarker(double lat, double lot) {
        LatLng latlng = new LatLng(lat, lot);
        int size = mMarkerList.size();
        if (size > 0) {
            addWayPointLine(mMarkerList.get(size - 1).getPosition(), latlng);
        }

        Marker temp = addMarkerWithLabel(latlng);
        temp.setDraggable(true);
        mMarkerList.add(temp);
    }


    Marker mOrbitMarker;

    @Override
    public void updateOrbit(double lat, double lot) {
        if (null != mOrbitMarker) {
            mOrbitMarker.destroy();
        }

        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(new LatLng(lat, lot));
        markerOption.draggable(false);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.favor_marker_point);
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
        mOrbitMarker = mAmap.addMarker(markerOption);

    }

    @Override
    public void resetMap() {
        if (null != mOrbitMarker) {
            mOrbitMarker.destroy();
        }
        if (null != polyLines) {
            for (Polyline line : polyLines) {
                line.remove();
            }
            polyLines.clear();

        }

        if (null != mMarkerList) {
            for (Marker marker : mMarkerList) {
                marker.destroy();
            }
            mMarkerList.clear();
        }
    }

    private Marker addMarkerWithLabel(LatLng latlng) {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latlng);
        markerOption.draggable(false);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.marker_point);
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
        return mAmap.addMarker(markerOption);
    }

    private List<Polyline> polyLines = new ArrayList<>();

    private Polyline addWayPointLine(LatLng start, LatLng end) {
        Polyline a = mAmap.addPolyline((new PolylineOptions()).add(start, end));
        a.setColor(Color.GREEN);
        a.setWidth(10);
        polyLines.add(a);
        return a;
    }

    Marker mDroneMarker;

    private void drawDroneMarker(LatLng dronell, AttitudeInfo info) {
        synchronized (AMapMissionActivity.class) {
            if (mDroneMarker == null) {
                MarkerOptions markerOption = new MarkerOptions();
                markerOption.position(dronell);
                markerOption.draggable(false);
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.drone_location_icon)));
                markerOption.anchor(0.5f, 0.5f);
                if (null != mAmap) {
                    mDroneMarker = mAmap.addMarker(markerOption);
                }
            } else {
                mDroneMarker.setPosition(dronell);
                mDroneMarker.setToTop();
            }
            if (null != info) {
                double degree = info.getYaw();
                if (degree < 0) {
                    degree = degree + 360;
                }
                if (mDroneMarker != null) {
                    if (mAmap.getCameraPosition() != null) {
                        mDroneMarker.setRotateAngle((float) degree);
                    }
                }
            }
        }
    }

    Marker mPhoneMarker;

    private void drawPhoneMarker(LatLng phonell) {
        if (mPhoneMarker == null) {
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(phonell);
            markerOption.draggable(false);
//            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),
//                    R.mipmap.drone_location_icon)));
//            markerOption.anchor(0.5f, 0.5f);
            mPhoneMarker = mAmap.addMarker(markerOption);
        } else {
            mPhoneMarker.setPosition(phonell);
        }
    }
}
