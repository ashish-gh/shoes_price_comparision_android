package com.example.shoespricecomparision;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoespricecomparision.admin.AdminDashboardActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnForgotPassword;
    private Button btnSignUp;
    private int onStartCount = 1;



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

        final String userType = "admin";

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                if(userType.equals("user")){
//                    Intent  intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }else if (userType.equals("admin")){
                    Intent  intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                    startActivity(intent);
                    finish();

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



    private Boolean validate() {
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
