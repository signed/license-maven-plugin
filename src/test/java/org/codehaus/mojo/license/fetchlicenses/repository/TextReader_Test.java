package org.codehaus.mojo.license.fetchlicenses.repository;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TextReader_Test {

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void useTheFilenameAsNameForTheText() throws Exception {
        File file = folder.newFile("filename");
        assertThat(new TextReader().read(file).name(), is("filename"));
    }
}
