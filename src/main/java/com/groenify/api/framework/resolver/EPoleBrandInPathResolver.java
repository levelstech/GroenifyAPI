package com.groenify.api.framework.resolver;

import com.groenify.api.database.EPoleBrand;
import com.groenify.api.exceptions.PathException;
import com.groenify.api.repository.EPoleBrandRepository;
import com.groenify.api.repository.RepositoryMethods;
import com.groenify.api.util.ResolverUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@Service
public class EPoleBrandInPathResolver
        extends AbstractMethodArgumentResolver<EPoleBrandInPath> {

    private final EPoleBrandRepository repository;

    public EPoleBrandInPathResolver(final EPoleBrandRepository var) {
        super(EPoleBrandInPath.class);
        this.repository = var;
    }

    @Override
    protected final EPoleBrand resolver(
            final NativeWebRequest var3, final EPoleBrandInPath annotation)
            throws PathException {

        final Long pathValue = ResolverUtil
                .findLongInPath(var3, annotation.value());
        final Optional<EPoleBrand> brand = repository.findById(pathValue);
        if (brand.isPresent()) return brand.get();

        throw PathException.notFoundWithId(EPoleBrand.class, pathValue);
    }
}
