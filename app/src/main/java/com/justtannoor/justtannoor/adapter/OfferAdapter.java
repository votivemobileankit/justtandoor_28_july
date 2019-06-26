package com.justtannoor.justtannoor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.justtannoor.justtannoor.R;
import com.justtannoor.justtannoor.Utilities.Validation;
import com.justtannoor.justtannoor.custom.TextViewHead;
import com.justtannoor.justtannoor.model.OfferModelResponse;

import java.util.List;


public class OfferAdapter extends  RecyclerView.Adapter<OfferAdapter.ViewHolder> {

    private List<OfferModelResponse> offerModelsList;
    private Context mContext;

    public OfferAdapter(List<OfferModelResponse> offerModelsList, Context mContext){

        this.offerModelsList = offerModelsList;
        this.mContext = mContext;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view1 = LayoutInflater.from(mContext).inflate(R.layout.offer_item_cardview,parent,false);

        OfferAdapter.ViewHolder viewHolder1 = new OfferAdapter.ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {

      //  holder.lvlOfferMainitemLayout.getLayoutParams().width = Validation.getDeviceHeightWidth(mContext, true)/3;
      // holder.lvlOfferMainitemLayout.getLayoutParams().height = Validation.getDeviceHeightWidth(mContext, false)/4;
       holder.rlvOfferMainView.getLayoutParams().height = Validation.getDeviceHeightWidth(mContext, false)/4;

        Picasso.with(mContext).load(offerModelsList.get(position).getProductImage())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imageView);



      //  holder.lvlOfferMainitemLayout.setBackgroundResource(Integer.parseInt(offerModelsList.get(position).getOfferImageUrl()));
      //  holder.imageView.setImageResource(offerModelsList.get(position).getOfferImageUrl());
      holder.txtPersentage.setText(offerModelsList.get(position).getDiscountPrice()+"%");
        //Log.d("offer :",offerModelsList.get(position).getOfferImageUrl());

    }

    @Override
    public int getItemCount() {
        return offerModelsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextViewHead txtPersentage;
        public LinearLayout lvlOfferMainitemLayout;
        public RelativeLayout rlvOfferMainView;

        public ViewHolder(View v){

            super(v);

            imageView = (ImageView) v.findViewById(R.id.offerImageView);
            txtPersentage = (TextViewHead) v.findViewById(R.id.txtPersentage);
            lvlOfferMainitemLayout = (LinearLayout) v.findViewById(R.id.lvlOfferMainitemLayout);
            rlvOfferMainView = (RelativeLayout) v.findViewById(R.id.rlvOfferMainView);

        }
    }
}
