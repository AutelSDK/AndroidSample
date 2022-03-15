package com.autel.sdksample.evo.mission.widge;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.autel.sdksample.R;
import com.autel.sdksample.evo.mission.util.AutelConfigManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by A15387 on 2017/10/19.
 */

public class MissionDropViewAdapter extends RecyclerView.Adapter<MissionDropViewAdapter.MissionDropViewHolder> {

    private static final int VIEW_TYPE_HAS_ARROW = 1;
    private static final int VIEW_TYPE_NORMAL = 2;

    List<String> datas = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private int curSelect;

    public MissionDropViewAdapter(List<String> datas) {
        this.datas = datas;
    }

    @Override
    public MissionDropViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MissionDropViewHolder missionDropViewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_HAS_ARROW:
                View firstView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mission_drop_item_view, parent, false);
                firstView.findViewById(R.id.mission_item_arrow).setVisibility(View.VISIBLE);
                missionDropViewHolder = new MissionDropViewHolder(firstView);
                break;
            case VIEW_TYPE_NORMAL:
                View normalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mission_drop_item_view, parent, false);
                missionDropViewHolder = new MissionDropViewHolder(normalView);
                break;
        }
        return missionDropViewHolder;
    }

    @Override
    public void onBindViewHolder(MissionDropViewHolder holder, int position) {
        holder.titleView.setText(datas.get(position));
        if (curSelect == position) {
            holder.titleView.setTextColor(AutelConfigManager.instance().getAppContext().getResources().getColor(R.color.blue));
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HAS_ARROW : VIEW_TYPE_NORMAL;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setCurSelect(int curSelect) {
        this.curSelect = curSelect;
    }

    public class MissionDropViewHolder extends RecyclerView.ViewHolder {
        public TextView titleView;

        public MissionDropViewHolder(View itemView) {
            super(itemView);
            this.titleView = (TextView) itemView.findViewById(R.id.mission_drop_item_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = MissionDropViewHolder.this.getAdapterPosition();
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
