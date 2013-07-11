package org.codehaus.mojo.license.fetchlicenses.repository.dsl;

import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.codehaus.mojo.license.fetchlicenses.repository.VersionMapping;

public interface VersionMappingLoader {
    VersionMapping loadVersionMappingFor(GavCoordinates coordinates);
}
