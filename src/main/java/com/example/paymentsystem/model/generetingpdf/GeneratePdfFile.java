package com.example.paymentsystem.model.generetingpdf;


import com.example.paymentsystem.view.PaymentWrapper;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class GeneratePdfFile {
    private static final Logger logger = Logger.getLogger(GeneratePdfFile.class);

    public void createPdfFile(String fileName, List<PaymentWrapper> paymentWrapperList) {
        logger.info("Creating new pdf report");

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();
            Paragraph p = new Paragraph();
            p.add("Payments report");
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);
            Paragraph p2 = new Paragraph();
            p2.add("Text 2"); //no alignment
            Paragraph spaceLine = new Paragraph(" ");

            //Creating table cells
            Table table = new Table(5);

            Cell idColumn = new Cell("ID");
            Cell descriptionColumn = new Cell("Description");
            Cell typeColumn = new Cell("Type");
            Cell cardColumn = new Cell("Card");
            Cell amountColumn = new Cell("Amount");

            buildingRowOfTable(table, idColumn, descriptionColumn, typeColumn, cardColumn, amountColumn);

            for (PaymentWrapper paymentWrapper : paymentWrapperList) {
                idColumn = new Cell(paymentWrapper.getId() + "");
                descriptionColumn = new Cell(paymentWrapper.getDescription());
                typeColumn = new Cell(paymentWrapper.getPaymentCategory());
                cardColumn = new Cell(paymentWrapper.getCreditCardDestination() + "");
                amountColumn = new Cell(paymentWrapper.getMoney() + "");

                buildingRowOfTable(table, idColumn, descriptionColumn, typeColumn, cardColumn, amountColumn);
            }

            document.add(table);

            document.add(p2);
            Font f = new Font();
            f.setStyle(Font.BOLD);
            f.setSize(8);
            document.add(new Paragraph("This is my paragraph 3", f));
            document.close();
        } catch (DocumentException | IOException e) {
            logger.error(e);
        }

        logger.info("File " + fileName + " was successfully created");

    }

    private void buildingRowOfTable(Table table, Cell idColumn, Cell descriptionColumn, Cell typeColumn, Cell cardColumn, Cell amountColumn) {
        table.addCell(idColumn);
        table.addCell(descriptionColumn);
        table.addCell(typeColumn);
        table.addCell(cardColumn);
        table.addCell(amountColumn);
    }

    public boolean deleteFile() {
        return false;
    }

}
