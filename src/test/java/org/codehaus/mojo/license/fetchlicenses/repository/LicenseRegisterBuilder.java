package org.codehaus.mojo.license.fetchlicenses.repository;

import org.apache.commons.io.FileUtils;
import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.junit.rules.ExternalResource;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

public class LicenseRegisterBuilder extends ExternalResource{

    public final TemporaryFolder licenseRegister = new TemporaryFolder();
    private FileRegisterStructure structure;

    @Override
    protected void before() throws Throwable {
        licenseRegister.create();
        structure = new FileRegisterStructure(licenseRegister.getRoot());
    }

    @Override
    protected void after() {
        licenseRegister.delete();
    }

    public void putLicenseInformationIntoSubdirectory(String relativeLicensePath, String licenseText, GavCoordinates coordinates) throws IOException {
        File licenseFile = new File(structure.artifactDirectoryFor(coordinates), relativeLicensePath);
        writeLicenseInformationInto(licenseFile, licenseText);
    }

    public void addLicenseMetaDataPointingToSubdirectory(String subDirectory, GavCoordinates coordinates) throws IOException {
        String metadata = fillMetaDataTemplate(subDirectory, coordinates.version);
        writeMetaDataFor(coordinates, metadata);
    }

    public void putNoticeInformationIntoSubdirectory(String relativeLicensePath, String noticeText, GavCoordinates coordinates) throws IOException {
        File noticeFile = new File(structure.artifactDirectoryFor(coordinates), relativeLicensePath);
        writeLicenseInformationInto(noticeFile, noticeText);
    }

    public void addNoticeMetaDataPointingToSubdirectory(String subDirectory, GavCoordinates coordinates) throws IOException {
        String metadata = fillMetaDataTemplate(subDirectory, coordinates.version);
        writeMetaDataFor(coordinates, metadata);
    }

    private String fillMetaDataTemplate(String licenseString, String version) {
        String pointerType = "license";
        return fillMetaData(licenseString, version, pointerType);
    }

    private String fillMetaDataTemplateForNotice(String licenseString, String version) {
        String pointerType = "notice";
        return fillMetaData(licenseString, version, pointerType);
    }

    private String fillMetaData(String licenseString, String version, String pointerType) {
        return "{ \n" +
                   "\"versions\" : [\""+ version +"\"] ,\n" +
                   "\"" + pointerType + "\" : \"" +licenseString+"\"\n" +
               "}";
    }

    public void writeMetaDataFor(GavCoordinates coordinates, String metaData) throws IOException {
        File metaDataDirectory = licenseRegister.newFolder("group", "id", coordinates.artifactId);
        File mappingFile = new File(metaDataDirectory, "version-mapping");
        FileUtils.writeStringToFile(mappingFile, metaData, "UTF-8");
    }

    public File getRoot() {
        return licenseRegister.getRoot();
    }

    private void writeLicenseInformationInto(File licenseFile, String licenseText) throws IOException {
        licenseFile.getParentFile().mkdirs();
        licenseFile.createNewFile();
        FileUtils.writeStringToFile(licenseFile, licenseText, "UTF-8");
    }

    public void addKnownLicense(String relativePath, String licenseText) throws IOException {
        File licenseFile = new File(structure.getWellKnownLicenseDirectory(), relativePath);
        writeLicenseInformationInto(licenseFile, licenseText);
    }

    public void addMetadataPointingToWellKnownLicense(final String relativePath, GavCoordinates coordinates) throws IOException {
        String metaData = fillMetaDataTemplate("->well-known-license/" + relativePath, coordinates.version);
        writeMetaDataFor(coordinates, metaData);
    }
}