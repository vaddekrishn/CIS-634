package com.digitalInnovation.internshipmanagementsystem.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.digitalInnovation.internshipmanagementsystem.ForgotPasswordActivity;
import com.digitalInnovation.internshipmanagementsystem.R;
import com.digitalInnovation.internshipmanagementsystem.UserVerificationActivity;
import com.google.android.material.textfield.TextInputEditText;

public class AdminForgotpassActivity extends AppCompatActivity {
    TextInputEditText regUsername;
    ImageButton sendLinkbtn,backArrow;
    String username;
    int code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_forgotpass);

        regUsername =findViewById(R.id.regUsername);
        sendLinkbtn =findViewById(R.id.sendLinkbtn);
        backArrow =findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminForgotpassActivity.this, AdminLoginActivity.class);
                startActivity(intent);
            }
        });

        sendLinkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = regUsername.getText().toString();
                if (Patterns.EMAIL_ADDRESS.matcher(regUsername.getText().toString()).matches()){

                    Intent intent = new Intent(AdminForgotpassActivity.this, AdminVerificationActivity.class);
                    intent.putExtra("UserEmail",username);
                    startActivity(intent);
                }else{

                    Toast.makeText(AdminForgotpassActivity.this, "Enter Valid Email ID", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}