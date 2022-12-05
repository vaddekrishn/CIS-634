package com.digitalInnovation.internshipmanagementsystem.Admin;

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
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.digitalInnovation.internshipmanagementsystem.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


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
    String EmaiID,UserID,Token,name,mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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
                Intent intent = new Intent(MainActivity2.this, AdminProfileActivity.class);
                startActivity(intent);
            }
        });

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.start, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nvBranch,R.id.nvLogoutadmin,R.id.adminhome).setDrawerLayout(drawerLayout).build();
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

        sp_user = getSharedPreferences("admin_login", MODE_PRIVATE);
        UserID = sp_user.getString("userId", "");
        EmaiID = sp_user.getString("email", "");

        Log.e( "onAdminLogin: ",UserID );

        if (EmaiID.equals("")) {
            Intent intent = new Intent(MainActivity2.this, AdminLoginActivity.class);
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
            case R.id.adminhome:
                // replaceFragment(new HomeFragment());
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nvBranch:
                startActivity(new Intent(this, BranchActivity.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;


            case R.id.nvLogoutadmin:
                sp_user = getSharedPreferences("admin_login", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sp_user.edit();
                id = sp_user.getString("id", "");
                Token = sp_user.getString("token", "");
                name = sp_user.getString("name", "");
                mobile = sp_user.getString("Mobile","");
                email = sp_user.getString("email", "");
                myEdit.clear();
                myEdit.commit();
                logout();
                break;
        }

        return true;

    }

    private void logout() {
        String URL = "http://ims.ditests.com/api/admin/logout";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("onResponseThree: ",response.toString() );
                        Toast.makeText(MainActivity2.this, "Logout successfully !", Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(MainActivity2.this,AdminLoginActivity.class);
                        startActivity(intent);

                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    //    Toast.makeText(MainActivity2.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(MainActivity2.this).add(request);
    }
    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

}
