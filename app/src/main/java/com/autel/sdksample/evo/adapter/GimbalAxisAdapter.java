package com.autel.sdksample.evo.adapter;

import android.content.Context;

import com.autel.common.gimbal.GimbalAxisType;
import com.autel.sdksample.base.adapter.SelectorAdapter;

public class GimbalAxisAdapter extends SelectorAdapter<GimbalAxisType> {

    public GimbalAxisAdapter(Context context) {
        super(context);
        elementList.add(GimbalAxisType.ROLL);
        elementList.add(GimbalAxisType.PITCH);
        elementList.add(GimbalAxisType.YAW);
    }
}
