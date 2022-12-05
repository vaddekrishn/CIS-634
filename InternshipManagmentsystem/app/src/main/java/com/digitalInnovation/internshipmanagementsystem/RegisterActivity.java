package com.digitalInnovation.internshipmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText regFirstName,regLastName,regEmail,regPass,regConfirmPass;
    ImageButton backArrow,userSignUp;
    TextView openLogin;
    String FirstName,LastName,Email,Password,ConfirmPassword,MobileNumber,CountryCode="+91",firstName;
    String URL="http://ims.ditests.com/api/register";
    CountryCodePicker countryCode;
    EditText edtPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
       // getSupportActionBar().hide();

        regFirstName = findViewById(R.id.regFirstName);
        regLastName = findViewById(R.id.regLastName);
        regEmail = findViewById(R.id.regEmail);
        regPass = findViewById(R.id.regPass);
        regConfirmPass = findViewById(R.id.regConfirmPass);
        backArrow = findViewById(R.id.backArrow);
        userSignUp = findViewById(R.id.userSignUp);
        openLogin = findViewById(R.id.openLogin);
        countryCode = findViewById(R.id.countryCode);
        edtPhone = findViewById(R.id.edtPhone);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,OnBoardingActivity.class);
                startActivity(intent);
            }
        });

        countryCode.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
               CountryCode = countryCode.getSelectedCountryCodeWithPlus();

                Log.e("Country Code", CountryCode);
            }
        });

        openLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        userSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirstName = regFirstName.getText().toString().trim();
                LastName = regLastName.getText().toString().trim();
                MobileNumber = edtPhone.getText().toString().trim();
                Email = regEmail.getText().toString();
                Password = regPass.getText().toString();
                ConfirmPassword = regConfirmPass.getText().toString();

                if (FirstName.isEmpty()) {
                    regFirstName.setError("Required!");
                    regFirstName.requestFocus();
                } else if (LastName.isEmpty()) {
                    regLastName.setError("Required!");
                    regLastName.requestFocus();
                }else if (MobileNumber.isEmpty()) {
                    edtPhone.setError("Required!");
                    edtPhone.requestFocus();
                } else if (Email.isEmpty()) {
                    regEmail.setError("Required!");
                    regEmail.requestFocus();
                } else if (Password.isEmpty()) {
                    regPass.setError("Required!");
                    regPass.requestFocus();

                }else if (edtPhone.getText().toString().length() != 10) {
                    edtPhone.setError("The Mobile Number has to be 10 digit");
                    edtPhone.requestFocus();
                }  else if (!Password.equals(ConfirmPassword)) {
                    regConfirmPass.setError("Please check your password!");
                    regConfirmPass.requestFocus();
                }else {
                    InsertUser();
                }

            }
        });
    }

    private void InsertUser() {
        JSONObject jobj = new JSONObject();

        try {
            jobj.put("first_name",FirstName);
            jobj.put("last_name",LastName);
            jobj.put("email",Email);
            jobj.put("password",Password);
            jobj.put("confirm_password",ConfirmPassword);
            jobj.put("country_code",CountryCode);
            jobj.put("mobile",MobileNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e( "InsertUser: ", jobj.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                URL,
                jobj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                         Toast.makeText(RegisterActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                        try {

                            String login_sts = response.getString("success");

                            if (login_sts.equals("true")) {

                                Toast.makeText(RegisterActivity.this, "User register successfully.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);



                            } else {

                                Toast.makeText(RegisterActivity.this, "Please Complete Your Registration!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    //    Toast.makeText(RegisterActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });
        int initialTimeoutMs = 0;
        request.setRetryPolicy(new DefaultRetryPolicy(200 * 30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(request);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RegisterActivity.this,OnBoardingActivity.class);
        startActivity(intent);
    }
}