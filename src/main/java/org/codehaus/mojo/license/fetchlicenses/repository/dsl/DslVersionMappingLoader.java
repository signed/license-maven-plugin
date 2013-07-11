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
    private VersionMappingParserBuilder parser;

    public DslVersionMappingLoader(FileRegisterStructure translator, VersionMappingParserBuilder versionMappingParserBuilder) {
        this.translator = translator;
        this.parser = versionMappingParserBuilder;
    }

    public VersionMapping loadVersionMappingFor(GavCoordinates coordinates) {
        VersionMapping mapping = new VersionMapping();
        File versionMappingFile = translator.versionMappingFileFor(coordinates);
        if (versionMappingFile.isFile()) {
            String mappingsAsString = readMappingFile(versionMappingFile);
            parser.forArtifact(coordinates);
            parser.informProductionListener(new PopulateVersionMapping(mapping));
            parser.build().parseMapping(mappingsAsString);
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
}