package com.autel.sdksample.base.camera.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autel.sdksample.R;


public class CameraNotConnectFragment extends CameraBaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_camera_not_connect, null);
        logOut(getString(R.string.camera_no_connect));
        return view;
    }
}
