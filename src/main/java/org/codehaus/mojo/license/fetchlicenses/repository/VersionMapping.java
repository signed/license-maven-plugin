package org.codehaus.mojo.license.fetchlicenses.repository;

import org.codehaus.mojo.license.fetchlicenses.repository.json.Pointer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class VersionMapping {

    private Collection<MappingRule> rules = new ArrayList<MappingRule>();

    public void addRule(MappingRule rule) {
        rules.add(rule);
    }

    public boolean coversVersion(String version) {
        return legalTexts(version).iterator().hasNext();
    }

    public Iterable<Pointer> legalTexts(String version) {
        List<Pointer> pointers = new ArrayList<Pointer>();
        for (MappingRule rule : rules) {
            if (rule.appliesTo(version)) {
                pointers.add(rule.getTarget(version));
            }
        }

        return pointers;
    }
}