package org.codehaus.mojo.license.fetchlicenses;

public class LicenseObligations {

    public final GavCoordinates coordinates;
    public final Iterable<Text> legalTexts;

    public LicenseObligations(GavCoordinates coordinates, Iterable<Text> legalTexts) {
        this.coordinates = coordinates;
        this.legalTexts = legalTexts;
    }
}
