package org.codehaus.mojo.license.fetchlicenses.repository;

import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;

import java.io.File;

public class FileRegisterStructure {
    private final File repositoryRoot;

    public FileRegisterStructure(File repositoryRoot) {
        this.repositoryRoot = repositoryRoot;
    }

    public File getWellKnownLicenseDirectory() {
        return  new File(repositoryRoot, "well-known-licenses");
    }

    public File versionMappingFileFor(GavCoordinates coordinates) {
        return new File(artifactDirectoryFor(coordinates), "version-mapping");
    }

    public File artifactDirectoryFor(GavCoordinates coordinates) {
        return new File(repositoryRoot, groupIdToPath(coordinates) + "/" + coordinates.artifactId + "/");
    }

    private String groupIdToPath(GavCoordinates coordinates) {
        return coordinates.groupId.replaceAll("\\.", "/");
    }
}