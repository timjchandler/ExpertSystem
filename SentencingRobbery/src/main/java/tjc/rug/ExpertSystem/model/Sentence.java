package tjc.rug.ExpertSystem.model;

import com.sun.tools.javac.util.List;

import java.util.ArrayList;
import java.util.Arrays;

public class Sentence {

    private static ArrayList<String> asked = null;      // TODO: use questions instead so that more info can be gathered
    private static ArrayList<String> answered = null;
    private Fact.Implication completion;
    private ArrayList<Fact> facts;
    private final float[] base = new float[2];
    private int segment = 0;
    private int maxLength = 20; // Placeholder
    private float multiUp = 1;
    private float multiDown = 1;

    public Sentence(String question, String answer) {
        this(question, new ArrayList<>(List.of(answer)));
    }

    public Sentence(String question, ArrayList<String> answers) {
        if (asked == null) {
            asked = new ArrayList<>();
            answered = new ArrayList<>();
        }
        for (String answer: answers) {
            asked.add(question);
            answered.add(answer);
        }
    }

    public Sentence(ArrayList<Fact> facts) {
        this.facts = facts;
        Arrays.fill(base, 0);
        completion = Fact.Implication.NONE;
        init();
    }

    public String getSentence() {
        float[] temp = base;
        temp[0] *= multiUp * multiDown;
        temp[1] *= multiUp * multiDown;
        return calculateSegment(temp);
    }

    //TODO: implement this
//    public String getSentence(boolean verbose) {
//        StringBuilder out = new StringBuilder();
//        String baseStr = sentenceStr(base);
//        if (completion.lessEqTo(Fact.Implication.MULTI)) return baseStr;
//        float[] temp = base;
//        temp[0] *= multiUp * multiDown;
//        temp[1] *= multiUp * multiDown;
//        String multiStr = sentenceStr(temp);
//    }

    private String calculateSegment(float[] current) {
        if (segment == 0) return sentenceStr(current);
        float modifier = (Math.min(current[1], maxLength) - current[0]) / 4; // Tired, check this
        current[1] = current[0] + segment * modifier;
        current[0] = current[0] + (segment - 1) * modifier;
        return sentenceStr(current);
    }
    
    private String sentenceStr(float[] current) {
        if (current[0] + current[1] == 0) return "Not enough information";
        StringBuilder out = new StringBuilder();
        boolean first = true;
        for (float f: current) {
            int weight = f < 2 ? 4 : f < 10 ? 2 : 1;
            f = Math.min((float) Math.round(f * weight) / weight, maxLength);
            int years = (int) f;
            int months  = (int) ((f - years) * 12);
            if (years > 0 || months == 0) out.append(years).append(" years ");
            if (years > 0 && months > 0) out.append("and ");
            if (months > 0) out.append(months).append(" months");
            if (first) {
                out.append(" to ");
                first = false;
            }
        }
        return out.toString();
    }

    private void init() {
        for (Fact fact: facts) {
            switch (fact.getImplication()) {
                case BASE:
                    updateBase(fact);
                    break;
                case MULTI:
                    updateMulti(fact);
                    break;
                case SEGMENT:
                    updateSegment(fact);
                    break;
                case ADD:
                    updateAdd(fact);
                    break;
                case SUB:
                    updateSub(fact);
                    break;
                case MAX:
                    maxLength = Math.max(maxLength, Integer.parseInt(fact.getValue()));
            }
        }
    }

    private void updateBase(Fact fact) {
        base[0] = Math.max(base[0], fact.getMinValue());
        base[1] = Math.max(base[0], fact.getMaxValue());
        if (completion.lessEqTo(Fact.Implication.BASE)) completion = Fact.Implication.BASE;
    }

    private void updateMulti(Fact fact) {
        if (fact.getMultiplier() > 1) multiUp = Math.max(fact.getMultiplier(), multiUp);
        if (fact.getMultiplier() < 1) multiDown = Math.min(fact.getMultiplier(), multiDown);
        if (completion.lessEqTo(Fact.Implication.MULTI)) completion = Fact.Implication.MULTI;
    }

    private void updateSegment(Fact fact) {
        int severity = 0;
        switch (fact.getValue()) {
            case "light":
                severity = 1;
                break;
            case "medium":
                severity = 2;
                break;
            case "severe":
                severity = 3;
                break;
            case "very-severe":
                severity = 4;
                break;
        }
        segment = Math.max(segment, severity);
        if (completion.lessEqTo(Fact.Implication.SEGMENT)) completion = Fact.Implication.SEGMENT;
    }

    private void updateAdd(Fact fact) {
        System.out.println("updateAdd not yet implemented\n" + fact.toString());
    }

    private void updateSub(Fact fact) {
        System.out.println("updateSub not yet implemented\n" + fact.toString());
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        StringBuilder otherFacts = new StringBuilder();
        StringBuilder baseFacts = new StringBuilder();
        StringBuilder multiFacts = new StringBuilder();
        StringBuilder segFacts = new StringBuilder();
        for (Fact fact: facts) {
            if (fact.getImplication() == Fact.Implication.NONE) otherFacts.append(fact.toString());
            else if (fact.getImplication() == Fact.Implication.BASE) baseFacts.append(fact.toString());
            else if (fact.getImplication() == Fact.Implication.MULTI) multiFacts.append(fact.toString());
            else if (fact.getImplication() == Fact.Implication.SEGMENT) segFacts.append(fact.toString());
        }
        String line = "-----------------------------\n";
        if (otherFacts.length() > 5) out.append("Facts from questions:\n").append(otherFacts).append(line);
        if (baseFacts.length() > 5) out.append("Facts affecting the base sentence:\n").append(baseFacts).append(line);
        if (multiFacts.length() > 5) out.append("Facts that increase or decrease the base sentence:\n").append(multiFacts).append(line);
        if (segFacts.length() > 5) out.append("Facts that effect which segment of the sentence is recommended:\n").append(segFacts).append(line);
        return out.toString();
    }


    public static void clearQA() {
        asked = null;
        answered = null;
    }

    public static String getQA() {
        String prev = "n/a";
        StringBuilder out = new StringBuilder();
        if (asked == null || answered == null) out.append("No questions yet asked.\n");
        else {
            for (int idx = 0; idx < asked.size(); ++idx) {
                out.append(prev.equals(asked.get(idx)) ? "" : "\n\n" + asked.get(idx));
                out.append("\n\t- ").append(answered.get(idx));
                prev = asked.get(idx);
            }
        }
        return out.toString();
    }

}
