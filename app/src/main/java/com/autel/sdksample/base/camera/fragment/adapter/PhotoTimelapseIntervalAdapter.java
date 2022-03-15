package com.autel.sdksample.base.camera.fragment.adapter;

import android.content.Context;

import com.autel.common.camera.media.PhotoTimelapseInterval;
import com.autel.sdksample.base.adapter.SelectorAdapter;

import java.util.List;


public class PhotoTimelapseIntervalAdapter extends SelectorAdapter<PhotoTimelapseInterval> {

    public PhotoTimelapseIntervalAdapter(Context context, List<PhotoTimelapseInterval> data) {
        super(context);
        elementList.addAll(data);
    }
}
