package com.groenify.api.framework.annotation.resolver;

import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.Factor;
import com.groenify.api.exceptions.PathException;
import com.groenify.api.framework.annotation.FactorInPath;
import com.groenify.api.repository.factor.FactorRepository;
import com.groenify.api.util.ResolverUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@Service
public class FactorInPathResolver
        extends AbstractMethodArgumentResolver<FactorInPath> {

    private final FactorRepository repository;

    public FactorInPathResolver(final FactorRepository var) {
        super(FactorInPath.class);
        this.repository = var;
    }

    @Override
    protected final Factor resolver(
            final NativeWebRequest var3, final FactorInPath annotation)
            throws PathException {

        final Long pathValue = ResolverUtil
                .findLongInPath(var3, annotation.value());
        final Optional<Factor> factor = repository.findById(pathValue);
        if (factor.isEmpty()) {
            logger().warn("Could not resolve {} for {} = {}",
                    Factor.class, annotation.value(), pathValue);
            throw PathException.notFoundWithId(Factor.class, pathValue);
        }

        return factor.get();
    }
}
