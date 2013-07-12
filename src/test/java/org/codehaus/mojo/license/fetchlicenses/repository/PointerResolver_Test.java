package org.codehaus.mojo.license.fetchlicenses.repository;

import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.codehaus.mojo.license.fetchlicenses.repository.json.Pointer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

public class PointerResolver_Test {

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();


    @Test
    public void pointerPathEndsWithDashReadAllFilesContainedInTheDirectory() throws Exception {
        PointerResolver resolver = new PointerResolver(new FileRegisterStructure(folder.getRoot()));
        GavCoordinates coordinates = new GavCoordinates("the.group.id", "artifact", "version");
        Pointer pointer = new DummyPointer("type", "/directory/");

        folder.newFolder("directory");
        folder.newFile("directory/one");
        folder.newFile("directory/two");

        assertThat(resolver.filesToLoad(coordinates, pointer), hasItems(file("one"), file("two")));
    }

    private File file(String subDirectory) {
        return new File(folder.getRoot(), "directory/" + subDirectory);
    }
}