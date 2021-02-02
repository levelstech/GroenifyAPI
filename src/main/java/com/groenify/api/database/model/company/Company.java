package com.groenify.api.database.model.company;

import com.groenify.api.database.methods.company.CompanyMethods;
import com.groenify.api.database.model.IdModel;
import com.groenify.api.database.model.ModelMethods;
import com.groenify.api.util.DateUtil;
import com.groenify.api.util.MapperUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
        return MapperUtil.readObjectFromJsonString(jsonStr, Company.class);
    }

    public static Company ofMethods(final CompanyMethods body) {
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
        return ModelMethods.getSaveDate(date);
    }

    public String getDateString() {
        return DateUtil.toIsoNoMillis(getDate());
    }

    public void setDate(final Date var) {
        final Date newDate = ModelMethods.getSaveDate(var);
        this.date = newDate == null ? DateUtil.now() : newDate;
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

    public Company update(final CompanyMethods updated) {
        this.setName(updated.getCompanyName());
        this.setDate(DateUtil.fromIsoNoMillis(updated.getCompanyDate()));
        this.setUrl(updated.getCompanyUrl());
        return this;
    }
}
