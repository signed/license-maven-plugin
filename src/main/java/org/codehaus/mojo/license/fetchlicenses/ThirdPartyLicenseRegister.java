package org.codehaus.mojo.license.fetchlicenses;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ThirdPartyLicenseRegister {

    private final File repositoryRoot;

    public ThirdPartyLicenseRegister(File repositoryRoot) {
        this.repositoryRoot = repositoryRoot;
    }

    public void lookup(GavCoordinates coordinates, LicenseLookupCallback callback) {
        File licenseFile = licenseFileFor(coordinates);
        if (!licenseFile.isFile()) {
            callback.missingLicenseInformationFor(coordinates);
        } else {
            Text license = read(licenseFile);
            LicenseObligations data = new LicenseObligations(coordinates, license);
            callback.found(data);
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