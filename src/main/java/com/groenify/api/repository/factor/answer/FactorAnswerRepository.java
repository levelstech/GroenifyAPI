package com.groenify.api.repository.factor.answer;

import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.answer.FactorAnswer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FactorAnswerRepository
        extends CrudRepository<FactorAnswer, Long> {

    List<FactorAnswer> findAllByFactor(Factor factor);
}
