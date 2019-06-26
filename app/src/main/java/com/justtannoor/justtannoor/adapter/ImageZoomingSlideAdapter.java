package com.justtannoor.justtannoor.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.justtannoor.justtannoor.R;
import com.justtannoor.justtannoor.model.GalleryResponse;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by ABC on 11/24/2016.
 */
public class ImageZoomingSlideAdapter extends PagerAdapter {

    Context mContext;
    List<GalleryResponse> galleryResponseList;
    private LayoutInflater inflater;
    private PhotoView imgDisplay;


    public ImageZoomingSlideAdapter(Context context, List<GalleryResponse> galleryResponseList) {
        mContext = context;
        this.galleryResponseList = galleryResponseList;
    }

    @Override
    public int getCount() {
        return galleryResponseList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object){
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final ProgressBar progress;
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View viewLayout = inflater.inflate(R.layout.cstm_img_slider_adptr, container,
                false);

        imgDisplay = viewLayout.findViewById(R.id.iv_zoom_photo);
        imgDisplay.setVisibility(View.VISIBLE);

        progress = viewLayout.findViewById(R.id.progress);
        String url = galleryResponseList.get(position).getGuid();

        imgDisplay.setZoomable(false);
        String newPathStr = "";
        if (url != null) {
            String imagePath = url.trim();
            newPathStr = imagePath.replaceAll(" ", "%20");

            Picasso.with(mContext)
                    .load(newPathStr).into(imgDisplay, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    if (imgDisplay != null) {
                        imgDisplay.setZoomable(true);
                    }
                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    progress.setVisibility(View.GONE);
                }
            });
        } else {
            System.out.println("newPathStr = " + newPathStr);
        }

        /*Glide.with(mContext).load(galleryResponseList.get(position).getGuid())
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progress.setVisibility(View.GONE);
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progress.setVisibility(View.GONE);
                        return false;
                    }
                }).into(imgDisplay);*/

        ((ViewPager) container).addView(viewLayout);
        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
    }
}