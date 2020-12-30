package com.groenify.api.repository.company;

import com.groenify.api.database.company.Company;
import com.groenify.api.database.company.CompanyEPole;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompanyEPoleRepository
        extends CrudRepository<CompanyEPole, Long> {

    List<CompanyEPole> findAllByCompany(Company var);
}
