package com.groenify.api.database.epole;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.IdModel;
import com.groenify.api.rest.epole.__model.EPoleReqMo;
import com.groenify.api.util.MapperUtil;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "`epole`")
public class EPole implements IdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "brand", nullable = false)
    private EPoleBrand brand;

    @Column(name = "type", nullable = false, columnDefinition = "mediumtext")
    private String type;

    @Column(name = "description", columnDefinition = "longtext")
    private String description;


    public static EPole ofJsonObjStr(final String jsonStr) {
        return MapperUtil.readObject(jsonStr, EPole.class);
    }

    public static EPole ofReqMo(final EPoleBrand brand, final EPoleReqMo body) {
        return new EPole().update(brand, body);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long var) {
        this.id = var;
    }

    public EPoleBrand getBrand() {
        return brand;
    }

    public void setBrand(final EPoleBrand var) {
        this.brand = var;
    }

    public String getType() {
        return type;
    }

    public void setType(final String var) {
        this.type = var;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String var) {
        this.description = var;
    }

    public EPole update(
            final EPoleBrand brand,
            final EPoleReqMo body) {
        this.setBrand(brand);
        return this.update(body);
    }

    public EPole update(final EPoleReqMo updated) {
        this.setType(updated.getType());
        this.setDescription(updated.getDescription());
        return this;
    }
}
