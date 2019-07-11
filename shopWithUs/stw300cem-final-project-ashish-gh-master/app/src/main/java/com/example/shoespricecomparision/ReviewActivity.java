package com.example.shoespricecomparision;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoespricecomparision.admin.AddItemActivity;
import com.example.shoespricecomparision.admin.BLL.CommentBLL;
import com.example.shoespricecomparision.admin.BLL.LoginBLL;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import adapter.ReviewAdapter;
import animation.Animation;
import model.Review;
import model.Shoes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import shoesAPI.ShoesAPI;
import strictMode.StrictMode;
import url.Url;

public class ReviewActivity extends AppCompatActivity implements View.OnClickListener{

    int onStartCount = 0;

    private Toolbar toolbarReview;
    private EditText etReview;
    private Button btnReview;
    private RecyclerView recyclerViewReview;

//    for animation
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

//        using animation
        animation = new Animation();
        animation.slideLeft(this);

        //        to make keyboard floatable
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST);


        etReview = findViewById(R.id.etReview);
        btnReview = findViewById(R.id.btnReview);
        recyclerViewReview = findViewById(R.id.recyclerViewReview);

        btnReview.setOnClickListener(this);


//      set toolbar
        toolbarReview = findViewById(R.id.toolbarReview);
        setSupportActionBar(toolbarReview);

//      set back button in toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

//      setting review inside recyclerview
        loadComment();
    }

//    to add back button in toolbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        Intent intent = new Intent(ReviewActivity.this, ShoesDescriptionActivity.class);
        startActivity(intent);
        finish();
        return  true;
    }


    //    implementing click listener
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnReview){
//            check if text field is empty or not
            if (textvalidation()) {
                // check if user is registered or not
                if (Url.accessToken.length() > 5){
                    if (save()){
                        Toast.makeText(this, "Review added . . ", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Intent intent = new Intent(ReviewActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                }
            }

        }
    }

    private boolean textvalidation() {
        boolean validate = true;
        if (TextUtils.isEmpty(etReview.getText().toString())) {
            etReview.requestFocus();
            Toast.makeText(ReviewActivity.this, "Please Enter review", Toast.LENGTH_SHORT).show();
            validate = false;
        }
        return validate;
    }

    //    to save review
    private boolean save() {
//        int id = Url.shoesId;
        String reviews = etReview.getText().toString();
        //get current user from shared preferences

        //  to add current date
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String strDate = dateFormat.format(date);

        //        instance of login business logic layer
        final CommentBLL commentBLL = new CommentBLL(reviews, Url.userId, Url.shoesId, strDate);
        StrictMode.StrictMode();
        if (commentBLL.addComment()) {
            return true;
        } else {
            return false;
        }
    }



    private void loadComment(){
        Log.d("shoes", "loadComment: this is shoes id" + Url.shoesId);
        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);
        Call<List<Review>> listCall = shoesAPI.getReviewsByShoes(Url.shoesId);
        listCall.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(ReviewActivity.this, "Code :" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Review> reviewList = response.body();

                for (int i=0; i<reviewList.size(); i++){
                    Log.d("rage", "onResponse: " + reviewList.get(i).getReview() + " "+ reviewList.get(i).getReviewDate());
                }
                Log.d("tag", "onResponse: size "  + reviewList.size());


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

}
