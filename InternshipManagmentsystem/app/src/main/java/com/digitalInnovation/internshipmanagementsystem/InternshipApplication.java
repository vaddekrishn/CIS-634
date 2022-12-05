package com.digitalInnovation.internshipmanagementsystem;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.documentfile.provider.DocumentFile;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.hbb20.CountryCodePicker;

import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.security.auth.login.LoginException;

public class InternshipApplication extends AppCompatActivity {
    ImageButton backbutton,submit;
    TextInputEditText user_Name,user_email,user_Address;
    EditText user_mobile;
    TextView uploadedfile;
    int progressStatus;
    ProgressDialog pd;
    ProgressBar progressBar;
    Button selecteFile;
    ActivityResultLauncher<Intent> resultLauncher;
    String InternshipID,UserName,MobileNo,EmailID,Address,fileName,UserID,CountryCode="+91";
    SharedPreferences sp_user;
    CountryCodePicker countryCode;
  // String URL ="http://ims.ditests.com/api/apply";
    String URL = "http://ims.ditests.com/public/applyinternship.php";
    Uri uri;
    String displayName,Token,token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internship_application);

        sp_user = getSharedPreferences("user_login", MODE_PRIVATE);
        UserID = sp_user.getString("userId", "");
        Token = sp_user.getString("token", "");

        Intent intent = getIntent();
        InternshipID = intent.getStringExtra("internshipId");
        Log.e("onCreate: ",InternshipID +UserID + Token );


        backbutton = findViewById(R.id.backbutton);
        submit = findViewById(R.id.submit);
        user_Name = findViewById(R.id.user_Name);
        user_mobile = findViewById(R.id.user_mobile);
        user_email = findViewById(R.id.user_email);
        user_Address = findViewById(R.id.user_Address);
        selecteFile = findViewById(R.id.selectFile);
        uploadedfile = findViewById(R.id.uploadedfile);
        countryCode = findViewById(R.id.cc);

        countryCode.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                CountryCode = countryCode.getSelectedCountryCodeWithPlus();

                Log.e("Country Code", CountryCode);
            }
        });


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InternshipApplication.this,MainActivity.class);
                startActivity(intent);
            }
        });
        selecteFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent, 1); //request code 1:for server database ,45: firebase database
            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserName = Objects.requireNonNull(user_Name.getText()).toString();
                MobileNo = user_mobile.getText().toString();
                EmailID = Objects.requireNonNull(user_email.getText()).toString();
                Address = Objects.requireNonNull(user_Address.getText()).toString();

                if (UserName.isEmpty()) {
                    user_Name.setError("Firstname is required!");
                    user_Name.requestFocus();
                } else if (user_mobile.getText().toString().length() != 10) {
                    user_mobile.setError("The Mobile Number has to be 10 digit");
                    user_mobile.requestFocus();
                }else if (Address.isEmpty()) {
                    user_Address.setError("Address is required!");
                    user_Address.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(user_email.getText().toString()).matches()) {
                    //  regEmail.setError("Please Enter Valid Email ID");
                    //   regEmail.requestFocus();
                    Toast.makeText(InternshipApplication.this, "Enter Valid Email ID", Toast.LENGTH_SHORT).show();
                } else {


                    UploadPdf(displayName, uri);
                 //   insertInternshipApplication();

                }


            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int
            resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            uri = Objects.requireNonNull(data).getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            displayName =
                    String.valueOf(Calendar.getInstance().getTimeInMillis() + ".pdf");
            Log.e("onActivityResult: ", displayName);
            uploadedfile.setText(displayName);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void UploadPdf(String displayName, Uri uri) {
        InputStream iStream = null;

        try {
            iStream = getContentResolver().openInputStream(uri);
            final byte[] inputData = getByte(iStream);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(
                    Request.Method.POST,
                    URL,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            Toast.makeText(InternshipApplication.this, "Data Uploaded", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(InternshipApplication.this,MainActivity.class);
                            startActivity(intent);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("onErrorResponse: ", error.toString());
                            Log.e("response Errorhome", error + "");
                            if (error instanceof NoConnectionError) {
                                Log.d("NoConnectionError", "NoConnectionError.......");
                            } else if (error instanceof AuthFailureError) {
                                Log.d("AuthFailureError", "AuthFailureError.......");
                            } else if (error instanceof ServerError) {
                                Log.d("ServerError>>>>>>>>>", "ServerError.......");
                            } else if (error instanceof NetworkError) {
                                Log.d("NetworkError>>>>>>>>>", "NetworkError.......");
                            } else if (error instanceof ParseError) {
                                Log.d("ParseError>>>>>>>>>", "ParseError.......");
                            }else if (error instanceof TimeoutError) {
                                Log.d("TimeoutError>>>>>>>>>", "TimeoutError.......");
                            }

                        }
                    }

            ) {


//                //This is for Headers If You Needed
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("Content-Type", "application/json; charset=UTF-8");
//                    params.put("Authorization:","Bearer "+Token);
//                    Log.e( "getHeaders: ",Token );
//                    return params;
//                }



                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("student_id", UserID);  //add string parameters
                    params.put("name", UserName);  //add string parameters
                    params.put("email", EmailID);  //add string parameters
                    params.put("country_code", CountryCode);  //add string parameters
                    params.put("mobile", MobileNo);  //add string parameters
                    params.put("address", Address);  //add string parameters
                    params.put("internship_id", InternshipID);  //add string parameters
                    Log.e( "getParams: ",UserID+UserName+EmailID+CountryCode+MobileNo+Address+InternshipID );
                    return params;
                }

                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();

                    params.put("resume", new DataPart(displayName, inputData));


                    Log.e("getByteData: ", params.toString());
                    return params;
                }
            };
            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(500000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(this).add(volleyMultipartRequest);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private byte[] getByte(InputStream iStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = iStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}