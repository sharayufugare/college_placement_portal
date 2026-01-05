package com.example.placementportal.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String skills;
    private String criteria;
    private String cgpa;
    private String branch;
    private String year;
    private String phone;
    private String linkedin;
    private String github;
    private String gender;
    private String achievements;

    private String resumePath;     // stored locally in /uploads/resumes
    private String photoPath;      // stored locally in /uploads/photos
    public String getName() {
        return name;
    }

}


