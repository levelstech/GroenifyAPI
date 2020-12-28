package com.example.groenify_api.database;

import com.example.groenify_api.util.JsonUtil;
import com.example.groenify_api.util.MapperUtil;
import com.google.gson.JsonObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

}
