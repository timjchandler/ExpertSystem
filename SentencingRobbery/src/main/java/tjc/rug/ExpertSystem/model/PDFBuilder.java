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
    private final StringBuilder modifiedString = new StringBuilder();
    private final StringBuilder segmentString = new StringBuilder();
    private final StringBuilder incDecString = new StringBuilder();

    private final List incDecList = new List(List.UNORDERED);
    private final List segmentList = new List(List.UNORDERED);

    private final ArrayList<String> links = new ArrayList<>();
    private final ArrayList<String> urls = new ArrayList<>();

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


    public PDFBuilder(String caseNumber, String userName, String fileName) {
        this.caseNumber = caseNumber;
        this.userName = userName;
        this.fileName = fileName;
        Chunk bullet = new Chunk("\u2022 ");
        incDecList.setListSymbol(bullet);
        segmentList.setListSymbol(bullet);
    }

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

    private void buildDocument(Document doc) throws DocumentException, IOException {
        doc.open();

        addHeader(doc);
        addCaseInfo(doc);
        buildStringArrays();

        addSection(initialString, doc, "\nInitial Sentence Frame");
        addSection(modifiedString, doc, "\nModified Sentence Frame");

        doc.add(new Paragraph("\nSegment of Sentence Frame\n", subsubheading));
        addSegmentIntroduction(doc);
        doc.add(segmentList);

        doc.add(new Paragraph("\nIncreases and/or Decreases to Sentence:\n", subsubheading));
        doc.add(incDecList);

        addReferences(urls, links, doc);

        doc.close();
    }

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
                case "Modified":
                    modifiedString.append(out.getDescription()).append("\n\n");
                    break;
                case "segment":
                    segmentString.append(out.getDescription()).append("\n\n");
                    segmentList.add(new ListItem(out.getDescription() + "\n", noteFont));
                    break;
                case "IncDec":
//                    incDecString.append(out.getDescription()).append("\n\n");
                    incDecList.add(new ListItem(out.getDescription() + "\n", noteFont));
                    break;
                default:
                    System.out.println("ERROR: Unknown output section");
                    break;
            }
        }
    }

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

    private void addSegmentIntroduction(Document doc) throws DocumentException {
        float percentageSegment = (float) Model.getSentenceSegment();
        percentageSegment /= 26.0;
        float[] sentenceRange = Model.getSentenceBase();
        float currentSegment = (sentenceRange[1] - sentenceRange[0]) * percentageSegment;
        currentSegment += sentenceRange[0];
        DecimalFormat twoDP = new DecimalFormat("#.0");
        doc.add(new Paragraph("From the following questions the perpetrators actions placed them at " + (int) (percentageSegment * 100) +
                "% of the sentence frame. This is a measure of the severity of the violation or endangerment of the legal interest concerned.\n"
                + "This leads to an unweighted sentence of " + twoDP.format(currentSegment) + " years within the frame " + sentenceRange[0] + " to "
                + sentenceRange[1] + " years.\n\nThis calculation is based off constant jurisprudence.\n\n", noteFont));
    }

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

    private void addSection(StringBuilder sb, Document doc, String heading) throws DocumentException {
        Font body = FontFactory.getFont(FontFactory.HELVETICA, 11);
        doc.add(new Paragraph(heading + ":\n", subsubheading));
        doc.add(new Paragraph(sb.toString(), body));
    }

    private void addReferences(ArrayList<String> urls, ArrayList<String> links, Document doc) throws DocumentException {
        doc.add(Chunk.NEWLINE);
        doc.add(new LineSeparator());
        for (int idx = 0; idx < links.size(); ++idx) {
            Anchor anchor = new Anchor(links.get(idx) + "\n", hyperlinkFont);
            anchor.setReference(urls.get(idx));
            doc.add(anchor);
        }

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
