package org.codehaus.mojo.license.fetchlicenses.repository.json;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class RuleSplitter_Test {

    @Test
    public void anEmptyStringContainsNoRules() throws Exception {
        assertThat(splitUp(""), hasItems(""));
    }

    @Test
    public void aRuleStartsWithAnOpeningCurlyBracesAndEndsWithAnClosingCurlyBraces() throws Exception {
        assertThat(splitUp("{lo\nad}").get(0), is("{lo\nad}"));
    }

    @Test
    public void findMultipleRules() throws Exception {
        assertThat(splitUp("{load}\n{me}"), hasItems("{load}", "{me}"));
    }

    @Test
    public void reportMalformedChunksAsRule() throws Exception {
        assertThat(splitUp("{rule"), hasItems("{rule"));
    }

    @Test
    public void reportMalformedChunksAsRuleEvenWhenMixedWithValidRules() throws Exception {
        assertThat(splitUp("{rule}trash{{another rule}bogus}"), hasItems("{rule}", "trash{", "{another rule}", "bogus}"));
    }

    @Test
    public void ignoreWhiteSpaceTrash() throws Exception {
        assertThat(splitUp("{rule}\n\t {another rule} \t"), not(hasItems("\n\t ", " \t")));
    }

    private List<String> splitUp(String input) {
        return new RuleSplitter().split(input);
    }
}