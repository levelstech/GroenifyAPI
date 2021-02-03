package com.groenify.api.rest.company;

import com.groenify.api.database.model.company.CompanyEPole;
import com.groenify.api.framework.annotation.CompanyEPoleInPath;
import com.groenify.api.rest.company.__model.CompanyEPoleReqMo;
import com.groenify.api.rest.company.__model.CompanyEPoleResMo;
import com.groenify.api.database.service.company.CompanyEPoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/company_epoles")
public class CompanyEPoleEndpoint {

    private final CompanyEPoleService service;

    public CompanyEPoleEndpoint(final CompanyEPoleService var) {
        this.service = var;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<CompanyEPoleResMo> getAllCompanyEPoles() {
        final List<CompanyEPole> list = service.getAll();
        return CompanyEPoleResMo.mapCompanyEPoleToResMoList(list);
    }

    @GetMapping(value = "/{companyEPoleId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final CompanyEPoleResMo getAllCompanyEPoleById(
            final @CompanyEPoleInPath CompanyEPole companyEPole) {
        return CompanyEPoleResMo.mapCompanyEPoleToResMo(companyEPole);
    }

    @PutMapping(value = "/{companyEPoleId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final CompanyEPoleResMo updateCompanyEPoleById(
            final @CompanyEPoleInPath CompanyEPole companyEPole,
            final @Valid @RequestBody CompanyEPoleReqMo body) {
        final CompanyEPole newCompanyEPole = service.update(companyEPole, body);
        return CompanyEPoleResMo.mapCompanyEPoleToResMo(newCompanyEPole);
    }

    @DeleteMapping(value = "/{companyEPoleId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public final Boolean deleteCompanyEPoleById(
            final @CompanyEPoleInPath CompanyEPole companyEPole) {
        return service.delete(companyEPole);
    }

}
