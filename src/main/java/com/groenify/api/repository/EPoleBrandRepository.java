package com.groenify.api.repository;

import com.groenify.api.database.EPoleBrand;
import org.springframework.data.repository.CrudRepository;

public interface EPoleBrandRepository extends CrudRepository<EPoleBrand, Long> {

    Boolean existsByNameIgnoreCase(String var);
}
