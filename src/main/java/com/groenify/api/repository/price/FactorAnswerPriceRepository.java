package com.groenify.api.repository.price;

import com.groenify.api.database.company.CompanyEPole;
import com.groenify.api.database.price.FactorAnswerPrice;
import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.answer.FactorAnswer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FactorAnswerPriceRepository
        extends CrudRepository<FactorAnswerPrice, Long> {

    List<FactorAnswerPrice> findAllByPole(CompanyEPole ePole);

    List<FactorAnswerPrice> findAllByPoleAndFactorAnswer(
            CompanyEPole companyEPole, FactorAnswer factor);

    @Query("SELECT FAP FROM FactorAnswerPrice FAP WHERE "
            + "FAP.pole=:s1 AND FAP.factorAnswer.factor =:s2")
    List<FactorAnswerPrice> findAllByPoleAndFactor(
            @Param("s1") CompanyEPole s1,
            @Param("s2") Factor s2);
}
