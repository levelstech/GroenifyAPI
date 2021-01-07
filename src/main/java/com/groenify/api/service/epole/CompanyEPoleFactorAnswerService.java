package com.groenify.api.service.epole;

import com.groenify.api.database.company.CompanyEPoleFactorAnswer;
import com.groenify.api.repository.company.CompanyEPoleFactorAnswerRepository;
import com.groenify.api.util.ListUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyEPoleFactorAnswerService {

    private final CompanyEPoleFactorAnswerRepository repository;

    public CompanyEPoleFactorAnswerService(
            final CompanyEPoleFactorAnswerRepository var) {
        this.repository = var;
    }

    public final CompanyEPoleFactorAnswerRepository getRepository() {
        return repository;
    }

    public final List<CompanyEPoleFactorAnswer> getAll() {
        final Iterable<CompanyEPoleFactorAnswer> allAnswerInIter =
                repository.findAll();
        return ListUtil.iterableToList(allAnswerInIter);
    }

}
