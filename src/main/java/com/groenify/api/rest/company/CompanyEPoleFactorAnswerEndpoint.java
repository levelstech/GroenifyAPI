package com.groenify.api.rest.company;

import com.groenify.api.database.company.CompanyEPoleFactorAnswer;
import com.groenify.api.rest.company.__model.CompanyEPoleFactorAnswerResMo;
import com.groenify.api.service.epole.CompanyEPoleFactorAnswerService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/company_epoles_answer")
public class CompanyEPoleFactorAnswerEndpoint {

    private final CompanyEPoleFactorAnswerService service;

    public CompanyEPoleFactorAnswerEndpoint(
            final CompanyEPoleFactorAnswerService var) {
        this.service = var;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<CompanyEPoleFactorAnswerResMo> getAllCompanyEPoles() {
        final List<CompanyEPoleFactorAnswer> list = service.getAll();
        return CompanyEPoleFactorAnswerResMo.mapCompanyEPoleToResMoList(list);
    }

}
