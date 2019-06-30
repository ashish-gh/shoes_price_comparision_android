package fragment;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoespricecomparision.LoginActivity;
import com.example.shoespricecomparision.MainActivity;
import com.example.shoespricecomparision.R;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment implements View.OnClickListener{

    private ImageView imgProfile;
    private TextView userName;
    public static final int CAMERA_REQUEST_CODE = 1999;
//    private Button btnLoginFragment;

    private TextView tvFistName;
    private EditText etEmail, etContact, etPassword;

    private CardView cardViewLogin, cardViewUpdateUser;
    int id;
    String imagePath;
    String imageName;
    private Button btnUpdate;


    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the shoes_admin for this fragment
        final View view = inflater.inflate(R.layout.fragment_setting,container,false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST);


        imgProfile = view.findViewById(R.id.imgProfile);
        userName = view.findViewById(R.id.userName);

        etEmail = view.findViewById(R.id.etEmailProfile);
        etContact = view.findViewById(R.id.etContactProfile);
        etPassword = view.findViewById(R.id.etPasswordProfile);
        tvFistName = view.findViewById(R.id.tvuserNameProfile);

//        btnLoginFragment = view.findViewById(R.id.btnLoginFragment);

        cardViewLogin = view.findViewById(R.id.cardLoginAccount);
        cardViewUpdateUser= view.findViewById(R.id.cardUpdateUserProfile);

        btnUpdate = view.findViewById(R.id.btnUpdateProfile);

//        btnLoginFragment.setOnClickListener(this);

        btnUpdate.setOnClickListener(this);
        imgProfile.setOnClickListener(this);
        imgProfile.setOnClickListener(this);
        if (Url.user != null){
//            userName.setText("Welcome, Ashish");
            checkPermission();
        }else {
            userName.setText("Welcome");
            imgProfile.setClickable(false);
//            cardViewUpdateUser.setVisibility(View.INVISIBLE);

        }
//        to check permission for user taking photo
        checkPermission();

//        to get details of current user
        getUser();

        return view;
    }

    private void getUser() {

        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);

        Call<List<User>> listCall = shoesAPI.getUserById(1);
        listCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()){
//                    use snackbar here
                    Toast.makeText(getActivity(), "Code : "+ response.code() , Toast.LENGTH_SHORT).show();
                }

                List<User> users =  response.body();

                for (int i =0  ; i<users.size(); i++){
                    tvFistName.setText(users.get(i).getFirstName() + " " + users.get(i).getLastName());
                    etEmail.setText(users.get(i).getEmail());
                    etContact.setText(users.get(i).getContact());
                    etEmail.setText(users.get(i).getEmail());
                    etPassword.setText(users.get(i).getPassword());
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
            updateUser();

            Toast.makeText(getActivity(), "Update Activity", Toast.LENGTH_SHORT).show();
        }

        if (v.getId()==R.id.btn){
            Toast.makeText(getActivity(), "hell", Toast.LENGTH_SHORT).show();
        }
        if (v.getId() == R.id.btnLoginFragment){
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
//                    Toast.makeText(getActivity(), "Gallery", Toast.LENGTH_SHORT).show();
                    browseImage();
                    dialog.dismiss();
                }
            });

            imgCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getActivity(), "Camera", Toast.LENGTH_SHORT).show();
                    loadCamera();
                    dialog.dismiss();

                }
            });
//
//            Button dialogButton = (Button) dialog.findViewById(R.id.btn);
//            // if button is clicked, close the custom dialog
//            dialogButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });



//            btn.setOnClickListener(this);
//            imgGallery.setOnClickListener(this);
//            imgProfile.setOnClickListener(this);


            if (v.getId() == R.id.imageGalleryProfile){
//                Toast.makeText(getActivity(), "Open Camera", Toast.LENGTH_SHORT).show();
            }

        }else if(v.getId() == R.id.imageCameraProfile){
//            Toast.makeText(getActivity(), "Open Camera", Toast.LENGTH_SHORT).show();
        }else if (v.getId() == R.id.imageGalleryProfile){
//            Toast.makeText(getActivity(), "Open Gallery", Toast.LENGTH_SHORT).show();
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
        saveImageOnly();
        String fistName = tvFistName.getText().toString();
        String lastName = tvFistName.getText().toString();
        String email =etEmail.getText().toString();
        String contact = etContact.getText().toString();
        String password = etPassword.getText().toString();
//        String imageName = "profile.jpg";


        User user = new User(fistName,lastName,contact,email,password,imageName,"user");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ShoesAPI shoesAPI = retrofit.create(ShoesAPI.class);

        Call<Void> itemsCall = shoesAPI.updateUser(1, user);

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
