package com.autel.sdksample.base.mission.adapter;

import android.content.Context;

import com.autel.common.mission.OrbitFinishedAction;
import com.autel.sdksample.base.adapter.SelectorAdapter;

public class OrbitFinishActionAdapter extends SelectorAdapter<OrbitFinishedAction> {

    public OrbitFinishActionAdapter(Context context) {
        super(context);
        elementList.add(OrbitFinishedAction.HOVER);
        elementList.add(OrbitFinishedAction.RETURN_HOME);
    }
}
