package com.example.shoespricecomparision.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shoespricecomparision.R;

import model.Shoes;
import model.Store;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import shoesAPI.ShoesAPI;
import url.Url;

public class AddShopActivity extends AppCompatActivity {

    private EditText etShopName, etShopLatitude, etShoeLongitude;
    private ImageView  imgBackAddShop;
    private Button btnAddShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);

        etShopName = findViewById(R.id.etShopName);
        etShopLatitude= findViewById(R.id.etAddShopLatitude);
        etShoeLongitude= findViewById(R.id.etAddShopLongitude);
        imgBackAddShop = findViewById(R.id.imgBackAddShop);
        btnAddShop= findViewById(R.id.btnAddShop);

        imgBackAddShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddShopActivity.this, AdminDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnAddShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                validate before saving
                save();

            }
        });

    }

    private void save() {
        String shopName = etShopName.getText().toString();
        float latitude = Float.parseFloat(etShopLatitude.getText().toString());
        float longitude = Float.parseFloat(etShoeLongitude.getText().toString());

        Store store = new Store(shopName,latitude,longitude);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ShoesAPI shoesAPI = retrofit.create(ShoesAPI.class);

        Call<Void> itemsCall = shoesAPI.addStores(store);

        itemsCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(AddShopActivity.this,"Code" + response.code(),Toast.LENGTH_LONG).show();
                    return;
                }else {
                    Toast.makeText(AddShopActivity.this, "Successfully Added", Toast.LENGTH_LONG).show();
                    loadActivity();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddShopActivity.this,"Error " + t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadActivity() {
//        this activity is called when item is deleted
//        this activity reloads the AddShopActivity
        Toast.makeText(this, "Starting ", Toast.LENGTH_SHORT).show();
        Intent loadActivity = new Intent(this,AddShopActivity.class);
        startActivity(loadActivity);
        finish();
    }


}
