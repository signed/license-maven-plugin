package org.codehaus.mojo.license.fetchlicenses.repository;

import org.codehaus.mojo.license.fetchlicenses.repository.dsl.VersionMappingBuilder;
import org.codehaus.mojo.license.fetchlicenses.repository.dsl.VersionMappingParser;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class VersionMappingParser_WellKnownLicenseTest {
    private final VersionMappingBuilder mock = mock(VersionMappingBuilder.class);

    @Before
    public void parse() throws Exception {
        new VersionMappingParser(mock).parseLine("well-known-license://apache2 <- 12.4, 13.0.1");
    }

    @Test
    public void passTheLicenseNameToTheBuilder() throws Exception {
        verify(mock).wellKnownLicense("apache2");
    }

    @Test
    public void passTheMappedVersionsToTheBuilder() throws Exception {
        verify(mock).version("12.4");
        verify(mock).version("13.0.1");
    }
}