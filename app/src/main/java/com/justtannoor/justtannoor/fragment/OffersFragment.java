package com.justtannoor.justtannoor.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.justtannoor.justtannoor.R;
import com.justtannoor.justtannoor.Utilities.Constant;
import com.justtannoor.justtannoor.adapter.OfferAdapter;
import com.justtannoor.justtannoor.model.OfferModel;
import com.justtannoor.justtannoor.model.OfferModelResponse;
import com.justtannoor.justtannoor.model.OfferModellist;
import com.justtannoor.justtannoor.model.homeModel;
import com.justtannoor.justtannoor.retrofit.BaseRequest;
import com.justtannoor.justtannoor.retrofit.RequestReciever;

import java.util.ArrayList;
import java.util.List;

public class OffersFragment extends Fragment{

    private View view;
    private RecyclerView offerRecyclerView;
    private Activity mActivity = null;
    private Context mContext =null;
    private LinearLayoutManager lLayout;
    private LinearLayout rlvDataContainer = null;
    private LinearLayout rlvNoInternetContainer = null;
    private LinearLayout rlvNoDataFoundContainer = null;
    private RecyclerView.Adapter recyclerViewAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView mIvPrevious = null;
    private ImageView mIvNext = null;
    private ViewFlipper mViewFlipper = null;

    private ArrayList<OfferModel> offerModelArrayList;
    private ArrayList<homeModel> offerModelArrayListint;

    private static List<OfferModelResponse> mOfferResponseList = null;
    //private static List<String> mGallerySliderList = null;
    private BaseRequest baseRequest;
    String[] uriStringImage = {
            "http://ahlanwasahlan.co.uk/wp-content/uploads/2014/06/IMG_3683-1024x680.jpg",
            "https://www.munatycooking.com/wp-content/uploads/2016/10/Arabian-Lamb-Stew-3.jpg",
            "http://ahlanwasahlan.co.uk/wp-content/uploads/2014/06/IMG_3683-1024x680.jpg",
            "https://www.munatycooking.com/wp-content/uploads/2016/10/Arabian-Lamb-Stew-3.jpg"};

    public OffersFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_offers, container, false);
        mContext = getActivity();
        mActivity = getActivity();

        init();
       // startOfferHttpRequest();
        return view;

    }


    public void init(){
        baseRequest = new BaseRequest(mContext);
        mOfferResponseList = new ArrayList<>();
      //  mGallerySliderList = new ArrayList<>();
        offerModelArrayListint = new ArrayList<>();
        offerModelArrayList = new ArrayList<>();

       // setData();

        lLayout = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, true);
        lLayout.setStackFromEnd(true);
        offerRecyclerView = (RecyclerView)view.findViewById(R.id.offerRecyclerView);
        rlvDataContainer = (LinearLayout) view.findViewById(R.id.rlvDataContainer);
        rlvNoInternetContainer = (LinearLayout) view.findViewById(R.id.rlvNoInternetContainer);
        rlvNoDataFoundContainer = (LinearLayout) view.findViewById(R.id.rlvNoDataFoundContainer);
     //   swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        offerRecyclerView.setLayoutManager(lLayout);

        mViewFlipper = (ViewFlipper) view.findViewById(R.id.viewFlipper);
        mIvPrevious = (ImageView) view.findViewById(R.id.mIvPrevious);
        mIvPrevious.setVisibility(View.GONE);
        mIvNext = (ImageView) view.findViewById(R.id.mIvNext);
        mIvNext.setVisibility(View.GONE);


        callOfferListAPI();

        //for (int i = 0; i < uriStringImage.length; i++) {

           /* final VideoView mVideoView = new VideoView(getActivity());

            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            mVideoView.setLayoutParams(rlp);
            mVideoView.setId(i);
            //mVideoView.setVideoURI();
            mVideoView.setVideoPath(uriString[i]);

            MediaController mediaController = new MediaController(getActivity());
            mediaController.setAnchorView(mVideoView);
            mVideoView.setMediaController(mediaController);
            mVideoView.setKeepScreenOn(true);
            mVideoView.start();

            mViewFlipper.addView(mVideoView);


            mVideoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mVideoView.start();
                }
            });*/

           /* ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            *//*Picasso.with(getActivity()).load(uriStringImage[i])
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageView);*//*

             imageView.setImageResource(R.drawable.img_offer_banner);
            mViewFlipper.addView(imageView);*/


      //  }////////////////////////////close loop here
      /*  mViewFlipper.startFlipping();
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
        });*/


    }

    public void setData(String  offerImag){

       /* for(int i = 0; i< 10; i++)
        {
            offerModelArrayListint.add(new homeModel(R.drawable.img_offer_img));
        }*/

        ImageView imageView = new ImageView(getActivity());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            Picasso.with(getActivity()).load(offerImag)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageView);

     // imageView.setImageResource(R.drawable.img_offer_banner);
        mViewFlipper.addView(imageView);
    }

    ///////////////////****************///////////////////

    private void callOfferListAPI() {
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
                    OfferModellist galleyModal = gson.fromJson(Json, OfferModellist.class);
                    offerResponse(galleyModal);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {
                // errorLayout.showError(message);
                // Toast.makeText(mContext, Constant.mResponseFailureMessage, Toast.LENGTH_LONG).show();
               // Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
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
        baseRequest.callAPIPost(1, jsonObject, Constant.OFFER_LIST);/////
    }

    private void offerResponse(OfferModellist offerModel) {

        try {
            if (offerModel.getStatus() != null && offerModel.getStatus().equalsIgnoreCase(Constant.TRUE_SATATUS)) {
                // SharePrefs.setData(this, Constant.USERID, String.valueOf(loginModal.getResponse().getUserId()));
                //  SharePrefs.setData(this, Constant.USERNAME, String.valueOf(loginModal.getResponse().getUserFirstname()+" "+loginModal.getResponse().getUserLastname()));

                if(mOfferResponseList != null && mOfferResponseList.size() > 0)
                    mOfferResponseList.clear();

                mOfferResponseList = offerModel.getResponse();

                if(mOfferResponseList.size() > 0)
                {
                    if(recyclerViewAdapter != null)
                        recyclerViewAdapter = null;

                    setData(offerModel.getOfferimages());
                    recyclerViewAdapter = new OfferAdapter(mOfferResponseList,getActivity());
                    offerRecyclerView.setAdapter(recyclerViewAdapter);
                }else{
                    rlvDataContainer.setVisibility(View.GONE);
                    rlvNoInternetContainer.setVisibility(View.GONE);
                    rlvNoDataFoundContainer.setVisibility(View.VISIBLE);
                }

            }
            else
            {

                Toast.makeText(mActivity, offerModel.getMessage(), Toast.LENGTH_LONG).show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
