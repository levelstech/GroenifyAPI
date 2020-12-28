package com.groenify.api.service;

import com.groenify.api.database.EPoleBrand;
import com.groenify.api.repository.CompanyRepository;
import com.groenify.api.repository.EPoleBrandRepository;
import com.groenify.api.util.ListUtil;
import com.groenify.api.util.LongUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public EPoleBrand create(final EPoleBrand ePoleBrand) {
        ePoleBrand.setId(null);
        return repository.save(ePoleBrand);
    }

    public EPoleBrand update(final EPoleBrand updated) {
        return repository.save(updated);
    }

    public Boolean delete(final EPoleBrand brand) {
        final Long id = brand.getId();
        repository.delete(brand);
        return !repository.existsById(id);
    }
}
