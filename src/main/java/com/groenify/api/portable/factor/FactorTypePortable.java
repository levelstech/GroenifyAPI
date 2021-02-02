package com.groenify.api.portable.factor;

import com.groenify.api.database.model.factor.FactorType;
import com.groenify.api.database.model.factor.FactorTypeEnum;
import com.groenify.api.database.service.factor.FactorTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public final class FactorTypePortable {

    private static final Logger L =
            LoggerFactory.getLogger(FactorTypePortable.class);

    private FactorTypePortable() {
    }

    private static FactorType storeFactorTypeFromEnum(
            final FactorTypeEnum typeEnum,
            final FactorTypeService service) {

        L.debug("FactorTypeEnum not found with title = {}, storing in database",
                typeEnum.toString());
        return service.create(typeEnum);
    }

    public static FactorType determineFactorTypeFromEnum(
            final FactorTypeEnum typeEnum,
            final FactorTypeService service) {
        final Optional<FactorType> opt =
                service.getByName(typeEnum.toString());
        return opt.orElseGet(
                () -> storeFactorTypeFromEnum(typeEnum, service));
    }
}
