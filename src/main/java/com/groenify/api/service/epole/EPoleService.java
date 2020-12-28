package com.groenify.api.service.epole;

import com.groenify.api.database.epole.EPole;
import com.groenify.api.database.epole.EPoleBrand;
import com.groenify.api.repository.epole.EPoleBrandRepository;
import com.groenify.api.repository.epole.EPoleRepository;
import com.groenify.api.util.ListUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EPoleService {

    private final EPoleRepository repository;

    public EPoleService(final EPoleRepository repository) {
        this.repository = repository;
    }

    public EPoleRepository getRepository() {
        return repository;
    }

    public List<EPole> getAll() {
        final Iterable<EPole> allBrandsInIter = repository.findAll();
        return ListUtil.iterableToList(allBrandsInIter);
    }

    public EPole create(final EPole brand) {
        brand.setId(null);
        return repository.save(brand);
    }

    public EPole update(
            final EPole original,
            final EPole updated) {
        return repository.save(original.update(updated));
    }

    public Boolean delete(final EPole brand) {
        final Long id = brand.getId();
        repository.delete(brand);
        return !repository.existsById(id);
    }
}
