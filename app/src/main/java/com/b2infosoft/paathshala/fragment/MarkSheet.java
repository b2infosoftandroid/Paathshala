package com.b2infosoft.paathshala.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
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
import com.b2infosoft.paathshala.model.DepositInstallent;
import com.b2infosoft.paathshala.model.FeeInstallment;
import com.b2infosoft.paathshala.model.Marks;
import com.b2infosoft.paathshala.volly.MySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MarkSheet extends Fragment {
    private static final String TAG = MarkSheet.class.getName();
    Active active;
    Tags tags;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnMarkSheetListener mListener;
    private View mView;
    private ProgressDialog progress;
    private TextView id, examName, subjectName, tMarks, tMarksObt, pMarks, pMarksObt, addInMark, addInRes;
    private TableLayout tableLayout;
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
        mView = inflater.inflate(R.layout.fragment_mark_sheet, container, false);
        spinner = (Spinner) mView.findViewById(R.id.exam_type_spinner);
        button = (Button) mView.findViewById(R.id.exam_type_search);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = (String) spinner.getSelectedItem();
                searchMarkSheet(type);
            }
        });
        init();
        return mView;
    }

    private void init() {
        tableLayout = (TableLayout) mView.findViewById(R.id.table_layout_mark_sheet);
        TableRow tr_head = new TableRow(getActivity());
        tr_head.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        id = new TextView(getActivity());
        id.setText("ID");
        id.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        id.setTextColor(getResources().getColor(R.color.app_background));
        id.setPadding(10, 5, 10, 5);
        tr_head.addView(id);

        examName = new TextView(getActivity());
        examName.setText("Exam Name");
        examName.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        examName.setTextColor(getResources().getColor(R.color.app_background));
        examName.setPadding(10, 5, 10, 5);
        tr_head.addView(examName);

        subjectName = new TextView(getActivity());
        subjectName.setText("Subject Name");
        subjectName.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        subjectName.setTextColor(getResources().getColor(R.color.app_background));
        subjectName.setPadding(10, 5, 10, 5);
        tr_head.addView(subjectName);

        tMarks = new TextView(getActivity());
        tMarks.setText("T Marks");
        tMarks.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        tMarks.setTextColor(getResources().getColor(R.color.app_background));
        tMarks.setPadding(10, 5, 10, 5);
        tr_head.addView(tMarks);

        tMarksObt = new TextView(getActivity());
        tMarksObt.setText("T Marks Obt");
        tMarksObt.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        tMarksObt.setTextColor(getResources().getColor(R.color.app_background));
        tMarksObt.setPadding(10, 5, 10, 5);
        tr_head.addView(tMarksObt);

        pMarks = new TextView(getActivity());
        pMarks.setText("P Marks");
        pMarks.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        pMarks.setTextColor(getResources().getColor(R.color.app_background));
        pMarks.setPadding(10, 5, 10, 5);
        tr_head.addView(pMarks);

        pMarksObt = new TextView(getActivity());
        pMarksObt.setText("P Marks Obt");
        pMarksObt.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        pMarksObt.setTextColor(getResources().getColor(R.color.app_background));
        pMarksObt.setPadding(10, 5, 10, 5);
        tr_head.addView(pMarksObt);

        addInMark = new TextView(getActivity());
        addInMark.setText("Add In Marks");
        addInMark.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        addInMark.setTextColor(getResources().getColor(R.color.app_background));
        addInMark.setPadding(10, 5, 10, 5);
        tr_head.addView(addInMark);

        addInRes = new TextView(getActivity());
        addInRes.setText("Add In Res");
        addInRes.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        addInRes.setTextColor(getResources().getColor(R.color.app_background));
        addInRes.setPadding(10, 5, 10, 5);
        tr_head.addView(addInRes);

        tableLayout.addView(tr_head);

    }

    private void setResult(List<Marks> marksList) {
        for (Marks marks : marksList) {
            TableRow tr_head = new TableRow(getActivity());
            tr_head.setBackgroundColor(getResources().getColor(R.color.colorAccent));

            id = new TextView(getActivity());
            id.setText(String.valueOf(marks.getId()));
            id.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            id.setTextColor(getResources().getColor(R.color.app_background));
            id.setPadding(10, 5, 10, 5);
            tr_head.addView(id);

            examName = new TextView(getActivity());
            examName.setText(marks.getExamName());
            examName.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            examName.setTextColor(getResources().getColor(R.color.app_background));
            examName.setPadding(10, 5, 10, 5);
            tr_head.addView(examName);

            subjectName = new TextView(getActivity());
            subjectName.setText(marks.getSubjectName());
            subjectName.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            subjectName.setTextColor(getResources().getColor(R.color.app_background));
            subjectName.setPadding(10, 5, 10, 5);
            tr_head.addView(subjectName);

            tMarks = new TextView(getActivity());
            tMarks.setText(String.valueOf(marks.getTMarks()));
            tMarks.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            tMarks.setTextColor(getResources().getColor(R.color.app_background));
            tMarks.setPadding(10, 5, 10, 5);
            tr_head.addView(tMarks);

            tMarksObt = new TextView(getActivity());
            tMarksObt.setText(String.valueOf(marks.getTMarksObt()));
            tMarksObt.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            tMarksObt.setTextColor(getResources().getColor(R.color.app_background));
            tMarksObt.setPadding(10, 5, 10, 5);
            tr_head.addView(tMarksObt);

            pMarks = new TextView(getActivity());
            pMarks.setText(String.valueOf(marks.getPMarks()));
            pMarks.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            pMarks.setTextColor(getResources().getColor(R.color.app_background));
            pMarks.setPadding(10, 5, 10, 5);
            tr_head.addView(pMarks);

            pMarksObt = new TextView(getActivity());
            pMarksObt.setText(String.valueOf(marks.getPMarksObt()));
            pMarksObt.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            pMarksObt.setTextColor(getResources().getColor(R.color.app_background));
            pMarksObt.setPadding(10, 5, 10, 5);
            tr_head.addView(pMarksObt);

            addInMark = new TextView(getActivity());
            addInMark.setText(String.valueOf(marks.getAddInMark()));
            addInMark.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            addInMark.setTextColor(getResources().getColor(R.color.app_background));
            addInMark.setPadding(10, 5, 10, 5);
            tr_head.addView(addInMark);

            addInRes = new TextView(getActivity());
            addInRes.setText(String.valueOf(marks.getAddInRes()));
            addInRes.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            addInRes.setTextColor(getResources().getColor(R.color.app_background));
            addInRes.setPadding(10, 5, 10, 5);
            tr_head.addView(addInRes);

            tableLayout.addView(tr_head);
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

    private void searchMarkSheet(String type) {
        Urls urls = Urls.getInstance();
        HashMap<String, String> map = new HashMap<>();
        map.put(tags.SCHOOL_ID, active.getValue(tags.SCHOOL_ID));
        map.put(tags.SESSION_ID, active.getValue(tags.SESSION_ID));
        map.put(tags.S_ID, active.getValue(tags.S_ID));
        showProgress();
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, urls.getUrl(urls.getPath(tags.FEES_LEDGER), map), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            if (response.has(tags.ARR_MARKS_DETAIL)) {
                                JSONArray result = response.getJSONArray(tags.ARR_MARKS_DETAIL);
                                List<Marks> installments = new ArrayList<>();
                                for (int i = 0; i < result.length(); i++) {
                                    Marks marks = new Marks();
                                    JSONObject object = result.getJSONObject(i);
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
                                    installments.add(marks);
                                }
                                //setDataInstallment(installments);
                            }
                            if (response.has(tags.ARR_FEES_DETAILS_DEPOSIT)) {
                                JSONArray result = response.getJSONArray(tags.ARR_FEES_DETAILS_DEPOSIT);
                                List<DepositInstallent> installments = new ArrayList<>();
                                for (int i = 0; i < result.length(); i++) {
                                    DepositInstallent installment = new DepositInstallent();
                                    JSONObject object = result.getJSONObject(i);
                                    if (object.has(tags.FEES_ID)) {

                                    }
                                    if (object.has(tags.FEES_NAME)) {
                                        installment.setDeposit_name(object.getString(tags.FEES_NAME));
                                    }
                                    if (object.has(tags.FEES_TYPE)) {
                                        installment.setDeposit_type(object.getString(tags.FEES_TYPE));
                                    }
                                    if (object.has(tags.FEES_AMOUNT)) {
                                        installment.setAmount(object.getDouble(tags.FEES_AMOUNT));
                                    }
                                    if (object.has(tags.FEES_RECEIPT_NO)) {
                                        installment.setReceipt_no(object.getInt(tags.FEES_RECEIPT_NO));
                                    }
                                    if (object.has(tags.FEES_PAY_DATE)) {
                                        installment.setReceipt_date(object.getString(tags.FEES_PAY_DATE));
                                    }
                                    if (object.has(tags.FEES_MODE)) {
                                        installment.setPayment_mode(object.getString(tags.FEES_MODE));
                                    }
                                    installments.add(installment);
                                }
                                //setDataDepositInstallment(installments);
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
