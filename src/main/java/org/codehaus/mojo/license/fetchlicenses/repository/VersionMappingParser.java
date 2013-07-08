package org.codehaus.mojo.license.fetchlicenses.repository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VersionMappingParser {
    private final MappingBuilder builder;

    public VersionMappingParser(MappingBuilder builder) {
        this.builder = builder;
    }

    public void parseLine(String line) {
        Pattern protocolPattern = Pattern.compile("://([^\\s]+)\\s*<-");
        Matcher matcher = protocolPattern.matcher(line);
        matcher.find();

        String apache2 = matcher.group(1);

        builder.wellKnownLicense(apache2);

        String[] versions = line.split("<-")[1].trim().split(",");
        for (String version : versions) {
            builder.version(version.trim());
        }
    }
}