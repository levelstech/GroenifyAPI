package com.groenify.api.service.factor.answer;

import com.groenify.api.database.company.Company;
import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.answer.FactorAnswer;
import com.groenify.api.repository.factor.answer.FactorAnswerRepository;
import com.groenify.api.util.ListUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FactorAnswerService {

    private final FactorAnswerRepository repository;

    public FactorAnswerService(final FactorAnswerRepository repository) {
        this.repository = repository;
    }

    public List<FactorAnswer> getAll() {
        final Iterable<FactorAnswer> allAnswersInIter = repository.findAll();
        return ListUtil.iterableToList(allAnswersInIter);
    }

    public List<FactorAnswer> getAllFromFactor(final Factor factor) {
        return repository.findAllByFactor(factor);
    }
}
