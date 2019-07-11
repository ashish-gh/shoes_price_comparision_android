package com.example.shoespricecomparision.admin;

import android.app.Notification;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shoespricecomparision.R;
import com.example.shoespricecomparision.admin.BLL.LoginBLL;
import com.example.shoespricecomparision.admin.BLL.ShopBLL;

import channel.CreateChannel;
import model.Shoes;
import model.Store;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import shoesAPI.ShoesAPI;
import strictMode.StrictMode;
import url.Url;

public class AddShopActivity extends AppCompatActivity {

    private EditText etShopName, etShopLatitude, etShoeLongitude;
    private ImageView  imgBackAddShop;
    private Button btnAddShop;

    //    for sensor
    private SensorManager sensorManager;

    //    for notification
    private int id =1;
    CreateChannel createChannel = new CreateChannel(this);
    private NotificationManagerCompat notificationManagerCompat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);

        etShopName = findViewById(R.id.etShopName);
        etShopLatitude= findViewById(R.id.etAddShopLatitude);
        etShoeLongitude= findViewById(R.id.etAddShopLongitude);
        imgBackAddShop = findViewById(R.id.imgBackAddShop);
        btnAddShop= findViewById(R.id.btnAddShop);

        //channel to handle notification
        notificationManagerCompat = NotificationManagerCompat.from(this);
        createChannel.createChannel();

        // click listener to start intent
        imgBackAddShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddShopActivity.this, AdminDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnAddShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                validate before saving
                if (textValidation()) {
                    save();
                }
            }
        });

//        calling sensor gyroscope
        sensorGyro();
//        calling sensor acclerometer
        sensorAcclerometer();
    }


//    function to validate text field
    private boolean textValidation() {
        boolean validate = true;
        if (TextUtils.isEmpty(etShopName.getText().toString())) {
            etShopName.requestFocus();
            Toast.makeText(AddShopActivity.this, "Please Enter Shop Nama", Toast.LENGTH_SHORT).show();
            validate = false;
        }
        if (TextUtils.isEmpty(etShopLatitude.getText().toString())) {
            etShopLatitude.requestFocus();
            Toast.makeText(AddShopActivity.this, "Please Enter Latitude", Toast.LENGTH_SHORT).show();
            validate = false;
        }
        if (TextUtils.isEmpty(etShoeLongitude.getText().toString())) {
            etShoeLongitude.requestFocus();
            Toast.makeText(AddShopActivity.this, "Please Enter Longiude", Toast.LENGTH_SHORT).show();
            validate = false;
        }
        return validate;
    }



    private void save() {
        String shopName = etShopName.getText().toString();
        double latitude = Double.parseDouble(etShopLatitude.getText().toString());
        double longitude = Double.parseDouble(etShoeLongitude.getText().toString());

        //        instance of login business logic layer
        final ShopBLL shopBLL= new ShopBLL(shopName, latitude, longitude);
        StrictMode.StrictMode();
        if (shopBLL.addShop()){
            clearText();
            addShopNotification();
        }else{
            Toast.makeText(this, "Item not added", Toast.LENGTH_SHORT).show();
        }
    }

    //    notification to add shop
    private void addShopNotification() {
        Notification notification = new NotificationCompat.Builder(this,CreateChannel.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_message)
                .setContentTitle("Shop Added")
                .setContentText("Shop Added successfully")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManagerCompat.notify(id, notification);
        id++;
    }



    private void clearText(){
        etShoeLongitude.setText("");
        etShopLatitude.setText("");
        etShopName.setText("");
    }



    private void sensorGyro(){
        //        used to get information from sensor
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.values[1] < 0 ){
//                    move to right for another activity

                }else if (event.values[1] > 0){
//                     move to dashbaoard
                    Intent intent = new Intent(AddShopActivity.this, AdminDashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        if (sensor != null){
            sensorManager.registerListener(sensorEventListener, sensor,SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            Toast.makeText(this, "No sensor found", Toast.LENGTH_SHORT).show();
        }
    }


    private void sensorAcclerometer(){
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener acclerometer = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] values = event.values;
//                if x axis has value more than 5 then add item
                if (values[0] > 5){
//                    add item
                    if (textValidation()){
                            save();
                    }
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        if (sensor != null){
            sensorManager.registerListener(acclerometer, sensor,SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            Toast.makeText(this, "No sensor found", Toast.LENGTH_SHORT).show();
        }
    }

}
