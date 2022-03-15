package com.autel.sdksample.base.camera.fragment.adapter;

import android.content.Context;

import com.autel.common.camera.CameraProduct;
import com.autel.common.camera.media.ExposureMode;
import com.autel.sdksample.base.adapter.SelectorAdapter;

public class ModelcExposureModeAdapter extends SelectorAdapter<ExposureMode> {
    public ModelcExposureModeAdapter(Context context) {
        super(context);
    }

    public void setData(CameraProduct camera) {
        switch (camera){
            case XT701:
            case XT706:{
                elementList.add(ExposureMode.Auto);
                elementList.add(ExposureMode.Manual);
                elementList.add(ExposureMode.ShutterPriority);
            }
            break;
            case XT705:{
                elementList.add(ExposureMode.Auto);
                elementList.add(ExposureMode.Manual);
                elementList.add(ExposureMode.ShutterPriority);
                elementList.add(ExposureMode.AperturePriority);
            }
            break;

            default:
        }

    }
}
