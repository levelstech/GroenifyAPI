package com.groenify.api.service.factor.answer;

import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.answer.FactorAnswer;
import com.groenify.api.repository.factor.answer.FactorAnswerRepository;
import com.groenify.api.rest.factor.answer.__model.FactorAnswerReqMo;
import com.groenify.api.util.ListUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FactorAnswerService {

    private final FactorAnswerRepository repository;

    public FactorAnswerService(final FactorAnswerRepository var) {
        this.repository = var;
    }

    public final List<FactorAnswer> getAll() {
        final Iterable<FactorAnswer> allAnswersInIter = repository.findAll();
        return ListUtil.iterableToList(allAnswersInIter);
    }

    public final List<FactorAnswer> getAllFromFactor(final Factor factor) {
        return repository.findAllByFactor(factor);
    }

    public final FactorAnswer create(
            final Factor factor,
            final FactorAnswerReqMo body) {
        final FactorAnswer answer = body.toToDatabaseModel(factor);
        return repository.save(answer);
    }

    public final FactorAnswer update(
            final FactorAnswer answer,
            final FactorAnswerReqMo body) {
        return repository.save(answer.update(body));
    }

    public final Boolean delete(final FactorAnswer answer) {
        final Long id = answer.getId();
        repository.delete(answer);
        return !repository.existsById(id);
    }

}
