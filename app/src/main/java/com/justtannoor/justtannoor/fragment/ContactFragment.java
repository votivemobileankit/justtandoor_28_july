package com.justtannoor.justtannoor.fragment;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.justtannoor.justtannoor.R;
import com.justtannoor.justtannoor.Utilities.Constant;
import com.justtannoor.justtannoor.Utilities.Validation;
import com.justtannoor.justtannoor.custom.CustomEdittext;
import com.justtannoor.justtannoor.custom.TextViewHead;
import com.justtannoor.justtannoor.retrofit.BaseRequest;
import com.justtannoor.justtannoor.retrofit.RequestReciever;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment implements View.OnClickListener {

    private CustomEdittext edtName, edtEmail, edtMobile, edtCity, edtMessage;
    private TextViewHead txtCallText, txtEmailText, txtInstaText, txtSnapText;
    private Button btnSubmite = null;
    private Activity mActivity = null;
    private View view;

    private BaseRequest baseRequest = null;
    private String mName, mEmail, mCity, mMobile, mMessage;

    private static final int STORAGE_PERMISSION_CODE = 123;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private static final String INSTAGRAM_PAGE = "https://www.instagram.com/justtannoor/";

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_contactus, container, false);
        mActivity = getActivity();

        initView();

        return view;
    }

    private void initView() {

        try {
            baseRequest = new BaseRequest(mActivity);
            edtName = (CustomEdittext) view.findViewById(R.id.edtName);
            edtEmail = (CustomEdittext) view.findViewById(R.id.edtEmail);
            edtMobile = (CustomEdittext) view.findViewById(R.id.edtMobile);
            edtCity = (CustomEdittext) view.findViewById(R.id.edtCity);
            edtMessage = (CustomEdittext) view.findViewById(R.id.edtMessage);
            btnSubmite = (Button) view.findViewById(R.id.btnSubmite);

            txtCallText = (TextViewHead) view.findViewById(R.id.txtCallText);
            txtEmailText = (TextViewHead) view.findViewById(R.id.txtEmailText);
            txtInstaText = (TextViewHead) view.findViewById(R.id.txtInstaText);
            txtSnapText = (TextViewHead) view.findViewById(R.id.txtSnapText);

            btnSubmite.setOnClickListener(this);
            txtCallText.setOnClickListener(this);
            txtEmailText.setOnClickListener(this);
            txtInstaText.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmite:
                try {
                    mName = edtName.getText().toString().trim();
                    mEmail = edtEmail.getText().toString().trim();
                    mMobile = edtMobile.getText().toString().trim();
                    mCity = edtCity.getText().toString().trim();
                    mMessage = edtMessage.getText().toString().trim();
                    if (checkValidate()) {
                        callContactusAPI();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //  checkValidate();

                break;
            case R.id.txtEmailText:

                try {
                    String[] recipients = new String[]{"justtannoor@gmail.com"};
                    Intent testIntent = new Intent(Intent.ACTION_SEND);
                    testIntent.setType("text/email");
                    testIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                    testIntent.putExtra(Intent.EXTRA_TEXT, "");
                    testIntent.putExtra(Intent.EXTRA_EMAIL, recipients);
                    startActivity(testIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.txtCallText:

                break;
            case R.id.txtInstaText:
                Uri uri = Uri.parse(INSTAGRAM_PAGE);
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                likeIng.setPackage("com.instagram.android");
                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(INSTAGRAM_PAGE)));
                }
                break;

        }
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getActivity().getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    private Boolean checkValidate() {


        try {
            if (mName == null || mName.equalsIgnoreCase("") || mName.equalsIgnoreCase("Null")) {
                edtName.setError("Please enter name");
                return false;
            } else if (mEmail == null || mEmail.equalsIgnoreCase("") || mEmail.equalsIgnoreCase("Null")) {
                edtEmail.setError("Please enter email");
                return false;
            } else if (!Validation.isValidEmail(mEmail)) {
                edtEmail.setError("Please enter valid email");
                return false;
            } else if (mMobile == null || mMobile.equalsIgnoreCase("") || mMobile.equalsIgnoreCase("Null")) {
                edtMobile.setError("Please enter contact number");
                return false;
            } else if (mCity == null || mCity.equalsIgnoreCase("") || mCity.equalsIgnoreCase("Null")) {
                edtCity.setError("Please enter city");
                return false;
            } else if (mMessage == null || mMessage.equalsIgnoreCase("") || mMessage.equalsIgnoreCase("Null")) {
                edtMessage.setError("Please enter message");
                return false;
            } else {
                return true;

            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


        // return false;
    }

    private void callContactusAPI() {
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here
                    // AddCartListModel galleyModal = gson.fromJson(Json, AddCartListModel.class);
                    //  addToCartResponse(galleyModal);
                    Toast.makeText(mActivity, "Message send Successfully", Toast.LENGTH_LONG).show();
                    edtName.setText("");
                    edtEmail.setText("");
                    edtMobile.setText("");
                    edtCity.setText("");
                    edtMessage.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {
                // errorLayout.showError(message);
                // Toast.makeText(mContext, Constant.mResponseFailureMessage, Toast.LENGTH_LONG).show();
                Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNetworkFailure(int APINumber, String message) {
                Toast.makeText(mActivity, "Please check internet connection", Toast.LENGTH_LONG).show();

            }
        });

        JsonObject jsonObject = new JsonObject();
        try {
            ////Put input parameter here
            jsonObject.addProperty("name", mName);
            jsonObject.addProperty("email", mEmail);
            jsonObject.addProperty("mobilenumber", mMobile);
            jsonObject.addProperty("address", mCity);
            jsonObject.addProperty("message", mMessage);
           /*  jsonObject.addProperty(Enum.USER_PASSWORD, password);
            jsonObject.addProperty(Enum.DEVICE_ID, androidDeviceId);
            jsonObject.addProperty(Enum.DEVICE_TYPE, "android");*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        baseRequest.callAPIPost(1, jsonObject, Constant.CONTACT_UC);/////
    }

}