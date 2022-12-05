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


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
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
     ImageButton profilebtn;
     SharedPreferences sp_user;
     Context context;
     TextView aboutUs;
    String id,email,mobileno,firstname,lastname,CountryCode;
     String EmaiID,UserID,Token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        CheckSp();
        // updateNavheader();

        navigationView = findViewById(R.id.navigation_view);

        // navigationView = findViewById(R.id.navigation_view);
        navController = Navigation.findNavController(this, R.id.frame_layout);
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        aboutUs = findViewById(R.id.aboutUs);
        profilebtn = findViewById(R.id.profilebtn);

        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ProfileAcitivity.class);
                startActivity(intent);
            }
        });

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.start, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nvMech,R.id.nvElect,R.id.nvCivil,R.id.nvComp,R.id.nvLogout,R.id.bnhome).setDrawerLayout(drawerLayout).build();
        //Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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
        UserID = sp_user.getString("userId", "");
        EmaiID = sp_user.getString("email", "");

        Log.e( "onUserLogin: ",UserID );

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
            case R.id.bnhome:
                // replaceFragment(new HomeFragment());
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nvMech:
                 startActivity(new Intent(this, MechanicalEnggActivity.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.nvCivil:
                startActivity(new Intent(this, CivilEngineeringActivity.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.nvElect:
                startActivity(new Intent(this, ElectectricalEnggActivity.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.nvComp:
                startActivity(new Intent(this, CompterScienceEngg.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

//            case R.id.nvdevelopers:
//                startActivity(new Intent(MainActivity.this, DeveloperActivty.class));
//                break;
            case R.id.nvLogout:


                sp_user = getSharedPreferences("user_login", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sp_user.edit();
                id = sp_user.getString("id", "");
                Token = sp_user.getString("token", "");
                firstname = sp_user.getString("first_name", "");
                lastname = sp_user.getString("last_name", "");
                email = sp_user.getString("email", "");
                CountryCode = sp_user.getString("country_code", "");
                mobileno = sp_user.getString("mobile", "");
                myEdit.clear();
                myEdit.commit();
                logout();
                break;
        }

        return true;
    }

    private void logout() {

        String URL = "http://ims.ditests.com/api/logout";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("onResponseThree: ",response.toString() );
                        Toast.makeText(MainActivity.this, "Logout successfully !", Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);

                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
        {

                //This is for Headers If You Needed
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization:","Bearer "+Token);
                Log.e( "getHeaders: ",Token );
                return params;
            }
        };
        int initialTimeoutMs = 0;
        request.setRetryPolicy(new DefaultRetryPolicy(200 * 30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(MainActivity.this).add(request);
    }


    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }


}