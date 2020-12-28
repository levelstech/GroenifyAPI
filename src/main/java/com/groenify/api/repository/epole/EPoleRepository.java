package com.groenify.api.repository.epole;

import com.groenify.api.database.epole.EPole;
import com.groenify.api.database.epole.EPoleBrand;
import org.springframework.data.repository.CrudRepository;

public interface EPoleRepository extends CrudRepository<EPole, Long> {

    Boolean existsByTypeIgnoreCase(String var);
}
