package tjc.rug.ExpertSystem.model;

import tjc.rug.ExpertSystem.parser.OutputParser;
import tjc.rug.ExpertSystem.parser.RuleParser;

import java.util.ArrayList;

public class State {

    // TODO: CHECK everything still works with facts/rules static
    private static ArrayList<Fact> facts;
    private static ArrayList<Rule> rules;
    private static ArrayList<Output> outputs = null;

    /**
     * Reads in rules from the xml file via the parser, initialises the facts and implications lists
     * Sets other member variables to their defaults
     */
    public State() {
        rules = new RuleParser("/resources/knowledgebase/rules.xml").getRules();
        outputs = new OutputParser("/resources/knowledgebase/outputs.xml").getOutputs();
//        for (Output o: outputs) System.out.println(o.toString());
        facts = new ArrayList<>();
    }

    /**
     * Adds a fact to the list of facts, if it is not already present
     * @param fact  The fact to add
     */
    public void addFact(Fact fact) {
        if (!facts.contains(fact)) facts.add(fact);
    }

    /**
     * Overloaded method.
     * Passes to the recursive updateImplications function with the size of the facts ArrayList
     */
    public void updateImplications() {
        updateImplications(facts.size());
    }

    /**
     * Overloaded method. Recursive. Updates the known facts, repeating until an iteration where no new facts are added.
     * @param size  The size of the previous iterations ArrayList of facts
     */
    public void updateImplications(int size) {
        for (Rule rule: rules) {
            if (rule.meetsRequirements(facts) &&
                    !facts.contains(rule.getImplication())) {
                facts.add(rule.getImplication());
            }
        }
        if (rules.size() <= size) return;
        updateImplications(rules.size());
    }

    /**
     * Returns the sentence as a string.
     * @return  The string representation of the sentence
     */
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

    public float[] getSentenceBase() {
        updateImplications();
        return new Sentence(facts).getBase();
    }

    public int getSentenceSegment() {
        updateImplications();
        return new Sentence(facts).getSegment();
    }

    public static ArrayList<Output> getOutputs() {
        ArrayList<Output> out = new ArrayList<>();
        for (Output o: outputs) {
            if (o.meetsRequirements(facts)) out.add(o);
        }
        return out;
    }

    public ArrayList<Fact> getImplications() {
        updateImplications();
        return facts;   // TODO: Check if this is redundant (maybe just add update to get facts?)
    }

    /**
     * Gets the current list of obtained facts
     * @return  The list of facts
     */
    public ArrayList<Fact> getFacts() {
        return facts;
    }

    public float[] getModBase() {
        return new Sentence(facts).getUpdatedSentenceFrame();
    }
}
