package com.digitalInnovation.internshipmanagementsystem.PogoData;

public class JobtypePogo {
    String id;
    String job_type;

    public JobtypePogo(String id, String job_type) {
        this.id = id;
        this.job_type = job_type;
    }

    public JobtypePogo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJob_type() {
        return job_type;
    }

    public void setJob_type(String job_type) {
        this.job_type = job_type;
    }
}
