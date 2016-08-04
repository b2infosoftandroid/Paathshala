package com.b2infosoft.paathshala.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.fragment.Holiday;
import com.b2infosoft.paathshala.model.ComplaintInfo;
import com.b2infosoft.paathshala.model.DummyContent.DummyItem;
import com.b2infosoft.paathshala.model.HolidayInfo;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link Holiday.OnHolidayFragmentListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ComplaintRecyclerViewAdapter extends RecyclerView.Adapter<ComplaintRecyclerViewAdapter.ViewHolder> {

    private final List<ComplaintInfo> complaintInfoList;

    public ComplaintRecyclerViewAdapter(List<ComplaintInfo> items) {
        complaintInfoList = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_complaint_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ComplaintInfo info = complaintInfoList.get(position);
        TextView msg = holder.msg;
        TextView sub= holder.sub;
        TextView c_date = holder.c_date;

        sub.setText(info.getSubject());
        msg.setText(info.getDetail());
        c_date.setText(info.getCdate());
    }

    @Override
    public int getItemCount() {
        return complaintInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView msg, sub, c_date;

        public ViewHolder(View view) {
            super(view);
            this.sub = (TextView) view.findViewById(R.id.subject_comp);
            this.msg = (TextView) view.findViewById(R.id.body_comp);
            this.c_date = (TextView) view.findViewById(R.id.date_comp);
        }

    }
}
