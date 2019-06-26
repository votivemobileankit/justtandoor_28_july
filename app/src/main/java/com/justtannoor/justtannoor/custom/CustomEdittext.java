package com.justtannoor.justtannoor.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;


public class CustomEdittext extends AppCompatEditText {

    public CustomEdittext(Context context) {
        super(context);
        setFont();
    }

    public CustomEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public CustomEdittext(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans-Regular.ttf");
        setTypeface(font, Typeface.NORMAL);
    }
}

