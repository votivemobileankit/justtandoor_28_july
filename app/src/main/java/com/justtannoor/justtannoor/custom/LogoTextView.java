package com.justtannoor.justtannoor.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class LogoTextView extends android.support.v7.widget.AppCompatTextView {

    public LogoTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public LogoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LogoTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Logo_font_text.ttf");

            setTypeface(tf);
        }
    }

}




