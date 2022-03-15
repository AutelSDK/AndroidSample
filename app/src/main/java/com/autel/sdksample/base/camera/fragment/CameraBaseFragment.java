package com.autel.sdksample.base.camera.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.autel.common.CallbackWithNoParam;
import com.autel.common.CallbackWithOneParam;
import com.autel.common.CallbackWithTwoParams;
import com.autel.common.camera.base.MediaMode;
import com.autel.common.camera.base.MediaStatus;
import com.autel.common.camera.base.SDCardState;
import com.autel.common.camera.base.WorkState;
import com.autel.common.error.AutelError;
import com.autel.sdk.camera.AutelBaseCamera;
import com.autel.sdksample.R;
import com.autel.sdksample.base.camera.CameraActivity;

public class CameraBaseFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    protected TextView log_output;

    AutelBaseCamera baseCamera;

    private Spinner mediaModeList;
    protected MediaMode mediaMode = MediaMode.SINGLE;

    protected void initClick(View view) {
        log_output = (TextView) view.findViewById(R.id.camera_log_output);
        baseCamera = ((CameraActivity) getActivity()).getCurrentCamera();

        mediaModeList = (Spinner) view.findViewById(R.id.mediaModeList);
        mediaModeList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mediaMode = MediaMode.SINGLE;
                        break;
                    case 1:
                        mediaMode = MediaMode.VIDEO;
                        break;
                    case 2:
                        mediaMode = MediaMode.TIMELAPSE;
                        break;
                    case 3:
                        mediaMode = MediaMode.BURST;
                        break;
                    case 4:
                        mediaMode = MediaMode.AEB;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        view.findViewById(R.id.setMediaMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseCamera.setMediaMode(mediaMode, new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setMediaMode  description  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("setMediaMode state onSuccess");
                    }
                });
            }
        });

        view.findViewById(R.id.setCameraSDCardStateListener).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseCamera.setSDCardStateListener(new CallbackWithOneParam<SDCardState>() {
                    @Override
                    public void onSuccess(SDCardState state) {
                        logOut("setSDCardStateListener  state  " + state);
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setSDCardStateListener  description  " + error.getDescription());
                    }
                });
            }
        });
        view.findViewById(R.id.resetCameraSDCardStateListener).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseCamera.setSDCardStateListener(null);
            }
        });
        view.findViewById(R.id.getMediaMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseCamera.getMediaMode(new CallbackWithOneParam<MediaMode>() {
                    @Override
                    public void onSuccess(MediaMode data) {
                        logOut("getMediaMode " + data);
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getMediaMode " + error.getDescription());
                    }
                });
            }
        });
        view.findViewById(R.id.getVersion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseCamera.getVersion(new CallbackWithOneParam<String>() {
                    @Override
                    public void onSuccess(String data) {
                        logOut("getVersion " + data);
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getVersion " + error.getDescription());
                    }
                });
            }
        });
        view.findViewById(R.id.getTimeStamp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                baseCamera.getTimeStamp(new CallbackWithOneParam<Long>() {
//                    @Override
//                    public void onSuccess(Long data) {
//                        logOut("getTimeStamp " + data);
//                    }
//
//                    @Override
//                    public void onFailure(AutelError error) {
//                        logOut("getTimeStamp " + error.getDescription());
//                    }
//                });
            }
        });
        view.findViewById(R.id.getProduct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut("getProduct " + baseCamera.getProduct());
            }
        });
        view.findViewById(R.id.getSdFreeSpace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseCamera.getSDCardFreeSpace(new CallbackWithOneParam<Long>() {
                    @Override
                    public void onSuccess(Long data) {
                        logOut("getSDCardFreeSpace " + data);
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getSDCardFreeSpace " + error.getDescription());
                    }
                });
            }
        });
        view.findViewById(R.id.getSDCardStatus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseCamera.getSDCardState(new CallbackWithOneParam<SDCardState>() {
                    @Override
                    public void onSuccess(SDCardState data) {
                        logOut("getSDCardState " + data);
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getSDCardState " + error.getDescription());
                    }
                });
            }
        });
        view.findViewById(R.id.getWorkStatus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseCamera.getWorkState(new CallbackWithOneParam<WorkState>() {
                    @Override
                    public void onSuccess(WorkState data) {
                        logOut("getWorkState " + data);
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getWorkState " + error.getDescription());
                    }
                });
            }
        });
        view.findViewById(R.id.resetCamera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseCamera.resetDefaults(new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("resetDefaults " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("resetDefaults onSuccess");
                    }
                });
            }
        });
        view.findViewById(R.id.formatSDCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseCamera.formatSDCard(new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("formatSDCard " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("formatSDCard onSuccess");
                    }
                });
            }
        });

        view.findViewById(R.id.stopTimelapse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseCamera.stopTakePhoto(new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("stopTakePhoto " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("stopTakePhoto onSuccess");
                    }
                });
            }
        });
        view.findViewById(R.id.takePhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseCamera.startTakePhoto(new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("startTakePhoto " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("startTakePhoto onSuccess");
                    }
                });
            }
        });
        view.findViewById(R.id.stopRecordVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseCamera.stopRecordVideo(new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("stopRecordVideo " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("stopRecordVideo onSuccess");
                    }
                });
            }
        });
        view.findViewById(R.id.startRecordVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseCamera.startRecordVideo(new CallbackWithNoParam() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("startRecordVideo " + error.getDescription());
                    }

                    @Override
                    public void onSuccess() {
                        logOut("startRecordVideo onSuccess");
                    }
                });
            }
        });
        view.findViewById(R.id.resetCameraMediaStateListener).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseCamera.setMediaStateListener(null);
            }
        });
        view.findViewById(R.id.setCameraMediaStateListener).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseCamera.setMediaStateListener(new CallbackWithTwoParams<MediaStatus, String>() {
                    @Override
                    public void onSuccess(MediaStatus state, String data) {
                        logOut("setMediaStateListener state " + state+" data "+data);
                    }

                    @Override
                    public void onFailure(AutelError error) {
                        logOut("setMediaStateListener error " + error.getDescription());
                    }
                });
            }
        });
    }

    protected void logOut(String log) {
        CameraActivity activity = ((CameraActivity) getActivity());
        if (null != activity) {
            activity.logOut(log);
        }

    }

    protected boolean isEmpty(String value) {
        return null == value || "".equals(value);
    }
}
