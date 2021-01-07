package com.groenify.api.framework.annotation.resolver;


import org.springframework.stereotype.Service;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.util.List;

@Service
public class PathResolverContainer {

    private final List<HandlerMethodArgumentResolver> list;

    public PathResolverContainer(
            final EPoleBrandInPathResolver resolver1,
            final EPoleInPathResolver resolver2,
            final CompanyInPathResolver resolver3,
            final CompanyEPoleInPathResolver resolver4,
            final FactorTypeInPathResolver resolver5,
            final FactorInPathResolver resolver6,
            final FactorAnswerInPathResolver resolver7) {
        this.list = List.of(resolver1, resolver2, resolver3, resolver4,
                resolver5, resolver6, resolver7);
    }

    public final List<HandlerMethodArgumentResolver> getList() {
        return list;
    }
}
