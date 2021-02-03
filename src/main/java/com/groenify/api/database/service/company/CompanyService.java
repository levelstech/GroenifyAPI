package com.groenify.api.database.service.company;

import com.groenify.api.database.model.company.Company;
import com.groenify.api.database.methods.company.CompanyMethods;
import com.groenify.api.database.repository.company.CompanyRepository;
import com.groenify.api.util.ListUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository repository;

    public CompanyService(final CompanyRepository var) {
        this.repository = var;
    }

    public final CompanyRepository getRepository() {
        return repository;
    }

    public final List<Company> getAll() {
        final Iterable<Company> allCompaniesInIter = repository.findAll();
        return ListUtil.iterableToList(allCompaniesInIter);
    }

    public final Optional<Company> getByName(final String name) {
        return repository.findByNameIgnoreCase(name);
    }

    public final Company create(final CompanyMethods body) {
        final Company company = Company.ofMethods(body);
        return repository.save(company);
    }

    public final Company update(
            final Company company,
            final CompanyMethods body) {
        return repository.save(company.update(body));
    }

    public final Boolean delete(final Company company) {
        final Long id = company.getId();
        repository.delete(company);
        return !repository.existsById(id);
    }
}
