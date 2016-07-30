package com.b2infosoft.paathshala.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.fragment.Holiday;
import com.b2infosoft.paathshala.model.DummyContent.DummyItem;
import com.b2infosoft.paathshala.model.HolidayInfo;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link Holiday.OnHolidayFragmentListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class HolidayRecyclerViewAdapter extends RecyclerView.Adapter<HolidayRecyclerViewAdapter.ViewHolder> {

    private final List<HolidayInfo> mValues;

    public HolidayRecyclerViewAdapter(List<HolidayInfo> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_holiday_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mIconView.setImageResource(mValues.get(position).getIcon());
        holder.mIdView.setText(mValues.get(position).getName());
        holder.mContentView.setText(mValues.get(position).getToDate());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mIconView;
        public final TextView mIdView;
        public final TextView mContentView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIconView = (ImageView)view.findViewById(R.id.holiday_icon);
            mIdView = (TextView) view.findViewById(R.id.holiday_subject);
            mContentView = (TextView) view.findViewById(R.id.holiday_duration);
        }
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

    }
}
