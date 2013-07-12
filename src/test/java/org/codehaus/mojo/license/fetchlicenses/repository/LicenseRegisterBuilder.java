package org.codehaus.mojo.license.fetchlicenses.repository;

import org.apache.commons.io.FileUtils;
import org.codehaus.mojo.license.fetchlicenses.GavCoordinates;
import org.codehaus.mojo.license.fetchlicenses.repository.json.VersionMappingJson;
import org.junit.rules.ExternalResource;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

public class LicenseRegisterBuilder extends ExternalResource {

    private final MappingRuleBuilder mappingRuleBuilder = new MappingRuleBuilder();
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
        writeContentInto(licenseFile, licenseText);
    }

    public void addLicenseMetaDataPointingToSubdirectory(String subDirectory, GavCoordinates coordinates) throws IOException {
        mappingRuleBuilder.addVersion(coordinates.version);
        mappingRuleBuilder.addLicensePointerTo(subDirectory);
    }
    public void putNoticeInformationIntoSubdirectory(String relativeLicensePath, String noticeText, GavCoordinates coordinates) throws IOException {
        File noticeFile = new File(structure.artifactDirectoryFor(coordinates), relativeLicensePath);
        writeContentInto(noticeFile, noticeText);
    }


    public void addNoticeMetaDataPointingToSubdirectory(String subDirectory, GavCoordinates coordinates) throws IOException {
        mappingRuleBuilder.addVersion(coordinates.version);
        mappingRuleBuilder.addPointer("notice", subDirectory);
    }

    public void writeMetaData(GavCoordinates coordinates) throws IOException {
        String metadata = new VersionMappingJson(null).toJson(mappingRuleBuilder.createMappingRule());
        writeMetaDataFor(coordinates, metadata);
    }

    public void writeMetaDataFor(GavCoordinates coordinates, String metaData) throws IOException {
        File metaDataDirectory = structure.artifactDirectoryFor(coordinates);
        File mappingFile = new File(metaDataDirectory, "version-mapping");
        FileUtils.writeStringToFile(mappingFile, metaData, "UTF-8");
    }

    public File getRoot() {
        return licenseRegister.getRoot();
    }

    public void addKnownLicense(String relativePath, String licenseText) throws IOException {
        File licenseFile = new File(structure.getWellKnownLicenseDirectory(), relativePath);
        writeContentInto(licenseFile, licenseText);
    }

    public void addMetadataPointingToWellKnownLicense(final String relativePath, GavCoordinates coordinates) throws IOException {
        mappingRuleBuilder.addVersion(coordinates.version);
        mappingRuleBuilder.addPointerToWellKnown(relativePath);
    }

    private void writeContentInto(File licenseFile, String content) throws IOException {
        licenseFile.getParentFile().mkdirs();
        licenseFile.createNewFile();
        FileUtils.writeStringToFile(licenseFile, content, "UTF-8");
    }
}