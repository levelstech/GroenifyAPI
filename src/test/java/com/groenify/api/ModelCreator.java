package com.groenify.api;

import com.groenify.api.database.IdModel;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.stream.Collectors;

public interface ModelCreator {
    TestEntityManager getEntityManager();

    void setEntityManager(TestEntityManager manager);

    default <T extends IdModel> T storeNew(final T model) {
        model.setId(null);
        return getEntityManager().persist(model);
    }

    default <T extends IdModel> List<T> storeNews(final List<T> model) {
        return model.stream()
                .map(this::storeNew)
                .collect(Collectors.toList());
    }
}
