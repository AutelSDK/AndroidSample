package com.autel.sdksample.evo.mission.adapter;

import android.content.Context;

import com.autel.common.mission.evo.OrbitHeadingDirection;
import com.autel.sdksample.base.adapter.SelectorAdapter;

public class EvoHeadingDirectionAdapter extends SelectorAdapter<OrbitHeadingDirection> {

    public EvoHeadingDirectionAdapter(Context context) {
        super(context);
        elementList.add(OrbitHeadingDirection.BACKWARD);
        elementList.add(OrbitHeadingDirection.CUSTOM);
        elementList.add(OrbitHeadingDirection.FACE_TO_POI);
        elementList.add(OrbitHeadingDirection.FORWARD);
        elementList.add(OrbitHeadingDirection.OUTWARD);

    }
}
