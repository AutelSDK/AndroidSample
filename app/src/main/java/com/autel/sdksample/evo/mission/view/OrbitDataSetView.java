package com.autel.sdksample.evo.mission.view;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.autel.sdksample.R;
import com.autel.sdksample.evo.mission.bean.AdvancedItemEnum;
import com.autel.sdksample.evo.mission.bean.MissionConstant;
import com.autel.sdksample.evo.mission.bean.OrbitAdvanceDataBean;
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

public class OrbitDataSetView extends FrameLayout {

    private final Context context;
    private LayoutInflater inflater;
    private FrameLayout titleContainer;
    private FrameLayout bottomContainer;
    private ViewGroup contentContainer;
    private View baseView;
    private View advanceView;
    private TextView directionBtn;
    private OrbitSetViewListener orbitSetViewListener;
    private MissionDropSelectView flightDirectionDropSelectView;
    private MissionDropSelectView baseFlightDirectionDropSelectView;
    private MissionDropSelectView headingDropSelectView;
    private MissionDropSelectView entryPointDropSelectView;
    private MissionDropSelectView completionDropSelectView;
    private TextView orbitBasicSpeedNum;
    private SeekBar orbitBasicSeekBar;
    private SeekBar altitudeSeekBar;
    private TextView altitudeNumTv;
    private TextView radiusNumTv;
    private SeekBar radiusSeekBar;
    private TextView speedNumTv;
    private SeekBar speedSeekBar;
    private TextView rotationNumTv;
    private SeekBar rotationSeekBar;
    //以下值只记录公制的用于发送个飞控
    private int curMpsSpeed;
    private int curAltitude;
    private int curRadius;
    private int curAdvanceMpsSpeed;
    private int curRotation;

    public OrbitDataSetView(@NonNull Context context) {
        super(context);
        this.context = context;
        initView(context);
    }

    public OrbitDataSetView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context);
    }

    public OrbitDataSetView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context);
    }

    private void initView(Context context) {
        inflater = LayoutInflater.from(context);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_mission_container, null);
        titleContainer = (FrameLayout) view.findViewById(R.id.title_container);
        bottomContainer = (FrameLayout) view.findViewById(R.id.bottom_container);
        contentContainer = (ViewGroup) view.findViewById(R.id.content_container);
        this.addView(view);
        showOrbitDataSetView();
    }

    public void showOrbitDataSetView() {
        clearView();
        View startTitle = inflater.inflate(R.layout.common_mission_title_with_close, null);
        ((TextView) startTitle.findViewById(R.id.mission_title)).setText(R.string.mission_orbit);
        startTitle.findViewById(R.id.mission_btn_close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orbitSetViewListener != null) {
                    orbitSetViewListener.exitClick();
                }
            }
        });
        View contentView = inflater.inflate(R.layout.orbit_data_set_content, null);
        if (baseView == null) {
            baseView = inflater.inflate(R.layout.orbit_data_set_base_content, null);
            orbitBasicSpeedNum = (TextView) baseView.findViewById(R.id.orbit_speed_num);
            orbitBasicSpeedNum.setText(MissionConstant.getDefaultSpeed() + TransformUtils.getSpeedUnitStrEn());
            orbitBasicSeekBar = (SeekBar) baseView.findViewById(R.id.common_mission_seekbar);
            orbitBasicSeekBar.setProgress((int)(MissionConstant.getOrbitSpeedLimitMin() * 10 + MissionConstant.getDefaultSpeed() * 10));
            TextView radiusLimitTv = (TextView) baseView.findViewById(R.id.orbit_radius_limit);
            radiusLimitTv.setText("("+MissionConstant.getOrbitRadiusLimitMin() + "-" + MissionConstant.getOrbitRadiusLimitMax() + ")");
            TextView speedLimitTv = (TextView)baseView.findViewById(R.id.orbit_speed_limit);
            speedLimitTv.setText("("+MissionConstant.getOrbitSpeedLimitMin() + "-" + MissionConstant.getOrbitSpeedLimitMax() + ")");
        }
        if (advanceView == null) {
            advanceView = inflater.inflate(R.layout.orbit_data_set_advance_content, null);

            initAdvanceView();
            prepareAdvanceView();
        }
        AutelSegmentedGroup orbitRb = (AutelSegmentedGroup) contentView.findViewById(R.id.orbit_rb);
        final ViewGroup orbitDataSetContainer = (ViewGroup) contentView.findViewById(R.id.orbit_execute_remote_container);
        orbitRb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                onBasicAdvancedChanged(checkedId, orbitDataSetContainer, baseView, advanceView);
            }
        });
        orbitRb.check(R.id.rb_left_btn);
        orbitDataSetContainer.removeAllViews();
        orbitDataSetContainer.addView(baseView);

        prepareBaseView(baseView); // Base Flight Direction

        View bottomView = inflater.inflate(R.layout.common_mission_bottom, null);
        TextView bottomText = (TextView) bottomView.findViewById(R.id.bottom_title);
        bottomText.setText(R.string.mission_start);
        bottomView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orbitSetViewListener != null) {
                    orbitSetViewListener.executeClick(completionDropSelectView.getCurSelet());
                }
            }
        });
        titleContainer.addView(startTitle);
        contentContainer.addView(contentView);
        bottomContainer.addView(bottomView);
    }

    private void initAdvanceView() {
        View altitudeView = advanceView.findViewById(R.id.orbit_advance_altitude_seekbar);
        TextView altitudeLimitTv = (TextView)altitudeView.findViewById(R.id.oribit_advance_item_limit);
        altitudeLimitTv.setText("(" + MissionConstant.getOrbitAltitudeLimitMin() + "-" + MissionConstant.getOrbitAltitudeLimitMax() + ")");
        altitudeNumTv = (TextView)altitudeView.findViewById(R.id.oribit_advance_item_num);
        altitudeNumTv.setText(MissionConstant.getOrbitAltitudeDefault() + TransformUtils.getUnitMeterStrEn());
        altitudeSeekBar = (SeekBar) altitudeView.findViewById(R.id.common_mission_seekbar);
        altitudeSeekBar.setProgress( MissionConstant.getOrbitAltitudeDefault() - MissionConstant.getOrbitAltitudeLimitMin());

        View radiusView = advanceView.findViewById(R.id.orbit_advance_radius_seekbar);
        TextView radiusLimitTv = (TextView)radiusView.findViewById(R.id.oribit_advance_item_limit);
        radiusLimitTv.setText("(" + MissionConstant.getOrbitRadiusLimitMin() + "-" + MissionConstant.getOrbitRadiusLimitMax() + ")");
        TextView radiusTitleTv = (TextView) radiusView.findViewById(R.id.orbit_advance_item_title);
        radiusTitleTv.setText(R.string.radius);
        radiusNumTv = (TextView) radiusView.findViewById(R.id.oribit_advance_item_num);
        radiusNumTv.setText(MissionConstant.getOrbitRadiusDefault() + TransformUtils.getUnitMeterStrEn());
        radiusSeekBar = (SeekBar) radiusView.findViewById(R.id.common_mission_seekbar);
        radiusSeekBar.setProgress(MissionConstant.getOrbitRadiusDefault() - MissionConstant.getOrbitRadiusLimitMin());

        View speedView = advanceView.findViewById(R.id.orbit_advance_speed_seekbar);
        TextView speedLimitTv = (TextView)speedView.findViewById(R.id.oribit_advance_item_limit);
        speedLimitTv.setText("(" + MissionConstant.getOrbitSpeedLimitMin() + "-" + MissionConstant.getOrbitSpeedLimitMax() + ")");
        TextView speedTitleTv = (TextView) speedView.findViewById(R.id.orbit_advance_item_title);
        speedTitleTv.setText(R.string.speed);
        speedNumTv = (TextView) speedView.findViewById(R.id.oribit_advance_item_num);
        speedNumTv.setText(MissionConstant.getDefaultSpeed() + TransformUtils.getSpeedUnitStrEn());
        speedSeekBar = (SeekBar) speedView.findViewById(R.id.common_mission_seekbar);
        speedSeekBar.setProgress((int)(MissionConstant.getOrbitSpeedLimitMin() * 10 + MissionConstant.getDefaultSpeed() * 10));

        View rotationView = advanceView.findViewById(R.id.orbit_advance_rotation_seekbar);
        TextView rotationLimitTv = (TextView)rotationView.findViewById(R.id.oribit_advance_item_limit);
        rotationLimitTv.setText("(" + MissionConstant.getOrbitRotationLimitMin() + "-" + MissionConstant.getOrbitRotationLimitMax() + ")");
        TextView rotationTitleTv = (TextView) rotationView.findViewById(R.id.orbit_advance_item_title);
        rotationTitleTv.setText(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.orbit_advanced_rotation));
        rotationNumTv = (TextView) rotationView.findViewById(R.id.oribit_advance_item_num);
        rotationSeekBar = (SeekBar) rotationView.findViewById(R.id.common_mission_seekbar);
        rotationNumTv.setText(MissionConstant.getOrbitRotationDefault() + "");
        rotationSeekBar.setProgress(MissionConstant.getOrbitRadiusDefault() - MissionConstant.getOrbitRotationLimitMin());

        curAltitude = 60;
        curRadius = 10;
        curAdvanceMpsSpeed = 5;
        curRotation = 360;
    }

    public void prepareBaseView(View basicView) {
        orbitBasicSeekBar.setMax((int) (MissionConstant.getOrbitSpeedLimitMax() * 10) - (int) (MissionConstant.getOrbitSpeedLimitMin() * 10));
        orbitBasicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double curNum =  TransformUtils.doubleFormat(((progress + (int) (MissionConstant.getOrbitSpeedLimitMin() * 10)) / 10f));
                orbitBasicSpeedNum.setText(curNum + TransformUtils.getSpeedUnitStrEn());
                switch (TransformUtils.getUnitFlag()){
                    case 0:
                        curMpsSpeed = (int)TransformUtils.kmph2mps(curNum);
                        break;
                    case 1:
                        curMpsSpeed = (int)curNum;
                        break;
                    case 2:
                        curMpsSpeed = (int)TransformUtils.mph2mps(curNum);
                        break;
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(orbitSetViewListener != null){
                    OrbitAdvanceDataBean orbitAdvanceDataBean = new OrbitAdvanceDataBean();
                    orbitAdvanceDataBean.setSpeed(curMpsSpeed);
                    orbitSetViewListener.basicDataChange(orbitAdvanceDataBean,OrbitAdvanceDataBean.TYPE_BASIC_SPEED_CHANGE);
                }
            }
        });

        baseFlightDirectionDropSelectView = (MissionDropSelectView) basicView.findViewById(R.id.base_flight_direction_drop_select_view);
        baseFlightDirectionDropSelectView.setOnItemClickListener(new MissionDropViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(orbitSetViewListener != null){
                    OrbitAdvanceDataBean orbitAdvanceDataBean = new OrbitAdvanceDataBean();
                    switch (position){
                        case 0:
                            orbitAdvanceDataBean.setFlightDirection(OrbitAdvanceDataBean.FLIGHT_DIRECTION_CW);
                            break;
                        case 1:
                            orbitAdvanceDataBean.setFlightDirection(OrbitAdvanceDataBean.FLIGHT_DIRECTION_CCW);
                            break;
                    }
                    orbitSetViewListener.basicDataChange(orbitAdvanceDataBean,OrbitAdvanceDataBean.TYPE_BASIC_FLIGHT_DIRECTION);
                }
            }
        });
        List<String> datas = new ArrayList<>();
        datas.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.orbit_direction_clockwise));
        datas.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.orbit_direction_anticlockwise));
        baseFlightDirectionDropSelectView.setDatas(datas);
        baseFlightDirectionDropSelectView.notifyDataChange(AdvancedItemEnum.ORBIT_FLIGHT_DIRECTION);
    }

    private void prepareAdvanceView() {
        altitudeSeekBar.setMax(MissionConstant.getOrbitAltitudeLimitMax() - MissionConstant.getOrbitAltitudeLimitMin());
        altitudeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                curAltitude = TransformUtils.isEnUnit()? (int)TransformUtils.feet2meter(progress + MissionConstant.getOrbitRadiusLimitMin(),0)
                        : progress + MissionConstant.getOrbitRadiusLimitMin();
                altitudeNumTv.setText(progress + MissionConstant.getOrbitRadiusLimitMin() + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(orbitSetViewListener != null){
                    OrbitAdvanceDataBean orbitAdvanceDataBean = new OrbitAdvanceDataBean();
                    orbitAdvanceDataBean.setAltitude(curAltitude);
                    orbitSetViewListener.basicDataChange(orbitAdvanceDataBean,OrbitAdvanceDataBean.TYPE_ADVANCE_ALTITUDE);
                }
            }
        });

        radiusSeekBar.setMax(MissionConstant.getOrbitRadiusLimitMax() - MissionConstant.getOrbitRadiusLimitMin());
        radiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                curRadius = TransformUtils.isEnUnit() ? (int)TransformUtils.feet2meter(progress + MissionConstant.getOrbitRadiusLimitMin(),0) :
                        progress + MissionConstant.getOrbitRadiusLimitMin();
                radiusNumTv.setText(progress + MissionConstant.getOrbitRadiusLimitMin() + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(orbitSetViewListener != null){
                    OrbitAdvanceDataBean orbitAdvanceDataBean = new OrbitAdvanceDataBean();
                    orbitAdvanceDataBean.setRadius(curRadius);
                    orbitSetViewListener.basicDataChange(orbitAdvanceDataBean,OrbitAdvanceDataBean.TYPE_ADVANCE_RADIUS);
                }
            }
        });

        speedSeekBar.setMax((int) (MissionConstant.getOrbitSpeedLimitMax() * 10) - (int) (MissionConstant.getOrbitSpeedLimitMin() * 10));
        speedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double curNum =  TransformUtils.doubleFormat(((progress + (int) (MissionConstant.getOrbitSpeedLimitMin() * 10)) / 10f));
                speedNumTv.setText(curNum + TransformUtils.getSpeedUnitStrEn());
                switch (TransformUtils.getUnitFlag()){
                    case 0:
                        curAdvanceMpsSpeed = (int)TransformUtils.kmph2mps(curNum);
                        break;
                    case 1:
                        curAdvanceMpsSpeed = (int)curNum;
                        break;
                    case 2:
                        curAdvanceMpsSpeed = (int)TransformUtils.mph2mps(curNum);
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(orbitSetViewListener != null){
                    OrbitAdvanceDataBean orbitAdvanceDataBean = new OrbitAdvanceDataBean();
                    orbitAdvanceDataBean.setSpeed(curAdvanceMpsSpeed);
                    orbitSetViewListener.basicDataChange(orbitAdvanceDataBean,OrbitAdvanceDataBean.TYPE_ADVANCE_SPEED);
                }
            }
        });

        rotationSeekBar.setMax(MissionConstant.getOrbitRotationLimitMax() - MissionConstant.getOrbitRotationLimitMin());
        rotationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                curRotation = progress + MissionConstant.getOrbitRotationLimitMin();
                rotationNumTv.setText(progress + MissionConstant.getOrbitRotationLimitMin() + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(orbitSetViewListener != null){
                    OrbitAdvanceDataBean orbitAdvanceDataBean = new OrbitAdvanceDataBean();
                    orbitAdvanceDataBean.setRotation(curRotation);
                    orbitSetViewListener.basicDataChange(orbitAdvanceDataBean,OrbitAdvanceDataBean.TYPE_ADVANCE_ROTATION);
                }
            }
        });

        TextView directionTitle = (TextView) advanceView.findViewById(R.id.flight_direction_title);
        directionTitle.setText(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.flight_direction));
        flightDirectionDropSelectView = (MissionDropSelectView) advanceView.findViewById(R.id.flight_direction_drop_select_view);
        flightDirectionDropSelectView.setOnItemClickListener(new MissionDropViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(orbitSetViewListener != null){
                    OrbitAdvanceDataBean orbitAdvanceDataBean = new OrbitAdvanceDataBean();
                    switch (position){
                        case 0:
                            orbitAdvanceDataBean.setFlightDirection(OrbitAdvanceDataBean.FLIGHT_DIRECTION_CW);
                            break;
                        case 1:
                            orbitAdvanceDataBean.setFlightDirection(OrbitAdvanceDataBean.FLIGHT_DIRECTION_CCW);
                            break;
                    }
                    orbitSetViewListener.basicDataChange(orbitAdvanceDataBean,OrbitAdvanceDataBean.TYPE_ADVANCE_FLIGHT_DIRECTION);
                }
            }
        });
        List<String> datas = new ArrayList<>();
        datas.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.orbit_direction_clockwise));
        datas.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.orbit_direction_anticlockwise));
        flightDirectionDropSelectView.setDatas(datas);
        flightDirectionDropSelectView.notifyDataChange(AdvancedItemEnum.ORBIT_FLIGHT_DIRECTION);

        TextView headingTv = (TextView) advanceView.findViewById(R.id.heading_title);
        headingTv.setText(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.orbit_advanced_heading));
        headingDropSelectView = (MissionDropSelectView) advanceView.findViewById(R.id.heading_drop_select_view);
        headingDropSelectView.setOnItemClickListener(new MissionDropViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(orbitSetViewListener != null){
                    OrbitAdvanceDataBean orbitAdvanceDataBean = new OrbitAdvanceDataBean();
                    switch (position){
                        case 0:
                            orbitAdvanceDataBean.setHeading(OrbitAdvanceDataBean.HEADING_FACE_FORWARD);
                            break;
                        case 1:
                            orbitAdvanceDataBean.setHeading(OrbitAdvanceDataBean.HEADING_FACE_TOWARDS_CENTER);
                            break;
                        case 2:
                            orbitAdvanceDataBean.setHeading(OrbitAdvanceDataBean.HEADING_AWAYS_FROM_CENTER);
                            break;
                        case 3:
                            orbitAdvanceDataBean.setHeading(OrbitAdvanceDataBean.HEADING_FACE_BACKWARD);
                            break;
                        case 4:
                            orbitAdvanceDataBean.setHeading(OrbitAdvanceDataBean.HEADING_USER_CONTROLLED);
                            break;
                    }
                    orbitSetViewListener.basicDataChange(orbitAdvanceDataBean,OrbitAdvanceDataBean.TYPE_ADVANCE_HEADING);
                }
            }
        });
        List<String> datas1 = new ArrayList<>();
        datas1.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.orbit_advanced_heading_face_forward));
        datas1.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.orbit_advanced_heading_face_towards_center));
        datas1.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.orbit_advanced_heading_aways_from_center));
        datas1.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.orbit_advanced_heading_face_backward));
        datas1.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.orbit_advanced_heading_user_controlled));
        headingDropSelectView.setDatas(datas1);
        headingDropSelectView.notifyDataChange(AdvancedItemEnum.ORBIT_HEADING);


        TextView entryPointTitle = (TextView) advanceView.findViewById(R.id.entry_point_title);
        entryPointTitle.setText(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.orbit_advanced_entry_point));
        entryPointDropSelectView = (MissionDropSelectView) advanceView.findViewById(R.id.entry_point_drop_select_view);
        entryPointDropSelectView.setOnItemClickListener(new MissionDropViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(orbitSetViewListener != null){
                    OrbitAdvanceDataBean orbitAdvanceDataBean = new OrbitAdvanceDataBean();
                    switch (position){
                        case 0:
                            orbitAdvanceDataBean.setEntryPoint(OrbitAdvanceDataBean.ENTRY_POINT_NEAREST);
                            break;
                        case 1:
                            orbitAdvanceDataBean.setEntryPoint(OrbitAdvanceDataBean.ENTRY_POINT_NORTH);
                            break;
                        case 2:
                            orbitAdvanceDataBean.setEntryPoint(OrbitAdvanceDataBean.ENTRY_POINT_SOUTH);
                            break;
                        case 3:
                            orbitAdvanceDataBean.setEntryPoint(OrbitAdvanceDataBean.ENTRY_POINT_EAST);
                            break;
                        case 4:
                            orbitAdvanceDataBean.setEntryPoint(OrbitAdvanceDataBean.ENTRY_POINT_WEST);
                            break;
                    }
                    orbitSetViewListener.basicDataChange(orbitAdvanceDataBean,OrbitAdvanceDataBean.TYPE_ADVANCE_ENTRY_POINT);
                }
            }
        });
        List<String> datas2 = new ArrayList<>();
        datas2.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.orbit_advanced_entry_point_nearest));
        datas2.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.orbit_advanced_entry_north));
        datas2.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.orbit_advanced_entry_south));
        datas2.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.orbit_advanced_entry_east));
        datas2.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.orbit_advanced_entry_west));
        entryPointDropSelectView.setDatas(datas2);
        entryPointDropSelectView.notifyDataChange(AdvancedItemEnum.ORBIT_ENTRY_POINT);

        TextView completionTitle = (TextView) advanceView.findViewById(R.id.completion_title);
        completionTitle.setText(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.orbit_advanced_completion));
        completionDropSelectView = (MissionDropSelectView) advanceView.findViewById(R.id.completion_drop_select_view);
        completionDropSelectView.setOnItemClickListener(new MissionDropViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(orbitSetViewListener != null){
                    OrbitAdvanceDataBean orbitAdvanceDataBean = new OrbitAdvanceDataBean();
                    switch (position){
                        case 0:
                            orbitAdvanceDataBean.setCompletion(OrbitAdvanceDataBean.COMPLETION_HOVER);
                            break;
                        case 1:
                            orbitAdvanceDataBean.setCompletion(OrbitAdvanceDataBean.COMPLETION_RTH);
                            break;
                    }
                    orbitSetViewListener.basicDataChange(orbitAdvanceDataBean,OrbitAdvanceDataBean.TYPE_ADVANCE_COMPLETION);
                }
            }
        });
        List<String> datas3 = new ArrayList<>();
        datas3.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.orbit_advanced_completion_hover));
        datas3.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.orbit_advanced_completion_rth));
        completionDropSelectView.setDatas(datas3);
        completionDropSelectView.notifyDataChange(AdvancedItemEnum.ORBIT_COMPLETION);
    }

    private void clearView() {
        titleContainer.removeAllViews();
        contentContainer.removeAllViews();
        bottomContainer.removeAllViews();
    }

    private void onBasicAdvancedChanged(@IdRes int checkedId, ViewGroup orbitDataSetContainer, View baseView, View advanceView) {
        switch (checkedId) {
            case R.id.rb_left_btn:
                orbitDataSetContainer.removeAllViews();
                orbitDataSetContainer.addView(baseView);
                if (orbitSetViewListener != null) {
                    orbitSetViewListener.basicClick();
                }
                break;
            case R.id.rb_right_btn:
                orbitDataSetContainer.removeAllViews();
                orbitDataSetContainer.addView(advanceView);
                if (orbitSetViewListener != null) {
                    orbitSetViewListener.advanceClick();
                }
                break;
        }
    }

    public OrbitAdvanceDataBean getBasicData() {
        OrbitAdvanceDataBean orbitAdvanceDataBean = new OrbitAdvanceDataBean();
        switch (baseFlightDirectionDropSelectView.getCurSelet()){
            case 0:
                orbitAdvanceDataBean.setFlightDirection(OrbitAdvanceDataBean.FLIGHT_DIRECTION_CW);
                break;
            case 1:
                orbitAdvanceDataBean.setFlightDirection(OrbitAdvanceDataBean.FLIGHT_DIRECTION_CCW);
                break;
        }
        orbitAdvanceDataBean.setSpeed(curMpsSpeed);
        return orbitAdvanceDataBean;
    }

    public void showOrbitRadius(String orbitRadiusStr, int missionTextColor) {
        if(baseView != null){
            TextView textView = (TextView)baseView.findViewById(R.id.orbit_radius_num);
            textView.setText(orbitRadiusStr);
            switch (missionTextColor){
                case MissionConstant.MISSION_TEXT_COLOR_BLUE:
                    textView.setTextColor(AutelConfigManager.instance().getAppContext().getResources().getColor(R.color.blue));
                    break;
                case MissionConstant.MISSION_TEXT_COLOR_RED:
                    textView.setTextColor(AutelConfigManager.instance().getAppContext().getResources().getColor(R.color.red));
                    break;
            }

        }
    }

    public OrbitAdvanceDataBean getAdvanceData() {
        OrbitAdvanceDataBean orbitAdvanceDataBean = new OrbitAdvanceDataBean();
        orbitAdvanceDataBean.setAltitude(curAltitude);
        orbitAdvanceDataBean.setRadius(curRadius);
        orbitAdvanceDataBean.setSpeed(curAdvanceMpsSpeed);
        orbitAdvanceDataBean.setRotation(curRotation);
        switch (flightDirectionDropSelectView.getCurSelet()){
            case 0:
                orbitAdvanceDataBean.setFlightDirection(OrbitAdvanceDataBean.FLIGHT_DIRECTION_CW);
                break;
            case 1:
                orbitAdvanceDataBean.setFlightDirection(OrbitAdvanceDataBean.FLIGHT_DIRECTION_CCW);
                break;
        }
        switch (headingDropSelectView.getCurSelet()){
            case 0:
                orbitAdvanceDataBean.setHeading(OrbitAdvanceDataBean.HEADING_FACE_FORWARD);
                break;
            case 1:
                orbitAdvanceDataBean.setHeading(OrbitAdvanceDataBean.HEADING_FACE_TOWARDS_CENTER);
                break;
            case 2:
                orbitAdvanceDataBean.setHeading(OrbitAdvanceDataBean.HEADING_AWAYS_FROM_CENTER);
                break;
            case 3:
                orbitAdvanceDataBean.setHeading(OrbitAdvanceDataBean.HEADING_FACE_BACKWARD);
                break;
            case 4:
                orbitAdvanceDataBean.setHeading(OrbitAdvanceDataBean.HEADING_USER_CONTROLLED);
                break;
        }
        switch (entryPointDropSelectView.getCurSelet()){
            case 0:
                orbitAdvanceDataBean.setEntryPoint(OrbitAdvanceDataBean.ENTRY_POINT_NEAREST);
                break;
            case 1:
                orbitAdvanceDataBean.setEntryPoint(OrbitAdvanceDataBean.ENTRY_POINT_NORTH);
                break;
            case 2:
                orbitAdvanceDataBean.setEntryPoint(OrbitAdvanceDataBean.ENTRY_POINT_SOUTH);
                break;
            case 3:
                orbitAdvanceDataBean.setEntryPoint(OrbitAdvanceDataBean.ENTRY_POINT_EAST);
                break;
            case 4:
                orbitAdvanceDataBean.setEntryPoint(OrbitAdvanceDataBean.ENTRY_POINT_WEST);
                break;

        }
        switch (completionDropSelectView.getCurSelet()){
            case 0:
                orbitAdvanceDataBean.setCompletion(OrbitAdvanceDataBean.COMPLETION_HOVER);
                break;
            case 1:
                orbitAdvanceDataBean.setCompletion(OrbitAdvanceDataBean.COMPLETION_RTH);
                break;
        }
        return orbitAdvanceDataBean;
    }

    public interface OrbitSetViewListener {
        void exitClick();

        void executeClick(int finishType);

        void basicClick();

        void advanceClick();

        void basicDataChange(OrbitAdvanceDataBean orbitAdvanceDataBean, int type);
    }

    public void setOrbitSetViewListener(OrbitSetViewListener orbitSetViewListener) {
        this.orbitSetViewListener = orbitSetViewListener;
    }

}
