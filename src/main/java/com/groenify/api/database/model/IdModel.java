package com.groenify.api.database.model;

import java.util.Objects;

public interface IdModel {
    Long getId();

    void setId(Long var);

    default Boolean equalsId(final IdModel model) {
        return model != null && Objects.equals(this.getId(), model.getId());
    }
}
