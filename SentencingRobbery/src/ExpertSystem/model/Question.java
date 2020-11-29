package ExpertSystem.model;

import java.util.ArrayList;

public class Question extends AbstractKnowledge {

    public enum QuestionType { SINGLE, MULTI }

    private QuestionType type;
    private String heading;
    private String questionText;
    private final ArrayList<String> answers;
    private final ArrayList<Fact> answerFacts;

    public Question() {
        super();
        answers = new ArrayList<>();
        answerFacts = new ArrayList<>();
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void appendAnswer(String answer) {
        this.answers.add(answer);
    }

    public void appendAnswerFact(Fact fact) {
        this.answerFacts.add(fact);
    }

    public QuestionType getType() {
        return type;
    }

    public String getHeading() {
        return heading;
    }

    public String getQuestionText() {
        return questionText;
    }

    public boolean meetsRequirements(ArrayList<Fact> facts) {
        for (Fact fact: facts) if (fact.getName().equals(answerFacts.get(0).getName())) return false;
        return super.meetsRequirements(facts);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("[").append(heading).append("]\n");
        out.append(questionText).append("\n");
        for (String answer: answers) {
            out.append("\t").append(answer).append(" -> ");
            out.append(answerFacts.get(answers.indexOf(answer)).toString()).append("\n");
        }
        out.append(requiredFacts.size()).append(" requirements\n");
        out.append(super.toString());
        return out.toString();
    }

    public ArrayList<Fact> getAnswerFacts() {
        return answerFacts;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }
}
