package org.codehaus.mojo.license.fetchlicenses.repository.dsl;

import org.apache.commons.io.FileUtils;
import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.codehaus.mojo.license.fetchlicenses.repository.FileRegisterStructure;
import org.codehaus.mojo.license.fetchlicenses.repository.MappingRule;
import org.codehaus.mojo.license.fetchlicenses.repository.VersionMapping;

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
            loadMapping(translator.artifactDirectoryFor(coordinates), versionMappingFile, mapping, translator.getWellKnownLicenseDirectory());
        }
        return mapping;
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

    private String readMappingFile(File versionMappingFile) {
        try {
            return FileUtils.readFileToString(versionMappingFile, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
