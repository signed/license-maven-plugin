package org.codehaus.mojo.license.fetchlicenses.repository;

import org.apache.commons.io.FileUtils;
import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.codehaus.mojo.license.fetchlicenses.LicenseLookupCallback;
import org.codehaus.mojo.license.fetchlicenses.LicenseObligations;
import org.codehaus.mojo.license.fetchlicenses.Text;
import org.hamcrest.MatcherAssert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.ArgumentCaptor;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ThirdPartyLicenseRegister_Test {

    @Rule
    public final TemporaryFolder licenseRegister = new TemporaryFolder();
    private final LicenseLookupCallback callback = mock(LicenseLookupCallback.class);
    private final GavCoordinates coordinates = new GavCoordinates("group.id", "artifact", "version");

    @Test
    public void informCallerAboutMissingLicenseInformation() throws Exception {
        licenseRegister().lookup(coordinates, callback);

        verify(callback).missingLicenseInformationFor(coordinates);
    }

    @Test
    public void returnAvailableLicenseInformationToCaller() throws Exception {
        writeLicenseFor(coordinates, "LicenseText");

        licenseRegister().lookup(coordinates, callback);

        ArgumentCaptor<LicenseObligations> captor = ArgumentCaptor.forClass(LicenseObligations.class);
        verify(callback).found(captor.capture());

        MatcherAssert.assertThat(captor.getValue().license, is(new Text("LicenseText")));
    }

    private void writeLicenseFor(GavCoordinates coordinates, String licenseText) throws IOException {
        File metaDataDirectory = licenseRegister.newFolder("group", "id", coordinates.artifactId);
        File mappingFile = new File(metaDataDirectory, "version-mapping");
        FileUtils.writeStringToFile(mappingFile, "sub-directory://version <- version", "UTF-8");


        File directory = licenseRegister.newFolder("group", "id", coordinates.artifactId, coordinates.version);
        File licenseFile = new File(directory, "LICENSE.txt");
        licenseFile.createNewFile();

        FileUtils.writeStringToFile(licenseFile, licenseText, "UTF-8");
    }

    private ThirdPartyLicenseRegister licenseRegister() {
        return new ThirdPartyLicenseRegister(licenseRegister.getRoot());
    }
}
