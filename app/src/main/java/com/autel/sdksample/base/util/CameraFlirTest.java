package com.autel.sdksample.base.util;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.autel.common.CallbackWithNoParam;
import com.autel.common.CallbackWithOneParam;
import com.autel.common.error.AutelError;
import com.autel.internal.camera.AutelFlirDuo;
import com.autel.internal.camera.AutelFlirDuoR;
import com.autel.internal.sdk.camera.flir.FLIRDisplayMode;
import com.autel.internal.sdk.camera.flir.FLIRIRMSXSetting;
import com.autel.internal.sdk.camera.flir.FLIRPhotoSetting;
import com.autel.internal.sdk.camera.flir.FLIRRecordSetting;
import com.autel.internal.sdk.camera.flir.IRColorMode;
import com.autel.internal.sdk.camera.flirInternal.FLIRRadiometrySetting;


public class CameraFlirTest {
    private final static String TAG = "CameraFlirTest";

    public static void setDisplayMode(FLIRDisplayMode mode, final Handler handler, AutelFlirDuo AutelFlirDuo) {
        AutelFlirDuo.setDisplayMode(mode, new CallbackWithNoParam() {
            @Override
            public void onSuccess() {
                logOut(handler, "setDisplayMode  onSuccess  ");
            }

            @Override
            public void onFailure(AutelError error) {
                logOut(handler, "setDisplayMode  description  " + error.getDescription());
            }
        });
    }


    public static void getDisplayMode(final Handler handler, AutelFlirDuo AutelFlirDuo) {
        AutelFlirDuo.getDisplayMode(new CallbackWithOneParam<FLIRDisplayMode>() {
            @Override
            public void onSuccess(FLIRDisplayMode data) {
                logOut(handler, "getDisplayMode  onSuccess  " + data);
            }

            @Override
            public void onFailure(AutelError error) {
                logOut(handler, "getDisplayMode  description  " + error.getDescription());
            }
        });
    }


    public static void setIRColorMode(IRColorMode colorMode, final Handler handler, AutelFlirDuo AutelFlirDuo) {
        AutelFlirDuo.setIRColorMode(colorMode, new CallbackWithNoParam() {
            @Override
            public void onSuccess() {
                logOut(handler, "getDisplayMode  onSuccess  ");
            }

            @Override
            public void onFailure(AutelError error) {
                logOut(handler, "setIRColorMode  description  " + error.getDescription());
            }
        });
    }


    public static void getIRColorMode(final Handler handler, AutelFlirDuo AutelFlirDuo) {
        AutelFlirDuo.getIRColorMode(new CallbackWithOneParam<IRColorMode>() {
            @Override
            public void onSuccess(IRColorMode data) {
                logOut(handler, "getIRColorMode  onSuccess  " + data);
            }

            @Override
            public void onFailure(AutelError error) {
                logOut(handler, "getIRColorMode  description  " + error.getDescription());
            }
        });
    }

    public static void setIRMSX(FLIRIRMSXSetting irmsxSetting, final Handler handler, AutelFlirDuo AutelFlirDuo) {
        AutelFlirDuo.setIRMSX(irmsxSetting, new CallbackWithNoParam() {
            @Override
            public void onSuccess() {
                logOut(handler, "setIRMSX  onSuccess  ");
            }

            @Override
            public void onFailure(AutelError error) {
                logOut(handler, "setIRMSX  description  " + error.getDescription());
            }
        });
    }

    public static void getIRMSX(final Handler handler, AutelFlirDuo AutelFlirDuo) {
        AutelFlirDuo.getIRMSX(new CallbackWithOneParam<FLIRIRMSXSetting>() {
            @Override
            public void onSuccess(FLIRIRMSXSetting data) {
                logOut(handler, "getIRMSX  onSuccess  " + data);
            }

            @Override
            public void onFailure(AutelError error) {
                logOut(handler, "getIRMSX  description  " + error.getDescription());
            }
        });
    }

    public static void setRecordingFormat(FLIRRecordSetting flirRecordSetting, final Handler handler, AutelFlirDuo AutelFlirDuo) {
        AutelFlirDuo.setRecordingFormat(flirRecordSetting, new CallbackWithNoParam() {
            @Override
            public void onSuccess() {
                logOut(handler, "setRecordingFormat  onSuccess  ");
            }

            @Override
            public void onFailure(AutelError error) {
                logOut(handler, "setRecordingFormat  description  " + error.getDescription());
            }
        });
    }

    public static void getRecordingFormat(final Handler handler, AutelFlirDuo AutelFlirDuo) {
        AutelFlirDuo.getRecordingFormat(new CallbackWithOneParam<FLIRRecordSetting>() {
            @Override
            public void onSuccess(FLIRRecordSetting data) {
                logOut(handler, "getRecordingFormat  onSuccess  " + data);
            }

            @Override
            public void onFailure(AutelError error) {
                logOut(handler, "getRecordingFormat  description  " + error.getDescription());
            }
        });
    }

    public static void setPhotoFormat(FLIRPhotoSetting photoSetting, final Handler handler, AutelFlirDuo AutelFlirDuo) {
        AutelFlirDuo.setPhotoFormat(photoSetting, new CallbackWithNoParam() {
            @Override
            public void onSuccess() {
                logOut(handler, "setPhotoFormat  onSuccess  ");
            }

            @Override
            public void onFailure(AutelError error) {
                logOut(handler, "setRecordingFormat  description  " + error.getDescription());
            }
        });
    }

    public static void getPhotoFormat(final Handler handler, AutelFlirDuo AutelFlirDuo) {
        AutelFlirDuo.getPhotoFormat(new CallbackWithOneParam<FLIRPhotoSetting>() {
            @Override
            public void onSuccess(FLIRPhotoSetting data) {
                logOut(handler, "getPhotoFormat  onSuccess  " + data);
            }

            @Override
            public void onFailure(AutelError error) {
                logOut(handler, "getPhotoFormat  description  " + error.getDescription());
            }
        });
    }
    public static void getRadiometrySettings(final Handler handler, AutelFlirDuoR AutelFlirDuoR) {
        AutelFlirDuoR.getRadiometrySetting(new CallbackWithOneParam<FLIRRadiometrySetting>() {
            @Override
            public void onSuccess(FLIRRadiometrySetting data) {
                logOut(handler, "getRadiometrySetting  onSuccess  " + data);
            }

            @Override
            public void onFailure(AutelError error) {
                logOut(handler, "getRadiometrySetting  description  " + error.getDescription());
            }
        });
    }

    public static void setRadiometrySettings(final Handler handler, AutelFlirDuoR AutelFlirDuo) {
        FLIRRadiometrySetting settings = new FLIRRadiometrySetting();
        settings.setSpotMeter(true);
        AutelFlirDuo.setRadiometrySetting(settings, new CallbackWithNoParam() {
            @Override
            public void onSuccess() {
                logOut(handler, "setRadiometrySetting  onSuccess  ");
            }

            @Override
            public void onFailure(AutelError error) {
                logOut(handler, "getRadiometrySetting  description  " + error.getDescription());
            }
        });
    }

    private static void logOut(Handler handler, String log) {
        Log.v(TAG, log);
        Message msg = handler.obtainMessage();
        msg.obj = log;
        handler.sendMessage(msg);
    }
}
