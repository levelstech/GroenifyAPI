package com.groenify.api.portable.price;

import com.groenify.api.database.methods.factor.FactorMethodsWithType;
import com.groenify.api.database.model.factor.Factor;
import com.groenify.api.database.model.factor.FactorTypeEnum;
import com.groenify.api.database.model.factor.answer.FactorAnswer;
import com.groenify.api.database.service.factor.answer.FactorAnswerService;
import com.groenify.api.portable.factor.FactorPortable;
import com.groenify.api.portable.price.__model.FactorAnswerMethods;
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
            final FactorAnswerMethods answerMethods,
            final FactorTypeEnum typeEnum) {
        final Class<? extends FactorAnswerReqMo> requestModelClazz =
                FactorAnswerReqMo.getClassOf(typeEnum);
        final String json = MapperUtil.writeJsonFromObject(
                answerMethods, FactorAnswerMethods.class);
        return MapperUtil.readObjectFromJsonString(json, requestModelClazz);
    }

    private FactorAnswer storeFactorAnswer(
            final Factor factor,
            final FactorAnswerMethods methods) {
        final FactorTypeEnum typeEnum = factor.getType().getAsEnum();
        final FactorAnswerReqMo reqMo = parseReqMoOfEnum(methods, typeEnum);

        L.debug("FactorAnswer not found with Factor(id={}) and answer = {}",
                factor.getId(), methods.getFactorAnswer());
        return service.create(factor, reqMo);
    }

    public final <T extends FactorMethodsWithType & FactorAnswerMethods>
    FactorAnswer getOrCreateFactorAnswerFromMethods(
            final T methods) {
        return getOrCreateFactorAnswerFromMethods(methods, methods);
    }

    public final FactorAnswer getOrCreateFactorAnswerFromMethods(
            final FactorAnswerMethods answerMethods,
            final FactorMethodsWithType factorMethods) {

        final Factor factor = factorPortable.
                getOrCreateFactorFromMethodsWithType(factorMethods);

        final Optional<FactorAnswer> opt = service.getAllFromFactorAndAnswer(
                factor, answerMethods.getFactorAnswer());
        return opt.orElseGet(() -> storeFactorAnswer(factor, answerMethods));
    }

}
