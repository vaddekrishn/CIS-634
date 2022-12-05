package com.digitalInnovation.internshipmanagementsystem;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.digitalInnovation.internshipmanagementsystem.Adapter.ApplicationAdapter;
import com.digitalInnovation.internshipmanagementsystem.Adapter.InternshipDataAdapter;
import com.digitalInnovation.internshipmanagementsystem.PogoData.ApplicationPogo;
import com.digitalInnovation.internshipmanagementsystem.PogoData.InternshipPogo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AppliedFragment extends Fragment {
    RecyclerView recApplication;
    ImageButton backbutton;
    List<ApplicationPogo> applicationPogoList;
    ApplicationAdapter applicationAdapter;
    String URL="http://Ims.ditests.com/api/apply_list";
    SharedPreferences sp_user;
    String user_id,Internship_Id,id,firstname,lastname,email,CountryCode,mobileno,Token,companyName,
            companyAddress,Title,Description,jobtype,campusType,branchname;
    int applicationStatus;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_applied, container, false);

        sp_user = getActivity().getSharedPreferences("user_login", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sp_user.edit();
        id = sp_user.getString("userId", "");
        firstname = sp_user.getString("firstName", "");
        lastname = sp_user.getString("lastName", "");
        email = sp_user.getString("email", "");
        CountryCode = sp_user.getString("country_code", "");
        mobileno = sp_user.getString("mobile", "");
        Token = sp_user.getString("token", "");

        Log.e( "onCreateViewU: ",id );

        recApplication=view.findViewById(R.id.recApplication);
        backbutton=view.findViewById(R.id.backbutton);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        recApplication.setHasFixedSize(true);
        applicationPogoList = new ArrayList<>();
        applicationPogoList.clear();



        getApplicationData();

        return view;
    }

    private void getApplicationData() {
        JSONObject jobj = new JSONObject();
        try {
            jobj.put("student_id",id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e( "insertapplied: ",jobj.toString() );

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                URL,
                jobj,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONObject response) {
                     //   Log.e( "onResponseApplication: ",response.toString() );
                        try {
                            String login_sts = response.getString("success");
                         //    Log.e( "onResponseCheck: ",login_sts );


                            if (login_sts.equals("true")){
                            //   Toast.makeText(getContext(), "get Application data", Toast.LENGTH_SHORT).show();



                                JSONObject jsonObject = response.getJSONObject("data");

                                Log.e( "onResponseApply: ",jsonObject.toString() );

                                JSONArray array = jsonObject.getJSONArray("application");


                                Log.e( "onResponseInternship: ", String.valueOf(array.length()));

                                if (array.length() == 0){

                                    Intent intent = new Intent(getContext(),EmptyDataActivity.class);
                                    startActivity(intent);
                                }else{

                                    for (int q=0;q<array.length();q++){

                                        JSONObject object = array.getJSONObject(q);
                                        Log.e("onResponseTest: ", object.getString("id"));
                                        applicationStatus = Integer.parseInt(object.getString("status"));
                                        Log.e( "onResponseAS: ", String.valueOf(applicationStatus));



                                        JSONArray internArray = object.getJSONArray("internship");

                                        Log.e( "onResponseInternArr: ",internArray.toString() );
                                        JSONObject jsonObjectint = internArray.getJSONObject(0);

                                        Title = jsonObjectint.getString("title");
                                        companyName = jsonObjectint.getString("company_name");
                                        companyAddress = jsonObjectint.getString("address");
                                        Description = jsonObjectint.getString("description");
                                        Log.e( "onResponseUC: ",Title+companyName + companyAddress+Description );


                                        JSONArray arrayJob = jsonObjectint.getJSONArray("job_type");
                                        JSONObject jobObject = arrayJob.getJSONObject(0);
                                        jobtype = jobObject.getString("job_type");
                                        Log.e( "onResponse: ",jobtype );

                                        JSONArray arrayCampus = jsonObjectint.getJSONArray("campus_type");
                                        JSONObject campusObject = arrayCampus.getJSONObject(0);
                                        campusType = campusObject.getString("campus_type");
                                        Log.e( "onResponse: ", campusType);


                                        JSONArray arrayBranch = jsonObjectint.getJSONArray("branch");
                                        JSONObject branchObject = arrayBranch.getJSONObject(0);
                                        branchname = branchObject.getString("name");
                                        Log.e( "onResponse: ",branchname);



                                        ApplicationPogo pogo = new ApplicationPogo();
                                        pogo.setTitle(Title);
                                        pogo.setCompany_name(companyName);
                                        pogo.setAddress(companyAddress);
                                        pogo.setDescription(Description);
                                        pogo.setJob_type(jobtype);
                                        pogo.setCampus_type(campusType);
                                        pogo.setBranch(branchname);
                                        pogo.setStatus(applicationStatus);

                                        applicationPogoList.add(pogo);

                                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                        recApplication.setLayoutManager(linearLayoutManager);
                                        applicationAdapter = new ApplicationAdapter(applicationPogoList,getContext(),applicationStatus);
                                        recApplication.setAdapter(applicationAdapter);

                                }



                                }






                            }else{
                                Toast.makeText(getContext(), "Data not retrived", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                        // applicationAdapter.notifyDataSetChanged();
                        //  prb.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                   //    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){

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

        request.setRetryPolicy(new DefaultRetryPolicy(200*30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(request);
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