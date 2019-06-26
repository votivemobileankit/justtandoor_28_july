package com.justtannoor.justtannoor.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.justtannoor.justtannoor.Interface.CustomItemClickListener;
import com.justtannoor.justtannoor.R;
import com.justtannoor.justtannoor.Utilities.Constant;
import com.justtannoor.justtannoor.Utilities.Validation;
import com.justtannoor.justtannoor.activity.MenuListActivity;
import com.justtannoor.justtannoor.custom.TextViewHead;
import com.justtannoor.justtannoor.model.menu.Response;

import java.util.List;

/**
 * Created by votive on 26/04/18.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private List<Response> menuModelsList;
    private Activity mContext;
    CustomItemClickListener listener;


    public MenuAdapter(List<Response> menuModelsList, Activity mContext)
    // public MenuAdapter(List<MenuModelResponse> menuModelsList,Activity mContext,CustomItemClickListener listener)
    {

        this.menuModelsList = menuModelsList;
        this.mContext = mContext;
        this.listener = listener;
    }


    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view1 = LayoutInflater.from(mContext).inflate(R.layout.menu_item_cardview, parent, false);

        final MenuAdapter.ViewHolder viewHolder1 = new MenuAdapter.ViewHolder(view1);
      /* view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, viewHolder1.getPosition());

            }
        });*/
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(MenuAdapter.ViewHolder holder, final int position) {

        holder.rlvMenuMainView.getLayoutParams().width = Validation.getDeviceHeightWidth(mContext, true) / 2;
        holder.rlvMenuMainView.getLayoutParams().height = Validation.getDeviceHeightWidth(mContext, false) / 4 + 20;

        holder.edtPriceTag.setText("AED " + menuModelsList.get(position).getPrice());

        if (menuModelsList.get(position).getType() == 1) {
            holder.rlVipTag.setVisibility(View.VISIBLE);
        } else {
            holder.rlVipTag.setVisibility(View.GONE);
        }

        holder.tvProductTitleEnglish.setText(menuModelsList.get(position).getProductName());
        holder.tvProductTitleArabic.setText(menuModelsList.get(position).getProductArabicTitle());

        Picasso.with(mContext).load(menuModelsList.get(position).getProductImage())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.mImgMenu);

        holder.rlvMenuMainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Long gggg = menuModelsList.get(position).getProductId();
                Constant.MENU_ID_FOR_DETAILS = (int) (long) menuModelsList.get(position).getProductId();
                Intent mIntent = new Intent(mContext, MenuListActivity.class);
                //mIntent.putExtra("menuID", gggg);
                // mIntent.putExtra("menuID", menuModelsList.get(position).getProductId());
                mContext.startActivity(mIntent);
            }
        });
        // holder.mImgMenu.setImageResource(menuModelsList.get(position).getOfferImageUrl());
        //Log.d("offer :",menuModelsList.get(position).getOfferImageUrl());
    }

    @Override
    public int getItemCount() {
        return menuModelsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImgMenu;
        public TextViewHead edtPriceTag, tvProductTitleEnglish, tvProductTitleArabic;
        public RelativeLayout rlvMenuMainView, rlvPriceBgView, rlVipTag;

        public ViewHolder(View v) {
            super(v);
            mImgMenu = (ImageView) v.findViewById(R.id.mImgMenu);
            edtPriceTag = (TextViewHead) v.findViewById(R.id.edtPriceTag);
            rlvMenuMainView = (RelativeLayout) v.findViewById(R.id.rlvMenuMainView);
            rlvPriceBgView = (RelativeLayout) v.findViewById(R.id.rlvPriceBgView);
            rlVipTag = (RelativeLayout) v.findViewById(R.id.rlVipTag);
            tvProductTitleEnglish = (TextViewHead) v.findViewById(R.id.tvProductTitleEnglish);
            tvProductTitleArabic = (TextViewHead) v.findViewById(R.id.tvProductTitleArabic);
        }
    }
}