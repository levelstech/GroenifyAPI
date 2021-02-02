package com.groenify.api.portable.epole;

import com.groenify.api.database.methods.epole.EPoleBrandMethods;
import com.groenify.api.database.model.epole.EPoleBrand;
import com.groenify.api.database.service.epole.EPoleBrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EPoleBrandPortable {
    private static final Logger L =
            LoggerFactory.getLogger(EPolePortable.class);

    private final EPoleBrandService service;

    public EPoleBrandPortable(final EPoleBrandService var1) {
        this.service = var1;
    }

    private EPoleBrand storeBrand(
            final EPoleBrandMethods methods) {

        L.debug("EPoleBrand not found with name = {}, storing in database",
                methods.getBrandName());
        return service.create(methods);
    }

    public final EPoleBrand determineBrand(final EPoleBrandMethods methods) {

        final Optional<EPoleBrand> opt =
                service.getByName(methods.getBrandName());
        return opt.orElseGet(() -> storeBrand(methods));
    }
}
