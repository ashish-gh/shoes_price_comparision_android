package com.example.shoespricecomparision.admin;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
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
import java.io.InputStream;
import java.net.URL;
import java.util.List;

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

public class UpdateShoesActivity extends AppCompatActivity {

    int onStartCount = 0;

    private EditText etShoeName, etShoePrice, etShoeDescription;
    private Spinner spnBrand;
    private ImageView imgShoe, imgBackUpdateShoes;
    private Button btnUpdateShoe;
    String imagePath;
    String imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_shoes);


//        setting slide animation for this page
        onStartCount = 1;
        if (savedInstanceState == null) // 1st time
        {
            this.overridePendingTransition(R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right);
        } else // already created so reverse animation
        {
            onStartCount = 2;
        }


        etShoeName = findViewById(R.id.etUpdateShoesName);
        etShoePrice = findViewById(R.id.etUpdateShoesPrice);
        etShoeDescription = findViewById(R.id.etUpdateShoesDescription);
        spnBrand  = findViewById(R.id.spnBrandUpdate);
        imgShoe = findViewById(R.id.imgUpdateShoes);
        imgBackUpdateShoes = findViewById(R.id.imgBackUpdateShoes);
        btnUpdateShoe = findViewById(R.id.btnUpdateShoes);


//        intent to go back to admin page
        imgBackUpdateShoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateShoesActivity.this, AdminDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        to update shoes in database
        btnUpdateShoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

//      to browse image
        imgShoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseImage();
            }
        });

//      for description on clicked image
        strictMode();
        URL url = null;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            imgShoe.setImageResource(bundle.getInt("shoesImage"));
//            change the url to get image
            try {
                url = new URL("http://10.0.2.2:8000/uploads/"+bundle.getString("shoesImage"));
                imgShoe.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
            } catch (IOException e) {
                e.printStackTrace();
            }

//            setting values in the edit text
            etShoeName.setText(bundle.getString("shoesName"));
            etShoePrice.setText(bundle.getInt("shoesPrice"));
            etShoeDescription.setText("shoesDescription");
        }
    }


//     onStart() is called when activity is visible to user
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (onStartCount > 1) {
            this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);

        } else if (onStartCount == 1) {
            onStartCount++;
        }

    }


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
//        imagePath = getRealPathFromUri(uri);
//        previewImage(imagePath); //after getting imagepath display it in imageview
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

    private void update() {
//        SaveImageOnly();
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

        Call<Void> itemsCall = shoesAPI.updateShoes(shoes);

        itemsCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()){

                    Toast.makeText(UpdateShoesActivity.this,"Code" + response.code(),Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(UpdateShoesActivity.this, "Successfully Added",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UpdateShoesActivity.this,"Error " + t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void SaveImageOnly() {
        File file = new File(imagePath);
        Toast.makeText(this,""+file,Toast.LENGTH_LONG).show();

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile",file.getName(),requestBody);

        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);
        Call<ImageResponse> responseBodyCall = shoesAPI.uploadImage(body);

        strictMode();

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




    private void strictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

}
