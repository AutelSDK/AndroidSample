package com.autel.sdksample.util;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class ThreadUtils {

    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    private static Handler sMainHandler = new Handler(Looper.getMainLooper());

    /**
     * 非UI线程执行
     * @param runnable
     */
    public static void runOnNonUIthread(Runnable runnable) {
        threadPool.submit(runnable);
    }

    /**
     * UI线程执行
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        sMainHandler.post(runnable);
    }


    public static void postDelayedOnUiThread(Runnable runnable, long delayMillis) {
        sMainHandler.postDelayed(runnable, delayMillis);
    }

    public static boolean isMainThread(){
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * 获取当前线程名称
     * @return
     */
    public static String getCurrentThreadName() {
        String result = "UNKNOWN";
        Looper looper = Looper.myLooper();
        if (looper != null) {
            result = looper.getThread().getName();
        }
        return result;
    }
    /**
     * 返回 Future
     * Future.get()可获得返回结果
     *
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Future<?> submit(Callable t) {
        return threadPool.submit(t);
    }


    public static final Handler getMainHander() {
        return sMainHandler;
    }

    public static void removeCallbacks(){
        getMainHander().removeCallbacksAndMessages(null);
    }
}
