package com.justtannoor.justtannoor.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.justtannoor.justtannoor.R;
import com.justtannoor.justtannoor.adapter.NotificationlistAdapter;
import com.justtannoor.justtannoor.model.CategoryListBean;

import java.util.ArrayList;

public class NotificationListActivity extends AppCompatActivity {

    private Context mContext = null;
    private RecyclerView notifListRecyclerview = null;
    RecyclerView.Adapter recyclerView_Adapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    ArrayList<CategoryListBean> categoryNameList;

    Integer cate_image[] = {
            R.drawable.img_menu_banner_five, R.drawable.img_menu_banner_four,R.drawable.img_menu_banner_three,R.drawable.img_menu_banner_two
    };

    String cate_Name[] = {"Car","Propery","Home","Bike"};
    String cate_Desc[] = {"You have seen many Android apps with sliding images with circle page indicator at top.","You have seen many Android apps with sliding images with circle page indicator at top. Today we are going to learn the same thing for our app. You have seen many Android apps with sliding images with circle page indicator at top.","You have seen many Android apps with sliding images with circle page indicator at top. Today we are going to learn the same thing for our app.You have seen many Android apps with sliding images with circle page indicator at top.You have seen many Android apps with sliding images with circle page indicator at top.","You have seen many Android apps with sliding images with circle page indicator at top. Today we are going to learn the same thing for our app.You have seen many Android apps with sliding images with circle page indicator at top.You have seen many Android apps with sliding images with circle page indicator at top.You have seen many Android apps with sliding images with circle page indicator at top.You have seen many Android apps with sliding images with circle page indicator at top."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        mContext = this;
        initView();
    }

    private void initView() {

        categoryNameList = new ArrayList<>();
        LinearLayoutManager lLayout;
        lLayout = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, true);
        lLayout.setStackFromEnd(false);
        notifListRecyclerview = (RecyclerView) findViewById(R.id.notifListRecyclerview);
        notifListRecyclerview.setLayoutManager(lLayout);

        for (int i = 0; i < cate_Name.length; i++) {

            categoryNameList.add(new CategoryListBean(cate_Name[i],cate_image[i],cate_Desc[i]));
        }

        recyclerView_Adapter = new NotificationlistAdapter(mContext,categoryNameList);
        notifListRecyclerview.setAdapter(recyclerView_Adapter);


    }
}
