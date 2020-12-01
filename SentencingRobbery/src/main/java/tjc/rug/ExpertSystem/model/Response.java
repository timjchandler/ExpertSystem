package tjc.rug.ExpertSystem.model;

import java.util.ArrayList;

public class Response {

    private final String section;
    private final String qText;
    private final ArrayList<String> answers;
    private final ArrayList<Fact> facts;

    /**
     * Sets the qText variable to the Questions question string. Sets the section variable to the Questions heading
     * string, initialises the ArrayLists passes to addAnswer()
     * @param question  The question to add from
     * @param idx       The index of the first answer
     */
    public Response(Question question, int idx) {
        qText = question.getQuestionText();
        section = question.getHeading();
        facts = new ArrayList<>();
        answers = new ArrayList<>();
        addAnswer(question, idx);
    }

    /**
     * Adds an answer to the Response. Returns immediately if the answer is already present. Adds the question
     * string to the answers variable and the Fact to the facts variable.
     * @param question  The question from which to add an answer
     * @param idx       The index of the answer and fact
     */
    public void addAnswer(Question question, int idx) {
        if (facts.contains(question.getAnswerFacts().get(idx))) return;
        facts.add(question.getAnswerFacts().get(idx));
        answers.add(question.getAnswers().get(idx));
    }

    /**
     * Getter for the section
     * @return  The section
     */
    public String getSection() {
        return section;
    }

    /**
     * Getter for the question text
     * @return  The qText string
     */
    public String getQText() {
        return qText;
    }

    /**
     * Getter for the answers ArrayList
     * @return  The ArrayList of answers
     */
    public ArrayList<String> getAnswers() {
        return answers;
    }

    /**
     * Getter for the facts ArrayList
     * @return  The ArrayList of Facts
     */
    public ArrayList<Fact> getFacts() {
        return facts;
    }
}
