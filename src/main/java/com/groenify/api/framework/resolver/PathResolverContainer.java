package com.groenify.api.framework.resolver;


import org.springframework.stereotype.Service;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.util.Collections;
import java.util.List;

@Service
public class PathResolverContainer {

    private final List<HandlerMethodArgumentResolver> list;

    public PathResolverContainer(
            final EPoleBrandInPathResolver resolver1) {
        this.list = Collections.singletonList(resolver1);
    }

    public List<HandlerMethodArgumentResolver> getList() {
        return list;
    }
}
