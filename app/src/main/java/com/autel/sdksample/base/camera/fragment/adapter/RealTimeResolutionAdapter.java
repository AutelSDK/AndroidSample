package com.autel.sdksample.base.camera.fragment.adapter;

import android.content.Context;

import com.autel.common.camera.xb015.RealTimeVideoResolution;
import com.autel.sdksample.base.adapter.SelectorAdapter;


public class RealTimeResolutionAdapter extends SelectorAdapter<RealTimeVideoResolution> {

    public RealTimeResolutionAdapter(Context context) {
        super(context);
        elementList.add(RealTimeVideoResolution.P_1280X720);
        elementList.add(RealTimeVideoResolution.P_1920X1080);
    }
}
