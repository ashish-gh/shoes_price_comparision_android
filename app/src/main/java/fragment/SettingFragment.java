package fragment;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shoespricecomparision.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment implements View.OnClickListener{

    private ImageView imgProfile;

    private Button btn;
    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the shoes_admin for this fragment
        final View view = inflater.inflate(R.layout.fragment_setting,container,false);
        imgProfile = view.findViewById(R.id.imgProfile);
        btn = view.findViewById(R.id.btn);
        imgProfile.setOnClickListener(this);


        checkPermission();
        return view;
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn){
            Toast.makeText(getActivity(), "hell", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getActivity(), "Gallery", Toast.LENGTH_SHORT).show();
                }
            });

            imgCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Camera", Toast.LENGTH_SHORT).show();
                    loadCamera();
                    dialog.dismiss();
                }
            });

            Button dialogButton = (Button) dialog.findViewById(R.id.btn);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });



//            btn.setOnClickListener(this);
//            imgGallery.setOnClickListener(this);
//            imgProfile.setOnClickListener(this);


            if (v.getId() == R.id.imageGalleryProfile){
                Toast.makeText(getActivity(), "Open Camera", Toast.LENGTH_SHORT).show();
            }

        }else if(v.getId() == R.id.imageCameraProfile){
            Toast.makeText(getActivity(), "Open Camera", Toast.LENGTH_SHORT).show();
        }else if (v.getId() == R.id.imageGalleryProfile){
            Toast.makeText(getActivity(), "Open Gallery", Toast.LENGTH_SHORT).show();
        }

    }

    private void loadCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) !=null){
            startActivityForResult(intent,1);
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


        if (resultCode == 1 && resultCode == Activity.RESULT_OK)
        {
            Bundle extra = data.getExtras();
            Bitmap imageMap = (Bitmap) extra.get("data");
            imgProfile.setImageBitmap(imageMap);

        }
    }
}
