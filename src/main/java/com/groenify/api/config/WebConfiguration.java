package com.groenify.api.config;

import com.groenify.api.framework.annotation.resolver.PathResolverContainer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final List<HandlerMethodArgumentResolver> list = new ArrayList<>();

    public WebConfiguration(final PathResolverContainer pathResolvers) {
        list.addAll(pathResolvers.getList());
    }

    @Override
    public final void addArgumentResolvers(
            final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.addAll(list);
    }
}

