/*package com.example.placementportal.controller;

import com.example.placementportal.model.Application;
import com.example.placementportal.repository.ApplicationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tnp")
@CrossOrigin
public class TnpApiController {

    private final ApplicationRepository applicationRepo;

    public TnpApiController(ApplicationRepository applicationRepo) {
        this.applicationRepo = applicationRepo;
    }

    // 🔹 All applications
    @GetMapping("/applications")
    public List<Application> getAllApplications() {
        return applicationRepo.findAll();
    }

    // 🔹 Company wise
    @GetMapping("/applications/company/{companyId}")
    public List<Application> getByCompany(@PathVariable Long companyId) {
        return applicationRepo.findByCompanyId(companyId);
    }
}
*/
package com.example.placementportal.controller;

import com.example.placementportal.dto.ApplicationDTO;
import com.example.placementportal.model.Application;
import com.example.placementportal.repository.ApplicationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tnp")
@CrossOrigin(origins = "*")
public class TnpApiController {

    private final ApplicationRepository applicationRepo;

    public TnpApiController(ApplicationRepository applicationRepo) {
        this.applicationRepo = applicationRepo;
    }

    @GetMapping("/applications")
    public List<ApplicationDTO> getAllApplications() {
        return applicationRepo.findAll()
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

    @PutMapping("/applications/{id}/accept")
    public ResponseEntity<String> acceptApplication(@PathVariable Long id) {

        Application app = applicationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setStatus("Accepted");
        applicationRepo.save(app);

        return ResponseEntity.ok("Application Accepted");
    }

    @PutMapping("/applications/{id}/reject")
    public ResponseEntity<String> rejectApplication(@PathVariable Long id) {

        Application app = applicationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setStatus("Rejected");
        applicationRepo.save(app);

        return ResponseEntity.ok("Application Rejected");
    }



}

