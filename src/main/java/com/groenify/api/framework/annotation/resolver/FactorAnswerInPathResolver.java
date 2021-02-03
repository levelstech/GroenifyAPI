package com.groenify.api.framework.annotation.resolver;

import com.groenify.api.database.model.factor.answer.FactorAnswer;
import com.groenify.api.exceptions.PathException;
import com.groenify.api.framework.annotation.FactorAnswerInPath;
import com.groenify.api.database.repository.factor.answer.FactorAnswerRepository;
import com.groenify.api.util.ResolverUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@Service
public class FactorAnswerInPathResolver
        extends AbstractMethodArgumentResolver<FactorAnswerInPath> {

    private final FactorAnswerRepository repository;

    public FactorAnswerInPathResolver(final FactorAnswerRepository var) {
        super(FactorAnswerInPath.class);
        this.repository = var;
    }

    @Override
    protected final FactorAnswer resolver(
            final NativeWebRequest var3, final FactorAnswerInPath annotation)
            throws PathException {

        final Long pathValue = ResolverUtil
                .findLongInPath(var3, annotation.value());
        final Optional<FactorAnswer> answer = repository.findById(pathValue);
        if (answer.isEmpty()) {
            logger().warn("Could not resolve {} for {} = {}",
                    FactorAnswer.class, annotation.value(), pathValue);
            throw PathException.notFoundWithId(FactorAnswer.class, pathValue);
        }

        return answer.get();
    }
}
