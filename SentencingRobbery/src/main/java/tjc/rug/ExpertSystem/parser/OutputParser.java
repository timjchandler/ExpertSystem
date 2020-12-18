package tjc.rug.ExpertSystem.parser;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import tjc.rug.ExpertSystem.model.Output;

import java.util.ArrayList;

public class OutputParser extends Parser {

    public OutputParser(String file) {
        super(file);
        parse();
    }

    private void parse() {
        super.parse("output");
    }

    public ArrayList<Output> getOutputs() {
        ArrayList<Output> out = new ArrayList<>();
        for (int idx = 0; idx < nodeList.getLength(); ++idx) {
            out.add(interpretOutput(nodeList.item(idx)));
        }
        return out;
    }

    private Output interpretOutput(Node item) {
        Element currEle = (Element) item;
        Output output = new Output();
        output.addFact(parseRequirements(currEle, "if", output));
        output.setSection(getElementText(currEle, "section"));
        output.setDescription(getElementText(currEle, "description"));
        if (currEle.getElementsByTagName("link").getLength() > 0) {
            output.setUrl(getElementText(currEle, "url"));
            output.setLink(getElementText(currEle, "text"));
        }
        return output;
    }
}
