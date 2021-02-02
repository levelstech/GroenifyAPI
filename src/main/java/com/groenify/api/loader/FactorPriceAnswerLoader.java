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

    private static final String RESOURCE = "data/prices.csv";
    private static final Logger L =
            LoggerFactory.getLogger(FactorPriceAnswerLoader.class);

    private final FactorAnswerPricePortable portable;

    public FactorPriceAnswerLoader(final FactorAnswerPricePortable var) {
        this.portable = var;
    }

    @LoadOrder(3)
    @Override
    public final void loadOnReady() {
        final List<FactorAnswerPriceCSV> priceCSVS =
                MapperUtil.readObjectFromCSVFile(
                        RESOURCE, FactorAnswerPriceCSV.class);
        for (final FactorAnswerPriceCSV priceCSV : priceCSVS) {
            final FactorAnswerPrice price =
                    portable.determineFactorAnswerPrice(priceCSV);
            L.trace("FactorAnswerPrice(id={},price={}) created from CSV",
                    price.getId(), price.getPrice());
        }
    }
}
