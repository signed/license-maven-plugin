package org.codehaus.mojo.license;

/*
 * #%L
 * License Maven Plugin
 * %%
 * Copyright (C) 2010 - 2011 CodeLutin, Codehaus, Tony Chemit
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.codehaus.mojo.license.fetchlicenses.LicenseLookupCallback;
import org.codehaus.mojo.license.fetchlicenses.LicenseObligations;
import org.codehaus.mojo.license.fetchlicenses.Licensee;
import org.codehaus.mojo.license.fetchlicenses.repository.FileRegisterStructure;
import org.codehaus.mojo.license.fetchlicenses.repository.LicenseReader;
import org.codehaus.mojo.license.fetchlicenses.repository.ThirdPartyLicenseRegister;
import org.codehaus.mojo.license.fetchlicenses.repository.VersionMappingLoader;
import org.codehaus.mojo.license.fetchlicenses.repository.VersionMappingParserBuilder;
import org.codehaus.mojo.license.model.ProjectLicenseInfo;

import java.io.File;

@Mojo(name = "fetch-licenses", requiresDependencyResolution = ResolutionScope.TEST,
        defaultPhase = LifecyclePhase.PACKAGE)
public class FetchLicensesMojo extends DownloadLicensesMojo {

    /**
     * The base directory fot the third party license register that contains the license information
     * in maven file structure.
     *
     * @since 1.6
     */
    @Parameter(property = "thirdPartyLicensesRegister", defaultValue = "${project.build.directory}/licenses-register/")
    private File licensesRegisterRoot;

    /**
     * The base directory fot the third party license register that contains the license information
     * in maven file structure.
     *
     * @since 1.6
     */
    @Parameter(property = "writeUsedLicensesTo", defaultValue = "${project.build.directory}/licenses")
    private File usedLicensesDirectory;


    @Override
    protected void downloadLicenses(ProjectLicenseInfo depProject) {
        final GavCoordinates coordinates = new GavCoordinates(depProject.getGroupId(), depProject.getArtifactId(), depProject.getVersion());
        final FileRegisterStructure structure = new FileRegisterStructure(licensesRegisterRoot);
        VersionMappingParserBuilder versionMappingParserBuilder = new VersionMappingParserBuilder(structure);
        VersionMappingLoader loader = new VersionMappingLoader(structure, versionMappingParserBuilder);
        ThirdPartyLicenseRegister licenseRepository = new ThirdPartyLicenseRegister(loader, new LicenseReader(structure));
        final Licensee licensee = new Licensee(usedLicensesDirectory);

        getLog().info(" license lookup for: "+coordinates);


        licenseRepository.lookup(coordinates, new LicenseLookupCallback() {
            public void found(LicenseObligations obligations) {
                licensee.complyWith(obligations);
            }

            public void missingLicenseInformationFor(GavCoordinates coordinates) {
                getLog().error("no license information for " + coordinates.toString());
            }

            public void couldNotParseMetaData(GavCoordinates coordinates) {
                getLog().error("could not parse metadata for "+coordinates.toString());
            }
        });
    }
}