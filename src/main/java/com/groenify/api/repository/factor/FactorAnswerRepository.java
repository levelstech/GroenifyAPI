package com.groenify.api.repository.factor;

import com.groenify.api.database.factor.answer.FactorAnswer;
import org.springframework.data.repository.CrudRepository;

public interface FactorAnswerRepository
        extends CrudRepository<FactorAnswer, Long> {
}
