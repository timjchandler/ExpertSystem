package test;

import home.model.ParseKB;
import home.model.Question;
import home.model.Trace;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TraceTest {

    @Test
    public void testTrace() {
        ArrayList<Question> questions = new ParseKB().getRules();
        Trace trace = new Trace();
        int[] answers = new int[2];
        for (int idx = 0; idx < 2; ++idx) answers[idx] = idx;
        for (Question q: questions) {
            if (q.getType().equals(Question.Type.MULTI)) trace.addResponse(q, answers);
            else trace.addResponse(q, 1);
        }
        System.out.print(trace.asString());
    }

}