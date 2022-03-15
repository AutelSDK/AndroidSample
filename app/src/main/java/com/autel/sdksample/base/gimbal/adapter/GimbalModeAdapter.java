package com.autel.sdksample.base.gimbal.adapter;

import android.content.Context;

import com.autel.common.gimbal.GimbalWorkMode;
import com.autel.sdksample.base.adapter.SelectorAdapter;


public class GimbalModeAdapter extends SelectorAdapter<GimbalWorkMode> {

    public GimbalModeAdapter(Context context) {
        super(context);
        elementList.add(GimbalWorkMode.FPV);
        elementList.add(GimbalWorkMode.STABILIZED);
    }
}
