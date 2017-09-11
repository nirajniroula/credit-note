package com.wolf.nniroula.creditrecorder.utils;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.Display;

import com.github.johnpersano.supertoasts.SuperToast;
import com.wolf.nniroula.creditrecorder.R;
import com.wolf.nniroula.creditrecorder.activity.CreditApplication;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Niraj Niroula on 8/24/17.
 */

public class CreditUtil {
    public static Typeface typefaceLatoRegular = null;
    public static Typeface typefaceLatoHairline = null;
    public static Typeface typefaceLatoLight = null;

    public static Typeface typefaceBig = null;
    public static Typeface typefaceComfortaRegular = null;

    public static SuperToast.Animations TOAST_ANIMATION = SuperToast.Animations.POPUP;


    public static int[] DRAWER_TOP_IMAGES = {
            R.drawable.material_design_0,
            R.drawable.material_design_1,
            R.drawable.material_design_2,
            R.drawable.material_design_3,
            R.drawable.material_design_4
    };

    public static int[] DRAWER_TOP_TIPS = {
            R.string.tip_text_0,
            R.string.tip_text_1,
            R.string.tip_text_2,
            R.string.tip_text_3,
            R.string.tip_text_4
    };


    public static HashMap<String, Integer> GetDrawerTopImages() {
        HashMap<String, Integer> drawerTopImages = new HashMap<>();
        drawerTopImages.put("0", DRAWER_TOP_IMAGES[0]);
        drawerTopImages.put("1", DRAWER_TOP_IMAGES[1]);
        drawerTopImages.put("2", DRAWER_TOP_IMAGES[2]);
        drawerTopImages.put("3", DRAWER_TOP_IMAGES[3]);
        drawerTopImages.put("4", DRAWER_TOP_IMAGES[4]);
        return drawerTopImages;
    }

    public static void init(Context context) {

        typefaceLatoRegular = Typeface.createFromAsset(
                context.getAssets(), "Lato-Regular.ttf");
        typefaceLatoHairline = Typeface.createFromAsset(
                context.getAssets(), "Lato-Hairline.ttf");
        typefaceLatoLight = Typeface.createFromAsset(
                context.getAssets(), "LatoLatin-Light.ttf");
        typefaceBig = Typeface.createFromAsset(
                context.getAssets(), "BIG.otf");
        typefaceComfortaRegular = Typeface.createFromAsset(
                context.getAssets(), "Comfortaa-Regular.ttf");
    }

    public static Typeface getTypeface() {
        if (typefaceLatoLight == null) init(CreditApplication.getAppContext());
        if ("en".equals(Locale.getDefault().getLanguage()))
            return typefaceLatoLight;
        if ("zh".equals(Locale.getDefault().getLanguage()))
            return Typeface.DEFAULT;
        return typefaceLatoLight;
    }

    private static CreditUtil ourInstance = new CreditUtil();

    public static CreditUtil getInstance() {
        if (ourInstance == null || typefaceLatoLight == null || typefaceLatoHairline == null) {
            ourInstance = new CreditUtil();
            init(CreditApplication.getAppContext());
        }
        return ourInstance;
    }

    public static String getCurrentVersion() {
        return "Version " + CreditApplication.VERSION / 100 + "." + CreditApplication.VERSION % 100 / 10 + "." + CreditApplication.VERSION % 10;
    }

    public static int changeDpToPx(int dp) {
        DisplayMetrics displayMetrics = CreditApplication.getAppContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static int getScreenWidth(Context context) {
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = CreditApplication.getAppContext()
                .getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = CreditApplication.getAppContext()
                    .getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getTagColor(String tagName) {
        int colors[] = new int[]{Color.RED,
                Color.BLUE, Color.DKGRAY, Color.MAGENTA,
                Color.CYAN, Color.BLACK, Color.argb(255, 125, 0, 255),
                Color.argb(255, 255, 125, 0), Color.argb(255, 102, 51, 0),
                Color.argb(255, 255, 0, 127)};
        return colors[Math.abs(tagName.hashCode()) % colors.length];
    }

    public static String getFirstLetter(String tagName) {
        return tagName.charAt(0) + "";
    }

    private static String lastToast = "";

    public static void showToast(Context context, String text) {
        if (context == null) return;
        if (lastToast.equals(text)) {
            SuperToast.cancelAllSuperToasts();
        } else {
            lastToast = text;
        }
        SuperToast superToast = new SuperToast(context);
        superToast.setAnimations(SuperToast.Animations.FLYIN);
        superToast.setDuration(SuperToast.Duration.VERY_SHORT);
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setTextSize(SuperToast.TextSize.SMALL);
        superToast.setText(text);
        superToast.setBackground(SuperToast.Background.BLUE);
        superToast.show();
    }

    public static void copyToClipboard(String content, Context context) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    private CreditUtil() {
    }
}
