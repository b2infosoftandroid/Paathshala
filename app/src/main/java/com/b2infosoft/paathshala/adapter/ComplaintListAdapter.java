package com.b2infosoft.paathshala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.model.ComplaintInfo;

import java.util.List;

/**
 * Created by Microsoft on 8/4/2016.
 */
public class ComplaintListAdapter extends BaseAdapter {
    private Context context;
    List<ComplaintInfo> complaintInfoList;
    TextView msg, sub, c_date;

    public ComplaintListAdapter(Context context, List<ComplaintInfo> complaintInfoList) {
        this.context = context;
        this.complaintInfoList = complaintInfoList;
    }

    @Override
    public int getCount() {
        return complaintInfoList.size();
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
        View view = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {

            view = inflater.inflate(R.layout.fragment_complaint_items, null);
            ComplaintInfo info = complaintInfoList.get(position);
            sub = (TextView) view.findViewById(R.id.subject_comp);
            sub.setText(info.getSubject());
            msg = (TextView) view.findViewById(R.id.body_comp);
            msg.setText(info.getDetail());
            c_date = (TextView) view.findViewById(R.id.date_comp);
            c_date.setText(info.getCdate());
            return view;
        } else {
            return convertView;
        }
    }
}
