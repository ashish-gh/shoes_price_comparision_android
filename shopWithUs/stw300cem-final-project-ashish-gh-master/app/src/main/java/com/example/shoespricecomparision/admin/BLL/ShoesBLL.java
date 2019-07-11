package com.example.shoespricecomparision.admin.BLL;

import model.ReviewResponse;
import model.Shoes;
import model.ShoesResponse;
import retrofit2.Call;
import retrofit2.Response;
import shoesAPI.ShoesAPI;
import url.Url;

public class ShoesBLL {

    private String brandName;
    private String shoesName;
    private float shoesPrice;
    private String shoesDescription;
    private String shoesImageName;
    private int shopId;

    private boolean isSuccess =false;

    public ShoesBLL(String brandName,String shoesName, float shoesPrice, String shoesDescription, String shoesImageName, int shopId){
        this.brandName = brandName;
        this.shoesName = shoesName;
        this.shoesPrice = shoesPrice;
        this.shoesDescription = shoesDescription;
        this.shoesImageName= shoesImageName;
        this.shopId = shopId;
    }

    public boolean addShoes(){
        Shoes shoes= new Shoes(this.brandName,this.shoesName, this.shoesPrice, this.shoesDescription, this.shoesImageName, this.shopId);

        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);
        Call<ShoesResponse> userCall = shoesAPI.addShoes(shoes);
        try {
            Response<ShoesResponse> shoesResponseResponse= userCall.execute();
            if (shoesResponseResponse.body().getSuccess()){

//                Url.accessToken = reviewResponseResponse.body().getAccessToken();
                isSuccess = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return isSuccess;
    }

}
