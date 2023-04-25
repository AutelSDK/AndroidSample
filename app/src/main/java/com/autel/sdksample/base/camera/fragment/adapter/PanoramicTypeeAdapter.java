package com.autel.sdksample.base.camera.fragment.adapter;

import android.content.Context;

import com.autel.common.camera.media.PanoramicType;
import com.autel.common.camera.media.PhotoBurstCount;
import com.autel.sdksample.base.adapter.SelectorAdapter;


public class PanoramicTypeeAdapter extends SelectorAdapter<PanoramicType> {

    public PanoramicTypeeAdapter(Context context) {
        super(context);
        elementList.add(PanoramicType.HORIZONTAL);
        elementList.add(PanoramicType.VERTICAL);
        elementList.add(PanoramicType.WIDE_ANGLE);
        elementList.add(PanoramicType.SPHERICAL);
    }
}
