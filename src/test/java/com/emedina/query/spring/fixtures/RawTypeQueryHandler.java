package com.emedina.query.spring.fixtures;

import com.emedina.sharedkernel.query.Query;
import com.emedina.sharedkernel.query.core.QueryHandler;

/**
 * Test fixture for a query handler without proper generic type information.
 * This is used to test error handling when generic types cannot be resolved.
 *
 * @author Enrique Medina Montenegro
 */
@SuppressWarnings("rawtypes")
public class RawTypeQueryHandler implements QueryHandler {

    @Override
    public Object handle(Query query) {
        // No-op implementation
        return null;
    }

}
