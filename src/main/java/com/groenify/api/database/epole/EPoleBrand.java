package com.groenify.api.database.epole;

import com.groenify.api.database.IdModel;
import com.groenify.api.rest.epole.__model.EPoleBrandReqMo;
import com.groenify.api.util.MapperUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "`epole_brand`")
public class EPoleBrand implements IdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "mediumtext")
    private String name;

    @OneToMany(mappedBy = "brand")
    private List<EPole> ePoles;

    public static EPoleBrand ofJsonObjStr(final String jsonStr) {
        return MapperUtil.readObject(jsonStr, EPoleBrand.class);
    }

    public static List<EPoleBrand> ofJsonArrStr(final String jsonStr) {
        return MapperUtil.readArray(jsonStr, EPoleBrand.class);
    }

    public static EPoleBrand ofReqMo(final EPoleBrandReqMo body) {
        return new EPoleBrand().update(body);
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

    public List<EPole> getEPoles() {
        return ePoles;
    }

    public void setEPoles(final List<EPole> var) {
        this.ePoles = var;
    }

    public EPoleBrand update(final EPoleBrandReqMo updated) {
        this.setName(updated.getName());
        return this;
    }
}
