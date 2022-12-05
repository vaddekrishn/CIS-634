package com.digitalInnovation.internshipmanagementsystem.PogoData;

public class CampustypePogo {
    Integer id;
    String campus_type;

    public CampustypePogo(Integer id, String campus_type) {
        this.id = id;
        this.campus_type = campus_type;
    }

    public CampustypePogo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCampus_type() {
        return campus_type;
    }

    public void setCampus_type(String campus_type) {
        this.campus_type = campus_type;
    }
}
