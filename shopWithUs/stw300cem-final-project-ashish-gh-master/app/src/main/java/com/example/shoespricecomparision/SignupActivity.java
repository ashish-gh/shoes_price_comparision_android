package com.example.shoespricecomparision;

import android.app.Notification;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.shoespricecomparision.admin.AddItemActivity;
import com.example.shoespricecomparision.admin.ListStoreActivity;

import java.util.List;

import adapter.StoreAdapter;
import animation.Animation;
import channel.CreateChannel;
import model.Shoes;
import model.Store;
import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import shoesAPI.ShoesAPI;
import url.Url;

public class SignupActivity extends AppCompatActivity {


    private EditText etFirstName, etLastName, etEmail, etContact, etPassword, etConfirmPassword;
    private Button btnRegister, btnSignIn;
    private LinearLayout linearLayout;

//  for animation
    private Animation animation;

//  for notification
    private int id =1;
    CreateChannel createChannel = new CreateChannel(this);
    private NotificationManagerCompat notificationManagerCompat;

    boolean userCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        animation = new Animation();
        animation.slideLeft(this);

        linearLayout = findViewById(R.id.linearLayoutRegister);

//        channel to handle notification
        notificationManagerCompat = NotificationManagerCompat.from(this);
        createChannel.createChannel();

        etFirstName = findViewById(R.id.etFirstNameRegister);
        etLastName = findViewById(R.id.etLastNameRegister);
        etEmail = findViewById(R.id.etEmailRegister);
        etContact = findViewById(R.id.etContactRegister);
        etPassword = findViewById(R.id.etpasswordRegister);
        etConfirmPassword = findViewById(R.id.etpasswordConfirmRegister);

        btnRegister = findViewById(R.id.btnRegister);
        btnSignIn = findViewById(R.id.btnLoginSignUp);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(SignupActivity.this, "Now register"  , Toast.LENGTH_SHORT).show();
                register();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void register() {
//        validate if text fields are empty or not
//        when all text fields are filled go to email validation
        if (textValidation()) {
//          check valid email
            if (emailValidation()) {
//                check contact number validation
                if (contactValidation()) {
//                  when valid email is given check user is registered or not
                    if (checkPassword()) {
//                     check password validation
                        if (passwordValidation()) {
//                          check if user email is registered or not
                            if (checkUser()) {
//                                register user
                                if (registerUser()){
                                    clearField();
                                }
                            }
                        }

                    }
                }

            }

        }

    }


    private boolean registerUser() {

        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String contact = etContact.getText().toString();
        String email = etEmail.getText().toString();
        String password= etPassword.getText().toString();
        String userType ="user";
        String profileImage ="profile.png";

        User user = new User(firstName,lastName,email,contact,password,profileImage,userType);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ShoesAPI shoesAPI = retrofit.create(ShoesAPI.class);
        Call<Void> itemsCall = shoesAPI.registerUser(user);

        itemsCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(SignupActivity.this,"Code" + response.code(),Toast.LENGTH_LONG).show();
                    return;
                }else {
                    Toast.makeText(SignupActivity.this, "Registered", Toast.LENGTH_LONG).show();
                    registerNotification();
                    userCheck = true;
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(SignupActivity.this,"Error " + t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
        return userCheck;
    }


    private void registerNotification() {
        Notification notification = new NotificationCompat.Builder(this,CreateChannel.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_message)
                .setContentTitle("User created")
                .setContentText("Please update your profile")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManagerCompat.notify(id, notification);
        id++;
    }


    private Boolean textValidation() {
        boolean validate = true;
        if (TextUtils.isEmpty(etFirstName.getText().toString())) {
            etFirstName.setError("Please Enter First Name");
            etFirstName.requestFocus();
            validate = false;
        }
        if (TextUtils.isEmpty(etLastName.getText().toString())) {
            etLastName.setError("Please Enter Last Name");
            etLastName.requestFocus();
            validate = false;
        }
        if (TextUtils.isEmpty(etContact.getText().toString())) {
            etContact.setError("Please Enter Contact Number");
            etContact.requestFocus();
            validate = false;
        }
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError("Please Enter Email");
            etEmail.requestFocus();
            validate = false;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError("Please Enter Password");
            etPassword.requestFocus();
            validate = false;
        }
        if (TextUtils.isEmpty(etConfirmPassword.getText().toString())) {
            etConfirmPassword.setError("Please Confirm Password");
            etConfirmPassword.requestFocus();
            validate = false;
        }
        return validate;
    }


    public Boolean emailValidation() {
        String email = etEmail.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.matches(emailPattern)) {
            return true;
        } else {
            Toast.makeText(SignupActivity.this, "Email is not valid", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean contactValidation() {
        String number = etContact.getText().toString();
        if (number.length() == 10) {
            return true;
        } else {
            Toast.makeText(this, "Please enter valid number", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public Boolean checkPassword() {
        if (etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
            return true;
        } else {
//            Snackbar snackbar = Snackbar.make(linearLayout, "Password Not Matched", Snackbar.LENGTH_SHORT);
//            snackbar.show();
            Toast.makeText(SignupActivity.this, "Password Not Matched", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public Boolean passwordValidation() {
        if (etPassword.getText().toString().length() >= 6 && etPassword.getText().toString().length() <= 10) {
            return true;
        } else {
            Toast.makeText(this, "Password should be more than 6 and less than 10 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean checkUser() {

        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);
        Call<List<User>> listCall = shoesAPI.getUserByEmail(etEmail.getText().toString());
        listCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()) {
//                    use snackbar here
                    Toast.makeText(SignupActivity.this, "Code : " + response.code(), Toast.LENGTH_SHORT).show();
                }
                List<User> users = response.body();
                if(users.size() > 0){
                    userCheck = false;
                    Toast.makeText(SignupActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
                }else {
                    userCheck = true;
                }

                Toast.makeText(SignupActivity.this, "size"  + users.size(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "Error : " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return userCheck;
    }


    private void clearField() {
        etFirstName.setText("");
        etLastName.setText("");
        etConfirmPassword.setText("");
        etPassword.setText("");
        etEmail.setText("");
        etContact.setText("");
    }


}
