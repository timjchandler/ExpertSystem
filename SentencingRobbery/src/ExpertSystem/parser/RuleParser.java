package ExpertSystem.parser;

import ExpertSystem.model.Rule;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;

public class RuleParser extends Parser {

    /**
     * Constructor
     * Reads in and parses the file from the given location.
     * @param file  the filepath of the xml file to parse
     */
    public RuleParser(String file) {
        super(file);
        parse();
    }

    /**
     * Method
     * Calls the supers method. Relevant information is stored in the nodeList variable
     * the Document returned by super.parse() is unnecessary here and therefore is not kept.
     */
    private void parse() {
        super.parse("rule");
    }

    /**
     * Method
     * @return  An array list of all the rules
     */
    public ArrayList<Rule> getRules() {
        ArrayList<Rule> rules = new ArrayList<>();
        for (int idx = 0; idx < nodeList.getLength(); ++idx) {
            rules.add(interpretRule(nodeList.item(idx)));
        }
        return rules;
    }

    /**
     * Method
     * Interprets a rule from a given node. The section of the rule is set first, followed by the prerequisite
     * facts. Whether multiple facts are and or or is also set. Finally the implication of the rule is set
     * @param item  The node to interpret a rule from
     * @return      A complete rule
     */
    private Rule interpretRule(Node item) {
        Element currEle = (Element) item;
        Rule rule = new Rule();
        rule.setSection(getElementText(currEle, "section"));
        rule.addFact(parseRequirements(currEle, "if", rule));
        rule.setThen(parseFact(subElement(currEle, "then")));
        return rule;
    }
}
