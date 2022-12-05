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
import android.widget.ImageView;
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
import com.digitalInnovation.internshipmanagementsystem.InternshipApplication;
import com.digitalInnovation.internshipmanagementsystem.LoginActivity;
import com.digitalInnovation.internshipmanagementsystem.MainActivity;
import com.digitalInnovation.internshipmanagementsystem.PogoData.BranchPogo;
import com.digitalInnovation.internshipmanagementsystem.PogoData.CivilPogo;
import com.digitalInnovation.internshipmanagementsystem.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.ViewHolder> {
    private List<BranchPogo> list;
    private Context rcContext;
    String Branch_ID,Token;
    SharedPreferences sp_user;

    public BranchAdapter(List<BranchPogo> list, Context rcContext) {
        this.list = list;
        this.rcContext = rcContext;
    }
    @NonNull
    @Override
    public BranchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_branch,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BranchAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final BranchPogo pogo=list.get(position);
        holder.branch_name.setText(pogo.getName());

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Branch_ID = String.valueOf(pogo.getId());
                Log.e( "onClick: ",Branch_ID );

                sp_user = rcContext.getSharedPreferences("admin_login", MODE_PRIVATE);
                Token = sp_user.getString("token", "");
                Log.e( "onClicktoken: ",Token );

                String URL="http://ims.ditests.com/api/admin/delete_branch";
                JSONObject jobj = new JSONObject();
                try {
                    jobj.put("branch_id",Branch_ID);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                        URL,
                        jobj,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                               // Toast.makeText(rcContext, response.toString(), Toast.LENGTH_SHORT).show();

                                try {
                                    String login_sts = response.getString("success");
                                    // Log.e( "onResponseCheck: ",login_sts );


                                    if (login_sts.equals("true")){
                                        Toast.makeText(rcContext, "Branch data deleted Successfully", Toast.LENGTH_SHORT).show();
                                        list.remove(position);
                                        notifyItemRemoved(position);
                                        notifyDataSetChanged();

                                    }else{
                                        Toast.makeText(rcContext, "User is not available, Please Complete your Registration", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //Toast.makeText(rcContext, "Something Went Wrong", Toast.LENGTH_SHORT).show();
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

                };
                int initialTimeoutMs = 0;
                request.setRetryPolicy(new DefaultRetryPolicy(200 * 30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                Volley.newRequestQueue(rcContext).add(request);


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView branch_name;
        ImageView deleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            branch_name = itemView.findViewById(R.id.branch_name);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }

}
