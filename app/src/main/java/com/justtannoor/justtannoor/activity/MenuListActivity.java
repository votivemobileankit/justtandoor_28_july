package com.justtannoor.justtannoor.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.justtannoor.justtannoor.R;
import com.justtannoor.justtannoor.Utilities.Constant;
import com.justtannoor.justtannoor.custom.TextViewHead;
import com.justtannoor.justtannoor.model.CounterResponse;
import com.justtannoor.justtannoor.model.Foodtype;
import com.justtannoor.justtannoor.model.MenuDetailModel;
import com.justtannoor.justtannoor.retrofit.BaseRequest;
import com.justtannoor.justtannoor.retrofit.RequestReciever;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MenuListActivity extends AppCompatActivity {
    private static final String TAG = MenuListActivity.class.getSimpleName();
    private LinearLayout lnrDaynamivMainView = null;
    private RelativeLayout rlvDataContainer = null;
    private RelativeLayout rltMenuImageView = null;
    private LinearLayout rlvNoInternetContainer = null;
    private LinearLayout rlvNoDataFoundContainer = null;
    private Context mContext = null;
    private ImageView mImgMenuList = null;
    private TextViewHead txtFoodNamePerson = null;
    private TextViewHead txtFoodNamePersonARB = null;
    private TextViewHead txtFoodPrice = null;
    private String mSelectedString = "";
    String[] mHeading = {"* Food Type", "* Rice Type", "* Included items"};
    String[] mRice = {"Biryani", "Rice White", "Rice Rgag"};
    String[] mInclude = {"Laban", "Pepper mix", "Salad", "wet tissue"};
    String[] mtannoor = {"Tannoor", "Grilled in oven"};
    private static List<Foodtype> mMenuDetailsResponseList = null;
    private BaseRequest baseRequest = null;
    private String menu_ID = null;
    private String mAndroidDeviceID = null;
    private TextViewHead aAddToCartBtn = null;
    ArrayList<String> mSelectedArray = new ArrayList<>();
    String[] mSelectData = null;
    // private static List<String> mMenuDetailsSliderList = null;
    //RadioGroup rgp = null;
    //  RadioButton rbn = null;
    private RadioGroup rgp = null;
    private String mProduct_Name,mArabicProduct_Name, mProduct_Price;
    HashMap<String, String> mResult = new HashMap<String, String>();
    HashMap<String, String> mResultFood = new HashMap<String, String>();

    private int mOderLimit = 0;
    private int mOderCounter = 0;
    private CounterResponse mCounterResponse = null;


    private HashMap<String,String> mKeyValueParams = new HashMap<String, String>();

    Map<Integer,String> mSelectRiceList = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        mContext = this;
        intiView();
    }

    private void intiView() {
        // Intent intent = getIntent();
        // menu_ID = intent.getStringExtra("menuID");
        // getSupportActionBar().setSubtitle("قائمة القائمة");
        getSupportActionBar().setSubtitle("أضف إلى السلة");
        mAndroidDeviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        baseRequest = new BaseRequest(mContext);
        mMenuDetailsResponseList = new ArrayList<>();
        lnrDaynamivMainView = (LinearLayout) findViewById(R.id.lnrDaynamivMainView);
        rlvDataContainer = (RelativeLayout) findViewById(R.id.rlvDataContainer);

        rltMenuImageView = (RelativeLayout) findViewById(R.id.rltMenuImageView);

        rlvNoInternetContainer = (LinearLayout) findViewById(R.id.rlvNoInternetContainer);
        rlvNoDataFoundContainer = (LinearLayout) findViewById(R.id.rlvNoDataFoundContainer);
        mImgMenuList = (ImageView) findViewById(R.id.mImgMenuList);
        txtFoodNamePerson = (TextViewHead) findViewById(R.id.txtFoodNamePerson);
        txtFoodNamePersonARB = (TextViewHead) findViewById(R.id.txtFoodNamePersonARB);
        aAddToCartBtn = (TextViewHead) findViewById(R.id.aAddToCartBtn);
        txtFoodPrice = (TextViewHead) findViewById(R.id.txtFoodPrice);
        // dynamicView();
        callmenuDetailsListAPI();

        aAddToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  int selectedId=rgp.getCheckedRadioButtonId();
                rbn=(RadioButton)findViewById(selectedId);*/
                Log.e(TAG, "mResult: >>" + mResult.toString());

                callAddmenuListAPI();

              /*  if (mCounterResponse.getTotalOrderCount() < mCounterResponse.getTotalOrderLimit()) {
                    callAddmenuListAPI();
                } else {
                    Toast.makeText(mContext, "Shop Closed for Today!!", Toast.LENGTH_LONG).show();
                }*/

                //  Intent mIntent = new Intent(mContext,AddtocartListActivity.class);
                // startActivity(mIntent);
                //  finish();
//                Toast.makeText(mContext,rbn.getText(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void callmenuDetailsListAPI() {
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
                    MenuDetailModel menuModal = gson.fromJson(Json, MenuDetailModel.class);
                    dynamicView(menuModal);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {
                // errorLayout.showError(message);
                // Toast.makeText(mContext, Constant.mResponseFailureMessage, Toast.LENGTH_LONG).show();
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
            jsonObject.addProperty("id", Constant.MENU_ID_FOR_DETAILS);


        } catch (Exception e) {
            e.printStackTrace();
        }
        baseRequest.callAPIPost(1, jsonObject, Constant.MENU_DETAILS_LIST);/////

        Log.e(TAG, "callmenuDetailsListAPI: >>"+Constant.MENU_DETAILS_LIST);
        Log.e(TAG, "callmenuDetailsListAPI: id>>"+Constant.MENU_ID_FOR_DETAILS);
    }

    @SuppressLint("ResourceType")
    private void dynamicView(final MenuDetailModel menuDetailModel) {

        mCounterResponse = menuDetailModel.getCounter();

        mProduct_Name = menuDetailModel.getResponse().getCdata().getProductName();
        mArabicProduct_Name = menuDetailModel.getResponse().getCdata().getProductArabicTitle();
        mProduct_Price = menuDetailModel.getResponse().getCdata().getProductPrice();

        mOderCounter = menuDetailModel.getResponse().getCdata().getLimitCounter();
        mOderLimit = menuDetailModel.getResponse().getCdata().getOrderLimit();

        txtFoodPrice.setText("AED " + menuDetailModel.getResponse().getCdata().getProductPrice());

        try {
            String mmNAMEText = menuDetailModel.getResponse().getCdata().getProductName();

            String mmnew = mmNAMEText.replace("***", "VIKAS");
            String[] mmNAMEText1 = mmnew.split("VIKAS");
            //  txtFoodNamePerson.setText(menuDetailModel.getResponse().getCdata().getProductName());
            txtFoodNamePerson.setText(mmNAMEText1[0]);
            txtFoodNamePersonARB.setText(mmNAMEText1[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        txtFoodNamePerson.setText(menuDetailModel.getResponse().getCdata().getProductName());
        txtFoodNamePersonARB.setText(menuDetailModel.getResponse().getCdata().getProductArabicTitle());

       /* int mmmint = Validation.getDeviceHeightWidth(mContext,false)/3;
        rltMenuImageView.setMinimumHeight(mmmint);
        rltMenuImageView.setMinimumWidth(Validation.getDeviceHeightWidth(mContext,true));
        mImgMenuList.setScaleType(ImageView.ScaleType.FIT_XY);*/

        Log.e(TAG, "dynamicView: >>" + menuDetailModel.getResponse().getCdata().getProductImage());
        Picasso.with(mContext).load(menuDetailModel.getResponse().getCdata().getProductImage())
                .placeholder(R.mipmap.ic_launcher)
                .into(mImgMenuList);

        if ((menuDetailModel.getResponse().getFoodtype() != null) && (menuDetailModel.getResponse().getFoodtype().size() > 0)) {

            mSelectData = new String[menuDetailModel.getResponse().getFoodtype().size()];
            for (int i = 0; i < menuDetailModel.getResponse().getFoodtype().size(); i++) {
                final int mListSize = menuDetailModel.getResponse().getFoodtype().get(i).getFoodvalue().size();
                try {
                    mSelectedArray.add(i, menuDetailModel.getResponse().getFoodtype().get(i).getFoodname());

                    mResult.put("v" + i, menuDetailModel.getResponse().getFoodtype().get(i).getFoodvalue().get(0));
                    LinearLayout iv_sub_linearlayout = new LinearLayout(this);
                    LinearLayout.LayoutParams iv_outparams = new LinearLayout.LayoutParams
                            ((int) LinearLayout.LayoutParams.MATCH_PARENT, (int) LinearLayout.LayoutParams.WRAP_CONTENT);
                    iv_outparams.setMarginEnd(10);
                    iv_outparams.setMarginStart(10);
                    iv_outparams.setMargins(0, 10, 0, 0);
                    iv_sub_linearlayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    iv_sub_linearlayout.setOrientation(LinearLayout.VERTICAL);
                    iv_sub_linearlayout.setLayoutParams(iv_outparams);
                    TextView tv_bold = new TextView(this);
                    LinearLayout.LayoutParams txtFoodHeading = new LinearLayout.LayoutParams
                            ((int) LinearLayout.LayoutParams.MATCH_PARENT, (int) LinearLayout.LayoutParams.WRAP_CONTENT);
                    txtFoodHeading.setMarginEnd(10);
                    txtFoodHeading.setMarginStart(10);
                    txtFoodHeading.setMargins(0, 20, 0, 0);

                    tv_bold.setText("* " + menuDetailModel.getResponse().getFoodtype().get(i).getFoodname());
                    tv_bold.setTextSize((float) 16);
                    tv_bold.setTypeface(null, Typeface.BOLD);
                    tv_bold.setId(i + 1);
                    tv_bold.setTextColor(getResources().getColor(R.color.colorBlack));

                    // tv_mornal[i].setPadding(20, 50, 20, 50);
                    tv_bold.setLayoutParams(txtFoodHeading);
                    iv_sub_linearlayout.addView(tv_bold);
                    LinearLayout rlvRadioButtonMainView = new LinearLayout(this);
                    //rlvRadioButtonMainView.setId(j+1);
                    rlvRadioButtonMainView.setOrientation(LinearLayout.VERTICAL);
                    RelativeLayout.LayoutParams rlvRadioViewPAram = new RelativeLayout.LayoutParams
                            ((int) RelativeLayout.LayoutParams.MATCH_PARENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);
                    rlvRadioButtonMainView.setLayoutParams(rlvRadioViewPAram);

                    rgp = new RadioGroup(this);

                    // rgp.setId(i+2);
                    //   rgp =  new RadioGroup(this);
                    //  rgp.setId(i+2);
                    for (int j = 0; j < menuDetailModel.getResponse().getFoodtype().get(i).getFoodvalue().size(); j++) {
                        if (menuDetailModel.getResponse().getFoodtype().get(i).getFoodname().contains("Rice Type")){

                            final CheckBox rbn = new CheckBox(this);
                            //   rbn = new RadioButton(this);
                            RelativeLayout.LayoutParams txtOptionParam = new RelativeLayout.LayoutParams
                                    ((int) RelativeLayout.LayoutParams.WRAP_CONTENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);
                            txtOptionParam.setMarginEnd(10);
                            txtOptionParam.setMarginStart(50);
                            txtOptionParam.setMargins(0, 20, 0, 0);
                            //   rbn.setId(i + 1);
                            rbn.setTag("v" + i);
                            // rbn.setTag(i,menuDetailModel.getResponse().getFoodtype().get(i).getFoodname());
                            rbn.setText(menuDetailModel.getResponse().getFoodtype().get(i).getFoodvalue().get(j));
                            rgp.setLayoutParams(txtOptionParam);
                            rgp.addView(rbn);
//                            rgp.check(1);
                            // rgp.c

                            final int finalI = i;
                            final int finalJ = j;
                            rbn.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {

                                    String mTag = (String) v.getTag();
                                    String mSelectedText = ((CheckBox) v).getText().toString();

                                    if (rbn.isChecked()){
                                        mSelectRiceList.put(finalJ,mSelectedText);
                                    }else {
                                        mSelectRiceList.remove(finalJ);
                                    }
                                    StringBuilder mSelectRiceString = new StringBuilder();

                                    Log.e(TAG, "onClick: mSelectRiceList>>"+mSelectRiceList.toString());

                                    for (Map.Entry<Integer, String> entry : mSelectRiceList.entrySet()) {
                                        System.out.println(entry.getKey() + " = " + entry.getValue());
                                        mSelectRiceString.append(" "+entry.getValue()+"  |  ");
                                    }

                                    String nmSelectRiceString;

                                    if (mSelectRiceString.length() > 3) {
                                        nmSelectRiceString = mSelectRiceString.toString().substring(0, mSelectRiceString.length() - 3);
                                    }else {
                                        nmSelectRiceString = mSelectRiceString.toString();
                                    }

                                    Log.e(TAG, "mSelectRiceList Rice List: >>"+mSelectRiceString);
                                    Log.e(TAG, "nmSelectRiceString Rice: >>"+nmSelectRiceString);

                                    mResult.put(mTag,nmSelectRiceString+"");

                                    mKeyValueParams.put(menuDetailModel.getResponse().getFoodtype().get(finalI).getFoodname(),nmSelectRiceString+"");

                                }
                            });

                        }else {
                            RadioButton rbn = new RadioButton(this);
                            //   rbn = new RadioButton(this);
                            RelativeLayout.LayoutParams txtOptionParam = new RelativeLayout.LayoutParams
                                    ((int) RelativeLayout.LayoutParams.WRAP_CONTENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);
                            txtOptionParam.setMarginEnd(10);
                            txtOptionParam.setMarginStart(50);
                            txtOptionParam.setMargins(0, 20, 0, 0);
                            //   rbn.setId(i + 1);
                            rbn.setTag("v" + i);
                            // rbn.setTag(i,menuDetailModel.getResponse().getFoodtype().get(i).getFoodname());
                            rbn.setText(menuDetailModel.getResponse().getFoodtype().get(i).getFoodvalue().get(j));
                            rgp.setLayoutParams(txtOptionParam);
                            rgp.addView(rbn);

//                            rgp.check(1);

                            // rgp.c

                            final int finalI = i;
                            rbn.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {

                                    String mTag = (String) v.getTag();
                                    String mSelectedText = ((RadioButton) v).getText().toString();

                                    mResult.put(mTag, mSelectedText);

                                    mKeyValueParams.put(menuDetailModel.getResponse().getFoodtype().get(finalI).getFoodname(),mSelectedText);

//                            Log.e(TAG, "onClick: >>"+result);
                                    //  Log.e("vikas_data>>",mSelectData[1].toString());
                                }
                            });
                            // rlvRadioButtonMainView.addView(rgp);
                            // iv_sub_linearlayout.addView(rlvRadioButtonMainView);
                        }
                    }
                    // rgp.check(i);
                    rlvRadioButtonMainView.addView(rgp);
                    iv_sub_linearlayout.addView(rlvRadioButtonMainView);
                    lnrDaynamivMainView.addView(iv_sub_linearlayout);
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            }
        } else {
            mImgMenuList.getLayoutParams().height = 600;
        }

        //////////////////////////*******************/////////////////////////

        if (menuDetailModel.getResponse().getIncludeditems() != null && menuDetailModel.getResponse().getIncludeditems().size() > 0)
            addIncludedItems(menuDetailModel);

    }

    private void addIncludedItems(MenuDetailModel menuDetailModel) {

        LinearLayout iv_sub_linearlayout = new LinearLayout(this);
        LinearLayout.LayoutParams iv_outparams = new LinearLayout.LayoutParams
                ((int) LinearLayout.LayoutParams.MATCH_PARENT, (int) LinearLayout.LayoutParams.WRAP_CONTENT);
        iv_outparams.setMarginEnd(10);
        iv_outparams.setMarginStart(10);
        iv_outparams.setMargins(0, 10, 0, 40);
        iv_sub_linearlayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        iv_sub_linearlayout.setOrientation(LinearLayout.VERTICAL);
        iv_sub_linearlayout.setLayoutParams(iv_outparams);

        TextView tv_bold = new TextView(this);
        LinearLayout.LayoutParams txtFoodHeading = new LinearLayout.LayoutParams
                ((int) LinearLayout.LayoutParams.MATCH_PARENT, (int) LinearLayout.LayoutParams.WRAP_CONTENT);
        txtFoodHeading.setMarginEnd(10);
        txtFoodHeading.setMarginStart(10);
        txtFoodHeading.setMargins(0, 20, 0, 0);
        // tv_bold.setText(mHeading[i]);
        // tv_bold.setText("*  Included Items البنود المدرجة");
        tv_bold.setText("*  Included Items يشمل");
        tv_bold.setTextSize((float) 16);
        tv_bold.setTypeface(null, Typeface.BOLD);
        // tv_bold.setId(8+1);
        tv_bold.setTextColor(getResources().getColor(R.color.colorBlack));
        // tv_mornal[i].setPadding(20, 50, 20, 50);
        tv_bold.setLayoutParams(txtFoodHeading);
        iv_sub_linearlayout.addView(tv_bold);


        for (int j = 0; j < menuDetailModel.getResponse().getIncludeditems().size(); j++) {
            try {

                RelativeLayout rlvRadioButtonMainView = new RelativeLayout(this);
                RelativeLayout.LayoutParams rlvRadioViewPAram = new RelativeLayout.LayoutParams
                        ((int) RelativeLayout.LayoutParams.MATCH_PARENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);
                rlvRadioButtonMainView.setLayoutParams(rlvRadioViewPAram);

                TextView txtOptionName = new TextView(this);
                LinearLayout.LayoutParams txtOptionParam = new LinearLayout.LayoutParams
                        ((int) LinearLayout.LayoutParams.MATCH_PARENT, (int) LinearLayout.LayoutParams.WRAP_CONTENT);
                txtOptionParam.setMarginEnd(10);
                txtOptionParam.setMarginStart(70);
                txtOptionParam.setMargins(0, 20, 0, 0);
                txtOptionName.setText(menuDetailModel.getResponse().getIncludeditems().get(j));
                txtOptionName.setTextSize((float) 14);
                txtOptionName.setTypeface(null, Typeface.NORMAL);
                txtOptionName.setId(j + 2);
                txtOptionName.setTextColor(getResources().getColor(R.color.colorBlack));
                // tv_mornal[i].setPadding(20, 50, 20, 50);
                txtOptionName.setLayoutParams(txtOptionParam);
                rlvRadioButtonMainView.addView(txtOptionName);
                iv_sub_linearlayout.addView(rlvRadioButtonMainView);

                lnrDaynamivMainView.addView(iv_sub_linearlayout);
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
    }

    private void callAddmenuListAPI() {
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here
                    // MenuModel menuModal = gson.fromJson(Json, MenuModel.class);
                    //  menuResponse(menuModal);
                    Intent mIntent = new Intent(mContext, AddtocartListActivity.class);
                    startActivity(mIntent);
                    finish();

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
                Toast.makeText(mContext, Constant.mResponseNoInternet, Toast.LENGTH_LONG).show();

            }
        });

        //  JsonObject jsonObject = new JsonObject();

        JSONObject jsonAdd = new JSONObject();
        try {
            ////Put input parameter here
            // jsonObject.addProperty("seleted", "android");
            // we need another object to store the address
            // jsonAdd.put("visitor_id", Validation.getDeviceIMEI(mContext));
            jsonAdd.put("visitor_id", mAndroidDeviceID);
            jsonAdd.put("product_id", Constant.MENU_ID_FOR_DETAILS);
            jsonAdd.put("product_name", mProduct_Name);
            jsonAdd.put("arabic_product_name", mArabicProduct_Name);
            jsonAdd.put("price", mProduct_Price);
            jsonAdd.put("quantity", "1");
            jsonAdd.put("total_price", mProduct_Price);

            JSONArray obj = new JSONArray();
            /*JSONArray obj = new JSONArray();
            try {
                int mIndex = mSelectedArray.size() - 1;
                Iterator myVeryOwnIterator = mResult.keySet().iterator();
                while (myVeryOwnIterator.hasNext()) {
                    JSONObject jsonObject1 = new JSONObject();
                    String key = (String) myVeryOwnIterator.next();
                    String value = (String) mResult.get(key);
                    // Toast.makeText(mContext, "Key: "+key+" Value: "+value, Toast.LENGTH_LONG).show();
                    jsonObject1.put("foodname", mSelectedArray.get(mIndex));
                    jsonObject1.put("foodvalue", value);
                    obj.put(jsonObject1);
                    mIndex--;
                }
                jsonAdd.put("seleted", obj);
            } catch (Exception e1) {
                // TODO Auto-generated catch block e1.printStackTrace();
                e1.printStackTrace();
            }*/
            Log.e(TAG, "mKeyValueParams: >>"+mKeyValueParams);
            Iterator it = mKeyValueParams.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();

                JSONObject jsonObject1 = new JSONObject();

                jsonObject1.put("foodname", pair.getKey());
                jsonObject1.put("foodvalue", pair.getValue());

                obj.put(jsonObject1);

                it.remove(); // avoids a ConcurrentModificationException
            }

            jsonAdd.put("seleted", obj);

            Log.e("request params>>>", jsonAdd.toString());

            // jsonObject.addProperty("seleted", "android");
        } catch (Exception e) {
            e.printStackTrace();
        }
        baseRequest.callAPIPost_New(1, jsonAdd, Constant.ADD_TO_CART);
    }

}