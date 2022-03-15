package com.autel.sdksample.evo.mission.adapter;

import android.content.Context;

import com.autel.common.mission.evo.EvoWaypointFinishedAction;
import com.autel.sdksample.base.adapter.SelectorAdapter;

public class EvoWaypointFinishActionAdapter extends SelectorAdapter<EvoWaypointFinishedAction> {

    public EvoWaypointFinishActionAdapter(Context context) {
        super(context);
        elementList.add(EvoWaypointFinishedAction.KEEP_ON_LAST_POINT);
        elementList.add(EvoWaypointFinishedAction.LAND_ON_LAST_POINT);
        elementList.add(EvoWaypointFinishedAction.RETURN_HOME);
        elementList.add(EvoWaypointFinishedAction.RETURN_TO_FIRST_POINT);
        elementList.add(EvoWaypointFinishedAction.STOP_ON_LAST_POINT);
    }
}
