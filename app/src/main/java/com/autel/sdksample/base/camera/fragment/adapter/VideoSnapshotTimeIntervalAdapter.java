package com.autel.sdksample.base.camera.fragment.adapter;

import android.content.Context;

import com.autel.common.camera.media.VideoSnapshotTimelapseInterval;
import com.autel.sdksample.base.adapter.SelectorAdapter;


public class VideoSnapshotTimeIntervalAdapter extends SelectorAdapter<VideoSnapshotTimelapseInterval> {

    public VideoSnapshotTimeIntervalAdapter(Context context) {
        super(context);
        elementList.add(VideoSnapshotTimelapseInterval.SECOND_5);
        elementList.add(VideoSnapshotTimelapseInterval.SECOND_10);
        elementList.add(VideoSnapshotTimelapseInterval.SECOND_30);
        elementList.add(VideoSnapshotTimelapseInterval.SECOND_60);
    }
}
