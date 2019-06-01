package com.example.shoespricecomparision.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.shoespricecomparision.R;

public class AdminDashboardActivity extends AppCompatActivity implements View.OnClickListener{

    private CardView cardViewAddItem,cardViewListItem,cardViewAddShops, cardViewListShops, cardViewListUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        cardViewAddItem = findViewById(R.id.cardAddItem);
        cardViewListItem = findViewById(R.id.cardListItem);
        cardViewAddShops = findViewById(R.id.cardAddShops);
        cardViewListShops= findViewById(R.id.cardListShops);
        cardViewListUser = findViewById(R.id.cardListUsers);

        cardViewAddItem.setOnClickListener(this);
        cardViewListItem.setOnClickListener(this);
        cardViewAddShops.setOnClickListener(this);
        cardViewListShops.setOnClickListener(this);
        cardViewListUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){
            case R.id.cardAddItem:
                Intent intentItemAdd = new Intent(this,AddItemActivity.class);
                startActivity(intentItemAdd);
                finish();
                break;
            case R.id.cardListItem:
                Intent intentItemList = new Intent(this,ListShoesActivity.class);
                startActivity(intentItemList);
                finish();
                break;
            case R.id.cardAddShops:
                Intent intentAddShops= new Intent(this,ListShoesActivity.class);
                startActivity(intentAddShops);
                finish();
                break;
        }

    }
}
