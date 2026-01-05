
/*package com.example.placementportal.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "applications",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "company_id"}))
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private String status; // APPLIED, SHORTLISTED, REJECTED

    private LocalDate appliedDate;

    public Application() {}

    public Application(Student student, Company company) {
        this.student = student;
        this.company = company;
        this.status = "APPLIED";
        this.appliedDate = LocalDate.now();
    }

    // getters & setters
}
*/
package com.example.placementportal.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(
        name = "applications",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "company_id"})
)
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    private String status;
    private LocalDate appliedDate;

    public Application() {}

    public Application(Student student, Company company) {
        this.student = student;
        this.company = company;
        this.status = "APPLIED";
        this.appliedDate = LocalDate.now();
    }

    public Long getId() { return id; }
    public Student getStudent() { return student; }
    public Company getCompany() { return company; }
    public String getStatus() { return status; }
    public LocalDate getAppliedDate() { return appliedDate; }

    public void setStatus(String status) {
        this.status = status;
    }
}
