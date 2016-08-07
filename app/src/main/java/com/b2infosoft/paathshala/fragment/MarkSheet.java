package com.b2infosoft.paathshala.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
import com.b2infosoft.paathshala.model.Marks;
import com.b2infosoft.paathshala.model.Result;
import com.b2infosoft.paathshala.services.Network;
import com.b2infosoft.paathshala.volly.MySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class MarkSheet extends Fragment {
    private static final String TAG = MarkSheet.class.getName();
    Active active;
    Tags tags;
    private DBHelper dbHelper;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Network network;
    private String mParam1;
    private String mParam2;

    private OnMarkSheetListener mListener;
    private View mView;
    private ProgressDialog progress;
    private TextView id, examName, subjectName, tMarks, tMarksObt, pMarks, pMarksObt, addInMark, addInRes,result,division,percentage,totMarks,totalObt,markSheetType;

    private TableLayout tableLayout,tableLayout1;
    private Spinner spinner;
    private Button button;

    public MarkSheet() {

    }

    public static MarkSheet newInstance(String param1, String param2) {
        MarkSheet fragment = new MarkSheet();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        active = Active.getInstance(getContext());
        tags = Tags.getInstance();
        network = Network.getInstance(getActivity());
        dbHelper = new DBHelper(getActivity());
        mView = inflater.inflate(R.layout.fragment_mark_sheet, container, false);
        spinner = (Spinner) mView.findViewById(R.id.exam_type_spinner);
        button = (Button) mView.findViewById(R.id.exam_type_search);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinner.getSelectedItemPosition()>0) {
                    String type = (String) spinner.getSelectedItem();
                    List<Marks> marks = dbHelper.getMarkSheetDetails(type);
                    List<Result> results = dbHelper.getMarkSheet(type);
                    if(marks.size()==0) {
                        searchMarkSheet(type);
                    }else {
                        setResult(marks);
                        setResult1(results);
                    }
                }
            }
        });
        return mView;
    }

    private void init() {
        tableLayout = (TableLayout) mView.findViewById(R.id.table_layout_mark_sheet);
        if(tableLayout.getChildCount()>0){
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

        examName = new TextView(getActivity());
        examName.setText("EXAM NAME");
        examName.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        examName.setTextColor(getResources().getColor(R.color.app_background));
        examName.setPadding(30, 30, 30, 30);
        examName.setTypeface(null, Typeface.BOLD);
        tr_head.addView(examName);

        subjectName = new TextView(getActivity());
        subjectName.setText("SUBJECT NAME");
        subjectName.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        subjectName.setTextColor(getResources().getColor(R.color.app_background));
        subjectName.setPadding(30, 30, 30, 30);
        subjectName.setTypeface(null, Typeface.BOLD);
        tr_head.addView(subjectName);

        tMarks = new TextView(getActivity());
        tMarks.setText("T MARKS");
        tMarks.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        tMarks.setTextColor(getResources().getColor(R.color.app_background));
        tMarks.setPadding(30, 30, 30, 30);
        tMarks.setTypeface(null, Typeface.BOLD);
        tr_head.addView(tMarks);

        tMarksObt = new TextView(getActivity());
        tMarksObt.setText("T MARKS OBT");
        tMarksObt.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        tMarksObt.setTextColor(getResources().getColor(R.color.app_background));
        tMarksObt.setPadding(30, 30, 30, 30);
        tMarksObt.setTypeface(null, Typeface.BOLD);
        tr_head.addView(tMarksObt);

        pMarks = new TextView(getActivity());
        pMarks.setText("P MARKS");
        pMarks.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        pMarks.setTextColor(getResources().getColor(R.color.app_background));
        pMarks.setPadding(30, 30, 30, 30);
        pMarks.setTypeface(null, Typeface.BOLD);
        tr_head.addView(pMarks);

        pMarksObt = new TextView(getActivity());
        pMarksObt.setText("P MARKS OBT");
        pMarksObt.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        pMarksObt.setTextColor(getResources().getColor(R.color.app_background));
        pMarksObt.setPadding(30, 30, 30, 30);
        pMarksObt.setTypeface(null, Typeface.BOLD);
        tr_head.addView(pMarksObt);

        addInMark = new TextView(getActivity());
        addInMark.setText("ADD IN MARKS");
        addInMark.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        addInMark.setTextColor(getResources().getColor(R.color.app_background));
        addInMark.setPadding(30, 30, 30, 30);
        addInMark.setTypeface(null, Typeface.BOLD);
        tr_head.addView(addInMark);

        addInRes = new TextView(getActivity());
        addInRes.setText("ADD IN RES");
        addInRes.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        addInRes.setTextColor(getResources().getColor(R.color.app_background));
        addInRes.setPadding(30, 30, 30, 30);
        addInRes.setTypeface(null, Typeface.BOLD);
        tr_head.addView(addInRes);

        tableLayout.addView(tr_head);

    }

    private void init1(){
        tableLayout1 = (TableLayout) mView.findViewById(R.id.table_layout_mark_sheet_details);
        if(tableLayout1.getChildCount()>0){
            tableLayout1.removeAllViews();
        }
        TableRow tr_head1 = new TableRow(getActivity());
        tr_head1.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        id = new TextView(getActivity());
        id.setText("ID");
        id.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        id.setTextColor(getResources().getColor(R.color.app_background));
        id.setPadding(30, 30, 30, 30);
        id.setTypeface(null, Typeface.BOLD);
        tr_head1.addView(id);


        result = new TextView(getActivity());
        result.setText("RESULT");
        result.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        result.setTextColor(getResources().getColor(R.color.app_background));
        result.setPadding(30, 30, 30, 30);
        result.setTypeface(null, Typeface.BOLD);
        tr_head1.addView(result);

        division = new TextView(getActivity());
        division.setText("DIVISION");
        division.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        division.setTextColor(getResources().getColor(R.color.app_background));
        division.setPadding(30, 30, 30, 30);
        division.setTypeface(null, Typeface.BOLD);
        tr_head1.addView(division);

        percentage = new TextView(getActivity());
        percentage.setText("PERCENTAGE");
        percentage.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        percentage.setTextColor(getResources().getColor(R.color.app_background));
        percentage.setPadding(30, 30, 30, 30);
        percentage.setTypeface(null, Typeface.BOLD);
        tr_head1.addView(percentage);

        totMarks = new TextView(getActivity());
        totMarks.setText("TOTAL MARKS");
        totMarks.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        totMarks.setTextColor(getResources().getColor(R.color.app_background));
        totMarks.setPadding(30, 30, 30, 30);
        totMarks.setTypeface(null, Typeface.BOLD);
        tr_head1.addView(totMarks);

        totalObt = new TextView(getActivity());
        totalObt.setText("TOTAL MARKS OBTAIN");
        totalObt.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        totalObt.setTextColor(getResources().getColor(R.color.app_background));
        totalObt.setPadding(30, 30, 30, 30);
        totalObt.setTypeface(null, Typeface.BOLD);
        tr_head1.addView(totalObt);

        markSheetType = new TextView(getActivity());
        markSheetType.setText("MARKSHEET TYPE");
        markSheetType.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        markSheetType.setTextColor(getResources().getColor(R.color.app_background));
        markSheetType.setPadding(30, 30, 30, 30);
        markSheetType.setTypeface(null, Typeface.BOLD);
        tr_head1.addView(markSheetType);

        tableLayout1.addView(tr_head1);

    }

    private void setResult(List<Marks> marksList) {
        init();
        int i=0;
        for (Marks marks : marksList) {
            TableRow tr_head = new TableRow(getActivity());
            if (i % 2 != 0)
                tr_head.setBackgroundColor(getResources().getColor(R.color.not_in_current_month_date));
            i++;
            id = new TextView(getActivity());
            id.setText(String.valueOf(marks.getId()));
            id.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            id.setTextColor(getResources().getColor(R.color.colorAccent));
            id.setPadding(30, 30, 30, 30);
            tr_head.addView(id);

            examName = new TextView(getActivity());
            examName.setText(marks.getExamName());
            examName.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            examName.setTextColor(getResources().getColor(R.color.colorAccent));
            examName.setPadding(30, 30, 30, 30);
            tr_head.addView(examName);

            subjectName = new TextView(getActivity());
            subjectName.setText(marks.getSubjectName());
            subjectName.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            subjectName.setTextColor(getResources().getColor(R.color.colorAccent));
            subjectName.setPadding(30, 30, 30, 30);
            tr_head.addView(subjectName);

            tMarks = new TextView(getActivity());
            tMarks.setText(String.valueOf(marks.getTMarks()));
            tMarks.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            tMarks.setTextColor(getResources().getColor(R.color.colorAccent));
            tMarks.setPadding(30, 30, 30, 30);
            tMarks.setGravity(Gravity.CENTER);
            tr_head.addView(tMarks);

            tMarksObt = new TextView(getActivity());
            tMarksObt.setText(String.valueOf(marks.getTMarksObt()));
            tMarksObt.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            tMarksObt.setTextColor(getResources().getColor(R.color.colorAccent));
            tMarksObt.setPadding(30, 30, 30, 30);
            tMarksObt.setGravity(Gravity.CENTER);
            tr_head.addView(tMarksObt);

            pMarks = new TextView(getActivity());
            pMarks.setText(String.valueOf(marks.getPMarks()));
            pMarks.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            pMarks.setTextColor(getResources().getColor(R.color.colorAccent));
            pMarks.setPadding(30, 30, 30, 30);
            pMarks.setGravity(Gravity.CENTER);
            tr_head.addView(pMarks);

            pMarksObt = new TextView(getActivity());
            pMarksObt.setText(String.valueOf(marks.getPMarksObt()));
            pMarksObt.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            pMarksObt.setTextColor(getResources().getColor(R.color.colorAccent));
            pMarksObt.setPadding(30, 30, 30, 30);
            pMarksObt.setGravity(Gravity.CENTER);
            tr_head.addView(pMarksObt);

            addInMark = new TextView(getActivity());
            addInMark.setText(String.valueOf(marks.getAddInMark()));
            addInMark.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            addInMark.setTextColor(getResources().getColor(R.color.colorAccent));
            addInMark.setPadding(30, 30, 30, 30);
            addInMark.setGravity(Gravity.CENTER);
            tr_head.addView(addInMark);

            addInRes = new TextView(getActivity());
            addInRes.setText(String.valueOf(marks.getAddInRes()));
            addInRes.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            addInRes.setAllCaps(true);
            addInRes.setTextColor(getResources().getColor(R.color.colorAccent));
            addInRes.setPadding(30, 30, 30, 30);
            addInRes.setGravity(Gravity.CENTER);
            tr_head.addView(addInRes);

            tableLayout.addView(tr_head);
        }
    }

    private void setResult1(List<Result> resultList) {
        init1();
        int i=0;
        for (Result res : resultList) {
            TableRow tr_head1 = new TableRow(getActivity());
            if (i % 2 != 0)
                tr_head1.setBackgroundColor(getResources().getColor(R.color.not_in_current_month_date));
            i++;

            id = new TextView(getActivity());
            id.setText(String.valueOf(res.getId()));
            id.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            id.setTextColor(getResources().getColor(R.color.colorAccent));
            id.setPadding(30, 30, 30, 30);
            tr_head1.addView(id);


            result = new TextView(getActivity());
            result.setText(res.getResult());
            result.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            result.setTextColor(getResources().getColor(R.color.colorAccent));
            result.setPadding(30, 30, 30, 30);
            result.setGravity(Gravity.CENTER);
            tr_head1.addView(result);

            division = new TextView(getActivity());
            division.setText(res.getDivision());
            division.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            division.setTextColor(getResources().getColor(R.color.colorAccent));
            division.setPadding(30, 30, 30, 30);
            tr_head1.addView(division);

            percentage = new TextView(getActivity());
            percentage.setText(String.valueOf(res.getPercentage()));
            percentage.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            percentage.setTextColor(getResources().getColor(R.color.colorAccent));
            percentage.setPadding(30, 30, 30, 30);
            percentage.setGravity(Gravity.CENTER);
            tr_head1.addView(percentage);

            totMarks = new TextView(getActivity());
            totMarks.setText(String.valueOf(res.getTotalMarks()));
            totMarks.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            totMarks.setTextColor(getResources().getColor(R.color.colorAccent));
            totMarks.setPadding(30, 30, 30, 30);
            totMarks.setGravity(Gravity.CENTER);
            tr_head1.addView(totMarks);

            totalObt = new TextView(getActivity());
            totalObt.setText(String.valueOf(res.getTotalObtain()));
            totalObt.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            totalObt.setTextColor(getResources().getColor(R.color.colorAccent));
            totalObt.setPadding(30, 30, 30, 30);
            totalObt.setGravity(Gravity.CENTER);
            tr_head1.addView(totalObt);

            markSheetType = new TextView(getActivity());
            markSheetType.setText(res.getMarkSheetType());
            markSheetType.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            markSheetType.setTextColor(getResources().getColor(R.color.colorAccent));
            markSheetType.setPadding(30, 30, 30, 30);
            tr_head1.addView(markSheetType);

            tableLayout1.addView(tr_head1);
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onMarkSheetInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMarkSheetListener) {
            mListener = (OnMarkSheetListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMarkSheetListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnMarkSheetListener {
        void onMarkSheetInteraction(Uri uri);
    }

    private void searchMarkSheet(final String type) {
        if(!network.isInternetAvailable()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            return;
        }
        Urls urls = Urls.getInstance();
        HashMap<String, String> map = new HashMap<>();
        map.put(tags.SCHOOL_ID, active.getValue(tags.SCHOOL_ID));
        map.put(tags.SESSION_ID, active.getValue(tags.SESSION_ID));
        map.put(tags.S_ID, active.getValue(tags.S_ID));
        map.put(tags.M_EXAM_TYPE,type);
        showProgress();
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, urls.getUrl(urls.getPath(tags.MARKS_SHEET), map), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(TAG, response.toString());
                        try {
                            if (response.has(tags.ARR_MARKS_DETAIL)) {
                                JSONArray jsonArray = response.getJSONArray(tags.ARR_MARKS_DETAIL);
                                List<Marks> marksList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Marks marks = new Marks();
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    if (object.has(tags.FEES_ID)) {
                                        marks.setId(object.getInt(tags.FEES_ID));
                                    }
                                    if (object.has(tags.M_EXAM_NAME)) {
                                        marks.setExamName(object.getString(tags.M_EXAM_NAME));
                                    }
                                    if (object.has(tags.M_SUBJECT_NAME)) {
                                        marks.setSubjectName(object.getString(tags.M_SUBJECT_NAME));
                                    }
                                    if (object.has(tags.M_T_MARKS)) {
                                        marks.setTMarks(object.getDouble(tags.M_T_MARKS));
                                    }
                                    if (object.has(tags.M_T_MARKS_OBT)) {
                                        marks.setTMarksObt(object.getDouble(tags.M_T_MARKS_OBT));
                                    }
                                    if (object.has(tags.M_P_MARKS)) {
                                        marks.setPMarks(object.getDouble(tags.M_P_MARKS));
                                    }
                                    if (object.has(tags.M_P_MARKS_OBT)) {
                                        marks.setPMarksObt(object.getDouble(tags.M_P_MARKS_OBT));
                                    }
                                    if (object.has(tags.M_ADD_IN_MARK)) {
                                        marks.setAddInMark(object.getString(tags.M_ADD_IN_MARK));
                                    }
                                    if (object.has(tags.M_ADD_IN_RES)) {
                                        marks.setAddInRes(object.getString(tags.M_ADD_IN_RES));
                                    }
                                    marks.setSearchType(type);
                                    marksList.add(marks);
                                }
                                setResult(marksList);
                                dbHelper.deleteMarkSheetDetails(type);
                                dbHelper.setMarkSheetDetails(marksList);
                            }
                            if (response.has(tags.ARR_MARK_SHEET)) {
                                JSONArray jsonArray = response.getJSONArray(tags.ARR_MARK_SHEET);
                                List<Result> resultList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Result result = new Result();
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    if (object.has(tags.M_ID)) {
                                        result.setId(object.getInt(tags.M_ID));
                                    }
                                    if (object.has(tags.M_RESULT)) {
                                        result.setResult(object.getString(tags.M_RESULT));
                                    }
                                    if (object.has(tags.M_DIVISION)) {
                                        result.setDivision(object.getString(tags.M_DIVISION));
                                    }
                                    if (object.has(tags.M_PERCENTAGE)) {
                                        result.setPercentage(object.getDouble(tags.M_PERCENTAGE));
                                    }
                                    if (object.has(tags.M_TOT_MARKS)) {
                                        result.setTotalMarks(object.getDouble(tags.M_TOT_MARKS));
                                    }
                                    if (object.has(tags.M_TOTAL_OBT)) {
                                        result.setTotalObtain(object.getDouble(tags.M_TOTAL_OBT));
                                    }
                                    if (object.has(tags.M_MARK_SHEET_TYPE)) {
                                        result.setMarkSheetType(object.getString(tags.M_MARK_SHEET_TYPE));
                                    }
                                    result.setSearchType(type);
                                    resultList.add(result);
                                }
                                setResult1(resultList);
                                dbHelper.deleteMarkSheet(type);
                                dbHelper.setMarkSheet(resultList);
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
