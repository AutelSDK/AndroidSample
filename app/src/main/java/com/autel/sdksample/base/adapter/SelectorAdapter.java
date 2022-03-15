package com.autel.sdksample.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.autel.sdksample.R;

import java.util.ArrayList;
import java.util.List;

public class SelectorAdapter<T> extends BaseAdapter {
    protected List<T> elementList = new ArrayList<>();
    protected Context mContext;

    public SelectorAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<T> elementList) {
        this.elementList = elementList;
        notifyDataSetInvalidated();
    }

    @Override
    public int getCount() {
        return null == elementList ? 0 : elementList.size();
    }

    @Override
    public Object getItem(int position) {
        return elementList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = null;
        if (null == convertView) {
            convertView = View.inflate(mContext, R.layout.spinner_item, null);

        }
        textView = (TextView) convertView.findViewById(R.id.spinner_item_text);

        if (position < elementList.size() && position >= 0) {
            textView.setText(elementList.get(position).toString());
        }
        return convertView;
    }
}
