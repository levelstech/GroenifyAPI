package com.groenify.api.database.repository.factor;

import com.groenify.api.database.model.factor.Factor;
import com.groenify.api.database.model.factor.FactorType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FactorRepository extends CrudRepository<Factor, Long> {

    List<Factor> findAllByType(FactorType type);

    Boolean existsByNameIgnoreCase(String var);

    Boolean existsByNameContaining(String var);

    Optional<Factor> findByNameIgnoreCase(String name);
}
