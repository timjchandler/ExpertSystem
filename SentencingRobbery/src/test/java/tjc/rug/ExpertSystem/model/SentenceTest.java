package tjc.rug.ExpertSystem.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

class SentenceTest {

    private static ArrayList<String> answers;

    @BeforeAll
    static void setup() {
        assertEquals(Sentence.getQA(), "No questions yet asked.\n");
        answers = new ArrayList<>();
        for (int idx = 0; idx < 5; ++idx) answers.add("Answer " + idx);
    }

    @Test
    public void testGetQA() {
        Sentence sentence = new Sentence("Question", answers);
        System.out.println(sentence.getQA());
    }

    @Test
    public void testBothConstructors() {
        Sentence sentence = new Sentence("A different question", "an answer");
        System.out.println(sentence.getQA());
    }

    @Test
    public void testClear() {
        Sentence sentence = new Sentence("...", answers);
        Sentence.clearQA();
        assertEquals(Sentence.getQA(), "No questions yet asked.\n");
    }
  
}