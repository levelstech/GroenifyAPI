package com.groenify.api.framework.annotation.resolver;

import com.groenify.api.database.model.price.FactorAnswerPrice;
import com.groenify.api.exceptions.PathException;
import com.groenify.api.framework.annotation.FactorAnswerPriceInPath;
import com.groenify.api.database.repository.price.FactorAnswerPriceRepository;
import com.groenify.api.util.ResolverUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@Service
public class FactorAnswerPriceInPathResolver
        extends AbstractMethodArgumentResolver<FactorAnswerPriceInPath> {

    private final FactorAnswerPriceRepository repository;

    public FactorAnswerPriceInPathResolver(
            final FactorAnswerPriceRepository var) {
        super(FactorAnswerPriceInPath.class);
        this.repository = var;
    }

    @Override
    protected final FactorAnswerPrice resolver(
            final NativeWebRequest var3,
            final FactorAnswerPriceInPath annotation)
            throws PathException {

        final Long pathValue = ResolverUtil
                .findLongInPath(var3, annotation.value());
        final Optional<FactorAnswerPrice> price
                = repository.findById(pathValue);
        if (price.isEmpty()) {
            logger().warn("Could not resolve {} for {} = {}",
                    FactorAnswerPrice.class, annotation.value(), pathValue);
            throw PathException.notFoundWithId(
                    FactorAnswerPrice.class, pathValue);
        }

        return price.get();
    }
}
