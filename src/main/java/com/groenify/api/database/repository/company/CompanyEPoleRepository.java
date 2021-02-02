package com.groenify.api.database.repository.company;

import com.groenify.api.database.model.company.Company;
import com.groenify.api.database.model.company.CompanyEPole;
import com.groenify.api.database.model.epole.EPole;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyEPoleRepository
        extends CrudRepository<CompanyEPole, Long> {

    List<CompanyEPole> findAllByCompany(Company var);

    Optional<CompanyEPole> findAllByCompanyAndPole(Company var1, EPole var2);
}
