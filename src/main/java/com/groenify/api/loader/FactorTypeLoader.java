package com.groenify.api.loader;

import com.groenify.api.database.model.factor.FactorType;
import com.groenify.api.database.model.factor.FactorTypeEnum;
import com.groenify.api.framework.config.LoadOrder;
import com.groenify.api.framework.config.ReadyEventLoader;
import com.groenify.api.database.repository.factor.FactorTypeRepository;
import com.groenify.api.database.service.factor.FactorTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.groenify.api.portable.factor.FactorTypePortable.determineFactorTypeFromEnum;

@Service
public class FactorTypeLoader implements ReadyEventLoader {

    private static final Logger L =
            LoggerFactory.getLogger(FactorTypeLoader.class);

    private final FactorTypeService service;

    public FactorTypeLoader(final FactorTypeService var) {
        this.service = var;
    }

    public static void loadFactorTypeEnumerators(
            final FactorTypeRepository repository) {
        final FactorTypeService service = new FactorTypeService(repository);
        loadFactorTypeEnumerators(service);
    }

    public static void loadFactorTypeEnumerators(
            final FactorTypeService service) {
        for (final FactorTypeEnum typeEnum : FactorTypeEnum.values()) {
            final FactorType factorType =
                    determineFactorTypeFromEnum(typeEnum, service);
            typeEnum.updateType(factorType);
            L.trace("FactorType(id = {}) is mapped to Enum(title = {})",
                    factorType.getId(), typeEnum.toString());
        }
    }

    @LoadOrder(1)
    @Override
    public final void loadOnReady() {
        loadFactorTypeEnumerators(service);
    }
}
