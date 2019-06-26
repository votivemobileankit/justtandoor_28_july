package com.justtannoor.justtannoor.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by poonam on 6/1/2018.
 */

public class CustomTextHome extends android.support.v7.widget.AppCompatTextView {

    public CustomTextHome(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTextHome(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextHome(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/SCRIPTBL.TTF");

            setTypeface(tf);
        }
    }

}