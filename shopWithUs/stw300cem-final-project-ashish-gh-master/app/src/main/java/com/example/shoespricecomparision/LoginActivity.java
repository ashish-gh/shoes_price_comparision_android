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

import java.util.List;

import model.LoginSignUpResponse;
import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shoesAPI.ShoesAPI;
import url.Url;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnForgotPassword;
    private Button btnSignUp;
    private int onStartCount = 1;
    private ProgressDialog progress;

    boolean userCheck;
    String emailAddress =null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        onStartCount = 1;
        if (savedInstanceState == null) // 1st time
        {
            this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);
        } else // already created so reverse animation
        {
            onStartCount = 2;
        }

        etEmail = findViewById(R.id.etEmailLogin);
        etPassword = findViewById(R.id.etPasswordLogin);
        btnLogin= findViewById(R.id.btnSignIn);
        btnForgotPassword= findViewById(R.id.btn_reset_password);
        btnSignUp = findViewById(R.id.btnSignUpLogin);

        progress =  new ProgressDialog(this);

        final String userType = "admin";

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//        validate if text fields are empty or not
//        when all text fields are filled go to email validation
                if (textValidation()) {
//              check email validation
                    if (emailValidation()){
                        if (authenticate()) {
//                            get user type
//                            loadProgressDialog();
                                if (getUserType()){
//                                    store user details in shared preferences

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                        }

                    }

                }



//                if(userType.equals("user")){
//                    Intent  intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }else if (userType.equals("admin")){
//                    Intent  intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
//                    startActivity(intent);
//                    finish();

//                }



            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadProgressDialog() {

        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }

    private boolean getUserType() {
        Toast.makeText(this, "To get user" + emailAddress, Toast.LENGTH_SHORT).show();
        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);
        Call<List<User>> listCall =  shoesAPI.getUserByEmail(emailAddress);
        listCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Not retreived", Toast.LENGTH_SHORT).show();
                }else{
                    List<User> users = response.body();
                    if(users.size() > 0){
                        userCheck = true;
                        Toast.makeText(LoginActivity.this, "Email address found", Toast.LENGTH_SHORT).show();
//                        add this email address to shared preferences

                    }else {
                        userCheck = false;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error :" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return userCheck;
    }


    private boolean authenticate() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        User user = new User(email,password);
        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);
        Call<LoginSignUpResponse> loginSignUpResponseCall = shoesAPI.authenticate(user);
        loginSignUpResponseCall.enqueue(new Callback<LoginSignUpResponse>() {
            @Override
            public void onResponse(Call<LoginSignUpResponse> call, Response<LoginSignUpResponse> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Either username or password ", Toast.LENGTH_SHORT).show();
                    userCheck = false;
                }else {
                    Toast.makeText(LoginActivity.this, "Here", Toast.LENGTH_SHORT).show();
                    if (response.body().getSuccess()){

                        Toast.makeText(LoginActivity.this, "No success", Toast.LENGTH_SHORT).show();
                    Toast.makeText(LoginActivity.this, "" + response.headers().get("Token"), Toast.LENGTH_SHORT).show();
                    Toast.makeText(LoginActivity.this, "" + response.headers().get("accessToken"), Toast.LENGTH_SHORT).show();

                        Url.accessToken = response.body().getAccessToken();
                        String token = response.body().getAccessToken();
                        emailAddress = response.body().getEmail();
                        userCheck = true;
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginSignUpResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error : "+ t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return userCheck;
    }



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