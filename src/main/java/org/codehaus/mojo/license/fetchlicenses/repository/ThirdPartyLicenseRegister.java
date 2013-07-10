package org.codehaus.mojo.license.fetchlicenses.repository;

import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.codehaus.mojo.license.fetchlicenses.LicenseLookupCallback;
import org.codehaus.mojo.license.fetchlicenses.LicenseObligations;
import org.codehaus.mojo.license.fetchlicenses.Text;

import java.io.File;

public class ThirdPartyLicenseRegister {

    private final TextReader textReader = new TextReader();
    private final FileRegisterStructure structure;

    private final VersionMappingLoader loader;

    public ThirdPartyLicenseRegister(File repositoryRoot) {
        structure = new FileRegisterStructure(repositoryRoot);
        loader = new VersionMappingLoader(structure);
    }

    public void lookup(GavCoordinates coordinates, final LicenseLookupCallback callback) {
        VersionMapping mapping = loader.loadVersionMapping(coordinates);
        if (!mapping.hasMappingForVersion(coordinates.version)) {
            callback.missingLicenseInformationFor(coordinates);
        } else {
            File artifactDirectory = structure.artifactDirectoryFor(coordinates);
            Text license = readLicense(coordinates, mapping, artifactDirectory);
            callback.found(new LicenseObligations(coordinates, license));
        }
    }

    private Text readLicense(GavCoordinates coordinates, VersionMapping mapping, File artifactDirectory) {
        Target target = mapping.target(coordinates.version);
        File root = resolveTarget(artifactDirectory, target);
        File licenseFileIn = new File(root, "LICENSE.txt");
        return textReader.read(licenseFileIn);
    }

    private File resolveTarget(File artifactDirectory, Target target) {
        if( target instanceof WellKnownLicense) {
            return new File(structure.getWellKnownLicenseDirectory(), target.subDirectory);
        }else if(target instanceof SubDirectory) {
            return  new File(artifactDirectory, target.subDirectory);
        }
        throw new RuntimeException("not supported target");
    }
}