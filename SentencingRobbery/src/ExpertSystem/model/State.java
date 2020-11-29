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

    public State() {
        rules = new RuleParser("knowledgebase/rules.xml").getRules();
        facts = new ArrayList<>();
        implications = new ArrayList<>();
        currentBase = new float[2];
        recentlyUpdated = false;
        segment = 0;
    }

    public void addFact(Fact fact) {
        if (!facts.contains(fact)) facts.add(fact);
        recentlyUpdated = false;
    }

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

    private void calculateSegment(Fact fact) {
        if (fact.getName().equals("segment")) {
            if (fact.equals(new Fact("segment", "light"))) segment = Math.max(1, segment);
            else if (fact.equals(new Fact("segment", "medium"))) segment = Math.max(2, segment);
            else if (fact.equals(new Fact("segment", "severe"))) segment = Math.max(3, segment);
            else if (fact.equals(new Fact("segment", "very-severe"))) segment = Math.max(4, segment);
        }
    }

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

    private float[] getRecommendation() {
        if (segment == 0) return currentBase;
        float[] out = currentBase;
        float diff = out[1] - out[0];
        out[0] += (diff / 4) * (segment - 1);
        out[1] = out[0] + (diff / 4) * segment;
        return out;
    }

    public String getSentence() {
        return calculateSentence();
    }

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

    public ArrayList<Fact> getFacts() {
        return facts;
    }

}
