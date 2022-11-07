/*4303990300000401
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.reportsHelper;

import googo.pmms.project2.frameHelper.MyTableModel;
import googo.pmms.project2.frames.PostingEntryWindow;
import googo.pmms.project2.databases.ReportsDatabase;
import googo.pmms.project2.databases.DatabaseQuaries;
import com.itextpdf.text.*;
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
import googo.pmms.project2.loanHelper.Amortization;
import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.loanHelper.MaxmumAmountBorrowedFormulas;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databaseConnectors.JdbcConnector;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Stanchart
 */
public class loanStatement implements PdfPageEvent {
    
 MaxmumAmountBorrowedFormulas MAth= new  MaxmumAmountBorrowedFormulas();
 
//     Amortization amortize = new Amortization();

     Calendar calN = Calendar.getInstance();
     
fileInputOutPutStreams fios= new fileInputOutPutStreams();

    Formartter ffm= new Formartter();
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date Trndate,valuedate;
    
   ArrayList<String> data4, column1;
   
 ArrayList<ArrayList<String>> data5;
 
          JOptionPane p;
     MyTableModel model;
  SimpleDateFormat df;

   double newbalance,ledgerBalance,creditamount;
    GregorianCalendar cal = new GregorianCalendar(); 
    JdbcConnector csx = new JdbcConnector(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "AccountDBDetails.txt"))); 
 DatabaseQuaries quaries =new DatabaseQuaries();
ReportsDatabase rdb = new ReportsDatabase();
 int   pagenumber=0;
   String userId;
 public void setUserID(String userid){
this.userId=userid;
}
   
 
   public boolean createRunningLoanStatement(String accountNumber,String fileName) { 
     
  boolean RunningLoanStatement=false;
     
  
  if(fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
  if(fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
createActuallyTRunningLoanStatement(accountNumber,fileName);
RunningLoanStatement=true;
  } 
    
  }else{
createActuallyTRunningLoanStatement(accountNumber,fileName);
RunningLoanStatement=true;
  }
      
      return RunningLoanStatement;
       }
  
      private void createActuallyTRunningLoanStatement(String accountNumber,String fileName){
   
   
   PdfPTable body1,body2;
      try {

          Paragraph headerz =createHeading(quaries.AccountName(accountNumber));
          headerz.setIndentationLeft(50);
          headerz.setIndentationRight(50);
          Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
          PdfWriter writer=PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf")));
          
//            if(ffm.convertTdate(SheetDate).equals(ffm.getTodayDate())){
           
          body1 = createTheTopPart(accountNumber);
            
             body2 = createTheBottomPart(accountNumber);
           
//            }else {
//            body = createPastBalanceSheet(SheetDate);
//            }
        
        
          Font font1 = new Font(Font.FontFamily.COURIER  , 14, Font.NORMAL);
          writer.setPageEvent(this);
          document.open();
          
         String BoxN=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));
          String Company=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "CompanyName.txt"));
          Paragraph ct= new Paragraph(Company+"\n P.O BOX"+"\n"+BoxN.split("[,]", 3)[0]+"\n"+"Tel:"+BoxN.split("[,]", 3)[1]+"\n\t"+"    "+BoxN.split("[,]", 3)[2],font1);
          ct.setIndentationLeft(50f);
          ct.setIndentationRight(200f);
          document.add(ct);
         
          Image image1 = Image.getInstance("strawberry3.jpg");
          image1.setAbsolutePosition(49f, 1080f);
          image1.scaleAbsolute(43f, 43f);
          document.add(image1);
          document.add(headerz);
          document.add(new Paragraph("  ") );
          Paragraph currency= new Paragraph("CURRENCY UGANDA SHILLINGS",font1);
          currency.setIndentationLeft(280f);
          currency.setIndentationRight(30f);
          document.add(currency);
          document.add(new Paragraph("  ") );
          document.add(body1);
          document.add(body2);
          document.add(new Paragraph("  ") );
          document.add(new Paragraph("  ") );
             document.add(new Paragraph("  ") );
    document.add(new Paragraph("  ") );
    document.add(this.createFooter());
            document.newPage();
          document.close();
         
         
      } catch (BadElementException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException | DocumentException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      }
       }
       public  PdfPTable createFooter( ){
    PdfPTable table = new PdfPTable(3);
    table.setWidthPercentage(100);
    float[] columnWidths = {30f, 30f, 30f};

    Font font1 = new Font(Font.FontFamily.COURIER  , 18, Font.NORMAL);
    
    Font font2 = new Font(Font.FontFamily.COURIER  , 18, Font.BOLD);

    try {
    table.setWidths(columnWidths);

    } catch (DocumentException ex) {
    Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
    }
    PdfPCell cell1 = new PdfPCell(new Paragraph("PREPARED BY", font2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("", font2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("APPROVED BY", font2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("NAME: "+quaries.getLoggedInUserName(), font1));
        PdfPCell cell5 = new PdfPCell(new Paragraph("", font1));
        PdfPCell cell6 = new PdfPCell(new Paragraph("NAME:_________________", font1));
        PdfPCell cell7 = new PdfPCell(new Paragraph("SIGNATURE:____________", font1));
        PdfPCell cell8 = new PdfPCell(new Paragraph("", font1));
        PdfPCell cell9 = new PdfPCell(new Paragraph("SIGNATURE:____________", font1));
        PdfPCell cell10 = new PdfPCell(new Paragraph("DATE: "+sdf.format(new Date(System.currentTimeMillis())), font1));
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
 
    cell1 .disableBorderSide(LEFT);         // sets border width to 3 units
    cell2 .disableBorderSide(LEFT);
    cell3 .disableBorderSide(LEFT);
  cell4.disableBorderSide(LEFT);         // sets border width to 3 units
    cell5 .disableBorderSide(LEFT);
    cell6 .disableBorderSide(LEFT);
     cell7 .disableBorderSide(LEFT);         // sets border width to 3 units
    cell8.disableBorderSide(LEFT);
    cell9 .disableBorderSide(LEFT);
     cell10 .disableBorderSide(LEFT);         // sets border width to 3 units
    cell11.disableBorderSide(LEFT);
    cell12 .disableBorderSide(LEFT);

    cell1 .disableBorderSide(TOP);       // sets border width to 3 units
    cell2.disableBorderSide(TOP); 
    cell3.disableBorderSide(TOP); 
    cell4.disableBorderSide(TOP);         // sets border width to 3 units
    cell5 .disableBorderSide(TOP);
    cell6 .disableBorderSide(TOP);
     cell7 .disableBorderSide(TOP);         // sets border width to 3 units
    cell8.disableBorderSide(TOP);
    cell9 .disableBorderSide(TOP);
     cell10 .disableBorderSide(TOP);         // sets border width to 3 units
    cell11.disableBorderSide(TOP);
    cell12 .disableBorderSide(TOP);
    
    cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
    cell2.disableBorderSide(RIGHT); 
    cell3.disableBorderSide(RIGHT);
      cell4.disableBorderSide(RIGHT);         // sets border width to 3 units
    cell5 .disableBorderSide(RIGHT);
    cell6 .disableBorderSide(RIGHT);
     cell7 .disableBorderSide(RIGHT);         // sets border width to 3 units
    cell8.disableBorderSide(RIGHT);
    cell9 .disableBorderSide(RIGHT);
     cell10 .disableBorderSide(RIGHT);         // sets border width to 3 units
    cell11.disableBorderSide(RIGHT);
    cell12 .disableBorderSide(RIGHT);
  
     cell1.disableBorderSide(BOTTOM);      // sets border width to 3 units
    cell2.disableBorderSide(BOTTOM); 
    cell3.disableBorderSide(BOTTOM); 
    cell1.disableBorderSide(BOTTOM);         // sets border width to 3 units
    cell2.disableBorderSide(BOTTOM); 
    cell3.disableBorderSide(BOTTOM);
      cell4.disableBorderSide(BOTTOM);         // sets border width to 3 units
    cell5 .disableBorderSide(BOTTOM);
    cell6 .disableBorderSide(BOTTOM);
     cell7 .disableBorderSide(BOTTOM);         // sets border width to 3 units
    cell8.disableBorderSide(BOTTOM);
    cell9 .disableBorderSide(BOTTOM);
     cell10 .disableBorderSide(BOTTOM);         // sets border width to 3 units
    cell11.disableBorderSide(BOTTOM);
    cell12 .disableBorderSide(BOTTOM);
    
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
      public  PdfPTable createTheTopPart(String accountNumber){
          
       PdfPTable table = new PdfPTable(4);
       table.setWidthPercentage(100);
       float[] columnWidths = {60f, 30f, 60f,30f};
       Font font1 = new Font(Font.FontFamily.COURIER  , 14, Font.NORMAL);
       Font font2 = new Font(Font.FontFamily.COURIER  , 14, Font.BOLD);
       Font font3 = new Font(Font.FontFamily.COURIER  , 14, Font.ITALIC);
       Font font4 = new Font(Font.FontFamily.COURIER  , 14, Font.BOLDITALIC);
    try {
        table.setWidths(columnWidths);
        
    } catch (DocumentException ex) {
        Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
    }
   PdfPCell cellEmpty= new PdfPCell(new Paragraph("",font1));
      cellEmpty.setFixedHeight(26.2f);
     cellEmpty.disableBorderSide(LEFT);
     cellEmpty.disableBorderSide(TOP);
     cellEmpty.disableBorderSide(RIGHT);
     cellEmpty.disableBorderSide(BOTTOM);
     cellEmpty.setPadding(.5f);
     cellEmpty.setBorderColorBottom(BaseColor.WHITE);
     cellEmpty.setBorderColorTop(BaseColor.WHITE);
     cellEmpty.setBorderColorLeft(BaseColor.WHITE);
     cellEmpty.setBorderColorRight(BaseColor.WHITE);
     cellEmpty.setVerticalAlignment(Element.ALIGN_TOP); 
   
  Map<Integer, Object> topDate=rdb.loanTopItems(accountNumber);
//      table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
      PdfPCell cellLeve5X= new PdfPCell(new Paragraph("Total Instalments:",font2));
      cellLeve5X.setFixedHeight(26.2f);
      cellLeve5X.disableBorderSide(LEFT);
      cellLeve5X.disableBorderSide(TOP);
      cellLeve5X.disableBorderSide(RIGHT);
      cellLeve5X.disableBorderSide(BOTTOM);
      cellLeve5X.setPadding(.5f);
      cellLeve5X.setBorderColorBottom(BaseColor.WHITE);
      cellLeve5X.setBorderColorTop(BaseColor.WHITE);
      cellLeve5X.setBorderColorLeft(BaseColor.WHITE);
      cellLeve5X.setBorderColorRight(BaseColor.WHITE);
      cellLeve5X.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cellLeve5X);
        PdfPCell cela= new PdfPCell(new Paragraph(topDate.get(0).toString(),font3));
      cela.setFixedHeight(26.2f);
      cela.disableBorderSide(LEFT);
      cela.disableBorderSide(TOP);
      cela.disableBorderSide(RIGHT);
      cela.disableBorderSide(BOTTOM);
      cela.setPadding(.5f);
      cela.setBorderColorBottom(BaseColor.WHITE);
      cela.setBorderColorTop(BaseColor.WHITE);
      cela.setBorderColorLeft(BaseColor.WHITE);
      cela.setBorderColorRight(BaseColor.WHITE);
      cela.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cela);  
        PdfPCell celb= new PdfPCell(new Paragraph("Total Loan Amount:",font2));
      celb.setFixedHeight(26.2f);
      celb.disableBorderSide(LEFT);
      celb.disableBorderSide(TOP);
      celb.disableBorderSide(RIGHT);
      celb.disableBorderSide(BOTTOM);
      celb.setPadding(.5f);
      celb.setBorderColorBottom(BaseColor.WHITE);
      celb.setBorderColorTop(BaseColor.WHITE);
      celb.setBorderColorLeft(BaseColor.WHITE);
      celb.setBorderColorRight(BaseColor.WHITE);
      celb.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celb); 

       PdfPCell celc= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(topDate.get(1).toString()),font3));
      celc.setFixedHeight(26.2f);
      celc.disableBorderSide(LEFT);
      celc.disableBorderSide(TOP);
      celc.disableBorderSide(RIGHT);
      celc.disableBorderSide(BOTTOM);
      celc.setPadding(.5f);
      celc.setBorderColorBottom(BaseColor.WHITE);
      celc.setBorderColorTop(BaseColor.WHITE);
      celc.setBorderColorLeft(BaseColor.WHITE);
      celc.setBorderColorRight(BaseColor.WHITE);
      celc.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celc);
//         table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
        PdfPCell celd= new PdfPCell(new Paragraph("Instalment Start Date:",font1));
     celd.setFixedHeight(26.2f);
      celd.disableBorderSide(LEFT);
      celd.disableBorderSide(TOP);
      celd.disableBorderSide(RIGHT);
      celd.disableBorderSide(BOTTOM);
      celd.setPadding(.5f);
      celd.setBorderColorBottom(BaseColor.WHITE);
      celd.setBorderColorTop(BaseColor.WHITE);
      celd.setBorderColorLeft(BaseColor.WHITE);
      celd.setBorderColorRight(BaseColor.WHITE);
      celd.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celd); 
         PdfPCell cele= new PdfPCell(new Paragraph(ffm.fromDatabaseWithDashSeperatorBeginningWithYear(topDate.get(2).toString()),font3));
      cele.setFixedHeight(26.2f);
      cele.disableBorderSide(LEFT);
      cele.disableBorderSide(TOP);
      cele.disableBorderSide(RIGHT);
      cele.disableBorderSide(BOTTOM);
      cele.setPadding(.5f);
      cele.setBorderColorBottom(BaseColor.WHITE);
      cele.setBorderColorTop(BaseColor.WHITE);
      cele.setBorderColorLeft(BaseColor.WHITE);
      cele.setBorderColorRight(BaseColor.WHITE);
      cele.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cele);    
         PdfPCell celf= new PdfPCell(new Paragraph("Total Principal Amount:",font1));
     celf.setFixedHeight(26.2f);
      celf.disableBorderSide(LEFT);
      celf.disableBorderSide(TOP);
      celf.disableBorderSide(RIGHT);
      celf.disableBorderSide(BOTTOM);
      celf.setPadding(.5f);
      celf.setBorderColorBottom(BaseColor.WHITE);
      celf.setBorderColorTop(BaseColor.WHITE);
      celf.setBorderColorLeft(BaseColor.WHITE);
      celf.setBorderColorRight(BaseColor.WHITE);
      celf.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celf); 
         PdfPCell celg= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(topDate.get(3).toString()),font3));
      celg.setFixedHeight(26.2f);
      celg.disableBorderSide(LEFT);
      celg.disableBorderSide(TOP);
      celg.disableBorderSide(RIGHT);
      celg.disableBorderSide(BOTTOM);
      celg.setPadding(.5f);
      celg.setBorderColorBottom(BaseColor.WHITE);
      celg.setBorderColorTop(BaseColor.WHITE);
      celg.setBorderColorLeft(BaseColor.WHITE);
      celg.setBorderColorRight(BaseColor.WHITE);
      celg.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celg);    
//          table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
        PdfPCell celh= new PdfPCell(new Paragraph("Next Instalment Date:",font2));
     celh.setFixedHeight(26.2f);
     celh.disableBorderSide(LEFT);
      celh.disableBorderSide(TOP);
      celh.disableBorderSide(RIGHT);
      celh.disableBorderSide(BOTTOM);
     celh.setPadding(.5f);
     celh.setBorderColorBottom(BaseColor.WHITE);
     celh.setBorderColorTop(BaseColor.WHITE);
     celh.setBorderColorLeft(BaseColor.WHITE);
    celh.setBorderColorRight(BaseColor.WHITE);
     celh.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celh); 
        PdfPCell celi= new PdfPCell(new Paragraph(ffm.fromDatabaseWithDashSeperatorBeginningWithYear(topDate.get(4).toString()),font3));
      celi.setFixedHeight(26.2f);
      celi.disableBorderSide(LEFT);
      celi.disableBorderSide(TOP);
      celi.disableBorderSide(RIGHT);
      celi.disableBorderSide(BOTTOM);
      celi.setPadding(.5f);
      celi.setBorderColorBottom(BaseColor.WHITE);
      celi.setBorderColorTop(BaseColor.WHITE);
      celi.setBorderColorLeft(BaseColor.WHITE);
      celi.setBorderColorRight(BaseColor.WHITE);
      celi.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celi);  
       
        PdfPCell celj= new PdfPCell(new Paragraph("Total Interest Amount:",font2));
     celj.setFixedHeight(26.2f);
     celj.disableBorderSide(LEFT);
     celj.disableBorderSide(TOP);
     celj.disableBorderSide(RIGHT);
     celj.disableBorderSide(BOTTOM);
     celj.setPadding(.5f);
     celj.setBorderColorBottom(BaseColor.WHITE);
     celj.setBorderColorTop(BaseColor.WHITE);
     celj.setBorderColorLeft(BaseColor.WHITE);
    celj.setBorderColorRight(BaseColor.WHITE);
     celj.setVerticalAlignment(Element.ALIGN_TOP);
     table.addCell(celj);
     
      PdfPCell celk= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(topDate.get(5).toString()),font3));
      celk.setFixedHeight(26.2f);
      celk.disableBorderSide(LEFT);
      celk.disableBorderSide(TOP);
      celk.disableBorderSide(RIGHT);
      celk.disableBorderSide(BOTTOM);
      celk.setPadding(.5f);
      celk.setBorderColorBottom(BaseColor.WHITE);
      celk.setBorderColorTop(BaseColor.WHITE);
      celk.setBorderColorLeft(BaseColor.WHITE);
      celk.setBorderColorRight(BaseColor.WHITE);
      celk.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celk);    
//       table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
        PdfPCell cella= new PdfPCell(new Paragraph("Instalement End Date:",font1));
    cella.setFixedHeight(26.2f);
     cella.disableBorderSide(LEFT);
     cella.disableBorderSide(TOP);
     cella.disableBorderSide(RIGHT);
     cella.disableBorderSide(BOTTOM);
     cella.setPadding(.5f);
     cella.setBorderColorBottom(BaseColor.WHITE);
    cella.setBorderColorTop(BaseColor.WHITE);
    cella.setBorderColorLeft(BaseColor.WHITE);
    cella.setBorderColorRight(BaseColor.WHITE);
    cella.setVerticalAlignment(Element.ALIGN_TOP);
     table.addCell(cella);
        
     PdfPCell celm= new PdfPCell(new Paragraph(ffm.fromDatabaseWithDashSeperatorBeginningWithYear(topDate.get(6).toString()),font3));
      celm.setFixedHeight(26.2f);
      celm.disableBorderSide(LEFT);
      celm.disableBorderSide(TOP);
      celm.disableBorderSide(RIGHT);
      celm.disableBorderSide(BOTTOM);
      celm.setPadding(.5f);
      celm.setBorderColorBottom(BaseColor.WHITE);
      celm.setBorderColorTop(BaseColor.WHITE);
      celm.setBorderColorLeft(BaseColor.WHITE);
      celm.setBorderColorRight(BaseColor.WHITE);
      celm.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celm); 
       
     PdfPCell celln= new PdfPCell(new Paragraph("Initial Instalment:",font1));
    celln.setFixedHeight(26.2f);
     celln.disableBorderSide(LEFT);
    celln.disableBorderSide(TOP);
    celln.disableBorderSide(RIGHT);
    celln.disableBorderSide(BOTTOM);
    celln.setPadding(.5f);
    celln.setBorderColorBottom(BaseColor.WHITE);
    celln.setBorderColorTop(BaseColor.WHITE);
    celln.setBorderColorLeft(BaseColor.WHITE);
    celln.setBorderColorRight(BaseColor.WHITE);
   celln.setVerticalAlignment(Element.ALIGN_TOP);
     table.addCell(celln);
       
     PdfPCell celo= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(topDate.get(7).toString()),font3));
     celo.setFixedHeight(26.2f);
      celo.disableBorderSide(LEFT);
      celo.disableBorderSide(TOP);
      celo.disableBorderSide(RIGHT);
      celo.disableBorderSide(BOTTOM);
      celo.setPadding(.5f);
      celo.setBorderColorBottom(BaseColor.WHITE);
      celo.setBorderColorTop(BaseColor.WHITE);
      celo.setBorderColorLeft(BaseColor.WHITE);
      celo.setBorderColorRight(BaseColor.WHITE);
      celo.setVerticalAlignment(Element.ALIGN_TOP);
      table.addCell(celo);
//        table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
       PdfPCell cellp= new PdfPCell(new Paragraph("Remaining Instalments:",font2));
    cellp.setFixedHeight(26.2f);
     cellp.disableBorderSide(LEFT);
   cellp.disableBorderSide(TOP);
    cellp.disableBorderSide(RIGHT);
   cellp.disableBorderSide(BOTTOM);
    cellp.setPadding(.5f);
   cellp.setBorderColorBottom(BaseColor.WHITE);
   cellp.setBorderColorTop(BaseColor.WHITE);
   cellp.setBorderColorLeft(BaseColor.WHITE);
   cellp.setBorderColorRight(BaseColor.WHITE);
  cellp.setVerticalAlignment(Element.ALIGN_TOP);
     table.addCell(cellp);
     
     if(topDate.get(8)==null){
     PdfPCell celaa= new PdfPCell(new Paragraph(topDate.get(0).toString(),font3));
      celaa.setFixedHeight(26.2f);
      celaa.disableBorderSide(LEFT);
      celaa.disableBorderSide(TOP);
      celaa.disableBorderSide(RIGHT);
      celaa.disableBorderSide(BOTTOM);
      celaa.setPadding(.5f);
      celaa.setBorderColorBottom(BaseColor.WHITE);
      celaa.setBorderColorTop(BaseColor.WHITE);
      celaa.setBorderColorLeft(BaseColor.WHITE);
      celaa.setBorderColorRight(BaseColor.WHITE);
      celaa.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celaa);  
      } else{
      PdfPCell celaab= new PdfPCell(new Paragraph(topDate.get(8).toString(),font3));
      celaab.setFixedHeight(26.2f);
      celaab.disableBorderSide(LEFT);
      celaab.disableBorderSide(TOP);
      celaab.disableBorderSide(RIGHT);
      celaab.disableBorderSide(BOTTOM);
      celaab.setPadding(.5f);
      celaab.setBorderColorBottom(BaseColor.WHITE);
      celaab.setBorderColorTop(BaseColor.WHITE);
      celaab.setBorderColorLeft(BaseColor.WHITE);
      celaab.setBorderColorRight(BaseColor.WHITE);
      celaab.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celaab);  
   
     }
        PdfPCell cellps= new PdfPCell(new Paragraph("Remaining Loan Amount:",font2));
    cellps.setFixedHeight(26.2f);
     cellps.disableBorderSide(LEFT);
   cellps.disableBorderSide(TOP);
    cellps.disableBorderSide(RIGHT);
   cellps.disableBorderSide(BOTTOM);
    cellps.setPadding(.5f);
   cellps.setBorderColorBottom(BaseColor.WHITE);
   cellps.setBorderColorTop(BaseColor.WHITE);
   cellps.setBorderColorLeft(BaseColor.WHITE);
   cellps.setBorderColorRight(BaseColor.WHITE);
  cellps.setVerticalAlignment(Element.ALIGN_TOP);
     table.addCell(cellps);
       
     if(topDate.get(9)==null){
     PdfPCell celaax= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(topDate.get(1).toString()),font3));
      celaax.setFixedHeight(26.2f);
      celaax.disableBorderSide(LEFT);
      celaax.disableBorderSide(TOP);
      celaax.disableBorderSide(RIGHT);
      celaax.disableBorderSide(BOTTOM);
      celaax.setPadding(.5f);
      celaax.setBorderColorBottom(BaseColor.WHITE);
      celaax.setBorderColorTop(BaseColor.WHITE);
      celaax.setBorderColorLeft(BaseColor.WHITE);
      celaax.setBorderColorRight(BaseColor.WHITE);
      celaax.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celaax);  
      } else{
      PdfPCell celaab= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(topDate.get(9).toString()),font3));
      celaab.setFixedHeight(26.2f);
      celaab.disableBorderSide(LEFT);
      celaab.disableBorderSide(TOP);
      celaab.disableBorderSide(RIGHT);
      celaab.disableBorderSide(BOTTOM);
      celaab.setPadding(.5f);
      celaab.setBorderColorBottom(BaseColor.WHITE);
      celaab.setBorderColorTop(BaseColor.WHITE);
      celaab.setBorderColorLeft(BaseColor.WHITE);
      celaab.setBorderColorRight(BaseColor.WHITE);
      celaab.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celaab);  
       }

     
      PdfPCell cellps1= new PdfPCell(new Paragraph("Loan Penalty Amount:",font2));
    cellps1.setFixedHeight(26.2f);
     cellps1.disableBorderSide(LEFT);
   cellps1.disableBorderSide(TOP);
    cellps1.disableBorderSide(RIGHT);
   cellps1.disableBorderSide(BOTTOM);
    cellps1.setPadding(.5f);
   cellps1.setBorderColorBottom(BaseColor.WHITE);
   cellps1.setBorderColorTop(BaseColor.WHITE);
   cellps1.setBorderColorLeft(BaseColor.WHITE);
   cellps1.setBorderColorRight(BaseColor.WHITE);
  cellps1.setVerticalAlignment(Element.ALIGN_TOP);
     table.addCell(cellps1);
       
     if(topDate.get(10)==null){
     PdfPCell celaax1= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(topDate.get(1).toString()),font3));
      celaax1.setFixedHeight(26.2f);
      celaax1.disableBorderSide(LEFT);
      celaax1.disableBorderSide(TOP);
      celaax1.disableBorderSide(RIGHT);
      celaax1.disableBorderSide(BOTTOM);
      celaax1.setPadding(.5f);
      celaax1.setBorderColorBottom(BaseColor.WHITE);
      celaax1.setBorderColorTop(BaseColor.WHITE);
      celaax1.setBorderColorLeft(BaseColor.WHITE);
      celaax1.setBorderColorRight(BaseColor.WHITE);
      celaax1.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celaax1);  
      } else{
      PdfPCell celaab1= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(topDate.get(10).toString()),font3));
      celaab1.setFixedHeight(26.2f);
      celaab1.disableBorderSide(LEFT);
      celaab1.disableBorderSide(TOP);
      celaab1.disableBorderSide(RIGHT);
      celaab1.disableBorderSide(BOTTOM);
      celaab1.setPadding(.5f);
      celaab1.setBorderColorBottom(BaseColor.WHITE);
      celaab1.setBorderColorTop(BaseColor.WHITE);
      celaab1.setBorderColorLeft(BaseColor.WHITE);
      celaab1.setBorderColorRight(BaseColor.WHITE);
      celaab1.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celaab1);  
       }

     
     
     
      PdfPCell cellps1x= new PdfPCell(new Paragraph("",font2));
    cellps1x.setFixedHeight(26.2f);
     cellps1x.disableBorderSide(LEFT);
   cellps1x.disableBorderSide(TOP);
    cellps1x.disableBorderSide(RIGHT);
   cellps1x.disableBorderSide(BOTTOM);
    cellps1x.setPadding(.5f);
   cellps1x.setBorderColorBottom(BaseColor.WHITE);
   cellps1x.setBorderColorTop(BaseColor.WHITE);
   cellps1x.setBorderColorLeft(BaseColor.WHITE);
   cellps1x.setBorderColorRight(BaseColor.WHITE);
  cellps1x.setVerticalAlignment(Element.ALIGN_TOP);
     table.addCell(cellps1x);
       
     if(topDate.get(10)==null){
     PdfPCell celaax1x= new PdfPCell(new Paragraph("",font3));
      celaax1x.setFixedHeight(26.2f);
      celaax1x.disableBorderSide(LEFT);
      celaax1x.disableBorderSide(TOP);
      celaax1x.disableBorderSide(RIGHT);
      celaax1x.disableBorderSide(BOTTOM);
      celaax1x.setPadding(.5f);
      celaax1x.setBorderColorBottom(BaseColor.WHITE);
      celaax1x.setBorderColorTop(BaseColor.WHITE);
      celaax1x.setBorderColorLeft(BaseColor.WHITE);
      celaax1x.setBorderColorRight(BaseColor.WHITE);
      celaax1x.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celaax1x);  
      } else{
      PdfPCell celaab1x= new PdfPCell(new Paragraph("",font3));
      celaab1x.setFixedHeight(26.2f);
      celaab1x.disableBorderSide(LEFT);
      celaab1x.disableBorderSide(TOP);
      celaab1x.disableBorderSide(RIGHT);
      celaab1x.disableBorderSide(BOTTOM);
      celaab1x.setPadding(.5f);
      celaab1x.setBorderColorBottom(BaseColor.WHITE);
      celaab1x.setBorderColorTop(BaseColor.WHITE);
      celaab1x.setBorderColorLeft(BaseColor.WHITE);
      celaab1x.setBorderColorRight(BaseColor.WHITE);
      celaab1x.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celaab1x);  
       }
      return table;

       }
      
       public  PdfPTable createTheTopPartclosed(String loanid){
          
       PdfPTable table = new PdfPTable(4);
       table.setWidthPercentage(100);
       float[] columnWidths = {60f, 30f, 60f,30f};
       Font font1 = new Font(Font.FontFamily.COURIER  , 14, Font.NORMAL);
       Font font2 = new Font(Font.FontFamily.COURIER  , 14, Font.BOLD);
       Font font3 = new Font(Font.FontFamily.COURIER  , 14, Font.ITALIC);
       Font font4 = new Font(Font.FontFamily.COURIER  , 14, Font.BOLDITALIC);
    try {
        table.setWidths(columnWidths);
        
    } catch (DocumentException ex) {
        Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
    }
   PdfPCell cellEmpty= new PdfPCell(new Paragraph("",font1));
      cellEmpty.setFixedHeight(26.2f);
     cellEmpty.disableBorderSide(LEFT);
     cellEmpty.disableBorderSide(TOP);
     cellEmpty.disableBorderSide(RIGHT);
     cellEmpty.disableBorderSide(BOTTOM);
     cellEmpty.setPadding(.5f);
     cellEmpty.setBorderColorBottom(BaseColor.WHITE);
     cellEmpty.setBorderColorTop(BaseColor.WHITE);
     cellEmpty.setBorderColorLeft(BaseColor.WHITE);
     cellEmpty.setBorderColorRight(BaseColor.WHITE);
     cellEmpty.setVerticalAlignment(Element.ALIGN_TOP); 
   
  Map<Integer, Object> topDate=rdb.loanTopItemsclosed(loanid);
//      table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
      PdfPCell cellLeve5X= new PdfPCell(new Paragraph("Total Instalments:",font2));
      cellLeve5X.setFixedHeight(26.2f);
      cellLeve5X.disableBorderSide(LEFT);
      cellLeve5X.disableBorderSide(TOP);
      cellLeve5X.disableBorderSide(RIGHT);
      cellLeve5X.disableBorderSide(BOTTOM);
      cellLeve5X.setPadding(.5f);
      cellLeve5X.setBorderColorBottom(BaseColor.WHITE);
      cellLeve5X.setBorderColorTop(BaseColor.WHITE);
      cellLeve5X.setBorderColorLeft(BaseColor.WHITE);
      cellLeve5X.setBorderColorRight(BaseColor.WHITE);
      cellLeve5X.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cellLeve5X);
        PdfPCell cela= new PdfPCell(new Paragraph(topDate.get(0).toString(),font3));
      cela.setFixedHeight(26.2f);
      cela.disableBorderSide(LEFT);
      cela.disableBorderSide(TOP);
      cela.disableBorderSide(RIGHT);
      cela.disableBorderSide(BOTTOM);
      cela.setPadding(.5f);
      cela.setBorderColorBottom(BaseColor.WHITE);
      cela.setBorderColorTop(BaseColor.WHITE);
      cela.setBorderColorLeft(BaseColor.WHITE);
      cela.setBorderColorRight(BaseColor.WHITE);
      cela.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cela);  
        PdfPCell celb= new PdfPCell(new Paragraph("Total Loan Amount:",font2));
      celb.setFixedHeight(26.2f);
      celb.disableBorderSide(LEFT);
      celb.disableBorderSide(TOP);
      celb.disableBorderSide(RIGHT);
      celb.disableBorderSide(BOTTOM);
      celb.setPadding(.5f);
      celb.setBorderColorBottom(BaseColor.WHITE);
      celb.setBorderColorTop(BaseColor.WHITE);
      celb.setBorderColorLeft(BaseColor.WHITE);
      celb.setBorderColorRight(BaseColor.WHITE);
      celb.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celb); 

       PdfPCell celc= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(topDate.get(1).toString()),font3));
      celc.setFixedHeight(26.2f);
      celc.disableBorderSide(LEFT);
      celc.disableBorderSide(TOP);
      celc.disableBorderSide(RIGHT);
      celc.disableBorderSide(BOTTOM);
      celc.setPadding(.5f);
      celc.setBorderColorBottom(BaseColor.WHITE);
      celc.setBorderColorTop(BaseColor.WHITE);
      celc.setBorderColorLeft(BaseColor.WHITE);
      celc.setBorderColorRight(BaseColor.WHITE);
      celc.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celc);
//         table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
        PdfPCell celd= new PdfPCell(new Paragraph("Instalment Start Date:",font1));
     celd.setFixedHeight(26.2f);
      celd.disableBorderSide(LEFT);
      celd.disableBorderSide(TOP);
      celd.disableBorderSide(RIGHT);
      celd.disableBorderSide(BOTTOM);
      celd.setPadding(.5f);
      celd.setBorderColorBottom(BaseColor.WHITE);
      celd.setBorderColorTop(BaseColor.WHITE);
      celd.setBorderColorLeft(BaseColor.WHITE);
      celd.setBorderColorRight(BaseColor.WHITE);
      celd.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celd); 
         PdfPCell cele= new PdfPCell(new Paragraph(ffm.fromDatabaseWithDashSeperatorBeginningWithYear(topDate.get(2).toString()),font3));
      cele.setFixedHeight(26.2f);
      cele.disableBorderSide(LEFT);
      cele.disableBorderSide(TOP);
      cele.disableBorderSide(RIGHT);
      cele.disableBorderSide(BOTTOM);
      cele.setPadding(.5f);
      cele.setBorderColorBottom(BaseColor.WHITE);
      cele.setBorderColorTop(BaseColor.WHITE);
      cele.setBorderColorLeft(BaseColor.WHITE);
      cele.setBorderColorRight(BaseColor.WHITE);
      cele.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cele);    
         PdfPCell celf= new PdfPCell(new Paragraph("Total Principal Amount:",font1));
     celf.setFixedHeight(26.2f);
      celf.disableBorderSide(LEFT);
      celf.disableBorderSide(TOP);
      celf.disableBorderSide(RIGHT);
      celf.disableBorderSide(BOTTOM);
      celf.setPadding(.5f);
      celf.setBorderColorBottom(BaseColor.WHITE);
      celf.setBorderColorTop(BaseColor.WHITE);
      celf.setBorderColorLeft(BaseColor.WHITE);
      celf.setBorderColorRight(BaseColor.WHITE);
      celf.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celf); 
         PdfPCell celg= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(topDate.get(3).toString()),font3));
      celg.setFixedHeight(26.2f);
      celg.disableBorderSide(LEFT);
      celg.disableBorderSide(TOP);
      celg.disableBorderSide(RIGHT);
      celg.disableBorderSide(BOTTOM);
      celg.setPadding(.5f);
      celg.setBorderColorBottom(BaseColor.WHITE);
      celg.setBorderColorTop(BaseColor.WHITE);
      celg.setBorderColorLeft(BaseColor.WHITE);
      celg.setBorderColorRight(BaseColor.WHITE);
      celg.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celg);    
//          table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
        PdfPCell celh= new PdfPCell(new Paragraph("Last Instalment Paid Date:",font2));
     celh.setFixedHeight(26.2f);
     celh.disableBorderSide(LEFT);
      celh.disableBorderSide(TOP);
      celh.disableBorderSide(RIGHT);
      celh.disableBorderSide(BOTTOM);
     celh.setPadding(.5f);
     celh.setBorderColorBottom(BaseColor.WHITE);
     celh.setBorderColorTop(BaseColor.WHITE);
     celh.setBorderColorLeft(BaseColor.WHITE);
    celh.setBorderColorRight(BaseColor.WHITE);
     celh.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celh); 
        PdfPCell celi= new PdfPCell(new Paragraph(ffm.fromDatabaseWithDashSeperatorBeginningWithYear(topDate.get(4).toString()),font3));
      celi.setFixedHeight(26.2f);
      celi.disableBorderSide(LEFT);
      celi.disableBorderSide(TOP);
      celi.disableBorderSide(RIGHT);
      celi.disableBorderSide(BOTTOM);
      celi.setPadding(.5f);
      celi.setBorderColorBottom(BaseColor.WHITE);
      celi.setBorderColorTop(BaseColor.WHITE);
      celi.setBorderColorLeft(BaseColor.WHITE);
      celi.setBorderColorRight(BaseColor.WHITE);
      celi.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celi);  
       
        PdfPCell celj= new PdfPCell(new Paragraph("Total Interest Amount:",font2));
     celj.setFixedHeight(26.2f);
     celj.disableBorderSide(LEFT);
     celj.disableBorderSide(TOP);
     celj.disableBorderSide(RIGHT);
     celj.disableBorderSide(BOTTOM);
     celj.setPadding(.5f);
     celj.setBorderColorBottom(BaseColor.WHITE);
     celj.setBorderColorTop(BaseColor.WHITE);
     celj.setBorderColorLeft(BaseColor.WHITE);
    celj.setBorderColorRight(BaseColor.WHITE);
     celj.setVerticalAlignment(Element.ALIGN_TOP);
     table.addCell(celj);
     
      PdfPCell celk= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(topDate.get(5).toString()),font3));
      celk.setFixedHeight(26.2f);
      celk.disableBorderSide(LEFT);
      celk.disableBorderSide(TOP);
      celk.disableBorderSide(RIGHT);
      celk.disableBorderSide(BOTTOM);
      celk.setPadding(.5f);
      celk.setBorderColorBottom(BaseColor.WHITE);
      celk.setBorderColorTop(BaseColor.WHITE);
      celk.setBorderColorLeft(BaseColor.WHITE);
      celk.setBorderColorRight(BaseColor.WHITE);
      celk.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celk);    
//       table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
        PdfPCell cella= new PdfPCell(new Paragraph("Instalement End Date:",font1));
    cella.setFixedHeight(26.2f);
     cella.disableBorderSide(LEFT);
     cella.disableBorderSide(TOP);
     cella.disableBorderSide(RIGHT);
     cella.disableBorderSide(BOTTOM);
     cella.setPadding(.5f);
     cella.setBorderColorBottom(BaseColor.WHITE);
    cella.setBorderColorTop(BaseColor.WHITE);
    cella.setBorderColorLeft(BaseColor.WHITE);
    cella.setBorderColorRight(BaseColor.WHITE);
    cella.setVerticalAlignment(Element.ALIGN_TOP);
     table.addCell(cella);
        
     PdfPCell celm= new PdfPCell(new Paragraph(ffm.fromDatabaseWithDashSeperatorBeginningWithYear(topDate.get(6).toString()),font3));
      celm.setFixedHeight(26.2f);
      celm.disableBorderSide(LEFT);
      celm.disableBorderSide(TOP);
      celm.disableBorderSide(RIGHT);
      celm.disableBorderSide(BOTTOM);
      celm.setPadding(.5f);
      celm.setBorderColorBottom(BaseColor.WHITE);
      celm.setBorderColorTop(BaseColor.WHITE);
      celm.setBorderColorLeft(BaseColor.WHITE);
      celm.setBorderColorRight(BaseColor.WHITE);
      celm.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celm); 
       
     PdfPCell celln= new PdfPCell(new Paragraph("Initial Instalment:",font1));
    celln.setFixedHeight(26.2f);
     celln.disableBorderSide(LEFT);
    celln.disableBorderSide(TOP);
    celln.disableBorderSide(RIGHT);
    celln.disableBorderSide(BOTTOM);
    celln.setPadding(.5f);
    celln.setBorderColorBottom(BaseColor.WHITE);
    celln.setBorderColorTop(BaseColor.WHITE);
    celln.setBorderColorLeft(BaseColor.WHITE);
    celln.setBorderColorRight(BaseColor.WHITE);
   celln.setVerticalAlignment(Element.ALIGN_TOP);
     table.addCell(celln);
       
     PdfPCell celo= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(topDate.get(7).toString()),font3));
     celo.setFixedHeight(26.2f);
      celo.disableBorderSide(LEFT);
      celo.disableBorderSide(TOP);
      celo.disableBorderSide(RIGHT);
      celo.disableBorderSide(BOTTOM);
      celo.setPadding(.5f);
      celo.setBorderColorBottom(BaseColor.WHITE);
      celo.setBorderColorTop(BaseColor.WHITE);
      celo.setBorderColorLeft(BaseColor.WHITE);
      celo.setBorderColorRight(BaseColor.WHITE);
      celo.setVerticalAlignment(Element.ALIGN_TOP);
      table.addCell(celo);
//        table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
//      table.addCell(cellEmpty);
       PdfPCell cellp= new PdfPCell(new Paragraph("Remaining Instalments:",font2));
    cellp.setFixedHeight(26.2f);
     cellp.disableBorderSide(LEFT);
   cellp.disableBorderSide(TOP);
    cellp.disableBorderSide(RIGHT);
   cellp.disableBorderSide(BOTTOM);
    cellp.setPadding(.5f);
   cellp.setBorderColorBottom(BaseColor.WHITE);
   cellp.setBorderColorTop(BaseColor.WHITE);
   cellp.setBorderColorLeft(BaseColor.WHITE);
   cellp.setBorderColorRight(BaseColor.WHITE);
  cellp.setVerticalAlignment(Element.ALIGN_TOP);
     table.addCell(cellp);
     
     if(topDate.get(8)==null){
     PdfPCell celaa= new PdfPCell(new Paragraph(topDate.get(0).toString(),font3));
      celaa.setFixedHeight(26.2f);
      celaa.disableBorderSide(LEFT);
      celaa.disableBorderSide(TOP);
      celaa.disableBorderSide(RIGHT);
      celaa.disableBorderSide(BOTTOM);
      celaa.setPadding(.5f);
      celaa.setBorderColorBottom(BaseColor.WHITE);
      celaa.setBorderColorTop(BaseColor.WHITE);
      celaa.setBorderColorLeft(BaseColor.WHITE);
      celaa.setBorderColorRight(BaseColor.WHITE);
      celaa.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celaa);  
      } else{
      PdfPCell celaab= new PdfPCell(new Paragraph(topDate.get(8).toString(),font3));
      celaab.setFixedHeight(26.2f);
      celaab.disableBorderSide(LEFT);
      celaab.disableBorderSide(TOP);
      celaab.disableBorderSide(RIGHT);
      celaab.disableBorderSide(BOTTOM);
      celaab.setPadding(.5f);
      celaab.setBorderColorBottom(BaseColor.WHITE);
      celaab.setBorderColorTop(BaseColor.WHITE);
      celaab.setBorderColorLeft(BaseColor.WHITE);
      celaab.setBorderColorRight(BaseColor.WHITE);
      celaab.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celaab);  
   
     }
        PdfPCell cellps= new PdfPCell(new Paragraph("Remaining Loan Amount:",font2));
    cellps.setFixedHeight(26.2f);
     cellps.disableBorderSide(LEFT);
   cellps.disableBorderSide(TOP);
    cellps.disableBorderSide(RIGHT);
   cellps.disableBorderSide(BOTTOM);
    cellps.setPadding(.5f);
   cellps.setBorderColorBottom(BaseColor.WHITE);
   cellps.setBorderColorTop(BaseColor.WHITE);
   cellps.setBorderColorLeft(BaseColor.WHITE);
   cellps.setBorderColorRight(BaseColor.WHITE);
  cellps.setVerticalAlignment(Element.ALIGN_TOP);
     table.addCell(cellps);
       
     if(topDate.get(9)==null){
     PdfPCell celaax= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(topDate.get(1).toString()),font3));
      celaax.setFixedHeight(26.2f);
      celaax.disableBorderSide(LEFT);
      celaax.disableBorderSide(TOP);
      celaax.disableBorderSide(RIGHT);
      celaax.disableBorderSide(BOTTOM);
      celaax.setPadding(.5f);
      celaax.setBorderColorBottom(BaseColor.WHITE);
      celaax.setBorderColorTop(BaseColor.WHITE);
      celaax.setBorderColorLeft(BaseColor.WHITE);
      celaax.setBorderColorRight(BaseColor.WHITE);
      celaax.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celaax);  
      } else{
      PdfPCell celaab= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(topDate.get(9).toString()),font3));
      celaab.setFixedHeight(26.2f);
      celaab.disableBorderSide(LEFT);
      celaab.disableBorderSide(TOP);
      celaab.disableBorderSide(RIGHT);
      celaab.disableBorderSide(BOTTOM);
      celaab.setPadding(.5f);
      celaab.setBorderColorBottom(BaseColor.WHITE);
      celaab.setBorderColorTop(BaseColor.WHITE);
      celaab.setBorderColorLeft(BaseColor.WHITE);
      celaab.setBorderColorRight(BaseColor.WHITE);
      celaab.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celaab);  
       }

     
      PdfPCell cellps1= new PdfPCell(new Paragraph("Loan Penalty Amount:",font2));
    cellps1.setFixedHeight(26.2f);
     cellps1.disableBorderSide(LEFT);
   cellps1.disableBorderSide(TOP);
    cellps1.disableBorderSide(RIGHT);
   cellps1.disableBorderSide(BOTTOM);
    cellps1.setPadding(.5f);
   cellps1.setBorderColorBottom(BaseColor.WHITE);
   cellps1.setBorderColorTop(BaseColor.WHITE);
   cellps1.setBorderColorLeft(BaseColor.WHITE);
   cellps1.setBorderColorRight(BaseColor.WHITE);
  cellps1.setVerticalAlignment(Element.ALIGN_TOP);
     table.addCell(cellps1);
       
     if(topDate.get(10)==null){
     PdfPCell celaax1= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(topDate.get(1).toString()),font3));
      celaax1.setFixedHeight(26.2f);
      celaax1.disableBorderSide(LEFT);
      celaax1.disableBorderSide(TOP);
      celaax1.disableBorderSide(RIGHT);
      celaax1.disableBorderSide(BOTTOM);
      celaax1.setPadding(.5f);
      celaax1.setBorderColorBottom(BaseColor.WHITE);
      celaax1.setBorderColorTop(BaseColor.WHITE);
      celaax1.setBorderColorLeft(BaseColor.WHITE);
      celaax1.setBorderColorRight(BaseColor.WHITE);
      celaax1.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celaax1);  
      } else{
      PdfPCell celaab1= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(topDate.get(10).toString()),font3));
      celaab1.setFixedHeight(26.2f);
      celaab1.disableBorderSide(LEFT);
      celaab1.disableBorderSide(TOP);
      celaab1.disableBorderSide(RIGHT);
      celaab1.disableBorderSide(BOTTOM);
      celaab1.setPadding(.5f);
      celaab1.setBorderColorBottom(BaseColor.WHITE);
      celaab1.setBorderColorTop(BaseColor.WHITE);
      celaab1.setBorderColorLeft(BaseColor.WHITE);
      celaab1.setBorderColorRight(BaseColor.WHITE);
      celaab1.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celaab1);  
       }

     
     
     
      PdfPCell cellps1x= new PdfPCell(new Paragraph("Loan Penalty Amount:",font2));
    cellps1x.setFixedHeight(26.2f);
     cellps1x.disableBorderSide(LEFT);
   cellps1x.disableBorderSide(TOP);
    cellps1x.disableBorderSide(RIGHT);
   cellps1x.disableBorderSide(BOTTOM);
    cellps1x.setPadding(.5f);
   cellps1x.setBorderColorBottom(BaseColor.WHITE);
   cellps1x.setBorderColorTop(BaseColor.WHITE);
   cellps1x.setBorderColorLeft(BaseColor.WHITE);
   cellps1x.setBorderColorRight(BaseColor.WHITE);
  cellps1x.setVerticalAlignment(Element.ALIGN_TOP);
     table.addCell(cellps1x);
       
     if(topDate.get(10)==null){
     PdfPCell celaax1x= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(topDate.get(1).toString()),font3));
      celaax1x.setFixedHeight(26.2f);
      celaax1x.disableBorderSide(LEFT);
      celaax1x.disableBorderSide(TOP);
      celaax1x.disableBorderSide(RIGHT);
      celaax1x.disableBorderSide(BOTTOM);
      celaax1x.setPadding(.5f);
      celaax1x.setBorderColorBottom(BaseColor.WHITE);
      celaax1x.setBorderColorTop(BaseColor.WHITE);
      celaax1x.setBorderColorLeft(BaseColor.WHITE);
      celaax1x.setBorderColorRight(BaseColor.WHITE);
      celaax1x.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celaax1x);  
      } else{
      PdfPCell celaab1x= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(topDate.get(10).toString()),font3));
      celaab1x.setFixedHeight(26.2f);
      celaab1x.disableBorderSide(LEFT);
      celaab1x.disableBorderSide(TOP);
      celaab1x.disableBorderSide(RIGHT);
      celaab1x.disableBorderSide(BOTTOM);
      celaab1x.setPadding(.5f);
      celaab1x.setBorderColorBottom(BaseColor.WHITE);
      celaab1x.setBorderColorTop(BaseColor.WHITE);
      celaab1x.setBorderColorLeft(BaseColor.WHITE);
      celaab1x.setBorderColorRight(BaseColor.WHITE);
      celaab1x.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(celaab1x);  
       }
      return table;

       }
       public  PdfPTable createTheBottomPart(String accountNumber){
          
       PdfPTable table = new PdfPTable(7);
       table.setWidthPercentage(100);
       float[] columnWidths = {17f, 30f, 30f,30f, 15f,30f,30f};
       Font font1 = new Font(Font.FontFamily.COURIER  , 14, Font.NORMAL);
       Font font2 = new Font(Font.FontFamily.COURIER  , 15, Font.BOLD);
       Font font3 = new Font(Font.FontFamily.COURIER  , 14, Font.ITALIC);
       Font font4 = new Font(Font.FontFamily.COURIER  , 14, Font.BOLDITALIC);
    try {
        table.setWidths(columnWidths);
        
    } catch (DocumentException ex) {
        Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
    }
   PdfPCell cellEmpty= new PdfPCell(new Paragraph("",font1));
      cellEmpty.setFixedHeight(16.2f);
     cellEmpty.disableBorderSide(LEFT);
     cellEmpty.disableBorderSide(TOP);
     cellEmpty.disableBorderSide(RIGHT);
     cellEmpty.disableBorderSide(BOTTOM);
     cellEmpty.setPadding(.5f);
     cellEmpty.setBorderColorBottom(BaseColor.WHITE);
     cellEmpty.setBorderColorTop(BaseColor.WHITE);
     cellEmpty.setBorderColorLeft(BaseColor.WHITE);
     cellEmpty.setBorderColorRight(BaseColor.WHITE);
     cellEmpty.setVerticalAlignment(Element.ALIGN_TOP); 
   
   PdfPCell cell1 = new PdfPCell(new Paragraph("S/n",font2));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Instalment Amnt",font2));
            PdfPCell cell3 = new PdfPCell(new Paragraph("O/S Instalment Amnt",font2));
            PdfPCell cell4 = new PdfPCell(new Paragraph("Instalment Amnt Paid",font2));
            PdfPCell cell5 = new PdfPCell(new Paragraph("Status",font2));
            PdfPCell cell6 = new PdfPCell(new Paragraph("Due Date",font2)); 
            PdfPCell cell7 = new PdfPCell(new Paragraph("Payment Date",font2));
            
           cell1.setBackgroundColor(BaseColor.GREEN);   //sets BG color to yellow.
           cell2.setBackgroundColor(BaseColor.GREEN); 
           cell3.setBackgroundColor(BaseColor.GREEN); 
           cell4.setBackgroundColor(BaseColor.GREEN); 
           cell5.setBackgroundColor(BaseColor.GREEN); 
           cell6.setBackgroundColor(BaseColor.GREEN);
              cell7.setBackgroundColor(BaseColor.GREEN);

           cell1.setBorderWidth (2f);         // sets border width to 3 units
           cell2.setBorderWidth (2f);
           cell3.setBorderWidth (2f);
           cell4.setBorderWidth (2f);
           cell5.setBorderWidth (2f);
           cell6.setBorderWidth (2f);
             cell7.setBorderWidth (2f);
           cell7.setBorderColorBottom(BaseColor.BLACK);
           
           
           cell1 .disableBorderSide(LEFT);         // sets border width to 3 units
           cell2 .disableBorderSide(LEFT);
           cell3 .disableBorderSide(LEFT);
           cell4 .disableBorderSide(LEFT);
           cell5 .disableBorderSide(LEFT);
           cell6 .disableBorderSide(LEFT);
           cell7.disableBorderSide(LEFT);
           
           cell1 .disableBorderSide(TOP);       // sets border width to 3 units
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
          int n=0; Map<Integer, List<Object>> dataBase=rdb.loanEntries(accountNumber);
           if(!dataBase.isEmpty()){
           while(n<dataBase.size()) {
           List realData=dataBase.get(n);int z=0;
           if(n==dataBase.size()-1){
            while(z<realData.size()){
           if(z==0){
               PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(),font2));
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
           if(z==1){
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(1).toString().replace(",", "")),font2));
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
            if(z==2){
         
            PdfPCell cellx3 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(2).toString()),font2));
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
            table.addCell(cellx3);
            }
            
             if(z==3){
       
            PdfPCell cellx4 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(3).toString())+"",font2));
         
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
            table.addCell(cellx4);
             }
              if(z==4){
                 
          PdfPCell cellx5 = new PdfPCell(new Paragraph(realData.get(4).toString(),font2)); 
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
            table.addCell(cellx5 );
              }
                if(z==5){
          
            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(5).toString(),font2)); 
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
                
               if(z==6){
          
            PdfPCell cellx7 = new PdfPCell(new Paragraph(realData.get(6).toString(),font2)); 
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
           
           }else{
           while(z<realData.size()){
           if(z==0){
               PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(),font1));
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
           if(z==1){
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(1).toString().replace(",", "")),font1));
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
            if(z==2){
         
            PdfPCell cellx3 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(2).toString()),font1));
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
            table.addCell(cellx3);
            }
            
             if(z==3){
       
            PdfPCell cellx4 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(3).toString())+"",font1));
         
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
            table.addCell(cellx4);
             }
              if(z==4){
                 
          PdfPCell cellx5 = new PdfPCell(new Paragraph(realData.get(4).toString(),font1)); 
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
            table.addCell(cellx5 );
              }
                if(z==5){
          
            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(5).toString(),font1)); 
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
                
               if(z==6){
          
            PdfPCell cellx7 = new PdfPCell(new Paragraph(realData.get(6).toString(),font1)); 
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
      
//           n++;
           }n++; }
           }else{
           table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
            table.addCell(new PdfPCell(new Paragraph("No Records Found",font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
           table.addCell(new PdfPCell(new Paragraph("N/A"+"",font1)));
           table.addCell(new PdfPCell(new Paragraph("N/A"+"",font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A",font1))); 
            table.addCell(new PdfPCell(new Paragraph("N/A",font1))); 
           }
 
          
          

  
  
  
      return table;

       }
      
       public boolean createRunningLoanStatementclosed(String loanId,String fileName) { 
     
  boolean RunningLoanStatement=false;
     
  
  if(fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
  if(fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
createActuallyTRunningLoanStatementclosed(loanId,fileName);
RunningLoanStatement=true;
  } 
    
  }else{
createActuallyTRunningLoanStatementclosed(loanId,fileName);
RunningLoanStatement=true;
  }
      
      return RunningLoanStatement;
       }
  
      private void createActuallyTRunningLoanStatementclosed(String loanId,String fileName){
   
   
   PdfPTable body1,body2;
      try {

          Paragraph headerz =createHeading(quaries.AccountName(loanId.substring(11, 22)));
          headerz.setIndentationLeft(50);
          headerz.setIndentationRight(50);
          Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
          PdfWriter writer=PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf")));

           
          body1 = createTheTopPartclosed(loanId);
            
             body2 = createTheBottomPartclosed(loanId);

        
          Font font1 = new Font(Font.FontFamily.COURIER  , 14, Font.NORMAL);
          writer.setPageEvent(this);
          document.open();
          
         String BoxN=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "boxNumber.txt"));
          String Company=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "CompanyName.txt"));
          Paragraph ct= new Paragraph(Company+"\n P.O BOX"+"\n"+BoxN.split("[,]", 3)[0]+"\n"+"Tel:"+BoxN.split("[,]", 3)[1]+"\n\t"+"    "+BoxN.split("[,]", 3)[2],font1);
          ct.setIndentationLeft(50f);
          ct.setIndentationRight(200f);
          document.add(ct);
         
          Image image1 = Image.getInstance("strawberry3.jpg");
          image1.setAbsolutePosition(49f, 1080f);
          image1.scaleAbsolute(43f, 43f);
          document.add(image1);
          document.add(headerz);
          document.add(new Paragraph("  ") );
          Paragraph currency= new Paragraph("CURRENCY UGANDA SHILLINGS",font1);
          currency.setIndentationLeft(280f);
          currency.setIndentationRight(30f);
          document.add(currency);
          document.add(new Paragraph("  ") );
          document.add(body1);
          document.add(body2);
          document.add(new Paragraph("  ") );
          document.add(new Paragraph("  ") );
             document.add(new Paragraph("  ") );
    document.add(new Paragraph("  ") );
    document.add(this.createFooter());
            document.newPage();
          document.close();
         
         
      } catch (BadElementException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException | DocumentException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      }
       }
    
       public  PdfPTable createTheBottomPartclosed(String loanid){
          
       PdfPTable table = new PdfPTable(8);
       table.setWidthPercentage(100);
       float[] columnWidths = {30f, 30f, 30f,30f, 30f,15f,25f,18f};
       Font font1 = new Font(Font.FontFamily.COURIER  , 14, Font.NORMAL);
       Font font2 = new Font(Font.FontFamily.COURIER  , 14, Font.BOLD);
       Font font3 = new Font(Font.FontFamily.COURIER  , 14, Font.ITALIC);
       Font font4 = new Font(Font.FontFamily.COURIER  , 14, Font.BOLDITALIC);
    try {
        table.setWidths(columnWidths);
        
    } catch (DocumentException ex) {
        Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
    }
   PdfPCell cellEmpty= new PdfPCell(new Paragraph("",font1));
      cellEmpty.setFixedHeight(16.2f);
     cellEmpty.disableBorderSide(LEFT);
     cellEmpty.disableBorderSide(TOP);
     cellEmpty.disableBorderSide(RIGHT);
     cellEmpty.disableBorderSide(BOTTOM);
     cellEmpty.setPadding(.5f);
     cellEmpty.setBorderColorBottom(BaseColor.WHITE);
     cellEmpty.setBorderColorTop(BaseColor.WHITE);
     cellEmpty.setBorderColorLeft(BaseColor.WHITE);
     cellEmpty.setBorderColorRight(BaseColor.WHITE);
     cellEmpty.setVerticalAlignment(Element.ALIGN_TOP); 
   
   PdfPCell cell1 = new PdfPCell(new Paragraph("Due Date",font2));
            PdfPCell cell2 = new PdfPCell(new Paragraph("The Instalment",font2));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Princimpal Instalment",font2));
            PdfPCell cell4 = new PdfPCell(new Paragraph("Interest Instalment",font2));
            PdfPCell cell5 = new PdfPCell(new Paragraph("Balance",font2));
            PdfPCell cell6 = new PdfPCell(new Paragraph("Loan Status",font2)); 
            PdfPCell cell7 = new PdfPCell(new Paragraph("Payment Date",font2));
            PdfPCell cell8 = new PdfPCell(new Paragraph("PayVar (days)",font2));
           cell1.setBackgroundColor(BaseColor.GREEN);   //sets BG color to yellow.
           cell2.setBackgroundColor(BaseColor.GREEN); 
           cell3.setBackgroundColor(BaseColor.GREEN); 
           cell4.setBackgroundColor(BaseColor.GREEN); 
           cell5.setBackgroundColor(BaseColor.GREEN); 
           cell6.setBackgroundColor(BaseColor.GREEN);
              cell7.setBackgroundColor(BaseColor.GREEN);
              cell8.setBackgroundColor(BaseColor.GREEN);
           cell1.setBorderWidth (2f);         // sets border width to 3 units
           cell2.setBorderWidth (2f);
           cell3.setBorderWidth (2f);
           cell4.setBorderWidth (2f);
           cell5.setBorderWidth (2f);
           cell6.setBorderWidth (2f);
             cell7.setBorderWidth (2f);
              cell8.setBorderWidth (2f);
//           cell7.setBorderColorBottom(BaseColor.BLACK);
           
           
           cell1 .disableBorderSide(LEFT);         // sets border width to 3 units
           cell2 .disableBorderSide(LEFT);
           cell3 .disableBorderSide(LEFT);
           cell4 .disableBorderSide(LEFT);
           cell5 .disableBorderSide(LEFT);
           cell6 .disableBorderSide(LEFT);
           cell7.disableBorderSide(LEFT);
            cell8.disableBorderSide(LEFT);
           cell1 .disableBorderSide(TOP);       // sets border width to 3 units
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
          int n=0; Map<Integer, List<Object>> dataBase=rdb.loanEntriesclosed(loanid);
           if(!dataBase.isEmpty()){
           while(n<dataBase.size()) {
           List realData=dataBase.get(n);int z=0;
           
           while(z<realData.size()){
           if(z==0){
               PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(),font1));
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
           if(z==1){
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(1).toString().replace(",", "")),font1));
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
            if(z==2){
         
            PdfPCell cellx3 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(2).toString()),font1));
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
            table.addCell(cellx3);
            }
            
             if(z==3){
       
            PdfPCell cellx4 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(3).toString())+"",font1));
         
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
            table.addCell(cellx4);
             }
              if(z==4){
                 
            PdfPCell cellx5 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(4).toString())+"",font1));
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
            table.addCell(cellx5 );
              }
                if(z==5){
          
            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(5).toString(),font1)); 
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
                
               if(z==6){
          
            PdfPCell cellx7 = new PdfPCell(new Paragraph(realData.get(6).toString(),font1)); 
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
                
                 if(z==7){
          
            PdfPCell cellx8 = new PdfPCell(new Paragraph(realData.get(7).toString(),font1)); 
             cellx8.setFixedHeight(16.2f);
        cellx8.disableBorderSide(LEFT);
         cellx8.disableBorderSide(TOP);
         cellx8.disableBorderSide(RIGHT);
            cellx8.setPadding(.5f);
            cellx8.setBorderColorBottom(BaseColor.BLACK);
             cellx8.setBorderColorTop(BaseColor.WHITE);
             cellx8.setBorderColorLeft(BaseColor.WHITE);
             cellx8.setBorderColorRight(BaseColor.WHITE);
              cellx8.setVerticalAlignment(Element.ALIGN_TOP);
            table.addCell(cellx8); 
                } 
                
           z++;   
           } 
      
           n++;
           } 
           }else{
           table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
            table.addCell(new PdfPCell(new Paragraph("No Records Found",font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
           table.addCell(new PdfPCell(new Paragraph("N/A"+"",font1)));
           table.addCell(new PdfPCell(new Paragraph("N/A"+"",font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A",font1))); 
            table.addCell(new PdfPCell(new Paragraph("N/A",font1))); 
           }
 
          
          

  
  
  
      return table;

       }
       
       
       
    public  PdfPTable  createTheClosedLoanStatement(String SheetDate){
    PdfPTable tbl=null;
    return tbl;
    
    
    }
  

private Paragraph createHeading(String sheetDate) {
  Paragraph ss=null;
  
  Font font1 = new Font(Font.FontFamily.COURIER  , 14, Font.NORMAL);
  ss= new Paragraph("LOAN STATEMENT FOR ACCOUNT "+" "+sheetDate+"\n"+"AS AT"+" "+ffm.dateConverterForNormalDate(System.currentTimeMillis()),font1);
   ss.setAlignment(Element.ALIGN_CENTER);
  return ss;
  
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
      Font font1 = new Font(Font.FontFamily.COURIER  , 10, Font.BOLDITALIC);
       
        ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Phrase(String.format("page %d", pagenumber),font1),
                    ( document.right()-document.left())/2+document.leftMargin(), document.bottom()-20, 0);
      
    }
    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
            Font font1 = new Font(Font.FontFamily.COURIER  , 10, Font.BOLDITALIC);
        ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Phrase("POWERED BY: BAZIRAKE AUGUSTINE GOOGO, MOBILE: 0782231039, EMAIL: augbazi@gmail.com",font1),
                    ( document.right()-document.left())/2+(document.leftMargin()), document.bottom()-30, 0);
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
  

  
    
    
}
