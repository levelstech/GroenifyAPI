package com.groenify.api.framework.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CompanyEPoleInPath {

    String DEFAULT_NAME = "companyEPoleId";

    @AliasFor("name")
    String value() default DEFAULT_NAME;

    @AliasFor("value")
    String name() default DEFAULT_NAME;

    boolean required() default true;
}
