package com.b2infosoft.paathshala.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.model.MonthInfo;

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
    private MonthInfo info;
    private int month;
    public AttendanceCalenderAdapter(Context context, int resource, ArrayList<Date> objects, HashSet<Date> eventDays,int month) {
        super(context, resource, objects);
        this.resource = resource;
        this.context = context;
        this.eventDays = eventDays;
        this.month = month;
    }
    public AttendanceCalenderAdapter(Context context, int resource, ArrayList<Date> objects, MonthInfo info,int month) {
        super(context, resource, objects);
        this.resource = resource;
        this.context = context;
        this.info = info;
        this.month = month;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(resource, parent, false);
        Date date = getItem(position);
        int day = date.getDate();
        int month_1 = date.getMonth();
        int year = date.getYear();

        TextView textView = (TextView)view.findViewById(R.id.calender_cell);
        Date today= new Date();

        // clear styling
        if(month == month_1) {
            textView.setTextColor(context.getResources().getColor(R.color.current_month_date));
            textView.setBackgroundColor(context.getResources().getColor(R.color.app_background));
            switch (day){
                case 1:
                    setEvent(textView,info.getOne());
                    break;
                case 2:
                    setEvent(textView,info.getTwo());
                    break;
                case 3:
                    setEvent(textView,info.getThree());
                    break;
                case 4:
                    setEvent(textView,info.getFour());
                    break;
                case 5:
                    setEvent(textView,info.getFive());
                    break;
                case 6:
                    setEvent(textView,info.getSix());
                    break;
                case 7:
                    setEvent(textView,info.getSeven());
                    break;
                case 8:
                    setEvent(textView,info.getEight());
                    break;
                case 9:
                    setEvent(textView,info.getNine());
                    break;

                case 10:
                    setEvent(textView,info.getTen());
                    break;

                case 11:
                    setEvent(textView,info.getEleven());
                    break;

                case 12:
                    setEvent(textView,info.getTwelve());
                    break;

                case 13:
                    setEvent(textView,info.getThirteen());
                    break;

                case 14:
                    setEvent(textView,info.getFourteen());
                    break;

                case 15:
                    setEvent(textView,info.getFifteen());
                    break;

                case 16:
                    setEvent(textView,info.getSixteen());
                    break;

                case 17:
                    setEvent(textView,info.getSeventeen());
                    break;

                case 18:
                    setEvent(textView,info.getEighteen());
                    break;

                case 19:
                    setEvent(textView,info.getNineteen());
                    break;

                case 20:
                    setEvent(textView,info.getTwenty());
                    break;

                case 21:
                    setEvent(textView,info.getTOne());
                    break;

                case 22:
                    setEvent(textView,info.getTTwo());
                    break;

                case 23:
                    setEvent(textView,info.getTThree());
                    break;

                case 24:
                    setEvent(textView,info.getTFour());
                    break;

                case 25:
                    setEvent(textView,info.getTFive());
                    break;

                case 26:
                    setEvent(textView,info.getTSix());
                    break;

                case 27:
                    setEvent(textView,info.getTSeven());
                    break;
                case 28:
                    setEvent(textView,info.getTEight());
                    break;
                case 29:
                    setEvent(textView,info.getTNine());
                    break;
                case 30:
                    setEvent(textView,info.getTTen());
                    break;
                case 31:
                    setEvent(textView,info.getTEleven());
                    break;
            }
        }else{
            textView.setTextColor(context.getResources().getColor(R.color.not_in_current_month_date));
            textView.setBackgroundColor(context.getResources().getColor(R.color.app_background));
        }
        /*
        if (month_1 != today.getMonth() || year != today.getYear()) {
            // if this day is outside current month, grey it out
            textView.setTextColor(context.getResources().getColor(R.color.not_in_current_month_date));
        }else if (day == today.getDate()) {
            // if it is today, set it to blue/bold
            textView.setTextColor(context.getResources().getColor(R.color.app_background));
            textView.setBackgroundColor(context.getResources().getColor(R.color.current_date));

        }
        */
        //((TextView)view).setBackgroundResource(R.drawable.grid_cell_border);
        textView.setText(day + "");
        return view;
    }
    private void setEvent(TextView textView,String str){
        if(str=="null"||str==null ||str.length()==0){

        }else{
            if(str.equalsIgnoreCase("P")){
                textView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_present));
            }else if(str.equalsIgnoreCase("A")){
                textView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_absent));
            } else if(str.equalsIgnoreCase("L")){
                textView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_leave));
            } else if(str.equalsIgnoreCase("H")){
                textView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_half_day));
            }
        }
    }
}
