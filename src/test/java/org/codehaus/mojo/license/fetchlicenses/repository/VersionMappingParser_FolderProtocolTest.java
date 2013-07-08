package org.codehaus.mojo.license.fetchlicenses.repository;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class VersionMappingParser_FolderProtocolTest {

    private final MappingBuilder builder = mock(MappingBuilder.class);

    @Test
    public void passTheSubDirectoryNameToTheBuilder() throws Exception {
        new VersionMappingParser(builder).parseLine("sub-directory://the-name <- 0.8-SNAPSHOT");

        verify(builder).subDirectory("the-name");
    }
}