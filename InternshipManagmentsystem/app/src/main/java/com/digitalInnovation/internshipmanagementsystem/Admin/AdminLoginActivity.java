package com.digitalInnovation.internshipmanagementsystem.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.digitalInnovation.internshipmanagementsystem.ForgotPasswordActivity;
import com.digitalInnovation.internshipmanagementsystem.LoginActivity;
import com.digitalInnovation.internshipmanagementsystem.R;
import com.digitalInnovation.internshipmanagementsystem.RegisterActivity;
import com.digitalInnovation.internshipmanagementsystem.WelcomeActivity;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class AdminLoginActivity extends AppCompatActivity {
    TextInputEditText regUsername,regPass;
    ImageButton userSignIn,backArrow;
    TextView forgetPass;
    String userName,password,firstName,lastName,userEmail,userMobile,userId,EmaiID,Token,AdminName,adminuserId,Name;
    String URL="http://ims.ditests.com/api/admin/login";
    SharedPreferences sp_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        regUsername = findViewById(R.id.regUsername);
        regPass = findViewById(R.id.regPass);
        userSignIn = findViewById(R.id.userSignIn);
        forgetPass = findViewById(R.id.forgetPass);
        backArrow = findViewById(R.id.backArrow);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminLoginActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminLoginActivity.this, AdminForgotpassActivity.class);
                startActivity(intent);
            }
        });


        userSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = regUsername.getText().toString();
                password = regPass.getText().toString();

                if (userName.isEmpty()){

                    regUsername.setError("Required!");
                    regUsername.requestFocus();
                }else if (password.isEmpty()){

                    regPass.setError("Required!");
                    regPass.requestFocus();
                }else{
                    loginAdmin();


                }

            }
        });
    }

    private void loginAdmin() {
        JSONObject jobj = new JSONObject();
        try {
            jobj.put("email",userName);
            jobj.put("password",password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                URL,
                jobj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                     //   Toast.makeText(AdminLoginActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                        try {
                            String login_sts = response.getString("success");
                            // Log.e( "onResponseCheck: ",login_sts );


                            if (login_sts.equals("true")){
                                Toast.makeText(AdminLoginActivity.this, "successfully login!", Toast.LENGTH_SHORT).show();



                                JSONObject jsonObject = response.getJSONObject("data");
                                Token = jsonObject.getString("token");
                                AdminName = jsonObject.getString("admin_name");

                                Log.e( "onResponseLogin: ",jsonObject.toString()+Token );

                                JSONObject jsonObject1 = jsonObject.getJSONObject("admin");

                                Log.e( "onResponseUser: ",jsonObject1.toString() );
                                adminuserId = jsonObject1.getString("id");
                                Name = jsonObject1.getString("name");
                                userEmail = jsonObject1.getString("email");
                                userMobile = jsonObject1.getString("mobile");

                                Log.e( "onResponseU: ",adminuserId+Name +userEmail+userMobile);

                                SharedPreferences sp_user = getSharedPreferences("admin_login",MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sp_user.edit();
                                myEdit.clear();

                                myEdit.putString("userId",jsonObject1.getString("id"));
                                myEdit.putString("token",jsonObject.getString("token"));
                                myEdit.putString("Name",jsonObject1.getString("name"));
                                myEdit.putString("email",jsonObject1.getString("email"));
                                myEdit.putString("Mobile",jsonObject1.getString("mobile"));
                                myEdit.apply();



                                Intent i = new Intent(AdminLoginActivity.this, MainActivity2.class);
                                startActivity(i);
                                finish();

                            }else{
                                Toast.makeText(AdminLoginActivity.this, "Please check your credentials!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      //  Toast.makeText(AdminLoginActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                }


        );
        int initialTimeoutMs = 0;
        request.setRetryPolicy(new DefaultRetryPolicy(200 * 30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(request);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AdminLoginActivity.this, WelcomeActivity.class);
        startActivity(intent);
    }
}