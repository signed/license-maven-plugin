package org.codehaus.mojo.license.fetchlicenses.repository;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class VersionMappingBuilder {

    private final File wellKnownLicenseDirectory;
    private final File artifactDirectory;
    private final RuleProductionListener listener;

    private Target current;
    private Set<String> versions = new HashSet<String>();

    public VersionMappingBuilder(File wellKnownLicenseDirectory, File artifactDirectory, RuleProductionListener listener) {
        this.wellKnownLicenseDirectory = wellKnownLicenseDirectory;
        this.artifactDirectory = artifactDirectory;
        this.listener = listener;
    }

    public void wellKnownLicense(String identifier) {
        current = new Target(wellKnownLicenseDirectory, identifier);
    }

    public void subDirectory(String directoryName) {
        current = new Target(artifactDirectory,directoryName);
    }

    public void version(String version) {
        versions.add(version);
    }

    public void build(){
        listener.produced(new VersionMappingRule(versions, current));
        current = null;
        versions.clear();
    }
}