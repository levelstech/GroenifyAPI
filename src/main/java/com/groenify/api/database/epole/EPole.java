package com.groenify.api.database.epole;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class EPole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonProperty("id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "brand", nullable = false)
    private EPoleBrand brand;

    @Column(name = "type", nullable = false, columnDefinition = "mediumtext")
    private String type;

    @Column(name = "description", columnDefinition = "longtext")
    private String description;

    public Long getId() {
        return id;
    }

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
}
