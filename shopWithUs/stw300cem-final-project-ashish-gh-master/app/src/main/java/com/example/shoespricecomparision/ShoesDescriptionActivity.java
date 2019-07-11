package com.example.shoespricecomparision;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import animation.Animation;
import url.Url;

public class ShoesDescriptionActivity extends AppCompatActivity implements OnMapReadyCallback {


    private Toolbar toolbar;
    private TextView tvNameDes,tvReviewDescription, tvShoesBrandDescription, tvShoesNameDescription, tvShoesPriceDescription;


    private ImageView imgDisplayHeader;

//    for map
    private GoogleMap mMap;

//    for animation
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoes_description);

//        using animation
        animation = new Animation();
        animation.slideLeft(this);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        imgDisplayHeader = findViewById(R.id.header);
        tvNameDes = findViewById(R.id.tvNameDescription);
        tvReviewDescription = findViewById(R.id.tvReviewDescription);

        tvShoesBrandDescription = findViewById(R.id.tvShoesBrandDescription);
        tvShoesNameDescription = findViewById(R.id.tvShoesNameDescription);
        tvShoesPriceDescription  = findViewById(R.id.tvShoesPriceDescription);


//      setting toolbar for this activity
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//      setting back navigation button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);


//        setting clickListener on tvReviewDescription

        tvReviewDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                calling startActivity to set animation style
                Intent intent = new Intent(ShoesDescriptionActivity.this,ReviewActivity.class);
                Toast.makeText(ShoesDescriptionActivity.this, "before going shoesId :" + Url.shoesId, Toast.LENGTH_SHORT).show();
                Log.d("tag" , "new value here we have" + Url.shoesId);
                intent.putExtra("shoeId : ", Url.shoesId);
                startActivity(intent);
                finish();
            }
        });


//       setting collapsing toolbar
        collapsingToolbar();

//        setting splash animation in textView
        settingAnimation();


//      load shoes
        strictMode();
        URL url = null;
        Bundle bundle = getIntent().getExtras();
        Url.shoesId = bundle.getInt("shoeId");

        int shoe = Integer.parseInt(bundle.getString("shoeId"));
        Toast.makeText(this, "shoesId" + Url.shoesId, Toast.LENGTH_SHORT).show();
        if (bundle != null) {
            imgDisplayHeader.setImageResource(bundle.getInt("image"));

            try {
                url = new URL("http://10.0.2.2:8005/uploads/" + bundle.getString("image"));
                imgDisplayHeader.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            tvShoesNameDescription.setText(bundle.getString("shoesName"));
            tvShoesBrandDescription.setText(bundle.getString("shoesBrand"));
            tvShoesPriceDescription.setText(String.valueOf(bundle.getString("shoesPrice")));
        }
    }

//    setting splash animation in review
    private void settingAnimation() {
        int[] attrs = new int[]{R.attr.selectableItemBackground};
        TypedArray typedArray = this    .obtainStyledAttributes(attrs);
        int backgroundResource = typedArray.getResourceId(0, 0);
        tvNameDes.setBackgroundResource(backgroundResource);
    }

//    setting collapsing toolbar
    private void collapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }if (scrollRange + i == 0) {
//                    collapsingToolbarLayout.setTitle("title");
//                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

    }


    private void strictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        Intent intent = new Intent(ShoesDescriptionActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        return  true;
    }


//    setting backward toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shoes_description,menu);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(87.23, 27.12);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

}
