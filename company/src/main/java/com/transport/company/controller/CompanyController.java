package com.transport.company.controller;

import com.transport.company.entity.Company;
import com.transport.company.repository.CompanyRepository;
import com.transport.company.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class CompanyController {
    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/company")
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable(value = "id") Long companyId)
            throws ResourceNotFoundException {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyId));
        return ResponseEntity.ok().body(company);
    }

    @PostMapping("/company")
    public Company createCompany(@Validated @RequestBody Company company) {
        return companyRepository.save(company);
    }

    @PutMapping("/company/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable(value = "id") Long companyId,
                                                   @Validated @RequestBody Company companyDetails) throws ResourceNotFoundException {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyId));

        company.setName(companyDetails.getName());
        final Company updatedCompany = companyRepository.save(company);
        return ResponseEntity.ok(updatedCompany);
    }

    @DeleteMapping("/company/{id}")
    public Map<String, Boolean> deleteCompany(@PathVariable(value = "id") Long companyId)
            throws ResourceNotFoundException {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyId));

        companyRepository.delete(company);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}