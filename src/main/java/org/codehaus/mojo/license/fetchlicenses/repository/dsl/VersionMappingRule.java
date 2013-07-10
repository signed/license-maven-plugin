package org.codehaus.mojo.license.fetchlicenses.repository.dsl;

import org.codehaus.mojo.license.fetchlicenses.repository.MappingRule;
import org.codehaus.mojo.license.fetchlicenses.repository.Reference;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VersionMappingRule implements MappingRule {

    private final Map<String, Reference> versionToTarget = new HashMap<String, Reference>();

    public VersionMappingRule(Set<String> versions, Reference reference) {
        for (String version : versions) {
            versionToTarget.put(version, reference);
        }
    }

    public boolean appliesTo(String version) {
        return versionToTarget.keySet().contains(version);
    }

    public Reference getTarget(String version) {
        return versionToTarget.get(version);
    }
}