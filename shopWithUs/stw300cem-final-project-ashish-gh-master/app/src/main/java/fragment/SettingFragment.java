package fragment;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoespricecomparision.LoginActivity;
import com.example.shoespricecomparision.MainActivity;
import com.example.shoespricecomparision.R;
import com.example.shoespricecomparision.SignupActivity;
import com.example.shoespricecomparision.admin.ListShoesActivity;
import com.example.shoespricecomparision.admin.UpdateShoesActivity;

import java.io.File;
import java.io.IOException;
import java.util.List;

import adapter.ShoesAdapterAdmin;
import model.ImageResponse;
import model.Shoes;
import model.User;
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

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;


public class SettingFragment extends Fragment implements View.OnClickListener{

    private ImageView imgProfile;
    private TextView userName;
    public static final int CAMERA_REQUEST_CODE = 1999;
    private Button btnLoginProfile;
    private TextView tvFistName,tvLastName,tvWelcome;
    private EditText etEmail, etContact, etPassword,etConfirmPassword;
    private Button btnLogout;


    private LinearLayout linearLayout;
    private RelativeLayout relativeLayout;
    private CardView cardViewLogin, cardViewUpdateUser;

    int id;
    String imagePath;
    String imageName;
    private Button btnUpdate;

    // for shared preference
    SharedPreferences sharedPreferences;



    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the shoes_admin for this fragment
        final View view = inflater.inflate(R.layout.fragment_setting,container,false);

        //        to make keyboard floatable
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST);

        imgProfile = view.findViewById(R.id.imgProfile);
        tvFistName = view.findViewById(R.id.tvFirstNameProfile);
        tvLastName = view.findViewById(R.id.tvLastNameProfile);
        tvWelcome= view.findViewById(R.id.tvWelcome);
        etEmail = view.findViewById(R.id.etEmailProfile);
        etContact = view.findViewById(R.id.etContactProfile);
        etPassword = view.findViewById(R.id.etPasswordProfile);
        etConfirmPassword = view.findViewById(R.id.etConfirmPasswordProfile);
        tvFistName = view.findViewById(R.id.tvFirstNameProfile);
        tvLastName= view.findViewById(R.id.tvLastNameProfile);
        btnLoginProfile = view.findViewById(R.id.btnLoginProfile);
        cardViewLogin = view.findViewById(R.id.cardLoginAccount);
        cardViewUpdateUser= view.findViewById(R.id.cardUpdateUserProfile);
        btnUpdate = view.findViewById(R.id.btnUpdateProfile);

        btnLogout = view.findViewById(R.id.btnLogoutUser);


        linearLayout= view.findViewById(R.id.lnrUserDetails);
        relativeLayout= view.findViewById(R.id.rtlLoginProfile);


        btnLoginProfile.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        imgProfile.setOnClickListener(this);
        imgProfile.setOnClickListener(this);

        sharedPreferences =  getActivity().getSharedPreferences("UserType",MODE_PRIVATE);

        if (Url.accessToken.length() > 5){
//            when user is logged in
            relativeLayout.setVisibility(View.GONE);
            tvWelcome.setVisibility(View.GONE);
            getUser();
            checkPermission();
        }else {
//            when user is not logged in
            linearLayout.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.GONE);
            tvWelcome.setText("Welcome");
            tvFistName.setVisibility(View.GONE);
            tvLastName.setVisibility(View.GONE);
            imgProfile.setClickable(false);
            imgProfile.setImageResource(R.drawable.noimage);
            btnLogout.setVisibility(View.GONE);
//            cardViewUpdateUser.setVisibility(View.INVISIBLE);

        }
//        to check permission for user taking photo
        checkPermission();
        return view;
    }

    private void getUser() {

        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);

        Call<List<User>> listCall = shoesAPI.getUserById(Url.userId);
        listCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()){
//                    use snackbar here
                    Toast.makeText(getActivity(), "Code : "+ response.code() , Toast.LENGTH_SHORT).show();
                }

                List<User> users =  response.body();

                for (int i =0  ; i<users.size(); i++){
                    tvFistName.setText(users.get(i).getFirstName());
                    tvLastName.setText(users.get(i).getLastName());
                    etEmail.setText(users.get(i).getEmail());
                    etContact.setText(users.get(i).getContact());
                    etEmail.setText(users.get(i).getEmail());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error : " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void onClick(View v) {
//        to update user profile

        if (v.getId()==R.id.btnUpdateProfile){
            if (textvalidation()){
                if (checkPassword()){
                    if (passwordValidation()){
                        updateUser();
                    }

                }
            }
        }


        if (v.getId()==R.id.btn){
            Toast.makeText(getActivity(), "hell", Toast.LENGTH_SHORT).show();
        }
        if (v.getId() == R.id.btnLoginProfile){
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().getFragmentManager().beginTransaction().remove(this).commit();
        }

        if (v.getId()== R.id.imgProfile){
            final Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.custom_popup);
            dialog.setTitle("Title...");
            dialog.show();

            ImageView imgGallery = dialog.findViewById(R.id.imageGalleryProfile);
            ImageView imgCamera = dialog.findViewById(R.id.imageCameraProfile);
            imgGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    browseImage();
                    dialog.dismiss();
                }
            });

            imgCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadCamera();
                    dialog.dismiss();

                }
            });
            if (v.getId() == R.id.imageGalleryProfile){
            }
        }else if(v.getId() == R.id.imageCameraProfile){
        }else if (v.getId() == R.id.imageGalleryProfile){
        }

    }

        public Boolean checkPassword() {
            if (etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                return true;
            } else {
                Toast.makeText(getActivity(), "Password Not Matched", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

    private boolean textvalidation() {
        boolean validate =true;
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.requestFocus();
            Toast.makeText(getActivity(), "Please Enter Email", Toast.LENGTH_SHORT).show();
            validate = false;
        }

        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.requestFocus();
            Toast.makeText(getActivity(), "Please Enter Password", Toast.LENGTH_SHORT).show();
            validate = false;
        }

        if (TextUtils.isEmpty(etContact.getText().toString())) {
            etContact.requestFocus();
            Toast.makeText(getActivity(), "Please Enter contact", Toast.LENGTH_SHORT).show();
            validate = false;
        }

        if (TextUtils.isEmpty(etConfirmPassword.getText().toString())) {
            etConfirmPassword .requestFocus();
            Toast.makeText(getActivity(), "Please Enter Password", Toast.LENGTH_SHORT).show();
            validate = false;
        }
        return validate;

    }


    public Boolean passwordValidation() {
        if (etPassword.getText().toString().length() >= 6 && etPassword.getText().toString().length() <= 10) {
            return true;
        } else {
            Toast.makeText(getActivity(), "Password should be more than 6 and less than 10 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    //    browse function to search image
    private void browseImage() {
        id =1;
        Intent intent = new Intent(Intent.ACTION_PICK); //to browse image
        intent.setType("image/*"); //user now can only select the image
        startActivityForResult(intent,CAMERA_REQUEST_CODE);
    }

//    to update user

    private void updateUser() {

        Log.d(TAG, "updateUser: this is user id" +Url.userId);
        saveImageOnly();
        String fistName = tvFistName.getText().toString();
        String lastName = tvLastName.getText().toString();
        String email =etEmail.getText().toString();
        String contact = etContact.getText().toString();
        String password = etPassword.getText().toString();
//        String imageName = "profile.jpg";


        Log.d(TAG, "updateUser: imgae name " + imageName);


        User user = new User(fistName,lastName,contact,email,password,imageName,"user");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ShoesAPI shoesAPI = retrofit.create(ShoesAPI.class);

        Call<Void> itemsCall = shoesAPI.updateUser(Url.userId, user);

        itemsCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()){

                    Toast.makeText(getActivity(),"Code" + response.code(),Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getActivity(), "Successfully Updated",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(),"Error " + t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadCamera(){
        id=2;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) !=null){
            startActivityForResult(intent,CAMERA_REQUEST_CODE);
        }
    }


    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]
                    {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},0);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (id ==1) {
            if(resultCode == Activity.RESULT_OK){
                if(data==null){
                    Toast.makeText(getActivity(),"Please Select an image",Toast.LENGTH_LONG).show();
                }
            }
            Uri uri = data.getData();
            imagePath = getRealPathFromUri(uri);
            previewImage(imagePath);

        }else if (id==2){
//        to display image
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                if (bitmap != null) {
                    Bundle extra = data.getExtras();
                    Bitmap imageMap = (Bitmap) extra.get("data");
                    imgProfile.setImageBitmap(imageMap);
                }
            }
            }

    }


    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getActivity().getApplicationContext(), uri, projection,null,null,null);
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
            imgProfile.setImageBitmap(myBitmap);

        }
    }


    private void saveImageOnly() {
        File file = new File(imagePath);
        Toast.makeText(getActivity(),""+file,Toast.LENGTH_LONG).show();

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile",file.getName(),requestBody);

        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);
        Call<ImageResponse> responseBodyCall = shoesAPI.uploadImage(body);

        strictMode();
        try {
            Response<ImageResponse> imageResponseResponse = responseBodyCall.execute();
            //After saving an image, retrieve the current name of the image
            imageName = imageResponseResponse.body().getFilename();
            Toast.makeText(getActivity(),""+imageName,Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            Toast.makeText(getActivity(),"Error",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void strictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }


}
