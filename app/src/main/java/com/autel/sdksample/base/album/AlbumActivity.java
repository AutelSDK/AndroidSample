package com.autel.sdksample.base.album;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.autel.AutelNet2.ablum.bean.event.AblumResponseEvent;
import com.autel.AutelNet2.ablum.bean.event.DownloadProgressEvent;
import com.autel.common.CallbackWithOneParam;
import com.autel.common.album.MediaInfo;
import com.autel.common.camera.media.VideoResolutionAndFps;
import com.autel.common.error.AutelError;
import com.autel.internal.sdk.product.datapost.PostRunnable;
import com.autel.sdk.album.AutelAlbum;
import com.autel.sdk.product.BaseProduct;
import com.autel.sdksample.R;
import com.autel.sdksample.base.BaseActivity;
import com.autel.sdksample.base.album.adapter.LocalVideoListAdapter;
import com.autel.sdksample.base.album.adapter.MediaListAdapter;
import com.autel.sdksample.util.ThreadUtils;
import com.autel.util.okhttp.OkHttpManager;
import com.autel.util.okhttp.callback.ResponseCallBack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class AlbumActivity extends BaseActivity<AutelAlbum> {
    private List<MediaInfo> mediaItems;
    private MediaInfo deleteMedia;
    private MediaInfo resolutionFromHttpHeader;
    private File resolutionFromLocalFile;
    private MediaInfo media2Download;
    private MediaListAdapter videoResolutionFromHttpHeaderAdapter;
    private LocalVideoListAdapter videoResolutionFromLocalFileAdapter;
    private MediaListAdapter mediaListAdapter;
    private MediaListAdapter videoList;

    Spinner mediaList;
    Spinner videoResolutionFromHttpHeaderList;
    Spinner videoResolutionFromLocalFileList;
    Spinner videoDownloadList;

    private OkHttpManager okHttpManager;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Album");
        EventBus.getDefault().register(this);
        videoResolutionFromHttpHeaderAdapter = new MediaListAdapter(this);
        videoResolutionFromLocalFileAdapter = new LocalVideoListAdapter(this);
        mediaListAdapter = new MediaListAdapter(this);
        videoList = new MediaListAdapter(this, MediaListAdapter.MediaType.Video);
    }

    @Override
    protected AutelAlbum initController(BaseProduct product) {
        return product.getAlbum();
    }

    @Override
    protected int getCustomViewResId() {
        return R.layout.ac_album;
    }

    @Override
    protected void initUi() {
        mediaList = (Spinner) findViewById(R.id.mediaList);
        mediaList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                deleteMedia = (MediaInfo) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        videoResolutionFromHttpHeaderList = (Spinner) findViewById(R.id.videoResolutionFromHttpHeaderList);
        videoResolutionFromHttpHeaderList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                resolutionFromHttpHeader = (MediaInfo) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        videoResolutionFromLocalFileList = (Spinner) findViewById(R.id.videoResolutionFromLocalFileList);
        videoResolutionFromLocalFileList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                resolutionFromLocalFile = (File) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        videoDownloadList = (Spinner) findViewById(R.id.videoDownloadList);
        videoDownloadList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                media2Download = (MediaInfo) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mediaItems = new ArrayList<>();

        initLocalFileList();

        findViewById(R.id.getMedia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.getMedia(0, 10, new CallbackWithOneParam<List<MediaInfo>>() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getMedia  error  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess(final List<MediaInfo> data) {
                        ThreadUtils.runOnUiThread(new PostRunnable() {
                            @Override
                            protected void task() {
                                logOut("getMedia  data  " + data.size());
                                index = data.size();
                                mediaItems = data;
                                for (MediaInfo item : data) {
                                    Log.v(TAG, "getMedia  data  " + item.getOriginalMedia() + "    " + item.getFileSize() + "   " + item.getFileTimeString() + "  SmallThumbnail  " + item.getSmallThumbnail());
                                }
                                mediaListAdapter.setData(data);
                                mediaList.setAdapter(mediaListAdapter);
                                videoResolutionFromHttpHeaderAdapter.setData(data);
                                videoResolutionFromHttpHeaderList.setAdapter(videoResolutionFromHttpHeaderAdapter);
                                videoList.setData(data);
                                videoDownloadList.setAdapter(videoList);
                            }
                        });
                    }
                });
            }
        });
        findViewById(R.id.getMMCMedia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.getFMCMedia(0, 10, new CallbackWithOneParam<List<MediaInfo>>() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getMedia  error  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess(final List<MediaInfo> data) {
                        ThreadUtils.runOnUiThread(new PostRunnable() {
                            @Override
                            protected void task() {
                                logOut("getMedia  data  " + data.size());
                                index = data.size();
                                mediaItems = data;
                                for (MediaInfo item : data) {
                                    Log.v(TAG, "getMedia  data  " + item.getOriginalMedia() + "    " + item.getFileSize() + "   " + item.getFileTimeString() + "  SmallThumbnail  " + item.getSmallThumbnail());
                                }
                                mediaListAdapter.setData(data);
                                mediaList.setAdapter(mediaListAdapter);
                                videoResolutionFromHttpHeaderAdapter.setData(data);
                                videoResolutionFromHttpHeaderList.setAdapter(videoResolutionFromHttpHeaderAdapter);
                                videoList.setData(data);
                                videoDownloadList.setAdapter(videoList);
                            }
                        });
                    }
                });
            }
        });
        findViewById(R.id.appendMedia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.getMedia(mediaItems.size() - 1, 30, new CallbackWithOneParam<List<MediaInfo>>() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("appendMedia  error  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess(List<MediaInfo> data) {
                        index = data.size();
                        mediaItems.addAll(data);
                        logOut("appendMedia  data  " + mediaItems.size());
                        for (MediaInfo item : data) {
                            Log.v(TAG, "getMedia  data  " + item.getOriginalMedia() + "    " + item.getFileSize() + "   " + item.getFileTimeString() + "  SmallThumbnail  " + item.getSmallThumbnail());
                        }
                        mediaListAdapter.setData(data);
                        mediaList.setAdapter(mediaListAdapter);
                        videoResolutionFromHttpHeaderAdapter.setData(data);
                        videoResolutionFromHttpHeaderList.setAdapter(videoResolutionFromHttpHeaderAdapter);
                        videoList.setData(data);
                        videoDownloadList.setAdapter(videoList);
                    }
                });
            }
        });
        findViewById(R.id.getMedia_Next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.getMedia(index + 1, 30, new CallbackWithOneParam<List<MediaInfo>>() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getMedia_Next  error  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess(List<MediaInfo> data) {
                        index += data.size();
                        logOut("getMedia_Next  data  " + data);
                        mediaItems = data;
                        for (MediaInfo item : data) {
                            Log.v(TAG, "getMedia  data  " + item.getOriginalMedia() + "    " + item.getFileSize() + "   " + item.getFileTimeString() + "  SmallThumbnail  " + item.getSmallThumbnail());
                        }

                        mediaListAdapter.setData(data);
                        mediaList.setAdapter(mediaListAdapter);
                        videoResolutionFromHttpHeaderAdapter.setData(data);
                        videoResolutionFromHttpHeaderList.setAdapter(videoResolutionFromHttpHeaderAdapter);
                        videoList.setData(data);
                        videoDownloadList.setAdapter(videoList);
                    }
                });
            }
        });

        findViewById(R.id.deleteAllMedia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaItems.size()==0) return;
                mController.deleteMedia(mediaItems, new CallbackWithOneParam<List<MediaInfo>>() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("deleteMedia  error  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess(List<MediaInfo> data) {
                        logOut("deleteMedia  size  " + data.size());
                        for (MediaInfo item : data) {
                            Log.v(TAG, "deleteMedia  data  onFailure " + item.getOriginalMedia() + "    " + item.getFileSize() + "   " + item.getFileTimeString() + "  SmallThumbnail  " + item.getSmallThumbnail());
                        }
                    }
                });
            }
        });
        findViewById(R.id.deleteMedia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.deleteMedia(deleteMedia, new CallbackWithOneParam<List<MediaInfo>>() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("deleteMedia  error  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess(List<MediaInfo> data) {
                        logOut("deleteMedia  size  " + data.size());
                        for (MediaInfo item : data) {
                            Log.v(TAG, "deleteMedia  data  onFailure " + item.getOriginalMedia() + "    " + item.getFileSize() + "   " + item.getFileTimeString() + "  SmallThumbnail  " + item.getSmallThumbnail());
                        }
                    }
                });
            }
        });
        findViewById(R.id.getVideoResolutionFromHttpHeader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.getVideoResolutionFromHttpHeader(resolutionFromHttpHeader, new CallbackWithOneParam<VideoResolutionAndFps>() {
                    @Override
                    public void onFailure(AutelError error) {
                        logOut("getVideoResolutionFromHttpHeader  error  " + error.getDescription());
                    }

                    @Override
                    public void onSuccess(VideoResolutionAndFps data) {
                        logOut("getVideoResolutionFromHttpHeader  data size " + data);
                    }
                });
            }
        });

        findViewById(R.id.downloadVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= 23 && !(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
                } else {
                    downloadVideo();
                }

            }
        });
        findViewById(R.id.download_picture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaItems.size() > 0) {
                    Log.d(TAG,"AblumResponseEvent download path:"+mediaItems.get(0).getOriginalMedia());
                    mController.downloadPicture(mediaItems.get(0).getOriginalMedia(),"/sdcard/album/");
                }

            }
        });
        findViewById(R.id.getVideoResolutionFromLocalFile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoResolutionAndFps data = mController.getVideoResolutionFromLocalFile(resolutionFromLocalFile);
                if (null != data) {
                    logOut("getVideoResolutionFromLocalFile  data size " + data);
                } else {
                    logOut("getVideoResolutionFromLocalFile  data == null ");
                }
            }
        });
    }

    private void initLocalFileList() {
        File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/album/albumtest");
        if (dir.exists()) {
            Log.v("albumtest", "files " + dir.listFiles());
            if (dir.listFiles() != null) {
                videoResolutionFromLocalFileAdapter.setRfData(Arrays.asList(dir.listFiles()));
                videoResolutionFromLocalFileList.setAdapter(videoResolutionFromLocalFileAdapter);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 3:
                if (android.os.Build.VERSION.SDK_INT >= 23 && (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                    downloadVideo();
                }
                break;
        }
    }

    private void downloadVideo() {
        if (null == okHttpManager) {
            okHttpManager = new OkHttpManager.Builder().build();
        }
        if (null != media2Download) {
            String videoPath = media2Download.getOriginalMedia();
            if (!isEmpty(videoPath)) {
                videoPath = videoPath.substring(videoPath.lastIndexOf("/") + 1, videoPath.length());
            }
            okHttpManager.download(media2Download.getLargeThumbnail(), Environment.getExternalStorageDirectory().getPath() + "/album/albumtest/" + videoPath, new ResponseCallBack<File>() {
                @Override
                public void onSuccess(File file) {
                    initLocalFileList();
                    logOut("file " + file.getPath());
                }

                @Override
                public void onFailure(Throwable throwable) {
                    logOut("download onFailure " + throwable.getMessage());
                }
            });
        } else {
            logOut("video to be download is null");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ReceiveDownloadProcessEvent(DownloadProgressEvent event) {
        Log.d(TAG,"ReceiveDownloadProcessEvent: process=" + event.process + " speed=" + event.speed);
        logOut("ReceiveDownloadProcessEvent: process=" + event.process + " speed=" + event.speed);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ReceiveAblumResponseEvent(AblumResponseEvent event) {
        Log.d(TAG,"AblumResponseEvent: event=" + event.source+" "+event.refresh);

        logOut("AblumResponseEvent: event=" + event.source+" "+event.refresh);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

