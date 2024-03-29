package com.groenify.api.rest.factor;

import com.groenify.api.database.model.factor.Factor;
import com.groenify.api.framework.annotation.FactorInPath;
import com.groenify.api.rest.factor.__model.FactorReqMo;
import com.groenify.api.rest.factor.__model.FactorResMo;
import com.groenify.api.database.service.factor.FactorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/factors")
public class FactorEndpoint {


    private final FactorService service;

    public FactorEndpoint(final FactorService var) {
        this.service = var;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<FactorResMo> getAllFactors() {
        final List<Factor> factors = service.getAll();
        return FactorResMo.mapFactorToResMoList(factors);
    }

    @GetMapping(value = "/{factorId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final FactorResMo getFactorById(
            final @FactorInPath Factor factor) {
        return FactorResMo.mapFactorToResMo(factor);
    }

    @PutMapping(value = "/{factorId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final FactorResMo updateFactorById(
            final @FactorInPath Factor factor,
            final @RequestBody FactorReqMo body) {
        final Factor newFactor = service.update(factor, body);
        return FactorResMo.mapFactorToResMo(newFactor);
    }

    @DeleteMapping(value = "/{factorId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public final Boolean deleteFactorById(
            final @FactorInPath Factor factor) {
        return service.delete(factor);
    }


}
