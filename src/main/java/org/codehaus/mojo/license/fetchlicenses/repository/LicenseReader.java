package org.codehaus.mojo.license.fetchlicenses.repository;

import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.codehaus.mojo.license.fetchlicenses.Text;

import java.io.File;

public class LicenseReader {
    private final TextReader textReader = new TextReader();
    private final FileRegisterStructure structure;

    public LicenseReader(FileRegisterStructure structure) {
        this.structure = structure;
    }

    public Text readLicense(GavCoordinates coordinates, VersionMapping mapping) {
        Target target = mapping.target(coordinates.version);
        File root = resolveTarget(target, coordinates);
        File licenseFileIn = new File(root, "LICENSE.txt");
        return textReader.read(licenseFileIn);
    }

    private File resolveTarget(Target target, GavCoordinates coordinates) {
        if( target instanceof WellKnownLicense) {
            return new File(structure.getWellKnownLicenseDirectory(), target.subDirectory);
        }else if(target instanceof SubDirectory) {
            return  new File(structure.artifactDirectoryFor(coordinates), target.subDirectory);
        }
        throw new RuntimeException("not supported target");
    }
}