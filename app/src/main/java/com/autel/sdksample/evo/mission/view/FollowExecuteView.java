package com.autel.sdksample.evo.mission.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.autel.sdksample.R;


/**
 * Created by A15387 on 2017/11/14.
 */

public class FollowExecuteView extends FrameLayout {


    private LayoutInflater inflater;
    private FrameLayout titleContainer;
    private FrameLayout bottomContainer;
    private ViewGroup contentContainer;
    private FollowExecuteListener followExecuteListener;
    private TextView distanceTv2;
    private GestureDetector gestureDetector;

    public FollowExecuteView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public FollowExecuteView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FollowExecuteView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(final Context context) {
        inflater = LayoutInflater.from(context);
        ConstraintLayout view = (ConstraintLayout) inflater.inflate(R.layout.fragment_mission_container,null);
        titleContainer = (FrameLayout) view.findViewById(R.id.title_container);
        bottomContainer = (FrameLayout) view.findViewById(R.id.bottom_container);
        contentContainer = (ViewGroup)view.findViewById(R.id.content_container);
        this.addView(view);
        showFollowExecuteView();
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                 if ((e2.getX() - e1.getX()) > 70 && (e2.getY() - e1.getY()) < 200 && (e1.getY() - e2.getY()) < 200) {
                    //右滑
                     //FollowExecuteView.this.animate().translationX(FollowExecuteView.this.getWidth()).start();
                     if(followExecuteListener != null){
                         followExecuteListener.onSlideHide();
                         followExecuteListener.onHideClick();
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

    private void showFollowExecuteView(){
        titleContainer.removeAllViews();
        bottomContainer.removeAllViews();
        contentContainer.removeAllViews();
        View executeTitle = inflater.inflate(R.layout.common_mission_title_with_hide,null);
        ((TextView)executeTitle.findViewById(R.id.common_title)).setText(R.string.mission_follow);
        TextView hideBtn = (TextView) executeTitle.findViewById(R.id.common_hide);
        hideBtn.setText(R.string.mission_hide);
        hideBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(followExecuteListener != null){
                    followExecuteListener.onHideClick();
                }
            }
        });
        View bottomView = inflater.inflate(R.layout.common_mission_bottom,null);
        TextView bottomTitle = (TextView) bottomView.findViewById(R.id.bottom_title);
        bottomTitle.setText(R.string.mission_exit);
        bottomView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(followExecuteListener != null){
                    followExecuteListener.onExitClick();
                }
            }
        });
        View contentView = inflater.inflate(R.layout.follow_execute_view,null);
        distanceTv2 = (TextView) contentView.findViewById(R.id.distance_tv);
        contentView.findViewById(R.id.face_me_btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(followExecuteListener != null){
                    followExecuteListener.onFaceMeClick();
                }
            }
        });
        View pauseBtn = contentView.findViewById(R.id.pause_btn);
        pauseBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(followExecuteListener != null){
                    followExecuteListener.onPauseClick();
                }
                
            }
        });
        titleContainer.addView(executeTitle);
        contentContainer.addView(contentView);
        bottomContainer.addView(bottomView);
    }

    public void showDistanceToAircraft(String distanceStr) {
        distanceTv2.setText(distanceStr);
    }

    public interface FollowExecuteListener{
        void onHideClick();
        void onExitClick();
        void onFaceMeClick();
        void onPauseClick();
        void onSlideHide();
    }
    
    public void setFollowExecuteListener(FollowExecuteListener followExecuteListener){
        this.followExecuteListener = followExecuteListener;
    }
}
