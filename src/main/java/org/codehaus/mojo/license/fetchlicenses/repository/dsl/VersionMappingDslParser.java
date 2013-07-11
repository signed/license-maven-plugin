package org.codehaus.mojo.license.fetchlicenses.repository.dsl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

public class VersionMappingDslParser implements VersionMappingParser {
    public static final String WellKnownLicenseProtocol = "well-known-license";
    public static final String SubDirectoryProtocol = "sub-directory";
    public static final String lineFormatRegex = format("(^%s|%s)://(.+)<-(.+)$", WellKnownLicenseProtocol, SubDirectoryProtocol);

    private final static Pattern locationPattern = Pattern.compile("://([^\\s]+)\\s*<-");
    private final VersionMappingBuilder builder;

    public VersionMappingDslParser(VersionMappingBuilder builder) {
        this.builder = builder;
    }

    public void parseMapping(String mappingsAsString) {
        for (String line : mappingsAsString.split("\n")) {
            parseLine(line);
        }
    }

    public void parseLine(String line) {
        if( !line.matches(lineFormatRegex)){
            return;
        }

        if (line.startsWith(protocolHeaderFor(WellKnownLicenseProtocol))) {
            builder.wellKnownLicense(extractTargetFrom(line));
        } else if (line.startsWith(protocolHeaderFor(SubDirectoryProtocol))) {
            builder.subDirectory(extractTargetFrom(line));
        }

        String[] versions = line.split("<-")[1].trim().split(",");
        for (String version : versions) {
            builder.version(version.trim());
        }
        builder.build();
    }

    private String extractTargetFrom(String line) {
        Matcher matcher = locationPattern.matcher(line);
        matcher.find();
        return matcher.group(1);
    }

    private String protocolHeaderFor(String protocol) {
        return protocol + "://";
    }
}