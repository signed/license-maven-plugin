package org.codehaus.mojo.license.fetchlicenses.repository;

public interface ReferenceVisitor<ReturnValue> {
    ReturnValue wellKnownLicense(WellKnownLicense wellKnownLicense);

    ReturnValue subDirectory(SubDirectory subDirectory);
}
