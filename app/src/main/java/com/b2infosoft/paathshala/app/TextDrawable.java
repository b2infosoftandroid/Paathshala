package com.b2infosoft.paathshala.app;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.b2infosoft.paathshala.R;

/**
 * Created by rajesh on 7/21/2016.
 */

public class TextDrawable {
    public static Drawable getDrawable(Context context, String string) {
        Fonts fonts = Fonts.getInstance();
        com.amulyakhare.textdrawable.TextDrawable drawable = com.amulyakhare.textdrawable.TextDrawable.builder()
                .beginConfig()
                .withBorder(2)
                .fontSize(30)
                .textColor(context.getResources().getColor(R.color.button_foreground))
                .endConfig()
                .buildRoundRect(string, context.getResources().getColor(R.color.colorAccent), 100);
//                .useFont(fonts.getFont(context, fonts.ROBOTO_MEDIUM))

        return drawable;
    }
}
