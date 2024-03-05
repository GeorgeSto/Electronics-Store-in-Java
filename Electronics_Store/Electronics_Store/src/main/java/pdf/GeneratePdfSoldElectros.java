package pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import model.Electro;


public class GeneratePdfSoldElectros {

    public void generatePdf(final String fileName, List<Electro> bookList) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4, 25, 25, 25, 25);

        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(fileName));

        document.open();

        Paragraph title = new Paragraph("Sold Electros");

        document.add(title);

        PdfPTable t = new PdfPTable(4);
        t.setSpacingBefore(25);
        t.setSpacingAfter(25);

        t.addCell(new PdfPCell(new Phrase("Electro ID")));
        t.addCell(new PdfPCell(new Phrase("Electro Title")));
        t.addCell(new PdfPCell(new Phrase("Electro Author")));
        t.addCell(new PdfPCell(new Phrase("Electro Published Date")));

        for(Electro b : bookList) {
            t.addCell(String.valueOf(b.getId()));
            t.addCell(b.getTitle());
            t.addCell(b.getCompany());
            t.addCell(String.valueOf(b.getPublishedDate()));
        }

        document.add(t);

        document.close();
        pdfWriter.close();
    }
}