package com.groenify.api.service.factor;

import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.FactorType;
import com.groenify.api.repository.factor.FactorRepository;
import com.groenify.api.util.ListUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactorService {

    private final FactorRepository repository;

    public FactorService(final FactorRepository repository) {
        this.repository = repository;
    }

    public FactorRepository getRepository() {
        return repository;
    }

    public List<Factor> getAll() {
        final Iterable<Factor> allTypesInIter = repository.findAll();
        return ListUtil.iterableToList(allTypesInIter);
    }

    public List<Factor> getAllFromType(final FactorType type) {
        return repository.findAllByType(type);
    }

    public Factor create(final FactorType type, final FactorReqMo body) {
        final Factor factor = Factor.ofReqMo(type, body);
        return repository.save(factor);
    }

    public Factor update(
            final Factor factor,
            final FactorReqMo body) {
        return repository.save(factor.update(body));
    }

    public Boolean delete(final Factor factor) {
        final Long id = factor.getId();
        repository.delete(factor);
        return !repository.existsById(id);
    }

}
