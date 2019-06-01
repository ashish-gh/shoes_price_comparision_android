package com.example.shoespricecomparision.admin;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_shoes);

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
}
