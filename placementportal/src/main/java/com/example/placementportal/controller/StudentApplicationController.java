/*package com.example.placementportal.controller;

import com.example.placementportal.model.*;
import com.example.placementportal.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/student")
@CrossOrigin
public class StudentApplicationController {

    private final ApplicationRepository applicationRepo;
    private final StudentRepository studentRepo;
    private final CompanyRepository companyRepo;

    public StudentApplicationController(
            ApplicationRepository applicationRepo,
            StudentRepository studentRepo,
            CompanyRepository companyRepo
    ) {
        this.applicationRepo = applicationRepo;
        this.studentRepo = studentRepo;
        this.companyRepo = companyRepo;
    }

    // ✅ APPLY TO COMPANY
    @PostMapping("/apply/{companyId}")
    public ResponseEntity<?> applyToCompany(
            @PathVariable Long companyId,
            @RequestParam Long studentId
    ) {
        // check student
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // check company
        Company company = companyRepo.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        // check duplicate apply
        Optional<Application> existing =
                applicationRepo.findByStudentIdAndCompanyId(studentId, companyId);

        if (existing.isPresent()) {
            return ResponseEntity.badRequest()
                    .body("Already applied to this company");
        }

        // save application
        Application application = new Application(student, company);
        applicationRepo.save(application);

        return ResponseEntity.ok("Applied successfully");
    }
}*/
package com.example.placementportal.controller;

import com.example.placementportal.model.*;
import com.example.placementportal.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "*")
public class StudentApplicationController {

    private final StudentRepository studentRepo;
    private final CompanyRepository companyRepo;
    private final ApplicationRepository applicationRepo;

    public StudentApplicationController(
            StudentRepository studentRepo,
            CompanyRepository companyRepo,
            ApplicationRepository applicationRepo
    ) {
        this.studentRepo = studentRepo;
        this.companyRepo = companyRepo;
        this.applicationRepo = applicationRepo;
    }

    @PostMapping("/apply/{companyId}")
    public ResponseEntity<?> applyToCompany(
            @PathVariable Long companyId,
            @RequestParam Long studentId
    ) {

        if (applicationRepo.findByStudentIdAndCompanyId(studentId, companyId).isPresent()) {
            return ResponseEntity.badRequest().body("Already applied");
        }

        Student student = studentRepo.findById(studentId).orElseThrow();
        Company company = companyRepo.findById(companyId).orElseThrow();

        Application application = new Application(student, company);
        applicationRepo.save(application);

        return ResponseEntity.ok("Applied successfully");
    }
}

