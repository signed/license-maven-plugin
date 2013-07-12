package org.codehaus.mojo.license.fetchlicenses.repository;

import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.codehaus.mojo.license.fetchlicenses.LicenseLookupCallback;
import org.codehaus.mojo.license.fetchlicenses.LicenseObligations;
import org.codehaus.mojo.license.fetchlicenses.Text;

import java.util.Collections;

public class ThirdPartyLicenseRegister {

    private final VersionMappingLoader loader;
    private final LicenseReader licenseReader;

    public ThirdPartyLicenseRegister(VersionMappingLoader loader, LicenseReader licenseReader) {
        this.loader = loader;
        this.licenseReader = licenseReader;
    }

    public void lookup(final GavCoordinates coordinates, final LicenseLookupCallback callback) {
        VersionMapping mapping = loader.loadVersionMappingFor(coordinates, new LoaderCallback() {
            public void failedToLoadVersionMapping() {
                callback.couldNotParseMetaData(coordinates);
            }
        });
        if (mapping.coversVersion(coordinates.version)) {
            passLicenseInformationToCaller(coordinates, callback, mapping);
        } else {
            informCallerAboutMissingLicenseInformation(coordinates, callback);
        }
    }

    private void passLicenseInformationToCaller(GavCoordinates coordinates, LicenseLookupCallback callback, VersionMapping mapping) {
        Text license = licenseReader.readLicense(coordinates, mapping);
        callback.found(new LicenseObligations(coordinates, Collections.singletonList(license)));
    }

    private void informCallerAboutMissingLicenseInformation(GavCoordinates coordinates, LicenseLookupCallback callback) {
        callback.missingLicenseInformationFor(coordinates);
    }
}