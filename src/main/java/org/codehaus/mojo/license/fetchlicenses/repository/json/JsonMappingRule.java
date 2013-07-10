package org.codehaus.mojo.license.fetchlicenses.repository.json;

import org.codehaus.mojo.license.fetchlicenses.repository.MappingRule;
import org.codehaus.mojo.license.fetchlicenses.repository.Target;

import java.util.Set;

public class JsonMappingRule implements MappingRule {
    private final Set<String> versions;
    private String license = "/well-known/apache2/";
    private String notice = "NOTICE.txt";

    public JsonMappingRule(Set<String> versions) {
        this.versions = versions;
    }

    public boolean appliesTo(String version) {
        return versions.contains(version);
    }

    public Target getTarget(String version) {
        throw new RuntimeException("not implemented yet");
    }
}