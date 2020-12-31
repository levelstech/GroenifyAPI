package com.groenify.api.framework.annotation.resolver;

import com.groenify.api.database.epole.EPoleBrand;
import com.groenify.api.exceptions.PathException;
import com.groenify.api.framework.annotation.EPoleBrandInPath;
import com.groenify.api.repository.epole.EPoleBrandRepository;
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
        if (brand.isEmpty()) {
            logger().warn("Could not resolve {} for {} = {}",
                    EPoleBrand.class, annotation.value(), pathValue);
            throw PathException.notFoundWithId(EPoleBrand.class, pathValue);
        }
        return brand.get();

    }
}
