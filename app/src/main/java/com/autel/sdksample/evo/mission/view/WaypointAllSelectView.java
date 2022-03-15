package com.autel.sdksample.evo.mission.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.autel.sdksample.R;
import com.autel.sdksample.evo.mission.bean.MissionConstant;
import com.autel.sdksample.evo.mission.bean.WaypointAdvanceDataBean;
import com.autel.sdksample.evo.mission.util.TransformUtils;


/**
 * Created by A15387 on 2017/11/16.
 */

public class WaypointAllSelectView extends FrameLayout {

    private LayoutInflater inflater;
    private FrameLayout titleContainer;
    private FrameLayout bottomContainer;
    private ViewGroup contentContainer;
    private WaypointAllSelectViewListener waypointAllSelectViewListener;
    private TextView btnRight;
    private TextView selectNum;
    private int curAltitude = MissionConstant.getWaypointDefaultAltitude();
    private int curSpeed = MissionConstant.getWaypointSpeedDefault();

    public WaypointAllSelectView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public WaypointAllSelectView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public WaypointAllSelectView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        showWaypointAllSelectView();
    }

    public void showWaypointAllSelectView() {
        View titleView = inflater.inflate(R.layout.common_mission_title_close_with_text,null);
        titleView.findViewById(R.id.mission_btn_close).setVisibility(INVISIBLE);
        titleView.findViewById(R.id.common_save).setVisibility(INVISIBLE);
        ((TextView)titleView.findViewById(R.id.mission_title)).setText(R.string.waypoint);
        View contentView = inflater.inflate(R.layout.waypoint_all_select_content,null);
        selectNum = (TextView) contentView.findViewById(R.id.select_num_tv);
        initAltitudeAdvance(contentView);
        initSpeedAdvance(contentView);

        View bottomView = inflater.inflate(R.layout.common_mission_bottom_two_btn,null);
        TextView btnLeft = (TextView) bottomView.findViewById(R.id.btn_left);
        btnRight = (TextView)bottomView.findViewById(R.id.btn_right);
        btnLeft.setText("Ok");
        btnLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(waypointAllSelectViewListener != null){
                    WaypointAdvanceDataBean waypointAdvanceDataBean = new WaypointAdvanceDataBean();
                    waypointAdvanceDataBean.setAltitude(curAltitude);
                    waypointAdvanceDataBean.setSpeed(curSpeed);
                    waypointAllSelectViewListener.waypointProjectMarkerDataChange(waypointAdvanceDataBean);
                    waypointAllSelectViewListener.leftClick();
                }
            }
        });
        btnRight.setText("Delete");
        btnRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(waypointAllSelectViewListener != null){
                    waypointAllSelectViewListener.rightClick();
                }
            }
        });
        titleContainer.addView(titleView);
        contentContainer.addView(contentView);
        bottomContainer.addView(bottomView);
    }

    public void setProjectMarkerNum(int projectMarkerNum) {
        if(btnRight != null){
            btnRight.setText("Delete" + " (" + projectMarkerNum + ")");
        }
        if(selectNum != null){
            selectNum.setText(projectMarkerNum + "");
        }

    }

    public interface WaypointAllSelectViewListener{
        void exitClick();
        void leftClick();
        void rightClick();
        void waypointProjectMarkerDataChange(WaypointAdvanceDataBean waypointAdvanceDataBean);
    }

    private void initAltitudeAdvance(View advanceView) {
        View altitudeView = advanceView.findViewById(R.id.waypoint_all_select_altitude);
        TextView limitView = (TextView) altitudeView.findViewById(R.id.oribit_advance_item_limit);
        limitView.setText("(" + MissionConstant.getWaypointAltitudeLimitMin() + "-" + MissionConstant.getWaypointAltitudeLimitMax() + ")");
        SeekBar altitudeSeekBar = (SeekBar) altitudeView.findViewById(R.id.common_mission_seekbar);
        altitudeSeekBar.setMax(MissionConstant.getWaypointAltitudeLimitMax() - MissionConstant.getWaypointAltitudeLimitMin());
        altitudeSeekBar.setProgress(MissionConstant.getWaypointDefaultAltitude());
        final TextView altitudeTv = (TextView) altitudeView.findViewById(R.id.oribit_advance_item_num);
        altitudeTv.setText(MissionConstant.getWaypointDefaultAltitude() + TransformUtils.getUnitMeterStrEn());
        altitudeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                altitudeTv.setText(progress + MissionConstant.getWaypointAltitudeLimitMin() + TransformUtils.getUnitMeterStrEn());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                curAltitude = seekBar.getProgress() + MissionConstant.getWaypointAltitudeLimitMin();
            }
        });
    }

    private void initSpeedAdvance(View advanceView) {
        View speedView = advanceView.findViewById(R.id.waypoint_all_select_speed);
        TextView titleView = (TextView) speedView.findViewById(R.id.orbit_advance_item_title);
        titleView.setText(R.string.speed);
        TextView limitView = (TextView)speedView.findViewById(R.id.oribit_advance_item_limit);
        limitView.setText("(" + MissionConstant.getOrbitSpeedLimitMin() + "-" + MissionConstant.getOrbitSpeedLimitMax() + ")");
        SeekBar speedSeekBar = (SeekBar) speedView.findViewById(R.id.common_mission_seekbar);
        speedSeekBar.setMax((int) (MissionConstant.getWaypointSpeedLimitMax()*10 - MissionConstant.getWaypointSpeedLimitMin() * 10));
        speedSeekBar.setProgress((int) (MissionConstant.getWaypointSpeedDefault() * 10));
        final TextView speedTv = (TextView) speedView.findViewById(R.id.oribit_advance_item_num);
        speedTv.setText(MissionConstant.getWaypointSpeedDefault() + TransformUtils.getSpeedUnitStrEn());
        speedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double curNum =  TransformUtils.doubleFormat(((progress + (int) (MissionConstant.getOrbitSpeedLimitMin() * 10)) / 10f));
                speedTv.setText(curNum + TransformUtils.getSpeedUnitStrEn());
                switch (TransformUtils.getUnitFlag()){
                    case 0:
                        curSpeed = (int)TransformUtils.kmph2mps(curNum);
                        break;
                    case 1:
                        curSpeed = (int)curNum;
                        break;
                    case 2:
                        curSpeed = (int)TransformUtils.mph2mps(curNum);
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void setWaypointAllSelectViewListener(WaypointAllSelectViewListener waypointAllSelectViewListener){
        this.waypointAllSelectViewListener = waypointAllSelectViewListener;
    }
}
