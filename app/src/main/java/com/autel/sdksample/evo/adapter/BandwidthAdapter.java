package com.autel.sdksample.evo.adapter;

import android.content.Context;

import com.autel.common.dsp.evo.Bandwidth;
import com.autel.sdksample.base.adapter.SelectorAdapter;

public class BandwidthAdapter extends SelectorAdapter<Bandwidth> {

    public BandwidthAdapter(Context context) {
        super(context);
        elementList.add(Bandwidth.WIDTH_3M);
        elementList.add(Bandwidth.WIDTH_5M);
        elementList.add(Bandwidth.WIDTH_10M);
        elementList.add(Bandwidth.WIDTH_20M);
        elementList.add(Bandwidth.UNKNOWN);
    }
}
