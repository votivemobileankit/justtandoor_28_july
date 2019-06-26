package com.justtannoor.justtannoor.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.justtannoor.justtannoor.R;
import com.justtannoor.justtannoor.Utilities.Validation;
import com.justtannoor.justtannoor.activity.MenusActivity;
import com.justtannoor.justtannoor.model.menucategories.MenuCategories;


public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private MenuCategories menuCategories;
    private Context mContext;

    public CategoriesAdapter(MenuCategories menuCategories, Context mContext) {
        this.menuCategories = menuCategories;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(mContext).inflate(R.layout.row_menucategories, parent, false);
        CategoriesAdapter.ViewHolder viewHolder1 = new CategoriesAdapter.ViewHolder(view1);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ivMenusCategoryImages.getLayoutParams().height = Validation.getDeviceHeightWidth(mContext, false) / 4;
        Picasso.with(mContext).load(menuCategories.getResponse().get(position).getTermImage())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.ivMenusCategoryImages);
    }

    @Override
    public int getItemCount() {
        return menuCategories.getResponse().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView ivMenusCategoryImages;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            ivMenusCategoryImages = (ImageView) v.findViewById(R.id.ivMenusCategoryImages);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            Intent intent = new Intent(mContext, MenusActivity.class);
            intent.putExtra("CATEGORY_NAME",menuCategories.getResponse().get(position).getTermName());
            intent.putExtra("CATEGORY_ARABIC_NAME",menuCategories.getResponse().get(position).getArabicTermName());
            intent.putExtra("CATEGORY_ID",menuCategories.getResponse().get(position).getTermId()+"");
            mContext.startActivity(intent);
        }
    }
}
