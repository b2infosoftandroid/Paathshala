package com.b2infosoft.paathshala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.b2infosoft.paathshala.R;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by rajesh on 7/23/2016.
 */

public class AttendanceCalenderAdapter extends ArrayAdapter<Date> {
    private Context context;
    private HashSet<Date> eventDays;
    private int resource;

    public AttendanceCalenderAdapter(Context context, int resource, ArrayList<Date> objects, HashSet<Date> eventDays) {
        super(context, resource, objects);
        this.resource = resource;
        this.context = context;
        this.eventDays = eventDays;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(resource, parent, false);
        Date date = getItem(position);
        int day = date.getDate();
        int month = date.getMonth();
        int year = date.getYear();

        TextView textView = (TextView)view.findViewById(R.id.calender_cell);
        Date today= new Date();

        // clear styling
        textView.setTextColor(context.getResources().getColor(R.color.current_month_date));
        textView.setBackgroundColor(context.getResources().getColor(R.color.app_background));

        if (month != today.getMonth() || year != today.getYear()) {
            // if this day is outside current month, grey it out
            textView.setTextColor(context.getResources().getColor(R.color.not_in_current_month_date));
        } else if (day == today.getDate()) {
            // if it is today, set it to blue/bold
            textView.setTextColor(context.getResources().getColor(R.color.app_background));
            textView.setBackgroundColor(context.getResources().getColor(R.color.current_date));
        }
        //((TextView)view).setBackgroundResource(R.drawable.grid_cell_border);
        textView.setText(day + "");

        return view;
    }
}
