package main.java.ExpertSystem.model;

import java.util.ArrayList;

public class Question extends AbstractKnowledge {

    public enum QuestionType { SINGLE, MULTI }

    private QuestionType type;
    private String heading;
    private String questionText;
    private final ArrayList<String> answers;
    private final ArrayList<Fact> answerFacts;

    /**
     * Calls the super constructor and initialises the member ArrayLists
     */
    public Question() {
        super();
        answers = new ArrayList<>();
        answerFacts = new ArrayList<>();
    }

    /**
     * Sets the type of question to Single or Multi choice
     * @param type  The type to set
     */
    public void setType(QuestionType type) {
        this.type = type;
    }

    /**
     * Sets the heading to of the section of the sentence being calculated.
     * @param heading   The heading to set
     */
    public void setHeading(String heading) {
        this.heading = heading;
    }

    /**
     * Sets the text of the question
     * @param questionText  The text to set
     */
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    /**
     * Add the text to display for an answer
     * @param answer    The answer text
     */
    public void appendAnswer(String answer) {
        this.answers.add(answer);
    }

    /**
     * Adds a fact to the facts list
     * @param fact  The fact to add
     */
    public void appendAnswerFact(Fact fact) {
        this.answerFacts.add(fact);
    }

    /**
     * Get the type of the question, single/multi choice
     * @return  The type
     */
    public QuestionType getType() {
        return type;
    }

    /**
     * Get the heading
     * @return  The heading
     */
    public String getHeading() {
        return heading;
    }

    /**
     * Get the question text
     * @return  The question text
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * Checks whether a list of facts satisfies this questions requirements, passes to the super
     * @param facts     The facts to check against the rule
     * @return          True if the list of facts satisfies the requirements, false otherwise
     */
    public boolean meetsRequirements(ArrayList<Fact> facts) {
        for (Fact fact: facts) if (fact.getName().equals(answerFacts.get(0).getName())) return false;
        return super.meetsRequirements(facts);
    }

    /**
     * Converts the question to a string representation
     * @return  A representation of the question as a string
     */
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

    /**
     * Gets the list of answer facts
     * @return  The list of answer facts
     */
    public ArrayList<Fact> getAnswerFacts() {
        return answerFacts;
    }

    /**
     * Get the list of the answer texts
     * @return  The list of answer texts
     */
    public ArrayList<String> getAnswers() {
        return answers;
    }
}
