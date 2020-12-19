package tjc.rug.ExpertSystem.model;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;

public class PDFBuilder {

    private String caseNumber;
    private String userName;
    private String fileName;

    public PDFBuilder(String caseNumber, String userName, String fileName) {
        this.caseNumber = caseNumber;
        this.userName = userName;
        this.fileName = fileName;
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
        Font smallFont = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.ITALIC);
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 20);
        Font subheading = FontFactory.getFont(FontFactory.HELVETICA, 16);
        Font sentenceFont = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD);

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
        if (!caseNumber.equals("")) caseNumber = caseNumber + " ";
//        else caseNumber = caseNumberField.getText() + " ";
        if (!userName.equals("")) userName = "presided by " + userName;
        if (!(caseNumber.equals("") && userName.equals(""))) {
            doc.add(new Paragraph("Case " + caseNumber + userName));
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
        addSection(initialString, doc, "Initial Sentence Frame");
        addSection(modifiedString, doc, "Modified Sentence Frame");
        addSection(segmentString, doc, "Segment of Sentence Frame");
        addSection(incDecString, doc, "Increases and/or Decreases to Sentence");

        addReferences(urls, links, doc);

        doc.close();
    }

    private void addSection(StringBuilder sb, Document doc, String heading) throws DocumentException {
        Font subsubheading = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.UNDERLINE);
        Font naFont = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.ITALIC);
        Font body = FontFactory.getFont(FontFactory.HELVETICA, 11);
        doc.add(new Paragraph(heading + ":\n", subsubheading));
        if (sb.length() == 0) doc.add(new Paragraph("Not applicable in this sentence.\n", naFont));
        else doc.add(new Paragraph(sb.toString(), body));
    }

    private void addReferences(ArrayList<String> urls, ArrayList<String> links, Document doc) throws DocumentException {
        Font hyperlinkFont = FontFactory.getFont(FontFactory.HELVETICA, 10, new CMYKColor(98, 17, 0, 34));
        doc.add(Chunk.NEWLINE);
        doc.add(new LineSeparator());
        for (int idx = 0; idx < links.size(); ++idx) {
            Anchor anchor = new Anchor(links.get(idx), hyperlinkFont);
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
