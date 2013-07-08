package org.codehaus.mojo.license.fetchlicenses.repository;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

public class VersionMappingParser_Test {

    @Test
    public void doNotTalkToTheBuilderIfProtocolIsUnKnown() throws Exception {
        VersionMappingBuilder builder = mock(VersionMappingBuilder.class);
        new VersionMappingParser(builder).parseLine("un-known-protocol://sky <- 12.04");
        verifyZeroInteractions(builder);
    }
}