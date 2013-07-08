package org.codehaus.mojo.license.fetchlicenses.repository;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class VersionMapping {

    private Collection<VersionMappingRule> rules = new ArrayList<VersionMappingRule>();

    public void addRule(VersionMappingRule rule) {
        rules.add(rule);
    }

    public boolean hasMappingForVersion(String version) {
        return null !=  rootDirectoryForVersion(version);
    }

    public File rootDirectoryForVersion(String version) {
        for (VersionMappingRule rule : rules) {
            if (rule.appliesTo(version)) {
                return rule.getBaseDirectory(version);
            }
        }
        return null;
    }
}