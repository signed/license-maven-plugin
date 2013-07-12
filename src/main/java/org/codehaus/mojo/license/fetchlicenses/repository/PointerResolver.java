package org.codehaus.mojo.license.fetchlicenses.repository;

import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.codehaus.mojo.license.fetchlicenses.repository.json.Pointer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PointerResolver {

    private final FileRegisterStructure structure;

    public PointerResolver(FileRegisterStructure structure) {
        this.structure = structure;
    }

    public Collection<File> filesToLoad(GavCoordinates coordinates, Pointer reference) {
        List<File> files = new ArrayList<File>();
        String path = reference.path();
        if (path.startsWith("/")) {
            files.add(new File(structure.getRoot(), reference.path()));
        } else if (path.startsWith("->well-known-license")) {
            int firstSlash = path.indexOf('/');
            String relativePathBelowWellKnownLicenseDirectory = path.substring(firstSlash, path.length());
            files.add(new File(structure.getWellKnownLicenseDirectory(), relativePathBelowWellKnownLicenseDirectory));
        } else {
            File artifactDirectory = structure.artifactDirectoryFor(coordinates);
            files.add(new File(artifactDirectory, reference.path()));
        }
        return files;
    }
}