package com.autel.sdksample.base.camera.fragment.adapter;

import android.content.Context;

import com.autel.common.camera.media.ExposureMode;
import com.autel.sdksample.base.adapter.SelectorAdapter;

import java.util.List;

public class ExposureModeAdapter extends SelectorAdapter<ExposureMode> {
    public ExposureModeAdapter(Context context) {
        super(context);
    }

    public ExposureModeAdapter(Context context, List<ExposureMode> modes) {
        super(context);
        elementList = modes;

    }
}
