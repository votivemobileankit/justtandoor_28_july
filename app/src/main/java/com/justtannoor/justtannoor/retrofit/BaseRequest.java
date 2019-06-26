package com.justtannoor.justtannoor.retrofit;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.justtannoor.justtannoor.R;
import com.justtannoor.justtannoor.Utilities.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BaseRequest extends BaseRequestParser {
    private Context mContext;
    private ApiInterface apiInterface;
    private RequestReciever requestReciever;
    private boolean runInBackground = false;
    private Dialog dialog;
    private View loaderView = null;
    private int APINumber_ = 1;
    TextView loader_tv;

    public BaseRequest(Context context) {
        mContext = context;
        apiInterface =
                ApiClient.getClient().create(ApiInterface.class);
        dialog = getProgressesDialog(context);
    }

    public void setBaseRequestListner(RequestReciever requestListner) {
        this.requestReciever = requestListner;

    }

    public Callback<JsonElement> responseCallback = new Callback<JsonElement>() {
        @Override
        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
            String responseServer = "";
            if (null != response.body()) {
                JsonElement jsonElement = (JsonElement) response.body();
                if (null != jsonElement) {
                    responseServer = jsonElement.toString();
                }

            } else if (response.errorBody() != null) {
                responseServer = readStreamFully(response.errorBody().contentLength(),
                        response.errorBody().byteStream());
            }
            logFullResponse(responseServer, "OUTPUT");
            if (parseJson(responseServer)) {
                if (mResponseCode.equals("true")) {
                    if (null != requestReciever) {
                        requestReciever.onSuccess(APINumber_, responseServer, getDataArray());
                    }
                } else if (mResponseCode.equals("false")) {
                    if (null != requestReciever) {
                        requestReciever.onFailure(1, "" + mResponseCode, Constant.mResponseNoDataMessage);
                    }
                } else {
                    if (null != requestReciever) {
                        requestReciever.onFailure(1, "" + mResponseCode, Constant.mResponseFailureMessage);
                    }
                }
            } else {
                if (null != requestReciever) {
                    requestReciever.onFailure(1, mResponseCode, responseServer);
                }
            }

            hideLoader();
        }

        @Override
        public void onFailure(Call<JsonElement> call, Throwable t) {
            if (getConnectivityStatus(mContext) == TYPE_NOT_CONNECTED) {
                if (null != requestReciever) {
                    requestReciever.onNetworkFailure(APINumber_, "Network Error");
                }
            }
            hideLoader();
        }
    };

    public void callAPIPost(final int APINumber, JsonObject jsonObject, String remainingURL) {

        APINumber_ = APINumber;
        showLoader();

        String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;

        Call<JsonElement> call = apiInterface.postData(baseURL, jsonObject);

        call.enqueue(responseCallback);
    }

    public void callAPIPost_New(final int APINumber, JSONObject jsonObject, String remainingURL) {

        APINumber_ = APINumber;
        showLoader();

        String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;

        Call<JsonElement> call = apiInterface.postDataNew(baseURL, jsonObject);

        call.enqueue(responseCallback);
    }

    public void callAPIPost1(final int APINumber, JsonObject jsonObject, String remainingURL) {

        APINumber_ = APINumber;
       // showLoader();

       /* if (jsonObject != null) {
            jsonObject = new JsonObject();
        }*/
        String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        //  logFullResponse(jsonObject.toString(), "INPUT");
        Call<JsonElement> call = apiInterface.postData(baseURL, jsonObject);
        call.enqueue(responseCallback);
    }


    public void callAPIGET(final int APINumber, Map<String, String> map, String remainingURL) {
        APINumber_ = APINumber;

       showLoader();
        String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        if (!baseURL.endsWith("?")) {
            baseURL = baseURL + "?";
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            baseURL = baseURL + entry.getKey() + "=" + entry.getValue() + "&";
        }
        Log.e("API", "callAPIGET: >>"+baseURL);

        Call<JsonElement> call = apiInterface.postDataGET(remainingURL, map);
        call.enqueue(responseCallback);
    }

    public void callAPIGET1(final int APINumber, Map<String, String> map, String remainingURL) {
        APINumber_ = APINumber;

      //  showLoader();
        String baseURL = remainingURL;
        /*if (!baseURL.endsWith("?")) {
            baseURL = baseURL + "?";
        }
        */
        for (Map.Entry<String, String> entry : map.entrySet()) {
            baseURL = baseURL + entry.getKey() + "=" + entry.getValue() + "&";
        }
        System.out.println("BaseReq INPUT URL : " + baseURL);

        Call<JsonElement> call = apiInterface.postDataGET(remainingURL, map);
        call.enqueue(responseCallback);
    }


    public void logFullResponse(String response, String inout) {
        final int chunkSize = 3000;

        if (null != response && response.length() > chunkSize) {
            int chunks = (int) Math.ceil((double) response.length()
                    / (double) chunkSize);


            for (int i = 1; i <= chunks; i++) {
                if (i != chunks) {
                    Log.i("BaseReq",
                            inout + " : "
                                    + response.substring((i - 1) * chunkSize, i
                                    * chunkSize));
                } else {
                    Log.i("BaseReq",
                            inout + " : "
                                    + response.substring((i - 1) * chunkSize,
                                    response.length()));
                }
            }
        } else {

            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.d("BaseReq", inout + " : " + jsonObject.toString(jsonObject.length()));

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("BaseReq", " logFullResponse=>  " + response);
            }

        }
    }

    private String readStreamFully(long len, InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

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

                } else if (null != dialog) {
                    dialog.show();
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
            } else if (null != dialog) {
                dialog.dismiss();
            }
        }
    }

    public int TYPE_NOT_CONNECTED = 0;
    public int TYPE_WIFI = 1;
    public int TYPE_MOBILE = 2;

    public int getConnectivityStatus(Context context) {
        if (null == context) {
            return TYPE_NOT_CONNECTED;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (null != activeNetwork && activeNetwork.isConnected()) {

            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return TYPE_WIFI;
            }

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return TYPE_MOBILE;
            }
        }
        return TYPE_NOT_CONNECTED;
    }
}
