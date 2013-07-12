package org.codehaus.mojo.license.fetchlicenses.repository;

import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
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

    public Iterable<Pointer> legalTexts(GavCoordinates coordinates) {
        Pointer reference = legalTexts(coordinates.version);
        List<Pointer> pointers = new ArrayList<Pointer>();
        pointers.add(reference);
        return pointers;
    }
}