package tjc.rug.ExpertSystem.model;

import java.util.ArrayList;

public class Response {

    private String section;
    private String qText;
    private ArrayList<String> answers;
    private ArrayList<Fact> facts;

    public Response(Question question, int idx) {
        qText = question.getQuestionText();
        section = question.getHeading();
        facts = new ArrayList<>();
        answers = new ArrayList<>();
        facts.add(question.getAnswerFacts().get(idx));
        answers.add(question.getAnswers().get(idx));
        addAnswer(question, idx);
    }

    public void addAnswer(Question question, int idx) {
        if (facts.contains(question.getAnswerFacts().get(idx))) return;
        facts.add(question.getAnswerFacts().get(idx));
        answers.add(question.getAnswers().get(idx));
    }

    public String getSection() {
        return section;
    }

    public String getQText() {
        return qText;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public ArrayList<Fact> getFacts() {
        return facts;
    }
}
