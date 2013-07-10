package org.codehaus.mojo.license.fetchlicenses.repository;

import org.apache.commons.io.FileUtils;
import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.codehaus.mojo.license.fetchlicenses.repository.dsl.RuleProductionListener;
import org.codehaus.mojo.license.fetchlicenses.repository.dsl.VersionMappingBuilder;
import org.codehaus.mojo.license.fetchlicenses.repository.dsl.VersionMappingParser;

import java.io.File;
import java.io.IOException;

public class VersionMappingLoader {

    private final FileRegisterStructure translator;

    public VersionMappingLoader(FileRegisterStructure translator) {
        this.translator = translator;
    }

    public String readMappingFile(File versionMappingFile) {
        try {
            return FileUtils.readFileToString(versionMappingFile, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadMapping(File artifactDirectory, File versionMappingFile, final VersionMapping mapping, File wellKnownLicenseDirectory1) {
        String mappingsAsString = readMappingFile(versionMappingFile);
        VersionMappingBuilder builder = new VersionMappingBuilder(wellKnownLicenseDirectory1, artifactDirectory, new RuleProductionListener() {
            public void produced(MappingRule rule) {
                mapping.addRule(rule);
            }
        });
        new VersionMappingParser(builder).parseMapping(mappingsAsString);
    }

    public VersionMapping loadVersionMapping(GavCoordinates coordinates) {
        final VersionMapping mapping = new VersionMapping();
        File versionMappingFile = translator.versionMappingFileFor(coordinates);
        if (versionMappingFile.isFile()) {
            loadMapping(translator.artifactDirectoryFor(coordinates), versionMappingFile, mapping, translator.getWellKnownLicenseDirectory());
        }
        return mapping;
    }
}