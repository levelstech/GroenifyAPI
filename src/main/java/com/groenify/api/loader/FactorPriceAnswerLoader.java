package com.groenify.api.loader;

import com.groenify.api.database.model.price.FactorAnswerPrice;
import com.groenify.api.framework.config.LoadOrder;
import com.groenify.api.framework.config.ReadyEventLoader;
import com.groenify.api.portable.price.__model.FactorAnswerPriceCSV;
import com.groenify.api.portable.price.FactorAnswerPricePortable;
import com.groenify.api.util.MapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactorPriceAnswerLoader implements ReadyEventLoader {

    private static final String DEFAULT_RESOURCE = "data/prices.csv";
    private static String resource = DEFAULT_RESOURCE;
    private static final Logger L =
            LoggerFactory.getLogger(FactorPriceAnswerLoader.class);

    private final FactorAnswerPricePortable portable;

    public FactorPriceAnswerLoader(final FactorAnswerPricePortable var) {
        this.portable = var;
    }

    static void setResource(final String fileName) {
        resource = fileName;
    }

    @LoadOrder(3)
    @Override
    public final void loadOnReady() {
        final List<FactorAnswerPriceCSV> priceCSVS = MapperUtil.
                readObjectFromCSVFile(resource, FactorAnswerPriceCSV.class);
        for (final FactorAnswerPriceCSV priceCSV : priceCSVS) {
            final FactorAnswerPrice price = portable.
                    getOrCreateFactorAnswerPriceFromCSV(priceCSV);
            L.trace("FactorAnswerPrice(id={},price={}) created from CSV",
                    price.getId(), price.getPrice());
        }
    }
}
