package com.groenify.api.repository.epole;

import com.groenify.api.database.epole.EPoleBrand;
import org.springframework.data.repository.CrudRepository;

public interface EPoleBrandRepository extends CrudRepository<EPoleBrand, Long> {

    Boolean existsByNameIgnoreCase(String var);
}
