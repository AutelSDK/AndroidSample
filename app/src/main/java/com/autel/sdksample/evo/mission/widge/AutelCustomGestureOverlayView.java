package com.autel.sdksample.evo.mission.widge;

import android.content.Context;
import android.gesture.GestureOverlayView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.autel.common.mission.AutelCoordinate3D;
import com.autel.sdksample.evo.mission.bean.AutelGPSLatLng;
import com.autel.util.log.AutelLog;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by A15556 on 2015/11/23.
 */
public class AutelCustomGestureOverlayView extends GestureOverlayView {

    private static final String TAG = "CustomGestureOverlayView";

    private Context mContext;
    private static final int TOLERANCE = 15;
    private double toleranceInPixels;

    private GestureCallbacks mGestureCallbacks;

    public AutelCustomGestureOverlayView(Context context) {
        super(context);
        this.mContext = context;
    }

    public AutelCustomGestureOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutelCustomGestureOverlayView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void addGestureCallbacks(GestureCallbacks mGestureCallbacks){
        this.mGestureCallbacks = mGestureCallbacks;
    }

    public void init(Context context){
        this.mContext = context;
        toleranceInPixels = scaleDpToPixels(TOLERANCE);
        this.addOnGestureListener(new OnGestureListener() {

            @Override
            public void onGesture(GestureOverlayView overlay, MotionEvent event) {}

            @Override
            public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) { }

            @Override
            public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {

                AutelLog.d(TAG, "   total points : " + getGesture().getStrokes().get(0).length);

                List<AutelGPSLatLng> path = decodeGesture();

                if (path.size() > 1) {
                    // path = MapUtils.simplify(path,
                    // toleranceInPixels);
                    // 把从手势上拿到的点进行简化
                    if(mGestureCallbacks != null){
                        path = getSimplifyList(mContext, path, toleranceInPixels,
                                mGestureCallbacks.getRemainMax());
                    }
                }
                if (path != null && path.size() == 2) {
                    if (distanceBetweenPoints(path.get(0), path.get(1)) < 50) {
                        path.remove(1);
                    }
                }
                // use call back to finish the code
                if(mGestureCallbacks != null){
                    mGestureCallbacks.projectMarker(path);
                }
            }

            @Override
            public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {

            }
        });
    }

    public void enableGesture(){
        this.setVisibility(View.VISIBLE);
        setEnabled(true);
    }

    public void disableGesture(){
        this.setVisibility(View.GONE);
        setEnabled(false);
    }

    private int scaleDpToPixels(double value){
        // / get the density of current monitor
        final float scale = getResources().getDisplayMetrics().density;
        AutelLog.d(TAG, "   tag :    " + (int) Math.round(value * scale));
        return (int) Math.round(value * scale);
    }

    // all points
    private List<AutelGPSLatLng> decodeGesture(){
        List<AutelGPSLatLng> path = new ArrayList<>();
        float[] arrayOfFloat = getGesture().getStrokes().get(0).points;
        for (int i = 0; i < arrayOfFloat.length; i = i + 2) {
            path.add(AutelGPSLatLng.createFromDrone(new AutelCoordinate3D((int) arrayOfFloat[i], (int) arrayOfFloat[i + 1])));
        }
        return path;
    }

    private double distanceBetweenPoints(AutelGPSLatLng latlng1, AutelGPSLatLng latlng2){
        double x = Math.pow(latlng1.getLat4Drone() - latlng2.getLat4Drone(), 2);
        double y = Math.pow(latlng1.getLng4Drone() - latlng2.getLng4Drone(), 2);
        return Math.sqrt(x + y);
    }

    public static List<AutelGPSLatLng> getSimplifyList(Context context, List<AutelGPSLatLng> list, double tolerance, int num){

        List<AutelGPSLatLng> ResultList = simplify(list, tolerance);
        if (num >= ResultList.size()) {
            return ResultList;
        }
        List<AutelGPSLatLng> newresult = new ArrayList<>();
        ArrayList<LineObject> lineList = new ArrayList<>();

        if (0 == num) {
            return null;
        }

        if (1 == num) {
            newresult.add(AutelGPSLatLng.createFromDrone(new AutelCoordinate3D(list.get(0).getLat4Drone(),list.get(0).getLng4Drone())));
            return newresult;
        }

        if (2 == num) {
            newresult.add(AutelGPSLatLng.createFromDrone(new AutelCoordinate3D(list.get(0).getLat4Drone(),list.get(0).getLng4Drone())));
            newresult.add(AutelGPSLatLng.createFromDrone(new AutelCoordinate3D(list.get(list.size() - 1).getLat4Drone(),list.get(list.size() - 1).getLng4Drone())));
            return newresult;
        }

        int[] temp = new int[num];
        int i = 1;
        // construct a bi-tree // another method
        lineList.add(new LineObject(0, list.size() - 1));
        temp[0] = 0;
        temp[num - 1] = list.size() - 1;

        while (!lineList.isEmpty()) {

            LineObject lo = lineList.get(0);
            int middle = getMaxPoint(list, lo.start, lo.end, tolerance);
            if (-1 != middle) {
                temp[i] = middle;
                i++;
                lineList.add(new LineObject(lo.start, middle));
                lineList.add(new LineObject(middle, lo.end));
            }
            if (i == num - 1) {
                break;
            }
            lineList.remove(0);
        }

        sort(temp);
        // Arrays.sort(temp);

        for (int j = 0; j < num; j++) {
            newresult.add(list.get(temp[j]));
        }

        return newresult;

    }

    private static void sort(int[] array){

        for (int i = 1; i < array.length; i++) {

            int j = i - 1;
            int temp = array[i];
            while (j >= 0 && array[j] > temp) {
                array[j + 1] = array[j];
                j--;
            }

            if (j != i - 1) {
                array[j + 1] = temp;
            }

        }
    }


    private static int getMaxPoint(List<AutelGPSLatLng> list, int start, int end, double tolerance){

        int index = 0;
        double dmax = 0;
        int lastIndex = end - 1;

        // Find the point with the maximum distance
        for (int i = start + 1; i < lastIndex; i++) {
            double d = pointToLineDistance(list.get(start), list.get(end), list.get(i));
            if (d > dmax) {
                index = i;
                dmax = d;
            }
        }

        if (dmax > tolerance) {
            return index;
        } else
            return -1;
    }


    public static List<AutelGPSLatLng> simplify(List<AutelGPSLatLng> list, double tolerance){

        int index = 0;
        double dmax = 0;
        int lastIndex = list.size() - 1;

        // Find the point with the maximum distance
        for (int i = 1; i < lastIndex; i++) {
            double d = pointToLineDistance(list.get(0), list.get(lastIndex), list.get(i));
            if (d > dmax) {
                index = i;
                dmax = d;
            }
        }

        // If max distance is greater than epsilon, recursively simplify
        List<AutelGPSLatLng> ResultList = new ArrayList<>();
        if (dmax > tolerance) {
            // Recursive call
            List<AutelGPSLatLng> recResults1 = simplify(list.subList(0, index + 1), tolerance);
            List<AutelGPSLatLng> recResults2 = simplify(list.subList(index, lastIndex + 1), tolerance);

            // Build the result list remove the overlay point
            recResults1.remove(recResults1.size() - 1);
            ResultList.addAll(recResults1);
            ResultList.addAll(recResults2);
        } else {
            // if epsilon is greater than max distance , choose the first and
            // the last point
            ResultList.add(list.get(0));
            ResultList.add(list.get(lastIndex));
        }

        // Return the result
        return ResultList;

    }


    public static double pointToLineDistance(AutelGPSLatLng L1, AutelGPSLatLng L2,
                                             AutelGPSLatLng P) {
        double A = P.getLat4Drone() - L1.getLat4Drone();
        double B = P.getLng4Drone() - L1.getLng4Drone();
        double C = L2.getLat4Drone() - L1.getLat4Drone();
        double D = L2.getLng4Drone() - L1.getLng4Drone();

        double dot = A * C + B * D;
        double len_sq = C * C + D * D;
        double param = dot / len_sq;

        double xx, yy;
        // the segment means the line between the start point and the end point
        if (param < 0) // point behind the segment
        {
            xx = L1.getLat4Drone();
            yy = L1.getLng4Drone();
        } else if (param > 1) // point after the segment
        {
            xx = L2.getLat4Drone();
            yy = L2.getLng4Drone();
        } else { // point on the side of the segment
            xx = L1.getLat4Drone() + param * C;
            yy = L1.getLng4Drone() + param * D;
        }

        return Math.hypot(xx - P.getLat4Drone(), yy - P.getLng4Drone());
    }



}

class LineObject{

    public LineObject(int start, int end){

        this.start = start;
        this.end = end;
    }

    int start;
    int end;
}
