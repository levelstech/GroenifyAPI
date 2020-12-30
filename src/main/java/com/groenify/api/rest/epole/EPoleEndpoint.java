package com.groenify.api.rest.epole;

import com.groenify.api.database.epole.EPole;
import com.groenify.api.database.epole.EPoleBrand;
import com.groenify.api.framework.annotation.EPoleBrandInPath;
import com.groenify.api.framework.annotation.EPoleInPath;
import com.groenify.api.rest.epole.__model.EPoleReqMo;
import com.groenify.api.rest.epole.__model.EPoleResMo;
import com.groenify.api.service.epole.EPoleService;
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
@RequestMapping(value = "/api/v1")
public class EPoleEndpoint {


    private final EPoleService service;

    public EPoleEndpoint(final EPoleService service) {
        this.service = service;
    }

    @GetMapping(value = "/epoles",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<EPoleResMo> getAllEPoles() {
        final List<EPole> ePoles = service.getAll();
        return EPoleResMo.mapEPoleBrandToResMoList(ePoles);
    }

    @GetMapping(value = "/epoles/{ePoleId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final EPoleResMo getEPoleById(
            final @EPoleInPath EPole ePole) {
        return EPoleResMo.mapEPoleBrandToResMo(ePole);
    }

    @PutMapping(value = "/epoles/{ePoleId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final EPoleResMo updateEPoleById(
            final @EPoleInPath EPole ePole,
            final @RequestBody EPoleReqMo body) {
        final EPole newEPole = service.update(ePole, body);
        return EPoleResMo.mapEPoleBrandToResMo(newEPole);
    }

    @DeleteMapping(value = "/epoles/{ePoleId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public final Boolean deleteEPoleById(
            final @EPoleInPath EPole ePole) {
        return service.delete(ePole);
    }

    @GetMapping(value = "/epole_brands/{brandId}/epoles",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<EPoleResMo> getAllEPolesFromEPoleBrand(
            final @EPoleBrandInPath EPoleBrand ePoleBrand) {
        final List<EPole> poles = service.getAllFromBrand(ePoleBrand);
        return EPoleResMo.mapEPoleBrandToResMoList(poles);
    }

    @PostMapping(value = "/epole_brands/{brandId}/epoles",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public final EPoleResMo createEPoleOnEPoleBrand(
            final @EPoleBrandInPath EPoleBrand ePoleBrand,
            final @Valid @RequestBody EPoleReqMo body) {
        final EPole newEPole = service.create(ePoleBrand, body);
        return EPoleResMo.mapEPoleBrandToResMo(newEPole);
    }

}
