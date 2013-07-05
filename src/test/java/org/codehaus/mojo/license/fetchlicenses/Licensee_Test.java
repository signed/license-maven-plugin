package org.codehaus.mojo.license.fetchlicenses;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Licensee_Test {

    @Rule
    public final TemporaryFolder thirdPartyLicenses = new TemporaryFolder();

    @Test
    public void putTheLicenseTextIntoTheThirdPartyLicenseDirectory() throws Exception {
        GavCoordinates coordinates = new GavCoordinates("org.example", "artifact", "do not care");
        Text licenseText = new Text("The license text");

        new Licensee(thirdPartyLicenses.getRoot()).complyWith(new LicenseObligations(coordinates, licenseText));
        File root = thirdPartyLicenses.getRoot();
        File file = new File(root, "org.example.artifact.LICENSE.txt");

        assertThat(FileUtils.readFileToString(file, "UTF-8"), is("The license text"));
    }
}