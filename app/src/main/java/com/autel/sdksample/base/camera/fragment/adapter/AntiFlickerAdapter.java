package com.autel.sdksample.base.camera.fragment.adapter;

import android.content.Context;

import com.autel.common.camera.media.AntiFlicker;
import com.autel.sdksample.base.adapter.SelectorAdapter;

public class AntiFlickerAdapter extends SelectorAdapter<AntiFlicker> {

    public AntiFlickerAdapter(Context context) {
        super(context);
        elementList.add(AntiFlicker.AUTO);
        elementList.add(AntiFlicker.ANTI_FLICKER_50HZ);
        elementList.add(AntiFlicker.ANTI_FLICKER_60HZ);
    }
}
