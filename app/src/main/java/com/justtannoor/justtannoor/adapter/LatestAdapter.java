package com.justtannoor.justtannoor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.justtannoor.justtannoor.R;
import com.justtannoor.justtannoor.model.Category;

import java.util.ArrayList;

/**
 * Created by ADMIN on 4/7/2018.
 */

public class LatestAdapter extends RecyclerView.Adapter<LatestAdapter.ViewHolder>{

    Context context1;
    ArrayList<Category> arrayList;

    public LatestAdapter(Context context1, ArrayList<Category> arrayList){


        this.context1 = context1;
        this.arrayList = arrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;

        public ViewHolder(View v){

            super(v);

            imageView = (ImageView) v.findViewById(R.id.imageView);

        }
    }

    @Override
    public LatestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View view1 = LayoutInflater.from(context1).inflate(R.layout.horizontal_card_item,parent,false);

        LatestAdapter.ViewHolder viewHolder1 = new LatestAdapter.ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(LatestAdapter.ViewHolder Vholder, int position){

        Vholder.imageView.setImageResource(arrayList.get(position).getImage());

    }

    @Override
    public int getItemCount(){

        return arrayList.size();
    }
}
