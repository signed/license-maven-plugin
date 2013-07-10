package org.codehaus.mojo.license.fetchlicenses.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.codehaus.mojo.license.fetchlicenses.repository.dsl.VersionMappingRule;
import org.junit.Test;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class VersionMappingRule_Test {

    @Test
    public void testName() throws Exception {
        Set<String> version = new HashSet<String>();
        version.add("1.0");
        version.add("1.5");
        version.add("3.0");
        MappingRule mappingRule = new VersionMappingRule(version, new Reference(new File(""), "sub"));

        Gson gson = new GsonBuilder().create();
        System.out.println(gson.toJson(mappingRule));
    }
}
