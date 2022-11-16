package com.digitalInnovation.internshipmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
     BottomNavigationView bottomNavigationView;
     NavController navController;
     DrawerLayout drawerLayout;
     ActionBarDrawerToggle toggle;
     NavigationView navigationView;
     Fragment frame_layout;
     FrameLayout fl;
     Toolbar toolbar;
     AppBarConfiguration mAppBarConfiguration;
     ImageButton wishlistBtn,cartBtn,locationButton;
     SharedPreferences sp_user;
     Context context;
     TextView aboutUs;
    String user_Latitude,user_Longitude,user_Lat,user_Long,user_city;
     String FirstName, LastName, MiddleName, MobileNumber, EmaiID, Password, Cpassword, DateOfBirth,Username,ProfilePicturePath,UserID,CompanyID,UserTypeID,UserRoleID,IsActive,CreatedBy,UserLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        CheckSp();
        // updateNavheader();

        navigationView = findViewById(R.id.navigation_view);

        // navigationView = findViewById(R.id.navigation_view);
        navController = Navigation.findNavController(this, R.id.frame_layout);
        wishlistBtn = findViewById(R.id.wishlistBtn);
        cartBtn = findViewById(R.id.cartBtn);
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        locationButton = findViewById(R.id.locationButton);
        aboutUs = findViewById(R.id.aboutUs);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.start, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nvMech,R.id.nvElect,R.id.nvCivil,R.id.nvComp).setDrawerLayout(drawerLayout).build();
        //  Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);//start
        NavigationUI.setupActionBarWithNavController(this,navController,mAppBarConfiguration);
        //  NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(toolbar,navController, mAppBarConfiguration);

        /* bottomNavigation work */
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);


    }

    private void CheckSp() {

        sp_user = getSharedPreferences("user_login", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sp_user.edit();
        UserID = sp_user.getString("id", "");
        EmaiID = sp_user.getString("email", "");

        Log.e( "onCreate: ",UserID );

        if (EmaiID.equals("")) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }


    /* using this below code we can click on drwer menu icon and drawer menu will open and close */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (toggle.onOptionsItemSelected(item)) {
            return true;

        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else

            super.onBackPressed();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        frame_layout = null;
        switch (item.getItemId()) {
            case R.id.nvMech:
                // startActivity(new Intent(this, OrderActivity.class));
                break;

            case R.id.nvCivil:
                //   startActivity(new Intent(this, ContactActivity.class));
                break;

            case R.id.nvElect:
                //   startActivity(new Intent(this, ContactActivity.class));
                break;

            case R.id.nvComp:
                //   startActivity(new Intent(this, ContactActivity.class));
                break;

//            case R.id.nvdevelopers:
//                startActivity(new Intent(MainActivity.this, DeveloperActivty.class));
//                break;
//            case R.id.nvlogout:
//                sp_user = getSharedPreferences("user_login", MODE_PRIVATE);
//                SharedPreferences.Editor myEdit = sp_user.edit();
//                id = sp_user.getString("id", "");
//                name = sp_user.getString("name", "");
//                email = sp_user.getString("email", "");
//                mobileno = sp_user.getString("mobileno", "");
//                myEdit.clear();
//                myEdit.commit();
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
//                break;
        }

        return true;
    }

//    private void logout() {
//
//        sp_user = getSharedPreferences("user_login", MODE_PRIVATE);
//        SharedPreferences.Editor myEdit = sp_user.edit();
//        UserID = sp_user.getString("UserID", "");
//        CompanyID = sp_user.getString("CompanyID", "");
//        UserTypeID = sp_user.getString("UserTypeID", "");
//        UserRoleID = sp_user.getString("UserRoleID", "");
//        Username = sp_user.getString("Username", "");
//        FirstName = sp_user.getString("FirstName", "");
//        MiddleName = sp_user.getString("MiddleName", "");
//        LastName = sp_user.getString("LastName", "");
//        EmaiID = sp_user.getString("EmaiID", "");
//        MobileNumber = sp_user.getString("MobileNumber", "");
//        DateOfBirth = sp_user.getString("DateOfBirth", "");
//        ProfilePicturePath = sp_user.getString("ProfilePicturePath", "");
//        IsActive = sp_user.getString("IsActive", "");
//        myEdit.clear();
//        myEdit.commit();
//
//        Toast.makeText(this, "logout successfully", Toast.LENGTH_SHORT).show();
//
//        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//        startActivity(intent);
//        finish();
//    }

    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }


}