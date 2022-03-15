package com.autel.sdksample.util;


public class CameraTestXb008 {
//    private final static String TAG = "FCTest";
//
//    public static void setCameraSDCardStateListener(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "supportAspectRatio  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().setSDCardStateListener(new CallbackWithOneParam<SDCardState>() {
//                    @Override
//                    public void onSuccess(SDCardState state) {
//                        logOut(handler, "setSDCardStateListener  state  " + state);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setSDCardStateListener  description  " + error.getDescription());
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().setSDCardStateListener(new CallbackWithOneParam<SDCardState>() {
//                    @Override
//                    public void onSuccess(SDCardState state) {
//                        logOut(handler, "setSDCardStateListener  state  " + state);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setSDCardStateListener  description  " + error.getDescription());
//                    }
//                });
//            }else{
//                logOut(handler, "setSDCardStateListener  description  camera type is not clear");
//            }
//        }
//
//    }
//
//    public static void resetCameraSDCardStateListener(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "supportAspectRatio  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().setSDCardStateListener(null);
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().setSDCardStateListener(null);
//            }else{
//                logOut(handler, "setSDCardStateListener  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void setCameraAutoExposureState(final CameraAutoExposureLockState lockState, final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "setCameraAutoExposureState  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().setAutoExposureLockState(lockState, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setAutoExposureLockState  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setAutoExposureLockState  lockState  " + lockState + " onSuccess ");
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().setAutoExposureLockState(lockState, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setAutoExposureLockState  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setAutoExposureLockState  lockState  " + lockState + " state " );
//                    }
//                });
//            }else{
//                logOut(handler, "setAutoExposureLockState  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getCameraAutoExposureState(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "setCameraAutoExposureState  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getAutoExposureLockState(new CallbackWithOneParam<CameraAutoExposureLockState>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getAutoExposureLockState  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(CameraAutoExposureLockState data) {
//                        logOut(handler, "getAutoExposureLockState  " + data);
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getAutoExposureLockState(new CallbackWithOneParam<CameraAutoExposureLockState>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getAutoExposureLockState  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(CameraAutoExposureLockState data) {
//                        logOut(handler, "getAutoExposureLockState  " + data);
//                    }
//                });
//            }else{
//                logOut(handler, "getAutoExposureLockState  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getPhotoExposureMode(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getExposureMode  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getExposureMode(new CallbackWithOneParam<CameraExposureMode>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getExposureMode  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(CameraExposureMode data) {
//                        logOut(handler, "getExposureMode  " + data);
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getExposureMode(new CallbackWithOneParam<CameraExposureMode>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getExposureMode  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(CameraExposureMode data) {
//                        logOut(handler, "getExposureMode  " + data);
//                    }
//                });
//            }else{
//                logOut(handler, "getExposureMode  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void setCameraGear(final CameraExposureMode gear, final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getExposureMode  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
////                AModuleCamera.cameraManager().getXb004().setExposureMode(gear, new CallbackWithNoParam() {
////                    @Override
////                    public void onFailure(AutelError error) {
////                        logOut(handler, "setExposureMode  description  " + error.getDescription());
////                    }
////
////                    @Override
////                    public void onSuccess() {
////                        logOut(handler, "setExposureMode  " + gear + "  state  onSuccess" );
////                    }
////                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().setExposureMode(gear, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setExposureMode  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setExposureMode  " + gear + "  state  onSuccess" );
//                    }
//                });
//            }else{
//                logOut(handler, "setExposureMode  description  camera type is not clear");
//            }
//        }
//
//    }
//
//    public static void setCameraSpotMeter(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "setSpotMeteringArea  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().setSpotMeteringArea(2, 3, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setSpotMeteringArea  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setSpotMeteringArea  " + "  state  onSuccess" );
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().setSpotMeteringArea(2, 3, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setSpotMeteringArea  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setSpotMeteringArea  " + "  state  onSuccess");
//                    }
//                });
//            }else{
//                logOut(handler, "setSpotMeteringArea  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getSpotMeterLocation(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getSpotMeteringArea  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getSpotMeteringArea(new CallbackWithOneParam<CameraSpotMeteringArea>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getSpotMeteringArea  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(CameraSpotMeteringArea data) {
//                        logOut(handler, "getSpotMeteringArea X " + data.X + "  Y " + data.Y);
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getSpotMeteringArea(new CallbackWithOneParam<CameraSpotMeteringArea>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getSpotMeteringArea  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(CameraSpotMeteringArea data) {
//                        logOut(handler, "getSpotMeteringArea X " + data.X + "  Y " + data.Y);
//                    }
//                });
//            }else{
//                logOut(handler, "getSpotMeteringArea  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void setCameraMode(MediaMode mode, final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getSpotMeteringArea  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().setMediaMode(mode, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setMediaMode  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setMediaMode state onSuccess");
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().setMediaMode(mode, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setMediaMode  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setMediaMode state onSuccess");
//                    }
//                });
//            }else{
//                logOut(handler, "setMediaMode  description  camera type is not clear");
//            }
//        }
//
//    }
//
//    public static void getExposure(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getExposure  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getExposure(new CallbackWithOneParam<CameraExposureCompensation>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getExposure  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(CameraExposureCompensation data) {
//                        logOut(handler, "getExposure " + data);
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getExposure(new CallbackWithOneParam<CameraExposureCompensation>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getExposure  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(CameraExposureCompensation data) {
//                        logOut(handler, "getExposure " + data);
//                    }
//                });
//            }else{
//                logOut(handler, "getExposure  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void setExposureValue(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "setExposure  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().setExposure(CameraExposureCompensation.NEGATIVE_0p3, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setExposure  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setExposure state onSuccess");
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().setExposure(CameraExposureCompensation.NEGATIVE_0p3, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setExposure  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setExposure state onSuccess");
//                    }
//                });
//            }else{
//                logOut(handler, "setExposure  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getISO(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getISO  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getISO(new CallbackWithOneParam<CameraISO>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getISO  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(CameraISO data) {
//                        logOut(handler, "getISO " + data);
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getISO(new CallbackWithOneParam<CameraISO>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getISO  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(CameraISO data) {
//                        logOut(handler, "getISO " + data);
//                    }
//                });
//            }else{
//                logOut(handler, "getISO  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void setISO(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "setISO  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().setISO(CameraISO.ISO_200, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setISO  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setISO state onSuccess");
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().setISO(CameraISO.ISO_200, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setISO  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setISO state onSuccess");
//                    }
//                });
//            }else{
//                logOut(handler, "setISO  description  camera type is not clear");
//            }
//        }
//
//    }
//
//    public static void getIRIS(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getIRIS  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                logOut(handler, "getIRIS  error: is not support");
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getIRIS(new CallbackWithOneParam<CameraAperture>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getIRIS  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(CameraAperture data) {
//                        logOut(handler, "getIRIS " + data);
//                    }
//                });
//            }else{
//                logOut(handler, "getIRIS  description  camera type is not clear");
//            }
//        }
//
//    }
//
//    public static void setIRIS(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "setIRIS  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                logOut(handler, "setIRIS  error: is not support");
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().setIRIS(CameraAperture.Aperture_2p0, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setIRIS  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setIRIS state onSuccess");
//                    }
//                });
//            }else{
//                logOut(handler, "setIRIS  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getShutter(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getShutter  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getShutter(new CallbackWithOneParam<CameraShutterSpeed>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getShutter  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(CameraShutterSpeed data) {
//                        logOut(handler, "getShutter " + data);
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getShutter(new CallbackWithOneParam<CameraShutterSpeed>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getShutter  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(CameraShutterSpeed data) {
//                        logOut(handler, "getShutter " + data);
//                    }
//                });
//            }else{
//                logOut(handler, "getShutter  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void setShutter(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "setShutter  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().setShutter(CameraShutterSpeed.ShutterSpeed_1_1dot25, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setShutter  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setShutter state onSuccess");
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().setShutter(CameraShutterSpeed.ShutterSpeed_1_1dot25, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setShutter  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setShutter state onSuccess");
//                    }
//                });
//            }else{
//                logOut(handler, "setShutter  description  camera type is not clear");
//            }
//        }
//
//    }
//
//    public static void getAspectRatio(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getAspectRatio  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getAspectRatio(new CallbackWithOneParam<CameraAspectRatio>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getAspectRatio  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(CameraAspectRatio data) {
//                        logOut(handler, "getAspectRatio " + data);
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getAspectRatio(new CallbackWithOneParam<CameraAspectRatio>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getAspectRatio  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(CameraAspectRatio data) {
//                        logOut(handler, "getAspectRatio " + data);
//                    }
//                });
//            }else{
//                logOut(handler, "getAspectRatio  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void setAspectRatio(CameraAspectRatio ratio, final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "setAspectRatio  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().setAspectRatio(ratio, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setAspectRatio  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setAspectRatio state onSuccess");
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().setAspectRatio(ratio, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setAspectRatio  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setAspectRatio state onSuccess");
//                    }
//                });
//            }else{
//                logOut(handler, "setAspectRatio  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void setPhotoFormat(PhotoFormat format, final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "setPhotoFormat  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().setPhotoFormat(format, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setPhotoFormat  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setPhotoFormat state onSuccess");
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().setPhotoFormat(format, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setPhotoFormat  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setPhotoFormat state onSuccess");
//                    }
//                });
//            }else{
//                logOut(handler, "setPhotoFormat  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getPhotoFormat(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getPhotoFormat  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getPhotoFormat(new CallbackWithOneParam<PhotoFormat>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getPhotoFormat  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(PhotoFormat data) {
//                        logOut(handler, "getPhotoFormat " + data);
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getPhotoFormat(new CallbackWithOneParam<PhotoFormat>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getPhotoFormat  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(PhotoFormat data) {
//                        logOut(handler, "getPhotoFormat " + data);
//                    }
//                });
//            }else{
//                logOut(handler, "getPhotoFormat  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getWhiteBalance(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getWhiteBalance  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getWhiteBalance(new CallbackWithOneParam<CameraWhiteBalance>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getWhiteBalance  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(CameraWhiteBalance data) {
//                        logOut(handler, "getWhiteBalance " + data.type + "  colorTemperature  " + data.colorTemperature);
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getWhiteBalance(new CallbackWithOneParam<CameraWhiteBalance>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getWhiteBalance  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(CameraWhiteBalance data) {
//                        logOut(handler, "getWhiteBalance " + data.type + "  colorTemperature  " + data.colorTemperature);
//                    }
//                });
//            }else{
//                logOut(handler, "getWhiteBalance  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void setWhiteBalance(CameraWhiteBalanceType whiteBalance, final Handler handler) {
//        CameraWhiteBalance whiteBalance1 = new CameraWhiteBalance();
//        whiteBalance1.type = whiteBalance;
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "setWhiteBalance  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().setWhiteBalance(whiteBalance1, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setWhiteBalance  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setWhiteBalance state onSuccess");
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().setWhiteBalance(whiteBalance1, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setWhiteBalance  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setWhiteBalance state onSuccess");
//                    }
//                });
//            }else{
//                logOut(handler, "setWhiteBalance  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void setCameraCustomWhiteBalance(final Handler handler) {
//        CameraWhiteBalance whiteBalance1 = new CameraWhiteBalance();
//        whiteBalance1.type = CameraWhiteBalanceType.CUSTOM;
//        whiteBalance1.colorTemperature = 2100;
//
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "setWhiteBalance  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().setWhiteBalance(whiteBalance1, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setWhiteBalance  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setWhiteBalance state onSuccess");
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().setWhiteBalance(whiteBalance1, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setWhiteBalance  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setWhiteBalance state onSuccess");
//                    }
//                });
//            }else{
//                logOut(handler, "setWhiteBalance  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void setCameraColor(CameraColorStyle cameraColor, final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "setColorStyle  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().setColorStyle(cameraColor, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setColorStyle  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setColorStyle state onSuccess");
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().setColorStyle(cameraColor, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setColorStyle  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setColorStyle state onSuccess");
//                    }
//                });
//            }else{
//                logOut(handler, "setColorStyle  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getVideoResolution(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getVideoResolutionAndFps  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getVideoResolutionAndFps(new CallbackWithOneParam<VideoResolutionAndFps>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getVideoResolutionAndFps  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(VideoResolutionAndFps data) {
//                        logOut(handler, "getVideoResolutionAndFps " + data);
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getVideoResolutionAndFps(new CallbackWithOneParam<VideoResolutionAndFps>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getVideoResolutionAndFps  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(VideoResolutionAndFps data) {
//                        logOut(handler, "getVideoResolutionAndFps " + data);
//                    }
//                });
//            }else{
//                logOut(handler, "getVideoResolutionAndFps  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void setVideoResolutionAndFrameRate(VideoResolutionAndFps videoResolution, final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "setVideoResolutionAndFrameRate  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().setVideoResolutionAndFrameRate(videoResolution, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setVideoResolutionAndFrameRate  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setVideoResolutionAndFrameRate state onSuccess");
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().setVideoResolutionAndFrameRate(videoResolution, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setVideoResolutionAndFrameRate  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setVideoResolutionAndFrameRate state onSuccess");
//                    }
//                });
//            }else{
//                logOut(handler, "setVideoResolutionAndFrameRate  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getVideoStandard(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getVideoStandard  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getVideoStandard(new CallbackWithOneParam<VideoStandard>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getVideoStandard  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(VideoStandard data) {
//                        logOut(handler, "getVideoStandard " + data);
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getVideoStandard(new CallbackWithOneParam<VideoStandard>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getVideoStandard  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(VideoStandard data) {
//                        logOut(handler, "getVideoStandard " + data);
//                    }
//                });
//            }else{
//                logOut(handler, "getVideoStandard  description  camera type is not clear");
//            }
//        }
//
//    }
//
//    public static void setCameraVideoStandard(VideoStandard videoStandard, final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "setVideoStandard  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().setVideoStandard(videoStandard, new CallbackWithNoParam() {
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setVideoStandard  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setVideoStandard state onSuccess");
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().setVideoStandard(videoStandard, new CallbackWithNoParam() {
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setVideoStandard  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setVideoStandard state onSuccess");
//                    }
//                });
//            }else{
//                logOut(handler, "setVideoStandard  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void setCameraVideoSubtitleEnable(boolean enable, final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "setVideoSubtitleEnable  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().setVideoSubtitleEnable(enable, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setVideoSubtitleEnable  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setVideoSubtitleEnable state onSuccess");
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().setVideoSubtitleEnable(enable, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setVideoSubtitleEnable  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setVideoSubtitleEnable state onSuccess");
//                    }
//                });
//            }else{
//                logOut(handler, "setVideoSubtitleEnable  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void is3DDenoiseEnable(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "is3DDenoiseEnable  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().is3DDenoiseEnable(new CallbackWithOneParam<Boolean>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "is3DDenoiseEnable  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(Boolean data) {
//                        logOut(handler, "is3DDenoiseEnable " + data);
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().is3DDenoiseEnable(new CallbackWithOneParam<Boolean>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "is3DDenoiseEnable  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(Boolean data) {
//                        logOut(handler, "is3DDenoiseEnable " + data);
//                    }
//                });
//            }else{
//                logOut(handler, "is3DDenoiseEnable  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void set3DNoiseReductionEnable(boolean enable, final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "set3DNoiseReductionEnable  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().set3DNoiseReductionEnable(enable, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "set3DNoiseReductionEnable  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "set3DNoiseReductionEnable state onSuccess");
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().set3DNoiseReductionEnable(enable, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "set3DNoiseReductionEnable  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "set3DNoiseReductionEnable state onSuccess");
//                    }
//                });
//            }else{
//                logOut(handler, "set3DNoiseReductionEnable  description  camera type is not clear");
//            }
//        }
//
//    }
//
//    public static void getAntiFlicker(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getAntiFlicker  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getAntiFlicker(new CallbackWithOneParam<CameraAntiFlicker>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getAntiFlicker  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(CameraAntiFlicker data) {
//                        logOut(handler, "getAntiFlicker " + data);
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getAntiFlicker(new CallbackWithOneParam<CameraAntiFlicker>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getAntiFlicker  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(CameraAntiFlicker data) {
//                        logOut(handler, "getAntiFlicker " + data);
//                    }
//                });
//            }else{
//                logOut(handler, "getAntiFlicker  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void setAntiFlicker(CameraAntiFlicker flicker, final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "setAntiFlicker  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().setAntiFlicker(flicker, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setAntiFlicker  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setAntiFlicker state onSuccess");
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().setAntiFlicker(flicker, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setAntiFlicker  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setAntiFlicker state onSuccess");
//                    }
//                });
//            }else{
//                logOut(handler, "setAntiFlicker  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void setPhotoStyle(PhotoStyleType styleType, final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "setPhotoStyle  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().setPhotoStyle(styleType, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setPhotoStyle  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setPhotoStyle state onSuccess");
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().setPhotoStyle(styleType, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setPhotoStyle  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setPhotoStyle state onSuccess");
//                    }
//                });
//            }else{
//                logOut(handler, "setPhotoStyle  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void setCameraCustomPhotoStyle(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "setPhotoStyle  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().setPhotoStyle(1, 2, 3, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setPhotoStyle  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setPhotoStyle state onSuccess");
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().setPhotoStyle(1, 2, 3, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setPhotoStyle  description  " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setPhotoStyle state onSuccess");
//                    }
//                });
//            }else{
//                logOut(handler, "setPhotoStyle  description  camera type is not clear");
//            }
//        }
//    }
//
//
//    public static void startTakePhoto(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "startTakePhoto  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().startTakePhoto(new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "startTakePhoto " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "startTakePhoto onSuccess");
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().startTakePhoto(new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "startTakePhoto " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "startTakePhoto onSuccess");
//                    }
//                });
//            }else{
//                logOut(handler, "startTakePhoto  description  camera type is not clear");
//            }
//        }
//    }
//
//
//    public static void stopTakePhoto(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getSpotMeteringArea  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().stopTakePhoto(new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "stopTakePhoto " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "stopTakePhoto onSuccess");
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().stopTakePhoto(new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "stopTakePhoto " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "stopTakePhoto onSuccess");
//                    }
//                });
//            }else{
//                logOut(handler, "setMediaMode  description  camera type is not clear");
//            }
//        }
//    }
//
//
//    public static void startRecordVideo(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getSpotMeteringArea  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().startRecordVideo(new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "startRecordVideo " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "startRecordVideo onSuccess");
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().startRecordVideo(new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "startRecordVideo " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "startRecordVideo onSuccess");
//                    }
//                });
//            }else{
//                logOut(handler, "startRecordVideo  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void stopRecordVideo(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "stopRecordVideo  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().stopRecordVideo(new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "stopRecordVideo " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "stopRecordVideo onSuccess");
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().stopRecordVideo(new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "stopRecordVideo " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "stopRecordVideo onSuccess");
//                    }
//                });
//            }else{
//                logOut(handler, "stopRecordVideo  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void formatSDCard(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "formatSDCard  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().formatSDCard(new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "formatSDCard " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "formatSDCard onSuccess");
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().formatSDCard(new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "formatSDCard " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "formatSDCard onSuccess");
//                    }
//                });
//            }else{
//                logOut(handler, "formatSDCard  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void resetCamera(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "resetDefaults  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().resetDefaults(new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "resetDefaults " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "resetDefaults onSuccess");
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().resetDefaults(new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "resetDefaults " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "resetDefaults onSuccess");
//                    }
//                });
//            }else{
//                logOut(handler, "resetDefaults  description  camera type is not clear");
//            }
//        }
//    }
//
////    public static void getCameraStatus(final Handler handler) {
////        AModuleCamera.camera().getStatusData(new CallbackWithOneParam<CameraStatus>() {
////            @Override
////            public void onFailure(AutelError error) {
////                logOut(handler, "getStatusData " + error.getDescription());
////            }
////
////            @Override
////            public void onSuccess(CameraStatus data) {
////                logOut(handler, "getStatusData " + data);
////            }
////        });
////    }
////
////    public static void getCameraSetting(final Handler handler) {
////        AModuleCamera.camera().getSetting(new CallbackWithOneParam<CameraSetting>() {
////            @Override
////            public void onFailure(AutelError error) {
////                logOut(handler, "getSetting " + error.getDescription());
////            }
////
////            @Override
////            public void onSuccess(CameraSetting data) {
////                logOut(handler, "getSetting " + data);
////            }
////        });
////    }
//
//
//    public static void getCameraCurrentVideoRecordTime(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getCurrentRecordTimeSeconds  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getCurrentRecordTimeSeconds(new CallbackWithOneParam<Integer>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getCurrentRecordTimeSeconds " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(Integer data) {
//                        logOut(handler, "getCurrentRecordTimeSeconds " + data);
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getCurrentRecordTimeSeconds(new CallbackWithOneParam<Integer>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getCurrentRecordTimeSeconds " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(Integer data) {
//                        logOut(handler, "getCurrentRecordTimeSeconds " + data);
//                    }
//                });
//            }else{
//                logOut(handler, "getCurrentRecordTimeSeconds  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void takePhotoWithFocus(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "startTakePhotoWithFocus  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                logOut(handler, "getFocusMode  error: is not suppoert for xb004");
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().startTakePhotoWithFocus(new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "startTakePhotoWithFocus " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "startTakePhotoWithFocus onSuccess");
//                    }
//                });
//            }else{
//                logOut(handler, "startTakePhotoWithFocus  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void setCameraFocusMode(CameraLensFocusMode mode, final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "setFocusMode  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                logOut(handler, "setFocusMode  error: is not suppoert for xb004");
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().setFocusMode(mode, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setFocusMode " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setFocusMode onSuccess");
//                    }
//                });
//            }else{
//                logOut(handler, "setFocusMode  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getCameraFocus(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getFocusMode  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                logOut(handler, "getFocusMode  error: is not suppoert for xb004");
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getFocusMode(new CallbackWithOneParam<CameraLensFocusMode>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getFocusMode " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(CameraLensFocusMode data) {
//                        logOut(handler, "getFocusMode " + data);
//                    }
//                });
//            }else{
//                logOut(handler, "getFocusMode  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void setCameraZoomFactor(int value, final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "setDigitalZoomScale  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                logOut(handler, "setDigitalZoomScale  error: is not suppoert for xb004");
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().setDigitalZoomScale(value, new CallbackWithNoParam() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setDigitalZoomScale " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        logOut(handler, "setDigitalZoomScale onSuccess");
//                    }
//                });
//            }else{
//                logOut(handler, "setDigitalZoomScale  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void setCameraConnectStateListener(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "setConnectStateListener  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().setConnectStateListener(new CallbackWithOneParam<Boolean>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setConnectStateListener camera error " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(Boolean data) {
//                        logOut(handler, data ? "camera connected" : "camera disconnect");
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().setConnectStateListener(new CallbackWithOneParam<Boolean>() {
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "setConnectStateListener camera error " + error.getDescription());
//                    }
//
//                    @Override
//                    public void onSuccess(Boolean data) {
//                        logOut(handler, data ? "camera connected" : "camera disconnect");
//                    }
//                });
//            }else{
//                logOut(handler, "setConnectStateListener  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void resetCameraConnectStateListener(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getSpotMeteringArea  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().setConnectStateListener(null);
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().setConnectStateListener(null);
//            }else{
//                logOut(handler, "setMediaMode  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getDigitalZoomScale(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getDigitalZoomScale  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                logOut(handler, "getDigitalZoomScale  error: is not suppoert for xb004");
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getDigitalZoomScale(new CallbackWithOneParam<Integer>() {
//                    @Override
//                    public void onSuccess(Integer data) {
//                        logOut(handler, "getDigitalZoomScale " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getDigitalZoomScale " + error.getDescription());
//                    }
//                });
//            }else{
//                logOut(handler, "getDigitalZoomScale  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getAutoExposureLockState(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getAutoExposureLockState  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                logOut(handler, "getDigitalZoomScale  error: is not suppoert for xb004");
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getDigitalZoomScale(new CallbackWithOneParam<Integer>() {
//                    @Override
//                    public void onSuccess(Integer data) {
//                        logOut(handler, "getAutoExposureLockState " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getAutoExposureLockState " + error.getDescription());
//                    }
//                });
//            }else{
//                logOut(handler, "getAutoExposureLockState  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getWorkState(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getWorkState  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getWorkState(new CallbackWithOneParam<CameraWorkState>() {
//                    @Override
//                    public void onSuccess(CameraWorkState data) {
//                        logOut(handler, "getWorkState " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getWorkState " + error.getDescription());
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getWorkState(new CallbackWithOneParam<CameraWorkState>() {
//                    @Override
//                    public void onSuccess(CameraWorkState data) {
//                        logOut(handler, "getWorkState " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getWorkState " + error.getDescription());
//                    }
//                });
//            }else{
//                logOut(handler, "getWorkState  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getSDCardState(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getSDCardState  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getSDCardState(new CallbackWithOneParam<SDCardState>() {
//                    @Override
//                    public void onSuccess(SDCardState data) {
//                        logOut(handler, "getSDCardState " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getSDCardState " + error.getDescription());
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getSDCardState(new CallbackWithOneParam<SDCardState>() {
//                    @Override
//                    public void onSuccess(SDCardState data) {
//                        logOut(handler, "getSDCardState " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getSDCardState " + error.getDescription());
//                    }
//                });
//            }else{
//                logOut(handler, "getSDCardState  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getMediaMode(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getMediaMode  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getMediaMode(new CallbackWithOneParam<MediaMode>() {
//                    @Override
//                    public void onSuccess(MediaMode data) {
//                        logOut(handler, "getMediaMode " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getMediaMode " + error.getDescription());
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getMediaMode(new CallbackWithOneParam<MediaMode>() {
//                    @Override
//                    public void onSuccess(MediaMode data) {
//                        logOut(handler, "getMediaMode " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getMediaMode " + error.getDescription());
//                    }
//                });
//            }else{
//                logOut(handler, "getMediaMode  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getVideoSum(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getVideoSum  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getVideoSum(new CallbackWithOneParam<VideoSum>() {
//                    @Override
//                    public void onSuccess(VideoSum data) {
//                        logOut(handler, "getVideoSum " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getVideoSum " + error.getDescription());
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getVideoSum(new CallbackWithOneParam<VideoSum>() {
//                    @Override
//                    public void onSuccess(VideoSum data) {
//                        logOut(handler, "getVideoSum " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getVideoSum " + error.getDescription());
//                    }
//                });
//            }else{
//                logOut(handler, "getVideoSum  description  camera type is not clear");
//            }
//        }
//
//    }
//
//    public static void getPhotoSumCanTake(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getPhotoSumCanTake  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getPhotoSumCanTake(new CallbackWithOneParam<PhotoSum>() {
//                    @Override
//                    public void onSuccess(PhotoSum data) {
//                        logOut(handler, "getPhotoSumCanTake " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getPhotoSumCanTake " + error.getDescription());
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getPhotoSumCanTake(new CallbackWithOneParam<PhotoSum>() {
//                    @Override
//                    public void onSuccess(PhotoSum data) {
//                        logOut(handler, "getPhotoSumCanTake " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getPhotoSumCanTake " + error.getDescription());
//                    }
//                });
//            }else{
//                logOut(handler, "getPhotoSumCanTake  description  camera type is not clear");
//            }
//        }
//
//    }
//
//    public static void getSdFreeSpace(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getSDCardFreeSpace  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getSDCardFreeSpace(new CallbackWithOneParam<Long>() {
//                    @Override
//                    public void onSuccess(Long data) {
//                        logOut(handler, "getSDCardFreeSpace " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getSDCardFreeSpace " + error.getDescription());
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getSDCardFreeSpace(new CallbackWithOneParam<Long>() {
//                    @Override
//                    public void onSuccess(Long data) {
//                        logOut(handler, "getSDCardFreeSpace " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getSDCardFreeSpace " + error.getDescription());
//                    }
//                });
//            }else{
//                logOut(handler, "getSDCardFreeSpace  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getCurrentRecordTimeSeconds(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getCurrentRecordTimeSeconds  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getCurrentRecordTimeSeconds(new CallbackWithOneParam<Integer>() {
//                    @Override
//                    public void onSuccess(Integer data) {
//                        logOut(handler, "getCurrentRecordTimeSeconds " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getCurrentRecordTimeSeconds " + error.getDescription());
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getCurrentRecordTimeSeconds(new CallbackWithOneParam<Integer>() {
//                    @Override
//                    public void onSuccess(Integer data) {
//                        logOut(handler, "getCurrentRecordTimeSeconds " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getCurrentRecordTimeSeconds " + error.getDescription());
//                    }
//                });
//            }else{
//                logOut(handler, "getCurrentRecordTimeSeconds  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getVideoResolutionAndFps(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getVideoResolutionAndFps  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getVideoResolutionAndFps(new CallbackWithOneParam<VideoResolutionAndFps>() {
//                    @Override
//                    public void onSuccess(VideoResolutionAndFps data) {
//                        logOut(handler, "getVideoResolutionAndFps " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getVideoResolutionAndFps " + error.getDescription());
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getVideoResolutionAndFps(new CallbackWithOneParam<VideoResolutionAndFps>() {
//                    @Override
//                    public void onSuccess(VideoResolutionAndFps data) {
//                        logOut(handler, "getVideoResolutionAndFps " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getVideoResolutionAndFps " + error.getDescription());
//                    }
//                });
//            }else{
//                logOut(handler, "getVideoResolutionAndFps  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getNickName(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getNickName  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getNickName(new CallbackWithOneParam<String>() {
//                    @Override
//                    public void onSuccess(String data) {
//                        logOut(handler, "getNickName " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getNickName " + error.getDescription());
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getNickName(new CallbackWithOneParam<String>() {
//                    @Override
//                    public void onSuccess(String data) {
//                        logOut(handler, "getNickName " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getNickName " + error.getDescription());
//                    }
//                });
//            }else{
//                logOut(handler, "getNickName  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getProduct(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getSpotMeteringArea  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                logOut(handler, "getProduct " + AModuleCamera.cameraManager().getXb004().getProduct());
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                logOut(handler, "getProduct " + AModuleCamera.cameraManager().getXb008().getProduct());
//            }else{
//                logOut(handler, "setMediaMode  description  camera type is not clear");
//            }
//        }
//
//
//    }
//
//    public static void isSubtitleSwitchEnable(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "isSubtitleSwitchEnable  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().isSubtitleSwitchEnable(new CallbackWithOneParam<Boolean>() {
//                    @Override
//                    public void onSuccess(Boolean data) {
//                        logOut(handler, "isSubtitleSwitchEnable " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "isSubtitleSwitchEnable " + error.getDescription());
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().isSubtitleSwitchEnable(new CallbackWithOneParam<Boolean>() {
//                    @Override
//                    public void onSuccess(Boolean data) {
//                        logOut(handler, "isSubtitleSwitchEnable " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "isSubtitleSwitchEnable " + error.getDescription());
//                    }
//                });
//            }else{
//                logOut(handler, "isSubtitleSwitchEnable  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getTimeStamp(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getTimeStamp  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getTimeStamp(new CallbackWithOneParam<Long>() {
//                    @Override
//                    public void onSuccess(Long data) {
//                        logOut(handler, "getTimeStamp " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getTimeStamp " + error.getDescription());
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getTimeStamp(new CallbackWithOneParam<Long>() {
//                    @Override
//                    public void onSuccess(Long data) {
//                        logOut(handler, "getTimeStamp " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getTimeStamp " + error.getDescription());
//                    }
//                });
//            }else{
//                logOut(handler, "getTimeStamp  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getVideoFormat(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getVideoFormat  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getVideoFormat(new CallbackWithOneParam<VideoFormat>() {
//                    @Override
//                    public void onSuccess(VideoFormat data) {
//                        logOut(handler, "getVideoFormat " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getVideoFormat " + error.getDescription());
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getVideoFormat(new CallbackWithOneParam<VideoFormat>() {
//                    @Override
//                    public void onSuccess(VideoFormat data) {
//                        logOut(handler, "getVideoFormat " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getVideoFormat " + error.getDescription());
//                    }
//                });
//            }else{
//                logOut(handler, "getVideoFormat  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getBurstNum(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getBurstCount  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getBurstCount(new CallbackWithOneParam<PhotoBurstCount>() {
//                    @Override
//                    public void onSuccess(PhotoBurstCount data) {
//                        logOut(handler, "getBurstCount " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getBurstCount " + error.getDescription());
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getBurstCount(new CallbackWithOneParam<PhotoBurstCount>() {
//                    @Override
//                    public void onSuccess(PhotoBurstCount data) {
//                        logOut(handler, "getBurstCount " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getBurstCount " + error.getDescription());
//                    }
//                });
//            }else{
//                logOut(handler, "getBurstCount  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getPhotoTimelapseInterval(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getPhotoTimelapseInterval  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getPhotoTimelapseInterval(new CallbackWithOneParam<PhotoTimelapseInterval>() {
//                    @Override
//                    public void onSuccess(PhotoTimelapseInterval data) {
//                        logOut(handler, "getPhotoTimelapseInterval " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getPhotoTimelapseInterval " + error.getDescription());
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getPhotoTimelapseInterval(new CallbackWithOneParam<PhotoTimelapseInterval>() {
//                    @Override
//                    public void onSuccess(PhotoTimelapseInterval data) {
//                        logOut(handler, "getPhotoTimelapseInterval " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getPhotoTimelapseInterval " + error.getDescription());
//                    }
//                });
//            }else{
//                logOut(handler, "getPhotoTimelapseInterval  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getVersion(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getVersion  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getVersion(new CallbackWithOneParam<String>() {
//                    @Override
//                    public void onSuccess(String data) {
//                        logOut(handler, "getVersion " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getVersion " + error.getDescription());
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getVersion(new CallbackWithOneParam<String>() {
//                    @Override
//                    public void onSuccess(String data) {
//                        logOut(handler, "getVersion " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getVersion " + error.getDescription());
//                    }
//                });
//            }else{
//                logOut(handler, "getVersion  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void isHistogramEnable(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "isHistogramEnable  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().isHistogramEnable(new CallbackWithOneParam<Boolean>() {
//                    @Override
//                    public void onSuccess(Boolean data) {
//                        logOut(handler, "isHistogramEnable " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "isHistogramEnable " + error.getDescription());
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().isHistogramEnable(new CallbackWithOneParam<Boolean>() {
//                    @Override
//                    public void onSuccess(Boolean data) {
//                        logOut(handler, "isHistogramEnable " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "isHistogramEnable " + error.getDescription());
//                    }
//                });
//            }else{
//                logOut(handler, "isHistogramEnable  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getPhotoStyle(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getPhotoStyle  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getPhotoStyle(new CallbackWithOneParam<PhotoStyle>() {
//                    @Override
//                    public void onSuccess(PhotoStyle data) {
//                        logOut(handler, "getPhotoStyle " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getPhotoStyle " + error.getDescription());
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getPhotoStyle(new CallbackWithOneParam<PhotoStyle>() {
//                    @Override
//                    public void onSuccess(PhotoStyle data) {
//                        logOut(handler, "getPhotoStyle " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getPhotoStyle " + error.getDescription());
//                    }
//                });
//            }else{
//                logOut(handler, "getPhotoStyle  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getColorStyle(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getColorStyle  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                AModuleCamera.cameraManager().getXb004().getColorStyle(new CallbackWithOneParam<CameraColorStyle>() {
//                    @Override
//                    public void onSuccess(CameraColorStyle data) {
//                        logOut(handler, "getColorStyle " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getColorStyle " + error.getDescription());
//                    }
//                });
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getColorStyle(new CallbackWithOneParam<CameraColorStyle>() {
//                    @Override
//                    public void onSuccess(CameraColorStyle data) {
//                        logOut(handler, "getColorStyle " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getColorStyle " + error.getDescription());
//                    }
//                });
//            }else{
//                logOut(handler, "getColorStyle  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getFocusMode(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getFocusMode  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                logOut(handler, "getFocusMode is not support for xb004");
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getFocusMode(new CallbackWithOneParam<CameraLensFocusMode>() {
//                    @Override
//                    public void onSuccess(CameraLensFocusMode data) {
//                        logOut(handler, "getFocusMode " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getFocusMode " + error.getDescription());
//                    }
//                });
//            }else{
//                logOut(handler, "getFocusMode  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getFocusDistance(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getFocusDistance  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                logOut(handler, "getFocusDistance is not support for xb004");
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getFocusDistance(new CallbackWithOneParam<CameraManualFocusDistance>() {
//                    @Override
//                    public void onSuccess(CameraManualFocusDistance data) {
//                        logOut(handler, "getFocusDistance " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getFocusDistance " + error.getDescription());
//                    }
//                });
//            }else{
//                logOut(handler, "getFocusDistance  description  camera type is not clear");
//            }
//        }
//    }
//
//    public static void getColorTemperature(final Handler handler) {
//        if (AModuleCamera.cameraManager().getParameterRangeManager().getError() != null) {
//            logOut(handler, "getSpotMeteringArea  error: " + AModuleCamera.cameraManager().getParameterRangeManager().getError().getDescription());
//        } else {
//            if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.R12){
//                logOut(handler, "getColorTemperature is not support for xb004");
//            } else if(AModuleCamera.cameraManager().getParameterRangeManager().getResult().getCurrentProduct() == CameraProduct.XB008){
//                AModuleCamera.cameraManager().getXb008().getColorTemperature(new CallbackWithOneParam<Integer>() {
//                    @Override
//                    public void onSuccess(Integer data) {
//                        logOut(handler, "getColorTemperature " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut(handler, "getColorTemperature " + error.getDescription());
//                    }
//                });
//            }else{
//                logOut(handler, "getColorTemperature  description  camera type is not clear");
//            }
//        }
//    }
//
//
//    private static void logOut(Handler handler, String log) {
//        Log.v(TAG, log);
//        Message msg = handler.obtainMessage();
//        msg.obj = log;
//        handler.sendMessage(msg);
//    }
}
