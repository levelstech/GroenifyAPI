package com.groenify.api.database.company;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.IdModel;
import com.groenify.api.database.epole.EPole;
import com.groenify.api.rest.company.__model.CompanyEPoleReqMo;
import com.groenify.api.util.MapperUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "`company_to_epole`")
public class CompanyEPole implements IdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "epole_id", nullable = false)
    private EPole ePole;

    @Column(name = "base_price")
    @JsonProperty("base_price")
    private Double basePrice;

    public static CompanyEPole ofReqMo(
            final Company company,
            final EPole ePole,
            final CompanyEPoleReqMo body) {
        return new CompanyEPole().update(company, ePole, body);
    }

    public static CompanyEPole ofJsonObjStr(final String jsonStr) {
        return MapperUtil.readObject(jsonStr, CompanyEPole.class);
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long var) {
        this.id = var;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(final Company var) {
        this.company = var;
    }

    public EPole getEPole() {
        return ePole;
    }

    public void setePole(final EPole var) {
        this.ePole = var;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(final Double var) {
        this.basePrice = var;
    }

    public CompanyEPole update(
            final Company newCompany,
            final EPole newEPole,
            final CompanyEPoleReqMo body) {
        this.setCompany(newCompany);
        this.setePole(newEPole);
        return update(body);
    }

    public CompanyEPole update(
            final CompanyEPoleReqMo body) {
        this.setBasePrice(body.getBasePrice());
        return this;
    }
}
