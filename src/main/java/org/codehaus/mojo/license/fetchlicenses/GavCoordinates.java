package org.codehaus.mojo.license.fetchlicenses;

public class GavCoordinates {
    public final String groupId;
    public final String artifactId;
    public final String version;

    public GavCoordinates(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    @Override
    public String toString() {
        return String.format("%s:%s:%s", groupId, artifactId, version);
    }
}
