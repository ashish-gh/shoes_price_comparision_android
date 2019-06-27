package com.example.shoespricecomparision.admin;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shoespricecomparision.R;

import java.io.File;
import java.io.IOException;

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
    private EditText  etShoeName, etShoePrice, etShoeDescription;
    private Spinner spnBrand;
    private ImageView imgShoe, imgBackAddItem;
    private Button btnAddShoe;
    String imagePath;
    String imageName;

    private int id =1;

    CreateChannel createChannel = new CreateChannel(this);
    private NotificationManagerCompat notificationManagerCompat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        spnBrand = findViewById(R.id.spnBrand);
        etShoeName = findViewById(R.id.etAddShoeName);
        etShoePrice= findViewById(R.id.etAddShoePrice);
        etShoeDescription = findViewById(R.id.etAddShoeDescription);
        imgShoe = findViewById(R.id.imgAddShoe);
        imgBackAddItem = findViewById(R.id.imgBackAddItem);
        btnAddShoe = findViewById(R.id.btnAddShoe);

        notificationManagerCompat = NotificationManagerCompat.from(this);

//        channel to handle notification
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
                save();
            }
        });

        imgShoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseImage();
            }
        });


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

    private void save() {
        SaveImageOnly();
        String shoesBrand = "Addidas";
        String shoesName = etShoeName.getText().toString();
        float shoesPrice = Float.parseFloat(etShoePrice.getText().toString());
        String shoesDescription = etShoeDescription.getText().toString();

        Shoes shoes = new Shoes(shoesBrand,shoesName,shoesPrice,shoesDescription,imageName);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ShoesAPI shoesAPI = retrofit.create(ShoesAPI.class);

        Call<Void> itemsCall = shoesAPI.addShoes(shoes);

        itemsCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()){

                    Toast.makeText(AddItemActivity.this,"Code" + response.code(),Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(AddItemActivity.this, "Successfully Added",Toast.LENGTH_LONG).show();
                addItemNotification();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddItemActivity.this,"Error " + t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
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


}
