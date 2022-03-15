package com.autel.sdksample.evo.mission.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.autel.sdksample.R;
import com.autel.sdksample.evo.mission.bean.ListItemDecoration;
import com.autel.sdksample.evo.mission.bean.MissionFileBean;
import com.autel.sdksample.evo.mission.widge.WaypointLoadAdapter;

import java.util.List;

/**
 * Created by A15387 on 2017/11/20.
 */

public class WaypointLoadView extends FrameLayout {

    private Context context;
    private LayoutInflater inflater;
    private FrameLayout titleContainer;
    private FrameLayout bottomContainer;
    private ViewGroup contentContainer;
    private WaypointLoadViewListener waypointLoadViewListener;
    private WaypointLoadAdapter waypointLoadAdapter;
    private TextView bottomTv;

    public WaypointLoadView(@NonNull Context context) {
        super(context);
        this.context = context;
        initView(context);
    }

    public WaypointLoadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context);
    }

    public WaypointLoadView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context);
    }

    private void initView(Context context) {
        inflater = LayoutInflater.from(context);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.waypoint_file_load_container,null);
        titleContainer = (FrameLayout) view.findViewById(R.id.title_container);
        bottomContainer = (FrameLayout) view.findViewById(R.id.bottom_container);
        contentContainer = (ViewGroup)view.findViewById(R.id.content_container);
        this.addView(view);
        fetchFileData();
        showWaypointFileSystemView();
    }

    private void fetchFileData() {
        waypointLoadAdapter = new WaypointLoadAdapter();
        waypointLoadAdapter.setCheckChangeListener(new WaypointLoadAdapter.CheckChangeListener() {
            @Override
            public void onCheckChange(int num) {
                bottomTv.setText("Delete" + "(" + num + ")");
            }
        });
        waypointLoadAdapter.setOnItemClickListener(new WaypointLoadAdapter.ItemClickListener() {
            @Override
            public void onItemClick(String name) {
                if(waypointLoadViewListener != null){
                    waypointLoadViewListener.onItemEditClick(name);
                }
            }

            @Override
            public void onItemEditClick(String name) {
                if(waypointLoadViewListener != null){
                    waypointLoadViewListener.onItemNameEditClick(name);
                }
            }

            @Override
            public void onItemDeleteClick(String name) {
                if(waypointLoadViewListener != null){
                    waypointLoadViewListener.onItemDeleteClick(name);
                }
            }
        });
    }

    private void showWaypointFileSystemView() {
        clearView();
        View titleView = inflater.inflate(R.layout.common_mission_title_close_with_text,null);
        View contentView = inflater.inflate(R.layout.waypoint_load_view,null);

        titleView.findViewById(R.id.mission_btn_close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(waypointLoadViewListener != null){
                    waypointLoadViewListener.exitClick();
                }
            }
        });
        TextView titleTv = (TextView) titleView.findViewById(R.id.mission_title);
        titleTv.setText(R.string.waypoint_load);
        TextView editTv = (TextView) titleView.findViewById(R.id.common_save);
        editTv.setText("edit");
        editTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(waypointLoadViewListener != null){
                    waypointLoadViewListener.editClick();
                }
                waypointLoadAdapter.setCheckState(true);
                waypointLoadAdapter.notifyDataSetChanged();
                showMutiChoiceView();
                if(waypointLoadAdapter.getItemCount() == 0){
                    showHaveNoDataView();
                }
            }
        });

        waypointLoadAdapter.setCheckState(false);
        RecyclerView loadRv = (RecyclerView) contentView.findViewById(R.id.load_rv);
        loadRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));
        loadRv.addItemDecoration(new ListItemDecoration(context, LinearLayoutManager.VERTICAL));
        loadRv.setAdapter(waypointLoadAdapter);

        titleContainer.addView(titleView);
        contentContainer.addView(contentView);

    }

    private void showHaveNoDataView(){
        contentContainer.removeAllViews();
        View contentView = inflater.inflate(R.layout.waypoint_file_system_no_data_layout,null);
        TextView tipTv = (TextView) contentView.findViewById(R.id.tip_tv);
        tipTv.setText(R.string.waypoint_no_file_tip);
        contentContainer.addView(contentView);
    }

    private void showMutiChoiceView() {
        View mutiTitleView = inflater.inflate(R.layout.waypoint_checkbox_title,null);
        final CheckBox mutiCheckBox = (CheckBox) mutiTitleView.findViewById(R.id.all_check);
        mutiCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                waypointLoadAdapter.checkAll(isChecked);
                if(isChecked){
                    bottomTv.setText("Delete" + "(" + waypointLoadAdapter.getItemCount() + ")");
                }else{
                    bottomTv.setText("Delete");
                }
            }
        });
        View okBtn = mutiTitleView.findViewById(R.id.ok_btn);
        okBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                waypointLoadAdapter.checkAll(false);
                showWaypointFileSystemView();
                if(waypointLoadAdapter.getItemCount() == 0){
                    showHaveNoDataView();
                }
            }
        });

        View mutiBottomView = inflater.inflate(R.layout.common_mission_bottom,null);
        bottomTv = (TextView) mutiBottomView.findViewById(R.id.bottom_title);
        bottomTv.setText("Delete");
        mutiBottomView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(waypointLoadViewListener != null){
                    waypointLoadViewListener.deleteSelectFile(waypointLoadAdapter.getSelectFiles());
                }
                bottomTv.setText("Delete");
                mutiCheckBox.setChecked(false);
            }
        });

        titleContainer.removeAllViews();
        titleContainer.addView(mutiTitleView);
        bottomContainer.removeAllViews();
        bottomContainer.addView(mutiBottomView);
    }

    private void clearView() {
        titleContainer.removeAllViews();
        bottomContainer.removeAllViews();
        contentContainer.removeAllViews();
    }

    public void setWaypointLoadViewListener(WaypointLoadViewListener waypointLoadViewListener){
        this.waypointLoadViewListener = waypointLoadViewListener;
    }

    public void showWaypointFileLists(List<MissionFileBean> missionFileBeans) {
        waypointLoadAdapter.setNames(missionFileBeans);
        if(missionFileBeans.size() == 0){
            showHaveNoDataView();
        }else{
            waypointLoadAdapter.notifyDataSetChanged();
        }
    }

    public interface WaypointLoadViewListener{
        void exitClick();
        void editClick();
        void onItemEditClick(String name);
        void onItemNameEditClick(String name);
        void onItemDeleteClick(String name);
        void deleteSelectFile(List<String> deletNames);
    }

}
