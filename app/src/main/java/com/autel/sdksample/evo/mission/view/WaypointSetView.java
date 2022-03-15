package com.autel.sdksample.evo.mission.view;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.autel.sdksample.R;
import com.autel.sdksample.evo.mission.bean.AdvancedItemEnum;
import com.autel.sdksample.evo.mission.bean.MissionConstant;
import com.autel.sdksample.evo.mission.bean.WaypointAdvanceDataBean;
import com.autel.sdksample.evo.mission.util.AutelConfigManager;
import com.autel.sdksample.evo.mission.util.TransformUtils;
import com.autel.sdksample.evo.mission.widge.AutelSegmentedGroup;
import com.autel.sdksample.evo.mission.widge.MissionDropSelectView;
import com.autel.sdksample.evo.mission.widge.MissionDropViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by A15387 on 2017/11/15.
 */

public class WaypointSetView extends FrameLayout {

    private LayoutInflater inflater;
    private FrameLayout titleContainer;
    private FrameLayout bottomContainer;
    private ViewGroup contentContainer;
    private FrameLayout waypointSetContainer;
    private MissionDropSelectView headingDropView;
    private MissionDropSelectView finishActionDropView;
    private MissionDropSelectView avoidDropView;
    private MissionDropSelectView creatChoiceDropView;
    private WaypointSetViewListener waypointSetViewListener;
    private TextView waypointNumTv;
    private TextView totalDistanceTv;
    private int curAltitude = 60;
    private int curSpeed = 5;
    private int curFinishType = 0;
    private int curHeading = 0;
    private TextView curNumTv;
    private SeekBar altitudeSeekBar;
    private TextView altitudeTv;
    private SeekBar speedSeekBar;
    private TextView speedTv;
    private SeekBar headingSeekBar;
    private TextView headingTv;
    private int curSelectWaypointIndex;
    private int curTotalWaypointNum;

    public WaypointSetView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public WaypointSetView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public WaypointSetView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        inflater = LayoutInflater.from(context);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_mission_container, null);
        titleContainer = (FrameLayout) view.findViewById(R.id.title_container);
        bottomContainer = (FrameLayout) view.findViewById(R.id.bottom_container);
        contentContainer = (ViewGroup) view.findViewById(R.id.content_container);
        this.addView(view);
        showWaypointSetView();
    }

    private void showWaypointSetView() {
        clearView();
        View titleView = inflater.inflate(R.layout.common_mission_title_close_with_text, null);
        titleView.findViewById(R.id.mission_btn_close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (waypointSetViewListener != null) {
                    waypointSetViewListener.exitClick();
                }
            }
        });
        ((TextView) titleView.findViewById(R.id.mission_title)).setText(R.string.waypoint);
        TextView saveBtn = (TextView) titleView.findViewById(R.id.common_save);
        saveBtn.setText("save");
        saveBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (waypointSetViewListener != null) {
                    waypointSetViewListener.saveClick();
                }
            }
        });
        final View contentView = inflater.inflate(R.layout.waypoint_set_content, null);
        waypointSetContainer = (FrameLayout) contentView.findViewById(R.id.waypoint_set_content_container);
        AutelSegmentedGroup waypointRb = (AutelSegmentedGroup) contentView.findViewById(R.id.waypoint_rb);
        waypointRb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_left_btn) {
                    showSetBasicView(contentView);
                    if (waypointSetViewListener != null) {
                        waypointSetViewListener.basicClick();
                    }
                } else if (checkedId == R.id.rb_right_btn) {
                    showSetAdvancedView(contentView);
                    if (waypointSetViewListener != null) {
                        waypointSetViewListener.advanceClick();
                    }
                }
            }
        });
        waypointRb.check(R.id.rb_left_btn);

        View bottomView = inflater.inflate(R.layout.waypoint_set_bottom, null);
        bottomView.findViewById(R.id.btn_add).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (waypointSetViewListener != null) {
                    waypointSetViewListener.waypointAddConfirm();
                }
            }
        });
        bottomView.findViewById(R.id.btn_delete).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (waypointSetViewListener != null) {
                    waypointSetViewListener.waypointDeleteConfirm();
                }
            }
        });
        bottomView.findViewById(R.id.btn_start).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (waypointSetViewListener != null) {
                    waypointSetViewListener.waypointMissionStart();
                }
            }
        });

        showSetBasicView(contentView);
        titleContainer.addView(titleView);
        contentContainer.addView(contentView);
        bottomContainer.addView(bottomView);
    }

    private void clearView() {
        titleContainer.removeAllViews();
        contentContainer.removeAllViews();
    }

    private void showSetAdvancedView(View contentView) {
        View advanceView = inflater.inflate(R.layout.waypoint_set_view_advance_content, null);
        curNumTv = (TextView)advanceView.findViewById(R.id.cur_waypoint_num_tv);
        advanceView.findViewById(R.id.img_prev).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(waypointSetViewListener != null){
                    waypointSetViewListener.showPrevWaypointData();
                }

            }
        });
        advanceView.findViewById(R.id.img_prev).setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    v.setAlpha(0.5f);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    v.setAlpha(1.0f);
                }else if(event.getAction() == MotionEvent.ACTION_CANCEL){
                    v.setAlpha(1.0f);
                }
                return false;
            }
        });
        advanceView.findViewById(R.id.img_last).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(waypointSetViewListener != null){
                    waypointSetViewListener.showLastWaypointData();
                }
            }
        });
        advanceView.findViewById(R.id.img_last).setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    v.setAlpha(0.5f);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    v.setAlpha(1.0f);
                }else if(event.getAction() == MotionEvent.ACTION_CANCEL){
                    v.setAlpha(1.0f);
                }
                return false;
            }
        });
        initAltitudeAdvance(advanceView);
        initSpeedAdvance(advanceView);
        initHeadingAdvance(advanceView);
        waypointSetContainer.removeAllViews();
        waypointSetContainer.addView(advanceView);
    }

    private void initHeadingAdvance(View advanceView) {
        View headingView = advanceView.findViewById(R.id.waypoint_advance_heading_seekbar);
        TextView titleView = (TextView) headingView.findViewById(R.id.orbit_advance_item_title);
        titleView.setText(R.string.orbit_advanced_heading);
        TextView limitView = (TextView)headingView.findViewById(R.id.oribit_advance_item_limit);
        limitView.setText("(" + MissionConstant.getWaypointHeadingMin() + "-" + MissionConstant.getWaypointHeadingMax() + ")");
        headingSeekBar = (SeekBar) headingView.findViewById(R.id.common_mission_seekbar);
        headingSeekBar.setMax((MissionConstant.getWaypointHeadingMax() - MissionConstant.getWaypointHeadingMin()));
        headingTv = (TextView) headingView.findViewById(R.id.oribit_advance_item_num);
        headingTv.setText(MissionConstant.getWaypointHeadingDefault() + "");
        headingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                curHeading = progress;
                headingTv.setText(curHeading + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (waypointSetViewListener != null) {
                    waypointSetViewListener.waypointCurDataChange(getAdvacneData(), WaypointAdvanceDataBean.TYPE_HEAD);
                }
            }
        });
    }

    private void initSpeedAdvance(View advanceView) {
        View speedView = advanceView.findViewById(R.id.waypoint_advance_speed_seekbar);
        TextView titleView = (TextView) speedView.findViewById(R.id.orbit_advance_item_title);
        titleView.setText(R.string.speed);
        TextView limitView = (TextView)speedView.findViewById(R.id.oribit_advance_item_limit);
        limitView.setText("(" + MissionConstant.getOrbitSpeedLimitMin() + "-" + MissionConstant.getOrbitSpeedLimitMax() + ")");
        speedSeekBar = (SeekBar) speedView.findViewById(R.id.common_mission_seekbar);
        speedSeekBar.setMax((int) (MissionConstant.getWaypointSpeedLimitMax()*10 - MissionConstant.getWaypointSpeedLimitMin() * 10));
        speedSeekBar.setProgress((int) (MissionConstant.getWaypointSpeedDefault() * 10));
        speedTv = (TextView) speedView.findViewById(R.id.oribit_advance_item_num);
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
                if (waypointSetViewListener != null) {
                    waypointSetViewListener.waypointCurDataChange(getAdvacneData(), WaypointAdvanceDataBean.TYPE_SPEED);
                }
            }
        });
    }

    private void initAltitudeAdvance(View advanceView) {
        View altitudeView = advanceView.findViewById(R.id.waypoint_advance_altitude_seekbar);
        TextView limitView = (TextView) altitudeView.findViewById(R.id.oribit_advance_item_limit);
        limitView.setText("(" + MissionConstant.getWaypointAltitudeLimitMin() + "-" + MissionConstant.getWaypointAltitudeLimitMax() + ")");
        altitudeSeekBar = (SeekBar) altitudeView.findViewById(R.id.common_mission_seekbar);
        altitudeSeekBar.setMax(MissionConstant.getWaypointAltitudeLimitMax() - MissionConstant.getWaypointAltitudeLimitMin());
        altitudeSeekBar.setProgress(MissionConstant.getWaypointDefaultAltitude());
        altitudeTv = (TextView) altitudeView.findViewById(R.id.oribit_advance_item_num);
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
                if (waypointSetViewListener != null) {
                    waypointSetViewListener.waypointCurDataChange(getAdvacneData(), WaypointAdvanceDataBean.TYPE_ALTITUDE);
                }
            }
        });
    }

    private void showSetBasicView(View contentView) {
        View basicView = inflater.inflate(R.layout.waypoint_set_view_basic_content, null);
        waypointNumTv = (TextView) basicView.findViewById(R.id.num_waypoint_tv);
        totalDistanceTv = (TextView) basicView.findViewById(R.id.total_distance_num_tv);
        waypointSetContainer.removeAllViews();
        waypointSetContainer.addView(basicView);
        initCreatChoiceDropView(basicView);
        initAvoidDropView(basicView);
        initFinishActionView(basicView);
        initHeadingDropView(basicView);

    }

    private void initHeadingDropView(View basicView) {
        headingDropView = (MissionDropSelectView) basicView.findViewById(R.id.waypoint_heading_drop_tv);
        headingDropView.setOnItemClickListener(new MissionDropViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        curHeading = 1;
                        break;
                    case 1:
                        curHeading = 2;
                        break;
                    case 2:
                        curHeading = 3;
                        break;
                }
                if (waypointSetViewListener != null) {
                    waypointSetViewListener.waypointCurDataChange(getAdvacneData(), 4);
                }
            }
        });
        List<String> datas = new ArrayList<>();
        datas.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.waypoint_heading_towards_next_waypoint));
        datas.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.waypoint_heading_initial_heading));
        datas.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.waypoint_heading_defined_by_waypoint));
        headingDropView.setDatas(datas);
        headingDropView.notifyDataChange(AdvancedItemEnum.WAYPOINT_HEADING);
    }

    private void initFinishActionView(View basicView) {
        finishActionDropView = (MissionDropSelectView) basicView.findViewById(R.id.waypoint_finish_action_drop_tv);
        finishActionDropView.setOnItemClickListener(new MissionDropViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (waypointSetViewListener != null) {
                    WaypointAdvanceDataBean waypointAdvanceDataBean = new WaypointAdvanceDataBean();
                    switch (position) {
                        case 0:
                            waypointAdvanceDataBean.setFinishType(WaypointAdvanceDataBean.FINISH_ACTION_STOP_ON_LAST_POINT);
                            break;
                        case 1:
                            waypointAdvanceDataBean.setFinishType(WaypointAdvanceDataBean.FINISH_ACTION_RETURN_HOME);
                            break;
                        case 2:
                            waypointAdvanceDataBean.setFinishType(WaypointAdvanceDataBean.FINISH_ACTION_LAND_ON_LAST_POINT);
                            break;
                        case 3:
                            waypointAdvanceDataBean.setFinishType(WaypointAdvanceDataBean.FINISH_ACTION_RETURN_TO_FIRST_POINT);
                            break;
                        case 4:
                            waypointAdvanceDataBean.setFinishType(WaypointAdvanceDataBean.FINISH_ACTION_KEEP_ON_LAST_POINT);
                            break;
                    }
                    waypointSetViewListener.waypointCurDataChange(waypointAdvanceDataBean, WaypointAdvanceDataBean.TYPE_FINISH_ACTION);
                }

            }
        });
        List<String> datas = new ArrayList<>();
        datas.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.waypoint_finish_action_hover));
        datas.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.waypoint_finish_action_return_to_home));
        datas.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.waypoint_finish_action_landing));
        datas.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.waypoint_finish_action_return_to_first_point));
        datas.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.waypoint_finish_action_hover_in_mission));
        finishActionDropView.setDatas(datas);
        finishActionDropView.notifyDataChange(AdvancedItemEnum.WAYPOINT_FINISH_ACTION);
    }

    private void initAvoidDropView(View basicView) {
        avoidDropView = (MissionDropSelectView) basicView.findViewById(R.id.waypoint_avoid_drop_tv);
        avoidDropView.setOnItemClickListener(new MissionDropViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(waypointSetViewListener != null){
                    WaypointAdvanceDataBean waypointAdvanceDataBean = new WaypointAdvanceDataBean();
                    switch (position){
                        case 0:
                            waypointAdvanceDataBean.setAvoidance(WaypointAdvanceDataBean.OBSTACLE_AVOIDANCE_MODE_INVALID);
                            break;
                        case 1:
                            waypointAdvanceDataBean.setAvoidance(WaypointAdvanceDataBean.OBSTACLE_AVOIDANCE_MODE_CLIMB_FIRST);
                            break;
                        case 2:
                            waypointAdvanceDataBean.setAvoidance(WaypointAdvanceDataBean.OBSTACLE_AVOIDANCE_MODE_HORIZOTAL);
                            break;
                    }
                    waypointSetViewListener.waypointCurDataChange(waypointAdvanceDataBean,WaypointAdvanceDataBean.TYPE_AVOIDANCE);
                }
            }
        });
        List<String> datas = new ArrayList<>();
        datas.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.waypoint_avoid_way_off));
        datas.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.waypoint_avoid_way_elevation));
        datas.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.waypoint_avoid_way_heading));
        avoidDropView.setDatas(datas);
        avoidDropView.notifyDataChange(AdvancedItemEnum.WAYPOINT_AVOID);
    }

    private void initCreatChoiceDropView(View basicView) {
        creatChoiceDropView = (MissionDropSelectView) basicView.findViewById(R.id.waypoint_choice_drop_tv);
        creatChoiceDropView.setOnItemClickListener(new MissionDropViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (waypointSetViewListener != null) {
                    waypointSetViewListener.waypointCreateWayConfirm(position);
                }
            }
        });
        List<String> datas = new ArrayList<>();
        datas.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.waypoint_create_aircraft));
        datas.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.waypoint_create_point_on_map));
        datas.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.waypoint_create_draw_on_map));
        creatChoiceDropView.setDatas(datas);
        creatChoiceDropView.notifyDataChange(AdvancedItemEnum.WAYPOINT_CREATE_CHOICE);
    }

    public void setWaypointNum(int waypointNum) {
        if (waypointNumTv != null) {
            waypointNumTv.setText(waypointNum + "");
        }
    }

    public void setWaypointTotalDistance(int waypointTotalDistance) {
        if (totalDistanceTv != null) {
            totalDistanceTv.setText(TransformUtils.isEnUnit() ? TransformUtils.meter2feet(waypointTotalDistance, 1) + TransformUtils.getUnitMeterStrEn() :
                    waypointTotalDistance + TransformUtils.getUnitMeterStrEn());
        }
    }

    public WaypointAdvanceDataBean getAdvacneData() {
        WaypointAdvanceDataBean waypointAdvanceDataBean = new WaypointAdvanceDataBean();
        waypointAdvanceDataBean.setAltitude(curAltitude);
        waypointAdvanceDataBean.setSpeed(curSpeed);
        waypointAdvanceDataBean.setFinishType(finishActionDropView.getCurSelet());
        waypointAdvanceDataBean.setHeading(headingDropView.getCurSelet());
        waypointAdvanceDataBean.setAvoidance(avoidDropView.getCurSelet());
        waypointAdvanceDataBean.setHeadingDegree(curHeading);
        return waypointAdvanceDataBean;
    }

    public interface WaypointSetViewListener {
        void exitClick();

        void basicClick();

        void advanceClick();

        void saveClick();

        void waypointCreateWayConfirm(int position);

        void waypointAddConfirm();

        void waypointDeleteConfirm();

        void waypointMissionStart();

        void waypointCurDataChange(WaypointAdvanceDataBean waypointAdvanceDataBean, int whichItem);

        void showLastWaypointData();

        void showPrevWaypointData();
    }

    public void setCurWaypointNum(int curIndex , int totalNum){
        curSelectWaypointIndex = curIndex;
        curTotalWaypointNum = totalNum;
        if(curNumTv != null){
            curNumTv.setText(Html.fromHtml("#"+"<font color = \"#0378ff\">"+curIndex+"</font>" + "/" + totalNum));
        }
    }

    public void showCurWaypointData(WaypointAdvanceDataBean waypointAdvanceDataBean){
        int altitude = TransformUtils.isEnUnit() ? (int)TransformUtils.meter2feet(waypointAdvanceDataBean.getAltitude(),0) :
                waypointAdvanceDataBean.getAltitude();
        if(altitudeSeekBar != null){
            altitudeSeekBar.setProgress(altitude);
        }
        if(altitudeTv != null){
            altitudeTv.setText(altitude + TransformUtils.getUnitMeterStrEn());
        }

        int speed = 0;
        switch (TransformUtils.getUnitFlag()){
            case 0:
                speed = (int)TransformUtils.mps2kmph(waypointAdvanceDataBean.getSpeed());
                break;
            case 1:
                speed = waypointAdvanceDataBean.getSpeed();
                break;
            case 2:
                speed = (int)TransformUtils.mps2mph(waypointAdvanceDataBean.getSpeed());
                break;
        }
        if(speedSeekBar != null){
            speedSeekBar.setProgress(speed * 10);
        }
        if(speedTv != null){
            speedTv.setText(speed + TransformUtils.getSpeedUnitStrEn());
        }

        if(headingSeekBar != null){
            headingSeekBar.setProgress(waypointAdvanceDataBean.getHeadingDegree());
        }
        if(headingTv != null){
            headingTv.setText(waypointAdvanceDataBean.getHeadingDegree() + "");
        }

    }

    public void setWaypointSetViewLisetener(WaypointSetViewListener waypointSetViewListener) {
        this.waypointSetViewListener = waypointSetViewListener;
    }

}
