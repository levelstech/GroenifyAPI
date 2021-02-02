package com.groenify.api.portable.epole;

import com.groenify.api.database.methods.epole.EPoleMethods;
import com.groenify.api.database.model.epole.EPole;
import com.groenify.api.database.model.epole.EPoleBrand;
import com.groenify.api.database.service.epole.EPoleService;
import com.groenify.api.portable.price.__model.FactorAnswerPriceCSV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EPolePortable {
    private static final Logger L =
            LoggerFactory.getLogger(EPolePortable.class);

    private final EPoleBrandPortable ePoleBrandPortable;

    private final EPoleService service;

    public EPolePortable(
            final EPoleBrandPortable var1,
            final EPoleService var2) {
        this.ePoleBrandPortable = var1;
        this.service = var2;
    }

    private EPole storeEPole(
            final EPoleBrand ePoleBrand,
            final EPoleMethods methods) {

        L.debug("EPole not found with EPoleBrand(id={}) and type = {}",
                ePoleBrand.getId(), methods.getEPoleType());
        return service.create(ePoleBrand, methods);
    }

    public final EPole determineEPole(final FactorAnswerPriceCSV priceCSV) {

        final EPoleBrand brand =
                ePoleBrandPortable.determineBrand(priceCSV);

        final Optional<EPole> opt =
                service.getAllFromBrandAndType(brand, priceCSV.getEPoleType());
        return opt.orElseGet(() -> storeEPole(brand, priceCSV));
    }
}
