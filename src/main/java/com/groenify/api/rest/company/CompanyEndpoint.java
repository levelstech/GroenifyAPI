package com.groenify.api.rest.company;

import com.groenify.api.database.company.Company;
import com.groenify.api.database.epole.EPoleBrand;
import com.groenify.api.framework.annotation.CompanyInPath;
import com.groenify.api.rest.company.__model.CompanyReqMo;
import com.groenify.api.rest.company.__model.CompanyResMo;
import com.groenify.api.rest.epole.__model.EPoleBrandResMo;
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
    public final List<CompanyResMo> getAllCompanies() {
        final List<Company> list = service.getAll();
        return CompanyResMo.mapCompanyToResMoList(list);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public final CompanyResMo createCompany(
            final @Valid @RequestBody CompanyReqMo body) {
        final Company company = service.create(body);
        return CompanyResMo.mapCompanyToResMo(company);
    }

    @GetMapping(value = "/{companyId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final CompanyResMo getCompanyById(
            final @CompanyInPath Company company) {
        return CompanyResMo.mapCompanyToResMo(company);
    }

    @PutMapping(value = "/{companyId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final CompanyResMo updateCompanyById(
            final @CompanyInPath Company company,
            final @Valid @RequestBody CompanyReqMo body) {
        final Company newCompany = service.update(company, body);
        return CompanyResMo.mapCompanyToResMo(newCompany);
    }

    @DeleteMapping(value = "/{companyId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public final Boolean deleteCompanyById(
            final @CompanyInPath Company company) {
        return service.delete(company);
    }

}
