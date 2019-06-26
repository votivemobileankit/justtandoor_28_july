package com.justtannoor.justtannoor.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.justtannoor.justtannoor.R;
import com.justtannoor.justtannoor.Utilities.Constant;
import com.justtannoor.justtannoor.Utilities.SharedPreferencesUtil;
import com.justtannoor.justtannoor.custom.TextViewHead;
import com.justtannoor.justtannoor.model.CaseOnDeliveryModel.CaseODlvrModel;
import com.justtannoor.justtannoor.model.CaseOnDeliveryModel.Response;
import com.justtannoor.justtannoor.retrofit.BaseRequest;
import com.justtannoor.justtannoor.retrofit.RequestReciever;

import java.util.ArrayList;
import java.util.List;

public class CaseOnDeliveyActivity extends AppCompatActivity {

    private Context mContext = null;
    private BaseRequest baseRequest = null;
    private TextViewHead edtInfoIfscCode, edtInfoAccountHolderName, edtInfoAccountNumber, edtInfoOrderID, edtInfoBankname;
    private TextViewHead tvInfoCompleteORD;
    private String order_id;
    private Button btnProceed;
    private Button btnGoToHome;
    private Button btnCompletedOrder;
    private Response mResponse;
    private String mCaseOrderStatus;

    private static final int STORAGE_PERMISSION_CODE = 123;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private String adminNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_on_delivey);
        mContext = this;
        initview();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Constant.CASE_COME_STATS_HOME = 0;
        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void initview() {

        baseRequest = new BaseRequest(this);

        getSupportActionBar().setSubtitle("الدفع عن الاستلام");

        tvInfoCompleteORD = findViewById(R.id.tvInfoCompleteORD);

        edtInfoAccountHolderName = findViewById(R.id.edtInfoAccountHolderName);
        edtInfoAccountNumber = findViewById(R.id.edtInfoAccountNumber);
        edtInfoIfscCode = findViewById(R.id.edtInfoIfscCode);
        edtInfoBankname = findViewById(R.id.edtInfoBankname);
        edtInfoOrderID = findViewById(R.id.edtInfoOrderID);

        try {
            mCaseOrderStatus = SharedPreferencesUtil.getData(mContext, Constant.PLACE_ORDER_STATUS_FOR_CASE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnProceed = findViewById(R.id.btnProceed);
        btnGoToHome = findViewById(R.id.btnGoToHome);
        btnCompletedOrder = findViewById(R.id.btnCompletedOrder);
        btnCompletedOrder.setVisibility(View.GONE);
        /*if (Constant.CASE_COME_STATS_HOME == 0) {
            btnCompletedOrder.setVisibility(View.GONE);
            tvInfoCompleteORD.setVisibility(View.GONE);
        } else {
            btnCompletedOrder.setVisibility(View.VISIBLE);
            tvInfoCompleteORD.setVisibility(View.VISIBLE);
        }*/

        if (getIntent().getStringExtra("order_id") != null) {

            order_id = getIntent().getStringExtra("order_id");
        }

        getCaseOnDeliveryInfoAPI();

        btnGoToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Constant.CASE_COME_STATS_HOME = 0;
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    adminNumber = mResponse.getAdminMobileNumber();
//                    adminNumber = "+919827445708";
                } catch (Exception e) {
                    // adminNumber = mResponse.getAdminMobileNumber().replace("+","");
                    e.printStackTrace();
                }
            }
        });

        btnCompletedOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferencesUtil.setData(mContext, Constant.PLACE_ORDER_STATUS_FOR_CASE, "casefalse");
                SharedPreferencesUtil.setData(mContext, Constant.PLACE_ORDER_ID_FOR_CASE, "");
                SharedPreferencesUtil.setData(mContext, Constant.PLACE_ORDER_CONTACT_EMAIL_CASE, "");
                SharedPreferencesUtil.setData(mContext, Constant.PLACE_ORDER_CONTACT_NUMBER_CASE, "");
                SharedPreferencesUtil.setData(mContext, Constant.PLACE_ORDER_IMPORTENT_INFO_CASE, "");
                Constant.CASE_COME_STATS_HOME = 0;
                finish();
            }
        });

    }

    ////////////////////////////////////////////////
    private void getCaseOnDeliveryInfoAPI() {
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {

                try {
                    Gson gson = new Gson();
                    //////////////add model class here
                    CaseODlvrModel mCaseODlvrModel = gson.fromJson(Json, CaseODlvrModel.class);
                    // edtInfoIfscCode.setText(getBankDetailModal.getResponse().getIfscCode());
                    edtInfoIfscCode.setText(mCaseODlvrModel.getResponse().getAdminMobileNumber());
                    edtInfoBankname.setText(mCaseODlvrModel.getResponse().getAdminMessage());/////////////44444444
                    edtInfoAccountHolderName.setText(mCaseODlvrModel.getResponse().getAdminEmailAddress());/////////2222
                    edtInfoAccountNumber.setText(mCaseODlvrModel.getResponse().getAdminMobileNumber());///3333
                    edtInfoOrderID.setText(order_id);

                    mResponse = mCaseODlvrModel.getResponse();
                    SharedPreferencesUtil.setData(mContext, Constant.PLACE_ORDER_STATUS_FOR_CASE, "casetrue");
                    SharedPreferencesUtil.setData(mContext, Constant.PLACE_ORDER_ID_FOR_CASE, order_id);
                    SharedPreferencesUtil.setData(mContext, Constant.PLACE_ORDER_CONTACT_EMAIL_CASE, mCaseODlvrModel.getResponse().getAdminEmailAddress());
                    SharedPreferencesUtil.setData(mContext, Constant.PLACE_ORDER_CONTACT_NUMBER_CASE, mCaseODlvrModel.getResponse().getAdminMobileNumber());
                    SharedPreferencesUtil.setData(mContext, Constant.PLACE_ORDER_IMPORTENT_INFO_CASE, mCaseODlvrModel.getResponse().getAdminMessage());
                    //SharedPreferencesUtil.setData(mContext, Constant.PLACE_BANK_NAME, mLoginResponse.getLastName());
                    //SharedPreferencesUtil.setData(mContext, Constant.PLACE_BANK_IFSC, getBankDetailModal.getResponse().getIban());
                    // SharedPreferencesUtil.setData(mContext, Constant.PLACE_BANK_NAME, getBankDetailModal.getResponse().getBankName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {

                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNetworkFailure(int APINumber, String message) {
                Toast.makeText(mContext, "Please check internet connection", Toast.LENGTH_LONG).show();

            }
        });

        JsonObject jsonObject = new JsonObject();
        try {
            ////Put input parameter here

        } catch (Exception e) {
            e.printStackTrace();
        }
        baseRequest.callAPIPost(1, jsonObject, Constant.GET_CASE_ON_DELIVER_DETAIL);/////
    }


    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    //////////////////////////////////////////////

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
            if (grantResults.length > 0 && grantResults[3] == PackageManager.PERMISSION_GRANTED) {
//Displaying a toast
                Toast.makeText(this, "Permission granted now you can able to call", Toast.LENGTH_LONG).show();
            } else {
//Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
            if (grantResults.length > 0 && grantResults[4] == PackageManager.PERMISSION_GRANTED) {
//Displaying a toast
                Toast.makeText(this, "Permission granted now you can able to send sms", Toast.LENGTH_LONG).show();
            } else {
//Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

}
