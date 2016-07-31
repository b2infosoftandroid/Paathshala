package com.b2infosoft.paathshala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.model.TimeTableInfo;

import java.util.List;

/**
 * Created by Microsoft on 7/31/2016.
 */
public class TimeTableAdapter extends BaseAdapter{
   private Context context;
    List<TimeTableInfo> list;

    public TimeTableAdapter(Context context, List<TimeTableInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            view = new View(context);

            view = inflater.inflate(R.layout.fragment_time_table_items, null);
            TimeTableInfo info = list.get(position);
            TextView subject = (TextView) view.findViewById(R.id.time_table_subject);
            subject.setText(info.getSubject());
            TextView time = (TextView) view.findViewById(R.id.time_table_time);
            time.setText(info.getDate());
            return view;
        }else{
            return convertView;
        }
    }
}
