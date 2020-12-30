package com.groenify.api.service.company;

import com.groenify.api.database.company.Company;
import com.groenify.api.database.company.CompanyEPole;
import com.groenify.api.database.epole.EPole;
import com.groenify.api.repository.company.CompanyEPoleRepository;
import com.groenify.api.rest.company.__model.CompanyEPoleReqMo;
import com.groenify.api.util.ListUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyEPoleService {
    private final CompanyEPoleRepository repository;

    public CompanyEPoleService(final CompanyEPoleRepository repository) {
        this.repository = repository;
    }

    public List<CompanyEPole> getAll() {
        final Iterable<CompanyEPole> allCompanyEPolesInIter =
                repository.findAll();
        return ListUtil.iterableToList(allCompanyEPolesInIter);
    }

    public List<CompanyEPole> getAllFromCompany(final Company company) {
        return repository.findAllByCompany(company);
    }

    public CompanyEPole create(
            final Company company,
            final EPole ePole,
            final CompanyEPoleReqMo body) {
        final CompanyEPole companyEPole =
                CompanyEPole.ofReqMo(company, ePole, body);
        return repository.save(companyEPole);
    }

    public CompanyEPole update(
            final CompanyEPole companyEPole,
            final CompanyEPoleReqMo body) {
        return repository.save(companyEPole.update(body));
    }

    public Boolean delete(final CompanyEPole companyEPole) {
        final Long id = companyEPole.getId();
        repository.delete(companyEPole);
        return !repository.existsById(id);
    }

}
