package com.digitalInnovation.internshipmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.digitalInnovation.internshipmanagementsystem.Admin.AdminLoginActivity;

public class WelcomeActivity extends AppCompatActivity {
    private ImageButton userSignIn,userSignUp,adminLogin;
    SharedPreferences sp_user;
    String EmaiID,UserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
       // getSupportActionBar().hide();



        userSignIn = findViewById(R.id.userSignIn);
        userSignUp = findViewById(R.id.userSignUp);
        adminLogin = findViewById(R.id.adminLogin);

        userSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        userSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, AdminLoginActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}