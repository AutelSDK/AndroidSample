package com.autel.sdksample.base.mission;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.autel.sdksample.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;


public class MissionActivity extends AppCompatActivity {
    final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Mission");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_mission);
        findViewById(R.id.GMap).setVisibility(isGoogleMapValidate() ? View.VISIBLE : View.GONE);
    }

    public void gMap(View view) {
        startActivity(new Intent(this, GMapMissionActivity.class));
    }

    public void aMap(View view) {
        startActivity(new Intent(this, AMapMissionActivity.class));
    }

    private boolean isGoogleMapValidate() {
        int playStatus = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        String googleServices = GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE;
        try {
            getPackageManager().getPackageInfo(googleServices, 0);
            return playStatus == ConnectionResult.SUCCESS;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
