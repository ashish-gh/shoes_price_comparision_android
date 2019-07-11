package com.example.shoespricecomparision.admin.BLL;

import android.util.Log;

import model.LoginSignUpResponse;
import model.User;
import retrofit2.Call;
import retrofit2.Response;
import shoesAPI.ShoesAPI;
import url.Url;

import static android.support.constraint.Constraints.TAG;

public class RegisterBLL {

    private String firstName;
    private String lastName;
    private String email;
    private String contact;
    private String password;
    private String profileImage;
    private String userType;

    private boolean isSuccess =false;


    public RegisterBLL(String firstName, String lastName, String email, String contact, String password, String profileImage, String userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contact = contact;
        this.password = password;
        this.profileImage = profileImage;
        this.userType = userType;
    }


    public boolean registerUser(){
        Log.d(TAG, "registerUser: I am here");
        User user = new User(this.firstName, this.lastName, this.email, this.contact,this.password,this.profileImage,this.userType);

        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);
        Call<LoginSignUpResponse> userCall = shoesAPI.registerUser(user);
        try {
            Response<LoginSignUpResponse> loginSignUpResponseResponse = userCall.execute();
            if (loginSignUpResponseResponse.body().getSuccess()){
                Log.d(TAG, "registerUser: is success");
                isSuccess = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d(TAG, "registerUser: value" + isSuccess);
        return isSuccess;
    }


}
