package com.autel.sdksample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 监听USB连接设备的广播
 */
public class UsbBroadCastReceiver extends BroadcastReceiver {
    final String TAG = getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        /**
         * 只会监听由USB连接到设备的广播，接收到连接到设备的广播后启动主页
         */
        Log.v(TAG, "action " + intent.getAction());
        ProductActivity.receiveUsbStartCommand(context);
    }
}

