package com.groenify.api.database.factor;

import com.groenify.api.database.IdModel;
import com.groenify.api.database.epole.EPoleBrand;
import com.groenify.api.rest.factor.__model.FactorReqMo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "`factor`")
public class Factor implements IdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "factor_type", nullable = false)
    private FactorType type;

    @Column(name = "name", nullable = false, columnDefinition = "mediumtext")
    private String name;

    @Column(name = "question", nullable = false, columnDefinition = "mediumtext")
    private String question;

    @Column(name = "description", columnDefinition = "longtext")
    private String description;

    public static Factor ofReqMo(final FactorType type, final FactorReqMo body) {
        return new Factor().update(type, body);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long var) {
        this.id = var;
    }

    public FactorType getType() {
        return type;
    }

    public void setType(final FactorType var) {
        this.type = var;
    }

    public String getName() {
        return name;
    }

    public void setName(final String var) {
        this.name = var;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(final String var) {
        this.question = var;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String var) {
        this.description = var;
    }

    private Factor update(final FactorType type, final FactorReqMo body) {
        this.setType(type);
        return update(body);
    }

    public Factor update(final FactorReqMo body) {
        this.setName(body.getName());
        this.setQuestion(body.getQuestion());
        this.setDescription(body.getDescription());
        return this;
    }
}