package com.groenify.api.loader;

import com.groenify.api.database.model.factor.Factor;
import com.groenify.api.framework.config.LoadOrder;
import com.groenify.api.framework.config.ReadyEventLoader;
import com.groenify.api.portable.factor.FactorPortable;
import com.groenify.api.portable.factor.__model.FactorCSV;
import com.groenify.api.util.MapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactorLoader implements ReadyEventLoader {

    private static final String RESOURCE = "data/factors.csv";
    private static final Logger L =
            LoggerFactory.getLogger(FactorLoader.class);

    private final FactorPortable portable;

    public FactorLoader(final FactorPortable var) {
        this.portable = var;
    }

    @LoadOrder(2)
    @Override
    public final void loadOnReady() {
        final List<FactorCSV> factorCSVS =
                MapperUtil.readObjectFromCSVFile(RESOURCE, FactorCSV.class);
        for (final FactorCSV factorCSV : factorCSVS) {
            final Factor factor = portable.determineFactor(factorCSV);
            L.trace("Factor(id={},name={}) created from CSV",
                    factor.getId(), factor.getName());
        }
    }
}
