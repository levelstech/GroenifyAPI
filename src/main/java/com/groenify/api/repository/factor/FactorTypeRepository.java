package com.groenify.api.repository.factor;

import com.groenify.api.database.factor.FactorType;
import org.springframework.data.repository.CrudRepository;

public interface FactorTypeRepository extends CrudRepository<FactorType, Long> {

    Boolean existsByNameIgnoreCase(String var);
}
