package org.codehaus.mojo.license.fetchlicenses.repository;

import java.io.File;

public class WellKnownLicense extends Target{

    public WellKnownLicense(File rootFolder, String subDirectory) {
        super(rootFolder, subDirectory);
    }
}
