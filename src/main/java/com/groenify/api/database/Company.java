package com.groenify.api.database;

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

    public void setDate(final Date var) {
        this.date = var;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String var) {
        this.url = var;
    }

}
