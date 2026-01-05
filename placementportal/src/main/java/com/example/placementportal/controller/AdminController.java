package com.example.placementportal.controller;

import com.example.placementportal.model.Admin;
import com.example.placementportal.model.Application;
import com.example.placementportal.repository.ApplicationRepository;
import com.example.placementportal.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @Autowired
    private ApplicationRepository applicationRepository;

    @GetMapping("/applications/{companyId}")
    public List<Application> getApplicationsForCompany(@PathVariable Long companyId) {
        return applicationRepository.findByCompanyId(companyId);
    }


    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> req) {
        String username = req.get("username");
        String password = req.get("password");

        Map<String, Object> response = new HashMap<>();
        Optional<Admin> adminOpt = adminService.login(username, password);

        if (adminOpt.isPresent()) {
            response.put("status", "success");
            response.put("admin", adminOpt.get());
        } else {
            response.put("status", "failed");
            response.put("message", "Invalid credentials");
        }

        return response;
    }
}
