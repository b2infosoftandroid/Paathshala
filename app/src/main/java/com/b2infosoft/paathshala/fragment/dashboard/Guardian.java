package com.b2infosoft.paathshala.fragment.dashboard;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.app.Fonts;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Guardian.OnGuardianListener} interface
 * to handle interaction events.
 * Use the {@link Guardian#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Guardian extends Fragment {

    EditText name,email,phone,mobile,remark,address;
    Button save;
    Fonts fonts = Fonts.getInstance();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnGuardianListener mListener;

    public Guardian() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Guardian.
     */
    // TODO: Rename and change types and number of parameters
    public static Guardian newInstance(String param1, String param2) {
        Guardian fragment = new Guardian();
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
        View view =  inflater.inflate(R.layout.fragment_guardian, container, false);
        name=(EditText)view.findViewById(R.id.guardian_name);
        email=(EditText)view.findViewById(R.id.guardian_email);
        phone=(EditText)view.findViewById(R.id.guardian_phone_no);
        mobile=(EditText)view.findViewById(R.id.guardian_mobile_no);
        remark=(EditText)view.findViewById(R.id.guardian_remark);
        address=(EditText)view.findViewById(R.id.guardian_address);
        save=(Button)view.findViewById(R.id.guardian_info_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        setFonts();
        return  view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onGuardianInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGuardianListener) {
            mListener = (OnGuardianListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
    public interface OnGuardianListener {
        // TODO: Update argument type and name
        void onGuardianInteraction(Uri uri);
    }
    private void setFonts(){
        name.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));
        email.setTypeface(fonts.getFont(getContext(),fonts.ROBOTO_REGULAR));
        phone.setTypeface(fonts.getFont(getContext(),fonts.ROBOTO_REGULAR));
        mobile.setTypeface(fonts.getFont(getContext(),fonts.ROBOTO_REGULAR));
        remark.setTypeface(fonts.getFont(getContext(),fonts.ROBOTO_REGULAR));
        address.setTypeface(fonts.getFont(getContext(),fonts.ROBOTO_REGULAR));
        save.setTypeface(fonts.getFont(getContext(),fonts.ROBOTO_MEDIUM));
    }
}
