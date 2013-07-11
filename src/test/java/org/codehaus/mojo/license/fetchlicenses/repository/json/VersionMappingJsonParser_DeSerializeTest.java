package org.codehaus.mojo.license.fetchlicenses.repository.json;

import org.codehaus.mojo.license.fetchlicenses.repository.MappingRule;
import org.codehaus.mojo.license.fetchlicenses.repository.RuleProductionListener;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class VersionMappingJsonParser_DeSerializeTest {

    private final String singleRule = "{\n" +
            "  \"versions\": [\"1.2\",\"2.3\", \"3\"],\n" +
            "  \"license\": \"/well-known-license/apache2\",\n" +
            "  \"notice\": \"NOTICE.txt\"\n" +
            "}";

    private final List<MappingRule> foundRules = new ArrayList<MappingRule>();
    private final RuleProductionListener listener = new RuleProductionListener() {
        public void produced(MappingRule rule) {
            foundRules.add(rule);
        }
    };
    private final VersionMappingJsonParser parser = new VersionMappingJsonParser(listener);

    @Test
    public void deserializeVersionsProperly() throws Exception {
        JsonMappingRule jsonMappingRule = parser.parseMappingRule(singleRule);

        assertThat(jsonMappingRule.versions(), hasItems("1.2", "2.3", "3"));
    }

    @Test
    public void deserializePointersProperly() throws Exception {
        JsonMappingRule jsonMappingRule = parser.parseMappingRule(singleRule);

        Pointer first = new BasePointer("license", "/well-known-license/apache2");
        BasePointer second = new BasePointer("notice", "NOTICE.txt");
        assertThat(jsonMappingRule.pointers(), hasItems(first, second));
    }

    @Test
    public void informTheListenerAboutEachProducedRule() throws Exception {
        parser.parseMapping(singleRule + " \n \t \n " + singleRule);

        assertThat(foundRules.size(), is(2));
    }
}