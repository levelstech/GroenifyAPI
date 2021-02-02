package com.groenify.api.database.service.company;

import com.groenify.api.database.methods.company.CompanyEPoleMethods;
import com.groenify.api.database.model.company.Company;
import com.groenify.api.database.model.company.CompanyEPole;
import com.groenify.api.database.model.epole.EPole;
import com.groenify.api.database.repository.company.CompanyEPoleRepository;
import com.groenify.api.util.ListUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyEPoleService {
    private final CompanyEPoleRepository repository;

    public CompanyEPoleService(final CompanyEPoleRepository var) {
        this.repository = var;
    }

    public final List<CompanyEPole> getAll() {
        final Iterable<CompanyEPole> allCompanyEPolesInIter =
                repository.findAll();
        return ListUtil.iterableToList(allCompanyEPolesInIter);
    }

    public final List<CompanyEPole> getAllFromCompany(final Company company) {
        return repository.findAllByCompany(company);
    }

    public final Optional<CompanyEPole> getAllFromCompanyWithEPole(
            final Company company,
            final EPole ePole) {
        return repository.findAllByCompanyAndPole(company, ePole);
    }

    public final CompanyEPole create(
            final Company company,
            final EPole ePole,
            final CompanyEPoleMethods body) {
        final CompanyEPole companyEPole =
                CompanyEPole.ofReqMo(company, ePole, body);
        return repository.save(companyEPole);
    }

    public final CompanyEPole update(
            final CompanyEPole companyEPole,
            final CompanyEPoleMethods body) {
        return repository.save(companyEPole.update(body));
    }

    public final Boolean delete(final CompanyEPole companyEPole) {
        final Long id = companyEPole.getId();
        repository.delete(companyEPole);
        return !repository.existsById(id);
    }

}
