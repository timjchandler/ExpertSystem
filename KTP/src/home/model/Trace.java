package home.model;

import java.util.ArrayList;

public class Trace {

    private static ArrayList<String> trace;
    private static ArrayList<Question> questions;

    public Trace() {
        trace = new ArrayList<>();
        questions = new ArrayList<>();
    }

    public void addResponse(Question question, int answer) {
        int[] answers = new int[1];
        answers[0] = answer;
        addResponse(question, answers);
    }

    public void addResponse(Question question, int[] answers) {
        StringBuilder out = new StringBuilder("In response to the question:\n\t");
        out.append(question.getQuestion());
        out.append("\nThe following response was given:");
        for (int idx: answers) out.append("\n->\t").append(question.getResponses().get(idx).getValue());
        trace.add(out.append("\n\n").toString());
        questions.add(question);
    }

    public String asString() {
        StringBuilder out = new StringBuilder();
        for (String element: trace) out.append(element);
        return out.toString();
    }

    public void removeLast() {
        if (trace.size() < 1 || questions.size() < 1) return;
        trace.remove(trace.size() - 1);
        questions.remove(questions.size() - 1);
    }

}
