package com.autel.sdksample.evo.mission.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.autel.sdksample.R;


/**
 * Created by A15387 on 2017/11/14.
 */

public class FollowStartView extends FrameLayout{


    private FrameLayout titleContainer;
    private FrameLayout bottomContainer;
    private ViewGroup contentContainer;
    private LayoutInflater inflater;
    private TextView distanceTv;
    private FollowStartViewListener followStartViewListener;

    public FollowStartView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public FollowStartView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FollowStartView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        inflater = LayoutInflater.from(context);
        ConstraintLayout view = (ConstraintLayout) inflater.inflate(R.layout.fragment_mission_container,null);
        titleContainer = (FrameLayout) view.findViewById(R.id.title_container);
        bottomContainer = (FrameLayout) view.findViewById(R.id.bottom_container);
        contentContainer = (ViewGroup)view.findViewById(R.id.content_container);
        this.addView(view);
        showFollowStartView();
    }


    private void showFollowStartView(){
        View startTitle = inflater.inflate(R.layout.common_mission_title_with_close,null);
        ((TextView)startTitle.findViewById(R.id.mission_title)).setText(R.string.mission_follow);
        startTitle.findViewById(R.id.mission_btn_close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(followStartViewListener != null){
                    followStartViewListener.onCloseClick();
                }
            }
        });
        View bottomView = inflater.inflate(R.layout.common_mission_bottom,null);
        ((TextView)bottomView.findViewById(R.id.bottom_title)).setText(R.string.mission_start);
        bottomView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(followStartViewListener != null){
                    followStartViewListener.onStartClick();
                }
            }
        });
        View contentView = inflater.inflate(R.layout.follow_start_view,null);
        distanceTv = (TextView) contentView.findViewById(R.id.distance_tv);
        titleContainer.addView(startTitle);
        bottomContainer.addView(bottomView);
        contentContainer.addView(contentView);
    }

    public void setFollowStartViewListener(FollowStartViewListener followStartViewListener){
        this.followStartViewListener = followStartViewListener;
    }

    public void showDistanceToAircraft(String distanceStr) {
        distanceTv.setText(distanceStr);
    }

    public interface FollowStartViewListener{
        void onCloseClick();
        void onStartClick();
    }


}
