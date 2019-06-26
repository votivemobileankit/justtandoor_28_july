package com.justtannoor.justtannoor.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.justtannoor.justtannoor.R;
import com.justtannoor.justtannoor.Utilities.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends Activity {

    private static int SPLASH_TIME_OUT = 3000;
    private static final int STORAGE_PERMISSION_CODE = 123;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private TextView mTextViewEnglish, mTextViewArabic, next;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


      /*  mTextViewEnglish = (TextView)findViewById(R.id.english);
      /*  mTextViewEnglish = (TextView)findViewById(R.id.english);*/
        mImageView = (ImageView) findViewById(R.id.logoimage);

        //Requesting storage permission
       // checkAndRequestPermissions();


        ConstantUtil.printHashKey(this);

       /* Animation zoomOutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in_to_out_animation);
        mImageView.startAnimation(zoomOutAnimation);*/


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                /*if (SharedPreferencesUtil.getInstance(SplashScreen.this).getData(UtilsConstants.ISLOGIN).equals("1")) {
                    Intent i = new Intent(SplashScreen.this, Home.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(SplashScreen.this, SignIn.class);
                    startActivity(i);
                   // overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
                    finish();
                }*/

               // Intent i = new Intent(SplashScreen.this, PreMainActivity.class);
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_left );
                finish();

               /* Animation englishAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
                 englishAnimation.setDuration(2000);
                mTextViewEnglish.startAnimation(englishAnimation);

                Animation arabicAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_left);
                arabicAnimation.setDuration(2000);
                mTextViewArabic.startAnimation(arabicAnimation);

                mTextViewEnglish.setVisibility(View.VISIBLE);
                mTextViewArabic.setVisibility(View.VISIBLE);*/
/*

                ArrayAdapter adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.array_countries, R.layout.spinner_item);
                mSpinner.setAdapter(adapter);

                ArrayAdapter adapter1 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.array_langague, R.layout.spinner_item);
                mSpinner1.setAdapter(adapter1);
*/


            }
        }, SPLASH_TIME_OUT);


       /* next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(SplashScreen.this, SignUpActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                finish();

            }
        });*/

/*
        mTextViewArabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(SplashScreen.this, SignUpActivity.class);
                startActivity(i);
                overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_left );
                finish();

            }
        });
*/

    }

    private boolean checkAndRequestPermissions() {

        int SD_CARD = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int CAMERA = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int LOCATION = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (SD_CARD != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (CAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (LOCATION != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == STORAGE_PERMISSION_CODE) {

//If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
//Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }

            if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//Displaying a toast
                Toast.makeText(this, "Permission granted now you can use camera", Toast.LENGTH_LONG).show();
            } else {
//Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }

            if (grantResults.length > 0 && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
//Displaying a toast
                Toast.makeText(this, "Permission granted now you can use user location", Toast.LENGTH_LONG).show();
            } else {
//Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

}

