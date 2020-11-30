package tjc.rug.ExpertSystem.model;

import java.util.ArrayList;

public abstract class AbstractKnowledge {

    protected final ArrayList<Fact> requiredFacts;
    protected boolean reqIsAnd;

    /**
     * Initialised the requiredFacts array
     */
    public AbstractKnowledge() {
        requiredFacts = new ArrayList<>();
    }

    /**
     * Sets the reqIsAnd variable, true if all facts required, false if only one required
     * @param and   boolean representing whether the choice is "and" or "or"
     */
    public void setAnd(boolean and) {
        reqIsAnd = and;
    }

    /**
     * Adds an ArrayList of facts to the requiredFact ArrayList
     * @param facts     The list of facts to add
     */
    public void addFact(ArrayList<Fact> facts) {
        requiredFacts.addAll(facts);
    }

    /**
     * Adds a single fact to the ArrayList requiredFacts
     * @param fact  The fact to add
     */
    public void addFact(Fact fact) {
        requiredFacts.add(fact);
    }

    /**
     * Check if the input ArrayList of facts meets the requirements of this rule
     * @param facts     The facts to check against the rule
     * @return          True if the prerequisites are met, else false
     */
    public boolean meetsRequirements(ArrayList<Fact> facts) {
        int meetsReq = 1;
        for (Fact req: requiredFacts) {
            if (reqIsAnd) {
                if (factPresent(req, facts)) meetsReq *= 1;
                else if (req.valueIsNull() || req.valueIsNotNull()) {
                    if (testNulls(req, facts)) meetsReq *= 1;
                    else meetsReq *= 0;
                }
                else meetsReq *= 0;
            }
            else {
                meetsReq = 0;
                if (factPresent(req, facts)) return true;
                if ((req.valueIsNull() || req.valueIsNotNull()) && testNulls(req, facts)) return true;
            }
        }
        return meetsReq > 0;
    }

    /**
     * If a fact has the requirement "null" or "not-null" it is tested here against the given ArrayList
     * of facts
     * @param req       The requirement fact
     * @param facts     The ArrayList of facts to ascertain meets the requirement
     * @return          True if requirement met, else false
     */
    private boolean testNulls(Fact req, ArrayList<Fact> facts) {
        boolean isNull = req.valueIsNotNull();
        for (Fact fact: facts) if (fact.getName().equals(req.getName())) {
            return isNull;
        }
        return !isNull;
    }

    /**
     * Ascertains whether a fact is present in a given ArrayList of facts
     * @param req       The required fact
     * @param facts     The ArrayList to check for the fact
     * @return          True of if the fact is present, else false
     */
    private boolean factPresent(Fact req, ArrayList<Fact> facts) {
        boolean conditionMet = false;
        for (Fact fact: facts) {
            if (req.equals(fact)) {
                conditionMet = true;
                break;
            }
        }
        return conditionMet;
    }

    /**
     * Shows the fact as a String representation
     * @return  The fact as a string
     */
    public String toString() {
        StringBuilder out = new StringBuilder("Requirements:\n");
        for (Fact fact: requiredFacts) {
            out.append(fact.toString());
            if (requiredFacts.indexOf(fact) < requiredFacts.size() - 1) out.append(reqIsAnd ? "AND\n" : "OR\n");
        }
        return out.toString();
    }
}
