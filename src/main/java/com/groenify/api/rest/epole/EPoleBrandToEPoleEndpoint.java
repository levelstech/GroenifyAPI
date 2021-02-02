package com.groenify.api.rest.epole;

import com.groenify.api.database.model.epole.EPole;
import com.groenify.api.database.model.epole.EPoleBrand;
import com.groenify.api.framework.annotation.EPoleBrandInPath;
import com.groenify.api.rest.epole.__model.EPoleReqMo;
import com.groenify.api.rest.epole.__model.EPoleResMo;
import com.groenify.api.database.service.epole.EPoleService;
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
@RequestMapping(value = "/api/v1/epole_brands")
public class EPoleBrandToEPoleEndpoint {

    private final EPoleService service;

    public EPoleBrandToEPoleEndpoint(final EPoleService var) {
        this.service = var;
    }

    @GetMapping(value = "/{brandId}/epoles",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<EPoleResMo> getAllEPolesFromEPoleBrand(
            final @EPoleBrandInPath EPoleBrand ePoleBrand) {
        final List<EPole> poles = service.getAllFromBrand(ePoleBrand);
        return EPoleResMo.mapEPoleToResMoList(poles);
    }

    @PostMapping(value = "/{brandId}/epoles",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public final EPoleResMo createEPoleOnEPoleBrand(
            final @EPoleBrandInPath EPoleBrand ePoleBrand,
            final @Valid @RequestBody EPoleReqMo body) {
        final EPole newEPole = service.create(ePoleBrand, body);
        return EPoleResMo.mapEPoleToResMo(newEPole);
    }

}
