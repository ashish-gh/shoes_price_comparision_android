package com.example.shoespricecomparision;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ShoesDescriptionActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvNameDes,tvReviewDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setting slide animation for this page
        setAnimation();
        setContentView(R.layout.activity_shoes_description);

        tvNameDes = findViewById(R.id.tvNameDescription);
        tvReviewDescription = findViewById(R.id.tvReviewDescription);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);


//        setting clicklisetener on tvReviewDescription

        tvReviewDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                calling startActivity to set animation style
                startActivity();
            }
        });

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




        int[] attrs = new int[]{R.attr.selectableItemBackground};
        TypedArray typedArray = this    .obtainStyledAttributes(attrs);
        int backgroundResource = typedArray.getResourceId(0, 0);
        tvNameDes.setBackgroundResource(backgroundResource);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        Intent intent = new Intent(ShoesDescriptionActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        return  true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shoes_description,menu);
        return true;
    }


//      this is slide animation for this activity which loads when page gets started

    public void setAnimation() {
        if (Build.VERSION.SDK_INT > 20) {
            Slide slide = new Slide();
            slide.setSlideEdge(Gravity.LEFT);
            slide.setDuration(1000);
            slide.setInterpolator(new FastOutSlowInInterpolator());
//            slide.setStartDelay(100);
//            slide.setInterpolator(new AccelerateDecelerateInterpolator());
            getWindow().setExitTransition(slide);
            getWindow().setEnterTransition(slide);
        }
    }

//    this is used to set animation i.e. for review activity page

    public void startActivity(){
        Intent i = new Intent(ShoesDescriptionActivity.this, ReviewActivity.class);
        if(Build.VERSION.SDK_INT>20){
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ShoesDescriptionActivity.this);
            startActivity(i,options.toBundle());
        }
        else {
            startActivity(i);
        }
    }
}
