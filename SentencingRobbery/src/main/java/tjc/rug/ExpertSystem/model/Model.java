package tjc.rug.ExpertSystem.model;

import tjc.rug.ExpertSystem.parser.QuestionParser;

import java.util.ArrayList;

public class Model {

    private static ArrayList<Question> questions = null;
    private static State state = null;
    private static String title = null;
    private static Question current;

    /**
     * Calls the reset method if member variables are not set
     */
    public Model() {
        if (questions == null && state == null && title == null) {
            System.out.println("[OPEN] Loading Questions and Rules");
            restart();
            System.out.println("[OPEN] ---------------------------");
            System.out.println("[OPEN] Loading Complete");
        }
    }

    /**
     * Reads in the questions.xml, sets the member variables
     */
    public void restart() {
        QuestionParser qp = new QuestionParser("/resources/knowledgebase/questions.xml");
        questions = qp.getQuestions();
        title = qp.getTitle();
        state = new State();
        Sentence.clearResponses();
    }

    /**
     * Gets the next question based on the current facts and question prerequisites
     * @param next  True if a new question requested, false if current question requested
     * @return      The requested question
     */
    public static Question getQuestion(boolean next) {
        if (next) {
            if (new Sentence(state.getImplications()).checkMistaken()) return null;
            for (Question question : questions) {
                if (question.meetsRequirements(state.getFacts())) {
                    current = question;
                    return question;
                }
            }
            return null;
        }
        else {
            return current;
        }
    }

    /**
     * Return the title
     * @return  The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Passes a fact to state to be added
     * @param fact  The fact to add
     */
    public static void addFact(Fact fact) {
        state.addFact(fact);
    }

    /**
     * Get the current trace of known and inferred facts from the state
     * @return  The trace as a string
     */
    public static String getTrace() {
        return state.getSentenceString();
    }

    /**
     * Get the current calculated sentence from the state
     * @return  The sentence as a string
     */
    public static String getSentence() {
        return state.getSentence();
    }

    public static float[] getModBase() {
        return state.getModBase();
    }

    public static float[] getSentenceBase() {
        return state.getSentenceBase();
    }

    public static int getSentenceSegment() {
        return state.getSentenceSegment();
    }

    /**
     * Checks if the questions variable has been initialised.
     * @return  True if the questions variable is null, else false
     */
    public static boolean isNull() {
        return questions == null;
    }
}
