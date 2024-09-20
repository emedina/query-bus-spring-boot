package com.emedina.query.spring;

import org.epo.cne.sharedkernel.query.core.QueryHandler;
import org.springframework.context.ApplicationContext;

/**
 * Creates a query handler that makes use of Spring's dependency injection capabilities.
 *
 * @param <H> type of the query handler
 * @author Enrique Medina Montenegro
 */
@SuppressWarnings("unchecked")
class QueryProvider<H extends QueryHandler<?, ?>> {

    private final ApplicationContext applicationContext;
    private final Class<H> type;

    /**
     * Constructor-based dependency injection.
     *
     * @param applicationContext Spring's application context
     * @param type               of the query handler
     */
    QueryProvider(final ApplicationContext applicationContext, final Class<H> type) {
        this.applicationContext = applicationContext;
        this.type = type;
    }

    public H get() {
        return this.applicationContext.getBean(this.type);
    }

}
