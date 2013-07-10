package org.codehaus.mojo.license.fetchlicenses.repository;

import org.apache.commons.io.FileUtils;
import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.codehaus.mojo.license.fetchlicenses.repository.dsl.RuleProductionListener;
import org.codehaus.mojo.license.fetchlicenses.repository.dsl.VersionMappingBuilder;
import org.codehaus.mojo.license.fetchlicenses.repository.dsl.VersionMappingParser;

import java.io.File;
import java.io.IOException;

public class VersionMappingLoader {

    private final CoordinatesToPathTranslator translator;

    public VersionMappingLoader(File repositoryRoot) {
        translator = new CoordinatesToPathTranslator(repositoryRoot);
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

    public VersionMapping loadVersionMapping(GavCoordinates coordinates, File wellKnownLicenseDirectory) {
        final VersionMapping mapping = new VersionMapping();
        File versionMappingFile = translator.versionMappingFile(coordinates);
        if (versionMappingFile.isFile()) {
            loadMapping(translator.artifactDirectory(coordinates), versionMappingFile, mapping, wellKnownLicenseDirectory);
        }
        return mapping;
    }
}
