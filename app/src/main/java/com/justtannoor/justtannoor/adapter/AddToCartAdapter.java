package com.justtannoor.justtannoor.adapter;

import android.content.Context;
import android.provider.Settings;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.justtannoor.justtannoor.Interface.DeleteCallBack;
import com.justtannoor.justtannoor.R;
import com.justtannoor.justtannoor.Utilities.Constant;
import com.justtannoor.justtannoor.Utilities.Validation;
import com.justtannoor.justtannoor.custom.TextViewHead;
import com.justtannoor.justtannoor.model.AddCartListResponse;
import com.justtannoor.justtannoor.retrofit.BaseRequest;
import com.justtannoor.justtannoor.retrofit.RequestReciever;

import java.util.List;

/**
 * Created by poonam on 5/29/2018.
 */

public class AddToCartAdapter extends RecyclerView.Adapter<AddToCartAdapter.ViewHolder>{
    Context mContext;
    List<AddCartListResponse> arrayList;
    DeleteCallBack deleteCallBack;
    int i = 1;
    private String mAndroidDeviceID = null;
    BaseRequest baseRequest = null;
    private String mQuentity = null,mID = null,mProductID = null,mPrice = null;
    public AddToCartAdapter(Context context1, List<AddCartListResponse> arrayList,DeleteCallBack deleteCallBack)
   // public AddToCartAdapter(Context context1, List<AddCartListResponse> arrayList)
    {


        this.mContext = context1;
        this.arrayList = arrayList;
        this.deleteCallBack=deleteCallBack;

        baseRequest = new BaseRequest(mContext);
        mAndroidDeviceID = Settings.Secure.getString(mContext.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgAddCartPic,imgRatingStar,imgSaveCategory;
        public TextViewHead textAddCartName,textAddCartDesc,textAddCartSize,textAddCartPrice,tvQuentityLess,tvQuentityNumber,tvQuentityMore;
       // public TextViewHead txtAddCartDelete;
        public ImageView txtAddCartDelete,imgQuentityMore,imgQuentityLess;
        public LinearLayout mainLinearLayout;
        public RelativeLayout rlvMainLayout;


        public ViewHolder(View v){

            super(v);

            mainLinearLayout = (LinearLayout) v.findViewById(R.id.mainLinearLayout);
            rlvMainLayout = (RelativeLayout) v.findViewById(R.id.rlvMainLayout);

            imgAddCartPic = (ImageView) v.findViewById(R.id.imgAddCartPic);

            textAddCartName = (TextViewHead) v.findViewById(R.id.textAddCartName);
            textAddCartDesc = (TextViewHead) v.findViewById(R.id.textAddCartDesc);

            imgQuentityMore = (ImageView) v.findViewById(R.id.imgQuentityMore);
            imgQuentityLess = (ImageView) v.findViewById(R.id.imgQuentityLess);
            tvQuentityLess = (TextViewHead) v.findViewById(R.id.tvQuentityLess);
            tvQuentityNumber = (TextViewHead) v.findViewById(R.id.tvQuentityNumber);
            tvQuentityMore = (TextViewHead) v.findViewById(R.id.tvQuentityMore);

            textAddCartSize = (TextViewHead) v.findViewById(R.id.textAddCartSize);
            textAddCartPrice = (TextViewHead) v.findViewById(R.id.textAddCartPrice);
           // txtAddCartDelete = (TextViewHead) v.findViewById(R.id.txtAddCartDelete);

            txtAddCartDelete = (ImageView) v.findViewById(R.id.txtAddCartDelete);

        }
    }

    @Override
    public AddToCartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View view1 = LayoutInflater.from(mContext).inflate(R.layout.addto_cart_item,parent,false);

        AddToCartAdapter.ViewHolder viewHolder1 = new AddToCartAdapter.ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final AddToCartAdapter.ViewHolder holder,final int position){

        holder.rlvMainLayout.getLayoutParams().height = Validation.getDeviceHeightWidth(mContext, false)/4-40;

        try {
            /*String mmnew = arrayList.get(position).getProductName().replace("***","VIKAS");
            String [] mmNAMEText1 = mmnew.split("VIKAS");
*/
            //  holder.imgAddCartPic.setImageResource(arrayList.get(position).getImage());
            Picasso.with(mContext).load(arrayList.get(position).getProductImage())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(holder.imgAddCartPic);


            //holder.textAddCartName.setText(arrayList.get(position).getProductName());
//            holder.textAddCartName.setText(mmNAMEText1[0] +"\n"+ mmNAMEText1[1]);
            holder.textAddCartName.setText(arrayList.get(position).getProductName()+" "+arrayList.get(position).getArabicProductName());
            // holder.textAddCartDesc.setText(arrayList.get(position).getProductName());
            holder.textAddCartDesc.setVisibility(View.GONE);
            holder.textAddCartSize.setText("Food Name: "+arrayList.get(position).getSelectedFood());
            holder.textAddCartPrice.setText("Price: AED "+arrayList.get(position).getPrice());

            holder.tvQuentityNumber.setText(arrayList.get(position).getQuantity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //  holder.txtAddCartDelete.setText(arrayList.get(position).getDesc());

        holder.txtAddCartDelete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               callDeleteCartListAPI(arrayList.get(position).getId(),position);

           }
       });

        holder.imgQuentityLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  i--;
                int mmInt = Integer.parseInt(arrayList.get(position).getQuantity());
                int mQuentityP = mmInt - 1;
                mID = arrayList.get(position).getId();
                mPrice= arrayList.get(position).getPrice();
                mProductID= arrayList.get(position).getProductId();

                if(mQuentityP > 0) {
                  //  holder.tvQuentityNumber.setText("" + i);
                    holder.tvQuentityNumber.setText("" + mQuentityP);
                    mQuentity = holder.tvQuentityNumber.getText().toString();
                    callUpdateQuentityAPI( position);
                }
                else
                {
                    mQuentityP = 1;
                }


            }
        });

        holder.imgQuentityMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  i++;
                int mmInt = Integer.parseInt(arrayList.get(position).getQuantity());
                int mQuentityP = mmInt + 1;
                mID = arrayList.get(position).getId();
                mPrice= arrayList.get(position).getPrice();
                mProductID= arrayList.get(position).getProductId();
               // holder.tvQuentityNumber.setText(""+i);
                holder.tvQuentityNumber.setText(""+mQuentityP);
                mQuentity = holder.tvQuentityNumber.getText().toString();
                callUpdateQuentityAPI(position);

            }
        });


    }

    @Override
    public int getItemCount(){

        return arrayList.size();
    }


    private void callDeleteCartListAPI(String cID,final int position) {
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here
                  //  AddCartListModel galleyModal = gson.fromJson(Json, AddCartListModel.class);
                 //   addToCartResponse(galleyModal);
                    Toast.makeText(mContext, "Cart deleted successfully", Toast.LENGTH_LONG).show();

                  //  Intent mIntent = new Intent(mContext, AddtocartListActivity.class);
                  //  mContext.startActivity(mIntent);
                    deleteCallBack.callBackDelete(true, position);
                  //  arrayList.remove(position);
                  //  notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {
                // errorLayout.showError(message);
                // Toast.makeText(mContext, Constant.mResponseFailureMessage, Toast.LENGTH_LONG).show();
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNetworkFailure(int APINumber, String message) {
                // Toast.makeText(mContext, Constant.NO_INTERNET_MESSAGE, Toast.LENGTH_LONG).show();

            }
        });

        JsonObject jsonObject = new JsonObject();
        try {
            ////Put input parameter here
            jsonObject.addProperty("id", cID);
           /*  jsonObject.addProperty(Enum.USER_PASSWORD, password);
            jsonObject.addProperty(Enum.DEVICE_ID, androidDeviceId);
            jsonObject.addProperty(Enum.DEVICE_TYPE, "android");*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        baseRequest.callAPIPost(1, jsonObject, Constant.DELETE_CART_LIST);/////
    }


    private void callUpdateQuentityAPI(final int position) {
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here
                   // Toast.makeText(mContext, "Cart deleted successfully", Toast.LENGTH_LONG).show();
                    deleteCallBack.callBackDelete(true, position);
                 //   AddCartListModel galleyModal = gson.fromJson(Json, AddCartListModel.class);
                    //addToCartResponse(galleyModal);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {
                // errorLayout.showError(message);
                // Toast.makeText(mContext, Constant.mResponseFailureMessage, Toast.LENGTH_LONG).show();
                //  Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNetworkFailure(int APINumber, String message) {
                 Toast.makeText(mContext, Constant.NO_INTERNET_MESSAGE, Toast.LENGTH_LONG).show();

            }
        });

        JsonObject jsonObject = new JsonObject();
        try {
            ////Put input parameter here
            jsonObject.addProperty("visitor_id", mAndroidDeviceID);
            jsonObject.addProperty("product_id", mProductID);
            jsonObject.addProperty("quantity", mQuentity);
            jsonObject.addProperty("price", mPrice);
            jsonObject.addProperty("id", mID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        baseRequest.callAPIPost1(1, jsonObject, Constant.UPDATE_QUENTITY_API);/////
    }

}


