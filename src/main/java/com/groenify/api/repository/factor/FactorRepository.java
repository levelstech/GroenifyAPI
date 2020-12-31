package com.groenify.api.repository.factor;

import com.groenify.api.database.factor.Factor;
import org.springframework.data.repository.CrudRepository;

public interface FactorRepository extends CrudRepository<Factor, Long> {
}
