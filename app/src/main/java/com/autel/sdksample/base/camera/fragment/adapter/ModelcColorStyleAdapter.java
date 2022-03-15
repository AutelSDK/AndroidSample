package com.autel.sdksample.base.camera.fragment.adapter;

import android.content.Context;

import com.autel.common.camera.media.ColorStyle;
import com.autel.sdksample.base.adapter.SelectorAdapter;

import static com.autel.common.camera.media.ColorStyle.BlackAndWhite;
import static com.autel.common.camera.media.ColorStyle.Log;
import static com.autel.common.camera.media.ColorStyle.None;
import static com.autel.common.camera.media.ColorStyle.Nostalgic;

public class ModelcColorStyleAdapter extends SelectorAdapter<ColorStyle> {

    public ModelcColorStyleAdapter(Context context) {
        super(context);
        mContext = context;
        elementList.add(None);
        elementList.add(Log);
        elementList.add(BlackAndWhite);
        elementList.add(Nostalgic);
    }
}
