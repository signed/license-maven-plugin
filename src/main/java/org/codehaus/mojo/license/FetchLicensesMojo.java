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

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.shared.artifact.filter.PatternExcludesArtifactFilter;
import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.codehaus.mojo.license.fetchlicenses.LicenseLookupCallback;
import org.codehaus.mojo.license.fetchlicenses.LicenseObligations;
import org.codehaus.mojo.license.fetchlicenses.Licensee;
import org.codehaus.mojo.license.fetchlicenses.repository.ThirdPartyLicenseRegister;
import org.codehaus.mojo.license.model.ProjectLicenseInfo;

import java.io.File;
import java.util.List;

@Mojo(name = "fetch-licenses", requiresDependencyResolution = ResolutionScope.TEST,
        defaultPhase = LifecyclePhase.PACKAGE)
public class FetchLicensesMojo extends DownloadLicensesMojo {

    /**
     * The base directory for the third party license register that contains the license information.
     *
     * @since 1.6
     */
    @Parameter(alias = "thirdPartyLicensesRegister", defaultValue = "${project.build.directory}/licenses-register/")
    private File licensesRegisterRoot;

    /**
     * The base directory fot the third party license register that contains the license information
     * in maven file structure.
     *
     * @since 1.6
     */
    @Parameter(alias = "writeUsedLicensesTo", defaultValue = "${project.build.directory}/licenses")
    private File usedLicensesDirectory;

    /**
     * Do not look for license information for artifacts that match on of the supplied pattern.
     *
     * @since 1.6
     */
    @Parameter(alias = "ignoreDependencyArtifacts")
    private List<String> ignoreArtifacts;

    /**
     * Break the build if there is no license for a dependency artifact.
     *
     * @since 1.6
     */
    @Parameter(alias = "failBuildOnMissingLicense", defaultValue = "true")
    private boolean failBuildOnMissingLicense;


    @Override
    protected void downloadLicenses(ProjectLicenseInfo depProject, Artifact artifact) {
        final GavCoordinates coordinates = new GavCoordinates(depProject.getGroupId(), depProject.getArtifactId(), depProject.getVersion());

        PatternExcludesArtifactFilter ignoreArtifactsFilter = new PatternExcludesArtifactFilter(ignoreArtifacts, false);
        if (!ignoreArtifactsFilter.include(artifact)) {
            getLog().info("ignoring artifact: " + coordinates);
            return;
        }
        getLog().info("lookup license for: " + coordinates);

        ThirdPartyLicenseRegister licenseRepository = new LicenseRegisterFactory().erectThirdPartyLicenseRegister(licensesRegisterRoot);
        final Licensee licensee = new Licensee(usedLicensesDirectory);

        final boolean[] foundLicense = {false};

        licenseRepository.lookup(coordinates, new LicenseLookupCallback() {
            public void found(LicenseObligations obligations) {
                licensee.complyWith(obligations);
                foundLicense[0] = true;
            }

            public void missingLicenseInformationFor(GavCoordinates coordinates) {
                getLog().error("no license information for " + coordinates.toString());

            }

            public void couldNotParseMetaData(GavCoordinates coordinates) {
                getLog().error("could not parse metadata for " + coordinates.toString());
            }
        });

        if (!foundLicense[0] && failBuildOnMissingLicense) {
            throw new RuntimeException("could not find license for " + coordinates);
        }
    }

}