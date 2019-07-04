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

import adapter.ShoesAdapterAdmin;
import adapter.StoreAdapter;
import model.Shoes;
import model.Store;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shoesAPI.ShoesAPI;
import url.Url;

public class ListStoreActivity extends AppCompatActivity {

    int onStartCount = 0;

    private RecyclerView recyclerViewListStoreAdmin;

//    private ImageView imgUpdateShoes;
//    private ImageView imgDeleteShoes;

    private ImageView imgBackListStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_store);

        onStartCount = 1;
        if (savedInstanceState == null) // 1st time
        {
            this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);
        } else // already created so reverse animation
        {
            onStartCount = 2;
        }

        imgBackListStore = findViewById(R.id.imgBackListStore);
        imgBackListStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListStoreActivity.this, AdminDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        recyclerViewListStoreAdmin = findViewById(R.id.recyclerViewListStoreAdmin);
        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);
        Call<List<Store>> listCall = shoesAPI.getStores();
        listCall.enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                if (!response.isSuccessful()){
//                    use snackbar here
                    Toast.makeText(ListStoreActivity.this, "Code : "+ response.code() , Toast.LENGTH_SHORT).show();
                }
                    List<Store> stores = response.body();
                    StoreAdapter storeAdapter = new StoreAdapter(stores, ListStoreActivity.this);
                    recyclerViewListStoreAdmin.setAdapter(storeAdapter);
                    recyclerViewListStoreAdmin.setLayoutManager(new LinearLayoutManager(ListStoreActivity.this));

            }

            @Override
            public void onFailure(Call<List<Store>> call, Throwable t) {
                Toast.makeText(ListStoreActivity.this, "Error : " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (onStartCount % 2 == 0 ) {
            onStartCount++;
            this.overridePendingTransition(R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right);

        } else {
            this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);
            onStartCount++;
        }
    }
}
