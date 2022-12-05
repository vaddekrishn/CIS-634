package com.digitalInnovation.internshipmanagementsystem.Admin;

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

import com.digitalInnovation.internshipmanagementsystem.MainActivity;
import com.digitalInnovation.internshipmanagementsystem.R;
import com.digitalInnovation.internshipmanagementsystem.UpdateProfile;


public class AdminProfileFragment extends Fragment {
    private ImageView EditProfile, editImage, share;
    private ImageButton backbutton,UpdateBtn;
    private TextView userName, userMiddlename, lastName, mobNo, emailAddress, userPass, Name, user_Add, user_phone, user_email,userPassword;
    private SharedPreferences sp_user;
    String firstname,lastname,id,email,mobileno,CountryCode,mob,name,Token;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_profile, container, false);
        sp_user = getActivity().getSharedPreferences("admin_login", MODE_PRIVATE);
        id = sp_user.getString("userId", "");
        name = sp_user.getString("Name", "");
        email = sp_user.getString("email", "");
        mobileno = sp_user.getString("Mobile", "");
        Token = sp_user.getString("token", "");
        Log.e( "onCreateShared: ",Token );

        userName = view.findViewById(R.id.userName);
        mobNo = view.findViewById(R.id.mobNo);
        emailAddress = view.findViewById(R.id.emailAddress);
        EditProfile = view.findViewById(R.id.EditProfile);
        backbutton = view.findViewById(R.id.backbutton);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity2.class);
                startActivity(intent);
            }
        });

        mob = mobileno;
        userName.setText(name);
        mobNo.setText(mob);
        emailAddress.setText(email);

        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AdminUpdateprofile.class);
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