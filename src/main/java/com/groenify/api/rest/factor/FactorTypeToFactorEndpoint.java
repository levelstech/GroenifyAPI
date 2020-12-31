package com.groenify.api.rest.factor;

import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.FactorType;
import com.groenify.api.framework.annotation.FactorTypeInPath;
import com.groenify.api.rest.factor.__model.FactorReqMo;
import com.groenify.api.rest.factor.__model.FactorResMo;
import com.groenify.api.service.factor.FactorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/factor_types")
public class FactorTypeToFactorEndpoint {

    private final FactorService service;

    public FactorTypeToFactorEndpoint(final FactorService service) {
        this.service = service;
    }

    @GetMapping(value = "/{factorTypeId}/factors",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<FactorResMo> getAllFactorsFromFactorType(
            final @FactorTypeInPath FactorType factorType) {
        final List<Factor> factors = service.getAllFromType(factorType);
        return FactorResMo.mapFactorToResMoList(factors);
    }

    @PostMapping(value = "/{factorTypeId}/factors",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public final FactorResMo createFactorOnFactorType(
            final @FactorTypeInPath FactorType factorType,
            final @Valid @RequestBody FactorReqMo body) {
        final Factor newFactor = service.create(factorType, body);
        return FactorResMo.mapFactorToResMo(newFactor);
    }

}
