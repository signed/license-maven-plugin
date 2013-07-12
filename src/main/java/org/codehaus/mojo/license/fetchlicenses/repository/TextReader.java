package org.codehaus.mojo.license.fetchlicenses.repository;

import org.apache.commons.io.FileUtils;
import org.codehaus.mojo.license.fetchlicenses.Text;

import java.io.File;
import java.io.IOException;

public class TextReader {
    public Text read(File file) {
        try {
            String string = FileUtils.readFileToString(file, "UTF-8");
            return new Text(file.getName(), string);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}