package com.autel.sdksample.evo.mission.adapter;

import android.content.Context;

import com.autel.common.mission.evo.OrbitRotateDirection;
import com.autel.sdksample.base.adapter.SelectorAdapter;

public class EvoRotateDirectionAdapter extends SelectorAdapter<OrbitRotateDirection> {

    public EvoRotateDirectionAdapter(Context context) {
        super(context);
        elementList.add(OrbitRotateDirection.Clockwise);
        elementList.add(OrbitRotateDirection.Counterclockwise);
    }
}
