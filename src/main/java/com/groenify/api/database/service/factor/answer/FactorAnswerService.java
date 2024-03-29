package com.groenify.api.database.service.factor.answer;

import com.groenify.api.database.model.factor.Factor;
import com.groenify.api.database.model.factor.answer.FactorAnswer;
import com.groenify.api.database.repository.factor.answer.FactorAnswerRepository;
import com.groenify.api.rest.factor.answer.__model.FactorAnswerReqMo;
import com.groenify.api.util.ListUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
        final FactorAnswer answer = body.mapToDatabaseModel(factor);
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

    public Optional<FactorAnswer> getAllFromFactorAndAnswer(
            final Factor factor, final String factorAnswer) {
        return getAllFromFactor(factor)
                .stream().filter(x -> x.hasStringedAnswer(factorAnswer))
                .findFirst();
    }
}
