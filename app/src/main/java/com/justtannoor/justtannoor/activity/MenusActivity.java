package com.justtannoor.justtannoor.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.justtannoor.justtannoor.R;
import com.justtannoor.justtannoor.Utilities.Constant;
import com.justtannoor.justtannoor.adapter.MenuAdapter;
import com.justtannoor.justtannoor.model.Category;
import com.justtannoor.justtannoor.model.OfferModel;
import com.justtannoor.justtannoor.model.menu.Menus;
import com.justtannoor.justtannoor.model.menu.Response;
import com.justtannoor.justtannoor.retrofit.BaseRequest;
import com.justtannoor.justtannoor.retrofit.RequestReciever;

import java.util.ArrayList;
import java.util.List;

public class MenusActivity extends AppCompatActivity {

    private static final String TAG = MenusActivity.class.getSimpleName();
    private RecyclerView offerRecyclerView;
    private ArrayList<Category> categoryArrayList;

    private Activity mActivity = null;
    private LinearLayoutManager lLayout;
    private RecyclerView.Adapter recyclerViewAdapter, recyclerViewCardAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout rlvDataContainer = null;
    private LinearLayout rlvNoInternetContainer = null;
    private LinearLayout rlvNoDataFoundContainer = null;
    private ImageView mIvPrevious = null;
    private ImageView mIvNext = null;
    private ViewFlipper mViewFlipper = null;
    private RecyclerView mMenuRecyclerView = null;
    private RecyclerView mMenuCardRecyclerView = null;
    LinearLayoutManager lvlmCardHorizontal, lvlmMenuhorizontal;
    private float initialX;
    private BaseRequest baseRequest;
    private ArrayList<OfferModel> menuModelArrayList;
    private ArrayList<OfferModel> menuModelArrayListCArd;

    int[] uriIntImage = {
            R.drawable.img_menu_banner, R.drawable.img_menu_banner_five, R.drawable.img_menu_banner_four, R.drawable.img_menu_banner_three, R.drawable.img_menu_banner_two
    };

    private int[] imageArray = {R.drawable.img_gallery_one, R.drawable.img_gallery_two, R.drawable.img_gallery_three, R.drawable.img_gallery_four, R.drawable.img_gallery_five};

    private int[] imageArrayMenu = {R.drawable.img_menu_cardright, R.drawable.img_menu_cardleft, R.drawable.img_menu_cardright, R.drawable.img_menu_cardleft, R.drawable.img_menu_cardright, R.drawable.img_menu_cardleft, R.drawable.img_menu_cardright, R.drawable.img_menu_cardleft};


    private static List<Response> mMenuResponseList = null;
    private static List<String> mMenuSliderList = null;
    private String mCategoryId;
    private String mCategoryName;
    private String mCategoryArabicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus);
        mActivity = this;
        initView();
    }

    private void initView() {
        baseRequest = new BaseRequest(mActivity);
        mMenuResponseList = new ArrayList<>();
        mMenuSliderList = new ArrayList<>();
        menuModelArrayList = new ArrayList<>();
        menuModelArrayListCArd = new ArrayList<>();
        //   setData();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // lvlmCardHorizontal = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        mMenuCardRecyclerView = (RecyclerView) findViewById(R.id.mMenuCardRecyclerView);
        rlvDataContainer = (LinearLayout) findViewById(R.id.rlvDataContainer);
        rlvNoInternetContainer = (LinearLayout) findViewById(R.id.rlvNoInternetContainer);
        rlvNoDataFoundContainer = (LinearLayout) findViewById(R.id.rlvNoDataFoundContainer);
        //  swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mMenuCardRecyclerView.setLayoutManager(gridLayoutManager);

        /*mMenuCardRecyclerView.getLayoutParams().width = Validation.getDeviceHeightWidth(getActivity(), true);
        mMenuCardRecyclerView.getLayoutParams().height = Validation.getDeviceHeightWidth(getActivity(), false)/4+60;*/

       /* recyclerViewCardAdapter = new MenuCardAdapter(menuModelArrayListCArd,getActivity());
        mMenuCardRecyclerView.setAdapter(recyclerViewCardAdapter);*/


        mViewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        mIvPrevious = (ImageView) findViewById(R.id.mIvPrevious);
        mIvNext = (ImageView) findViewById(R.id.mIvNext);


        mCategoryName = getIntent().getStringExtra("CATEGORY_NAME");
        mCategoryArabicName = getIntent().getStringExtra("CATEGORY_ARABIC_NAME");
        mCategoryId = getIntent().getStringExtra("CATEGORY_ID");

        Log.e(TAG, "mCategoryId: >>"+mCategoryId);

        getSupportActionBar().setTitle(mCategoryName);
        getSupportActionBar().setSubtitle(mCategoryArabicName);

        callmenuListAPI();

      /*  for (int i = 0; i < uriIntImage.length; i++) {

            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            imageView.setImageResource(uriIntImage[i]);

            Picasso.with(getActivity()).load(uriStringImage[i])
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageView);

            mViewFlipper.addView(imageView);
           *//* mVideoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mVideoView.start();
                }
            });
*//*

        }


        mViewFlipper.startFlipping();
        mIvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewFlipper.showPrevious();

            }
        });

        mIvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mViewFlipper.showNext();
            }
        });
*/

    }

    public void setData() {

        for (int i = 0; i < mMenuSliderList.size(); i++) {
            ImageView imageView = new ImageView(mActivity);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(mActivity).load(mMenuSliderList.get(i))
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageView);

            //  imageView.setImageResource(mIconInt[i]);
            //  imageView.setBackgroundResource(mIconInt[i]);
            mViewFlipper.addView(imageView);

        }
        mViewFlipper.startFlipping();
        mIvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewFlipper.showPrevious();

            }
        });

        mIvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mViewFlipper.showNext();
            }
        });

       /* for(int i = 0; i< imageArray.length;i++)
        {
            menuModelArrayListCArd.add(new OfferModel(imageArray[i]));
           // menuModelArrayListCArd.add(new OfferModel(imageArrayMenu[i]));
        //    menuModelArrayList.add(new OfferModel(imageArray[i]));

        }*/

    }

    private void callmenuListAPI() {
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

//                    MenuModel menuModal = gson.fromJson(Json, MenuModel.class);

                    Menus menuModal = gson.fromJson(Json, Menus.class);

                    menuResponse(menuModal);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {
                // errorLayout.showError(message);
                // Toast.makeText(mContext, Constant.mResponseFailureMessage, Toast.LENGTH_LONG).show();
                //Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();
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
            jsonObject.addProperty("term_id", mCategoryId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        baseRequest.callAPIPost(1, jsonObject, Constant.MENUCATEGORYDETAILS);/////
    }

    private void menuResponse(Menus menuModel) {

        try {
            if (menuModel.getStatus() != null && menuModel.getStatus().equalsIgnoreCase(Constant.TRUE_SATATUS)) {
                // SharePrefs.setData(this, Constant.USERID, String.valueOf(loginModal.getResponse().getUserId()));
                //  SharePrefs.setData(this, Constant.USERNAME, String.valueOf(loginModal.getResponse().getUserFirstname()+" "+loginModal.getResponse().getUserLastname()));

                if (mMenuResponseList != null && mMenuResponseList.size() > 0)
                    mMenuResponseList.clear();

                mMenuResponseList = menuModel.getResponse();
                mMenuSliderList = menuModel.getSliderimage();
                if (mMenuResponseList.size() > 0) {
                    if (recyclerViewAdapter != null)
                        recyclerViewAdapter = null;

                    recyclerViewAdapter = new MenuAdapter(mMenuResponseList, mActivity);
                  /*  recyclerViewAdapter = new MenuAdapter(mMenuResponseList,mActivity, new CustomItemClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {
                            Log.d("Ram", "clicked position:" + position);
                            long postId = mMenuResponseList.get(position).getProductId();
                            Toast.makeText(mActivity,"jai ram ji ki",Toast.LENGTH_SHORT).show();
                            // do what ever you want to do with it
                        }
                    });*/
                    mMenuCardRecyclerView.setAdapter(recyclerViewAdapter);
                } else {
                    rlvDataContainer.setVisibility(View.GONE);
                    rlvNoInternetContainer.setVisibility(View.GONE);
                    rlvNoDataFoundContainer.setVisibility(View.VISIBLE);
                }
                // mCouponList = trainerModel.getResponse();
            } else {

                Toast.makeText(mActivity, menuModel.getMessage(), Toast.LENGTH_LONG).show();
            }

            setData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  /*  @Override
    public void onItemClick(View v, int position) {
        Log.d("Ram", "clicked position:" + position);
        long postId = mMenuResponseList.get(position).getProductId();
        Toast.makeText(mActivity,"jai ram ji ki",Toast.LENGTH_SHORT).show();
    }*/
}
