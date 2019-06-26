package com.justtannoor.justtannoor.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.justtannoor.justtannoor.R;
import com.justtannoor.justtannoor.adapter.ImageZoomingSlideAdapter;
import com.justtannoor.justtannoor.model.GalleryModel;

import uk.co.senab.photoview.PhotoView;

public class ImageZoomingActivity extends AppCompatActivity{

    private static final String TAG = ImageZoomingActivity.class.getSimpleName();
    private PhotoView mImageView=null;
    private Context mContext = null;
    private GalleryModel galleryModel;
    private int imgLength;
    private String newPathStr;
    private String position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_zooming);
        initView();
    }

    private void initView() {

        getSupportActionBar().setSubtitle("صورة فوتوغرافية");

        // String url = getArguments().getString("fullImageUrl");
        galleryModel = (GalleryModel) getIntent().getSerializableExtra("galleryimages");
        Log.e(TAG, "galleryModel: >>"+galleryModel);

        String url = getIntent().getStringExtra("fullImageUrl");
        position = getIntent().getStringExtra("position");

        final ViewPager imgVpgr = (ViewPager) findViewById(R.id.pager);

    /*    final ImageView btnLFt = (ImageView) findViewById(R.id.btnLft);
        final ImageView btnRGt = (ImageView) findViewById(R.id.btnRght);*/

        imgLength = galleryModel.getResponse().size();

        int pos = imgVpgr.getCurrentItem();
       /* if (pos == 0) {
            btnLFt.setVisibility(View.GONE);
        }
        if (imgLength == 1) {
            btnLFt.setVisibility(View.GONE);
            btnRGt.setVisibility(View.GONE);
        }
*/
       /* btnLFt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = imgVpgr.getCurrentItem();
                imgVpgr.setCurrentItem(--pos);

            }
        });
        btnRGt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = imgVpgr.getCurrentItem();
                imgVpgr.setCurrentItem(++pos);
            }
        });*/

        /*imgVpgr.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        btnLFt.setVisibility(View.GONE);
                        btnRGt.setVisibility(View.VISIBLE);
                        if (imgLength == 1) {
                            btnRGt.setVisibility(View.GONE);
                        }
                        break;
                    case 1:
                        btnLFt.setVisibility(View.VISIBLE);
                        btnRGt.setVisibility(View.VISIBLE);
                        if (imgLength == 2) {
                            btnLFt.setVisibility(View.VISIBLE);
                            btnRGt.setVisibility(View.GONE);
                        }
                        break;
                    case 2:
                        btnLFt.setVisibility(View.VISIBLE);
                        btnRGt.setVisibility(View.GONE);
                        if (imgLength == 3) {
                            btnLFt.setVisibility(View.VISIBLE);
                        }
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });*/

        ImageZoomingSlideAdapter vWpgr = new ImageZoomingSlideAdapter(ImageZoomingActivity.this, galleryModel.getResponse());

        imgVpgr.setAdapter(vWpgr);
        imgVpgr.setCurrentItem(Integer.parseInt(position));
    }
}