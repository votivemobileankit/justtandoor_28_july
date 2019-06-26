package com.justtannoor.justtannoor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CounterResponse implements Serializable {


    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("count_pickup")
    @Expose
    private String countPickup;
    @SerializedName("count_delevery")
    @Expose
    private String countDelevery;
    @SerializedName("pickup_order_limit")
    @Expose
    private String pickupOrderLimit;
    @SerializedName("delivery_order_limit")
    @Expose
    private String deliveryOrderLimit;
    @SerializedName("total_order_count")
    @Expose
    private String totalOrderCount;
    @SerializedName("total_order_limit")
    @Expose
    private String totalOrderLimit;
    @SerializedName("fix_kilometre_delivery_charge")
    @Expose
    private int fixKilometreDeliveryCharge;
    @SerializedName("per_kilometre_delivery_charge")
    @Expose
    private double perKilometreDeliveryCharge;
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;
    @SerializedName("type")
    @Expose
    private int type;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCountPickup() {
        return countPickup;
    }

    public void setCountPickup(String countPickup) {
        this.countPickup = countPickup;
    }

    public String getCountDelevery() {
        return countDelevery;
    }

    public void setCountDelevery(String countDelevery) {
        this.countDelevery = countDelevery;
    }

    public String getPickupOrderLimit() {
        return pickupOrderLimit;
    }

    public void setPickupOrderLimit(String pickupOrderLimit) {
        this.pickupOrderLimit = pickupOrderLimit;
    }

    public String getDeliveryOrderLimit() {
        return deliveryOrderLimit;
    }

    public void setDeliveryOrderLimit(String deliveryOrderLimit) {
        this.deliveryOrderLimit = deliveryOrderLimit;
    }

    public String getTotalOrderCount() {
        return totalOrderCount;
    }

    public void setTotalOrderCount(String totalOrderCount) {
        this.totalOrderCount = totalOrderCount;
    }

    public String getTotalOrderLimit() {
        return totalOrderLimit;
    }

    public void setTotalOrderLimit(String totalOrderLimit) {
        this.totalOrderLimit = totalOrderLimit;
    }

    public int getFixKilometreDeliveryCharge() {
        return fixKilometreDeliveryCharge;
    }

    public void setFixKilometreDeliveryCharge(int fixKilometreDeliveryCharge) {
        this.fixKilometreDeliveryCharge = fixKilometreDeliveryCharge;
    }

    public double getPerKilometreDeliveryCharge() {
        return perKilometreDeliveryCharge;
    }

    public void setPerKilometreDeliveryCharge(double perKilometreDeliveryCharge) {
        this.perKilometreDeliveryCharge = perKilometreDeliveryCharge;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

   /* @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("count_pickup")
    @Expose
    private int countPickup;
    @SerializedName("count_delevery")
    @Expose
    private int countDelevery;
    @SerializedName("pickup_order_limit")
    @Expose
    private int pickupOrderLimit;
    @SerializedName("delivery_order_limit")
    @Expose
    private int deliveryOrderLimit;
    @SerializedName("total_order_limit")
    @Expose
    private int totalOrderLimit;
    @SerializedName("total_order_count")
    @Expose
    private int totalOrderCount;

    @SerializedName("type")
    @Expose
    private int type;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCountPickup() {
        return countPickup;
    }

    public void setCountPickup(int countPickup) {
        this.countPickup = countPickup;
    }

    public int getCountDelevery() {
        return countDelevery;
    }

    public void setCountDelevery(int countDelevery) {
        this.countDelevery = countDelevery;
    }

    public int getPickupOrderLimit() {
        return pickupOrderLimit;
    }

    public void setPickupOrderLimit(int pickupOrderLimit) {
        this.pickupOrderLimit = pickupOrderLimit;
    }

    public int getDeliveryOrderLimit() {
        return deliveryOrderLimit;
    }

    public void setDeliveryOrderLimit(int deliveryOrderLimit) {
        this.deliveryOrderLimit = deliveryOrderLimit;
    }

    public int getTotalOrderLimit() {
        return totalOrderLimit;
    }

    public void setTotalOrderLimit(int totalOrderLimit) {
        this.totalOrderLimit = totalOrderLimit;
    }

    public int getTotalOrderCount() {
        return totalOrderCount;
    }

    public void setTotalOrderCount(int totalOrderCount) {
        this.totalOrderCount = totalOrderCount;
    }


  public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }*/


}
