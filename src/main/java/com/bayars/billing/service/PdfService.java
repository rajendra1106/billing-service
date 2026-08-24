package com.bayars.billing.service;

import com.bayars.billing.dto.BillItemResponse;
import com.bayars.billing.dto.BillResponse;
import org.openpdf.text.*;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    public byte[] generateBillPdf(BillResponse bill) {

        try (ByteArrayOutputStream outputStream =
                     new ByteArrayOutputStream()) {

            Document document = new Document(
                    PageSize.A4,
                    40,
                    40,
                    40,
                    40
            );

            PdfWriter.getInstance(document, outputStream);

            document.open();

            addHeader(document);
            addDate(document);
            addItemsTable(document, bill);
            addTotal(document, bill);

            document.close();

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to generate bill PDF",
                    e
            );
        }
    }

    private void addHeader(Document document)
            throws DocumentException {

        Font titleFont = new Font(
                Font.HELVETICA,
                20,
                Font.BOLD
        );

        Paragraph title = new Paragraph(
                "BILL",
                titleFont
        );

        title.setAlignment(Element.ALIGN_CENTER);

        document.add(title);

        Paragraph subtitle = new Paragraph(
                "Wholesale & Retail",
                new Font(Font.HELVETICA, 10)
        );

        subtitle.setAlignment(Element.ALIGN_CENTER);

        document.add(subtitle);

        document.add(Chunk.NEWLINE);
    }

    private void addDate(Document document)
            throws DocumentException {

        String date = LocalDate.now()
                .format(
                        DateTimeFormatter.ofPattern("dd-MM-yyyy")
                );

        Paragraph dateParagraph = new Paragraph(
                "Date: " + date,
                new Font(Font.HELVETICA, 10)
        );

        dateParagraph.setAlignment(
                Element.ALIGN_RIGHT
        );

        document.add(dateParagraph);

        document.add(Chunk.NEWLINE);
    }

    private void addItemsTable(
            Document document,
            BillResponse bill
    ) throws DocumentException {

        PdfPTable table = new PdfPTable(4);

        table.setWidthPercentage(100);

        table.setWidths(new float[]{
                4f,
                1f,
                2f,
                2f
        });

        addTableHeader(table, "Product");
        addTableHeader(table, "Qty");
        addTableHeader(table, "Rate");
        addTableHeader(table, "Total");

        for (BillItemResponse item : bill.items()) {

            table.addCell(item.productName());

            table.addCell(
                    String.valueOf(item.quantity())
            );

            table.addCell(
                    item.unitPrice()
                            .setScale(2)
                            .toPlainString()
            );

            table.addCell(
                    item.total()
                            .setScale(2)
                            .toPlainString()
            );
        }

        document.add(table);

        document.add(Chunk.NEWLINE);
    }

    private void addTableHeader(
            PdfPTable table,
            String text
    ) {

        PdfPCell cell = new PdfPCell(
                new Phrase(
                        text,
                        new Font(
                                Font.HELVETICA,
                                10,
                                Font.BOLD
                        )
                )
        );

        cell.setHorizontalAlignment(
                Element.ALIGN_CENTER
        );

        table.addCell(cell);
    }

    private void addTotal(
            Document document,
            BillResponse bill
    ) throws DocumentException {

        Paragraph total = new Paragraph(
                "GRAND TOTAL: ₹" +
                        bill.grandTotal()
                                .setScale(2)
                                .toPlainString(),
                new Font(
                        Font.HELVETICA,
                        14,
                        Font.BOLD
                )
        );

        total.setAlignment(
                Element.ALIGN_RIGHT
        );

        document.add(total);
    }
}