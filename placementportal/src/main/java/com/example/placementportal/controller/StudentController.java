package com.example.placementportal.controller;

import com.example.placementportal.dto.ApplicationDTO;
import com.example.placementportal.model.Application;
import com.example.placementportal.model.Company;
import com.example.placementportal.model.Student;
import com.example.placementportal.repository.ApplicationRepository;
import com.example.placementportal.repository.CompanyRepository;
import com.example.placementportal.service.StudentService;
import com.example.placementportal.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private CompanyRepository companyRepository;


    private final StudentService studentService;
    private final StudentRepository repo;

    // ---------------- REGISTER ----------------
    @PostMapping("/register")
    public Student register(@RequestBody Student s) {
        return studentService.createStudent(s);
    }

    // ---------------- LOGIN ----------------
    @PostMapping("/login")
    public Student login(@RequestBody Student request) {
        Optional<Student> s = studentService.login(request.getEmail(), request.getPassword());
        return s.orElse(null);
    }

    // ---------------- DASHBOARD ----------------
    @GetMapping("/dashboard/{id}")
    public Student dashboard(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    // ---------------- UPDATE PROFILE ----------------
    @PutMapping("/update/{id}")
    public Student update(@PathVariable Long id, @RequestBody Student updated) {
        return studentService.updateProfile(id, updated);
    }

    // ---------------- UPLOAD RESUME ----------------
// ---------------- UPLOAD RESUME ----------------
    @PostMapping("/uploadResume/{id}")
    public ResponseEntity<?> uploadResume(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            String path = System.getProperty("user.dir") + "/uploads/resumes/";
            new File(path).mkdirs();

            String filePath = path + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            studentService.saveResume(id, filePath);

            return ResponseEntity.ok("Resume uploaded");
        } catch (Exception e) {
            e.printStackTrace();  // SEE EXACT ERROR IN CONSOLE
            return ResponseEntity.status(500).body("ERROR: " + e.getMessage());
        }
    }


    // ---------------- UPLOAD PHOTO ----------------
    @PostMapping("/uploadPhoto/{id}")
    public ResponseEntity<?> uploadPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            String path = System.getProperty("user.dir") + "/uploads/photos/";
            new File(path).mkdirs();

            String filePath = path + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            studentService.savePhoto(id, filePath);

            return ResponseEntity.ok("Photo uploaded");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("ERROR: " + e.getMessage());
        }
    }

  /*  @PostMapping("/apply/{companyId}")
    public ResponseEntity<?> applyToCompany(
            @PathVariable Long companyId,
            @RequestParam Long studentId) {

        Student student = repo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        // prevent duplicate apply
        if (applicationRepository
                .findByStudentIdAndCompanyId(studentId, companyId)
                .isPresent()) {
            return ResponseEntity.badRequest().body("Already applied");
        }

        Application application = new Application(student, company);
        applicationRepository.save(application);

        return ResponseEntity.ok("Application submitted successfully");
    }
*/
  @GetMapping("/applications/{studentId}")
  public List<ApplicationDTO> getMyApplications(@PathVariable Long studentId) {
      return applicationRepository.findByStudentId(studentId)
              .stream()
              .map(app -> new ApplicationDTO(
                      app.getId(),
                      app.getStudent().getName(),
                      app.getCompany().getCompanyName(),
                      app.getStatus(),
                      app.getAppliedDate()
              ))
              .toList();
  }

}
