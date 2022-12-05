package com.digitalInnovation.internshipmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileAcitivity extends AppCompatActivity {
    private ImageView EditProfile, editImage, share;
    private ImageButton backbutton,UpdateBtn;
    private TextView userName, userMiddlename, lastName, mobNo, emailAddress, userPass, Name, user_Add, user_phone, user_email,userPassword;
    private SharedPreferences sp_user;
    String firstname,lastname,id,email,mobileno,CountryCode,mob;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_acitivity);
        sp_user = getSharedPreferences("user_login", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sp_user.edit();
        id = sp_user.getString("userId", "");
        firstname = sp_user.getString("firstName", "");
        lastname = sp_user.getString("lastName", "");
        email = sp_user.getString("email", "");
        CountryCode = sp_user.getString("country_code", "");
        mobileno = sp_user.getString("mobile", "");

        Log.e( "onCreateView: ",id+firstname+lastname+email+mobileno );

        userName = findViewById(R.id.userName);
        lastName = findViewById(R.id.lastName);
        mobNo = findViewById(R.id.mobNo);
        emailAddress =findViewById(R.id.emailAddress);
        EditProfile = findViewById(R.id.EditProfile);
        backbutton = findViewById(R.id.backbutton);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileAcitivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        mob = CountryCode+mobileno;
        userName.setText(firstname);
        lastName.setText(lastname);
        mobNo.setText(mob);
        emailAddress.setText(email);

        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileAcitivity.this,UpdateProfile.class);
                startActivity(intent);
            }
        });
    }
}