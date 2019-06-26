package com.justtannoor.justtannoor.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.justtannoor.justtannoor.R;
import com.justtannoor.justtannoor.Utilities.Validation;
import com.justtannoor.justtannoor.model.homeModel;

import java.util.ArrayList;

/**
 * Created by votive on 27/04/18.
 */

public class HomeAdapter extends  RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private ArrayList<homeModel> galleryModelsList;
    private Activity mContext;

    public HomeAdapter(ArrayList<homeModel> galleryModelsList, Activity mContext){

        this.galleryModelsList = galleryModelsList;
        this.mContext = mContext;

    }

    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view1 = LayoutInflater.from(mContext).inflate(R.layout.home_item_cardview,parent,false);

        HomeAdapter.ViewHolder viewHolder1 = new HomeAdapter.ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(HomeAdapter.ViewHolder holder, int position)
    {
        holder.rlvHomeMainView.getLayoutParams().width = Validation.getDeviceHeightWidth(mContext, true)/2;
        holder.rlvHomeMainView.getLayoutParams().height = Validation.getDeviceHeightWidth(mContext, false)/3+30;


     /*   Picasso.with(mContext).load(galleryModelsList.get(position).getOfferImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.mImgHome);*/
        holder.mImgHome.setImageResource(galleryModelsList.get(position).getOfferImageUrl());

      //  Log.d("offer :",galleryModelsList.get(position).getOfferImageUrl());

    }

    @Override
    public int getItemCount() {
        return galleryModelsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImgHome;
        public RelativeLayout rlvHomeMainView;

        public ViewHolder(View v){

            super(v);

            mImgHome = (ImageView) v.findViewById(R.id.mImgHome);
            rlvHomeMainView = (RelativeLayout) v.findViewById(R.id.rlvHomeMainView);

        }
    }
}

