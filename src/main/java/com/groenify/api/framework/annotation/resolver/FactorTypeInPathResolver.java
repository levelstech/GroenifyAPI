package com.groenify.api.framework.annotation.resolver;

import com.groenify.api.database.factor.FactorType;
import com.groenify.api.exceptions.PathException;
import com.groenify.api.framework.annotation.FactorTypeInPath;
import com.groenify.api.repository.factor.FactorTypeRepository;
import com.groenify.api.util.ResolverUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@Service
public class FactorTypeInPathResolver
        extends AbstractMethodArgumentResolver<FactorTypeInPath> {

    private final FactorTypeRepository repository;

    public FactorTypeInPathResolver(final FactorTypeRepository var) {
        super(FactorTypeInPath.class);
        this.repository = var;
    }

    @Override
    protected final FactorType resolver(
            final NativeWebRequest var3, final FactorTypeInPath annotation)
            throws PathException {

        final Long pathValue = ResolverUtil
                .findLongInPath(var3, annotation.value());
        final Optional<FactorType> type = repository.findById(pathValue);
        if (type.isEmpty()) {
            logger().warn("Could not resolve {} for {} = {}",
                    FactorType.class, annotation.value(), pathValue);
            throw PathException.notFoundWithId(FactorType.class, pathValue);
        }

        return type.get();
    }
}
