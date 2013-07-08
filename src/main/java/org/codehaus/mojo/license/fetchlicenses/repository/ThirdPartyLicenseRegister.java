package org.codehaus.mojo.license.fetchlicenses.repository;

import org.apache.commons.io.FileUtils;
import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.codehaus.mojo.license.fetchlicenses.LicenseLookupCallback;
import org.codehaus.mojo.license.fetchlicenses.LicenseObligations;
import org.codehaus.mojo.license.fetchlicenses.Text;

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
        File artifactDirectory = new File(repositoryRoot, coordinates.groupId + "/" + coordinates.artifactId + "/");
        File versionMappingFile = new File(repositoryRoot, coordinates.groupId + "/" + coordinates.artifactId + "/" + "version-mapping");
        final VersionMapping mapping = new VersionMapping();

        if (versionMappingFile.isFile()) {
            loadMapping(artifactDirectory, versionMappingFile, mapping);
        }

        if (!mapping.hasMappingForVersion(coordinates.version)) {
            callback.missingLicenseInformationFor(coordinates);
        } else {
            File root = mapping.rootDirectoryForVersion(coordinates.version);
            File licenseFileIn = getLicenseFileIn(root);
            Text license = read(licenseFileIn);
            LicenseObligations data = new LicenseObligations(coordinates, license);
            callback.found(data);
        }

    }

    private void loadMapping(File artifactDirectory, File versionMappingFile, final VersionMapping mapping) {
        String mappingsAsString = readMappingFile(versionMappingFile);
        VersionMappingBuilder builder = new VersionMappingBuilder(wellKnownLicenseDirectory, artifactDirectory, new RuleProductionListener() {
            public void produced(VersionMappingRule rule) {
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

    private File getLicenseFileIn(File directory) {
        return new File(directory, "LICENSE.txt");
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