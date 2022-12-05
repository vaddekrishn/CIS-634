package com.digitalInnovation.internshipmanagementsystem.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.digitalInnovation.internshipmanagementsystem.R;
import com.digitalInnovation.internshipmanagementsystem.UpdateProfile;
import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdminUpdateprofile extends AppCompatActivity {
    private EditText updateFirstName,updateUserMobile,updateUserEmail,updateUserPass;
    ImageButton UpdateBtn,backbutton;
    SharedPreferences sp_user;
    String id,mobile;
    public String firstName,lastName,email,mobileNo;
    String name,lastname,mobileno,Name,LastName,EmaiID,MobileNumber,Password,CountryCode="+91",Token,adminName,adminEmail,adminMobileNo;
    CountryCodePicker countryCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_updateprofile);
        sp_user = getSharedPreferences("admin_login", MODE_PRIVATE);
        id = sp_user.getString("userId", "");
        name = sp_user.getString("Name", "");
        email = sp_user.getString("email", "");
        mobile = sp_user.getString("Mobile","");
        Token = sp_user.getString("token", "");
        Log.e( "onCreateShared: ",name + Token );


        updateFirstName =findViewById(R.id.updateFirstName);
        updateUserMobile =findViewById(R.id.updateUserMobile);
        updateUserEmail =findViewById(R.id.updateUserEmail);
        UpdateBtn =findViewById(R.id.UpdateBtn);
        countryCode = findViewById(R.id.countryCode);
        backbutton = findViewById(R.id.backbutton);


        updateFirstName.setText(name);
        updateUserEmail.setText(email);
        updateUserMobile.setText(mobile);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminUpdateprofile.this, MainActivity2.class);
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

        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name=updateFirstName.getText().toString();
                MobileNumber=updateUserMobile.getText().toString();
                EmaiID=updateUserEmail.getText().toString();

                updateUser();
                //callPUTDataMethod(FirstName,MiddleName,LastName,MobileNumber,EmaiID);
            }
        });
    }
    private void updateUser() {
        String URL="http://Ims.ditests.com/api/admin/edit_profile";


        JSONObject jobj = new JSONObject();
        try {
            jobj.put("admin_id", id);
            jobj.put("name",Name);
            jobj.put("email",EmaiID);
            jobj.put("country_code",CountryCode);
            jobj.put("mobile",MobileNumber);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e( "UpdateAdminUser: ",jobj.toString() );

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                URL,
                jobj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("onResponseUpdateUser: ",response.toString() );
                        Toast.makeText(AdminUpdateprofile.this, "Profile data updated successfully !", Toast.LENGTH_SHORT).show();
                        try {

                            String login_sts = response.getString("success");

                            if (login_sts.equals("true")) {


                                JSONObject jsonObject = response.getJSONObject("data");

                                Log.e( "onResponseLogin: ",jsonObject.toString() );

                                JSONArray array = jsonObject.getJSONArray("user_data");


                                Log.e( "onResponseInternship: ", String.valueOf(array.length()));

                                for (int i = 0; i < array.length(); i++) {

                                    // JSONObject object = array.getJSONObject(i);
                                    JSONObject object = array.getJSONObject(i);
                                    adminName = object.getString("name");
                                    adminEmail = object.getString("email");
                                    adminMobileNo = object.getString("mobile");


                                    updateFirstName.setText(adminName);
                                    updateUserMobile.setText(adminMobileNo);
                                    updateUserEmail.setText(adminEmail);

                                    SharedPreferences sp_user = getSharedPreferences("admin_login", MODE_PRIVATE);
                                    SharedPreferences.Editor myEdit = sp_user.edit();

                                    myEdit.putString("userId",object.getString("id"));
                                    myEdit.putString("Name",object.getString("name"));
                                    myEdit.putString("email",object.getString("email"));
                                    myEdit.putString("Mobile",object.getString("mobile"));
                                    myEdit.commit();

                                    Intent intent = new Intent(AdminUpdateprofile.this,MainActivity2.class);
                                    startActivity(intent);


                                }

                            } else {

                                Toast.makeText(AdminUpdateprofile.this, "Invalid User", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AdminUpdateprofile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization:", "Bearer " + Token);
                Log.e("getHeaders: ", Token);
                return params;
            }
        };
        int initialTimeoutMs = 0;
        request.setRetryPolicy(new DefaultRetryPolicy(200 * 30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(AdminUpdateprofile.this).add(request);

    }
}