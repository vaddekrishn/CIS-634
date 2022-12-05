package com.digitalInnovation.internshipmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;
import com.digitalInnovation.internshipmanagementsystem.Admin.AdminResetpassActivity;
import com.digitalInnovation.internshipmanagementsystem.Admin.AdminVerificationActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class UserVerificationActivity extends AppCompatActivity implements View.OnClickListener {
     PinView firstPinView;
     ImageButton backArrow,submitOtp;
     ImageView ivTimer;
     TextView mobNumber, resend, tvTimer;
    int minutes, seconds;
    String sDuration, idToken,userEmail,UserID;
    CountDownTimer countDownTimer;
    long duration = 120000;
     boolean timeRunning;
     String mCode,Otp,userId;
    int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_verification);
    //    getSupportActionBar().hide();

        SharedPreferences sp_user = getSharedPreferences("user_login", MODE_PRIVATE);
        UserID = sp_user.getString("userId", "");
        Intent intent = getIntent();
        userEmail = intent.getStringExtra("UserEmail");
        Log.e( "onCreate: ", userEmail+UserID);

        firstPinView = findViewById(R.id.firstPinView);
        backArrow = findViewById(R.id.backArrow);
        resend = findViewById(R.id.resend);
        ivTimer = findViewById(R.id.ivTimer);
        submitOtp = findViewById(R.id.submitOtp);
        tvTimer=findViewById(R.id.tvTimer);

        sendVerifyEmail();
        startTimer();
        updateTimer();

        submitOtp.setOnClickListener(this);
        resend.setOnClickListener(this);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }






    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id) {
            case R.id.submitOtp:
                mCode = Objects.requireNonNull(firstPinView.getText()).toString();
               verifyOtp();
                break;

            case R.id.resend:
                resendVerifyEmail();

                startTimer();
                resend.setVisibility(View.GONE);
                tvTimer.setVisibility(View.VISIBLE);
                ivTimer.setVisibility(View.VISIBLE);
                updateTimer();

                break;
    }
}



    private void startTimer() {

        countDownTimer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                duration = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                tvTimer.setVisibility(View.GONE);
                ivTimer.setVisibility(View.GONE);
                resend.setVisibility(View.VISIBLE);
            }
        }.start();

        timeRunning = true;
    }


    private void updateTimer() {
        minutes = (int) duration / 60000;
        seconds = (int) duration % 60000 / 1000;
        sDuration = "" + minutes;
        sDuration += " : ";
        if (seconds < 10) {
            sDuration += 0;
        }
        sDuration += seconds;
        tvTimer.setText(sDuration);
    }

    private void sendVerifyEmail() {
        String URL="http://ims.ditests.com/api/forgetPassword";
//        RequestQueue requestQueue =Volley.newRequestQueue(getApplicationContext());
//
//        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(UserVerificationActivity.this, response, Toast.LENGTH_SHORT).show();
//                Log.e( "onResponset: ", response.toString());
//
//
//
//                try {
//
//                    JSONObject jsonObject = new JSONObject(response);
//
//                    Log.e( "onResponseE: ",jsonObject.toString());
//                    String login_sts = jsonObject.getString("success");
//
//
//                    if (login_sts.equals("true")){
//                        Toast.makeText(UserVerificationActivity.this, "OTP send on your email address", Toast.LENGTH_SHORT).show();
//
//
//
//                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
//                        Log.e( "onResponseS: ",jsonObject1.toString() );
//
//                        userId = jsonObject1.getString("id");
//                        Otp = jsonObject1.getString("otp");
//
//                        Log.e( "onResponseT: ",userId+Otp );
//
//
//
//
//
//                    }else{
//                        Toast.makeText(UserVerificationActivity.this, "User is not available, Please Complete your Registration", Toast.LENGTH_SHORT).show();
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(UserVerificationActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("email", userEmail);
//
//                Log.e( "getParams: ",userEmail);
//                return params;
//
//            }
//
//        };
//
//        requestQueue.add(stringRequest);

        JSONObject jobj = new JSONObject();

        try {

            jobj.put("email",userEmail);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e( "InsertEmail: ", jobj.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                URL,
                jobj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    //    Toast.makeText(UserVerificationActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                        try {

                            String login_sts = response.getString("success");

                            if (login_sts.equals("true")) {

                                Toast.makeText(UserVerificationActivity.this, "OTP send on your email address.", Toast.LENGTH_SHORT).show();
                                JSONObject jsonObject = response.getJSONObject("data");
                                userId = jsonObject.getString("id");
                                Otp = jsonObject.getString("otp");

                                Log.e( "onResponseFP: ",userId+Otp );

                            } else {

                                Toast.makeText(UserVerificationActivity.this, "User is not available, Please Complete your Registration", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    //    Toast.makeText(UserVerificationActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        int initialTimeoutMs = 0;
        request.setRetryPolicy(new DefaultRetryPolicy(200 * 30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(request);
    }

    private void verifyOtp() {

        String URL="http://ims.ditests.com/api/Verify_otp";
        JSONObject jobj = new JSONObject();

        try {

            jobj.put("user_id",userId);
            jobj.put("otp",Otp);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e( "verifyOtp: ", jobj.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                URL,
                jobj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    //    Toast.makeText(UserVerificationActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                        try {

                            String login_sts = response.getString("success");

                            if (login_sts.equals("true")) {

                                Toast.makeText(UserVerificationActivity.this, "OTP verify successfully !!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UserVerificationActivity.this, ResetPasswordActivity.class);
                                intent.putExtra("userID",userId);
                                startActivity(intent);

                            } else {

                                Toast.makeText(UserVerificationActivity.this, "OTP is incorrect !!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserVerificationActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });
        int initialTimeoutMs = 0;
        request.setRetryPolicy(new DefaultRetryPolicy(200 * 30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(request);

    }

    private void resendVerifyEmail() {

        String URL="http://ims.ditests.com/api/Verify_otp";
        JSONObject jobj = new JSONObject();

        try {

            jobj.put(" user_id",userId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e( "verifyOtp: ", jobj.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                URL,
                jobj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    //    Toast.makeText(UserVerificationActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                        try {

                            String login_sts = response.getString("success");

                            if (login_sts.equals("true")) {

                                Toast.makeText(UserVerificationActivity.this, "OTP resend on your email address", Toast.LENGTH_SHORT).show();

                            } else {

                                Toast.makeText(UserVerificationActivity.this, "User not registered!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    //    Toast.makeText(UserVerificationActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });
        int initialTimeoutMs = 0;
        request.setRetryPolicy(new DefaultRetryPolicy(200 * 30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(request);
    }
    }