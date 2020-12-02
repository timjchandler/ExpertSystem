package tjc.rug.ExpertSystem.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Sentence {

    private static ArrayList<Response> responses = null;
    private Fact.Implication completion;
    private ArrayList<Fact> facts;
    private final float[] base = new float[2];
    private int segment = 0;
    private int maxLength = 0; // TODO: Placeholder, add a fact that implements this
    private float multiUp = 1;
    private float multiDown = 1;

    /**
     * Initialises the static responses ArrayList if required. Checks if a given question has already been recorded
     * and if not adds the response derived from the question
     * @param question  The question to add information from
     * @param idx       The index of the answer within the question
     */
    public Sentence(Question question, int idx) {
        if (responses == null) responses = new ArrayList<>();
        boolean questionPresent = false;
        for (Response response: responses) {
            if (response.getQText().equals(question.getQuestionText())) {
                response.addAnswer(question, idx);
                questionPresent = true;
                break;
            }
        }
        if (!questionPresent) responses.add(new Response(question, idx));
    }

    /**
     * Sets the facts member variable, sets the base array to [0.0, 0.0] sets the state completion to NONE, passes to
     * init
     * @param facts The facts to build the sentence from
     */
    public Sentence(ArrayList<Fact> facts) {
        this.facts = facts;
        Arrays.fill(base, 0);
        completion = Fact.Implication.NONE;
        init();
    }

    /**
     * Returns the calculated sentence as a string
     * @return  A string representing the calculated sentence
     */
    public String getSentence() {
        if (checkMistaken()) return "No sentence: The answers provided suggest that the verdict should have been NOT-GUILTY";
        float[] temp = base;
        temp[0] *= multiUp * multiDown;
        temp[1] *= multiUp * multiDown;
        return calculateSegment(temp);
    }

    protected boolean checkMistaken() {
        for (Fact f: facts) if (f.equals(new Fact("mistaken-verdict", "true"))) return true;
        return false;
    }

    /**
     * Calculates which segment of the sentence to recommend. If no sentence fact has been applied, returns the full
     * range.
     * @param current   The current sentence
     * @return          The adjusted segment of sentence
     */
    private String calculateSegment(float[] current) {
        if (segment == 0) return sentenceStr(current);
        float modifier = (Math.min(current[1], maxLength) - current[0]) / 4;
        current[1] = current[0] + segment * modifier;
        current[0] = current[0] + (segment - 1) * modifier;
        return sentenceStr(current);
    }

    /**
     * Tidies the sentence and returns it as a string. If the current sentence is [0.0, 0.0] returns a message that more
     * information is needed. Otherwise adjusts both bounds of the sentence, normalising them to the nearest 3 months if
     * under 2 years long, 6 months if under 10 years long or nearest year otherwise. Then builds a string representation
     * of the tidied sentence.
     * @param current   The current sentence
     * @return          The string representation of the tidied sentence.
     */
    private String sentenceStr(float[] current) {
        if (current[0] + current[1] == 0) return "Not enough information. Please answer more questions.";
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

    /**
     * Passes each known fact to its relevant handler in order to build the components of the sentence.
     * If the fact is regarding the maximum sentence length the member variable is directly updated in the
     * case of an increase.
     */
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

    /**
     * Updates the base sentence range
     * @param fact  The fact from which to update the base sentence. Updates
     * the completion variable if required to denote greater information available.
     */
    private void updateBase(Fact fact) {
        base[0] = Math.max(base[0], fact.getMinValue());
        base[1] = Math.max(base[0], fact.getMaxValue());
        if (completion.lessEqTo(Fact.Implication.BASE)) completion = Fact.Implication.BASE;
    }

    /**
     * Updates the sentence multipliers, selecting the maximum for an increase, and the minimum for a decrease. Updates
     * the completion variable if required to denote greater information available.
     * @param fact  The fact from which to update the multipliers.
     */
    private void updateMulti(Fact fact) {
        if (fact.getMultiplier() > 1) multiUp = Math.max(fact.getMultiplier(), multiUp);
        if (fact.getMultiplier() < 1) multiDown = Math.min(fact.getMultiplier(), multiDown);
        if (completion.lessEqTo(Fact.Implication.MULTI)) completion = Fact.Implication.MULTI;
    }

    /**
     * Updates the segment member variable. This denotes which slice of the sentence will be recommended. Updates
     * the completion variable if required to denote greater information available.
     * @param fact  The fact from which to update the segment information
     */
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

    /**
     * TODO: Add rules regarding this and implement
     * @param fact  The fact from which to make the update
     */
    private void updateAdd(Fact fact) {
        System.out.println("updateAdd not yet implemented\n" + fact.toString());
    }

    /**
     * TODO: Add rules regarding this and implement
     * @param fact  The fact from which to make the update
     */
    private void updateSub(Fact fact) {
        System.out.println("updateSub not yet implemented\n" + fact.toString());
    }

    /**
     * Builds a string of known facts. TODO: Add more information
     * @return  The string of known facts
     */
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

    /**
     * Sets the static responses variable to null
     */
    public static void clearResponses() {
        responses = null;
    }

    /**
     * Static getter for the ArrayList of responses
     * @return  The responses
     */
    public static  ArrayList<Response> getResponses() {
        return responses;
    }

    public static boolean isNull() {
        return (responses == null);
    }
}
