package org.codehaus.mojo.license.fetchlicenses.repository;

import java.io.File;

public class Target {

    public final File directory;
    public final String subDirectory;

    public Target(File rootFolder, String subDirectory) {
        this.subDirectory = subDirectory;
        directory = new File(rootFolder, subDirectory);
    }
}