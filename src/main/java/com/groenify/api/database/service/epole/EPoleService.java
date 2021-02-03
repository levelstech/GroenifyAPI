package com.groenify.api.database.service.epole;

import com.groenify.api.database.methods.epole.EPoleMethods;
import com.groenify.api.database.model.epole.EPole;
import com.groenify.api.database.model.epole.EPoleBrand;
import com.groenify.api.database.repository.epole.EPoleRepository;
import com.groenify.api.util.ListUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EPoleService {

    private final EPoleRepository repository;

    public EPoleService(final EPoleRepository var) {
        this.repository = var;
    }

    public final EPoleRepository getRepository() {
        return repository;
    }

    public final List<EPole> getAll() {
        final Iterable<EPole> allBrandsInIter = repository.findAll();
        return ListUtil.iterableToList(allBrandsInIter);
    }

    public final List<EPole> getAllFromBrand(final EPoleBrand brand) {
        return repository.findAllByBrand(brand);
    }

    public final Optional<EPole> getAllFromBrandAndType(
            final EPoleBrand brand,
            final String type) {
        return repository.findAllByBrandAndTypeIgnoreCase(brand, type);
    }

    public final EPole create(final EPoleBrand brand, final EPoleMethods body) {
        final EPole pole = EPole.ofMethods(brand, body);
        return repository.save(pole);
    }

    public final EPole update(final EPole pole, final EPoleMethods body) {
        return repository.save(pole.update(body));
    }

    public final Boolean delete(final EPole pole) {
        final Long id = pole.getId();
        repository.delete(pole);
        return !repository.existsById(id);
    }

}
