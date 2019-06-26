package com.justtannoor.justtannoor.retrofit;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;


public interface ApiInterface {

    /**
     *
     * @param remainingURL
     * @param jsonObject
     * @return
     */

    @POST
    Call<JsonElement> postData(@Url String remainingURL, @Body JsonObject jsonObject);


    @POST
    Call<JsonElement> postDataNew(@Url String remainingURL, @Body JSONObject jsonObject);
  //  Map<String, String> params

    /**
     *
     * @param remainingURL
     * @param map
     * @return
     */

    @GET
    Call<JsonElement> postDataGET(@Url String remainingURL, @QueryMap Map<String, String> map);


}

