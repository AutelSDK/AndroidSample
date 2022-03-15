package com.autel.sdksample.evo.mission.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.autel.sdksample.R;


/**
 * Created by A15387 on 2017/11/15.
 */

public class WaypointCreateOrLoadView extends FrameLayout {

    private LayoutInflater inflater;
    private FrameLayout titleContainer;
    private FrameLayout bottomContainer;
    private ViewGroup contentContainer;
    private WaypointCreateOrLoadListener waypointCreateOrLoadListener;

    public WaypointCreateOrLoadView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public WaypointCreateOrLoadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public WaypointCreateOrLoadView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        showWaypointCreateView();
    }

    public void showWaypointCreateView(){
        clearView();
        View titleView = inflater.inflate(R.layout.common_mission_title_with_close,null);
        ((TextView)titleView.findViewById(R.id.mission_title)).setText(R.string.waypoint);
        titleView.findViewById(R.id.mission_btn_close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(waypointCreateOrLoadListener != null){
                    waypointCreateOrLoadListener.exitClick();
                }
            }
        });
        View contentView = inflater.inflate(R.layout.waypoint_create_view_content,null);
        contentView.findViewById(R.id.load_waypoint_btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(waypointCreateOrLoadListener != null){
                    waypointCreateOrLoadListener.loadClick();
                }
            }
        });
        contentView.findViewById(R.id.create_waypoint_btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(waypointCreateOrLoadListener != null){
                    waypointCreateOrLoadListener.createClick();
                }

            }
        });
        titleContainer.addView(titleView);
        contentContainer.addView(contentView);
    }

    private void clearView() {
        titleContainer.removeAllViews();
        contentContainer.removeAllViews();
    }

    public interface WaypointCreateOrLoadListener{
        void exitClick();
        void createClick();
        void loadClick();
    }

    public void setWaypointCreateOrLoadListener(WaypointCreateOrLoadListener waypointCreateOrLoadListener){
        this.waypointCreateOrLoadListener = waypointCreateOrLoadListener;
    }

}
