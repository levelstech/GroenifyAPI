package com.groenify.api.service.factor;

import com.groenify.api.database.epole.EPoleBrand;
import com.groenify.api.database.factor.FactorType;
import com.groenify.api.repository.epole.EPoleBrandRepository;
import com.groenify.api.repository.factor.FactorTypeRepository;
import com.groenify.api.rest.epole.__model.EPoleBrandReqMo;
import com.groenify.api.rest.factor.__model.FactorTypeReqMo;
import com.groenify.api.util.ListUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactorTypeService {

    private final FactorTypeRepository repository;

    public FactorTypeService(final FactorTypeRepository repository) {
        this.repository = repository;
    }

    public FactorTypeRepository getRepository() {
        return repository;
    }

    public List<FactorType> getAll() {
        final Iterable<FactorType> allFactorTypeInIter = repository.findAll();
        return ListUtil.iterableToList(allFactorTypeInIter);
    }

    public FactorType create(final FactorTypeReqMo body) {
        final FactorType type = FactorType.ofReqMo(body);
        return repository.save(type);
    }

    public EPoleBrand update(
            final FactorType type,
            final FactorTypeReqMo body) {
        return repository.save(type.update(body));
    }

    public Boolean delete(final FactorType type) {
        final Long id = type.getId();
        repository.delete(type);
        return !repository.existsById(id);
    }
}
