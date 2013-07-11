package org.codehaus.mojo.license.fetchlicenses;

import org.codehaus.mojo.license.fetchlicenses.repository.MappingRule;
import org.codehaus.mojo.license.fetchlicenses.repository.RuleProductionListener;
import org.codehaus.mojo.license.fetchlicenses.repository.VersionMapping;

public class PopulateVersionMapping implements RuleProductionListener {
    private final VersionMapping mapping;

    public PopulateVersionMapping(VersionMapping mapping) {
        this.mapping = mapping;
    }

    public void produced(MappingRule rule) {
        mapping.addRule(rule);
    }
}
