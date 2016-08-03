package com.b2infosoft.paathshala.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.model.InstituteInfo;

import java.util.List;

/**
 * Created by Microsoft on 7/31/2016.
 */
public class GetIdRecyclerViewAdapter extends RecyclerView.Adapter<GetIdRecyclerViewAdapter.ViewHolder> {
     private List<InstituteInfo> id_details;


    public GetIdRecyclerViewAdapter(List<InstituteInfo> id_details) {
        this.id_details = id_details;
    }

    @Override
    public GetIdRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_get_institute_id, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GetIdRecyclerViewAdapter.ViewHolder holder, int position) {
                  InstituteInfo get_info = id_details.get(position);
             TextView id = holder.id;
             TextView city_id = holder.city_id;
             TextView name= holder.name;
             TextView schActive = holder.sch_active;


              id.setText(get_info.getId());
              city_id.setText(get_info.getCity_id());
              name.setText(get_info.getSchl_name());
              schActive.setText(get_info.getSch_active());
    }

    @Override
    public int getItemCount() {
        return id_details.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView id,city_id,name,sch_active;

        public ViewHolder(View view) {
              super(view);
              this.id = (TextView)view.findViewById(R.id.school_id);
              this.city_id = (TextView)view.findViewById(R.id.school_city_id);
               this.name = (TextView)view.findViewById(R.id.school_name);
               this.sch_active = (TextView)view.findViewById(R.id.school_active);
        }
    }
}
