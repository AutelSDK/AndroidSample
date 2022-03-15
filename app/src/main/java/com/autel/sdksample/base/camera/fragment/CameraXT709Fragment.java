package com.autel.sdksample.base.camera.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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
import com.autel.common.camera.CameraProduct;
import com.autel.common.camera.XT706.DisplayMode;
import com.autel.common.camera.XT706.IrColor;
import com.autel.common.camera.XT706.IrPosition;
import com.autel.common.camera.XT706.XT706CameraInfo;
import com.autel.common.camera.XT706.XT706ParameterRangeManager;
import com.autel.common.camera.XT706.XT706StateInfo;
import com.autel.common.camera.base.BaseStateInfo;
import com.autel.common.camera.base.MediaMode;
import com.autel.common.camera.base.PhotoFormat;
import com.autel.common.camera.base.SDCardState;
import com.autel.common.camera.media.AntiFlicker;
import com.autel.common.camera.media.AutoExposureLockState;
import com.autel.common.camera.media.CameraISO;
import com.autel.common.camera.media.ColorStyle;
import com.autel.common.camera.media.ExposureCompensation;
import com.autel.common.camera.media.ExposureMode;
import com.autel.common.camera.media.FlashCardStatus;
import com.autel.common.camera.media.PhotoAEBCount;
import com.autel.common.camera.media.PhotoAspectRatio;
import com.autel.common.camera.media.PhotoBurstCount;
import com.autel.common.camera.media.PhotoStyle;
import com.autel.common.camera.media.PhotoStyleType;
import com.autel.common.camera.media.PhotoTimelapseInterval;
import com.autel.common.camera.media.ShutterSpeed;
import com.autel.common.camera.media.SkylinePositionData;
import com.autel.common.camera.media.SpotMeteringArea;
import com.autel.common.camera.media.VideoEncodeFormat;
import com.autel.common.camera.media.VideoFormat;
import com.autel.common.camera.media.VideoResolutionAndFps;
import com.autel.common.camera.media.VideoSnapshotTimelapseInterval;
import com.autel.common.camera.media.VideoSum;
import com.autel.common.camera.media.WhiteBalance;
import com.autel.common.camera.media.WhiteBalanceType;
import com.autel.common.camera.xb015.PIVMode;
import com.autel.common.error.AutelError;
import com.autel.sdk.camera.AutelXT706;
import com.autel.sdk.camera.AutelXT709;
import com.autel.sdksample.R;
import com.autel.sdksample.base.camera.CameraActivity;
import com.autel.sdksample.base.camera.fragment.adapter.AntiFlickerAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.AutoExposureLockStateAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.ExposureValueAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.ISOValueAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.IrColorStyleAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.ModelcAspectRatioAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.ModelcColorStyleAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.ModelcExposureModeAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.PIVModeAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.PhotoAEBCountAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.PhotoBurstAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.PhotoFormatAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.PhotoStyleAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.PhotoTimelapseIntervalAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.ShutterSpeedAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.VideoEncodeAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.VideoFormatAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.VideoResolutionFpsAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.VideoSnapshotTimeIntervalAdapter;
import com.autel.sdksample.base.camera.fragment.adapter.WhiteBalanceTypeAdapter;
import com.autel.sdksample.util.ThreadUtils;

import java.util.Arrays;

public class CameraXT709Fragment extends CameraBaseFragment {
    AutelXT709 xt706;

    Button setCameraColor;
    Spinner colorStyle;

    Button setExposureMode;
    Spinner exposureModeList;

    EditText spotMeteringAreaX;
    EditText spotMeteringAreaY;

    EditText positionX;
    EditText positionY;

    EditText photoCustomStyleContrast;
    EditText photoCustomStyleSaturation;
    EditText photoCustomStyleSharpness;

    Spinner exposureValueList;
    VideoResolutionFpsAdapter videoResolutionFpsAdapter;
    Spinner videoResolutionAndFrameRateList;
    Spinner photoTimelapseIntervalList;

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
    PhotoFormat photoFormat = PhotoFormat.JPEG;
    PhotoFormat currentPhotoFormat = PhotoFormat.JPEG;
    PhotoAspectRatio aspectRatio = PhotoAspectRatio.Aspect_16_9;
    VideoResolutionAndFps videoResolutionAndFps = null;
    DisplayMode displayMode = DisplayMode.VISIBLE;
    IrColor irColor = IrColor.Lava;
    VideoEncodeFormat videoEncoding = VideoEncodeFormat.H264;
    PIVMode pivMode = PIVMode.Manual;
    VideoSnapshotTimelapseInterval snapshotTimelapseInterval = VideoSnapshotTimelapseInterval.SECOND_5;
    VideoResolutionAndFps currentVideoResolutionAndFps = null;

    ShutterSpeedAdapter shutterSpeedAdapter = null;
    Spinner shutterList = null;

    private XT706ParameterRangeManager rangeManager;
    private XT706StateInfo xt706StateInfo;
    ModelcAspectRatioAdapter aspectRatioAdapter;
    Spinner aspectRatioList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_camera_xt706, null);
        xt706 = (AutelXT709) ((CameraActivity) getActivity()).getCurrentCamera();
        rangeManager = xt706.getParameterRangeManager();
        logOut("");

        ThreadUtils.runOnNonUIthread(new Runnable() {
            @Override
            public void run() {
                xt706.getStateInfo(new CallbackWithOneParam<BaseStateInfo>() {
                    @Override
                    public void onSuccess(BaseStateInfo baseStateInfo) {
                        xt706StateInfo = (XT706StateInfo) baseStateInfo;
                        displayMode = xt706StateInfo.getDisplayMode();
                        ThreadUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(null != view) {
                                    initView(view);
                                    initClick(view);
                                    initXT706Click(view);
                                    initData();
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(AutelError autelError) {

                    }
                });
            }
        });

        return view;
    }

    private void initData() {
        if (null != xt706) {
            xt706.getVideoResolutionAndFrameRate(new CallbackWithOneParam<VideoResolutionAndFps>() {
                @Override
                public void onFailure(AutelError error) {
                }

                @Override
                public void onSuccess(VideoResolutionAndFps data) {
                    currentVideoResolutionAndFps = data;
                    initShuttleSpeedList();
                }
            });

            initVideoResolutionFpsList();

            xt706.getPhotoFormat(new CallbackWithOneParam<PhotoFormat>() {
                @Override
                public void onFailure(AutelError error) {
                }

                @Override
                public void onSuccess(PhotoFormat data) {
                    currentPhotoFormat = data;
                    if (null != xt706) {
                        photoTimelapseIntervalList.setAdapter(new PhotoTimelapseIntervalAdapter(getContext(),
                                xt706.getParameterRangeManager().getPhotoTimelapseInterval()));
                    }
                }
            });
        }
    }


    private void initXT706Click(final View view) {
        view.findViewById(R.id.getSDCardInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.getSDCardState(new CallbackWithOneParam<SDCardState>() {
                    @Override
                    public void onSuccess(SDCardState state) {
                        logOut("getSDCardInfo  :" + state.toString());
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getSDCardInfo  description  " + error.getDescription());
                    }
                });
            }
        });
        view.findViewById(R.id.getFlashCardInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.getFMCStatus(new CallbackWithOneParam<FlashCardStatus>() {
                    @Override
                    public void onSuccess(FlashCardStatus state) {
                        logOut("getFlashCardInfo  :" + state.toString());
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getFlashCardInfo  description  " + error.getDescription());
                    }
                });
            }
        });
        view.findViewById(R.id.formatMMCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.formatFlashMemoryCard(new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("formatMMCard " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("formatMMCard onSuccess");
                    }
                });
            }
        });
        view.findViewById(R.id.getStateInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.getStateInfo(new CallbackWithOneParam<BaseStateInfo>() {
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

        view.findViewById(R.id.setAutelCameraModeListener).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.setMediaModeListener(new CallbackWithOneParam<MediaMode>() {
                    @Override
                    public void onSuccess(MediaMode state) {
                        logOut("setMediaModeListener  :" + state);
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setMediaModeListener  description  " + error.getDescription());
                    }
                });
            }
        });
        view.findViewById(R.id.resetAutelCameraModeListener).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.setMediaModeListener(null);
            }
        });
        view.findViewById(R.id.setInfoListener).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.setInfoListener(new CallbackWithOneParam<XT706CameraInfo>() {
                    @Override
                    public void onSuccess(XT706CameraInfo state) {
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
                xt706.setInfoListener(null);
            }
        });

        view.findViewById(R.id.resetHistogramListener).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.setHistogramListener(null);
            }
        });

        view.findViewById(R.id.setHistogramListener).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.setHistogramListener(new CallbackWithOneParam<int[]>() {
                    @Override
                    public void onSuccess(int[] ints) {
                        StringBuffer stringBuffer = new StringBuffer("{");
                        for (int item : ints) {
                            stringBuffer.append(item);
                            stringBuffer.append(",");
                        }
                        if (stringBuffer.length() > 1)
                            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                        stringBuffer.append("}");
                        logOut("setHistogramListener  onSuccess  " + stringBuffer.toString());
                    }

                    @Override
                    public void onFailure(AutelError autelError) {
                        logOut("setHistogramListener  description  " + autelError.getDescription());
                    }
                });
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
                xt706.setDigitalZoomScale(parameter, new CallbackWithNoParam() {
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
                xt706.getDigitalZoomScale(new CallbackWithOneParam<Integer>() {
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

        view.findViewById(R.id.getMediaMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.getMediaMode(new CallbackWithOneParam<MediaMode>() {
                    @Override
                    public void onSuccess(final MediaMode data) {
                        logOut("getMediaMode " + data);
                        Activity activity = getActivity();
                        if (null != activity)
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
                xt706.setMediaMode(mediaMode, new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setMediaMode  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("setMediaMode state onSuccess ");
                        Activity activity = getActivity();
                        if (null != activity)
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
                xt706.setSpotMeteringArea(isEmpty(NoX) ? 1 : Integer.valueOf(NoX), isEmpty(NoY) ? 1 : Integer.valueOf(NoY), new CallbackWithNoParam() {
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
                        xt706.getSpotMeteringArea(new CallbackWithOneParam<SpotMeteringArea>() {
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
                        xt706.setExposure(cameraExposureCompensation, new CallbackWithNoParam() {
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
                        xt706.getExposure(new CallbackWithOneParam<ExposureCompensation>() {
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
                        xt706.setISO(cameraISO, new CallbackWithNoParam() {
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
                        xt706.getISO(new CallbackWithOneParam<CameraISO>() {
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
                        xt706.setShutter(cameraShutterSpeed, new CallbackWithNoParam() {
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
                        xt706.getShutter(new CallbackWithOneParam<ShutterSpeed>() {
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
                xt706.setColorStyle(cameraColorStyle, new CallbackWithNoParam() {
                    @Override
                    public void onSuccess() {
                        logOut("setColorStyle  onSuccess  ");
                    }

                    @Override
                    public void onFailure(AutelError autelError) {
                        logOut("setAutoExposureLockState  description  " + autelError.getDescription());
                    }
                });
            }
        });

        view.findViewById(R.id.getColorStyle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.getColorStyle(new CallbackWithOneParam<ColorStyle>() {
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
                String colorValueStr = ((EditText) view.findViewById(R.id.colorTemperatureValue)).getText().toString();
                int colorValue = isEmpty(colorValueStr) ? 2000 : Integer.valueOf(colorValueStr);
                WhiteBalance cameraWhiteBalance = new WhiteBalance();
                cameraWhiteBalance.type = cameraWhiteBalanceType;
                cameraWhiteBalance.colorTemperature = colorValue;
                xt706.setWhiteBalance(cameraWhiteBalance, new CallbackWithNoParam() {
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
                xt706.getWhiteBalance(new CallbackWithOneParam<WhiteBalance>() {
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

        view.findViewById(R.id.setAntiFlicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.setAntiFlicker(cameraAntiFlicker, new CallbackWithNoParam() {
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
                xt706.getAntiFlicker(new CallbackWithOneParam<AntiFlicker>() {
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
                xt706.setAutoExposureLockState(cameraAutoExposureLockState, new CallbackWithNoParam() {
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
                xt706.getAutoExposureLockState(new CallbackWithOneParam<AutoExposureLockState>() {
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
                xt706.isHistogramStatusEnable(new CallbackWithOneParam<Boolean>() {
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
                xt706.setExposureMode(cameraExposureMode, new CallbackWithNoParam() {
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
                xt706.getExposureMode(new CallbackWithOneParam<ExposureMode>() {
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

                    xt706.setPhotoStyle(isEmpty(contrastValue) ? 1 : Integer.valueOf(contrastValue),
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
                    xt706.setPhotoStyle(photoStyleType, new CallbackWithNoParam() {
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
                xt706.getPhotoStyle(new CallbackWithOneParam<PhotoStyle>() {
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
                xt706.setVideoSubtitleEnable(isChecked, new CallbackWithNoParam() {
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
                xt706.isSubtitleEnable(new CallbackWithOneParam<Boolean>() {
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
                xt706.setPhotoBurstCount(photoBurstCount, new CallbackWithNoParam() {
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
                xt706.getPhotoBurstCount(new CallbackWithOneParam<PhotoBurstCount>() {
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
                xt706.setPhotoTimelapseInterval(photoTimelapseInterval, new CallbackWithNoParam() {
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
                xt706.getPhotoTimelapseInterval(new CallbackWithOneParam<PhotoTimelapseInterval>() {
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
                xt706.setPhotoAEBCount(photoAEBCount, new CallbackWithNoParam() {
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
                xt706.getPhotoAEBCount(new CallbackWithOneParam<PhotoAEBCount>() {
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

        view.findViewById(R.id.setVideoEncodeFormat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.setVideoEncodeFormat(videoEncoding, new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setVideoEncoder  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("setVideoEncoder onSuccess");
                    }
                });
            }
        });

        view.findViewById(R.id.getVideoEncodeFormat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.getVideoEncodeFormat(new CallbackWithOneParam<VideoEncodeFormat>() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getVideoEncodeFormat  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess(VideoEncodeFormat data) {
                        logOut("getVideoEncodeFormat " + data);
                    }
                });
            }
        });

        view.findViewById(R.id.getVideoSum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.getVideoSum(new CallbackWithOneParam<VideoSum>() {
                    @Override
                    public void onSuccess(VideoSum data) {
                        logOut("getVideoSum " + data);
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getVideoSum " + error.getDescription());
                    }
                });
            }
        });

        view.findViewById(R.id.getLeftPhotoSum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.getLeftPhotoSum(new CallbackWithOneParam<Integer>() {
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

        view.findViewById(R.id.setVideoFormat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.setVideoFormat(videoFormat, new CallbackWithNoParam() {
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
                xt706.getVideoFormat(new CallbackWithOneParam<VideoFormat>() {
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

        view.findViewById(R.id.setPhotoFormat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.setPhotoFormat(photoFormat, new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setPhotoFormat  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("setPhotoFormat state onSuccess");
                        if (null != xt706) {
                            xt706.getPhotoFormat(new CallbackWithOneParam<PhotoFormat>() {
                                @Override
                                public void onFailure(AutelError error) {
                                }

                                @Override
                                public void onSuccess(PhotoFormat data) {
                                    currentPhotoFormat = data;
                                    if (null != xt706) {
                                        photoTimelapseIntervalList.setAdapter(new PhotoTimelapseIntervalAdapter(getContext(),
                                                xt706.getParameterRangeManager().getPhotoTimelapseInterval()));
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });

        view.findViewById(R.id.getPhotoFormat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.getPhotoFormat(new CallbackWithOneParam<PhotoFormat>() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getPhotoFormat  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess(PhotoFormat data) {
                        logOut("getPhotoFormat " + data);
                        currentPhotoFormat = data;
                        if (null != xt706) {
                            photoTimelapseIntervalList.setAdapter(new PhotoTimelapseIntervalAdapter(getContext(),
                                    xt706.getParameterRangeManager().getPhotoTimelapseInterval()));
                        }
                    }
                });
            }
        });

        view.findViewById(R.id.setAspectRatio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.setAspectRatio(aspectRatio, new CallbackWithNoParam() {
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
                xt706.getAspectRatio(new CallbackWithOneParam<PhotoAspectRatio>() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getAspectRatio  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess(PhotoAspectRatio data) {
                        logOut("getAspectRatio " + data.toFormat());
                    }
                });
            }
        });

        view.findViewById(R.id.getPIVMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.getPIVMode(new CallbackWithOneParam<PIVMode>() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getPIVMode  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess(PIVMode mode) {
                        logOut("getPIVMode onSuccess " + mode);
                    }
                });
            }
        });
        view.findViewById(R.id.setPIVMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.setPIVMode(pivMode, new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setPIVMode  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("setPIVMode onSuccess");
                    }
                });
            }
        });
        view.findViewById(R.id.setAutoPIVTimelapseInterval).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.setAutoPIVTimelapseInterval(snapshotTimelapseInterval, new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setAutoPIVTimelapseInterval  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("setAutoPIVTimelapseInterval onSuccess");
                    }
                });
            }
        });

        view.findViewById(R.id.setVideoResolutionAndFrameRate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.setVideoResolutionAndFrameRate(videoResolutionAndFps, new CallbackWithNoParam() {
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

        view.findViewById(R.id.getSkylinePositionData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.getSkylinePositionData(100, 100, new CallbackWithOneParam<SkylinePositionData>() {
                    @Override
                    public void onSuccess(SkylinePositionData data) {
                        logOut("getSkylinePositionData  " + data);
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getSkylinePositionData  description  " + error.getDescription());
                    }
                });
            }
        });

        view.findViewById(R.id.getVideoResolutionAndFrameRate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.getVideoResolutionAndFrameRate(new CallbackWithOneParam<VideoResolutionAndFps>() {
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

        view.findViewById(R.id.getDisplayMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.getDisplayMode(new CallbackWithOneParam<DisplayMode>() {
                    @Override
                    public void onSuccess(DisplayMode displayMode) {
                        logOut("getDisplayMode onSuccess " + displayMode);
                    }

                    @Override
                    public void onFailure(AutelError autelError) {
                        logOut("getDisplayMode  description  " + autelError.getDescription());
                    }
                });
            }
        });
        view.findViewById(R.id.setDisplayMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.setDisplayMode(displayMode, new CallbackWithNoParam() {
                    @Override
                    public void onSuccess() {
                        logOut("setDisplayMode onSuccess");
                        aspectRatioAdapter.setData(CameraProduct.XT706, displayMode);
                        aspectRatioList.setAdapter(aspectRatioAdapter);
                    }

                    @Override
                    public void onFailure(AutelError autelError) {
                        logOut("setDisplayMode  description  " + autelError.getDescription());
                    }
                });
            }
        });

        //just for IR or PictureInPicture
        view.findViewById(R.id.FFC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.setIrFlushShutter(new CallbackWithNoParam() {
                    @Override
                    public void onSuccess() {
                        logOut("FFC onSuccess");
                    }

                    @Override
                    public void onFailure(AutelError autelError) {
                        logOut("FFC  description  " + autelError.getDescription());
                    }
                });
            }
        });

        //just for PictureInPicture
        view.findViewById(R.id.setIrPosition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NoX = positionX.getText().toString();
                String NoY = positionY.getText().toString();
                if(TextUtils.isEmpty(NoX) || TextUtils.isEmpty(NoY)){
                    return;
                }
                //NoX:-10~10   NoY:-10~10
                IrPosition irPosition = new IrPosition(Integer.parseInt(NoX), Integer.parseInt(NoY));
                xt706.setIrPosition(irPosition, new CallbackWithNoParam() {
                    @Override
                    public void onSuccess() {
                        logOut("setIrPosition  onSuccess  ");
                    }

                    @Override
                    public void onFailure(AutelError autelError) {
                        logOut("setIrPosition  description  " + autelError.getDescription());
                    }
                });
            }
        });

        view.findViewById(R.id.getIrPosition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.getIrPosition(new CallbackWithOneParam<IrPosition>() {
                    @Override
                    public void onSuccess(IrPosition irPosition) {
                        logOut("getIrPosition  X:" + irPosition.getDeltaX() + " Y:" + irPosition.getDeltaY());
                    }

                    @Override
                    public void onFailure(AutelError autelError) {
                        logOut("getIrPosition  description  " + autelError.getDescription());
                    }
                });
            }
        });

        view.findViewById(R.id.getIrColorStyle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.getIrColor(new CallbackWithOneParam<IrColor>() {
                    @Override
                    public void onSuccess(IrColor irColor) {
                        logOut("getIrColorStyle onSuccess " + irColor);
                    }

                    @Override
                    public void onFailure(AutelError autelError) {
                        logOut("getIrColorStyle  description  " + autelError.getDescription());
                    }
                });
            }
        });
        view.findViewById(R.id.setIrColorStyle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xt706.setIrColor(irColor, new CallbackWithNoParam() {
                    @Override
                    public void onSuccess() {
                        logOut("setIrColorStyle onSuccess");
                    }

                    @Override
                    public void onFailure(AutelError autelError) {
                        logOut("setIrColorStyle  description  " + autelError.getDescription());
                    }
                });
            }
        });
    }

    private void initVideoResolutionFpsList() {
        if (null != xt706) {
            videoResolutionFpsAdapter.setData(Arrays.asList(rangeManager.getVideoResolutionAndFps()));
        }
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
        xt706.getMediaMode(new CallbackWithOneParam<MediaMode>() {
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

    private void initView(final View parentView) {

        Spinner autoPIVTimelapseIntervalList = (Spinner) parentView.findViewById(R.id.autoPIVTimelapseIntervalList);
        autoPIVTimelapseIntervalList.setAdapter(new VideoSnapshotTimeIntervalAdapter(getContext()));
        autoPIVTimelapseIntervalList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                snapshotTimelapseInterval = (VideoSnapshotTimelapseInterval) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner pivModeList = (Spinner) parentView.findViewById(R.id.PIVModeList);
        pivModeList.setAdapter(new PIVModeAdapter(getContext()));
        pivModeList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pivMode = (PIVMode) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner videoEncodeList = (Spinner) parentView.findViewById(R.id.videoEncodeList);
        videoEncodeList.setAdapter(new VideoEncodeAdapter(getContext()));
        videoEncodeList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                videoEncoding = (VideoEncodeFormat) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        aspectRatioList = (Spinner) parentView.findViewById(R.id.aspectRatioList);
        aspectRatioAdapter = new ModelcAspectRatioAdapter(getContext());
        aspectRatioAdapter.setData(CameraProduct.XT706, displayMode);
        aspectRatioList.setAdapter(aspectRatioAdapter);
        aspectRatioList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                aspectRatio = PhotoAspectRatio.find((String) parent.getAdapter().getItem(position), CameraProduct.XT706);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner photoFormatList = (Spinner) parentView.findViewById(R.id.photoFormatList);
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

        Spinner videoFormatList = (Spinner) parentView.findViewById(R.id.videoFormatList);
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

        Spinner photoAEBCountList = (Spinner) parentView.findViewById(R.id.photoAEBCountList);
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

        photoTimelapseIntervalList = (Spinner) parentView.findViewById(R.id.photoTimelapseIntervalList);

        photoTimelapseIntervalList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                photoTimelapseInterval = (PhotoTimelapseInterval) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner photoBurstCountList = (Spinner) parentView.findViewById(R.id.photoBurstCountList);
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

        photoCustomStyleContrast = (EditText) parentView.findViewById(R.id.photoCustomStyleContrast);
        photoCustomStyleSaturation = (EditText) parentView.findViewById(R.id.photoCustomStyleSaturation);
        photoCustomStyleSharpness = (EditText) parentView.findViewById(R.id.photoCustomStyleSharpness);

        Spinner photoStyleList = (Spinner) parentView.findViewById(R.id.photoStyleList);
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

        exposureValueList = (Spinner) parentView.findViewById(R.id.exposureValueList);
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

        shutterList = (Spinner) parentView.findViewById(R.id.shutterList);
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

        Spinner ISOList = (Spinner) parentView.findViewById(R.id.ISOList);
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

        colorStyle = (Spinner) parentView.findViewById(R.id.colorStyleList);
        colorStyle.setAdapter(new ModelcColorStyleAdapter(getContext()));
        colorStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cameraColorStyle = (ColorStyle) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner irColorStyle = (Spinner) parentView.findViewById(R.id.irColorStyleList);
        irColorStyle.setAdapter(new IrColorStyleAdapter(getContext()));
        irColorStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                irColor = (IrColor) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner whiteBalanceTypeList = (Spinner) parentView.findViewById(R.id.whiteBalanceTypeList);
        WhiteBalanceTypeAdapter whiteBalanceTypeAdapter = new WhiteBalanceTypeAdapter(getContext());
        whiteBalanceTypeAdapter.setData(Arrays.asList(rangeManager.getCameraWhiteBalanceType()));
        whiteBalanceTypeList.setAdapter(whiteBalanceTypeAdapter);
        whiteBalanceTypeList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cameraWhiteBalanceType = (WhiteBalanceType) parent.getAdapter().getItem(position);
                EditText colorTempView = ((EditText) parentView.findViewById(R.id.colorTemperatureValue));
                if (cameraWhiteBalanceType == WhiteBalanceType.CUSTOM) {
                    RangePair<Integer> colorTemp = rangeManager.getColorTemperature();

                    colorTempView.setVisibility(View.VISIBLE);
                    colorTempView.setHint("color temperature range from " + colorTemp.getValueFrom() + " to " + colorTemp.getValueTo());
                } else {
                    colorTempView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner antiFlickerList = (Spinner) parentView.findViewById(R.id.antiFlickerList);
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

        Spinner autoExposureLockStateList = (Spinner) parentView.findViewById(R.id.autoExposureLockStateList);
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


        exposureModeList = (Spinner) parentView.findViewById(R.id.exposureModeList);
        ModelcExposureModeAdapter exposureModeAdapter = new ModelcExposureModeAdapter(getContext());
        exposureModeAdapter.setData(CameraProduct.XT706);
        exposureModeList.setAdapter(exposureModeAdapter);
        exposureModeList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cameraExposureMode = (ExposureMode) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spotMeteringAreaX = (EditText) parentView.findViewById(R.id.spotMeteringAreaX);
        spotMeteringAreaY = (EditText) parentView.findViewById(R.id.spotMeteringAreaY);

        positionX = (EditText) parentView.findViewById(R.id.positionX);
        positionY = (EditText) parentView.findViewById(R.id.positionY);

        Spinner displayModeList = (Spinner) parentView.findViewById(R.id.displayModeList);
        displayModeList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {
                        displayMode = DisplayMode.VISIBLE;
                    }
                    break;
                    case 1: {
                        displayMode = DisplayMode.IR;
                    }
                    break;
                    case 2: {
                        displayMode = DisplayMode.PICTURE_IN_PICTURE;
                    }
                    break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
