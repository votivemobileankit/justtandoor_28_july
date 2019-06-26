package com.justtannoor.justtannoor.Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.view.inputmethod.InputMethodManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by votive on 13/04/18.
 */

public class Validation {


    private static final String TODO = "" ;

    ///////////////This is for Device Height and Width
    public static int getDeviceHeightWidth(Context context, boolean isWidth) {

        if (isWidth)
            return context.getResources().getDisplayMetrics().widthPixels;
        else
            return context.getResources().getDisplayMetrics().heightPixels;

    }


    // validating email id
    public static boolean isValidEmail(String email) {

        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password with retype password
    public static boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6 && pass.length() < 14) {
            return true;
        }
        return false;
    }

    public static boolean isNullValue(String stsText) {
        if (stsText != null && !stsText.equalsIgnoreCase("")) {
            return true;
        }
        return false;
    }

    public static void shareApp(Context context) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
       /* sendIntent.putExtra(Intent.EXTRA_TEXT,
              "להורדת האפליקציה מיכל ממן , לחצו על הקישור https://play.google.com/store/apps/details?id=com.fitnessapp.mind&hl=en");
       */sendIntent.putExtra(Intent.EXTRA_TEXT,
                "להורדת האפליקציה מיכל ממן , לחצו על הקישור https://play.google.com/store/apps/details?id=com.fitnessapp.customerapp");
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }


    /**
     * Method use to hide keyboard.
     *
     * @param ctx context of current activity.
     */
    public static void hideKeyboard(Activity ctx) {
        if (ctx.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) ctx.getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(ctx.getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Method use to show keyboard on current screen.
     *
     * @param ctx context of current activity.
     */
    public final void showKeyboard(Activity ctx) {
        if (ctx.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) ctx.getSystemService(INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    /**
     * Method use to check whether user is online or not.
     *
     * @param context context of current activity.
     * @return true if user is online else returns false.
     */
    public final boolean isOnline(Context context) {

        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected())
            return true;
        return false;
    }



}
