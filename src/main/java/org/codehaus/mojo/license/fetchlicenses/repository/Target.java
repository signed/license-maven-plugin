package org.codehaus.mojo.license.fetchlicenses.repository;

import java.io.File;

public class Target {

    public final File directory;

    public Target(File rootFolder, String subDirectory) {
        directory = new File(rootFolder, subDirectory);
    }
}