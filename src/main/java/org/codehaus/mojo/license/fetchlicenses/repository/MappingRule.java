package org.codehaus.mojo.license.fetchlicenses.repository;

import org.codehaus.mojo.license.fetchlicenses.repository.json.Pointer;

public interface MappingRule {
    boolean appliesTo(String version);

    Pointer getTarget(String version);
}
