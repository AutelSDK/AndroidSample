package com.autel.sdksample.base.camera.fragment.adapter;

import android.content.Context;

import com.autel.common.camera.media.AutoExposureLockState;
import com.autel.sdksample.base.adapter.SelectorAdapter;


public class AutoExposureLockStateAdapter extends SelectorAdapter<AutoExposureLockState> {

    public AutoExposureLockStateAdapter(Context context) {
        super(context);
        elementList.add(AutoExposureLockState.LOCK);
        elementList.add(AutoExposureLockState.UNLOCK);
        elementList.add(AutoExposureLockState.DISABLE);
    }
}
