package com.autel.sdksample.base.camera.fragment.adapter;

import android.content.Context;

import com.autel.common.camera.media.VideoResolutionAndFps;
import com.autel.sdksample.base.adapter.SelectorAdapter;

import java.util.List;


public class VideoResolutionFpsAdapter extends SelectorAdapter<VideoResolutionAndFps> {

    public VideoResolutionFpsAdapter(Context context) {
        super(context);
    }

    public VideoResolutionFpsAdapter(Context context, List<VideoResolutionAndFps> list) {
        super(context);
        elementList = list;
    }
}
