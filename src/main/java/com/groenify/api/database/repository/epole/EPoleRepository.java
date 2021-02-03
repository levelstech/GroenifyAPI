package com.groenify.api.database.repository.epole;

import com.groenify.api.database.model.epole.EPole;
import com.groenify.api.database.model.epole.EPoleBrand;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EPoleRepository extends CrudRepository<EPole, Long> {

    Boolean existsByTypeIgnoreCase(String var);

    List<EPole> findAllByBrand(EPoleBrand var);

    Optional<EPole> findAllByBrandAndTypeIgnoreCase(EPoleBrand v1, String v2);
}
