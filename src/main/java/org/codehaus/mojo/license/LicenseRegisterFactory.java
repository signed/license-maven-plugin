package org.codehaus.mojo.license;

import org.codehaus.mojo.license.fetchlicenses.repository.FileRegisterStructure;
import org.codehaus.mojo.license.fetchlicenses.repository.LicenseReader;
import org.codehaus.mojo.license.fetchlicenses.repository.ThirdPartyLicenseRegister;
import org.codehaus.mojo.license.fetchlicenses.repository.VersionMappingLoader;

import java.io.File;

public class LicenseRegisterFactory {

    public ThirdPartyLicenseRegister erectThirdPartyLicenseRegister(File licensesRegisterRootDirectory) {
        FileRegisterStructure structure = new FileRegisterStructure(licensesRegisterRootDirectory);
        VersionMappingLoader loader = new VersionMappingLoader(structure);
        return new ThirdPartyLicenseRegister(loader, new LicenseReader(structure));
    }
}
