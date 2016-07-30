package com.b2infosoft.paathshala.app;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by rajesh on 7/17/2016.
 */
public class Fonts {
    private static final Fonts FONTS = new Fonts();

    public static Fonts getInstance() {
        return FONTS;
    }

    private Fonts() {

    }

    public final String ROBOTO_BLACK = "Roboto_Black.ttf";
    public final String ROBOTO_BLACKITALIC = "Roboto_BlackItalic.ttf";
    public final String ROBOTO_BOlD = "Roboto_Bold.ttf";
    public final String ROBOTO_BOlDITALIC = "Roboto_BoldItalic.ttf";
    public final String ROBOTO_ITALIC = "Roboto_Italic.ttf";
    public final String ROBOTO_LIGHT = "Roboto_Light.ttf";
    public final String ROBOTO_LIGHTITALIC = "Roboto_LightItalic.ttf";
    public final String ROBOTO_MEDIUM = "Roboto_Medium.ttf";
    public final String ROBOTO_MEDIUMITALIC = "Roboto_MediumItalic.ttf";
    public final String ROBOTO_REGULAR = "Roboto_Regular.ttf";
    public final String ROBOTO_THIN = "Roboto_Thin.ttf";
    public final String ROBOTOCONDENSED_BOLD_1 = "RobotoCondensed_Bold_1.ttf";
    public final String ROBOTOCONDENSED_BOLDITALIC_1 = "RobotoCondensed_BoldItalic_1.ttf";
    public final String ROBOTOCONDENSED_ITALIC_1 = "RobotoCondensed_Italic_1.ttf";
    public final String ROBOTOCONDENSED_LIGHT_1 = "RobotoCondensed_Light_1.ttf";
    public final String ROBOTOCONDENSED_LIGHTITALIC_1 = "RobotoCondensed_LightItalic_1.ttf";
    public final String ROBOTOCONDENSED_REGULAR_1 = "RobotoCondensed_Regular_1.ttf";

    public Typeface getFont(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(),"fonts/"+font);
    }
}
