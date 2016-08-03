package com.b2infosoft.paathshala.fragment.attendance;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.app.Tags;
import com.b2infosoft.paathshala.app.Urls;
import com.b2infosoft.paathshala.credential.Active;
import com.b2infosoft.paathshala.model.Marks;
import com.b2infosoft.paathshala.model.Result;
import com.b2infosoft.paathshala.model.YearInfo;
import com.b2infosoft.paathshala.volly.MySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Year extends Fragment {
    private static final String TAG = Year.class.getName();
    Active active;
    Tags tags;
    private View mView;
    private ProgressDialog progress;
    TextView id, month, present, absent, leave, halfDay, total;
    TableLayout tableLayout;

    public Year() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        active = Active.getInstance(getContext());
        tags = Tags.getInstance();
        mView = inflater.inflate(R.layout.fragment_year, container, false);
        Date date = new Date();
        searchYearAttendance(String.valueOf(date.getYear()+1900));
        return mView;
    }

    private void init() {
        tableLayout = (TableLayout) mView.findViewById(R.id.table_layout_mark_sheet);
        if (tableLayout.getChildCount() > 0) {
            tableLayout.removeAllViews();
        }

        TableRow tr_head = new TableRow(getActivity());
        tr_head.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        id = new TextView(getActivity());
        id.setText("ID");
        id.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        id.setTextColor(getResources().getColor(R.color.app_background));
        id.setPadding(30, 25, 30, 25);
        tr_head.addView(id);

        month = new TextView(getActivity());
        month.setText("MONTH");
        month.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        month.setTextColor(getResources().getColor(R.color.app_background));
        month.setPadding(30, 25, 30, 25);
        tr_head.addView(month);

        present = new TextView(getActivity());
        present.setText("PRESENT");
        present.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        present.setTextColor(getResources().getColor(R.color.app_background));
        present.setPadding(30, 25, 30, 25);
        tr_head.addView(present);

        absent = new TextView(getActivity());
        absent.setText("ABSENT");
        absent.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        absent.setTextColor(getResources().getColor(R.color.app_background));
        absent.setPadding(30, 25, 30, 25);
        tr_head.addView(absent);

        leave = new TextView(getActivity());
        leave.setText("LEAVE");
        leave.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        leave.setTextColor(getResources().getColor(R.color.app_background));
        leave.setPadding(30, 25, 30, 25);
        tr_head.addView(leave);

        halfDay = new TextView(getActivity());
        halfDay.setText("HALF DAY");
        halfDay.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        halfDay.setTextColor(getResources().getColor(R.color.app_background));
        halfDay.setPadding(30,25, 30, 25);
        tr_head.addView(halfDay);

        total = new TextView(getActivity());
        total.setText("TOTAL");
        total.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        total.setTextColor(getResources().getColor(R.color.app_background));
        total.setPadding(30, 25, 30, 25);
        tr_head.addView(total);

        tableLayout.addView(tr_head);
    }

    private void setResult(List<YearInfo> marksList) {
        init();
        int i = 0;
        for (YearInfo marks : marksList) {
            TableRow tr_head = new TableRow(getActivity());
            if (i % 2 != 0)
                tr_head.setBackgroundColor(getResources().getColor(R.color.not_in_current_month_date));
            i++;
            id = new TextView(getActivity());
            id.setText(String.valueOf(marks.getId()));
            id.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            id.setTextColor(getResources().getColor(R.color.colorAccent));
            id.setPadding(30, 25, 30, 25);
            tr_head.addView(id);

            month = new TextView(getActivity());
            month.setText(marks.getMonth());
            month.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            month.setTextColor(getResources().getColor(R.color.colorAccent));
            month.setPadding(30, 25, 30, 25);
            tr_head.addView(month);

            present = new TextView(getActivity());
            present.setText(String.valueOf(marks.getPresent()));
            present.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            present.setTextColor(getResources().getColor(R.color.colorAccent));
            present.setPadding(30, 25, 30, 25);
            tr_head.addView(present);

            absent = new TextView(getActivity());
            absent.setText(String.valueOf(marks.getAbsent()));
            absent.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            absent.setTextColor(getResources().getColor(R.color.colorAccent));
            absent.setPadding(30, 25, 30, 25);
            tr_head.addView(absent);

            leave = new TextView(getActivity());
            leave.setText(String.valueOf(marks.getLeave()));
            leave.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            leave.setTextColor(getResources().getColor(R.color.colorAccent));
            leave.setPadding(30, 25, 30, 25);
            tr_head.addView(leave);

            halfDay = new TextView(getActivity());
            halfDay.setText(String.valueOf(marks.getHalfDay()));
            halfDay.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            halfDay.setTextColor(getResources().getColor(R.color.colorAccent));
            halfDay.setPadding(30, 25, 30, 25);
            tr_head.addView(halfDay);

            total = new TextView(getActivity());
            total.setText(String.valueOf(marks.getTotal()));
            total.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            total.setTextColor(getResources().getColor(R.color.colorAccent));
            total.setPadding(30, 25, 30, 25);
            tr_head.addView(total);

            tableLayout.addView(tr_head);
        }
    }

    private void searchYearAttendance(String year) {
        Urls urls = Urls.getInstance();
        HashMap<String, String> map = new HashMap<>();
        map.put(tags.SCHOOL_ID, active.getValue(tags.SCHOOL_ID));
        map.put(tags.SESSION_ID, active.getValue(tags.SESSION_ID));
        map.put(tags.S_ID, active.getValue(tags.S_ID));
        map.put(tags.YEAR_YEAR, year);
        showProgress();
        String url = urls.getUrl(urls.getPath(tags.YEAR_ATTENDANCE), map);
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET,url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.d(TAG, response.toString());
                        try {
                            if (response.has(tags.ARR_TOTAL_YEAR_ATTENDANCE)) {
                                JSONArray result = response.getJSONArray(tags.ARR_TOTAL_YEAR_ATTENDANCE);
                                List<YearInfo> installments = new ArrayList<>();
                                for (int i = 0; i < result.length(); i++) {
                                    YearInfo marks = new YearInfo();
                                    JSONObject object = result.getJSONObject(i);
                                    if (object.has(tags.S_ID)) {
                                        marks.setId(object.getInt(tags.S_ID));
                                    }
                                    if (object.has(tags.YEAR_MONTH)) {
                                        marks.setMonth(object.getString(tags.YEAR_MONTH));
                                    }
                                    if (object.has(tags.YEAR_PRESENT)) {
                                        marks.setPresent(object.getInt(tags.YEAR_PRESENT));
                                    }
                                    if (object.has(tags.YEAR_ABSENT)) {
                                        marks.setAbsent(object.getInt(tags.YEAR_ABSENT));
                                    }
                                    if (object.has(tags.YEAR_HALF_DAY)) {
                                        marks.setHalfDay(object.getInt(tags.YEAR_HALF_DAY));
                                    }
                                    if (object.has(tags.YEAR_LEAVE)) {
                                        marks.setLeave(object.getInt(tags.YEAR_LEAVE));
                                    }
                                    if (object.has(tags.YEAR_TOTAL)) {
                                        marks.setTotal(object.getInt(tags.YEAR_TOTAL));
                                    }
                                    installments.add(marks);
                                }
                                setResult(installments);
                            }
                            dismissProgress();
                        } catch (Exception e) {
                            dismissProgress();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                    }
                });
        jsObjRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        jsObjRequest.setTag(TAG);
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsObjRequest);

    }

    private void showProgress() {
        progress = new ProgressDialog(getActivity());
        progress.setMessage("Please Wait...");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();
    }

    private void dismissProgress() {
        if (progress != null) {
            progress.dismiss();
        }
    }

}
