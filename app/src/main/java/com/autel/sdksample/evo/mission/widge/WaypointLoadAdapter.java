package com.autel.sdksample.evo.mission.widge;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.autel.sdksample.R;
import com.autel.sdksample.evo.mission.bean.MissionFileBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by A15387 on 2017/11/21.
 */

public class WaypointLoadAdapter extends RecyclerView.Adapter<WaypointLoadAdapter.ViewHolder> {

    List<MissionFileBean> names = new ArrayList<>();
    List<String> selectNames = new ArrayList<>();
    private boolean checkState;
    private CheckChangeListener checkChangeListener;
    private ItemClickListener itemClickListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.waypoint_load_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ((TextView)holder.view.findViewById(R.id.item_name_tv)).setText(names.get(position).getName());
        final SwipeMenuLayout swipeMenuLayout = (SwipeMenuLayout) holder.view.findViewById(R.id.swipe_layout);
        holder.view.findViewById(R.id.content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener != null){
                    itemClickListener.onItemClick(names.get(holder.getAdapterPosition()).getName());
                }

            }
        });
        holder.view.findViewById(R.id.btnUnRead).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeMenuLayout.smoothClose();
                if(itemClickListener != null){
                    itemClickListener.onItemEditClick(names.get(holder.getAdapterPosition()).getName());
                }
            }
        });
        holder.view.findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeMenuLayout.smoothClose();
                if(itemClickListener != null){
                    itemClickListener.onItemDeleteClick(names.get(holder.getAdapterPosition()).getName());
                }
            }
        });
        CheckBox checkBox = (CheckBox) holder.view.findViewById(R.id.item_check_box);
        if(checkState){
            checkBox.setVisibility(View.VISIBLE);
            checkBox.setChecked(names.get(position).isCheckState());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked != names.get(holder.getAdapterPosition()).isCheckState()){
                        names.get(holder.getAdapterPosition()).setCheckState(isChecked);
                        if(checkChangeListener != null){
                            int count = 0;
                            for (MissionFileBean bean : names) {
                                if(bean.isCheckState()){
                                    count++;
                                }
                            }
                            if(isChecked){
                                selectNames.add(names.get(holder.getAdapterPosition()).getName());
                            }else{
                                selectNames.remove(names.get(holder.getAdapterPosition()).getName());
                            }
                            checkChangeListener.onCheckChange(count);
                        }
                    }
                }
            });
        }else{
            checkBox.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public void setNames(List<MissionFileBean> names) {
        this.names = names;
    }

    public void setCheckState(boolean checkState) {
        this.checkState = checkState;
        notifyDataSetChanged();
    }

    public void checkAll(boolean isChecked) {

        for (MissionFileBean missionFileBean : names) {
            missionFileBean.setCheckState(isChecked);
            if(isChecked){
                selectNames.add(missionFileBean.getName());
            }else{
                selectNames.remove(missionFileBean.getName());
            }
        }
        notifyDataSetChanged();
    }

    public List<String> getSelectFiles() {
        return selectNames;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }

    }

    public interface CheckChangeListener{
        void onCheckChange(int num);
    }

    public interface ItemClickListener{
        void onItemClick(String name);
        void onItemEditClick(String name);
        void onItemDeleteClick(String name);
    }

    public void setCheckChangeListener(CheckChangeListener checkChangeListener){
        this.checkChangeListener = checkChangeListener;
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

}
