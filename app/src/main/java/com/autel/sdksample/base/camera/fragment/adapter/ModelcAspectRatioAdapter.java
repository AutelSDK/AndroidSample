package com.autel.sdksample.base.camera.fragment.adapter;

import android.content.Context;

import com.autel.common.camera.CameraProduct;
import com.autel.common.camera.XT706.DisplayMode;
import com.autel.common.camera.media.PhotoAspectRatio;
import com.autel.sdksample.base.adapter.SelectorAdapter;

public class ModelcAspectRatioAdapter extends SelectorAdapter<String> {

    public ModelcAspectRatioAdapter(Context context) {
        super(context);
    }

    public void setData(CameraProduct camera, DisplayMode displayMode){
        elementList.clear();
        switch (camera){
            case XT701: {
                elementList.add(PhotoAspectRatio.Aspect_4_3.value(CameraProduct.XT701));
                elementList.add(PhotoAspectRatio.Aspect_16_9.value(CameraProduct.XT701));
                elementList.add(PhotoAspectRatio.Aspect_4_3_xt.value(CameraProduct.XT701));
                elementList.add(PhotoAspectRatio.Aspect_16_9_HDR.value(CameraProduct.XT701));
            }
            break;

            case XT705:{
                elementList.add(PhotoAspectRatio.Aspect_16_9.value(CameraProduct.XT705));
                elementList.add(PhotoAspectRatio.Aspect_3_2.value(CameraProduct.XT705));
                elementList.add(PhotoAspectRatio.Aspect_16_9_HDR.value(CameraProduct.XT705));
            }
            break;

            case XT706:{
                if(displayMode == DisplayMode.IR) {
                    elementList.add(PhotoAspectRatio.Aspect_640_512.value(CameraProduct.XT706));

                }else if(displayMode == DisplayMode.PICTURE_IN_PICTURE){
                    elementList.add(PhotoAspectRatio.Aspect_1920_1080.value(CameraProduct.XT706));
                    elementList.add(PhotoAspectRatio.Aspect_1280_720.value(CameraProduct.XT706));

                }else {
                    elementList.add(PhotoAspectRatio.Aspect_4_3.value(CameraProduct.XT706));
                    elementList.add(PhotoAspectRatio.Aspect_16_9.value(CameraProduct.XT706));
                    elementList.add(PhotoAspectRatio.Aspect_4_3_xt.value(CameraProduct.XT706));
                    elementList.add(PhotoAspectRatio.Aspect_16_9_HDR.value(CameraProduct.XT706));
                }
            }
            break;

            default:
        }
    }
}
