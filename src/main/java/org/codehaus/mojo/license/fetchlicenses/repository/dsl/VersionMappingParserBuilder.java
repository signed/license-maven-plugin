package org.codehaus.mojo.license.fetchlicenses.repository.dsl;

import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.codehaus.mojo.license.fetchlicenses.VersionMappingParser;
import org.codehaus.mojo.license.fetchlicenses.repository.FileRegisterStructure;

import java.io.File;

public class VersionMappingParserBuilder {

    private final FileRegisterStructure translator;
    private RuleProductionListener listener;
    private GavCoordinates coordinates;

    public VersionMappingParserBuilder(FileRegisterStructure translator) {
        this.translator = translator;
    }

    public VersionMappingParser build() {
        File artifactDirectory = translator.artifactDirectoryFor(this.coordinates);
        File wellKnownLicenseDirectory = translator.getWellKnownLicenseDirectory();
        VersionMappingBuilder builder = new VersionMappingBuilder(wellKnownLicenseDirectory, artifactDirectory, listener);


        return new VersionMappingDslParser(builder);
    }

    public VersionMappingParserBuilder forArtifact(GavCoordinates coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public void informProductionListener(RuleProductionListener listener) {
        this.listener = listener;
    }
}
