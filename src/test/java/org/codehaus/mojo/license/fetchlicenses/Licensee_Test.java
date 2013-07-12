package org.codehaus.mojo.license.fetchlicenses;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Licensee_Test {

    @Rule
    public final TemporaryFolder thirdPartyLicenses = new TemporaryFolder();
    private final GavCoordinates coordinates = new GavCoordinates("org.example", "artifact", "do not care");
    private final Text licenseText = new Text("The license text");
    private final List<Text> legalTextsToAdd = new ArrayList<Text>();

    @Test
    public void putTheLicenseTextIntoTheThirdPartyLicenseDirectory() throws Exception {
        legalTextsToAdd.add(licenseText);
        writeLegalTexts();

        assertThat(contentOfFileInThirdPartyLicenses("org.example.artifact.LICENSE.txt"), is("The license text"));
    }

    @Test(expected = RuntimeException.class)
    public void doNotOverrideExistingFiles() throws Exception {
        legalTextsToAdd.add(new Text("Text One"));
        legalTextsToAdd.add(new Text("Text Two"));

        writeLegalTexts();
    }

    private void writeLegalTexts() {
        new Licensee(thirdPartyLicenses.getRoot()).complyWith(new LicenseObligations(coordinates, legalTextsToAdd));
    }

    private String contentOfFileInThirdPartyLicenses(String child) throws IOException {
        File root = thirdPartyLicenses.getRoot();
        File file = new File(root, child);
        return FileUtils.readFileToString(file, "UTF-8");
    }
}