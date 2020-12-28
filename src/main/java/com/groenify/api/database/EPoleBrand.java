package com.groenify.api.database;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.util.MapperUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "`epole_brand`")
public class EPoleBrand implements IdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonProperty("id")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "mediumtext")
    @JsonProperty("name")
    @NotNull(message = "'name' is a required field")
    private String name;

    public static EPoleBrand ofJsonObjStr(final String jsonStr) {
        return MapperUtil.readObject(jsonStr, EPoleBrand.class);
    }

    public static List<EPoleBrand> ofJsonArrStr(final String jsonStr) {
        return MapperUtil.readArray(jsonStr, EPoleBrand.class);
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

    public EPoleBrand update(final EPoleBrand updated) {
        this.setName(updated.getName());
        return this;
    }
}
