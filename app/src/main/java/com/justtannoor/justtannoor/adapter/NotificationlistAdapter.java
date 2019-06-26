package com.justtannoor.justtannoor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.justtannoor.justtannoor.R;
import com.justtannoor.justtannoor.model.CategoryListBean;

import java.util.ArrayList;

/**
 * Created by votive on 11/04/18.
 */

public class NotificationlistAdapter extends RecyclerView.Adapter<NotificationlistAdapter.ViewHolder>{

    Context context1;
    ArrayList<CategoryListBean> arrayList;

    public NotificationlistAdapter(Context context1, ArrayList<CategoryListBean> arrayList){


        this.context1 = context1;
        this.arrayList = arrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgCatPic,imgRatingStar,imgSaveCategory;
        public TextView textNotifyName,textNotifyDesc,txtNotifyTime;
        public LinearLayout mainLinearLayout;


        public ViewHolder(View v){

            super(v);

            mainLinearLayout = (LinearLayout) v.findViewById(R.id.mainLinearLayout);

           /* imgCatPic = (ImageView) v.findViewById(R.id.imgCatPic);
            imgRatingStar = (ImageView) v.findViewById(R.id.imgRatingStar);
            imgSaveCategory = (ImageView) v.findViewById(R.id.imgSaveCategory);*/

            textNotifyName = (TextView) v.findViewById(R.id.textNotifyName);
            textNotifyDesc = (TextView) v.findViewById(R.id.textNotifyDesc);
            txtNotifyTime = (TextView) v.findViewById(R.id.txtNotifyTime);


        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View view1 = LayoutInflater.from(context1).inflate(R.layout.notificatio_list_item,parent,false);

        ViewHolder viewHolder1 = new ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position){

       // Vholder.imgCatPic.setImageResource(arrayList.get(position).getImage());

       // Vholder.mainLinearLayout.getLayoutParams().height =;
        //Vholder.mainLinearLayout.requestLayout();

        Vholder.textNotifyName.setText(arrayList.get(position).getTitle());
        Vholder.textNotifyDesc.setText(arrayList.get(position).getDesc());


    }

    @Override
    public int getItemCount(){

        return arrayList.size();
    }
}

