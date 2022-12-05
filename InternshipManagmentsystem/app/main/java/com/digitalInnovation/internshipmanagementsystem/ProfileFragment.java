package com.digitalInnovation.internshipmanagementsystem;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileFragment extends Fragment {
    private ImageView EditProfile, editImage, share;
    private ImageButton backbutton,UpdateBtn;
    private TextView userName, userMiddlename, lastName, mobNo, emailAddress, userPass, Name, user_Add, user_phone, user_email,userPassword;
    private SharedPreferences sp_user;
    String firstname,lastname,id,email,mobileno,CountryCode,mob;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //user_id, first_name,  last_name,  email,  country_code, mobile,password

        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        sp_user = getActivity().getSharedPreferences("user_login", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sp_user.edit();
        id = sp_user.getString("userId", "");
        firstname = sp_user.getString("firstName", "");
        lastname = sp_user.getString("lastName", "");
        email = sp_user.getString("email", "");
        CountryCode = sp_user.getString("country_code", "");
        mobileno = sp_user.getString("mobile", "");

        Log.e( "onCreateView: ",id+firstname+lastname+email+mobileno );

        userName = view.findViewById(R.id.userName);
        lastName = view.findViewById(R.id.lastName);
        mobNo = view.findViewById(R.id.mobNo);
        emailAddress = view.findViewById(R.id.emailAddress);
        EditProfile = view.findViewById(R.id.EditProfile);
        backbutton = view.findViewById(R.id.backbutton);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),MainActivity.class);
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
                Intent intent = new Intent(getContext(),UpdateProfile.class);
                startActivity(intent);
            }
        });

        return  view;
    }
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}