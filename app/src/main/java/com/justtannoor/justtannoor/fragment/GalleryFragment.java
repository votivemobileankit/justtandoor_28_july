package com.justtannoor.justtannoor.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.justtannoor.justtannoor.R;
import com.justtannoor.justtannoor.Utilities.Constant;
import com.justtannoor.justtannoor.adapter.GalleryAdapter;
import com.justtannoor.justtannoor.model.Category;
import com.justtannoor.justtannoor.model.GalleryModel;
import com.justtannoor.justtannoor.model.GalleryResponse;
import com.justtannoor.justtannoor.model.OfferModel;
import com.justtannoor.justtannoor.model.homeModel;
import com.justtannoor.justtannoor.retrofit.BaseRequest;
import com.justtannoor.justtannoor.retrofit.RequestReciever;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment {


    private RecyclerView offerRecyclerView;
    private ArrayList<Category> categoryArrayList;

    private Activity mActivity = null;
    private View view;
    private LinearLayoutManager lLayout;
    private RelativeLayout rlvMainGalleryListLayout;
    private RecyclerView.Adapter recyclerViewAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private LinearLayout rlvDataContainer = null;
    private LinearLayout rlvNoInternetContainer = null;
    private LinearLayout rlvNoDataFoundContainer = null;

    private ImageView mIvPrevious = null;
    private ImageView mIvNext = null;
    private ViewFlipper mViewFlipper = null;
    private RecyclerView mGalleryRecyclerView = null;
    private static List<GalleryResponse> mGalleryResponseList = null;
    private static List<String> mGallerySliderList = null;

    private ArrayList<OfferModel> galleryModelArrayList;
    private ArrayList<homeModel> HomeModelArrayListint;

    private int[] imageArray = {R.drawable.img_gallery_one, R.drawable.img_gallery_two, R.drawable.img_gallery_three, R.drawable.img_gallery_four, R.drawable.img_gallery_five, R.drawable.img_gallery_one, R.drawable.img_gallery_two, R.drawable.img_gallery_three, R.drawable.img_gallery_four, R.drawable.img_gallery_five, R.drawable.img_gallery_one, R.drawable.img_gallery_two, R.drawable.img_gallery_three, R.drawable.img_gallery_four, R.drawable.img_gallery_five, R.drawable.img_gallery_one, R.drawable.img_gallery_two, R.drawable.img_gallery_three, R.drawable.img_gallery_four, R.drawable.img_gallery_five, R.drawable.img_gallery_one, R.drawable.img_gallery_two, R.drawable.img_gallery_three, R.drawable.img_gallery_four, R.drawable.img_gallery_five, R.drawable.img_gallery_one, R.drawable.img_gallery_two, R.drawable.img_gallery_three, R.drawable.img_gallery_four, R.drawable.img_gallery_five};


    String[] uriString = {
            "https://www.videvo.net/videvo_files/converted/2013_09/preview/hd0437.mov79503.webm",
            "https://www.videvo.net/videvo_files/converted/2013_09/preview/hd0437.mov79503.webm",
            "https://www.videvo.net/videvo_files/converted/2013_09/preview/hd0437.mov79503.webm",
            "https://www.videvo.net/videvo_files/converted/2013_09/preview/hd0437.mov79503.webm"};

    String[] uriStringImage = {
            "http://ahlanwasahlan.co.uk/wp-content/uploads/2014/06/IMG_3683-1024x680.jpg",
            "https://www.munatycooking.com/wp-content/uploads/2016/10/Arabian-Lamb-Stew-3.jpg",
            "http://ahlanwasahlan.co.uk/wp-content/uploads/2014/06/IMG_3683-1024x680.jpg",
            "https://www.munatycooking.com/wp-content/uploads/2016/10/Arabian-Lamb-Stew-3.jpg"};
    private int[] mIconInt = {
            R.drawable.img_menu_banner, R.drawable.img_menu_banner_five, R.drawable.img_menu_banner_four, R.drawable.img_menu_banner_three, R.drawable.img_menu_banner_two
    };

    private BaseRequest baseRequest;
    private int index = 0;

    public GalleryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gallery, container, false);
        mActivity = getActivity();
        initView();
        return view;
    }

    private void initView() {
        try {
            baseRequest = new BaseRequest(mActivity);
            mGalleryResponseList = new ArrayList<>();
            mGallerySliderList = new ArrayList<>();
            galleryModelArrayList = new ArrayList<>();
            HomeModelArrayListint = new ArrayList<>();
            //  setData();
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
            gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mGalleryRecyclerView = (RecyclerView) view.findViewById(R.id.mGalleryRecyclerView);
            rlvDataContainer = (LinearLayout) view.findViewById(R.id.rlvDataContainer);
            rlvNoInternetContainer = (LinearLayout) view.findViewById(R.id.rlvNoInternetContainer);
            rlvNoDataFoundContainer = (LinearLayout) view.findViewById(R.id.rlvNoDataFoundContainer);
            mGalleryRecyclerView.setLayoutManager(gridLayoutManager);
            mViewFlipper = (ViewFlipper) view.findViewById(R.id.viewFlipper);
            mIvPrevious = (ImageView) view.findViewById(R.id.mIvPrevious);
            mIvNext = (ImageView) view.findViewById(R.id.mIvNext);
            callGalleryListAPI();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void setData() {
        //  for (int i = 0; i < mIconInt.length; i++)
        if (mGallerySliderList.size() > 0) {
            mIvPrevious.setVisibility(View.VISIBLE);
            mIvNext.setVisibility(View.VISIBLE);
        } else {
            mIvPrevious.setVisibility(View.GONE);
            mIvNext.setVisibility(View.GONE);
        }

        for (int i = 0; i < mGallerySliderList.size(); i++) {
            try {
                ImageView imageView = new ImageView(getActivity());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Picasso.with(getActivity()).load(mGallerySliderList.get(i))
                        .placeholder(R.mipmap.ic_launcher)
                        .into(imageView);

                mViewFlipper.addView(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }


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
    }

    private void callGalleryListAPI() {
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here
                    //  rlvMainGalleryListLayout.setVisibility(View.VISIBLE);
                    rlvDataContainer.setVisibility(View.VISIBLE);
                    rlvNoInternetContainer.setVisibility(View.GONE);
                    rlvNoDataFoundContainer.setVisibility(View.GONE);
                    GalleryModel galleyModal = gson.fromJson(Json, GalleryModel.class);
                    galleryResponse(galleyModal);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {
                // errorLayout.showError(message);
                // Toast.makeText(mContext, Constant.mResponseFailureMessage, Toast.LENGTH_LONG).show();
                // Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();

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
           /* jsonObject.addProperty(Enum.USER_EMAIL, email);
            jsonObject.addProperty(Enum.USER_PASSWORD, password);
            jsonObject.addProperty(Enum.DEVICE_ID, androidDeviceId);
            jsonObject.addProperty(Enum.DEVICE_TYPE, "android");*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        baseRequest.callAPIPost(1, jsonObject, Constant.GALLERY_LIST);/////
    }

    private void galleryResponse(GalleryModel galleryModel) {

        try {
            if (galleryModel.getStatus() != null && galleryModel.getStatus().equalsIgnoreCase(Constant.TRUE_SATATUS)) {

                if (mGalleryResponseList != null && mGalleryResponseList.size() > 0)
                    mGalleryResponseList.clear();

                mGalleryResponseList = galleryModel.getResponse();
                mGallerySliderList = galleryModel.getSliderimage();
                // mCouponList = trainerModel.getResponse();
                if (mGalleryResponseList.size() > 0) {
                    if (recyclerViewAdapter != null)
                        recyclerViewAdapter = null;

                    recyclerViewAdapter = new GalleryAdapter(mGalleryResponseList,galleryModel,getActivity());
                    mGalleryRecyclerView.setAdapter(recyclerViewAdapter);
                } else {
                    rlvDataContainer.setVisibility(View.GONE);
                    rlvNoInternetContainer.setVisibility(View.GONE);
                    rlvNoDataFoundContainer.setVisibility(View.VISIBLE);
                }
            } else {

                Toast.makeText(mActivity, galleryModel.getMessage(), Toast.LENGTH_LONG).show();
            }
            setData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
