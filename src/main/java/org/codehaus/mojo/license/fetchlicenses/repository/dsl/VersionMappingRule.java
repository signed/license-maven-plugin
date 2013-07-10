package org.codehaus.mojo.license.fetchlicenses.repository.dsl;

import org.codehaus.mojo.license.fetchlicenses.repository.MappingRule;
import org.codehaus.mojo.license.fetchlicenses.repository.Target;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VersionMappingRule implements MappingRule {

    private final Map<String, Target> versionToTarget = new HashMap<String, Target>();

    public VersionMappingRule(Set<String> versions, Target target) {
        for (String version : versions) {
            versionToTarget.put(version, target);
        }
    }

    public boolean appliesTo(String version) {
        return versionToTarget.keySet().contains(version);
    }

    public Target getTarget(String version) {
        return versionToTarget.get(version);
    }
}