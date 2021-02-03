package com.groenify.api.database.repository.company;

import com.groenify.api.database.model.company.Company;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CompanyRepository extends CrudRepository<Company, Long> {

    Boolean existsByNameIgnoreCase(String var);

    Optional<Company> findByNameIgnoreCase(String var);
}
