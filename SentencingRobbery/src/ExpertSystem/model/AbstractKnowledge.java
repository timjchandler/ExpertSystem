package ExpertSystem.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public abstract class AbstractKnowledge {

    protected final ArrayList<Fact> requiredFacts;
    protected boolean reqIsAnd;

    public AbstractKnowledge() {
        requiredFacts = new ArrayList<>();
    }

    public void setAnd(boolean and) {
        reqIsAnd = and;
    }

    public void addFact(ArrayList<Fact> facts) {
        requiredFacts.addAll(facts);
    }

    public void addFact(Fact fact) {
        requiredFacts.add(fact);
    }

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

    private boolean testNulls(Fact req, ArrayList<Fact> facts) {
        boolean isNull = req.valueIsNotNull();
        for (Fact fact: facts) if (fact.getName().equals(req.getName())) {
            return isNull;
        }
        return !isNull;
    }

    private boolean factPresent(Fact req, @NotNull ArrayList<Fact> facts) {
        boolean conditionMet = false;
        for (Fact fact: facts) {
            if (req.equals(fact)) {
                conditionMet = true;
                break;
            }
        }
        return conditionMet;
    }

    public String toString() {
        StringBuilder out = new StringBuilder("Requirements:\n");
        for (Fact fact: requiredFacts) {
            out.append(fact.toString());
            if (requiredFacts.indexOf(fact) < requiredFacts.size() - 1) out.append(reqIsAnd ? "AND\n" : "OR\n");
        }
        return out.toString();
    }
}
