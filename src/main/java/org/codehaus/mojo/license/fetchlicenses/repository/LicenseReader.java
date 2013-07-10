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
        Reference reference = mapping.target(coordinates.version);
        File root = resolveTarget(reference, coordinates);
        File licenseFileIn = new File(root, "LICENSE.txt");
        return textReader.read(licenseFileIn);
    }

    private File resolveTarget(Reference reference, final GavCoordinates coordinates) {
        return reference.accept(new ResolveConfiguredEndpoint(coordinates, structure));
    }

}