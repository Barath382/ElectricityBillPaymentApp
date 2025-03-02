package in.customer.electricitypayment.invoice;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import in.customer.electricitypayment.model.PaymentTransaction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class InvoiceService {

    public static ResponseEntity<byte[]> generatePaymentInvoice(PaymentTransaction paymentTransaction) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();

        Paragraph title = new Paragraph("Electricity Payment Invoice", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLUE));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" "));


        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new int[]{50, 50});


        PdfPCell customerCell = new PdfPCell();
        customerCell.setBorder(PdfPCell.NO_BORDER);
        customerCell.addElement(new Paragraph("Customer Details:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12,BaseColor.MAGENTA)));
        customerCell.addElement(new Paragraph("Name: " + paymentTransaction.getBillId().getCustomer().getCustomerName()));
        customerCell.addElement(new Paragraph("Customer ID: " + paymentTransaction.getBillId().getCustomer().getCustomerId()));
        customerCell.addElement(new Paragraph("Phone: " + paymentTransaction.getBillId().getCustomer().getPhoneNumber()));
        customerCell.addElement(new Paragraph("Email: " + paymentTransaction.getBillId().getCustomer().getEmail()));
        customerCell.addElement(new Paragraph("Address: " + paymentTransaction.getBillId().getCustomer().getAddress()));
        headerTable.addCell(customerCell);


        PdfPCell companyCell = new PdfPCell();
        companyCell.setBorder(PdfPCell.NO_BORDER);
        companyCell.addElement(new Paragraph("Company Details:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12,BaseColor.GREEN)));
        companyCell.addElement(new Paragraph("Bharat Bijili Corporation (BBC) Electricity"));
        companyCell.addElement(new Paragraph("Address: Pune, Maharashtra"));
        companyCell.addElement(new Paragraph("Phone: +123-456-7890"));
        companyCell.addElement(new Paragraph("Email: support@electricityboard.com"));
        headerTable.addCell(companyCell);

        document.add(headerTable);
        document.add(new Paragraph(" "));


        document.add(new Paragraph("---------------------------------------------------------------- ".repeat(2)));
        document.add(new Paragraph(" "));


        PdfPTable detailsTable = new PdfPTable(2);
        detailsTable.setWidthPercentage(100);
        detailsTable.setWidths(new int[]{50, 50});


        PdfPCell billDetailsHeader = new PdfPCell(new Paragraph("Bill Details", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
        billDetailsHeader.setColspan(2);
        billDetailsHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        detailsTable.addCell(billDetailsHeader);

        detailsTable.addCell("Bill ID:");
        detailsTable.addCell(String.valueOf(paymentTransaction.getBillId().getBillId()));
        detailsTable.addCell("Units Consumed:");
        detailsTable.addCell(String.valueOf(paymentTransaction.getBillId().getUnits()));
        detailsTable.addCell("Bill Type:");
        detailsTable.addCell(paymentTransaction.getBillId().getBillType().toString());
        detailsTable.addCell("Bill Date:");
        detailsTable.addCell(paymentTransaction.getBillId().getBillDate().toString());
        detailsTable.addCell("Due Date:");
        detailsTable.addCell(paymentTransaction.getBillId().getDueDate().toString());
        detailsTable.addCell("Total Amount:");
        detailsTable.addCell("₹" + paymentTransaction.getBillId().getTotalAmount());


        PdfPCell transactionDetailsHeader = new PdfPCell(new Paragraph("Transaction Details", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
        transactionDetailsHeader.setColspan(2);
        transactionDetailsHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        detailsTable.addCell(transactionDetailsHeader);

        detailsTable.addCell("Transaction ID:");
        detailsTable.addCell(paymentTransaction.getTransactionId());
        detailsTable.addCell("Payment Method:");
        detailsTable.addCell(paymentTransaction.getTransactionMethod().toString());
        detailsTable.addCell("Amount Paid:");
        detailsTable.addCell("₹" + paymentTransaction.getTransactionAmount());
        detailsTable.addCell("Transaction Date:");
        detailsTable.addCell(paymentTransaction.getTransactionDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        detailsTable.addCell("Transaction Status:");
        detailsTable.addCell(paymentTransaction.getTransactionStatus().toString());
        detailsTable.addCell("Transaction Message:");
        detailsTable.addCell(paymentTransaction.getTransactionMessage());


        document.add(detailsTable);

        document.close();


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "invoice.pdf");

        return ResponseEntity.ok().headers(headers).body(out.toByteArray());
    }
}
