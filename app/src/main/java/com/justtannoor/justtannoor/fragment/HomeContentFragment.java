package com.justtannoor.justtannoor.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.justtannoor.justtannoor.R;
import com.justtannoor.justtannoor.Utilities.Validation;
import com.justtannoor.justtannoor.custom.LogoTextView;
import com.justtannoor.justtannoor.model.Category;
import com.justtannoor.justtannoor.model.OfferModel;
import com.justtannoor.justtannoor.model.homeModel;

import java.util.ArrayList;


public class HomeContentFragment extends Fragment  {

    private View view;
    private RecyclerView offerRecyclerView;
    private ArrayList<Category> categoryArrayList;
    private Activity mActivity = null;
    private LinearLayoutManager lLayout;
    private RecyclerView.Adapter recyclerViewAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView mIvPrevious = null;
    private ImageView logoo = null;
    private TextView txtTannoorText1;
    private LogoTextView txtTannoorText;
    private ImageView mIvNext = null;
    private ViewFlipper mViewFlipper = null;
    private RecyclerView mHomeRecyclerView = null;
    private ArrayList<OfferModel> HomeModelArrayList;
    private ArrayList<homeModel> HomeModelArrayListint;

    private int[] mIconInt = {
            R.drawable.img_menu_banner_five,R.drawable.img_menu_banner_four,R.drawable.img_menu_banner_three,R.drawable.img_menu_banner_two
    };

    String[] uriString = {
            "https://www.videvo.net/videvo_files/converted/2013_09/preview/hd0437.mov79503.webm",
            "https://www.videvo.net/videvo_files/converted/2013_09/preview/hd0437.mov79503.webm",
            "https://www.videvo.net/videvo_files/converted/2013_09/preview/hd0437.mov79503.webm",
            "https://www.videvo.net/videvo_files/converted/2013_09/preview/hd0437.mov79503.webm"};

    private int[] imageArray = {R.drawable.imh_home_one, R.drawable.img_home_two, R.drawable.img_home_two, R.drawable.imh_home_one, R.drawable.img_home_two, R.drawable.imh_home_one,R.drawable.imh_home_one, R.drawable.img_home_two, R.drawable.img_home_two, R.drawable.imh_home_one,R.drawable.imh_home_one, R.drawable.img_home_two, R.drawable.img_home_two, R.drawable.imh_home_one,R.drawable.imh_home_one, R.drawable.img_home_two, R.drawable.img_home_two, R.drawable.imh_home_one,R.drawable.imh_home_one, R.drawable.img_home_two, R.drawable.img_home_two, R.drawable.imh_home_one,R.drawable.imh_home_one, R.drawable.img_home_two, R.drawable.img_home_two, R.drawable.imh_home_one};

    private int index = 0;

    public HomeContentFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view  = inflater.inflate(R.layout.fragment_home_content, container, false);
        mActivity = getActivity();

        initView();
        return view;
    }

    private void initView() {

        logoo =  view.findViewById(R.id.logoo);
        txtTannoorText =  view.findViewById(R.id.txtTannoorText);
        txtTannoorText1 =  view.findViewById(R.id.txtTannoorText1);
        int mmmint = Validation.getDeviceHeightWidth(getActivity(),false)/38;
      //  logoo.setMaxHeight(Validation.getDeviceHeightWidth(getActivity(),false)/4+200);
    //    logoo.setMinimumHeight(mmmint);
        logoo.setPadding(mmmint,mmmint,mmmint,mmmint);

        txtTannoorText.setTextSize(Validation.getDeviceHeightWidth(getActivity(),false)/42);
        txtTannoorText1.setTextSize(Validation.getDeviceHeightWidth(getActivity(),false)/37);



    }

}
