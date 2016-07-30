package com.b2infosoft.paathshala.Custom;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by rajesh on 7/17/2016.
 */

public class MyProgressDialog extends ProgressDialog {
    public MyProgressDialog(Context context) {
        super(context);
        super.setCancelable(false);
        super.setIndeterminate(true);
        super.setMessage("Please Wait...");
    }
    public MyProgressDialog(Context context, int theme) {
        super(context, theme);
        super.setCancelable(false);
        super.setIndeterminate(true);
        super.setMessage("Please Wait...");
    }
}
