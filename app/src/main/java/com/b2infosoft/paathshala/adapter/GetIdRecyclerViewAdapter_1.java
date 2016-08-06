package com.b2infosoft.paathshala.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.model.InstituteInfo;

import java.util.List;

/**
 * Created by Microsoft on 7/31/2016.
 */
public class GetIdRecyclerViewAdapter_1 extends RecyclerView.Adapter<GetIdRecyclerViewAdapter_1.ViewHolder> {
    private List<InstituteInfo> id_details;


    public GetIdRecyclerViewAdapter_1(List<InstituteInfo> id_details) {
        this.id_details = id_details;
    }

    @Override
    public GetIdRecyclerViewAdapter_1.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_get_institute_id_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GetIdRecyclerViewAdapter_1.ViewHolder holder, int position) {
        if (position % 2 != 0) {
            holder.cardView.setBackgroundResource(R.color.complaint_card_2);
        } else {
            holder.cardView.setBackgroundResource(R.color.complaint_card_1);
        }
        InstituteInfo get_info = id_details.get(position);
        holder.institute_id.setText(String.valueOf(get_info.getId()));
        holder.institute_name.setText(String.valueOf(get_info.getName()));
    }

    @Override
    public int getItemCount() {
        return id_details.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView institute_id,institute_name;
        CardView cardView;
        LinearLayout institute_layout;
        public ViewHolder(View view) {
            super(view);
            this.cardView = (CardView) view.findViewById(R.id.card_view);
            this.institute_id = (TextView) view.findViewById(R.id.institute_id);
            this.institute_name = (TextView) view.findViewById(R.id.institute_name);
            this.institute_layout = (LinearLayout)view.findViewById(R.id.institute_layout);
        }
    }
}
