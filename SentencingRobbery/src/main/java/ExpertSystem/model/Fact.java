package main.java.ExpertSystem.model;
public class Fact {

    /**
     * The implication of a rule:
     *      NONE : This rule does not directly imply a change to sentencing
     *      BASE : This rule implies a certain base sentence
     *      MULTI: This rule implies a multiplication to a base sentence
     *      ADD  : This rule implies an addition to a base sentence
     *      SUB  : This rule implies a subtraction from a base sentence
     */
    protected enum Implication { NONE, BASE, MULTI, ADD, SUB }

    protected final int NOT_APPLICABLE = -1;

    private final String name;
    private final String value;
    private float minValue = NOT_APPLICABLE;
    private float maxValue = NOT_APPLICABLE;
    private float multiplier = NOT_APPLICABLE;
    private Implication implication;

    /**
     * Sets the name and value variables from the input, sets implication to NONE as default
     * @param name      The name, or category of the fact
     * @param value     The value assigned to the fact
     */
    public Fact(String name, String value) {
        this.name = name;
        this.value = value;
        this.implication = Implication.NONE;
        manageNumbers();
    }

    /**
     * Interprets numbers from the value variable. Updates the implication variable as required
     * Updates minValue, maxValue and multiplier variables where relevant
     */
    private void manageNumbers() {
        if (value.contains(",")) {
            minValue = Float.parseFloat(value.split(",")[0]);
            maxValue = Float.parseFloat(value.split(",")[1]);
            this.implication = Implication.BASE;
        } else if (value.contains(".") || value.contains("1")) {
            multiplier = Float.parseFloat(value);
            this.implication = Implication.MULTI;
        }
    }

    /**
     * Returns the implication, this represents whether the fact impacts the base sentence, a multiplier
     * to the base sentence, an addition to the base sentence, a subtraction from the base sentence or none
     * of these
     * @return  Implication
     */
    public Implication getImplication() {
        return implication;
    }

    /**
     * Converts the rule into a string
     * @return A string representing the rule
     */
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("[").append(name).append("] has the value: ");
        if (minValue != NOT_APPLICABLE && maxValue != NOT_APPLICABLE) {
            out.append("From ").append(minValue).append(" to ");
            out.append(maxValue).append(" years");
        } else if (multiplier != NOT_APPLICABLE) {
            out.append("Multiplier of ").append(multiplier);
        } else out.append(value);
        out.append("\n");
        return out.toString();
    }

    /**
     * Updates a given base time with regards to this rule, if applicable
     * @param current   The base time to be updated
     * @return          The updated base time
     */
    public float[] adjustBaseTime(float[] current) {
        if (implication == Implication.BASE) {
            current[0] = Math.max(minValue, current[0]);
            current[1] = Math.max(maxValue, current[1]);
        }
        return current;
    }

    /**
     * Ascertains whether this rule is null
     * @return          True if the value of this fact is "null", else false
     */
    public boolean valueIsNull() {
        return value.equals("null");
    }

    /**
     * @return  True if the value of this fact is "not-null", else false
     */
    public boolean valueIsNotNull() {
        return value.equals("not-null");
    }

    /**
     * Getter for the name of the fact
     * @return          The name variable
     */
    public String getName() {
        return name;
    }

    /**
     * Updates a given multiplier with regards to this rule, if applicable
     * @param current   The multiplier to be updated
     * @return          The updated multiplier
     */
    public float adjustMultiplier(float current) {
        if (multiplier < 0) return current;
        return Math.max(current + multiplier - 1, 0);
    }

    /**
     * Ascertains whether this fact is equal to a fact consisting of two given strings. Since
     * all other member variables are derived from the two input strings, and cannot be externally
     * modified no other comparisons are required.
     * @param name      The name variable of a fact
     * @param value     The value variable of a fact
     * @return          True if the fact matches this one, false otherwise
     */
    public boolean equals(String name, String value) {
        return (this.name.equals(name) && this.value.equals(value));
    }

    /**
     * Ascertains whether this fact is equal to a given fact
     * @param fact      The fact to compare
     * @return          True if the fact matches this one, false otherwise
     */
    public boolean equals(Fact fact) {
        return fact.equals(name, value);
    }
}
