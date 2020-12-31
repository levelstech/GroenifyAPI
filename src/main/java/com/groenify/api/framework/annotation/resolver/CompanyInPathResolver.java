package com.groenify.api.framework.annotation.resolver;

import com.groenify.api.database.company.Company;
import com.groenify.api.exceptions.PathException;
import com.groenify.api.framework.annotation.CompanyInPath;
import com.groenify.api.repository.company.CompanyRepository;
import com.groenify.api.util.ResolverUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@Service
public class CompanyInPathResolver
        extends AbstractMethodArgumentResolver<CompanyInPath> {

    private final CompanyRepository repository;

    public CompanyInPathResolver(final CompanyRepository var) {
        super(CompanyInPath.class);
        this.repository = var;
    }

    @Override
    protected final Company resolver(
            final NativeWebRequest var3, final CompanyInPath annotation)
            throws PathException {

        final Long pathValue = ResolverUtil
                .findLongInPath(var3, annotation.value());
        final Optional<Company> company = repository.findById(pathValue);
        if (company.isEmpty()) {
            logger().warn("Could not resolve {} for {} = {}",
                    Company.class, annotation.value(), pathValue);
            throw PathException.notFoundWithId(Company.class, pathValue);
        }
        return company.get();
    }
}
