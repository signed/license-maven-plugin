package org.codehaus.mojo.license.fetchlicenses.repository;

import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.codehaus.mojo.license.fetchlicenses.Text;
import org.codehaus.mojo.license.fetchlicenses.repository.dsl.ResolveConfiguredEndpoint;

import java.io.File;

public class LicenseReader {
    private final TextReader textReader = new TextReader();
    private final FileRegisterStructure structure;

    public LicenseReader(FileRegisterStructure structure) {
        this.structure = structure;
    }

    public Text readLicense(GavCoordinates coordinates, VersionMapping mapping) {
        Reference reference = mapping.target(coordinates.version);
        return textReader.read(fileToLoad(coordinates, reference));
    }

    private File fileToLoad(GavCoordinates coordinates, Reference reference) {
        File root = resolveTarget(reference, coordinates);
        return new File(root, "LICENSE.txt");
    }

    private File resolveTarget(Reference reference, final GavCoordinates coordinates) {
        return reference.accept(new ResolveConfiguredEndpoint(coordinates, structure));
    }
}