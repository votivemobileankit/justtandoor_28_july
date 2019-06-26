package com.justtannoor.justtannoor.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.justtannoor.justtannoor.R;
import com.justtannoor.justtannoor.Utilities.Constant;
import com.justtannoor.justtannoor.adapter.CategoriesAdapter;
import com.justtannoor.justtannoor.model.menucategories.MenuCategories;
import com.justtannoor.justtannoor.retrofit.BaseRequest;
import com.justtannoor.justtannoor.retrofit.RequestReciever;

/**
 * A simple {@link Fragment} subclass.
 */
//public class MenuFragment extends Fragment implements CustomItemClickListener
public class MenuFragment extends Fragment {

    private View mView;
    private FragmentActivity mActivity;
    private Intent intent;
    private RecyclerView mRvFoodCategories;
    private LinearLayoutManager mLinearLayoutManager;
    private BaseRequest baseRequest;
    private ImageView ivCategoryBg = null;
    private LinearLayout rlvDataContainer = null;
    private LinearLayout rlvNoInternetContainer = null;
    private LinearLayout rlvNoDataFoundContainer = null;

    public MenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_menu, container, false);
        mActivity = getActivity();
        initView();
        return mView;
    }

    private void initView() {
        baseRequest = new BaseRequest(mActivity);
        ivCategoryBg = mView.findViewById(R.id.ivCategoryBg);
        rlvDataContainer = mView.findViewById(R.id.rlvDataContainer);
        rlvNoInternetContainer = mView.findViewById(R.id.rlvNoInternetContainer);
        rlvNoDataFoundContainer = mView.findViewById(R.id.rlvNoDataFoundContainer);
        mRvFoodCategories = mView.findViewById(R.id.rvFoodCategories);
        mLinearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, true);
        mLinearLayoutManager.setStackFromEnd(true);
        mRvFoodCategories.setLayoutManager(mLinearLayoutManager);

        getMenuCategories();
    }

    private void getMenuCategories() {
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                try {
                    rlvDataContainer.setVisibility(View.VISIBLE);
                    rlvNoInternetContainer.setVisibility(View.GONE);
                    rlvNoDataFoundContainer.setVisibility(View.GONE);
                    Gson gson = new Gson();
                    MenuCategories menuCategories = gson.fromJson(Json, MenuCategories.class);

                    Picasso.with(getActivity()).load(menuCategories.getBackgroundImage())
                            .placeholder(R.mipmap.ic_launcher)
                            .into(ivCategoryBg);

                    CategoriesAdapter categoriesAdapter = new CategoriesAdapter(menuCategories, getActivity());
                    mRvFoodCategories.setAdapter(categoriesAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {
                rlvDataContainer.setVisibility(View.GONE);
                rlvNoInternetContainer.setVisibility(View.GONE);
                rlvNoDataFoundContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNetworkFailure(int APINumber, String message) {
                rlvDataContainer.setVisibility(View.GONE);
                rlvNoInternetContainer.setVisibility(View.VISIBLE);
                rlvNoDataFoundContainer.setVisibility(View.GONE);
            }
        });
        JsonObject jsonObject = new JsonObject();
        /*try {
            ////Put input parameter here
            jsonObject.addProperty(Enum.USER_EMAIL, email);
            jsonObject.addProperty(Enum.USER_PASSWORD, password);
            jsonObject.addProperty(Enum.DEVICE_ID, androidDeviceId);
            jsonObject.addProperty(Enum.DEVICE_TYPE, "android");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        baseRequest.callAPIPost(1, jsonObject, Constant.MENUCATEGORYTYPE);/////
    }

}