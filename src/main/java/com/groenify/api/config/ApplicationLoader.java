package com.groenify.api.config;

import com.groenify.api.database.factor.FactorType;
import com.groenify.api.database.factor.FactorTypeEnum;
import com.groenify.api.framework.annotation.resolver.AbstractMethodArgumentResolver;
import com.groenify.api.repository.factor.FactorTypeRepository;
import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ApplicationLoader
        implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger L =
            LoggerFactory.getLogger(ApplicationLoader.class);

    private final FactorTypeRepository repository;

    public ApplicationLoader(final FactorTypeRepository var) {
        this.repository = var;
    }

    private FactorType storeFactorTypeFromEnum(final FactorTypeEnum typeEnum) {
        L.debug("FactorTypeEnum not found with title = {}, storing in database",
                typeEnum.toString());
        return repository.save(FactorType.ofFactorTypeEnum(typeEnum));
    }

    private FactorType determineFactorTypeFromEnum(
            final FactorTypeEnum typeEnum) {
        final Optional<FactorType> opt =
                repository.findByNameIgnoreCase(typeEnum.toString());
        return opt.orElseGet(() -> storeFactorTypeFromEnum(typeEnum));
    }

    @Override
    public void onApplicationEvent(final @NotNull ApplicationReadyEvent event) {
        for (final FactorTypeEnum typeEnum : FactorTypeEnum.values()) {
            final FactorType factorType =
                    determineFactorTypeFromEnum(typeEnum);
            typeEnum.setMappedTo(factorType);
            L.trace("FactorType(id = {}) is mapped to Enum(title = {})",
                    factorType.getId(), typeEnum.toString());
        }
    }


}
