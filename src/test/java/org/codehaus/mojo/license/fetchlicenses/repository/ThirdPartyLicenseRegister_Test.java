package org.codehaus.mojo.license.fetchlicenses.repository;

import org.apache.commons.io.FileUtils;
import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.codehaus.mojo.license.fetchlicenses.LicenseLookupCallback;
import org.codehaus.mojo.license.fetchlicenses.LicenseObligations;
import org.codehaus.mojo.license.fetchlicenses.Text;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.ArgumentCaptor;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
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
    public void returnLicenseInformationInSubdirectoryToCaller() throws Exception {
        addMetaDataPointingToSubdirectory(coordinates, "legal");
        putLicenseInformationIntoSubdirectory("legal");

        licenseRegister().lookup(coordinates, callback);

        assertThat(returnedLicenseText(), is(text("LicenseText")));
    }

    @Test
    public void returnWellKnownLicenseInformationToCaller() throws Exception {
        addKnownLicense("apache", "The Apache License");
        writeMetaDataFor(coordinates, "well-known-license://apache <- version");

        licenseRegister().lookup(coordinates, callback);

        assertThat(returnedLicenseText(), is(text("The Apache License")));
    }

    private Text text(String licenseText) {
        return new Text(licenseText);
    }

    private Text returnedLicenseText() {
        ArgumentCaptor<LicenseObligations> captor = ArgumentCaptor.forClass(LicenseObligations.class);
        verify(callback).found(captor.capture());
        return captor.getValue().license;
    }

    private void putLicenseInformationIntoSubdirectory(String legal) throws IOException {
        File directory = licenseRegister.newFolder("group", "id", coordinates.artifactId, legal);
        writeLicenseInformationTo(directory, "LicenseText");
    }

    private void addKnownLicense(String licenseName, String licenseText) throws IOException {
        File apacheLicenseDirectory = licenseRegister.newFolder("well-known-licenses", licenseName);
        writeLicenseInformationTo(apacheLicenseDirectory, licenseText);
    }

    private void addMetaDataPointingToSubdirectory(GavCoordinates coordinates, String legal) throws IOException {
        writeMetaDataFor(coordinates, "sub-directory://" + legal + " <- version");
    }

    private void writeMetaDataFor(GavCoordinates coordinates, String metaData) throws IOException {
        File metaDataDirectory = licenseRegister.newFolder("group", "id", coordinates.artifactId);
        File mappingFile = new File(metaDataDirectory, "version-mapping");
        FileUtils.writeStringToFile(mappingFile, metaData, "UTF-8");
    }

    private void writeLicenseInformationTo(File directory, String licenseText) throws IOException {
        File licenseFile = new File(directory, "LICENSE.txt");
        licenseFile.createNewFile();
        FileUtils.writeStringToFile(licenseFile, licenseText, "UTF-8");
    }

    private ThirdPartyLicenseRegister licenseRegister() {
        return new ThirdPartyLicenseRegister(licenseRegister.getRoot());
    }
}
