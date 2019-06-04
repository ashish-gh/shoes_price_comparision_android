package com.example.shoespricecomparision;

import android.content.Intent;
import android.os.Build;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.widget.Toast;

import java.util.List;

import adapter.ReviewAdapter;
import model.Review;
import model.Shoes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shoesAPI.ShoesAPI;
import url.Url;

public class ReviewActivity extends AppCompatActivity {

    int onStartCount = 0;
    private Toolbar toolbarReview;

    private RecyclerView recyclerViewReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        recyclerViewReview = findViewById(R.id.recyclerViewReview);

//      set animation
        onStartCount = 1;
        if (savedInstanceState == null) // 1st time
        {
            this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);
        } else // already created so reverse animation
        {
            onStartCount = 2;
        }

//      set toolbar
        toolbarReview = findViewById(R.id.toolbarReview);
        setSupportActionBar(toolbarReview);

//      set back button in toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

//        setting review inside recyclerview
        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);
        Call<List<Review>> listCall = shoesAPI.getReviews();
        listCall.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(ReviewActivity.this, "Code :" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Review> reviewList = response.body();
                ReviewAdapter reviewAdapter = new ReviewAdapter(reviewList,ReviewActivity.this);
                recyclerViewReview.setAdapter(reviewAdapter);
                recyclerViewReview.setLayoutManager(new LinearLayoutManager(ReviewActivity.this));
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                Toast.makeText(ReviewActivity.this, "Error : " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


//     onStart() is called when activity is visible to user
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (onStartCount > 1) {
            this.overridePendingTransition(R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right);
        } else if (onStartCount == 1) {
            onStartCount++;
        }
    }


//to go back to the ShoesDescriptionPage
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        Intent intent = new Intent(ReviewActivity.this, ShoesDescriptionActivity.class);
        startActivity(intent);
        finish();
        return  true;
    }





}
