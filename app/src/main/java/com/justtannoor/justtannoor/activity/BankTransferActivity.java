package com.justtannoor.justtannoor.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.justtannoor.justtannoor.R;
import com.justtannoor.justtannoor.Utilities.Constant;
import com.justtannoor.justtannoor.Utilities.SharedPreferencesUtil;
import com.justtannoor.justtannoor.custom.TextViewHead;
import com.justtannoor.justtannoor.model.GetBankDetail;
import com.justtannoor.justtannoor.retrofit.BaseRequest;
import com.justtannoor.justtannoor.retrofit.RequestReciever;

public class BankTransferActivity extends AppCompatActivity {

    private Context mContext=null;
    private BaseRequest baseRequest = null;
    private TextViewHead edtInfoIfscCode, edtInfoAccountHolderName, edtInfoAccountNumber,edtInfoOrderID,edtInfoBankname;
    private String order_id;
    private Button btnProceed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_transfer);
        mContext = this;
        initview();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void initview(){

        baseRequest = new BaseRequest(this);

        getSupportActionBar().setSubtitle("التحويل المصرفي");

        edtInfoAccountHolderName = findViewById(R.id.edtInfoAccountHolderName);
        edtInfoAccountNumber = findViewById(R.id.edtInfoAccountNumber);
        edtInfoIfscCode = findViewById(R.id.edtInfoIfscCode);
        edtInfoBankname = findViewById(R.id.edtInfoBankname);
        edtInfoOrderID = findViewById(R.id.edtInfoOrderID);

        btnProceed = findViewById(R.id.btnProceed);

        if(getIntent().getStringExtra("order_id")!=null){

           order_id = getIntent().getStringExtra("order_id");
        }

        callgetBankDetailInfoAPI();

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    ////////////////////////////////////////////////

    private void callgetBankDetailInfoAPI() {
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {

                try {
                    Gson gson = new Gson();
                    //////////////add model class here
                    GetBankDetail getBankDetailModal = gson.fromJson(Json, GetBankDetail.class);
                   // edtInfoIfscCode.setText(getBankDetailModal.getResponse().getIfscCode());
                    edtInfoIfscCode.setText(getBankDetailModal.getResponse().getIban());
                    edtInfoBankname.setText(getBankDetailModal.getResponse().getBankName());
                    edtInfoAccountHolderName.setText(getBankDetailModal.getResponse().getAccountName());
                    edtInfoAccountNumber.setText(getBankDetailModal.getResponse().getAccountNumber());
                    edtInfoOrderID.setText(order_id);

                    SharedPreferencesUtil.setData(mContext, Constant.PLACE_ORDER_STATUS, "true");
                    SharedPreferencesUtil.setData(mContext, Constant.PLACE_ORDER_ID, order_id);
                    SharedPreferencesUtil.setData(mContext, Constant.PLACE_AC_NUMBER, getBankDetailModal.getResponse().getAccountNumber());
                    SharedPreferencesUtil.setData(mContext, Constant.PLACE_AC_NAME, getBankDetailModal.getResponse().getAccountName());
                   // SharedPreferencesUtil.setData(mContext, Constant.PLACE_BANK_NAME, mLoginResponse.getLastName());
                    SharedPreferencesUtil.setData(mContext, Constant.PLACE_BANK_IFSC, getBankDetailModal.getResponse().getIban());
                    SharedPreferencesUtil.setData(mContext, Constant.PLACE_BANK_NAME, getBankDetailModal.getResponse().getBankName());


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
        baseRequest.callAPIPost(1, jsonObject, Constant.GET_BANK_ACCOUNT_DETAIL);/////
    }

    //////////////////////////////////////////////

}
