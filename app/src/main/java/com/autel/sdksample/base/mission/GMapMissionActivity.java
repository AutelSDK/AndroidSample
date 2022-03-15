package com.autel.sdksample.base.mission;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.autel.common.flycontroller.AttitudeInfo;
import com.autel.sdksample.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;


public class GMapMissionActivity extends MapActivity {
    final String TAG = getClass().getSimpleName();
    MapView gMapView;
    private GoogleMap mGmap;
    boolean isFirstChangeToPhone = true;
    private int mIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapsInitializer.initialize(this);
        super.onCreate(savedInstanceState);
        setMapContentView(R.layout.ac_mission_gmap);
        gMapView = (MapView) findViewById(R.id.gMapView);
        try {
            gMapView.onCreate(savedInstanceState);
        } catch (Exception e) {
        }
        gMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGmap = googleMap;
                mGmap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                attachTapListener();
            }
        });
    }

    public void onResume() {
        super.onResume();
        try {
            gMapView.onResume();
        } catch (Exception e) {
        }
    }


    public void onPause() {
        super.onPause();
        try {
            gMapView.onPause();
        } catch (Exception e) {
        }
    }


    public void onDestroy() {
        super.onDestroy();
        detachTapListener();
        try {
            gMapView.onDestroy();
        } catch (Exception e) {
        }
        resetUI();
    }

    private void resetUI() {
        mMarkerList.clear();
        mOrbitMarker = null;
        mDroneMarker = null;
        mPhoneMarker = null;
    }

    private void attachTapListener() {
        if(null != mGmap) {
            mGmap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    onAbsMapClick(latLng.latitude, latLng.longitude);
                }
            });
            mGmap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
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
    }

    private void detachTapListener() {
        mGmap.setOnMapClickListener(null);
    }

    @Override
    protected void phoneLocationChanged(Location location) {
        AutelLatLng all = MapRectifyUtil.wgs2gcj(new AutelLatLng(location.getLatitude(), location.getLongitude()));
        LatLng latLng = new LatLng(all.getLatitude(), all.getLongitude());
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (mGmap == null || mGmap.getCameraPosition() == null) {
            return;
        }
        if (isFirstChangeToPhone) {
            CameraPosition cp = new CameraPosition(latLng,
                    MapInitZoomSize,
                    0,
                    0);
            mGmap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));
            isFirstChangeToPhone = false;
        }
        drawPhoneMarker(latLng);
    }

    @Override
    protected void updateAircraftLocation(double lat, double lot, final AttitudeInfo info) {
        AutelLatLng latLng = MapRectifyUtil.wgs2gcj(new AutelLatLng(lat, lot));
        final LatLng lng = new LatLng(latLng.latitude, latLng.longitude);
        Log.d("YAW","yaw:"+info.getYaw()+"");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                drawDroneMarker(lng, info);
            }
        });
    }


    protected ArrayList<Marker> mMarkerList = new ArrayList<>();

    Marker mOrbitMarker;

    @Override
    public void updateOrbit(double lat, double lot) {
        if (null != mOrbitMarker) {
            mOrbitMarker.setPosition(new LatLng(lat, lot));
        } else {
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(new LatLng(lat, lot));
            markerOption.draggable(false);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.favor_marker_point);
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
            mOrbitMarker = mGmap.addMarker(markerOption);
        }
    }

    @Override
    public void resetMap() {
        mOrbitMarker = null;
        mDroneMarker = null;
        mPhoneMarker = null;

        mGmap.clear();
    }

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

    private Marker addMarkerWithLabel(LatLng latlng) {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latlng);
        markerOption.draggable(false);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.marker_point);
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
        return mGmap.addMarker(markerOption);
    }

    private Polyline addWayPointLine(LatLng start, LatLng end) {
        Polyline a = mGmap.addPolyline((new PolylineOptions()).add(start, end));
        a.setColor(Color.GREEN);
        a.setWidth(10);
        return a;
    }

    Marker mDroneMarker;

    private void drawDroneMarker(LatLng dronell, AttitudeInfo info) {
        if (mDroneMarker == null) {
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(dronell);
            markerOption.draggable(false);
            markerOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.drone_location_icon));
            markerOption.anchor(0.5f, 0.5f);
            if (null != mGmap) {
                mDroneMarker = mGmap.addMarker(markerOption);
            }
        } else {
            mDroneMarker.setPosition(dronell);
        }
        if (null != info) {
            double degree = info.getYaw();
            if (degree < 0) {
                degree = degree + 360;
            }
            Log.d("YAW","drawDroneMarker "+degree+" degree");
            if (mDroneMarker != null) {
                if (mGmap.getCameraPosition() != null) {
                    mDroneMarker.setRotation((float) degree);
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
            mPhoneMarker = mGmap.addMarker(markerOption);
        } else {
            mPhoneMarker.setPosition(phonell);
        }
    }
}
