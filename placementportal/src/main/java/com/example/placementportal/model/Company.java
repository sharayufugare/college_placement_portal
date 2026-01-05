package com.example.placementportal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "skills")
    private String skills;

    @Column(name = "criteria")
    private String criteria;

    @Column(name = "status")
    private String status;

    public Company() {}

    public Company(String companyName, String skills, String criteria, String status) {
        this.companyName = companyName;
        this.skills = skills;
        this.criteria = criteria;
        this.status = status;
    }

    // Getters and setters
    public Long getId() { return id; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }
    public String getCriteria() { return criteria; }
    public void setCriteria(String criteria) { this.criteria = criteria; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
