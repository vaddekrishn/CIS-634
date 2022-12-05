package com.digitalInnovation.internshipmanagementsystem.PogoData;

public class StudentPogo {

    Integer id,internship_id,student_id,status;
    String name, company_name, address, branch,job_type, campus_type,resume,first_name,last_name;

    public StudentPogo(Integer id,Integer internship_id,Integer student_id,Integer status, String name, String company_name, String address, String branch, String job_type, String campus_type, String resume,String first_name,String last_name) {

        this.id = id;
        this.internship_id =internship_id;
        this.student_id = student_id;
        this.status = status;
        this.name = name;
        this.company_name = company_name;
        this.address = address;
        this.branch = branch;
        this.job_type = job_type;
        this.campus_type = campus_type;
        this.resume = resume;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public StudentPogo() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getInternship_id() {
        return internship_id;
    }

    public void setInternship_id(Integer internship_id) {
        this.internship_id = internship_id;
    }

    public Integer getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
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

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
}
