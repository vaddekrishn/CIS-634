package com.digitalInnovation.internshipmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.digitalInnovation.internshipmanagementsystem.Admin.MainActivity2;

public class SplashScreenActivity extends AppCompatActivity {

    SharedPreferences sp_User,sp_Admin;
    String user_id,user_email,user_mobile,user_address,user_countrycode,user_token,user_firstname,user_lastname;
    String admin_id,admin_email,admin_mobile,admin_name,admin_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);






        // getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Thread td=new Thread(){

            public void run(){

                try {
                    sleep(2000);


                }catch (Exception ex){

                    ex.printStackTrace();

                }finally {
                    //User Sharedpreferences
                    sp_User = getSharedPreferences("user_login", MODE_PRIVATE);
                    user_id = sp_User.getString("id", "");
                    user_token = sp_User.getString("token", "");
                    user_firstname = sp_User.getString("first_name", "");
                    user_lastname = sp_User.getString("last_name", "");
                    user_email = sp_User.getString("email", "");
                    user_countrycode = sp_User.getString("country_code", "");
                    user_mobile = sp_User.getString("mobile", "");
                    Log.e( "onCreateUSerS: ",user_id+user_token+user_firstname+user_lastname+user_email+user_countrycode+user_mobile );

                    //Admin Sharedpreferences
                    sp_Admin = getSharedPreferences("admin_login", MODE_PRIVATE);
                    admin_id =  sp_Admin .getString("id", "");
                    admin_token =  sp_Admin .getString("token", "");
                    admin_name =  sp_Admin .getString("name", "");
                    admin_mobile =  sp_Admin .getString("Mobile","");
                    admin_email =  sp_Admin .getString("email", "");
                    Log.e("onCreateAdminSP: ",admin_id+admin_token+admin_name+admin_name+admin_mobile+admin_email );


                    if(user_mobile.length() > 0){

                        Intent intent = new Intent(SplashScreenActivity.this,MainActivity.class);
                        startActivity(intent);
                    }else if (admin_mobile.length() > 0){
                        Intent intent = new Intent(SplashScreenActivity.this, MainActivity2.class);
                        startActivity(intent);

                    }else{

                        Intent intent = new Intent(SplashScreenActivity.this,OnBoardingActivity.class);
                        startActivity(intent);

                    }

                }

            }

        };td.start();
    }




}