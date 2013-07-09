package org.codehaus.mojo.license.fetchlicenses.repository;

import java.io.File;

public interface MappingRule {
    boolean appliesTo(String version);

    File getBaseDirectory(String version);
}
