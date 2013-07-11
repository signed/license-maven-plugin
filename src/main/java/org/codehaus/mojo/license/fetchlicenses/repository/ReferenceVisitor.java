package org.codehaus.mojo.license.fetchlicenses.repository;

import org.codehaus.mojo.license.fetchlicenses.repository.dsl.SubDirectory;
import org.codehaus.mojo.license.fetchlicenses.repository.dsl.WellKnownLicense;

public interface ReferenceVisitor<ReturnValue> {
    ReturnValue wellKnownLicense(WellKnownLicense wellKnownLicense);

    ReturnValue subDirectory(SubDirectory subDirectory);
}
