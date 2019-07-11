package com.example.shoespricecomparision;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoespricecomparision.admin.AdminDashboardActivity;
import com.example.shoespricecomparision.admin.BLL.LoginBLL;

import java.util.List;

import animation.Animation;
import model.LoginSignUpResponse;
import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sharedPreferences.PreferenceUtility;
import shoesAPI.ShoesAPI;
import strictMode.StrictMode;
import url.Url;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignUp;

    boolean userCheck, userType;

//  for animation
    private Animation animation;

//    for shared preference
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferencesUserType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        using animation
        animation = new Animation();
        animation.slideLeft(this);


        etEmail = findViewById(R.id.etEmailLogin);
        etPassword = findViewById(R.id.etPasswordLogin);
        btnLogin= findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUpLogin);


        sharedPreferencesUserType = this.getSharedPreferences("UserType",MODE_PRIVATE);
        sharedPreferences = this.getSharedPreferences("User",MODE_PRIVATE);
        if (sharedPreferences.getString("token", "") == null){
//
            Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show();
        }

//       click listener event on login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            text validation
                if (textValidation()) {
//              check email validation
                    if (emailValidation()){
                        if (authenticate()) {
//                            get user type
                                if (getUserType()){
//                                    store user details in shared preferences

                                    if (sharedPreferencesUserType.getString("userType","").equals("admin")){
                                        Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                                        startActivity(intent);
                                        finish();
                                  }else if(sharedPreferencesUserType.getString("userType", "").equals("user")){
                                        Intent intent= new Intent(LoginActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                  }
                              }
                        }

                    }

                }
            }
        });

//        click listener event on signup
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
    }



    private boolean getUserType() {
        String email = etEmail.getText().toString();
        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);
        Call<List<User>> listCall =  shoesAPI.getUserByEmail(email);
        listCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Not retreived", Toast.LENGTH_SHORT).show();
                }else{
                    List<User> users = response.body();
                    if(users.size() > 0){
                        for (int i=0; i < users.size(); i++  ){
                            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("UserType", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userType",users.get(i).getUserType());
                            editor.putString("email", users.get(i).getEmail());
                            editor.putString("userId", Integer.toString(users.get(i).getUserId()));
                            Url.userId = users.get(i).getUserId();
                            editor.commit();
                        }
                        userType = true;
                    }else {
                        userType = false;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error :" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return userType;
    }


    private boolean authenticate() {

//       setting values in variable
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

//        instance of login business logic layer
        final LoginBLL loginBLL = new LoginBLL(email,password);
        StrictMode.StrictMode();
        if (loginBLL.checkUser()){

            return true;
        }else{
            return false;
        }
    }


    // text validations
    private Boolean textValidation() {
        boolean validate = true;
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.requestFocus();
            Toast.makeText(LoginActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            validate = false;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.requestFocus();
            Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            validate = false;
        }
        return validate;
    }


    //    email validation
    private Boolean emailValidation(){
        String email = etEmail.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.matches(emailPattern)){
            return true;
        }else {
            Toast.makeText(LoginActivity.this, "Email is not valid", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
