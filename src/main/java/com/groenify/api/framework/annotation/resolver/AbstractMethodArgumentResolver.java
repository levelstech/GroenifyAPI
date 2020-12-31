package com.groenify.api.framework.annotation.resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;
import java.util.Objects;

public abstract class AbstractMethodArgumentResolver<A extends Annotation>
        implements HandlerMethodArgumentResolver {

    private final Logger L =
            LoggerFactory.getLogger(AbstractMethodArgumentResolver.class);
    private final Class<A> annotationClass;

    protected AbstractMethodArgumentResolver(final Class<A> aClassParam) {
        this.annotationClass = aClassParam;
    }

    protected Logger logger() {
        return L;
    }

    @Override
    public final boolean supportsParameter(final MethodParameter var1) {
        return var1.getParameterAnnotation(annotationClass) != null;
    }

    @Override
    public final Object resolveArgument(
            final MethodParameter var1,
            final ModelAndViewContainer var2,
            final NativeWebRequest var3,
            final WebDataBinderFactory var4)
            throws Exception {
        final A annotation = var1.getParameterAnnotation(annotationClass);
        if (Objects.nonNull(annotation)) return resolver(var3, annotation);

        //Check whether the correct PathVariable was defined in the methods.
        // This is done by @annotationClass to the methods of the
        // RestController, annotation = annotationClass.
        throw new Exception(annotationClass.getSimpleName());
    }

    protected abstract Object resolver(NativeWebRequest var3, A annotation)
            throws Exception;

}

