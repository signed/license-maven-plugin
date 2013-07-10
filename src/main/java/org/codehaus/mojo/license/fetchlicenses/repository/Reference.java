package org.codehaus.mojo.license.fetchlicenses.repository;

import java.io.File;

public abstract class Reference {

    public final File directory;
    public final String subDirectory;

    public Reference(File rootFolder, String subDirectory) {
        this.subDirectory = subDirectory;
        directory = new File(rootFolder, subDirectory);
    }

    public  abstract <T> T accept(ReferenceVisitor<T> visitor);
}