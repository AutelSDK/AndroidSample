package com.autel.sdksample.base.mission.adapter;

import android.content.Context;

import com.autel.common.mission.xstar.WaypointFinishedAction;
import com.autel.sdksample.base.adapter.SelectorAdapter;

public class WaypointFinishActionAdapter extends SelectorAdapter<WaypointFinishedAction> {

    public WaypointFinishActionAdapter(Context context) {
        super(context);
        elementList.add(WaypointFinishedAction.HOVER);
        elementList.add(WaypointFinishedAction.RETURN_HOME);
    }
}
