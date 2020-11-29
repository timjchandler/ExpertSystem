package home.model;

import java.util.ArrayList;

public class Sentencing {

    /**
     * Enum describing robbery type:
     */
    public enum Robbery {
        QUALIFIED,
        BASIC
    }

    /**
     * Enum describing possible mitigating circumstances
     */
    public enum Mitigating {
        HONORABLE,
        DISTRESS,
        UNDER_THREAT,
        AT_BEHEST,
        PROVOKED,
        EXTREME_EMOTION,
        GENUINE_REMORSE,
        TIME_ELAPSED
    }

    /**
     * Enum categorising severity of an action
     */
    public enum Severity {
        LIGHT,
        MEDIUM,
        SEVERE,
        VERY_SEVERE;
    }

    private Robbery robbery;
    private int minSentenceMonths;
    private int maxSentenceMonths;
    private int percentIncrease;
    private Mitigating mitigating;
    private Severity fault;
    private boolean past_record;
    private boolean cooperated;
    private Severity dependents;
    private Severity future_life;

    public Sentencing() {

    }

    public ArrayList<String> getTrace() {
        return new ArrayList<>();
    }


}
