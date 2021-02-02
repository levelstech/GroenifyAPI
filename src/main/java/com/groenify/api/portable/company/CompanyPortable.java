package com.groenify.api.portable.company;

import com.groenify.api.database.methods.company.CompanyMethods;
import com.groenify.api.database.model.company.Company;
import com.groenify.api.database.service.company.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyPortable {

    private static final Logger L =
            LoggerFactory.getLogger(CompanyPortable.class);

    private final CompanyService service;

    public CompanyPortable(final CompanyService var) {
        this.service = var;
    }

    private Company storeCompany(final CompanyMethods methods) {

        L.debug("Company not found with name = {}, storing in database",
                methods.getCompanyName());
        return service.create(methods);
    }

    public final Company determineCompany(final CompanyMethods methods) {
        final Optional<Company> opt =
                service.getByName(methods.getCompanyName());
        return opt.orElseGet(() -> storeCompany(methods));
    }
}
