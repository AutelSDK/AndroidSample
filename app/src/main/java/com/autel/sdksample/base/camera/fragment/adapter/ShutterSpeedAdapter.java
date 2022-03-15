package com.autel.sdksample.base.camera.fragment.adapter;

import android.content.Context;

import com.autel.common.camera.media.ShutterSpeed;
import com.autel.sdksample.base.adapter.SelectorAdapter;

import java.util.List;


public class ShutterSpeedAdapter extends SelectorAdapter<ShutterSpeed> {

    public ShutterSpeedAdapter(Context context, List<ShutterSpeed> shutterSpeedList) {
        super(context);
        elementList = shutterSpeedList;
    }
}
