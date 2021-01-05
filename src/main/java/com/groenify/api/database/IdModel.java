package com.groenify.api.database;

import java.util.Objects;

public interface IdModel {
    Long getId();

    void setId(Long var);

    default Boolean equalsId(final IdModel model) {
        return Objects.equals(this.getId(), model.getId());
    }
}
