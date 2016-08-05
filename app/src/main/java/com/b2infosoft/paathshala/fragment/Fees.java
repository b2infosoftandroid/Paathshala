package com.b2infosoft.paathshala.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.app.Fonts;
import com.b2infosoft.paathshala.app.Tags;
import com.b2infosoft.paathshala.app.Urls;
import com.b2infosoft.paathshala.credential.Active;
import com.b2infosoft.paathshala.database.DBHelper;
import com.b2infosoft.paathshala.model.DepositInstallment;
import com.b2infosoft.paathshala.model.FeeInstallment;
import com.b2infosoft.paathshala.volly.MySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fees extends Fragment {
    private final static String TAG = Fees.class.getName();
    Active active;
    Tags tags;
    Fonts fonts = Fonts.getInstance();

    TableLayout t1, t2;
    TextView name, type, total_fee, deposit, t_discount, balance, deposit_name, deposit_type, amt, receipt_no, receipt_date, pay_mode;
    List<FeeInstallment> fee_installments;
    List<DepositInstallment> deposit_installments;
    ScrollView sv;
    View view;
    private OnFeesListener mListener;
    private ProgressDialog progress = null;
    private DBHelper dbHelper;

    public Fees() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void init() {
        sv = (ScrollView) view.findViewById(R.id.scrollview);
        t1 = (TableLayout) view.findViewById(R.id.fee_ledger);
        t2 = (TableLayout) view.findViewById(R.id.fee_deposit_table);
        TableRow tr_head = new TableRow(getActivity());
        tr_head.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        TableRow tr1_head = new TableRow(getActivity());
        tr1_head.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        name = new TextView(getActivity());
        name.setText("FEE NAME");
        name.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        name.setTextColor(getResources().getColor(R.color.app_background));
        name.setPadding(30, 30, 30, 30);
        tr_head.addView(name);

        type = new TextView(getActivity());
        type.setText("FEE TYPE");
        type.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        type.setTextColor(getResources().getColor(R.color.app_background));
        type.setPadding(30, 30, 30, 30);
        tr_head.addView(type);

        total_fee = new TextView(getActivity());
        total_fee.setText("TOTAL FEES");
        total_fee.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        total_fee.setTextColor(getResources().getColor(R.color.app_background));
        total_fee.setPadding(30, 30, 30, 30);
        tr_head.addView(total_fee);

        deposit = new TextView(getActivity());
        deposit.setText("DEPOSIT");
        deposit.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        deposit.setTextColor(getResources().getColor(R.color.app_background));
        deposit.setPadding(30, 30, 30, 30);
        tr_head.addView(deposit);

        t_discount = new TextView(getActivity());
        t_discount.setText("T DISCOUNT");
        t_discount.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        t_discount.setTextColor(getResources().getColor(R.color.app_background));
        t_discount.setPadding(30, 30, 30, 30);
        tr_head.addView(t_discount);

        balance = new TextView(getActivity());
        balance.setText("BALANCE");
        balance.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        balance.setTextColor(getResources().getColor(R.color.app_background));
        balance.setPadding(30, 30, 30, 30);
        tr_head.addView(balance);

        t1.addView(tr_head);

        deposit_name = new TextView(getActivity());
        deposit_name.setText("FEE NAME");
        deposit_name.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        deposit_name.setTextColor(getResources().getColor(R.color.app_background));
        deposit_name.setPadding(30, 30, 30, 30);
        tr1_head.addView(deposit_name);

        deposit_type = new TextView(getActivity());
        deposit_type.setText("FEE TYPE");
        deposit_type.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        deposit_type.setTextColor(getResources().getColor(R.color.app_background));
        deposit_type.setPadding(30, 30, 30, 30);
        tr1_head.addView(deposit_type);

        amt = new TextView(getActivity());
        amt.setText("AMOUNT");
        amt.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        amt.setTextColor(getResources().getColor(R.color.app_background));
        amt.setPadding(30, 30, 30, 30);
        tr1_head.addView(amt);

        receipt_no = new TextView(getActivity());
        receipt_no.setText("RECEIPT NO");
        receipt_no.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        receipt_no.setTextColor(getResources().getColor(R.color.app_background));
        receipt_no.setPadding(30, 30, 30, 30);
        tr1_head.addView(receipt_no);

        receipt_date = new TextView(getActivity());
        receipt_date.setText("RECEIPT DATE");
        receipt_date.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        receipt_date.setTextColor(getResources().getColor(R.color.app_background));
        receipt_date.setPadding(30, 30, 30, 30);
        tr1_head.addView(receipt_date);

        pay_mode = new TextView(getActivity());
        pay_mode.setText("PAYMENT MODE");
        pay_mode.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
        pay_mode.setTextColor(getResources().getColor(R.color.app_background));
        pay_mode.setPadding(30, 30, 30, 30);
        tr1_head.addView(pay_mode);

        t2.addView(tr1_head);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dbHelper = new DBHelper(getActivity());
        active = Active.getInstance(getActivity());
        tags = Tags.getInstance();
        view = inflater.inflate(R.layout.fragment_fees, container, false);
        init();
        //setDataInstallment(getInstallments());
        List<FeeInstallment> installments = dbHelper.getInstallments();
        List<DepositInstallment> depositInstallments = dbHelper.getDepositInstallments();
        if (installments.size() == 0) {
            fetchInstallmentData();
        } else {
            setDataInstallment(installments);
            setDataDepositInstallment(depositInstallments);
        }
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFeesInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFeesListener) {
            mListener = (OnFeesListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFeesListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFeesListener {
        void onFeesInteraction(Uri uri);
    }

    /*
    private void setFonts() {
        name.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_MEDIUM));
        type.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_MEDIUM));
        total_fee.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_MEDIUM));
        deposit.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_MEDIUM));
        discount.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_MEDIUM));
        balance.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_MEDIUM));

        deposit_name.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_MEDIUM));
        deposit_type.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_MEDIUM));
        amount.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_MEDIUM));
        receipt_no.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_MEDIUM));
        receipt_date.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_MEDIUM));
        payment_type.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_MEDIUM));
    }
    */
    private void setDataInstallment(List<FeeInstallment> installments) {
        for (int i = 0; i < installments.size(); i++) {
            FeeInstallment installment = installments.get(i);
            TableRow tr1 = new TableRow(getActivity());
            if (i % 2 != 0)
                tr1.setBackgroundColor(getResources().getColor(R.color.table_row));

            name = new TextView(getActivity());
            name.setText(installment.getName());
            name.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            name.setTextColor(getResources().getColor(R.color.colorAccent));
            name.setPadding(30, 30, 30, 30);
           // name.setGravity(Gravity.CENTER);
            tr1.addView(name);


            type = new TextView(getActivity());
            type.setText(installment.getType());
            type.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            type.setTextColor(getResources().getColor(R.color.colorAccent));
            type.setPadding(30, 30, 30, 30);
            type.setGravity(Gravity.CENTER);
            tr1.addView(type);

            total_fee = new TextView(getActivity());
            total_fee.setText(String.valueOf(installment.getTotal()));
            total_fee.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            total_fee.setTextColor(getResources().getColor(R.color.colorAccent));
            total_fee.setPadding(30, 30, 30, 30);
            total_fee.setGravity(Gravity.CENTER);
            tr1.addView(total_fee);

            deposit = new TextView(getActivity());
            deposit.setText(String.valueOf(installment.getDeposit()));
            deposit.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            deposit.setTextColor(getResources().getColor(R.color.colorAccent));
            deposit.setPadding(30, 30, 30, 30);
            deposit.setGravity(Gravity.CENTER);
            tr1.addView(deposit);

            t_discount = new TextView(getActivity());
            t_discount.setText(String.valueOf(installment.getDiscount()));
            t_discount.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            t_discount.setTextColor(getResources().getColor(R.color.colorAccent));
            t_discount.setPadding(30, 30, 30, 30);
            t_discount.setGravity(Gravity.CENTER);
            tr1.addView(t_discount);

            balance = new TextView(getActivity());
            balance.setText(String.valueOf(installment.getBalance()));
            balance.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            balance.setTextColor(getResources().getColor(R.color.colorAccent));
            balance.setPadding(30, 30, 30, 30);
            balance.setGravity(Gravity.CENTER);
            tr1.addView(balance);
            t1.addView(tr1);
        }
    }

    private void setDataDepositInstallment(List<DepositInstallment> deposits) {
        for (int i = 0; i < deposits.size(); i++) {
            DepositInstallment deposit = deposits.get(i);
            TableRow tr = new TableRow(getActivity());
            if (i % 2 != 0)
                tr.setBackgroundColor(getResources().getColor(R.color.table_row));

            deposit_name = new TextView(getActivity());
            deposit_name.setText(deposit.getName());
            deposit_name.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            deposit_name.setTextColor(getResources().getColor(R.color.colorAccent));
            deposit_name.setPadding(30, 30, 30, 30);
           // deposit_name.setGravity(Gravity.CENTER);
            tr.addView(deposit_name);

            deposit_type = new TextView(getActivity());
            deposit_type.setText(deposit.getType());
            deposit_type.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            deposit_type.setTextColor(getResources().getColor(R.color.colorAccent));
            deposit_type.setPadding(30, 30, 30, 30);
            deposit_type.setGravity(Gravity.CENTER);
            tr.addView(deposit_type);

            amt = new TextView(getActivity());
            amt.setText(String.valueOf(deposit.getAmount()));
            amt.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            amt.setTextColor(getResources().getColor(R.color.colorAccent));
            amt.setPadding(30, 30, 30, 30);
            amt.setGravity(Gravity.CENTER);
            tr.addView(amt);

            receipt_no = new TextView(getActivity());
            receipt_no.setText(String.valueOf(deposit.getReceiptNo()));
            receipt_no.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            receipt_no.setTextColor(getResources().getColor(R.color.colorAccent));
            receipt_no.setPadding(30, 30, 30, 30);
            receipt_no.setGravity(Gravity.CENTER);
            tr.addView(receipt_no);

            receipt_date = new TextView(getActivity());
            receipt_date.setText(deposit.getReceiptDate());
            receipt_date.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            receipt_date.setTextColor(getResources().getColor(R.color.colorAccent));
            receipt_date.setPadding(30, 30, 30, 30);
           // receipt_date.setGravity(Gravity.CENTER);
            tr.addView(receipt_date);

            pay_mode = new TextView(getActivity());
            pay_mode.setText(deposit.getPaymentMode());
            pay_mode.setTextSize(getResources().getDimension(R.dimen.table_text_view_font_size));
            pay_mode.setTextColor(getResources().getColor(R.color.colorAccent));
            pay_mode.setPadding(30, 30, 30, 30);
            pay_mode.setGravity(Gravity.CENTER);
            tr.addView(pay_mode);

            t2.addView(tr);
        }
    }

    private List<FeeInstallment> getInstallments() {
        List<FeeInstallment> installments = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            FeeInstallment installment = new FeeInstallment();
            installment.setName(i + " INSTALLMENT");
            installment.setType("REGULAR");
            installment.setTotal(1200.00);
            installment.setDeposit(1000.00);
            installment.setDiscount(0.00);
            installment.setBalance(0.00);
            installments.add(installment);
        }
        return installments;
    }

    private void fetchInstallmentData() {
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
                            if (response.has(tags.ARR_FEES_DETAILS)) {
                                JSONArray result = response.getJSONArray(tags.ARR_FEES_DETAILS);
                                List<FeeInstallment> installments = new ArrayList<>();
                                for (int i = 0; i < result.length(); i++) {
                                    FeeInstallment installment = new FeeInstallment();
                                    JSONObject object = result.getJSONObject(i);
                                    if (object.has(tags.FEES_ID)) {

                                    }
                                    if (object.has(tags.FEES_NAME)) {
                                        installment.setName(object.getString(tags.FEES_NAME));
                                    }
                                    if (object.has(tags.FEES_TYPE)) {
                                        installment.setType(object.getString(tags.FEES_TYPE));
                                    }
                                    if (object.has(tags.FEES_TOTAL)) {
                                        installment.setTotal(object.getDouble(tags.FEES_TOTAL));
                                    }
                                    if (object.has(tags.FEES_DEPOSIT)) {
                                        installment.setDeposit(object.getDouble(tags.FEES_DEPOSIT));
                                    }
                                    if (object.has(tags.FEES_DISCOUNT)) {
                                        installment.setDiscount(object.getDouble(tags.FEES_DISCOUNT));
                                    }
                                    if (object.has(tags.FEES_BALANCE)) {
                                        installment.setBalance(object.getDouble(tags.FEES_BALANCE));
                                    }
                                    installments.add(installment);
                                }
                                setDataInstallment(installments);
                                dbHelper.deleteInstallments();
                                dbHelper.setFeesInstallment(installments);
                            }
                            if (response.has(tags.ARR_FEES_DETAILS_DEPOSIT)) {
                                JSONArray result = response.getJSONArray(tags.ARR_FEES_DETAILS_DEPOSIT);
                                List<DepositInstallment> installments = new ArrayList<>();
                                for (int i = 0; i < result.length(); i++) {
                                    DepositInstallment installment = new DepositInstallment();
                                    JSONObject object = result.getJSONObject(i);
                                    if (object.has(tags.FEES_ID)) {

                                    }
                                    if (object.has(tags.FEES_NAME)) {
                                        installment.setName(object.getString(tags.FEES_NAME));
                                    }
                                    if (object.has(tags.FEES_TYPE)) {
                                        installment.setType(object.getString(tags.FEES_TYPE));
                                    }
                                    if (object.has(tags.FEES_AMOUNT)) {
                                        installment.setAmount(object.getDouble(tags.FEES_AMOUNT));
                                    }
                                    if (object.has(tags.FEES_RECEIPT_NO)) {
                                        installment.setReceiptNo(object.getInt(tags.FEES_RECEIPT_NO));
                                    }
                                    if (object.has(tags.FEES_PAY_DATE)) {
                                        installment.setReceiptDate(object.getString(tags.FEES_PAY_DATE));
                                    }
                                    if (object.has(tags.FEES_MODE)) {
                                        installment.setPaymentMode(object.getString(tags.FEES_MODE));
                                    }
                                    installments.add(installment);
                                }
                                setDataDepositInstallment(installments);
                                dbHelper.deleteDepositInstallments();
                                dbHelper.setDepositInstallments(installments);
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
