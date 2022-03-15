package com.autel.sdksample.base.camera.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.autel.common.CallbackWithNoParam;
import com.autel.common.CallbackWithOneParam;
import com.autel.common.RangePair;
import com.autel.common.camera.base.BaseStateInfo;
import com.autel.common.camera.base.MediaMode;
import com.autel.common.camera.base.PhotoFormat;
import com.autel.common.camera.media.AntiFlicker;
import com.autel.common.camera.media.AutoExposureLockState;
import com.autel.common.camera.media.CameraISO;
import com.autel.common.camera.media.ColorStyle;
import com.autel.common.camera.media.ExposureCompensation;
import com.autel.common.camera.media.ExposureMode;
import com.autel.common.camera.media.PhotoAEBCount;
import com.autel.common.camera.media.PhotoAspectRatio;
import com.autel.common.camera.media.PhotoBurstCount;
import com.autel.common.camera.media.PhotoStyle;
import com.autel.common.camera.media.PhotoStyleType;
import com.autel.common.camera.media.PhotoTimelapseInterval;
import com.autel.common.camera.media.ShutterSpeed;
import com.autel.common.camera.media.SpotMeteringArea;
import com.autel.common.camera.media.VideoFormat;
import com.autel.common.camera.media.VideoResolutionAndFps;
import com.autel.common.camera.media.VideoStandard;
import com.autel.common.camera.media.WhiteBalance;
import com.autel.common.camera.media.WhiteBalanceType;
import com.autel.common.camera.r12.R12CameraInfo;
import com.autel.common.camera.r12.R12ParameterRangeManager;
import com.autel.common.error.AutelError;
import com.autel.sdk.camera.AutelR12;
import com.autel.sdksample.R;
import com.autel.sdksample.base.camera.CameraActivity;
import com.autel.sdksample.base.camera.fragment.adapter.AntiFlickerAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.AspectRatioAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.AutoExposureLockStateAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.ColorStyleAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.ExposureModeAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.ExposureValueAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.ISOValueAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.PhotoAEBCountAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.PhotoBurstAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.PhotoFormatAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.PhotoStyleAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.PhotoTimelapseIntervalAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.ShutterSpeedAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.VideoFormatAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.VideoResolutionFpsAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.VideoStandardAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.WhiteBalanceTypeAdapter;

import java.util.Arrays;

public class CameraR12Fragment extends CameraBaseFragment {
    AutelR12 autelR12;

    Button setCameraColor;
    Spinner colorStyleList;

    Button setExposureMode;
    Spinner exposureModeList;

    EditText spotMeteringAreaX;
    EditText spotMeteringAreaY;

    EditText photoCustomStyleContrast;
    EditText photoCustomStyleSaturation;
    EditText photoCustomStyleSharpness;

    Spinner exposureValueList;

    ShutterSpeedAdapter shutterSpeedAdapter;
    Spinner shutterList;

    VideoResolutionFpsAdapter videoResolutionFpsAdapter;
    Spinner videoResolutionAndFrameRateList;

    ColorStyle cameraColorStyle = ColorStyle.None;
    ExposureMode cameraExposureMode = ExposureMode.Auto;
    ExposureCompensation cameraExposureCompensation = ExposureCompensation.NEGATIVE_0p3;
    CameraISO cameraISO = CameraISO.ISO_100;
    ShutterSpeed cameraShutterSpeed = ShutterSpeed.ShutterSpeed_1;
    WhiteBalanceType cameraWhiteBalanceType = WhiteBalanceType.AUTO;
    AntiFlicker cameraAntiFlicker = AntiFlicker.AUTO;
    AutoExposureLockState cameraAutoExposureLockState = AutoExposureLockState.LOCK;
    PhotoStyleType photoStyleType = PhotoStyleType.Standard;
    PhotoBurstCount photoBurstCount = PhotoBurstCount.BURST_3;
    PhotoTimelapseInterval photoTimelapseInterval = PhotoTimelapseInterval.SECOND_5;
    PhotoAEBCount photoAEBCount = PhotoAEBCount.CAPTURE_3;
    VideoFormat videoFormat = VideoFormat.MOV;
    VideoStandard selectedVideoStandard = VideoStandard.NTSC;
    VideoStandard currentVideoStandard = VideoStandard.UNKNOWN;
    PhotoFormat photoFormat = PhotoFormat.JPEG;
    PhotoAspectRatio aspectRatio = PhotoAspectRatio.Aspect_16_9;
    VideoResolutionAndFps videoResolutionAndFps = null;
    VideoResolutionAndFps currentVideoResolutionAndFps = null;

    private R12ParameterRangeManager rangeManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_camera_r12, null);
        autelR12 = (AutelR12) ((CameraActivity) getActivity()).getCurrentCamera();
        rangeManager = autelR12.getParameterRangeManager();
        logOut("");
        initView(view);
        initClick(view);
        initR12Click(view);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        }, 600);

        return view;
    }

    private void initData() {

        if (null != autelR12) {

            autelR12.getVideoResolutionAndFrameRate(new CallbackWithOneParam<VideoResolutionAndFps>() {
                @Override
                public void onFailure(AutelError error) {
                }

                @Override
                public void onSuccess(VideoResolutionAndFps data) {
                    currentVideoResolutionAndFps = data;
                    initShuttleSpeedList();
                }
            });
            autelR12.getVideoStandard(new CallbackWithOneParam<VideoStandard>() {
                @Override
                public void onFailure(AutelError error) {
                }

                @Override
                public void onSuccess(VideoStandard data) {
                    currentVideoStandard = data;
                    initVideoResolutionFpsList();
                }
            });
        }
    }

    private void initR12Click(View view) {
        view.findViewById(R.id.getStateInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.getStateInfo(new CallbackWithOneParam<BaseStateInfo>() {
                    @Override
                    public void onSuccess(BaseStateInfo state) {
                        logOut("getStateInfo  :" + state);
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getStateInfo  description  " + error.getDescription());
                    }
                });
            }
        });

        view.findViewById(R.id.setInfoListener).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.setInfoListener(new CallbackWithOneParam<R12CameraInfo>() {
                    @Override
                    public void onSuccess(R12CameraInfo state) {
                        logOut("setInfoListener  :" + state);
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setInfoListener  description  " + error.getDescription());
                    }
                });
            }
        });

        view.findViewById(R.id.resetInfoListener).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.setInfoListener(null);
            }
        });

        view.findViewById(R.id.getMediaMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.getMediaMode(new CallbackWithOneParam<MediaMode>() {
                    @Override
                    public void onSuccess(final MediaMode data) {
                        logOut("getMediaMode " + data);
                        if (null != currentVideoResolutionAndFps) {
                            shutterSpeedAdapter.setData(rangeManager.getCameraShutterSpeed());
                            shutterList.setAdapter(shutterSpeedAdapter);
                        }
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getMediaMode " + error.getDescription());
                    }
                });
            }
        });


        view.findViewById(R.id.setMediaMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.setMediaMode(mediaMode, new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setMediaMode  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("setMediaMode state onSuccess");
                        if (null != currentVideoResolutionAndFps) {
                            shutterSpeedAdapter.setData(rangeManager.getCameraShutterSpeed());
                            shutterList.setAdapter(shutterSpeedAdapter);
                        }
                    }
                });
            }
        });


        view.findViewById(R.id.setSpotMeteringArea).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NoX = spotMeteringAreaX.getText().toString();
                String NoY = spotMeteringAreaY.getText().toString();
                autelR12.setSpotMeteringArea(isEmpty(NoX) ? 1 : Integer.valueOf(NoX), isEmpty(NoY) ? 1 : Integer.valueOf(NoY), new CallbackWithNoParam() {
                    @Override
                    public void onSuccess() {
                        logOut("setSpotMeteringArea  onSuccess  ");
                    }

                    @Override
                    public void onFailure(AutelError autelError) {
                        logOut("setSpotMeteringArea  description  " + autelError.getDescription());
                    }
                });
            }
        });
        view.findViewById(R.id.getSpotMeteringArea).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        autelR12.getSpotMeteringArea(new CallbackWithOneParam<SpotMeteringArea>() {
                            @Override
                            public void onFailure(AutelError error) {
                                logOut("getSpotMeteringArea  description  " + error.getDescription());
                            }

                            @Override
                            public void onSuccess(SpotMeteringArea data) {
                                logOut("getSpotMeteringArea X " + data.X + "  Y " + data.Y);
                            }
                        });
                    }
                });

        view.findViewById(R.id.setExposure).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        autelR12.setExposure(cameraExposureCompensation, new CallbackWithNoParam() {
                            @Override
                            public void onSuccess() {
                                logOut("setExposure  onSuccess  ");
                            }

                            @Override
                            public void onFailure(AutelError autelError) {
                                logOut("setExposure  description  " + autelError.getDescription());
                            }
                        });
                    }
                });


        view.findViewById(R.id.getExposure).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        autelR12.getExposure(new CallbackWithOneParam<ExposureCompensation>() {
                            @Override
                            public void onSuccess(ExposureCompensation cameraExposureCompensation) {
                                logOut("getExposure  onSuccess  " + cameraExposureCompensation.getValue());
                            }

                            @Override
                            public void onFailure(AutelError autelError) {
                                logOut("getExposure  description  " + autelError.getDescription());
                            }
                        });
                    }
                });


        view.findViewById(R.id.setISO).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        autelR12.setISO(cameraISO, new CallbackWithNoParam() {
                            @Override
                            public void onSuccess() {
                                logOut("setISO  onSuccess  ");
                            }

                            @Override
                            public void onFailure(AutelError autelError) {
                                logOut("setISO  description  " + autelError.getDescription());
                            }
                        });
                    }
                });

        view.findViewById(R.id.getISO).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        autelR12.getISO(new CallbackWithOneParam<CameraISO>() {
                            @Override
                            public void onFailure(AutelError error) {
                                logOut("getISO  description  " + error.getDescription());
                            }

                            @Override
                            public void onSuccess(CameraISO data) {
                                logOut("getISO " + data);
                            }
                        });
                    }
                });


        view.findViewById(R.id.setShutter).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        autelR12.setShutter(cameraShutterSpeed, new CallbackWithNoParam() {
                            @Override
                            public void onFailure(AutelError error) {
                                logOut("setShutter  description  " + error.getDescription());
                            }

                            @Override
                            public void onSuccess() {
                                logOut("setShutter state onSuccess");
                            }
                        });
                    }
                });

        view.findViewById(R.id.getShutter).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        autelR12.getShutter(new CallbackWithOneParam<ShutterSpeed>() {
                            @Override
                            public void onFailure(AutelError error) {
                                logOut("getShutter  description  " + error.getDescription());
                            }

                            @Override
                            public void onSuccess(ShutterSpeed data) {
                                logOut("getShutter " + data);
                            }
                        });
                    }
                });

        view.findViewById(R.id.setColorStyle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.setColorStyle(cameraColorStyle, new CallbackWithNoParam() {
                    @Override
                    public void onSuccess() {
                        logOut("setColorStyle  onSuccess  ");
                    }

                    @Override
                    public void onFailure(AutelError autelError) {
                        logOut("setColorStyle  description  " + autelError.getDescription());
                    }
                });
            }
        });

        view.findViewById(R.id.getColorStyle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.getColorStyle(new CallbackWithOneParam<ColorStyle>() {
                    @Override
                    public void onSuccess(ColorStyle cameraColorStyle) {
                        logOut("getColorStyle  onSuccess  " + cameraColorStyle);
                    }

                    @Override
                    public void onFailure(AutelError autelError) {
                        logOut("getColorStyle  description  " + autelError.getDescription());
                    }
                });
            }
        });

        view.findViewById(R.id.setWhiteBalance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WhiteBalance cameraWhiteBalance = new WhiteBalance();
                cameraWhiteBalance.type = cameraWhiteBalanceType;

                autelR12.setWhiteBalance(cameraWhiteBalance, new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setWhiteBalance  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("setWhiteBalance state onSuccess");
                    }
                });
            }
        });

        view.findViewById(R.id.getWhiteBalance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.getWhiteBalance(new CallbackWithOneParam<WhiteBalance>() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getWhiteBalance  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess(WhiteBalance data) {
                        logOut("getWhiteBalance " + data.type + "  colorTemperature  " + data.colorTemperature);
                    }
                });
            }
        });

        ((Switch) view.findViewById(R.id.set3DNoiseReductionEnable)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                autelR12.set3DNoiseReductionEnable(isChecked, new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("set3DNoiseReductionEnable  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("set3DNoiseReductionEnable state onSuccess");
                    }
                });
            }
        });

        view.findViewById(R.id.is3DNoiseReductionEnable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.is3DNoiseReductionEnable(new CallbackWithOneParam<Boolean>() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("is3DNoiseReductionEnable  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess(Boolean data) {
                        logOut("is3DNoiseReductionEnable " + data);
                    }
                });
            }
        });


        view.findViewById(R.id.setAntiFlicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.setAntiFlicker(cameraAntiFlicker, new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setAntiFlicker  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("setAntiFlicker state onSuccess");
                    }
                });
            }
        });

        view.findViewById(R.id.getAntiFlicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.getAntiFlicker(new CallbackWithOneParam<AntiFlicker>() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getAntiFlicker  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess(AntiFlicker data) {
                        logOut("getAntiFlicker " + data);
                    }
                });
            }
        });

        view.findViewById(R.id.setAutoExposureLockState).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.setAutoExposureLockState(cameraAutoExposureLockState, new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setAutoExposureLockState  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("setAutoExposureLockState  onSuccess  ");
                    }
                });
            }
        });

        view.findViewById(R.id.getAutoExposureLockState).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.getAutoExposureLockState(new CallbackWithOneParam<AutoExposureLockState>() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getAutoExposureLockState  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess(AutoExposureLockState data) {
                        logOut("getAutoExposureLockState  " + data);
                    }
                });
            }
        });

        view.findViewById(R.id.isHistogramStatusEnable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.isHistogramEnable(new CallbackWithOneParam<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        logOut("isHistogramEnable " + data);
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("isHistogramEnable " + error.getDescription());
                    }
                });
            }
        });

        view.findViewById(R.id.setExposureMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.setExposureMode(cameraExposureMode, new CallbackWithNoParam() {
                    @Override
                    public void onSuccess() {
                        logOut("setExposureMode  onSuccess  ");
                    }

                    @Override
                    public void onFailure(AutelError autelError) {
                        logOut("setAutoExposureLockState  description  " + autelError.getDescription());
                    }
                });
            }
        });

        view.findViewById(R.id.getExposureMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.getExposureMode(new CallbackWithOneParam<ExposureMode>() {
                    @Override
                    public void onSuccess(ExposureMode cameraExposureMode) {
                        logOut("getExposureMode  onSuccess  " + cameraExposureMode);
                    }

                    @Override
                    public void onFailure(AutelError autelError) {
                        logOut("getExposureMode  description  " + autelError.getDescription());
                    }
                });
            }
        });

        view.findViewById(R.id.setPhotoStyle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photoStyleType == PhotoStyleType.Custom) {
                    photoCustomStyleContrast.setVisibility(View.VISIBLE);
                    photoCustomStyleSaturation.setVisibility(View.VISIBLE);
                    photoCustomStyleSharpness.setVisibility(View.VISIBLE);
                    String contrastValue = photoCustomStyleContrast.getText().toString();
                    String saturationValue = photoCustomStyleSaturation.getText().toString();
                    String sharpnessValue = photoCustomStyleSharpness.getText().toString();

                    autelR12.setPhotoStyle(isEmpty(contrastValue) ? 1 : Integer.valueOf(contrastValue),
                            isEmpty(saturationValue) ? 2 : Integer.valueOf(saturationValue),
                            isEmpty(sharpnessValue) ? 3 : Integer.valueOf(sharpnessValue), new CallbackWithNoParam() {
                                @Override
                                public void onFailure(AutelError error) {
                                    logOut("setPhotoStyle  description  " + error.getDescription());
                                }

                                @Override
                                public void onSuccess() {
                                    logOut("setPhotoStyle state onSuccess");
                                }
                            });
                } else {
                    autelR12.setPhotoStyle(photoStyleType, new CallbackWithNoParam() {
                        @Override
                        public void onFailure(AutelError error) {
                            logOut("setPhotoStyle  description  " + error.getDescription());
                        }

                        @Override
                        public void onSuccess() {
                            logOut("setPhotoStyle state onSuccess");
                        }
                    });
                }
            }
        });

        view.findViewById(R.id.getPhotoStyle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.getPhotoStyle(new CallbackWithOneParam<PhotoStyle>() {
                    @Override
                    public void onSuccess(PhotoStyle data) {
                        logOut("getPhotoStyle " + data);
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getPhotoStyle " + error.getDescription());
                    }
                });
            }
        });

        ((Switch) view.findViewById(R.id.setVideoSubtitleEnable)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                autelR12.setVideoSubtitleEnable(isChecked, new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setVideoSubtitleEnable  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("setVideoSubtitleEnable onSuccess");
                    }
                });
            }
        });

        view.findViewById(R.id.isSubtitleEnable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.isSubtitleEnable(new CallbackWithOneParam<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        logOut("isSubtitleEnable " + data);
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("isSubtitleEnable " + error.getDescription());
                    }
                });
            }
        });

        view.findViewById(R.id.setPhotoBurstCount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.setPhotoBurstCount(photoBurstCount, new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setPhotoBurstCount  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("setPhotoBurstCount state onSuccess");
                    }
                });
            }
        });

        view.findViewById(R.id.getPhotoBurstCount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.getPhotoBurstCount(new CallbackWithOneParam<PhotoBurstCount>() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getPhotoBurstCount  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess(PhotoBurstCount data) {
                        logOut("getPhotoBurstCount " + data);
                    }
                });
            }
        });

        view.findViewById(R.id.setPhotoTimelapseInterval).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.setPhotoTimelapseInterval(photoTimelapseInterval, new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setPhotoTimelapseInterval  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("setPhotoTimelapseInterval state onSuccess");
                    }
                });
            }
        });

        view.findViewById(R.id.getPhotoTimelapseInterval).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.getPhotoTimelapseInterval(new CallbackWithOneParam<PhotoTimelapseInterval>() {
                    @Override
                    public void onSuccess(PhotoTimelapseInterval data) {
                        logOut("getPhotoTimelapseInterval " + data);
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getPhotoTimelapseInterval " + error.getDescription());
                    }
                });
            }
        });

        view.findViewById(R.id.setPhotoAEBCount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.setPhotoAEBCount(photoAEBCount, new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setPhotoAEBCount  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("setPhotoAEBCount state onSuccess");
                    }
                });
            }
        });

        view.findViewById(R.id.getPhotoAEBCount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.getPhotoAEBCount(new CallbackWithOneParam<PhotoAEBCount>() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getPhotoAEBCount  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess(PhotoAEBCount data) {
                        logOut("getPhotoAEBCount " + data);
                    }
                });
            }
        });

        view.findViewById(R.id.getLeftRecordTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.getLeftRecordTime(new CallbackWithOneParam<Long>() {
                    @Override
                    public void onSuccess(Long data) {
                        logOut("getLeftRecordTime " + data);
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getLeftRecordTime " + error.getDescription());
                    }
                });
            }
        });

        view.findViewById(R.id.getPhotoSum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.getPhotoSum(new CallbackWithOneParam<Integer>() {
                    @Override
                    public void onSuccess(Integer data) {
                        logOut("getPhotoSum " + data);
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getPhotoSum " + error.getDescription());
                    }
                });
            }
        });

        view.findViewById(R.id.getCurrentRecordTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.getCurrentRecordTime(new CallbackWithOneParam<Integer>() {
                    @Override
                    public void onSuccess(Integer data) {
                        logOut("getCurrentRecordTime " + data);
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getCurrentRecordTime " + error.getDescription());
                    }
                });
            }
        });

        view.findViewById(R.id.setVideoFormat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.setVideoFormat(videoFormat, new CallbackWithNoParam() {
                    @Override
                    public void onSuccess() {
                        logOut("setVideoFormat onSuccess");
                    }

                    @Override
                    public void onFailure(AutelError autelError) {
                        logOut("setVideoFormat " + autelError.getDescription());
                    }
                });
            }
        });

        view.findViewById(R.id.getVideoFormat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.getVideoFormat(new CallbackWithOneParam<VideoFormat>() {
                    @Override
                    public void onSuccess(VideoFormat data) {
                        logOut("getVideoFormat " + data);
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getVideoFormat " + error.getDescription());
                    }
                });
            }
        });

        view.findViewById(R.id.setVideoStandard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.setVideoStandard(selectedVideoStandard, new CallbackWithNoParam() {

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setVideoStandard  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("setVideoStandard state onSuccess");
                        currentVideoStandard = selectedVideoStandard;
                        initVideoResolutionFpsList();
                    }
                });
            }
        });

        view.findViewById(R.id.getVideoStandard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.getVideoStandard(new CallbackWithOneParam<VideoStandard>() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getVideoStandard  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess(VideoStandard data) {
                        logOut("getVideoStandard " + data);
                        currentVideoStandard = data;
                        initVideoResolutionFpsList();
                    }
                });
            }
        });

        view.findViewById(R.id.setPhotoFormat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.setPhotoFormat(photoFormat, new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setPhotoFormat  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("setPhotoFormat state onSuccess");
                    }
                });
            }
        });

        view.findViewById(R.id.getPhotoFormat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.getPhotoFormat(new CallbackWithOneParam<PhotoFormat>() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getPhotoFormat  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess(PhotoFormat data) {
                        logOut("getPhotoFormat " + data);
                    }
                });
            }
        });

        view.findViewById(R.id.setAspectRatio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.setAspectRatio(aspectRatio, new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setAspectRatio  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("setAspectRatio state onSuccess");
                    }
                });
            }
        });

        view.findViewById(R.id.getAspectRatio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.getAspectRatio(new CallbackWithOneParam<PhotoAspectRatio>() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getAspectRatio  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess(PhotoAspectRatio data) {
                        logOut("getAspectRatio " + data);
                    }
                });
            }
        });

        view.findViewById(R.id.setVideoResolutionAndFrameRate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                autelR12.setVideoResolutionAndFrameRate(videoResolutionAndFps, new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setVideoResolutionAndFrameRate  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("setVideoResolutionAndFrameRate onSuccess");
                        currentVideoResolutionAndFps = videoResolutionAndFps;
                        initShuttleSpeedList();

                    }
                });
            }
        });

        view.findViewById(R.id.getVideoResolutionAndFrameRate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.getVideoResolutionAndFrameRate(new CallbackWithOneParam<VideoResolutionAndFps>() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getVideoResolutionAndFrameRate  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess(VideoResolutionAndFps data) {
                        logOut("getVideoResolutionAndFrameRate " + data);
                        currentVideoResolutionAndFps = data;
                        initShuttleSpeedList();
                    }
                });
            }
        });

        videoResolutionAndFrameRateList = (Spinner) view.findViewById(R.id.videoResolutionAndFrameRateList);
        videoResolutionFpsAdapter = new VideoResolutionFpsAdapter(getContext());
        videoResolutionAndFrameRateList.setAdapter(videoResolutionFpsAdapter);

        view.findViewById(R.id.setHistogramListener).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.setHistogramListener(new CallbackWithOneParam<int[]>() {
                    @Override
                    public void onSuccess(int[] ints) {
                        logOut("setHistogramListener  " + ints);
                    }

                    @Override
                    public void onFailure(AutelError autelError) {
                        logOut("setHistogramListener  description " + autelError.getDescription());
                    }
                });
            }
        });

        view.findViewById(R.id.resetHistogramListener).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.setHistogramListener(null);
            }
        });

        final EditText digitalZoomScaleValue = (EditText) view.findViewById(R.id.digitalZoomScaleValue);
        final TextView digitalZoomScaleRange = (TextView) view.findViewById(R.id.digitalZoomScaleRange);
        digitalZoomScaleValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (isEmpty(digitalZoomScaleRange.getText().toString())) {
                    RangePair<Integer> digitalScaleRange = rangeManager.getDigitalZoomScale();
                    digitalZoomScaleRange.setText("integer value of digital scale,  range from " + digitalScaleRange.getValueFrom() + " to " + digitalScaleRange.getValueTo());
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        view.findViewById(R.id.setDigitalZoomScale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = digitalZoomScaleValue.getText().toString();
                int parameter = isEmpty(value) ? 100 : Integer.valueOf(value);
                autelR12.setDigitalZoomScale(parameter, new CallbackWithNoParam() {
                    @Override
                    public void onSuccess() {
                        logOut("setDigitalZoomScale  onSuccess  ");
                    }

                    @Override
                    public void onFailure(AutelError autelError) {
                        logOut("setDigitalZoomScale  description  " + autelError.getDescription());
                    }
                });
            }
        });

        view.findViewById(R.id.getDigitalZoomScale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.getDigitalZoomScale(new CallbackWithOneParam<Integer>() {
                    @Override
                    public void onSuccess(Integer value) {
                        logOut("getDigitalZoomScale  onSuccess  " + value);
                    }

                    @Override
                    public void onFailure(AutelError autelError) {
                        logOut("getDigitalZoomScale  description  " + autelError.getDescription());
                    }
                });
            }
        });

        view.findViewById(R.id.resetAutelCameraModeListener).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.setMediaModeListener(null);
            }
        });
        view.findViewById(R.id.setAutelCameraModeListener).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autelR12.setMediaModeListener(new CallbackWithOneParam<MediaMode>() {
                    @Override
                    public void onSuccess(MediaMode mode) {
                        logOut("setMediaModeListener mode " + mode);
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setMediaModeListener error " + error.getDescription());
                    }
                });
            }
        });

    }

    private void initVideoResolutionFpsList() {
        videoResolutionFpsAdapter.setData(Arrays.asList(rangeManager.getVideoResolutionAndFps()));
        videoResolutionAndFrameRateList.setAdapter(videoResolutionFpsAdapter);
        videoResolutionAndFrameRateList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                videoResolutionAndFps = (VideoResolutionAndFps) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initShuttleSpeedList() {
        autelR12.getMediaMode(new CallbackWithOneParam<MediaMode>() {
            @Override
            public void onSuccess(final MediaMode mode) {
                if (null != currentVideoResolutionAndFps) {
                    shutterSpeedAdapter.setData(rangeManager.getCameraShutterSpeed());
                    shutterList.setAdapter(shutterSpeedAdapter);
                }
            }

            @Override
            public void onFailure(AutelError autelError) {

            }
        });

    }

    private void initView(View view) {


        Spinner aspectRatioList = (Spinner) view.findViewById(R.id.aspectRatioList);
        AspectRatioAdapter ratioAdapter = new AspectRatioAdapter(getContext());
        ratioAdapter.setData(Arrays.asList(rangeManager.getPhotoAspectRatio()));
        aspectRatioList.setAdapter(ratioAdapter);
        aspectRatioList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                aspectRatio = (PhotoAspectRatio) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner photoFormatList = (Spinner) view.findViewById(R.id.photoFormatList);
        photoFormatList.setAdapter(new PhotoFormatAdapter(getContext()));
        photoFormatList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                photoFormat = (PhotoFormat) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner videoStandardList = (Spinner) view.findViewById(R.id.videoStandardList);
        videoStandardList.setAdapter(new VideoStandardAdapter(getContext()));
        videoStandardList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedVideoStandard = (VideoStandard) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner videoFormatList = (Spinner) view.findViewById(R.id.videoFormatList);
        videoFormatList.setAdapter(new VideoFormatAdapter(getContext()));
        videoFormatList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                videoFormat = (VideoFormat) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner photoAEBCountList = (Spinner) view.findViewById(R.id.photoAEBCountList);
        photoAEBCountList.setAdapter(new PhotoAEBCountAdapter(getContext()));
        photoAEBCountList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                photoAEBCount = (PhotoAEBCount) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner photoTimelapseIntervalList = (Spinner) view.findViewById(R.id.photoTimelapseIntervalList);
        if (null != autelR12) {
            photoTimelapseIntervalList.setAdapter(new PhotoTimelapseIntervalAdapter(getContext(),
                    Arrays.asList(rangeManager.getPhotoTimelapseInterval())));
        }
        photoTimelapseIntervalList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                photoTimelapseInterval = (PhotoTimelapseInterval) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner photoBurstCountList = (Spinner) view.findViewById(R.id.photoBurstCountList);
        photoBurstCountList.setAdapter(new PhotoBurstAdapter(getContext()));
        photoBurstCountList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                photoBurstCount = (PhotoBurstCount) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        photoCustomStyleContrast = (EditText) view.findViewById(R.id.photoCustomStyleContrast);
        photoCustomStyleSaturation = (EditText) view.findViewById(R.id.photoCustomStyleSaturation);
        photoCustomStyleSharpness = (EditText) view.findViewById(R.id.photoCustomStyleSharpness);

        Spinner photoStyleList = (Spinner) view.findViewById(R.id.photoStyleList);
        photoStyleList.setAdapter(new PhotoStyleAdapter(getContext()));
        photoStyleList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                photoStyleType = (PhotoStyleType) parent.getAdapter().getItem(position);
                if (photoStyleType == PhotoStyleType.Custom) {
                    photoCustomStyleContrast.setVisibility(View.VISIBLE);
                    photoCustomStyleSaturation.setVisibility(View.VISIBLE);
                    photoCustomStyleSharpness.setVisibility(View.VISIBLE);
                } else {
                    photoCustomStyleContrast.setVisibility(View.GONE);
                    photoCustomStyleSaturation.setVisibility(View.GONE);
                    photoCustomStyleSharpness.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        exposureValueList = (Spinner) view.findViewById(R.id.exposureValueList);
        exposureValueList.setAdapter(new ExposureValueAdapter(getContext()));
        exposureValueList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cameraExposureCompensation = ExposureCompensation.find((String)parent.getAdapter().getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        shutterList = (Spinner) view.findViewById(R.id.shutterList);
        shutterSpeedAdapter = new ShutterSpeedAdapter(getContext(), rangeManager.getCameraShutterSpeed());
        shutterList.setAdapter(shutterSpeedAdapter);
        shutterList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cameraShutterSpeed = (ShutterSpeed) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner ISOList = (Spinner) view.findViewById(R.id.ISOList);
        ISOList.setAdapter(new ISOValueAdapter(getContext(), Arrays.asList(rangeManager.getCameraISO())));
        ISOList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cameraISO = (CameraISO) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        colorStyleList = (Spinner) view.findViewById(R.id.colorStyleList);
        colorStyleList.setAdapter(new ColorStyleAdapter(getContext()));
        colorStyleList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cameraColorStyle = (ColorStyle) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner whiteBalanceTypeList = (Spinner) view.findViewById(R.id.whiteBalanceTypeList);
        WhiteBalanceTypeAdapter whiteBalanceTypeAdapter = new WhiteBalanceTypeAdapter(getContext());
        whiteBalanceTypeAdapter.setData(Arrays.asList(rangeManager.getCameraWhiteBalanceType()));
        whiteBalanceTypeList.setAdapter(whiteBalanceTypeAdapter);
        whiteBalanceTypeList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cameraWhiteBalanceType = (WhiteBalanceType) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner antiFlickerList = (Spinner) view.findViewById(R.id.antiFlickerList);
        antiFlickerList.setAdapter(new AntiFlickerAdapter(getContext()));
        antiFlickerList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cameraAntiFlicker = (AntiFlicker) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner autoExposureLockStateList = (Spinner) view.findViewById(R.id.autoExposureLockStateList);
        autoExposureLockStateList.setAdapter(new AutoExposureLockStateAdapter(getContext()));
        autoExposureLockStateList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cameraAutoExposureLockState = (AutoExposureLockState) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        exposureModeList = (Spinner) view.findViewById(R.id.exposureModeList);
        exposureModeList.setAdapter(new ExposureModeAdapter(getContext(), Arrays.asList(rangeManager.getCameraExposureMode())));
        exposureModeList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cameraExposureMode = (ExposureMode) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spotMeteringAreaX = (EditText) view.findViewById(R.id.spotMeteringAreaX);
        spotMeteringAreaY = (EditText) view.findViewById(R.id.spotMeteringAreaY);
    }
}
