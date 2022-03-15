package com.autel.sdksample.evo.adapter;

import android.content.Context;

import com.autel.common.dsp.evo.TransferMode;
import com.autel.sdksample.base.adapter.SelectorAdapter;

public class TransferModeAdapter extends SelectorAdapter<TransferMode> {

    public TransferModeAdapter(Context context) {
        super(context);
        elementList.add(TransferMode.FLUENCY);
        elementList.add(TransferMode.HIGH_DEFINITION);
        elementList.add(TransferMode.NORMAL);
        elementList.add(TransferMode.UNKNOWN);
    }
}
