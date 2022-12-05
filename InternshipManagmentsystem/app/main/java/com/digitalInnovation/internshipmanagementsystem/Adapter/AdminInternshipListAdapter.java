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
import com.digitalInnovation.internshipmanagementsystem.Admin.MainActivity2;
import com.digitalInnovation.internshipmanagementsystem.InternshipApplication;
import com.digitalInnovation.internshipmanagementsystem.PogoData.InternshipPogo;
import com.digitalInnovation.internshipmanagementsystem.R;
import com.digitalInnovation.internshipmanagementsystem.Admin.UpdateInternship;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminInternshipListAdapter extends RecyclerView.Adapter<AdminInternshipListAdapter.ViewHolder> {

    private List<InternshipPogo> list;
    private Context rcContext;
    SharedPreferences sp_user;
     String id,Token;
    String Internship_ID;

    public AdminInternshipListAdapter(List<InternshipPogo> list, Context rcContext) {
        this.list = list;
        this.rcContext = rcContext;
    }
    @NonNull
    @Override
    public AdminInternshipListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_internship_admin,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminInternshipListAdapter.ViewHolder holder, int position) {
        final InternshipPogo pogo=list.get(position);
        holder.title_internship.setText(pogo.getTitle());
        holder.companyName.setText(pogo.getCompany_name());
        holder.companyAddress.setText(pogo.getAddress());
        holder.jobType.setText(pogo.getJob_type());
        holder.campusType.setText(pogo.getCampus_type());
        holder.branch.setText(pogo.getBranch());
        holder.companyDescription.setText(pogo.getDescription());
        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Internship_ID = String.valueOf(pogo.getId());
                String tittle = pogo.getTitle();
                String companyname = pogo.getCompany_name();
                String typeofjob = pogo.getJob_type();
                String typeofcampus = pogo.getCampus_type();
                String branchs = pogo.getBranch();
                String Address = pogo.getAddress();
                String desc = pogo.getDescription();
                Log.e( "onClick: ",Internship_ID );
                Intent intent = new Intent(rcContext, UpdateInternship.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("internshipId",Internship_ID);
                intent.putExtra("companyname",companyname);
                intent.putExtra("typeofjob",typeofjob);
                intent.putExtra("typeofcampus",typeofcampus);
                intent.putExtra("Address",Address);
                intent.putExtra("desc",desc);
                intent.putExtra("banches",branchs);
                intent.putExtra("title",tittle);
                rcContext.startActivity(intent);
            }
        });

        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                Internship_ID = String.valueOf(pogo.getId());
                Log.e( "onClick: ",Internship_ID );
//                Intent intent = new Intent(rcContext, MainActivity2.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("internshipId",Internship_ID);
//                rcContext.startActivity(intent);

                deleteInternshipdata();
                notifyDataSetChanged();
            }
        });
    }

    private void deleteInternshipdata() {
        sp_user = rcContext.getSharedPreferences("admin_login", MODE_PRIVATE);
        id = sp_user.getString("userId", "");
        Token = sp_user.getString("token", "");
        Log.e( "onCreateShared: ",Token );
        String URL="http://ims.ditests.com/api/admin/delete_internship";
        JSONObject jobj = new JSONObject();

        try {

            jobj.put("internship_id",Internship_ID);


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
//                        Toast.makeText(rcContext, response.toString(), Toast.LENGTH_SHORT).show();

                        try {

                            String login_sts = response.getString("success");

                            if (login_sts.equals("true")) {

                                Toast.makeText(rcContext, " Data Deleted !.", Toast.LENGTH_SHORT).show();
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
                       // Toast.makeText(rcContext, error.getMessage(), Toast.LENGTH_SHORT).show();
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
    public void filteredlist(ArrayList<InternshipPogo> filterlist) {


        list = filterlist;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title_internship,companyName,companyAddress,jobType,branch,campusType,companyDescription;
        ImageButton updateBtn,removeBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title_internship = itemView.findViewById(R.id.title_internship);
            companyName = itemView.findViewById(R.id.companyName);
            companyAddress = itemView.findViewById(R.id.companyAddress);
            jobType = itemView.findViewById(R.id.jobType);
            campusType = itemView.findViewById(R.id.campusType);
            branch = itemView.findViewById(R.id.branch);
            companyDescription = itemView.findViewById(R.id.companyDescription);
            updateBtn = itemView.findViewById(R.id.updateBtn);
            removeBtn = itemView.findViewById(R.id.removeBtn);

        }
    }
}
