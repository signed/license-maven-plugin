package org.codehaus.mojo.license.fetchlicenses.repository;

public interface MappingRule {
    boolean appliesTo(String version);

    Target getTarget(String version);
}
