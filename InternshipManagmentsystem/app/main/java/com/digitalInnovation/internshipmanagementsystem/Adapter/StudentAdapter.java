package com.digitalInnovation.internshipmanagementsystem.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.digitalInnovation.internshipmanagementsystem.Admin.AdminVerificationActivity;
import com.digitalInnovation.internshipmanagementsystem.PdfReaderActivity;
import com.digitalInnovation.internshipmanagementsystem.PogoData.InternshipPogo;
import com.digitalInnovation.internshipmanagementsystem.PogoData.StudentPogo;
import com.digitalInnovation.internshipmanagementsystem.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private List<StudentPogo> list;
    private Context rcContext;
    SharedPreferences sp_user;
    String id,email,Token,internshipID,StudentID;
    Integer status;


    public StudentAdapter(List<StudentPogo> list, Context rcContext,Integer status) {
        this.list = list;
        this.rcContext = rcContext;
        this.status = status;
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_student,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final StudentPogo pogo=list.get(position);
        holder.user_Name.setText(pogo.getName());
        holder.companyName.setText(pogo.getCompany_name());
        holder.companyAddress.setText(pogo.getAddress());
        holder.jobType.setText(pogo.getJob_type());
        holder.campusType.setText(pogo.getCampus_type());
        holder.branch.setText(pogo.getBranch());
        holder.resumefile.setText(pogo.getResume());
        int application_status = pogo.getStatus();
        Log.e( "onBindViewHolder: ", String.valueOf(application_status));

        if (application_status == 0){

            holder.approve.setText("Approve");
        }else {

            holder.approve.setText("Approved");
        }

        holder.resumefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             String resumepdf = pogo.getResume();
                Intent i = new Intent(rcContext, PdfReaderActivity.class);
                i.putExtra("studentResume",resumepdf);
                rcContext.startActivity(i);
            }
        });

        holder.approvedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                internshipID = String.valueOf(pogo.getInternship_id());
                StudentID = String.valueOf(pogo.getStudent_id());

                Log.e( "onClickA: ",internshipID+StudentID );

                holder.approve.setText("Approved");

                approvedStudent();

            }
        });

        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                internshipID = String.valueOf(pogo.getInternship_id());
                StudentID = String.valueOf(pogo.getStudent_id());

                Log.e( "onClickR: ",internshipID+StudentID );

                holder.Remove.setText("Rejected");

                RejectStudent();


            }
        });

    }

    private void RejectStudent() {
        sp_user = rcContext.getSharedPreferences("admin_login", MODE_PRIVATE);
        id = sp_user.getString("userId", "");
        Token = sp_user.getString("token", "");
        Log.e( "onCreateShared: ",Token );
        String URL="http://ims.ditests.com/api/admin/reject_student";
        JSONObject jobj = new JSONObject();

        try {

            jobj.put("internship_id",internshipID);
            jobj.put("student_id",StudentID);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e( "InsertEmail: ", jobj.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                URL,
                jobj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                       // Toast.makeText(rcContext, response.toString(), Toast.LENGTH_SHORT).show();

                        try {

                            String login_sts = response.getString("success");

                            if (login_sts.equals("true")) {

                                Toast.makeText(rcContext, " status updated !.", Toast.LENGTH_SHORT).show();


                                JSONObject jsonObject = response.getJSONObject("data");
                                Log.e( "onResponse: ",jsonObject.toString() );
                            } else {

                                Toast.makeText(rcContext, "data not available", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                     //   Toast.makeText(rcContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
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
        Volley.newRequestQueue(rcContext.getApplicationContext()).add(request);
    }

    private void approvedStudent() {


        sp_user = rcContext.getSharedPreferences("admin_login", MODE_PRIVATE);
        id = sp_user.getString("userId", "");
        Token = sp_user.getString("token", "");
        Log.e( "onCreateShared: ",Token );
        String URL="http://ims.ditests.com/api/admin/approve_student";
        JSONObject jobj = new JSONObject();

        try {

            jobj.put("internship_id",internshipID);
            jobj.put("student_id",StudentID);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e( "InsertEmail: ", jobj.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                URL,
                jobj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                      //  Toast.makeText(rcContext, response.toString(), Toast.LENGTH_SHORT).show();

                        try {

                            String login_sts = response.getString("success");

                            if (login_sts.equals("true")) {

                                Toast.makeText(rcContext, " status updated !.", Toast.LENGTH_SHORT).show();
                                JSONObject jsonObject = response.getJSONObject("data");
                                Log.e( "onResponse: ",jsonObject.toString() );
                            } else {

                                Toast.makeText(rcContext, "data not available", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      //  Toast.makeText(rcContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
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
        Volley.newRequestQueue(rcContext.getApplicationContext()).add(request);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filteredlist(ArrayList<StudentPogo> filterlist) {


        list = filterlist;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView user_Name,companyName,companyAddress,jobType,branch,campusType,resumefile,approve,Remove;
        ImageButton approvedBtn,removeBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_Name = itemView.findViewById(R.id.stud_Name);
            companyName = itemView.findViewById(R.id.company_Name);
            companyAddress = itemView.findViewById(R.id.company_Address);
            branch = itemView.findViewById(R.id.stud_branch);
            jobType = itemView.findViewById(R.id.job_Type);
            campusType = itemView.findViewById(R.id.campus_Type);
            resumefile = itemView.findViewById(R.id.resumefile);
            approvedBtn = itemView.findViewById(R.id.approvedBtn);
            removeBtn = itemView.findViewById(R.id.removeBtn);
            approve = itemView.findViewById(R.id.approve);
            Remove = itemView.findViewById(R.id.Remove);
        }
    }
}
