package org.codehaus.mojo.license.fetchlicenses;

public interface LicenseLookupCallback {
    void found(LicenseObligations data);

    void missingLicenseInformationFor(GavCoordinates coordinates);

    void couldNotParseMetaData(GavCoordinates coordinates);
}
