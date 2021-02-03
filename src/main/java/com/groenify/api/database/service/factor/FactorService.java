package com.groenify.api.database.service.factor;

import com.groenify.api.database.methods.factor.FactorMethodsWithType;
import com.groenify.api.database.model.factor.Factor;
import com.groenify.api.database.methods.factor.FactorMethods;
import com.groenify.api.database.model.factor.FactorType;
import com.groenify.api.database.model.factor.FactorTypeEnum;
import com.groenify.api.database.repository.factor.FactorRepository;
import com.groenify.api.rest.factor.__model.FactorReqMo;
import com.groenify.api.util.ListUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public final Optional<Factor> getByName(final String name) {
        return repository.findByNameIgnoreCase(name);
    }

    public final Factor create(
            final FactorType type,
            final FactorMethods body) {

        final Factor factor = Factor.ofMethods(type, body);
        return repository.save(factor);
    }

    public final Factor create(final FactorMethodsWithType body) {
        final FactorTypeEnum typeEnum =
                FactorTypeEnum.valueOfFactorOfId(body.getFactorType());
        if (typeEnum == null) return null;

        return create(typeEnum.getMappedTo(), body);
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
