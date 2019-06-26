package com.justtannoor.justtannoor.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class TextViewHead extends android.support.v7.widget.AppCompatTextView {

    public TextViewHead(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextViewHead(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewHead(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans-Regular.ttf");

            setTypeface(tf);
        }
    }

}
