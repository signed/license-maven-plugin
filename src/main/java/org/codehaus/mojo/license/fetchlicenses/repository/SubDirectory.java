package org.codehaus.mojo.license.fetchlicenses.repository;

import java.io.File;

public class SubDirectory extends Target {
    public SubDirectory(File rootFolder, String subDirectory) {
        super(rootFolder, subDirectory);
    }
}
