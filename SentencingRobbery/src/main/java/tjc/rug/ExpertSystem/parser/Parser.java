package tjc.rug.ExpertSystem.parser;

import org.xml.sax.SAXException;
import tjc.rug.ExpertSystem.model.AbstractKnowledge;
import tjc.rug.ExpertSystem.model.Fact;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public abstract class Parser {

    protected InputStream input;
    protected NodeList nodeList;

    /**
     * Sets the file to be parsed, from the input argument
     * @param file  the file to be parsed
     */
    public Parser(String file) {
        input = getClass().getResourceAsStream(file);
        System.out.println(input);
    }

    /**
     * Get an element from within a given element, based on the given tag, assuming index 0
     * @param ele   The element to look within
     * @param tag   The tag to identify where to look
     * @return      The requested element
     */
    protected Element subElement(Element ele, String tag) {
        return subElement(ele, tag, 0);
    }

    /**
     * Get an element from within a given element, based on the given tag and index
     * @param ele   The element to look within
     * @param tag   The tag to identify where to look
     * @param idx   The index at which to look based on the given tag
     * @return      The requested element
     */
    protected Element subElement(Element ele, String tag, int idx) {
        return (Element) ele.getElementsByTagName(tag).item(idx);
    }

    /**
     * Returns the text from a given element with a given tag
     * @param ele   The element to retrieve text from
     * @param tag   The tag to identify where to retrieve text
     * @return      The retrieved text
     */
    String getElementText(Element ele, String tag) {
        return getElementText(ele, 0, tag);
    }

    /**
     * Returns the text from a given element with a given tag
     * @param ele   The element to retrieve text from
     * @param idx   The index at which to look based on the given tag
     * @param tag   The tag to identify where to retrieve text
     * @return      The retrieved text
     */
    protected String getElementText(Element ele, int idx, String tag) {
        if (idx >= ele.getElementsByTagName(tag).getLength()) {
            System.out.println("ERROR: index exceeds bounds.");
            System.out.println("Tag:\t" + tag);
            System.out.println("Index:\t" + idx);
            return null;
        }
        return ele.getElementsByTagName(tag).item(idx).getTextContent();
    }

    /**
     * Parse the main information from a xml document based on a given tag, storing it in the nodeList variable
     * @param tag   Which information is requested (rule or question)
     * @return      The document that was parsed
     */
    protected Document parse(String tag) {
        Document document = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            document = documentBuilder.parse(input);
            document.getDocumentElement().normalize();
            nodeList = document.getElementsByTagName(tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * Parses a fact from a given element
     * @param ele   The element to extract a fact from
     * @return      The parsed fact
     */
    protected Fact parseFact(Element ele) {
        if (!ele.getTagName().equals("fact")) ele = subElement(ele, "fact");
        return new Fact(ele.getAttribute("name"), ele.getTextContent());
    }

    /**
     * Retrieves the list of requirements for a given knowledge type, returning them as an
     * ArrayList and setting updating the knowledge type with whether these requirements are
     * following an "and" or "or" structure
     * @param ele       The element to parse
     * @param tag       The tag to identify the section of the element
     * @param ak        The knowledge type
     * @return          An ArrayList of facts
     */
    protected ArrayList<Fact> parseRequirements(Element ele, String tag, AbstractKnowledge ak) {
        ArrayList<Fact> facts = new ArrayList<>();
        for (int idx = 0; idx < ele.getElementsByTagName(tag).getLength(); ++idx) {
            Element ifEle = subElement(ele, tag, idx);
            boolean isAnd = ifEle.getElementsByTagName("and").getLength() > 0;
            if (isAnd || ifEle.getElementsByTagName("or").getLength() > 0) {
                ak.setAnd(isAnd);
                facts.addAll(parseAndOr(subElement(ifEle, isAnd ? "and" : "or")));
            } else facts.add(parseFact(ifEle));
        }
        return facts;
    }

    /**
     * Extracts rules from an "and" or "or" branch
     * @param ele   The element the branch is within
     * @return      An ArrayList of facts
     */
    protected ArrayList<Fact> parseAndOr(Element ele) {
        ArrayList<Fact> facts = new ArrayList<>();
        for (int idx = 0; idx < ele.getElementsByTagName("fact").getLength(); ++idx) {
            facts.add(parseFact(subElement(ele, "fact", idx)));
        }
        return facts;
    }
}
