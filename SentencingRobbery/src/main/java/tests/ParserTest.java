package main.java.tests;

import main.java.ExpertSystem.model.Question;
import main.java.ExpertSystem.model.Rule;
import main.java.ExpertSystem.parser.QuestionParser;
import main.java.ExpertSystem.parser.RuleParser;
import org.junit.jupiter.api.Test;

class ParserTest {

    @Test
    public void testParser() {
        QuestionParser parser = new QuestionParser("knowledgebase/questions.xml");
//        parser.print();
    }

    @Test
    public void testGetQuestions() {
        QuestionParser parser = new QuestionParser("knowledgebase/questions.xml");
        for (Question question: parser.getQuestions()) System.out.print(question.toString());
    }

    @Test
    public void testGetRules() {
        RuleParser parser = new RuleParser("knowledgebase/rules.xml");
        parser.getRules();
        for (Rule rule: parser.getRules()) System.out.print(rule.toString());
    }

}