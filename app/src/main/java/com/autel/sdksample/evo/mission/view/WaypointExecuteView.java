package com.autel.sdksample.evo.mission.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.autel.sdksample.R;
import com.autel.sdksample.evo.mission.util.AutelConfigManager;
import com.autel.sdksample.evo.mission.util.TransformUtils;


/**
 * Created by A15387 on 2017/11/20.
 */

public class WaypointExecuteView extends FrameLayout {

    private LayoutInflater inflater;
    private FrameLayout titleContainer;
    private FrameLayout bottomContainer;
    private ViewGroup contentContainer;
    private WaypointExecuteListener waypointExecuteListener;
    private GestureDetector gestureDetector;
    private TextView nextIndexTv;
    private TextView distanceTv;

    public WaypointExecuteView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public WaypointExecuteView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public WaypointExecuteView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        inflater = LayoutInflater.from(context);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_mission_container,null);
        titleContainer = (FrameLayout) view.findViewById(R.id.title_container);
        bottomContainer = (FrameLayout) view.findViewById(R.id.bottom_container);
        contentContainer = (ViewGroup)view.findViewById(R.id.content_container);
        this.addView(view);
        showWaypointExecuteView();
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if ((e2.getX() - e1.getX()) > 70 && (e2.getY() - e1.getY()) < 200 && (e1.getY() - e2.getY()) < 200) {
                    //右滑
                    if(waypointExecuteListener != null){
                        waypointExecuteListener.slideHide();
                        waypointExecuteListener.hideClick();
                    }
                }
                return true;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        gestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    private void showWaypointExecuteView() {
        clearView();
        View titleView = inflater.inflate(R.layout.common_mission_title_with_hide,null);
        ((TextView)titleView.findViewById(R.id.common_title)).setText(R.string.waypoint);
        TextView saveBtn = (TextView) titleView.findViewById(R.id.common_hide);
        saveBtn.setText("Save");
        saveBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(waypointExecuteListener != null){
                    waypointExecuteListener.saveClick();
                }
            }
        });

        View contentView = inflater.inflate(R.layout.waypoint_execute_content,null);
        nextIndexTv = (TextView) contentView.findViewById(R.id.next_waypoint_index_tv);
        distanceTv = (TextView)contentView.findViewById(R.id.distance_to_next_waypoint_tv);
        View bottomView = inflater.inflate(R.layout.common_mission_bottom,null);
        TextView bottomBtn = (TextView) bottomView.findViewById(R.id.bottom_title);
        bottomBtn.setText(R.string.mission_exit);
        bottomBtn.setTextColor(AutelConfigManager.instance().getAppContext().getResources().getColor(R.color.red));
        bottomBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(waypointExecuteListener != null){
                    waypointExecuteListener.exitClick();
                }
            }
        });
        contentView.findViewById(R.id.pause_btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(waypointExecuteListener != null){
                    waypointExecuteListener.pauseClick();
                }
            }
        });


        titleContainer.addView(titleView);
        contentContainer.addView(contentView);
        bottomContainer.addView(bottomView);

    }

    private void clearView() {
        titleContainer.removeAllViews();
        bottomContainer.removeAllViews();
        contentContainer.removeAllViews();
    }

    public void setWaypointExecuteListener(WaypointExecuteListener waypointExecuteListener){
        this.waypointExecuteListener = waypointExecuteListener;
    }

    public void showExecuteWaypointData(int seq, int distance) {
        if(nextIndexTv != null){
            nextIndexTv.setText(seq + "");
        }
        if(distanceTv != null){
            String distanceStr = TransformUtils.getDistanceChangeUnit(distance);
            distanceTv.setText(distanceStr + TransformUtils.getUnitMeterStrEn());
        }
    }


    public interface WaypointExecuteListener{
        void saveClick();
        void exitClick();
        void slideHide();
        void hideClick();
        void pauseClick();
    }

}
