package org.codehaus.mojo.license.fetchlicenses.repository;

import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.codehaus.mojo.license.fetchlicenses.VersionMappingParser;
import org.codehaus.mojo.license.fetchlicenses.repository.json.VersionMappingJsonParser;

public class VersionMappingParserBuilder {

    private final FileRegisterStructure translator;
    private RuleProductionListener listener;
    private GavCoordinates coordinates;

    public VersionMappingParserBuilder(FileRegisterStructure translator) {
        this.translator = translator;
    }

    public VersionMappingParser build() {
        return new VersionMappingJsonParser(listener);
    }

    public VersionMappingParserBuilder forArtifact(GavCoordinates coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public void informProductionListener(RuleProductionListener listener) {
        this.listener = listener;
    }
}
