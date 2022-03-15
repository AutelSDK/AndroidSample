package com.autel.sdksample.base.mission.adapter;

import android.content.Context;

import com.autel.common.mission.FollowFinishedAction;
import com.autel.sdksample.base.adapter.SelectorAdapter;

public class FollowFinishActionAdapter extends SelectorAdapter<FollowFinishedAction> {

    public FollowFinishActionAdapter(Context context) {
        super(context);
        elementList.add(FollowFinishedAction.RETURN_HOME);
        elementList.add(FollowFinishedAction.RETURN_TO_MY_LOCATION);
    }
}
