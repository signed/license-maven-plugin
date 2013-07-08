package org.codehaus.mojo.license.fetchlicenses.repository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VersionMappingParser {
    public static final String WellKnownLicenseProtocol = "well-known-license";
    public static final String SubDirectoryProtocol = "sub-directory";
    public static final String lineFormatRegex = "(^" + WellKnownLicenseProtocol + "|" + SubDirectoryProtocol + ")://(.+)<-(.+)$";

    private final static Pattern locationPattern = Pattern.compile("://([^\\s]+)\\s*<-");
    private final MappingBuilder builder;

    public VersionMappingParser(MappingBuilder builder) {
        this.builder = builder;
    }

    public void parseLine(String line) {
        if( !line.matches(lineFormatRegex)){
            return;
        }

        Matcher matcher = locationPattern.matcher(line);
        matcher.find();

        String location = matcher.group(1);
        if (line.startsWith(WellKnownLicenseProtocol + "://")) {
            builder.wellKnownLicense(location);
        } else if (line.startsWith(SubDirectoryProtocol + "://")) {
            builder.subDirectory(location);
        }

        String[] versions = line.split("<-")[1].trim().split(",");
        for (String version : versions) {
            builder.version(version.trim());
        }
    }
}