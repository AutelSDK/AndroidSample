package com.autel.sdksample.base.camera.fragment.adapter;

import android.content.Context;

import com.autel.common.camera.media.CameraAperture;
import com.autel.sdksample.base.adapter.SelectorAdapter;


public class CameraApertureAdapter extends SelectorAdapter<CameraAperture> {

    public CameraApertureAdapter(Context context) {
        super(context);
        elementList.add(CameraAperture.Aperture_2p8);
        elementList.add(CameraAperture.Aperture_3p2);
        elementList.add(CameraAperture.Aperture_3p5);
        elementList.add(CameraAperture.Aperture_4p0);
        elementList.add(CameraAperture.Aperture_4p5);
        elementList.add(CameraAperture.Aperture_5p0);
        elementList.add(CameraAperture.Aperture_5p6);
        elementList.add(CameraAperture.Aperture_6p3);
        elementList.add(CameraAperture.Aperture_7p1);
        elementList.add(CameraAperture.Aperture_8p0);
        elementList.add(CameraAperture.Aperture_9p0);
        elementList.add(CameraAperture.Aperture_10);
        elementList.add(CameraAperture.Aperture_11);
    }
}
