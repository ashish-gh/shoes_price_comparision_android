package com.example.shoespricecomparision.admin.BLL;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.shoespricecomparision.LoginActivity;

import java.util.List;

import model.LoginSignUpResponse;
import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shoesAPI.ShoesAPI;
import url.Url;

import static android.content.Context.MODE_PRIVATE;
import static android.support.constraint.Constraints.TAG;

public class LoginBLL {

    private String email;
    private String password;
    private boolean isSuccess =false;

    public LoginBLL(String email, String password){
        this.email = email;
        this.password = password;
        }

    public boolean checkUser(){
        User user  = new User(this.email, this.password);

        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);
        Call<LoginSignUpResponse> userCall = shoesAPI.authenticate(user);
        try {
            Response<LoginSignUpResponse> loginSignUpResponseResponse = userCall.execute();
            if (loginSignUpResponseResponse.body().getSuccess()){
                Url.accessToken = loginSignUpResponseResponse.body().getAccessToken();
                isSuccess = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return isSuccess;
    }




}
