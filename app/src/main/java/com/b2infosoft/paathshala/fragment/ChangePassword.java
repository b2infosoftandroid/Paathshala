package com.b2infosoft.paathshala.fragment;

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
 * {@link ChangePassword.OnChangePasswordListener} interface
 * to handle interaction events.
 * Use the {@link ChangePassword#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePassword extends Fragment {
    EditText password_old,password_new,password_confirm_new;
    Button update_password;
    Fonts fonts = Fonts.getInstance();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnChangePasswordListener mListener;

    public ChangePassword() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangePassword.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePassword newInstance(String param1, String param2) {
        ChangePassword fragment = new ChangePassword();
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
        View  view = inflater.inflate(R.layout.fragment_change_password, container, false);
        password_old=(EditText)view.findViewById(R.id.change_old_password);
        password_new=(EditText)view.findViewById(R.id.change_new_password);
        password_confirm_new=(EditText)view.findViewById(R.id.change_confirm_new_password);
        update_password=(Button)view.findViewById(R.id.change_password_button);
        update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
      //  setFonts();
        return  view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onChangePasswordInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnChangePasswordListener) {
            mListener = (OnChangePasswordListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnChangePasswordListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnChangePasswordListener {
        // TODO: Update argument type and name
        void onChangePasswordInteraction(Uri uri);
    }

    private void setFonts(){
        password_old.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));
        password_new.setTypeface(fonts.getFont(getContext(),fonts.ROBOTO_REGULAR));
        password_confirm_new.setTypeface(fonts.getFont(getContext(),fonts.ROBOTO_REGULAR));
        update_password.setTypeface(fonts.getFont(getContext(),fonts.ROBOTO_MEDIUM));
    }

}
