package org.codehaus.mojo.license.fetchlicenses;

import org.apache.commons.io.FileUtils;
import org.codehaus.mojo.license.fetchlicenses.repository.VersionMappingBuilder;
import org.codehaus.mojo.license.fetchlicenses.repository.VersionMappingParser;

import java.io.File;
import java.io.IOException;

public class ThirdPartyLicenseRegister {

    private final File repositoryRoot;

    public ThirdPartyLicenseRegister(File repositoryRoot) {
        this.repositoryRoot = repositoryRoot;
    }

    public void lookup(GavCoordinates coordinates, LicenseLookupCallback callback) {
        File versionMappingFile = new File(repositoryRoot, coordinates.groupId + "/" + coordinates.artifactId + "/" + "version-mapping");
        if(versionMappingFile.isFile()) {
            String mappingsAsString = readMappingFile(versionMappingFile);
            new VersionMappingParser(new VersionMappingBuilder()).parseMapping(mappingsAsString);
            String[] split = mappingsAsString.split("<-");
            System.out.println(split[0].trim());
            System.out.println(split[1].trim());
            return;
        }

        File licenseFile = licenseFileFor(coordinates);
        if (!licenseFile.isFile()) {
            callback.missingLicenseInformationFor(coordinates);
        } else {
            Text license = read(licenseFile);
            LicenseObligations data = new LicenseObligations(coordinates, license);
            callback.found(data);
        }
    }

    private String readMappingFile(File versionMappingFile) {
        try {
            return FileUtils.readFileToString(versionMappingFile, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File licenseFileFor(GavCoordinates coordinates) {
        File directory = new File(repositoryRoot, coordinates.groupId + "/" + coordinates.artifactId + "/" + coordinates.version);
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