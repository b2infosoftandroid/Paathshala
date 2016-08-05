package com.b2infosoft.paathshala.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.app.Format;
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
    private Format format;
    public ComplaintRecyclerViewAdapter(List<ComplaintInfo> items) {
        complaintInfoList = items;
        format = Format.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_complaint_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        if(position%2 != 0){
            holder.cardView.setBackgroundResource(R.color.complaint_card_2);
        }else {
            holder.cardView.setBackgroundResource(R.color.complaint_card_1);
        }
        ComplaintInfo info = complaintInfoList.get(position);
        TextView msg = holder.msg;
        TextView sub= holder.sub;
        TextView c_date = holder.c_date;

        sub.setText(info.getSubject());
        msg.setText(info.getDetail());
        c_date.setText(format.getDate(info.getCdate()));
    }

    @Override
    public int getItemCount() {
        return complaintInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView msg, sub, c_date;
        CardView cardView;

        public ViewHolder(View view) {
            super(view);
            this.cardView = (CardView)view.findViewById(R.id.complaint_card_view);
            this.sub = (TextView) view.findViewById(R.id.subject_comp);
            this.msg = (TextView) view.findViewById(R.id.body_comp);
            this.c_date = (TextView) view.findViewById(R.id.date_comp);
        }

    }
}
