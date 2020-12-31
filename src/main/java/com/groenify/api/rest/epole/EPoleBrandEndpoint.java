package com.groenify.api.rest.epole;

import com.groenify.api.database.epole.EPoleBrand;
import com.groenify.api.framework.annotation.EPoleBrandInPath;
import com.groenify.api.rest.epole.__model.EPoleBrandReqMo;
import com.groenify.api.rest.epole.__model.EPoleBrandResMo;
import com.groenify.api.service.epole.EPoleBrandService;
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

    public EPoleBrandEndpoint(final EPoleBrandService var) {
        this.service = var;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<EPoleBrandResMo> getAllEPoleBrands() {
        final List<EPoleBrand> list = service.getAll();
        return EPoleBrandResMo.mapEPoleBrandToResMoList(list);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public final EPoleBrandResMo createEPoleBrand(
            final @Valid @RequestBody EPoleBrandReqMo body) {
        final EPoleBrand brand = service.create(body);
        return EPoleBrandResMo.mapEPoleBrandToResMo(brand);
    }

    @GetMapping(value = "/{brandId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final EPoleBrandResMo getEPoleBrandById(
            final @EPoleBrandInPath EPoleBrand brand) {
        return EPoleBrandResMo.mapEPoleBrandToResMo(brand);
    }

    @PutMapping(value = "/{brandId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final EPoleBrandResMo updateEPoleBrandById(
            final @EPoleBrandInPath EPoleBrand brand,
            final @RequestBody EPoleBrandReqMo body) {
        final EPoleBrand newBrand = service.update(brand, body);
        return EPoleBrandResMo.mapEPoleBrandToResMo(newBrand);
    }

    @DeleteMapping(value = "/{brandId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public final Boolean deleteEPoleBrandById(
            final @EPoleBrandInPath EPoleBrand brand) {
        return service.delete(brand);
    }

}
