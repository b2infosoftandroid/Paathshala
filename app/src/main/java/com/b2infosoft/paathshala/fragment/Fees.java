package com.b2infosoft.paathshala.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.app.Fonts;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fees.OnFeesListener} interface
 * to handle interaction events.
 * Use the {@link Fees#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fees extends Fragment {

    HorizontalScrollView s1, s2;
    TextView name, type, total_fee, deposit, discount, balance, deposit_name, deposit_type, amount, receipt_no, receipt_date, payment_type;
    Fonts fonts = Fonts.getInstance();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFeesListener mListener;

    public Fees() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fees.
     */
    // TODO: Rename and change types and number of parameters
    public static Fees newInstance(String param1, String param2) {
        Fees fragment = new Fees();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fees, container, false);
        name = (TextView) view.findViewById(R.id.stu_fee_name);
        type = (TextView) view.findViewById(R.id.stu_fee_type);
        total_fee = (TextView) view.findViewById(R.id.stu_total_fee);
        deposit = (TextView) view.findViewById(R.id.stu_deposit);
        discount = (TextView) view.findViewById(R.id.stu_tdiscount);
        balance = (TextView) view.findViewById(R.id.stu_balance);

        deposit_name = (TextView) view.findViewById(R.id.deposit_fee_name);
        deposit_type = (TextView) view.findViewById(R.id.deposit_fee_type);
        amount = (TextView) view.findViewById(R.id.deposit_amount);
        receipt_no = (TextView) view.findViewById(R.id.deposit_receipt_no);
        receipt_date = (TextView) view.findViewById(R.id.deposit_receipt_date);
        payment_type = (TextView) view.findViewById(R.id.payment_mode);
/*
        s1 = (HorizontalScrollView) view.findViewById(R.id.stu_scroll);
        s2 = (HorizontalScrollView) view.findViewById(R.id.deposit_scroll);
        s1.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
        s2.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
*/
        setFonts();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFeesListener {
        // TODO: Update argument type and name
        void onFeesInteraction(Uri uri);
    }

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
}
