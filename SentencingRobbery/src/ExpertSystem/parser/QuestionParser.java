package ExpertSystem.parser;

import ExpertSystem.model.Question;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;

public class QuestionParser extends Parser{

    /**
     * The title of the Expert System
     */
    private String title;

    /**
     * Constructor
     * Reads in and parses the file from the given location.
     * @param file  the filepath of the xml file to parse
     */
    public QuestionParser(String file) {
        super(file);
        parse();
    }

    /**
     * Method
     * Parses the title from the document
     * @param document  The document to parse
     */
    private void setTitle(Document document) {
        title = document.getElementsByTagName("title").item(0).getTextContent();
    }

    /**
     * Method
     * Calls the supers parse method, passing the Document to getTitle
     */
    private void parse() {
        setTitle(super.parse("question"));
    }

    /**
     * Method
     * Interprets a question from a given node
     * @param current   The node to interpret
     * @return          The interpreted question
     */
    private Question interpretQuestion(Node current) {
        Element currEle = (Element) current;
        Question question = new Question();
        question.setHeading(getElementText(currEle, "heading"));
        String typeStr = getElementText(currEle, "type");

        if (typeStr.equals("multi")) question.setType(Question.QuestionType.MULTI);
        else if (typeStr.equals("single")) question.setType(Question.QuestionType.SINGLE);

        question.setQuestionText(getElementText(currEle, "text"));
        question.addFact(parseRequirements(currEle, "requires", question));

        for (int idx = 0; idx < currEle.getElementsByTagName("response").getLength(); ++idx) {
            Element temp = subElement(currEle, "response", idx);
            question.appendAnswer(super.getElementText(temp,"text"));
            question.appendAnswerFact(super.parseFact(temp));
        }

        return question;
    }

    /**
     * Method
     * @return  An ArrayList containing all of the parsed questions
     */
    public ArrayList<Question> getQuestions() {
        ArrayList<Question> questions = new ArrayList<>();
        for (int idx = 0; idx < nodeList.getLength(); ++idx) {
            questions.add(interpretQuestion(nodeList.item(idx)));
        }
        return questions;
    }



    public String getTitle() {
        return title;
    }
}
