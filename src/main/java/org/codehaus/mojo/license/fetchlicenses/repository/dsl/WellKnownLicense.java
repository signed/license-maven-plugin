package org.codehaus.mojo.license.fetchlicenses.repository.dsl;

import org.codehaus.mojo.license.fetchlicenses.repository.Reference;
import org.codehaus.mojo.license.fetchlicenses.repository.ReferenceVisitor;

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