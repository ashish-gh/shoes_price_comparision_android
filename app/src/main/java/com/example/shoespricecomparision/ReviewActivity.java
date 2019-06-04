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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoespricecomparision.admin.AddItemActivity;

import java.util.Date;
import java.util.List;

import adapter.ReviewAdapter;
import model.Review;
import model.Shoes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import shoesAPI.ShoesAPI;
import url.Url;

public class ReviewActivity extends AppCompatActivity {

    int onStartCount = 0;
    private Toolbar toolbarReview;

    private EditText etReview;
    private Button btnReview;

    private RecyclerView recyclerViewReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        recyclerViewReview = findViewById(R.id.recyclerViewReview);
        etReview = findViewById(R.id.etReview);
        btnReview = findViewById(R.id.btnReview);


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

//        setting clicklistener on button

        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void save() {

        String reviews = etReview.getText().toString();
        String userName = "ashish";
        int shoesId = 1;
        String reviewDate = "2019-06-03";

        Review review  = new Review(reviews,userName,shoesId,reviewDate);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ShoesAPI shoesAPI = retrofit.create(ShoesAPI.class);

        Call<Void> itemsCall = shoesAPI.addReview(review);
        itemsCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(ReviewActivity.this,"Code" + response.code(),Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(ReviewActivity.this, "Successfully Added",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ReviewActivity.this,"Error " + t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
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
