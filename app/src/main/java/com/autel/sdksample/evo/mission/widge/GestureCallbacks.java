package com.autel.sdksample.evo.mission.widge;


import com.autel.sdksample.evo.mission.bean.AutelGPSLatLng;

import java.util.List;

public interface GestureCallbacks {

        void projectMarker(List<AutelGPSLatLng> path);

        int getRemainMax();
}