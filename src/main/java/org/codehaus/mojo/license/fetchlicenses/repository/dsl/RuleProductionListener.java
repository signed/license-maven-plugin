package org.codehaus.mojo.license.fetchlicenses.repository.dsl;

import org.codehaus.mojo.license.fetchlicenses.repository.MappingRule;

public interface RuleProductionListener {
    void produced(MappingRule rule);
}
