package com.autel.sdksample.base.camera.fragment.adapter;

import android.content.Context;

import com.autel.common.camera.media.WhiteBalanceType;
import com.autel.sdksample.base.adapter.SelectorAdapter;

import java.util.List;


public class WhiteBalanceTypeAdapter extends SelectorAdapter<WhiteBalanceType> {

    public WhiteBalanceTypeAdapter(Context context) {
        super(context);
    }

    public WhiteBalanceTypeAdapter(Context context, List<WhiteBalanceType> list) {
        super(context);
        elementList = list;
    }
}
