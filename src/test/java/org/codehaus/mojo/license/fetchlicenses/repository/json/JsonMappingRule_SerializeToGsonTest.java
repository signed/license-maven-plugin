package org.codehaus.mojo.license.fetchlicenses.repository.json;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

public class JsonMappingRule_SerializeToGsonTest {

    private final String json = "{\n" +
            "  \"versions\": [\"1.2\",\"2.3\", \"3\"],\n" +
            "  \"license\": \"/well-known-license/apache2\",\n" +
            "  \"notice\": \"NOTICE.txt\"\n" +
            "}";

    private final JsonMappingRuleParser parser = new JsonMappingRuleParser();

    @Test
    public void deserializeVersionsProperly() throws Exception {
        JsonMappingRule jsonMappingRule = parser.parseMappingRule(json);

        assertThat(jsonMappingRule.versions(), hasItems("1.2", "2.3", "3"));
    }

    @Test
    public void deserializePointersProperly() throws Exception {
        JsonMappingRule jsonMappingRule = parser.parseMappingRule(json);

        Pointer first = new BasePointer("license", "/well-known-license/apache2");
        BasePointer second = new BasePointer("notice", "NOTICE.txt");
        assertThat(jsonMappingRule.pointers(), hasItems(first, second));
    }
}