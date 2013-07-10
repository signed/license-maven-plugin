package org.codehaus.mojo.license.fetchlicenses.repository;

import org.apache.commons.io.FileUtils;
import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.junit.rules.ExternalResource;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

public class LicenseRegisterBuilder extends ExternalResource{


    public final TemporaryFolder licenseRegister = new TemporaryFolder();

    @Override
    protected void before() throws Throwable {
        licenseRegister.create();
    }

    @Override
    protected void after() {
        licenseRegister.delete();
    }

    public void putLicenseInformationIntoSubdirectory(String legal, String licenseText, GavCoordinates coordinates) throws IOException {
        File directory = licenseRegister.newFolder("group", "id", coordinates.artifactId, legal);
        writeLicenseInformationTo(directory, licenseText);
    }

    public void addMetaDataPointingToSubdirectory(String legal, GavCoordinates coordinates) throws IOException {
        writeMetaDataFor(coordinates, "sub-directory://" + legal + " <- version");
    }

    public void writeMetaDataFor(GavCoordinates coordinates, String metaData) throws IOException {
        File metaDataDirectory = licenseRegister.newFolder("group", "id", coordinates.artifactId);
        File mappingFile = new File(metaDataDirectory, "version-mapping");
        FileUtils.writeStringToFile(mappingFile, metaData, "UTF-8");
    }

    public File getRoot() {
        return licenseRegister.getRoot();
    }

    private void writeLicenseInformationTo(File directory, String licenseText) throws IOException {
        File licenseFile = new File(directory, "LICENSE.txt");
        licenseFile.createNewFile();
        FileUtils.writeStringToFile(licenseFile, licenseText, "UTF-8");
    }

    void addKnownLicense(String licenseName, String licenseText) throws IOException {
        File apacheLicenseDirectory = licenseRegister.newFolder("well-known-licenses", licenseName);
        writeLicenseInformationTo(apacheLicenseDirectory, licenseText);
    }

    public void addMetadataPointingToWellKnownLicense(String licenseName, GavCoordinates coordinates) throws IOException {
        writeMetaDataFor(coordinates, "well-known-license://" + licenseName + " <- " + coordinates.version);
    }
}