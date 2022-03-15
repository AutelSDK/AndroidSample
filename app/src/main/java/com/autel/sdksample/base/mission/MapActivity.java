package com.autel.sdksample.base.mission;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.autel.common.CallbackWithOneParam;
import com.autel.common.error.AutelError;
import com.autel.common.flycontroller.AttitudeInfo;
import com.autel.common.flycontroller.evo.EvoFlyControllerInfo;
import com.autel.common.mission.AutelCoordinate3D;
import com.autel.common.mission.AutelMission;
import com.autel.internal.flycontroller.evo.bean.G2FlyControllerInfoImpl;
import com.autel.sdk.flycontroller.Evo2FlyController;
import com.autel.sdk.mission.MissionManager;
import com.autel.sdk.product.BaseProduct;
import com.autel.sdk.product.Evo2Aircraft;
import com.autel.sdksample.R;
import com.autel.sdksample.TestApplication;
import com.autel.sdksample.base.mission.fragment.MissionFragment;
import com.autel.sdksample.base.mission.fragment.MissionOperatorFragment;
import com.autel.sdksample.evo.mission.fragment.EvoFollowMissionFragment;
import com.autel.sdksample.evo.mission.fragment.EvoOrbitMissionFragment;
import com.autel.sdksample.evo.mission.fragment.EvoWaypointFragment;
import com.autel.sdksample.xstar.mission.XStarFollowMissionFragment;
import com.autel.sdksample.xstar.mission.XStarOrbitMissionFragment;
import com.autel.sdksample.xstar.mission.XStarWaypointFragment;
import com.autel.util.log.AutelLog;


public abstract class MapActivity extends FragmentActivity implements MapOperator {
    public interface LocationChangeListener {
        void locationChanged(Location location);
    }

    public interface WaypointHeightListener {
        int fetchHeight();
    }

    public static final float MapInitZoomSize = 18.0f;
    final String TAG = getClass().getSimpleName();
    LocationManager mLocationManager;
    LocationListener mLocationListener;
    LocationListener netLocationListener;
    MissionManager missionManager;

    Spinner missionTypeSpinner;
    TextView flyModeInfo;
    TextView missionModeInfo;
    TextView flyControllerInfo;
    TextView logInfo;

    MissionType missionType = MissionType.WAYPOINT;

    private LocationChangeListener locationChangeListener;
    private WaypointHeightListener waypointHeightListener;
    Evo2FlyController xStarFlyController;
    boolean flyInfoShow;
    volatile long flyInfoNotify;
    long clickStamp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_mission_map);
        registerPhoneGPS();
        initUi();
        initAircraftListener();
    }

    public void onDestroy() {
        super.onDestroy();
        if (null != xStarFlyController) {
//            xStarFlyController.setFlyControllerInfoListener(null);
        }
    }


    private void initUi() {
        missionTypeSpinner = (Spinner) findViewById(R.id.missionTypeSpinner);
        missionTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("spinnertest", "position " + position);
                missionType = MissionType.find(position);
                changePage(missionType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        flyModeInfo = (TextView) findViewById(R.id.flyModeInfo);
        logInfo = (TextView) findViewById(R.id.logInfo);
        logInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long current = System.currentTimeMillis();
                if (current - clickStamp < 1500) {
                    logInfo.setVisibility(View.GONE);
                }
                clickStamp = current;
            }
        });
        missionModeInfo = (TextView) findViewById(R.id.missionInfo);
        flyControllerInfo = (TextView) findViewById(R.id.flyControllerInfo);
        ((Switch) findViewById(R.id.flyInfoSwitch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                flyInfoShow = isChecked;
                flyControllerInfo.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.mission_operator, new MissionOperatorFragment()).commit();
        ;
    }

    public void updateLogInfo(final String log) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (null != logInfo) {
                    logInfo.setVisibility(View.VISIBLE);
                    logInfo.setText(log);
                }
            }
        });
    }

    public void updateMissionInfo(final String info) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                missionModeInfo.setText(info);
            }
        });
    }


    private void changePage(MissionType type) {
        BaseProduct baseProduct = ((TestApplication) getApplicationContext()).getCurrentProduct();
        if (baseProduct == null) {
            switch (type) {
                case WAYPOINT:
                    getSupportFragmentManager().beginTransaction().replace(R.id.mission_item_content, new EvoWaypointFragment(this)).commit();
                    break;
                case ORBIT:
                    getSupportFragmentManager().beginTransaction().replace(R.id.mission_item_content, new EvoOrbitMissionFragment(this)).commit();
                    break;
                case FOLLOW:
                    getSupportFragmentManager().beginTransaction().replace(R.id.mission_item_content, new EvoFollowMissionFragment(this)).commit();
                    break;
                default:
            }
            return;
        }
        switch (baseProduct.getType()) {
            case EVO:
                switch (type) {
                    case WAYPOINT:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mission_item_content, new EvoWaypointFragment(this)).commit();
                        break;
                    case ORBIT:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mission_item_content, new EvoOrbitMissionFragment(this)).commit();
                        break;
                    case FOLLOW:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mission_item_content, new EvoFollowMissionFragment(this)).commit();
                        break;
                    default:
                }
                break;
            case X_STAR:
            case PREMIUM:
                switch (type) {
                    case WAYPOINT:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mission_item_content, new XStarWaypointFragment(this)).commit();
                        break;
                    case ORBIT:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mission_item_content, new XStarOrbitMissionFragment(this)).commit();
                        break;
                    case FOLLOW:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mission_item_content, new XStarFollowMissionFragment(this)).commit();
                        break;
                    default:
                }
                break;
        }

        resetMap();
    }

    public AutelMission createMission() {
        return ((MissionFragment) getSupportFragmentManager().findFragmentById(R.id.mission_item_content)).createAutelMission();
    }

    protected void setMapContentView(@LayoutRes int layoutResID) {
        ((ViewGroup) findViewById(R.id.layoutParent)).addView(View.inflate(this, layoutResID, null), 0);
    }

    private void initAircraftListener() {
        BaseProduct baseProduct = ((TestApplication) getApplicationContext()).getCurrentProduct();
        if (null != baseProduct) {
            switch (baseProduct.getType()) {
                case X_STAR:
//                    xStarFlyController = ((XStarAircraft) baseProduct).getFlyController();
                    break;
                case PREMIUM:
//                    xStarFlyController = ((XStarPremiumAircraft) baseProduct).getFlyController();
                    break;
                case EVO_2:
                    xStarFlyController = ((Evo2Aircraft) baseProduct).getFlyController();
                    break;
            }
            if (null != xStarFlyController) {
                xStarFlyController.setFlyControllerInfoListener(new CallbackWithOneParam<EvoFlyControllerInfo>() {
                    @Override
                    public void onSuccess(EvoFlyControllerInfo flyControllerInfo) {
                        if (System.currentTimeMillis() - flyInfoNotify > 1000) {
                            flyInfoNotify = System.currentTimeMillis();
                            Message msg = handler.obtainMessage();
                            msg.obj = flyControllerInfo;
                            handler.sendMessage(msg);
                            if (null != flyControllerInfo.getGpsInfo()) {
                                AutelCoordinate3D coord3D = new AutelCoordinate3D(flyControllerInfo.getGpsInfo().getLatitude(),flyControllerInfo.getGpsInfo().getLongitude(),flyControllerInfo.getGpsInfo().getAltitude());
                                if (null != coord3D) {
                                    updateAircraftLocation(coord3D.getLatitude(), coord3D.getLongitude(), flyControllerInfo.getAttitudeInfo());
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(AutelError autelError) {
                        Log.v("initAircraftListener", " setFlyControllerInfoListener " + autelError.getDescription());
                    }
                });
            }
        }
    }

    public String getLowAccuracyProvider() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_LOW);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return mLocationManager.getBestProvider(criteria, true);
    }

    private void registerPhoneGPS() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                AutelLog.e("PhoneGPS", "status = " + status);
            }

            @Override
            public void onProviderEnabled(String provider) {
                AutelLog.e("PhoneGPS", "onProviderEnabled  " + provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                AutelLog.e("PhoneGPS", "onProviderDisabled  " + provider);
            }

            @Override
            public void onLocationChanged(Location location) {
                notifyPhoneLocationChanged(location);
            }
        };

        netLocationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                notifyPhoneLocationChanged(location);
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
        };

        try {
            if (mLocationManager != null) {
                if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
                    } else {
                        mLocationManager.requestLocationUpdates(getLowAccuracyProvider(), 500, 0, mLocationListener);
                        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 0, netLocationListener);
                        AutelLog.e("PhoneGPS", "status = " + "isProviderEnabled GPS_PROVIDER");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void notifyPhoneLocationChanged(Location location) {
        phoneLocationChanged(location);
        if (null != locationChangeListener) {
            locationChangeListener.locationChanged(location);
        }
    }


    public void setLocationChangeListener(LocationChangeListener locationChangeListener) {
        this.locationChangeListener = locationChangeListener;
    }

    public void setWaypointHeightListener(WaypointHeightListener waypointHeightListener) {
        this.waypointHeightListener = waypointHeightListener;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 123:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                mLocationManager.requestLocationUpdates(getLowAccuracyProvider(), 500, 0, mLocationListener);
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 0, netLocationListener);
        }
    }

    protected void onAbsMapClick(double lat, double lot) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.mission_item_content);
        if (fragment instanceof MissionFragment) {
            ((MissionFragment) fragment).onMapClick(lat, lot);
        }
    }

    protected void markerClick(int position) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.mission_item_content);
        if (fragment instanceof MissionFragment) {
            ((MissionFragment) fragment).onMarkerClick(position);
        }
    }

    public void setMissionContainerVisible(boolean visible) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.mission_item_content);
        if (null == fragment) {
            return;
        }

        if (visible) {
            getSupportFragmentManager().beginTransaction().show(fragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().hide(fragment).commit();
        }
    }

//    private int getMaxWaypointHeight() {
//        return waypointHeightListener == null ? 40 : waypointHeightListener.fetchHeight();
//    }
//
//    protected int addWaypoint(AutelLatLng latLng) {
////        AutelLatLng latLng1 = MapRectifyUtil.gcj2wgs(latLng);
//        AutelCoordinate3D cd = new AutelCoordinate3D(latLng.latitude, latLng.longitude, getMaxWaypointHeight());
//        Waypoint wp = new Waypoint(cd);
//        wayPointList.add(wp);
//        return wayPointList.indexOf(wp);
//    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            G2FlyControllerInfoImpl flyControllerData = (G2FlyControllerInfoImpl) msg.obj;
            if (flyInfoShow) {
                flyControllerInfo.setText(flyControllerData.toString());
            }
            flyModeInfo.setText("FlyMode : " + flyControllerData.getFlyControllerStatus().getFlyMode()+"  yaw "+flyControllerData.getAttitudeInfo().getYaw());
        }
    };

    protected abstract void phoneLocationChanged(Location location);

    protected abstract void updateAircraftLocation(double lat, double lot, AttitudeInfo attitudeInfo);
}
