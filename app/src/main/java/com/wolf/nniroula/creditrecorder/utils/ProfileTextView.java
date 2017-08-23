package com.wolf.nniroula.creditrecorder.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by root on 7/22/17.
 */

public class ProfileTextView extends TextView {

    public ProfileTextView(Context context) {
        super(context);
        init();
    }

    public ProfileTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    public ProfileTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Neon.ttf");
            setTypeface(tf);
        }
    }

}
