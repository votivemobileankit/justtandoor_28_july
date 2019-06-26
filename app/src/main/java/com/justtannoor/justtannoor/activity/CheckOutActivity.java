package com.justtannoor.justtannoor.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.justtannoor.justtannoor.R;
import com.justtannoor.justtannoor.Utilities.Constant;
import com.justtannoor.justtannoor.custom.CustomEdittext;
import com.justtannoor.justtannoor.custom.TextViewHead;
import com.justtannoor.justtannoor.model.CheckOutModel;
import com.justtannoor.justtannoor.model.CounterResponse;
import com.justtannoor.justtannoor.model.GetTokenModel;
import com.justtannoor.justtannoor.retrofit.BaseRequest;
import com.justtannoor.justtannoor.retrofit.RequestReciever;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class CheckOutActivity extends AppCompatActivity implements com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    /*braintree*/
    private static final String PATH_TO_SERVER = "https://braintree-sample-merchant.herokuapp.com/client_token";
    private static final String TAG = CheckOutActivity.class.getSimpleName();
    private String mClientToken;
    private static final int BRAINTREE_REQUEST_CODE = 4949;
    private final static int PLACE_PICKER_REQUEST = 123;

    private CustomEdittext edtInfoName, edtInfoLastName, edtInfoEmail, edtInfoMobile,edtPostalCode;
        private TextViewHead edtInfoAddress;
    private Button btnSubmite = null;
    private Context mContext = null;
    private TextViewHead tstPartialPayment = null, edtInfoTime, edtInfoDate;
    private TextViewHead tstFullPayment = null, tstTotalPrice, tstDlCharge, tstOrderPrice, tstDicountPrice;
    private ImageView imgPlacePicker;
    private TextViewHead tstBankTransferPayment = null;
    private View view;
    private String mCheckOutOrderID = "";
    private int mMinimumAmount = 0;
    private int mMinimumTime = 0;
    private String mAndroidDeviceID = null;
    private BaseRequest baseRequest = null;
    private String mName, mNameLast, mEmail, mAddress, mLat, mLong, mMobile, mPaymentType, mPostalCode, mDate, mTime, mDeliveryMethod = "Pickup";
    private Dialog openDialog = null;

    private RadioGroup mRadioGroup;
    Integer mDayCount = 0;
    private RadioButton mRadioButtonPicup, mRadioButtonDelivery, silent;
    private CounterResponse mCounterResponse = null;
    private DatePickerDialog datePickerDialog;
    private int Year, Month, Day;
    private Calendar calendar;
    String mSendLessonDate;
    boolean isStart = true;
    String mDateSave = "";
    double lateA;
    double longA;
    String mSelectedDate;
    String mSelectedDateTime;
    int mTotalPriceAMT = 0;
    private GoogleApiClient mGoogleApiClient;
    private int ADP_Price = 0;
    private int mTotal_Discount = 0;
    private double dist;
    private int amt2 = 0;
    private int mDeliveryCharges = 0;
    private int mOfferDicountPrice = 0;
    //  private int  amt1 = 0;
    private int mFixKMDeliveryCharge = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        mContext = this;
        setUpGClient();
        initView();
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Full Payment");
        setSupportActionBar(toolbar);*/
    }

 /*   @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mmIntent = new Intent(mContext, AddtocartListActivity.class);
        startActivity(mmIntent);
        finish();
    }*/

    private void initView() {
        baseRequest = new BaseRequest(this);
        getSupportActionBar().setSubtitle("الخروج");
        //   private CustomEdittext edtInfoName, edtInfoEmail, edtInfoMobile, edtInfoAddress;

        mCounterResponse = (CounterResponse) getIntent().getSerializableExtra("mCounterValue");
        int mm = mCounterResponse.getType();
        mAndroidDeviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        String mmm = getCurrentTime();

        edtInfoName = (CustomEdittext) findViewById(R.id.edtInfoName);
        edtInfoLastName = (CustomEdittext) findViewById(R.id.edtInfoLastName);
        mRadioGroup = (RadioGroup) findViewById(R.id.mRadioGroup);
        edtInfoEmail = (CustomEdittext) findViewById(R.id.edtInfoEmail);
        edtInfoMobile = (CustomEdittext) findViewById(R.id.edtInfoMobile);
        edtInfoAddress = findViewById(R.id.edtInfoAddress);
        tstOrderPrice = (TextViewHead) findViewById(R.id.tstOrderPrice);
        tstDlCharge = (TextViewHead) findViewById(R.id.tstDlCharge);
        tstTotalPrice = (TextViewHead) findViewById(R.id.tstTotalPrice);//tstTotalPrice
        imgPlacePicker = (ImageView) findViewById(R.id.imgPlacePicker);

        tstDicountPrice = (TextViewHead) findViewById(R.id.tstDicountPrice);///new added for dicount price

        edtInfoDate = (TextViewHead) findViewById(R.id.edtInfoDate);
        edtInfoTime = (TextViewHead) findViewById(R.id.edtInfoTime);
        edtPostalCode = (CustomEdittext) findViewById(R.id.edtPostalCode);
        tstPartialPayment = (TextViewHead) findViewById(R.id.tstPartialPayment);
        tstFullPayment = (TextViewHead) findViewById(R.id.tstFullPayment);
        tstBankTransferPayment = (TextViewHead) findViewById(R.id.tstBankTransferPayment);
        tstPartialPayment.setOnClickListener(this);
        tstFullPayment.setOnClickListener(this);
        tstBankTransferPayment.setOnClickListener(this);
        edtInfoDate.setOnClickListener(this);
        edtInfoTime.setOnClickListener(this);
        edtInfoAddress.setOnClickListener(this);
        imgPlacePicker.setOnClickListener(this);

        //   tstOrderPrice.setText("Order Price:          AED " + Constant.TOTAL_PRICE);
        tstOrderPrice.setText("AED " + Constant.TOTAL_PRICE);
        //  tstTotalPrice.setText("Total Price:           AED " + Constant.TOTAL_PRICE);
        tstTotalPrice.setText("AED " + Constant.TOTAL_PRICE);
        tstDlCharge.setText("AED 0");

        mMinimumAmount = Constant.TOTAL_PRICE;

        mDeliveryMethod = "Pickup";
        // tstDlCharge.setText("Delivery Charge: 0");
        tstDlCharge.setText("AED 0");

        if (Constant.TOTAL_AMOUNT_FOR_CHECKOUT > Constant.TOTAL_DISCOUNT_PRICE) {

            mTotal_Discount = (Constant.TOTAL_AMOUNT_FOR_CHECKOUT * Constant.TOTAL_OFFER_PERCENTAGE) / 100;
            mOfferDicountPrice = mTotal_Discount;
            ADP_Price = Constant.TOTAL_AMOUNT_FOR_CHECKOUT - mTotal_Discount;
            //mDeliveryCharges
            // mTotalPriceAMT =  ADP_Price + mDeliveryCharges;
            mTotalPriceAMT = ADP_Price;
            tstTotalPrice.setText("AED " + mTotalPriceAMT);
            //   Toast.makeText(mContext, " ADP_Price = "+ADP_Price + "\n mTotal_Discount = " + mTotal_Discount  , Toast.LENGTH_LONG).show();
            //  calladdpaymentInfoAPI();
        } else {
            mOfferDicountPrice = 0;
            tstTotalPrice.setText("AED " + Constant.TOTAL_PRICE);
            mMinimumAmount = Constant.TOTAL_PRICE;
            mTotalPriceAMT = Constant.TOTAL_PRICE;
        }

        tstDicountPrice.setText("AED " + mOfferDicountPrice);


        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mAddress = edtInfoAddress.getText().toString().trim();

                if (mAddress == null || mAddress.equalsIgnoreCase("") || mAddress.equalsIgnoreCase("Null")) {
                    edtInfoAddress.setError("Please enter delivery address.");
                }
                // find which radio button is selected
                if (checkedId == R.id.mRadioButtonPicup) {
                    mDeliveryMethod = "Pickup";
                    // tstDlCharge.setText("Delivery Charge: 0");
                    tstDlCharge.setText("AED 0");

                    mDeliveryCharges = 0;

                    if (Constant.TOTAL_AMOUNT_FOR_CHECKOUT > Constant.TOTAL_DISCOUNT_PRICE) {

                        mTotal_Discount = (Constant.TOTAL_AMOUNT_FOR_CHECKOUT * Constant.TOTAL_OFFER_PERCENTAGE) / 100;
                        mOfferDicountPrice = mTotal_Discount;
                        ADP_Price = Constant.TOTAL_AMOUNT_FOR_CHECKOUT - mTotal_Discount;
                        //mDeliveryCharges
                        // mTotalPriceAMT =  ADP_Price + mDeliveryCharges;
                        mTotalPriceAMT = ADP_Price;
                        tstTotalPrice.setText("AED " + mTotalPriceAMT);
                        //   Toast.makeText(mContext, " ADP_Price = "+ADP_Price + "\n mTotal_Discount = " + mTotal_Discount  , Toast.LENGTH_LONG).show();
                        //  calladdpaymentInfoAPI();
                    } else {
                        mOfferDicountPrice = 0;
                        tstTotalPrice.setText("AED " + Constant.TOTAL_PRICE);
                        mMinimumAmount = Constant.TOTAL_PRICE;
                        mTotalPriceAMT = Constant.TOTAL_PRICE;
                    }
                    //  getLocationFromAddress(mAddress);
                    // Toast.makeText(getApplicationContext(), "choice: Pickup", Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.mRadioButtonDelivery) {
                    mDeliveryMethod = "Delivery";
                    getLocationFromAddress(mAddress);
                    // Toast.makeText(getApplicationContext(), "choice: Delivery", Toast.LENGTH_SHORT).show();
                }
            }

        });

        if (Constant.TOTAL_AMOUNT_FOR_CHECKOUT > Constant.TOTAL_DISCOUNT_PRICE) {

            mTotal_Discount = (Constant.TOTAL_AMOUNT_FOR_CHECKOUT * Constant.TOTAL_OFFER_PERCENTAGE) / 100;
            ADP_Price = Constant.TOTAL_AMOUNT_FOR_CHECKOUT - mTotal_Discount;
            //mDeliveryCharges
            // mTotalPriceAMT =  ADP_Price + mDeliveryCharges;
            mTotalPriceAMT = ADP_Price;

            tstTotalPrice.setText("AED " + mTotalPriceAMT);

            //   Toast.makeText(mContext, " ADP_Price = "+ADP_Price + "\n mTotal_Discount = " + mTotal_Discount  , Toast.LENGTH_LONG).show();
            //  calladdpaymentInfoAPI();
        }
        //  getClientTokenFromServer();
        // callGetDeviceTokenAPI();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getCalculateDistanceinKM() {
        try {
            Location loc1 = new Location("");
            loc1.setLatitude(lateA);
            loc1.setLongitude(longA);/////////////////plasiya

            Location loc2 = new Location("");/////rigalll
            loc2.setLatitude(mCounterResponse.getLatitude());////////////lamon treeee
            loc2.setLongitude(mCounterResponse.getLongitude());

            double distanceInMeters = loc1.distanceTo(loc2);
            double distanceInLikometer = (double) (distanceInMeters / 1000.0);

            // int amt1 = mCounterResponse.getFixKilometreDeliveryCharge();
            mFixKMDeliveryCharge = mCounterResponse.getFixKilometreDeliveryCharge();////////50 AED

            int amt = Constant.TOTAL_PRICE + mFixKMDeliveryCharge;

            //double dist = distanceInLikometer;
            dist = distanceInLikometer;
            if (dist > 30) {
                double mDist = dist - 30;
                //mDayCount = toIntExact(elapsedDays);
                // int mmDist =  (int) (mDist*1.5);
                int mmDist = (((int) dist) * ((int) mCounterResponse.getPerKilometreDeliveryCharge()));
                // int mmDist = (int) (mDist * mCounterResponse.getPerKilometreDeliveryCharge());
                mTotalPriceAMT = amt + mmDist;
                mDeliveryCharges = mFixKMDeliveryCharge + mmDist;
                //  Toast.makeText(this,"distanceInMeters==>>"+distanceInLikometer,Toast.LENGTH_SHORT).show();
                //  Toast.makeText(this,"Constant.TOTAL_PRICE==>>"+mTotalPriceAMT,Toast.LENGTH_LONG).show();
            } else {
                mTotalPriceAMT = amt;
                mDeliveryCharges = mFixKMDeliveryCharge;
            }

            if (Constant.TOTAL_AMOUNT_FOR_CHECKOUT > Constant.TOTAL_DISCOUNT_PRICE) {

                mTotal_Discount = (Constant.TOTAL_AMOUNT_FOR_CHECKOUT * Constant.TOTAL_OFFER_PERCENTAGE) / 100;
                mOfferDicountPrice = mTotal_Discount;
                ADP_Price = Constant.TOTAL_AMOUNT_FOR_CHECKOUT - mTotal_Discount;

                //mDeliveryCharges
                mTotalPriceAMT = ADP_Price + mDeliveryCharges;

                tstTotalPrice.setText("AED " + mTotalPriceAMT);

                //  Toast.makeText(mContext, " ADP_Price = "+ADP_Price + "\n mTotal_Discount = " + mTotal_Discount  , Toast.LENGTH_LONG).show();
                //    calladdpaymentInfoAPI();
            } else {
                mOfferDicountPrice = 0;
                if (dist > 50) {
                    if (Constant.TOTAL_AMOUNT_FOR_CHECKOUT > Constant.MINIMUM_ORDER_OUTOF_ABU) {

                        mTotalPriceAMT = Constant.TOTAL_AMOUNT_FOR_CHECKOUT + mDeliveryCharges;

                        tstTotalPrice.setText("AED " + mTotalPriceAMT);

                        //calladdpaymentInfoAPI();
                    } else {
                        //////If Distance Greater then 50 KM then minimum order should be 1500 AED

                        Toast.makeText(mContext, "If distance greater then 50 KM then minimum order should be greater then " + Constant.MINIMUM_ORDER_OUTOF_ABU + " AED", Toast.LENGTH_LONG).show();
                    }
                }
                //  tstDlCharge.setText("AED " + mDeliveryCharges);
            }
            tstDlCharge.setText("AED " + mDeliveryCharges);
            tstTotalPrice.setText("AED " + mTotalPriceAMT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(mContext);
        List<Address> address;
        LatLng p1 = null;
        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
//            if (address == null) {
//                return null;
//            }
            if (address.size() > 0) {
                if (mDeliveryMethod.equalsIgnoreCase("Delivery")) {
                    Address location = address.get(0);
                    lateA = location.getLatitude();
                    longA = location.getLongitude();
                    getCalculateDistanceinKM();
                }
            } else {
                edtInfoAddress.setError("Please Enter Vaild Address!");
            }
            //  p1 = new LatLng(location.getLatitude(), location.getLongitude() );
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // return p1;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tstPartialPayment:
                mPaymentType = "Bank Transfer";
                // mMinimumAmount = Constant.TOTAL_AMOUNT_FOR_CHECKOUT;
                mMinimumAmount = mTotalPriceAMT;
                mName = edtInfoName.getText().toString().trim();
                mNameLast = edtInfoLastName.getText().toString().trim();
                mEmail = edtInfoEmail.getText().toString().trim();
                mMobile = edtInfoMobile.getText().toString().trim();
                mAddress = edtInfoAddress.getText().toString().trim();
                mPostalCode = edtPostalCode.getText().toString().trim();
                mDate = edtInfoDate.getText().toString().trim();
                mTime = edtInfoTime.getText().toString().trim();
                if (checkValidate()) {

                    if (Constant.TOTAL_PRICE > 500) {

                        if (Constant.TOTAL_AMOUNT_FOR_CHECKOUT > Constant.TOTAL_DISCOUNT_PRICE) {

                            mTotal_Discount = (Constant.TOTAL_AMOUNT_FOR_CHECKOUT * Constant.TOTAL_OFFER_PERCENTAGE) / 100;
                            ADP_Price = Constant.TOTAL_AMOUNT_FOR_CHECKOUT - mTotal_Discount;
                            //mDeliveryCharges
                            mTotalPriceAMT = ADP_Price + mDeliveryCharges;

                            tstTotalPrice.setText("AED " + mTotalPriceAMT);

                            //   Toast.makeText(mContext, " ADP_Price = "+ADP_Price + "\n mTotal_Discount = " + mTotal_Discount  , Toast.LENGTH_LONG).show();
                            calladdpaymentInfoAPI();
                        } else if (dist > 50) {
                            //  if (dist > 50)
                            //   {
                            if (Constant.TOTAL_AMOUNT_FOR_CHECKOUT > Constant.MINIMUM_ORDER_OUTOF_ABU) {

                                mTotalPriceAMT = Constant.TOTAL_AMOUNT_FOR_CHECKOUT + mDeliveryCharges;
                                tstTotalPrice.setText("AED " + mTotalPriceAMT);

                                calladdpaymentInfoAPI();
                            } else {
                                //////If Distance Greater then 50 KM then minimum order should be 1500 AED
                                Toast.makeText(mContext, "If distance greater then 50 KM then minimum order should be greater then " + Constant.MINIMUM_ORDER_OUTOF_ABU + " AED", Toast.LENGTH_LONG).show();
                            }
                            //  }
                            //  tstDlCharge.setText("AED " + mDeliveryCharges);
                        } else {
                            calladdpaymentInfoAPI();
                        }
                    } else {
                        Toast.makeText(mContext, "Minimum order must be AED 500 and above.", Toast.LENGTH_LONG).show();
                    }

                    /*if (Constant.TOTAL_PRICE > 500)
                        calladdpaymentInfoAPI();
                    else
                        Toast.makeText(mContext, "Minimum order must be AED 500 and above.", Toast.LENGTH_LONG).show();*/
                }

                /////this is for partial payment for order
               /* mName = edtInfoName.getText().toString().trim();
                //                mNameLast = edtInfoLastName.getText().toString().trim();
                //                mEmail = edtInfoEmail.getText().toString().trim();
                //                mMobile = edtInfoMobile.getText().toString().trim();
                //                mAddress = edtInfoAddress.getText().toString().trim();
                //                mPostalCode = edtPostalCode.getText().toString().trim();
                //
                //                mDate = edtInfoDate.getText().toString().trim();
                //                mTime = edtInfoTime.getText().toString().trim();
                //                //  mDeliveryMethod,mTime,mDate
                //                mPaymentType = "Partial Payment";
                //                // mPaymentType = "Partial Payment";
                //                if (checkValidate()) {
                //
                //                    showAdvanceSearch();
                //                    //calladdpaymentInfoAPI();
                //                }
                //                // showAdvanceSearch();*/
                break;
            case R.id.tstFullPayment:

                //   mPaymentType = "Cash on delivery";
                mPaymentType = "Cash on Delivery";
                // mMinimumAmount = Constant.TOTAL_AMOUNT_FOR_CHECKOUT;
                mMinimumAmount = mTotalPriceAMT;
                mName = edtInfoName.getText().toString().trim();
                mNameLast = edtInfoLastName.getText().toString().trim();
                mEmail = edtInfoEmail.getText().toString().trim();
                mMobile = edtInfoMobile.getText().toString().trim();
                mAddress = edtInfoAddress.getText().toString().trim();
                mPostalCode = edtPostalCode.getText().toString().trim();
                mDate = edtInfoDate.getText().toString().trim();
                mTime = edtInfoTime.getText().toString().trim();
                if (checkValidate()) {
                    /*
                     Constant.MINIMUM_ORDER_OUTOF_ABU = galleryModel.getMinimumorderprice();
                Constant.TOTAL_DISCOUNT_PRICE = galleryModel.getTotaldiscountprice();
                Constant.TOTAL_OFFER_PERCENTAGE = galleryModel.getTotalofferpercentage();

                 Constant.TOTAL_AMOUNT_FOR_CHECKOUT//////order Total order amount*/

                    if (Constant.TOTAL_PRICE > 500) {

                        if (Constant.TOTAL_AMOUNT_FOR_CHECKOUT > Constant.TOTAL_DISCOUNT_PRICE) {

                            mTotal_Discount = (Constant.TOTAL_AMOUNT_FOR_CHECKOUT * Constant.TOTAL_OFFER_PERCENTAGE) / 100;
                            ADP_Price = Constant.TOTAL_AMOUNT_FOR_CHECKOUT - mTotal_Discount;

                            //mDeliveryCharges
                            mTotalPriceAMT = ADP_Price + mDeliveryCharges;

                            tstTotalPrice.setText("AED " + mTotalPriceAMT);

                            // Toast.makeText(mContext, " ADP_Price = "+ADP_Price + "\n mTotal_Discount = " + mTotal_Discount  , Toast.LENGTH_LONG).show();
                            calladdpaymentInfoAPI();
                        } else if (dist > 50) {
                            //  if (dist > 50)
                            //   {
                            if (Constant.TOTAL_AMOUNT_FOR_CHECKOUT > Constant.MINIMUM_ORDER_OUTOF_ABU) {

                                mTotalPriceAMT = Constant.TOTAL_AMOUNT_FOR_CHECKOUT + mDeliveryCharges;
                                tstTotalPrice.setText("AED " + mTotalPriceAMT);

                                calladdpaymentInfoAPI();
                            } else {
                                //////If Distance Greater then 50 KM then minimum order should be 1500 AED
                                Toast.makeText(mContext, "If distance greater then 50 KM then minimum order should be greater then " + Constant.MINIMUM_ORDER_OUTOF_ABU + " AED", Toast.LENGTH_LONG).show();
                            }
                            //  }
                            //  tstDlCharge.setText("AED " + mDeliveryCharges);
                        } else {
                            calladdpaymentInfoAPI();
                        }
                      /*  } else {
                            if (dist > 50) {
                                if (Constant.TOTAL_AMOUNT_FOR_CHECKOUT > Constant.MINIMUM_ORDER_OUTOF_ABU) {

                                    mTotalPriceAMT =  Constant.TOTAL_AMOUNT_FOR_CHECKOUT + mDeliveryCharges;

                                    tstTotalPrice.setText("AED " + mTotalPriceAMT);

                                   calladdpaymentInfoAPI();
                                } else {
                                    //////If Distance Greater then 50 KM then minimum order should be 1500 AED

                                    Toast.makeText(mContext, "If distance greater then 50 KM then minimum order should be greater then " + Constant.MINIMUM_ORDER_OUTOF_ABU +" AED", Toast.LENGTH_LONG).show();
                                }

                            }
                            //  tstDlCharge.setText("AED " + mDeliveryCharges);
                        }*/


                    } else {
                        Toast.makeText(mContext, "Minimum order must be AED 500 and above.", Toast.LENGTH_LONG).show();
                    }

                    /* if(Constant.TOTAL_PRICE > 500) {
                        calladdpaymentInfoAPI();
                    }
                    else {
                        Toast.makeText(mContext, "Minimum order must be AED 500 and above.", Toast.LENGTH_LONG).show();
                    }*/
                }

                /////this is for full payment for order
               /* //   mMinimumAmount = Constant.TOTAL_AMOUNT_FOR_CHECKOUT;
                mMinimumAmount = mTotalPriceAMT;
                mName = edtInfoName.getText().toString().trim();
                mNameLast = edtInfoLastName.getText().toString().trim();
                mEmail = edtInfoEmail.getText().toString().trim();
                mMobile = edtInfoMobile.getText().toString().trim();
                mAddress = edtInfoAddress.getText().toString().trim();
                mPostalCode = edtPostalCode.getText().toString().trim();
                mDate = edtInfoDate.getText().toString().trim();
                mTime = edtInfoTime.getText().toString().trim();
                mPaymentType = "Full Payment";

                if (checkValidate()) {

                    if(Constant.TOTAL_PRICE > 500)
                        calladdpaymentInfoAPI();
                    else
                        Toast.makeText(mContext,"Minimum order must be AED 500 and above.",Toast.LENGTH_LONG).show();
                }*/
                break;
            case R.id.edtInfoDate:
                ChooseDate();
                break;
            case R.id.edtInfoTime:
                //  isStart = true;
                chooseTimePicker();
                break;
            /*case R.id.tstBankTransferPayment:
                mPaymentType = "Bank Transfer";
                // mMinimumAmount = Constant.TOTAL_AMOUNT_FOR_CHECKOUT;
                mMinimumAmount = mTotalPriceAMT;
                mName = edtInfoName.getText().toString().trim();
                mNameLast = edtInfoLastName.getText().toString().trim();
                mEmail = edtInfoEmail.getText().toString().trim();
                mMobile = edtInfoMobile.getText().toString().trim();
                mAddress = edtInfoAddress.getText().toString().trim();
                mPostalCode = edtPostalCode.getText().toString().trim();
                mDate = edtInfoDate.getText().toString().trim();
                mTime = edtInfoTime.getText().toString().trim();
                if (checkValidate()) {
                  if(Constant.TOTAL_PRICE > 500)
                    calladdpaymentInfoAPI();
                  else
                      Toast.makeText(mContext,"Minimum order must be AED 500 and above.",Toast.LENGTH_LONG).show();
                }
                break;*/
            case R.id.imgPlacePicker:
                try {
                    // KeyboardUtil.hideSoftKeyboard(CheckOutActivity.this);
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//                    builder.setLatLngBounds(new LatLngBounds(new LatLng(35, 88), new LatLng(71, 173)));
                    startActivityForResult(builder.build(CheckOutActivity.this), PLACE_PICKER_REQUEST);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.edtInfoAddress:
                try {
                    // KeyboardUtil.hideSoftKeyboard(CheckOutActivity.this);
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//                    builder.setLatLngBounds(new LatLngBounds(new LatLng(35, 88), new LatLng(71, 173)));
                    startActivityForResult(builder.build(CheckOutActivity.this), PLACE_PICKER_REQUEST);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      //  mFBCallbackManager.onActivityResult(requestCode, resultCode, data); //facebook

        switch (requestCode) {

            case PLACE_PICKER_REQUEST:
                if (resultCode == RESULT_OK) {
                    Place place = PlacePicker.getPlace(data, this);
                    if (place != null) {
                     //   mEtSearchLocation.setText(place.getAddress());

                    }
                } else {
                    Toast.makeText(this, "Request Cancelled", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }*/

    private synchronized void setUpGClient() {
        try {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, 0, this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
            mGoogleApiClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Boolean checkValidate() {

        if (mName == null || mName.equalsIgnoreCase("") || mName.equalsIgnoreCase("Null")) {
            edtInfoName.setError("Please enter first name");
            return false;
        } else if (mNameLast == null || mNameLast.equalsIgnoreCase("") || mNameLast.equalsIgnoreCase("Null")) {
            edtInfoLastName.setError("Please enter last name");
            return false;
        }
        /*else if (mEmail == null || mEmail.equalsIgnoreCase("") || mEmail.equalsIgnoreCase("Null") )
        {
            edtInfoEmail.setError("Please enter email");
            return false;
        }
        else if (!Validation.isValidEmail(mEmail))
        {
            edtInfoEmail.setError("Please enter valid email");
            return false;
        }*/
        else if (mMobile == null || mMobile.equalsIgnoreCase("") || mMobile.equalsIgnoreCase("Null")) {
            edtInfoMobile.setError("Please enter contact number");
            return false;
        } else if (mAddress == null || mAddress.equalsIgnoreCase("") || mAddress.equalsIgnoreCase("Null")) {
            edtInfoAddress.setError("Please enter delivery address");
            return false;
        } else if (mDate == null || mDate.equalsIgnoreCase("") || mDate.equalsIgnoreCase("Null")) {
            edtInfoDate.setError("Please Select Date");
            return false;
        } else if (mTime == null || mTime.equalsIgnoreCase("") || mTime.equalsIgnoreCase("Null")) {
            edtInfoTime.setError("Please Select Time");
            return false;
        }
       /* else if (mPostalCode == null || mPostalCode.equalsIgnoreCase("") || mPostalCode.equalsIgnoreCase("Null") )
        {
            edtPostalCode.setError("Please enter postal code");
            return false;
        }*/
        else {
            return true;
        }
        // return false;
    }

    ///////////////////current time....

    public String getCurrentTime_pre() {

        String mmDate = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy HH");

            String currentDateandTime = sdf.format(new Date());
            String[] mTimearry = currentDateandTime.split(" ");
            mMinimumTime = Integer.parseInt(mTimearry[1]);
            mmDate = mTimearry[0];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mmDate;
    }

    public String getCurrentTime() {
        String currentDateandTime = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy HH");
            //  SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy hh aa");
            // SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy hh");
            currentDateandTime = sdf.format(new Date());
            String[] mTimearry = currentDateandTime.split(" ");
            mMinimumTime = Integer.parseInt(mTimearry[1]);
            String mformat;

       /* if (mMinimumTime == 0) {

            mMinimumTime += 12;

            mformat = "AM";
        }
        else if (mMinimumTime == 12) {

            mformat = "PM";

        }
        else if (mMinimumTime > 12) {

            mMinimumTime -= 12;

            mformat = "PM";

        }
        else {

            mformat = "AM";
        }
        mMinimumTime = mMinimumTime;*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        return currentDateandTime;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getCurrentDateTimeDifference(String mCurrentDate, String mSelectedDate) {

        // DateTimeUtils obj = new DateTimeUtils();
        // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
        // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh aa");
        // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy HH");

        try {
            Date date1 = simpleDateFormat.parse(mCurrentDate);
            Date date2 = simpleDateFormat.parse(mSelectedDate);

            printDifference(date1, date2);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    //1 minute = 60 seconds
//1 hour = 60 x 60 = 3600
//1 day = 3600 x 24 = 86400
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void printDifference(Date startDate, Date endDate) {
        //milliseconds
        try {
            long different = endDate.getTime() - startDate.getTime();

            System.out.println("startDate : " + startDate);
            System.out.println("endDate : " + endDate);
            System.out.println("different : " + different);

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            // long daysInMilli = hoursInMilli * 24;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;
            // mDayCount = (int) (long) elapsedDays;
            // mDayCount = toIntExact(elapsedDays);
            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;
       /* long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;
        long elapsedSeconds = different / secondsInMilli;*/
            System.out.printf(
                    "%d days, %d hours%n",
                    elapsedDays, elapsedHours);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////
    private void ChooseDate() {
        try {
            Locale locale = getResources().getConfiguration().locale;
            Locale.setDefault(locale);
            datePickerDialog = DatePickerDialog.newInstance(this, Year, Month, Day);
            datePickerDialog.showYearPickerFirst(true);
            datePickerDialog.setMinDate(calendar);
            datePickerDialog.setThemeDark(false);
            datePickerDialog.setAccentColor(Color.parseColor("#c8a271"));
            datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        try {
            int myear = year;
            int mmonth = monthOfYear + 1;
            int mday = dayOfMonth;
            String YEAR = String.valueOf(myear);
            mSelectedDate = mday + "/" + mmonth + "/" + myear;
            getDifferenceDays(getCurrentTime_pre(), mSelectedDate);
            int mpTime = 0;
            mpTime = mMinimumTime + 8;
            if (mDayCount == 0) {
                if (mpTime < 24) {
                    int length = (int) Math.log10(mmonth) + 1;

                    if (length == 1) {
                        mSendLessonDate = myear + "-" + "0" + mmonth + "-" + mday;
                        //   mLessonDate.setText(mSendLessonDate);
                        mDateSave = mSendLessonDate;
                    } else {
                        mSendLessonDate = myear + "-" + mmonth + "-" + mday;
                        //  mLessonDate.setText(mSendLessonDate);
                        mDateSave = mSendLessonDate;
                    }
                    edtInfoDate.setText(mSendLessonDate);
                } else {
                    Toast.makeText(mContext, "Today Order Limit is over So please order for next day!", Toast.LENGTH_LONG).show();
                }

            } else {
                int length = (int) Math.log10(mmonth) + 1;

                if (length == 1) {
                    mSendLessonDate = myear + "-" + "0" + mmonth + "-" + mday;
                    //   mLessonDate.setText(mSendLessonDate);
                    mDateSave = mSendLessonDate;
                } else {
                    mSendLessonDate = myear + "-" + mmonth + "-" + mday;
                    //  mLessonDate.setText(mSendLessonDate);
                    mDateSave = mSendLessonDate;
                }
                edtInfoDate.setText(mSendLessonDate);
            }

            //  mMinimumTime
            // mSendLessonDate = mday + "-" + mmonth + "-" + myear;
      /*  int length = (int) Math.log10(mmonth) + 1;

        if (length == 1) {
            mSendLessonDate = myear + "-" + "0" + mmonth + "-" + mday;
            //   mLessonDate.setText(mSendLessonDate);
            mDateSave = mSendLessonDate;
        } else {
            mSendLessonDate = myear + "-" + mmonth + "-" + mday;
            //  mLessonDate.setText(mSendLessonDate);
            mDateSave = mSendLessonDate;
        }
        edtInfoDate.setText(mSendLessonDate);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getDifferenceDays(String d1, String d2) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy");
        Date date1;
        Date date2;
        long diff;

        try {
            date1 = simpleDateFormat.parse(d1);
            date2 = simpleDateFormat.parse(d2);

            diff = date2.getTime() - date1.getTime();
            //
            long mm = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            //     mDayCount = toIntExact(mm);
            mDayCount = (int) mm;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void chooseTimePicker() {
        try {
            Calendar now = Calendar.getInstance();
            int mmTime = 0;
            int mmMinut = 0;
            int mmSecond = 0;

            mmTime = mMinimumTime + 8;
            //  TimePickerDialog tpd = TimePickerDialog.newInstance(this, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);

            if (mDayCount == 0) {

                if (mmTime > 23) {
                    mmTime = 23;
                    mmMinut = 59;
                    mmSecond = 59;
                }

                TimePickerDialog tpd = TimePickerDialog.newInstance(this, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);/////////
                tpd.setAccentColor(Color.parseColor("#c8a271"));
                tpd.setMinTime(mmTime, mmMinut, mmSecond);

                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd.show(getFragmentManager(), "Timepickerdialog");
            } else {

                TimePickerDialog tpd = TimePickerDialog.newInstance(this, 0, 0, true);/////////
                tpd.setAccentColor(Color.parseColor("#c8a271"));

                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd.show(getFragmentManager(), "Timepickerdialog");

                // tpd.setMinTime(mMinimumTime, 0, 0);
                // tpd = TimePickerDialog.newInstance(this, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);/////////
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

/*else
{
    tpd.setMinTime(mMinimumTime,0,0);
    //tpd.setMinTime(0,0,0);
    //tpd.setMinTime(mMinimumTime,0,0);
}*/

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

        try {
            //  String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
            //   String minuteString = minute < 10 ? "0" + minute : "" + minute;
            //   String secondString = second < 10 ? "0" + second : "" + second;
            //   String time = "You picked the following time: " + hourString + "h" + minuteString + "m" + secondString + "s";
            // String eventTime = hourString + ":" + minuteString + ":" + secondString;
            String mformat;
            //String eventTime = hourString + ":" + minuteString;
            int mHourOfDay = hourOfDay;

      /*  if (mHourOfDay == 0) {

            mHourOfDay += 12;

            mformat = "AM";
        }
        else if (mHourOfDay == 12) {

            mformat = "PM";

        }
        else if (mHourOfDay > 12) {

            mHourOfDay -= 12;

            mformat = "PM";

        }
        else {

            mformat = "AM";
        }*/

            String eventTime = "" + hourOfDay;
            //  String eventTime = hourOfDay+" "+mformat;
            //  String endTime = hourString + ":" + minuteString;
            mSelectedDateTime = mSelectedDate + " " + eventTime;

            getCurrentDateTimeDifference(getCurrentTime(), mSelectedDateTime);//////////////////add both the date  ****************
//        int mSelectedTime = Integer.parseInt(eventTime);
            // if(getCurrentTime() > mSelectedTime)


            // edtInfoTime.setText(mHourOfDay+" "+mformat);////////////////set Time
            edtInfoTime.setText("" + mHourOfDay + ":" + minute);////////////////set Time
/*
        if (isStart) {
            edtInfoTime.setText(eventTime);////////////////set Time
        }
            */
        /*else
            mLessonEndTime.setText(eventTime);*/
            // date_tv.setText(mSendKittyDate + " " + eventTime);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    ////////////////////////////////////////////////

    private void calladdpaymentInfoAPI() {

        if (mLat.length() > 0 && mLong.length() > 0) {


            baseRequest.setBaseRequestListner(new RequestReciever() {
                @Override
                public void onSuccess(int APINumber, String Json, Object obj) {
                    //  JSONArray arr = (JSONArray) obj;
                    try {
                        Gson gson = new Gson();
                        //////////////add model class here
                        CheckOutModel galleyModal = gson.fromJson(Json, CheckOutModel.class);
                        mCheckOutOrderID = galleyModal.getOrderId();
                        //  addToCartResponse(galleyModal);
                        if (galleyModal.getType() == 1) {
                            if (mPaymentType.equalsIgnoreCase("Bank Transfer")) {
                                Intent intent = new Intent(mContext, BankTransferActivity.class);
                                intent.putExtra("order_id", mCheckOutOrderID);
                                startActivity(intent);
                                finish();
                            } else {//////////this is for case on delivey of order
                                //  onBraintreeSubmit();
                                Intent intent = new Intent(mContext, CaseOnDeliveyActivity.class);
                                intent.putExtra("order_id", mCheckOutOrderID);
                                startActivity(intent);
                                finish();

                            }
                            openDialog.dismiss();
                            Constant.MINIMUM_ORDER_OUTOF_ABU = 0;
                            Constant.TOTAL_DISCOUNT_PRICE = 0;
                            Constant.TOTAL_OFFER_PERCENTAGE = 0;
                            Constant.TOTAL_AMOUNT_FOR_CHECKOUT = 0;//////order Total order amount

                            Toast.makeText(mContext, "Info Add Successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(mContext, galleyModal.getMessage(), Toast.LENGTH_LONG).show();
                            //openDialog.dismiss();

                        }

                        // edtMessage.setText("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int APINumber, String errorCode, String message) {
                    // errorLayout.showError(message);
                    // Toast.makeText(mContext, Constant.mResponseFailureMessage, Toast.LENGTH_LONG).show();
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
                jsonObject.addProperty("visitor_id", mAndroidDeviceID);
                jsonObject.addProperty("user_name", mName);
                // jsonObject.addProperty("last_name", mNameLast);
                jsonObject.addProperty("email_address", mEmail);
                jsonObject.addProperty("phone", "+971" + mMobile);
                jsonObject.addProperty("address", mAddress);

                jsonObject.addProperty("lat", mLat);
                jsonObject.addProperty("lang", mLong);

                jsonObject.addProperty("total_amount", mTotalPriceAMT);////////////new parametre
                //   jsonObject.addProperty("postcode", mPostalCode);
                jsonObject.addProperty("payment_type", mPaymentType);
                jsonObject.addProperty("delivery_date", mDate);
                jsonObject.addProperty("delivery_time", mTime);
                jsonObject.addProperty("delivery_type", mDeliveryMethod);

                jsonObject.addProperty("discount_price", mOfferDicountPrice);  //////
                //delivery_charge
                jsonObject.addProperty("delivery_charge", mDeliveryCharges);////delivery_charge


//  mDeliveryMethod,mTime,mDate
           /*  jsonObject.addProperty(Enum.USER_PASSWORD, password);
            jsonObject.addProperty(Enum.DEVICE_ID, androidDeviceId);
            jsonObject.addProperty(Enum.DEVICE_TYPE, "android");*/

            } catch (Exception e) {
                e.printStackTrace();
            }
            baseRequest.callAPIPost(1, jsonObject, Constant.GET_CART_CHECKOUT);/////
        } else {
            Toast.makeText(mContext, "Please select correct address.", Toast.LENGTH_SHORT).show();
        }
    }

    //////////////////////////////////////////////
    public void showAdvanceSearch() {

        openDialog = new Dialog(mContext);
        openDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        openDialog.setContentView(R.layout.dailog_advance_search);
        openDialog.setCancelable(false);
        openDialog.setCanceledOnTouchOutside(false);
        openDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        openDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView dialogImage = (ImageView) openDialog.findViewById(R.id.imageView_custom_dialog_close);
        final CustomEdittext edtInfoAddress = (CustomEdittext) openDialog.findViewById(R.id.edtInfoAddress);
        TextViewHead txtSubmitBTN = (TextViewHead) openDialog.findViewById(R.id.txtSubmitBTN);

        //  mMinimumAmount = Constant.TOTAL_AMOUNT_FOR_CHECKOUT / 3;
        //  mMinimumAmount = mTotalPriceAMT;
        mMinimumAmount = mTotalPriceAMT / 3;

        edtInfoAddress.setText("" + mMinimumAmount);

        txtSubmitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mGetMinimumAMT = 0;
                if (edtInfoAddress.getText().toString().trim() != null && !edtInfoAddress.getText().toString().trim().equalsIgnoreCase("")) {
                    mGetMinimumAMT = Integer.parseInt(edtInfoAddress.getText().toString().trim());
                    if (mGetMinimumAMT >= mMinimumAmount) {
                        if (Constant.TOTAL_PRICE > 500)
                            calladdpaymentInfoAPI();
                        else
                            Toast.makeText(mContext, "Minimum order must be AED 500 and above.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(mContext, "Please Add Minimum " + mMinimumAmount + " Amount", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(mContext, "Please Add Minimum " + mMinimumAmount + " Amount", Toast.LENGTH_LONG).show();
                }
                //openDialog.dismiss();
            }
        });

        dialogImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog.dismiss();
            }
        });

        openDialog.show();

    }
////////////////////braintree
    /*
   braintree integration
    */

    private void callGetDeviceTokenAPI() {
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here
                    edtInfoName.setText("");
                    edtInfoLastName.setText("");
                    edtInfoEmail.setText("");
                    edtInfoMobile.setText("");
                    edtInfoAddress.setText("");
                    edtPostalCode.setText("");

                    GetTokenModel galleyModal = gson.fromJson(Json, GetTokenModel.class);
                    //  addToCartResponse(galleyModal);
                    mClientToken = galleyModal.getResponse();
                    // Toast.makeText(mContext, "Info Add Successfully", Toast.LENGTH_LONG).show();
                    // edtMessage.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {
                // errorLayout.showError(message);
                // Toast.makeText(mContext, Constant.mResponseFailureMessage, Toast.LENGTH_LONG).show();
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
            /*jsonObject.addProperty("visitor_id", mAndroidDeviceID);
            jsonObject.addProperty("user_name", mName);
            jsonObject.addProperty("email_address", mEmail);
            jsonObject.addProperty("phone", mMobile);
            jsonObject.addProperty("address", mAddress);
            jsonObject.addProperty("payment_type", mPaymentType);
*/
           /*  jsonObject.addProperty(Enum.USER_PASSWORD, password);
            jsonObject.addProperty(Enum.DEVICE_ID, androidDeviceId);
            jsonObject.addProperty(Enum.DEVICE_TYPE, "android");*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        baseRequest.callAPIPost(1, jsonObject, Constant.GET_BRAINTREE_TOKEN);/////
    }

    private void getClientTokenFromServer() {
        AsyncHttpClient androidClient = new AsyncHttpClient();
        androidClient.get(" http://1apextech.com/justtannoor/webapi/index.php/Webservices/getToken", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, "703 : onFailure: TOKEN FAILED" + responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseToken) {
                Log.e(TAG, "708 : onSuccess: Client token SUCCESS: " + responseToken);
                mClientToken = responseToken;
            }
        });
    }

    public void onBraintreeSubmit() {
        DropInRequest dropInRequest = new DropInRequest()

                .disablePayPal()
                .amount("" + mMinimumAmount)
                .clientToken(mClientToken);

        // .clientToken("eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiI3MzVmOWZhYTY0OGE3ZmYxNjgyMDBiY2M0NTZlNGQ5NjAzN2IyNTI0MTExY2NmOGM3ZTA0MjY0YjBmYTUwMTM1fGNyZWF0ZWRfYXQ9MjAxOC0wNS0yNVQxMTozOTo0Ni44NDQ3MTQ0ODgrMDAwMFx1MDAyNm1lcmNoYW50X2lkPTM0OHBrOWNnZjNiZ3l3MmJcdTAwMjZwdWJsaWNfa2V5PTJuMjQ3ZHY4OWJxOXZtcHIiLCJjb25maWdVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvMzQ4cGs5Y2dmM2JneXcyYi9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJjaGFsbGVuZ2VzIjpbXSwiZW52aXJvbm1lbnQiOiJzYW5kYm94IiwiY2xpZW50QXBpVXJsIjoiaHR0cHM6Ly9hcGkuc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbTo0NDMvbWVyY2hhbnRzLzM0OHBrOWNnZjNiZ3l3MmIvY2xpZW50X2FwaSIsImFzc2V0c1VybCI6Imh0dHBzOi8vYXNzZXRzLmJyYWludHJlZWdhdGV3YXkuY29tIiwiYXV0aFVybCI6Imh0dHBzOi8vYXV0aC52ZW5tby5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tIiwiYW5hbHl0aWNzIjp7InVybCI6Imh0dHBzOi8vY2xpZW50LWFuYWx5dGljcy5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tLzM0OHBrOWNnZjNiZ3l3MmIifSwidGhyZWVEU2VjdXJlRW5hYmxlZCI6dHJ1ZSwicGF5cGFsRW5hYmxlZCI6dHJ1ZSwicGF5cGFsIjp7ImRpc3BsYXlOYW1lIjoiQWNtZSBXaWRnZXRzLCBMdGQuIChTYW5kYm94KSIsImNsaWVudElkIjpudWxsLCJwcml2YWN5VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3BwIiwidXNlckFncmVlbWVudFVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS90b3MiLCJiYXNlVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhc3NldHNVcmwiOiJodHRwczovL2NoZWNrb3V0LnBheXBhbC5jb20iLCJkaXJlY3RCYXNlVXJsIjpudWxsLCJhbGxvd0h0dHAiOnRydWUsImVudmlyb25tZW50Tm9OZXR3b3JrIjp0cnVlLCJlbnZpcm9ubWVudCI6Im9mZmxpbmUiLCJ1bnZldHRlZE1lcmNoYW50IjpmYWxzZSwiYnJhaW50cmVlQ2xpZW50SWQiOiJtYXN0ZXJjbGllbnQzIiwiYmlsbGluZ0FncmVlbWVudHNFbmFibGVkIjp0cnVlLCJtZXJjaGFudEFjY291bnRJZCI6ImFjbWV3aWRnZXRzbHRkc2FuZGJveCIsImN1cnJlbmN5SXNvQ29kZSI6IlVTRCJ9LCJtZXJjaGFudElkIjoiMzQ4cGs5Y2dmM2JneXcyYiIsInZlbm1vIjoib2ZmIn0=");
//                .requestThreeDSecureVerification(true);
        startActivityForResult(dropInRequest.getIntent(this), BRAINTREE_REQUEST_CODE);
    }

    private void sendPaymentNonceToServer(String paymentNonce) {
        RequestParams params = new RequestParams("NONCE", paymentNonce);
        AsyncHttpClient androidClient = new AsyncHttpClient();
        androidClient.post(PATH_TO_SERVER, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG, "Error: >>Failed to create a transaction");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d(TAG, "Output >>" + responseString);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == BRAINTREE_REQUEST_CODE) {
            if (RESULT_OK == resultCode) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                String paymentNonce = result.getPaymentMethodNonce().getNonce();
                //send to your server
                Log.e(TAG, "467 : BRAINTREE_REQUEST_CODE: Testing the app here");
                // sendPaymentNonceToServer(paymentNonce);
                // Constant.CHECK_BASEURL_SERVICE = true;
                // Constant.BASE_URL = "http://1apextech.com/justtannoor/webapi/index.php/Webapis/";
                callSendNonecTokenAPI(paymentNonce);


            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(mContext, "Payment request cancelled.", Toast.LENGTH_SHORT).show();
            } else {
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.e(TAG, "472 : BRAINTREE_REQUEST_CODE: " + error);
                Toast.makeText(mContext, "An invalid Payment was submitted. Please try again.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PLACE_PICKER_REQUEST)
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                if (place != null) {
                    LatLng latlang = place.getLatLng();
                    mLat = latlang.latitude + "";
                    mLong = latlang.longitude + "";
                    edtInfoAddress.setText(place.getAddress());
                    Log.e(TAG, "LatLng: >>" + mLat + ">>" + mLong);
                }
            } else {
                Toast.makeText(this, "Request Cancelled", Toast.LENGTH_SHORT).show();
            }
    }

    /////////////////////send nonec to server

    private void callSendNonecTokenAPI(String paymentNonce) {
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here
                    //GetTokenModel galleyModal = gson.fromJson(Json, GetTokenModel.class);
                    //  addToCartResponse(galleyModal);
                    //   mClientToken = galleyModal.getResponse();
                    //  Constant.CHECK_BASEURL_SERVICE = false;
                    // Constant.BASE_URL = "http://1apextech.com/justtannoor/webapi/index.php/webservices/";


                    Toast.makeText(mContext, "Payment Successfully", Toast.LENGTH_LONG).show();
                    Constant.CHECK_HOME_PAGE_RELOAD = true;
                    Intent mIntent = new Intent(mContext, MainActivity.class);
                    startActivity(mIntent);


                    // edtMessage.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {
                // errorLayout.showError(message);
                // Toast.makeText(mContext, Constant.mResponseFailureMessage, Toast.LENGTH_LONG).show();
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
            jsonObject.addProperty("responce_token", paymentNonce);
            jsonObject.addProperty("amt", mMinimumAmount);
            jsonObject.addProperty("order_id", mCheckOutOrderID);

            Log.e(TAG, "responce_token--->" + paymentNonce + "/n amt--->" + mMinimumAmount + "/n order_id--->" + mCheckOutOrderID);

           /*  jsonObject.addProperty(Enum.USER_PASSWORD, password);
            jsonObject.addProperty(Enum.DEVICE_ID, androidDeviceId);
            jsonObject.addProperty(Enum.DEVICE_TYPE, "android");*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        baseRequest.callAPIPost(1, jsonObject, Constant.SEND_BRAINTREE_NONECTOKEN);/////
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
