package com.autel.sdksample.base.camera.fragment.adapter;

import android.content.Context;

import com.autel.sdksample.base.adapter.SelectorAdapter;


public class BooleanTypeAdapter extends SelectorAdapter<Boolean> {

    public BooleanTypeAdapter(Context context) {
        super(context);
        elementList.add(Boolean.FALSE);
        elementList.add(Boolean.TRUE);
    }
}
