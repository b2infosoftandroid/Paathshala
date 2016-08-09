package com.b2infosoft.paathshala.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.app.Fonts;
import com.b2infosoft.paathshala.app.Tags;
import com.b2infosoft.paathshala.credential.Active;
import com.b2infosoft.paathshala.database.DBHelper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Dashboard.OnAdmissionListener} interface
 * to handle interaction events.
 * Use the {@link Dashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dashboard extends Fragment {
    DisplayMetrics device;
    LinearLayout linearLayoutClassmates, linearLayoutAttendance, linearLayoutEnquiry, linearLayoutResult;
    Fonts fonts = Fonts.getInstance();
    TextView textView;
    Active active;
    Tags tags;
    DBHelper dbHelper;
    TextView lastUpdate;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnAdmissionListener mListener;

    public Dashboard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Dashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static Dashboard newInstance(String param1, String param2) {
        Dashboard fragment = new Dashboard();
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
        View view = inflater.inflate(R.layout.fragment_admission, container, false);
        device = getActivity().getResources().getDisplayMetrics();
        textView = (TextView) view.findViewById(R.id.institute_name);
        active = Active.getInstance(getActivity());
        tags = Tags.getInstance();
        dbHelper = new DBHelper(getActivity());
        lastUpdate = (TextView) view.findViewById(R.id.last_update);

        String school_name = dbHelper.getInstituteName(active.getValue(tags.SCHOOL_ID));
        if (school_name != null) {
            if (school_name.contains(","))
                textView.setText(school_name.substring(0, school_name.indexOf(",")).toUpperCase());
        }
        //TranslateAnimation slide = new TranslateAnimation(600.0f, -600.0f, 0.0f, 0.0f);
        //slide.setDuration(7000);
        //slide.setRepeatCount(Animation.INFINITE);
        //slide.setRepeatMode(Animation.RESTART);
        //textView.startAnimation(slide);

        linearLayoutClassmates = (LinearLayout) view.findViewById(R.id.layout_classmates);
        linearLayoutAttendance = (LinearLayout) view.findViewById(R.id.layout_attendance);
        linearLayoutEnquiry = (LinearLayout) view.findViewById(R.id.layout_holidays);
        linearLayoutResult = (LinearLayout) view.findViewById(R.id.layout_fees);
        setLayoutParams(linearLayoutClassmates);
        setLayoutParams(linearLayoutAttendance);
        setLayoutParams(linearLayoutEnquiry);
        setLayoutParams(linearLayoutResult);

        linearLayoutClassmates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Fees());
            }
        });
        linearLayoutAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new MarkSheet());
            }
        });
        linearLayoutEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Attendance());
            }
        });
        linearLayoutResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Complaint());
            }
        });

        ImageView classmate = (ImageView) view.findViewById(R.id.dashboard_classmates);
        ImageView attendance = (ImageView) view.findViewById(R.id.dashboard_attendance);
        ImageView holidays = (ImageView) view.findViewById(R.id.dashboard_holidays);
        ImageView fees = (ImageView) view.findViewById(R.id.dashboard_fees);
        // classmate.setImageDrawable(getDrawable("20"));
        // attendance.setImageDrawable(getDrawable("70%"));
        // holidays.setImageDrawable(getDrawable("10"));
        // fees.setImageDrawable(getDrawable("100"));
        refreshLastUpdate();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshLastUpdate();
    }

    private void refreshLastUpdate() {
        String last_update = getActivity().getResources().getString(R.string.data_last_updated);
        lastUpdate.setText(last_update.concat(active.getValue(tags.LAST_UPDATE)));
    }

    private void setLayoutParams(LinearLayout layout) {
        layout.getLayoutParams().width = (int) (device.widthPixels / 2.3);
    }

    private Drawable getDrawable(String string) {
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .withBorder(10)
                .fontSize(getFontSize(device.widthPixels, device.heightPixels))
                .textColor(getResources().getColor(R.color.button_foreground))
                .endConfig()
                .buildRoundRect(string, getResources().getColor(R.color.layout_top), 100);
//        .useFont(Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto_Medium.ttf"))
        return drawable;
    }

    private int getFontSize(int w, int h) {
        if (w <= 320 && h <= 480) {
            return 25;
        } else {
            return 40;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onAdmissionInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAdmissionListener) {
            mListener = (OnAdmissionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAdmissionListener");
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
    public interface OnAdmissionListener {
        // TODO: Update argument type and name
        void onAdmissionInteraction(Uri uri);
    }

    public void replaceFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_content, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
