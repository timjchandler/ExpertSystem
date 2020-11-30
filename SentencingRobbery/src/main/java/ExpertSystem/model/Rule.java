package main.java.ExpertSystem.model;

public class Rule extends AbstractKnowledge {

    public enum Section { FRAME, INCREASE, DECREASE, UNKNOWN }

    private Section section;
    private Fact then;

    /**
     * Calls the super constructor and sets the current section to Unknown
     */
    public Rule() {
        super();
        section = Section.UNKNOWN;
    }

    /**
     * Sets the section that the rule applies to
     * Currently only implemented for FRAME and unknown
     * @param section   The section to set
     */
    public void setSection(String section) {
        if (section.equals("penalty-frame")) this.section = Section.FRAME;
        else this.section = Section.UNKNOWN;
    }

    /**
     * Set the then variable, representing the fact that this rule implies
     * @param then  The fact to set
     */
    public void setThen(Fact then) {
        this.then = then;
    }

    /**
     * Get the implied fact
     * @return  The implied fact
     */
    public Fact getImplication() {
        return then;
    }

    /**
     * Returns a representation of the rule as a string
     * @return  The string representation of the rule
     */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("Section:\t").append(section).append("\n");
        String andOr = reqIsAnd ? "\tAND\n" : "\tOR\n";
        for (Fact fact: requiredFacts) {
            out.append("IF ").append(fact.toString()).append("\n");
            if (requiredFacts.indexOf(fact) < requiredFacts.size() - 1) {
                out.append(andOr);
            }
        }
        out.append("\tTHEN\n").append(then.toString()).append("\n\n");
        return out.toString();
    }
}
