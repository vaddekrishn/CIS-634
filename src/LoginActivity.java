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

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class LoginActivity extends AppCompatActivity {
  TextInputEditText regUsername,regPass;
  ImageButton userSignIn;
  TextView forgetPass,openReg;
  String userName,password,firstName,lastName,userEmail,userMobile,userId;
    String URL="http://ims.ditests.com/api/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
      //  getSupportActionBar().hide();

        regUsername = findViewById(R.id.regUsername);
        regPass = findViewById(R.id.regPass);
        userSignIn = findViewById(R.id.userSignIn);
        forgetPass = findViewById(R.id.forgetPass);
        openReg = findViewById(R.id.openReg);

         forgetPass.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                 startActivity(intent);
             }
         });

         openReg.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
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

                     loginUser();
                 }

             }
         });

    }

    private void loginUser() {

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
                          Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                        try {
                           String login_sts = response.getString("success");
                            // Log.e( "onResponseCheck: ",login_sts );


                            if (login_sts.equals("true")){
                                Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();



                                JSONObject jsonObject = response.getJSONObject("data");

                                Log.e( "onResponseLogin: ",jsonObject.toString() );

                                JSONObject jsonObject1 = jsonObject.getJSONObject("user");

                                Log.e( "onResponseUser: ",jsonObject1.toString() );
                                userId = jsonObject1.getString("id");
                                firstName = jsonObject1.getString("first_name");
                                lastName = jsonObject1.getString("last_name");
                                userEmail = jsonObject1.getString("email");
                                userMobile = jsonObject1.getString("mobile");

                                Log.e( "onResponseU: ",userId+firstName+ lastName +userEmail + userMobile );

                                SharedPreferences sp_user = getSharedPreferences("user_login",MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sp_user.edit();

                                myEdit.putString("userId",jsonObject1.getString("id"));
                                myEdit.putString("firstName",jsonObject1.getString("first_name"));
                                myEdit.putString("lastName",jsonObject1.getString("last_name"));
                                myEdit.putString("email",jsonObject1.getString("email"));
                                myEdit.putString("mobile",jsonObject1.getString("mobile"));
                                myEdit.commit();


                                 Toast.makeText(LoginActivity.this, "successfully login!!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(i);
                                finish();

                            }else{
                                Toast.makeText(LoginActivity.this, "User is not available, Please Complete your Registration", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                }


        );
        int initialTimeoutMs = 0;
        request.setRetryPolicy(new DefaultRetryPolicy(200 * 30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(request);

    }
}
