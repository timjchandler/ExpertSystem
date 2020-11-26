package home.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ParseKB {

    private final String filename;
    private final ArrayList<Question> rules;

    public ParseKB() {
        this.filename = "knowledgebase/kb.txt";
        this.rules = new ArrayList<>();
        readFile();
    }

    enum Element {
        TYPE,
        NUMBER,
        QUESTION,
        ANSWER
    }

    private void readFile() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(this.filename));
            readRules(reader);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Element getElement(String line) {
        String start = line.split(":")[0];
        if (start.equals("TYPE")) return Element.TYPE;
        if (start.equals("ANSWERS")) return Element.NUMBER;
        if (start.equals("Q")) return Element.QUESTION;
        if (start.equals("A")) return Element.ANSWER;
        System.out.println(start);
        System.out.println("ERROR: Invalid input to element parser.");
        return null;
    }

    private void readRules(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        Question currentRule;
        while (line != null) {
            if (line.equals("+RULE")) {
                currentRule = new Question();
                while (true) {
                    line = reader.readLine();
                    if (line.equals("-RULE")) {
                        rules.add(currentRule);
                        break;
                    }
                    Element el = getElement(line);
                    switch (Objects.requireNonNull(el)) {
                        case TYPE:
                            currentRule.setType(line.split(":")[1].equals("MULTI") ? Question.Type.MULTI : Question.Type.SINGLE);
                            break;
                        case NUMBER:
                            currentRule.setNumberOfAnswers(Integer.parseInt(line.split(":")[1]));
                            break;
                        case QUESTION:
                            currentRule.setQuestion(line.split(":")[1]);
                            break;
                        case ANSWER:
                            currentRule.setAnswer(line.split(":VALUE:")[1]);
                    }
                }
            }
            line = reader.readLine();
        }
    }

    public void printRules() {
        for (Question rule: rules) System.out.println(rule.viewAsString());
    }

    public ArrayList<Question> getRules() {
        return rules;
    }
}
