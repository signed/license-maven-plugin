package org.codehaus.mojo.license.fetchlicenses;

public class LicenseObligations {

    public final GavCoordinates coordinates;
    public final Text license;

    public LicenseObligations(Text license) {
        this(null, license);
    }

    public LicenseObligations(GavCoordinates coordinates, Text license) {
        this.coordinates = coordinates;
        this.license = license;
    }
}
