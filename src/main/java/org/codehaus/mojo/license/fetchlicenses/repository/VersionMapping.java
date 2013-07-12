package org.codehaus.mojo.license.fetchlicenses.repository;

import org.codehaus.mojo.license.fetchlicenses.repository.json.Pointer;

import java.util.ArrayList;
import java.util.Collection;

public class VersionMapping {

    private Collection<MappingRule> rules = new ArrayList<MappingRule>();

    public void addRule(MappingRule rule) {
        rules.add(rule);
    }

    public boolean coversVersion(String version) {
        return null !=  legalTexts(version);
    }

    public Pointer legalTexts(String version) {
        for (MappingRule rule : rules) {
            if (rule.appliesTo(version)) {
                return rule.getTarget(version);
            }
        }
        return null;
    }
}