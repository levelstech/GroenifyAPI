package com.groenify.api.rest.company;

import com.groenify.api.database.company.Company;
import com.groenify.api.framework.annotation.CompanyInPath;
import com.groenify.api.service.company.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/companies")
public class CompanyEndpoint {

    private final CompanyService service;

    public CompanyEndpoint(final CompanyService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<Company> getAllCompanies() {
        return service.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public final Company createCompany(
            final @Valid @RequestBody Company company) {
        return service.create(company);
    }

    @GetMapping(value = "/{companyId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final Company getCompanyById(
            final @CompanyInPath Company company) {
        return company;
    }

    @PutMapping(value = "/{companyId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final Company updateCompanyById(
            final @CompanyInPath Company company,
            final @Valid @RequestBody Company updated) {
        return service.update(company, updated);
    }

    @DeleteMapping(value = "/{companyId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public final Boolean deleteCompanyById(
            final @CompanyInPath Company company) {
        return service.delete(company);
    }

}
