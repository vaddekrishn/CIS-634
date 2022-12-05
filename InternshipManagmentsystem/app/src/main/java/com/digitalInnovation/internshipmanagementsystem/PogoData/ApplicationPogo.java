package com.digitalInnovation.internshipmanagementsystem.PogoData;

public class ApplicationPogo {
    Integer id,status;
    String title, company_name, address, description,job_type, campus_type, branch;

    public ApplicationPogo(Integer id,Integer status, String title, String company_name, String address, String description, String job_type, String campus_type, String branch) {
        this.id = id;
        this.status = status;
        this.title = title;
        this.company_name = company_name;
        this.address = address;
        this.description = description;
        this.job_type = job_type;
        this.campus_type = campus_type;
        this.branch = branch;
    }

    public ApplicationPogo() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJob_type() {
        return job_type;
    }

    public void setJob_type(String job_type) {
        this.job_type = job_type;
    }

    public String getCampus_type() {
        return campus_type;
    }

    public void setCampus_type(String campus_type) {
        this.campus_type = campus_type;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
