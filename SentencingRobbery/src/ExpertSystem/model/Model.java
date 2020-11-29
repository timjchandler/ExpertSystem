package ExpertSystem.model;

import ExpertSystem.parser.QuestionParser;

import java.util.ArrayList;

public class Model {

    private static ArrayList<Question> questions = null;
    private static State state = null;
    private static String title = null;
    private static Question current;

    public Model() {
        if (questions == null && state == null && title == null) restart();
    }

    public void restart() {
        QuestionParser qp = new QuestionParser("knowledgebase/questions.xml");
        questions = qp.getQuestions();
        title = qp.getTitle();
        state = new State();
//        System.out.println(questions.size());
//        System.out.println(questions.get(2).toString());
    }

    public Question getQuestion(boolean next) {
        if (next) {
            for (Question question : questions) {
                if (question.meetsRequirements(state.getFacts())) {
                    current = question;
                    return question;
                }
            }
            System.out.println("No more questions");
            return null;
        }
        else {
            return current;
        }
    }

    public String getTitle() {
        return title;
    }

    public void addFact(Fact fact) {
        state.addFact(fact);
    }

    public String getTrace() {
        return state.toString();
    }

    public String getSentence() {
        return state.getSentence();
    }
}
