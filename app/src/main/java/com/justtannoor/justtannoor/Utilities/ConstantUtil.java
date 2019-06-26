package com.justtannoor.justtannoor.Utilities;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ADMIN on 4/5/2018.
 */

public class ConstantUtil {

    private static final String TAG = "ConstantUtil";

    public static final String mypreference = "mypref";
    public static final String Name = "nameKey";
    public static final String Email = "emailKey";


    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }
    }

    public static String getDeviceId(Context mContext){

        String device_id = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);

        return device_id;
    }

    public static byte[] readBytesFromFile(String aFilePath) {

        FileInputStream fio = null;
        byte[] bytesArray = null;

        try {

            File file = new File(aFilePath);
            bytesArray = new byte[(int) file.length()];

            //read file into bytes[]
            fio = new FileInputStream(file);
            fio.read(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fio != null) {
                try {
                    fio.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytesArray;
    }

}
