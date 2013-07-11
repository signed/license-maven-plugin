package org.codehaus.mojo.license.fetchlicenses.repository;

import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;

public interface VersionMappingLoader {
    VersionMapping loadVersionMappingFor(GavCoordinates coordinates);
}
