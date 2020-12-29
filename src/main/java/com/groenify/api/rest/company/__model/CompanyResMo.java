package com.groenify.api.rest.company.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.company.Company;
import com.groenify.api.util.DateUtil;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyResMo {

    @JsonProperty("id")
    private final Long id;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("date")
    private final String date;

    @JsonProperty("url")
    private final String url;

    private CompanyResMo(final Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.date = DateUtil.toIsoNoMillis(company.getDate());
        this.url = company.getUrl();
    }

    public static List<CompanyResMo> mapCompanyToResMoList(
            final List<Company> list) {
        return list.stream()
                .map(CompanyResMo::mapCompanyToResMo)
                .collect(Collectors.toList());
    }

    public static CompanyResMo mapCompanyToResMo(final Company company) {
        return new CompanyResMo(company);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }
}
