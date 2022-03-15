package com.autel.sdksample.base.camera.fragment.adapter;

import android.content.Context;

import com.autel.common.camera.XT706.IrColor;
import com.autel.sdksample.base.adapter.SelectorAdapter;


public class IrColorStyleAdapter extends SelectorAdapter<IrColor> {

    public IrColorStyleAdapter(Context context) {
        super(context);
        mContext = context;
        elementList.add(IrColor.WhiteHot);
        elementList.add(IrColor.BlackHot);
        elementList.add(IrColor.RainBow);
        elementList.add(IrColor.RainHC);
        elementList.add(IrColor.IronBow);
        elementList.add(IrColor.Lava);
        elementList.add(IrColor.Arctic);
        elementList.add(IrColor.GlowBow);
        elementList.add(IrColor.GradedFire);
        elementList.add(IrColor.HotTest);
    }
}
