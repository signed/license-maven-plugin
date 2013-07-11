package org.codehaus.mojo.license.fetchlicenses.repository.dsl;

import org.apache.commons.io.FileUtils;
import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.codehaus.mojo.license.fetchlicenses.repository.FileRegisterStructure;
import org.codehaus.mojo.license.fetchlicenses.repository.VersionMapping;
import org.codehaus.mojo.license.fetchlicenses.repository.VersionMappingLoader;

import java.io.File;
import java.io.IOException;

public class DslVersionMappingLoader implements VersionMappingLoader {

    private final FileRegisterStructure translator;

    public DslVersionMappingLoader(FileRegisterStructure translator) {
        this.translator = translator;
    }

    public VersionMapping loadVersionMappingFor(GavCoordinates coordinates) {
        final VersionMapping mapping = new VersionMapping();
        File versionMappingFile = translator.versionMappingFileFor(coordinates);
        if (versionMappingFile.isFile()) {
            String mappingsAsString = readMappingFile(versionMappingFile);
            parseMapping(mappingsAsString, new PopulateVersionMapping(mapping), translator, coordinates);
        }
        return mapping;
    }

    private String readMappingFile(File versionMappingFile) {
        try {
            return FileUtils.readFileToString(versionMappingFile, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseMapping(String mappingsAsString, RuleProductionListener listener, FileRegisterStructure translator, GavCoordinates coordinates) {
        File artifactDirectory = translator.artifactDirectoryFor(coordinates);
        File wellKnownLicenseDirectory = translator.getWellKnownLicenseDirectory();
        VersionMappingBuilder builder = new VersionMappingBuilder(wellKnownLicenseDirectory, artifactDirectory, listener);
        new VersionMappingParser(builder).parseMapping(mappingsAsString);
    }
}