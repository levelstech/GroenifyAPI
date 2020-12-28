package com.groenify.api.repository.company;

import com.groenify.api.database.company.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, Long> {

    Boolean existsByNameIgnoreCase(String var);
}
