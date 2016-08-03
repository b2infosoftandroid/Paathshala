package com.b2infosoft.paathshala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.model.HolidayInfo;
import com.b2infosoft.paathshala.model.TimeTableInfo;

import java.util.List;

/**
 * Created by Microsoft on 7/31/2016.
 */
public class HolidayAdapter extends BaseAdapter{
   private Context context;
    List<HolidayInfo> list;

    public HolidayAdapter(Context context, List<HolidayInfo> list) {
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

            view = inflater.inflate(R.layout.fragment_holiday_items, null);
            HolidayInfo info = list.get(position);
            TextView subject = (TextView) view.findViewById(R.id.holiday_subject);
            subject.setText(info.getName());
            TextView time = (TextView) view.findViewById(R.id.holiday_duration_from);
            time.setText(info.getFromDate());
            return view;
        }else{
            return convertView;
        }
    }
}
