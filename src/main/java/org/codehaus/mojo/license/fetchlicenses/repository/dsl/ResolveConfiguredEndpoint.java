package org.codehaus.mojo.license.fetchlicenses.repository.dsl;

import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.codehaus.mojo.license.fetchlicenses.repository.FileRegisterStructure;
import org.codehaus.mojo.license.fetchlicenses.repository.Reference;
import org.codehaus.mojo.license.fetchlicenses.repository.ReferenceVisitor;

import java.io.File;

public class ResolveConfiguredEndpoint implements ReferenceVisitor<File> {
    private final GavCoordinates coordinates;
    private final FileRegisterStructure structure;

    public ResolveConfiguredEndpoint(GavCoordinates coordinates, FileRegisterStructure structure) {
        this.coordinates = coordinates;
        this.structure = structure;
    }

    public File wellKnownLicense(WellKnownLicense license) {
        File wellKnownLicenseDirectory = structure.getWellKnownLicenseDirectory();
        return resolve(wellKnownLicenseDirectory, license);
    }

    public File subDirectory(SubDirectory subDirectory) {
        return resolve(structure.artifactDirectoryFor(coordinates), subDirectory);
    }

    private File resolve(File wellKnownLicenseDirectory, Reference license) {
        return new File(wellKnownLicenseDirectory, license.subDirectory);
    }
}