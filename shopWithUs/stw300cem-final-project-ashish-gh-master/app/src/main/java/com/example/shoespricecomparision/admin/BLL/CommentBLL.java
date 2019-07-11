package com.example.shoespricecomparision.admin.BLL;

import model.LoginSignUpResponse;
import model.Review;
import model.ReviewResponse;
import model.User;
import retrofit2.Call;
import retrofit2.Response;
import shoesAPI.ShoesAPI;
import url.Url;

public class CommentBLL {

    private int id;
    private String review;
    private int userId;
    private int shoesId;
    private String reviewTime;
    private boolean isSuccess =false;

    public CommentBLL(String review, int userId, int shoesId, String reviewTime){
        this.review = review;
        this.userId = userId;
        this.shoesId = shoesId;
        this.reviewTime = reviewTime;
    }

    public boolean addComment(){
        Review review = new Review(this.review,this.userId, this.shoesId, this.reviewTime);

        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);
        Call<ReviewResponse> userCall = shoesAPI.addReview(review);
        try {
            Response<ReviewResponse> reviewResponseResponse = userCall.execute();
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
