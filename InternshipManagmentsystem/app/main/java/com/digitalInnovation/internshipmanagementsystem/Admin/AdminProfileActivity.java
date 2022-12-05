package com.digitalInnovation.internshipmanagementsystem.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalInnovation.internshipmanagementsystem.MainActivity;
import com.digitalInnovation.internshipmanagementsystem.ProfileAcitivity;
import com.digitalInnovation.internshipmanagementsystem.R;
import com.digitalInnovation.internshipmanagementsystem.UpdateProfile;

public class AdminProfileActivity extends AppCompatActivity {
    private ImageView EditProfile, editImage, share;
    private ImageButton backbutton,UpdateBtn;
    private TextView userName, adminName, lastName, mobNo, emailAddress, userPass, Name, user_Add, user_phone, user_email,userPassword;
    private SharedPreferences sp_user;
    String firstname,lastname,id,email,mobileno,CountryCode,mob,name,Token,adminMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        sp_user = getSharedPreferences("admin_login", MODE_PRIVATE);
        id = sp_user.getString("userId", "");
        name = sp_user.getString("Name", "");
        adminMobile =sp_user.getString("Mobile","");
        email = sp_user.getString("email", "");
        Token = sp_user.getString("token", "");
        Log.e( "onCreateShared: ",name +adminMobile + Token );



        adminName = findViewById(R.id.adminName);
        mobNo = findViewById(R.id.mobNo);
        emailAddress =findViewById(R.id.emailAddress);
        EditProfile = findViewById(R.id.EditProfile);
        backbutton = findViewById(R.id.backbutton);

        mob = adminMobile;
        adminName.setText(name);
        mobNo.setText(mob);
        emailAddress.setText(email);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminProfileActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });



        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminProfileActivity.this, AdminUpdateprofile.class);
                startActivity(intent);
            }
        });
    }
}