package org.codehaus.mojo.license.fetchlicenses.repository;

import org.codehaus.mojo.license.fetchlicenses.repository.json.Pointer;

public class DummyPointer implements Pointer {
    private final String type;
    private final String path;

    public DummyPointer(String type, String path) {
        this.type = type;
        this.path = path;
    }

    public String name() {
        return type;
    }

    public String path() {
        return path;
    }
}
