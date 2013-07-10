package org.codehaus.mojo.license.fetchlicenses.repository;

import java.util.ArrayList;
import java.util.Collection;

public class VersionMapping {

    private Collection<MappingRule> rules = new ArrayList<MappingRule>();

    public void addRule(MappingRule rule) {
        rules.add(rule);
    }

    public boolean hasMappingForVersion(String version) {
        return null !=  target(version);
    }

    public Reference target(String version) {
        for (MappingRule rule : rules) {
            if (rule.appliesTo(version)) {
                return rule.getTarget(version);
            }
        }
        return null;
    }
}