package org.codehaus.mojo.license.fetchlicenses.repository.json;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuleSplitter {

    public static final String MarkerUnlikelyToBeFoundInThePayload = "\\|@\\|";
    public static final String RulePattern = "\\{[^\\{\\}]+\\}";

    public List<String> split(String ruleString) {
        List<String> result = new ArrayList<String>();
        if( "".equals(ruleString)){
            result.add(ruleString);
            return result;
        }

        extractAndAddWellFormedRuleTos(result, ruleString);
        extractAndAddChunkTo(result, ruleString);

        return result;
    }

    private void extractAndAddWellFormedRuleTos(List<String> rules, String ruleString) {
        Pattern pattern = Pattern.compile("("+RulePattern+")");
        Matcher matcher = pattern.matcher(ruleString);

        while (matcher.find()) {
            String group = matcher.group(0);
            rules.add(group);
        }
    }

    private void extractAndAddChunkTo(List<String> rules, String ruleString) {
        String result = ruleString.replaceAll(RulePattern, MarkerUnlikelyToBeFoundInThePayload);
        String[] split = result.split(MarkerUnlikelyToBeFoundInThePayload);

        for(int part=0; part < split.length; ++part) {
            String current = split[part];
            if(!current.trim().isEmpty()){
                rules.add(part, current);
            }
        }
    }
}