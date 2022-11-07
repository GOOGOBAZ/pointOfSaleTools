/*
    * To change this license header, choose License Headers in Project Properties.
    * To change this template file, choose Tools | Templates
    * and open the template in the editor.
 */
package googo.pmms.project2.reportsHelper;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import static com.itextpdf.text.Rectangle.BOTTOM;
import static com.itextpdf.text.Rectangle.LEFT;
import static com.itextpdf.text.Rectangle.RIGHT;
import static com.itextpdf.text.Rectangle.TOP;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEvent;
import com.itextpdf.text.pdf.PdfWriter;
import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databases.DatabaseQuaries;
import googo.pmms.project2.databaseConnectors.JdbcConnector;
import googo.pmms.project2.databases.ReportsDatabase;
import googo.pmms.project2.frameHelper.AssetRegisterModel;
import googo.pmms.project2.frameHelper.ListDataModel;
import googo.pmms.project2.frameHelper.ListDataModelCreateInt;
import googo.pmms.project2.frameHelper.ListDataModel_1;
import googo.pmms.project2.frameHelper.MyTableModel;
import googo.pmms.project2.frameHelper.ReportsModelStatusReport;
import googo.pmms.project2.frameHelper.budgetEstimatesModel;
import googo.pmms.project2.frameHelper.loanSatementModel;
import googo.pmms.project2.frames.PostingEntryWindow;
import java.awt.Component;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Stanchart
 */
public class OtherLoanReports implements PdfPageEvent, googo.pmms.project2.financialStatement.BalanceSheet {

//    Amortization amortize = new Amortization();
    Calendar calN = Calendar.getInstance();
    ReportsDatabase getdata = new ReportsDatabase();
    fileInputOutPutStreams fios = new fileInputOutPutStreams();
    Formartter fmt = new Formartter();
    Date Trndate, valuedate;
    ArrayList<String> data4, column1;
    ArrayList<ArrayList<String>> data5;
    JOptionPane p;
    MyTableModel model;
    Date date;
    SimpleDateFormat df;
    String text;
    int realMonth, otherMonth;
    String dates, dates2, getFieldValue, actualFieldValue, jTFuserId1mt, today, thistime, today1, newDate1, jTFuserId1mt1, newDate11, today2;
    Integer Value, Value1;
    double newbalance, ledgerBalance, creditamount;
    GregorianCalendar cal = new GregorianCalendar();
    JdbcConnector csx = new JdbcConnector(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "AccountDBDetails.txt")));
    DatabaseQuaries quaries = new DatabaseQuaries();
    ReportsDatabase rdb = new ReportsDatabase();
    int pagenumber = 0;
    DecimalFormat f = new DecimalFormat("#");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdfS = new SimpleDateFormat("yyyy-MM-dd");
    String userId;
    ListDataModel model1;
    ListDataModel_1 model1b;
    ListDataModelCreateInt model1bc;

    public void setUserID(String userid) {
        this.userId = userid;

        String title;

        List<String> tableHeaders = new ArrayList();

        List< List<Object>> dataBody = new ArrayList();

        Map<Integer, List<Object>> data = new HashMap();
    }

    public boolean createLoansToStaffReport(String fileName) {
        boolean loansToStaff = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyLoansToStaff(fileName);
                loansToStaff = true;
            }

        } else {
            createActuallyLoansToStaff(fileName);
            loansToStaff = true;
        }

        return loansToStaff;
    }

    private void createActuallyLoansToStaff(String file) {

        try {

            Paragraph headerz = createHeading("loanTostaff", "", "");
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", file + ".pdf")));
            PdfPTable body = createTheLoansToStaff();

            Font font1 = new Font(Font.FontFamily.COURIER, 14, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();

            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));
            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);
            ct.setIndentationLeft(50f);
            ct.setIndentationRight(200f);
            document.add(ct);

            Image image1 = Image.getInstance("strawberry3.jpg");
            image1.setAbsolutePosition(49f, 1080f);
            image1.scaleAbsolute(43f, 43f);
            document.add(image1);
            document.add(headerz);
            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(280f);
            currency.setIndentationRight(30f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createTheLoansToStaff() {
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        float[] columnWidths = {15f, 50f, 30f, 30f, 30f, 30f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 14, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 14, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Name", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Amt Loan", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Principal", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Interest", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Amnt Remain", font2));
        PdfPCell cell7 = new PdfPCell(new Paragraph("Amt Paid", font2));
        cell1.setBackgroundColor(BaseColor.ORANGE);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.ORANGE);
        cell3.setBackgroundColor(BaseColor.ORANGE);
        cell4.setBackgroundColor(BaseColor.ORANGE);
        cell5.setBackgroundColor(BaseColor.ORANGE);
        cell6.setBackgroundColor(BaseColor.ORANGE);
        cell7.setBackgroundColor(BaseColor.ORANGE);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);
        cell7.setBorderWidth(2f);
        cell6.setBorderColorBottom(BaseColor.BLACK);

        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);
        cell5.disableBorderSide(LEFT);
        cell6.disableBorderSide(LEFT);
        cell7.disableBorderSide(LEFT);

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);
        cell7.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);
        cell7.disableBorderSide(RIGHT);
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.staffLoans();
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {

                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(16.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(16.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font2));
                            cellx3.setFixedHeight(16.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));

                            cellx4.setFixedHeight(16.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                            cellx5.setFixedHeight(16.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font2));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setPadding(.5f);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }

                        if (z == 6) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(6).toString()), font2));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setPadding(.5f);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }
                        z++;
                    }
                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(16.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(16.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font1));
                            cellx3.setFixedHeight(16.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font1));

                            cellx4.setFixedHeight(16.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font1));
                            cellx5.setFixedHeight(16.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font1));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setPadding(.5f);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }

                        if (z == 6) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(6).toString()), font1));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setPadding(.5f);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }

                        z++;
                    }
                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
        }
        return table;

    }

    public boolean createLoanArrearsReport(String startDate, String endDate, String fileName) {

        boolean loanArrears = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyLoanArrearsReport(startDate, endDate, fileName);
                loanArrears = true;
            }

        } else {
            createActuallyLoanArrearsReport(startDate, endDate, fileName);
            loanArrears = true;
        }

        return loanArrears;
    }

    private void createActuallyLoanArrearsReport(String startDate, String endDate, String fileName) {
        try {

            Paragraph headerz = createHeading("loan_Arrears", "", "");
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));
            PdfPTable body = createTheArrearsReport(startDate, endDate);

            Font font1 = new Font(Font.FontFamily.COURIER, 14, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();

            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));
            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);
            ct.setIndentationLeft(50f);
            ct.setIndentationRight(200f);
            document.add(ct);

            Image image1 = Image.getInstance("strawberry3.jpg");
            image1.setAbsolutePosition(49f, 1080f);
            image1.scaleAbsolute(43f, 43f);
            document.add(image1);
            document.add(headerz);
            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(280f);
            currency.setIndentationRight(30f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public PdfPTable createTheArrearsReport(String startDate, String endDate) {
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        float[] columnWidths = {14f, 50f, 30f, 30f, 30f, 24f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 14, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 14, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Borrower", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Loan Amount Borrowed", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Loan Amount Paid", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Amount Remaining", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Instalmts Arrears", font2));
        PdfPCell cell7 = new PdfPCell(new Paragraph("Amount in Arrears", font2));

        cell1.setBackgroundColor(BaseColor.ORANGE);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.ORANGE);
        cell3.setBackgroundColor(BaseColor.ORANGE);
        cell4.setBackgroundColor(BaseColor.ORANGE);   //sets BG color to yellow.
        cell5.setBackgroundColor(BaseColor.ORANGE);
        cell6.setBackgroundColor(BaseColor.ORANGE);
        cell7.setBackgroundColor(BaseColor.ORANGE);   //sets BG color to yellow.

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);         // sets border width to 3 units
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);
        cell7.setBorderWidth(2f);         // sets border width to 3 units

        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);         // sets border width to 3 units
        cell5.disableBorderSide(LEFT);
        cell6.disableBorderSide(LEFT);
        cell7.disableBorderSide(LEFT);         // sets border width to 3 units

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);       // sets border width to 3 units
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);
        cell7.disableBorderSide(TOP);       // sets border width to 3 units

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);
        cell7.disableBorderSide(RIGHT);         // sets border width to 3 units

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.loansInArrears(startDate, endDate);
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(16.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(16.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font2));
                            cellx3.setFixedHeight(16.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                            cellx4.setFixedHeight(16.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }

                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                            cellx5.setFixedHeight(16.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }

                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(5).toString(), font2));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.setPadding(.5f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx6);
                        }
                        if (z == 6) {

                            PdfPCell cellx7 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(6).toString()), font2));
                            cellx7.setFixedHeight(16.2f);
                            cellx7.setPadding(.5f);
                            cellx7.disableBorderSide(LEFT);
                            cellx7.disableBorderSide(TOP);
                            cellx7.disableBorderSide(RIGHT);
                            cellx7.setBorderColorBottom(BaseColor.BLACK);
                            cellx7.setBorderColorTop(BaseColor.WHITE);
                            cellx7.setBorderColorLeft(BaseColor.WHITE);
                            cellx7.setBorderColorRight(BaseColor.WHITE);
                            cellx7.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx7.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx7);
                        }

                        z++;
                    }

                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(16.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(16.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font1));
                            cellx3.setFixedHeight(16.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font1));
                            cellx4.setFixedHeight(16.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }

                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font1));
                            cellx5.setFixedHeight(16.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }

                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(5).toString(), font1));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.setPadding(.5f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx6);
                        }
                        if (z == 6) {

                            PdfPCell cellx7 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(6).toString()), font1));
                            cellx7.setFixedHeight(16.2f);
                            cellx7.setPadding(.5f);
                            cellx7.disableBorderSide(LEFT);
                            cellx7.disableBorderSide(TOP);
                            cellx7.disableBorderSide(RIGHT);
                            cellx7.setBorderColorBottom(BaseColor.BLACK);
                            cellx7.setBorderColorTop(BaseColor.WHITE);
                            cellx7.setBorderColorLeft(BaseColor.WHITE);
                            cellx7.setBorderColorRight(BaseColor.WHITE);
                            cellx7.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx7.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx7);
                        }

                        z++;
                    }
                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
        }
        return table;

    }

    public boolean createLoanArrearsReportDetails(String fileName, String accountNumber, String accountName) {
        boolean arrearsDetailsR = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyArrearsDetailsReport(fileName, accountNumber, accountName);
                arrearsDetailsR = true;
            }

        } else {
            createActuallyArrearsDetailsReport(fileName, accountNumber, accountName);
            arrearsDetailsR = true;
        }

        return arrearsDetailsR;
    }

    private void createActuallyArrearsDetailsReport(String fileName, String accountNumber, String accountName) {
        try {

            Paragraph headerz = createHeading("arrearsDetails", accountName, accountNumber);
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));
            PdfPTable body = createTheArrearsDetailsReport(accountNumber);

            Font font1 = new Font(Font.FontFamily.COURIER, 14, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);
            ct.setIndentationLeft(50f);
            ct.setIndentationRight(200f);
            document.add(ct);

            Image image1 = Image.getInstance("strawberry3.jpg");
            image1.setAbsolutePosition(49f, 1080f);
            image1.scaleAbsolute(43f, 43f);
            document.add(image1);
            document.add(headerz);
            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(280f);
            currency.setIndentationRight(30f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createTheArrearsDetailsReport(String accountNumber) {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        float[] columnWidths = {15f, 30f, 30f, 30f, 35f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 14, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 14, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Instalment Amount Due", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Charges Accrued", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Total Instalment Due", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Instalment Due Date", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Aging", font2));

        cell1.setBackgroundColor(BaseColor.ORANGE);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.ORANGE);
        cell3.setBackgroundColor(BaseColor.ORANGE);
        cell4.setBackgroundColor(BaseColor.ORANGE);
        cell5.setBackgroundColor(BaseColor.ORANGE);
        cell6.setBackgroundColor(BaseColor.ORANGE);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);
        cell6.setBorderColorBottom(BaseColor.BLACK);

        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);
        cell5.disableBorderSide(LEFT);
        cell6.disableBorderSide(LEFT);

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.loanArrearsDetails(accountNumber);
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(16.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(1).toString()), font2));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(16.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx2.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font2));
                            cellx3.setFixedHeight(16.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));

                            cellx4.setFixedHeight(16.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(realData.get(4).toString(), font2));
                            cellx5.setFixedHeight(16.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(5).toString(), font2));

                            cellx6.setFixedHeight(16.2f);
                            cellx6.setPadding(.5f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx6);
                        }

                        z++;
                    }

                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(16.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(1).toString()), font1));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(16.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx2.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font1));
                            cellx3.setFixedHeight(16.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font1));

                            cellx4.setFixedHeight(16.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(realData.get(4).toString(), font1));
                            cellx5.setFixedHeight(16.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(5).toString(), font1));

                            cellx6.setFixedHeight(16.2f);
                            cellx6.setPadding(.5f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx6);
                        }

                        z++;
                    }
                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));

        }
        return table;

    }

    public boolean createLoanPortfolioReport(String fileName) {

        boolean loanPortfolio = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyLoanPortfolio(fileName);
                loanPortfolio = true;
            }

        } else {
            createActuallyLoanPortfolio(fileName);
            loanPortfolio = true;
        }

        return loanPortfolio;
    }

    private void createActuallyLoanPortfolio(String fileName) {

        try {

            Paragraph headerz = createHeading("loan_portfolio", "", "");
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));
            PdfPTable body = createTheLoanPorfolio();

            Font font1 = new Font(Font.FontFamily.COURIER, 14, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));
            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);
            ct.setIndentationLeft(50f);
            ct.setIndentationRight(200f);
            document.add(ct);

            Image image1 = Image.getInstance("strawberry3.jpg");
            image1.setAbsolutePosition(49f, 1080f);
            image1.scaleAbsolute(43f, 43f);
            document.add(image1);
            document.add(headerz);
            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(280f);
            currency.setIndentationRight(30f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public PdfPTable createTheLoanPorfolio() {
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        float[] columnWidths = {15f, 50f, 30f, 30f, 30f, 30f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 14, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 14, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Name", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Amt Loan", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Principal", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Interest", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Amnt Remain", font2));
        PdfPCell cell7 = new PdfPCell(new Paragraph("Amt Paid", font2));
        cell1.setBackgroundColor(BaseColor.ORANGE);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.ORANGE);
        cell3.setBackgroundColor(BaseColor.ORANGE);
        cell4.setBackgroundColor(BaseColor.ORANGE);
        cell5.setBackgroundColor(BaseColor.ORANGE);
        cell6.setBackgroundColor(BaseColor.ORANGE);
        cell7.setBackgroundColor(BaseColor.ORANGE);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);
        cell7.setBorderWidth(2f);
        cell6.setBorderColorBottom(BaseColor.BLACK);

        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);
        cell5.disableBorderSide(LEFT);
        cell6.disableBorderSide(LEFT);
        cell7.disableBorderSide(LEFT);

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);
        cell7.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);
        cell7.disableBorderSide(RIGHT);
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.loanPortfolio();
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {

                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(16.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(16.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font2));
                            cellx3.setFixedHeight(16.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));

                            cellx4.setFixedHeight(16.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                            cellx5.setFixedHeight(16.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font2));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setPadding(.5f);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }

                        if (z == 6) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(6).toString()), font2));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setPadding(.5f);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }
                        z++;
                    }
                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(16.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(16.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font1));
                            cellx3.setFixedHeight(16.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font1));

                            cellx4.setFixedHeight(16.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font1));
                            cellx5.setFixedHeight(16.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font1));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setPadding(.5f);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }

                        if (z == 6) {

                            PdfPCell cellx7 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(6).toString()), font1));
                            cellx7.setFixedHeight(16.2f);
                            cellx7.disableBorderSide(LEFT);
                            cellx7.disableBorderSide(TOP);
                            cellx7.disableBorderSide(RIGHT);
                            cellx7.setPadding(.5f);
                            cellx7.setBorderColorBottom(BaseColor.BLACK);
                            cellx7.setBorderColorTop(BaseColor.WHITE);
                            cellx7.setBorderColorLeft(BaseColor.WHITE);
                            cellx7.setBorderColorRight(BaseColor.WHITE);
                            cellx7.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx7.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx7);
                        }

                        z++;
                    }
                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
        }
        return table;

    }

    public boolean createLoanStatusReport(String fileName) {

        boolean loanPortfolio = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyStatusfolio(fileName);
                loanPortfolio = true;
            }

        } else {
            createActuallyStatusfolio(fileName);
            loanPortfolio = true;
        }

        return loanPortfolio;
    }

    private void createActuallyStatusfolio(String fileName) {

        try {

            Paragraph headerz = createHeading("loan_portfolio", "", "");
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));
            PdfPTable body = createTheLoanPorfolio();

            Font font1 = new Font(Font.FontFamily.COURIER, 14, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));
            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);
            ct.setIndentationLeft(50f);
            ct.setIndentationRight(200f);
            document.add(ct);

            Image image1 = Image.getInstance("strawberry3.jpg");
            image1.setAbsolutePosition(49f, 1080f);
            image1.scaleAbsolute(43f, 43f);
            document.add(image1);
            document.add(headerz);
            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(280f);
            currency.setIndentationRight(30f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public PdfPTable createTheLoanStatus() {
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        float[] columnWidths = {15f, 50f, 30f, 30f, 30f, 30f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 14, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 14, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Name", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Amt Loan", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Principal", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Interest", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Amnt Remain", font2));
        PdfPCell cell7 = new PdfPCell(new Paragraph("Amt Paid", font2));
        cell1.setBackgroundColor(BaseColor.ORANGE);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.ORANGE);
        cell3.setBackgroundColor(BaseColor.ORANGE);
        cell4.setBackgroundColor(BaseColor.ORANGE);
        cell5.setBackgroundColor(BaseColor.ORANGE);
        cell6.setBackgroundColor(BaseColor.ORANGE);
        cell7.setBackgroundColor(BaseColor.ORANGE);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);
        cell7.setBorderWidth(2f);
        cell6.setBorderColorBottom(BaseColor.BLACK);

        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);
        cell5.disableBorderSide(LEFT);
        cell6.disableBorderSide(LEFT);
        cell7.disableBorderSide(LEFT);

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);
        cell7.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);
        cell7.disableBorderSide(RIGHT);
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.loanPortfolio();
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {

                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(16.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(16.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font2));
                            cellx3.setFixedHeight(16.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));

                            cellx4.setFixedHeight(16.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                            cellx5.setFixedHeight(16.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font2));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setPadding(.5f);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }

                        if (z == 6) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(6).toString()), font2));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setPadding(.5f);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }
                        z++;
                    }
                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(16.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(16.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font1));
                            cellx3.setFixedHeight(16.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font1));

                            cellx4.setFixedHeight(16.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font1));
                            cellx5.setFixedHeight(16.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font1));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setPadding(.5f);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }

                        if (z == 6) {

                            PdfPCell cellx7 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(6).toString()), font1));
                            cellx7.setFixedHeight(16.2f);
                            cellx7.disableBorderSide(LEFT);
                            cellx7.disableBorderSide(TOP);
                            cellx7.disableBorderSide(RIGHT);
                            cellx7.setPadding(.5f);
                            cellx7.setBorderColorBottom(BaseColor.BLACK);
                            cellx7.setBorderColorTop(BaseColor.WHITE);
                            cellx7.setBorderColorLeft(BaseColor.WHITE);
                            cellx7.setBorderColorRight(BaseColor.WHITE);
                            cellx7.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx7.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx7);
                        }

                        z++;
                    }
                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
        }
        return table;

    }

    public boolean createLoanAppliedReport(String fileName) {

        boolean loanApplied = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyLoansAppliedReport(fileName);
                loanApplied = true;
            }

        } else {
            createActuallyLoansAppliedReport(fileName);
            loanApplied = true;
        }

        return loanApplied;
    }

    private void createActuallyLoansAppliedReport(String fileName) {

        try {

            Paragraph headerz = createHeading("loan_applied", "", "");
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));
            PdfPTable body = createTheLoansApplied();

            Font font1 = new Font(Font.FontFamily.COURIER, 14, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();

            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));
            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);
            ct.setIndentationLeft(50f);
            ct.setIndentationRight(200f);
            document.add(ct);

            Image image1 = Image.getInstance("strawberry3.jpg");
            image1.setAbsolutePosition(49f, 1080f);
            image1.scaleAbsolute(43f, 43f);
            document.add(image1);
            document.add(headerz);
            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(280f);
            currency.setIndentationRight(30f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public PdfPTable createTheLoansApplied() {
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        float[] columnWidths = {15f, 50f, 30f, 30f, 30f, 34f, 30f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 14, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 14, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Borrower", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Loan Amount", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Principal Amount", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Interest Amount", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Number Instalments", font2));
        PdfPCell cell7 = new PdfPCell(new Paragraph("Instalment Start Date", font2));
        PdfPCell cell8 = new PdfPCell(new Paragraph("Instalment End Date", font2));
        cell1.setBackgroundColor(BaseColor.ORANGE);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.ORANGE);
        cell3.setBackgroundColor(BaseColor.ORANGE);
        cell4.setBackgroundColor(BaseColor.ORANGE);
        cell5.setBackgroundColor(BaseColor.ORANGE);
        cell6.setBackgroundColor(BaseColor.ORANGE);
        cell7.setBackgroundColor(BaseColor.ORANGE);
        cell8.setBackgroundColor(BaseColor.ORANGE);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);
        cell7.setBorderWidth(2f);
        cell8.setBorderWidth(2f);
        cell8.setBorderColorBottom(BaseColor.BLACK);

        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);
        cell5.disableBorderSide(LEFT);
        cell6.disableBorderSide(LEFT);
        cell7.disableBorderSide(LEFT);
        cell8.disableBorderSide(LEFT);
        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);
        cell7.disableBorderSide(TOP);
        cell8.disableBorderSide(TOP);
        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);
        cell7.disableBorderSide(RIGHT);
        cell8.disableBorderSide(RIGHT);
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        table.addCell(cell8);
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.loanApplied();
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {

                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(16.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(16.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font2));
                            cellx3.setFixedHeight(16.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));

                            cellx4.setFixedHeight(16.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                            cellx5.setFixedHeight(16.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font2));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setPadding(.5f);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }

                        if (z == 6) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(6).toString(), font2));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setPadding(.5f);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx6);
                        }
                        if (z == 7) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(7).toString(), font2));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setPadding(.5f);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx6);
                        }
                        z++;
                    }
                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(16.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(16.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font1));
                            cellx3.setFixedHeight(16.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font1));

                            cellx4.setFixedHeight(16.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font1));
                            cellx5.setFixedHeight(16.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font1));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setPadding(.5f);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }

                        if (z == 6) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(6).toString(), font1));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setPadding(.5f);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx6);
                        }
                        if (z == 6) {

                            PdfPCell cellx7 = new PdfPCell(new Paragraph(realData.get(7).toString(), font1));
                            cellx7.setFixedHeight(16.2f);
                            cellx7.disableBorderSide(LEFT);
                            cellx7.disableBorderSide(TOP);
                            cellx7.disableBorderSide(RIGHT);
                            cellx7.setPadding(.5f);
                            cellx7.setBorderColorBottom(BaseColor.BLACK);
                            cellx7.setBorderColorTop(BaseColor.WHITE);
                            cellx7.setBorderColorLeft(BaseColor.WHITE);
                            cellx7.setBorderColorRight(BaseColor.WHITE);
                            cellx7.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx7);
                        }
                        z++;
                    }
                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
        }
        return table;

    }

    public boolean createLoanApprovedReport(String fileName) {

        boolean loanApproved = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyLoanApprovedReport(fileName);
                loanApproved = true;
            }

        } else {
            createActuallyLoanApprovedReport(fileName);
            loanApproved = true;
        }

        return loanApproved;
    }

    private void createActuallyLoanApprovedReport(String fileName) {

        try {

            Paragraph headerz = createHeading("loan_approved", "", "");
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));
            PdfPTable body = createTheLoansApproved();

            Font font1 = new Font(Font.FontFamily.COURIER, 14, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();

            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));
            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);
            ct.setIndentationLeft(50f);
            ct.setIndentationRight(200f);
            document.add(ct);

            Image image1 = Image.getInstance("strawberry3.jpg");
            image1.setAbsolutePosition(49f, 1080f);
            image1.scaleAbsolute(43f, 43f);
            document.add(image1);
            document.add(headerz);
            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(280f);
            currency.setIndentationRight(30f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public PdfPTable createTheLoansApproved() {
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        float[] columnWidths = {15f, 50f, 30f, 30f, 30f, 34f, 30f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 14, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 14, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Borrower", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Loan Amount", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Principal Amount", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Interest Amount", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Number Instalments", font2));
        PdfPCell cell7 = new PdfPCell(new Paragraph("Instalment Start Date", font2));
        PdfPCell cell8 = new PdfPCell(new Paragraph("Instalment End Date", font2));
        cell1.setBackgroundColor(BaseColor.ORANGE);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.ORANGE);
        cell3.setBackgroundColor(BaseColor.ORANGE);
        cell4.setBackgroundColor(BaseColor.ORANGE);
        cell5.setBackgroundColor(BaseColor.ORANGE);
        cell6.setBackgroundColor(BaseColor.ORANGE);
        cell7.setBackgroundColor(BaseColor.ORANGE);
        cell8.setBackgroundColor(BaseColor.ORANGE);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);
        cell7.setBorderWidth(2f);
        cell8.setBorderWidth(2f);
        cell8.setBorderColorBottom(BaseColor.BLACK);

        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);
        cell5.disableBorderSide(LEFT);
        cell6.disableBorderSide(LEFT);
        cell7.disableBorderSide(LEFT);
        cell8.disableBorderSide(LEFT);
        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);
        cell7.disableBorderSide(TOP);
        cell8.disableBorderSide(TOP);
        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);
        cell7.disableBorderSide(RIGHT);
        cell8.disableBorderSide(RIGHT);
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        table.addCell(cell8);
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.loanApproved();
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {

                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(16.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(16.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font2));
                            cellx3.setFixedHeight(16.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));

                            cellx4.setFixedHeight(16.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                            cellx5.setFixedHeight(16.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font2));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setPadding(.5f);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }

                        if (z == 6) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(6).toString(), font2));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setPadding(.5f);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx6);
                        }
                        if (z == 7) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(7).toString(), font2));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setPadding(.5f);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx6);
                        }
                        z++;
                    }
                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(16.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(16.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font1));
                            cellx3.setFixedHeight(16.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font1));

                            cellx4.setFixedHeight(16.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font1));
                            cellx5.setFixedHeight(16.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font1));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setPadding(.5f);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }

                        if (z == 6) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(6).toString(), font1));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setPadding(.5f);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx6);
                        }
                        if (z == 6) {

                            PdfPCell cellx7 = new PdfPCell(new Paragraph(realData.get(7).toString(), font1));
                            cellx7.setFixedHeight(16.2f);
                            cellx7.disableBorderSide(LEFT);
                            cellx7.disableBorderSide(TOP);
                            cellx7.disableBorderSide(RIGHT);
                            cellx7.setPadding(.5f);
                            cellx7.setBorderColorBottom(BaseColor.BLACK);
                            cellx7.setBorderColorTop(BaseColor.WHITE);
                            cellx7.setBorderColorLeft(BaseColor.WHITE);
                            cellx7.setBorderColorRight(BaseColor.WHITE);
                            cellx7.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx7);
                        }
                        z++;
                    }
                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
        }
        return table;

    }

    public boolean createLoansDisbursedReport(String fileName) {

        boolean loanDisbursed = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyLoanDisbursedReport(fileName);
                loanDisbursed = true;
            }

        } else {
            createActuallyLoanDisbursedReport(fileName);
            loanDisbursed = true;
        }

        return loanDisbursed;
    }

    private void createActuallyLoanDisbursedReport(String fileName) {

        try {

            Paragraph headerz = createHeading("loan_disbursed", "", "");
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));
            PdfPTable body = createTheLoansDisbursed();

            Font font1 = new Font(Font.FontFamily.COURIER, 14, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();

            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));
            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);
            ct.setIndentationLeft(50f);
            ct.setIndentationRight(200f);
            document.add(ct);

            Image image1 = Image.getInstance("strawberry3.jpg");
            image1.setAbsolutePosition(49f, 1080f);
            image1.scaleAbsolute(43f, 43f);
            document.add(image1);
            document.add(headerz);
            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(280f);
            currency.setIndentationRight(30f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public PdfPTable createTheLoansDisbursed() {
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        float[] columnWidths = {15f, 50f, 30f, 30f, 30f, 34f, 30f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 14, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 14, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Borrower", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Loan Amount", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Principal Amount", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Interest Amount", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Number Instalments", font2));
        PdfPCell cell7 = new PdfPCell(new Paragraph("Instalment Start Date", font2));
        PdfPCell cell8 = new PdfPCell(new Paragraph("Instalment End Date", font2));
        cell1.setBackgroundColor(BaseColor.ORANGE);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.ORANGE);
        cell3.setBackgroundColor(BaseColor.ORANGE);
        cell4.setBackgroundColor(BaseColor.ORANGE);
        cell5.setBackgroundColor(BaseColor.ORANGE);
        cell6.setBackgroundColor(BaseColor.ORANGE);
        cell7.setBackgroundColor(BaseColor.ORANGE);
        cell8.setBackgroundColor(BaseColor.ORANGE);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);
        cell7.setBorderWidth(2f);
        cell8.setBorderWidth(2f);
        cell8.setBorderColorBottom(BaseColor.BLACK);

        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);
        cell5.disableBorderSide(LEFT);
        cell6.disableBorderSide(LEFT);
        cell7.disableBorderSide(LEFT);
        cell8.disableBorderSide(LEFT);
        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);
        cell7.disableBorderSide(TOP);
        cell8.disableBorderSide(TOP);
        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);
        cell7.disableBorderSide(RIGHT);
        cell8.disableBorderSide(RIGHT);
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        table.addCell(cell8);
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.loanDisbursed();
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {

                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(16.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(16.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font2));
                            cellx3.setFixedHeight(16.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));

                            cellx4.setFixedHeight(16.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                            cellx5.setFixedHeight(16.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font2));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setPadding(.5f);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }

                        if (z == 6) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(6).toString(), font2));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setPadding(.5f);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx6);
                        }
                        if (z == 7) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(7).toString(), font2));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setPadding(.5f);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx6);
                        }
                        z++;
                    }
                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(16.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(16.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font1));
                            cellx3.setFixedHeight(16.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font1));

                            cellx4.setFixedHeight(16.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font1));
                            cellx5.setFixedHeight(16.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font1));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setPadding(.5f);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }

                        if (z == 6) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(6).toString(), font1));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setPadding(.5f);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx6);
                        }
                        if (z == 6) {

                            PdfPCell cellx7 = new PdfPCell(new Paragraph(realData.get(7).toString(), font1));
                            cellx7.setFixedHeight(16.2f);
                            cellx7.disableBorderSide(LEFT);
                            cellx7.disableBorderSide(TOP);
                            cellx7.disableBorderSide(RIGHT);
                            cellx7.setPadding(.5f);
                            cellx7.setBorderColorBottom(BaseColor.BLACK);
                            cellx7.setBorderColorTop(BaseColor.WHITE);
                            cellx7.setBorderColorLeft(BaseColor.WHITE);
                            cellx7.setBorderColorRight(BaseColor.WHITE);
                            cellx7.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx7);
                        }
                        z++;
                    }
                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
        }
        return table;

    }

    public boolean createLoansDueReport(String fileName) {
        boolean LoansDue = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyLoansDueReport(fileName);
                LoansDue = true;
            }

        } else {
            createActuallyLoansDueReport(fileName);
            LoansDue = true;
        }

        return LoansDue;
    }

    private void createActuallyLoansDueReport(String fileName) {
        try {

            Paragraph headerz = createHeading("loans_due", "", "");
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3.rotate(), 45f, 36f, 54f, 72f);
            document.addAuthor("Bazirake Augustine Googo");
            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));
            PdfPTable body = createTheloansDueReport();

            Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);
            ct.setIndentationLeft(50f);
            ct.setIndentationRight(200f);
            document.add(ct);
            ct.setAlignment(Element.ALIGN_CENTER);
            Image image1 = Image.getInstance("strawberry3.jpg");
            image1.setAbsolutePosition(49f, 1080f);
            image1.scaleAbsolute(43f, 43f);
            document.add(image1);
            document.add(headerz);
            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(400f);
            currency.setIndentationRight(30f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createTheloansDueReport() {
        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(100);
        float[] columnWidths = {15f, 60f, 25f, 25f, 25f, 25f, 25f, 35f, 25f};

        Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Borrower", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Prncimpal Amount", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Interest Amount", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Instalment Amount", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Charges Accrued", font2));
        PdfPCell cell7 = new PdfPCell(new Paragraph("Instalment Due", font2));
        PdfPCell cell8 = new PdfPCell(new Paragraph("Instalment Due Date", font2));
        PdfPCell cell9 = new PdfPCell(new Paragraph("Aging", font2));

        cell1.setBackgroundColor(BaseColor.ORANGE);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.ORANGE);
        cell3.setBackgroundColor(BaseColor.ORANGE);
        cell4.setBackgroundColor(BaseColor.ORANGE);
        cell5.setBackgroundColor(BaseColor.ORANGE);
        cell6.setBackgroundColor(BaseColor.ORANGE);
        cell7.setBackgroundColor(BaseColor.ORANGE);
        cell8.setBackgroundColor(BaseColor.ORANGE);

        cell9.setBackgroundColor(BaseColor.ORANGE);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);
        cell7.setBorderWidth(2f);
        cell8.setBorderWidth(2f);
        cell9.setBorderWidth(2f);

        cell9.setBorderColorBottom(BaseColor.BLACK);

        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);
        cell5.disableBorderSide(LEFT);
        cell6.disableBorderSide(LEFT);
        cell7.disableBorderSide(LEFT);
        cell8.disableBorderSide(LEFT);
        cell9.disableBorderSide(LEFT);

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);
        cell7.disableBorderSide(TOP);
        cell8.disableBorderSide(TOP);
        cell9.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);
        cell7.disableBorderSide(RIGHT);
        cell8.disableBorderSide(RIGHT);
        cell9.disableBorderSide(RIGHT);
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        table.addCell(cell8);
        table.addCell(cell9);
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.loansDueReport();
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font2));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));

                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString().replace(",", "")), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString().replace(",", "")), font2));

                            cellx6.setFixedHeight(20.2f);
                            cellx6.setPadding(.5f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }
                        if (z == 6) {

                            PdfPCell cellx7 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(6).toString().replace(",", "")), font2));

                            cellx7.setFixedHeight(20.2f);
                            cellx7.setPadding(.5f);
                            cellx7.disableBorderSide(LEFT);
                            cellx7.disableBorderSide(TOP);
                            cellx7.disableBorderSide(RIGHT);
                            cellx7.setBorderColorBottom(BaseColor.BLACK);
                            cellx7.setBorderColorTop(BaseColor.WHITE);
                            cellx7.setBorderColorLeft(BaseColor.WHITE);
                            cellx7.setBorderColorRight(BaseColor.WHITE);
                            cellx7.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx7.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx7);
                        }
                        if (z == 7) {

                            PdfPCell cellx8 = new PdfPCell(new Paragraph(realData.get(7).toString(), font2));

                            cellx8.setFixedHeight(20.2f);
                            cellx8.setPadding(.5f);
                            cellx8.disableBorderSide(LEFT);
                            cellx8.disableBorderSide(TOP);
                            cellx8.disableBorderSide(RIGHT);
                            cellx8.setBorderColorBottom(BaseColor.BLACK);
                            cellx8.setBorderColorTop(BaseColor.WHITE);
                            cellx8.setBorderColorLeft(BaseColor.WHITE);
                            cellx8.setBorderColorRight(BaseColor.WHITE);
                            cellx8.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx8);
                        }
                        if (z == 8) {

                            PdfPCell cellx9 = new PdfPCell(new Paragraph(realData.get(8).toString(), font2));

                            cellx9.setFixedHeight(20.2f);
                            cellx9.setPadding(.5f);
                            cellx9.disableBorderSide(LEFT);
                            cellx9.disableBorderSide(TOP);
                            cellx9.disableBorderSide(RIGHT);
                            cellx9.setBorderColorBottom(BaseColor.BLACK);
                            cellx9.setBorderColorTop(BaseColor.WHITE);
                            cellx9.setBorderColorLeft(BaseColor.WHITE);
                            cellx9.setBorderColorRight(BaseColor.WHITE);
                            cellx9.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx9);
                        }

                        z++;
                    }

                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString().replace(",", "")), font1));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString().replace(",", "")), font1));

                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString().replace(",", "")), font1));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString().replace(",", "")), font1));

                            cellx6.setFixedHeight(20.2f);
                            cellx6.setPadding(.5f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }
                        if (z == 6) {

                            PdfPCell cellx7 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(6).toString().replace(",", "")), font1));

                            cellx7.setFixedHeight(20.2f);
                            cellx7.setPadding(.5f);
                            cellx7.disableBorderSide(LEFT);
                            cellx7.disableBorderSide(TOP);
                            cellx7.disableBorderSide(RIGHT);
                            cellx7.setBorderColorBottom(BaseColor.BLACK);
                            cellx7.setBorderColorTop(BaseColor.WHITE);
                            cellx7.setBorderColorLeft(BaseColor.WHITE);
                            cellx7.setBorderColorRight(BaseColor.WHITE);
                            cellx7.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx7.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx7);
                        }
                        if (z == 7) {

                            PdfPCell cellx8 = new PdfPCell(new Paragraph(realData.get(7).toString(), font1));

                            cellx8.setFixedHeight(20.2f);
                            cellx8.setPadding(.5f);
                            cellx8.disableBorderSide(LEFT);
                            cellx8.disableBorderSide(TOP);
                            cellx8.disableBorderSide(RIGHT);
                            cellx8.setBorderColorBottom(BaseColor.BLACK);
                            cellx8.setBorderColorTop(BaseColor.WHITE);
                            cellx8.setBorderColorLeft(BaseColor.WHITE);
                            cellx8.setBorderColorRight(BaseColor.WHITE);
                            cellx8.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx8);
                        }
                        if (z == 8) {

                            PdfPCell cellx9 = new PdfPCell(new Paragraph(realData.get(8).toString(), font1));

                            cellx9.setFixedHeight(20.2f);
                            cellx9.setPadding(.5f);
                            cellx9.disableBorderSide(LEFT);
                            cellx9.disableBorderSide(TOP);
                            cellx9.disableBorderSide(RIGHT);
                            cellx9.setBorderColorBottom(BaseColor.BLACK);
                            cellx9.setBorderColorTop(BaseColor.WHITE);
                            cellx9.setBorderColorLeft(BaseColor.WHITE);
                            cellx9.setBorderColorRight(BaseColor.WHITE);
                            cellx9.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx9);
                        }

                        z++;
                    }
                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
        }
        return table;

    }

    public boolean createLoansDueReport1(String fileName) {
        boolean LoansDue = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyLoansDueReport1(fileName);
                LoansDue = true;
            }

        } else {
            createActuallyLoansDueReport1(fileName);
            LoansDue = true;
        }

        return LoansDue;
    }

    private void createActuallyLoansDueReport1(String fileName) {
        try {

            Paragraph headerz = createHeading("loans_due1", "", "");
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3.rotate(), 45f, 36f, 54f, 72f);
            document.addAuthor("Bazirake Augustine Googo");
            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));
            PdfPTable body = createTheloansDueReport1();

            Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);
            ct.setIndentationLeft(50f);
            ct.setIndentationRight(200f);
            document.add(ct);
            ct.setAlignment(Element.ALIGN_CENTER);
            Image image1 = Image.getInstance("strawberry3.jpg");
            image1.setAbsolutePosition(49f, 1080f);
            image1.scaleAbsolute(43f, 43f);
            document.add(image1);
            document.add(headerz);
            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(400f);
            currency.setIndentationRight(30f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createTheloansDueReport1() {
        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(100);
        float[] columnWidths = {15f, 60f, 25f, 25f, 25f, 25f, 25f, 35f, 25f};

        Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Borrower", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Prncimpal Amount", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Interest Amount", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Instalment Amount", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Charges Accrued", font2));
        PdfPCell cell7 = new PdfPCell(new Paragraph("Instalment Due", font2));
        PdfPCell cell8 = new PdfPCell(new Paragraph("Instalment Due Date", font2));
        PdfPCell cell9 = new PdfPCell(new Paragraph("Aging", font2));

        cell1.setBackgroundColor(BaseColor.ORANGE);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.ORANGE);
        cell3.setBackgroundColor(BaseColor.ORANGE);
        cell4.setBackgroundColor(BaseColor.ORANGE);
        cell5.setBackgroundColor(BaseColor.ORANGE);
        cell6.setBackgroundColor(BaseColor.ORANGE);
        cell7.setBackgroundColor(BaseColor.ORANGE);
        cell8.setBackgroundColor(BaseColor.ORANGE);

        cell9.setBackgroundColor(BaseColor.ORANGE);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);
        cell7.setBorderWidth(2f);
        cell8.setBorderWidth(2f);
        cell9.setBorderWidth(2f);

        cell9.setBorderColorBottom(BaseColor.BLACK);

        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);
        cell5.disableBorderSide(LEFT);
        cell6.disableBorderSide(LEFT);
        cell7.disableBorderSide(LEFT);
        cell8.disableBorderSide(LEFT);
        cell9.disableBorderSide(LEFT);

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);
        cell7.disableBorderSide(TOP);
        cell8.disableBorderSide(TOP);
        cell9.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);
        cell7.disableBorderSide(RIGHT);
        cell8.disableBorderSide(RIGHT);
        cell9.disableBorderSide(RIGHT);
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        table.addCell(cell8);
        table.addCell(cell9);
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.loansDueReport1();
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font2));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));

                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString().replace(",", "")), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString().replace(",", "")), font2));

                            cellx6.setFixedHeight(20.2f);
                            cellx6.setPadding(.5f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }
                        if (z == 6) {

                            PdfPCell cellx7 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(6).toString().replace(",", "")), font2));

                            cellx7.setFixedHeight(20.2f);
                            cellx7.setPadding(.5f);
                            cellx7.disableBorderSide(LEFT);
                            cellx7.disableBorderSide(TOP);
                            cellx7.disableBorderSide(RIGHT);
                            cellx7.setBorderColorBottom(BaseColor.BLACK);
                            cellx7.setBorderColorTop(BaseColor.WHITE);
                            cellx7.setBorderColorLeft(BaseColor.WHITE);
                            cellx7.setBorderColorRight(BaseColor.WHITE);
                            cellx7.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx7.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx7);
                        }
                        if (z == 7) {

                            PdfPCell cellx8 = new PdfPCell(new Paragraph(realData.get(7).toString(), font2));

                            cellx8.setFixedHeight(20.2f);
                            cellx8.setPadding(.5f);
                            cellx8.disableBorderSide(LEFT);
                            cellx8.disableBorderSide(TOP);
                            cellx8.disableBorderSide(RIGHT);
                            cellx8.setBorderColorBottom(BaseColor.BLACK);
                            cellx8.setBorderColorTop(BaseColor.WHITE);
                            cellx8.setBorderColorLeft(BaseColor.WHITE);
                            cellx8.setBorderColorRight(BaseColor.WHITE);
                            cellx8.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx8);
                        }
                        if (z == 8) {

                            PdfPCell cellx9 = new PdfPCell(new Paragraph(realData.get(8).toString(), font2));

                            cellx9.setFixedHeight(20.2f);
                            cellx9.setPadding(.5f);
                            cellx9.disableBorderSide(LEFT);
                            cellx9.disableBorderSide(TOP);
                            cellx9.disableBorderSide(RIGHT);
                            cellx9.setBorderColorBottom(BaseColor.BLACK);
                            cellx9.setBorderColorTop(BaseColor.WHITE);
                            cellx9.setBorderColorLeft(BaseColor.WHITE);
                            cellx9.setBorderColorRight(BaseColor.WHITE);
                            cellx9.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx9);
                        }

                        z++;
                    }

                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString().replace(",", "")), font1));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString().replace(",", "")), font1));

                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString().replace(",", "")), font1));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString().replace(",", "")), font1));

                            cellx6.setFixedHeight(20.2f);
                            cellx6.setPadding(.5f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }
                        if (z == 6) {

                            PdfPCell cellx7 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(6).toString().replace(",", "")), font1));

                            cellx7.setFixedHeight(20.2f);
                            cellx7.setPadding(.5f);
                            cellx7.disableBorderSide(LEFT);
                            cellx7.disableBorderSide(TOP);
                            cellx7.disableBorderSide(RIGHT);
                            cellx7.setBorderColorBottom(BaseColor.BLACK);
                            cellx7.setBorderColorTop(BaseColor.WHITE);
                            cellx7.setBorderColorLeft(BaseColor.WHITE);
                            cellx7.setBorderColorRight(BaseColor.WHITE);
                            cellx7.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx7.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx7);
                        }
                        if (z == 7) {

                            PdfPCell cellx8 = new PdfPCell(new Paragraph(realData.get(7).toString(), font1));

                            cellx8.setFixedHeight(20.2f);
                            cellx8.setPadding(.5f);
                            cellx8.disableBorderSide(LEFT);
                            cellx8.disableBorderSide(TOP);
                            cellx8.disableBorderSide(RIGHT);
                            cellx8.setBorderColorBottom(BaseColor.BLACK);
                            cellx8.setBorderColorTop(BaseColor.WHITE);
                            cellx8.setBorderColorLeft(BaseColor.WHITE);
                            cellx8.setBorderColorRight(BaseColor.WHITE);
                            cellx8.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx8);
                        }
                        if (z == 8) {

                            PdfPCell cellx9 = new PdfPCell(new Paragraph(realData.get(8).toString(), font1));

                            cellx9.setFixedHeight(20.2f);
                            cellx9.setPadding(.5f);
                            cellx9.disableBorderSide(LEFT);
                            cellx9.disableBorderSide(TOP);
                            cellx9.disableBorderSide(RIGHT);
                            cellx9.setBorderColorBottom(BaseColor.BLACK);
                            cellx9.setBorderColorTop(BaseColor.WHITE);
                            cellx9.setBorderColorLeft(BaseColor.WHITE);
                            cellx9.setBorderColorRight(BaseColor.WHITE);
                            cellx9.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx9);
                        }

                        z++;
                    }
                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
        }
        return table;

    }

    public boolean createLoanDueWriteOffReport(String fileName) {

        boolean dueWriteOff = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyLoanDueWriteOffReport(fileName);
                dueWriteOff = true;
            }

        } else {
            createActuallyLoanDueWriteOffReport(fileName);
            dueWriteOff = true;
        }

        return dueWriteOff;
    }

    private void createActuallyLoanDueWriteOffReport(String fileName) {
        try {

            Paragraph headerz = createHeading("loan_dueWriteOff", "", "");
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));
            PdfPTable body = createTheLoanDueWriteOffReport();

            Font font1 = new Font(Font.FontFamily.COURIER, 14, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();

            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));
            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);
            ct.setIndentationLeft(50f);
            ct.setIndentationRight(200f);
            document.add(ct);

            Image image1 = Image.getInstance("strawberry3.jpg");
            image1.setAbsolutePosition(49f, 1080f);
            image1.scaleAbsolute(43f, 43f);
            document.add(image1);
            document.add(headerz);
            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(280f);
            currency.setIndentationRight(30f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public PdfPTable createTheLoanDueWriteOffReport() {
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        float[] columnWidths = {14f, 50f, 30f, 30f, 30f, 30f, 20f};

        Font font1 = new Font(Font.FontFamily.COURIER, 14, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 14, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Borrower", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Princimpal", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Interest", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Princimpal Paid", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Principal Remaining", font2));
        PdfPCell cell7 = new PdfPCell(new Paragraph("Aging", font2));

        cell1.setBackgroundColor(BaseColor.ORANGE);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.ORANGE);
        cell3.setBackgroundColor(BaseColor.ORANGE);
        cell4.setBackgroundColor(BaseColor.ORANGE);   //sets BG color to yellow.
        cell5.setBackgroundColor(BaseColor.ORANGE);
        cell6.setBackgroundColor(BaseColor.ORANGE);
        cell7.setBackgroundColor(BaseColor.ORANGE);   //sets BG color to yellow.

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);         // sets border width to 3 units
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);
        cell7.setBorderWidth(2f);         // sets border width to 3 units
        // sets border width to 3 units

        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);         // sets border width to 3 units
        cell5.disableBorderSide(LEFT);
        cell6.disableBorderSide(LEFT);
        cell7.disableBorderSide(LEFT);         // sets border width to 3 units

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);       // sets border width to 3 units
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);
        cell7.disableBorderSide(TOP);       // sets border width to 3 units

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);
        cell7.disableBorderSide(RIGHT);         // sets border width to 3 units

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.loansDueWriteOff();
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(16.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(16.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font2));
                            cellx3.setFixedHeight(16.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                            cellx4.setFixedHeight(16.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }

                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                            cellx5.setFixedHeight(16.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }

                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font2));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.setPadding(.5f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }
                        if (z == 6) {

                            PdfPCell cellx7 = new PdfPCell(new Paragraph(realData.get(6).toString(), font2));
                            cellx7.setFixedHeight(16.2f);
                            cellx7.setPadding(.5f);
                            cellx7.disableBorderSide(LEFT);
                            cellx7.disableBorderSide(TOP);
                            cellx7.disableBorderSide(RIGHT);
                            cellx7.setBorderColorBottom(BaseColor.BLACK);
                            cellx7.setBorderColorTop(BaseColor.WHITE);
                            cellx7.setBorderColorLeft(BaseColor.WHITE);
                            cellx7.setBorderColorRight(BaseColor.WHITE);
                            cellx7.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx7);
                        }

                        z++;
                    }

                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(16.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(16.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font1));
                            cellx3.setFixedHeight(16.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font1));
                            cellx4.setFixedHeight(16.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }

                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font1));
                            cellx5.setFixedHeight(16.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }

                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font1));
                            cellx6.setFixedHeight(16.2f);
                            cellx6.setPadding(.5f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }
                        if (z == 6) {

                            PdfPCell cellx7 = new PdfPCell(new Paragraph(realData.get(6).toString(), font1));
                            cellx7.setFixedHeight(16.2f);
                            cellx7.setPadding(.5f);
                            cellx7.disableBorderSide(LEFT);
                            cellx7.disableBorderSide(TOP);
                            cellx7.disableBorderSide(RIGHT);
                            cellx7.setBorderColorBottom(BaseColor.BLACK);
                            cellx7.setBorderColorTop(BaseColor.WHITE);
                            cellx7.setBorderColorLeft(BaseColor.WHITE);
                            cellx7.setBorderColorRight(BaseColor.WHITE);
                            cellx7.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx7);
                        }

                        z++;
                    }
                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));

        }
        return table;

    }

    public boolean createPeriodicSummury(String startDate, String endDate, String fileName) {

        boolean PeriodicSummury = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyPeriodicSummury(startDate, endDate, fileName);
                PeriodicSummury = true;
            }

        } else {
            createActuallyPeriodicSummury(startDate, endDate, fileName);
            PeriodicSummury = true;
        }

        return PeriodicSummury;
    }

    private void createActuallyPeriodicSummury(String startDate, String endDate, String fileName) {
        try {

            Paragraph headerz = createHeading("periodic_in", startDate, endDate);
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));
            PdfPTable body = createThePeriodicSummury(startDate, endDate);

            Font font1 = new Font(Font.FontFamily.COURIER, 14, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();

            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));
            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);
            ct.setIndentationLeft(50f);
            ct.setIndentationRight(200f);
            document.add(ct);

            Image image1 = Image.getInstance("strawberry3.jpg");
            image1.setAbsolutePosition(49f, 1080f);
            image1.scaleAbsolute(43f, 43f);
            document.add(image1);
            document.add(headerz);
            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(280f);
            currency.setIndentationRight(30f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public PdfPTable createThePeriodicSummury(String startDate, String endDate) {
        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(100);
        float[] columnWidths = {20f, 17f, 20f, 17f, 20f, 19f, 20f, 19f, 20f};

        Font font1 = new Font(Font.FontFamily.COURIER, 14, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 14, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        PdfPCell cell1 = new PdfPCell(new Paragraph("Number of Loan Borrowings", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Number of Loans Applied", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Value of Loans Applied", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Number of Loans Approved", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Value of Loans Approved", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Number of Loans Disbursed", font2));
        PdfPCell cell7 = new PdfPCell(new Paragraph("Value of Loans Disbursed", font2));
        PdfPCell cell8 = new PdfPCell(new Paragraph("Number of Loans Completed", font2));
        PdfPCell cell9 = new PdfPCell(new Paragraph("Value of Loans Completed", font2));
        cell1.setBackgroundColor(BaseColor.CYAN);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.CYAN);
        cell3.setBackgroundColor(BaseColor.CYAN);
        cell4.setBackgroundColor(BaseColor.CYAN);
        cell5.setBackgroundColor(BaseColor.CYAN);
        cell6.setBackgroundColor(BaseColor.CYAN);
        cell7.setBackgroundColor(BaseColor.CYAN);
        cell8.setBackgroundColor(BaseColor.CYAN);
        cell9.setBackgroundColor(BaseColor.CYAN);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);         // sets border width to 3 units
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);
        cell7.setBorderWidth(2f);         // sets border width to 3 units
        cell8.setBorderWidth(2f);
        cell9.setBorderWidth(2f);

        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);         // sets border width to 3 units
        cell5.disableBorderSide(LEFT);
        cell6.disableBorderSide(LEFT);
        cell7.disableBorderSide(LEFT);         // sets border width to 3 units
        cell8.disableBorderSide(LEFT);
        cell9.disableBorderSide(LEFT);

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);       // sets border width to 3 units
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);
        cell7.disableBorderSide(TOP);       // sets border width to 3 units
        cell8.disableBorderSide(TOP);
        cell9.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);
        cell7.disableBorderSide(RIGHT);         // sets border width to 3 units

        cell8.disableBorderSide(RIGHT);
        cell9.disableBorderSide(RIGHT);

        cell1.setVerticalAlignment(Element.ALIGN_CENTER);       // sets border width to 3 units
        cell2.setVerticalAlignment(Element.ALIGN_CENTER);
        cell3.setVerticalAlignment(Element.ALIGN_CENTER);
        cell4.setVerticalAlignment(Element.ALIGN_CENTER);       // sets border width to 3 units
        cell5.setVerticalAlignment(Element.ALIGN_CENTER);
        cell6.setVerticalAlignment(Element.ALIGN_CENTER);
        cell7.setVerticalAlignment(Element.ALIGN_CENTER);      // sets border width to 3 units

        cell8.setVerticalAlignment(Element.ALIGN_CENTER);
        cell9.setVerticalAlignment(Element.ALIGN_CENTER);

        //             cell1.setHorizontalAlignment(Element.ALIGN_CENTER);       // sets border width to 3 units
        //           cell2.setHorizontalAlignment(Element.ALIGN_CENTER); 
        //           cell3.setHorizontalAlignment(Element.ALIGN_CENTER); 
        //             cell4.setHorizontalAlignment(Element.ALIGN_CENTER);       // sets border width to 3 units
        //           cell5.setHorizontalAlignment(Element.ALIGN_CENTER); 
        //           cell6.setHorizontalAlignment(Element.ALIGN_CENTER); 
        //              cell7.setHorizontalAlignment(Element.ALIGN_CENTER);      // sets border width to 3 units
        //     
        //            cell8.setHorizontalAlignment(Element.ALIGN_CENTER); 
        //              cell9.setHorizontalAlignment(Element.ALIGN_CENTER); 
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        table.addCell(cell8);
        table.addCell(cell9);

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.summuryLoanP(startDate, endDate);
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;

                while (z < realData.size()) {
                    if (z == 0) {
                        PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                        cellx1.setFixedHeight(16.2f);
                        cellx1.disableBorderSide(LEFT);
                        cellx1.disableBorderSide(TOP);
                        cellx1.disableBorderSide(RIGHT);
                        cellx1.setPadding(.5f);
                        cellx1.setBorderColorBottom(BaseColor.BLACK);
                        cellx1.setBorderColorTop(BaseColor.WHITE);
                        cellx1.setBorderColorLeft(BaseColor.WHITE);
                        cellx1.setBorderColorRight(BaseColor.WHITE);
                        cellx1.setVerticalAlignment(Element.ALIGN_CENTER);
                        cellx1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cellx1);
                    }
                    if (z == 1) {

                        PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                        cellx2.disableBorderSide(LEFT);
                        cellx2.disableBorderSide(TOP);
                        cellx2.disableBorderSide(RIGHT);
                        cellx2.setBorderColorBottom(BaseColor.BLACK);
                        cellx2.setBorderColorTop(BaseColor.WHITE);
                        cellx2.setBorderColorLeft(BaseColor.WHITE);
                        cellx2.setBorderColorRight(BaseColor.WHITE);
                        cellx2.setFixedHeight(16.2f);
                        cellx2.setPadding(.5f);
                        cellx2.setVerticalAlignment(Element.ALIGN_CENTER);
                        cellx2.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cellx2);

                    }
                    if (z == 2) {

                        PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font1));
                        cellx3.setFixedHeight(16.2f);
                        cellx3.setPadding(.5f);
                        cellx3.disableBorderSide(LEFT);
                        cellx3.disableBorderSide(TOP);
                        cellx3.disableBorderSide(RIGHT);
                        cellx3.setBorderColorBottom(BaseColor.BLACK);
                        cellx3.setBorderColorTop(BaseColor.WHITE);
                        cellx3.setBorderColorLeft(BaseColor.WHITE);
                        cellx3.setBorderColorRight(BaseColor.WHITE);
                        cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx3);
                    }
                    if (z == 3) {

                        PdfPCell cellx4 = new PdfPCell(new Paragraph(realData.get(3).toString(), font1));
                        cellx4.setFixedHeight(16.2f);
                        cellx4.setPadding(.5f);
                        cellx4.disableBorderSide(LEFT);
                        cellx4.disableBorderSide(TOP);
                        cellx4.disableBorderSide(RIGHT);
                        cellx4.setBorderColorBottom(BaseColor.BLACK);
                        cellx4.setBorderColorTop(BaseColor.WHITE);
                        cellx4.setBorderColorLeft(BaseColor.WHITE);
                        cellx4.setBorderColorRight(BaseColor.WHITE);
                        cellx4.setVerticalAlignment(Element.ALIGN_CENTER);
                        cellx4.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cellx4);
                    }

                    if (z == 4) {

                        PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font1));
                        cellx5.setFixedHeight(16.2f);
                        cellx5.setPadding(.5f);
                        cellx5.disableBorderSide(LEFT);
                        cellx5.disableBorderSide(TOP);
                        cellx5.disableBorderSide(RIGHT);
                        cellx5.setBorderColorBottom(BaseColor.BLACK);
                        cellx5.setBorderColorTop(BaseColor.WHITE);
                        cellx5.setBorderColorLeft(BaseColor.WHITE);
                        cellx5.setBorderColorRight(BaseColor.WHITE);
                        cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx5);
                    }
                    if (z == 5) {

                        PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(5).toString(), font1));
                        cellx6.setFixedHeight(16.2f);
                        cellx6.setPadding(.5f);
                        cellx6.disableBorderSide(LEFT);
                        cellx6.disableBorderSide(TOP);
                        cellx6.disableBorderSide(RIGHT);
                        cellx6.setBorderColorBottom(BaseColor.BLACK);
                        cellx6.setBorderColorTop(BaseColor.WHITE);
                        cellx6.setBorderColorLeft(BaseColor.WHITE);
                        cellx6.setBorderColorRight(BaseColor.WHITE);
                        cellx6.setVerticalAlignment(Element.ALIGN_CENTER);
                        cellx6.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cellx6);
                    }
                    if (z == 6) {

                        PdfPCell cellx7 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(6).toString()), font1));
                        cellx7.setFixedHeight(16.2f);
                        cellx7.setPadding(.5f);
                        cellx7.disableBorderSide(LEFT);
                        cellx7.disableBorderSide(TOP);
                        cellx7.disableBorderSide(RIGHT);
                        cellx7.setBorderColorBottom(BaseColor.BLACK);
                        cellx7.setBorderColorTop(BaseColor.WHITE);
                        cellx7.setBorderColorLeft(BaseColor.WHITE);
                        cellx7.setBorderColorRight(BaseColor.WHITE);
                        cellx7.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx7);
                    }
                    if (z == 7) {

                        PdfPCell cellx8 = new PdfPCell(new Paragraph(realData.get(7).toString(), font1));
                        cellx8.setFixedHeight(16.2f);
                        cellx8.setPadding(.5f);
                        cellx8.disableBorderSide(LEFT);
                        cellx8.disableBorderSide(TOP);
                        cellx8.disableBorderSide(RIGHT);
                        cellx8.setBorderColorBottom(BaseColor.BLACK);
                        cellx8.setBorderColorTop(BaseColor.WHITE);
                        cellx8.setBorderColorLeft(BaseColor.WHITE);
                        cellx8.setBorderColorRight(BaseColor.WHITE);
                        cellx8.setVerticalAlignment(Element.ALIGN_CENTER);
                        cellx8.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cellx8);
                    }
                    if (z == 8) {

                        PdfPCell cellx9 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(8).toString()), font1));
                        cellx9.setFixedHeight(16.2f);
                        cellx9.setPadding(.5f);
                        cellx9.disableBorderSide(LEFT);
                        cellx9.disableBorderSide(TOP);
                        cellx9.disableBorderSide(RIGHT);
                        cellx9.setBorderColorBottom(BaseColor.BLACK);
                        cellx9.setBorderColorTop(BaseColor.WHITE);
                        cellx9.setBorderColorLeft(BaseColor.WHITE);
                        cellx9.setBorderColorRight(BaseColor.WHITE);
                        cellx9.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx9);
                    }
                    z++;
                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));

        }
        return table;

    }

    public boolean createAgingReport(String fileName) {
        boolean AgingReport = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyAgingReport(fileName);
                AgingReport = true;
            }

        } else {
            createActuallyAgingReport(fileName);
            AgingReport = true;
        }

        return AgingReport;
    }

    private void createActuallyAgingReport(String fileName) {
        try {

            Paragraph headerz = createHeading("loans_due1", "", "");
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
            document.addAuthor("Bazirake Augustine Googo");
            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));
            PdfPTable body = createTheAgingReport();

            Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);
            ct.setIndentationLeft(50f);
            ct.setIndentationRight(200f);
            document.add(ct);
            ct.setAlignment(Element.ALIGN_CENTER);
            Image image1 = Image.getInstance("strawberry3.jpg");
            image1.setAbsolutePosition(49f, 1080f);
            image1.scaleAbsolute(43f, 43f);
            document.add(image1);
            document.add(headerz);
            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(250f);
            currency.setIndentationRight(80f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createTheAgingReport() {
        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(110);
        float[] columnWidths = {10f, 45f, 30f, 30f, 30f, 30f, 30f, 30f, 30f, 20f};

        Font font1 = new Font(Font.FontFamily.COURIER, 14, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 14, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

//    dfdf
        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Borrower", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Contact Number", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Deposit Bal", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Outstanding  Principal", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Outstanding Interest", font2));
        PdfPCell cell7 = new PdfPCell(new Paragraph("Outstanding Accum Interest", font2));
        PdfPCell cell8 = new PdfPCell(new Paragraph("Outstanding Loan Penalty", font2));
        PdfPCell cell9 = new PdfPCell(new Paragraph("Outstanding Loan Amount", font2));
        PdfPCell cell10 = new PdfPCell(new Paragraph("Aging", font2));

        cell1.setBackgroundColor(BaseColor.GREEN);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.GREEN);
        cell3.setBackgroundColor(BaseColor.GREEN);
        cell4.setBackgroundColor(BaseColor.GREEN);
        cell5.setBackgroundColor(BaseColor.GREEN);
        cell6.setBackgroundColor(BaseColor.GREEN);   //sets BG color to yellow.
        cell7.setBackgroundColor(BaseColor.GREEN);
        cell8.setBackgroundColor(BaseColor.GREEN);
        cell9.setBackgroundColor(BaseColor.GREEN);
        cell10.setBackgroundColor(BaseColor.GREEN);
//    cell1.setBorderWidth (2f);         // sets border width to 3 units
//    cell2.setBorderWidth (2f);
//    cell3.setBorderWidth (2f);
//    cell4.setBorderWidth (2f);
//    cell5.setBorderWidth (2f);
//    cell6.setBorderWidth (2f);
//    cell7.setBorderWidth (2f);
//    cell8.setBorderWidth (2f);
//     cell9.setBorderWidth (2f);

//    cell1 .disableBorderSide(LEFT);         // sets border width to 3 units
//    cell2 .disableBorderSide(LEFT);
//    cell3 .disableBorderSide(LEFT);
//    cell4 .disableBorderSide(LEFT);
//    cell5 .disableBorderSide(LEFT);
//    cell6.disableBorderSide(LEFT);
//    cell7 .disableBorderSide(LEFT);
//    cell8.disableBorderSide(LEFT);
//     cell9.disableBorderSide(LEFT);
//     
//    cell1 .disableBorderSide(TOP);       // sets border width to 3 units
//    cell2.disableBorderSide(TOP); 
//    cell3.disableBorderSide(TOP); 
//    cell4.disableBorderSide(TOP); 
//    cell5.disableBorderSide(TOP);
//    cell6.disableBorderSide(TOP);
//    cell7.disableBorderSide(TOP);
//    cell8.disableBorderSide(TOP);
//      cell9.disableBorderSide(TOP);
//      
//    cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
//    cell2.disableBorderSide(RIGHT); 
//    cell3.disableBorderSide(RIGHT); 
//    cell4.disableBorderSide(RIGHT); 
//    cell5.disableBorderSide(RIGHT); 
//    cell6.disableBorderSide(RIGHT); 
//    cell7.disableBorderSide(RIGHT); 
//    cell8.disableBorderSide(RIGHT);
//    cell9.disableBorderSide(RIGHT);
//    
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        table.addCell(cell8);
        table.addCell(cell9);
        table.addCell(cell10);
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.agingReport();
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(60.2f);
//    cellx1.disableBorderSide(LEFT);
//    cellx1.disableBorderSide(TOP);
//    cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
//    cellx1.setBorderColorTop(BaseColor.WHITE);
//    cellx1.setBorderColorLeft(BaseColor.WHITE);
//    cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
//    cellx2.disableBorderSide(LEFT);
//    cellx2.disableBorderSide(TOP);
//    cellx2.disableBorderSide(RIGHT);
//    cellx2.setBorderColorBottom(BaseColor.BLACK);
//    cellx2.setBorderColorTop(BaseColor.WHITE);
//    cellx2.setBorderColorLeft(BaseColor.WHITE);
//    cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(60.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(realData.get(2).toString(), font2));

                            cellx3.setFixedHeight(60.2f);
                            cellx3.setPadding(.5f);
//    cellx3.disableBorderSide(LEFT);
//    cellx3.disableBorderSide(TOP);
//    cellx3.disableBorderSide(RIGHT);
//    cellx3.setBorderColorBottom(BaseColor.BLACK);
//    cellx3.setBorderColorTop(BaseColor.WHITE);
//    cellx3.setBorderColorLeft(BaseColor.WHITE);
//    cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx3);
                        }

                        if (z == 3) {
                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                            cellx4.setFixedHeight(60.2f);
                            cellx4.setPadding(.5f);
//    cellx4.disableBorderSide(LEFT);
//    cellx4.disableBorderSide(TOP);
//    cellx4.disableBorderSide(RIGHT);
//    cellx4.setBorderColorBottom(BaseColor.BLACK);
//    cellx4.setBorderColorTop(BaseColor.WHITE);
//    cellx4.setBorderColorLeft(BaseColor.WHITE);
//    cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);

                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));

                            cellx5.setFixedHeight(60.2f);
                            cellx5.setPadding(.5f);
//    cellx5.disableBorderSide(LEFT);
//    cellx5.disableBorderSide(TOP);
//    cellx5.disableBorderSide(RIGHT);
//    cellx5.setBorderColorBottom(BaseColor.BLACK);
//    cellx5.setBorderColorTop(BaseColor.WHITE);
//    cellx5.setBorderColorLeft(BaseColor.WHITE);
//    cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font2));

                            cellx6.setFixedHeight(60.2f);
                            cellx6.setPadding(.5f);
//    cellx6.disableBorderSide(LEFT);
//    cellx6.disableBorderSide(TOP);
//    cellx6.disableBorderSide(RIGHT);
//    cellx6.setBorderColorBottom(BaseColor.BLACK);
//    cellx6.setBorderColorTop(BaseColor.WHITE);
//    cellx6.setBorderColorLeft(BaseColor.WHITE);
//    cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }
                        if (z == 6) {

                            PdfPCell cellx7 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(6).toString()), font2));

                            cellx7.setFixedHeight(60.2f);
                            cellx7.setPadding(.5f);
//    cellx7.disableBorderSide(LEFT);
//    cellx7.disableBorderSide(TOP);
//    cellx7.disableBorderSide(RIGHT);
//    cellx7.setBorderColorBottom(BaseColor.BLACK);
//    cellx7.setBorderColorTop(BaseColor.WHITE);
//    cellx7.setBorderColorLeft(BaseColor.WHITE);
//    cellx7.setBorderColorRight(BaseColor.WHITE);
                            cellx7.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx7.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx7);
                        }
                        if (z == 7) {

                            PdfPCell cellx8 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(7).toString()), font2));

                            cellx8.setFixedHeight(60.2f);
                            cellx8.setPadding(.5f);
//    cellx8.disableBorderSide(LEFT);
//    cellx8.disableBorderSide(TOP);
//    cellx8.disableBorderSide(RIGHT);
//    cellx8.setBorderColorBottom(BaseColor.BLACK);
//    cellx8.setBorderColorTop(BaseColor.WHITE);
//    cellx8.setBorderColorLeft(BaseColor.WHITE);
//    cellx8.setBorderColorRight(BaseColor.WHITE);
                            cellx8.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx8.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx8);
                        }
                        if (z == 8) {

                            PdfPCell cellx8 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(8).toString()), font2));

                            cellx8.setFixedHeight(60.2f);
                            cellx8.setPadding(.5f);
//    cellx8.disableBorderSide(LEFT);
//    cellx8.disableBorderSide(TOP);
//    cellx8.disableBorderSide(RIGHT);
//    cellx8.setBorderColorBottom(BaseColor.BLACK);
//    cellx8.setBorderColorTop(BaseColor.WHITE);
//    cellx8.setBorderColorLeft(BaseColor.WHITE);
//    cellx8.setBorderColorRight(BaseColor.WHITE);
                            cellx8.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx8.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx8);
                        }
                        if (z == 9) {
                            PdfPCell cellx9 = new PdfPCell(new Paragraph(realData.get(9).toString(), font2));
                            cellx9.setFixedHeight(60.2f);
                            cellx9.setPadding(.5f);
//    cellx9.disableBorderSide(LEFT);
//    cellx9.disableBorderSide(TOP);
//    cellx9.disableBorderSide(RIGHT);
//    cellx9.setBorderColorBottom(BaseColor.BLACK);
//    cellx9.setBorderColorTop(BaseColor.WHITE);
//    cellx9.setBorderColorLeft(BaseColor.WHITE);
//    cellx9.setBorderColorRight(BaseColor.WHITE);
                            cellx9.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx9);

                        }

                        z++;
                    }

                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(60.2f);
//    cellx1.disableBorderSide(LEFT);
//    cellx1.disableBorderSide(TOP);
//    cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
//    cellx1.setBorderColorBottom(BaseColor.BLACK);
//    cellx1.setBorderColorTop(BaseColor.WHITE);
//    cellx1.setBorderColorLeft(BaseColor.WHITE);
//    cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
//    cellx2.disableBorderSide(LEFT);
//    cellx2.disableBorderSide(TOP);
//    cellx2.disableBorderSide(RIGHT);
//    cellx2.setBorderColorBottom(BaseColor.BLACK);
//    cellx2.setBorderColorTop(BaseColor.WHITE);
//    cellx2.setBorderColorLeft(BaseColor.WHITE);
//    cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(60.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(realData.get(2).toString(), font2));

                            cellx3.setFixedHeight(60.2f);
                            cellx3.setPadding(.5f);
//    cellx3.disableBorderSide(LEFT);
//    cellx3.disableBorderSide(TOP);
//    cellx3.disableBorderSide(RIGHT);
//    cellx3.setBorderColorBottom(BaseColor.BLACK);
//    cellx3.setBorderColorTop(BaseColor.WHITE);
//    cellx3.setBorderColorLeft(BaseColor.WHITE);
//    cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx3);
                        }

                        if (z == 3) {
                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font1));
                            cellx4.setFixedHeight(60.2f);
                            cellx4.setPadding(.5f);
//    cellx4.disableBorderSide(LEFT);
//    cellx4.disableBorderSide(TOP);
//    cellx4.disableBorderSide(RIGHT);
//    cellx4.setBorderColorBottom(BaseColor.BLACK);
//    cellx4.setBorderColorTop(BaseColor.WHITE);
//    cellx4.setBorderColorLeft(BaseColor.WHITE);
//    cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);

                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font1));

                            cellx5.setFixedHeight(60.2f);
                            cellx5.setPadding(.5f);
//    cellx5.disableBorderSide(LEFT);
//    cellx5.disableBorderSide(TOP);
//    cellx5.disableBorderSide(RIGHT);
//    cellx5.setBorderColorBottom(BaseColor.BLACK);
//    cellx5.setBorderColorTop(BaseColor.WHITE);
//    cellx5.setBorderColorLeft(BaseColor.WHITE);
//    cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font1));

                            cellx6.setFixedHeight(60.2f);
                            cellx6.setPadding(.5f);
//    cellx6.disableBorderSide(LEFT);
//    cellx6.disableBorderSide(TOP);
//    cellx6.disableBorderSide(RIGHT);
//    cellx6.setBorderColorBottom(BaseColor.BLACK);
//    cellx6.setBorderColorTop(BaseColor.WHITE);
//    cellx6.setBorderColorLeft(BaseColor.WHITE);
//    cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }
                        if (z == 6) {

                            PdfPCell cellx7 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(6).toString()), font1));

                            cellx7.setFixedHeight(60.2f);
                            cellx7.setPadding(.5f);
//    cellx7.disableBorderSide(LEFT);
//    cellx7.disableBorderSide(TOP);
//    cellx7.disableBorderSide(RIGHT);
//    cellx7.setBorderColorBottom(BaseColor.BLACK);
//    cellx7.setBorderColorTop(BaseColor.WHITE);
//    cellx7.setBorderColorLeft(BaseColor.WHITE);
//    cellx7.setBorderColorRight(BaseColor.WHITE);
                            cellx7.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx7.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx7);
                        }
                        if (z == 7) {

                            PdfPCell cellx8 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(7).toString()), font1));

                            cellx8.setFixedHeight(60.2f);
                            cellx8.setPadding(.5f);
//    cellx8.disableBorderSide(LEFT);
//    cellx8.disableBorderSide(TOP);
//    cellx8.disableBorderSide(RIGHT);
//    cellx8.setBorderColorBottom(BaseColor.BLACK);
//    cellx8.setBorderColorTop(BaseColor.WHITE);
//    cellx8.setBorderColorLeft(BaseColor.WHITE);
//    cellx8.setBorderColorRight(BaseColor.WHITE);
                            cellx8.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx8.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx8);
                        }
                        if (z == 8) {

                            PdfPCell cellx8 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(8).toString()), font1));

                            cellx8.setFixedHeight(60.2f);
                            cellx8.setPadding(.5f);
//    cellx8.disableBorderSide(LEFT);
//    cellx8.disableBorderSide(TOP);
//    cellx8.disableBorderSide(RIGHT);
//    cellx8.setBorderColorBottom(BaseColor.BLACK);
//    cellx8.setBorderColorTop(BaseColor.WHITE);
//    cellx8.setBorderColorLeft(BaseColor.WHITE);
//    cellx8.setBorderColorRight(BaseColor.WHITE);
                            cellx8.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx8.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx8);
                        }
                        if (z == 9) {
                            PdfPCell cellx9 = new PdfPCell(new Paragraph(realData.get(9).toString(), font1));
                            cellx9.setFixedHeight(60.2f);
                            cellx9.setPadding(.5f);
//    cellx9.disableBorderSide(LEFT);
//    cellx9.disableBorderSide(TOP);
//    cellx9.disableBorderSide(RIGHT);
//    cellx9.setBorderColorBottom(BaseColor.BLACK);
//    cellx9.setBorderColorTop(BaseColor.WHITE);
//    cellx9.setBorderColorLeft(BaseColor.WHITE);
//    cellx9.setBorderColorRight(BaseColor.WHITE);
                            cellx9.setVerticalAlignment(Element.ALIGN_TOP);
//     cellx8.setHorizontalAlignment(Element.ALIGN_RIGHT);
//    cellx9.setNoWrap(false);
                            table.addCell(cellx9);

                        }

                        z++;
                    }
                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
        }
        return table;

    }

    public List<List> createExceleAgingReport() {
//   PdfPCell cell1 = new PdfPCell(new Paragraph("S/n",font2));
//    PdfPCell cell2 = new PdfPCell(new Paragraph("Borrower",font2));
//    PdfPCell cell3 = new PdfPCell(new Paragraph("Contact Number",font2));
//    PdfPCell cell4 = new PdfPCell(new Paragraph("Outstanding  Principal",font2));
//    PdfPCell cell5 = new PdfPCell(new Paragraph("Outstanding Interest",font2));
//    PdfPCell cell6 = new PdfPCell(new Paragraph("Outstanding Accum Interest",font2));
//     PdfPCell cell7 = new PdfPCell(new Paragraph("Outstanding Loan Penalty",font2));
//     PdfPCell cell8 = new PdfPCell(new Paragraph("Outstanding Loan Amount",font2));
//    PdfPCell cell9 = new PdfPCell(new Paragraph("Aging",font2));
        List<List> Total = new ArrayList();
        List Totals = new ArrayList();
        Totals.add("S/n");
        Totals.add("Borrower");
        Totals.add("Contact Number");
        Totals.add("Deposit  Bal");
        Totals.add("Outstanding  Principal");
        Totals.add("Outstanding Interest");
        Totals.add("Outstanding Accum Interest");
        Totals.add("Outstanding Loan Penalty");
        Totals.add("Outstanding Loan Amount");
        Totals.add("Aging");
        Total.add(Totals);
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.agingReport();
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }
                        if (z == 4) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                        }
                        if (z == 5) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                        }
                        if (z == 6) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(6).toString()));

                        }
                        if (z == 7) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(7).toString()));

                        }
                        if (z == 8) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(8).toString()));

                        }
                        if (z == 9) {
                            data1.add(realData.get(9).toString());

                        }

                        z++;
                    }
                    Total.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 2) {
                            data2.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }
                        if (z == 4) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                        }
                        if (z == 5) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                        }
                        if (z == 6) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(6).toString()));

                        }
                        if (z == 7) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(7).toString()));

                        }
                        if (z == 8) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(8).toString()));

                        }
                        if (z == 9) {
                            data2.add(realData.get(9).toString());

                        }

                        z++;
                    }
                    Total.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            Total.add(data3);
        }
        return Total;

    }

    public ListDataModel aginLoans() {
//         ss

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("Borrower");
        tableHeaders.add("Contact Number");
        tableHeaders.add("Deposit/Savings");
        tableHeaders.add("Principal Outstanding");
        tableHeaders.add("Interest Outstanding");
        tableHeaders.add("Accum Interest Outstanding");
        tableHeaders.add("Loan Penalty Outstanding");
        tableHeaders.add("Loan Amount Outstanding");
        tableHeaders.add("Aging");

        List< List<Object>> dataBody = new ArrayList();
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.agingReport();
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }
                        if (z == 4) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                        }
                        if (z == 5) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                        }
                        if (z == 6) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(6).toString()));

                        }
                        if (z == 7) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(7).toString()));

                        }
                        if (z == 8) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(8).toString()));

                        }
                        if (z == 9) {
                            data1.add(realData.get(9).toString());

                        }

                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 2) {
                            data2.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }
                        if (z == 4) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                        }
                        if (z == 5) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                        }
                        if (z == 6) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(6).toString()));

                        }
                        if (z == 7) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(7).toString()));

                        }
                        if (z == 8) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(8).toString()));

                        }
                        if (z == 9) {
                            data2.add(realData.get(9).toString());

                        }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            dataBody.add(data3);
        }
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

    public loanSatementModel loanStatement(String loanId) {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("TxnDate");
//      tableHeaders.add("BatchCode");
        tableHeaders.add("MnthPaid");
        tableHeaders.add("YrPaid");
        tableHeaders.add("AmntDIS");
        tableHeaders.add("InterestEXP");
        tableHeaders.add("TotalAmntEXP");
        tableHeaders.add("Rate");
        tableHeaders.add("AmntPaid");
        tableHeaders.add("PrincPaid");

        tableHeaders.add("InterPaid");
        tableHeaders.add("AccumIntertPaid");
        tableHeaders.add("PenaltyPaid");
//    tableHeaders.add("PrincipalBal");
//    
//    tableHeaders.add("InterBal");
//    tableHeaders.add("AccumInterBal");
//    tableHeaders.add("PenaltyBal");
        tableHeaders.add("TotalLoanBal");

        List< List> dataBody = new ArrayList();

        int n = 0;

        Map<Integer, List<Object>> dataBase = rdb.loanStatmentReport(loanId);

        if (!dataBase.isEmpty()) {

            while (n < dataBase.size()) {

                List realData = dataBase.get(n);

                dataBody.add(realData);

                n++;
            }

        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            dataBody.add(data3);
        }
        loanSatementModel model1v = new loanSatementModel(dataBody, tableHeaders);

        return model1v;

    }

    public ReportsModelStatusReport LoanStatusReport(Component c) {

        ReportsModelStatusReport statusModel = null;

//        if (fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "CompanyType.txt")).equalsIgnoreCase("Money Lender")) {
        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
//       tableHeaders.add("Loan Id");
        tableHeaders.add("Borrower");
        tableHeaders.add("Princ");
        tableHeaders.add("Princ Paid");
        tableHeaders.add("Princ O/S");

        tableHeaders.add("Inter");
        tableHeaders.add("Inter Paid");
        tableHeaders.add("Inter O/S");

        tableHeaders.add("AccumInterest");
        tableHeaders.add("AccumInterest Paid");
        tableHeaders.add("AccumInterest O/S");

        tableHeaders.add("Penalty");
        tableHeaders.add("Penalty O/S");
        tableHeaders.add("Penalty Paid");
//    tableHeaders.add("Total");
//    tableHeaders.add("Total O/S");
//    tableHeaders.add("Total Paid");
//            tableHeaders.add("DisbursDate");
//            tableHeaders.add("NextDue");
        List< List<Object>> dataBody = new ArrayList();

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.loanStatusReport("accumm", c);
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                List data2 = new ArrayList();
                while (z < realData.size()) {
                    if (z == 0) {

                        data2.add(realData.get(0).toString());

                    }
                    if (z == 1) {
                        data2.add(realData.get(1).toString());
                    }
                    if (z == 2) {
                        data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));

                    }

                    if (z == 3) {
                        data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                    }
                    if (z == 4) {
                        data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                    }
                    if (z == 5) {
                        data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                    }
                    if (z == 6) {
                        data2.add(fmt.formatForStatementNumbers(realData.get(6).toString()));

                    }
                    if (z == 7) {
                        data2.add(fmt.formatForStatementNumbers(realData.get(7).toString()));

                    }
//         if(z==8){
//data2.add(fmt.formatForStatementNumbers(realData.get(8).toString())); 
//  
//    }
//            if(z==9){
//data2.add(fmt.formatForStatementNumbers(realData.get(9).toString())); 
//  
//    }
//               if(z==10){
//data2.add(fmt.formatForStatementNumbers(realData.get(10).toString())); 
//  
//    }
                    if (z == 8) {
                        data2.add(fmt.formatForStatementNumbers(realData.get(8).toString()));

                    }
                    if (z == 9) {
                        data2.add(fmt.formatForStatementNumbers(realData.get(9).toString()));

                    }
                    if (z == 10) {
                        data2.add(fmt.formatForStatementNumbers(realData.get(10).toString()));

                    }
                    if (z == 11) {
                        data2.add(realData.get(11).toString());

                    }
                    if (z == 12) {
                        data2.add(realData.get(12).toString());

                    }

                    if (z == 13) {
                        data2.add(realData.get(13).toString());

                    }
//                                    if(z==14){
//data2.add(fmt.formatForStatementNumbers(realData.get(14).toString())); 
//  
//    }
//              
// 
//      
//    if(z==15){
//        data2.add(realData.get(15).toString()); 
//  
//
//    }
//
//  if(z==16){
//        data2.add(realData.get(16).toString()); 
//  
//
//    }
                    z++;
                }
                dataBody.add(data2);

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("0.0");
            data3.add("0.0");
            data3.add("0.0");
            data3.add("0.0");
            data3.add("0.0");
            data3.add("0.0");
            data3.add("0.0");
            data3.add("0.0");
            data3.add("0.0");
            data3.add("0.0");
            data3.add("0.0");
            data3.add("0.0");
            data3.add("0.0");
            data3.add("0.0");
            dataBody.add(data3);
        }

        statusModel = new ReportsModelStatusReport(dataBody, tableHeaders);

//        } else {
//
//            List<String> tableHeaders = new ArrayList();
//            tableHeaders.add("S/n");
////       tableHeaders.add("Loan Id");
//            tableHeaders.add("Borrower");
//            tableHeaders.add("Princ");
//            tableHeaders.add("Princ Paid");
//            tableHeaders.add("Princ O/S");
//
//            tableHeaders.add("Interest");
//            tableHeaders.add("Inter Paid");
//            tableHeaders.add("Inter O/S");
//
////    tableHeaders.add("AccumulatedInterest");
////    tableHeaders.add("AccumulatedInterest O/S");
////    tableHeaders.add("AccumulatedInterest Paid");
//            tableHeaders.add("Penalty");
//            tableHeaders.add("Penalty O/S");
//            tableHeaders.add("Penalty Paid");
////    tableHeaders.add("Total");
////    tableHeaders.add("Total O/S");
////    tableHeaders.add("Total Paid");
//            tableHeaders.add("DisbDate");
//            tableHeaders.add("NxtDue");
//            List< List<Object>> dataBody = new ArrayList();
//
//            int n = 0;
//            Map<Integer, List<Object>> dataBase = rdb.loanStatusReport("penal", c);
//            if (!dataBase.isEmpty()) {
//                while (n < dataBase.size()) {
//                    List realData = dataBase.get(n);
//                    int z = 0;
//                    List data2 = new ArrayList();
//                    while (z < realData.size()) {
//                        if (z == 0) {
//
//                            data2.add(realData.get(0).toString());
//
//                        }
//                        if (z == 1) {
//                            data2.add(realData.get(1).toString());
//                        }
//                        if (z == 2) {
//                            data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));
//
//                        }
//
//                        if (z == 3) {
//                            data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
//                        }
//                        if (z == 4) {
//                            data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
//
//                        }
//                        if (z == 5) {
//                            data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));
//
//                        }
//                        if (z == 6) {
//                            data2.add(fmt.formatForStatementNumbers(realData.get(6).toString()));
//
//                        }
//                        if (z == 7) {
//                            data2.add(fmt.formatForStatementNumbers(realData.get(7).toString()));
//
//                        }
////         if(z==8){
////data2.add(fmt.formatForStatementNumbers(realData.get(8).toString())); 
////  
////    }
////            if(z==9){
////data2.add(fmt.formatForStatementNumbers(realData.get(9).toString())); 
////  
////    }
////               if(z==10){
////data2.add(fmt.formatForStatementNumbers(realData.get(10).toString())); 
////  
////    }
////                  if(z==8){
////data2.add(fmt.formatForStatementNumbers(realData.get(8).toString())); 
////  
////    }
////                     if(z==9){
////data2.add(fmt.formatForStatementNumbers(realData.get(9).toString())); 
////  
////    }
////                        if(z==10){
////data2.add(fmt.formatForStatementNumbers(realData.get(10).toString())); 
////  
////    }
//                        if (z == 8) {
//                            data2.add(fmt.formatForStatementNumbers(realData.get(8).toString()));
//
//                        }
//                        if (z == 9) {
//                            data2.add(fmt.formatForStatementNumbers(realData.get(9).toString()));
//
//                        }
//                        if (z == 10) {
//                            data2.add(fmt.formatForStatementNumbers(realData.get(10).toString()));
//
//                        }
//                        if (z == 11) {
//                            data2.add(realData.get(11).toString());
//
//                        }
//
//                        if (z == 12) {
//                            data2.add(realData.get(12).toString());
//
//                        }
//
////  if(z==13){
////        data2.add(realData.get(13).toString()); 
////  
////
////    }
//                        z++;
//                    }
//                    dataBody.add(data2);
//
//                    n++;
//                }
//            } else {
//                List data3 = new ArrayList();
//                data3.add("0.0");
//                data3.add("0.0");
//                data3.add("0.0");
//                data3.add("0.0");
//                data3.add("0.0");
//                data3.add("0.0");
//                data3.add("0.0");
//                data3.add("0.0");
//                data3.add("0.0");
//                data3.add("0.0");
//                data3.add("0.0");
//                data3.add("0.0");
//                data3.add("0.0");
//                data3.add("0.0");
//                dataBody.add(data3);
//            }
//
//            statusModel = new ReportsModelStatusReport(dataBody, tableHeaders);
//
//        }
        return statusModel;

    }

    public AssetRegisterModel assetRegisterReport(Component c) {

        AssetRegisterModel statusModel = null;

        List<String> tableHeaders = new ArrayList();

        tableHeaders.add("S/n");
        tableHeaders.add("AssetName");
        tableHeaders.add("AssetCategory");
        tableHeaders.add("PurchaseDate");
        tableHeaders.add("NextDepriDate");

        tableHeaders.add("UsefulLife(Months)");
        tableHeaders.add("DepriMethod");
        tableHeaders.add("PurchasePrice");

        tableHeaders.add("AccumDepri");
        tableHeaders.add("NBV");
        tableHeaders.add("DepriStatus");

        List< List> dataBody = new ArrayList();

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.depreciationStore(c);

        if (!dataBase.isEmpty()) {

            while (n < dataBase.size()) {

                List realData = dataBase.get(n);
                int z = 0;
                List data2 = new ArrayList();
                while (z < realData.size()) {
                    if (z == 0) {

                        data2.add(realData.get(0).toString());

                    }
                    if (z == 1) {
                        data2.add(realData.get(1).toString());
                    }
                    if (z == 2) {
                        data2.add(realData.get(2).toString());

                    }

                    if (z == 3) {
                        data2.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(realData.get(3).toString()));
                    }
                    if (z == 4) {
                        data2.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(realData.get(4).toString()));

                    }
                    if (z == 5) {
                        data2.add(realData.get(5).toString());

                    }
                    if (z == 6) {
                        data2.add(realData.get(6).toString());

                    }
                    if (z == 7) {
                        data2.add(fmt.formatForStatementNumbers(realData.get(7).toString()));

                    }

                    if (z == 8) {
                        data2.add(fmt.formatForStatementNumbers(realData.get(8).toString()));

                    }
                    if (z == 9) {
                        data2.add(fmt.formatForStatementNumbers(realData.get(9).toString()));

                    }
                    if (z == 10) {
                        data2.add(realData.get(10).toString());

                    }

                    z++;
                }
                dataBody.add(data2);

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");

            dataBody.add(data3);
        }

        statusModel = new AssetRegisterModel(dataBody, tableHeaders);

        return statusModel;

    }

    public ListDataModel createAllOutstandingLaonPaymentsSpecialxc(String batchNumber) {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("Item Date");
        tableHeaders.add("Account Name");
        tableHeaders.add("Account Number");
        tableHeaders.add("Principal");
        tableHeaders.add("Interest");
        tableHeaders.add("Accum Interest");
        tableHeaders.add("Penalty");
        tableHeaders.add("Loan Amount");
        tableHeaders.add("Due Date");
        List< List<Object>> dataBody = new ArrayList();
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.createAllOutstandingLaonPaymentsSpecialX(parseInt(batchNumber));
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data1.add(realData.get(3).toString());
                        }
                        if (z == 4) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(4).toString().replace(",", "").trim()));

                        }
                        if (z == 5) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(5).toString().replace(",", "").trim()));

                        }
                        if (z == 6) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(6).toString().replace(",", "").trim()));

                        }
                        if (z == 7) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(7).toString().replace(",", "").trim()));

                        }
                        if (z == 8) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(8).toString().replace(",", "").trim()));

                        }
                        if (z == 9) {
                            data1.add(realData.get(9).toString());

                        }

                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 2) {
                            data2.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data2.add(realData.get(3).toString());
                        }
                        if (z == 4) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(4).toString().replace(",", "").trim()));

                        }
                        if (z == 5) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(5).toString().replace(",", "").trim()));

                        }
                        if (z == 6) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(6).toString().replace(",", "").trim()));

                        }
                        if (z == 7) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(7).toString().replace(",", "").trim()));

                        }

                        if (z == 8) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(8).toString().replace(",", "").trim()));

                        }
                        if (z == 9) {
                            data2.add(realData.get(9).toString());

                        }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            dataBody.add(data3);
        }
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

    public ListDataModel_1 amortScheduleWaive(String accountNumber) {
//            ddddd
        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("InstalmentNo.");
        tableHeaders.add("Interest");
        tableHeaders.add("Penalty");
        tableHeaders.add("Accum Interest");
        tableHeaders.add("Due Date");
        tableHeaders.add("Check");
        List< List<Object>> dataBody = new ArrayList();
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.amortScheduleWaiveDb(accountNumber);
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data1.add(realData.get(3).toString());
                        }
                        if (z == 4) {
                            data1.add(realData.get(4).toString());

                        }
                        if (z == 5) {
                            data1.add(realData.get(5));

                        }
                        if (z == 6) {
                            data1.add(realData.get(6));

                        }

                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 2) {
                            data2.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data2.add(realData.get(3).toString());
                        }
                        if (z == 4) {
                            data2.add(realData.get(4).toString());

                        }
                        if (z == 5) {
                            data2.add(realData.get(5));

                        }
                        if (z == 6) {
                            data2.add(realData.get(6));

                        }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add(false);
            dataBody.add(data3);
        }
        model1b = new ListDataModel_1(dataBody, tableHeaders);

        return model1b;

    }

    public ListDataModelCreateInt amortScheduleCreateInterest(String accountNumber) {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("InstalmentNo.");
        tableHeaders.add("Interest");
        tableHeaders.add("Due Date");
        tableHeaders.add("Check");
        List< List<Object>> dataBody = new ArrayList();
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.createInterest(accountNumber);
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(realData.get(2).toString());

                        }

//    if(z==3){
//          data1.add(realData.get(3).toString());
//    }
//    if(z==4){
// data1.add(realData.get(4).toString());
//   
//    }
                        if (z == 3) {
                            data1.add(realData.get(3));

                        }
                        if (z == 4) {
                            data1.add(realData.get(4));

                        }

                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 2) {
                            data2.add(realData.get(2).toString());

                        }
//
//    if(z==3){
//      data2.add(realData.get(3).toString());   
//       }
//    if(z==4){
//data2.add(realData.get(4).toString()); 
//  
//    }
                        if (z == 3) {
                            data2.add(realData.get(3));

                        }
                        if (z == 4) {
                            data2.add(realData.get(4));

                        }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
//     data3.add("N/A");
            data3.add("N/A");
            data3.add(false);
            dataBody.add(data3);
        }
        model1bc = new ListDataModelCreateInt(dataBody, tableHeaders);

        return model1bc;

    }

    public ListDataModel createBatchPostingLoansSpecial(String batchNumber) {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("Borrower");
        tableHeaders.add("Account Number");
        tableHeaders.add("Interest");
        tableHeaders.add("Accum Interest");
        tableHeaders.add("Penalty");
        tableHeaders.add("Principal");
        tableHeaders.add("Loan Amount");
        tableHeaders.add("Due Date");
        List< List<Object>> dataBody = new ArrayList();
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.createBatchPostingLoansSpecialX(parseInt(batchNumber));
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }
                        if (z == 4) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                        }
                        if (z == 5) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                        }
                        if (z == 6) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(6).toString()));

                        }
                        if (z == 7) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(7).toString()));

                        }
                        if (z == 8) {
                            data1.add(realData.get(8).toString());

                        }

                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 2) {

                            data2.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }
                        if (z == 4) {

                            data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                        }
                        if (z == 5) {

                            data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                        }
                        if (z == 6) {

                            data2.add(fmt.formatForStatementNumbers(realData.get(6).toString()));

                        }
                        if (z == 7) {

                            data2.add(fmt.formatForStatementNumbers(realData.get(7).toString()));

                        }

                        if (z == 8) {

                            data2.add(realData.get(8).toString());

                        }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            dataBody.add(data3);
        }
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

    public ListDataModel createAllOutstandingSavingsPaymentsSpecialxc(String batchNumber) {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("Item Date");
        tableHeaders.add("Account Name");
        tableHeaders.add("Account Number");
        tableHeaders.add("Savings");
        tableHeaders.add("Total Amount");
        List< List<Object>> dataBody = new ArrayList();
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.createAllOutstandingSavingsPaymentsSpecialX(parseInt(batchNumber));
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data1.add(realData.get(3).toString());
                        }
                        if (z == 4) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(4).toString().replace(",", "").trim()));

                        }
                        if (z == 5) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(5).toString().replace(",", "").trim()));

                        }

                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 2) {
                            data2.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data2.add(realData.get(3).toString());
                        }
                        if (z == 4) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(4).toString().replace(",", "").trim()));

                        }
                        if (z == 5) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(5).toString().replace(",", "").trim()));

                        }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            dataBody.add(data3);
        }
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

    public ListDataModel createAllOutstandingSharesPaymentsSpecialxc(String batchNumber) {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("Item Date");
        tableHeaders.add("Account Name");
        tableHeaders.add("Account Number");
        tableHeaders.add("Shares");
        tableHeaders.add("Total Amount");
        List< List<Object>> dataBody = new ArrayList();
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.createAllOutstandingSharesPaymentsSpecialX(parseInt(batchNumber));
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data1.add(realData.get(3).toString());
                        }
                        if (z == 4) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(4).toString().replace(",", "").trim()));

                        }
                        if (z == 5) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(5).toString().replace(",", "").trim()));

                        }

                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 2) {
                            data2.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data2.add(realData.get(3).toString());
                        }
                        if (z == 4) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(4).toString().replace(",", "").trim()));

                        }
                        if (z == 5) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(5).toString().replace(",", "").trim()));

                        }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            dataBody.add(data3);
        }
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

    public ListDataModel createAllOutstandingLaonPayments() {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("Borrower");
        tableHeaders.add("Account Number");
        tableHeaders.add("Interest");
        tableHeaders.add("Accum Interest");
        tableHeaders.add("Penalty");
        tableHeaders.add("Principal");
        tableHeaders.add("Loan Amount");
        tableHeaders.add("Due Date");
        List< List<Object>> dataBody = new ArrayList();
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.createAllOutstandingLaonPayments();
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }
                        if (z == 4) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                        }
                        if (z == 5) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                        }
                        if (z == 6) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(6).toString()));

                        }
                        if (z == 7) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(7).toString()));

                        }
                        if (z == 8) {
                            data1.add(realData.get(8).toString());

                        }

                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 2) {
                            data2.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }
                        if (z == 4) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                        }
                        if (z == 5) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                        }
                        if (z == 6) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(6).toString()));

                        }
                        if (z == 7) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(7).toString()));

                        }

                        if (z == 8) {
                            data2.add(realData.get(8).toString());

                        }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            dataBody.add(data3);
        }
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

    public ListDataModel createAllOutstandingSavingsPayments() {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("Borrower");
        tableHeaders.add("Account Number");
        tableHeaders.add("Savings");
        tableHeaders.add("Total Amount");
        List< List<Object>> dataBody = new ArrayList();
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.createAllOutstandingSaviePPayments();
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }
                        if (z == 4) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                        }
//    createAllOutstandingSharesPayments

                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 2) {
                            data2.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }
                        if (z == 4) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                        }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            dataBody.add(data3);
        }
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

    public ListDataModel createAllOutstandingSharesPayments() {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("Borrower");
        tableHeaders.add("Account Number");
        tableHeaders.add("Shares");
        tableHeaders.add("Total Amount");
        List< List<Object>> dataBody = new ArrayList();
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.createAllOutstandingaSharePPayments();
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }
                        if (z == 4) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                        }
//    createAllOutstandingSharesPayments

                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 2) {
                            data2.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }
                        if (z == 4) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                        }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            dataBody.add(data3);
        }
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

    public ListDataModel createAllOutstandingLaonPaymentsSummury() {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("Batch Date");
        tableHeaders.add("Batch Id");
        tableHeaders.add("Batch Type");
        tableHeaders.add("No. Entries");
        tableHeaders.add("Total Amount");
        tableHeaders.add("Batch Status");
        List< List<Object>> dataBody = new ArrayList();

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.createAllOutstandingLaonPaymentsDum();

        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data1.add(realData.get(3).toString());
                        }
                        if (z == 4) {
                            data1.add(realData.get(4).toString());

                        }
                        if (z == 5) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(5).toString().replace(",", "")));

                        }
                        if (z == 6) {
                            data1.add(realData.get(6).toString());

                        }
//       if(z==7){
// data1.add(fmt.formatForStatementNumbers(realData.get(7).toString()));
//   
//    }
//    if(z==8){ 
//        data1.add(realData.get(8).toString());
//    
//    }

                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 2) {
                            data2.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data2.add(realData.get(3).toString());
                        }
                        if (z == 4) {
                            data2.add(realData.get(4).toString());

                        }
                        if (z == 5) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(5).toString().replace(",", "")));

                        }
                        if (z == 6) {
                            data2.add(realData.get(6).toString());

                        }
//       if(z==7){
//data2.add(fmt.formatForStatementNumbers(realData.get(7).toString())); 
//  
//    }
//      
//    if(z==8){
//        data2.add(realData.get(8).toString()); 
//  
//
//    }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
//    data3.add("N/A");
//     data3.add("N/A");
            dataBody.add(data3);
        }
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

    public ListDataModel createAllOutstandingLaonPaymentsSummurySub() {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("Batch Date");
        tableHeaders.add("Batch Id");
        tableHeaders.add("Batch Type");
        tableHeaders.add("No. Entries");
        tableHeaders.add("Total Amount");
        tableHeaders.add("Batch Status");
        List< List<Object>> dataBody = new ArrayList();

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.createAllOutstandingLaonPaymentsDumSub();

        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data1.add(realData.get(3).toString());
                        }
                        if (z == 4) {
                            data1.add(realData.get(4).toString());

                        }
                        if (z == 5) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(5).toString().replace(",", "")));

                        }
                        if (z == 6) {
                            data1.add(realData.get(6).toString());

                        }
//       if(z==7){
// data1.add(fmt.formatForStatementNumbers(realData.get(7).toString()));
//   
//    }
//    if(z==8){ 
//        data1.add(realData.get(8).toString());
//    
//    }

                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 2) {
                            data2.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data2.add(realData.get(3).toString());
                        }
                        if (z == 4) {
                            data2.add(realData.get(4).toString());

                        }
                        if (z == 5) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(5).toString().replace(",", "")));

                        }
                        if (z == 6) {
                            data2.add(realData.get(6).toString());

                        }
//       if(z==7){
//data2.add(fmt.formatForStatementNumbers(realData.get(7).toString())); 
//  
//    }
//      
//    if(z==8){
//        data2.add(realData.get(8).toString()); 
//  
//
//    }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
//    data3.add("N/A");
//     data3.add("N/A");
            dataBody.add(data3);
        }
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

        
        
        
        public ListDataModel statementOfLoanStatus(List dates,Component c) {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("Borrower Name");
        tableHeaders.add("Borrower Account");
        tableHeaders.add("Opening PrincipalBal");
        tableHeaders.add("Principal Disbursed");
        tableHeaders.add("Principal Paid");
        tableHeaders.add("Closing PrincipalBal");
        tableHeaders.add("Opening InterestBal");
        tableHeaders.add("Interest Expected");
        tableHeaders.add("Interest Paid");
        tableHeaders.add("Closing InterestBal");

        List< List<Object>> dataBody = new ArrayList();
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.statementOfLoanS(fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(0).toString()), fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(1).toString()),c);
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(realData.get(2).toString());

                        }
                        if (z == 3) {
                            data1.add(realData.get(3).toString());

                        }
                        if (z == 4) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                        }
                        if (z == 5) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                        }
                        if (z == 6) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(6).toString()));

                        }
                        if (z == 7) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(7).toString()));

                        }
                        if (z == 8) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(8).toString()));

                        }
                        if (z == 9) {
                            data1.add(realData.get(9).toString());

                        }

                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 2) {
                            data2.add(realData.get(2).toString());

                        }
                        if (z == 3) {
                            data2.add(realData.get(3).toString());

                        }
                        if (z == 4) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                        }
                        if (z == 5) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                        }
                        if (z == 6) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(6).toString()));

                        }
                        if (z == 7) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(7).toString()));

                        }
                        if (z == 8) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(8).toString()));

                        }

                        if (z == 9) {
                            data2.add(realData.get(9).toString());

                        }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            dataBody.add(data3);
        }
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }
        
        
        
    public ListDataModel projectedPayments(List dates) {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("Borrower");
        tableHeaders.add("Account");
        tableHeaders.add("Instalment Number");
        tableHeaders.add("Principal Projected");
        tableHeaders.add("Interest Projected");
        tableHeaders.add("Accum Interest Projected");
        tableHeaders.add("Loan Penalty Projected");
        tableHeaders.add("Loan Amount Projected");
        tableHeaders.add("Due Date");

        List< List<Object>> dataBody = new ArrayList();
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.projectPay(fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(0).toString()), fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(1).toString()));
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(realData.get(2).toString());

                        }
                        if (z == 3) {
                            data1.add(realData.get(3).toString());

                        }
                        if (z == 4) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                        }
                        if (z == 5) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                        }
                        if (z == 6) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(6).toString()));

                        }
                        if (z == 7) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(7).toString()));

                        }
                        if (z == 8) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(8).toString()));

                        }
                        if (z == 9) {
                            data1.add(realData.get(9).toString());

                        }

                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 2) {
                            data2.add(realData.get(2).toString());

                        }
                        if (z == 3) {
                            data2.add(realData.get(3).toString());

                        }
                        if (z == 4) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                        }
                        if (z == 5) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                        }
                        if (z == 6) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(6).toString()));

                        }
                        if (z == 7) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(7).toString()));

                        }
                        if (z == 8) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(8).toString()));

                        }

                        if (z == 9) {
                            data2.add(realData.get(9).toString());

                        }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            dataBody.add(data3);
        }
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

    public ListDataModel loanListings() {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("Borrower");
        tableHeaders.add("Account");
        tableHeaders.add("Instalment Number");
        tableHeaders.add("Principal Projected");
        tableHeaders.add("Interest Projected");
        tableHeaders.add("Accum Interest Projected");
        tableHeaders.add("Loan Penalty Projected");
        tableHeaders.add("Loan Amount Projected");
        tableHeaders.add("Due Date");

        List< List<Object>> dataBody = new ArrayList();
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.listLoansData();
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(realData.get(2).toString());

                        }
                        if (z == 3) {
                            data1.add(realData.get(3).toString());

                        }
                        if (z == 4) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                        }
                        if (z == 5) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                        }
                        if (z == 6) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(6).toString()));

                        }
                        if (z == 7) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(7).toString()));

                        }
                        if (z == 8) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(8).toString()));

                        }
                        if (z == 9) {
                            data1.add(realData.get(9).toString());

                        }

                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 2) {
                            data2.add(realData.get(2).toString());

                        }
                        if (z == 3) {
                            data2.add(realData.get(3).toString());

                        }
                        if (z == 4) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                        }
                        if (z == 5) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                        }
                        if (z == 6) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(6).toString()));

                        }
                        if (z == 7) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(7).toString()));

                        }
                        if (z == 8) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(8).toString()));

                        }

                        if (z == 9) {
                            data2.add(realData.get(9).toString());

                        }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            dataBody.add(data3);
        }
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

    public ListDataModel quickSummuryReport(String valueDate, Component c) {

        List<String> tableHeaders = new ArrayList();

        tableHeaders.add("ITEM");
        tableHeaders.add("VALUE");
        tableHeaders.add("ITEM");
        tableHeaders.add("VALUE");

        List< List<Object>> dataBody = new ArrayList();

        int n = 0;

        List realData = rdb.quickSummuryRatios(valueDate, c);

        if (!realData.isEmpty()) {

            List data1 = new ArrayList();
            data1.add("TOTAL NUMBER OF ACCOUNTS:");
            data1.add(realData.get(0).toString());
            data1.add(" TOTAL NUMBER OF CUSTOMERS:");
            data1.add(realData.get(1).toString());
            dataBody.add(data1);

            List data2 = new ArrayList();
            data2.add("TOTAL NUMBER OF ACTIVE SAVINGS CUSTOMERS:");
            data2.add(realData.get(2).toString());
            data2.add("TOTAL NUMBER SAVINGS WITHDRAWS:");
            data2.add(realData.get(3).toString());
            dataBody.add(data2);

            List data3 = new ArrayList();
            data3.add("TOTAL NUMBER OF SAVINGS MADE:");
            data3.add(realData.get(4).toString());
            data3.add("TOTAL VALUE SAVINGS WITHDRAWS:");
            data3.add(realData.get(5).toString());
            dataBody.add(data3);

            List data4 = new ArrayList();
            data4.add("TOTAL VALUE OF SAVINGS:");
            data4.add(realData.get(6).toString());
            data4.add("TOTAL VALUE OF SAVINGS MADE:");
            data4.add(realData.get(7).toString());
            dataBody.add(data4);

            List data5 = new ArrayList();
            data5.add("TOTAL NUMBER OF ACTIVE SHARES CUSTOMERS:");
            data5.add(realData.get(8).toString());
            data5.add("TOTAL NUMBER OF CAPITALISATIONS:");
            data5.add(realData.get(9).toString());
            dataBody.add(data5);

            List data6 = new ArrayList();
            data6.add("TOTAL NUMBER OF DECAPITALISATIONS:");
            data6.add(realData.get(10).toString());
            data6.add("TOTAL VALUE OF SHARES BOUGHT:");
            data6.add(realData.get(11).toString());
            dataBody.add(data6);

            List data7 = new ArrayList();
            data7.add("TOTAL VALUE OF SHARES REMOVED:");
            data7.add(realData.get(12).toString());
            data7.add("TOTAL VALUE OF SHARES:");
            data7.add(realData.get(13).toString());
            dataBody.add(data7);

            List data8 = new ArrayList();
            data8.add("TOTAL NUMBER OF SHARES :");
            data8.add(realData.get(14).toString());
            data8.add("TOTAL NUMBER OF CUSTOMERS WITH DEPOSITS:");
            data8.add(realData.get(15).toString());
            dataBody.add(data8);

            List data9 = new ArrayList();
            data9.add("TOTAL VALUE OF DEPOSITS:");
            data9.add(realData.get(16).toString());
            data9.add("TOTAL NUMBER OF ACTIVE LOANS:");
            data9.add(realData.get(17).toString());
            dataBody.add(data9);

            List data10 = new ArrayList();
            data10.add("TOTAL NUMBER OF ACTIVE LOANS CYCLE 1:");
            data10.add(realData.get(18).toString());
            data10.add("TOTAL NUMBER OF ACTIVE LOANS CYCLE 2:");
            data10.add(realData.get(19).toString());
            dataBody.add(data10);

            List data11 = new ArrayList();
            data11.add("TOTAL NUMBER OF ACTIVE LOANS CYCLE 3:");
            data11.add(realData.get(20).toString());
            data11.add("TOTAL NUMBER OF ACTIVE LOANS CYCLE 4:");
            data11.add(realData.get(21).toString());
            dataBody.add(data11);

            List data12 = new ArrayList();
            data12.add("TOTAL NUMBER OF ACTIVE LOANS CYCLE 5:");
            data12.add(realData.get(22).toString());
            data12.add(" TOTAL NUMBER OF ACTIVE LOANS CYCLE 6:");
            data12.add(realData.get(23).toString());
            dataBody.add(data12);

            List data13 = new ArrayList();
            data13.add("TOTAL NUMBER OF ACTIVE LOANS CYCLE 7:");
            data13.add(realData.get(24).toString());
            data13.add("TOTAL NUMBER OF ACTIVE LOANS CYCLE ABOVE 7:");
            data13.add(realData.get(25).toString());
            dataBody.add(data13);

            List data14 = new ArrayList();
            data14.add("TOTAL VALUE OF ACTIVE LOANS:");
            data14.add(realData.get(26).toString());
            data14.add("TOTAL VALUE OF ACTIVE LOANS CYCLE 1:");
            data14.add(realData.get(27).toString());
            dataBody.add(data14);

            List data15 = new ArrayList();
            data15.add("TOTAL VALUE OF ACTIVE LOANS CYCLE 2:");
            data15.add(realData.get(28).toString());
            data15.add("TOTAL VALUE OF ACTIVE LOANS CYCLE 3:");
            data15.add(realData.get(29).toString());
            dataBody.add(data15);

            List data16 = new ArrayList();
            data16.add("TOTAL VALUE OF ACTIVE LOANS CYCLE 4:");
            data16.add(realData.get(30).toString());
            data16.add("TOTAL VALUE OF ACTIVE LOANS CYCLE 5:");
            data16.add(realData.get(31).toString());
            dataBody.add(data16);

            List data17 = new ArrayList();
            data17.add("TOTAL VALUE OF ACTIVE LOANS CYCLE 6:");
            data17.add(realData.get(32).toString());
            data17.add("TOTAL VALUE OF ACTIVE LOANS CYCLE 7:");
            data17.add(realData.get(33).toString());
            dataBody.add(data17);

            List data18 = new ArrayList();
            data18.add("TOTAL VALUE OF ACTIVE LOANS CYCLE ABOVE 7:");
            data18.add(realData.get(34).toString());
            data18.add("TOTAL NUMBER OF ACTIVE LOAN CUSTOMERS:");
            data18.add(realData.get(35).toString());
            dataBody.add(data18);

            List data19 = new ArrayList();
            data19.add("TOTAL NUMBER OF ACTIVE LOAN CUSTOMERS CYCLE 1:");
            data19.add(realData.get(36).toString());
            data19.add("TOTAL NUMBER OF ACTIVE LOAN CUSTOMERS CYCLE 2:");
            data19.add(realData.get(37).toString());
            dataBody.add(data19);

            List data20 = new ArrayList();
            data20.add("TOTAL NUMBER OF ACTIVE LOAN CUSTOMERS CYCLE 3:");
            data20.add(realData.get(38).toString());
            data20.add("TOTAL NUMBER OF ACTIVE LOAN CUSTOMERS CYCLE 4:");
            data20.add(fmt.formatForStatementNumbers(realData.get(39).toString()));
            dataBody.add(data20);

            List data21 = new ArrayList();
            data21.add("TOTAL NUMBER OF ACTIVE LOAN CUSTOMERS CYCLE 5:");
            data21.add(realData.get(40).toString());
            data21.add("TOTAL NUMBER OF ACTIVE LOAN CUSTOMERS CYCLE 6:");
            data21.add(realData.get(41).toString());
            dataBody.add(data21);

            List data22 = new ArrayList();
            data22.add("TOTAL NUMBER OF ACTIVE LOAN CUSTOMERS CYCLE 7:");
            data22.add(realData.get(42).toString());
            data22.add("TOTAL NUMBER OF ACTIVE LOAN CUSTOMERS CYCLE ABOVE CYCLE 7:");
            data22.add(realData.get(43).toString());
            dataBody.add(data22);

            List data23 = new ArrayList();
            data23.add("TOTAL VALUE OF ACTIVE LOAN CUSTOMERS:");
            data23.add(realData.get(44).toString());
            data23.add("TOTAL VALUE OF ACTIVE LOAN CUSTOMERS CYCLE 1:");
            data23.add(realData.get(45).toString());
            dataBody.add(data23);

            List data24 = new ArrayList();
            data24.add("TOTAL VALUE OF ACTIVE LOAN CUSTOMERS CYCLE 2:");
            data24.add(realData.get(46).toString());
            data24.add("TOTAL VALUE OF ACTIVE LOAN CUSTOMERS CYCLE 3:");
            data24.add(realData.get(47).toString());
            dataBody.add(data24);

            List data25 = new ArrayList();
            data25.add("TOTAL VALUE OF ACTIVE LOAN CUSTOMERS CYCLE 4:");
            data25.add(realData.get(48).toString());
            data25.add("TOTAL VALUE OF ACTIVE LOAN CUSTOMERS CYCLE5:");
            data25.add(realData.get(49).toString());
            dataBody.add(data25);

            List data26 = new ArrayList();
            data26.add("TOTAL VALUE OF ACTIVE LOAN CUSTOMERS CYCLE 6:");
            data26.add(realData.get(50).toString());
            data26.add("TOTAL VALUE OF ACTIVE LOAN CUSTOMERS CYCLE 7:");
            data26.add(realData.get(51).toString());
            dataBody.add(data26);

            List data27 = new ArrayList();
            data27.add("TOTAL VALUE OF ACTIVE LOAN CUSTOMERS CYCLE ABOVE CYCLE 7:");
            data27.add(realData.get(52).toString());
            data27.add("TOTAL NUMBER OF LOANS DISBURSED:");
            data27.add(realData.get(53).toString());
            dataBody.add(data27);

            List data28 = new ArrayList();
            data28.add("TOTAL NUMBER OF LOANS DISBURSED CYCLE 1:");
            data28.add(realData.get(54).toString());
            data28.add("TOTAL NUMBER OF LOANS DISBURSED CYCLE 2:");
            data28.add(realData.get(55).toString());
            dataBody.add(data28);

            List data29 = new ArrayList();
            data29.add("TOTAL NUMBER OF LOANS DISBURSED CYCLE3:");
            data29.add(realData.get(56).toString());
            data29.add("TOTAL NUMBER OF LOANS DISBURSED CYCLE 4:");
            data29.add(realData.get(57).toString());
            dataBody.add(data29);

            List data30 = new ArrayList();
            data30.add("TOTAL NUMBER OF LOANS DISBURSED CYCLE 5:");
            data30.add(realData.get(58).toString());
            data30.add("TOTAL NUMBER OF LOANS DISBURSED CYCLE 6:");
            data30.add(realData.get(59).toString());
            dataBody.add(data30);

            List data31 = new ArrayList();
            data31.add("TOTAL NUMBER OF LOANS DISBURSED CYCLE 7:");
            data31.add(realData.get(60).toString());
            data31.add("TOTAL NUMBER OF LOANS DISBURSED CYCLE ABOVE 7:");
            data31.add(realData.get(61).toString());
            dataBody.add(data31);

            List data32 = new ArrayList();
            data32.add("TOTAL VALUE OF LOANS DISBURSED:");
            data32.add(realData.get(62).toString());
            data32.add("TOTAL VALUE OF LOANS DISBURSED CYCLE 1:");
            data32.add(realData.get(63).toString());
            dataBody.add(data32);

            List data33 = new ArrayList();
            data33.add("TOTAL VALUE OF LOANS DISBURSED CYCLE 2:");
            data33.add(realData.get(64).toString());
            data33.add("TOTAL VALUE OF LOANS DISBURSED CYCLE 3:");
            data33.add(realData.get(65).toString());
            dataBody.add(data33);

            List data34 = new ArrayList();
            data34.add("TOTAL VALUE OF LOANS DISBURSED CYCLE 4:");
            data34.add(realData.get(66).toString());
            data34.add("TOTAL VALUE OF LOANS DISBURSED CYCLE 5:");
            data34.add(realData.get(67).toString());
            dataBody.add(data34);

            List data35 = new ArrayList();
            data35.add("TOTAL VALUE OF LOANS DISBURSED CYCLE 6:");
            data35.add(realData.get(68).toString());
            data35.add("TOTAL VALUE OF LOANS DISBURSED CYCLE 7:");
            data35.add(realData.get(69).toString());
            dataBody.add(data35);

            List data36 = new ArrayList();
            data36.add("TOTAL VALUE OF LOANS DISBURSED CYCLE ABOVE 7:");
            data36.add(realData.get(70).toString());
            data36.add("TOTAL NUMBER OF GROUP LOANS DISBURSED:");
            data36.add(realData.get(71).toString());
            dataBody.add(data36);

            List data37 = new ArrayList();
            data37.add("TOTAL NUMBER OF GROUP LOANS  DISBURSED CYCLE 1:");
            data37.add(realData.get(72).toString());
            data37.add("TOTAL NUMBER OF GROUP LOANS DISBURSED CYCLE 2:");
            data37.add(realData.get(73).toString());
            dataBody.add(data37);

            List data38 = new ArrayList();
            data38.add("TOTAL NUMBER OF GROUP LOANS DISBURSED CYCLE 3:");
            data38.add(realData.get(74).toString());
            data38.add("TOTAL NUMBER OF GROUP LOANS DISBURSED CYCLE 4:");
            data38.add(realData.get(75).toString());
            dataBody.add(data38);

            List data39 = new ArrayList();
            data39.add("TOTAL NUMBER OF GROUP LOANS DISBURSED CYCLE 5:");
            data39.add(realData.get(76).toString());
            data39.add("TOTAL NUMBER OF GROUP LOANS DISBURSED CYCLE 6:");
            data39.add(realData.get(77).toString());
            dataBody.add(data39);

            List data40 = new ArrayList();
            data40.add("TOTAL NUMBER OF GROUP LOANS DISBURSED CYCLE 7:");
            data40.add(realData.get(78).toString());
            data40.add("TOTAL NUMBER OF GROUP LOANS DISBURSED CYCLE ABOVE 7:");
            data40.add(realData.get(79).toString());
            dataBody.add(data40);

            List data41 = new ArrayList();
            data41.add("TOTAL VALUE OF GROUP LOANS DISBURSED:");
            data41.add(realData.get(80).toString());
            data41.add("TOTAL VALUE OF GROUP LOANS DISBURSED CYCLE 1:");
            data41.add(realData.get(81).toString());
            dataBody.add(data41);

            List data42 = new ArrayList();
            data42.add("TOTAL VALUE OF GROUP LOANS DISBURSED CYCLE 2:");
            data42.add(realData.get(82).toString());
            data42.add("TOTAL VALUE OF GROUP LOANS DISBURSED CYCLE 3:");
            data42.add(realData.get(83).toString());
            dataBody.add(data42);

            List data43 = new ArrayList();
            data43.add("TOTAL VALUE OF GROUP LOANS DISBURSED CYCLE 4:");
            data43.add(realData.get(84).toString());
            data43.add("TOTAL VALUE OF GROUP LOANS DISBURSED CYCLE 5:");
            data43.add(realData.get(85).toString());
            dataBody.add(data43);

            List data44 = new ArrayList();
            data44.add("TOTAL VALUE OF GROUP LOANS DISBURSED CYCLE 6:");
            data44.add(realData.get(86).toString());
            data44.add("TOTAL VALUE OF GROUP LOANS DISBURSED CYCLE 7:");
            data44.add(realData.get(87).toString());
            dataBody.add(data44);

            List data45 = new ArrayList();
            data45.add("TOTAL VALUE OF GROUP LOANS DISBURSED CYCLE ABOVE 7:");
            data45.add(realData.get(88).toString());
            data45.add("TOTAL NUMBER OF INDIVIDUAL LOANS DISBURSED:");
            data45.add(realData.get(89).toString());
            dataBody.add(data45);

            List data46 = new ArrayList();
            data46.add("TOTAL NUMBER OF INDIVIDUAL LOANS DISBURSED CYCLE 1:");
            data46.add(realData.get(90).toString());
            data46.add("TOTAL NUMBER OF INDIVIDUAL LOANS DISBURSED CYCLE 2:");
            data46.add(realData.get(91).toString());
            dataBody.add(data46);

            List data47 = new ArrayList();
            data47.add("TOTAL NUMBER OF INDIVIDUAL LOANS DISBURSED CYCLE 3:");
            data47.add(realData.get(92).toString());
            data47.add("TOTAL NUMBER OF INDIVIDUAL LOANS DISBURSED CYCLE 4:");
            data47.add(realData.get(93).toString());
            dataBody.add(data47);

            List data48 = new ArrayList();
            data48.add("TOTAL NUMBER OF INDIVIDUAL LOANS DISBURSED CYCLE 5:");
            data48.add(realData.get(94).toString());
            data48.add("TOTAL NUMBER OF INDIVIDUAL LOANS DISBURSED CYCLE 6 :");
            data48.add(realData.get(95).toString());
            dataBody.add(data48);

            List data49 = new ArrayList();
            data49.add("TOTAL NUMBER OF INDIVIDUAL LOANS DISBURSED CYCLE 7:");
            data49.add(realData.get(96).toString());
            data49.add("TOTAL NUMBER OF INDIVIDUAL LOANS DISBURSED CYCLE ABOVE 7:");
            data49.add(realData.get(97).toString());
            dataBody.add(data49);

            List data50 = new ArrayList();
            data50.add("TOTAL VALUE OF INDIVIDUAL LOANS DISBURSED:");
            data50.add(realData.get(98).toString());
            data50.add("TOTAL VALUE OF INDIVIDUAL LOANS DISBURSED CYCLE 1:");
            data50.add(realData.get(99).toString());
            dataBody.add(data50);

            List data51 = new ArrayList();
            data51.add("TOTAL VALUE OF INDIVIDUAL LOANS DISBURSED CYCLE 2:");
            data51.add(realData.get(100).toString());
            data51.add("TOTAL VALUE OF INDIVIDUAL LOANS DISBURSED CYCLE 3:");
            data51.add(realData.get(101).toString());
            dataBody.add(data51);

            List data52 = new ArrayList();
            data52.add("TOTAL VALUE OF INDIVIDUAL LOANS DISBURSED CYCLE 4:");
            data52.add(realData.get(102).toString());
            data52.add("TOTAL VALUE OF INDIVIDUAL LOANS DISBURSED CYCLE 5:");
            data52.add(realData.get(103).toString());
            dataBody.add(data52);

            List data53 = new ArrayList();
            data53.add("TOTAL VALUE OF INDIVIDUAL LOANS DISBURSED CYCLE 6:");
            data53.add(realData.get(104).toString());
            data53.add("TOTAL VALUE OF INDIVIDUAL LOANS DISBURSED CYCLE 7:");
            data53.add(realData.get(105).toString());
            dataBody.add(data53);

            List data54 = new ArrayList();
            data54.add("TOTAL VALUE OF INDIVIDUAL LOANS DISBURSED CYCLE ABOVE 7:");
            data54.add(realData.get(106).toString());
            data54.add("TOTAL NUMBER OF LOANS COMPLETED:");
            data54.add(realData.get(107).toString());
            dataBody.add(data54);

            List data55 = new ArrayList();
            data55.add("TOTAL VALUE OF LOANS COMPLETED:");
            data55.add(realData.get(108).toString());
            data55.add("TOTAL NUMBER OF LOANS WRITTEN OFF:");
            data55.add(realData.get(109).toString());
            dataBody.add(data55);

            List data56 = new ArrayList();
            data56.add("TOTAL VALUE OF LOANS WRITTEN OFF:");
            data56.add(realData.get(110).toString());
            data56.add("TOTAL NUMBER OF ALL PRINCIPAL LOAN REPAYMENTS:");
            data56.add(realData.get(111).toString());
            dataBody.add(data56);

            List data57 = new ArrayList();
            data57.add("TOTAL VALUE OF ALL PRINCIPAL LOAN REPAYMENTS:");
            data57.add(realData.get(112).toString());
            data57.add("TOTAL NUMBER OF PRINCIPAL LOAN REPAYMENTS DUE LOANS ONLY:");
            data57.add(realData.get(113).toString());
            dataBody.add(data57);

            List data58 = new ArrayList();
            data58.add("TOTAL VALUE OF PRINCIPAL LOAN REPAYMENTS DUE LOANS ONLY:");
            data58.add(realData.get(114).toString());
            data58.add("TOTAL NUMBER OF EARLY PRINCIPAL LOAN REPAYMENTS:");
            data58.add(realData.get(115).toString());
            dataBody.add(data58);

            List data59 = new ArrayList();
            data59.add("TOTAL VALUE OF EARLY PRINCIPAL LOAN REPAYMENTS:");
            data59.add(realData.get(116).toString());
            data59.add("TOTAL NUMBER OF ARREARS PRINCIPAL LOAN REPAYMENTS:");
            data59.add(realData.get(117).toString());
            dataBody.add(data59);

            List data60 = new ArrayList();
            data60.add("TOTAL VALUE OF ARREARS PRINCIPAL LOAN REPAYMENTS:");
            data60.add(realData.get(118).toString());
            data60.add("TOTAL NUMBER OF LOAN REPAYMENTS MINUS ARREARS:");
            data60.add(realData.get(119).toString());
            dataBody.add(data60);

            List data61 = new ArrayList();
            data61.add("TOTAL VALUE OF LOAN REPAYMENTS MINUS ARREARS:");
            data61.add(realData.get(120).toString());
            data61.add("TOTAL NUMBER OF ALL INTEREST PAYMENTS:");
            data61.add(realData.get(121).toString());
            dataBody.add(data61);

            List data62 = new ArrayList();
            data62.add("TOTAL VALUE OF INTEREST RECEIVED:");
            data62.add(realData.get(122).toString());
            data62.add("TOTAL NUMBER OF INTEREST PAYMENTS DUE LOANS ONLY:");
            data62.add(realData.get(123).toString());
            dataBody.add(data62);

            List data63 = new ArrayList();
            data63.add("TOTAL VALUE OF INTEREST PAYMENTS DUE LOANS ONLY:");
            data63.add(realData.get(124).toString());
            data63.add("TOTAL NUMBER OF EARLY INTEREST PAYMENTS:");
            data63.add(realData.get(125).toString());
            dataBody.add(data63);

            List data64 = new ArrayList();
            data64.add("TOTAL VALUE OF EARLY INTEREST PAYMENTS:");
            data64.add(realData.get(126).toString());
            data64.add("TOTAL NUMBER OF ARREARS INTEREST PAYMENTS:");
            data64.add(realData.get(127).toString());
            dataBody.add(data64);

            List data65 = new ArrayList();
            data65.add("TOTAL VALUE OF ARREARS INTEREST PAYMENTS:");
            data65.add(realData.get(128).toString());
            data65.add("TOTAL NUMBER OF ALL ACCUMULATED INTEREST PAYMENTS:");
            data65.add(realData.get(129).toString());
            dataBody.add(data65);

            List data66 = new ArrayList();
            data66.add("TOTAL VALUE OF ALL ACCUMULATED INTEREST PAYMENTS:");
            data66.add(realData.get(130).toString());
            data66.add("TOTAL NUMBER OF ALL LOAN PENALTY PAYMENTS:");
            data66.add(realData.get(131).toString());
            dataBody.add(data66);

            List data67 = new ArrayList();
            data67.add("TOTAL VALUE OF ALL LOAN PENALTY PAYMENTS:");
            data67.add(realData.get(132).toString());
            data67.add("TOTAL NUMBER OF ALL INTEREST AND PRINCIPAL LOAN REPAYMENTS:");
            data67.add(realData.get(133).toString());
            dataBody.add(data67);

            List data68 = new ArrayList();
            data68.add("TOTAL VALUE OF ALL INTEREST AND PRINCIPAL LOAN REPAYMENTS:");
            data68.add(realData.get(134).toString());
            data68.add("TOTAL VALUE OF PRINCIPAL  OUTSTANDING ARREARS:");
            data68.add(realData.get(135).toString());
            dataBody.add(data68);

            List data69 = new ArrayList();
            data69.add("TOTAL VALUE OF INTEREST OUTSTANDING ARREARS:");
            data69.add(realData.get(136).toString());
            data69.add("TOTAL NUMBER OF LOANS IN ARREARS:");
            data69.add(realData.get(137).toString());
            dataBody.add(data69);

            List data70 = new ArrayList();
            data70.add("TOTAL VALUE OF PRINCIPAL LOANS IN ARREARS:");
            data70.add(realData.get(138).toString());
            data70.add("TOTAL VALUE OF INTEREST INARREARS:");
            data70.add(realData.get(139).toString());
            dataBody.add(data70);

            List data71 = new ArrayList();
            data71.add("TOTAL VALUE OF LOAN BOOK:");
            data71.add(realData.get(140).toString());
            data71.add("TOTAL VALUE OF CASH BALANCES:");
            data71.add(realData.get(141).toString());
            dataBody.add(data71);

            List data72 = new ArrayList();
            data72.add("TOTAL VALUE OF BANK BALANCES:");
            data72.add(realData.get(142).toString());
            data72.add("TOTAL VALUE OF ASSETS:");
            data72.add(realData.get(143).toString());
            dataBody.add(data72);

            List data73 = new ArrayList();
            data73.add("TOTAL VALUE OF RECEIVABLES:");
            data73.add(realData.get(144).toString());
            data73.add("TOTAL VALUE OF PAYABLES:");
            data73.add(realData.get(145).toString());
            dataBody.add(data73);

            List data74 = new ArrayList();
            data74.add("TOTAL VALUE OF FIXED ASSETS:");
            data74.add(realData.get(146).toString());
            data74.add("TOTAL VALUE OF CURRENT ASSETS INCLUDING INTEREST RECEIVABLE:");
            data74.add(realData.get(147).toString());
            dataBody.add(data74);

            List data75 = new ArrayList();
            data75.add("TOTAL VALUE OF CURRENT ASSETS MINUS INTEREST RECEIVABLE:");
            data75.add(realData.get(148).toString());
            data75.add("TOTAL VALUE OF MAIN INCOME:");
            data75.add(realData.get(149).toString());
            dataBody.add(data75);

            List data76 = new ArrayList();
            data76.add("TOTAL VALUE OF OTHER INCOME:");
            data76.add(realData.get(150).toString());
            data76.add("TOTAL VALUE OF INCOME:");
            data76.add(realData.get(151).toString());
            dataBody.add(data76);

            List data77 = new ArrayList();
            data77.add("TOTAL VALUE OF EXPENSES:");
            data77.add(realData.get(152).toString());
            data77.add("TOTAL VALUE OF LIABILITIES:");
            data77.add(realData.get(153).toString());
            dataBody.add(data77);

            List data78 = new ArrayList();
            data78.add("TOTAL VALUE OF EQUITY:");
            data78.add(realData.get(154).toString());
            data78.add("OTHER DATA:");
            data78.add("COMING SOON");
            dataBody.add(data78);

        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            dataBody.add(data3);
        }

        model1 = new ListDataModel(dataBody, tableHeaders);
        return model1;
    }

    public ListDataModel returnOnInvestment(List dates) {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("Date");
        tableHeaders.add("Month");
        tableHeaders.add("Shares");
        tableHeaders.add("Return");
        tableHeaders.add("Running Balance");

        List< List<Object>> dataBody = new ArrayList();
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.returnOI(dates.get(0).toString(), fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(1).toString()), fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(2).toString()));
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }
                        if (z == 4) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                        }
                        if (z == 5) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                        }

                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 2) {
                            data2.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }
                        if (z == 4) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                        }
                        if (z == 5) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                        }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");

            dataBody.add(data3);
        }
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

    public ListDataModel returnOnInvestmentShares(List dates, Component c) {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("TxnYear");
        tableHeaders.add("AccountName");
        tableHeaders.add("AccountNum");
        tableHeaders.add("LedgerBalance");
        tableHeaders.add("InterestComputed");

        List< List<Object>> dataBody = new ArrayList();
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.sharesIndivROIGrouped(fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(0).toString()), fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(1).toString()), c);

        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;

                List data2 = new ArrayList();
                while (z < realData.size()) {
                    if (z == 0) {
                        data2.add(realData.get(0).toString());

                    }
                    if (z == 1) {
                        data2.add(realData.get(1).toString());
                    }
                    if (z == 2) {
                        data2.add(realData.get(2).toString());

                    }

                    if (z == 3) {
                        data2.add(realData.get(3).toString());
                    }
                    if (z == 4) {
                        data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                    }
                    if (z == 5) {
                        data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                    }

                    z++;

                }
                dataBody.add(data2);
//                    JOptionPane.showMessageDialog(c, data2.size());
                n++;
            }

        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");

            dataBody.add(data3);
        }

//        JOptionPane.showMessageDialog(c, dataBody.size());
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

    public ListDataModel returnOnInvestmentSavings(List dates, Component c, JTextField f) {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("TxnYear");
        tableHeaders.add("AccountName");
        tableHeaders.add("AccountNum");
        tableHeaders.add("LedgerBalance");
        tableHeaders.add("InterestComputed");

        List< List<Object>> dataBody = new ArrayList();
        int n = 0;
//        JOptionPane.showConfirmDialog(c, "No");
        Map<Integer, List<Object>> dataBase = rdb.savingsIndivROIGrouped(fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(0).toString()), fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(1).toString()), c, f);

        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;

                List data2 = new ArrayList();
                while (z < realData.size()) {
                    if (z == 0) {
                        data2.add(realData.get(0).toString());

                    }
                    if (z == 1) {
                        data2.add(realData.get(1).toString());
                    }
                    if (z == 2) {
                        data2.add(realData.get(2).toString());

                    }

                    if (z == 3) {
                        data2.add(realData.get(3).toString());
                    }
                    if (z == 4) {
                        data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                    }
                    if (z == 5) {
                        data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                    }

                    z++;

                }
                dataBody.add(data2);
//                    JOptionPane.showMessageDialog(c, data2.size());
                n++;
            }

        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");

            dataBody.add(data3);
        }

        JOptionPane.showMessageDialog(c, dataBody.size());

        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

    public ListDataModel generalReturnOnInvestment(List dates) {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("Date");
        tableHeaders.add("Name");
        tableHeaders.add("Shares");
        tableHeaders.add("Return");
        tableHeaders.add("Running Balance");

        List< List<Object>> dataBody = new ArrayList();
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.returnOIGroup(fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(0).toString()), fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(1).toString()));
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }
                        if (z == 4) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                        }
                        if (z == 5) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                        }

                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 2) {
                            data2.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }
                        if (z == 4) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                        }
                        if (z == 5) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                        }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");

            dataBody.add(data3);
        }
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

    public ListDataModel customersByBalances(List dates) {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("Ledger Name");
        tableHeaders.add("Ledger Id");
        tableHeaders.add("Running Balance");

        List< List<Object>> dataBody = new ArrayList();
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.customersByBalances(fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(0).toString()));
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }

                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 2) {
                            data2.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");

            dataBody.add(data3);
        }
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

    public synchronized budgetEstimatesModel budgetEstimates(List budgetPeriod, Component c) {

        List<String> tableHeaders = rdb.getTheActualMonthsHeaders();

        List< List<Object>> dataBody = new ArrayList();

        switch (budgetPeriod.get(2).toString()) {

            case "Entire Period":

                int n = 0,
                 n1 = 0;
                int t = 1;
//   JOptionPane.showMessageDialog(c, "Ndiyo");

                while (t <= 2) {

                    if (t == 1) {

                        Map<Integer, List<Object>> dataBase = rdb.budgetEstimates(budgetPeriod, 100, c);

//        JOptionPane.showMessageDialog(c, dataBase.size());
                        if (!dataBase.isEmpty()) {

                            while (n < dataBase.size()) {

                                List realData = dataBase.get(n);
                                int z = 0;

                                List data2 = new ArrayList();

//        JOptionPane.showMessageDialog(c, rdb.getnumberOfBudgetPeriods1());
                                switch (rdb.getnumberOfBudgetPeriods1()) {

                                    case 1:
                                        while (z < realData.size()) {
                                            if (z == 0) {
                                                data2.add(realData.get(0).toString());

                                            }
                                            if (z == 1) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(1).toString()));
                                            }

                                            if (z == 2) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));

                                            }
                                            z++;
                                        }

                                        break;
                                    case 2:
                                        while (z < realData.size()) {
                                            if (z == 0) {
                                                data2.add(realData.get(0).toString());

                                            }
                                            if (z == 1) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(1).toString()));
                                            }
                                            if (z == 2) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));
                                            }
                                            if (z == 3) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));

                                            }
                                            z++;
                                        }
                                        break;
                                    case 3:
                                        while (z < realData.size()) {
                                            if (z == 0) {
                                                data2.add(realData.get(0).toString());

                                            }
                                            if (z == 1) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(1).toString()));
                                            }
                                            if (z == 2) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));
                                            }
                                            if (z == 3) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                                            }
                                            if (z == 4) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                                            }
                                            z++;
                                        }

                                        break;
                                    case 4:
                                        while (z < realData.size()) {
                                            if (z == 0) {
                                                data2.add(realData.get(0).toString());

                                            }
                                            if (z == 1) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(1).toString()));
                                            }
                                            if (z == 2) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));
                                            }
                                            if (z == 3) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                                            }

                                            if (z == 4) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                                            }
                                            if (z == 5) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                                            }

                                            z++;
                                        }

                                        break;
                                    case 5:
                                        while (z < realData.size()) {
                                            if (z == 0) {
                                                data2.add(realData.get(0).toString());

                                            }
                                            if (z == 1) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(1).toString()));
                                            }
                                            if (z == 2) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));
                                            }
                                            if (z == 3) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                                            }

                                            if (z == 4) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                                            }

                                            if (z == 5) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));
                                            }
                                            if (z == 6) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(6).toString()));

                                            }
                                            z++;
                                        }

                                        break;
                                    case 6:
                                        while (z < realData.size()) {
                                            if (z == 0) {
                                                data2.add(realData.get(0).toString());

                                            }
                                            if (z == 1) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(1).toString()));
                                            }
                                            if (z == 2) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));
                                            }
                                            if (z == 3) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                                            }

                                            if (z == 4) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                                            }

                                            if (z == 5) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));
                                            }

                                            if (z == 6) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(6).toString()));
                                            }
                                            if (z == 7) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(7).toString()));

                                            }

                                            z++;
                                        }

                                        break;
                                    case 7:
                                        while (z < realData.size()) {
                                            if (z == 0) {
                                                data2.add(realData.get(0).toString());

                                            }
                                            if (z == 1) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(1).toString()));
                                            }
                                            if (z == 2) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));
                                            }
                                            if (z == 3) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                                            }

                                            if (z == 4) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                                            }

                                            if (z == 5) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));
                                            }

                                            if (z == 6) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(6).toString()));
                                            }
                                            if (z == 7) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(7).toString()));
                                            }
                                            if (z == 8) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(8).toString()));

                                            }
                                            z++;
                                        }

                                        break;
                                    case 8:
                                        while (z < realData.size()) {
                                            if (z == 0) {
                                                data2.add(realData.get(0).toString());

                                            }
                                            if (z == 1) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(1).toString()));
                                            }
                                            if (z == 2) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));
                                            }
                                            if (z == 3) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                                            }

                                            if (z == 4) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                                            }

                                            if (z == 5) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));
                                            }

                                            if (z == 6) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(6).toString()));
                                            }
                                            if (z == 7) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(7).toString()));
                                            }
                                            if (z == 8) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(8).toString()));
                                            }
                                            if (z == 9) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(9).toString()));

                                            }

                                            z++;
                                        }

                                        break;
                                    case 9:
                                        while (z < realData.size()) {
                                            if (z == 0) {
                                                data2.add(realData.get(0).toString());

                                            }
                                            if (z == 1) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(1).toString()));
                                            }
                                            if (z == 2) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));
                                            }
                                            if (z == 3) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                                            }

                                            if (z == 4) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                                            }

                                            if (z == 5) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));
                                            }

                                            if (z == 6) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(6).toString()));
                                            }
                                            if (z == 7) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(7).toString()));
                                            }
                                            if (z == 8) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(8).toString()));
                                            }

                                            if (z == 9) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(9).toString()));
                                            }
                                            if (z == 10) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(10).toString()));

                                            }

                                            z++;
                                        }

                                        break;
                                    case 10:
                                        while (z < realData.size()) {
                                            if (z == 0) {
                                                data2.add(realData.get(0).toString());

                                            }
                                            if (z == 1) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(1).toString()));
                                            }
                                            if (z == 2) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));
                                            }
                                            if (z == 3) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                                            }

                                            if (z == 4) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                                            }

                                            if (z == 5) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));
                                            }

                                            if (z == 6) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(6).toString()));
                                            }
                                            if (z == 7) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(7).toString()));
                                            }
                                            if (z == 8) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(8).toString()));
                                            }

                                            if (z == 9) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(9).toString()));
                                            }
                                            if (z == 10) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(10).toString()));
                                            }
                                            if (z == 11) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(11).toString()));

                                            }
                                            z++;
                                        }

                                        break;
                                    case 11:
                                        while (z < realData.size()) {
                                            if (z == 0) {
                                                data2.add(realData.get(0).toString());

                                            }
                                            if (z == 1) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(1).toString()));
                                            }
                                            if (z == 2) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));
                                            }
                                            if (z == 3) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                                            }

                                            if (z == 4) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                                            }

                                            if (z == 5) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));
                                            }

                                            if (z == 6) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(6).toString()));
                                            }
                                            if (z == 7) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(7).toString()));
                                            }
                                            if (z == 8) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(8).toString()));
                                            }

                                            if (z == 9) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(9).toString()));
                                            }
                                            if (z == 10) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(10).toString()));
                                            }
                                            if (z == 11) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(11).toString()));
                                            }
                                            if (z == 12) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(12).toString()));

                                            }

                                            z++;
                                        }

                                        break;
                                    case 12:
                                        while (z < realData.size()) {
                                            if (z == 0) {
                                                data2.add(realData.get(0).toString());

                                            }
                                            if (z == 1) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(1).toString()));
                                            }
                                            if (z == 2) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));
                                            }
                                            if (z == 3) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                                            }

                                            if (z == 4) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                                            }

                                            if (z == 5) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));
                                            }

                                            if (z == 6) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(6).toString()));
                                            }
                                            if (z == 7) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(7).toString()));
                                            }
                                            if (z == 8) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(8).toString()));
                                            }

                                            if (z == 9) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(9).toString()));
                                            }
                                            if (z == 10) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(10).toString()));
                                            }
                                            if (z == 11) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(11).toString()));
                                            }
                                            if (z == 12) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(12).toString()));
                                            }
                                            if (z == 13) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(13).toString()));

                                            }
                                            z++;
                                        }

                                        break;

                                }

                                dataBody.add(data2);
                                n++;
                            }

                        }
                    }

                    if (t == 2) {

//           JOptionPane.showMessageDialog(c, "Second");
                        Map<Integer, List<Object>> dataBase = rdb.budgetEstimates(budgetPeriod, 200, c);

//       JOptionPane.showMessageDialog(c, "Second"+dataBase.size());
                        if (!dataBase.isEmpty()) {

                            while (n1 < dataBase.size()) {

                                List realData = dataBase.get(n1);
                                int z = 0;

                                List data2 = new ArrayList();

                                switch (rdb.getnumberOfBudgetPeriods()) {
                                    case 1:
                                        while (z < realData.size()) {
                                            if (z == 0) {
                                                data2.add(realData.get(0).toString());

                                            }
                                            if (z == 1) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(1).toString()));
                                            }
                                            if (z == 2) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));

                                            }
                                            z++;
                                        }

                                        break;
                                    case 2:
                                        while (z < realData.size()) {
                                            if (z == 0) {
                                                data2.add(realData.get(0).toString());

                                            }
                                            if (z == 1) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(1).toString()));
                                            }
                                            if (z == 2) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));
                                            }
                                            if (z == 3) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));

                                            }
                                            z++;
                                        }
                                        break;
                                    case 3:
                                        while (z < realData.size()) {
                                            if (z == 0) {
                                                data2.add(realData.get(0).toString());

                                            }
                                            if (z == 1) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(1).toString()));
                                            }
                                            if (z == 2) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));
                                            }
                                            if (z == 3) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                                            }
                                            if (z == 4) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                                            }
                                            z++;
                                        }

                                        break;
                                    case 4:
                                        while (z < realData.size()) {
                                            if (z == 0) {
                                                data2.add(realData.get(0).toString());

                                            }
                                            if (z == 1) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(1).toString()));
                                            }
                                            if (z == 2) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));
                                            }
                                            if (z == 3) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                                            }

                                            if (z == 4) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                                            }
                                            if (z == 5) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                                            }
                                            z++;
                                        }

                                        break;
                                    case 5:
                                        while (z < realData.size()) {
                                            if (z == 0) {
                                                data2.add(realData.get(0).toString());

                                            }
                                            if (z == 1) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(1).toString()));
                                            }
                                            if (z == 2) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));
                                            }
                                            if (z == 3) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                                            }

                                            if (z == 4) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                                            }

                                            if (z == 5) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));
                                            }
                                            if (z == 6) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(6).toString()));

                                            }
                                            z++;
                                        }

                                        break;
                                    case 6:
                                        while (z < realData.size()) {
                                            if (z == 0) {
                                                data2.add(realData.get(0).toString());

                                            }
                                            if (z == 1) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(1).toString()));
                                            }
                                            if (z == 2) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));
                                            }
                                            if (z == 3) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                                            }

                                            if (z == 4) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                                            }

                                            if (z == 5) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));
                                            }

                                            if (z == 6) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(6).toString()));
                                            }
                                            if (z == 7) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(7).toString()));

                                            }
                                            z++;
                                        }

                                        break;
                                    case 7:
                                        while (z < realData.size()) {
                                            if (z == 0) {
                                                data2.add(realData.get(0).toString());

                                            }
                                            if (z == 1) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(1).toString()));
                                            }
                                            if (z == 2) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));
                                            }
                                            if (z == 3) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                                            }

                                            if (z == 4) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                                            }

                                            if (z == 5) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));
                                            }

                                            if (z == 6) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(6).toString()));
                                            }
                                            if (z == 7) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(7).toString()));
                                            }
                                            if (z == 8) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(8).toString()));

                                            }
                                            z++;
                                        }

                                        break;
                                    case 8:
                                        while (z < realData.size()) {
                                            if (z == 0) {
                                                data2.add(realData.get(0).toString());

                                            }
                                            if (z == 1) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(1).toString()));
                                            }
                                            if (z == 2) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));
                                            }
                                            if (z == 3) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                                            }

                                            if (z == 4) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                                            }

                                            if (z == 5) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));
                                            }

                                            if (z == 6) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(6).toString()));
                                            }
                                            if (z == 7) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(7).toString()));
                                            }
                                            if (z == 8) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(8).toString()));
                                            }
                                            if (z == 9) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(9).toString()));

                                            }

                                            z++;
                                        }

                                        break;
                                    case 9:
                                        while (z < realData.size()) {
                                            if (z == 0) {
                                                data2.add(realData.get(0).toString());

                                            }
                                            if (z == 1) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(1).toString()));
                                            }
                                            if (z == 2) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));
                                            }
                                            if (z == 3) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                                            }

                                            if (z == 4) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                                            }

                                            if (z == 5) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));
                                            }

                                            if (z == 6) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(6).toString()));
                                            }
                                            if (z == 7) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(7).toString()));
                                            }
                                            if (z == 8) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(8).toString()));
                                            }

                                            if (z == 9) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(9).toString()));
                                            }
                                            if (z == 10) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(10).toString()));

                                            }

                                            z++;
                                        }

                                        break;
                                    case 10:
                                        while (z < realData.size()) {
                                            if (z == 0) {
                                                data2.add(realData.get(0).toString());

                                            }
                                            if (z == 1) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(1).toString()));
                                            }
                                            if (z == 2) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));
                                            }
                                            if (z == 3) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                                            }

                                            if (z == 4) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                                            }

                                            if (z == 5) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));
                                            }

                                            if (z == 6) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(6).toString()));
                                            }
                                            if (z == 7) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(7).toString()));
                                            }
                                            if (z == 8) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(8).toString()));
                                            }

                                            if (z == 9) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(9).toString()));
                                            }
                                            if (z == 10) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(10).toString()));
                                            }
                                            if (z == 11) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(11).toString()));

                                            }
                                            z++;
                                        }

                                        break;
                                    case 11:
                                        while (z < realData.size()) {
                                            if (z == 0) {
                                                data2.add(realData.get(0).toString());

                                            }
                                            if (z == 1) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(1).toString()));
                                            }
                                            if (z == 2) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));
                                            }
                                            if (z == 3) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                                            }

                                            if (z == 4) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                                            }

                                            if (z == 5) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));
                                            }

                                            if (z == 6) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(6).toString()));
                                            }
                                            if (z == 7) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(7).toString()));
                                            }
                                            if (z == 8) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(8).toString()));
                                            }

                                            if (z == 9) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(9).toString()));
                                            }
                                            if (z == 10) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(10).toString()));
                                            }
                                            if (z == 11) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(11).toString()));
                                            }
                                            if (z == 12) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(12).toString()));

                                            }

                                            z++;
                                        }

                                        break;
                                    case 12:
                                        while (z < realData.size()) {
                                            if (z == 0) {
                                                data2.add(realData.get(0).toString());

                                            }
                                            if (z == 1) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(1).toString()));
                                            }
                                            if (z == 2) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));
                                            }
                                            if (z == 3) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                                            }

                                            if (z == 4) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                                            }

                                            if (z == 5) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));
                                            }

                                            if (z == 6) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(6).toString()));
                                            }
                                            if (z == 7) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(7).toString()));
                                            }
                                            if (z == 8) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(8).toString()));
                                            }

                                            if (z == 9) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(9).toString()));
                                            }
                                            if (z == 10) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(10).toString()));
                                            }
                                            if (z == 11) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(11).toString()));
                                            }
                                            if (z == 12) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(12).toString()));
                                            }
                                            if (z == 13) {
                                                data2.add(fmt.formatForStatementNumbers(realData.get(13).toString()));

                                            }
                                            z++;
                                        }

                                        break;

                                }

                                dataBody.add(data2);
                                n1++;
                            }

                        }
                    }
//        try {
//        wait(1200);
//        } catch (InterruptedException ex) {
//        Logger.getLogger(OtherLoanReports.class.getName()).log(Level.SEVERE, null, ex);
//        }

                    t++;
                }

                break;
            case "Date Range":

                break;

        }

        budgetEstimatesModel BudgetEstmodel1 = new budgetEstimatesModel(dataBody, tableHeaders);

        return BudgetEstmodel1;

    }

    public ListDataModel generalLedgerForAllTxns(List dates) {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/N");
        tableHeaders.add("TXN DATE");
        tableHeaders.add("TXN REF.");
        tableHeaders.add("DR");
        tableHeaders.add("CR");
        tableHeaders.add("DR LEDGER");
        tableHeaders.add("CR LEDGER");
        tableHeaders.add("FOLIO");
        List< List<Object>> dataBody = new ArrayList();

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.generalLedgerAll(fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(0).toString()), fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(1).toString()));
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }
                        if (z == 4) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                        }
                        if (z == 5) {
                            data1.add(realData.get(5).toString());

                        }
                        if (z == 6) {
                            data1.add(realData.get(6).toString());

                        }
                        if (z == 7) {

                            data1.add(realData.get(7).toString());

                        }

                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 2) {
                            data2.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }
                        if (z == 4) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                        }
                        if (z == 5) {
                            data2.add(realData.get(5).toString());

                        }
                        if (z == 6) {
                            data2.add(realData.get(6).toString());

                        }
                        if (z == 7) {

                            data2.add(realData.get(7).toString());

                        }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            dataBody.add(data3);
        }
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

    public ListDataModel generalLedgerForAccountTxns(List dates) {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/N");
        tableHeaders.add("TXN DATE");
        tableHeaders.add("TXN REF.");
        tableHeaders.add("DR");
        tableHeaders.add("CR");
        tableHeaders.add("DR LEDGER");
        tableHeaders.add("CR LEDGER");
        tableHeaders.add("FOLIO");
        List< List<Object>> dataBody = new ArrayList();

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.generalLedgerAccount(dates.get(0).toString(), fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(1).toString()), fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(2).toString()));
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }
                        if (z == 4) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                        }
                        if (z == 5) {
                            data1.add(realData.get(5).toString());

                        }
                        if (z == 6) {
                            data1.add(realData.get(6).toString());

                        }
                        if (z == 7) {

                            data1.add(realData.get(7).toString());

                        }

                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 2) {
                            data2.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }
                        if (z == 4) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                        }
                        if (z == 5) {
                            data2.add(realData.get(5).toString());

                        }
                        if (z == 6) {
                            data2.add(realData.get(6).toString());

                        }
                        if (z == 7) {

                            data2.add(realData.get(7).toString());

                        }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            dataBody.add(data3);
        }
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

    public ListDataModel sharesContributed(List dates) {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("Date");
        tableHeaders.add("SharesRemoved");
        tableHeaders.add("SharesAdded");
        tableHeaders.add("N_Shares");
        tableHeaders.add("V_Shares");

        List< List<Object>> dataBody = new ArrayList();

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.sharesStatemnt(dates.get(0).toString(), fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(1).toString()), fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(2).toString()));

        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(2).toString()));

                        }

                        if (z == 3) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }
                        if (z == 4) {
                            data1.add(realData.get(4).toString());

                        }
                        if (z == 5) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                        }

                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 2) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));

                        }

                        if (z == 3) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }
                        if (z == 4) {
                            data2.add(realData.get(4).toString());

                        }
                        if (z == 5) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                        }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");

            dataBody.add(data3);
        }
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

    public ListDataModel savingsContributed(List dates) {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("TrnDate");
        tableHeaders.add("Period");
//    tableHeaders.add("Year");
        tableHeaders.add("SavingsRemoved");
        tableHeaders.add("SavingsAdded");
        tableHeaders.add("TotalSavings");

        List< List<Object>> dataBody = new ArrayList();

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.savingsStatement(dates.get(0).toString(), fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(1).toString()), fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(2).toString()));

        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(5).toString());

                        }
                        if (z == 2) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 3) {
                            data1.add(realData.get(2).toString());

                        }

                        if (z == 4) {
                            data1.add(realData.get(3).toString());
                        }
                        if (z == 5) {
                            data1.add(realData.get(4).toString());

                        }

//    if(z==5){
// data1.add(fmt.formatForStatementNumbers(realData.get(5).toString()));
//   
//    }
//     
                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(5).toString());
                        }
                        if (z == 2) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 3) {
                            data2.add(realData.get(2).toString());

                        }

                        if (z == 4) {
                            data2.add(realData.get(3).toString());
                        }
                        if (z == 5) {
                            data2.add(realData.get(4).toString());

                        }
//     if(z==5){
//data2.add(fmt.formatForStatementNumbers(realData.get(5).toString())); 
//  
//    }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
//     data3.add("N/A");

            dataBody.add(data3);
        }
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

    public ListDataModel sharesInterestPayDaily(List dates, Component c) {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("TxnDate");
        tableHeaders.add("TxnPeriod");
        tableHeaders.add("AccountName");
        tableHeaders.add("LedgerBalance");
        tableHeaders.add("InterestComputed");
        tableHeaders.add("InterestComRunningBal");

        List< List<Object>> dataBody = new ArrayList();

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.sharesIndivROIDaily(dates.get(0).toString(), fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(1).toString()), fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(2).toString()), c);

        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(2).toString()));

                        }

                        if (z == 3) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }
                        if (z == 4) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                        }

//    if(z==5){
// data1.add(fmt.formatForStatementNumbers(realData.get(5).toString()));
//   
//    }
//     
                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 2) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));

                        }

                        if (z == 3) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }
                        if (z == 4) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                        }
//     if(z==5){
//data2.add(fmt.formatForStatementNumbers(realData.get(5).toString())); 
//  
//    }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
//     data3.add("N/A");

            dataBody.add(data3);
        }
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

    public ListDataModel groupSharesContributed(List dates) {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("Ledger Id");
        tableHeaders.add("Ledger Name");
        tableHeaders.add("SharesRemoved");
        tableHeaders.add("SharesAdded");
        tableHeaders.add("N_Shares");
        tableHeaders.add("V_Shares");

        List< List<Object>> dataBody = new ArrayList();

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.listGroupSharesStatemnt(fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(0).toString()), fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(1).toString()));

        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(realData.get(2).toString());

                        }
                        if (z == 3) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(3).toString()));

                        }

                        if (z == 4) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                        }
                        if (z == 5) {
                            data1.add(realData.get(5).toString());

                        }
                        if (z == 6) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(6).toString()));

                        }

                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 2) {
                            data2.add(realData.get(2).toString());
                        }
                        if (z == 3) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));

                        }

                        if (z == 4) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                        }
                        if (z == 5) {
                            data2.add(realData.get(5).toString());

                        }
                        if (z == 6) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(6).toString()));

                        }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            dataBody.add(data3);
        }
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

    public ListDataModel groupSavingsContributed(List dates) {

        List<String> tableHeaders = new ArrayList();

        tableHeaders.add("S/n");
        tableHeaders.add("Ledger Id");
        tableHeaders.add("Ledger Name");
        tableHeaders.add("SavingsRemoved");
        tableHeaders.add("SavingsAdded");
        tableHeaders.add("RunningBalance");

        List< List<Object>> dataBody = new ArrayList();

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.listGroupSavingsStatemnt(fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(0).toString()), fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(1).toString()));

        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(realData.get(2).toString());

                        }
                        if (z == 3) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(3).toString()));

                        }

                        if (z == 4) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                        }
//    if(z==5){
// data1.add(realData.get(5).toString());
//   
//    }
                        if (z == 5) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                        }

                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 2) {
                            data2.add(realData.get(2).toString());
                        }
                        if (z == 3) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));

                        }

                        if (z == 4) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                        }
//    if(z==5){
//data2.add(realData.get(5).toString()); 
//  
//    }
                        if (z == 5) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                        }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
//    data3.add("N/A");
            dataBody.add(data3);
        }
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

    public ListDataModel groupSavingsContributedAll(List dates) {

        List<String> tableHeaders = new ArrayList();

        tableHeaders.add("S/n");
        tableHeaders.add("Ledger Id");
        tableHeaders.add("Ledger Name");
        tableHeaders.add("SavingsRemoved");
        tableHeaders.add("SavingsAdded");
        tableHeaders.add("RunningBalance");

        List< List<Object>> dataBody = new ArrayList();

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.listGroupSavingsStatemntAll(fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(0).toString()), fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(1).toString()));

        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(realData.get(2).toString());

                        }
                        if (z == 3) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(3).toString()));

                        }

                        if (z == 4) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                        }
//    if(z==5){
// data1.add(realData.get(5).toString());
//   
//    }
                        if (z == 5) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                        }

                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 2) {
                            data2.add(realData.get(2).toString());
                        }
                        if (z == 3) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));

                        }

                        if (z == 4) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                        }
//    if(z==5){
//data2.add(realData.get(5).toString()); 
//  
//    }
                        if (z == 5) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                        }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
//    data3.add("N/A");
            dataBody.add(data3);
        }
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

    public ListDataModel groupSharesContributedAll(List dates) {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("Ledger Id");
        tableHeaders.add("Ledger Name");
        tableHeaders.add("SharesRemoved");
        tableHeaders.add("SharesAdded");
        tableHeaders.add("N_Shares");
        tableHeaders.add("V_Shares");

        List< List<Object>> dataBody = new ArrayList();

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.listGroupSharesStatemntAll(fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(0).toString()), fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(1).toString()));

        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(realData.get(2).toString());

                        }
                        if (z == 3) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(3).toString()));

                        }

                        if (z == 4) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                        }
                        if (z == 5) {
                            data1.add(realData.get(5).toString());

                        }
                        if (z == 6) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(6).toString()));

                        }

                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }
                        if (z == 2) {
                            data2.add(realData.get(2).toString());
                        }
                        if (z == 3) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));

                        }

                        if (z == 4) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));
                        }
                        if (z == 5) {
                            data2.add(realData.get(5).toString());

                        }
                        if (z == 6) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(6).toString()));

                        }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            dataBody.add(data3);
        }
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

    public ListDataModel individualMontlyROI(List dates) {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("Month");
        tableHeaders.add("Shares");
        tableHeaders.add("Return");
        tableHeaders.add("Running Balance");

        List< List<Object>> dataBody = new ArrayList();
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.montlyReturnOIGroup(dates.get(0).toString(), fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(1).toString()), fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(2).toString()));

//    fios.stringFileWriter(fios.createFileName("test", "testit", dataBase.size()+"trdyr.txt"),dataBase.size()+""); 
        if (!dataBase.isEmpty()) {

            while (n < dataBase.size()) {

                List realData = dataBase.get(n);
                int z = 0;

                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }

                        if (z == 2) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(2).toString()));
                        }
                        if (z == 3) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(3).toString()));

                        }
                        if (z == 4) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                        }

                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }

                        if (z == 2) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(2).toString()));
                        }
                        if (z == 3) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));

                        }
                        if (z == 4) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                        }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");

            dataBody.add(data3);
        }
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

    public ListDataModel groupMontlyROI(List dates) {

        List<String> tableHeaders = new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("Month");
        tableHeaders.add("Name");
        tableHeaders.add("Shares");
        tableHeaders.add("Return");
        tableHeaders.add("Running Balance");

        List< List<Object>> dataBody = new ArrayList();

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.grroupmontlyReturnOIGroup(fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(0).toString()), fmt.forDatabaseWithFullYearBeginningWithDate(dates.get(1).toString()));

//    fios.stringFileWriter(fios.createFileName("test", "testit", dataBase.size()+"trdyr.txt"),dataBase.size()+""); 
        if (!dataBase.isEmpty()) {

            while (n < dataBase.size()) {

                List realData = dataBase.get(n);
                int z = 0;

                if (n == dataBase.size() - 1) {
                    List data1 = new ArrayList();
                    while (z < realData.size()) {

                        if (z == 0) {

                            data1.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data1.add(realData.get(1).toString());

                        }
                        if (z == 2) {
                            data1.add(realData.get(2).toString());

                        }

                        if (z == 3) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }
                        if (z == 4) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                        }
                        if (z == 5) {
                            data1.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                        }

                        z++;
                    }
                    dataBody.add(data1);
                } else {
                    List data2 = new ArrayList();
                    while (z < realData.size()) {
                        if (z == 0) {
                            data2.add(realData.get(0).toString());

                        }
                        if (z == 1) {
                            data2.add(realData.get(1).toString());
                        }

                        if (z == 2) {
                            data2.add(realData.get(2).toString());
                        }

                        if (z == 3) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(3).toString()));
                        }
                        if (z == 4) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(4).toString()));

                        }
                        if (z == 5) {
                            data2.add(fmt.formatForStatementNumbers(realData.get(5).toString()));

                        }

                        z++;
                    }
                    dataBody.add(data2);
                }

                n++;
            }
        } else {
            List data3 = new ArrayList();
            data3.add("N/A");
            data3.add("Empty selection from database");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");
            data3.add("N/A");

            dataBody.add(data3);
        }
        model1 = new ListDataModel(dataBody, tableHeaders);

        return model1;

    }

    public boolean createNonPeriodicSummury(String fileName) {

        boolean nonPeriodicSummury = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyNonPeriodicSummury(fileName);
                nonPeriodicSummury = true;
            }

        } else {
            createActuallyNonPeriodicSummury(fileName);
            nonPeriodicSummury = true;
        }

        return nonPeriodicSummury;
    }

    private void createActuallyNonPeriodicSummury(String fileName) {
        try {

            Paragraph headerz = createHeading("nperiodic_in", "", "");
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));
            PdfPTable body = createTheNonPeriodicSummury();

            Font font1 = new Font(Font.FontFamily.COURIER, 14, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();

            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));
            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);
            ct.setIndentationLeft(50f);
            ct.setIndentationRight(200f);
            document.add(ct);

            Image image1 = Image.getInstance("strawberry3.jpg");
            image1.setAbsolutePosition(49f, 1080f);
            image1.scaleAbsolute(43f, 43f);
            document.add(image1);
            document.add(headerz);
            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("", font1);
            currency.setIndentationLeft(280f);
            currency.setIndentationRight(30f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public PdfPTable createTheNonPeriodicSummury() {
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        float[] columnWidths = {55f, 20f, 40f, 20f, 40f, 20f, 40f};

        Font font1 = new Font(Font.FontFamily.COURIER, 14, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 14, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        PdfPCell emptyCell = new PdfPCell(new Paragraph("", font2));
        PdfPCell cell1 = new PdfPCell(new Paragraph("Male", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Female", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Total", font2));
        PdfPCell sumItem = new PdfPCell(new Paragraph("Summury Item", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Number", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Value", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Number", font2));
        PdfPCell cell7 = new PdfPCell(new Paragraph("Value", font2));
        PdfPCell cell8 = new PdfPCell(new Paragraph("Number", font2));
        PdfPCell cell9 = new PdfPCell(new Paragraph("Value", font2));

        cell1.setBackgroundColor(BaseColor.CYAN);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.CYAN);
        cell3.setBackgroundColor(BaseColor.CYAN);
        sumItem.setBackgroundColor(BaseColor.CYAN);
        cell4.setBackgroundColor(BaseColor.CYAN);
        cell5.setBackgroundColor(BaseColor.CYAN);
        cell6.setBackgroundColor(BaseColor.CYAN);
        cell7.setBackgroundColor(BaseColor.CYAN);
        cell8.setBackgroundColor(BaseColor.CYAN);
        cell9.setBackgroundColor(BaseColor.CYAN);

        emptyCell.setBorderWidth(2f);
        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        sumItem.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);
        cell7.setBorderWidth(2f);
        cell8.setBorderWidth(2f);
        cell9.setBorderWidth(2f);

        emptyCell.disableBorderSide(LEFT);
        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        sumItem.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);
        cell5.disableBorderSide(LEFT);
        cell6.disableBorderSide(LEFT);
        cell7.disableBorderSide(LEFT);
        cell8.disableBorderSide(LEFT);
        cell9.disableBorderSide(LEFT);
        emptyCell.disableBorderSide(TOP);
        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        sumItem.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);
        cell7.disableBorderSide(TOP);
        cell8.disableBorderSide(TOP);
        cell9.disableBorderSide(TOP);
        emptyCell.disableBorderSide(RIGHT);
        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        sumItem.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);
        cell7.disableBorderSide(RIGHT);
        cell8.disableBorderSide(RIGHT);
        cell9.disableBorderSide(RIGHT);
        cell1.setColspan(2);
        cell2.setColspan(2);
        cell3.setColspan(2);

        cell1.setVerticalAlignment(Element.ALIGN_CENTER);       // sets border width to 3 units
        cell2.setVerticalAlignment(Element.ALIGN_CENTER);
        cell3.setVerticalAlignment(Element.ALIGN_CENTER);
        cell4.setVerticalAlignment(Element.ALIGN_CENTER);       // sets border width to 3 units
        cell5.setVerticalAlignment(Element.ALIGN_CENTER);
        cell6.setVerticalAlignment(Element.ALIGN_CENTER);
        cell7.setVerticalAlignment(Element.ALIGN_CENTER);       // sets border width to 3 units
        cell8.setVerticalAlignment(Element.ALIGN_CENTER);
        cell9.setVerticalAlignment(Element.ALIGN_CENTER);

        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);       // sets border width to 3 units
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);       // sets border width to 3 units
        cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell7.setHorizontalAlignment(Element.ALIGN_CENTER);       // sets border width to 3 units
        cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell9.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(emptyCell);
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);

        table.addCell(sumItem);

        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        table.addCell(cell8);
        table.addCell(cell9);

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.summuryLoanNP();
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;

                while (z < realData.size()) {
                    if (z == 0) {
                        PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                        cellx1.setFixedHeight(16.2f);
                        cellx1.disableBorderSide(LEFT);
                        cellx1.disableBorderSide(TOP);
                        cellx1.disableBorderSide(RIGHT);
                        cellx1.setPadding(.5f);
                        cellx1.setBorderColorBottom(BaseColor.BLACK);
                        cellx1.setBorderColorTop(BaseColor.WHITE);
                        cellx1.setBorderColorLeft(BaseColor.WHITE);
                        cellx1.setBorderColorRight(BaseColor.WHITE);
                        cellx1.setVerticalAlignment(Element.ALIGN_TOP);

                        table.addCell(cellx1);
                    }
                    if (z == 1) {

                        PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                        cellx2.disableBorderSide(LEFT);
                        cellx2.disableBorderSide(TOP);
                        cellx2.disableBorderSide(RIGHT);
                        cellx2.setBorderColorBottom(BaseColor.BLACK);
                        cellx2.setBorderColorTop(BaseColor.WHITE);
                        cellx2.setBorderColorLeft(BaseColor.WHITE);
                        cellx2.setBorderColorRight(BaseColor.WHITE);
                        cellx2.setFixedHeight(16.2f);
                        cellx2.setPadding(.5f);
                        cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx2.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cellx2);

                    }
                    if (z == 2) {

                        PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font1));
                        cellx3.setFixedHeight(16.2f);
                        cellx3.setPadding(.5f);
                        cellx3.disableBorderSide(LEFT);
                        cellx3.disableBorderSide(TOP);
                        cellx3.disableBorderSide(RIGHT);
                        cellx3.setBorderColorBottom(BaseColor.BLACK);
                        cellx3.setBorderColorTop(BaseColor.WHITE);
                        cellx3.setBorderColorLeft(BaseColor.WHITE);
                        cellx3.setBorderColorRight(BaseColor.WHITE);
                        cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx3);
                    }
                    if (z == 3) {

                        PdfPCell cellx4 = new PdfPCell(new Paragraph(realData.get(3).toString(), font1));
                        cellx4.setFixedHeight(16.2f);
                        cellx4.setPadding(.5f);
                        cellx4.disableBorderSide(LEFT);
                        cellx4.disableBorderSide(TOP);
                        cellx4.disableBorderSide(RIGHT);
                        cellx4.setBorderColorBottom(BaseColor.BLACK);
                        cellx4.setBorderColorTop(BaseColor.WHITE);
                        cellx4.setBorderColorLeft(BaseColor.WHITE);
                        cellx4.setBorderColorRight(BaseColor.WHITE);
                        cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx4.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cellx4);
                    }
                    if (z == 4) {

                        PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font1));
                        cellx5.setFixedHeight(16.2f);
                        cellx5.setPadding(.5f);
                        cellx5.disableBorderSide(LEFT);
                        cellx5.disableBorderSide(TOP);
                        cellx5.disableBorderSide(RIGHT);
                        cellx5.setBorderColorBottom(BaseColor.BLACK);
                        cellx5.setBorderColorTop(BaseColor.WHITE);
                        cellx5.setBorderColorLeft(BaseColor.WHITE);
                        cellx5.setBorderColorRight(BaseColor.WHITE);
                        cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx5);
                    }
                    if (z == 5) {

                        PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(5).toString(), font1));
                        cellx6.setFixedHeight(16.2f);
                        cellx6.setPadding(.5f);
                        cellx6.disableBorderSide(LEFT);
                        cellx6.disableBorderSide(TOP);
                        cellx6.disableBorderSide(RIGHT);
                        cellx6.setBorderColorBottom(BaseColor.BLACK);
                        cellx6.setBorderColorTop(BaseColor.WHITE);
                        cellx6.setBorderColorLeft(BaseColor.WHITE);
                        cellx6.setBorderColorRight(BaseColor.WHITE);
                        cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx6.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cellx6);
                    }
                    if (z == 6) {

                        PdfPCell cellx7 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(6).toString()), font1));
                        cellx7.setFixedHeight(16.2f);
                        cellx7.setPadding(.5f);
                        cellx7.disableBorderSide(LEFT);
                        cellx7.disableBorderSide(TOP);
                        cellx7.disableBorderSide(RIGHT);
                        cellx7.setBorderColorBottom(BaseColor.BLACK);
                        cellx7.setBorderColorTop(BaseColor.WHITE);
                        cellx7.setBorderColorLeft(BaseColor.WHITE);
                        cellx7.setBorderColorRight(BaseColor.WHITE);
                        cellx7.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx7.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx7);
                    }
                    z++;
                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));

        }
        return table;

    }

    public boolean createGrossPortfolio(String startDate, String endDate, String fileName) {
        boolean grossloanPort = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyGrossPortfolio(startDate, endDate, fileName);
                grossloanPort = true;
            }

        } else {
            createActuallyGrossPortfolio(startDate, endDate, fileName);
            grossloanPort = true;
        }

        return grossloanPort;
    }

    private void createActuallyGrossPortfolio(String startDate, String endDate, String fileName) {
        try {

            Paragraph headerz = createHeading("gross_loan_port", startDate, endDate);
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
            document.addAuthor("Bazirake Augustine Googo");
            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));
            PdfPTable body = createTheGrossPortfolio(startDate, endDate);

            Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);
            ct.setIndentationLeft(50f);
            ct.setIndentationRight(200f);
            document.add(ct);
            ct.setAlignment(Element.ALIGN_CENTER);
            Image image1 = Image.getInstance("strawberry3.jpg");
            image1.setAbsolutePosition(49f, 1080f);
            image1.scaleAbsolute(43f, 43f);
            document.add(image1);
            document.add(headerz);
            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(250f);
            currency.setIndentationRight(80f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createTheGrossPortfolio(String sdate, String edate) {
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        float[] columnWidths = {20f, 60f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Borrower", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Princimpal", font2));

        cell1.setBackgroundColor(BaseColor.GREEN);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.GREEN);
        cell3.setBackgroundColor(BaseColor.GREEN);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);

        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.grossPortfolio(sdate, edate);
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font2));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        z++;
                    }

                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font1));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        z++;
                    }
                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));

        }
        return table;

    }

    public boolean createperformingPortfolio(String startDate, String endDate, String fileName) {
        boolean performingloanPort = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyPerformingPortfolio(startDate, endDate, fileName);
                performingloanPort = true;
            }

        } else {
            createActuallyPerformingPortfolio(startDate, endDate, fileName);
            performingloanPort = true;
        }

        return performingloanPort;
    }

    private void createActuallyPerformingPortfolio(String startDate, String endDate, String fileName) {
        try {

            Paragraph headerz = createHeading("performing_loan_port", startDate, endDate);
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
            document.addAuthor("Bazirake Augustine Googo");
            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));
            PdfPTable body = createThePerformingPortfolio(startDate, endDate);

            Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);
            ct.setIndentationLeft(50f);
            ct.setIndentationRight(200f);
            document.add(ct);
            ct.setAlignment(Element.ALIGN_CENTER);
            Image image1 = Image.getInstance("strawberry3.jpg");
            image1.setAbsolutePosition(49f, 1080f);
            image1.scaleAbsolute(43f, 43f);
            document.add(image1);
            document.add(headerz);
            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(250f);
            currency.setIndentationRight(80f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createThePerformingPortfolio(String sdate, String edate) {
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        float[] columnWidths = {20f, 60f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Borrower", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Princimpal", font2));

        cell1.setBackgroundColor(BaseColor.GREEN);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.GREEN);
        cell3.setBackgroundColor(BaseColor.GREEN);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);

        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.performingPortfolio(sdate, edate);
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font2));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        z++;
                    }

                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font1));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        z++;
                    }
                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));

        }
        return table;

    }

    public boolean createLoanInAreearsReport(String startDate, String endDate, String fileName) {
        boolean arrearsR = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyArrearsR(startDate, endDate, fileName);
                arrearsR = true;
            }

        } else {
            createActuallyArrearsR(startDate, endDate, fileName);
            arrearsR = true;
        }

        return arrearsR;
    }

    private void createActuallyArrearsR(String startDate, String endDate, String fileName) {
        try {

            Paragraph headerz = createHeading("loan_arrears_R", startDate, endDate);
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
            document.addAuthor("Bazirake Augustine Googo");
            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));
            PdfPTable body = createTheArrearsR(startDate, endDate);

            Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);
            ct.setIndentationLeft(50f);
            ct.setIndentationRight(200f);
            document.add(ct);
            ct.setAlignment(Element.ALIGN_CENTER);
            Image image1 = Image.getInstance("strawberry3.jpg");
            image1.setAbsolutePosition(49f, 1080f);
            image1.scaleAbsolute(43f, 43f);
            document.add(image1);
            document.add(headerz);
            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(250f);
            currency.setIndentationRight(80f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createTheArrearsR(String sdate, String edate) {
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        float[] columnWidths = {20f, 60f, 60f};

        Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Borrower", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Princimpal_In_Arrears", font2));

        cell1.setBackgroundColor(BaseColor.GREEN);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.GREEN);
        cell3.setBackgroundColor(BaseColor.GREEN);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);

        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.arrearR(sdate, edate);
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font2));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        z++;
                    }

                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font1));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        z++;
                    }
                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));

        }
        return table;

    }

    public boolean createPortfolioAtRisk(String startDate, String endDate, String fileName) {
        boolean loanPortAtRisk = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyPortfolioAtRisk(startDate, endDate, fileName);
                loanPortAtRisk = true;
            }

        } else {
            createActuallyPortfolioAtRisk(startDate, endDate, fileName);
            loanPortAtRisk = true;
        }

        return loanPortAtRisk;
    }

    private void createActuallyPortfolioAtRisk(String startDate, String endDate, String fileName) {
        try {

            Paragraph headerz = createHeading("loan_port_atRisk", startDate, endDate);
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
            document.addAuthor("Bazirake Augustine Googo");
            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));
            PdfPTable body = createThePortfolioAtRisk(startDate, endDate);

            Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);
            ct.setIndentationLeft(50f);
            ct.setIndentationRight(200f);
            document.add(ct);
            ct.setAlignment(Element.ALIGN_CENTER);
            Image image1 = Image.getInstance("strawberry3.jpg");
            image1.setAbsolutePosition(49f, 1080f);
            image1.scaleAbsolute(43f, 43f);
            document.add(image1);
            document.add(headerz);
            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(250f);
            currency.setIndentationRight(80f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createThePortfolioAtRisk(String sdate, String edate) {
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        float[] columnWidths = {20f, 60f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Borrower", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Princimpal", font2));

        cell1.setBackgroundColor(BaseColor.GREEN);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.GREEN);
        cell3.setBackgroundColor(BaseColor.GREEN);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);

        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.portfolioAtRisk(sdate, edate);
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font2));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        z++;
                    }

                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font1));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }

                        z++;
                    }
                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));

        }
        return table;

    }

    public boolean createLoanDueWriteOff(String startDate, String endDate, String fileName) {
        boolean loandueWriteOff = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyLoanDueWriteOff(startDate, endDate, fileName);
                loandueWriteOff = true;
            }

        } else {
            createActuallyLoanDueWriteOff(startDate, endDate, fileName);
            loandueWriteOff = true;
        }

        return loandueWriteOff;
    }

    private void createActuallyLoanDueWriteOff(String startDate, String endDate, String fileName) {
        try {

            Paragraph headerz = createHeading("loan_due_writeOff", startDate, endDate);
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
            document.addAuthor("Bazirake Augustine Googo");
            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));
            PdfPTable body = createTheLoanDueWriteOff(startDate, endDate);

            Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);
            ct.setIndentationLeft(50f);
            ct.setIndentationRight(200f);
            document.add(ct);
            ct.setAlignment(Element.ALIGN_CENTER);
            Image image1 = Image.getInstance("strawberry3.jpg");
            image1.setAbsolutePosition(49f, 1080f);
            image1.scaleAbsolute(43f, 43f);
            document.add(image1);
            document.add(headerz);
            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(250f);
            currency.setIndentationRight(80f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createTheLoanDueWriteOff(String sdate, String edate) {
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        float[] columnWidths = {12f, 60f, 30f, 30f, 20f};

        Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Borrower", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Princimpal", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Interest", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Aging (Days)", font2));

        cell1.setBackgroundColor(BaseColor.GREEN);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.GREEN);
        cell3.setBackgroundColor(BaseColor.GREEN);
        cell4.setBackgroundColor(BaseColor.GREEN);
        cell5.setBackgroundColor(BaseColor.GREEN);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);

        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);
        cell5.disableBorderSide(LEFT);

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);
        cell5.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);
        cell5.disableBorderSide(RIGHT);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.loansForWriteOff(sdate, edate);
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font2));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }
                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(realData.get(4).toString(), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx5);
                        }
                        z++;
                    }

                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font1));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }
                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }

                        z++;
                    }
                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
        }
        return table;

    }

    public boolean createLoanApplied(String startDate, String endDate, String fileName) {
        boolean loansApplied = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyLoanApplied(startDate, endDate, fileName);
                loansApplied = true;
            }

        } else {
            createActuallyLoanApplied(startDate, endDate, fileName);
            loansApplied = true;
        }

        return loansApplied;
    }

    private void createActuallyLoanApplied(String startDate, String endDate, String fileName) {
        try {

            Paragraph headerz = createHeading("loan_appliedx", startDate, endDate);
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
            document.addAuthor("Bazirake Augustine Googo");
            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));
            PdfPTable body = createTheLoanAppledx(startDate, endDate);

            Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);
            ct.setIndentationLeft(50f);
            ct.setIndentationRight(200f);
            document.add(ct);
            ct.setAlignment(Element.ALIGN_CENTER);
            Image image1 = Image.getInstance("strawberry3.jpg");
            image1.setAbsolutePosition(49f, 1080f);
            image1.scaleAbsolute(43f, 43f);
            document.add(image1);
            document.add(headerz);
            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(250f);
            currency.setIndentationRight(80f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createTheLoanAppledx(String sdate, String edate) {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        float[] columnWidths = {14f, 50f, 30f, 30f, 30f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Borrower", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Princimpal", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Interest", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Instalment Start", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Instalment End", font2));

        cell1.setBackgroundColor(BaseColor.GREEN);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.GREEN);
        cell3.setBackgroundColor(BaseColor.GREEN);
        cell4.setBackgroundColor(BaseColor.GREEN);
        cell5.setBackgroundColor(BaseColor.GREEN);

        cell6.setBackgroundColor(BaseColor.GREEN);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);
        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);
        cell5.disableBorderSide(LEFT);
        cell6.disableBorderSide(LEFT);

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.loansAppliedx(sdate, edate);
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font2));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }
                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(realData.get(4).toString(), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(5).toString(), font2));
                            cellx6.setFixedHeight(20.2f);
                            cellx6.setPadding(.5f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx6);
                        }
                        z++;
                    }

                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font1));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }
                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(realData.get(4).toString(), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx5);
                        }

                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(5).toString(), font2));
                            cellx6.setFixedHeight(20.2f);
                            cellx6.setPadding(.5f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx6);
                        }
                        z++;
                    }
                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));

        }
        return table;
    }

    public boolean createReturnOnInvestmentMonthlyGroup(String startDate, String endDate, String fileName) {
        boolean montlyReturnGroup = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyReturnOnInvestmentMonthlyGroup(startDate, endDate, fileName);
                montlyReturnGroup = true;
            }

        } else {
            createActuallyReturnOnInvestmentMonthlyGroup(startDate, endDate, fileName);
            montlyReturnGroup = true;
        }

        return montlyReturnGroup;
    }

    private void createActuallyReturnOnInvestmentMonthlyGroup(String startDate, String endDate, String fileName) {
        try {

            Paragraph headerz = createHeading("montly_ReturnGroup", startDate, endDate);
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
            document.addAuthor("Bazirake Augustine Googo");
            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));
            PdfPTable body = createTheMonthlyReturnGroup(startDate, endDate);

            Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);
            ct.setIndentationLeft(50f);
            ct.setIndentationRight(200f);
            document.add(ct);
            ct.setAlignment(Element.ALIGN_CENTER);
            Image image1 = Image.getInstance("strawberry3.jpg");
            image1.setAbsolutePosition(49f, 1080f);
            image1.scaleAbsolute(43f, 43f);
            document.add(image1);
            document.add(headerz);
            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(250f);
            currency.setIndentationRight(80f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createTheMonthlyReturnGroup(String sdate, String edate) {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        float[] columnWidths = {14f, 30f, 60f, 30f, 30f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Month", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Name", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Shares", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Return", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Running Balance", font2));

        cell1.setBackgroundColor(BaseColor.GREEN);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.GREEN);
        cell3.setBackgroundColor(BaseColor.GREEN);
        cell4.setBackgroundColor(BaseColor.GREEN);
        cell5.setBackgroundColor(BaseColor.GREEN);

        cell6.setBackgroundColor(BaseColor.GREEN);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);
        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);
        cell5.disableBorderSide(LEFT);
        cell6.disableBorderSide(LEFT);

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.grroupmontlyReturnOIGroup(sdate, edate);
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(realData.get(2).toString(), font2));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx3);
                        }
                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font2));
                            cellx6.setFixedHeight(20.2f);
                            cellx6.setPadding(.5f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }
                        z++;
                    }

                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(realData.get(2).toString(), font1));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx3);
                        }
                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }

                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font2));
                            cellx6.setFixedHeight(20.2f);
                            cellx6.setPadding(.5f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }
                        z++;
                    }
                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));

        }
        return table;
    }

    public boolean createReturnOnInvestmentDailyIndividual(String accountNumber, String startDate, String endDate, String fileName) {
        boolean dailyReturnIndividual = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyReturnOnInvestmentDailyIndividual(accountNumber, startDate, endDate, fileName);
                dailyReturnIndividual = true;
            }

        } else {
            createActuallyReturnOnInvestmentDailyIndividual(accountNumber, startDate, endDate, fileName);
            dailyReturnIndividual = true;
        }

        return dailyReturnIndividual;
    }

    private void createActuallyReturnOnInvestmentDailyIndividual(String accountNumber, String startDate, String endDate, String fileName) {
        try {
            fios.stringFileWriter(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "accountNumber.txt"), accountNumber);

            Paragraph headerz = createHeading("individual_ReturnDaily", startDate, endDate);
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
            document.addAuthor("Bazirake Augustine Googo");
            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));
            PdfPTable body = createTheDailyReturnIndividual(accountNumber, startDate, endDate);

            Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);
            ct.setIndentationLeft(50f);
            ct.setIndentationRight(200f);
            document.add(ct);
            ct.setAlignment(Element.ALIGN_CENTER);
            Image image1 = Image.getInstance("strawberry3.jpg");
            image1.setAbsolutePosition(49f, 1080f);
            image1.scaleAbsolute(43f, 43f);
            document.add(image1);
            document.add(headerz);
            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(250f);
            currency.setIndentationRight(80f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createTheDailyReturnIndividual(String accountNumber, String sdate, String edate) {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        float[] columnWidths = {14f, 30f, 30f, 30f, 30f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Date", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Month", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Shares", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Return", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Running Balance", font2));

        cell1.setBackgroundColor(BaseColor.GREEN);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.GREEN);
        cell3.setBackgroundColor(BaseColor.GREEN);
        cell4.setBackgroundColor(BaseColor.GREEN);
        cell5.setBackgroundColor(BaseColor.GREEN);

        cell6.setBackgroundColor(BaseColor.GREEN);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);
        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);
        cell5.disableBorderSide(LEFT);
        cell6.disableBorderSide(LEFT);

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.returnOI(accountNumber, sdate, edate);
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(realData.get(2).toString(), font2));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx3);
                        }
                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font2));
                            cellx6.setFixedHeight(20.2f);
                            cellx6.setPadding(.5f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }
                        z++;
                    }

                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(realData.get(2).toString(), font1));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx3);
                        }
                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }

                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font2));
                            cellx6.setFixedHeight(20.2f);
                            cellx6.setPadding(.5f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }
                        z++;
                    }
                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));

        }
        return table;
    }

    public boolean individualMontlyROIpdf(String accountNumber, String startDate, String endDate, String fileName) {
        boolean IndivisualmontlyReturnGroup = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyindividualMontlyROIpdf(accountNumber, startDate, endDate, fileName);
                IndivisualmontlyReturnGroup = true;
            }

        } else {
            createActuallyindividualMontlyROIpdf(accountNumber, startDate, endDate, fileName);
            IndivisualmontlyReturnGroup = true;
        }

        return IndivisualmontlyReturnGroup;
    }

    private void createActuallyindividualMontlyROIpdf(String accountNumber, String startDate, String endDate, String fileName) {
        try {

            fios.stringFileWriter(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "accountNumber.txt"), accountNumber);

            Paragraph headerz = createHeading("individual_montly_ReturnGroup", startDate, endDate);
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);

            document.addAuthor("Bazirake Augustine Googo");

            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));

            PdfPTable body = createActuallyindividualMontlTable(accountNumber, startDate, endDate);

            Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);

            ct.setIndentationLeft(50f);

            ct.setIndentationRight(200f);

            document.add(ct);

            ct.setAlignment(Element.ALIGN_CENTER);

            Image image1 = Image.getInstance("strawberry3.jpg");

            image1.setAbsolutePosition(49f, 1080f);

            image1.scaleAbsolute(43f, 43f);

            document.add(image1);

            document.add(headerz);

            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(250f);
            currency.setIndentationRight(80f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createActuallyindividualMontlTable(String accountNumber, String sdate, String edate) {
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        float[] columnWidths = {14f, 50f, 30f, 30f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Month", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Shares", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Return", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Running Balance", font2));

        cell1.setBackgroundColor(BaseColor.GREEN);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.GREEN);
        cell3.setBackgroundColor(BaseColor.GREEN);
        cell4.setBackgroundColor(BaseColor.GREEN);
        cell5.setBackgroundColor(BaseColor.GREEN);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);

        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);
        cell5.disableBorderSide(LEFT);

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);
        cell5.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);
        cell5.disableBorderSide(RIGHT);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.montlyReturnOIGroup(accountNumber, sdate, edate);
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font2));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }
                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }

                        z++;
                    }

                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font1));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }
                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }

                        z++;
                    }

                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
        }
        return table;
    }

    public boolean individualSharesContributions(String accountNumber, String startDate, String endDate, String fileName) {

        boolean IndivisualmontlyReturnGroup = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyindividualSharesContributionspdf(accountNumber, startDate, endDate, fileName);
                IndivisualmontlyReturnGroup = true;
            }

        } else {
            createActuallyindividualSharesContributionspdf(accountNumber, startDate, endDate, fileName);
            IndivisualmontlyReturnGroup = true;
        }

        return IndivisualmontlyReturnGroup;
    }

    private void createActuallyindividualSharesContributionspdf(String accountNumber, String startDate, String endDate, String fileName) {
        try {

            fios.stringFileWriter(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "accountNumber.txt"), accountNumber);

            Paragraph headerz = createHeading("individual_montly_Shares", startDate, endDate);
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);

            document.addAuthor("Bazirake Augustine Googo");

            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));

            PdfPTable body = createActuallyindividualSavingsTable(accountNumber, startDate, endDate);

            Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);

            ct.setIndentationLeft(50f);

            ct.setIndentationRight(200f);

            document.add(ct);

            ct.setAlignment(Element.ALIGN_CENTER);

            Image image1 = Image.getInstance("strawberry3.jpg");

            image1.setAbsolutePosition(49f, 1080f);

            image1.scaleAbsolute(43f, 43f);

            document.add(image1);

            document.add(headerz);

            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(250f);
            currency.setIndentationRight(80f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createActuallyindividualSavingsTable(String accountNumber, String sdate, String edate) {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        float[] columnWidths = {14f, 25f, 30f, 30f, 30f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
//         List<String> tableHeaders=new ArrayList();
//      tableHeaders.add("S/n");
//    tableHeaders.add("Date");
//    tableHeaders.add("SharesRemoved");
//    tableHeaders.add("SharesAdded");
//    tableHeaders.add("N_Shares");
//    tableHeaders.add("V_Shares"); 
//    
//     List< List<Object>> dataBody=new ArrayList();
//     
//     int n=0; Map<Integer, List<Object>> dataBase=rdb. sharesStatemnt
        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Date", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("SharesRemoved", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("SharesAdded", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("N_Shares", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("V_Shares", font2));

        cell1.setBackgroundColor(BaseColor.CYAN);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.CYAN);
        cell3.setBackgroundColor(BaseColor.CYAN);
        cell4.setBackgroundColor(BaseColor.CYAN);
        cell5.setBackgroundColor(BaseColor.CYAN);
        cell6.setBackgroundColor(BaseColor.CYAN);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);

        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);
        cell5.disableBorderSide(LEFT);
        cell6.disableBorderSide(LEFT);

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);

        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);        // sets border width to 3 units
        cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.sharesStatemnt(accountNumber, sdate, edate);
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font2));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }
                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }

                        if (z == 5) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }

                        z++;
                    }

                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font1));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }
                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        z++;
                    }

                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
        }
        return table;
    }

    public boolean individualSavingsContributions(String accountNumber, String startDate, String endDate, String fileName) {

        boolean IndivisualmontlySavings = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyindividualSavingsContributionspdf(accountNumber, startDate, endDate, fileName);
                IndivisualmontlySavings = true;
            }

        } else {
            createActuallyindividualSavingsContributionspdf(accountNumber, startDate, endDate, fileName);
            IndivisualmontlySavings = true;
        }

        return IndivisualmontlySavings;
    }

    private void createActuallyindividualSavingsContributionspdf(String accountNumber, String startDate, String endDate, String fileName) {
        try {

            fios.stringFileWriter(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "accountNumber.txt"), accountNumber);

            Paragraph headerz = createHeading("individual_montly_Savings", startDate, endDate);
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);

            document.addAuthor("Bazirake Augustine Googo");

            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));

            PdfPTable body = createActuallyindividualSharesTable(accountNumber, startDate, endDate);

            Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);

            ct.setIndentationLeft(50f);

            ct.setIndentationRight(200f);

            document.add(ct);

            ct.setAlignment(Element.ALIGN_CENTER);

            Image image1 = Image.getInstance("strawberry3.jpg");

            image1.setAbsolutePosition(49f, 1080f);

            image1.scaleAbsolute(43f, 43f);

            document.add(image1);

            document.add(headerz);

            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(250f);
            currency.setIndentationRight(80f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createActuallyindividualSharesTable(String accountNumber, String sdate, String edate) {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        float[] columnWidths = {8f, 25f,50f, 30f, 30f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Date", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Narration", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Savings Removed", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Savings Added", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Running Balance", font2));

        cell1.setBackgroundColor(BaseColor.CYAN);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.CYAN);
        cell3.setBackgroundColor(BaseColor.CYAN);
        cell4.setBackgroundColor(BaseColor.CYAN);
        cell5.setBackgroundColor(BaseColor.CYAN);
         cell6.setBackgroundColor(BaseColor.CYAN);
         
        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);
//        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
//        cell2.disableBorderSide(LEFT);
//        cell3.disableBorderSide(LEFT);
//        cell4.disableBorderSide(LEFT);
//        cell5.disableBorderSide(LEFT);
//        cell6.disableBorderSide(LEFT);
//        cell1.disableBorderSide(TOP);       // sets border width to 3 units
//        cell2.disableBorderSide(TOP);
//        cell3.disableBorderSide(TOP);
//        cell4.disableBorderSide(TOP);
//        cell5.disableBorderSide(TOP);
//        cell6.disableBorderSide(TOP);
//        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
//        cell2.disableBorderSide(RIGHT);
//        cell3.disableBorderSide(RIGHT);
//        cell4.disableBorderSide(RIGHT);
//        cell5.disableBorderSide(RIGHT);
//        cell6.disableBorderSide(RIGHT);
        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);        // sets border width to 3 units
        cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell6.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.savingsStatement(accountNumber, sdate, edate);
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;

                while (z < realData.size()) {
                    if (z == 0) {
                        PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                        cellx1.setFixedHeight(40.2f);
//                        cellx1.disableBorderSide(LEFT);
//                        cellx1.disableBorderSide(TOP);
//                        cellx1.disableBorderSide(RIGHT);
                        cellx1.setPadding(.5f);
                        cellx1.setBorderColorBottom(BaseColor.BLACK);
//                        cellx1.setBorderColorTop(BaseColor.WHITE);
//                        cellx1.setBorderColorLeft(BaseColor.WHITE);
//                        cellx1.setBorderColorRight(BaseColor.WHITE);
                        cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                        table.addCell(cellx1);
                    }
                    if (z == 1) {

                        PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(5).toString(), font1));
//                        cellx2.disableBorderSide(LEFT);
//                        cellx2.disableBorderSide(TOP);
//                        cellx2.disableBorderSide(RIGHT);
                        cellx2.setBorderColorBottom(BaseColor.BLACK);
//                        cellx2.setBorderColorTop(BaseColor.WHITE);
//                        cellx2.setBorderColorLeft(BaseColor.WHITE);
//                        cellx2.setBorderColorRight(BaseColor.WHITE);
                        cellx2.setFixedHeight(40.2f);
                        cellx2.setPadding(.5f);
                        cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                        table.addCell(cellx2);

                    }
                     if (z == 2) {

                        PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
//                        cellx2.disableBorderSide(LEFT);
//                        cellx2.disableBorderSide(TOP);
//                        cellx2.disableBorderSide(RIGHT);
                        cellx2.setBorderColorBottom(BaseColor.BLACK);
//                        cellx2.setBorderColorTop(BaseColor.WHITE);
//                        cellx2.setBorderColorLeft(BaseColor.WHITE);
//                        cellx2.setBorderColorRight(BaseColor.WHITE);
                        cellx2.setFixedHeight(40.2f);
                        cellx2.setPadding(.5f);
                        cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                        table.addCell(cellx2);

                    }
                    if (z == 3) {

                        PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font1));
                        cellx3.setFixedHeight(20.2f);
                        cellx3.setPadding(.5f);
//                        cellx3.disableBorderSide(LEFT);
//                        cellx3.disableBorderSide(TOP);
//                        cellx3.disableBorderSide(RIGHT);
                        cellx3.setBorderColorBottom(BaseColor.BLACK);
//                        cellx3.setBorderColorTop(BaseColor.WHITE);
//                        cellx3.setBorderColorLeft(BaseColor.WHITE);
//                        cellx3.setBorderColorRight(BaseColor.WHITE);
                        cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx3);
                    }
                    if (z == 4) {

                        PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                        cellx4.setFixedHeight(40.2f);
                        cellx4.setPadding(.5f);
//                        cellx4.disableBorderSide(LEFT);
//                        cellx4.disableBorderSide(TOP);
//                        cellx4.disableBorderSide(RIGHT);
                        cellx4.setBorderColorBottom(BaseColor.BLACK);
//                        cellx4.setBorderColorTop(BaseColor.WHITE);
//                        cellx4.setBorderColorLeft(BaseColor.WHITE);
//                        cellx4.setBorderColorRight(BaseColor.WHITE);
                        cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx4);
                    }
                    if (z == 5) {

                        PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                        cellx5.setFixedHeight(40.2f);
                        cellx5.setPadding(.5f);
//                        cellx5.disableBorderSide(LEFT);
//                        cellx5.disableBorderSide(TOP);
//                        cellx5.disableBorderSide(RIGHT);
                        cellx5.setBorderColorBottom(BaseColor.BLACK);
//                        cellx5.setBorderColorTop(BaseColor.WHITE);
//                        cellx5.setBorderColorLeft(BaseColor.WHITE);
//                        cellx5.setBorderColorRight(BaseColor.WHITE);
                        cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx5);
                    }
// if(z==5){
//
//
//    PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()),font2));
//    cellx5.setFixedHeight(20.2f);
//    cellx5.setPadding(.5f);
//    cellx5.disableBorderSide(LEFT);
//    cellx5.disableBorderSide(TOP);
//    cellx5.disableBorderSide(RIGHT);
//    cellx5.setBorderColorBottom(BaseColor.BLACK);
//    cellx5.setBorderColorTop(BaseColor.WHITE);
//    cellx5.setBorderColorLeft(BaseColor.WHITE);
//    cellx5.setBorderColorRight(BaseColor.WHITE);
//    cellx5.setVerticalAlignment(Element.ALIGN_TOP);
//      cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
//    table.addCell(cellx5);
//    }
                    z++;
                }

//    }
                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
//     table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
        }
        return table;
    }

    public boolean individualReturnSharesDaily(String accountNumber, String startDate, String endDate, String fileName, Component c) {

        boolean individualROISharesDaily = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyindividualROISharesDailypdf(accountNumber, startDate, endDate, fileName, c);
                individualROISharesDaily = true;
            }

        } else {
            createActuallyindividualROISharesDailypdf(accountNumber, startDate, endDate, fileName, c);
            individualROISharesDaily = true;
        }

        return individualROISharesDaily;
    }

    private void createActuallyindividualROISharesDailypdf(String accountNumber, String startDate, String endDate, String fileName, Component c) {
        try {

            fios.stringFileWriter(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "accountNumber.txt"), accountNumber);

            Paragraph headerz = createHeading("individual_daily_shares_roi", startDate, endDate);
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);

            document.addAuthor("Bazirake Augustine Googo");

            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));

            PdfPTable body = createActuallyindividualROISharesTable(accountNumber, startDate, endDate, c);

            Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);

            ct.setIndentationLeft(50f);

            ct.setIndentationRight(200f);

            document.add(ct);

            ct.setAlignment(Element.ALIGN_CENTER);

            Image image1 = Image.getInstance("strawberry3.jpg");

            image1.setAbsolutePosition(49f, 1080f);

            image1.scaleAbsolute(43f, 43f);

            document.add(image1);

            document.add(headerz);

            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(250f);
            currency.setIndentationRight(80f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createActuallyindividualROISharesTable(String accountNumber, String sdate, String edate, Component c) {

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        float[] columnWidths = {14f, 25f, 30f, 50f, 30f, 30f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("TxnDate", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("TxnPeriod", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("AccountName", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("LedgerBalance", font2));

        PdfPCell cell6 = new PdfPCell(new Paragraph("InterestComputed", font2));
        PdfPCell cell7 = new PdfPCell(new Paragraph("InterestComRunningBal", font2));

        cell1.setBackgroundColor(BaseColor.CYAN);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.CYAN);
        cell3.setBackgroundColor(BaseColor.CYAN);
        cell4.setBackgroundColor(BaseColor.CYAN);
        cell5.setBackgroundColor(BaseColor.CYAN);
        cell6.setBackgroundColor(BaseColor.CYAN);
        cell7.setBackgroundColor(BaseColor.CYAN);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);
        cell7.setBorderWidth(2f);

        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);
        cell5.disableBorderSide(LEFT);
        cell6.disableBorderSide(LEFT);
        cell7.disableBorderSide(LEFT);

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);
        cell7.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);
        cell7.disableBorderSide(RIGHT);

        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);        // sets border width to 3 units
        cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell7.setHorizontalAlignment(Element.ALIGN_RIGHT);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.sharesIndivROIDaily(accountNumber, sdate, edate, c);
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
//    if(n==dataBase.size()-1){
//   if (z == 0) {
//                        PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
//                        cellx1.setFixedHeight(20.2f);
//                        cellx1.disableBorderSide(LEFT);
//                        cellx1.disableBorderSide(TOP);
//                        cellx1.disableBorderSide(RIGHT);
//                        cellx1.setPadding(.5f);
//                        cellx1.setBorderColorBottom(BaseColor.BLACK);
//                        cellx1.setBorderColorTop(BaseColor.WHITE);
//                        cellx1.setBorderColorLeft(BaseColor.WHITE);
//                        cellx1.setBorderColorRight(BaseColor.WHITE);
//                        cellx1.setVerticalAlignment(Element.ALIGN_TOP);
//                        table.addCell(cellx1);
//                    }
//                    if (z == 1) {
//
//                        PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
//                        cellx2.disableBorderSide(LEFT);
//                        cellx2.disableBorderSide(TOP);
//                        cellx2.disableBorderSide(RIGHT);
//                        cellx2.setBorderColorBottom(BaseColor.BLACK);
//                        cellx2.setBorderColorTop(BaseColor.WHITE);
//                        cellx2.setBorderColorLeft(BaseColor.WHITE);
//                        cellx2.setBorderColorRight(BaseColor.WHITE);
//                        cellx2.setFixedHeight(20.2f);
//                        cellx2.setPadding(.5f);
//                        cellx2.setVerticalAlignment(Element.ALIGN_TOP);
//                        table.addCell(cellx2);
//
//                    }
//                    if (z == 2) {
//
//                        PdfPCell cellx3 = new PdfPCell(new Paragraph(realData.get(2).toString(), font1));
//                        cellx3.setFixedHeight(20.2f);
//                        cellx3.setPadding(.5f);
//                        cellx3.disableBorderSide(LEFT);
//                        cellx3.disableBorderSide(TOP);
//                        cellx3.disableBorderSide(RIGHT);
//                        cellx3.setBorderColorBottom(BaseColor.BLACK);
//                        cellx3.setBorderColorTop(BaseColor.WHITE);
//                        cellx3.setBorderColorLeft(BaseColor.WHITE);
//                        cellx3.setBorderColorRight(BaseColor.WHITE);
//                        cellx3.setVerticalAlignment(Element.ALIGN_TOP);
//                        cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                        table.addCell(cellx3);
//                    }
//                    if (z == 3) {
//
//                        PdfPCell cellx4 = new PdfPCell(new Paragraph(realData.get(3).toString(), font2));
//                        cellx4.setFixedHeight(20.2f);
//                        cellx4.setPadding(.5f);
//                        cellx4.disableBorderSide(LEFT);
//                        cellx4.disableBorderSide(TOP);
//                        cellx4.disableBorderSide(RIGHT);
//                        cellx4.setBorderColorBottom(BaseColor.BLACK);
//                        cellx4.setBorderColorTop(BaseColor.WHITE);
//                        cellx4.setBorderColorLeft(BaseColor.WHITE);
//                        cellx4.setBorderColorRight(BaseColor.WHITE);
//                        cellx4.setVerticalAlignment(Element.ALIGN_TOP);
//                        cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                        table.addCell(cellx4);
//                    }
//                    if (z == 4) {
//
//                        PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
//                        cellx5.setFixedHeight(20.2f);
//                        cellx5.setPadding(.5f);
//                        cellx5.disableBorderSide(LEFT);
//                        cellx5.disableBorderSide(TOP);
//                        cellx5.disableBorderSide(RIGHT);
//                        cellx5.setBorderColorBottom(BaseColor.BLACK);
//                        cellx5.setBorderColorTop(BaseColor.WHITE);
//                        cellx5.setBorderColorLeft(BaseColor.WHITE);
//                        cellx5.setBorderColorRight(BaseColor.WHITE);
//                        cellx5.setVerticalAlignment(Element.ALIGN_TOP);
//                        cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                        table.addCell(cellx5);
//                    }
// if(z==5){
//
//
//    PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()),font2));
//    cellx6.setFixedHeight(20.2f);
//    cellx6.setPadding(.5f);
//    cellx6.disableBorderSide(LEFT);
//    cellx6.disableBorderSide(TOP);
//    cellx6.disableBorderSide(RIGHT);
//    cellx6.setBorderColorBottom(BaseColor.BLACK);
//    cellx6.setBorderColorTop(BaseColor.WHITE);
//    cellx6.setBorderColorLeft(BaseColor.WHITE);
//    cellx6.setBorderColorRight(BaseColor.WHITE);
//    cellx6.setVerticalAlignment(Element.ALIGN_TOP);
//      cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
//    table.addCell(cellx6);
//    }
//  if (z == 6) {
//
//                        PdfPCell cellx7 = new PdfPCell(new Paragraph(realData.get(6).toString(), font2));
//                        cellx7.setFixedHeight(20.2f);
//                        cellx7.setPadding(.5f);
//                        cellx7.disableBorderSide(LEFT);
//                        cellx7.disableBorderSide(TOP);
//                        cellx7.disableBorderSide(RIGHT);
//                        cellx7.setBorderColorBottom(BaseColor.BLACK);
//                        cellx7.setBorderColorTop(BaseColor.WHITE);
//                        cellx7.setBorderColorLeft(BaseColor.WHITE);
//                        cellx7.setBorderColorRight(BaseColor.WHITE);
//                        cellx7.setVerticalAlignment(Element.ALIGN_TOP);
//                        cellx7.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                        table.addCell(cellx7);
//                    }
//                    z++;
//                
//
//    }else{
                while (z < realData.size()) {
                    if (z == 0) {
                        PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                        cellx1.setFixedHeight(20.2f);
                        cellx1.disableBorderSide(LEFT);
                        cellx1.disableBorderSide(TOP);
                        cellx1.disableBorderSide(RIGHT);
                        cellx1.setPadding(.5f);
                        cellx1.setBorderColorBottom(BaseColor.BLACK);
                        cellx1.setBorderColorTop(BaseColor.WHITE);
                        cellx1.setBorderColorLeft(BaseColor.WHITE);
                        cellx1.setBorderColorRight(BaseColor.WHITE);
                        cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                        table.addCell(cellx1);
                    }
                    if (z == 1) {

                        PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                        cellx2.disableBorderSide(LEFT);
                        cellx2.disableBorderSide(TOP);
                        cellx2.disableBorderSide(RIGHT);
                        cellx2.setBorderColorBottom(BaseColor.BLACK);
                        cellx2.setBorderColorTop(BaseColor.WHITE);
                        cellx2.setBorderColorLeft(BaseColor.WHITE);
                        cellx2.setBorderColorRight(BaseColor.WHITE);
                        cellx2.setFixedHeight(20.2f);
                        cellx2.setPadding(.5f);
                        cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                        table.addCell(cellx2);

                    }
                    if (z == 2) {

                        PdfPCell cellx3 = new PdfPCell(new Paragraph(realData.get(2).toString(), font1));
                        cellx3.setFixedHeight(20.2f);
                        cellx3.setPadding(.5f);
                        cellx3.disableBorderSide(LEFT);
                        cellx3.disableBorderSide(TOP);
                        cellx3.disableBorderSide(RIGHT);
                        cellx3.setBorderColorBottom(BaseColor.BLACK);
                        cellx3.setBorderColorTop(BaseColor.WHITE);
                        cellx3.setBorderColorLeft(BaseColor.WHITE);
                        cellx3.setBorderColorRight(BaseColor.WHITE);
                        cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx3);
                    }
                    if (z == 3) {

                        PdfPCell cellx4 = new PdfPCell(new Paragraph(realData.get(3).toString(), font2));
                        cellx4.setFixedHeight(20.2f);
                        cellx4.setPadding(.5f);
                        cellx4.disableBorderSide(LEFT);
                        cellx4.disableBorderSide(TOP);
                        cellx4.disableBorderSide(RIGHT);
                        cellx4.setBorderColorBottom(BaseColor.BLACK);
                        cellx4.setBorderColorTop(BaseColor.WHITE);
                        cellx4.setBorderColorLeft(BaseColor.WHITE);
                        cellx4.setBorderColorRight(BaseColor.WHITE);
                        cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx4);
                    }
                    if (z == 4) {

                        PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                        cellx5.setFixedHeight(20.2f);
                        cellx5.setPadding(.5f);
                        cellx5.disableBorderSide(LEFT);
                        cellx5.disableBorderSide(TOP);
                        cellx5.disableBorderSide(RIGHT);
                        cellx5.setBorderColorBottom(BaseColor.BLACK);
                        cellx5.setBorderColorTop(BaseColor.WHITE);
                        cellx5.setBorderColorLeft(BaseColor.WHITE);
                        cellx5.setBorderColorRight(BaseColor.WHITE);
                        cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx5);
                    }
                    if (z == 5) {

                        PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font2));
                        cellx6.setFixedHeight(20.2f);
                        cellx6.setPadding(.5f);
                        cellx6.disableBorderSide(LEFT);
                        cellx6.disableBorderSide(TOP);
                        cellx6.disableBorderSide(RIGHT);
                        cellx6.setBorderColorBottom(BaseColor.BLACK);
                        cellx6.setBorderColorTop(BaseColor.WHITE);
                        cellx6.setBorderColorLeft(BaseColor.WHITE);
                        cellx6.setBorderColorRight(BaseColor.WHITE);
                        cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx6);
                    }
                    if (z == 6) {

                        PdfPCell cellx7 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(6).toString()), font2));
                        cellx7.setFixedHeight(20.2f);
                        cellx7.setPadding(.5f);
                        cellx7.disableBorderSide(LEFT);
                        cellx7.disableBorderSide(TOP);
                        cellx7.disableBorderSide(RIGHT);
                        cellx7.setBorderColorBottom(BaseColor.BLACK);
                        cellx7.setBorderColorTop(BaseColor.WHITE);
                        cellx7.setBorderColorLeft(BaseColor.WHITE);
                        cellx7.setBorderColorRight(BaseColor.WHITE);
                        cellx7.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx7.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx7);
                    }
                    z++;
                }

//    }
                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
        }
        return table;
    }

    public boolean individualReturnSavingsDaily(String accountNumber, String startDate, String endDate, String fileName) {

        boolean individualROISavingsDaily = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyindividualROISavingsDailypdf(accountNumber, startDate, endDate, fileName);
                individualROISavingsDaily = true;
            }

        } else {
            createActuallyindividualROISavingsDailypdf(accountNumber, startDate, endDate, fileName);
            individualROISavingsDaily = true;
        }

        return individualROISavingsDaily;
    }

    private void createActuallyindividualROISavingsDailypdf(String accountNumber, String startDate, String endDate, String fileName) {
        try {

            fios.stringFileWriter(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "accountNumber.txt"), accountNumber);

            Paragraph headerz = createHeading("individual_daily_shares_roi", startDate, endDate);
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);

            document.addAuthor("Bazirake Augustine Googo");

            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));

            PdfPTable body = createActuallyindividualROISavingsTable(accountNumber, startDate, endDate);

            Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);

            ct.setIndentationLeft(50f);

            ct.setIndentationRight(200f);

            document.add(ct);

            ct.setAlignment(Element.ALIGN_CENTER);

            Image image1 = Image.getInstance("strawberry3.jpg");

            image1.setAbsolutePosition(49f, 1080f);

            image1.scaleAbsolute(43f, 43f);

            document.add(image1);

            document.add(headerz);

            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(250f);
            currency.setIndentationRight(80f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createActuallyindividualROISavingsTable(String accountNumber, String sdate, String edate) {

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        float[] columnWidths = {10f, 35f, 30f, 50f, 30f, 25f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("TxnDate", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("TxnPeriod", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("AccountName", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("LedgerBalance", font2));

        PdfPCell cell6 = new PdfPCell(new Paragraph("InterestComputed", font2));
        PdfPCell cell7 = new PdfPCell(new Paragraph("InterestComRunningBal", font2));

        cell1.setBackgroundColor(BaseColor.CYAN);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.CYAN);
        cell3.setBackgroundColor(BaseColor.CYAN);
        cell4.setBackgroundColor(BaseColor.CYAN);
        cell5.setBackgroundColor(BaseColor.CYAN);
        cell6.setBackgroundColor(BaseColor.CYAN);
        cell7.setBackgroundColor(BaseColor.CYAN);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);
        cell7.setBorderWidth(2f);

        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);
        cell5.disableBorderSide(LEFT);
        cell6.disableBorderSide(LEFT);
        cell7.disableBorderSide(LEFT);

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);
        cell7.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);
        cell7.disableBorderSide(RIGHT);

        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);        // sets border width to 3 units
        cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell7.setHorizontalAlignment(Element.ALIGN_RIGHT);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.savingsIndivROIDaily(accountNumber, sdate, edate);
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
//    if(n==dataBase.size()-1){
//    while(z<realData.size()){
//    if(z==0){
//    PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(),font2));
//    cellx1.setFixedHeight(20.2f);
//    cellx1.disableBorderSide(LEFT);
//    cellx1.disableBorderSide(TOP);
//    cellx1.disableBorderSide(RIGHT);
//    cellx1.setPadding(.5f);
//    cellx1.setBorderColorBottom(BaseColor.BLACK);
//    cellx1.setBorderColorTop(BaseColor.WHITE);
//    cellx1.setBorderColorLeft(BaseColor.WHITE);
//    cellx1.setBorderColorRight(BaseColor.WHITE);
//    cellx1.setVerticalAlignment(Element.ALIGN_TOP);
//    table.addCell(cellx1);
//    }
//    if(z==1){
//
//    PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(1).toString(),font2));
//    cellx2.disableBorderSide(LEFT);
//    cellx2.disableBorderSide(TOP);
//    cellx2.disableBorderSide(RIGHT);
//    cellx2.setBorderColorBottom(BaseColor.BLACK);
//    cellx2.setBorderColorTop(BaseColor.WHITE);
//    cellx2.setBorderColorLeft(BaseColor.WHITE);
//    cellx2.setBorderColorRight(BaseColor.WHITE);
//    cellx2.setFixedHeight(20.2f);
//    cellx2.setPadding(.5f);
//    cellx2.setVerticalAlignment(Element.ALIGN_TOP);
//    table.addCell(cellx2);
//
//    }
//    if(z==2){
//
//
//    PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()),font2));
//    cellx3.setFixedHeight(20.2f);
//    cellx3.setPadding(.5f);
//    cellx3.disableBorderSide(LEFT);
//    cellx3.disableBorderSide(TOP);
//    cellx3.disableBorderSide(RIGHT);
//    cellx3.setBorderColorBottom(BaseColor.BLACK);
//    cellx3.setBorderColorTop(BaseColor.WHITE);
//    cellx3.setBorderColorLeft(BaseColor.WHITE);
//    cellx3.setBorderColorRight(BaseColor.WHITE);
//    cellx3.setVerticalAlignment(Element.ALIGN_TOP);
//      cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
//    table.addCell(cellx3);
//    }
// if(z==3){
//
//
//    PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()),font2));
//    cellx4.setFixedHeight(20.2f);
//    cellx4.setPadding(.5f);
//    cellx4.disableBorderSide(LEFT);
//    cellx4.disableBorderSide(TOP);
//    cellx4.disableBorderSide(RIGHT);
//    cellx4.setBorderColorBottom(BaseColor.BLACK);
//    cellx4.setBorderColorTop(BaseColor.WHITE);
//    cellx4.setBorderColorLeft(BaseColor.WHITE);
//    cellx4.setBorderColorRight(BaseColor.WHITE);
//    cellx4.setVerticalAlignment(Element.ALIGN_TOP);
//     cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
//    table.addCell(cellx4);
//    }
// if(z==4){
//
//
//    PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()),font2));
//    cellx5.setFixedHeight(20.2f);
//    cellx5.setPadding(.5f);
//    cellx5.disableBorderSide(LEFT);
//    cellx5.disableBorderSide(TOP);
//    cellx5.disableBorderSide(RIGHT);
//    cellx5.setBorderColorBottom(BaseColor.BLACK);
//    cellx5.setBorderColorTop(BaseColor.WHITE);
//    cellx5.setBorderColorLeft(BaseColor.WHITE);
//    cellx5.setBorderColorRight(BaseColor.WHITE);
//    cellx5.setVerticalAlignment(Element.ALIGN_TOP);
//         cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
//    table.addCell(cellx5);
//    }
////
////  if(z==5){
////
////
////    PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()),font2));
////    cellx5.setFixedHeight(20.2f);
////    cellx5.setPadding(.5f);
////    cellx5.disableBorderSide(LEFT);
////    cellx5.disableBorderSide(TOP);
////    cellx5.disableBorderSide(RIGHT);
////    cellx5.setBorderColorBottom(BaseColor.BLACK);
////    cellx5.setBorderColorTop(BaseColor.WHITE);
////    cellx5.setBorderColorLeft(BaseColor.WHITE);
////    cellx5.setBorderColorRight(BaseColor.WHITE);
////    cellx5.setVerticalAlignment(Element.ALIGN_TOP);
////         cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
////    table.addCell(cellx5);
////    }
// 
// 
//    z++;   
//    }
//
//    }else{
                while (z < realData.size()) {
                    if (z == 0) {
                        PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                        cellx1.setFixedHeight(20.2f);
                        cellx1.disableBorderSide(LEFT);
                        cellx1.disableBorderSide(TOP);
                        cellx1.disableBorderSide(RIGHT);
                        cellx1.setPadding(.5f);
                        cellx1.setBorderColorBottom(BaseColor.BLACK);
                        cellx1.setBorderColorTop(BaseColor.WHITE);
                        cellx1.setBorderColorLeft(BaseColor.WHITE);
                        cellx1.setBorderColorRight(BaseColor.WHITE);
                        cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                        table.addCell(cellx1);
                    }
                    if (z == 1) {

                        PdfPCell cellx2 = new PdfPCell(new Paragraph(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(realData.get(1).toString()), font1));
                        cellx2.disableBorderSide(LEFT);
                        cellx2.disableBorderSide(TOP);
                        cellx2.disableBorderSide(RIGHT);
                        cellx2.setBorderColorBottom(BaseColor.BLACK);
                        cellx2.setBorderColorTop(BaseColor.WHITE);
                        cellx2.setBorderColorLeft(BaseColor.WHITE);
                        cellx2.setBorderColorRight(BaseColor.WHITE);
                        cellx2.setFixedHeight(20.2f);
                        cellx2.setPadding(.5f);
                        cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                        table.addCell(cellx2);

                    }
                    if (z == 2) {

                        PdfPCell cellx3 = new PdfPCell(new Paragraph(realData.get(2).toString(), font1));
                        cellx3.setFixedHeight(20.2f);
                        cellx3.setPadding(.5f);
                        cellx3.disableBorderSide(LEFT);
                        cellx3.disableBorderSide(TOP);
                        cellx3.disableBorderSide(RIGHT);
                        cellx3.setBorderColorBottom(BaseColor.BLACK);
                        cellx3.setBorderColorTop(BaseColor.WHITE);
                        cellx3.setBorderColorLeft(BaseColor.WHITE);
                        cellx3.setBorderColorRight(BaseColor.WHITE);
                        cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx3);
                    }
                    if (z == 3) {

                        PdfPCell cellx4 = new PdfPCell(new Paragraph(realData.get(3).toString(), font2));
                        cellx4.setFixedHeight(20.2f);
                        cellx4.setPadding(.5f);
                        cellx4.disableBorderSide(LEFT);
                        cellx4.disableBorderSide(TOP);
                        cellx4.disableBorderSide(RIGHT);
                        cellx4.setBorderColorBottom(BaseColor.BLACK);
                        cellx4.setBorderColorTop(BaseColor.WHITE);
                        cellx4.setBorderColorLeft(BaseColor.WHITE);
                        cellx4.setBorderColorRight(BaseColor.WHITE);
                        cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx4);
                    }
                    if (z == 4) {

                        PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                        cellx5.setFixedHeight(20.2f);
                        cellx5.setPadding(.5f);
                        cellx5.disableBorderSide(LEFT);
                        cellx5.disableBorderSide(TOP);
                        cellx5.disableBorderSide(RIGHT);
                        cellx5.setBorderColorBottom(BaseColor.BLACK);
                        cellx5.setBorderColorTop(BaseColor.WHITE);
                        cellx5.setBorderColorLeft(BaseColor.WHITE);
                        cellx5.setBorderColorRight(BaseColor.WHITE);
                        cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx5);
                    }
                    if (z == 5) {

                        PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font2));
                        cellx6.setFixedHeight(20.2f);
                        cellx6.setPadding(.5f);
                        cellx6.disableBorderSide(LEFT);
                        cellx6.disableBorderSide(TOP);
                        cellx6.disableBorderSide(RIGHT);
                        cellx6.setBorderColorBottom(BaseColor.BLACK);
                        cellx6.setBorderColorTop(BaseColor.WHITE);
                        cellx6.setBorderColorLeft(BaseColor.WHITE);
                        cellx6.setBorderColorRight(BaseColor.WHITE);
                        cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx6);
                    }
                    if (z == 6) {

                        PdfPCell cellx7 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(6).toString()), font2));
                        cellx7.setFixedHeight(20.2f);
                        cellx7.setPadding(.5f);
                        cellx7.disableBorderSide(LEFT);
                        cellx7.disableBorderSide(TOP);
                        cellx7.disableBorderSide(RIGHT);
                        cellx7.setBorderColorBottom(BaseColor.BLACK);
                        cellx7.setBorderColorTop(BaseColor.WHITE);
                        cellx7.setBorderColorLeft(BaseColor.WHITE);
                        cellx7.setBorderColorRight(BaseColor.WHITE);
                        cellx7.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx7.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx7);
                    }
                    z++;
                }

//    }
                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
        }
        return table;
    }

    public boolean groupReturnSavingsDaily(String startDate, String endDate, String fileName, Component c, JTextField ff) {

        boolean groupROISavingsDaily = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyGroupROISavingsDailypdf(startDate, endDate, fileName, c, ff);
                groupROISavingsDaily = true;
            }

        } else {
            createActuallyGroupROISavingsDailypdf(startDate, endDate, fileName, c, ff);
            groupROISavingsDaily = true;
        }

        return groupROISavingsDaily;
    }

    private void createActuallyGroupROISavingsDailypdf(String startDate, String endDate, String fileName, Component c, JTextField g) {
        try {

            Paragraph headerz = createHeading("individual_daily_savings_roi", startDate, endDate);
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);

            document.addAuthor("Bazirake Augustine Googo");

            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));

            PdfPTable body = createActuallyGroupROISavingsTable(startDate, endDate, c, g);

            Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);

            ct.setIndentationLeft(50f);

            ct.setIndentationRight(200f);

            document.add(ct);

            ct.setAlignment(Element.ALIGN_CENTER);

            Image image1 = Image.getInstance("strawberry3.jpg");

            image1.setAbsolutePosition(49f, 1080f);

            image1.scaleAbsolute(43f, 43f);

            document.add(image1);

            document.add(headerz);

            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(250f);
            currency.setIndentationRight(80f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createActuallyGroupROISavingsTable(String sdate, String edate, Component c, JTextField kk) {

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        float[] columnWidths = {14f, 30f, 450f, 40f, 30f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("TxnPeriod", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("AccountName", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("AccountNum", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("LedgerBalance", font2));

        PdfPCell cell6 = new PdfPCell(new Paragraph("InterestComputed", font2));

        cell1.setBackgroundColor(BaseColor.CYAN);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.CYAN);
        cell3.setBackgroundColor(BaseColor.CYAN);
        cell4.setBackgroundColor(BaseColor.CYAN);
        cell5.setBackgroundColor(BaseColor.CYAN);
        cell6.setBackgroundColor(BaseColor.CYAN);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);

        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);
        cell5.disableBorderSide(LEFT);
        cell6.disableBorderSide(LEFT);

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);

        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);        // sets border width to 3 units
        cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.savingsIndivROIGrouped(sdate, edate, c, kk);
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
//    if(n==dataBase.size()-1){
//    while(z<realData.size()){
//    if(z==0){
//    PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(),font2));
//    cellx1.setFixedHeight(20.2f);
//    cellx1.disableBorderSide(LEFT);
//    cellx1.disableBorderSide(TOP);
//    cellx1.disableBorderSide(RIGHT);
//    cellx1.setPadding(.5f);
//    cellx1.setBorderColorBottom(BaseColor.BLACK);
//    cellx1.setBorderColorTop(BaseColor.WHITE);
//    cellx1.setBorderColorLeft(BaseColor.WHITE);
//    cellx1.setBorderColorRight(BaseColor.WHITE);
//    cellx1.setVerticalAlignment(Element.ALIGN_TOP);
//    table.addCell(cellx1);
//    }
//    if(z==1){
//
//    PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(1).toString(),font2));
//    cellx2.disableBorderSide(LEFT);
//    cellx2.disableBorderSide(TOP);
//    cellx2.disableBorderSide(RIGHT);
//    cellx2.setBorderColorBottom(BaseColor.BLACK);
//    cellx2.setBorderColorTop(BaseColor.WHITE);
//    cellx2.setBorderColorLeft(BaseColor.WHITE);
//    cellx2.setBorderColorRight(BaseColor.WHITE);
//    cellx2.setFixedHeight(20.2f);
//    cellx2.setPadding(.5f);
//    cellx2.setVerticalAlignment(Element.ALIGN_TOP);
//    table.addCell(cellx2);
//
//    }
//    if(z==2){
//
//
//    PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()),font2));
//    cellx3.setFixedHeight(20.2f);
//    cellx3.setPadding(.5f);
//    cellx3.disableBorderSide(LEFT);
//    cellx3.disableBorderSide(TOP);
//    cellx3.disableBorderSide(RIGHT);
//    cellx3.setBorderColorBottom(BaseColor.BLACK);
//    cellx3.setBorderColorTop(BaseColor.WHITE);
//    cellx3.setBorderColorLeft(BaseColor.WHITE);
//    cellx3.setBorderColorRight(BaseColor.WHITE);
//    cellx3.setVerticalAlignment(Element.ALIGN_TOP);
//      cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
//    table.addCell(cellx3);
//    }
// if(z==3){
//
//
//    PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()),font2));
//    cellx4.setFixedHeight(20.2f);
//    cellx4.setPadding(.5f);
//    cellx4.disableBorderSide(LEFT);
//    cellx4.disableBorderSide(TOP);
//    cellx4.disableBorderSide(RIGHT);
//    cellx4.setBorderColorBottom(BaseColor.BLACK);
//    cellx4.setBorderColorTop(BaseColor.WHITE);
//    cellx4.setBorderColorLeft(BaseColor.WHITE);
//    cellx4.setBorderColorRight(BaseColor.WHITE);
//    cellx4.setVerticalAlignment(Element.ALIGN_TOP);
//     cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
//    table.addCell(cellx4);
//    }
// if(z==4){
//
//
//    PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()),font2));
//    cellx5.setFixedHeight(20.2f);
//    cellx5.setPadding(.5f);
//    cellx5.disableBorderSide(LEFT);
//    cellx5.disableBorderSide(TOP);
//    cellx5.disableBorderSide(RIGHT);
//    cellx5.setBorderColorBottom(BaseColor.BLACK);
//    cellx5.setBorderColorTop(BaseColor.WHITE);
//    cellx5.setBorderColorLeft(BaseColor.WHITE);
//    cellx5.setBorderColorRight(BaseColor.WHITE);
//    cellx5.setVerticalAlignment(Element.ALIGN_TOP);
//         cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
//    table.addCell(cellx5);
//    }
////
////  if(z==5){
////
////
////    PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()),font2));
////    cellx5.setFixedHeight(20.2f);
////    cellx5.setPadding(.5f);
////    cellx5.disableBorderSide(LEFT);
////    cellx5.disableBorderSide(TOP);
////    cellx5.disableBorderSide(RIGHT);
////    cellx5.setBorderColorBottom(BaseColor.BLACK);
////    cellx5.setBorderColorTop(BaseColor.WHITE);
////    cellx5.setBorderColorLeft(BaseColor.WHITE);
////    cellx5.setBorderColorRight(BaseColor.WHITE);
////    cellx5.setVerticalAlignment(Element.ALIGN_TOP);
////         cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
////    table.addCell(cellx5);
////    }
// 
// 
//    z++;   
//    }
//
//    }else{
                while (z < realData.size()) {
                    if (z == 0) {
                        PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                        cellx1.setFixedHeight(20.2f);
                        cellx1.disableBorderSide(LEFT);
                        cellx1.disableBorderSide(TOP);
                        cellx1.disableBorderSide(RIGHT);
                        cellx1.setPadding(.5f);
                        cellx1.setBorderColorBottom(BaseColor.BLACK);
                        cellx1.setBorderColorTop(BaseColor.WHITE);
                        cellx1.setBorderColorLeft(BaseColor.WHITE);
                        cellx1.setBorderColorRight(BaseColor.WHITE);
                        cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                        table.addCell(cellx1);
                    }
                    if (z == 1) {

                        PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                        cellx2.disableBorderSide(LEFT);
                        cellx2.disableBorderSide(TOP);
                        cellx2.disableBorderSide(RIGHT);
                        cellx2.setBorderColorBottom(BaseColor.BLACK);
                        cellx2.setBorderColorTop(BaseColor.WHITE);
                        cellx2.setBorderColorLeft(BaseColor.WHITE);
                        cellx2.setBorderColorRight(BaseColor.WHITE);
                        cellx2.setFixedHeight(20.2f);
                        cellx2.setPadding(.5f);
                        cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                        table.addCell(cellx2);

                    }
                    if (z == 2) {

                        PdfPCell cellx3 = new PdfPCell(new Paragraph(realData.get(2).toString(), font1));
                        cellx3.setFixedHeight(20.2f);
                        cellx3.setPadding(.5f);
                        cellx3.disableBorderSide(LEFT);
                        cellx3.disableBorderSide(TOP);
                        cellx3.disableBorderSide(RIGHT);
                        cellx3.setBorderColorBottom(BaseColor.BLACK);
                        cellx3.setBorderColorTop(BaseColor.WHITE);
                        cellx3.setBorderColorLeft(BaseColor.WHITE);
                        cellx3.setBorderColorRight(BaseColor.WHITE);
                        cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx3);
                    }
                    if (z == 3) {

                        PdfPCell cellx4 = new PdfPCell(new Paragraph(realData.get(3).toString(), font2));
                        cellx4.setFixedHeight(20.2f);
                        cellx4.setPadding(.5f);
                        cellx4.disableBorderSide(LEFT);
                        cellx4.disableBorderSide(TOP);
                        cellx4.disableBorderSide(RIGHT);
                        cellx4.setBorderColorBottom(BaseColor.BLACK);
                        cellx4.setBorderColorTop(BaseColor.WHITE);
                        cellx4.setBorderColorLeft(BaseColor.WHITE);
                        cellx4.setBorderColorRight(BaseColor.WHITE);
                        cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx4);
                    }
                    if (z == 4) {

                        PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                        cellx5.setFixedHeight(20.2f);
                        cellx5.setPadding(.5f);
                        cellx5.disableBorderSide(LEFT);
                        cellx5.disableBorderSide(TOP);
                        cellx5.disableBorderSide(RIGHT);
                        cellx5.setBorderColorBottom(BaseColor.BLACK);
                        cellx5.setBorderColorTop(BaseColor.WHITE);
                        cellx5.setBorderColorLeft(BaseColor.WHITE);
                        cellx5.setBorderColorRight(BaseColor.WHITE);
                        cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx5);
                    }
                    if (z == 5) {

                        PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font2));
                        cellx6.setFixedHeight(20.2f);
                        cellx6.setPadding(.5f);
                        cellx6.disableBorderSide(LEFT);
                        cellx6.disableBorderSide(TOP);
                        cellx6.disableBorderSide(RIGHT);
                        cellx6.setBorderColorBottom(BaseColor.BLACK);
                        cellx6.setBorderColorTop(BaseColor.WHITE);
                        cellx6.setBorderColorLeft(BaseColor.WHITE);
                        cellx6.setBorderColorRight(BaseColor.WHITE);
                        cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx6);
                    }

                    z++;
                }

//    }
                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
        }
        return table;
    }

    public boolean groupReturnSharesDaily(String startDate, String endDate, String fileName, Component c) {

        boolean groupROISharesDaily = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyGroupROISharesDailypdf(startDate, endDate, fileName, c);
                groupROISharesDaily = true;
            }

        } else {
            createActuallyGroupROISharesDailypdf(startDate, endDate, fileName, c);
            groupROISharesDaily = true;
        }

        return groupROISharesDaily;
    }

    private void createActuallyGroupROISharesDailypdf(String startDate, String endDate, String fileName, Component c) {
        try {

            Paragraph headerz = createHeading("Group_daily_shares_roi", startDate, endDate);
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);

            document.addAuthor("Bazirake Augustine Googo");

            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));

            PdfPTable body = createActuallyGroupROISharesTable(startDate, endDate, c);

            Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);

            ct.setIndentationLeft(50f);

            ct.setIndentationRight(200f);

            document.add(ct);

            ct.setAlignment(Element.ALIGN_CENTER);

            Image image1 = Image.getInstance("strawberry3.jpg");

            image1.setAbsolutePosition(49f, 1080f);

            image1.scaleAbsolute(43f, 43f);

            document.add(image1);

            document.add(headerz);

            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(250f);
            currency.setIndentationRight(80f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createActuallyGroupROISharesTable(String sdate, String edate, Component c) {

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        float[] columnWidths = {14f, 25f, 50f, 40f, 30f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("TxnYear", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("AccountName", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("AccountNum", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("LedgerBalance", font2));

        PdfPCell cell6 = new PdfPCell(new Paragraph("InterestComputed", font2));

        cell1.setBackgroundColor(BaseColor.CYAN);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.CYAN);
        cell3.setBackgroundColor(BaseColor.CYAN);
        cell4.setBackgroundColor(BaseColor.CYAN);
        cell5.setBackgroundColor(BaseColor.CYAN);
        cell6.setBackgroundColor(BaseColor.CYAN);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);

        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);
        cell5.disableBorderSide(LEFT);

        cell6.disableBorderSide(LEFT);
        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);

        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);        // sets border width to 3 units
        cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);

        table.addCell(cell6);
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.sharesIndivROIGrouped(sdate, edate, c);
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
//    if(n==dataBase.size()-1){
//    while(z<realData.size()){
//    if(z==0){
//    PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(),font2));
//    cellx1.setFixedHeight(20.2f);
//    cellx1.disableBorderSide(LEFT);
//    cellx1.disableBorderSide(TOP);
//    cellx1.disableBorderSide(RIGHT);
//    cellx1.setPadding(.5f);
//    cellx1.setBorderColorBottom(BaseColor.BLACK);
//    cellx1.setBorderColorTop(BaseColor.WHITE);
//    cellx1.setBorderColorLeft(BaseColor.WHITE);
//    cellx1.setBorderColorRight(BaseColor.WHITE);
//    cellx1.setVerticalAlignment(Element.ALIGN_TOP);
//    table.addCell(cellx1);
//    }
//    if(z==1){
//
//    PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(1).toString(),font2));
//    cellx2.disableBorderSide(LEFT);
//    cellx2.disableBorderSide(TOP);
//    cellx2.disableBorderSide(RIGHT);
//    cellx2.setBorderColorBottom(BaseColor.BLACK);
//    cellx2.setBorderColorTop(BaseColor.WHITE);
//    cellx2.setBorderColorLeft(BaseColor.WHITE);
//    cellx2.setBorderColorRight(BaseColor.WHITE);
//    cellx2.setFixedHeight(20.2f);
//    cellx2.setPadding(.5f);
//    cellx2.setVerticalAlignment(Element.ALIGN_TOP);
//    table.addCell(cellx2);
//
//    }
//    if(z==2){
//
//
//    PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()),font2));
//    cellx3.setFixedHeight(20.2f);
//    cellx3.setPadding(.5f);
//    cellx3.disableBorderSide(LEFT);
//    cellx3.disableBorderSide(TOP);
//    cellx3.disableBorderSide(RIGHT);
//    cellx3.setBorderColorBottom(BaseColor.BLACK);
//    cellx3.setBorderColorTop(BaseColor.WHITE);
//    cellx3.setBorderColorLeft(BaseColor.WHITE);
//    cellx3.setBorderColorRight(BaseColor.WHITE);
//    cellx3.setVerticalAlignment(Element.ALIGN_TOP);
//      cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
//    table.addCell(cellx3);
//    }
// if(z==3){
//
//
//    PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()),font2));
//    cellx4.setFixedHeight(20.2f);
//    cellx4.setPadding(.5f);
//    cellx4.disableBorderSide(LEFT);
//    cellx4.disableBorderSide(TOP);
//    cellx4.disableBorderSide(RIGHT);
//    cellx4.setBorderColorBottom(BaseColor.BLACK);
//    cellx4.setBorderColorTop(BaseColor.WHITE);
//    cellx4.setBorderColorLeft(BaseColor.WHITE);
//    cellx4.setBorderColorRight(BaseColor.WHITE);
//    cellx4.setVerticalAlignment(Element.ALIGN_TOP);
//     cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
//    table.addCell(cellx4);
//    }
// if(z==4){
//
//
//    PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()),font2));
//    cellx5.setFixedHeight(20.2f);
//    cellx5.setPadding(.5f);
//    cellx5.disableBorderSide(LEFT);
//    cellx5.disableBorderSide(TOP);
//    cellx5.disableBorderSide(RIGHT);
//    cellx5.setBorderColorBottom(BaseColor.BLACK);
//    cellx5.setBorderColorTop(BaseColor.WHITE);
//    cellx5.setBorderColorLeft(BaseColor.WHITE);
//    cellx5.setBorderColorRight(BaseColor.WHITE);
//    cellx5.setVerticalAlignment(Element.ALIGN_TOP);
//         cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
//    table.addCell(cellx5);
//    }
////
////  if(z==5){
////
////
////    PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()),font2));
////    cellx5.setFixedHeight(20.2f);
////    cellx5.setPadding(.5f);
////    cellx5.disableBorderSide(LEFT);
////    cellx5.disableBorderSide(TOP);
////    cellx5.disableBorderSide(RIGHT);
////    cellx5.setBorderColorBottom(BaseColor.BLACK);
////    cellx5.setBorderColorTop(BaseColor.WHITE);
////    cellx5.setBorderColorLeft(BaseColor.WHITE);
////    cellx5.setBorderColorRight(BaseColor.WHITE);
////    cellx5.setVerticalAlignment(Element.ALIGN_TOP);
////         cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
////    table.addCell(cellx5);
////    }
// 
// 
//    z++;   
//    }
//
//    }else{
                while (z < realData.size()) {
                    if (z == 0) {
                        PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                        cellx1.setFixedHeight(20.2f);
                        cellx1.disableBorderSide(LEFT);
                        cellx1.disableBorderSide(TOP);
                        cellx1.disableBorderSide(RIGHT);
                        cellx1.setPadding(.5f);
                        cellx1.setBorderColorBottom(BaseColor.BLACK);
                        cellx1.setBorderColorTop(BaseColor.WHITE);
                        cellx1.setBorderColorLeft(BaseColor.WHITE);
                        cellx1.setBorderColorRight(BaseColor.WHITE);
                        cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                        table.addCell(cellx1);
                    }
                    if (z == 1) {

                        PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                        cellx2.disableBorderSide(LEFT);
                        cellx2.disableBorderSide(TOP);
                        cellx2.disableBorderSide(RIGHT);
                        cellx2.setBorderColorBottom(BaseColor.BLACK);
                        cellx2.setBorderColorTop(BaseColor.WHITE);
                        cellx2.setBorderColorLeft(BaseColor.WHITE);
                        cellx2.setBorderColorRight(BaseColor.WHITE);
                        cellx2.setFixedHeight(20.2f);
                        cellx2.setPadding(.5f);
                        cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                        table.addCell(cellx2);

                    }
                    if (z == 2) {

                        PdfPCell cellx3 = new PdfPCell(new Paragraph(realData.get(2).toString(), font1));
                        cellx3.setFixedHeight(20.2f);
                        cellx3.setPadding(.5f);
                        cellx3.disableBorderSide(LEFT);
                        cellx3.disableBorderSide(TOP);
                        cellx3.disableBorderSide(RIGHT);
                        cellx3.setBorderColorBottom(BaseColor.BLACK);
                        cellx3.setBorderColorTop(BaseColor.WHITE);
                        cellx3.setBorderColorLeft(BaseColor.WHITE);
                        cellx3.setBorderColorRight(BaseColor.WHITE);
                        cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx3);
                    }
                    if (z == 3) {

                        PdfPCell cellx4 = new PdfPCell(new Paragraph(realData.get(3).toString(), font2));
                        cellx4.setFixedHeight(20.2f);
                        cellx4.setPadding(.5f);
                        cellx4.disableBorderSide(LEFT);
                        cellx4.disableBorderSide(TOP);
                        cellx4.disableBorderSide(RIGHT);
                        cellx4.setBorderColorBottom(BaseColor.BLACK);
                        cellx4.setBorderColorTop(BaseColor.WHITE);
                        cellx4.setBorderColorLeft(BaseColor.WHITE);
                        cellx4.setBorderColorRight(BaseColor.WHITE);
                        cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx4);
                    }
                    if (z == 4) {

                        PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                        cellx5.setFixedHeight(20.2f);
                        cellx5.setPadding(.5f);
                        cellx5.disableBorderSide(LEFT);
                        cellx5.disableBorderSide(TOP);
                        cellx5.disableBorderSide(RIGHT);
                        cellx5.setBorderColorBottom(BaseColor.BLACK);
                        cellx5.setBorderColorTop(BaseColor.WHITE);
                        cellx5.setBorderColorLeft(BaseColor.WHITE);
                        cellx5.setBorderColorRight(BaseColor.WHITE);
                        cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx5);
                    }

                    if (z == 5) {

                        PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font2));
                        cellx6.setFixedHeight(20.2f);
                        cellx6.setPadding(.5f);
                        cellx6.disableBorderSide(LEFT);
                        cellx6.disableBorderSide(TOP);
                        cellx6.disableBorderSide(RIGHT);
                        cellx6.setBorderColorBottom(BaseColor.BLACK);
                        cellx6.setBorderColorTop(BaseColor.WHITE);
                        cellx6.setBorderColorLeft(BaseColor.WHITE);
                        cellx6.setBorderColorRight(BaseColor.WHITE);
                        cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx6);
                    }
// if(z==5){
//
//
//    PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()),font2));
//    cellx6.setFixedHeight(20.2f);
//    cellx6.setPadding(.5f);
//    cellx6.disableBorderSide(LEFT);
//    cellx6.disableBorderSide(TOP);
//    cellx6.disableBorderSide(RIGHT);
//    cellx6.setBorderColorBottom(BaseColor.BLACK);
//    cellx6.setBorderColorTop(BaseColor.WHITE);
//    cellx6.setBorderColorLeft(BaseColor.WHITE);
//    cellx6.setBorderColorRight(BaseColor.WHITE);
//    cellx6.setVerticalAlignment(Element.ALIGN_TOP);
//      cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
//    table.addCell(cellx6);
//    }
//  if (z == 6) {
//
//                        PdfPCell cellx7 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(6).toString()), font2));
//                        cellx7.setFixedHeight(20.2f);
//                        cellx7.setPadding(.5f);
//                        cellx7.disableBorderSide(LEFT);
//                        cellx7.disableBorderSide(TOP);
//                        cellx7.disableBorderSide(RIGHT);
//                        cellx7.setBorderColorBottom(BaseColor.BLACK);
//                        cellx7.setBorderColorTop(BaseColor.WHITE);
//                        cellx7.setBorderColorLeft(BaseColor.WHITE);
//                        cellx7.setBorderColorRight(BaseColor.WHITE);
//                        cellx7.setVerticalAlignment(Element.ALIGN_TOP);
//                        cellx7.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                        table.addCell(cellx7);
//                    }
                    z++;
                }

//    }
                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
        }
        return table;
    }

    public boolean listGroupSharesContributions(String startDate, String endDate, String fileName) {

        boolean groupListSharesAvailable = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallylistGroupSharesContributionspdf(startDate, endDate, fileName);
                groupListSharesAvailable = true;
            }

        } else {
            createActuallylistGroupSharesContributionspdf(startDate, endDate, fileName);
            groupListSharesAvailable = true;
        }

        return groupListSharesAvailable;
    }

    private void createActuallylistGroupSharesContributionspdf(String startDate, String endDate, String fileName) {
        try {

//fios.stringFileWriter(fios.createFileName("PMMS_Statements", "PMMS_Statements1","accountNumber.txt"),accountNumber);
            Paragraph headerz = createHeading("listGroup_Shares", startDate, endDate);
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);

            document.addAuthor("Bazirake Augustine Googo");

            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));

            PdfPTable body = createActuallyListGroupSharesTable(startDate, endDate);

            Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);

            ct.setIndentationLeft(50f);

            ct.setIndentationRight(200f);

            document.add(ct);

            ct.setAlignment(Element.ALIGN_CENTER);

            Image image1 = Image.getInstance("strawberry3.jpg");

            image1.setAbsolutePosition(49f, 1080f);

            image1.scaleAbsolute(43f, 43f);

            document.add(image1);

            document.add(headerz);

            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(250f);
            currency.setIndentationRight(80f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createActuallyListGroupSharesTable(String sdate, String edate) {

        PdfPTable table = new PdfPTable(7);

        table.setWidthPercentage(100);

        float[] columnWidths = {14f, 35f, 50f, 30f, 30f, 15f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Ledger Id", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Ledger Name", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Shares Removed", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Shares Added", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("N_Shares", font2));
        PdfPCell cell7 = new PdfPCell(new Paragraph("V_Shares", font2));

        cell1.setBackgroundColor(BaseColor.CYAN);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.CYAN);
        cell3.setBackgroundColor(BaseColor.CYAN);
        cell4.setBackgroundColor(BaseColor.CYAN);
        cell5.setBackgroundColor(BaseColor.CYAN);
        cell6.setBackgroundColor(BaseColor.CYAN);
        cell7.setBackgroundColor(BaseColor.CYAN);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);
        cell7.setBorderWidth(2f);

        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);
        cell5.disableBorderSide(LEFT);
        cell6.disableBorderSide(LEFT);
        cell7.disableBorderSide(LEFT);

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);
        cell7.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);
        cell7.disableBorderSide(RIGHT);

        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);        // sets border width to 3 units
        cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell7.setHorizontalAlignment(Element.ALIGN_RIGHT);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.listGroupSharesStatemnt(sdate, edate);

        if (!dataBase.isEmpty()) {

            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx2x = new PdfPCell(new Paragraph(realData.get(2).toString(), font2));
                            cellx2x.disableBorderSide(LEFT);
                            cellx2x.disableBorderSide(TOP);
                            cellx2x.disableBorderSide(RIGHT);
                            cellx2x.setBorderColorBottom(BaseColor.BLACK);
                            cellx2x.setBorderColorTop(BaseColor.WHITE);
                            cellx2x.setBorderColorLeft(BaseColor.WHITE);
                            cellx2x.setBorderColorRight(BaseColor.WHITE);
                            cellx2x.setFixedHeight(20.2f);
                            cellx2x.setPadding(.5f);
                            cellx2x.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2x);

                        }
                        if (z == 3) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }
                        if (z == 4) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 5) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }

                        if (z == 6) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(6).toString()), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }

                        z++;
                    }

                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }

                        if (z == 2) {

                            PdfPCell cellx2x = new PdfPCell(new Paragraph(realData.get(2).toString(), font1));
                            cellx2x.disableBorderSide(LEFT);
                            cellx2x.disableBorderSide(TOP);
                            cellx2x.disableBorderSide(RIGHT);
                            cellx2x.setBorderColorBottom(BaseColor.BLACK);
                            cellx2x.setBorderColorTop(BaseColor.WHITE);
                            cellx2x.setBorderColorLeft(BaseColor.WHITE);
                            cellx2x.setBorderColorRight(BaseColor.WHITE);
                            cellx2x.setFixedHeight(20.2f);
                            cellx2x.setPadding(.5f);
                            cellx2x.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2x);

                        }

                        if (z == 3) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font1));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }
                        if (z == 4) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 5) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        if (z == 6) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(6).toString()), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        z++;
                    }

                }

                n++;
            }
        } else {

            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
        }
        return table;
    }

    public boolean listGroupSavingsContributions(String startDate, String endDate, String fileName) {

        boolean groupListSavingsAvailable = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallylistGroupSavingsContributionspdf(startDate, endDate, fileName);
                groupListSavingsAvailable = true;
            }

        } else {
            createActuallylistGroupSavingsContributionspdf(startDate, endDate, fileName);
            groupListSavingsAvailable = true;
        }

        return groupListSavingsAvailable;
    }

    private void createActuallylistGroupSavingsContributionspdf(String startDate, String endDate, String fileName) {
        try {

//fios.stringFileWriter(fios.createFileName("PMMS_Statements", "PMMS_Statements1","accountNumber.txt"),accountNumber);
            Paragraph headerz = createHeading("listGroup_Savings", startDate, endDate);
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);

            document.addAuthor("Bazirake Augustine Googo");

            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));

            PdfPTable body = createActuallyListGroupSavingsTable(startDate, endDate);

            Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);

            ct.setIndentationLeft(50f);

            ct.setIndentationRight(200f);

            document.add(ct);

            ct.setAlignment(Element.ALIGN_CENTER);

            Image image1 = Image.getInstance("strawberry3.jpg");

            image1.setAbsolutePosition(49f, 1080f);

            image1.scaleAbsolute(43f, 43f);

            document.add(image1);

            document.add(headerz);

            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(250f);
            currency.setIndentationRight(80f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createActuallyListGroupSavingsTable(String sdate, String edate) {

        PdfPTable table = new PdfPTable(6);

        table.setWidthPercentage(100);

        float[] columnWidths = {10f, 30f, 45f, 25f, 30f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Ledger Id", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Ledger Name", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Savings Removed", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Savings Added", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Running Balance", font2));

        cell1.setBackgroundColor(BaseColor.CYAN);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.CYAN);
        cell3.setBackgroundColor(BaseColor.CYAN);
        cell4.setBackgroundColor(BaseColor.CYAN);
        cell5.setBackgroundColor(BaseColor.CYAN);
        cell6.setBackgroundColor(BaseColor.CYAN);
// cell7.setBackgroundColor(BaseColor.CYAN); 

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);
//  cell7.setBorderWidth (2f);

        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);
        cell5.disableBorderSide(LEFT);
        cell6.disableBorderSide(LEFT);
//   cell7 .disableBorderSide(LEFT);

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);
//    cell7.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);
//         cell7.disableBorderSide(RIGHT); 

        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);        // sets border width to 3 units
        cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
//         cell7.setHorizontalAlignment(Element.ALIGN_RIGHT);  

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
//   table.addCell(cell7);

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.listGroupSavingsStatemnt(sdate, edate);

        if (!dataBase.isEmpty()) {

            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx2x = new PdfPCell(new Paragraph(realData.get(2).toString(), font2));
                            cellx2x.disableBorderSide(LEFT);
                            cellx2x.disableBorderSide(TOP);
                            cellx2x.disableBorderSide(RIGHT);
                            cellx2x.setBorderColorBottom(BaseColor.BLACK);
                            cellx2x.setBorderColorTop(BaseColor.WHITE);
                            cellx2x.setBorderColorLeft(BaseColor.WHITE);
                            cellx2x.setBorderColorRight(BaseColor.WHITE);
                            cellx2x.setFixedHeight(20.2f);
                            cellx2x.setPadding(.5f);
                            cellx2x.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2x);

                        }
                        if (z == 3) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }
                        if (z == 4) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 5) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }

////  if(z==6){
////
////
////    PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(6).toString()),font2));
////    cellx5.setFixedHeight(20.2f);
////    cellx5.setPadding(.5f);
////    cellx5.disableBorderSide(LEFT);
////    cellx5.disableBorderSide(TOP);
////    cellx5.disableBorderSide(RIGHT);
////    cellx5.setBorderColorBottom(BaseColor.BLACK);
////    cellx5.setBorderColorTop(BaseColor.WHITE);
////    cellx5.setBorderColorLeft(BaseColor.WHITE);
////    cellx5.setBorderColorRight(BaseColor.WHITE);
////    cellx5.setVerticalAlignment(Element.ALIGN_TOP);
////         cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
////    table.addCell(cellx5);
////    }
                        z++;
                    }

                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }

                        if (z == 2) {

                            PdfPCell cellx2x = new PdfPCell(new Paragraph(realData.get(2).toString(), font1));
                            cellx2x.disableBorderSide(LEFT);
                            cellx2x.disableBorderSide(TOP);
                            cellx2x.disableBorderSide(RIGHT);
                            cellx2x.setBorderColorBottom(BaseColor.BLACK);
                            cellx2x.setBorderColorTop(BaseColor.WHITE);
                            cellx2x.setBorderColorLeft(BaseColor.WHITE);
                            cellx2x.setBorderColorRight(BaseColor.WHITE);
                            cellx2x.setFixedHeight(20.2f);
                            cellx2x.setPadding(.5f);
                            cellx2x.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2x);

                        }

                        if (z == 3) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font1));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }
                        if (z == 4) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font1));
                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 5) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font1));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
// if(z==6){
//
//
//    PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(6).toString()),font2));
//    cellx5.setFixedHeight(20.2f);
//    cellx5.setPadding(.5f);
//    cellx5.disableBorderSide(LEFT);
//    cellx5.disableBorderSide(TOP);
//    cellx5.disableBorderSide(RIGHT);
//    cellx5.setBorderColorBottom(BaseColor.BLACK);
//    cellx5.setBorderColorTop(BaseColor.WHITE);
//    cellx5.setBorderColorLeft(BaseColor.WHITE);
//    cellx5.setBorderColorRight(BaseColor.WHITE);
//    cellx5.setVerticalAlignment(Element.ALIGN_TOP);
//      cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
//    table.addCell(cellx5);
//    }
                        z++;
                    }

                }

                n++;
            }
        } else {

            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
//       table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
        }
        return table;
    }

    public boolean listGroupSavingsContributionsAll(String startDate, String endDate, String fileName) {

        boolean groupListSavingsAvailableAll = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallylistGroupSavingsContributionspdfAll(startDate, endDate, fileName);
                groupListSavingsAvailableAll = true;
            }

        } else {
            createActuallylistGroupSavingsContributionspdfAll(startDate, endDate, fileName);
            groupListSavingsAvailableAll = true;
        }

        return groupListSavingsAvailableAll;
    }

    private void createActuallylistGroupSavingsContributionspdfAll(String startDate, String endDate, String fileName) {
        try {

//fios.stringFileWriter(fios.createFileName("PMMS_Statements", "PMMS_Statements1","accountNumber.txt"),accountNumber);
            Paragraph headerz = createHeading("listGroup_Savingsall", startDate, endDate);
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);

            document.addAuthor("Bazirake Augustine Googo");

            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));

            PdfPTable body = createActuallyListGroupSavingsTableAll(startDate, endDate);

            Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);

            ct.setIndentationLeft(50f);

            ct.setIndentationRight(200f);

            document.add(ct);

            ct.setAlignment(Element.ALIGN_CENTER);

            Image image1 = Image.getInstance("strawberry3.jpg");

            image1.setAbsolutePosition(49f, 1080f);

            image1.scaleAbsolute(43f, 43f);

            document.add(image1);

            document.add(headerz);

            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(250f);
            currency.setIndentationRight(80f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createActuallyListGroupSavingsTableAll(String sdate, String edate) {

        PdfPTable table = new PdfPTable(6);

        table.setWidthPercentage(100);

        float[] columnWidths = {10f, 30f, 45f, 25f, 30f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Ledger Id", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Ledger Name", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Savings Removed", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Savings Added", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Running Balance", font2));

        cell1.setBackgroundColor(BaseColor.CYAN);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.CYAN);
        cell3.setBackgroundColor(BaseColor.CYAN);
        cell4.setBackgroundColor(BaseColor.CYAN);
        cell5.setBackgroundColor(BaseColor.CYAN);
        cell6.setBackgroundColor(BaseColor.CYAN);
// cell7.setBackgroundColor(BaseColor.CYAN); 

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);
//  cell7.setBorderWidth (2f);

        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);
        cell5.disableBorderSide(LEFT);
        cell6.disableBorderSide(LEFT);
//   cell7 .disableBorderSide(LEFT);

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);
//    cell7.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);
//         cell7.disableBorderSide(RIGHT); 

        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);        // sets border width to 3 units
        cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
//         cell7.setHorizontalAlignment(Element.ALIGN_RIGHT);  

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
//   table.addCell(cell7);

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.listGroupSavingsStatemntAll(sdate, edate);

        if (!dataBase.isEmpty()) {

            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx2x = new PdfPCell(new Paragraph(realData.get(2).toString(), font2));
                            cellx2x.disableBorderSide(LEFT);
                            cellx2x.disableBorderSide(TOP);
                            cellx2x.disableBorderSide(RIGHT);
                            cellx2x.setBorderColorBottom(BaseColor.BLACK);
                            cellx2x.setBorderColorTop(BaseColor.WHITE);
                            cellx2x.setBorderColorLeft(BaseColor.WHITE);
                            cellx2x.setBorderColorRight(BaseColor.WHITE);
                            cellx2x.setFixedHeight(20.2f);
                            cellx2x.setPadding(.5f);
                            cellx2x.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2x);

                        }
                        if (z == 3) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }
                        if (z == 4) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 5) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }

////  if(z==6){
////
////
////    PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(6).toString()),font2));
////    cellx5.setFixedHeight(20.2f);
////    cellx5.setPadding(.5f);
////    cellx5.disableBorderSide(LEFT);
////    cellx5.disableBorderSide(TOP);
////    cellx5.disableBorderSide(RIGHT);
////    cellx5.setBorderColorBottom(BaseColor.BLACK);
////    cellx5.setBorderColorTop(BaseColor.WHITE);
////    cellx5.setBorderColorLeft(BaseColor.WHITE);
////    cellx5.setBorderColorRight(BaseColor.WHITE);
////    cellx5.setVerticalAlignment(Element.ALIGN_TOP);
////         cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
////    table.addCell(cellx5);
////    }
                        z++;
                    }

                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }

                        if (z == 2) {

                            PdfPCell cellx2x = new PdfPCell(new Paragraph(realData.get(2).toString(), font1));
                            cellx2x.disableBorderSide(LEFT);
                            cellx2x.disableBorderSide(TOP);
                            cellx2x.disableBorderSide(RIGHT);
                            cellx2x.setBorderColorBottom(BaseColor.BLACK);
                            cellx2x.setBorderColorTop(BaseColor.WHITE);
                            cellx2x.setBorderColorLeft(BaseColor.WHITE);
                            cellx2x.setBorderColorRight(BaseColor.WHITE);
                            cellx2x.setFixedHeight(20.2f);
                            cellx2x.setPadding(.5f);
                            cellx2x.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2x);

                        }

                        if (z == 3) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font1));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }
                        if (z == 4) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font1));
                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 5) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font1));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
// if(z==6){
//
//
//    PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(6).toString()),font2));
//    cellx5.setFixedHeight(20.2f);
//    cellx5.setPadding(.5f);
//    cellx5.disableBorderSide(LEFT);
//    cellx5.disableBorderSide(TOP);
//    cellx5.disableBorderSide(RIGHT);
//    cellx5.setBorderColorBottom(BaseColor.BLACK);
//    cellx5.setBorderColorTop(BaseColor.WHITE);
//    cellx5.setBorderColorLeft(BaseColor.WHITE);
//    cellx5.setBorderColorRight(BaseColor.WHITE);
//    cellx5.setVerticalAlignment(Element.ALIGN_TOP);
//      cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
//    table.addCell(cellx5);
//    }
                        z++;
                    }

                }

                n++;
            }
        } else {

            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
//       table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
        }
        return table;
    }

    public boolean listGroupSharesContributionsAll(String startDate, String endDate, String fileName) {

        boolean groupListSharesAvailableAll = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallylistGroupSharesContributionspdfAll(startDate, endDate, fileName);
                groupListSharesAvailableAll = true;
            }

        } else {
            createActuallylistGroupSharesContributionspdfAll(startDate, endDate, fileName);
            groupListSharesAvailableAll = true;
        }

        return groupListSharesAvailableAll;
    }

    private void createActuallylistGroupSharesContributionspdfAll(String startDate, String endDate, String fileName) {
        try {

//fios.stringFileWriter(fios.createFileName("PMMS_Statements", "PMMS_Statements1","accountNumber.txt"),accountNumber);
            Paragraph headerz = createHeading("listGroup_SharesAll", startDate, endDate);
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);

            document.addAuthor("Bazirake Augustine Googo");

            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));

            PdfPTable body = createActuallyListGroupSharesTableAll(startDate, endDate);

            Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);

            ct.setIndentationLeft(50f);

            ct.setIndentationRight(200f);

            document.add(ct);

            ct.setAlignment(Element.ALIGN_CENTER);

            Image image1 = Image.getInstance("strawberry3.jpg");

            image1.setAbsolutePosition(49f, 1080f);

            image1.scaleAbsolute(43f, 43f);

            document.add(image1);

            document.add(headerz);

            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(250f);
            currency.setIndentationRight(80f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createActuallyListGroupSharesTableAll(String sdate, String edate) {

        PdfPTable table = new PdfPTable(7);

        table.setWidthPercentage(100);

        float[] columnWidths = {14f, 35f, 50f, 30f, 30f, 15f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Ledger Id", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Ledger Name", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Shares Removed", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Shares Added", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("N_Shares", font2));
        PdfPCell cell7 = new PdfPCell(new Paragraph("V_Shares", font2));

        cell1.setBackgroundColor(BaseColor.CYAN);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.CYAN);
        cell3.setBackgroundColor(BaseColor.CYAN);
        cell4.setBackgroundColor(BaseColor.CYAN);
        cell5.setBackgroundColor(BaseColor.CYAN);
        cell6.setBackgroundColor(BaseColor.CYAN);
        cell7.setBackgroundColor(BaseColor.CYAN);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);
        cell7.setBorderWidth(2f);

        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);
        cell5.disableBorderSide(LEFT);
        cell6.disableBorderSide(LEFT);
        cell7.disableBorderSide(LEFT);

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);
        cell7.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);
        cell7.disableBorderSide(RIGHT);

        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);        // sets border width to 3 units
        cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell7.setHorizontalAlignment(Element.ALIGN_RIGHT);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.listGroupSharesStatemntAll(sdate, edate);

        if (!dataBase.isEmpty()) {

            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx2x = new PdfPCell(new Paragraph(realData.get(2).toString(), font2));
                            cellx2x.disableBorderSide(LEFT);
                            cellx2x.disableBorderSide(TOP);
                            cellx2x.disableBorderSide(RIGHT);
                            cellx2x.setBorderColorBottom(BaseColor.BLACK);
                            cellx2x.setBorderColorTop(BaseColor.WHITE);
                            cellx2x.setBorderColorLeft(BaseColor.WHITE);
                            cellx2x.setBorderColorRight(BaseColor.WHITE);
                            cellx2x.setFixedHeight(20.2f);
                            cellx2x.setPadding(.5f);
                            cellx2x.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2x);

                        }
                        if (z == 3) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }
                        if (z == 4) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 5) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }

                        if (z == 6) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(6).toString()), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }

                        z++;
                    }

                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }

                        if (z == 2) {

                            PdfPCell cellx2x = new PdfPCell(new Paragraph(realData.get(2).toString(), font1));
                            cellx2x.disableBorderSide(LEFT);
                            cellx2x.disableBorderSide(TOP);
                            cellx2x.disableBorderSide(RIGHT);
                            cellx2x.setBorderColorBottom(BaseColor.BLACK);
                            cellx2x.setBorderColorTop(BaseColor.WHITE);
                            cellx2x.setBorderColorLeft(BaseColor.WHITE);
                            cellx2x.setBorderColorRight(BaseColor.WHITE);
                            cellx2x.setFixedHeight(20.2f);
                            cellx2x.setPadding(.5f);
                            cellx2x.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2x);

                        }

                        if (z == 3) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font1));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }
                        if (z == 4) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 5) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        if (z == 6) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(6).toString()), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        z++;
                    }

                }

                n++;
            }
        } else {

            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
        }
        return table;
    }

    public boolean groupDailyReturnOnInvestemnt(String startDate, String endDate, String fileName) {
        boolean IndivisualmontlyReturnGroup = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallygroupDailyReturnOnInvestemnt(startDate, endDate, fileName);
                IndivisualmontlyReturnGroup = true;
            }

        } else {
            createActuallygroupDailyReturnOnInvestemnt(startDate, endDate, fileName);
            IndivisualmontlyReturnGroup = true;
        }

        return IndivisualmontlyReturnGroup;
    }

    private void createActuallygroupDailyReturnOnInvestemnt(String startDate, String endDate, String fileName) {
        try {

//fios.stringFileWriter(fios.createFileName("PMMS_Statements", "PMMS_Statements1","accountNumber.txt"),accountNumber);
            Paragraph headerz = createHeading("group_daily_ReturnGroup", startDate, endDate);
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);

            document.addAuthor("Bazirake Augustine Googo");

            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));

            PdfPTable body = createActuallygroupDailyReturnOnInvestemntPdf(startDate, endDate);

            Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);

            ct.setIndentationLeft(50f);

            ct.setIndentationRight(200f);

            document.add(ct);

            ct.setAlignment(Element.ALIGN_CENTER);

            Image image1 = Image.getInstance("strawberry3.jpg");

            image1.setAbsolutePosition(49f, 1080f);

            image1.scaleAbsolute(43f, 43f);

            document.add(image1);

            document.add(headerz);

            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(250f);
            currency.setIndentationRight(80f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createActuallygroupDailyReturnOnInvestemntPdf(String sdate, String edate) {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        float[] columnWidths = {14f, 30f, 50f, 30f, 30f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Date", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Name", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Shares", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Return", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Running Balance", font2));

        cell1.setBackgroundColor(BaseColor.GREEN);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.GREEN);
        cell3.setBackgroundColor(BaseColor.GREEN);
        cell4.setBackgroundColor(BaseColor.GREEN);
        cell5.setBackgroundColor(BaseColor.GREEN);
        cell6.setBackgroundColor(BaseColor.GREEN);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);
        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);
        cell5.disableBorderSide(LEFT);
        cell6.disableBorderSide(LEFT);

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);

        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.returnOIGroup(sdate, edate);
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(realData.get(2).toString(), font2));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx3);
                        }
                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font2));
                            cellx6.setFixedHeight(20.2f);
                            cellx6.setPadding(.5f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }
                        z++;
                    }

                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(realData.get(2).toString(), font1));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx3);
                        }
                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(5).toString()), font2));
                            cellx6.setFixedHeight(20.2f);
                            cellx6.setPadding(.5f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            cellx6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx6);
                        }

                        z++;
                    }

                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
        }
        return table;
    }

    public boolean createLoanApproved(String startDate, String endDate, String fileName) {
        boolean loansApproved = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyLoanApproved(startDate, endDate, fileName);
                loansApproved = true;
            }

        } else {
            createActuallyLoanApproved(startDate, endDate, fileName);
            loansApproved = true;
        }

        return loansApproved;
    }

    private void createActuallyLoanApproved(String startDate, String endDate, String fileName) {
        try {

            Paragraph headerz = createHeading("loan_approvedx", startDate, endDate);
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
            document.addAuthor("Bazirake Augustine Googo");
            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));
            PdfPTable body = createTheLoanApprovedx(startDate, endDate);

            Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);
            ct.setIndentationLeft(50f);
            ct.setIndentationRight(200f);
            document.add(ct);
            ct.setAlignment(Element.ALIGN_CENTER);
            Image image1 = Image.getInstance("strawberry3.jpg");
            image1.setAbsolutePosition(49f, 1080f);
            image1.scaleAbsolute(43f, 43f);
            document.add(image1);
            document.add(headerz);
            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(250f);
            currency.setIndentationRight(80f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createTheLoanApprovedx(String sdate, String edate) {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        float[] columnWidths = {14f, 50f, 30f, 30f, 30f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Borrower", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Princimpal", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Interest", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Instalment Start", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Instalment End", font2));

        cell1.setBackgroundColor(BaseColor.GREEN);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.GREEN);
        cell3.setBackgroundColor(BaseColor.GREEN);
        cell4.setBackgroundColor(BaseColor.GREEN);
        cell5.setBackgroundColor(BaseColor.GREEN);

        cell6.setBackgroundColor(BaseColor.GREEN);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);
        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);
        cell5.disableBorderSide(LEFT);
        cell6.disableBorderSide(LEFT);

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.loansApprovedx(sdate, edate);
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font2));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }
                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(realData.get(4).toString(), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(5).toString(), font2));
                            cellx6.setFixedHeight(20.2f);
                            cellx6.setPadding(.5f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx6);
                        }
                        z++;
                    }

                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(20.2f);
                            cellx1.disableBorderSide(LEFT);
                            cellx1.disableBorderSide(TOP);
                            cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
                            cellx2.disableBorderSide(LEFT);
                            cellx2.disableBorderSide(TOP);
                            cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(20.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font1));
                            cellx3.setFixedHeight(20.2f);
                            cellx3.setPadding(.5f);
                            cellx3.disableBorderSide(LEFT);
                            cellx3.disableBorderSide(TOP);
                            cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }
                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                            cellx4.setFixedHeight(20.2f);
                            cellx4.setPadding(.5f);
                            cellx4.disableBorderSide(LEFT);
                            cellx4.disableBorderSide(TOP);
                            cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(realData.get(4).toString(), font2));
                            cellx5.setFixedHeight(20.2f);
                            cellx5.setPadding(.5f);
                            cellx5.disableBorderSide(LEFT);
                            cellx5.disableBorderSide(TOP);
                            cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx5);
                        }

                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(5).toString(), font2));
                            cellx6.setFixedHeight(20.2f);
                            cellx6.setPadding(.5f);
                            cellx6.disableBorderSide(LEFT);
                            cellx6.disableBorderSide(TOP);
                            cellx6.disableBorderSide(RIGHT);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx6);
                        }
                        z++;
                    }
                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));

        }
        return table;
    }

    public boolean createLoanCompleted(String startDate, String endDate, String fileName) {
        boolean loansCompleted = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyLoanCompleted(startDate, endDate, fileName);
                loansCompleted = true;
            }

        } else {
            createActuallyLoanCompleted(startDate, endDate, fileName);
            loansCompleted = true;
        }

        return loansCompleted;
    }

    private void createActuallyLoanCompleted(String startDate, String endDate, String fileName) {
        try {

            Paragraph headerz = createHeading("loan_completedx", startDate, endDate);
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
            document.addAuthor("Bazirake Augustine Googo");
            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));
            PdfPTable body = createTheLoanCompletedx(startDate, endDate);

            Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);
            ct.setIndentationLeft(50f);
            ct.setIndentationRight(200f);
            document.add(ct);
            ct.setAlignment(Element.ALIGN_CENTER);
            Image image1 = Image.getInstance("strawberry3.jpg");
            image1.setAbsolutePosition(49f, 1080f);
            image1.scaleAbsolute(43f, 43f);
            document.add(image1);
            document.add(headerz);
            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(250f);
            currency.setIndentationRight(80f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createTheLoanCompletedx(String sdate, String edate) {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        float[] columnWidths = {14f, 50f, 30f, 30f, 30f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Borrower", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Princimpal", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Interest", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Instalment Start", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Instalment End", font2));

        cell1.setBackgroundColor(BaseColor.GREEN);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.GREEN);
        cell3.setBackgroundColor(BaseColor.GREEN);
        cell4.setBackgroundColor(BaseColor.GREEN);
        cell5.setBackgroundColor(BaseColor.GREEN);

        cell6.setBackgroundColor(BaseColor.GREEN);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);
//    cell1 .disableBorderSide(LEFT);         // sets border width to 3 units
//    cell2 .disableBorderSide(LEFT);
//    cell3 .disableBorderSide(LEFT);
// cell4 .disableBorderSide(LEFT);
//    cell5 .disableBorderSide(LEFT);
//    cell6 .disableBorderSide(LEFT);

//    cell1 .disableBorderSide(TOP);       // sets border width to 3 units
//    cell2.disableBorderSide(TOP); 
//    cell3.disableBorderSide(TOP); 
//  cell4.disableBorderSide(TOP); 
//    cell5.disableBorderSide(TOP);
//     cell6.disableBorderSide(TOP);
//    cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
//    cell2.disableBorderSide(RIGHT); 
//    cell3.disableBorderSide(RIGHT); 
//   cell4.disableBorderSide(RIGHT); 
//    cell5.disableBorderSide(RIGHT); 
//   cell6.disableBorderSide(RIGHT); 
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.loansCompletedx(sdate, edate);
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(70.2f);
//    cellx1.disableBorderSide(LEFT);
//    cellx1.disableBorderSide(TOP);
//    cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.BLACK);
                            cellx1.setBorderColorLeft(BaseColor.BLACK);
                            cellx1.setBorderColorRight(BaseColor.BLACK);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
//    cellx2.disableBorderSide(LEFT);
//    cellx2.disableBorderSide(TOP);
//    cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.BLACK);
                            cellx2.setBorderColorLeft(BaseColor.BLACK);
                            cellx2.setBorderColorRight(BaseColor.BLACK);
                            cellx2.setFixedHeight(70.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font2));
                            cellx3.setFixedHeight(70.2f);
                            cellx3.setPadding(.5f);
//    cellx3.disableBorderSide(LEFT);
//    cellx3.disableBorderSide(TOP);
//    cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.BLACK);
                            cellx3.setBorderColorLeft(BaseColor.BLACK);
                            cellx3.setBorderColorRight(BaseColor.BLACK);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }
                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                            cellx4.setFixedHeight(70.2f);
                            cellx4.setPadding(.5f);
//    cellx4.disableBorderSide(LEFT);
//    cellx4.disableBorderSide(TOP);
//    cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.BLACK);
                            cellx4.setBorderColorLeft(BaseColor.BLACK);
                            cellx4.setBorderColorRight(BaseColor.BLACK);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(realData.get(4).toString(), font2));
                            cellx5.setFixedHeight(70.2f);
                            cellx5.setPadding(.5f);
//    cellx5.disableBorderSide(LEFT);
//    cellx5.disableBorderSide(TOP);
//    cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.BLACK);
                            cellx5.setBorderColorLeft(BaseColor.BLACK);
                            cellx5.setBorderColorRight(BaseColor.BLACK);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(5).toString(), font2));
                            cellx6.setFixedHeight(70.2f);
                            cellx6.setPadding(.5f);
//    cellx6.disableBorderSide(LEFT);
//    cellx6.disableBorderSide(TOP);
//    cellx6.disableBorderSide(RIGHT);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.BLACK);
                            cellx6.setBorderColorLeft(BaseColor.BLACK);
                            cellx6.setBorderColorRight(BaseColor.BLACK);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx6);
                        }
                        z++;
                    }

                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(70.2f);
//    cellx1.disableBorderSide(LEFT);
//    cellx1.disableBorderSide(TOP);
//    cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.BLACK);
                            cellx1.setBorderColorLeft(BaseColor.BLACK);
                            cellx1.setBorderColorRight(BaseColor.BLACK);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
//    cellx2.disableBorderSide(LEFT);
//    cellx2.disableBorderSide(TOP);
//    cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.BLACK);
                            cellx2.setBorderColorLeft(BaseColor.BLACK);
                            cellx2.setBorderColorRight(BaseColor.BLACK);
                            cellx2.setFixedHeight(70.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font1));
                            cellx3.setFixedHeight(70.2f);
                            cellx3.setPadding(.5f);
//    cellx3.disableBorderSide(LEFT);
//    cellx3.disableBorderSide(TOP);
//    cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.BLACK);
                            cellx3.setBorderColorLeft(BaseColor.BLACK);
                            cellx3.setBorderColorRight(BaseColor.BLACK);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }
                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                            cellx4.setFixedHeight(70.2f);
                            cellx4.setPadding(.5f);
//    cellx4.disableBorderSide(LEFT);
//    cellx4.disableBorderSide(TOP);
//    cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.BLACK);
                            cellx4.setBorderColorLeft(BaseColor.BLACK);
                            cellx4.setBorderColorRight(BaseColor.BLACK);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(realData.get(4).toString(), font2));
                            cellx5.setFixedHeight(70.2f);
                            cellx5.setPadding(.5f);
//    cellx5.disableBorderSide(LEFT);
//    cellx5.disableBorderSide(TOP);
//    cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.BLACK);
                            cellx5.setBorderColorLeft(BaseColor.BLACK);
                            cellx5.setBorderColorRight(BaseColor.BLACK);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx5);
                        }

                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(5).toString(), font2));
                            cellx6.setFixedHeight(70.2f);
                            cellx6.setPadding(.5f);
//    cellx6.disableBorderSide(LEFT);
//    cellx6.disableBorderSide(TOP);
//    cellx6.disableBorderSide(RIGHT);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.BLACK);
                            cellx6.setBorderColorLeft(BaseColor.BLACK);
                            cellx6.setBorderColorRight(BaseColor.BLACK);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx6);
                        }
                        z++;
                    }
                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));

        }
        return table;
    }

    public boolean createLoanDisbursed(String startDate, String endDate, String fileName) {
        boolean loansDisbursed = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf"))) {
                createActuallyLoanDisbursed(startDate, endDate, fileName);
                loansDisbursed = true;
            }

        } else {
            createActuallyLoanDisbursed(startDate, endDate, fileName);
            loansDisbursed = true;
        }

        return loansDisbursed;
    }

    private void createActuallyLoanDisbursed(String startDate, String endDate, String fileName) {
        try {

            Paragraph headerz = createHeading("loan_disbursedx", startDate, endDate);
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
            document.addAuthor("Bazirake Augustine Googo");
            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName + ".pdf")));
            PdfPTable body = createTheLoanDisbursedx(startDate, endDate);

            Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);
            ct.setIndentationLeft(50f);
            ct.setIndentationRight(200f);
            document.add(ct);
            ct.setAlignment(Element.ALIGN_CENTER);
            Image image1 = Image.getInstance("strawberry3.jpg");
            image1.setAbsolutePosition(49f, 1080f);
            image1.scaleAbsolute(43f, 43f);
            document.add(image1);
            document.add(headerz);
            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(250f);
            currency.setIndentationRight(80f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createTheLoanDisbursedx(String sdate, String edate) {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        float[] columnWidths = {14f, 50f, 30f, 30f, 30f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Borrower", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Princimpal", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Interest", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Instalment Start", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Instalment End", font2));

        cell1.setBackgroundColor(BaseColor.GREEN);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.GREEN);
        cell3.setBackgroundColor(BaseColor.GREEN);
        cell4.setBackgroundColor(BaseColor.GREEN);
        cell5.setBackgroundColor(BaseColor.GREEN);

        cell6.setBackgroundColor(BaseColor.GREEN);

        cell1.setBorderWidth(2f);         // sets border width to 3 units
        cell2.setBorderWidth(2f);
        cell3.setBorderWidth(2f);
        cell4.setBorderWidth(2f);
        cell5.setBorderWidth(2f);
        cell6.setBorderWidth(2f);
//    cell1 .disableBorderSide(LEFT);         // sets border width to 3 units
//    cell2 .disableBorderSide(LEFT);
//    cell3 .disableBorderSide(LEFT);
// cell4 .disableBorderSide(LEFT);
//    cell5 .disableBorderSide(LEFT);
//    cell6 .disableBorderSide(LEFT);

//    cell1 .disableBorderSide(TOP);       // sets border width to 3 units
//    cell2.disableBorderSide(TOP); 
//    cell3.disableBorderSide(TOP); 
//  cell4.disableBorderSide(TOP); 
//    cell5.disableBorderSide(TOP);
//     cell6.disableBorderSide(TOP);
//    cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
//    cell2.disableBorderSide(RIGHT); 
//    cell3.disableBorderSide(RIGHT); 
//   cell4.disableBorderSide(RIGHT); 
//    cell5.disableBorderSide(RIGHT); 
//   cell6.disableBorderSide(RIGHT); 
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.loansDisbursedx(sdate, edate);
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
                if (n == dataBase.size() - 1) {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
                            cellx1.setFixedHeight(70.2f);
//    cellx1.disableBorderSide(LEFT);
//    cellx1.disableBorderSide(TOP);
//    cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
//    cellx2.disableBorderSide(LEFT);
//    cellx2.disableBorderSide(TOP);
//    cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(70.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font2));
                            cellx3.setFixedHeight(70.2f);
                            cellx3.setPadding(.5f);
//    cellx3.disableBorderSide(LEFT);
//    cellx3.disableBorderSide(TOP);
//    cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }
                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                            cellx4.setFixedHeight(70.2f);
                            cellx4.setPadding(.5f);
//    cellx4.disableBorderSide(LEFT);
//    cellx4.disableBorderSide(TOP);
//    cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(realData.get(4).toString(), font2));
                            cellx5.setFixedHeight(70.2f);
                            cellx5.setPadding(.5f);
//    cellx5.disableBorderSide(LEFT);
//    cellx5.disableBorderSide(TOP);
//    cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx5);
                        }
                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(5).toString(), font2));
                            cellx6.setFixedHeight(70.2f);
                            cellx6.setPadding(.5f);
//    cellx6.disableBorderSide(LEFT);
//    cellx6.disableBorderSide(TOP);
//    cellx6.disableBorderSide(RIGHT);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx6);
                        }
                        z++;
                    }

                } else {
                    while (z < realData.size()) {
                        if (z == 0) {
                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                            cellx1.setFixedHeight(70.2f);
//    cellx1.disableBorderSide(LEFT);
//    cellx1.disableBorderSide(TOP);
//    cellx1.disableBorderSide(RIGHT);
                            cellx1.setPadding(.5f);
                            cellx1.setBorderColorBottom(BaseColor.BLACK);
                            cellx1.setBorderColorTop(BaseColor.WHITE);
                            cellx1.setBorderColorLeft(BaseColor.WHITE);
                            cellx1.setBorderColorRight(BaseColor.WHITE);
                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx1);
                        }
                        if (z == 1) {

                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
//    cellx2.disableBorderSide(LEFT);
//    cellx2.disableBorderSide(TOP);
//    cellx2.disableBorderSide(RIGHT);
                            cellx2.setBorderColorBottom(BaseColor.BLACK);
                            cellx2.setBorderColorTop(BaseColor.WHITE);
                            cellx2.setBorderColorLeft(BaseColor.WHITE);
                            cellx2.setBorderColorRight(BaseColor.WHITE);
                            cellx2.setFixedHeight(70.2f);
                            cellx2.setPadding(.5f);
                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx2);

                        }
                        if (z == 2) {

                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font1));
                            cellx3.setFixedHeight(70.2f);
                            cellx3.setPadding(.5f);
//    cellx3.disableBorderSide(LEFT);
//    cellx3.disableBorderSide(TOP);
//    cellx3.disableBorderSide(RIGHT);
                            cellx3.setBorderColorBottom(BaseColor.BLACK);
                            cellx3.setBorderColorTop(BaseColor.WHITE);
                            cellx3.setBorderColorLeft(BaseColor.WHITE);
                            cellx3.setBorderColorRight(BaseColor.WHITE);
                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx3);
                        }
                        if (z == 3) {

                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                            cellx4.setFixedHeight(70.2f);
                            cellx4.setPadding(.5f);
//    cellx4.disableBorderSide(LEFT);
//    cellx4.disableBorderSide(TOP);
//    cellx4.disableBorderSide(RIGHT);
                            cellx4.setBorderColorBottom(BaseColor.BLACK);
                            cellx4.setBorderColorTop(BaseColor.WHITE);
                            cellx4.setBorderColorLeft(BaseColor.WHITE);
                            cellx4.setBorderColorRight(BaseColor.WHITE);
                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cellx4);
                        }
                        if (z == 4) {

                            PdfPCell cellx5 = new PdfPCell(new Paragraph(realData.get(4).toString(), font2));
                            cellx5.setFixedHeight(70.2f);
                            cellx5.setPadding(.5f);
//    cellx5.disableBorderSide(LEFT);
//    cellx5.disableBorderSide(TOP);
//    cellx5.disableBorderSide(RIGHT);
                            cellx5.setBorderColorBottom(BaseColor.BLACK);
                            cellx5.setBorderColorTop(BaseColor.WHITE);
                            cellx5.setBorderColorLeft(BaseColor.WHITE);
                            cellx5.setBorderColorRight(BaseColor.WHITE);
                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx5);
                        }

                        if (z == 5) {

                            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(5).toString(), font2));
                            cellx6.setFixedHeight(70.2f);
                            cellx6.setPadding(.5f);
//    cellx6.disableBorderSide(LEFT);
//    cellx6.disableBorderSide(TOP);
//    cellx6.disableBorderSide(RIGHT);
                            cellx6.setBorderColorBottom(BaseColor.BLACK);
                            cellx6.setBorderColorTop(BaseColor.WHITE);
                            cellx6.setBorderColorLeft(BaseColor.WHITE);
                            cellx6.setBorderColorRight(BaseColor.WHITE);
                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                            table.addCell(cellx6);
                        }
                        z++;
                    }
                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));

        }
        return table;
    }

    public boolean createActuallyReport(List theData, Component c, JTextField j) {
        boolean loansDisbursed = false;

        if (fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", theData.get(4).toString() + ".pdf"))) {
            if (fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", theData.get(4).toString() + ".pdf"))) {
                createActuallySummuryReport(theData, c, j);
                loansDisbursed = true;
            }

        } else {
            createActuallySummuryReport(theData, c, j);
            loansDisbursed = true;
        }

        return loansDisbursed;
    }

    private void createActuallySummuryReport(List theData1, Component c, JTextField j) {
        try {

            Paragraph headerz = createHeading("summuryReport_ks", theData1.get(1).toString(), theData1.get(2).toString());
            headerz.setIndentationLeft(50);
            headerz.setIndentationRight(50);

            Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
            document.addAuthor("Bazirake Augustine Googo");
            PdfWriter writer = PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", theData1.get(4).toString() + ".pdf")));
            PdfPTable body = createTheLoanDisbursedx(theData1, c, j);

            Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
            writer.setPageEvent(this);
            document.open();
            String BoxN = fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));

            Paragraph ct = new Paragraph("P.O BOX" + "\n" + BoxN.split("[,]", 3)[0] + "\n" + "Tel:" + BoxN.split("[,]", 3)[1] + "\n\t" + "    " + BoxN.split("[,]", 3)[2], font1);
            ct.setIndentationLeft(50f);
            ct.setIndentationRight(200f);
            document.add(ct);
            ct.setAlignment(Element.ALIGN_CENTER);
            Image image1 = Image.getInstance("strawberry3.jpg");
            image1.setAbsolutePosition(49f, 1080f);
            image1.scaleAbsolute(43f, 43f);
            document.add(image1);
            document.add(headerz);
            document.add(new Paragraph("  "));
            Paragraph currency = new Paragraph("CURRENCY UGANDA SHILLINGS", font1);
            currency.setIndentationLeft(250f);
            currency.setIndentationRight(80f);
            document.add(currency);
            document.add(new Paragraph("  "));
            document.add(body);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(this.createFooter());
            document.newPage();
            document.close();

        } catch (BadElementException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable createTheLoanDisbursedx(List theData2, Component c, JTextField j) {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        float[] columnWidths = {10f, 35f, 30f, 30f, 30f, 25f};

        Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);
        Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        PdfPCell cell1 = new PdfPCell(new Paragraph("S/n", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("ItemName", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Start Figure", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Difference", font2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("End Figure", font2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("ChangeStatus", font2));

        cell1.setBackgroundColor(BaseColor.ORANGE);   //sets BG color to yellow.
        cell2.setBackgroundColor(BaseColor.ORANGE);
        cell3.setBackgroundColor(BaseColor.ORANGE);
        cell4.setBackgroundColor(BaseColor.ORANGE);
        cell5.setBackgroundColor(BaseColor.ORANGE);

        cell6.setBackgroundColor(BaseColor.ORANGE);

//        cell1.setBorderWidth(2f);         // sets border width to 3 units
//        cell2.setBorderWidth(2f);
//        cell3.setBorderWidth(2f);
//        cell4.setBorderWidth(2f);
//        cell5.setBorderWidth(2f);
//        cell6.setBorderWidth(2f);
//    cell1 .disableBorderSide(LEFT);         // sets border width to 3 units
//    cell2 .disableBorderSide(LEFT);
//    cell3 .disableBorderSide(LEFT);
// cell4 .disableBorderSide(LEFT);
//    cell5 .disableBorderSide(LEFT);
//    cell6 .disableBorderSide(LEFT);
//    cell1 .disableBorderSide(TOP);       // sets border width to 3 units
//    cell2.disableBorderSide(TOP); 
//    cell3.disableBorderSide(TOP); 
//  cell4.disableBorderSide(TOP); 
//    cell5.disableBorderSide(TOP);
//     cell6.disableBorderSide(TOP);
//    cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
//    cell2.disableBorderSide(RIGHT); 
//    cell3.disableBorderSide(RIGHT); 
//   cell4.disableBorderSide(RIGHT); 
//    cell5.disableBorderSide(RIGHT); 
//   cell6.disableBorderSide(RIGHT); 
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        int n = 0;
        Map<Integer, List<Object>> dataBase = rdb.summuOneByOneRepo(theData2, c, j);
        if (!dataBase.isEmpty()) {
            while (n < dataBase.size()) {
                List realData = dataBase.get(n);
                int z = 0;
//                if (n == dataBase.size() - 1) {
//                    while (z < realData.size()) {
//                        if (z == 0) {
//                            PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font2));
//                            cellx1.setFixedHeight(20.2f);
////    cellx1.disableBorderSide(LEFT);
////    cellx1.disableBorderSide(TOP);
////    cellx1.disableBorderSide(RIGHT);
//                            cellx1.setPadding(.5f);
//                            cellx1.setBorderColorBottom(BaseColor.BLACK);
//                            cellx1.setBorderColorTop(BaseColor.WHITE);
//                            cellx1.setBorderColorLeft(BaseColor.WHITE);
//                            cellx1.setBorderColorRight(BaseColor.WHITE);
//                            cellx1.setVerticalAlignment(Element.ALIGN_TOP);
//                            table.addCell(cellx1);
//                        }
//                        if (z == 1) {
//
//                            PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font2));
////    cellx2.disableBorderSide(LEFT);
////    cellx2.disableBorderSide(TOP);
////    cellx2.disableBorderSide(RIGHT);
//                            cellx2.setBorderColorBottom(BaseColor.BLACK);
//                            cellx2.setBorderColorTop(BaseColor.WHITE);
//                            cellx2.setBorderColorLeft(BaseColor.WHITE);
//                            cellx2.setBorderColorRight(BaseColor.WHITE);
//                            cellx2.setFixedHeight(20.2f);
//                            cellx2.setPadding(.5f);
//                            cellx2.setVerticalAlignment(Element.ALIGN_TOP);
//                            table.addCell(cellx2);
//
//                        }
//                        if (z == 2) {
//
//                            PdfPCell cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(2).toString()), font2));
//                            cellx3.setFixedHeight(20.2f);
//                            cellx3.setPadding(.5f);
////    cellx3.disableBorderSide(LEFT);
////    cellx3.disableBorderSide(TOP);
////    cellx3.disableBorderSide(RIGHT);
//                            cellx3.setBorderColorBottom(BaseColor.BLACK);
//                            cellx3.setBorderColorTop(BaseColor.WHITE);
//                            cellx3.setBorderColorLeft(BaseColor.WHITE);
//                            cellx3.setBorderColorRight(BaseColor.WHITE);
//                            cellx3.setVerticalAlignment(Element.ALIGN_TOP);
//                            cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                            table.addCell(cellx3);
//                        }
//                        if (z == 3) {
//
//                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
//                            cellx4.setFixedHeight(20.2f);
//                            cellx4.setPadding(.5f);
////    cellx4.disableBorderSide(LEFT);
////    cellx4.disableBorderSide(TOP);
////    cellx4.disableBorderSide(RIGHT);
//                            cellx4.setBorderColorBottom(BaseColor.BLACK);
//                            cellx4.setBorderColorTop(BaseColor.WHITE);
//                            cellx4.setBorderColorLeft(BaseColor.WHITE);
//                            cellx4.setBorderColorRight(BaseColor.WHITE);
//                            cellx4.setVerticalAlignment(Element.ALIGN_TOP);
//                            cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                            table.addCell(cellx4);
//                        }
//                        if (z == 4) {
//
//                            PdfPCell cellx5 = new PdfPCell(new Paragraph(realData.get(4).toString(), font2));
//                            cellx5.setFixedHeight(20.2f);
//                            cellx5.setPadding(.5f);
////    cellx5.disableBorderSide(LEFT);
////    cellx5.disableBorderSide(TOP);
////    cellx5.disableBorderSide(RIGHT);
//                            cellx5.setBorderColorBottom(BaseColor.BLACK);
//                            cellx5.setBorderColorTop(BaseColor.WHITE);
//                            cellx5.setBorderColorLeft(BaseColor.WHITE);
//                            cellx5.setBorderColorRight(BaseColor.WHITE);
//                            cellx5.setVerticalAlignment(Element.ALIGN_TOP);
//                            table.addCell(cellx5);
//                        }
//                        if (z == 5) {
//
//                            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(5).toString(), font2));
//                            cellx6.setFixedHeight(20.2f);
//                            cellx6.setPadding(.5f);
////    cellx6.disableBorderSide(LEFT);
////    cellx6.disableBorderSide(TOP);
////    cellx6.disableBorderSide(RIGHT);
//                            cellx6.setBorderColorBottom(BaseColor.BLACK);
//                            cellx6.setBorderColorTop(BaseColor.WHITE);
//                            cellx6.setBorderColorLeft(BaseColor.WHITE);
//                            cellx6.setBorderColorRight(BaseColor.WHITE);
//                            cellx6.setVerticalAlignment(Element.ALIGN_TOP);
//                            table.addCell(cellx6);
//                        }
//                        z++;
//                    }
//
//                } else {
                while (z < realData.size()) {
                    if (z == 0) {
                        PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(), font1));
                        cellx1.setFixedHeight(70.2f);
//    cellx1.disableBorderSide(LEFT);
//    cellx1.disableBorderSide(TOP);
//    cellx1.disableBorderSide(RIGHT);
                        cellx1.setPadding(.5f);
                        cellx1.setBorderColorBottom(BaseColor.BLACK);
                        cellx1.setBorderColorTop(BaseColor.WHITE);
                        cellx1.setBorderColorLeft(BaseColor.WHITE);
                        cellx1.setBorderColorRight(BaseColor.WHITE);
                        cellx1.setVerticalAlignment(Element.ALIGN_TOP);
                        table.addCell(cellx1);
                    }
                    if (z == 1) {

                        PdfPCell cellx2 = new PdfPCell(new Paragraph(realData.get(1).toString(), font1));
//    cellx2.disableBorderSide(LEFT);
//    cellx2.disableBorderSide(TOP);
//    cellx2.disableBorderSide(RIGHT);
                        cellx2.setBorderColorBottom(BaseColor.BLACK);
                        cellx2.setBorderColorTop(BaseColor.WHITE);
                        cellx2.setBorderColorLeft(BaseColor.WHITE);
                        cellx2.setBorderColorRight(BaseColor.WHITE);
                        cellx2.setFixedHeight(70.2f);
                        cellx2.setPadding(.5f);
                        cellx2.setVerticalAlignment(Element.ALIGN_TOP);
                        table.addCell(cellx2);

                    }
                    if (z == 2) {
                        PdfPCell cellx3 = null;
                        double amount = parseDouble(realData.get(2).toString());
                        if (amount < 0.0) {
                            cellx3 = new PdfPCell(new Paragraph("(" + fmt.formatForStatementNumbers(amount + "") + ")", font1));
                        } else {

                            cellx3 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(amount + ""), font1));

                        }

                        cellx3.setFixedHeight(70.2f);
                        cellx3.setPadding(.5f);
//    cellx3.disableBorderSide(LEFT);
//    cellx3.disableBorderSide(TOP);
//    cellx3.disableBorderSide(RIGHT);
                        cellx3.setBorderColorBottom(BaseColor.BLACK);
                        cellx3.setBorderColorTop(BaseColor.WHITE);
                        cellx3.setBorderColorLeft(BaseColor.WHITE);
                        cellx3.setBorderColorRight(BaseColor.WHITE);
                        cellx3.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx3);
                    }
                    if (z == 3) {

                        PdfPCell cellx4 = null;
                        double amount = parseDouble(realData.get(3).toString());
                        if (amount < 0.0) {
                            cellx4 = new PdfPCell(new Paragraph("(" + fmt.formatForStatementNumbers(amount + "") + ")", font1));
                        } else {

                            cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(amount + ""), font1));

                        }

//                            PdfPCell cellx4 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(3).toString()), font2));
                        cellx4.setFixedHeight(70.2f);
                        cellx4.setPadding(.5f);
//    cellx4.disableBorderSide(LEFT);
//    cellx4.disableBorderSide(TOP);
//    cellx4.disableBorderSide(RIGHT);
                        cellx4.setBorderColorBottom(BaseColor.BLACK);
                        cellx4.setBorderColorTop(BaseColor.WHITE);
                        cellx4.setBorderColorLeft(BaseColor.WHITE);
                        cellx4.setBorderColorRight(BaseColor.WHITE);
                        cellx4.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx4);
                    }
                    if (z == 4) {
                        PdfPCell cellx5 = null;
                        double amount = parseDouble(realData.get(4).toString());
                        if (amount < 0.0) {
                            cellx5 = new PdfPCell(new Paragraph("(" + fmt.formatForStatementNumbers(amount + "") + ")", font1));
                        } else {

                            cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(amount + ""), font1));

                        }
//                            PdfPCell cellx5 = new PdfPCell(new Paragraph(fmt.formatForStatementNumbers(realData.get(4).toString()), font2));
                        cellx5.setFixedHeight(70.2f);
                        cellx5.setPadding(.5f);
//    cellx5.disableBorderSide(LEFT);
//    cellx5.disableBorderSide(TOP);
//    cellx5.disableBorderSide(RIGHT);
                        cellx5.setBorderColorBottom(BaseColor.BLACK);
                        cellx5.setBorderColorTop(BaseColor.WHITE);
                        cellx5.setBorderColorLeft(BaseColor.WHITE);
                        cellx5.setBorderColorRight(BaseColor.WHITE);
                        cellx5.setVerticalAlignment(Element.ALIGN_TOP);
                        cellx5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellx5);
                    }

                    if (z == 5) {

                        PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(5).toString(), font2));
                        cellx6.setFixedHeight(70.2f);
                        cellx6.setPadding(.5f);
//    cellx6.disableBorderSide(LEFT);
//    cellx6.disableBorderSide(TOP);
//    cellx6.disableBorderSide(RIGHT);
                        cellx6.setBorderColorBottom(BaseColor.BLACK);
                        cellx6.setBorderColorTop(BaseColor.WHITE);
                        cellx6.setBorderColorLeft(BaseColor.WHITE);
                        cellx6.setBorderColorRight(BaseColor.WHITE);
                        cellx6.setVerticalAlignment(Element.ALIGN_TOP);
                        table.addCell(cellx6);
                    }
                    z++;
                }
//                }

                n++;
            }
        } else {
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A", font1)));

        }
        return table;
    }

    public PdfPTable createFooter() {
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        float[] columnWidths = {30f, 30f, 30f};

        Font font1 = new Font(Font.FontFamily.COURIER, 18, Font.NORMAL);

        Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

        try {
            table.setWidths(columnWidths);

        } catch (DocumentException ex) {
            Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        PdfPCell cell1 = new PdfPCell(new Paragraph("PREPARED BY", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("APPROVED BY", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("NAME: " + quaries.getLoggedInUserName(), font1));
        PdfPCell cell5 = new PdfPCell(new Paragraph("", font1));
        PdfPCell cell6 = new PdfPCell(new Paragraph("NAME:_________________", font1));
        PdfPCell cell7 = new PdfPCell(new Paragraph("SIGNATURE:____________", font1));
        PdfPCell cell8 = new PdfPCell(new Paragraph("", font1));
        PdfPCell cell9 = new PdfPCell(new Paragraph("SIGNATURE:____________", font1));
        PdfPCell cell10 = new PdfPCell(new Paragraph("DATE: " + sdf.format(new Date(System.currentTimeMillis())), font1));
        PdfPCell cell11 = new PdfPCell(new Paragraph("", font1));
        PdfPCell cell12 = new PdfPCell(new Paragraph("DATE:_________________", font1));

//
//    cell1.setBackgroundColor(BaseColor.GREEN);   //sets BG color to yellow.
//    cell2.setBackgroundColor(BaseColor.GREEN); 
//    cell3.setBackgroundColor(BaseColor.GREEN); 
//
//    cell1.setBorderWidth (2f);         // sets border width to 3 units
//    cell2.setBorderWidth (2f);
//    cell3.setBorderWidth (2f);
        cell1.disableBorderSide(LEFT);         // sets border width to 3 units
        cell2.disableBorderSide(LEFT);
        cell3.disableBorderSide(LEFT);
        cell4.disableBorderSide(LEFT);         // sets border width to 3 units
        cell5.disableBorderSide(LEFT);
        cell6.disableBorderSide(LEFT);
        cell7.disableBorderSide(LEFT);         // sets border width to 3 units
        cell8.disableBorderSide(LEFT);
        cell9.disableBorderSide(LEFT);
        cell10.disableBorderSide(LEFT);         // sets border width to 3 units
        cell11.disableBorderSide(LEFT);
        cell12.disableBorderSide(LEFT);

        cell1.disableBorderSide(TOP);       // sets border width to 3 units
        cell2.disableBorderSide(TOP);
        cell3.disableBorderSide(TOP);
        cell4.disableBorderSide(TOP);         // sets border width to 3 units
        cell5.disableBorderSide(TOP);
        cell6.disableBorderSide(TOP);
        cell7.disableBorderSide(TOP);         // sets border width to 3 units
        cell8.disableBorderSide(TOP);
        cell9.disableBorderSide(TOP);
        cell10.disableBorderSide(TOP);         // sets border width to 3 units
        cell11.disableBorderSide(TOP);
        cell12.disableBorderSide(TOP);

        cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell2.disableBorderSide(RIGHT);
        cell3.disableBorderSide(RIGHT);
        cell4.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell5.disableBorderSide(RIGHT);
        cell6.disableBorderSide(RIGHT);
        cell7.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell8.disableBorderSide(RIGHT);
        cell9.disableBorderSide(RIGHT);
        cell10.disableBorderSide(RIGHT);         // sets border width to 3 units
        cell11.disableBorderSide(RIGHT);
        cell12.disableBorderSide(RIGHT);

        cell1.disableBorderSide(BOTTOM);      // sets border width to 3 units
        cell2.disableBorderSide(BOTTOM);
        cell3.disableBorderSide(BOTTOM);
        cell1.disableBorderSide(BOTTOM);         // sets border width to 3 units
        cell2.disableBorderSide(BOTTOM);
        cell3.disableBorderSide(BOTTOM);
        cell4.disableBorderSide(BOTTOM);         // sets border width to 3 units
        cell5.disableBorderSide(BOTTOM);
        cell6.disableBorderSide(BOTTOM);
        cell7.disableBorderSide(BOTTOM);         // sets border width to 3 units
        cell8.disableBorderSide(BOTTOM);
        cell9.disableBorderSide(BOTTOM);
        cell10.disableBorderSide(BOTTOM);         // sets border width to 3 units
        cell11.disableBorderSide(BOTTOM);
        cell12.disableBorderSide(BOTTOM);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        table.addCell(cell8);
        table.addCell(cell9);
        table.addCell(cell10);
        table.addCell(cell11);
        table.addCell(cell12);
        return table;
    }

    private Paragraph createHeading(String identifier, String startDate, String endDate) {
        Paragraph ss = null;
        Font fonts = new Font(Font.FontFamily.COURIER, 14, Font.UNDERLINE);
        Font fontsR = new Font(Font.FontFamily.COURIER, 18, Font.UNDERLINE);
        switch (identifier) {
            case "loanTostaff":

                ss = new Paragraph("LIST OF LOANS TO ADMINS" + "\n" + "AS AT" + " " + sdf.format(fmt.getTodayDate()) + "", fonts);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;
            case "loan_Arrears":

                ss = new Paragraph("LOANS IN  ARREARS REPORT" + "\n" + "AS AT" + " " + sdf.format(fmt.getTodayDate()), fonts);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;

            case "arrearsDetails":

                ss = new Paragraph("Loan Arrears Report" + "\n" + startDate + "(" + endDate + ")" + " " + "\n" + "As At" + " " + sdf.format(fmt.getTodayDate()) + "", fonts);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;
            case "loan_portfolio":

                ss = new Paragraph("Loan Portfolio Report" + "\n" + "As At" + " " + sdf.format(fmt.getTodayDate()) + "", fonts);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;

            case "loan_applied":

                ss = new Paragraph("Loans Applied Report" + "\n" + "As As" + " " + sdf.format(fmt.getTodayDate()) + "", fonts);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;
            case "loan_approved":

                ss = new Paragraph("Loans Approved Report" + "\n" + "As At" + " " + sdf.format(fmt.getTodayDate()) + "", fonts);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;
            case "loan_disbursed":

                ss = new Paragraph("Loans Disbursed Report" + "\n" + "As At" + " " + sdf.format(fmt.getTodayDate()) + "", fonts);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;
            case "loans_due":

                ss = new Paragraph("Details Of Loans Due Report" + "\n" + "As At" + " " + sdf.format(fmt.getTodayDate()) + "", fontsR);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;
            case "loans_due1":

                ss = new Paragraph("Details Of Loans Aging Report" + "\n" + "As At" + " " + sdf.format(fmt.getTodayDate()) + "", fontsR);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;
            case "gross_loan_port":
                ss = new Paragraph("Gross Loan Portfolio" + "\n" + "Between" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate) + " " + "And" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDate), fontsR);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;

            case "loan_dueWriteOff":

                ss = new Paragraph("LIST OF LOANS DUE FOR WRITE OFF" + "\n" + "AS AT" + " " + sdf.format(fmt.getTodayDate()) + "", fonts);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;
            case "performing_loan_port":

                ss = new Paragraph("Performing Loan Portfolio" + "\n" + "Between" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate) + " " + "And" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDate), fontsR);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;
            case "loan_arrears_R":

                ss = new Paragraph("Loan Arrears" + "\n" + "Between" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate) + " " + "And" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDate), fontsR);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;

            case "loan_port_atRisk":

                ss = new Paragraph("Loan Portfolio At Risk" + "\n" + "Between" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate) + " " + "And" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDate), fontsR);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;

            case "loan_due_writeOff":

                ss = new Paragraph("List of Loans Due For WriteOff" + "\n" + "Between" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate) + " " + "And" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDate), fontsR);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;

            case "loan_appliedx":

                ss = new Paragraph("List of Loans Applied" + "\n" + "Between" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate) + " " + "And" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDate), fontsR);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;

            case "montly_ReturnGroup":

                ss = new Paragraph("Statement of Group Montly Return On Investment For The Period" + "\n" + "Between" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate) + " " + "And" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDate), fontsR);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;

            case "individual_ReturnDaily":

                ss = new Paragraph("Statement of" + " " + quaries.AccountName(fios.stringFileReader(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "accountNumber.txt"))) + "'s " + "Daily Return On Investment For The Period" + "\n" + "Between" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate) + " " + "And" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDate), fontsR);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;

            case "group_daily_ReturnGroup":

                ss = new Paragraph("Statement of Daily Return On Investment For The Period" + "\n" + "Between" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate) + " " + "And" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDate), fontsR);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;

            case "individual_montly_ReturnGroup":

                ss = new Paragraph("Statement of" + " " + quaries.AccountName(fios.stringFileReader(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "accountNumber.txt"))) + "'s " + "Montly Return On Investment For The Period" + "\n" + "Between" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate) + " " + "And" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDate), fontsR);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;

            case "listGroup_Shares":

                ss = new Paragraph("STATEMENT OF SHARES AVAILABLE FOR ALL MEMBERS WITH SHARES FOR THE PERIOD" + "\n" + "BETWEEN" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate) + " " + "AND" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDate), fontsR);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;
            case "individual_daily_shares_roi":

                ss = new Paragraph("STATEMENT OF ROI ON SHARES FOR" + " " + quaries.AccountName(fios.stringFileReader(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "accountNumber.txt"))) + "(" + fios.stringFileReader(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "accountNumber.txt")) + ")" + "\n" + "FOR THE PERIOD BETWEEN" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate) + " " + "AND" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDate), fontsR);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;

            case "individual_daily_savings_roi":

                ss = new Paragraph("STATEMENT OF ROI ON SAVINGS FOR THE PERIOD BETWEEN" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate) + " " + "AND" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDate), fontsR);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;
            case "Group_daily_shares_roi":

                ss = new Paragraph("STATEMENT OF ROI ON SHARES FOR THE PERIOD BETWEEN" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate) + " " + "AND" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDate), fontsR);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;

            case "listGroup_Savings":

                ss = new Paragraph("STATEMENT OF SAVINGS  FOR  MEMBERS WITH SAVINGS FOR THE PERIOD" + "\n" + "BETWEEN" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate) + " " + "AND" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDate), fontsR);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;

            case "listGroup_Savingsall":

                ss = new Paragraph("STATEMENT OF SAVINGS  FOR ALL MEMBERS FOR THE PERIOD" + "\n" + "BETWEEN" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate) + " " + "AND" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDate), fontsR);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;

            case "listGroup_SharesAll":

                ss = new Paragraph("STATEMENT OF SHARES AVAILABLE FOR ALL MEMBERS FOR THE PERIOD" + "\n" + "BETWEEN" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate) + " " + "AND" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDate), fontsR);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;

            case "individual_montly_Shares":

                ss = new Paragraph("Statement of" + " " + quaries.AccountName(fios.stringFileReader(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "accountNumber.txt"))) + "'s " + "Shares Available for the period" + "\n" + "Between" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate) + " " + "And" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDate), fontsR);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;
            case "individual_montly_Savings":

                ss = new Paragraph("STATEMENT OF SAVINGS AVAILABLE FOR" + " " + quaries.AccountName(fios.stringFileReader(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "accountNumber.txt"))) + "(" + fios.stringFileReader(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "accountNumber.txt")) + ")" + "\n" + "FOR THE PERIOD BETWEEN" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate) + " " + "AND" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDate), fontsR);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;

            case "loan_approvedx":

                ss = new Paragraph("List of Loans Approved" + "\n" + "Between" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate) + " " + "And" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDate), fontsR);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;
            case "loan_disbursedx":

                ss = new Paragraph("List of Loans Disbursed" + "\n" + "Between" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate) + " " + "And" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDate), fontsR);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;
            case "loan_completedx":

                ss = new Paragraph("List of Loans Completed" + "\n" + "Between" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate) + " " + "And" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDate), fontsR);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;

            case "periodic_in":
                ss = new Paragraph("SUMMURY OF LOAN BORROWINGS" + "\n" + "AS AT" + " " + sdf.format(fmt.getTodayDate()) + "", fonts);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;
            case "nperiodic_in":
                ss = new Paragraph("SUMMURY OF LOAN BORROWINGS" + "\n" + "AS AT" + " " + sdf.format(fmt.getTodayDate()) + "", fonts);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;
            case "summuryReport_ks":
                ss = new Paragraph("SUMMURY REPORT" + "\n" + "FOR THE PERIOD BETWEEN" + " " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate) + "  AND  " + fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDate), fonts);
                ss.setAlignment(Element.ALIGN_CENTER);
                break;

        }

        return ss;

    }

    public Paragraph createSignitories(String accountNumber3) {
        Paragraph s = null;
        Font font1 = new Font(Font.FontFamily.COURIER, 10, Font.BOLDITALIC);
        s = new Paragraph("POWERED BY: BAZIRAKE AUGUSTINE GOOGO, MOBILE: 0782231039, EMAIL: augbazi@gmail.com", font1);
        s.setAlignment(Element.ALIGN_CENTER);
        return s;
    }

    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        pagenumber = 0;
    }

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        pagenumber++;
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        Font font1 = new Font(Font.FontFamily.COURIER, 10, Font.BOLDITALIC);

        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_CENTER, new Phrase(String.format("page %d", pagenumber), font1),
                (document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() + 1, 0);

//    Font font11 = new Font(Font.FontFamily.COURIER  , 10, Font.BOLD);
//
//    ColumnText.showTextAligned(writer.getDirectContent(),
//    Element.ALIGN_CENTER, new Phrase("We are almost THERE",font11),
//    ( document.right()-document.left())/2+document.leftMargin(), document.bottom()+50, 0);
    }

    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        document.addTitle("THIS IS IT");
    }

    @Override
    public void onParagraph(PdfWriter writer, Document document, float paragraphPosition) {

    }

    @Override
    public void onParagraphEnd(PdfWriter writer, Document document, float paragraphPosition) {

    }

    @Override
    public void onChapter(PdfWriter writer, Document document, float paragraphPosition, Paragraph title) {

    }

    @Override
    public void onChapterEnd(PdfWriter writer, Document document, float paragraphPosition) {

    }

    @Override
    public void onSection(PdfWriter writer, Document document, float paragraphPosition, int depth, Paragraph title) {

    }

    @Override
    public void onSectionEnd(PdfWriter writer, Document document, float paragraphPosition) {

    }

    @Override
    public void onGenericTag(PdfWriter writer, Document document, Rectangle rect, String text) {

    }

    @Override
    public String getTitle(String date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getTableHeaders() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<List<Object>> getBodyList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, List<Object>> getBodyMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTitle(String title) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTableHeader(String header) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setBodyList(List body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setBodyMap(Integer index, List<Object> body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
