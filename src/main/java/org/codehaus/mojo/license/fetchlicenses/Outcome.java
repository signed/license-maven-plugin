package org.codehaus.mojo.license.fetchlicenses;

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

    public void onFailureThrow(RuntimeException e) {
        if(!success){
            throw e;
        }
    }
}
