package com.groenify.api.portable.price;

import com.groenify.api.database.model.factor.Factor;
import com.groenify.api.database.model.factor.FactorTypeEnum;
import com.groenify.api.database.model.factor.answer.FactorAnswer;
import com.groenify.api.database.service.factor.answer.FactorAnswerService;
import com.groenify.api.portable.factor.FactorPortable;
import com.groenify.api.portable.price.__model.FactorAnswerMethods;
import com.groenify.api.portable.price.__model.FactorAnswerPriceCSV;
import com.groenify.api.rest.factor.answer.__model.FactorAnswerReqMo;
import com.groenify.api.util.MapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FactorAnswerPortable {

    private static final Logger L =
            LoggerFactory.getLogger(FactorAnswerPortable.class);
    private final FactorPortable factorPortable;
    private final FactorAnswerService service;


    public FactorAnswerPortable(
            final FactorPortable var1,
            final FactorAnswerService var2) {
        this.factorPortable = var1;
        this.service = var2;
    }

    private static FactorAnswerReqMo parseReqMoOfEnum(
            final FactorAnswerMethods answer,
            final FactorTypeEnum typeEnum) {
        final Class<? extends FactorAnswerReqMo> requestModelClazz =
                FactorAnswerReqMo.getClassOf(typeEnum);
        final String json = MapperUtil.writeJsonFromObject(
                answer, FactorAnswerMethods.class);
        return MapperUtil.readObjectFromJsonString(json, requestModelClazz);
    }

    private FactorAnswer storeFactorAnswer(
            final Factor factor,
            final FactorAnswerMethods answerCSV) {
        final FactorTypeEnum typeEnum = factor.getType().getAsEnum();
        final FactorAnswerReqMo reqMo = parseReqMoOfEnum(answerCSV, typeEnum);

        L.debug("FactorAnswer not found with Factor(id={}) and answer = {}",
                factor.getId(), answerCSV.getFactorAnswer());
        return service.create(factor, reqMo);
    }

    public final FactorAnswer determineFactorAnswer(
            final FactorAnswerPriceCSV priceCSV) {

        final Factor factor = factorPortable.determineFactor(
                priceCSV, priceCSV.getFactorType());

        final Optional<FactorAnswer> opt = service.getAllFromFactorAndAnswer(
                factor, priceCSV.getFactorAnswer());
        return opt.orElseGet(() -> storeFactorAnswer(factor, priceCSV));
    }

}
