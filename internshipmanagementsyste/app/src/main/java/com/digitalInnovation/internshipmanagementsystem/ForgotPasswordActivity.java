package com.digitalInnovation.internshipmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class ForgotPasswordActivity extends AppCompatActivity {
    TextInputEditText regUsername;
    ImageButton sendLinkbtn,backArrow;
    String username;
    int code;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
       // getSupportActionBar().hide();

        regUsername =findViewById(R.id.regUsername);
        sendLinkbtn =findViewById(R.id.sendLinkbtn);
        backArrow =findViewById(R.id.backArrow);

        sendLinkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = regUsername.getText().toString();
                if (Patterns.EMAIL_ADDRESS.matcher(regUsername.getText().toString()).matches()){

                    Intent intent = new Intent(ForgotPasswordActivity.this,UserVerificationActivity.class);
                    intent.putExtra("UserEmail",username);
                    startActivity(intent);
                }else{

                    Toast.makeText(ForgotPasswordActivity.this, "Enter Valid Email ID", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



}