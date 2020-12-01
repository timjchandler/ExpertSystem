package tjc.rug.ExpertSystem.model;

import tjc.rug.ExpertSystem.parser.RuleParser;

import java.util.ArrayList;

public class State {

    private final ArrayList<Fact> facts;
    private final ArrayList<Rule> rules;

    /**
     * Reads in rules from the xml file via the parser, initialises the facts and implications lists
     * Sets other member variables to their defaults
     */
    public State() {
        rules = new RuleParser("/resources/knowledgebase/rules.xml").getRules();
        facts = new ArrayList<>();
    }

    /**
     * Adds a fact to the list of facts, if it is not already present
     * @param fact  The fact to add
     */
    public void addFact(Fact fact) {
        if (!facts.contains(fact)) facts.add(fact);
    }

    public void updateImplications() {
        updateImplications(facts.size());
    }

    public void updateImplications(int size) {
        for (Rule rule: rules) {
            if (rule.meetsRequirements(facts) &&
                    !facts.contains(rule.getImplication())) {
                facts.add(rule.getImplication());
            }
        }
        if (rules.size() <= size) return;
        System.out.println("check for infinite loop");
        updateImplications(rules.size());
    }

    public String getSentenceString() {
        return new Sentence(facts).toString();
    }

    /**
     * Gets the current calculated sentence
     * @return  The sentence in string form
     */
    public String getSentence() {
        updateImplications();
        return new Sentence(facts).getSentence();
    }

    /**
     * Gets the current list of obtained facts
     * @return  The list of facts
     */
    public ArrayList<Fact> getFacts() {
        return facts;
    }

}
