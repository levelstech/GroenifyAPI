package com.groenify.api.database.model.factor;

import com.groenify.api.database.model.IdModel;
import com.groenify.api.rest.factor.__model.FactorTypeReqMo;
import com.groenify.api.util.MapperUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "`factor_type`")
public class FactorType implements IdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "mediumtext")
    private String name;

    @Column(name = "description", columnDefinition = "longtext")
    private String description;

    public static FactorType ofReqMo(final FactorTypeReqMo body) {
        return new FactorType().update(body);
    }

    public static FactorType ofJsonObjStr(final String jsonStr) {
        return MapperUtil.readObjectFromJsonString(jsonStr, FactorType.class);
    }

    public static FactorType ofFactorTypeEnum(final FactorTypeEnum typeEnum) {
        final FactorType factorType = new FactorType();
        factorType.setName(typeEnum.toString());
        return factorType;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(final String var) {
        this.description = var;
    }

    public FactorTypeEnum getAsEnum() {
        return FactorTypeEnum.valueOfFactorType(this);
    }

    public FactorType update(final FactorTypeReqMo body) {
        this.setName(body.getName());
        this.setDescription(body.getDescription());
        return this;
    }
}
