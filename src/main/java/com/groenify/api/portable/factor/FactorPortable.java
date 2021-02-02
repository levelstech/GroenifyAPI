package com.groenify.api.portable.factor;

import com.groenify.api.database.methods.factor.FactorMethods;
import com.groenify.api.database.model.factor.Factor;
import com.groenify.api.database.model.factor.FactorTypeEnum;
import com.groenify.api.portable.factor.__model.FactorCSV;
import com.groenify.api.database.service.factor.FactorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FactorPortable {

    private static final Logger L =
            LoggerFactory.getLogger(FactorPortable.class);

    private final FactorService service;

    public FactorPortable(final FactorService var) {
        this.service = var;
    }

    private Factor storeFactor(
            final FactorMethods factorCSV,
            final Long type) {

        final FactorTypeEnum typeEnum =
                FactorTypeEnum.valueOfFactorOfId(type);
        if (typeEnum == null) {
            L.error("FactorEnum not found with id = {}", type);
            return null;
        }
        L.debug("Factor not found with name = {}, storing in database",
                factorCSV.getFactorName());
        return service.create(typeEnum.getMappedTo(), factorCSV);
    }

    public final Factor determineFactor(final FactorCSV factorCSV) {
        return determineFactor(factorCSV, factorCSV.getType());
    }

    public final Factor determineFactor(
            final FactorMethods methods,
            final Long type) {
        final Optional<Factor> opt = service.getByName(methods.getFactorName());
        return opt.orElseGet(() -> storeFactor(methods, type));
    }
}
