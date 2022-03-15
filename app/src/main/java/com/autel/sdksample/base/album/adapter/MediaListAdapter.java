package com.autel.sdksample.base.album.adapter;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.autel.common.album.MediaInfo;
import com.autel.downloader.HttpDownloadConfig;
import com.autel.downloader.HttpDownloadManager;
import com.autel.downloader.bean.DownloadTask;
import com.autel.downloader.bean.HttpDownloadCallback;
import com.autel.downloader.utils.DownloadUtils;
import com.autel.sdksample.R;
import com.autel.sdksample.base.adapter.SelectorAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MediaListAdapter extends SelectorAdapter<MediaInfo> implements HttpDownloadCallback {
    public enum MediaType {
        Video,
        Photo,
        Media
    }

    public static String[] videos = new String[]{"mp4", "mov", "MP4", "MOV", ".video"};
    public static String[] photos = new String[]{"jpg", "JPG", "dng", "DNG", "png", "PNG", ".photo"};

    private MediaType mediaType = MediaType.Media;

    public MediaListAdapter(Context context) {
        super(context);
        getHttpDownloadManager(mContext).addDownloadCallback(this);
    }

    public MediaListAdapter(Context context, MediaType mediaType) {
        super(context);
        this.mediaType = mediaType;
        getHttpDownloadManager(mContext).addDownloadCallback(this);
    }


    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }


    public void setData(List<MediaInfo> data) {
        if (mediaType == MediaType.Media) {
            this.elementList = data;
        } else if (mediaType == MediaType.Video) {
            this.elementList = new ArrayList<>();
            for (MediaInfo item : data) {
                for (String tag : videos) {
                    if (item.getOriginalMedia().endsWith(tag)) {
                        elementList.add(item);
                        break;
                    }
                }
            }
        } else if (mediaType == MediaType.Photo) {
            this.elementList = new ArrayList<>();
            for (MediaInfo item : data) {
                for (String tag : photos) {
                    if (item.getOriginalMedia().endsWith(tag)) {
                        elementList.add(item);
                        break;
                    }
                }
            }
        }

        notifyDataSetInvalidated();
    }

    static HttpDownloadManager mHttpDownloadManager = null;

    private static HttpDownloadManager getHttpDownloadManager(Context context) {
        if (null == mHttpDownloadManager) {
            mHttpDownloadManager = new HttpDownloadManager();
            mHttpDownloadManager.init(context, new HttpDownloadConfig.Builder().setMaxThread(3).build());
        }

        return mHttpDownloadManager;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = null;
        if (null == convertView) {
            convertView = View.inflate(mContext, R.layout.album_item, null);
        }
        textView = (TextView) convertView.findViewById(R.id.spinner_item_text);
        String originUrl = elementList.get(position).getOriginalMedia();
        if (position < elementList.size() && position >= 0) {
            textView.setText(originUrl);
        }
        convertView.findViewById(R.id.downloadStart).setTag(position);
        convertView.findViewById(R.id.downloadStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = elementList.get((int) v.getTag()).getOriginalMedia();
                getHttpDownloadManager(mContext).start(new DownloadTask(url, getSavePath(mContext, url)));
            }
        });
        convertView.findViewById(R.id.downloadCancel).setTag(position);
        convertView.findViewById(R.id.downloadCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = elementList.get((int) v.getTag()).getOriginalMedia();
                getHttpDownloadManager(mContext).cancel(DownloadUtils.getTaskId(url, getSavePath(mContext, url)));
            }
        });

        DownloadTask task = getHttpDownloadManager(mContext)
                .getTaskInfo(DownloadUtils.getTaskId(originUrl, getSavePath(mContext, originUrl)));
        if (null != task) {
            float percent = (float) task.getReceiveLength() / (float) task.getTotalLength();
            ((TextView) convertView.findViewById(R.id.downloadProgress)).setText(String.valueOf((int) (percent * 100)));
        }


        convertView.setTag(position);
        return convertView;
    }

    private String getSavePath(Context context, String url) {
        return getAlbumPath(context, url.substring(url.lastIndexOf("/") + 1));
    }

    private String getAlbumPath(Context context, String fileName) {
        return getRootDirPath(context) + File.separator + "album" + File.separator + fileName;
    }

    private String getRootDirPath(Context context) {
        String packageName = context.getPackageName();
        String rootName = packageName.substring(packageName.lastIndexOf(".") + 1);
        return Environment.getExternalStorageDirectory() + File.separator + rootName;
    }

    Handler handler = new Handler(Looper.myLooper());

    @Override
    public void createdTask(DownloadTask task, Object object) {

    }

    @Override
    public void waitting(int task_id, long receive_length, long total_length) {

    }

    @Override
    public void started(int task_id) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public void progress(int task_id, long receive_length, long total_length) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public void completed(int task_id, String path) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public void paused(int task_id, long receive_length, long total_length) {

    }

    @Override
    public void error(int task_id, Throwable e) {

    }
}
