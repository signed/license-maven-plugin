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

        boolean licenseMissing = true;

        for (Pointer pointer : mapping.pointers(coordinates.version)) {
            if("license".equals(pointer.name())) {
                licenseMissing = false;
            }

            for (File toLoad : new PointerResolver(structure).filesToLoad(coordinates, pointer)) {
                Text license = textReader.read(toLoad);
                result.add(license);
            }
        }

        if(licenseMissing){
            throw new RuntimeException("no license information present");
        }
        return result;
    }

}