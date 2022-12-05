package com.digitalInnovation.internshipmanagementsystem.Admin;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
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
import com.digitalInnovation.internshipmanagementsystem.Adapter.StudentAdapter;
import com.digitalInnovation.internshipmanagementsystem.PogoData.ApplicationPogo;
import com.digitalInnovation.internshipmanagementsystem.PogoData.StudentPogo;
import com.digitalInnovation.internshipmanagementsystem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdminAppliedStudentFragment extends Fragment {
    RecyclerView recStudentList;
    List<StudentPogo> studentPogoList;
    StudentAdapter studentAdapter;
    EditText searchBar;
    ImageButton searchbtn;
    String URL = "http://ims.ditests.com/api/admin/all_apply_studentlist";
    SharedPreferences sp_user;
    String user_id, Internship_Id,id,name,email,Token,userID,FirstName,jobtype,campustype,branchname;
    String user_userID,userName,companyName,companyAddress,userResume,LastName,studentId,internshipId;
    int applicationStatus;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_applied_student, container, false);
        sp_user = getActivity().getSharedPreferences("admin_login", MODE_PRIVATE);
        id = sp_user.getString("userId", "");
        name = sp_user.getString("Name", "");
        email = sp_user.getString("email", "");
        Token = sp_user.getString("token", "");
        Log.e( "onCreateShared: ",Token );

        recStudentList = view.findViewById(R.id.recStudentList);
        searchBar = view.findViewById(R.id.searchBar);
        searchbtn = view.findViewById(R.id.searchbtn);

        recStudentList.setHasFixedSize(true);
        studentPogoList = new ArrayList<>();
        studentPogoList.clear();

        getApplicationData();

        return view;
    }

    private void getApplicationData() {

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("onResponseApplication: ", response.toString());
                        try {
                            String login_sts = response.getString("success");
                            // Log.e( "onResponseCheck: ",login_sts );


                            if (login_sts.equals("true")) {
                             //   Toast.makeText(getContext(), "get Application data", Toast.LENGTH_SHORT).show();


                                JSONObject jsonObject = response.getJSONObject("data");

                                Log.e( "onResponseApply: ",jsonObject.toString() );

                                JSONArray array = jsonObject.getJSONArray("application");


                                Log.e( "onResponseInternship: ", String.valueOf(array.toString()));

                                for (int l=0;l<array.length();l++){
                                    JSONObject object = array.getJSONObject(l);
                                    Log.e("onResponseTest: ", object.toString());

                                    userID = object.getString("id");
                                    userName = object.getString("name");
                                    //  companyName = object.getString("company_name");
                                    userResume = object.getString("resume");
                                    internshipId = object.getString("internship_id");
                                    studentId = object.getString("student_id");
                                    applicationStatus = Integer.parseInt(object.getString("status"));

                                    Log.e( "onResponseHome: ",userID+userName+userResume );

                                    JSONArray studArray = object.getJSONArray("student");
                                    Log.e( "onResponseStud: ", String.valueOf(studArray));
                                    JSONObject jsonObject1 = studArray.getJSONObject(0);
                                    FirstName = jsonObject1.getString("first_name");
                                    LastName = jsonObject1.getString("last_name");
                                    Log.e("onResponseU: ",FirstName + LastName);

                                    JSONArray intArray = object.getJSONArray("internship");

                                    Log.e( "onResponseAI: ",intArray.toString() );

                                    if (intArray.length()> 0){
                                        JSONObject jsonObject2 = intArray.getJSONObject(0);
                                        Log.e( "onResponseAIntern: ",jsonObject2.toString() );

                                        companyName = jsonObject2.getString("company_name");
                                        companyAddress = jsonObject2.getString("address");
                                        Log.e( "onResponseAC: ",companyName + companyAddress );

                                        JSONArray jobTypeArray = jsonObject2.getJSONArray("job_type");
                                        JSONObject jobTypeaObject = jobTypeArray.getJSONObject(0);

                                        jobtype = jobTypeaObject.getString("job_type");
                                        Log.e( "onResponseAJ: ",jobtype );

                                        JSONArray campusTypeArray = jsonObject2.getJSONArray("campus_type");
                                        JSONObject campusTypeaObject = campusTypeArray.getJSONObject(0);

                                        campustype = campusTypeaObject.getString("campus_type");
                                        Log.e( "onResponseAJ: ",campustype );

                                        JSONArray branchArray = jsonObject2.getJSONArray("branch");
                                        JSONObject branchArrayJSONObject = branchArray.getJSONObject(0);

                                        branchname = branchArrayJSONObject.getString("name");
                                        Log.e( "onResponseAB: ",branchname );
                                        if (companyName!="" && companyAddress!="" && jobtype!="" && campustype!="" && branchname!=""){

                                            StudentPogo pogo = new StudentPogo();

                                            pogo.setName(FirstName+" "+LastName);
                                            pogo.setAddress(companyAddress);
                                            pogo.setResume(userResume);
                                            pogo.setJob_type(jobtype);
                                            pogo.setCampus_type(campustype);
                                            pogo.setBranch(branchname);
                                            pogo.setCompany_name(companyName);
                                            pogo.setInternship_id(Integer.valueOf(internshipId));
                                            pogo.setStudent_id(Integer.valueOf(studentId));
                                            pogo.setStatus(applicationStatus);

                                            studentPogoList.add(pogo);

                                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                            recStudentList.setLayoutManager(linearLayoutManager);
                                            studentAdapter = new StudentAdapter(studentPogoList, getContext(),applicationStatus);
                                            recStudentList.setAdapter(studentAdapter);

                                        }

                                    }





                                }


                            } else {
                                Toast.makeText(getContext(), "Data not retrived", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


//                         studentAdapter.notifyDataSetChanged();
                        //  prb.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                     //   Toast.makeText(getContext(), "Something Went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization:", "Bearer " + Token);
                Log.e("getHeaders: ", Token);
                return params;
            }


        } ;

        request.setRetryPolicy(new DefaultRetryPolicy(200 * 30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
