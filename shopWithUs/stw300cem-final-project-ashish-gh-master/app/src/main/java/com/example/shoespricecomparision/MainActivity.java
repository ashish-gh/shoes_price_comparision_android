package com.example.shoespricecomparision;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.shoespricecomparision.admin.AdminDashboardActivity;

import fragment.FavoriteFragment;
import fragment.SearchFragment;
import fragment.SettingFragment;
import sharedPreferences.PreferenceUtility;

public class MainActivity extends AppCompatActivity {

    int onStartCount=1;
    private BottomNavigationView mBottomNavigationView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
//    private Toolbar toolbar;

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("User",MODE_PRIVATE);
        if (sharedPreferences.getString("email", "") != null){
            Toast.makeText(this, "No email ", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "" + sharedPreferences.getString("email", ""), Toast.LENGTH_SHORT).show();
        }
        sharedPreferences = this.getSharedPreferences("UserType",MODE_PRIVATE);
        if (sharedPreferences.getString("userType", "") != null){
            Toast.makeText(this, "No user ", Toast.LENGTH_SHORT).show();
        }








        onStartCount = 1;
        if (savedInstanceState == null) // 1st time
        {
            this.overridePendingTransition(R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right);
            onStartCount++;
        } else // already created so reverse animation
        {
            this.overridePendingTransition(R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right);

            onStartCount = 2;
        }


        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        setupBottomNavigation();
        if (savedInstanceState == null) {
            loadSearchFragment();
        }
    }

    private void setupBottomNavigation() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        loadSearchFragment();
                        return true;
                    case R.id.action_profile:
                        loadFavoriteFragment();
                        return true;
                    case R.id.action_settings:
                        loadSettingsFragment();
                        return true;
                }
                return false;
            }
        });
    }

    private void loadSearchFragment() {
        SearchFragment searchFragment = new SearchFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragment_frame,searchFragment);
        fragmentTransaction.commit();
    }

    private void loadFavoriteFragment() {
        FavoriteFragment favoriteFragment = new FavoriteFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragment_frame,favoriteFragment);
        fragmentTransaction.commit();
    }

    private void loadSettingsFragment() {
        SettingFragment settingFragment = new SettingFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragment_frame,settingFragment);
        fragmentTransaction.commit();
    }

}
