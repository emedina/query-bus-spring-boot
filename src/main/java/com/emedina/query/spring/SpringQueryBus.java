package com.emedina.query.spring;

import com.emedina.sharedkernel.query.Query;
import com.emedina.sharedkernel.query.core.QueryBus;
import com.emedina.sharedkernel.query.core.QueryHandler;

/**
 * Implementation of a query bus backed by Spring's registry.
 *
 * @author Enrique Medina Montenegro
 */
public class SpringQueryBus implements QueryBus {

    private final Registry registry;

    /**
     * Creates a new instance with the given registry using constructor-based dependency injection.
     *
     * @param registry a wrapper around Spring's application context
     */
    public SpringQueryBus(final Registry registry) {
        this.registry = registry;
    }

    /**
     * Delegates the handling of the query to the corresponding {@link Bean} from Spring.
     *
     * @param query the query object
     * @return the result of the command's execution
     */
    @Override
    @SuppressWarnings("unchecked")
    public <R, Q extends Query> R query(final Q query) {
        Class<Q> queryClass = (Class<Q>) query.getClass();
        QueryHandler<R, Q> queryHandler = this.registry.get(queryClass);
        return queryHandler.handle(query);
    }

}
