package com.autel.sdksample.base.camera.fragment.adapter;

import android.content.Context;

import com.autel.common.camera.media.ExposureMode;
import com.autel.common.camera.xb015.RealTimeVideoResolution;
import com.autel.sdksample.base.adapter.SelectorAdapter;

import java.util.List;

public class RtkCoordinateAdapter extends SelectorAdapter<String> {

    public RtkCoordinateAdapter(Context context,List<String> mode) {
        super(context);
        elementList = mode;

    }

    public RtkCoordinateAdapter(Context context) {
        super(context);
    }
}
