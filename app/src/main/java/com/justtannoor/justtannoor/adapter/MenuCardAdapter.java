package com.justtannoor.justtannoor.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.justtannoor.justtannoor.R;
import com.justtannoor.justtannoor.Utilities.Validation;
import com.justtannoor.justtannoor.activity.MenuListActivity;
import com.justtannoor.justtannoor.custom.TextViewHead;
import com.justtannoor.justtannoor.model.OfferModel;

import java.util.ArrayList;

/**
 * Created by votive on 30/04/18.
 */

public class MenuCardAdapter extends RecyclerView.Adapter<MenuCardAdapter.ViewHolder> {

    private ArrayList<OfferModel> menuModelsList;
    private Activity mContext;

    public MenuCardAdapter(ArrayList<OfferModel> menuModelsList, Activity mContext) {

        this.menuModelsList = menuModelsList;
        this.mContext = mContext;

    }

    @Override
    public MenuCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view1 = LayoutInflater.from(mContext).inflate(R.layout.menu_carditem_cardview, parent, false);

        MenuCardAdapter.ViewHolder viewHolder1 = new MenuCardAdapter.ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(MenuCardAdapter.ViewHolder holder, final int position) {
        holder.rlvMenuMainView.getLayoutParams().width = Validation.getDeviceHeightWidth(mContext, true) / 2;
        holder.rlvMenuMainView.getLayoutParams().height = Validation.getDeviceHeightWidth(mContext, false) / 3 + 30;

        if (position == 4) {
            holder.txtSpecialOffer.setVisibility(View.VISIBLE);
        }

     /*   Picasso.with(mContext).load(galleryModelsList.get(position).getOfferImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.mImgHome);*/
        holder.mImgMenu.setImageResource(menuModelsList.get(position).getOfferImageUrl());

        //  Log.d("offer :",galleryModelsList.get(position).getOfferImageUrl());


       /* Picasso.with(mContext).load(menuModelsList.get(position).getOfferImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.mImgMenuCard);*/

        holder.rlvMenuMainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, MenuListActivity.class);
                mContext.startActivity(mIntent);

            }
        });




      /*  holder.mImgMenuCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int imgValue = menuModelsList.get(position).getOfferImageUrl();

                Intent intent = new Intent(mContext, ImageZoomingActivity.class);
//
     //           List<PostImage> strUrlList = mHomeCellItemList.get(aIndex).getPostImages();
         //      String strUrl = strUrlList.get(0).getImagePost();
                intent.putExtra("fullImageUrl", imgValue);
            //    intent.putExtra("mPostID", aAllPostDatum.getPostId());

                mContext.startActivity(intent);

            }
        });*/

        //Log.d("offer :",menuModelsList.get(position).getOfferImageUrl());

    }

    @Override
    public int getItemCount() {
        return menuModelsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImgMenu;
        public RelativeLayout rlvMenuMainView;
        public TextViewHead txtSpecialOffer;

        public ViewHolder(View v) {

            super(v);

            mImgMenu = (ImageView) v.findViewById(R.id.mImgMenu);
            txtSpecialOffer = (TextViewHead) v.findViewById(R.id.txtSpecialOffer);
            rlvMenuMainView = (RelativeLayout) v.findViewById(R.id.rlvMenuMainView);

        }
    }
}

