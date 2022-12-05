package com.digitalInnovation.internshipmanagementsystem.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalInnovation.internshipmanagementsystem.PogoData.ApplicationPogo;
import com.digitalInnovation.internshipmanagementsystem.PogoData.InternshipPogo;
import com.digitalInnovation.internshipmanagementsystem.R;

import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ViewHolder> {

    private List<ApplicationPogo> list;
    private Context rcContext;
    Integer status;

    public ApplicationAdapter(List<ApplicationPogo> list, Context rcContext, Integer status) {
        this.list = list;
        this.rcContext = rcContext;
        this.status = status;
    }

    @NonNull
    @Override
    public ApplicationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_appliedinternship, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationAdapter.ViewHolder holder, int position) {
        final ApplicationPogo pogo = list.get(position);
        holder.title_internship.setText(pogo.getTitle());
        holder.companyName.setText(pogo.getCompany_name());
        holder.companyAddress.setText(pogo.getAddress());
        holder.jobType.setText(pogo.getJob_type());
        holder.campusType.setText(pogo.getCampus_type());
        holder.branch.setText(pogo.getBranch());
        int application_status = pogo.getStatus();
        Log.e("onBindViewHolder: ", String.valueOf(application_status));

        if (application_status == 0) {

            holder.applicationStatus.setText("Pending");
        } else if (application_status == 1) {

            holder.applicationStatus.setText("Accepted");
        } else {
            holder.applicationStatus.setText("Rejected");

        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title_internship, companyName, companyAddress, jobType, branch, campusType, companyDescription, applicationStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title_internship = itemView.findViewById(R.id.title_internship);
            companyName = itemView.findViewById(R.id.companyName);
            companyAddress = itemView.findViewById(R.id.companyAddress);
            jobType = itemView.findViewById(R.id.jobType);
            campusType = itemView.findViewById(R.id.campusType);
            branch = itemView.findViewById(R.id.branch);
            //  companyDescription = itemView.findViewById(R.id.companyDescription);
            applicationStatus = itemView.findViewById(R.id.applicationStatus);
        }
    }
}
