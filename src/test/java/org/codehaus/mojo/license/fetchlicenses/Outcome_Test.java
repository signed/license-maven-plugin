package org.codehaus.mojo.license.fetchlicenses;

import org.apache.commons.collections.Closure;
import org.junit.Test;

public class Outcome_Test {
    private final Outcome outcome = Outcome.pessimistic();
    private final Closure action = new Closure() {
        public void execute(Object input) {
            throw new RuntimeException();
        }
    };

    @Test(expected = RuntimeException.class)
    public void throwPassedExceptionOnFailure() throws Exception {
        outcome.failure();
        outcome.onFailure(action);
    }

    @Test
    public void doNotThrowExceptionOnSuccess() throws Exception {
        outcome.success();
        outcome.onFailure(action);
    }

    @Test(expected = RuntimeException.class)
    public void assumeFailureAsInitialOutcome() throws Exception {
        outcome.onFailure(action);
    }
}