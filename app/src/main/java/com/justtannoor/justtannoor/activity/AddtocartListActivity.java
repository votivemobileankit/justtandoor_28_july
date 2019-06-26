package com.justtannoor.justtannoor.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.justtannoor.justtannoor.Interface.DeleteCallBack;
import com.justtannoor.justtannoor.R;
import com.justtannoor.justtannoor.Utilities.Constant;
import com.justtannoor.justtannoor.adapter.AddToCartAdapter;
import com.justtannoor.justtannoor.custom.TextViewHead;
import com.justtannoor.justtannoor.model.AddCartListModel;
import com.justtannoor.justtannoor.model.AddCartListResponse;
import com.justtannoor.justtannoor.model.CategoryListBean;
import com.justtannoor.justtannoor.model.CounterResponse;
import com.justtannoor.justtannoor.retrofit.BaseRequest;
import com.justtannoor.justtannoor.retrofit.RequestReciever;

import java.util.ArrayList;
import java.util.List;

public class AddtocartListActivity extends AppCompatActivity {

    private RelativeLayout rlvDataContainer = null;
    private RelativeLayout rlvContinueView = null;
    private LinearLayout rlvNoInternetContainer = null;
    private LinearLayout rlvNoDataFoundContainer = null;
    private Context mContext = null;
    private BaseRequest baseRequest = null;
    private String mAndroidDeviceID = null;
    private RecyclerView mAddtoCardRecyclerview = null;
    private TextViewHead txtContinueToCheckout = null;
    private TextViewHead txtContinueToCheckoutPrice = null;

    RecyclerView.Adapter recyclerView_Adapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    ArrayList<CategoryListBean> categoryNameList;
    private static List<AddCartListResponse> mAddToCArtResponseList = null;
    private CounterResponse mCounterResponse = null;
    Integer cate_image[] = {
            R.drawable.img_menu_banner_five, R.drawable.img_menu_banner_four, R.drawable.img_menu_banner_three, R.drawable.img_menu_banner_two
    };

    String cate_Name[] = {"Car", "Propery", "Home", "Bike"};
    String cate_Desc[] = {" top You have seen many Android apps with sliding images with circle page indicator at top You have seen many Android apps with sliding images with circle page indicator at top You have seen many Android apps with sliding images with circle page indicator at top You have seen many Android apps with sliding images with circle page indicator at top You have seen many Android apps with sliding images with circle page indicator at top You have seen many Android apps with sliding images with circle page indicator at top.", "You have seen many Android apps with sliding images with circle page indicator at top. Today we are going to learn the same thing for our app. You have seen many Android apps with sliding images with circle page indicator at top.", "You have seen many Android apps with sliding images with circle page indicator at top. Today we are going to learn the same thing for our app.You have seen many Android apps with sliding images with circle page indicator at top.You have seen many Android apps with sliding images with circle page indicator at top.", "You have seen many Android apps with sliding images with circle page indicator at top. Today we are going to learn the same thing for our app.You have seen many Android apps with sliding images with circle page indicator at top.You have seen many Android apps with sliding images with circle page indicator at top.You have seen many Android apps with sliding images with circle page indicator at top.You have seen many Android apps with sliding images with circle page indicator at top.", "You have seen many Android apps with sliding images with circle page indicator", "You have seen many Android apps with sliding images with circle page indicator", "You have seen many Android apps with sliding images with circle page indicator"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtocart_list);
        mContext = this;
        initView();
    }



    private void initView() {

        Constant.TOTAL_AMOUNT_FOR_CHECKOUT = 0;
        baseRequest = new BaseRequest(this);
        categoryNameList = new ArrayList<>();
        mAddToCArtResponseList = new ArrayList<>();

        getSupportActionBar().setSubtitle("السلة");
        LinearLayoutManager lLayout;
        lLayout = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        // lLayout.setStackFromEnd(true);
        txtContinueToCheckout = (TextViewHead) findViewById(R.id.txtContinueToCheckout);
        txtContinueToCheckoutPrice = (TextViewHead) findViewById(R.id.txtContinueToCheckoutPrice);
        mAddtoCardRecyclerview = (RecyclerView) findViewById(R.id.mAddtoCardRecyclerview);

        rlvContinueView = (RelativeLayout) findViewById(R.id.rlvContinueView);
        rlvDataContainer = (RelativeLayout) findViewById(R.id.rlvDataContainer);
        rlvNoInternetContainer = (LinearLayout) findViewById(R.id.rlvNoInternetContainer);
        rlvNoDataFoundContainer = (LinearLayout) findViewById(R.id.rlvNoDataFoundContainer);
        mAddtoCardRecyclerview.setLayoutManager(lLayout);
        mAndroidDeviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        callAddToCartListAPI();

        rlvContinueView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Constant.TOTAL_PRICE > 500)
                {
                    Intent mIntent = new Intent(mContext, CheckOutActivity.class);
                    mIntent.putExtra("mCounterValue",mCounterResponse);
                    startActivity(mIntent);
                   // finish();
                }
                else {
                    Toast.makeText(mContext, "Minimum order must be AED 500 and above.", Toast.LENGTH_LONG).show();
                }

               /* if (mCounterResponse.getTotalOrderCount() < mCounterResponse.getTotalOrderLimit()) {

                    Intent mIntent = new Intent(mContext, CheckOutActivity.class);
                    mIntent.putExtra("mCounterValue",mCounterResponse);
                    startActivity(mIntent);
                } else {
                    Toast.makeText(mContext, "Shop Close for Today!!", Toast.LENGTH_LONG).show();
                }*/
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            callAddToCartListAPI();
        } catch (Exception e) {
            e.printStackTrace();///
        }
    }

    private void callAddToCartListAPI() {
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here
                    rlvDataContainer.setVisibility(View.VISIBLE);
                    rlvNoInternetContainer.setVisibility(View.GONE);
                    rlvNoDataFoundContainer.setVisibility(View.GONE);

                    AddCartListModel galleyModal = gson.fromJson(Json, AddCartListModel.class);
                    addToCartResponse(galleyModal);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {
                // errorLayout.showError(message);
                // Toast.makeText(mContext, Constant.mResponseFailureMessage, Toast.LENGTH_LONG).show();
                //  Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                rlvDataContainer.setVisibility(View.GONE);
                rlvNoInternetContainer.setVisibility(View.GONE);
                rlvNoDataFoundContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNetworkFailure(int APINumber, String message) {
                // Toast.makeText(mContext, Constant.NO_INTERNET_MESSAGE, Toast.LENGTH_LONG).show();
                rlvDataContainer.setVisibility(View.GONE);
                rlvNoInternetContainer.setVisibility(View.VISIBLE);
                rlvNoDataFoundContainer.setVisibility(View.GONE);
            }
        });

        JsonObject jsonObject = new JsonObject();
        try {
            ////Put input parameter here
            jsonObject.addProperty("visitor_id", mAndroidDeviceID);
           /*  jsonObject.addProperty(Enum.USER_PASSWORD, password);
            jsonObject.addProperty(Enum.DEVICE_ID, androidDeviceId);
            jsonObject.addProperty(Enum.DEVICE_TYPE, "android");*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        baseRequest.callAPIPost(1, jsonObject, Constant.GET_CART_LIST);/////
    }

    private void addToCartResponse(final AddCartListModel galleryModel) {
        try {
            if (galleryModel.getStatus() != null && galleryModel.getStatus().equalsIgnoreCase(Constant.TRUE_SATATUS)) {

                Constant.MINIMUM_ORDER_OUTOF_ABU = galleryModel.getMinimumorderprice();///////////out of abu Dhabi
                Constant.TOTAL_DISCOUNT_PRICE = galleryModel.getTotaldiscountprice();////5000 AED
                Constant.TOTAL_OFFER_PERCENTAGE = galleryModel.getTotalofferpercentage();/////////Offer will be apply

                Constant.TOTAL_AMOUNT_FOR_CHECKOUT = Integer.parseInt(galleryModel.getTotalPrice());
               // txtContinueToCheckout.setText("Continue To CheckOut " + "( AED " + galleryModel.getTotalPrice() + ")");
                txtContinueToCheckoutPrice.setText("( AED " + galleryModel.getTotalPrice() + ")");
                Constant.TOTAL_PRICE = Integer.parseInt(galleryModel.getTotalPrice());
                if (mAddToCArtResponseList != null && mAddToCArtResponseList.size() > 0)
                    mAddToCArtResponseList.clear();

                mCounterResponse = galleryModel.getCounter();
                mAddToCArtResponseList = galleryModel.getResponse();

                if (mAddToCArtResponseList.size() > 0) {
                    if (recyclerView_Adapter != null)
                        recyclerView_Adapter = null;

                    recyclerView_Adapter = new AddToCartAdapter(mContext, mAddToCArtResponseList, new DeleteCallBack() {
                        @Override
                        public void callBackDelete(boolean status, int position) {
                            if (status) {
                                try {
                                    callAddToCartListAPI();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    mAddtoCardRecyclerview.setAdapter(recyclerView_Adapter);
                } else {
                    rlvDataContainer.setVisibility(View.GONE);
                    rlvNoInternetContainer.setVisibility(View.GONE);
                    rlvNoDataFoundContainer.setVisibility(View.VISIBLE);
                }
                //////////////////////
            } else {
                Toast.makeText(mContext, galleryModel.getMessage(), Toast.LENGTH_LONG).show();
            }
            // setData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



   /* private void callUpdateQuentityAPI() {
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here
                    rlvDataContainer.setVisibility(View.VISIBLE);
                    rlvNoInternetContainer.setVisibility(View.GONE);
                    rlvNoDataFoundContainer.setVisibility(View.GONE);

                  *//*  AddCartListModel galleyModal = gson.fromJson(Json, AddCartListModel.class);
                    addToCartResponse(galleyModal);*//*

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {
                // errorLayout.showError(message);
                // Toast.makeText(mContext, Constant.mResponseFailureMessage, Toast.LENGTH_LONG).show();
                //  Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                rlvDataContainer.setVisibility(View.GONE);
                rlvNoInternetContainer.setVisibility(View.GONE);
                rlvNoDataFoundContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNetworkFailure(int APINumber, String message) {
                // Toast.makeText(mContext, Constant.NO_INTERNET_MESSAGE, Toast.LENGTH_LONG).show();
                rlvDataContainer.setVisibility(View.GONE);
                rlvNoInternetContainer.setVisibility(View.VISIBLE);
                rlvNoDataFoundContainer.setVisibility(View.GONE);
            }
        });

        JsonObject jsonObject = new JsonObject();
        try {
            ////Put input parameter here
            jsonObject.addProperty("visitor_id", mAndroidDeviceID);
             jsonObject.addProperty("product_id", password);
            jsonObject.addProperty("quantity", androidDeviceId);
            jsonObject.addProperty("price", "android");
            jsonObject.addProperty("id", "android");
        } catch (Exception e) {
            e.printStackTrace();
        }
        baseRequest.callAPIPost(1, jsonObject, Constant.UPDATE_QUENTITY_API);/////
    }*/


}
