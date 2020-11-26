package home.model;

import java.util.ArrayList;

public class Question {

    public enum Type {
        MULTI,
        SINGLE,
    }

    private final int id;
    private Type type;
    private int numberOfAnswers;
    private String question;
    private final ArrayList<Response> answers;

    public Question() {
        this.id = 1;
        answers = new ArrayList<>();
    }

    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Sets the number of answers
     * @param numberOfAnswers The number of potential answers to the question
     */
    public void setNumberOfAnswers(int numberOfAnswers) {
        this.numberOfAnswers = numberOfAnswers;
    }

    /**
     * Sets the question
     * @param question  A string containing the rules question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Sets the answer as a Response and adds to answers array
     * @param fullAnswer String should be of format "Yes#RESPONSE:That is good"
     */
    public void setAnswer(String fullAnswer) {
        String[] parts = fullAnswer.split("#RESPONSE:");
        answers.add(new Response(parts[0], parts[1]));
    }

    public String viewAsString() {
        StringBuilder out = new StringBuilder("This rule has ");
        out.append(numberOfAnswers);
        out.append(" answers.\nThe questions is:\n\"");
        out.append(question);
        out.append("\"\nThe possible answers are:\n");
        for (Response answer : answers) {
            if (!answer.getValue().equals("")) out.append(answer.getValue());
            else out.append("<No answer given>");
            out.append("\n\t\"");
            out.append(answer.getResponse());
            out.append("\"\n\n");
        }
        out.append("\n");
        return out.toString();
    }

    public String getQuestion() {
        return question;
    }

    public int getNumberOfAnswers() {
        return numberOfAnswers;
    }

    public ArrayList<Response> getAnswers() {
        return answers;
    }

    public Type getType() {
        return type;
    }

    public boolean matchID(int id) {
        return this.id == id;
    }
}
