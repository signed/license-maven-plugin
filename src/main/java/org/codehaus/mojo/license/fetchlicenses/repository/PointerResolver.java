package org.codehaus.mojo.license.fetchlicenses.repository;

import org.apache.commons.io.filefilter.FileFileFilter;
import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.codehaus.mojo.license.fetchlicenses.repository.json.Pointer;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class PointerResolver {

    private final FileRegisterStructure structure;

    public PointerResolver(FileRegisterStructure structure) {
        this.structure = structure;
    }

    public Collection<File> filesToLoad(GavCoordinates coordinates, Pointer pointer) {
        File targetOfPointer = resolveTarget(coordinates, pointer);
        if (pointer.path().endsWith("/")) {
            FileFilter filter = FileFileFilter.FILE;
            return asList(targetOfPointer.listFiles(filter));
        } else {
            return singletonList(targetOfPointer);
        }
    }

    private File resolveTarget(GavCoordinates coordinates, Pointer pointer) {
        File targetOfPointer;
        String path = pointer.path();
        if (path.startsWith("/")) {
            targetOfPointer = new File(structure.getRoot(), pointer.path());
        } else if (path.startsWith("->well-known-license")) {
            int firstSlash = path.indexOf('/');
            String relativePathBelowWellKnownLicenseDirectory = path.substring(firstSlash, path.length());
            targetOfPointer = new File(structure.getWellKnownLicenseDirectory(), relativePathBelowWellKnownLicenseDirectory);
        } else {
            File artifactDirectory = structure.artifactDirectoryFor(coordinates);
            targetOfPointer = new File(artifactDirectory, pointer.path());
        }
        return targetOfPointer;
    }
}