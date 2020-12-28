package com.groenify.api.repository;

import com.groenify.api.database.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, Long> {
}
