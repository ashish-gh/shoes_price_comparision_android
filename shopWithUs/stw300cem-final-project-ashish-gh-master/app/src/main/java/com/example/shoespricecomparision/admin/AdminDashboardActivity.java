package com.example.shoespricecomparision.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.example.shoespricecomparision.MainActivity;
import com.example.shoespricecomparision.R;

import animation.Animation;

public class AdminDashboardActivity extends AppCompatActivity implements View.OnClickListener{

    private SensorManager sensorManager;
    private Animation animation;

    private CardView cardViewAddItem,cardViewListItem,cardViewAddShops, cardViewListShops, cardViewListUser, cardViewLogOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);


        animation = new Animation();
        animation.slideRight(this);

        cardViewAddItem = findViewById(R.id.cardAddItem);
        cardViewListItem = findViewById(R.id.cardListItem);
        cardViewAddShops = findViewById(R.id.cardAddShops);
        cardViewListShops= findViewById(R.id.cardListShops);
        cardViewListUser = findViewById(R.id.cardListUsers);
        cardViewLogOut = findViewById(R.id.cardLogOut);

        cardViewAddItem.setOnClickListener(this);
        cardViewListItem.setOnClickListener(this);
        cardViewAddShops.setOnClickListener(this);
        cardViewListShops.setOnClickListener(this);
        cardViewListUser.setOnClickListener(this);
        cardViewLogOut.setOnClickListener(this);

//        logoutProximity();
    }


    private void logoutProximity() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.values[0]  <= 4){
                    Intent intentLogOut = new Intent(AdminDashboardActivity.this, MainActivity.class);
                    startActivity(intentLogOut);
                    finish();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        sensorManager.registerListener(sensorEventListener, sensor, sensorManager.SENSOR_DELAY_NORMAL);
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
                Toast.makeText(this, "Add shop activity", Toast.LENGTH_SHORT).show();
                Intent intentaddshop = new Intent(this,AddShopActivity.class);
                startActivity(intentaddshop);
//                Intent intentAddShops= new Intent(this,AddShopActivity.class);
//                startActivity(intentAddShops);
//                finish();
                break;
            case R.id.cardListShops:
                Intent intentListShops= new Intent(this,ListStoreActivity.class);
                startActivity(intentListShops);
                finish();
                break;

            case R.id.cardLogOut:
                logout();
                break;
        }



    }

    private void logout() {
//        alert dialog box in logout
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to logout ?");
//        setting positive message
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AdminDashboardActivity.this, "You clicked yes button", Toast.LENGTH_SHORT).show();

                Intent intentLogOut = new Intent(AdminDashboardActivity.this, MainActivity.class);
                startActivity(intentLogOut);
                finish();

            }
        });

//        setting negative message
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
