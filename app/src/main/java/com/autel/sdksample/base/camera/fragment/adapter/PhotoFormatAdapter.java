package com.autel.sdksample.base.camera.fragment.adapter;

import android.content.Context;

import com.autel.common.camera.base.PhotoFormat;
import com.autel.sdksample.base.adapter.SelectorAdapter;


public class PhotoFormatAdapter extends SelectorAdapter<PhotoFormat> {

    public PhotoFormatAdapter(Context context) {
        super(context);
        elementList.add(PhotoFormat.JPEG);
        elementList.add(PhotoFormat.RAW);
        elementList.add(PhotoFormat.RawAndJPEG);
    }

}
