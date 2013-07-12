package org.codehaus.mojo.license.fetchlicenses.repository;

import org.codehaus.mojo.license.LicenseRegisterFactory;
import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.codehaus.mojo.license.fetchlicenses.LicenseLookupCallback;
import org.codehaus.mojo.license.fetchlicenses.LicenseObligations;
import org.codehaus.mojo.license.fetchlicenses.Text;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ThirdPartyLicenseRegister_Test {

    @Rule
    public final LicenseRegisterBuilder licenseRegisterBuilder = new LicenseRegisterBuilder();
    private final LicenseLookupCallback callback = mock(LicenseLookupCallback.class);
    private final GavCoordinates artifact = new GavCoordinates("group.id", "artifact", "version");


    @Test
    public void informCallerAboutMissingLicenseInformation() throws Exception {
        licenseRegister().lookup(artifact, callback);

        verify(callback).missingLicenseInformationFor(artifact);
    }

    @Test
    public void returnLicenseInformationInSubdirectoryToCaller() throws Exception {
        licenseRegisterBuilder.addMetaDataPointingToSubdirectory("legal/LICENSE.txt", artifact);
        licenseRegisterBuilder.putLicenseInformationIntoSubdirectory("legal/LICENSE.txt", "LicenseText", artifact);

        licenseRegister().lookup(artifact, callback);

        assertThat(returnedLicenseText(), is(text("LicenseText")));
    }

    @Test
    public void returnWellKnownLicenseInformationToCaller() throws Exception {
        licenseRegisterBuilder.addKnownLicense("apache/LICENSE.txt", "The Apache License");
        licenseRegisterBuilder.addMetadataPointingToWellKnownLicense("apache/LICENSE.txt", artifact);

        licenseRegister().lookup(artifact, callback);

        assertThat(returnedLicenseText(), is(text("The Apache License")));
    }

    private ThirdPartyLicenseRegister licenseRegister() {
        File root = licenseRegisterBuilder.getRoot();
        return new LicenseRegisterFactory().erectThirdPartyLicenseRegister(root);
    }

    private Text text(String licenseText) {
        return new Text(licenseText);
    }

    private Text returnedLicenseText() {
        ArgumentCaptor<LicenseObligations> captor = ArgumentCaptor.forClass(LicenseObligations.class);
        verify(callback).found(captor.capture());
        return captor.getValue().legalTexts.iterator().next();
    }

}
