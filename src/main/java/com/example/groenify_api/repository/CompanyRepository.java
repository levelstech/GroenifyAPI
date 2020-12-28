package com.example.groenify_api.repository;

import com.example.groenify_api.database.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, Long> {
}
