package com.groenify.api.config;

import com.groenify.api.loader.FactorLoader;
import com.groenify.api.loader.FactorPriceAnswerLoader;
import com.groenify.api.loader.FactorTypeLoader;
import com.groenify.api.framework.config.LoadOrderFetcher;
import com.groenify.api.framework.config.ReadyEventLoader;
import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class ApplicationLoader
        implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger L =
            LoggerFactory.getLogger(ApplicationLoader.class);

    private final List<ReadyEventLoader> loaderList;

    public ApplicationLoader(
            final FactorTypeLoader var1,
            final FactorLoader var2,
            final FactorPriceAnswerLoader var3) {
        loaderList = List.of(var1, var2, var3);
    }

    private static void runLoader(final ReadyEventLoader loader) {

        L.trace("Running loader ({}) with order '{}'",
                loader.getClass(), LoadOrderFetcher.getOrder(loader));
        loader.loadOnReady();
    }

    @Override
    public final void onApplicationEvent(
            final @NotNull ApplicationReadyEvent event) {
        loaderList.stream()
                .sorted(Comparator.comparing(LoadOrderFetcher::getOrder))
                .forEach(ApplicationLoader::runLoader);
    }
}
