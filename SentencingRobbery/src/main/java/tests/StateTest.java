package main.java.tests;

import main.java.ExpertSystem.model.Fact;
import main.java.ExpertSystem.model.State;
import org.junit.jupiter.api.Test;

class StateTest {

    @Test
    public void testState() {
        State state = new State();
        Fact fact0 = new Fact("robbery-type", "simple");
        state.addFact(new Fact("multi-offence", "yes"));
        state.addFact(new Fact("other-offence", "theft"));
        state.addFact(fact0);
        System.out.print(state.toString());
    }

}