package org.codehaus.mojo.license.fetchlicenses.repository.dsl;

import org.codehaus.mojo.license.fetchlicenses.repository.MappingRule;
import org.codehaus.mojo.license.fetchlicenses.repository.VersionMapping;

class PopulateVersionMapping implements RuleProductionListener {
    private final VersionMapping mapping;

    public PopulateVersionMapping(VersionMapping mapping) {
        this.mapping = mapping;
    }

    public void produced(MappingRule rule) {
        mapping.addRule(rule);
    }
}
