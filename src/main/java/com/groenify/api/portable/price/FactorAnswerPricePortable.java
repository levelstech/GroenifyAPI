package com.groenify.api.portable.price;

import com.groenify.api.database.methods.price.FactorAnswerPriceMethods;
import com.groenify.api.database.model.company.CompanyEPole;
import com.groenify.api.database.model.factor.answer.FactorAnswer;
import com.groenify.api.database.model.price.FactorAnswerPrice;
import com.groenify.api.database.service.price.FactorAnswerPriceService;
import com.groenify.api.portable.company.CompanyEPolePortable;
import com.groenify.api.portable.price.__model.FactorAnswerPriceCSV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FactorAnswerPricePortable {


    private static final Logger L =
            LoggerFactory.getLogger(FactorAnswerPricePortable.class);
    private final CompanyEPolePortable companyEPolePortable;
    private final FactorAnswerPortable factorAnswerPortable;

    private final FactorAnswerPriceService service;

    public FactorAnswerPricePortable(
            final CompanyEPolePortable var1,
            final FactorAnswerPortable var2,
            final FactorAnswerPriceService var3) {
        this.companyEPolePortable = var1;
        this.factorAnswerPortable = var2;
        this.service = var3;
    }

    private FactorAnswerPrice storeFactorAnswerPrice(
            final CompanyEPole ePole,
            final FactorAnswer answer,
            final FactorAnswerPriceMethods methods) {

        L.debug("{} not found with FactorAnswer(id={}) and price = {}",
                FactorAnswerPrice.class, answer.getId(),
                methods.getFactorAnswerPrice());
        return service.create(ePole, answer, methods);
    }

    public final FactorAnswerPrice getOrCreateFactorAnswerPriceFromCSV(
            final FactorAnswerPriceCSV priceCSV) {

        final CompanyEPole ePole = companyEPolePortable.
                getOrCreateCompanyEPoleFromMethods(priceCSV);
        final FactorAnswer answer = factorAnswerPortable.
                getOrCreateFactorAnswerFromMethods(priceCSV);

        final Optional<FactorAnswerPrice> opt = service.
                getFromCompanyEPoleAndFactorAnswer(ePole, answer);
        return opt.orElseGet(() ->
                storeFactorAnswerPrice(ePole, answer, priceCSV));
    }

}
