package com.example.shoespricecomparision.admin;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.CursorLoader;
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
import com.example.shoespricecomparision.admin.BLL.ShoesBLL;

import java.io.File;
import java.io.IOException;

import animation.Animation;
import channel.CreateChannel;
import model.ImageResponse;
import model.Shoes;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import shoesAPI.ShoesAPI;
import url.Url;

public class AddItemActivity extends AppCompatActivity {
    private EditText  etShoeName, etShoePrice, etShoeDescription, etBrand, etShop;
    private ImageView imgShoe, imgBackAddItem;
    private Button btnAddShoe;
    String imagePath;
    String imageName;

    // animation class
    private Animation animation;

    //for notification
    private int id =1;
    CreateChannel createChannel = new CreateChannel(this);
    private NotificationManagerCompat notificationManagerCompat;

    //for sensor
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        animation= new Animation();
        animation.slideLeft(this);

        etBrand = findViewById(R.id.etBrandAddShoe);
        etShoeName = findViewById(R.id.etAddShoeName);
        etShoePrice= findViewById(R.id.etAddShoePrice);
        etShoeDescription = findViewById(R.id.etAddShoeDescription);
        imgShoe = findViewById(R.id.imgAddShoe);
        etShop = findViewById(R.id.etAddShop);
        imgBackAddItem = findViewById(R.id.imgBackAddItem);
        btnAddShoe = findViewById(R.id.btnAddShoe);


//        channel to handle notification
        notificationManagerCompat = NotificationManagerCompat.from(this);
        createChannel.createChannel();

//        intent to go back to admin page
        imgBackAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddItemActivity.this, AdminDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });


//        to add shoe in database
        btnAddShoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textValidation()){
                        save();
                }
            }
        });

//        to browse image
        imgShoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseImage();
            }
        });

//        calling sensor gyroscope
        sensorGyro();

//        calling sensor acclerometer
        sensorAcclerometer();

    }


    //    browse function to search image
    private void browseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK); //to browse image
        intent.setType("image/*"); //user now can only select the image
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(data==null){
                Toast.makeText(this,"Please Select an image",Toast.LENGTH_LONG).show();
            }
        }
        Uri uri = data.getData();
        imagePath = getRealPathFromUri(uri);
        previewImage(imagePath); //after getting imagepath display it in imageview
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projection,null,null,null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();;
        return result;
    }

    private void previewImage(String imagepath) {
        File imgFlie = new File(imagepath);
        if(imgFlie.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFlie.getAbsolutePath());
            imgShoe.setImageBitmap(myBitmap);
        }
    }

//
    private void save() {
        SaveImageOnly();
        String shoesBrand = etBrand.getText().toString().trim();
        String shoesName = etShoeName.getText().toString().trim();
        float shoesPrice = Float.parseFloat(etShoePrice.getText().toString());
        String shoesDescription = etShoeDescription.getText().toString();
        int shopid = Integer.parseInt(etShop.getText().toString());

        //        instance of login business logic layer
        final ShoesBLL shoesBLL= new ShoesBLL(shoesBrand,shoesName, shoesPrice,shoesDescription,imageName,shopid);
        strictMode.StrictMode.StrictMode();
        if (shoesBLL.addShoes()){
            addItemNotification();
        }else{
        }
    }



//    notification to add item
    private void addItemNotification() {
        Notification notification = new NotificationCompat.Builder(this,CreateChannel.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_message)
                .setContentTitle("Item Added")
                .setContentText("Item Added successfully")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManagerCompat.notify(id, notification);
        id++;
    }


    private void SaveImageOnly() {
        File file = new File(imagePath);
        Toast.makeText(this,""+file,Toast.LENGTH_LONG).show();
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile",file.getName(),requestBody);
        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);
        Call<ImageResponse> responseBodyCall = shoesAPI.uploadImage(body);
        StrictMode();
        try {
            Response<ImageResponse> imageResponseResponse = responseBodyCall.execute();
            //After saving an image, retrieve the current name of the image
            imageName = imageResponseResponse.body().getFilename();
            Toast.makeText(this,""+imageName,Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    private void StrictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    // text validation
    private boolean textValidation() {
        boolean validate = true;
        if (TextUtils.isEmpty(etShoeName.getText().toString())) {
            etShoeName.requestFocus();
            Toast.makeText(AddItemActivity.this, "Please Enter Shoes Name", Toast.LENGTH_SHORT).show();
            validate = false;
        }
        if (TextUtils.isEmpty(etShoePrice.getText().toString())) {
            etShoePrice.requestFocus();
            Toast.makeText(AddItemActivity.this, "Please Enter Price", Toast.LENGTH_SHORT).show();
            validate = false;
        }
        if (TextUtils.isEmpty(etShoeDescription.getText().toString())) {
            etShoeDescription.requestFocus();
            Toast.makeText(AddItemActivity.this, "Please Enter Decription", Toast.LENGTH_SHORT).show();
            validate = false;
        }
        if (TextUtils.isEmpty(etBrand.getText().toString())) {
            etBrand.requestFocus();
            Toast.makeText(AddItemActivity.this, "Please Enter Brand", Toast.LENGTH_SHORT).show();
            validate = false;
        }
        return validate;
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
                    Intent intent = new Intent(AddItemActivity.this, AdminDashboardActivity.class);
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
                        if (imageName !=null){
                            save();
                        }else {
                            Toast.makeText(AddItemActivity.this, "Please select valid image", Toast.LENGTH_SHORT).show();
                        }
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
