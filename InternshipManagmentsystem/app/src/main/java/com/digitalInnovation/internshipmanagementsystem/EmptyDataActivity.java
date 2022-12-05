package com.digitalInnovation.internshipmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class EmptyDataActivity extends AppCompatActivity {
    ImageButton backbutton,applyNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_data);

        backbutton = findViewById(R.id.backbutton);
        applyNow = findViewById(R.id.applyNow);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmptyDataActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        applyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmptyDataActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}