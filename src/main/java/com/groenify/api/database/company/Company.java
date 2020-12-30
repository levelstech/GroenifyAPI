package com.groenify.api.database.company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.IdModel;
import com.groenify.api.rest.company.__model.CompanyReqMo;
import com.groenify.api.util.DateUtil;
import com.groenify.api.util.MapperUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "`company`")
public class Company implements IdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "mediumtext")
    private String name;

    @Column(name = "creation_date", nullable = false)
    private Date date;

    @Column(name = "url", columnDefinition = "longtext")
    private String url;

    public static Company ofJsonObjStr(final String jsonStr) {
        return MapperUtil.readObject(jsonStr, Company.class);
    }

    public static Company ofReqMo(final CompanyReqMo body) {
        return new Company().update(body);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long var) {
        this.id = var;
    }

    public String getName() {
        return name;
    }

    public void setName(final String var) {
        this.name = var;
    }

    public Date getDate() {
        return date;
    }

    public String getDateString() {
        return DateUtil.toIsoNoMillis(getDate());
    }

    public void setDate(final Date var) {
        this.date = var;
    }

    public void setDateString(final String var) {
        setDate(DateUtil.fromIsoNoMillis(var));
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String var) {
        this.url = var;
    }

    public Company update(final CompanyReqMo updated) {
        this.setName(updated.getName());
        this.setDate(DateUtil.fromIsoNoMillis(updated.getDate()));
        this.setUrl(updated.getUrl());
        return this;
    }
}
