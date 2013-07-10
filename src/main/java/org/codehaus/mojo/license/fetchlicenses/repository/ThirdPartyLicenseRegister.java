package org.codehaus.mojo.license.fetchlicenses.repository;

import org.apache.commons.io.FileUtils;
import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.codehaus.mojo.license.fetchlicenses.LicenseLookupCallback;
import org.codehaus.mojo.license.fetchlicenses.LicenseObligations;
import org.codehaus.mojo.license.fetchlicenses.Text;
import org.codehaus.mojo.license.fetchlicenses.repository.dsl.RuleProductionListener;
import org.codehaus.mojo.license.fetchlicenses.repository.dsl.VersionMappingBuilder;
import org.codehaus.mojo.license.fetchlicenses.repository.dsl.VersionMappingParser;

import java.io.File;
import java.io.IOException;

public class ThirdPartyLicenseRegister {

    private final File repositoryRoot;
    private final File wellKnownLicenseDirectory;

    public ThirdPartyLicenseRegister(File repositoryRoot) {
        this.repositoryRoot = repositoryRoot;
        this.wellKnownLicenseDirectory = new File(repositoryRoot, "well-known-licenses");
    }

    public void lookup(GavCoordinates coordinates, final LicenseLookupCallback callback) {
        File artifactDirectory = new File(repositoryRoot, groupIdToDirectory(coordinates) + "/" + coordinates.artifactId + "/");
        VersionMapping mapping = loadVersionMapping(artifactDirectory, coordinates);

        if (!mapping.hasMappingForVersion(coordinates.version)) {
            callback.missingLicenseInformationFor(coordinates);
        } else {
            callback.found(new LicenseObligations(coordinates, readLicense(coordinates, mapping)));
        }
    }

    private Text readLicense(GavCoordinates coordinates, VersionMapping mapping) {
        File root = mapping.pathWithinRepository(coordinates.version);
        File licenseFileIn = new File(root, "LICENSE.txt");
        return read(licenseFileIn);
    }

    private VersionMapping loadVersionMapping(File artifactDirectory, GavCoordinates coordinates) {
        final VersionMapping mapping = new VersionMapping();
        File versionMappingFile = new File(repositoryRoot, groupIdToDirectory(coordinates) + "/" + coordinates.artifactId + "/" + "version-mapping");
        if (versionMappingFile.isFile()) {
            loadMapping(artifactDirectory, versionMappingFile, mapping);
        }
        return mapping;
    }

    private String groupIdToDirectory(GavCoordinates coordinates) {
        return coordinates.groupId.replaceAll("\\.", "/");
    }

    private void loadMapping(File artifactDirectory, File versionMappingFile, final VersionMapping mapping) {
        String mappingsAsString = readMappingFile(versionMappingFile);
        VersionMappingBuilder builder = new VersionMappingBuilder(wellKnownLicenseDirectory, artifactDirectory, new RuleProductionListener() {
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

    private Text read(File licenseFile) {
        try {
            String string = FileUtils.readFileToString(licenseFile);
            return new Text(string);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}