package tjc.rug.ExpertSystem.model;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PDFBuilder {

    private String caseNumber;
    private String userName;
    private final String fileName;

    private final StringBuilder initialString = new StringBuilder();

    private final List incDecList = new List(List.UNORDERED);
    private final List segmentList = new List(List.UNORDERED);
    private final List modifyList = new List(List.UNORDERED);

    private final ArrayList<String> links = new ArrayList<>();
    private final ArrayList<String> urls = new ArrayList<>();

    private float[] modFrame;

    /**
     * Fonts - Public to allow for consistency if used in other classes
     */
    public final Font smallFont = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.ITALIC);
    public final Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 20);
    public final Font subheading = FontFactory.getFont(FontFactory.HELVETICA, 16);
    public final Font sentenceFont = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD);
    public final Font hyperlinkFont = FontFactory.getFont(FontFactory.HELVETICA, 10, new CMYKColor(98, 17, 0, 34));
    public final Font subsubheading = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.UNDERLINE);
    public final Font naFont = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.ITALIC);
    public final Font noteFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

    /**
     * Constructor sets the caseNumber, userName and fileName variables. Sets up the empty lists for the summary.
     * @param caseNumber    An optional String entered by the user
     * @param userName      An optional String entered by the user
     * @param fileName      A mandatory string entered by the user
     */
    public PDFBuilder(String caseNumber, String userName, String fileName) {
        this.caseNumber = caseNumber;
        this.userName = userName;
        this.fileName = fileName;
        Chunk bullet = new Chunk("\u2022 ");
        incDecList.setListSymbol(bullet);
        segmentList.setListSymbol(bullet);
        modifyList.setListSymbol(bullet);
        modFrame = Model.getModBase();
    }

    /**
     * Strips illegal characters from the filename creates, generates and saves the document under that filename.
     * The method then opens the pdf file and returns the filename under which it was saved to be displayed in the
     * gui
     * @return
     */
    public String savePDF() {
        Document document = new Document();
        String filename = fileName.replaceAll("[^A-Za-z]+", "");
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
        return filename;
    }

    /**
     * Builds the document. Calls other methods to create sections.
     * @param doc       The document to build
     * @throws DocumentException
     * @throws IOException
     */
    private void buildDocument(Document doc) throws DocumentException, IOException {
        doc.open();

        addHeader(doc);
        addCaseInfo(doc);
        buildStringArrays();

        addSection(initialString, doc, "\nInitial Sentence Frame");

        doc.add(new Paragraph("\nModified Sentence Frame", subsubheading));
        addModIntroduction(doc);
        doc.add(modifyList);

        doc.add(new Paragraph("\nSegment of Sentence Frame\n", subsubheading));
        addSegmentIntroduction(doc);
        doc.add(segmentList);

        doc.add(new Paragraph("\nIncreases and/or Decreases to Sentence:\n", subsubheading));
        doc.add(incDecList);

        addReferences(urls, links, doc);

        doc.close();
    }

    /**
     * Builds the information base from the facts stored within the Model. Prints an error message if an unknown
     * output is read in
     */
    private void buildStringArrays() {
        ArrayList<Output> outputs = State.getOutputs();
        for (Output out: outputs) {
            if (out.getLink() != null) {
                links.add(out.getLink());
                urls.add(out.getUrl());
            }
            switch (out.getSection()) {
                case "Initial":
                    initialString.append(out.getDescription()).append("\n\n");
                    break;
                case "modified":
                    modifyList.add(new ListItem(out.getDescription() + "\n", noteFont));
                    break;
                case "segment":
                    segmentList.add(new ListItem(out.getDescription() + "\n", noteFont));
                    break;
                case "IncDec":
                    incDecList.add(new ListItem(out.getDescription() + "\n", noteFont));
                    break;
                default:
                    System.out.println("ERROR: Unknown output section");
                    break;
            }
        }
    }

    /**
     * Adds the title, case number, user name, date, sentence and logo to the top of the pdf
     * @param doc       The document to add the header to.
     * @throws DocumentException
     * @throws IOException
     */
    private void addHeader(Document doc) throws DocumentException, IOException {
        doc.add(new Paragraph("\nSentence Calculation\n", titleFont));
        String timestamp = new Timestamp(System.currentTimeMillis()).toString().split("[.]")[0];
        doc.add(new Paragraph(timestamp, smallFont));
        doc.add(new Paragraph("\n\n"));
        URL iconURL = getClass().getResource("/resources/media/icon.jpg");
        Image logo = Image.getInstance(iconURL);
        logo.scaleAbsolute(80, 86);
        logo.setAbsolutePosition(460, 710);
        doc.add(logo);
    }

    /**
     * Adds a short summary to the start of the Sentence modification section.
     * @param doc   The document to add the summary to.
     * @throws DocumentException
     */
    private void addModIntroduction(Document doc) throws DocumentException {
        DecimalFormat twoDP = new DecimalFormat("#0.0");
        doc.add(new Paragraph("The modifications listed below lead to an updated sentence frame of " + twoDP.format(modFrame[0])
        + " to " + twoDP.format(modFrame[1]) + " years.\n\n"));
    }

    /**
     * Calculates the segment of the sentence and adds a short summary of it to the document.
     * @param doc   The document to add the summary to.
     * @throws DocumentException
     */
    private void addSegmentIntroduction(Document doc) throws DocumentException {
        float percentageSegment = (float) Model.getSentenceSegment();
        percentageSegment /= 26.0;
        float[] sentenceRange = modFrame;
        float currentSegment = (sentenceRange[1] - sentenceRange[0]) * percentageSegment;
        currentSegment += sentenceRange[0];
        DecimalFormat twoDP = new DecimalFormat("#0.0");
        doc.add(new Paragraph("From the following questions the perpetrators actions placed them at " + (int) (percentageSegment * 100) +
                "% of the sentence frame. This is a measure of the severity of the violation or endangerment of the legal interest concerned.\n"
                + "This leads to an unweighted sentence of " + twoDP.format(currentSegment) + " years within the frame " + twoDP.format(sentenceRange[0]) + " to "
                + twoDP.format(sentenceRange[1]) + " years.\n\nThis calculation is based off constant jurisprudence.\n\n", noteFont));
    }

    /**
     * Adds information about the case to the document
     * @param doc   The document to add to
     * @throws DocumentException
     */
    private void addCaseInfo(Document doc) throws DocumentException {
        doc.add(Chunk.NEWLINE);
        doc.add(new LineSeparator());
        if (!caseNumber.equals("")) caseNumber = caseNumber + " ";
        if (!userName.equals("")) userName = "presided by " + userName;
        if (!(caseNumber.equals("") && userName.equals(""))) {
            doc.add(new Paragraph("Case " + caseNumber + userName));
            doc.add(Chunk.NEWLINE);
            doc.add(new LineSeparator());
        }
        doc.add(new Paragraph("\n" + Model.getSentence(), sentenceFont));

        doc.add(new Paragraph("\nThis sentence is rounded to the nearest 3 months for sentences under 2 years, 6 months for sentences between 2 and 10 years, and to the nearest year for sentences over 10 years.", naFont));
        doc.add(new Paragraph("\nThis sentence was calculated in the following manner:", subheading));
    }

    /**
     * Adds a section to the document
     * @param sb    A stringbuilder object
     * @param doc   The document to add to
     * @param heading   The heading for the section
     * @throws DocumentException
     */
    private void addSection(StringBuilder sb, Document doc, String heading) throws DocumentException {
        Font body = FontFactory.getFont(FontFactory.HELVETICA, 11);
        doc.add(new Paragraph(heading + ":\n", subsubheading));
        doc.add(new Paragraph(sb.toString(), body));
    }

    /**
     * Adds a list of reference links to the document
     * @param urls      The URL to link to
     * @param links     The name of the link
     * @param doc       The document to add to
     * @throws DocumentException
     */
    private void addReferences(ArrayList<String> urls, ArrayList<String> links, Document doc) throws DocumentException {
        doc.add(Chunk.NEWLINE);
        doc.add(new LineSeparator());
        for (int idx = 0; idx < links.size(); ++idx) {
            Anchor anchor = new Anchor(links.get(idx) + "\n", hyperlinkFont);
            anchor.setReference(urls.get(idx));
            doc.add(anchor);
        }

    }

    /**
     * Opens the specified document on the users desktop
     * @param filename  The name of the document to open
     */
    private void openPDF(String filename) {
        try {
            File myFile = new File(filename + ".pdf");
            Desktop.getDesktop().open(myFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
