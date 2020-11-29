package ExpertSystem.model;

public class Rule extends AbstractKnowledge {

    public enum Section { FRAME, INCREASE, DECREASE, UNKNOWN }

    private Section section;
    private Fact then;

    public Rule() {
        super();
        section = Section.UNKNOWN;
    }

    public void setSection(String section) {
        if (section.equals("penalty-frame")) this.section = Section.FRAME;
        else this.section = Section.UNKNOWN;
    }

    public void setThen(Fact then) {
        this.then = then;
    }

    public Fact getImplication() {
        return then;
    }

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
