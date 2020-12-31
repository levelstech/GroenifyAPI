package com.groenify.api.rest.company;

import com.groenify.api.database.company.Company;
import com.groenify.api.database.company.CompanyEPole;
import com.groenify.api.database.epole.EPole;
import com.groenify.api.framework.annotation.CompanyEPoleInPath;
import com.groenify.api.framework.annotation.CompanyInPath;
import com.groenify.api.framework.annotation.EPoleInPath;
import com.groenify.api.rest.company.__model.CompanyEPoleReqMo;
import com.groenify.api.rest.company.__model.CompanyEPoleResMo;
import com.groenify.api.service.company.CompanyEPoleService;
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
public class CompanyToEPoleEndpoint {

    private final CompanyEPoleService service;

    public CompanyToEPoleEndpoint(final CompanyEPoleService service) {
        this.service = service;
    }

    @PostMapping(value = "/{companyId}/epoles/{ePoleId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public final CompanyEPoleResMo createCompanyEPoleOnCompany(
            final @CompanyInPath Company company,
            final @EPoleInPath EPole ePole,
            final @Valid @RequestBody CompanyEPoleReqMo body) {
        final CompanyEPole newCompanyEPole = service.create(company, ePole, body);
        return CompanyEPoleResMo.mapCompanyEPoleToResMo(newCompanyEPole);
    }
}
