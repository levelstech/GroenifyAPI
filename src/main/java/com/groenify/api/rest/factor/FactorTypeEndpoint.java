package com.groenify.api.rest.factor;

import com.groenify.api.database.epole.EPoleBrand;
import com.groenify.api.database.factor.FactorType;
import com.groenify.api.framework.annotation.EPoleBrandInPath;
import com.groenify.api.framework.annotation.FactorTypeInPath;
import com.groenify.api.rest.epole.__model.EPoleBrandReqMo;
import com.groenify.api.rest.epole.__model.EPoleBrandResMo;
import com.groenify.api.rest.factor.__model.FactorTypeReqMo;
import com.groenify.api.rest.factor.__model.FactorTypeResMo;
import com.groenify.api.service.epole.EPoleBrandService;
import com.groenify.api.service.factor.FactorTypeService;
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
@RequestMapping(value = "/api/v1/factor_types")
public class FactorTypeEndpoint {

    private final FactorTypeService service;

    public FactorTypeEndpoint(final FactorTypeService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<FactorTypeResMo> getAllFactorTypes() {
        final List<FactorType> list = service.getAll();
        return FactorTypeResMo.mapFactorTypeToResMoList(list);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public final FactorTypeResMo createFactorType(
            final @Valid @RequestBody FactorTypeReqMo body) {
        final FactorType type = service.create(body);
        return FactorTypeResMo.mapFactorTypeToResMo(type);
    }

    @GetMapping(value = "/{factorTypeId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final FactorTypeResMo getFactorTypeById(
            final @FactorTypeInPath FactorType type) {
        return FactorTypeResMo.mapFactorTypeToResMo(type);
    }

    @PutMapping(value = "/{factorTypeId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final FactorTypeResMo updateFactorTypeById(
            final @FactorTypeInPath FactorType type,
            final @RequestBody FactorTypeReqMo body) {
        final FactorType newType = service.update(type, body);
        return FactorTypeResMo.mapFactorTypeToResMo(newType);
    }

    @DeleteMapping(value = "/{factorTypeId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public final Boolean deleteFactorTypeById(
            final @FactorTypeInPath FactorType type) {
        return service.delete(type);
    }

}
