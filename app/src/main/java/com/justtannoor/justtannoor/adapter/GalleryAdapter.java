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
import com.justtannoor.justtannoor.R;
import com.justtannoor.justtannoor.Utilities.Validation;
import com.justtannoor.justtannoor.activity.ImageZoomingActivity;
import com.justtannoor.justtannoor.model.GalleryModel;
import com.justtannoor.justtannoor.model.GalleryResponse;

import java.util.List;

/**
 * Created by votive on 26/04/18.
 */

public class GalleryAdapter extends  RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private List<GalleryResponse> galleryModelsList;
    private Activity mContext;
    private GalleryModel galleryModel;

    public GalleryAdapter(List<GalleryResponse> galleryModelsList, GalleryModel galleryModel,Activity mContext){
        this.galleryModelsList = galleryModelsList;
        this.galleryModel = galleryModel;
        this.mContext = mContext;
    }
    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view1 = LayoutInflater.from(mContext).inflate(R.layout.gallery_item_cardview,parent,false);

        GalleryAdapter.ViewHolder viewHolder1 = new GalleryAdapter.ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(GalleryAdapter.ViewHolder holder, final int position)
    {
        final GalleryResponse galleryList = galleryModelsList.get(position);
      //  holder.rlvGalleryMainView
        holder.rlvGalleryMainView.getLayoutParams().width = Validation.getDeviceHeightWidth(mContext, true)/3;
        holder.rlvGalleryMainView.getLayoutParams().height = Validation.getDeviceHeightWidth(mContext, false)/6+20;

        Picasso.with(mContext).load(galleryList.getGuid())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.mImgGallery);

       // holder.mImgGallery.setImageResource(galleryList.getGuid());
        holder.mImgGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent mIntent = new Intent(mContext, CheckOutActivity.class);
                mContext.startActivity(mIntent);*/
                //int imgValue = galleryList;
                Intent intent = new Intent(mContext, ImageZoomingActivity.class);
                //           List<PostImage> strUrlList = mHomeCellItemList.get(aIndex).getPostImages();
                //      String strUrl = strUrlList.get(0).getImagePost();
                intent.putExtra("fullImageUrl", galleryList.getGuid());
                intent.putExtra("galleryimages",galleryModel);
                intent.putExtra("position",position+"");
                //    intent.putExtra("mPostID", aAllPostDatum.getPostId());
                mContext.startActivity(intent);

            }
        });
        //Log.d("offer :",galleryModelsList.get(position).getOfferImageUrl());

    }

    @Override
    public int getItemCount() {
        return galleryModelsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImgGallery;
        public RelativeLayout rlvGalleryMainView;

        public ViewHolder(View v){

            super(v);

            mImgGallery = (ImageView) v.findViewById(R.id.mImgGallery);
            rlvGalleryMainView = (RelativeLayout) v.findViewById(R.id.rlvGalleryMainView);

        }
    }
}
