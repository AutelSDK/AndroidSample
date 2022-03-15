package com.autel.sdksample.evo.mission.util;


import android.content.Context;

/**
 * Created by A16343 on 2016/7/5.
 */
@SuppressWarnings("DefaultFileTemplate")
public class AutelConfigManager {
    private static AutelConfigManager autelConfigManager;

    private AutelConfigManager() {

    }

    public static AutelConfigManager instance() {
        if (null == autelConfigManager) {
            synchronized (AutelConfigManager.class) {
                if (null == autelConfigManager) {
                    autelConfigManager = new AutelConfigManager();
                }
            }
        }
        return autelConfigManager;
    }
    private Context mContext;

    private void initManager(Context context) {
        this.mContext = context;
    }

    public static void init(Context context) {
        instance().initManager(context);
    }

    public Context getAppContext() {
        return mContext;
    }

}
