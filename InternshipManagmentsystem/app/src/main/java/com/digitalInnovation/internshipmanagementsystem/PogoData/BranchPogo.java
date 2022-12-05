package com.digitalInnovation.internshipmanagementsystem.PogoData;

public class BranchPogo {
    Integer id;
    String name;

    public BranchPogo(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public BranchPogo() {
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
}
