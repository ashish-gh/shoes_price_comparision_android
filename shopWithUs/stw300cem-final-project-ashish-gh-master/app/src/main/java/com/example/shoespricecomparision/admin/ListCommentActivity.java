package com.example.shoespricecomparision.admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.example.shoespricecomparision.R;

public class ListCommentActivity extends AppCompatActivity {

    int onStartCount = 0;
    private RecyclerView recyclerViewListUsersAdmin;
    private ImageView imgBackListUsersAdmin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_comment);
    }
}
