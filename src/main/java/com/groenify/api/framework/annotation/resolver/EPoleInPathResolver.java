package com.groenify.api.framework.annotation.resolver;

import com.groenify.api.database.model.epole.EPole;
import com.groenify.api.exceptions.PathException;
import com.groenify.api.framework.annotation.EPoleInPath;
import com.groenify.api.database.repository.epole.EPoleRepository;
import com.groenify.api.util.ResolverUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@Service
public class EPoleInPathResolver
        extends AbstractMethodArgumentResolver<EPoleInPath> {

    private final EPoleRepository repository;

    public EPoleInPathResolver(final EPoleRepository var) {
        super(EPoleInPath.class);
        this.repository = var;
    }

    @Override
    protected final EPole resolver(
            final NativeWebRequest var3, final EPoleInPath annotation)
            throws PathException {

        final Long pathValue = ResolverUtil
                .findLongInPath(var3, annotation.value());
        final Optional<EPole> ePole = repository.findById(pathValue);
        if (ePole.isEmpty()) {
            logger().warn("Could not resolve {} for {} = {}",
                    EPole.class, annotation.value(), pathValue);
            throw PathException.notFoundWithId(EPole.class, pathValue);
        }
        return ePole.get();
    }
}
