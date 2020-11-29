package tests;

import ExpertSystem.model.Fact;
import ExpertSystem.model.Model;
import ExpertSystem.model.Question;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class KnowledgeTest {

    @Test
    public void testSatisfiesNullNotNull() {
        Question question = new Question();
        question.addFact(new Fact("name", "null"));
        ArrayList<Fact> facts = new ArrayList<>();
        facts.add(new Fact("name-three", "value"));
        assertTrue(question.meetsRequirements(facts));

    }
}
