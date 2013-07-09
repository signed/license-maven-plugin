package org.codehaus.mojo.license.fetchlicenses.repository;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class VersionMapping {

    private Collection<MappingRule> rules = new ArrayList<MappingRule>();

    public void addRule(MappingRule rule) {
        rules.add(rule);
    }

    public boolean hasMappingForVersion(String version) {
        return null !=  rootDirectoryForVersion(version);
    }

    public File rootDirectoryForVersion(String version) {
        for (MappingRule rule : rules) {
            if (rule.appliesTo(version)) {
                return rule.getBaseDirectory(version);
            }
        }
        return null;
    }
}