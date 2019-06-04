package com.example.shoespricecomparision;

import android.content.Intent;
import android.os.Build;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;

public class ReviewActivity extends AppCompatActivity {

    int onStartCount = 0;
    private Toolbar toolbarReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

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
