package com.groenify.api.rest.epole;

import com.groenify.api.database.epole.EPole;
import com.groenify.api.framework.annotation.EPoleInPath;
import com.groenify.api.rest.epole.__model.EPoleReqMo;
import com.groenify.api.rest.epole.__model.EPoleResMo;
import com.groenify.api.service.epole.EPoleService;
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
@RequestMapping(value = "/api/v1/epoles")
public class EPoleEndpoint {


    private final EPoleService service;

    public EPoleEndpoint(final EPoleService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<EPoleResMo> getAllEPoles() {
        final List<EPole> ePoles = service.getAll();
        return EPoleResMo.mapEPoleToResMoList(ePoles);
    }

    @GetMapping(value = "/{ePoleId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final EPoleResMo getEPoleById(
            final @EPoleInPath EPole ePole) {
        return EPoleResMo.mapEPoleToResMo(ePole);
    }

    @PutMapping(value = "/{ePoleId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final EPoleResMo updateEPoleById(
            final @EPoleInPath EPole ePole,
            final @RequestBody EPoleReqMo body) {
        final EPole newEPole = service.update(ePole, body);
        return EPoleResMo.mapEPoleToResMo(newEPole);
    }

    @DeleteMapping(value = "/{ePoleId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public final Boolean deleteEPoleById(
            final @EPoleInPath EPole ePole) {
        return service.delete(ePole);
    }


}
