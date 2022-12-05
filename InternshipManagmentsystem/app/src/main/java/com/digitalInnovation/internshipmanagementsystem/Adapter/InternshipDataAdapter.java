package com.digitalInnovation.internshipmanagementsystem.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalInnovation.internshipmanagementsystem.InternshipApplication;
import com.digitalInnovation.internshipmanagementsystem.MainActivity;
import com.digitalInnovation.internshipmanagementsystem.PogoData.InternshipPogo;
import com.digitalInnovation.internshipmanagementsystem.R;

import java.util.ArrayList;
import java.util.List;

public class InternshipDataAdapter extends RecyclerView.Adapter<InternshipDataAdapter.ViewHolder> {

    private List<InternshipPogo> list;
    private Context rcContext;

    public InternshipDataAdapter(List<InternshipPogo> list, Context rcContext) {
        this.list = list;
        this.rcContext = rcContext;
    }

    @NonNull
    @Override
    public InternshipDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_internship,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InternshipDataAdapter.ViewHolder holder, int position) {
        final InternshipPogo pogo=list.get(position);
        holder.title_internship.setText(pogo.getTitle());
        holder.companyName.setText(pogo.getCompany_name());
        holder.companyAddress.setText(pogo.getAddress());
        holder.jobType.setText(pogo.getJob_type());
        holder.campusType.setText(pogo.getCampus_type());
        holder.branch.setText(pogo.getBranch());
        holder.companyDescription.setText(pogo.getDescription());
        holder.applybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Internship_ID = String.valueOf(pogo.getId());
                Log.e( "onClick: ",Internship_ID );
                holder.Apply.setText("Applied");
                Intent intent = new Intent(rcContext, InternshipApplication.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("internshipId",Internship_ID);
                rcContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filteredlist(ArrayList<InternshipPogo> filterlist) {


        list = filterlist;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title_internship,companyName,companyAddress,jobType,branch,campusType,companyDescription,Apply;
        ImageButton applybtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title_internship = itemView.findViewById(R.id.title_internship);
            companyName = itemView.findViewById(R.id.companyName);
            companyAddress = itemView.findViewById(R.id.companyAddress);
            jobType = itemView.findViewById(R.id.jobType);
            campusType = itemView.findViewById(R.id.campusType);
            branch = itemView.findViewById(R.id.branch);
            companyDescription = itemView.findViewById(R.id.companyDescription);
            applybtn = itemView.findViewById(R.id.applybtn);
            Apply = itemView.findViewById(R.id.Apply);

        }
    }
}
