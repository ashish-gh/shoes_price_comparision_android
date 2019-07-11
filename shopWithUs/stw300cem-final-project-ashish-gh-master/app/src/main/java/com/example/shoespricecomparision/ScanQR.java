package com.example.shoespricecomparision;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.google.zxing.Result;

import fragment.SearchFragment;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanQR extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
    }
    @Override
    public void handleResult(Result result) {

//        check if obtained text is valid or not
//        to check if text is url
        boolean isValid = URLUtil.isValidUrl(result.getText());
        if (isValid){
//            handle this event with Intent to open in browser
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(result.getText()));
            startActivity(intent);
        }else{
            SearchFragment.etSearchShoes.setText(result.getText());
        }

        SearchFragment.etSearchShoes.setText(result.getText());
//        display result here
//        HomeActivity.etSearchRestaurant.setText(result.getText());
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();

        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}
