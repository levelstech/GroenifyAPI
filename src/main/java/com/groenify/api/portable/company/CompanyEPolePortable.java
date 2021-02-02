package com.groenify.api.portable.company;

import com.groenify.api.database.methods.company.CompanyEPoleMethods;
import com.groenify.api.database.model.company.Company;
import com.groenify.api.database.model.company.CompanyEPole;
import com.groenify.api.database.model.epole.EPole;
import com.groenify.api.database.service.company.CompanyEPoleService;
import com.groenify.api.portable.epole.EPolePortable;
import com.groenify.api.portable.price.__model.FactorAnswerPriceCSV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyEPolePortable {

    private static final Logger L =
            LoggerFactory.getLogger(CompanyEPolePortable.class);

    private final CompanyPortable companyPortable;
    private final EPolePortable ePolePortable;

    private final CompanyEPoleService service;

    public CompanyEPolePortable(
            final CompanyPortable var1,
            final EPolePortable var2,
            final CompanyEPoleService var3) {
        this.companyPortable = var1;
        this.ePolePortable = var2;
        this.service = var3;
    }

    private CompanyEPole storeCompanyEPole(
            final Company company,
            final EPole ePole,
            final CompanyEPoleMethods methods) {

        L.debug("CompanyEPole not found with EPole(id={}) and Company(id={})",
                ePole.getId(), company.getId());
        return service.create(company, ePole, methods);
    }

    public final CompanyEPole determineCompanyEPole(
            final FactorAnswerPriceCSV priceCSV) {

        final Company company = companyPortable.determineCompany(priceCSV);
        final EPole ePole = ePolePortable.determineEPole(priceCSV);

        final Optional<CompanyEPole> opt =
                service.getAllFromCompanyWithEPole(company, ePole);
        return opt.orElseGet(() -> storeCompanyEPole(company, ePole, priceCSV));
    }

}
