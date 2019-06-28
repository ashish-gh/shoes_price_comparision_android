package com.example.shoespricecomparision.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shoespricecomparision.R;

import java.util.List;

import adapter.UserAdapterAdmin;
import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shoesAPI.ShoesAPI;
import url.Url;

public class ListUserActivity extends AppCompatActivity {

    int onStartCount = 0;
    private RecyclerView recyclerViewListUsersAdmin;
    private ImageView imgBackListUsersAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        recyclerViewListUsersAdmin = findViewById(R.id.recyclerViewListUsersAdmin);
        imgBackListUsersAdmin = findViewById(R.id.imgBackListUser);

        imgBackListUsersAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListUserActivity.this, AdminDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

//      to load data into recyclerView
        loadData();
    }

    private void loadData() {

        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);
        Call<List<User>> listCall = shoesAPI.getUser();
        listCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(ListUserActivity.this, "Code : "+ response.code() , Toast.LENGTH_SHORT).show();
                }
                List<User> items =  response.body();
                UserAdapterAdmin userAdapterAdmin = new UserAdapterAdmin(items,ListUserActivity.this);
                recyclerViewListUsersAdmin.setAdapter(userAdapterAdmin);
                recyclerViewListUsersAdmin.setLayoutManager(new LinearLayoutManager(ListUserActivity.this));
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(ListUserActivity.this, "Error : " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
