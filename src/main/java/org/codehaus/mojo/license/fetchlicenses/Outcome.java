package org.codehaus.mojo.license.fetchlicenses;

import org.apache.commons.collections.Closure;

public class Outcome {
    private boolean success;

    public static Outcome pessimistic() {
        return new Outcome();
    }

    public void success() {
        success = true;
    }

    public void failure() {
        success = false;
    }

    public void onFailure(Closure action) {
        if(!success){
            action.execute(null);
        }
    }
}
