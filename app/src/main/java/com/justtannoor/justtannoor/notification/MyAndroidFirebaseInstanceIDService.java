package com.justtannoor.justtannoor.notification;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.justtannoor.justtannoor.Utilities.SharedPreferencesUtil;


public class MyAndroidFirebaseInstanceIDService extends FirebaseInstanceIdService
{

    @Override
    public void onTokenRefresh()
    {
        // get hold of the registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        SharedPreferencesUtil.putPref(this,"device_token",refreshedToken);

        //Log the token
        Log.d("Device Token:", "onTokenRefresh: " +refreshedToken);
    }

}