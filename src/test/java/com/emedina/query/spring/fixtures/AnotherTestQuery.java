package com.emedina.query.spring.fixtures;

import com.emedina.sharedkernel.query.Query;

/**
 * Another test query fixture for testing the query bus.
 * 
 * @author Enrique Medina Montenegro
 */
public class AnotherTestQuery implements Query {

    private final Integer value;

    public AnotherTestQuery(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        AnotherTestQuery that = (AnotherTestQuery) obj;
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AnotherTestQuery{value=" + value + "}";
    }
}
