package com.autel.sdksample.evo.mission.adapter;

import android.content.Context;

import com.autel.common.mission.evo.OrbitEntryDirection;
import com.autel.sdksample.base.adapter.SelectorAdapter;

public class EvoEntryDirectionAdapter extends SelectorAdapter<OrbitEntryDirection> {

    public EvoEntryDirectionAdapter(Context context) {
        super(context);
        elementList.add(OrbitEntryDirection.EAST);
        elementList.add(OrbitEntryDirection.NEAREST);
        elementList.add(OrbitEntryDirection.NORTH);
        elementList.add(OrbitEntryDirection.SOUTH);
        elementList.add(OrbitEntryDirection.WEST);
    }
}
