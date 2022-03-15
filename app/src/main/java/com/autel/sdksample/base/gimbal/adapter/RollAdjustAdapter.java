package com.autel.sdksample.base.gimbal.adapter;

import android.content.Context;

import com.autel.common.gimbal.GimbalRollAngleAdjust;
import com.autel.sdksample.base.adapter.SelectorAdapter;


public class RollAdjustAdapter extends SelectorAdapter<GimbalRollAngleAdjust> {

    public RollAdjustAdapter(Context context) {
        super(context);
        elementList.add(GimbalRollAngleAdjust.MINUS);
        elementList.add(GimbalRollAngleAdjust.PLUS);
        elementList.add(GimbalRollAngleAdjust.QUERY);
        elementList.add(GimbalRollAngleAdjust.RESET);
    }
}
