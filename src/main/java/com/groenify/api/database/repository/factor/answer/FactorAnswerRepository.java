package com.groenify.api.database.repository.factor.answer;

import com.groenify.api.database.model.factor.Factor;
import com.groenify.api.database.model.factor.answer.FactorAnswer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FactorAnswerRepository
        extends CrudRepository<FactorAnswer, Long> {

    List<FactorAnswer> findAllByFactor(Factor factor);
}
