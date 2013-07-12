package org.codehaus.mojo.license.fetchlicenses.repository;

import org.codehaus.mojo.license.fetchlicenses.repository.json.BasePointer;
import org.codehaus.mojo.license.fetchlicenses.repository.json.JsonMappingRule;
import org.codehaus.mojo.license.fetchlicenses.repository.json.Pointer;

import java.util.LinkedHashSet;
import java.util.Set;

public class MappingRuleBuilder {
    private final Set<Pointer> pointers = new LinkedHashSet<Pointer>();
    private final Set<String> versions = new LinkedHashSet<String>();


    public void addVersion(String version) {
        versions.add(version);
    }

    public void addPointer(String pointerType, String path) {
        Pointer pointer = new BasePointer(pointerType, path);
        pointers.add(pointer);
    }

    public MappingRule createMappingRule() {
        return new JsonMappingRule(versions, pointers);
    }

    public void addLicensePointerTo(String licenseString) {
        addPointer("license", licenseString);
    }

    public void addPointerToWellKnown(String relativePath) {
        addLicensePointerTo("->well-known-license/" + relativePath);
    }
}
