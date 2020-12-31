package com.groenify.api.service.factor;

import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.FactorType;
import com.groenify.api.repository.factor.FactorRepository;
import com.groenify.api.rest.factor.__model.FactorReqMo;
import com.groenify.api.util.ListUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactorService {

    private final FactorRepository repository;

    public FactorService(final FactorRepository var) {
        this.repository = var;
    }

    public final FactorRepository getRepository() {
        return repository;
    }

    public final List<Factor> getAll() {
        final Iterable<Factor> allTypesInIter = repository.findAll();
        return ListUtil.iterableToList(allTypesInIter);
    }

    public final List<Factor> getAllFromType(final FactorType type) {
        return repository.findAllByType(type);
    }

    public final Factor create(final FactorType type, final FactorReqMo body) {
        final Factor factor = Factor.ofReqMo(type, body);
        return repository.save(factor);
    }

    public final Factor update(
            final Factor factor,
            final FactorReqMo body) {
        return repository.save(factor.update(body));
    }

    public final Boolean delete(final Factor factor) {
        final Long id = factor.getId();
        repository.delete(factor);
        return !repository.existsById(id);
    }

}
