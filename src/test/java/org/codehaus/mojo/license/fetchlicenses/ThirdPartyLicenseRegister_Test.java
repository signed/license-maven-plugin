package org.codehaus.mojo.license.fetchlicenses;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.ArgumentCaptor;

import java.io.File;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ThirdPartyLicenseRegister_Test {

    @Rule
    public final TemporaryFolder licenseRegister = new TemporaryFolder();
    private final LicenseLookupCallback callback = mock(LicenseLookupCallback.class);
    private final GavCoordinates coordinates = new GavCoordinates("groupid", "artifact", "version");

    @Test
    public void informCallerAboutMissingLicenseInformation() throws Exception {
        licenseRegister().lookup(coordinates, callback);

        verify(callback).missingLicenseInformationFor(coordinates);
    }

    @Test
    public void returnAvailableLicenseInformationToCaller() throws Exception {
        File directory = licenseRegister.newFolder("groupid", "artifact", "version");
        new File(directory, "LICENSE.txt").createNewFile();

        licenseRegister().lookup(coordinates, callback);

        ArgumentCaptor<LicenseObligations> captor = ArgumentCaptor.forClass(LicenseObligations.class);
        verify(callback).found(captor.capture());

        assertThat(captor.getValue(), notNullValue());
    }

    private ThirdPartyLicenseRegister licenseRegister() {
        return new ThirdPartyLicenseRegister(licenseRegister.getRoot());
    }
}
