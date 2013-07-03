package org.codehaus.mojo.license.fetchlicenses;

import org.junit.Test;

public class Outcome_Test {
    private final Outcome outcome = Outcome.pessimistic();

    @Test(expected = RuntimeException.class)
    public void throwPassedExceptionOnFailure() throws Exception {
        outcome.failure();
        outcome.onFailureThrow(new RuntimeException());
    }

    @Test
    public void doNotThrowExceptionOnSuccess() throws Exception {
        outcome.success();
        outcome.onFailureThrow(new RuntimeException());
    }

    @Test(expected = RuntimeException.class)
    public void assumeFailureAsInitialOutcome() throws Exception {
        outcome.onFailureThrow(new RuntimeException());
    }
}
