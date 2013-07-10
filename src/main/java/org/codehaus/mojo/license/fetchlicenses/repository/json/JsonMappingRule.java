package org.codehaus.mojo.license.fetchlicenses.repository.json;

import org.codehaus.mojo.license.fetchlicenses.repository.MappingRule;
import org.codehaus.mojo.license.fetchlicenses.repository.Reference;

import java.util.Set;

public class JsonMappingRule implements MappingRule {
    private final Set<String> versions;
    private final Set<Pointer> pointers;

    public JsonMappingRule(Set<String> versions, Set<Pointer> pointers) {
        this.versions = versions;
        this.pointers = pointers;
    }

    public boolean appliesTo(String version) {
        return versions.contains(version);
    }

    public Reference getTarget(String version) {
        throw new RuntimeException("not implemented yet");
    }

    public Set<String> versions() {
        return versions;
    }

    public Set<Pointer> pointers() {
        return pointers;
    }
}