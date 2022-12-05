package com.digitalInnovation.internshipmanagementsystem.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.digitalInnovation.internshipmanagementsystem.PogoData.BranchPogo;
import com.digitalInnovation.internshipmanagementsystem.PogoData.JobtypePogo;

import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter<JobtypePogo> {

    private ArrayList<JobtypePogo> myarrayList;
    int count;
    TextView text1;
    public SpinnerAdapter(Context context, int textViewResourceId, ArrayList<JobtypePogo> modelArrayList) {
        super(context, textViewResourceId, modelArrayList);
        this.myarrayList = modelArrayList;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);

    }

    @Nullable
    @Override
    public JobtypePogo getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        int count = myarrayList.size();
        return super.getCount();
        //return count > 0 ? count - 1 : count;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
    private View getCustomView(int position, ViewGroup parent) {
        JobtypePogo pogo = getItem(position);
        View spinnerRow = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        TextView label = (TextView) spinnerRow.findViewById(android.R.id.text1);
        assert pogo != null;
        label.setText(pogo.getJob_type());
        return spinnerRow;
    }
}
