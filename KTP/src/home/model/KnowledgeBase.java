package home.model;

import java.util.ArrayList;

public class KnowledgeBase {

    private static ArrayList<Question> questions;

    public KnowledgeBase() {
    }

    public void readInKB() {
        questions = new ParseKB().getRules();
    }

    public Question getQuestion(int id) {
        for (Question question: questions) {
            if (question.matchID(id)) return question;
        }
        return null;
    }
}
