package com.groenify.api.rest;

import com.groenify.api.database.EPoleBrand;
import com.groenify.api.framework.resolver.EPoleBrandInPath;
import com.groenify.api.repository.EPoleBrandRepository;
import com.groenify.api.service.EPoleBrandService;
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
@RequestMapping(value = "/api/v1/epole_brands")
public class EPoleBrandEndpoint {

    private final EPoleBrandService service;

    public EPoleBrandEndpoint(final EPoleBrandService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<EPoleBrand> getAllEPoleBrands() {
        return service.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public final EPoleBrand createEPoleBrand(
            final @Valid @RequestBody EPoleBrand ePoleBrand) {
        return service.create(ePoleBrand);
    }

    @GetMapping(value = "/{brandId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final EPoleBrand getEPoleBrandById(
            final @EPoleBrandInPath EPoleBrand ePoleBrand) {
        return ePoleBrand;
    }

    @PutMapping(value = "/{brandId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final EPoleBrand updateEPoleBrandById(
            final @EPoleBrandInPath EPoleBrand ePoleBrand,
            final @RequestBody EPoleBrand updated) {
        return service.update(ePoleBrand, updated);
    }

    @DeleteMapping(value = "/{brandId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public final Boolean deleteEPoleBrandById(
            final @EPoleBrandInPath EPoleBrand ePoleBrand) {
        return service.delete(ePoleBrand);
    }

}
