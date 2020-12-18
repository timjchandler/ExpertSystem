package tjc.rug.ExpertSystem.controller;

// Writing to pdf
import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfWriter;

// GUI
import com.itextpdf.text.pdf.draw.LineSeparator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

// Local
import tjc.rug.ExpertSystem.model.*;

// Other
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.sun.xml.internal.ws.commons.xmlutil.Converter.UTF_8;

public class QuestionController implements Initializable {

    private boolean finished = false;
    private ArrayList<RadioButton> rbArray = null;
    private ArrayList<CheckBox> cbArray = null;
    private TextField userName = null;
    private TextField caseNumberField = null;
    private TextField fileName = null;

    @FXML
    Label labelBelowNext;

    @FXML
    VBox questionVBox;

    @FXML
    Label questionLabel;

    @FXML
    Button next;

    @FXML
    Pane topPane;

    @FXML
    Label bannerLabel;

    /**
     * Load the first question
     * @param location      Not used
     * @param resources     Not used
     */
    public void initialize(URL location, ResourceBundle resources) {
        next();
    }

    /**
     * React to the button "next" being pressed. If no options were selected, present a prompt
     * to select something. Pass over to methods that record new facts and set the button arrays
     * to null
     */
    public void next() {
        if (finished) {
            savePDF();
            return;
        }
        boolean selectionMade = false;
        if (rbArray != null) selectionMade = checkRB();
        if (cbArray != null) selectionMade = checkCB();
        if (!selectionMade && (rbArray != null || cbArray != null)) {
            labelBelowNext.setText("Please select an answer");
            return;
        }
        labelBelowNext.setText("");
        rbArray = null;
        cbArray = null;
        buildScene(Model.getQuestion(true));
    }

    /**
     * Build the main question panel, update the question text and pass to other methods to add buttons.
     * If no new questions available shows a summary of gathered information
     * @param question      The question to build around
     */
    private void buildScene(Question question) {
        if (question == null) {
            finished = true;
            showSummary();
            return;
        }
        questionLabel.setText(question.getQuestionText());
        bannerLabel.setText(question.getHeading());
        next.setText("Next");
        questionVBox.getChildren().clear();
        buildButtons(question);
    }

    /**
     * Changes the visuals of the question pane to indicate the end, shows the calculated recommended
     * sentence
     */
    private void showSummary() {
        questionLabel.setText(Model.getSentence());
        topPane.setStyle("-fx-background-color: #1E1E24");
        bannerLabel.setText("Recommended Sentence:");
        bannerLabel.setTextFill(Color.web("#9A031E"));
        questionVBox.getChildren().clear();
        Text temp = new Text("To view the summary as a pdf click below\n\n");
        temp.getStyleClass().add("endtext");
        questionVBox.getChildren().add(temp);
        questionVBox.getChildren().add(buildFields());
        next.setText("Summary");
        next.setStyle("-fx-background-color: #e09f3e");
    }

    private VBox buildFields() {
        VBox fieldBox = new VBox();
        fieldBox.setSpacing(15);
        Text user = new Text("Enter your name:");
        userName = new TextField();
        userName.setPromptText("Optional field");

        Text caseNumber = new Text(("Enter the case number:"));
        caseNumberField = new TextField();
        caseNumberField.setPromptText("Optional field");

        Text file = new Text("Enter the name to save the file under:");
        fileName = new TextField("summary");

        user.setStyle("-fx-fill: #D9D9D9;");
        caseNumber.setStyle("-fx-fill: #D9D9D9;");
        file.setStyle("-fx-fill: #D9D9D9;");

        fieldBox.getChildren().add(user);
        fieldBox.getChildren().add(userName);
        fieldBox.getChildren().add(caseNumber);
        fieldBox.getChildren().add(caseNumberField);
        fieldBox.getChildren().add(file);
        fieldBox.getChildren().add(fileName);

        return fieldBox;
    }

    /**
     * Check the CheckBox ArrayList for new facts, add them to the model.
     * @return      True if facts obtained, false otherwise
     */
    private boolean checkCB() {
        if (Model.getQuestion(false) == null) return false;
        boolean out = false;
        for (CheckBox cb: cbArray) {
            if (cb.isSelected()) {
                Fact fact = Objects.requireNonNull(Model.getQuestion(false)).getAnswerFacts().get(cbArray.indexOf(cb));
                new Sentence(Model.getQuestion(false), cbArray.indexOf(cb));
                Model.addFact(fact);
                out = true;
            }
        }
        return out;
    }

    /**
     * Check the RadioButton ArrayList for new facts, add them to the model.
     * @return      True if facts obtained, false otherwise
     */
    private boolean checkRB() {
        if (Model.getQuestion(false) == null) return false;
        boolean out = false;
        for (RadioButton rb: rbArray) {
            if (rb.isSelected()) {
                Fact fact = Objects.requireNonNull(Model.getQuestion(false)).getAnswerFacts().get(rbArray.indexOf(rb));
                new Sentence(Model.getQuestion(false), rbArray.indexOf(rb));
                Model.addFact(fact);
                out = true;
            }
        }
        return out;
    }

    /**
     * Generates the buttons to display: RadioButtons for single choice answers or CheckBoxes for multi choice
     * Stores the buttons in the relevant ArrayList and adds them to the VBox
     * @param question  The question from which to add the answers
     */
    private void buildButtons(Question question) {
        boolean multi = question.getType() == Question.QuestionType.MULTI;
        if (multi) cbArray = new ArrayList<>();
        else rbArray = new ArrayList<>();
        ToggleGroup group = new ToggleGroup();
        for (String answer: question.getAnswers()) {
            ButtonBase btn = multi ? new CheckBox(answer) : new RadioButton(answer);
            btn.getStyleClass().add("optionbutton");
            btn.setWrapText(true);
            if (question.getAnswers().size() < 5) btn.setPadding(new Insets(0, 0, 10, 0));
            else btn.setPadding(new Insets(0, 0, 5, 0));
            questionVBox.getChildren().add(btn);
            if (multi) cbArray.add((CheckBox) btn);
            else {
                ((RadioButton) btn).setToggleGroup(group);
                rbArray.add((RadioButton) btn);
            }
        }
    }

    private void savePDF() {
        Document document = new Document();
        String filename = fileName.getText().replaceAll("[^A-Za-z]+", "");
        if (filename.equals("")) filename = "output";
        try
        {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename + ".pdf"));
            buildDocument(document);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        openPDF(filename);
        labelBelowNext.setText("Saved as " + filename + ".pdf");
    }

    private void buildDocument(Document doc) throws DocumentException, IOException {
        Font smallFont = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.ITALIC);
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 20);
        Font subheading = FontFactory.getFont(FontFactory.HELVETICA, 16);
        Font body = FontFactory.getFont(FontFactory.HELVETICA, 11);
        Font sentenceFont = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD);
        Font subsubheading = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.UNDERLINE);
        Font hyperlinkFont = FontFactory.getFont(FontFactory.HELVETICA, 10, new CMYKColor(98, 17, 0, 34));

        doc.open();

        // Title, timestamp and logo
        doc.add(new Paragraph("\nSentence Calculation\n", titleFont));
        String timestamp = new Timestamp(System.currentTimeMillis()).toString().split("[.]")[0];
        doc.add(new Paragraph(timestamp, smallFont));
        doc.add(new Paragraph("\n\n"));
        URL iconURL = getClass().getResource("/resources/media/icon.jpg");
        Image logo = Image.getInstance(iconURL);
        logo.scaleAbsolute(80, 86);
        logo.setAbsolutePosition(460, 710);
        doc.add(logo);

        // Line separators, user name, case number
        doc.add(Chunk.NEWLINE);
        doc.add(new LineSeparator());
        String caseNumber, userNameString;
        if (caseNumberField.getText().equals("")) caseNumber = "";
        else caseNumber = caseNumberField.getText() + " ";
        if (userName.getText().equals("")) userNameString = "";
        else userNameString = "presided by " + userName.getText();
        if (!(caseNumber.equals("") && userNameString.equals(""))) {
            doc.add(new Paragraph("Case " + caseNumber + userNameString));
            doc.add(Chunk.NEWLINE);
            doc.add(new LineSeparator());
        }

        // Get relevant outputs
        ArrayList<Output> outputs = State.getOutputs();
        ArrayList<String> links = new ArrayList<>();
        ArrayList<String> urls = new ArrayList<>();

        StringBuilder initialString = new StringBuilder();
        StringBuilder modifiedString = new StringBuilder();
        StringBuilder segmentString = new StringBuilder();
        StringBuilder incDecString = new StringBuilder();

        for (Output out: outputs) {
            if (!links.contains(out.getLink())) {
                links.add(out.getLink());
                urls.add(out.getUrl());
            }
            switch (out.getSection()) {
                case "Initial":
                    initialString.append(out.getDescription()).append("\n\n");
                    break;
                case "Modified":
                    modifiedString.append(out.getDescription()).append("\n\n");
                    break;
                case "Segment":
                    segmentString.append(out.getDescription()).append("\n\n");
                    break;
                case "IncDec":
                    incDecString.append(out.getDescription()).append("\n\n");
                    break;
                default:
                    System.out.println("ERROR: Unknown output section");
                    break;
            }
        }

        // Summary
        doc.add(new Paragraph("\n" + Model.getSentence(), sentenceFont));
        doc.add(new Paragraph("\nThis sentence was calculated in the following manner:", subheading));

        doc.add(new Paragraph("Initial Sentence Frame:\n", subsubheading));
        doc.add(new Paragraph(initialString.toString(), body));

        doc.add(new Paragraph("Modified Sentence Frame:\n", subsubheading));
        doc.add(new Paragraph(modifiedString.toString(), body));

        doc.add(new Paragraph("Segment of Sentence Frame:\n", subsubheading));
        doc.add(new Paragraph(segmentString.toString(), body));

        doc.add(new Paragraph("Increases and/or Decreases to Sentence:", subsubheading));
        doc.add(new Paragraph(incDecString.toString(), body));

        doc.add(Chunk.NEWLINE);
        doc.add(new LineSeparator());

        for (int idx = 0; idx < links.size(); ++idx) {
            Anchor anchor = new Anchor(links.get(idx), hyperlinkFont);
            anchor.setReference(urls.get(idx));
            doc.add(anchor);
        }

        doc.close();
    }

    private void openPDF(String filename) {
        try {
            File myFile = new File(filename + ".pdf");
            Desktop.getDesktop().open(myFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
