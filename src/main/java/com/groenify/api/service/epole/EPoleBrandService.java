package com.groenify.api.service.epole;

import com.groenify.api.database.epole.EPoleBrand;
import com.groenify.api.repository.epole.EPoleBrandRepository;
import com.groenify.api.util.ListUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EPoleBrandService {

    private final EPoleBrandRepository repository;

    public EPoleBrandService(final EPoleBrandRepository repository) {
        this.repository = repository;
    }

    public EPoleBrandRepository getRepository() {
        return repository;
    }

    public List<EPoleBrand> getAll() {
        final Iterable<EPoleBrand> allBrandsInIter = repository.findAll();
        return ListUtil.iterableToList(allBrandsInIter);
    }

    public EPoleBrand create(final EPoleBrand brand) {
        brand.setId(null);
        return repository.save(brand);
    }

    public EPoleBrand update(
            final EPoleBrand original,
            final EPoleBrand updated) {
        return repository.save(original.update(updated));
    }

    public Boolean delete(final EPoleBrand brand) {
        final Long id = brand.getId();
        repository.delete(brand);
        return !repository.existsById(id);
    }
}
