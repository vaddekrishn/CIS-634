package com.digitalInnovation.internshipmanagementsystem.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.digitalInnovation.internshipmanagementsystem.MainActivity;
import com.digitalInnovation.internshipmanagementsystem.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateInternship extends AppCompatActivity {

    ImageButton backbutton,Add;
    TextInputEditText TitleofInternship,companyName,comp_address,comp_Desc;
    Spinner jobType,campusType,branch;
    String branchID;
    Spinner jobTypeSpinner,campusTypespinner,branchSpinner;
    ArrayList<String> jobtypeList = new ArrayList<>();
    ArrayList<String> jobTypeIdList = new ArrayList<>();
    ArrayList<String> campustypePogoList = new ArrayList<>();
    ArrayList<String> campusIdList = new ArrayList<>();
    ArrayList<String> branchPogoList = new ArrayList<>();
    ArrayList<String> branchIdList = new ArrayList<>();
    ArrayAdapter<String> jobtypeAdapter;
    ArrayAdapter<String> campustypeAdapter;
    ArrayAdapter<String> branchtwoAdapter;
    String typeofjob,typeofcampus,branches,branchName,branch_ID;
    String jobtypeIds,campustypeIds,branchIds;
    String Title,CompanyName,CompanyAddress,CompanyDescription,JobType,CampusType,BranchCategory;
    SharedPreferences sp_user;
    String id,name,email,Token,InternshipID;
    String typeofJob,typeofCampus,categoryofBranch,updatetitle,updatecompName,updatejobType,updatecampusType,updatebranch,updateAdd,updateDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_internship);


        sp_user = getSharedPreferences("admin_login", MODE_PRIVATE);
        id = sp_user.getString("userId", "");
        name = sp_user.getString("Name", "");
        email = sp_user.getString("email", "");
        Token = sp_user.getString("token", "");
        Log.e( "onCreateShared: ",Token );


        Intent i = getIntent();
        updatetitle = i.getStringExtra("title");
        updatecompName = i.getStringExtra("companyname");
        updatejobType = i.getStringExtra("typeofjob");
        updatecampusType = i.getStringExtra("typeofcampus");
        updatebranch = i.getStringExtra("banches");
        updateAdd = i.getStringExtra("Address");
        updateDesc = i.getStringExtra("desc");
        InternshipID = i.getStringExtra("internshipId");

        Log.e( "onCreate: ",updatetitle+updatebranch+updatejobType );



        backbutton =findViewById(R.id.backbutton);
        Add =findViewById(R.id.Add);
        TitleofInternship =findViewById(R.id.TitleofInternship);
        companyName =findViewById(R.id.companyName);
        comp_address =findViewById(R.id.comp_address);
        comp_Desc =findViewById(R.id.comp_Desc);


        jobTypeSpinner = (Spinner) findViewById(R.id.jobTypeSpinner);
        campusTypespinner = (Spinner) findViewById(R.id.campusTypespinner);
        branchSpinner = (Spinner) findViewById(R.id.branchSpinner);

        TitleofInternship.setText(updatetitle);
        companyName.setText(updatecompName);
        comp_address.setText(updateAdd);
        comp_Desc.setText(updateDesc);



        jobTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jobtypeIds = jobTypeIdList.get(i);
                Toast.makeText(UpdateInternship.this, jobtypeIds, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        campusTypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                campustypeIds = campusIdList.get(i);
            //    Toast.makeText(UpdateInternship.this, campustypeIds, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        branchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                branchIds = branchIdList.get(i);
            //    Toast.makeText(UpdateInternship.this, branchIds, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getJobTypedata();
        getCampusTypedata();
        getBranchData();

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateInternship.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Title = TitleofInternship.getText().toString();
                CompanyName =companyName.getText().toString();
                CompanyAddress = comp_address.getText().toString();
                CompanyDescription = comp_Desc.getText().toString();

                updateInternshipData();

            }
        });




    }

    private void updateInternshipData() {
        String URL ="http://ims.ditests.com/api/admin/update_internship";

        JSONObject jobj = new JSONObject();
        try {
            jobj.put("title",Title);
            jobj.put("company_name",CompanyName);
            jobj.put("job_type",jobtypeIds);
            jobj.put("campus_type",campustypeIds);
            jobj.put("branch",branchIds);
            jobj.put("address",CompanyAddress);
            jobj.put("description",CompanyDescription);
            jobj.put("internship_id",InternshipID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e( "insertApplication: ",jobj.toString() );
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                URL,
                jobj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("onResponseThree: ",response.toString() );
                        Toast.makeText(UpdateInternship.this, "data Updated successfully !", Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(UpdateInternship.this, MainActivity2.class);
                        startActivity(intent);

                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    //    Toast.makeText(UpdateInternship.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
        {



                //This is for Headers If You Needed
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization:","Bearer "+Token);
                Log.e( "getHeaders: ",Token );
                return params;
            }




        };
        int initialTimeoutMs = 0;
        request.setRetryPolicy(new DefaultRetryPolicy(200 * 30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(UpdateInternship.this).add(request);
    }


    private void getBranchData() {
        String URL = "http://Ims.ditests.com/api/admin/get_all_branch";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e( "onResponseIntern: ",response.toString() );
                        try {
                            String login_sts = response.getString("success");
                            // Log.e( "onResponseCheck: ",login_sts );


                            if (login_sts.equals("true")){
                           //     Toast.makeText(UpdateInternship.this, "get Branch data", Toast.LENGTH_SHORT).show();



                                JSONObject jsonObject = response.getJSONObject("data");

                                Log.e( "onResponsebranch: ",jsonObject.toString() );

                                JSONArray array = jsonObject.getJSONArray("Branch_Data");


                                Log.e( "onResponseB: ", String.valueOf(array.toString()));

                                for (int p=0;p<array.length();p++){
                                    JSONObject object = array.getJSONObject(p);

                                    branchID = object.optString("id");
                                    branches = object.optString("name");
                                    Log.e("onResponseBranch: ",branches +branchID);


                                    branchPogoList.add(branches);
                                    branchIdList.add((branchID));

                                    branchtwoAdapter = new ArrayAdapter<>(UpdateInternship.this, android.R.layout.simple_spinner_item,
                                            branchPogoList);
                                    branchSpinner.setAdapter(branchtwoAdapter);


                                }


                            }else{
                                Toast.makeText(UpdateInternship.this, "Data not retrived", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                        // internshipDataAdapter.notifyDataSetChanged();
                        //  prb.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    //    Toast.makeText(UpdateInternship.this, "Something Went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        request.setRetryPolicy(new DefaultRetryPolicy(200*30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(UpdateInternship.this).add(request);
    }

    private void getCampusTypedata() {
        String Url = "http://Ims.ditests.com/api/admin/get_campus_type";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                Url,
                null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e( "onResponseIntern: ",response.toString() );
                        try {
                            String login_sts = response.getString("success");
                            // Log.e( "onResponseCheck: ",login_sts );


                            if (login_sts.equals("true")){
                                Toast.makeText(UpdateInternship.this, "get Campus data", Toast.LENGTH_SHORT).show();



                                JSONObject jsonObject = response.getJSONObject("data");

                                Log.e( "onResponseCampus: ",jsonObject.toString() );

                                JSONArray array = jsonObject.getJSONArray("campus_type");


                                Log.e( "onResponseC: ", String.valueOf(array.toString()));

                                for (int m=0;m<array.length();m++){
                                    JSONObject object2 = array.getJSONObject(m);

                                    String campus = object2.optString("campus_type");

                                    campustypePogoList.add(campus);
                                    campusIdList.add(object2.getString("id"));

                                    campustypeAdapter = new ArrayAdapter<>(UpdateInternship.this, android.R.layout.simple_spinner_item,
                                            campustypePogoList);
                                    campusTypespinner.setAdapter(campustypeAdapter);


                                }

                            }else{
                                Toast.makeText(UpdateInternship.this, "Data not retrived", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                        // internshipDataAdapter.notifyDataSetChanged();
                        //  prb.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateInternship.this, "Something Went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        request.setRetryPolicy(new DefaultRetryPolicy(200*30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(UpdateInternship.this).add(request);
    }

    private void getJobTypedata() {
        String url="http://Ims.ditests.com/api/admin/get_job_type";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e( "onResponseIntern: ",response.toString() );
                        try {
                            String login_sts = response.getString("success");
                            // Log.e( "onResponseCheck: ",login_sts );


                            if (login_sts.equals("true")){
                                Toast.makeText(UpdateInternship.this, "get JobType data", Toast.LENGTH_SHORT).show();



                                JSONObject jsonObject = response.getJSONObject("data");

                                Log.e( "onResponseJobtype: ",jsonObject.toString() );

                                JSONArray array = jsonObject.getJSONArray("job_type");


                                Log.e( "onResponseJ: ", String.valueOf(array.length()));

                                for (int k=0;k<array.length();k++){
                                    JSONObject object3 = array.getJSONObject(k);
                                    String jobType = object3.optString("job_type");
                                    Log.e("onResponseCheck: ", jobType);
                                    jobtypeList.add(jobType);
                                    jobTypeIdList.add(object3.optString("id"));


                                    jobtypeAdapter = new ArrayAdapter<>(UpdateInternship.this, android.R.layout.simple_spinner_item,
                                            jobtypeList);
                                    jobTypeSpinner.setAdapter(jobtypeAdapter);

                                   /* SpinnerAdapter adapter = new SpinnerAdapter(AddInternshipActivity.this, android.R.layout.simple_spinner_dropdown_item, jobtypePogoList);
                                    jobTypeSpinner.setAdapter(adapter);*/


                                }






                            }else{
                                Toast.makeText(UpdateInternship.this, "Data not retrived", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                        //internshipDataAdapter.notifyDataSetChanged();
                        //  prb.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateInternship.this, "Something Went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        request.setRetryPolicy(new DefaultRetryPolicy(200*30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(UpdateInternship.this).add(request);
    }

}