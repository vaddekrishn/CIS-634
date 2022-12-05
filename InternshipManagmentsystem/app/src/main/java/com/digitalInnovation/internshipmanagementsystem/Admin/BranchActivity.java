package com.digitalInnovation.internshipmanagementsystem.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.digitalInnovation.internshipmanagementsystem.Adapter.AdminInternshipListAdapter;
import com.digitalInnovation.internshipmanagementsystem.Adapter.BranchAdapter;
import com.digitalInnovation.internshipmanagementsystem.LoginActivity;
import com.digitalInnovation.internshipmanagementsystem.PogoData.BranchPogo;
import com.digitalInnovation.internshipmanagementsystem.PogoData.InternshipPogo;
import com.digitalInnovation.internshipmanagementsystem.R;
import com.digitalInnovation.internshipmanagementsystem.RegisterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BranchActivity extends AppCompatActivity {
    ImageButton backbutton;
    EditText addbranch;
    Button Add;
    RecyclerView recBranch;
    String branchName;
    SharedPreferences sp_user;
    String id,email,name,Token;
    List<BranchPogo> branchPogoList;
    BranchAdapter branchAdapter;
    String URL="http://ims.ditests.com/api/admin/get_all_branch";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);

        sp_user = getSharedPreferences("admin_login", MODE_PRIVATE);
        id = sp_user.getString("userId", "");
        name = sp_user.getString("Name", "");
        email = sp_user.getString("email", "");
        Token = sp_user.getString("token", "");
        Log.e( "onCreateShared: ",Token );


        backbutton =findViewById(R.id.backbutton);
        addbranch =findViewById(R.id.addbranch);
        Add =findViewById(R.id.Add);
        recBranch =findViewById(R.id.recBranch);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BranchActivity.this, MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });

        recBranch.setHasFixedSize(true);
        branchPogoList = new ArrayList<>();
        branchPogoList.clear();





         Add.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 branchName = addbranch.getText().toString();

                 addBranchData();




             }
         });
        getBranchData();
    }

    private void getBranchData() {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("onResponseIntern: ", response.toString());
                        try {
                            String login_sts = response.getString("success");
                            // Log.e( "onResponseCheck: ",login_sts );


                            if (login_sts.equals("true")) {
                              //  Toast.makeText(BranchActivity.this, "get Internship data", Toast.LENGTH_SHORT).show();


                                JSONObject jsonObject = response.getJSONObject("data");

                                Log.e("onResponseLogin: ", jsonObject.toString());

                                JSONArray array = jsonObject.getJSONArray("Branch_Data");


                                Log.e("onResponseInternship: ", String.valueOf(array.length()));

                                for (int k = 0; k < array.length(); k++) {
                                    JSONObject object = array.getJSONObject(k);
                                    Log.e("onResponseTest: ", object.getString("id"));
                                    BranchPogo pogo = new BranchPogo();
                                    pogo.setId(Integer.valueOf(object.getString("id")));
                                    pogo.setName(object.getString("name"));




                                    branchPogoList.add(pogo);

                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BranchActivity.this);
                                    recBranch.setLayoutManager(linearLayoutManager);
                                    branchAdapter = new BranchAdapter(branchPogoList,BranchActivity.this);
                                    recBranch.setAdapter(branchAdapter);

                                }


                            } else {
                                Toast.makeText(BranchActivity.this, "Data not retrived", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                         branchAdapter.notifyDataSetChanged();
                        //  prb.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    //    Toast.makeText(BranchActivity.this, "Something Went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        request.setRetryPolicy(new DefaultRetryPolicy(200*30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(BranchActivity.this).add(request);
    }

    private void addBranchData() {
        String URL= "http://ims.ditests.com/api/admin/add_branch";

        JSONObject jobj = new JSONObject();

        try {
            jobj.put("name",branchName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e( "InsertBranch: ", jobj.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                URL,
                jobj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    //    Toast.makeText(BranchActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                        try {

                            String login_sts = response.getString("success");

                            if (login_sts.equals("true")) {

                                Toast.makeText(BranchActivity.this, "Branch Added Successfully.", Toast.LENGTH_SHORT).show();
                                restartActivity();



                            } else {

                                Toast.makeText(BranchActivity.this, "Branch Not added!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                 //       Toast.makeText(BranchActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                }){


         //This is for Headers If You Needed
        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            params.put("Content-Type", "application/json; charset=UTF-8");
            params.put("Authorization:","Bearer "+Token);
            Log.e( "getHeaders: ",Token );
            return params;
        }



        } ;
        int initialTimeoutMs = 0;
        request.setRetryPolicy(new DefaultRetryPolicy(200 * 30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(request);
    }

    private void restartActivity() {
        Intent intent = new Intent(BranchActivity.this,BranchActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        this.recreate();
        super.onRestart();


    }
}