package com.groenify.api.database.company;

import com.groenify.api.database.epole.EPole;

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
public class CompanyEPole {

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
    private Double basePrice;

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

    public EPole getePole() {
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
}
