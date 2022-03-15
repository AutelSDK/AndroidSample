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
import com.autel.sdksample.evo.mission.bean.AdvancedItemEnum;
import com.autel.sdksample.evo.mission.util.AutelConfigManager;
import com.autel.sdksample.evo.mission.widge.MissionDropSelectView;
import com.autel.sdksample.evo.mission.widge.MissionDropViewAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by A15387 on 2017/11/15.
 */

public class OrbitPositionSetView extends FrameLayout {

    private LayoutInflater inflater;
    private FrameLayout titleContainer;
    private FrameLayout bottomContainer;
    private ViewGroup contentContainer;
    private MissionDropSelectView missionDropSelectView;
    private OrbitPositionSetViewListener orbitPositionSetViewListener;
    private TextView orbitSetPositionTip;

    public OrbitPositionSetView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public OrbitPositionSetView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public OrbitPositionSetView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        showOrbitPositionSetView();
    }

    public void showOrbitPositionSetView() {
        clearView();
        View startTitle = inflater.inflate(R.layout.common_mission_title_with_close, null);
        ((TextView) startTitle.findViewById(R.id.mission_title)).setText(R.string.mission_orbit);
        startTitle.findViewById(R.id.mission_btn_close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orbitPositionSetViewListener != null) {
                    orbitPositionSetViewListener.exitClick();
                }
            }
        });
        View contentView = inflater.inflate(R.layout.orbit_position_set_content, null);
        missionDropSelectView = (MissionDropSelectView) contentView.findViewById(R.id.mission_drop_select_view);
        orbitSetPositionTip = (TextView) contentView.findViewById(R.id.orbit_set_position_tip);
        missionDropSelectView.setOnItemClickListener(new MissionDropViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (orbitPositionSetViewListener != null) {
                    orbitPositionSetViewListener.positionSetWayItemClick(position);
                }
                showObitSetTip(position);
            }
        });
        List<String> datas = new ArrayList<>();
        datas.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.orbit_create_from_drone_location));
        datas.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.orbit_create_from_phone_location));
        datas.add(AutelConfigManager.instance().getAppContext().getResources().getString(R.string.orbit_create_from_map));
        missionDropSelectView.setDatas(datas);
        missionDropSelectView.notifyDataChange(AdvancedItemEnum.WAYPOINT_MISSION);
        View bottomView = inflater.inflate(R.layout.common_mission_bottom, null);
        ((TextView) bottomView.findViewById(R.id.bottom_title)).setText(R.string.mission_set);
        bottomView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orbitPositionSetViewListener != null) {
                    orbitPositionSetViewListener.positionSetConfirmClick();
                }
            }
        });
        titleContainer.addView(startTitle);
        contentContainer.addView(contentView);
        bottomContainer.addView(bottomView);
    }

    public void showObitSetTip(int position) {
        switch (position) {
            case 0:
                orbitSetPositionTip.setText(R.string.orbit_position0_set_tip);
                break;
            case 1:
                orbitSetPositionTip.setText(R.string.orbit_position1_set_tip);
                break;
            case 2:
                orbitSetPositionTip.setText(R.string.orbit_position2_set_tip);
                break;
            default:
                orbitSetPositionTip.setText(R.string.orbit_position0_set_tip);
                break;

        }
    }

    private void clearView() {
        titleContainer.removeAllViews();
        contentContainer.removeAllViews();
        bottomContainer.removeAllViews();
    }

    public interface OrbitPositionSetViewListener {
        void exitClick();

        void positionSetWayItemClick(int position);

        void positionSetConfirmClick();
    }

    public void setOrbitPositionSetViewListener(OrbitPositionSetViewListener orbitPositionSetViewListener) {
        this.orbitPositionSetViewListener = orbitPositionSetViewListener;
    }

}
