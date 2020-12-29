package com.groenify.api.service.epole;

import com.groenify.api.database.epole.EPole;
import com.groenify.api.database.epole.EPoleBrand;
import com.groenify.api.repository.epole.EPoleBrandRepository;
import com.groenify.api.repository.epole.EPoleRepository;
import com.groenify.api.rest.epole.__model.EPoleReqMo;
import com.groenify.api.rest.epole.__model.EPoleResMo;
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

    public EPole create(final EPoleBrand brand, final EPoleReqMo body) {
        final EPole pole = EPole.ofReqMo(brand, body);
        return repository.save(pole);
    }

    public EPole update(
            final EPole pole,
            final EPoleReqMo body) {
        return repository.save(pole.update(body));
    }

    public Boolean delete(final EPole pole) {
        final Long id = pole.getId();
        repository.delete(pole);
        return !repository.existsById(id);
    }
}
