package org.codehaus.mojo.license.fetchlicenses.repository;

import org.apache.commons.io.FileUtils;
import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.codehaus.mojo.license.fetchlicenses.PopulateVersionMapping;
import org.codehaus.mojo.license.fetchlicenses.repository.json.VersionMappingJson;

import java.io.File;
import java.io.IOException;

public class VersionMappingLoader {

    private final FileRegisterStructure translator;

    public VersionMappingLoader(FileRegisterStructure translator) {
        this.translator = translator;
    }

    public VersionMapping loadVersionMappingFor(GavCoordinates coordinates, LoaderCallback loaderCallback) {
        VersionMapping mapping = new VersionMapping();
        File versionMappingFile = translator.versionMappingFileFor(coordinates);
        if (versionMappingFile.isFile()) {
            try {
                readAndParse(mapping, versionMappingFile);
            } catch (Exception e) {
                loaderCallback.failedToLoadVersionMapping();
            }
        }
        return mapping;
    }

    private void readAndParse(VersionMapping mapping, File versionMappingFile) {
        String mappingsAsString = readMappingFile(versionMappingFile);
        VersionMappingJson parser = new VersionMappingJson(new PopulateVersionMapping(mapping));
        parser.parseMapping(mappingsAsString);
    }

    private String readMappingFile(File versionMappingFile) {
        try {
            return FileUtils.readFileToString(versionMappingFile, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}