package com.example.placementportal.controller;

import com.example.placementportal.model.Company;
import com.example.placementportal.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    // ✅ 1. Add company
    @PostMapping("/add")
    public Company addCompany(@RequestParam String companyName,
                              @RequestParam String skills,
                              @RequestParam String criteria,
                              @RequestParam String status) {
        Company company = new Company(companyName, skills, criteria, status);
        return companyRepository.save(company);
    }


    // ✅ 2. Get all companies
    @GetMapping("/all")
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    // ✅ 3. Search by skill or criteria
    @GetMapping("/search")
    public List<Company> searchCompany(@RequestParam(required = false) String skill,
                                       @RequestParam(required = false) String criteria) {
        if (skill != null && criteria != null)
            return companyRepository.findBySkillsContainingAndCriteriaContaining(skill, criteria);
        else if (skill != null)
            return companyRepository.findBySkillsContaining(skill);
        else if (criteria != null)
            return companyRepository.findByCriteriaContaining(criteria);
        else
            return companyRepository.findAll();
    }

    // ✅ 4. Update company by ID
    @PutMapping("/update/{id}")
    public Company updateCompany(@PathVariable Long id, @RequestBody Company updatedCompany) {
        Optional<Company> existing = companyRepository.findById(id);
        if (existing.isPresent()) {
            Company company = existing.get();
            company.setCompanyName(updatedCompany.getCompanyName());
            company.setSkills(updatedCompany.getSkills());
            company.setCriteria(updatedCompany.getCriteria());
            company.setStatus(updatedCompany.getStatus());
            return companyRepository.save(company);
        }
        return null;
    }

    // ✅ 5. Delete company by ID
    @DeleteMapping("/delete/{id}")
    public String deleteCompany(@PathVariable Long id) {
        companyRepository.deleteById(id);
        return "Company deleted with ID: " + id;
    }
}



