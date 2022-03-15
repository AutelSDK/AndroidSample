package com.autel.sdksample.base.camera.fragment.adapter;

import android.content.Context;

import com.autel.common.camera.media.PhotoBurstCount;
import com.autel.sdksample.base.adapter.SelectorAdapter;


public class PhotoBurstAdapter extends SelectorAdapter<PhotoBurstCount> {

    public PhotoBurstAdapter(Context context) {
        super(context);
        elementList.add(PhotoBurstCount.BURST_3);
        elementList.add(PhotoBurstCount.BURST_5);
        elementList.add(PhotoBurstCount.BURST_7);
    }
}
