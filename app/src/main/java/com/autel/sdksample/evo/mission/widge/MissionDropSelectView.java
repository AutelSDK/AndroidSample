package com.autel.sdksample.evo.mission.widge;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.autel.sdksample.R;
import com.autel.sdksample.evo.mission.bean.AdvancedItemEnum;
import com.autel.sdksample.evo.mission.bean.ListItemDecoration;
import com.autel.sdksample.evo.mission.util.AutelConfigManager;

import java.util.List;

/**
 * Created by A15387 on 2017/10/19.
 */

public class MissionDropSelectView extends FrameLayout {


    private Context mContext;
    private View unpressView;
    private TextView selectTv;
    private List<String> datas;
    private int curSelet;
    private MissionDropViewAdapter.OnItemClickListener onItemClickListener;
    private View pressView;

    public MissionDropSelectView(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }


    public MissionDropSelectView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public MissionDropSelectView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    private void showUnPressView(Context context, int position, final AdvancedItemEnum itemEnum) {
        if (datas == null) {
            return;
        }
        this.removeAllViews();
        unpressView = LayoutInflater.from(context).inflate(R.layout.mission_drop_unpress_view, this, true);
        setPressViewHeight(AdvancedItemEnum.DEFAULT, unpressView);
        selectTv = (TextView) unpressView.findViewById(R.id.orbit_position_select_one_tv);
        selectTv.setText(datas.get(position));
        curSelet = position;
        unpressView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showPressView(itemEnum);
            }
        });
    }

    private void showPressView(final AdvancedItemEnum itemEnum) {
        if (datas == null) {
            return;
        }
        this.removeAllViews();
        pressView = LayoutInflater.from(mContext).inflate(R.layout.mission_drop_press_view, this, true);
        //根据itemEnum设置不同的view高度
        setPressViewHeight(itemEnum, pressView);
        pressView.setBackgroundResource(R.drawable.mission_pop_bg);
        RecyclerView missionDropRv = (RecyclerView) pressView.findViewById(R.id.mission_drop_rv);
        missionDropRv.addItemDecoration(new ListItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        missionDropRv.setLayoutManager(new LinearLayoutManager(mContext));
        MissionDropViewAdapter adapter = new MissionDropViewAdapter(datas);
        adapter.setOnItemClickListener(new MissionDropViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showUnPressView(mContext, position, itemEnum);
                pressView.setBackgroundResource(0);
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
        adapter.setCurSelect(curSelet);
        missionDropRv.setAdapter(adapter);

    }

    public void setPressViewHeight(AdvancedItemEnum itemEnum, View view) {
        ViewGroup.LayoutParams Params = view.getLayoutParams();
        switch (itemEnum) {
            case ORBIT_FLIGHT_DIRECTION:
            case ORBIT_COMPLETION:
                Params.height = (int) (2 * AutelConfigManager.instance().getAppContext().getResources().getDimension(R.dimen.mission_unpress_rv_height) + 8);
                break;
            case WAYPOINT_HEADING:
            case WAYPOINT_AVOID:
            case WAYPOINT_MISSION:
            case WAYPOINT_CREATE_CHOICE:
                Params.height = (int) (3 * AutelConfigManager.instance().getAppContext().getResources().getDimension(R.dimen.mission_unpress_rv_height) + 8);
                break;
            case ORBIT_HEADING:
            case ORBIT_ENTRY_POINT:
            case WAYPOINT_FINISH_ACTION:
                Params.height = (int) (5 * AutelConfigManager.instance().getAppContext().getResources().getDimension(R.dimen.mission_unpress_rv_height) + 8);
                break;
            case DEFAULT:
                Params.height = (int) (AutelConfigManager.instance().getAppContext().getResources().getDimension(R.dimen.mission_unpress_rv_height) + 8);
                break;
        }
        view.setLayoutParams(Params);
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
    }

    public void notifyDataChange(AdvancedItemEnum itemEnumtem) {
        showUnPressView(mContext, 0, itemEnumtem);
    }


    public void setOnItemClickListener(MissionDropViewAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public int getCurSelet() {
        return curSelet;
    }
}
