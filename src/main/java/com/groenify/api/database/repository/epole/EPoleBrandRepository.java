package com.groenify.api.database.repository.epole;

import com.groenify.api.database.model.epole.EPoleBrand;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EPoleBrandRepository extends CrudRepository<EPoleBrand, Long> {

    Boolean existsByNameIgnoreCase(String var);

    Optional<EPoleBrand> findByNameIgnoreCase(String var);
}
