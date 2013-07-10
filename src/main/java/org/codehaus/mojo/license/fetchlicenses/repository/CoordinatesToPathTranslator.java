package org.codehaus.mojo.license.fetchlicenses.repository;

import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;

import java.io.File;

public class CoordinatesToPathTranslator {
    private final File repositoryRoot;

    public CoordinatesToPathTranslator(File repositoryRoot) {
        this.repositoryRoot = repositoryRoot;
    }

    public String groupIdToPath(GavCoordinates coordinates) {
        return coordinates.groupId.replaceAll("\\.", "/");
    }

    public File artifactDirectory(GavCoordinates coordinates) {
        return new File(repositoryRoot, groupIdToPath(coordinates) + "/" + coordinates.artifactId + "/");
    }

    public File versionMappingFile(GavCoordinates coordinates) {
        return new File(repositoryRoot, groupIdToPath(coordinates) + "/" + coordinates.artifactId + "/" + "version-mapping");
    }
}