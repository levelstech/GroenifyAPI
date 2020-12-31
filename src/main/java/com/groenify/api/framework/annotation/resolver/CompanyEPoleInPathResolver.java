package com.groenify.api.framework.annotation.resolver;

import com.groenify.api.database.company.Company;
import com.groenify.api.database.company.CompanyEPole;
import com.groenify.api.exceptions.PathException;
import com.groenify.api.framework.annotation.CompanyEPoleInPath;
import com.groenify.api.framework.annotation.CompanyInPath;
import com.groenify.api.repository.company.CompanyEPoleRepository;
import com.groenify.api.repository.company.CompanyRepository;
import com.groenify.api.util.ResolverUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@Service
public class CompanyEPoleInPathResolver
        extends AbstractMethodArgumentResolver<CompanyEPoleInPath> {

    private final CompanyEPoleRepository repository;

    public CompanyEPoleInPathResolver(final CompanyEPoleRepository var) {
        super(CompanyEPoleInPath.class);
        this.repository = var;
    }

    @Override
    protected final CompanyEPole resolver(
            final NativeWebRequest var3, final CompanyEPoleInPath annotation)
            throws PathException {

        final Long pathValue = ResolverUtil
                .findLongInPath(var3, annotation.value());
        final Optional<CompanyEPole> pole = repository.findById(pathValue);
        if (pole.isEmpty()) {
            logger().warn("Could not resolve {} for {} = {}",
                    CompanyEPole.class, annotation.value(), pathValue);
            throw PathException.notFoundWithId(CompanyEPole.class, pathValue);
        }
        return pole.get();

    }
}
