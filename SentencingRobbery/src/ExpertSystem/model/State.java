package ExpertSystem.model;

import ExpertSystem.parser.RuleParser;

import java.util.ArrayList;

public class State {

    private final ArrayList<Fact> facts;
    private final ArrayList<Fact> implications;
    private final ArrayList<Rule> rules;
    private float[] currentBase;
    private int segment;
    private boolean recentlyUpdated;

    /**
     * Constructor
     * Reads in rules from the xml file via the parser, initialises the facts and implications lists
     * Sets other member variables to their defaults
     */
    public State() {
        rules = new RuleParser("knowledgebase/rules.xml").getRules();
        facts = new ArrayList<>();
        implications = new ArrayList<>();
        currentBase = new float[2];
        recentlyUpdated = false;
        segment = 0;
    }

    /**
     * Method
     * Adds a fact to the list of facts, if it is not already present
     * @param fact  The fact to add
     */
    public void addFact(Fact fact) {
        if (!facts.contains(fact)) facts.add(fact);
        recentlyUpdated = false;
    }

    /**
     * Method
     * Updates the list of implications by applying rules to the facts obtained through
     * questions
     */
    private void updateImplications() {
        if (recentlyUpdated) {
            System.out.println("Already updated");
            return;
        }
        System.out.println("Updating");
        for (Rule rule: rules) {
            if (rule.meetsRequirements(facts) &&
                    !implications.contains(rule.getImplication())) {
                implications.add(rule.getImplication());
            }
        }
        recentlyUpdated = true;
    }

    /**
     * Method
     * Calculates the current sentence recommendation from the list of implications
     * @return  A string of the current sentence
     */
    private String calculateSentence() {
        updateImplications();
        float multiplier = 1;
        for (int idx = 0; idx < 2; ++idx) currentBase[idx] = 0;
        for (Fact fact: implications) {
            if (fact.getImplication() == Fact.Implication.BASE) {
                currentBase = fact.adjustBaseTime(currentBase);
            } else if (fact.getImplication() == Fact.Implication.MULTI) {
                multiplier = fact.adjustMultiplier(multiplier);
            }
            else calculateSegment(fact);
        }
        for (int idx = 0; idx < 2; ++idx) currentBase[idx] *= multiplier;
        return tidySentence();
    }

    /**
     * Method
     * Sets the segment of the sentence to select, based on the severity of a given fact
     * @param fact  The fact to calculate from
     */
    private void calculateSegment(Fact fact) {
        if (fact.getName().equals("segment")) {
            if (fact.equals(new Fact("segment", "light"))) segment = Math.max(1, segment);
            else if (fact.equals(new Fact("segment", "medium"))) segment = Math.max(2, segment);
            else if (fact.equals(new Fact("segment", "severe"))) segment = Math.max(3, segment);
            else if (fact.equals(new Fact("segment", "very-severe"))) segment = Math.max(4, segment);
        }
    }

    /**
     * Method
     * Tidies the sentence to a round number of years/months
     * @return  A string of the tidied sentence
     */
    private String tidySentence() {
        StringBuilder out = new StringBuilder("From ");
        float[] recommendation = getRecommendation();
        boolean first = true;
        for (float f: recommendation) {
            System.out.println(f);
            int weight = f < 2 ? 4 : 2;
            f = Math.min(((float) Math.round(f * weight) / weight), 20);
            int years = (int) f;
            int months = (int) ((f - years) * 12);
            if (years > 0) out.append(years).append(" years ");
            if (years > 0 && months > 0) out.append("and ");
            if (months > 0) out.append(months).append(" months");
            if (first) {
                out.append(" to ");
                first = false;
            }
        }
        return out.toString();
    }

    /**
     * Method
     * Gets the recommended sentence based on the current sentence and segment (if applicable)
     * @return      The segment of a sentence as two floats
     */
    private float[] getRecommendation() {
        if (segment == 0) return currentBase;
        float[] out = currentBase;
        float diff = out[1] - out[0];
        out[1] = out[0] + (diff / 4) * segment;
        out[0] += (diff / 4) * (segment - 1);
        return out;
    }

    /**
     * Method
     * Gets the current calculated sentence
     * @return  The sentence in string form
     */
    public String getSentence() {
        return calculateSentence();
    }

    /**
     * Override Method
     * Converts the current state to a string representation
     * @return  The state in string representation
     */
    @Override
    public String toString() {
        updateImplications();
        StringBuilder out = new StringBuilder();
        String line = "\n----------------------------\n";
        out.append(line).append("CURRENT SENTENCE").append(line);
        out.append(calculateSentence());
        out.append(line).append("WHAT IS KNOWN").append(line);
        for (Fact fact: facts) out.append(fact.toString());
        out.append(line).append("WHAT THIS ENTAILS").append(line);
        for (Fact fact: implications) out.append(fact.toString());
        return out.toString();
    }

    /**
     * Method
     * Gets the current list of obtained facts
     * @return  The list of facts
     */
    public ArrayList<Fact> getFacts() {
        return facts;
    }

}
