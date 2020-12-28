package com.groenify.api.rest;

import com.groenify.api.database.EPoleBrand;
import com.groenify.api.repository.EPoleBrandRepository;
import com.groenify.api.util.ListUtil;
import com.groenify.api.util.LongUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/epole_brands")
public class EPoleBrandEndpoint {

    private final EPoleBrandRepository repository;

    public EPoleBrandEndpoint(final EPoleBrandRepository repository) {
        this.repository = repository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<EPoleBrand> getAllEPoleBrands() {

        final Iterable<EPoleBrand> allBrandsInIter = repository.findAll();
        return ListUtil.iterableToList(allBrandsInIter);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<String> createEPoleBrand() {
        return List.of("Test", "test", "t");
    }

    @GetMapping(value = "/{brandId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final EPoleBrand getEPoleBrandById(
            final HttpServletResponse response,
            final @PathVariable String brandId) {
        response.setStatus(404);

        final Long id = LongUtil.parseOrDefault(brandId);
        if (id <= 0) return null;

        final Optional<EPoleBrand> optionalBrand = repository.findById(id);

        if (optionalBrand.isEmpty()) return null;

        response.setStatus(200);
        return optionalBrand.get();
    }

    @PutMapping(value = "/1",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<String> updateEPoleBrandById() {
        return List.of("Test", "test", "t");
    }

    @DeleteMapping(value = "/1",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<String> deleteEPoleBrandById() {
        return List.of("Test", "test", "t");
    }

}
