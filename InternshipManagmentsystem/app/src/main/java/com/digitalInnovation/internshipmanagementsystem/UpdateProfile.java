package com.digitalInnovation.internshipmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.futuremind.recyclerviewfastscroll.Utils;
import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginException;

public class UpdateProfile extends AppCompatActivity {

    private EditText updateFirstName,updateLastName,updateUserMobile,updateUserEmail,updateUserPass;
    ImageButton UpdateBtn,backbutton;
    SharedPreferences sp_user;
    String id;
    public String firstName,lastName,email,mobileNo;
    String firstname,lastname,mobileno,FirstName,LastName,EmaiID,MobileNumber,Password,CountryCode="+91",Token;
    CountryCodePicker countryCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        sp_user = getSharedPreferences("user_login", MODE_PRIVATE);
        id = sp_user.getString("userId", "");
        firstname = sp_user.getString("firstName", "");
        lastname = sp_user.getString("lastName", "");
        email = sp_user.getString("email", "");
        mobileno = sp_user.getString("mobile", "");
        Token = sp_user.getString("token", "");
        Log.e( "onCreateShared: ",Token );


        updateFirstName =findViewById(R.id.updateFirstName);
        updateLastName =findViewById(R.id.updateLastName);
        updateUserMobile =findViewById(R.id.updateUserMobile);
        updateUserEmail =findViewById(R.id.updateUserEmail);
        UpdateBtn =findViewById(R.id.UpdateBtn);
        countryCode = findViewById(R.id.countryCode);
        backbutton = findViewById(R.id.backbutton);


        updateFirstName.setText(firstname);
        updateLastName.setText(lastname);
        updateUserMobile.setText(mobileno);
        updateUserEmail.setText(email);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        countryCode.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                CountryCode = countryCode.getSelectedCountryCodeWithPlus();

                Log.e("Country Code", CountryCode);
            }
        });

        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirstName=updateFirstName.getText().toString();
                LastName=updateLastName.getText().toString();
                MobileNumber=updateUserMobile.getText().toString();
                EmaiID=updateUserEmail.getText().toString();
//                Password=updateUserPass.getText().toString();
                updateUser();
                //callPUTDataMethod(FirstName,MiddleName,LastName,MobileNumber,EmaiID);
            }
        });
    }

    private void updateUser() {
        String URL = "http://Ims.ditests.com/api/edit_profile";


        JSONObject jobj = new JSONObject();
        try {
            jobj.put("user_id", id);
            jobj.put("first_name", FirstName);
            jobj.put("last_name", LastName);
            jobj.put("email", EmaiID);
            jobj.put("country_code", CountryCode);
            jobj.put("mobile", MobileNumber);



        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("UpdateUser: ", jobj.toString());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                URL,
                jobj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("onResponseUpdateUser: ", response.toString());

                        try {

                            String login_sts = response.getString("success");

                            if (login_sts.equals("true")) {
                                Toast.makeText(UpdateProfile.this, "Profile data updated successfully !", Toast.LENGTH_SHORT).show();

                                JSONObject jsonObject = response.getJSONObject("data");

                                Log.e("onResponseLogin: ", jsonObject.toString());

                                JSONArray array = jsonObject.getJSONArray("user_data");


                                Log.e("onResponseInternship: ", array.toString());

                                for (int m = 0; m < array.length(); m++) {

                                    JSONObject object = array.getJSONObject(m);

                                    SharedPreferences sp_user = getSharedPreferences("user_login", MODE_PRIVATE);
                                    SharedPreferences.Editor myEdit = sp_user.edit();

                                    myEdit.putString("userId", object.getString("id"));
                                    myEdit.putString("firstName", object.getString("first_name"));
                                    myEdit.putString("lastName", object.getString("last_name"));
                                    myEdit.putString("email", object.getString("email"));
                                    myEdit.putString("mobile", object.getString("mobile"));
                                    myEdit.commit();

                                    
                                    firstName = object.getString("FirstName");
                                    lastName = object.getString("LastName");
                                    email = object.getString("EmaiID");
                                    mobileNo = object.getString("MobileNumber");


                                    updateFirstName.setText(firstName);
                                    updateLastName.setText(lastName);
                                    updateUserMobile.setText(mobileNo);
                                    updateUserEmail.setText(email);



                                    Intent intent = new Intent(UpdateProfile.this, MainActivity.class);
                                    startActivity(intent);


                                }

                            } else {

                                Toast.makeText(UpdateProfile.this, "Invalid User", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    //    Toast.makeText(UpdateProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

        //This is for Headers If You Needed
        @Override
        public Map<String, String> getHeaders () throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            params.put("Content-Type", "application/json; charset=UTF-8");
            params.put("Authorization:", "Bearer " + Token);
            Log.e("getHeaders: ", Token);
            return params;
        }
    };
        int initialTimeoutMs = 0;
        request.setRetryPolicy(new DefaultRetryPolicy(200 * 30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(UpdateProfile.this).add(request);

    }
}