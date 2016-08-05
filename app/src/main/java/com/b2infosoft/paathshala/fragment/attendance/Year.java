package com.b2infosoft.paathshala.fragment.attendance;


import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
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
import com.b2infosoft.paathshala.database.DBHelper;
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
    DBHelper dbHelper;
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
        dbHelper = new DBHelper(getActivity());
        mView = inflater.inflate(R.layout.fragment_year, container, false);
        Date date = new Date();
        updateList();
        return mView;
    }

    private void updateList() {
        String[] session = active.getValue(tags.SESSION).split("-");
        List<YearInfo> yearInfos = dbHelper.getYearAttendance();
        if (yearInfos.size() == 0) {
            searchYearAttendance(session[0]);
            searchYearAttendance(session[1]);
        } else {
            setResult(yearInfos);
        }
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
        id.setPadding(30, 30, 30, 30);
        id.setTypeface(null, Typeface.BOLD);
        tr_head.addView(id);

        month = new TextView(getActivity());
        month.setText("MONTH");
        month.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        month.setTextColor(getResources().getColor(R.color.app_background));
        month.setPadding(30, 30, 30, 30);
        month.setTypeface(null,Typeface.BOLD);
        tr_head.addView(month);

        present = new TextView(getActivity());
        present.setText("PRESENT");
        present.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        present.setTextColor(getResources().getColor(R.color.app_background));
        present.setPadding(30, 30, 30, 30);
        present.setTypeface(null,Typeface.BOLD);
        tr_head.addView(present);

        absent = new TextView(getActivity());
        absent.setText("ABSENT");
        absent.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        absent.setTextColor(getResources().getColor(R.color.app_background));
        absent.setPadding(30, 30, 30, 30);
        absent.setTypeface(null,Typeface.BOLD);
        tr_head.addView(absent);

        leave = new TextView(getActivity());
        leave.setText("LEAVE");
        leave.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        leave.setTextColor(getResources().getColor(R.color.app_background));
        leave.setPadding(30, 30, 30, 30);
        leave.setTypeface(null,Typeface.BOLD);
        tr_head.addView(leave);

        halfDay = new TextView(getActivity());
        halfDay.setText("HALF DAY");
        halfDay.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        halfDay.setTextColor(getResources().getColor(R.color.app_background));
        halfDay.setPadding(30, 30, 30, 30);
        halfDay.setTypeface(null,Typeface.BOLD);
        tr_head.addView(halfDay);

        total = new TextView(getActivity());
        total.setText("TOTAL");
        total.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        total.setTextColor(getResources().getColor(R.color.app_background));
        total.setPadding(30, 30, 30, 30);
        total.setTypeface(null,Typeface.BOLD);
        tr_head.addView(total);

        tableLayout.addView(tr_head);
    }

    private void setResult(List<YearInfo> yearInfos) {
        init();
        int i = 0;
        for (YearInfo year : yearInfos) {
            TableRow tr_head = new TableRow(getActivity());
            if (i % 2 != 0)
                tr_head.setBackgroundColor(getResources().getColor(R.color.table_row));
            i++;
            id = new TextView(getActivity());
            id.setText(String.valueOf(year.getId()));
            id.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            id.setTextColor(getResources().getColor(R.color.colorAccent));
            id.setPadding(30, 30, 30, 30);
            tr_head.addView(id);

            month = new TextView(getActivity());
            month.setText(year.getMonth());
            month.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            month.setTextColor(getResources().getColor(R.color.colorAccent));
            month.setPadding(30, 30, 30, 30);
            tr_head.addView(month);

            present = new TextView(getActivity());
            present.setText(String.valueOf(year.getPresent()));
            present.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            present.setTextColor(getResources().getColor(R.color.colorAccent));
            present.setPadding(30, 30, 30, 30);
            present.setGravity(Gravity.CENTER);
            tr_head.addView(present);

            absent = new TextView(getActivity());
            absent.setText(String.valueOf(year.getAbsent()));
            absent.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            absent.setTextColor(getResources().getColor(R.color.colorAccent));
            absent.setPadding(30, 30, 30, 30);
            absent.setGravity(Gravity.CENTER);
            tr_head.addView(absent);

            leave = new TextView(getActivity());
            leave.setText(String.valueOf(year.getLeave()));
            leave.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            leave.setTextColor(getResources().getColor(R.color.colorAccent));
            leave.setPadding(30, 30, 30, 30);
            leave.setGravity(Gravity.CENTER);
            tr_head.addView(leave);

            halfDay = new TextView(getActivity());
            halfDay.setText(String.valueOf(year.getHalfDay()));
            halfDay.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            halfDay.setTextColor(getResources().getColor(R.color.colorAccent));
            halfDay.setPadding(30, 30, 30, 30);
            halfDay.setGravity(Gravity.CENTER);
            tr_head.addView(halfDay);

            total = new TextView(getActivity());
            total.setText(String.valueOf(year.getTotal()));
            total.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            total.setTextColor(getResources().getColor(R.color.colorAccent));
            total.setPadding(30, 30, 30, 30);
            total.setGravity(Gravity.CENTER);
            tr_head.addView(total);

            tableLayout.addView(tr_head);
        }
    }

    private void searchYearAttendance(final String search) {
        Urls urls = Urls.getInstance();
        HashMap<String, String> map = new HashMap<>();
        map.put(tags.SCHOOL_ID, active.getValue(tags.SCHOOL_ID));
        map.put(tags.SESSION_ID, active.getValue(tags.SESSION_ID));
        map.put(tags.S_ID, active.getValue(tags.S_ID));
        map.put(tags.YEAR_YEAR, search);
        //showProgress();
        String url = urls.getUrl(urls.getPath(tags.YEAR_ATTENDANCE), map);
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.d(TAG, response.toString());
                        try {
                            if (response.has(tags.ARR_TOTAL_YEAR_ATTENDANCE)) {
                                JSONArray result = response.getJSONArray(tags.ARR_TOTAL_YEAR_ATTENDANCE);
                                List<YearInfo> yearInfos = new ArrayList<>();
                                for (int i = 0; i < result.length(); i++) {
                                    YearInfo year = new YearInfo();
                                    JSONObject object = result.getJSONObject(i);
                                    if (object.has(tags.S_ID)) {
                                        year.setId(object.getInt(tags.S_ID));
                                    }
                                    if (object.has(tags.YEAR_MONTH)) {
                                        year.setMonth(object.getString(tags.YEAR_MONTH));
                                    }
                                    if (object.has(tags.YEAR_PRESENT)) {
                                        year.setPresent(object.getInt(tags.YEAR_PRESENT));
                                    }
                                    if (object.has(tags.YEAR_ABSENT)) {
                                        year.setAbsent(object.getInt(tags.YEAR_ABSENT));
                                    }
                                    if (object.has(tags.YEAR_HALF_DAY)) {
                                        year.setHalfDay(object.getInt(tags.YEAR_HALF_DAY));
                                    }
                                    if (object.has(tags.YEAR_LEAVE)) {
                                        year.setLeave(object.getInt(tags.YEAR_LEAVE));
                                    }
                                    if (object.has(tags.YEAR_TOTAL)) {
                                        year.setTotal(object.getInt(tags.YEAR_TOTAL));
                                    }
                                    year.setYear(Integer.parseInt(search));
                                    yearInfos.add(year);
                                }
                                //setResult(installments);
                                dismissProgress();
                                updateList();
                                dbHelper.deleteYearAttendance(search);
                                dbHelper.setYearAttendance(yearInfos);
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
                        dismissProgress();
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
        if (!progress.isShowing())
            progress.show();
    }

    private void dismissProgress() {
        if (progress != null) {
            progress.dismiss();
        }
    }

}
