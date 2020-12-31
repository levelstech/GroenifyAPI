package com.groenify.api.repository.factor;

import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.FactorType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FactorRepository extends CrudRepository<Factor, Long> {

    List<Factor> findAllByType(FactorType type);

    Boolean existsByNameIgnoreCase(String var);
}
