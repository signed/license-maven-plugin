package org.codehaus.mojo.license.fetchlicenses.repository;

import org.codehaus.mojo.license.fetchlicenses.repository.json.Pointer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.addAll;

public class VersionMapping {

    private Collection<MappingRule> rules = new ArrayList<MappingRule>();

    public void addRule(MappingRule rule) {
        rules.add(rule);
    }

    public boolean coversVersion(String version) {
        return pointers(version).iterator().hasNext();
    }

    public Iterable<Pointer> pointers(String version) {
        List<Pointer> pointers = new ArrayList<Pointer>();
        for (MappingRule rule : rules) {
            if (rule.appliesTo(version)) {
                addAll(pointers, rule.pointers().iterator());
            }
        }

        return pointers;
    }
}