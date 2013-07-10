package org.codehaus.mojo.license.fetchlicenses.repository;

import java.io.File;

public class WellKnownLicense extends Reference {

    public WellKnownLicense(File rootFolder, String subDirectory) {
        super(rootFolder, subDirectory);
    }

    @Override
    public <T> T accept(ReferenceVisitor<T> visitor) {
        return visitor.wellKnownLicense(this);
    }
}