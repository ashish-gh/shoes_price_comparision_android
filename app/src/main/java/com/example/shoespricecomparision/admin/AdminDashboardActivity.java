package com.example.shoespricecomparision.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.shoespricecomparision.R;

public class AdminDashboardActivity extends AppCompatActivity {

    private CardView cardViewAddItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        cardViewAddItem = findViewById(R.id.cardAddItem);
        cardViewAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboardActivity.this,AddItemActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
