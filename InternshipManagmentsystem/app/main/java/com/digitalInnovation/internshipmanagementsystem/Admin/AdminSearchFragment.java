package com.digitalInnovation.internshipmanagementsystem.Admin;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
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
import com.digitalInnovation.internshipmanagementsystem.Adapter.AdminInternshipListAdapter;
import com.digitalInnovation.internshipmanagementsystem.PogoData.InternshipPogo;
import com.digitalInnovation.internshipmanagementsystem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminSearchFragment extends Fragment {
    RecyclerView recInternship;
    List<InternshipPogo> internshipPogoList;
    AdminInternshipListAdapter adminInternshipListAdapter;
    String URL="http://ims.ditests.com/api/admin/get_all_internship_data";
    SharedPreferences sp_user;
    String user_id,Internship_Id,id,name,email,Token;
    EditText searchBar;
    ImageButton searchbtn,addBtn;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_search, container, false);

        sp_user = getActivity().getSharedPreferences("admin_login", MODE_PRIVATE);
        id = sp_user.getString("userId", "");
        name = sp_user.getString("Name", "");
        email = sp_user.getString("email", "");
        Token = sp_user.getString("token", "");
        Log.e( "onCreateShared: ",Token );
        recInternship=view.findViewById(R.id.recInternship);
        searchBar=view.findViewById(R.id.searchBar);
        searchbtn=view.findViewById(R.id.searchbtn);
        addBtn=view.findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),AddInternshipActivity.class);
                startActivity(intent);

            }
        });

        recInternship.setHasFixedSize(true);
        internshipPogoList = new ArrayList<>();
        internshipPogoList.clear();



        getInternshipData();

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });


        return view;
    }
    private void getInternshipData() {

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
                             //   Toast.makeText(getContext(), "get Internship data", Toast.LENGTH_SHORT).show();


                                JSONObject jsonObject = response.getJSONObject("data");

                                Log.e("onResponseLogin: ", jsonObject.toString());

                                JSONArray array = jsonObject.getJSONArray("internship_data");


                                Log.e("onResponseInternship: ", String.valueOf(array.length()));

                                for (int k = 0; k < array.length(); k++) {
                                    JSONObject object = array.getJSONObject(k);
                                    Log.e("onResponseTest: ", object.getString("id"));
                                    InternshipPogo pogo = new InternshipPogo();
                                    pogo.setId(Integer.valueOf(object.getString("id")));
                                    pogo.setTitle(object.getString("title"));
                                    pogo.setCompany_name(object.getString("company_name"));
                                    pogo.setAddress(object.getString("address"));
                                    pogo.setDescription(object.getString("description"));


                                    JSONArray arrayJob = object.getJSONArray("job_type");
                                    Log.e("onResponsejob: ", arrayJob.toString());
                                    JSONObject jobObject = arrayJob.getJSONObject(0);
                                    Log.e("onResponsejobObject: ", jobObject.toString());
                                    pogo.setJob_type(jobObject.getString("job_type"));


                                    JSONArray arrayCampus = object.getJSONArray("campus_type");
                                    JSONObject campusObject = arrayCampus.getJSONObject(0);
                                    pogo.setCampus_type(campusObject.getString("campus_type"));


                                    JSONArray arrayBranch = object.getJSONArray("branch");
                                    JSONObject branchObject = arrayBranch.getJSONObject(0);
                                    pogo.setBranch(branchObject.getString("name"));


                                    internshipPogoList.add(pogo);

                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                    recInternship.setLayoutManager(linearLayoutManager);
                                    adminInternshipListAdapter = new AdminInternshipListAdapter(internshipPogoList, getContext());
                                    recInternship.setAdapter(adminInternshipListAdapter);

                                }


                            } else {
                                Toast.makeText(getContext(), "Data not retrived", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                         adminInternshipListAdapter.notifyDataSetChanged();
                        //  prb.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                     //   Toast.makeText(getContext(), "Something Went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
        ){

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

        request.setRetryPolicy(new DefaultRetryPolicy(200*30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(request);
    }

    private void filter(String toString) {

        ArrayList<InternshipPogo> filterlist = new ArrayList<>();
        for (InternshipPogo item : internshipPogoList) {

            if (item.getBranch().toLowerCase().contains(toString.toLowerCase())) {
                filterlist.add(item);
            }
        }
        adminInternshipListAdapter.filteredlist(filterlist);
    }
}