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

    public final String ROBOTO_BLACK = "fonts/Roboto-Black.ttf";
    public final String ROBOTO_BLACKITALIC = "fonts/Roboto-BlackItalic.ttf";
    public final String ROBOTO_BOlD = "fonts/Roboto-Bold.ttf";
    public final String ROBOTO_BOlDITALIC = "fonts/Roboto-BoldItalic.ttf";
    public final String ROBOTO_ITALIC = "fonts/Roboto-Italic.ttf";
    public final String ROBOTO_LIGHT = "fonts/Roboto-Light.ttf";
    public final String ROBOTO_LIGHTITALIC = "fonts/Roboto-LightItalic.ttf";
    public final String ROBOTO_MEDIUM = "fonts/Roboto-Medium.ttf";
    public final String ROBOTO_MEDIUMITALIC = "fonts/Roboto-MediumItalic.ttf";
    public final String ROBOTO_REGULAR = "fonts/Roboto-Regular.ttf";
    public final String ROBOTO_THIN = "fonts/Roboto-Thin.ttf";
    public final String ROBOTOCONDENSED_BOLD_1 = "fonts/RobotoCondensed-Bold_1.ttf";
    public final String ROBOTOCONDENSED_BOLDITALIC_1 = "fonts/RobotoCondensed-BoldItalic_1.ttf";
    public final String ROBOTOCONDENSED_ITALIC_1 = "fonts/RobotoCondensed-Italic_1.ttf";
    public final String ROBOTOCONDENSED_LIGHT_1 = "fonts/RobotoCondensed-Light_1.ttf";
    public final String ROBOTOCONDENSED_LIGHTITALIC_1 = "fonts/RobotoCondensed-LightItalic_1.ttf";
    public final String ROBOTOCONDENSED_REGULAR_1 = "fonts/RobotoCondensed-Regular_1.ttf";

    public Typeface getFont(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }
}
