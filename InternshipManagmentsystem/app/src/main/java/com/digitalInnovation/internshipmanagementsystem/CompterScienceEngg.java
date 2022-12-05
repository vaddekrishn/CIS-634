package com.digitalInnovation.internshipmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.digitalInnovation.internshipmanagementsystem.Adapter.ComputerAdapter;
import com.digitalInnovation.internshipmanagementsystem.Adapter.ElectAdapter;
import com.digitalInnovation.internshipmanagementsystem.Adapter.MechanicalAdapter;
import com.digitalInnovation.internshipmanagementsystem.PogoData.ComputerPogo;
import com.digitalInnovation.internshipmanagementsystem.PogoData.ElectPogo;
import com.digitalInnovation.internshipmanagementsystem.PogoData.MechPogo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CompterScienceEngg extends AppCompatActivity {
    RecyclerView recComputer;
    List<ComputerPogo> computerPogoList;
    ComputerAdapter computerAdapter;
    String URL="http://ims.ditests.com/api/get_filter_data";
    SharedPreferences sp_user;
    String user_id,Internship_Id;
    ImageButton backbutton;
    TextView aboutUs;
    int branchID =5;
    String jobtype,campustype,branchname,Title,companyName,companyAddress,Description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compter_science_engg);

        recComputer=findViewById(R.id.recComputer);
        backbutton=findViewById(R.id.backbutton);
        aboutUs=findViewById(R.id.aboutUs);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompterScienceEngg.this,MainActivity.class);
                startActivity(intent);
            }
        });


        recComputer.setHasFixedSize(true);
        computerPogoList = new ArrayList<>();
        computerPogoList.clear();



        getCompData();

    }

    private void  getCompData() {
        JSONObject jobj = new JSONObject();

        try {
            jobj.put("branch_id",branchID);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e( "CompUser: ", jobj.toString());

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                jobj,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e( "onResponseIntern: ",response.toString() );
                        try {
                            String login_sts = response.getString("success");
                            // Log.e( "onResponseCheck: ",login_sts );


                            if (login_sts.equals("true")){
                            //    Toast.makeText(CompterScienceEngg.this, "get Internship data", Toast.LENGTH_SHORT).show();



                                JSONObject jsonObject = response.getJSONObject("data");

                                Log.e( "onResponseLogin: ",jsonObject.toString() );

                                JSONArray array = jsonObject.getJSONArray("internship_data");


                                Log.e( "onResponseInternship: ", String.valueOf(array.length()));

                                for (int r=0;r<array.length();r++){
                                    JSONObject object = array.getJSONObject(r);
                                    Log.e("onResponseTest: ", object.getString("id"));

                                    Title = object.getString("title");
                                    companyName = object.getString("company_name");
                                    companyAddress = object.getString("address");
                                    Description = object.getString("description");


                                    JSONArray arrayJob = object.getJSONArray("job_type");
                                    JSONObject jobObject = arrayJob.getJSONObject(0);
                                    jobtype= jobObject.getString("job_type");


                                    JSONArray arrayCampus = object.getJSONArray("campus_type");
                                    JSONObject campusObject = arrayCampus.getJSONObject(0);
                                    campustype=campusObject.getString("campus_type");


                                    JSONArray arrayBranch = object.getJSONArray("branch");
                                    JSONObject branchObject = arrayBranch.getJSONObject(0);
                                    branchname =branchObject.getString("name");

                                    ComputerPogo pogo = new ComputerPogo();
                                    pogo.setTitle(Title);
                                    pogo.setCompany_name(companyName);
                                    pogo.setAddress(companyAddress);
                                    pogo.setDescription(Description);
                                    pogo.setJob_type(jobtype);
                                    pogo.setCampus_type(campustype);
                                    pogo.setBranch(branchname);
                                    computerPogoList.add(pogo);

                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CompterScienceEngg.this);
                                    recComputer.setLayoutManager(linearLayoutManager);
                                    computerAdapter = new ComputerAdapter(computerPogoList,CompterScienceEngg.this);
                                    recComputer.setAdapter(computerAdapter);

                                }


                            }else{
                                Toast.makeText(CompterScienceEngg.this, "Data not retrived", Toast.LENGTH_SHORT).show();
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
                    //    Toast.makeText(CompterScienceEngg.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        request.setRetryPolicy(new DefaultRetryPolicy(200*30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(CompterScienceEngg.this).add(request);
    }

}