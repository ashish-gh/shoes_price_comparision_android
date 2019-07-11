package com.example.shoespricecomparision.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class UpdateStoreActivity extends AppCompatActivity {

    int onStartCount = 0;
    private int storeId;

    private EditText etStoreName, etStoreLatitude, etStoreLongitude;
    private ImageView  imgBackUpdateStore;
    private Button btnUpdateStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_store);

//        setting slide animation for this page
        onStartCount = 1;
        if (savedInstanceState == null) // 1st time
        {
            this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);
        } else // already created so reverse animation
        {
            onStartCount = 2;
        }


        etStoreName = findViewById(R.id.etStoreNameUpdate);
        etStoreLatitude = findViewById(R.id.etStoreLatitudeUpdate);
        etStoreLongitude = findViewById(R.id.etStoreLongitudeUpdate);
        imgBackUpdateStore = findViewById(R.id.imgBackUpdateStore);
        btnUpdateStore = findViewById(R.id.btnUpdateStore);


        //        intent to go back to admin page
        imgBackUpdateStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateStoreActivity.this, AdminDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //        to update shoes in database
        btnUpdateStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        ////            setting values in the edit text

        Bundle bundle = getIntent().getExtras();
        etStoreName.setText(bundle.getString("storeName"));
        etStoreLatitude.setText(bundle.getString("storeLatitude"));
        etStoreLongitude.setText(bundle.getString("storeLongitude"));

//        etStoreLatitude.setText(Double.toString(bundle.getString("storeLatitude")));

        storeId = bundle.getInt("storeId");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (onStartCount % 2 == 0 ) {
            this.overridePendingTransition(R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right);
            onStartCount++;

        } else {
            this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);
            onStartCount++;
        }
    }

    private void update() {
        String storeName = etStoreName.getText().toString();
        float latitude = Float.parseFloat(etStoreLatitude.getText().toString());
        float longitude = Float.parseFloat(etStoreLongitude.getText().toString());

        Store store = new Store(storeName,latitude,longitude);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ShoesAPI shoesAPI = retrofit.create(ShoesAPI.class);

        Call<Void> itemsCall = shoesAPI.updateStore(storeId, store);

        itemsCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()){

                    Toast.makeText(UpdateStoreActivity.this,"Code" + response.code(),Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(UpdateStoreActivity.this, "Successfully Updated",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UpdateStoreActivity.this,"Error " + t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

}
