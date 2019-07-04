package com.example.shoespricecomparision.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shoespricecomparision.MainActivity;
import com.example.shoespricecomparision.R;

import java.util.List;

import adapter.ShoesAdapterAdmin;
import model.Shoes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shoesAPI.ShoesAPI;
import url.Url;

public class ListShoesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewListShoeAdmin;

//    private ImageView imgUpdateShoes;
//    private ImageView imgDeleteShoes;

    private ImageView imgBackListShoes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_shoes);

//        imgUpdateShoes = findViewById(R.id.imgUpdateShoes);
//        imgDeleteShoes= findViewById(R.id.imgDeleteShoes);

//        imgUpdateShoes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                call update activity to update shoes
//                Intent intent = new Intent(ListShoesActivity.this,UpdateShoesActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        imgBackListShoes = findViewById(R.id.imgBackListShoes);
        imgBackListShoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListShoesActivity.this, AdminDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });



        recyclerViewListShoeAdmin = findViewById(R.id.recyclerViewListShoesAdmin);
        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);

        Call<List<Shoes>> listCall = shoesAPI.getShoes();
        listCall.enqueue(new Callback<List<Shoes>>() {
            @Override
            public void onResponse(Call<List<Shoes>> call, Response<List<Shoes>> response) {
                if (!response.isSuccessful()){
//                    use snackbar here
                    Toast.makeText(ListShoesActivity.this, "Code : "+ response.code() , Toast.LENGTH_SHORT).show();
                }
                List<Shoes> items =  response.body();
                ShoesAdapterAdmin shoesAdapterAdmin = new ShoesAdapterAdmin(items,ListShoesActivity.this);
                recyclerViewListShoeAdmin.setAdapter(shoesAdapterAdmin);
                recyclerViewListShoeAdmin.setLayoutManager(new LinearLayoutManager(ListShoesActivity.this));
            }

            @Override
            public void onFailure(Call<List<Shoes>> call, Throwable t) {
                Toast.makeText(ListShoesActivity.this, "Error : " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }

//    private void delete() {
//        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);
//        Call<Void> voidCall = shoesAPI.deleteShoes(1);
//        voidCall.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (!response.isSuccessful()){
//                    Toast.makeText(ListShoesActivity.this,"Code" + response.code(),Toast.LENGTH_LONG).show();
//                    return;
//                }
//                Toast.makeText(ListShoesActivity.this, "Successfully Added",Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Toast.makeText(ListShoesActivity.this,"Error " + t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//

}
