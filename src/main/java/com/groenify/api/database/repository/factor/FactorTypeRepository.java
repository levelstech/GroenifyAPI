package com.groenify.api.database.repository.factor;

import com.groenify.api.database.model.factor.FactorType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FactorTypeRepository extends CrudRepository<FactorType, Long> {

    Boolean existsByNameIgnoreCase(String var);

    Optional<FactorType> findByNameIgnoreCase(String var);
}
