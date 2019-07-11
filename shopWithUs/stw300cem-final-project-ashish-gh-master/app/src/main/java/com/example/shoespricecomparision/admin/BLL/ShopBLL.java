package com.example.shoespricecomparision.admin.BLL;

import model.Review;
import model.ReviewResponse;
import model.ShopResponse;
import model.Store;
import retrofit2.Call;
import retrofit2.Response;
import shoesAPI.ShoesAPI;
import url.Url;

public class ShopBLL {

    private String shopName;
    private double latitude;
    private double longitude;
    private boolean isSuccess =false;

    public ShopBLL(String shopName, double latitude, double longitude){
        this.shopName = shopName;
        this.latitude = latitude;
        this.longitude= longitude;
    }

    public boolean addShop(){
        Store store= new Store(this.shopName,this.latitude, this.longitude);

        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);
        Call<ShopResponse> userCall = shoesAPI.addStores(store);
        try {
            Response<ShopResponse> reviewResponseResponse = userCall.execute();
            if (reviewResponseResponse.body().getSuccess()){

//                Url.accessToken = reviewResponseResponse.body().getAccessToken();
                isSuccess = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return isSuccess;
    }


}
