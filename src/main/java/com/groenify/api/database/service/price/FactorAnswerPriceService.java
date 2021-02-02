package com.groenify.api.database.service.price;

import com.groenify.api.database.methods.price.FactorAnswerPriceMethods;
import com.groenify.api.database.model.company.CompanyEPole;
import com.groenify.api.database.model.price.FactorAnswerPrice;
import com.groenify.api.database.model.factor.Factor;
import com.groenify.api.database.model.factor.answer.FactorAnswer;
import com.groenify.api.database.repository.price.FactorAnswerPriceRepository;
import com.groenify.api.util.ListUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FactorAnswerPriceService {

    private final FactorAnswerPriceRepository repository;

    public FactorAnswerPriceService(
            final FactorAnswerPriceRepository var) {
        this.repository = var;
    }

    public final FactorAnswerPriceRepository getRepository() {
        return repository;
    }

    public final List<FactorAnswerPrice> getAll() {
        final Iterable<FactorAnswerPrice> allAnswerInIter =
                repository.findAll();
        return ListUtil.iterableToList(allAnswerInIter);
    }

    public final List<FactorAnswerPrice> getAllFromCompanyEPole(
            final CompanyEPole companyEPole) {
        return repository.findAllByPole(companyEPole);
    }

    public final Optional<FactorAnswerPrice> getFromCompanyEPoleAndFactorAnswer(
            final CompanyEPole companyEPole, final FactorAnswer answer) {
        return repository.findAllByPoleAndFactorAnswer(companyEPole, answer);
    }

    public final List<FactorAnswerPrice> getAllFromCompanyEPoleAndFactor(
            final CompanyEPole companyEPole, final Factor factor) {
        return repository.findAllByPoleAndFactor(companyEPole, factor);
    }

    public final FactorAnswerPrice create(
            final CompanyEPole companyEPole,
            final FactorAnswer factorAnswer,
            final FactorAnswerPriceMethods body) {
        final FactorAnswerPrice price =
                FactorAnswerPrice.ofReqMo(companyEPole, factorAnswer, body);
        return repository.save(price);
    }

    public final FactorAnswerPrice update(
            final FactorAnswerPrice price,
            final FactorAnswerPriceMethods body) {
        return repository.save(price.update(body));
    }

    public final Boolean delete(final FactorAnswerPrice price) {
        final Long id = price.getId();
        repository.delete(price);
        return !repository.existsById(id);
    }
}
