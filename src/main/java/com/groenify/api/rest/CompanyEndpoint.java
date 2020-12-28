package com.groenify.api.rest;

import com.groenify.api.database.Company;
import com.groenify.api.repository.CompanyRepository;
import com.groenify.api.util.ListUtil;
import com.groenify.api.util.LongUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/companies")
public class CompanyEndpoint {

    private final CompanyRepository repository;

    public CompanyEndpoint(final CompanyRepository repository) {
        this.repository = repository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<Company> getAllCompanies() {

        final Iterable<Company> allCompaniesInIter = repository.findAll();
        return ListUtil.iterableToList(allCompaniesInIter);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<String> createCompany() {
        return List.of("Test", "test", "t");
    }

    @GetMapping(value = "/{companyId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final Company getCompanyById(
            final @PathVariable String companyId) {

        final Long id = LongUtil.parseOrDefault(companyId);
        if (id <= 0) return null;

        final Optional<Company> optionalCompany = repository.findById(id);

        if (optionalCompany.isEmpty()) return null;

        return optionalCompany.get();
    }

    @PutMapping(value = "/1",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<String> updateCompanyById() {
        return List.of("Test", "test", "t");
    }

    @DeleteMapping(value = "/1",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<String> deleteCompanyById() {
        return List.of("Test", "test", "t");
    }

}
