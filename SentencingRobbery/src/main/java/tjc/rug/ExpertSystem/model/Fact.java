package tjc.rug.ExpertSystem.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Fact {

    /**
     * The implication of a rule with comparison method:
     *      NONE    : This rule does not directly imply a change to sentencing
     *      BASE    : This rule implies a certain base sentence
     *      MULTI   : This rule implies a multiplication to a base sentence
     *      SEGMENT : This rule regards the segment of the sentence to select
     *      ADD     : This rule implies an addition to a base sentence
     *      SUB     : This rule implies a subtraction from a base sentence
     *      MAX     : This rule applies an upper limit to the sentence
     */
    protected enum Implication {
        NONE(0),
        BASE(1),
        MULTI(2),
        SEGMENT(3),
        ADD(4),
        SUB(5),
        MAX(6);

        private final int i;

        Implication(int i) {
            this.i = i;
        }

        public boolean lessEqTo(Implication imp) {
            return this.i <= imp.i;
        }
    }

    protected final int NOT_APPLICABLE = -1;

    private final ArrayList<String> segmentValues = new ArrayList<>(
            Arrays.asList("none", "low", "medium", "severe", "very-severe")
    );
    private final String name;
    private final String value;
    private float minValue = NOT_APPLICABLE;
    private float maxValue = NOT_APPLICABLE;
    private float multiplier = 1;
    private Implication implication;

    /**
     * Sets the name and value variables from the input, sets implication to NONE as default
     * @param name      The name, or category of the fact
     * @param value     The value assigned to the fact
     */
    public Fact(String name, String value) {
        this.name = name;
        this.value = value;
        implication = Implication.NONE;
        if (segmentValues.contains(value)) implication = Implication.SEGMENT;
        else if (name.equals("max")) implication = Implication.MAX;
        else manageNumbers();
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
        } else if (value.contains(".") || value.equals("1")) {
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
     * Generates a string representation of the fact in human readable form.
     * @return  The string representing the fact.
     */
    @Override
    public String toString() {
        return "[" + name + "]\t has the value: \t{ " + value + " }\n";
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

    // TODO: UPDATE THIS JAVADOC
    /**
     * Ascertains whether this fact is equal to a given fact
     * @param fact      The fact to compare
     * @return          True if the fact matches this one, false otherwise
     */
    public boolean equals(Fact fact) {
        return (name.equals(fact.getName()) && value.equals(fact.getValue()));
    }

    /**
     * Getter for the value
     * @return  The value
     */
    public String getValue() {
        return value;
    }

    /**
     * Getter for the min value
     * @return  The min value
     */
    public float getMinValue() {
        return minValue;
    }

    /**
     * Getter for the max value
     * @return  The max value
     */
    public float getMaxValue() {
        return maxValue;
    }

    /**
     * Getter for the multiplier value
     * @return  The multiplier
     */
    public float getMultiplier() {
        return multiplier;
    }
}
