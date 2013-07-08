package org.codehaus.mojo.license.fetchlicenses.repository;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VersionMappingRule {

    private final Map<String, Target> versionToTarget = new HashMap<String, Target>();

    public VersionMappingRule(Set<String> versions, Target target) {
        for (String version : versions) {
            versionToTarget.put(version, target);
        }
    }

    public boolean appliesTo(String version) {
        return versionToTarget.keySet().contains(version);
    }

    public File getBaseDirectory(String version) {
        return versionToTarget.get(version).directory;
    }

}
