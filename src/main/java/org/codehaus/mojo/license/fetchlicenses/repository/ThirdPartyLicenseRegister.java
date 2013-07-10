package org.codehaus.mojo.license.fetchlicenses.repository;

import org.apache.commons.io.FileUtils;
import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.codehaus.mojo.license.fetchlicenses.LicenseLookupCallback;
import org.codehaus.mojo.license.fetchlicenses.LicenseObligations;
import org.codehaus.mojo.license.fetchlicenses.Text;

import java.io.File;
import java.io.IOException;

public class ThirdPartyLicenseRegister {

    private final TextReader textReader = new TextReader();
    private final CoordinatesToPathTranslator translator;

    private final File wellKnownLicenseDirectory;

    private final VersionMappingLoader loader;

    public ThirdPartyLicenseRegister(File repositoryRoot) {
        this.wellKnownLicenseDirectory = new File(repositoryRoot, "well-known-licenses");
        loader = new VersionMappingLoader(repositoryRoot);
        translator = new CoordinatesToPathTranslator(repositoryRoot);
    }

    public void lookup(GavCoordinates coordinates, final LicenseLookupCallback callback) {
        File artifactDirectory = translator.artifactDirectory(coordinates);
        VersionMapping mapping = loader.loadVersionMapping(coordinates, wellKnownLicenseDirectory);

        if (!mapping.hasMappingForVersion(coordinates.version)) {
            callback.missingLicenseInformationFor(coordinates);
        } else {
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
            return new File(wellKnownLicenseDirectory, target.subDirectory);
        }else if(target instanceof SubDirectory) {
            return  new File(artifactDirectory, target.subDirectory);
        }
        throw new RuntimeException("not supported target");
    }

    public static class TextReader {
        public Text read(File file) {
            try {
                String string = FileUtils.readFileToString(file, "UTF-8");
                return new Text(string);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}