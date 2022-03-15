package com.autel.sdksample.evo.mission.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Typeface;

import com.autel.sdksample.R;

import java.util.Locale;


/**
 * Created by A15387 on 2016/5/17.
 */
public class MapMarkerIconUtil {
    private static final String ALTITUDE_FORMAT = "A%d%s";
    private static final String DISTANCE_FORMAT = "%d%s";
    private static final int ALTITUDE_FONT_SIZE = 20;
    private static final int DISTANCE_FONT_SIZE = 20;
    private static final int LABEL_HEIGHT = 25;
    private static final float FONT_WIDTH_RATIO = 0.7f;

    public static Bitmap getHomeMarkerIcon(){
        return MapImageUtil.drawableToBitmap(R.mipmap.home_location_icon);
    }


    public static Bitmap getCombineDroneMarkerIcon(){
        return MapImageUtil.drawableToBitmap(R.mipmap.drone_location_icon);
    }

    public static float[] lableOrigin = new float[2];
    public static float[] markerTextOrigin = new float[2];

    static {
        //初始化markerTextOrigin
        Bitmap markerBitmap = MapImageUtil.drawableToBitmap(R.mipmap.marker_point);
        int i = markerBitmap.getWidth();
        int j = markerBitmap.getHeight();
        lableOrigin[0] = (i / 2.0F);
        lableOrigin[1] = j;
        // / set up the size of the text in the marker for example , the number
        // in the marker
        markerTextOrigin[0] = lableOrigin[0];
        markerTextOrigin[1] = (2.0F * lableOrigin[1] / 5.0F);
    }


    public static Bitmap getCombineWaypointMarkerIcon(int index,boolean isValid){
        Paint paintM = new Paint(257);
        paintM.setColor(Color.WHITE);
        if(index < 10){
            paintM.setTextSize(MapImageUtil.dip2px(18.0f));
        }else{
            paintM.setTextSize(MapImageUtil.dip2px(14.0F));
        }
        paintM.setTextAlign(Paint.Align.CENTER);
        Bitmap markerBitmap = MapImageUtil.drawableToBitmap(R.mipmap.marker_point);
        if(!isValid){
            markerBitmap = MapImageUtil.drawableToBitmap(R.mipmap.marker_point_invalid);
        }
        Bitmap waypointBitmap = Bitmap.createBitmap(markerBitmap.getWidth(), markerBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(waypointBitmap);
        localCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        localCanvas.drawBitmap(markerBitmap, 0.0F, 0.0F, null);
        int margin = 2;
        localCanvas.drawText(String.valueOf(index), markerTextOrigin[0], markerTextOrigin[1] + margin, paintM);
        return waypointBitmap;
    }

    public static Bitmap getCombineWaypointLableIcon(int index, double altitude, boolean isValid){
        int intAltitude = TransformUtils.isEnUnit() ? (int)TransformUtils.meter2feet(altitude,1) : (int)TransformUtils.feet2meter(altitude,1);

        Bitmap markerBitmap = MapMarkerIconUtil.getCombineWaypointMarkerIcon(index,isValid);
        int markerWidth = markerBitmap.getWidth();
        int markerHeight = markerBitmap.getHeight();
        String labelText = String.format(Locale.getDefault(), ALTITUDE_FORMAT, intAltitude, TransformUtils.changeRangeUnitForUnitFlag(intAltitude));
        float rectFWidth = labelText.length() * ALTITUDE_FONT_SIZE * FONT_WIDTH_RATIO;
        float rectFHeight = LABEL_HEIGHT;

        Bitmap canvasBitmap = Bitmap.createBitmap((int)rectFWidth + markerWidth, markerHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(canvasBitmap);
        canvas.drawColor(Color.WHITE,PorterDuff.Mode.CLEAR);

        RectF rectF = new RectF(markerWidth * 0.75f, markerHeight * 0.5f, (markerWidth * 0.75f) + rectFWidth, rectFHeight + (markerHeight * 0.5f));
        Paint paintBg = new Paint();
        paintBg.setColor(AutelConfigManager.instance().getAppContext().getResources().getColor(R.color.waypoint_distance_background));
        paintBg.setStyle(Paint.Style.FILL);

        Paint paintBorder = new Paint();
        paintBorder.setStrokeWidth(1);
        paintBorder.setAntiAlias(true);
        paintBorder.setStyle(Paint.Style.STROKE);
        paintBorder.setColor(Color.BLACK);

        canvas.drawRoundRect(rectF,25,25,paintBg);
        canvas.drawRoundRect(rectF,25,25,paintBorder);
        canvas.drawBitmap(markerBitmap,0,0,new Paint());

        Paint labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
        labelPaint.setColor(Color.BLACK);
        labelPaint.setTextSize(ALTITUDE_FONT_SIZE);
        labelPaint.setTextAlign(Paint.Align.CENTER);

        canvas.drawText(labelText, rectF.left + (rectFWidth * 0.5f), rectF.bottom - 5, labelPaint);

        return canvasBitmap;
    }

    public static Bitmap getDistanceMarker(double distance) {
        int intDistance = TransformUtils.isEnUnit() ? (int)TransformUtils.meter2feet(distance,1) : (int)TransformUtils.feet2meter(distance,1);;

        String labelText = String.format(Locale.getDefault(), DISTANCE_FORMAT, intDistance, TransformUtils.changeRangeUnitForUnitFlag(intDistance));
        float rectWidth = labelText.length() * DISTANCE_FONT_SIZE * FONT_WIDTH_RATIO;
        float rectHeight = LABEL_HEIGHT;
        Bitmap bitmap = Bitmap.createBitmap((int)rectWidth, (int)rectHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);

        RectF rectF = new RectF(0,0,rectWidth,rectHeight);
        Paint paintBg = new Paint();
        paintBg.setAntiAlias(true);
        paintBg.setColor(AutelConfigManager.instance().getAppContext().getResources().getColor(R.color.waypoint_distance_background));

        Paint paintBorder = new Paint();
        paintBorder.setStyle(Paint.Style.STROKE);
        paintBorder.setColor(Color.BLACK);
        paintBorder.setAntiAlias(true);
        paintBorder.setStrokeWidth(1);

        canvas.drawRoundRect(rectF,25,25,paintBg);
        canvas.drawRoundRect(rectF,25,25,paintBorder);

        Paint labelPaint = new Paint();
        labelPaint.setAntiAlias(true);
        labelPaint.setColor(Color.BLACK);
        labelPaint.setTextSize(DISTANCE_FONT_SIZE);
        labelPaint.setTextAlign(Paint.Align.CENTER);
        labelPaint.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText(labelText, rectWidth * 0.5f, rectF.bottom - 5, labelPaint);

        return bitmap;
    }

    public static Bitmap getCombineHotPointMarkerIcon(){
        return MapImageUtil.drawableToBitmap(R.mipmap.favor_marker_point);
    }

    public static Bitmap getCombineHotPointMarkerIconInValid(){
        return MapImageUtil.drawableToBitmap(R.mipmap.favor_marker_icon_unable);
    }


    public static Bitmap getFlightRecordfootmarkIcon(Bitmap bgBitmap,Bitmap bitmap){

        Canvas localCanvas = new Canvas(bgBitmap);
        Paint paintBg = new Paint();

        localCanvas.drawBitmap(bitmap,(int)(7*((float)bgBitmap.getWidth()/102)),(int)(8*((float)bgBitmap.getWidth()/108)),paintBg);
        return bgBitmap;
    }
}
