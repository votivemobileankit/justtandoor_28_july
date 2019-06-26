package com.justtannoor.justtannoor.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.justtannoor.justtannoor.R;
import com.justtannoor.justtannoor.Utilities.AppController;
import com.justtannoor.justtannoor.Utilities.Constant;
import com.justtannoor.justtannoor.Utilities.ConstantUtil;
import com.justtannoor.justtannoor.Utilities.NetworkUtil;
import com.justtannoor.justtannoor.Utilities.SharedPreferencesUtil;
import com.justtannoor.justtannoor.Utilities.VolleyMultipartRequest;
import com.justtannoor.justtannoor.custom.TextViewHead;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadBankSlipActivity extends AppCompatActivity{

    private TextViewHead edtInfoIfscCode, edtInfoAccountHolderName, edtInfoAccountNumber,edtInfoBankname;
    private ImageView ivImageBankSlip;
    private Button btnImageUpload, btn_proceed;
    private Context mContext;
    private static final String TAG = "UploadBankSlipActivity";
    private Bitmap bitmap;
    private File destination = null;
    private InputStream inputStreamImg;
    private String imgPath = null;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    private Uri selectedImage;
    private String filedata;
    private String extension;
    private byte[] image;
    private ProgressDialog dialog;
    private static final int STORAGE_PERMISSION_CODE = 123;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    private boolean runInBackground = false;

    TextView loader_tv;
    private View loaderView = null;
    private Dialog mDialog;
    String mOrderStatus,mOrderID,mBankName,mBankAccount,mBankIfsc,mBankHolderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_bank_slip);
        mContext = this;
        initview();
    }

    public void initview() {
      //  getSupportActionBar().setSubtitle("تحميل زلة البنك");
        getSupportActionBar().setSubtitle("تحميل الإيداع البنكي");
        edtInfoAccountHolderName = findViewById(R.id.edtInfoAccountHolderName);
        edtInfoAccountNumber = findViewById(R.id.edtInfoAccountNumber);
        edtInfoIfscCode = findViewById(R.id.edtInfoIfscCode);
        edtInfoBankname = findViewById(R.id.edtInfoBankname);

         mOrderStatus = SharedPreferencesUtil.getData(mContext, Constant.PLACE_ORDER_STATUS);
         mOrderID = SharedPreferencesUtil.getData(mContext, Constant.PLACE_ORDER_ID);
        mBankAccount = SharedPreferencesUtil.getData(mContext, Constant.PLACE_AC_NUMBER);
      //  mBankName = SharedPreferencesUtil.getData(mContext, Constant.PLACE_BANK_NAME);
        mBankHolderName = SharedPreferencesUtil.getData(mContext, Constant.PLACE_AC_NAME);
        mBankIfsc = SharedPreferencesUtil.getData(mContext, Constant.PLACE_BANK_IFSC);
        mBankName = SharedPreferencesUtil.getData(mContext, Constant.PLACE_BANK_NAME);
        //SharedPreferencesUtil.setData(mContext, Constant.PLACE_BANK_NAME, getBankDetailModal.getResponse().getBankName());

        edtInfoAccountHolderName.setText(mBankHolderName);
        edtInfoAccountNumber.setText(mBankAccount);
        edtInfoIfscCode.setText(mBankIfsc);
        edtInfoBankname.setText(mBankName);

        ivImageBankSlip = findViewById(R.id.ivImageBankSlip);

        btnImageUpload = findViewById(R.id.btnImageUpload);
        btn_proceed = findViewById(R.id.btn_proceed);
        mDialog = getProgressesDialog(mContext);
        dialog = new ProgressDialog(mContext);


        btnImageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndRequestPermissions();
                selectImage();
            }
        });

        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoader();
                uploadBankSlip();
            }
        });

    }
    // Select image from camera and gallery
    private void selectImage() {
        try {
            PackageManager pm = getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Take Photo", "Choose From Gallery","Cancel"};
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
                builder.setTitle("Select Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            dialog.dismiss();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, PICK_IMAGE_CAMERA);
                        } else if (options[item].equals("Choose From Gallery")) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent();
                            pickPhoto.setType("image/*");
                            pickPhoto.setAction(Intent.ACTION_GET_CONTENT);
                          //  Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            //Intent pickPhoto = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else
                Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        inputStreamImg = null;
        if (requestCode == PICK_IMAGE_CAMERA) {
            try {
                /* selectedImage = data.getData();
                 data.get

                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

                Log.e("Activity", "Pick from Camera::>>> ");

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                destination = new File(Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name), "IMG_" + timeStamp + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
*/

                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(),"temp.jpg");
                FileOutputStream fo;
                try {
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                filedata = destination.getAbsolutePath();

              //  filedata = getPath(selectedImage);
                image = ConstantUtil.readBytesFromFile(filedata);
                ivImageBankSlip.setImageBitmap(thumbnail);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_GALLERY) {
             selectedImage = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                Log.e("Activity", "Pick from Gallery::>>> ");

                filedata = getPath(selectedImage);
                image = ConstantUtil.readBytesFromFile(filedata);
                ivImageBankSlip.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void uploadBankSlip() {

        dialog.setMessage("uploading, please wait.");
        dialog.show();



        if (NetworkUtil.isNetworkAvailable(mContext)) {
            
            final VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, Constant.UPLOADSLIP_API, new Response.Listener<NetworkResponse>()////Place user for service
            {
                @Override
                public void onResponse(NetworkResponse response) {

                    dialog.dismiss();
                    String resultResponse = new String(response.data);
                    Log.e(TAG, "onResponse: >>" + resultResponse);

                    try {

                        JSONObject jsonObject = new JSONObject(resultResponse);
                        String status = jsonObject.getString("status");
                        String message = jsonObject.getString("message");
                        JSONObject data = jsonObject.getJSONObject("response");

                        if (status.equalsIgnoreCase("true")) {

                            ivImageBankSlip.setBackgroundResource(R.mipmap.ic_launcher);
                            SharedPreferencesUtil.setData(mContext, Constant.PLACE_ORDER_STATUS,"false");
                           // Constant.PLACE_ORDER_STATUS = "false";
                             SharedPreferencesUtil.setData(mContext, Constant.PLACE_ORDER_ID,"");
                            SharedPreferencesUtil.setData(mContext, Constant.PLACE_AC_NUMBER,"");
                            SharedPreferencesUtil.setData(mContext, Constant.PLACE_AC_NAME,"");
                            SharedPreferencesUtil.setData(mContext, Constant.PLACE_BANK_IFSC,"");

                            Toast.makeText(mContext,"Slip Uploaded Successfully.",Toast.LENGTH_LONG).show();
                            finish();

                        }
                        hideLoader();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        hideLoader();
                        dialog.dismiss();
                    }

                    Log.e(TAG, "onResponse: >>"+response.toString() );
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hideLoader();
                    dialog.dismiss();
                    error.getMessage();
                    Log.e(TAG, "onErrorResponse: >>"+error.toString() );
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("order_id", mOrderID);
                  //  params.put("order_id", "25435222");
                    return params;
                }

                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();

                    String str = filedata.substring(filedata.lastIndexOf("/") + 1);///////////open comment by vikas
                    params.put("image_upload", new DataPart(str, image));////////////////put image here///////////open comment by vikas
                    return params;
                }
            };

            multipartRequest.setShouldCache(false);
            multipartRequest.setTag(TAG);
            multipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(multipartRequest);


        } else {
            hideLoader();
            Toast.makeText(mContext,"No Internet Avaliable.", Toast.LENGTH_LONG).show();

        }
    }


    private boolean checkAndRequestPermissions() {

        int SD_CARD = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int CAMERA = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int LOCATION = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (SD_CARD != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (CAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (LOCATION != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == STORAGE_PERMISSION_CODE) {

//If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
//Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }

            if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//Displaying a toast
                Toast.makeText(this, "Permission granted now you can use camera", Toast.LENGTH_LONG).show();
            } else {
//Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }

            if (grantResults.length > 0 && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
//Displaying a toast
                Toast.makeText(this, "Permission granted now you can use user location", Toast.LENGTH_LONG).show();
            } else {
//Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    //////////////////////////////loding
    public Dialog getProgressesDialog(Context ct) {
        Dialog dialog = new Dialog(ct);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_dialog_loader);
        dialog.setCanceledOnTouchOutside(false);
        loader_tv = (TextView)dialog.findViewById(R.id.loader_tv);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        return dialog;
    }

    public void showLoader() {
        try {
            if (!runInBackground) {
                if (null != loaderView) {

                    loaderView.setVisibility(View.VISIBLE);

                } else if (null != mDialog) {
                    mDialog.show();
                }
            }
        }catch (Exception e){
            e.printStackTrace();

        }

    }

    public void hideLoader() {
        if (!runInBackground) {
            if (null != loaderView) {
                loaderView.setVisibility(View.GONE);
            } else if (null != mDialog) {
                mDialog.dismiss();
            }
        }
    }

}
