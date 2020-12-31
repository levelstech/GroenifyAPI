package com.groenify.api.framework.annotation.resolver;


import org.springframework.stereotype.Service;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.util.List;

@Service
public class PathResolverContainer {

    private final List<HandlerMethodArgumentResolver> list;

    public PathResolverContainer(
            final EPoleBrandInPathResolver resolver1,
            final CompanyInPathResolver resolver2,
            final EPoleInPathResolver resolver3,
            final CompanyEPoleInPathResolver resolver4) {
        this.list = List.of(resolver1, resolver2, resolver3, resolver4);
    }

    public List<HandlerMethodArgumentResolver> getList() {
        return list;
    }
}
