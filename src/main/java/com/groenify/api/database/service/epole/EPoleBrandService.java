package com.groenify.api.database.service.epole;

import com.groenify.api.database.methods.epole.EPoleBrandMethods;
import com.groenify.api.database.model.epole.EPoleBrand;
import com.groenify.api.database.repository.epole.EPoleBrandRepository;
import com.groenify.api.rest.epole.__model.EPoleBrandReqMo;
import com.groenify.api.util.ListUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EPoleBrandService {

    private final EPoleBrandRepository repository;

    public EPoleBrandService(final EPoleBrandRepository var) {
        this.repository = var;
    }

    public final EPoleBrandRepository getRepository() {
        return repository;
    }

    public final List<EPoleBrand> getAll() {
        final Iterable<EPoleBrand> allBrandsInIter = repository.findAll();
        return ListUtil.iterableToList(allBrandsInIter);
    }

    public Optional<EPoleBrand> getByName(final String name) {
        return repository.findByNameIgnoreCase(name);
    }


    public final EPoleBrand create(final EPoleBrandMethods body) {
        final EPoleBrand brand = EPoleBrand.ofMethods(body);
        return repository.save(brand);
    }

    public final EPoleBrand update(
            final EPoleBrand brand,
            final EPoleBrandReqMo body) {
        return repository.save(brand.update(body));
    }

    public final Boolean delete(final EPoleBrand brand) {
        final Long id = brand.getId();
        repository.delete(brand);
        return !repository.existsById(id);
    }
}
