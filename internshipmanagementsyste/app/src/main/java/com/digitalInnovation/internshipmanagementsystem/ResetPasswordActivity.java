package com.digitalInnovation.internshipmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class ResetPasswordActivity extends AppCompatActivity {
    TextInputEditText newPass,newConfirmPass;
    ImageButton save,backArrow;
    String new_pass,new_confirmpass,UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
       // getSupportActionBar().hide();

        SharedPreferences sp_user = getSharedPreferences("user_login", MODE_PRIVATE);
        UserID = sp_user.getString("UserID", "");

        newPass = findViewById(R.id.newPass);
        newConfirmPass = findViewById(R.id.newConfirmPass);
        save = findViewById(R.id.save);
        backArrow = findViewById(R.id.backArrow);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_pass = newPass.getText().toString();
                new_confirmpass = newConfirmPass.getText().toString();

                if (new_pass.isEmpty()) {
                    newPass.setError("Required!");
                    newPass.requestFocus();

                } else if (!new_pass.equals(new_confirmpass)) {
                    newConfirmPass.setError("Please check your password!");
                    newConfirmPass.requestFocus();
                }else {
                    InsertUser();
                }
            }
        });
    }

    private void InsertUser() {
        String URL="http://ims.ditests.com/api/reset_Password";

        JSONObject jobj = new JSONObject();

        try {
            jobj.put("user_id",UserID);
            jobj.put("password",new_pass);
            jobj.put("confirm_password",new_confirmpass);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e( "ResetUserData: ", jobj.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                URL,
                jobj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(ResetPasswordActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                        try {

                            String login_sts = response.getString("success");

                            if (login_sts.equals("true")) {

                                Toast.makeText(ResetPasswordActivity.this, "Password Change successfully !", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(ResetPasswordActivity.this,LoginActivity.class);
                                startActivity(intent);

//                                 JSONObject jsonObject = new JSONObject("data");
//                                 JSONObject jobj = jsonObject.getJSONObject("user");
//
//                                 firstName = jobj.getString("first_name");
//
//                                Log.e( "onResponse: ",firstName );


                            } else {

                                Toast.makeText(ResetPasswordActivity.this, "User not registered!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ResetPasswordActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });
        int initialTimeoutMs = 0;
        request.setRetryPolicy(new DefaultRetryPolicy(200 * 30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(request);
    }
}