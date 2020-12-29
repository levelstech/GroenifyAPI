package com.groenify.api.service.company;

import com.groenify.api.database.company.Company;
import com.groenify.api.repository.company.CompanyRepository;
import com.groenify.api.rest.company.__model.CompanyReqMo;
import com.groenify.api.util.ListUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository repository;

    public CompanyService(final CompanyRepository repository) {
        this.repository = repository;
    }

    public CompanyRepository getRepository() {
        return repository;
    }

    public List<Company> getAll() {
        final Iterable<Company> allCompaniesInIter = repository.findAll();
        return ListUtil.iterableToList(allCompaniesInIter);
    }

    public Company create(final CompanyReqMo body) {
        final Company company = Company.ofReqMo(body);
        return repository.save(company);
    }

    public Company update(
            final Company company,
            final CompanyReqMo body) {
        return repository.save(company.update(body));
    }

    public Boolean delete(final Company company) {
        final Long id = company.getId();
        repository.delete(company);
        return !repository.existsById(id);
    }
}
