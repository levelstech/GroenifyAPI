package com.groenify.api.repository.epole;

import com.groenify.api.database.epole.EPole;
import com.groenify.api.database.epole.EPoleBrand;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EPoleRepository extends CrudRepository<EPole, Long> {

    Boolean existsByTypeIgnoreCase(String var);

    List<EPole> findAllByBrand(EPoleBrand var);
}
