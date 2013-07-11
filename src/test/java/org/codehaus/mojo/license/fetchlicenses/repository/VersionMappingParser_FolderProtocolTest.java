package org.codehaus.mojo.license.fetchlicenses.repository;

import org.codehaus.mojo.license.fetchlicenses.repository.dsl.VersionMappingBuilder;
import org.codehaus.mojo.license.fetchlicenses.repository.dsl.VersionMappingDslParser;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class VersionMappingParser_FolderProtocolTest {

    private final VersionMappingBuilder builder = mock(VersionMappingBuilder.class);

    @Test
    public void passTheSubDirectoryNameToTheBuilder() throws Exception {
        new VersionMappingDslParser(builder).parseLine("sub-directory://the-name <- 0.8-SNAPSHOT");

        verify(builder).subDirectory("the-name");
    }
}