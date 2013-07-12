package org.codehaus.mojo.license.fetchlicenses;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class Licensee {

    private final File usedLicenseDirectory;

    public Licensee(File usedLicenseDirectory) {
        this.usedLicenseDirectory = usedLicenseDirectory;
    }

    public void complyWith(LicenseObligations obligations) {
        try {
            String groupId = obligations.coordinates.groupId;
            String artifact = obligations.coordinates.artifactId;
            File file = new File(usedLicenseDirectory, groupId + "." + artifact + ".LICENSE.txt");
            for (Text legalText : obligations.legalTexts) {
                FileUtils.writeStringToFile(file, legalText.toString(), "UTF-8");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}