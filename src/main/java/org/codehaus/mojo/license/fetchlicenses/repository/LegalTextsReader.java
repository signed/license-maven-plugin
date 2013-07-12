package org.codehaus.mojo.license.fetchlicenses.repository;

import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.codehaus.mojo.license.fetchlicenses.Text;
import org.codehaus.mojo.license.fetchlicenses.repository.json.Pointer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LegalTextsReader {
    private final TextReader textReader = new TextReader();
    private final FileRegisterStructure structure;

    public LegalTextsReader(FileRegisterStructure structure) {
        this.structure = structure;
    }

    public Iterable<Text> readFor(GavCoordinates coordinates, VersionMapping mapping) {
        List<Text> result = new ArrayList<Text>();

        for (Pointer pointer : mapping.legalTexts(coordinates)) {
            File toLoad = fileToLoad(coordinates, pointer);
            Text license = textReader.read(toLoad);
            result.add(license);
        }

        return result;
    }

    private File fileToLoad(GavCoordinates coordinates, Pointer reference) {
        String path = reference.path();
        if (path.startsWith("/")) {
            return new File(structure.getRoot(), reference.path());
        } else if (path.startsWith("->well-known-license")) {
            int firstSlash = path.indexOf('/');
            String relativePathBelowWellKnownLicenseDirectory = path.substring(firstSlash, path.length());
            return new File(structure.getWellKnownLicenseDirectory(), relativePathBelowWellKnownLicenseDirectory);
        } else {
            File artifactDirectory = structure.artifactDirectoryFor(coordinates);
            return new File(artifactDirectory, reference.path());
        }
    }
}