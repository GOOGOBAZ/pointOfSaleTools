/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.reportsHelper;

import googo.pmms.project2.databases.ReportsDatabase;
import googo.pmms.project2.databases.DatabaseQuaries;
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
import googo.pmms.project2.frameHelper.MyTableModel;
import googo.pmms.project2.frames.PostingEntryWindow;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databaseConnectors.JdbcConnector;
import java.awt.Component;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Stanchart
 */
public class trialBalance implements PdfPageEvent {
    
 MaxmumAmountBorrowedFormulas MAth= new  MaxmumAmountBorrowedFormulas();
//     Amortization amortize = new Amortization();

     Calendar calN = Calendar.getInstance();
fileInputOutPutStreams fios= new fileInputOutPutStreams();
    Formartter ffm= new Formartter();
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
   PdfPTable body;
   String userId;
public void setUserID(String userid){
this.userId=userid;
}

    public boolean createTrialBalance(String startDate,String EndDate,String fileName,Component c) { 
     
  boolean TrialBalance=false;
     
  
  if(fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
      
  if(fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
      
createActuallyTrialBalance(startDate, EndDate,fileName,c);
TrialBalance=true;
  } 
    
  }else{
createActuallyTrialBalance(startDate, EndDate,fileName,c);
TrialBalance=true;
  }
      
      return TrialBalance;
       }
  
      private void createActuallyTrialBalance(String startDate,String EndDate,String fileName,Component c){
      try {

          Paragraph headerz =createHeading(startDate, EndDate);
          headerz.setIndentationLeft(50);
          headerz.setIndentationRight(50);
          Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
          PdfWriter writer=PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf")));
          
//            if(ffm.convertTdate(PAndLDate).equals(ffm.getTodayDate())){
            body = createTodayTrialBalance(startDate, EndDate,c);
            
//            }else {
//            body = createPastBalanceSheet(PAndLDate);
//            }
        
        
          Font font1 = new Font(Font.FontFamily.HELVETICA  , 14, Font.NORMAL);
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
          document.add(body);
          document.add(new Paragraph("  ") );
          document.add(new Paragraph("  ") );
            document.newPage();
          document.close();
         
         
      } catch (BadElementException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException | DocumentException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      }

     }
      public  PdfPTable createTodayTrialBalance(String startDate,String EndDate, Component c){
          
       PdfPTable table = new PdfPTable(4);
       table.setWidthPercentage(100);
       float[] columnWidths = {70f,40f, 35f, 35f};
       Font font1 = new Font(Font.FontFamily.HELVETICA  , 14, Font.NORMAL);
       Font font2 = new Font(Font.FontFamily.HELVETICA  , 14, Font.BOLD);
       Font font3 = new Font(Font.FontFamily.HELVETICA  , 14, Font.ITALIC);
       Font font4 = new Font(Font.FontFamily.COURIER  , 14, Font.BOLDITALIC);
    try {
        table.setWidths(columnWidths);
        
    } catch (DocumentException ex) {
        Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
    }
   PdfPCell cellEmpty= new PdfPCell(new Paragraph("",font1));
      cellEmpty.setFixedHeight(16.2f);
      cellEmpty.setFixedHeight(16.2f);
      cellEmpty.disableBorderSide(LEFT);
      cellEmpty.disableBorderSide(TOP);
      cellEmpty.disableBorderSide(RIGHT);
      cellEmpty.setPadding(.5f);
      cellEmpty.setBorderColorBottom(BaseColor.BLACK);
      cellEmpty.setBorderColorTop(BaseColor.BLACK);
      cellEmpty.setBorderColorLeft(BaseColor.BLACK);
      cellEmpty.setBorderColorRight(BaseColor.BLACK);
      cellEmpty.setVerticalAlignment(Element.ALIGN_TOP);
      
        PdfPCell cellEmptyq= new PdfPCell(new Paragraph("",font1));
    cellEmptyq.setFixedHeight(23.2f);
              cellEmptyq.disableBorderSide(LEFT);
             cellEmptyq.disableBorderSide(TOP);
              cellEmptyq.disableBorderSide(RIGHT);
             cellEmptyq.setPadding(2f);
             cellEmptyq.setBorderWidthBottom(5);
      cellEmptyq.setBorderColorBottom(BaseColor.BLACK);
      cellEmptyq.setBorderColorTop(BaseColor.BLACK);
      cellEmptyq.setBorderColorLeft(BaseColor.BLACK);
      cellEmptyq.setBorderColorRight(BaseColor.BLACK);
      cellEmptyq.setVerticalAlignment(Element.ALIGN_TOP);
   
       PdfPCell cell1 = new PdfPCell(new Paragraph("Account",font2));
       PdfPCell cell2 = new PdfPCell(new Paragraph("AccountType",font2));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Debits",font2));
            PdfPCell cell4 = new PdfPCell(new Paragraph("Credits",font2));
       
            
           cell1.setBackgroundColor(BaseColor.CYAN);   //sets BG color to yellow.
           cell2.setBackgroundColor(BaseColor.CYAN); 
           cell3.setBackgroundColor(BaseColor.CYAN); 
           cell4.setBackgroundColor(BaseColor.CYAN);
             

           cell1.setBorderWidth (2f);         // sets border width to 3 units
           cell2.setBorderWidth (2f);
           cell3.setBorderWidth (2f);
           cell4.setBorderWidth (2f);
           
           
           cell1 .disableBorderSide(LEFT);         // sets border width to 3 units
           cell2 .disableBorderSide(LEFT);
           cell3 .disableBorderSide(LEFT);
           cell4 .disableBorderSide(LEFT);
           
           
           cell1 .disableBorderSide(TOP);       // sets border width to 3 units
           cell2.disableBorderSide(TOP); 
           cell3.disableBorderSide(TOP); 
           cell4.disableBorderSide(TOP);
           
           cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
           cell2.disableBorderSide(RIGHT); 
           cell3.disableBorderSide(RIGHT); 
           cell4.disableBorderSide(RIGHT);
      
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            
    int n=0; Map<Integer, List<Object>> dataBase=rdb.trialBalanceItems(startDate,EndDate,c); 
    
List<Double> sumHolder1= new ArrayList();

List<Double>  sumHolder2= new ArrayList();
        
           if(!dataBase.isEmpty()){
               
           while(n<dataBase.size()) {
               
           List realData=dataBase.get(n);
           
           if(realData.get(3).toString().equalsIgnoreCase("Debits")&&!(parseDouble(realData.get(2).toString())==0.0)){
             int z=0;
               while(z<3){ 
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
                }else   if(z==1){
               PdfPCell cellx1x = new PdfPCell(new Paragraph(realData.get(1).toString(),font1));
             cellx1x.setFixedHeight(16.2f);
               cellx1x.disableBorderSide(LEFT);
             cellx1x.disableBorderSide(TOP);
               cellx1x.disableBorderSide(RIGHT);
             cellx1x.setPadding(.5f);
               cellx1x.setBorderColorBottom(BaseColor.BLACK);
             cellx1x.setBorderColorTop(BaseColor.WHITE);
             cellx1x.setBorderColorLeft(BaseColor.WHITE);
             cellx1x.setBorderColorRight(BaseColor.WHITE);
             cellx1x.setVerticalAlignment(Element.ALIGN_TOP);
               table.addCell(cellx1x);
                }
                
                
                else if (z==2){
                         PdfPCell cellx12=null;
                double sum1=parseDouble(realData.get(2).toString());
                if(sum1<0.0){
                cellx12 = new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(sum1+"")+")",font1));
                }else{
                cellx12 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(sum1+""),font1));
                }
                   
                
             cellx12.setFixedHeight(16.2f);
               cellx12.disableBorderSide(LEFT);
             cellx12.disableBorderSide(TOP);
               cellx12.disableBorderSide(RIGHT);
             cellx12.setPadding(.5f);
               cellx12.setBorderColorBottom(BaseColor.BLACK);
             cellx12.setBorderColorTop(BaseColor.WHITE);
             cellx12.setBorderColorLeft(BaseColor.WHITE);
             cellx12.setBorderColorRight(BaseColor.WHITE);
             cellx12.setVerticalAlignment(Element.ALIGN_TOP);
               table.addCell(cellx12);
                 table.addCell(cellEmpty);
               sumHolder1.add(parseDouble(realData.get(2).toString()));
                }
                z++;
               }
           } else if(realData.get(3).toString().equalsIgnoreCase("Credits")&&!(parseDouble(realData.get(2).toString())==0.0)) {
         int z=0;
               
               while(z<3) {
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
                }else  if(z==1){
               PdfPCell cellx1x = new PdfPCell(new Paragraph(realData.get(1).toString(),font1));
             cellx1x.setFixedHeight(16.2f);
               cellx1x.disableBorderSide(LEFT);
             cellx1x.disableBorderSide(TOP);
               cellx1x.disableBorderSide(RIGHT);
             cellx1x.setPadding(.5f);
               cellx1x.setBorderColorBottom(BaseColor.BLACK);
             cellx1x.setBorderColorTop(BaseColor.WHITE);
             cellx1x.setBorderColorLeft(BaseColor.WHITE);
             cellx1x.setBorderColorRight(BaseColor.WHITE);
             cellx1x.setVerticalAlignment(Element.ALIGN_TOP);
               table.addCell(cellx1x);
                 table.addCell(cellEmpty);
                }else if (z==2){
                     PdfPCell cellx12=null;
                double sum1=parseDouble(realData.get(2).toString());
                if(sum1<0.0){
                cellx12= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(sum1+"")+")",font1));
                }else{
                cellx12 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(sum1+""),font1));
                }
             cellx12.setFixedHeight(16.2f);
               cellx12.disableBorderSide(LEFT);
             cellx12.disableBorderSide(TOP);
               cellx12.disableBorderSide(RIGHT);
             cellx12.setPadding(.5f);
               cellx12.setBorderColorBottom(BaseColor.BLACK);
             cellx12.setBorderColorTop(BaseColor.WHITE);
             cellx12.setBorderColorLeft(BaseColor.WHITE);
             cellx12.setBorderColorRight(BaseColor.WHITE);
             cellx12.setVerticalAlignment(Element.ALIGN_TOP);
               table.addCell(cellx12);
              sumHolder2.add(parseDouble(realData.get(2).toString()));
               
                }
   
           z++;
               }
               
           }
            n++;
           }
           
           double sum1=0.0,sum2=0.0;int k1=0,k2=0;
           while(k1<sumHolder1.size()){
           
           sum1=sum1+sumHolder1.get(k1);
           k1++;
           }
           while(k2<sumHolder2.size()){
           
           sum2=sum2+sumHolder2.get(k2);
           k2++;
           }
           
            PdfPCell cellx1s1 = new PdfPCell(new Paragraph("Total Value",font2));
             cellx1s1.setFixedHeight(23.2f);
              cellx1s1.disableBorderSide(LEFT);
             cellx1s1.disableBorderSide(TOP);
              cellx1s1.disableBorderSide(RIGHT);
             cellx1s1.setPadding(2f);
             cellx1s1.setBorderWidthBottom(5);
             cellx1s1.setBorderColorBottom(BaseColor.BLACK);
            cellx1s1.setBorderColorTop(BaseColor.WHITE);
             cellx1s1.setBorderColorLeft(BaseColor.WHITE);
             cellx1s1.setBorderColorRight(BaseColor.WHITE);
             cellx1s1.setVerticalAlignment(Element.ALIGN_TOP);
              table.addCell(cellEmptyq);
               table.addCell(cellx1s1);
                PdfPCell cellx1s2=null;
               if(sum1<0){
               cellx1s2 = new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(sum1+"")+")",font2));
               }else if(sum2>=0){
                cellx1s2 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(sum1+""),font2));
               }
             cellx1s2.setFixedHeight(22.2f);
               cellx1s2.disableBorderSide(LEFT);
             cellx1s2.disableBorderSide(TOP);
               cellx1s2.disableBorderSide(RIGHT);
             cellx1s2.setPadding(2f);
             cellx1s2.setBorderWidthBottom(5);
             cellx1s2.setBorderColorBottom(BaseColor.BLACK);
             cellx1s2.setBorderColorTop(BaseColor.WHITE);
             cellx1s2.setBorderColorLeft(BaseColor.WHITE);
             cellx1s2.setBorderColorRight(BaseColor.WHITE);
             cellx1s2.setVerticalAlignment(Element.ALIGN_TOP);
               table.addCell(cellx1s2);
               PdfPCell cellx1s3=null;
               if(sum2<0){
               cellx1s3 = new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(sum2+"")+")",font2));
               }else if(sum2>=0){
                cellx1s3 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(sum2+""),font2));
               }
             cellx1s3.setFixedHeight(22.2f);
               cellx1s3.disableBorderSide(LEFT);
             cellx1s3.disableBorderSide(TOP);
               cellx1s3.disableBorderSide(RIGHT);
             cellx1s3.setPadding(2f);
             cellx1s3.setBorderWidthBottom(5);
             cellx1s3.setBorderColorBottom(BaseColor.BLACK);
             cellx1s3.setBorderColorTop(BaseColor.WHITE);
             cellx1s3.setBorderColorLeft(BaseColor.WHITE);
             cellx1s3.setBorderColorRight(BaseColor.WHITE);
             cellx1s3.setVerticalAlignment(Element.ALIGN_TOP);
               table.addCell(cellx1s3);
           
           }else{
           table.addCell(new PdfPCell(new Paragraph("NA",font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database",font1)));
            table.addCell(new PdfPCell(new Paragraph("NA",font1)));
           table.addCell(new PdfPCell(new Paragraph("NA",font1)));
           }
      return table;

       }
          
           

    public void setBody(PdfPTable body) {
        this.body = body;
    }

//    @Override
//    public String toString() {
//        return "ProfitAndLoss{" + "MAth=" + MAth + ", amortize=" + amortize + ", calN=" + calN + ", fios=" + fios + ", ffm=" + ffm + ", Trndate=" + Trndate + ", valuedate=" + valuedate + ", data4=" + data4 + ", column1=" + column1 + ", data5=" + data5 + ", p=" + p + ", model=" + model + ", df=" + df + ", newbalance=" + newbalance + ", ledgerBalance=" + ledgerBalance + ", creditamount=" + creditamount + ", cal=" + cal + ", csx=" + csx + ", quaries=" + quaries + ", rdb=" + rdb + ", pagenumber=" + pagenumber + ", body=" + body + '}';
//    }
      
    public  PdfPTable  createPastBalanceSheet(String SheetDate){
    PdfPTable tbl=null;
    return tbl;
    
    
    }
  

private Paragraph createHeading(String startDate,String EndDate) {
  Paragraph ss=null;
  
  Font font1 = new Font(Font.FontFamily.HELVETICA , 14, Font.NORMAL);
 
  ss= new Paragraph("TRIAL BALANCE FOR THE PERIOD"+"\n"+"BETWEEN"+" "+ffm.fromDatabaseWithDashSeperatorBeginningWithYear(startDate)+" "+"AND"+" "+ffm.fromDatabaseWithDashSeperatorBeginningWithYear(EndDate),font1);
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
      Font font1 = new Font(Font.FontFamily.HELVETICA  , 10, Font.BOLDITALIC);
       
        ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Phrase(String.format("page %d", pagenumber),font1),
                    ( document.right()-document.left())/2+document.leftMargin(), document.bottom()-20, 0);
      
    }
    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
            Font font1 = new Font(Font.FontFamily.HELVETICA , 10, Font.BOLDITALIC);
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
  
public  boolean isTrialBalanceBalanceing(Component c){
          
boolean truethen=false; double sum1=0.0,sum2=0.0;int k1=0,k2=0;

    int n=0; Map<Integer, List<Object>> dataBase=rdb.trialBalanceItemsv(c); 
    
List<Double> sumHolder1= new ArrayList();

List<Double>  sumHolder2= new ArrayList();
        
           if(!dataBase.isEmpty()){
               
           while(n<dataBase.size()) {
               
           List realData=dataBase.get(n);
           
           if(realData.get(2).toString().equalsIgnoreCase("Debits")&&!(parseDouble(realData.get(1).toString())==0.0)){
             int z=0;
               while(z<2){ 
               if (z==1){
               sumHolder1.add(parseDouble(realData.get(1).toString()));
                }
                z++;
               }
           } else if(realData.get(2).toString().equalsIgnoreCase("Credits")&&!(parseDouble(realData.get(1).toString())==0.0)) {
         int z=0;
               
               while(z<2) {
               if (z==1){
        
             
              sumHolder2.add(parseDouble(realData.get(1).toString()));
               
                }
   
           z++;
               }
               
           }
            n++;
           }
           
          
           while(k1<sumHolder1.size()){
           
           sum1=sum1+sumHolder1.get(k1);
           k1++;
           }
           while(k2<sumHolder2.size()){
           
           sum2=sum2+sumHolder2.get(k2);
           k2++;
           }
           
       
            
           
           }
      JOptionPane.showMessageDialog(c, sum1+";"+sum2);
           if(sum1==sum2){
        truethen =true;  
           }
           
      return truethen;

       }
   
   
   
}
