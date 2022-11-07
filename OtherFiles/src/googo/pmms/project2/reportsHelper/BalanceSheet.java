
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.reportsHelper;

import googo.pmms.project2.frameHelper.MyTableModel;
import googo.pmms.project2.frames.PostingEntryWindow;
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
import googo.pmms.project2.loanHelper.MaxmumAmountBorrowedFormulas;
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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.util.List;

/**
 *
 * @author Stanchart
 */
public class BalanceSheet implements PdfPageEvent {
     MaxmumAmountBorrowedFormulas MAth= new  MaxmumAmountBorrowedFormulas();
//     Amortization amortize = new Amortization();
  Formartter ffm= new Formartter();
     Calendar calN = Calendar.getInstance();
fileInputOutPutStreams fios= new fileInputOutPutStreams();

    Date Trndate,valuedate;
   ArrayList<String> data4, column1;
 ArrayList<ArrayList<String>> data5;
          JOptionPane p;
     MyTableModel model;
  SimpleDateFormat df;

   double newbalance,ledgerBalance,creditamount;
    GregorianCalendar cal = new GregorianCalendar(); 
       JdbcConnector csx = new JdbcConnector(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "AccountDBDetails.txt"))); ; 
    DatabaseQuaries quaries =new DatabaseQuaries();
ReportsDatabase rdb = new ReportsDatabase();
 int   pagenumber=0;
   PdfPTable body;
    netProfits netprofits= new netProfits();
  String userId;
public void setUserID(String userid){
this.userId=userid;
}
  public boolean createBalanceSheet(String SheetDate,String fileName,Component c) { 
     
  boolean BalanceSheet=false;
     
  
  if(fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
  if(fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
createActuallyBalanceSheet( SheetDate,fileName,c);
BalanceSheet=true;
  } 
    
  }else{
createActuallyBalanceSheet( SheetDate,fileName,c);
BalanceSheet=true;
  }
      
      return BalanceSheet;
       }
  
      private void createActuallyBalanceSheet(String SheetDate,String fileName,Component c){
      try {

          Paragraph headerz =createHeading(SheetDate);
          headerz.setIndentationLeft(50);
          headerz.setIndentationRight(50);
          Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
          PdfWriter writer=PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf")));
            body = createTodayBalanceSheet(SheetDate,c);

          Font font1 = new Font(Font.FontFamily.COURIER  , 14, Font.NORMAL);
          writer.setPageEvent(this);
          writer.setPdfVersion(PdfWriter.PDF_VERSION_1_7);
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
       
      public  PdfPTable createTodayBalanceSheet(String sheetDate,Component c){
          
       PdfPTable table = new PdfPTable(6);
       table.setWidthPercentage(100);
       float[] columnWidths = {15f, 100f, 1f,30f, 10f,10f};
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
     
     PdfPCell celd= new PdfPCell(new Paragraph("",font2));
       celd.setColspan(6);
      celd.setFixedHeight(16.2f);
      celd.disableBorderSide(LEFT);
      celd.disableBorderSide(TOP);
      celd.disableBorderSide(RIGHT);
       celd.setPadding(.5f);
       celd.setBorderColorBottom(BaseColor.BLACK);
       celd.setBorderColorTop(BaseColor.WHITE);
       celd.setBorderColorLeft(BaseColor.WHITE);
       celd.setBorderColorRight(BaseColor.WHITE);
       celd.setVerticalAlignment(Element.ALIGN_TOP);
   Map<Integer,Double> TotalHolder= new HashMap<>();
  int i=0;boolean indicator=false; boolean fixedIndicator=false;boolean indicatorInvest=false; boolean indicatorCash=false,indicatorLoan=false,indicatorReceivable=false,indicatorInvento=false;boolean currentLiabCred=false,provision=false, noncursecLiab=false,noncurunseclian=false;boolean taxLiabi=false;   boolean currentLiabCreds=false,provisions=false,secondRetained=false;
  while (i<=30){
  TotalHolder.put(i, 0.0);
  i++;
  }
 
          
           int x=0;
           
      Map<Integer, String> level3=rdb. getLevel3Items("Assets");
      
       PdfPCell cellLeve2= new PdfPCell(new Paragraph("Assets",font2));
       
       cellLeve2.setColspan(6);
      cellLeve2.setFixedHeight(16.2f);
      cellLeve2.disableBorderSide(LEFT);
      cellLeve2.disableBorderSide(TOP);
      cellLeve2.disableBorderSide(RIGHT);
       cellLeve2.setPadding(.5f);
       cellLeve2.setBorderColorBottom(BaseColor.BLACK);
       cellLeve2.setBorderColorTop(BaseColor.WHITE);
       cellLeve2.setBorderColorLeft(BaseColor.WHITE);
       cellLeve2.setBorderColorRight(BaseColor.WHITE);
       cellLeve2.setVerticalAlignment(Element.ALIGN_TOP);
               table.addCell(cellLeve2);
        while(x<=level3.size()-1){
        switch(level3.get(x)){
            
       case "Fixed Assets&Investments":
           
           int y=0;
       Map<Integer, String> level4=rdb.getLevel4Items("Fixed Assets&Investments");   
       
       PdfPCell cellLeve3= new PdfPCell(new Paragraph("Fixed Assets & Investments",font2));
       
        cellLeve3.setColspan(6);
      cellLeve3.setFixedHeight(16.2f);
      cellLeve3.disableBorderSide(LEFT);
      cellLeve3.disableBorderSide(TOP);
      cellLeve3.disableBorderSide(RIGHT);
      cellLeve3.disableBorderSide(BOTTOM);
      cellLeve3.setPadding(.5f);
      cellLeve3.setBorderColorBottom(BaseColor.WHITE);
      cellLeve3.setBorderColorTop(BaseColor.WHITE);
      cellLeve3.setBorderColorLeft(BaseColor.WHITE);
      cellLeve3.setBorderColorRight(BaseColor.WHITE);
      cellLeve3.setVerticalAlignment(Element.ALIGN_TOP);
      table.addCell(cellLeve3);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
           while(y<=level4.size()-1){
        switch(level4.get(y)){
        case "Fixed Assets/Non-Current Assets":
            int z=0; double sum=0.0; boolean fixedAExists=false;
       Map<Integer, String> level5=rdb.getLevel5Items("Fixed Assets/Non-Current Assets"); 
  while(z<=level5.size()-1){
      
        if(parseDouble(rdb.getBalancesSheet(level5.get(z),sheetDate,c).get(level5.get(z)).toString())!=0.0){
            if(!fixedAExists){
      table.addCell(cellEmpty);
     PdfPCell cellLeve4= new PdfPCell(new Paragraph("Fixed Assets/Non-Current Assets",font2));
     cellLeve4.setColspan(5);
      cellLeve4.setFixedHeight(16.2f);
      cellLeve4.disableBorderSide(LEFT);
      cellLeve4.disableBorderSide(TOP);
      cellLeve4.disableBorderSide(RIGHT);
      cellLeve4.disableBorderSide(BOTTOM);
      cellLeve4.setPadding(.5f);
      cellLeve4.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4.setBorderColorTop(BaseColor.WHITE);
      cellLeve4.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4.setBorderColorRight(BaseColor.WHITE);
      cellLeve4.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4);
       fixedAExists=true;
        }
            if(!(level5.get(z).equalsIgnoreCase("Accumulated Depreciation/Amortization"))){
        
      table.addCell(cellEmpty);
      PdfPCell cellLeve5= new PdfPCell(new Paragraph(level5.get(z),font1));
      cellLeve5.setFixedHeight(16.2f);
      cellLeve5.disableBorderSide(LEFT);
      cellLeve5.disableBorderSide(TOP);
      cellLeve5.disableBorderSide(RIGHT);
      cellLeve5.disableBorderSide(BOTTOM);
      cellLeve5.setPadding(.5f);
      cellLeve5.setBorderColorBottom(BaseColor.WHITE);
      cellLeve5.setBorderColorTop(BaseColor.WHITE);
      cellLeve5.setBorderColorLeft(BaseColor.WHITE);
      cellLeve5.setBorderColorRight(BaseColor.WHITE);
      cellLeve5.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cellLeve5);
    
      table.addCell(cellEmpty);
      PdfPCell balAmount=null;
      double amount=parseDouble(rdb.getBalancesSheet(level5.get(z),sheetDate,c).get(level5.get(z)).toString());
      if(amount<0.0){
       balAmount= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(amount+"")+")",font1));
      } else{
      
       balAmount= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(amount+""),font1));
      
      }
     
      balAmount.setFixedHeight(16.2f);
      balAmount.disableBorderSide(LEFT);
      balAmount.disableBorderSide(TOP);
      balAmount.disableBorderSide(RIGHT);
      balAmount.disableBorderSide(BOTTOM);
      balAmount.setPadding(.5f);
      balAmount.setBorderColorBottom(BaseColor.WHITE);
      balAmount.setBorderColorTop(BaseColor.WHITE);
      balAmount.setBorderColorLeft(BaseColor.WHITE);
      balAmount.setBorderColorRight(BaseColor.WHITE);
      balAmount.setHorizontalAlignment(Element.ALIGN_CENTER);
     
     table.addCell(balAmount);
      table.addCell(cellEmpty);
     
      table.addCell(cellEmpty);
       
       sum= sum+(parseDouble(rdb.getBalancesSheet(level5.get(z),sheetDate,c).get(level5.get(z)).toString()));    
       fixedIndicator=true;
        }
          if(level5.get(z).equalsIgnoreCase("Accumulated Depreciation/Amortization")){
              
        indicator=true;
        
        TotalHolder.put(0, parseDouble(rdb.getBalancesSheet(level5.get(z),sheetDate,c).get(level5.get(z)).toString()));
        
        }
          
        }
           z++; 
                 }
              if(fixedIndicator){
             TotalHolder.put(1, sum);
             table.addCell(cellEmpty);
          PdfPCell totalValue= new PdfPCell(new Paragraph("Total Fixed Assets",font3));
      totalValue.setFixedHeight(16.2f);
      totalValue.disableBorderSide(LEFT);
      totalValue.disableBorderSide(TOP);
      totalValue.disableBorderSide(RIGHT);
      totalValue.disableBorderSide(BOTTOM);
      totalValue.setPadding(.5f);
      totalValue.setBorderColorBottom(BaseColor.WHITE);
      totalValue.setBorderColorTop(BaseColor.WHITE);
      totalValue.setBorderColorLeft(BaseColor.WHITE);
      totalValue.setBorderColorRight(BaseColor.WHITE);
      totalValue.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(totalValue);  
      table.addCell(cellEmpty);
      PdfPCell totalAmount=null;
       if(TotalHolder.get(1)<0.0){
      totalAmount= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(1)+"")+")",font1));
       }else{
       
      totalAmount= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(1)+""),font1)); 
       }
        totalAmount.setFixedHeight(16.2f);
      totalAmount.disableBorderSide(LEFT);
      totalAmount.disableBorderSide(RIGHT);
      totalAmount.setPadding(.5f);
      totalAmount.setBorderColorBottom(BaseColor.BLACK);
      totalAmount.setBorderColorTop(BaseColor.BLACK);
      totalAmount.setBorderColorLeft(BaseColor.WHITE);
      totalAmount.setBorderColorRight(BaseColor.WHITE);
      totalAmount.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(totalAmount);
      table.addCell(cellEmpty); 
       table.addCell(cellEmpty);
              }
        if(indicator){
       
       PdfPCell less= new PdfPCell(new Paragraph("Less",font3));
      less.setFixedHeight(16.2f);
      less.disableBorderSide(LEFT);
      less.disableBorderSide(TOP);
      less.disableBorderSide(RIGHT);
      less.disableBorderSide(BOTTOM);
      less.setPadding(.5f);
      less.setBorderColorBottom(BaseColor.WHITE);
      less.setBorderColorTop(BaseColor.WHITE);
      less.setBorderColorLeft(BaseColor.WHITE);
      less.setBorderColorRight(BaseColor.WHITE);
      less.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(less);
       PdfPCell accuDep5= new PdfPCell(new Paragraph("Accumulated Depreciation/Amortization",font1));
      accuDep5.setFixedHeight(16.2f);
      accuDep5.disableBorderSide(LEFT);
      accuDep5.disableBorderSide(TOP);
      accuDep5.disableBorderSide(RIGHT);
      accuDep5.disableBorderSide(BOTTOM);
      accuDep5.setPadding(.5f);
      accuDep5.setBorderColorBottom(BaseColor.WHITE);
      accuDep5.setBorderColorTop(BaseColor.WHITE);
      accuDep5.setBorderColorLeft(BaseColor.WHITE);
      accuDep5.setBorderColorRight(BaseColor.WHITE);
      accuDep5.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(accuDep5);  
      table.addCell(cellEmpty);
      
        PdfPCell balAmountAC= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(0)+"")+")",font1));
      balAmountAC.setFixedHeight(16.2f);
      balAmountAC.disableBorderSide(LEFT);
     balAmountAC.disableBorderSide(TOP);
      balAmountAC.disableBorderSide(RIGHT);
      balAmountAC.disableBorderSide(BOTTOM);
      balAmountAC.setPadding(.5f);
      balAmountAC.setBorderColorBottom(BaseColor.WHITE);
      balAmountAC.setBorderColorTop(BaseColor.WHITE);
      balAmountAC.setBorderColorLeft(BaseColor.WHITE);
      balAmountAC.setBorderColorRight(BaseColor.WHITE);
      balAmountAC.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(balAmountAC);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
     
        
        table.addCell(cellEmpty);
       PdfPCell accuDepN= new PdfPCell(new Paragraph("Net Book Value of Fixed Assets",font1));
      accuDepN.setFixedHeight(16.2f);
      accuDepN.disableBorderSide(LEFT);
      accuDepN.disableBorderSide(TOP);
      accuDepN.disableBorderSide(RIGHT);
      accuDepN.disableBorderSide(BOTTOM);
      accuDepN.setPadding(.5f);
      accuDepN.setBorderColorBottom(BaseColor.WHITE);
      accuDepN.setBorderColorTop(BaseColor.WHITE);
      accuDepN.setBorderColorLeft(BaseColor.WHITE);
      accuDepN.setBorderColorRight(BaseColor.WHITE);
      accuDepN.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(accuDepN);  
      table.addCell(cellEmpty);
       
       PdfPCell netAmountAC=null;
       double net =MAth.subtract(TotalHolder.get(1), TotalHolder.get(0));
        TotalHolder.put(2, net);
       if(net<0){netAmountAC= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(net+"")+")",font1));} else{
        netAmountAC= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(net+""),font1));
       }
      netAmountAC.setFixedHeight(16.2f);
      netAmountAC.disableBorderSide(LEFT);
     netAmountAC.disableBorderSide(RIGHT);
     netAmountAC.setPadding(.5f);
     netAmountAC.setBorderColorBottom(BaseColor.BLACK);
     netAmountAC.setBorderColorTop(BaseColor.BLACK);
     netAmountAC.setBorderColorLeft(BaseColor.WHITE);
     netAmountAC.setBorderColorRight(BaseColor.WHITE);
     netAmountAC.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(netAmountAC);
      table.addCell(cellEmpty);
       table.addCell(cellEmpty);
       
       table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
        }
              break;
            
    
      case   "Investments" :
         int z1=0; double sum1=0.0;boolean investExists=false;
       Map<Integer, String> level5a=rdb.getLevel5Items("Investments"); 
       while(z1<=level5a.size()-1){
      
        if(parseDouble(rdb.getBalancesSheet(level5a.get(z1),sheetDate,c).get(level5a.get(z1)).toString())!=0.0){
            
            if(!investExists){
      table.addCell(cellEmpty);
     PdfPCell cellLeve4a= new PdfPCell(new Paragraph("Investments",font2));
     cellLeve4a.setColspan(5);
      cellLeve4a.setFixedHeight(16.2f);
      cellLeve4a.disableBorderSide(LEFT);
      cellLeve4a.disableBorderSide(TOP);
      cellLeve4a.disableBorderSide(RIGHT);
      cellLeve4a.disableBorderSide(BOTTOM);
      cellLeve4a.setPadding(.5f);
      cellLeve4a.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4a.setBorderColorTop(BaseColor.WHITE);
      cellLeve4a.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4a.setBorderColorRight(BaseColor.WHITE);
      cellLeve4a.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4a);
           investExists=false;  
            }
      table.addCell(cellEmpty);
      PdfPCell cellLeve5= new PdfPCell(new Paragraph(level5a.get(z1),font1));
      cellLeve5.setFixedHeight(16.2f);
      cellLeve5.disableBorderSide(LEFT);
      cellLeve5.disableBorderSide(TOP);
      cellLeve5.disableBorderSide(RIGHT);
      cellLeve5.disableBorderSide(BOTTOM);
      cellLeve5.setPadding(.5f);
      cellLeve5.setBorderColorBottom(BaseColor.WHITE);
      cellLeve5.setBorderColorTop(BaseColor.WHITE);
      cellLeve5.setBorderColorLeft(BaseColor.WHITE);
      cellLeve5.setBorderColorRight(BaseColor.WHITE);
      cellLeve5.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cellLeve5);
    
      table.addCell(cellEmpty);
     
     PdfPCell balAmount=null;
      double amount=parseDouble(rdb.getBalancesSheet(level5a.get(z1),sheetDate,c).get(level5a.get(z1)).toString());
      if(amount<0.0){
       balAmount= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(amount+"")+")",font1));
      } else{
      
       balAmount= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(amount+""),font1));
      }
      balAmount.setFixedHeight(16.2f);
      balAmount.disableBorderSide(LEFT);
      balAmount.disableBorderSide(TOP);
      balAmount.disableBorderSide(RIGHT);
      balAmount.disableBorderSide(BOTTOM);
      balAmount.setPadding(.5f);
      balAmount.setBorderColorBottom(BaseColor.WHITE);
      balAmount.setBorderColorTop(BaseColor.WHITE);
      balAmount.setBorderColorLeft(BaseColor.WHITE);
      balAmount.setBorderColorRight(BaseColor.WHITE);
      balAmount.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(balAmount);
     
      table.addCell(cellEmpty);
     
      table.addCell(cellEmpty);
        
       indicatorInvest=true;
        }
       sum1= sum1+(parseDouble(rdb.getBalancesSheet(level5a.get(z1),sheetDate,c).get(level5a.get(z1)).toString()));   
        z1++; 
              
              
              
              }
              if(indicatorInvest){
               TotalHolder.put(3, sum1);
             table.addCell(cellEmpty);
          PdfPCell totalValueInvetments= new PdfPCell(new Paragraph("Total Investments",font3));
      totalValueInvetments.setFixedHeight(16.2f);
      totalValueInvetments.disableBorderSide(LEFT);
      totalValueInvetments.disableBorderSide(TOP);
      totalValueInvetments.disableBorderSide(RIGHT);
      totalValueInvetments.disableBorderSide(BOTTOM);
      totalValueInvetments.setPadding(.5f);
      totalValueInvetments.setBorderColorBottom(BaseColor.WHITE);
      totalValueInvetments.setBorderColorTop(BaseColor.WHITE);
      totalValueInvetments.setBorderColorLeft(BaseColor.WHITE);
      totalValueInvetments.setBorderColorRight(BaseColor.WHITE);
      totalValueInvetments.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(totalValueInvetments);  
      table.addCell(cellEmpty);
      
      PdfPCell totalAmountInvest=null;
              double totalAmountInv=0.0;
              totalAmountInv=TotalHolder.get(3);
              if(totalAmountInv<0.0){
            totalAmountInvest   = new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(totalAmountInv+"")+")",font1));
              
              } else{
          totalAmountInvest   = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(totalAmountInv+""),font1));    
              }
     
      
      totalAmountInvest.setFixedHeight(16.2f);
      totalAmountInvest.disableBorderSide(LEFT);
      totalAmountInvest.disableBorderSide(RIGHT);
      totalAmountInvest.setPadding(.5f);
      totalAmountInvest.setBorderColorBottom(BaseColor.BLACK);
      totalAmountInvest.setBorderColorTop(BaseColor.BLACK);
      totalAmountInvest.setBorderColorLeft(BaseColor.WHITE);
      totalAmountInvest.setBorderColorRight(BaseColor.WHITE);
      totalAmountInvest.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(totalAmountInvest);
      table.addCell(cellEmpty); 
       table.addCell(cellEmpty);
              }             
                        
         break; 
           
           }
          
          y++; 
           }
           if(fixedIndicator||indicatorInvest){
           double totalFixedAndInvestments=0.0;
      
           if(indicator){
        totalFixedAndInvestments=TotalHolder.get(2)+TotalHolder.get(3);
           }else{
           
           totalFixedAndInvestments=TotalHolder.get(1)+TotalHolder.get(3);
           }
       
       
       TotalHolder.put(4, totalFixedAndInvestments);
       table.addCell(cellEmpty);
       table.addCell(cellEmpty);
       table.addCell(cellEmpty);
       table.addCell(cellEmpty);
       table.addCell(cellEmpty);
       table.addCell(cellEmpty);
       table.addCell(cellEmpty);
     PdfPCell cellLeve4a1= new PdfPCell(new Paragraph("Total Fixed Assets And Investments",font4));
      cellLeve4a1.setFixedHeight(16.2f);
      cellLeve4a1.disableBorderSide(LEFT);
      cellLeve4a1.disableBorderSide(TOP);
      cellLeve4a1.disableBorderSide(RIGHT);
      cellLeve4a1.disableBorderSide(BOTTOM);
      cellLeve4a1.setPadding(.5f);
      cellLeve4a1.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4a1.setBorderColorTop(BaseColor.WHITE);
      cellLeve4a1.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4a1.setBorderColorRight(BaseColor.WHITE);
      cellLeve4a1.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4a1);  
       table.addCell(cellEmpty);
 
        PdfPCell totalAmountInvests=null;
       if(TotalHolder.get(4)<0.0){
        totalAmountInvests= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(4)+"")+")",font4));
       }else{ totalAmountInvests= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(4)+""),font4));}
       
        totalAmountInvests.setFixedHeight(16.2f);
      totalAmountInvests.disableBorderSide(LEFT);
      totalAmountInvests.disableBorderSide(RIGHT);
      totalAmountInvests.disableBorderSide(TOP);
      totalAmountInvests.setPadding(.5f);
      totalAmountInvests.setBorderColorBottom(BaseColor.BLACK);
      totalAmountInvests.setBorderColorTop(BaseColor.WHITE);
      totalAmountInvests.setBorderColorLeft(BaseColor.WHITE);
      totalAmountInvests.setBorderColorRight(BaseColor.WHITE);
      totalAmountInvests.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(totalAmountInvests);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
       }
           break;
       case "Current Assets, Loans And Advances":
              int yb=0;
       Map<Integer, String> level4b=rdb.getLevel4Items("Current Assets, Loans And Advances");  
        PdfPCell cellLeve3a= new PdfPCell(new Paragraph("Current Assets, Loans And Advances",font2));
        cellLeve3a.setColspan(6);
      cellLeve3a.setFixedHeight(16.2f);
      cellLeve3a.disableBorderSide(LEFT);
      cellLeve3a.disableBorderSide(TOP);
      cellLeve3a.disableBorderSide(RIGHT);
      cellLeve3a.disableBorderSide(BOTTOM);
      cellLeve3a.setPadding(.5f);
      cellLeve3a.setBorderColorBottom(BaseColor.WHITE);
      cellLeve3a.setBorderColorTop(BaseColor.WHITE);
      cellLeve3a.setBorderColorLeft(BaseColor.WHITE);
      cellLeve3a.setBorderColorRight(BaseColor.WHITE);
      cellLeve3a.setVerticalAlignment(Element.ALIGN_TOP);
      table.addCell(cellLeve3a);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     
           while(yb<=level4b.size()-1){
        switch(level4b.get(yb)){
        case "Inventories":
            int zb=0; double sumb=0.0; boolean inventoExists=false;
       Map<Integer, String> level5b=rdb.getLevel5Items("Inventories"); 
  while(zb<=level5b.size()-1){
      
        if(parseDouble(rdb.getBalancesSheet(level5b.get(zb),sheetDate,c).get(level5b.get(zb)).toString())!=0.0){
     
            if(!inventoExists){
            table.addCell(cellEmpty);
     PdfPCell cellLeve4= new PdfPCell(new Paragraph("Inventories",font2));
     cellLeve4.setColspan(5);
      cellLeve4.setFixedHeight(16.2f);
      cellLeve4.disableBorderSide(LEFT);
      cellLeve4.disableBorderSide(TOP);
      cellLeve4.disableBorderSide(RIGHT);
      cellLeve4.disableBorderSide(BOTTOM);
      cellLeve4.setPadding(.5f);
      cellLeve4.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4.setBorderColorTop(BaseColor.WHITE);
      cellLeve4.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4.setBorderColorRight(BaseColor.WHITE);
      cellLeve4.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4);
             inventoExists=true;
            }
      table.addCell(cellEmpty);
      PdfPCell cellLeve5= new PdfPCell(new Paragraph(level5b.get(zb),font1));
      cellLeve5.setFixedHeight(16.2f);
      cellLeve5.disableBorderSide(LEFT);
      cellLeve5.disableBorderSide(TOP);
      cellLeve5.disableBorderSide(RIGHT);
      cellLeve5.disableBorderSide(BOTTOM);
      cellLeve5.setPadding(.5f);
      cellLeve5.setBorderColorBottom(BaseColor.WHITE);
      cellLeve5.setBorderColorTop(BaseColor.WHITE);
      cellLeve5.setBorderColorLeft(BaseColor.WHITE);
      cellLeve5.setBorderColorRight(BaseColor.WHITE);
      cellLeve5.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cellLeve5);
    
      table.addCell(cellEmpty);
      PdfPCell balAmountb=null;
      double amountb=parseDouble(rdb.getBalancesSheet(level5b.get(zb),sheetDate,c).get(level5b.get(zb)).toString());
      if(amountb<0.0){
       balAmountb= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(amountb+"")+")",font1));
      } else{
      
       balAmountb= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(amountb+""),font1));
      
      }
     
      balAmountb.setFixedHeight(16.2f);
      balAmountb.disableBorderSide(LEFT);
      balAmountb.disableBorderSide(TOP);
      balAmountb.disableBorderSide(RIGHT);
      balAmountb.disableBorderSide(BOTTOM);
      balAmountb.setPadding(.5f);
      balAmountb.setBorderColorBottom(BaseColor.WHITE);
      balAmountb.setBorderColorTop(BaseColor.WHITE);
      balAmountb.setBorderColorLeft(BaseColor.WHITE);
      balAmountb.setBorderColorRight(BaseColor.WHITE);
      balAmountb.setHorizontalAlignment(Element.ALIGN_CENTER);
     
     table.addCell(balAmountb);
      table.addCell(cellEmpty);
     
      table.addCell(cellEmpty);
       
       sumb= sumb+(parseDouble(rdb.getBalancesSheet(level5b.get(zb),sheetDate,c).get(level5b.get(zb)).toString()));   
    indicatorInvento=true;
        }
         
           zb++; 
                 }
              if(indicatorInvento){
             TotalHolder.put(5, sumb);
             table.addCell(cellEmpty);
          PdfPCell totalValue= new PdfPCell(new Paragraph("Total Inventory Holdings",font3));
      totalValue.setFixedHeight(16.2f);
      totalValue.disableBorderSide(LEFT);
      totalValue.disableBorderSide(TOP);
      totalValue.disableBorderSide(RIGHT);
      totalValue.disableBorderSide(BOTTOM);
      totalValue.setPadding(.5f);
      totalValue.setBorderColorBottom(BaseColor.WHITE);
      totalValue.setBorderColorTop(BaseColor.WHITE);
      totalValue.setBorderColorLeft(BaseColor.WHITE);
      totalValue.setBorderColorRight(BaseColor.WHITE);
      totalValue.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(totalValue);  
      table.addCell(cellEmpty);
      PdfPCell totalAmount=null;
      double sumTotal= TotalHolder.get(5);
      if(sumTotal<0){
       totalAmount= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(sumTotal+"")+")",font1));
      } else{
    totalAmount= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(sumTotal+""),font1));
      }
        totalAmount.setFixedHeight(16.2f);
      totalAmount.disableBorderSide(LEFT);
      totalAmount.disableBorderSide(RIGHT);
      totalAmount.setPadding(.5f);
      totalAmount.setBorderColorBottom(BaseColor.BLACK);
      totalAmount.setBorderColorTop(BaseColor.BLACK);
      totalAmount.setBorderColorLeft(BaseColor.WHITE);
      totalAmount.setBorderColorRight(BaseColor.WHITE);
      totalAmount.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(totalAmount);
      table.addCell(cellEmpty); 
       table.addCell(cellEmpty);
       
       table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
              }
        break;
            
    
      case   "Accounts Receivables/Debtors":
         int z1b=0; double sum1b=0.0;boolean accountsRExists=false;
       Map<Integer, String> level5ab=rdb.getLevel5Items("Accounts Receivables/Debtors"); 
        while(z1b<=level5ab.size()-1){
      
        if(parseDouble(rdb.getBalancesSheet(level5ab.get(z1b),sheetDate,c).get(level5ab.get(z1b)).toString())!=0.0){
            if(!accountsRExists){
      table.addCell(cellEmpty);
     PdfPCell cellLeve4a= new PdfPCell(new Paragraph("Accounts Receivables/Debtors",font2));
     cellLeve4a.setColspan(5);
      cellLeve4a.setFixedHeight(16.2f);
      cellLeve4a.disableBorderSide(LEFT);
      cellLeve4a.disableBorderSide(TOP);
      cellLeve4a.disableBorderSide(RIGHT);
      cellLeve4a.disableBorderSide(BOTTOM);
      cellLeve4a.setPadding(.5f);
      cellLeve4a.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4a.setBorderColorTop(BaseColor.WHITE);
      cellLeve4a.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4a.setBorderColorRight(BaseColor.WHITE);
      cellLeve4a.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4a);
            accountsRExists=true;
            }
      table.addCell(cellEmpty);
      PdfPCell cellLeve5= new PdfPCell(new Paragraph(level5ab.get(z1b),font1));
      cellLeve5.setFixedHeight(16.2f);
      cellLeve5.disableBorderSide(LEFT);
      cellLeve5.disableBorderSide(TOP);
      cellLeve5.disableBorderSide(RIGHT);
      cellLeve5.disableBorderSide(BOTTOM);
      cellLeve5.setPadding(.5f);
      cellLeve5.setBorderColorBottom(BaseColor.WHITE);
      cellLeve5.setBorderColorTop(BaseColor.WHITE);
      cellLeve5.setBorderColorLeft(BaseColor.WHITE);
      cellLeve5.setBorderColorRight(BaseColor.WHITE);
      cellLeve5.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cellLeve5);
    
      table.addCell(cellEmpty);
     
     PdfPCell balAmountb=null;
      double amountb=parseDouble(rdb.getBalancesSheet(level5ab.get(z1b),sheetDate,c).get(level5ab.get(z1b)).toString());
      if(amountb<0.0){
       balAmountb= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(amountb+"")+")",font1));
      } else{
      
       balAmountb= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(amountb+""),font1));
      }
      balAmountb.setFixedHeight(16.2f);
      balAmountb.disableBorderSide(LEFT);
      balAmountb.disableBorderSide(TOP);
      balAmountb.disableBorderSide(RIGHT);
      balAmountb.disableBorderSide(BOTTOM);
      balAmountb.setPadding(.5f);
      balAmountb.setBorderColorBottom(BaseColor.WHITE);
      balAmountb.setBorderColorTop(BaseColor.WHITE);
      balAmountb.setBorderColorLeft(BaseColor.WHITE);
      balAmountb.setBorderColorRight(BaseColor.WHITE);
      balAmountb.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(balAmountb);
     
      table.addCell(cellEmpty);
     
      table.addCell(cellEmpty);
         
      indicatorReceivable=true;
        }
       sum1b= sum1b+(parseDouble(rdb.getBalancesSheet(level5ab.get(z1b),sheetDate,c).get(level5ab.get(z1b)).toString()));   
        z1b++; }
              if(indicatorReceivable){
               TotalHolder.put(6, sum1b);
             table.addCell(cellEmpty);
          PdfPCell totalValueInvetments= new PdfPCell(new Paragraph("Total Value of Accounts Receivable",font3));
      totalValueInvetments.setFixedHeight(16.2f);
      totalValueInvetments.disableBorderSide(LEFT);
      totalValueInvetments.disableBorderSide(TOP);
      totalValueInvetments.disableBorderSide(RIGHT);
      totalValueInvetments.disableBorderSide(BOTTOM);
      totalValueInvetments.setPadding(.5f);
      totalValueInvetments.setBorderColorBottom(BaseColor.WHITE);
      totalValueInvetments.setBorderColorTop(BaseColor.WHITE);
      totalValueInvetments.setBorderColorLeft(BaseColor.WHITE);
      totalValueInvetments.setBorderColorRight(BaseColor.WHITE);
      totalValueInvetments.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(totalValueInvetments);  
      table.addCell(cellEmpty);
      
      PdfPCell totalAmountInvest=null;
              double totalAmountInv=0.0;
              totalAmountInv= TotalHolder.get(6);
              if(totalAmountInv<0.0){
            totalAmountInvest   = new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(totalAmountInv+"")+")",font1));
              
              } else{
          totalAmountInvest   = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(totalAmountInv+""),font1));    
              }
     
      
      totalAmountInvest.setFixedHeight(16.2f);
      totalAmountInvest.disableBorderSide(LEFT);
      totalAmountInvest.disableBorderSide(RIGHT);
      totalAmountInvest.setPadding(.5f);
      totalAmountInvest.setBorderColorBottom(BaseColor.BLACK);
      totalAmountInvest.setBorderColorTop(BaseColor.BLACK);
      totalAmountInvest.setBorderColorLeft(BaseColor.WHITE);
      totalAmountInvest.setBorderColorRight(BaseColor.WHITE);
      totalAmountInvest.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(totalAmountInvest);
      table.addCell(cellEmpty); 
       table.addCell(cellEmpty);
       
        table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);                
              }             
         break; 
          
        case   "Cash And Bank Balance":
         int z1bc=0; double sum1bc=0.0;boolean cashBankBal=false;
       Map<Integer, String> level5abc=rdb.getLevel5Items("Cash And Bank Balance"); 
       while(z1bc<=level5abc.size()-1){
      
        if(parseDouble(rdb.getBalancesSheet(level5abc.get(z1bc),sheetDate,c).get(level5abc.get(z1bc)).toString())!=0.0){
            if(!cashBankBal){
      table.addCell(cellEmpty);
     PdfPCell cellLeve4ac= new PdfPCell(new Paragraph("Cash And Bank Balance",font2));
     cellLeve4ac.setColspan(5);
      cellLeve4ac.setFixedHeight(16.2f);
      cellLeve4ac.disableBorderSide(LEFT);
      cellLeve4ac.disableBorderSide(TOP);
      cellLeve4ac.disableBorderSide(RIGHT);
      cellLeve4ac.disableBorderSide(BOTTOM);
      cellLeve4ac.setPadding(.5f);
      cellLeve4ac.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4ac.setBorderColorTop(BaseColor.WHITE);
      cellLeve4ac.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4ac.setBorderColorRight(BaseColor.WHITE);
      cellLeve4ac.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4ac);
           cashBankBal=true;
            }
     
      table.addCell(cellEmpty);
      PdfPCell cellLeve5c= new PdfPCell(new Paragraph(level5abc.get(z1bc),font1));
      cellLeve5c.setFixedHeight(16.2f);
      cellLeve5c.disableBorderSide(LEFT);
      cellLeve5c.disableBorderSide(TOP);
      cellLeve5c.disableBorderSide(RIGHT);
      cellLeve5c.disableBorderSide(BOTTOM);
      cellLeve5c.setPadding(.5f);
      cellLeve5c.setBorderColorBottom(BaseColor.WHITE);
      cellLeve5c.setBorderColorTop(BaseColor.WHITE);
      cellLeve5c.setBorderColorLeft(BaseColor.WHITE);
      cellLeve5c.setBorderColorRight(BaseColor.WHITE);
      cellLeve5c.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cellLeve5c);
    
      table.addCell(cellEmpty);
     
     PdfPCell balAmountbc=null;
      double amountbc=parseDouble(rdb.getBalancesSheet(level5abc.get(z1bc),sheetDate,c).get(level5abc.get(z1bc)).toString());
      if(amountbc<0.0){
       balAmountbc= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(amountbc+"")+")",font1));
      } else{
      
       balAmountbc= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(amountbc+""),font1));
      }
      balAmountbc.setFixedHeight(16.2f);
      balAmountbc.disableBorderSide(LEFT);
      balAmountbc.disableBorderSide(TOP);
      balAmountbc.disableBorderSide(RIGHT);
      balAmountbc.disableBorderSide(BOTTOM);
      balAmountbc.setPadding(.5f);
      balAmountbc.setBorderColorBottom(BaseColor.WHITE);
      balAmountbc.setBorderColorTop(BaseColor.WHITE);
      balAmountbc.setBorderColorLeft(BaseColor.WHITE);
      balAmountbc.setBorderColorRight(BaseColor.WHITE);
      balAmountbc.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(balAmountbc);
     
      table.addCell(cellEmpty);
     
      table.addCell(cellEmpty);
        
    indicatorCash=true;
        }
        
       sum1bc= sum1bc+(parseDouble(rdb.getBalancesSheet(level5abc.get(z1bc),sheetDate,c).get(level5abc.get(z1bc)).toString()));   
        z1bc++; }
              if(indicatorCash){
               TotalHolder.put(7, sum1bc);
             table.addCell(cellEmpty);
          PdfPCell totalValueInvetmentsc= new PdfPCell(new Paragraph("Total Value of Cash And Bank Balances",font3));
      totalValueInvetmentsc.setFixedHeight(16.2f);
      totalValueInvetmentsc.disableBorderSide(LEFT);
      totalValueInvetmentsc.disableBorderSide(TOP);
      totalValueInvetmentsc.disableBorderSide(RIGHT);
      totalValueInvetmentsc.disableBorderSide(BOTTOM);
      totalValueInvetmentsc.setPadding(.5f);
      totalValueInvetmentsc.setBorderColorBottom(BaseColor.WHITE);
      totalValueInvetmentsc.setBorderColorTop(BaseColor.WHITE);
      totalValueInvetmentsc.setBorderColorLeft(BaseColor.WHITE);
      totalValueInvetmentsc.setBorderColorRight(BaseColor.WHITE);
      totalValueInvetmentsc.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(totalValueInvetmentsc);  
      table.addCell(cellEmpty);
      
      PdfPCell totalAmountInvestc=null;
              double totalAmountInvc=0.0;
              totalAmountInvc= TotalHolder.get(7);
              if(totalAmountInvc<0.0){
            totalAmountInvestc   = new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(totalAmountInvc+"")+")",font1));
              
              } else{
          totalAmountInvestc   = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(totalAmountInvc+""),font1));    
              }
     
      
      totalAmountInvestc.setFixedHeight(16.2f);
      totalAmountInvestc.disableBorderSide(LEFT);
      totalAmountInvestc.disableBorderSide(RIGHT);
      totalAmountInvestc.setPadding(.5f);
      totalAmountInvestc.setBorderColorBottom(BaseColor.BLACK);
      totalAmountInvestc.setBorderColorTop(BaseColor.BLACK);
      totalAmountInvestc.setBorderColorLeft(BaseColor.WHITE);
      totalAmountInvestc.setBorderColorRight(BaseColor.WHITE);
      totalAmountInvestc.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(totalAmountInvestc);
      table.addCell(cellEmpty); 
       table.addCell(cellEmpty);
       
        table.addCell(cellEmpty);
        
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);              
              }              
         break;  
            
            case   "Loans And Advances":
         int z1bA1=0; double sum1bA1=0.0;boolean loanThere=false;
       Map<Integer, String> level5abA1=rdb.getLevel5Items("Loans And Advances"); 
       while(z1bA1<=level5abA1.size()-1){
      
        if(parseDouble(rdb.getBalancesSheet(level5abA1.get(z1bA1),sheetDate,c).get(level5abA1.get(z1bA1)).toString())!=0.0){
            if(!loanThere){
      table.addCell(cellEmpty);
     PdfPCell cellLeve4aA1= new PdfPCell(new Paragraph("Loans And Advances",font2));
     cellLeve4aA1.setColspan(5);
      cellLeve4aA1.setFixedHeight(16.2f);
      cellLeve4aA1.disableBorderSide(LEFT);
      cellLeve4aA1.disableBorderSide(TOP);
      cellLeve4aA1.disableBorderSide(RIGHT);
      cellLeve4aA1.disableBorderSide(BOTTOM);
      cellLeve4aA1.setPadding(.5f);
      cellLeve4aA1.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4aA1.setBorderColorTop(BaseColor.WHITE);
      cellLeve4aA1.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4aA1.setBorderColorRight(BaseColor.WHITE);
      cellLeve4aA1.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4aA1);
       loanThere=true;
            }
     
      table.addCell(cellEmpty);
      PdfPCell cellLeve5= new PdfPCell(new Paragraph(level5abA1.get(z1bA1),font1));
      cellLeve5.setFixedHeight(16.2f);
      cellLeve5.disableBorderSide(LEFT);
      cellLeve5.disableBorderSide(TOP);
      cellLeve5.disableBorderSide(RIGHT);
      cellLeve5.disableBorderSide(BOTTOM);
      cellLeve5.setPadding(.5f);
      cellLeve5.setBorderColorBottom(BaseColor.WHITE);
      cellLeve5.setBorderColorTop(BaseColor.WHITE);
      cellLeve5.setBorderColorLeft(BaseColor.WHITE);
      cellLeve5.setBorderColorRight(BaseColor.WHITE);
      cellLeve5.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cellLeve5);
    
      table.addCell(cellEmpty);
     
     PdfPCell balAmountb1=null;
      double amountbA1=parseDouble(rdb.getBalancesSheet(level5abA1.get(z1bA1),sheetDate,c).get(level5abA1.get(z1bA1)).toString());
      if(amountbA1<0.0){
       balAmountb1= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(amountbA1+"")+")",font1));
      } else{
      
       balAmountb1= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(amountbA1+""),font1));
      }
      balAmountb1.setFixedHeight(16.2f);
      balAmountb1.disableBorderSide(LEFT);
      balAmountb1.disableBorderSide(TOP);
      balAmountb1.disableBorderSide(RIGHT);
      balAmountb1.disableBorderSide(BOTTOM);
      balAmountb1.setPadding(.5f);
      balAmountb1.setBorderColorBottom(BaseColor.WHITE);
      balAmountb1.setBorderColorTop(BaseColor.WHITE);
      balAmountb1.setBorderColorLeft(BaseColor.WHITE);
      balAmountb1.setBorderColorRight(BaseColor.WHITE);
      balAmountb1.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(balAmountb1);
     
      table.addCell(cellEmpty);
     
      table.addCell(cellEmpty);
        
         indicatorLoan=true;
        }
         
       sum1bA1= sum1bA1+(parseDouble(rdb.getBalancesSheet(level5abA1.get(z1bA1),sheetDate,c).get(level5abA1.get(z1bA1)).toString()));   
        z1bA1++; }
               if(indicatorLoan){ 
               TotalHolder.put(8, sum1bA1);
             table.addCell(cellEmpty);
          PdfPCell totalValueInvetmentsA1= new PdfPCell(new Paragraph("Total Value of Loans And Advances",font3));
      totalValueInvetmentsA1.setFixedHeight(16.2f);
      totalValueInvetmentsA1.disableBorderSide(LEFT);
      totalValueInvetmentsA1.disableBorderSide(TOP);
      totalValueInvetmentsA1.disableBorderSide(RIGHT);
      totalValueInvetmentsA1.disableBorderSide(BOTTOM);
      totalValueInvetmentsA1.setPadding(.5f);
      totalValueInvetmentsA1.setBorderColorBottom(BaseColor.WHITE);
      totalValueInvetmentsA1.setBorderColorTop(BaseColor.WHITE);
      totalValueInvetmentsA1.setBorderColorLeft(BaseColor.WHITE);
      totalValueInvetmentsA1.setBorderColorRight(BaseColor.WHITE);
      totalValueInvetmentsA1.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(totalValueInvetmentsA1);  
      table.addCell(cellEmpty);
      
      PdfPCell totalAmountInvestA1=null;
              double totalAmountInvA1=0.0;
              totalAmountInvA1= TotalHolder.get(8);
              if(totalAmountInvA1<0.0){
            totalAmountInvestA1   = new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(totalAmountInvA1+"")+")",font1));
              
              } else{
          totalAmountInvestA1   = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(totalAmountInvA1+""),font1));    
              }
     
      
      totalAmountInvestA1.setFixedHeight(16.2f);
      totalAmountInvestA1.disableBorderSide(LEFT);
      totalAmountInvestA1.disableBorderSide(RIGHT);
      totalAmountInvestA1.setPadding(.5f);
      totalAmountInvestA1.setBorderColorBottom(BaseColor.BLACK);
      totalAmountInvestA1.setBorderColorTop(BaseColor.BLACK);
      totalAmountInvestA1.setBorderColorLeft(BaseColor.WHITE);
      totalAmountInvestA1.setBorderColorRight(BaseColor.WHITE);
      totalAmountInvestA1.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(totalAmountInvestA1);
      table.addCell(cellEmpty); 
       table.addCell(cellEmpty);
                        
              }              
         break;
           }
          
          yb++; 
           }
        
       double totalCurrentAssets=0.0;
       if(indicatorCash||indicatorLoan||indicatorReceivable||indicatorInvento){
        totalCurrentAssets= TotalHolder.get(5)+ TotalHolder.get(6)+ TotalHolder.get(7)+ TotalHolder.get(8);
        TotalHolder.put(9, totalCurrentAssets);
        table.addCell(cellEmpty);
        table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     PdfPCell cellLevex= new PdfPCell(new Paragraph("Total Current Assets,Loans And Advances",font4));
      cellLevex.setFixedHeight(16.2f);
      cellLevex.disableBorderSide(LEFT);
      cellLevex.disableBorderSide(TOP);
      cellLevex.disableBorderSide(RIGHT);
      cellLevex.disableBorderSide(BOTTOM);
      cellLevex.setPadding(.5f);
      cellLevex.setBorderColorBottom(BaseColor.WHITE);
      cellLevex.setBorderColorTop(BaseColor.WHITE);
      cellLevex.setBorderColorLeft(BaseColor.WHITE);
      cellLevex.setBorderColorRight(BaseColor.WHITE);
      cellLevex.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLevex);  
     
       table.addCell(cellEmpty); 
       
        PdfPCell totalCurrentAssetxs=null;
        
        if(TotalHolder.get(9)<0){
            
        totalCurrentAssetxs= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(9)+"")+")",font4)); 
        
        }else{totalCurrentAssetxs= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(9)+""),font4)); }
        
       
      totalCurrentAssetxs.setFixedHeight(16.2f);
      totalCurrentAssetxs.disableBorderSide(LEFT);
      totalCurrentAssetxs.disableBorderSide(RIGHT);
      totalCurrentAssetxs.disableBorderSide(TOP);
      totalCurrentAssetxs.setPadding(.5f);
      totalCurrentAssetxs.setBorderColorBottom(BaseColor.BLACK);
      totalCurrentAssetxs.setBorderColorTop(BaseColor.WHITE);
      totalCurrentAssetxs.setBorderColorLeft(BaseColor.WHITE);
      totalCurrentAssetxs.setBorderColorRight(BaseColor.WHITE);
      totalCurrentAssetxs.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(totalCurrentAssetxs);
      table.addCell(cellEmpty);
        table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
       }
           break;
             
       case   "Deferred Taxes Assets" :
           
  boolean defferedTaxa=false;
  
        int zX=0; double sumX=0.0; 
       Map<Integer, String> level5=rdb.getLevel5Items("Deferred Tax Assets"); 
      
      
        if(parseDouble(rdb.getBalancesSheet(level5.get(zX),sheetDate,c).get(level5.get(zX)).toString())!=0.0){
            
       PdfPCell cellLeve3X= new PdfPCell(new Paragraph( "Deferred Tax Assets",font2));
        cellLeve3X.setColspan(6);
      cellLeve3X.setFixedHeight(16.2f);
      cellLeve3X.disableBorderSide(LEFT);
      cellLeve3X.disableBorderSide(TOP);
      cellLeve3X.disableBorderSide(RIGHT);
      cellLeve3X.disableBorderSide(BOTTOM);
      cellLeve3X.setPadding(.5f);
      cellLeve3X.setBorderColorBottom(BaseColor.WHITE);
      cellLeve3X.setBorderColorTop(BaseColor.WHITE);
      cellLeve3X.setBorderColorLeft(BaseColor.WHITE);
      cellLeve3X.setBorderColorRight(BaseColor.WHITE);
      cellLeve3X.setVerticalAlignment(Element.ALIGN_TOP);
      table.addCell(cellLeve3X);
      
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
   
          

      table.addCell(cellEmpty);
     PdfPCell cellLeve4X= new PdfPCell(new Paragraph("Deferred Tax Assets",font2));
     cellLeve4X.setColspan(5);
      cellLeve4X.setFixedHeight(16.2f);
      cellLeve4X.disableBorderSide(LEFT);
      cellLeve4X.disableBorderSide(TOP);
      cellLeve4X.disableBorderSide(RIGHT);
      cellLeve4X.disableBorderSide(BOTTOM);
      cellLeve4X.setPadding(.5f);
      cellLeve4X.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4X.setBorderColorTop(BaseColor.WHITE);
      cellLeve4X.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4X.setBorderColorRight(BaseColor.WHITE);
      cellLeve4X.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4X);
            
        
      table.addCell(cellEmpty);
      PdfPCell cellLeve5X= new PdfPCell(new Paragraph(level5.get(zX),font1));
      cellLeve5X.setFixedHeight(16.2f);
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
    
      table.addCell(cellEmpty);
      PdfPCell balAmountX=null;
      double amountX=parseDouble(rdb.getBalancesSheet(level5.get(zX),sheetDate,c).get(level5.get(zX)).toString());
      
      if(amountX<0.0){
       balAmountX= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(amountX+"")+")",font1));
      } else{
      
       balAmountX= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(amountX+""),font1));
      
      }
     
      balAmountX.setFixedHeight(16.2f);
      balAmountX.disableBorderSide(LEFT);
      balAmountX.disableBorderSide(TOP);
      balAmountX.disableBorderSide(RIGHT);
      balAmountX.disableBorderSide(BOTTOM);
      balAmountX.setPadding(.5f);
      balAmountX.setBorderColorBottom(BaseColor.WHITE);
      balAmountX.setBorderColorTop(BaseColor.WHITE);
      balAmountX.setBorderColorLeft(BaseColor.WHITE);
      balAmountX.setBorderColorRight(BaseColor.WHITE);
      balAmountX.setHorizontalAlignment(Element.ALIGN_CENTER);
     
     table.addCell(balAmountX);
      table.addCell(cellEmpty);
     
      table.addCell(cellEmpty);
       
       sumX= sumX+(parseDouble(rdb.getBalancesSheet(level5.get(zX),sheetDate,c).get(level5.get(zX)).toString()));  
       defferedTaxa=true;
        }
         
        
              if(defferedTaxa){
             TotalHolder.put(10, sumX);
             table.addCell(cellEmpty);
          PdfPCell totalValue= new PdfPCell(new Paragraph("Total Deferred Tax Assets",font3));
      totalValue.setFixedHeight(16.2f);
      totalValue.disableBorderSide(LEFT);
      totalValue.disableBorderSide(TOP);
      totalValue.disableBorderSide(RIGHT);
      totalValue.disableBorderSide(BOTTOM);
      totalValue.setPadding(.5f);
      totalValue.setBorderColorBottom(BaseColor.WHITE);
      totalValue.setBorderColorTop(BaseColor.WHITE);
      totalValue.setBorderColorLeft(BaseColor.WHITE);
      totalValue.setBorderColorRight(BaseColor.WHITE);
      totalValue.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(totalValue);  
      table.addCell(cellEmpty);
      
        PdfPCell totalAmountX= null;
     if(TotalHolder.get(10)<0.0){
     totalAmountX= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(10)+"")+")",font1));
     
     }else{totalAmountX= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(10)+""),font1));}
        totalAmountX.setFixedHeight(16.2f);
      totalAmountX.disableBorderSide(LEFT);
      totalAmountX.disableBorderSide(RIGHT);
      totalAmountX.setPadding(.5f);
      totalAmountX.setBorderColorBottom(BaseColor.BLACK);
      totalAmountX.setBorderColorTop(BaseColor.BLACK);
      totalAmountX.setBorderColorLeft(BaseColor.WHITE);
      totalAmountX.setBorderColorRight(BaseColor.WHITE);
      totalAmountX.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(totalAmountX);
      table.addCell(cellEmpty); 
       table.addCell(cellEmpty);
              }
         
           
          
         
           if(defferedTaxa){
           double totalDefered=0.0;
     
       totalDefered=TotalHolder.get(10);
       
       
       TotalHolder.put(11, totalDefered);
       table.addCell(cellEmpty);
       table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     PdfPCell cellLeve4a1X= new PdfPCell(new Paragraph("Total Deferred Tax Assets",font4));
      cellLeve4a1X.setFixedHeight(16.2f);
      cellLeve4a1X.disableBorderSide(LEFT);
      cellLeve4a1X.disableBorderSide(TOP);
      cellLeve4a1X.disableBorderSide(RIGHT);
      cellLeve4a1X.disableBorderSide(BOTTOM);
      cellLeve4a1X.setPadding(.5f);
      cellLeve4a1X.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4a1X.setBorderColorTop(BaseColor.WHITE);
      cellLeve4a1X.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4a1X.setBorderColorRight(BaseColor.WHITE);
      cellLeve4a1X.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4a1X);  
       table.addCell(cellEmpty);
      
       PdfPCell totalAmountInvestsX= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(11)+""),font4)); 
        totalAmountInvestsX.setFixedHeight(16.2f);
      totalAmountInvestsX.disableBorderSide(LEFT);
      totalAmountInvestsX.disableBorderSide(RIGHT);
      totalAmountInvestsX.disableBorderSide(TOP);
      totalAmountInvestsX.setPadding(.5f);
      totalAmountInvestsX.setBorderColorBottom(BaseColor.BLACK);
      totalAmountInvestsX.setBorderColorTop(BaseColor.WHITE);
      totalAmountInvestsX.setBorderColorLeft(BaseColor.WHITE);
      totalAmountInvestsX.setBorderColorRight(BaseColor.WHITE);
      totalAmountInvestsX.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(totalAmountInvestsX);
      table.addCell(cellEmpty);
       table.addCell(cellEmpty);
       
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);  
           }
       break;     
   
        }
        x++;
        }  

       int xL=0; 
      Map<Integer, String> level3L=rdb. getLevel3Items("Liabilities");
       PdfPCell cellLeve2L= new PdfPCell(new Paragraph("Liabilities",font2));
       cellLeve2L.setColspan(6);
      cellLeve2L.setFixedHeight(16.2f);
      cellLeve2L.disableBorderSide(LEFT);
      cellLeve2L.disableBorderSide(TOP);
      cellLeve2L.disableBorderSide(RIGHT);
       cellLeve2L.setPadding(.5f);
       cellLeve2L.setBorderColorBottom(BaseColor.BLACK);
       cellLeve2L.setBorderColorTop(BaseColor.WHITE);
       cellLeve2L.setBorderColorLeft(BaseColor.WHITE);
       cellLeve2L.setBorderColorRight(BaseColor.WHITE);
       cellLeve2L.setVerticalAlignment(Element.ALIGN_TOP);
        table.addCell(cellLeve2L);
        while(xL<=level3L.size()-1){
        switch(level3L.get(xL)){
       case "Current Liabilities & Provisions":
           int yL=0;
       Map<Integer, String> level4L=rdb.getLevel4Items("Current Liabilities & Provisions");   
       PdfPCell cellLeve3L= new PdfPCell(new Paragraph("Current Liabilities And Provisions",font2));
        cellLeve3L.setColspan(6);
      cellLeve3L.setFixedHeight(16.2f);
      cellLeve3L.disableBorderSide(LEFT);
      cellLeve3L.disableBorderSide(TOP);
      cellLeve3L.disableBorderSide(RIGHT);
      cellLeve3L.disableBorderSide(BOTTOM);
      cellLeve3L.setPadding(.5f);
      cellLeve3L.setBorderColorBottom(BaseColor.WHITE);
      cellLeve3L.setBorderColorTop(BaseColor.WHITE);
      cellLeve3L.setBorderColorLeft(BaseColor.WHITE);
      cellLeve3L.setBorderColorRight(BaseColor.WHITE);
      cellLeve3L.setVerticalAlignment(Element.ALIGN_TOP);
      table.addCell(cellLeve3L);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     
           while(yL<=level4L.size()-1){
        switch(level4L.get(yL)){
        case "Current Liabilities":
            int zL=0; double sumL=0.0; boolean currentThere=false;
       Map<Integer, String> level5L=rdb.getLevel5Items("Current Liabilities"); 
   while(zL<=level5L.size()-1){
      
        if(parseDouble(rdb.getBalancesSheet(level5L.get(zL),sheetDate,c).get(level5L.get(zL)).toString())!=0.0){
            if(!currentThere){
      table.addCell(cellEmpty);
     PdfPCell cellLeve4L= new PdfPCell(new Paragraph("Current Liabilities",font2));
     cellLeve4L.setColspan(5);
      cellLeve4L.setFixedHeight(16.2f);
      cellLeve4L.disableBorderSide(LEFT);
      cellLeve4L.disableBorderSide(TOP);
      cellLeve4L.disableBorderSide(RIGHT);
      cellLeve4L.disableBorderSide(BOTTOM);
      cellLeve4L.setPadding(.5f);
      cellLeve4L.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4L.setBorderColorTop(BaseColor.WHITE);
      cellLeve4L.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4L.setBorderColorRight(BaseColor.WHITE);
      cellLeve4L.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4L);
       currentThere=true;
            }
        
      table.addCell(cellEmpty);
      PdfPCell cellLeve5L= new PdfPCell(new Paragraph(level5L.get(zL),font1));
      cellLeve5L.setFixedHeight(16.2f);
      cellLeve5L.disableBorderSide(LEFT);
      cellLeve5L.disableBorderSide(TOP);
      cellLeve5L.disableBorderSide(RIGHT);
      cellLeve5L.disableBorderSide(BOTTOM);
      cellLeve5L.setPadding(.5f);
      cellLeve5L.setBorderColorBottom(BaseColor.WHITE);
      cellLeve5L.setBorderColorTop(BaseColor.WHITE);
      cellLeve5L.setBorderColorLeft(BaseColor.WHITE);
      cellLeve5L.setBorderColorRight(BaseColor.WHITE);
      cellLeve5L.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cellLeve5L);
      table.addCell(cellEmpty);
   
      PdfPCell balAmountL=null;
      double amountL=parseDouble(rdb.getBalancesSheet(level5L.get(zL),sheetDate,c).get(level5L.get(zL)).toString());
      if(amountL<0.0){
       balAmountL= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(amountL+"")+")",font1));
      } else{
      
       balAmountL= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(amountL+""),font1));
      
      }
     
      balAmountL.setFixedHeight(16.2f);
      balAmountL.disableBorderSide(LEFT);
      balAmountL.disableBorderSide(TOP);
      balAmountL.disableBorderSide(RIGHT);
      balAmountL.disableBorderSide(BOTTOM);
      balAmountL.setPadding(.5f);
      balAmountL.setBorderColorBottom(BaseColor.WHITE);
      balAmountL.setBorderColorTop(BaseColor.WHITE);
      balAmountL.setBorderColorLeft(BaseColor.WHITE);
      balAmountL.setBorderColorRight(BaseColor.WHITE);
      balAmountL.setHorizontalAlignment(Element.ALIGN_CENTER);
     
     table.addCell(balAmountL);
      table.addCell(cellEmpty);
     
      table.addCell(cellEmpty);
       
       sumL= sumL+(parseDouble(rdb.getBalancesSheet(level5L.get(zL),sheetDate,c).get(level5L.get(zL)).toString()));     
       currentLiabCred=true; 
        }
      
           zL++; 
                 }
              if(currentLiabCred){
             TotalHolder.put(12, sumL);
             table.addCell(cellEmpty);
          PdfPCell totalValue= new PdfPCell(new Paragraph("Total Current Liabilities",font3));
      totalValue.setFixedHeight(16.2f);
      totalValue.disableBorderSide(LEFT);
      totalValue.disableBorderSide(TOP);
      totalValue.disableBorderSide(RIGHT);
      totalValue.disableBorderSide(BOTTOM);
      totalValue.setPadding(.5f);
      totalValue.setBorderColorBottom(BaseColor.WHITE);
      totalValue.setBorderColorTop(BaseColor.WHITE);
      totalValue.setBorderColorLeft(BaseColor.WHITE);
      totalValue.setBorderColorRight(BaseColor.WHITE);
      totalValue.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(totalValue);  
      table.addCell(cellEmpty);
      
        PdfPCell totalAmount= null;
     if(TotalHolder.get(12)<0.0)
     {totalAmount= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(12)+"")+")",font1));
     } else{totalAmount= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(12)+""),font1));}
        totalAmount.setFixedHeight(16.2f);
      totalAmount.disableBorderSide(LEFT);
      totalAmount.disableBorderSide(RIGHT);
      totalAmount.setPadding(.5f);
      totalAmount.setBorderColorBottom(BaseColor.BLACK);
      totalAmount.setBorderColorTop(BaseColor.BLACK);
      totalAmount.setBorderColorLeft(BaseColor.WHITE);
      totalAmount.setBorderColorRight(BaseColor.WHITE);
      totalAmount.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(totalAmount);
      table.addCell(cellEmpty); 
       table.addCell(cellEmpty);
              }
              break;
            
    
      case   "Provisions" :
         int z1L=0; double sum1L=0.0;boolean provisionsThere=false;
       Map<Integer, String> level5aL=rdb.getLevel5Items("Provisions"); 
         while(z1L<=level5aL.size()-1){
      
        if(parseDouble(rdb.getBalancesSheet(level5aL.get(z1L),sheetDate,c).get(level5aL.get(z1L)).toString())!=0.0){
            if(!provisionsThere){
      table.addCell(cellEmpty);
     PdfPCell cellLeve4aL= new PdfPCell(new Paragraph("Provisions",font2));
     cellLeve4aL.setColspan(5);
      cellLeve4aL.setFixedHeight(16.2f);
      cellLeve4aL.disableBorderSide(LEFT);
      cellLeve4aL.disableBorderSide(TOP);
      cellLeve4aL.disableBorderSide(RIGHT);
      cellLeve4aL.disableBorderSide(BOTTOM);
      cellLeve4aL.setPadding(.5f);
      cellLeve4aL.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4aL.setBorderColorTop(BaseColor.WHITE);
      cellLeve4aL.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4aL.setBorderColorRight(BaseColor.WHITE);
      cellLeve4aL.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4aL);
       provisionsThere=true;
            }
     
      table.addCell(cellEmpty);
      PdfPCell cellLeve5L= new PdfPCell(new Paragraph(level5aL.get(z1L),font1));
      cellLeve5L.setFixedHeight(16.2f);
      cellLeve5L.disableBorderSide(LEFT);
      cellLeve5L.disableBorderSide(TOP);
      cellLeve5L.disableBorderSide(RIGHT);
      cellLeve5L.disableBorderSide(BOTTOM);
      cellLeve5L.setPadding(.5f);
      cellLeve5L.setBorderColorBottom(BaseColor.WHITE);
      cellLeve5L.setBorderColorTop(BaseColor.WHITE);
      cellLeve5L.setBorderColorLeft(BaseColor.WHITE);
      cellLeve5L.setBorderColorRight(BaseColor.WHITE);
      cellLeve5L.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cellLeve5L);
    
      table.addCell(cellEmpty);
     
     PdfPCell balAmountL=null;
      double amountL=parseDouble(rdb.getBalancesSheet(level5aL.get(z1L),sheetDate,c).get(level5aL.get(z1L)).toString());
      if(amountL<0.0){
       balAmountL= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(amountL+"")+")",font1));
      } else{
      
       balAmountL= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(amountL+""),font1));
      }
      balAmountL.setFixedHeight(16.2f);
      balAmountL.disableBorderSide(LEFT);
      balAmountL.disableBorderSide(TOP);
      balAmountL.disableBorderSide(RIGHT);
      balAmountL.disableBorderSide(BOTTOM);
      balAmountL.setPadding(.5f);
      balAmountL.setBorderColorBottom(BaseColor.WHITE);
      balAmountL.setBorderColorTop(BaseColor.WHITE);
      balAmountL.setBorderColorLeft(BaseColor.WHITE);
      balAmountL.setBorderColorRight(BaseColor.WHITE);
      balAmountL.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(balAmountL);
     
      table.addCell(cellEmpty);
     
      table.addCell(cellEmpty);
        provision=true;
       
        }
       sum1L= sum1L+(parseDouble(rdb.getBalancesSheet(level5aL.get(z1L),sheetDate,c).get(level5aL.get(z1L)).toString()));   
        z1L++; }
              if(provision){
               TotalHolder.put(13, sum1L);
             table.addCell(cellEmpty);
          PdfPCell totalValueInvetmentsP= new PdfPCell(new Paragraph("Total Provisions",font3));
      totalValueInvetmentsP.setFixedHeight(16.2f);
      totalValueInvetmentsP.disableBorderSide(LEFT);
      totalValueInvetmentsP.disableBorderSide(TOP);
      totalValueInvetmentsP.disableBorderSide(RIGHT);
      totalValueInvetmentsP.disableBorderSide(BOTTOM);
      totalValueInvetmentsP.setPadding(.5f);
      totalValueInvetmentsP.setBorderColorBottom(BaseColor.WHITE);
      totalValueInvetmentsP.setBorderColorTop(BaseColor.WHITE);
      totalValueInvetmentsP.setBorderColorLeft(BaseColor.WHITE);
      totalValueInvetmentsP.setBorderColorRight(BaseColor.WHITE);
      totalValueInvetmentsP.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(totalValueInvetmentsP);  
      table.addCell(cellEmpty);
      
      PdfPCell totalAmountInvestP=null;
              double totalAmountInvP=0.0;
              totalAmountInvP=TotalHolder.get(13);
              if(totalAmountInvP<0.0){
            totalAmountInvestP   = new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(totalAmountInvP+"")+")",font1));
              
              } else{
          totalAmountInvestP   = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(totalAmountInvP+""),font1));    
              }
     
      
      totalAmountInvestP.setFixedHeight(16.2f);
      totalAmountInvestP.disableBorderSide(LEFT);
      totalAmountInvestP.disableBorderSide(RIGHT);
      totalAmountInvestP.setPadding(.5f);
      totalAmountInvestP.setBorderColorBottom(BaseColor.BLACK);
      totalAmountInvestP.setBorderColorTop(BaseColor.BLACK);
      totalAmountInvestP.setBorderColorLeft(BaseColor.WHITE);
      totalAmountInvestP.setBorderColorRight(BaseColor.WHITE);
      totalAmountInvestP.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(totalAmountInvestP);
      table.addCell(cellEmpty); 
       table.addCell(cellEmpty);
              }          
                        
         break; 
           
           }
          
          yL++; 
           }
           if(currentLiabCred||provision){
           double totalCurrentLiabilities=0.0;
        totalCurrentLiabilities=TotalHolder.get(12)+TotalHolder.get(13);
       
       
       TotalHolder.put(14, totalCurrentLiabilities);
       table.addCell(cellEmpty);
        table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
     PdfPCell cellLeve4a1= new PdfPCell(new Paragraph("Total Current Liabilities And Provisions",font4));
      cellLeve4a1.setFixedHeight(16.2f);
      cellLeve4a1.disableBorderSide(LEFT);
      cellLeve4a1.disableBorderSide(TOP);
      cellLeve4a1.disableBorderSide(RIGHT);
      cellLeve4a1.disableBorderSide(BOTTOM);
      cellLeve4a1.setPadding(.5f);
      cellLeve4a1.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4a1.setBorderColorTop(BaseColor.WHITE);
      cellLeve4a1.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4a1.setBorderColorRight(BaseColor.WHITE);
      cellLeve4a1.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4a1);  
       table.addCell(cellEmpty);
      
       
       PdfPCell totalAmountInvests= null;
        if(TotalHolder.get(14)<0.0){ totalAmountInvests= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(14)+"")+")",font4)); }else{
         totalAmountInvests= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(14)+""),font4)); }
       totalAmountInvests.setFixedHeight(16.2f);
      totalAmountInvests.disableBorderSide(LEFT);
      totalAmountInvests.disableBorderSide(RIGHT);
      totalAmountInvests.disableBorderSide(TOP);
      totalAmountInvests.setPadding(.5f);
      totalAmountInvests.setBorderColorBottom(BaseColor.BLACK);
      totalAmountInvests.setBorderColorTop(BaseColor.WHITE);
      totalAmountInvests.setBorderColorLeft(BaseColor.WHITE);
      totalAmountInvests.setBorderColorRight(BaseColor.WHITE);
      totalAmountInvests.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(totalAmountInvests);
      table.addCell(cellEmpty);
       table.addCell(cellEmpty); 
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
           }
      if(indicatorCash||indicatorLoan||indicatorReceivable||indicatorInvento||currentLiabCred||provision){
        double netCurrentAssets=0.0;
        netCurrentAssets=MAth.subtract(TotalHolder.get(9),TotalHolder.get(14));
       
       
       TotalHolder.put(20, netCurrentAssets);
         table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      
       table.addCell(cellEmpty);
       PdfPCell cellLeve4a1L= new PdfPCell(new Paragraph("Net Value of Current Assets",font4));
      cellLeve4a1L.setFixedHeight(16.2f);
      cellLeve4a1L.disableBorderSide(LEFT);
      cellLeve4a1L.disableBorderSide(TOP);
      cellLeve4a1L.disableBorderSide(RIGHT);
      cellLeve4a1L.disableBorderSide(BOTTOM);
      cellLeve4a1L.setPadding(.5f);
      cellLeve4a1L.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4a1L.setBorderColorTop(BaseColor.WHITE);
      cellLeve4a1L.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4a1L.setBorderColorRight(BaseColor.WHITE);
      cellLeve4a1L.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4a1L);  
     
     
       table.addCell(cellEmpty);
       
       PdfPCell totalAmountInvestsN= null;
       if(TotalHolder.get(20)<0.0){totalAmountInvestsN= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(20)+"")+")",font4)); }
       else{totalAmountInvestsN= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(20)+""),font4)); }
       totalAmountInvestsN.setFixedHeight(16.2f);
      totalAmountInvestsN.disableBorderSide(LEFT);
      totalAmountInvestsN.disableBorderSide(RIGHT);
      totalAmountInvestsN.disableBorderSide(TOP);
      totalAmountInvestsN.setPadding(.5f);
      totalAmountInvestsN.setBorderColorBottom(BaseColor.BLACK);
      totalAmountInvestsN.setBorderColorTop(BaseColor.WHITE);
      totalAmountInvestsN.setBorderColorLeft(BaseColor.WHITE);
      totalAmountInvestsN.setBorderColorRight(BaseColor.WHITE);
      totalAmountInvestsN.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(totalAmountInvestsN);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
         table.addCell(cellEmpty);
        table.addCell(cellEmpty); 
       double totalAssets=0.0;
        totalAssets=MAth.add(TotalHolder.get(11),MAth.add(TotalHolder.get(20),TotalHolder.get(4)));
       
       
       TotalHolder.put(21, totalAssets);
        table.addCell(celd);
          table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
       table.addCell(cellEmpty);
             PdfPCell cellLeve4a1LT= new PdfPCell(new Paragraph("Total Assets",font2));
      cellLeve4a1LT.setFixedHeight(16.2f);
      cellLeve4a1LT.disableBorderSide(LEFT);
      cellLeve4a1LT.disableBorderSide(TOP);
      cellLeve4a1LT.disableBorderSide(RIGHT);
      cellLeve4a1LT.disableBorderSide(BOTTOM);
      cellLeve4a1LT.setPadding(.5f);
      cellLeve4a1LT.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4a1LT.setBorderColorTop(BaseColor.WHITE);
      cellLeve4a1LT.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4a1LT.setBorderColorRight(BaseColor.WHITE);
      cellLeve4a1LT.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4a1LT);  
       table.addCell(cellEmpty);
      
       
       PdfPCell totalAmountInvestsNT= null;
       if(TotalHolder.get(21)<0.0){totalAmountInvestsNT= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(21)+"")+")",font2)); }
       else{totalAmountInvestsNT= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(21)+""),font2)); }
       totalAmountInvestsNT.setFixedHeight(16.2f);
      totalAmountInvestsNT.disableBorderSide(LEFT);
      totalAmountInvestsNT.disableBorderSide(RIGHT);
      totalAmountInvestsNT.disableBorderSide(TOP);
      totalAmountInvestsNT.disableBorderSide(BOTTOM);
      totalAmountInvestsNT.setPadding(.5f);
      totalAmountInvestsNT.setBorderColorBottom(BaseColor.BLACK);
      totalAmountInvestsNT.setBorderColorTop(BaseColor.WHITE);
      totalAmountInvestsNT.setBorderColorLeft(BaseColor.WHITE);
      totalAmountInvestsNT.setBorderColorRight(BaseColor.WHITE);
      totalAmountInvestsNT.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(totalAmountInvestsNT);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
       table.addCell(celd);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
       table.addCell(cellEmpty); 
       table.addCell(cellEmpty);
        table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
       table.addCell(cellEmpty); 
       table.addCell(cellEmpty);
       }
           break;
           
           
           
       case "Non-Current Liabilities/Long Term Liabilities":
              int ybL=0;
       Map<Integer, String> level4bL=rdb.getLevel4Items("Non-Current Liabilities/Long Term Liabilities");  
        PdfPCell cellLeve3aL= new PdfPCell(new Paragraph("Non-Current Liabilities/Long Term Liabilities",font2));
        cellLeve3aL.setColspan(6);
      cellLeve3aL.setFixedHeight(16.2f);
      cellLeve3aL.disableBorderSide(LEFT);
      cellLeve3aL.disableBorderSide(TOP);
      cellLeve3aL.disableBorderSide(RIGHT);
      cellLeve3aL.disableBorderSide(BOTTOM);
      cellLeve3aL.setPadding(.5f);
      cellLeve3aL.setBorderColorBottom(BaseColor.WHITE);
      cellLeve3aL.setBorderColorTop(BaseColor.WHITE);
      cellLeve3aL.setBorderColorLeft(BaseColor.WHITE);
      cellLeve3aL.setBorderColorRight(BaseColor.WHITE);
      cellLeve3aL.setVerticalAlignment(Element.ALIGN_TOP);
      table.addCell(cellLeve3aL);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
           while(ybL<=level4bL.size()-1){
        switch(level4bL.get(ybL)){
        case "Secured Liabilities":
            int zbL=0; double sumbL=0.0; boolean suredThere=false;
       Map<Integer, String> level5bL=rdb.getLevel5Items("Secured Liabilities");
         while(zbL<=level5bL.size()-1){
      
        if(parseDouble(rdb.getBalancesSheet(level5bL.get(zbL),sheetDate,c).get(level5bL.get(zbL)).toString())!=0.0){
      if(!suredThere){
      table.addCell(cellEmpty);
     PdfPCell cellLeve4LA= new PdfPCell(new Paragraph("Secured Liabilities",font2));
     cellLeve4LA.setColspan(5);
      cellLeve4LA.setFixedHeight(16.2f);
      cellLeve4LA.disableBorderSide(LEFT);
      cellLeve4LA.disableBorderSide(TOP);
      cellLeve4LA.disableBorderSide(RIGHT);
      cellLeve4LA.disableBorderSide(BOTTOM);
      cellLeve4LA.setPadding(.5f);
      cellLeve4LA.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4LA.setBorderColorTop(BaseColor.WHITE);
      cellLeve4LA.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4LA.setBorderColorRight(BaseColor.WHITE);
      cellLeve4LA.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4LA);
       suredThere=false;
      }
            
        
      table.addCell(cellEmpty);
      PdfPCell cellLeve5L= new PdfPCell(new Paragraph(level5bL.get(zbL),font1));
      cellLeve5L.setFixedHeight(16.2f);
      cellLeve5L.disableBorderSide(LEFT);
      cellLeve5L.disableBorderSide(TOP);
      cellLeve5L.disableBorderSide(RIGHT);
      cellLeve5L.disableBorderSide(BOTTOM);
      cellLeve5L.setPadding(.5f);
      cellLeve5L.setBorderColorBottom(BaseColor.WHITE);
      cellLeve5L.setBorderColorTop(BaseColor.WHITE);
      cellLeve5L.setBorderColorLeft(BaseColor.WHITE);
      cellLeve5L.setBorderColorRight(BaseColor.WHITE);
      cellLeve5L.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cellLeve5L);
    
      table.addCell(cellEmpty);
      PdfPCell balAmountbL=null;
      double amountbL=parseDouble(rdb.getBalancesSheet(level5bL.get(zbL),sheetDate,c).get(level5bL.get(zbL)).toString());
      if(amountbL<0.0){
       balAmountbL= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(amountbL+"")+")",font1));
      } else{
      
       balAmountbL= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(amountbL+""),font1));
      
      }
     
      balAmountbL.setFixedHeight(16.2f);
      balAmountbL.disableBorderSide(LEFT);
      balAmountbL.disableBorderSide(TOP);
      balAmountbL.disableBorderSide(RIGHT);
      balAmountbL.disableBorderSide(BOTTOM);
      balAmountbL.setPadding(.5f);
      balAmountbL.setBorderColorBottom(BaseColor.WHITE);
      balAmountbL.setBorderColorTop(BaseColor.WHITE);
      balAmountbL.setBorderColorLeft(BaseColor.WHITE);
      balAmountbL.setBorderColorRight(BaseColor.WHITE);
      balAmountbL.setHorizontalAlignment(Element.ALIGN_CENTER);
     
     table.addCell(balAmountbL);
      table.addCell(cellEmpty);
     
      table.addCell(cellEmpty);
      noncursecLiab=true;
       sumbL= sumbL+(parseDouble(rdb.getBalancesSheet(level5bL.get(zbL),sheetDate,c).get(level5bL.get(zbL)).toString()));     
        }
         
           zbL++; 
                 }
            
              if(noncursecLiab){
             TotalHolder.put(15, sumbL);
             table.addCell(cellEmpty);
          PdfPCell totalValueA= new PdfPCell(new Paragraph("Total Secured Liabilities",font3));
      totalValueA.setFixedHeight(16.2f);
      totalValueA.disableBorderSide(LEFT);
      totalValueA.disableBorderSide(TOP);
      totalValueA.disableBorderSide(RIGHT);
      totalValueA.disableBorderSide(BOTTOM);
      totalValueA.setPadding(.5f);
      totalValueA.setBorderColorBottom(BaseColor.WHITE);
      totalValueA.setBorderColorTop(BaseColor.WHITE);
      totalValueA.setBorderColorLeft(BaseColor.WHITE);
      totalValueA.setBorderColorRight(BaseColor.WHITE);
      totalValueA.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(totalValueA);  
      table.addCell(cellEmpty);
      PdfPCell totalAmountA=null;
      double sumTotalA= TotalHolder.get(15);
      if(sumTotalA<0){
       totalAmountA= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(sumTotalA+"")+")",font1));
      } else{
    totalAmountA= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(sumTotalA+""),font1));
      }
        totalAmountA.setFixedHeight(16.2f);
      totalAmountA.disableBorderSide(LEFT);
      totalAmountA.disableBorderSide(RIGHT);
      totalAmountA.setPadding(.5f);
      totalAmountA.setBorderColorBottom(BaseColor.BLACK);
      totalAmountA.setBorderColorTop(BaseColor.BLACK);
      totalAmountA.setBorderColorLeft(BaseColor.WHITE);
      totalAmountA.setBorderColorRight(BaseColor.WHITE);
      totalAmountA.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(totalAmountA);
      table.addCell(cellEmpty); 
       table.addCell(cellEmpty);
       
       table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
              }
     break;
            
    
      case   "Unsecured Liabilities":
         int z1bL=0; double sum1bL=0.0; boolean unsecuredLthere=false;
       Map<Integer, String> level5abL=rdb.getLevel5Items("Unsecured Liabilities"); 
       while(z1bL<=level5abL.size()-1){
      
        if(parseDouble(rdb.getBalancesSheet(level5abL.get(z1bL),sheetDate,c).get(level5abL.get(z1bL)).toString())!=0.0){
            if(!unsecuredLthere){
      table.addCell(cellEmpty);
     PdfPCell cellLeve4aL= new PdfPCell(new Paragraph("Unsecured Liabilities",font2));
     cellLeve4aL.setColspan(5);
      cellLeve4aL.setFixedHeight(16.2f);
      cellLeve4aL.disableBorderSide(LEFT);
      cellLeve4aL.disableBorderSide(TOP);
      cellLeve4aL.disableBorderSide(RIGHT);
      cellLeve4aL.disableBorderSide(BOTTOM);
      cellLeve4aL.setPadding(.5f);
      cellLeve4aL.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4aL.setBorderColorTop(BaseColor.WHITE);
      cellLeve4aL.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4aL.setBorderColorRight(BaseColor.WHITE);
      cellLeve4aL.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4aL);
       unsecuredLthere=true;
            }
     
      table.addCell(cellEmpty);
      PdfPCell cellLeve5= new PdfPCell(new Paragraph(level5abL.get(z1bL),font1));
      cellLeve5.setFixedHeight(16.2f);
      cellLeve5.disableBorderSide(LEFT);
      cellLeve5.disableBorderSide(TOP);
      cellLeve5.disableBorderSide(RIGHT);
      cellLeve5.disableBorderSide(BOTTOM);
      cellLeve5.setPadding(.5f);
      cellLeve5.setBorderColorBottom(BaseColor.WHITE);
      cellLeve5.setBorderColorTop(BaseColor.WHITE);
      cellLeve5.setBorderColorLeft(BaseColor.WHITE);
      cellLeve5.setBorderColorRight(BaseColor.WHITE);
      cellLeve5.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cellLeve5);
    
      table.addCell(cellEmpty);
     
     PdfPCell balAmountbL=null;
      double amountbL=parseDouble(rdb.getBalancesSheet(level5abL.get(z1bL),sheetDate,c).get(level5abL.get(z1bL)).toString());
      if(amountbL<0.0){
       balAmountbL= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(amountbL+"")+")",font1));
      } else{
      
       balAmountbL= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(amountbL+""),font1));
      }
      balAmountbL.setFixedHeight(16.2f);
      balAmountbL.disableBorderSide(LEFT);
      balAmountbL.disableBorderSide(TOP);
      balAmountbL.disableBorderSide(RIGHT);
      balAmountbL.disableBorderSide(BOTTOM);
      balAmountbL.setPadding(.5f);
      balAmountbL.setBorderColorBottom(BaseColor.WHITE);
      balAmountbL.setBorderColorTop(BaseColor.WHITE);
      balAmountbL.setBorderColorLeft(BaseColor.WHITE);
      balAmountbL.setBorderColorRight(BaseColor.WHITE);
      balAmountbL.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(balAmountbL);
     
      table.addCell(cellEmpty);
     
      table.addCell(cellEmpty);
         noncurunseclian=true; 
       
        }
       sum1bL= sum1bL+(parseDouble(rdb.getBalancesSheet(level5abL.get(z1bL),sheetDate,c).get(level5abL.get(z1bL)).toString()));   
        z1bL++; }
              if(noncurunseclian){
               TotalHolder.put(16, sum1bL);
             table.addCell(cellEmpty);
          PdfPCell totalValueInvetments= new PdfPCell(new Paragraph("Total Unsecured Liabilities",font3));
      totalValueInvetments.setFixedHeight(16.2f);
      totalValueInvetments.disableBorderSide(LEFT);
      totalValueInvetments.disableBorderSide(TOP);
      totalValueInvetments.disableBorderSide(RIGHT);
      totalValueInvetments.disableBorderSide(BOTTOM);
      totalValueInvetments.setPadding(.5f);
      totalValueInvetments.setBorderColorBottom(BaseColor.WHITE);
      totalValueInvetments.setBorderColorTop(BaseColor.WHITE);
      totalValueInvetments.setBorderColorLeft(BaseColor.WHITE);
      totalValueInvetments.setBorderColorRight(BaseColor.WHITE);
      totalValueInvetments.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(totalValueInvetments);  
      table.addCell(cellEmpty);
      
      PdfPCell totalAmountInvest=null;
              double totalAmountInv=0.0;
              totalAmountInv= TotalHolder.get(16);
              if(totalAmountInv<0.0){
            totalAmountInvest   = new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(totalAmountInv+"")+")",font1));
              
              } else{
          totalAmountInvest   = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(totalAmountInv+""),font1));    
              }
     
      
      totalAmountInvest.setFixedHeight(16.2f);
      totalAmountInvest.disableBorderSide(LEFT);
      totalAmountInvest.disableBorderSide(RIGHT);
      totalAmountInvest.setPadding(.5f);
      totalAmountInvest.setBorderColorBottom(BaseColor.BLACK);
      totalAmountInvest.setBorderColorTop(BaseColor.BLACK);
      totalAmountInvest.setBorderColorLeft(BaseColor.WHITE);
      totalAmountInvest.setBorderColorRight(BaseColor.WHITE);
      totalAmountInvest.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(totalAmountInvest);
      table.addCell(cellEmpty); 
       table.addCell(cellEmpty);
       
        table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);                
              }              
         break; 
        
 
           }
          
          ybL++; 
           }
           
           if(noncursecLiab||noncurunseclian){
       double totalNonCurrentLiabi=0.0;
       
        totalNonCurrentLiabi= TotalHolder.get(15)+ TotalHolder.get(16);
        TotalHolder.put(17, totalNonCurrentLiabi);
        table.addCell(cellEmpty);
     PdfPCell cellLevexK= new PdfPCell(new Paragraph("Total Non Current Liabilities",font4));
      cellLevexK.setFixedHeight(16.2f);
      cellLevexK.disableBorderSide(LEFT);
      cellLevexK.disableBorderSide(TOP);
      cellLevexK.disableBorderSide(RIGHT);
      cellLevexK.disableBorderSide(BOTTOM);
      cellLevexK.setPadding(.5f);
      cellLevexK.setBorderColorBottom(BaseColor.WHITE);
      cellLevexK.setBorderColorTop(BaseColor.WHITE);
      cellLevexK.setBorderColorLeft(BaseColor.WHITE);
      cellLevexK.setBorderColorRight(BaseColor.WHITE);
      cellLevexK.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLevexK);  
       table.addCell(cellEmpty);
      
       PdfPCell totalCurrentAssetxs=null;
      if(TotalHolder.get(17)<0.0){
      totalCurrentAssetxs= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(17)+"")+")",font4)); 
      }else{
      totalCurrentAssetxs= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(17)+""),font4)); 
      }
       totalCurrentAssetxs.setFixedHeight(16.2f);
      totalCurrentAssetxs.disableBorderSide(LEFT);
      totalCurrentAssetxs.disableBorderSide(RIGHT);
      totalCurrentAssetxs.disableBorderSide(TOP);
      totalCurrentAssetxs.setPadding(.5f);
      totalCurrentAssetxs.setBorderColorBottom(BaseColor.BLACK);
      totalCurrentAssetxs.setBorderColorTop(BaseColor.WHITE);
      totalCurrentAssetxs.setBorderColorLeft(BaseColor.WHITE);
      totalCurrentAssetxs.setBorderColorRight(BaseColor.WHITE);
      totalCurrentAssetxs.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(totalCurrentAssetxs);
      table.addCell(cellEmpty);
       table.addCell(cellEmpty); 
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
           }
           break;
             
       case   "Deferred Taxes Liabilities" :
  int yXL=0;
       Map<Integer, String> level4XL=rdb.getLevel4Items("Deferred Taxes Liabilities");   
       PdfPCell cellLeve3XL= new PdfPCell(new Paragraph("Deferred Taxes Liabilities",font2));
        cellLeve3XL.setColspan(6);
      cellLeve3XL.setFixedHeight(16.2f);
      cellLeve3XL.disableBorderSide(LEFT);
      cellLeve3XL.disableBorderSide(TOP);
      cellLeve3XL.disableBorderSide(RIGHT);
      cellLeve3XL.disableBorderSide(BOTTOM);
      cellLeve3XL.setPadding(.5f);
      cellLeve3XL.setBorderColorBottom(BaseColor.WHITE);
      cellLeve3XL.setBorderColorTop(BaseColor.WHITE);
      cellLeve3XL.setBorderColorLeft(BaseColor.WHITE);
      cellLeve3XL.setBorderColorRight(BaseColor.WHITE);
      cellLeve3XL.setVerticalAlignment(Element.ALIGN_TOP);
      table.addCell(cellLeve3XL);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
           while(yXL<=level4XL.size()-1){
        switch(level4XL.get(yXL)){
            
        case "Deferred Tax Liabilities":
            int zXL=0; double sumXL=0.0; 
       Map<Integer, String> level5L=rdb.getLevel5Items("Deferred Tax Liabilities"); 

      table.addCell(cellEmpty);
     PdfPCell cellLeve4XL= new PdfPCell(new Paragraph("Deferred Tax Liabilities",font2));
     cellLeve4XL.setColspan(5);
      cellLeve4XL.setFixedHeight(16.2f);
      cellLeve4XL.disableBorderSide(LEFT);
      cellLeve4XL.disableBorderSide(TOP);
      cellLeve4XL.disableBorderSide(RIGHT);
      cellLeve4XL.disableBorderSide(BOTTOM);
      cellLeve4XL.setPadding(.5f);
      cellLeve4XL.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4XL.setBorderColorTop(BaseColor.WHITE);
      cellLeve4XL.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4XL.setBorderColorRight(BaseColor.WHITE);
      cellLeve4XL.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4XL);
              while(zXL<=level5L.size()-1){
      
        if(parseDouble(rdb.getBalancesSheet(level5L.get(zXL),sheetDate,c).get(level5L.get(zXL)).toString())!=0.0){
        
      table.addCell(cellEmpty);
      PdfPCell cellLeve5X= new PdfPCell(new Paragraph(level5L.get(zXL),font1));
      cellLeve5X.setFixedHeight(16.2f);
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
    
      table.addCell(cellEmpty);
      PdfPCell balAmountXL=null;
      double amountXL=parseDouble(rdb.getBalancesSheet(level5L.get(zXL),sheetDate,c).get(level5L.get(zXL)).toString());
      if(amountXL<0.0){
       balAmountXL= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(amountXL+"")+")",font1));
      } else{
      
       balAmountXL= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(amountXL+""),font1));
      
      }
     
      balAmountXL.setFixedHeight(16.2f);
      balAmountXL.disableBorderSide(LEFT);
      balAmountXL.disableBorderSide(TOP);
      balAmountXL.disableBorderSide(RIGHT);
      balAmountXL.disableBorderSide(BOTTOM);
      balAmountXL.setPadding(.5f);
      balAmountXL.setBorderColorBottom(BaseColor.WHITE);
      balAmountXL.setBorderColorTop(BaseColor.WHITE);
      balAmountXL.setBorderColorLeft(BaseColor.WHITE);
      balAmountXL.setBorderColorRight(BaseColor.WHITE);
      balAmountXL.setHorizontalAlignment(Element.ALIGN_CENTER);
     
     table.addCell(balAmountXL);
      table.addCell(cellEmpty);
     
    table.addCell(cellEmpty);
       
       sumXL= sumXL+(parseDouble(rdb.getBalancesSheet(level5L.get(zXL),sheetDate,c).get(level5L.get(zXL)).toString()));     
      taxLiabi=true;
        }
         
          
           zXL++; 
                 }
              if(taxLiabi){
             TotalHolder.put(18, sumXL);
             table.addCell(cellEmpty);
             
          PdfPCell totalValueB= new PdfPCell(new Paragraph("Sub Total Deferred Tax Liabilities",font3));
      totalValueB.setFixedHeight(16.2f);
      totalValueB.disableBorderSide(LEFT);
      totalValueB.disableBorderSide(TOP);
      totalValueB.disableBorderSide(RIGHT);
      totalValueB.disableBorderSide(BOTTOM);
      totalValueB.setPadding(.5f);
      totalValueB.setBorderColorBottom(BaseColor.WHITE);
      totalValueB.setBorderColorTop(BaseColor.WHITE);
      totalValueB.setBorderColorLeft(BaseColor.WHITE);
      totalValueB.setBorderColorRight(BaseColor.WHITE);
      totalValueB.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(totalValueB);  
      table.addCell(cellEmpty);
       
        PdfPCell totalAmountX= null;
     if(TotalHolder.get(18)<0.0){
     totalAmountX= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(18)+"")+")",font1));
     }else{
     totalAmountX= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(18)+""),font1));
     }
        totalAmountX.setFixedHeight(16.2f);
      totalAmountX.disableBorderSide(LEFT);
      totalAmountX.disableBorderSide(RIGHT);
      totalAmountX.setPadding(.5f);
      totalAmountX.setBorderColorBottom(BaseColor.BLACK);
      totalAmountX.setBorderColorTop(BaseColor.BLACK);
      totalAmountX.setBorderColorLeft(BaseColor.WHITE);
      totalAmountX.setBorderColorRight(BaseColor.WHITE);
      totalAmountX.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(totalAmountX);
      table.addCell(cellEmpty); 
       table.addCell(cellEmpty);
     
              }
              break;

           }
          
          yXL++; 
           }
           
           if(taxLiabi){
           double totalDefered=0.0;
     
       totalDefered=TotalHolder.get(18);
       
       
       TotalHolder.put(19, totalDefered);
       table.addCell(cellEmpty);
          table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     PdfPCell cellLeve4a1X= new PdfPCell(new Paragraph("Total Deferred Tax Liabilities",font4));
      cellLeve4a1X.setFixedHeight(16.2f);
      cellLeve4a1X.disableBorderSide(LEFT);
      cellLeve4a1X.disableBorderSide(TOP);
      cellLeve4a1X.disableBorderSide(RIGHT);
      cellLeve4a1X.disableBorderSide(BOTTOM);
      cellLeve4a1X.setPadding(.5f);
      cellLeve4a1X.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4a1X.setBorderColorTop(BaseColor.WHITE);
      cellLeve4a1X.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4a1X.setBorderColorRight(BaseColor.WHITE);
      cellLeve4a1X.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4a1X);  
       table.addCell(cellEmpty);
      
       
       PdfPCell totalAmountInvestsX= null; 
        if(TotalHolder.get(19)<0.0){
        
        totalAmountInvestsX= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(19)+"")+")",font4)); 
        }else{
        totalAmountInvestsX= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(19)+""),font4)); 
        }
       
       totalAmountInvestsX.setFixedHeight(16.2f);
      totalAmountInvestsX.disableBorderSide(LEFT);
      totalAmountInvestsX.disableBorderSide(RIGHT);
      totalAmountInvestsX.disableBorderSide(TOP);
      totalAmountInvestsX.setPadding(.5f);
      totalAmountInvestsX.setBorderColorBottom(BaseColor.BLACK);
      totalAmountInvestsX.setBorderColorTop(BaseColor.WHITE);
      totalAmountInvestsX.setBorderColorLeft(BaseColor.WHITE);
      totalAmountInvestsX.setBorderColorRight(BaseColor.WHITE);
      totalAmountInvestsX.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(totalAmountInvestsX);
      table.addCell(cellEmpty);
       table.addCell(cellEmpty); 
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);  
           }
       break;     
   
        }
        xL++;
        }  

          int xLe=0; 
      Map<Integer, String> level3Le=rdb. getLevel3Items("Equity/Capital");
       PdfPCell cellLeve2Le= new PdfPCell(new Paragraph("Equity/Capital",font2));
       cellLeve2Le.setColspan(6);
      cellLeve2Le.setFixedHeight(16.2f);
      cellLeve2Le.disableBorderSide(LEFT);
      cellLeve2Le.disableBorderSide(TOP);
      cellLeve2Le.disableBorderSide(RIGHT);
       cellLeve2Le.setPadding(.5f);
       cellLeve2Le.setBorderColorBottom(BaseColor.BLACK);
       cellLeve2Le.setBorderColorTop(BaseColor.WHITE);
       cellLeve2Le.setBorderColorLeft(BaseColor.WHITE);
       cellLeve2Le.setBorderColorRight(BaseColor.WHITE);
       cellLeve2Le.setVerticalAlignment(Element.ALIGN_TOP);
        table.addCell(cellLeve2Le);
        while(xLe<=level3Le.size()-1){
        switch(level3Le.get(xLe)){
       case "Equity":
           int yLe=0;
       Map<Integer, String> level4Le=rdb.getLevel4Items("Equity");   
       PdfPCell cellLeve3Le= new PdfPCell(new Paragraph("Equity",font2));
        cellLeve3Le.setColspan(6);
      cellLeve3Le.setFixedHeight(16.2f);
      cellLeve3Le.disableBorderSide(LEFT);
      cellLeve3Le.disableBorderSide(TOP);
      cellLeve3Le.disableBorderSide(RIGHT);
      cellLeve3Le.disableBorderSide(BOTTOM);
      cellLeve3Le.setPadding(.5f);
      cellLeve3Le.setBorderColorBottom(BaseColor.WHITE);
      cellLeve3Le.setBorderColorTop(BaseColor.WHITE);
      cellLeve3Le.setBorderColorLeft(BaseColor.WHITE);
      cellLeve3Le.setBorderColorRight(BaseColor.WHITE);
      cellLeve3Le.setVerticalAlignment(Element.ALIGN_TOP);
      table.addCell(cellLeve3Le);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     
           while(yLe<=level4Le.size()-1){
        switch(level4Le.get(yLe)){
        case "Share Capital":
            int zLe=0; double sumLe=0.0; boolean sharesThere=false;
       Map<Integer, String> level5Le=rdb.getLevel5Items("Share Capital"); 
  while(zLe<=level5Le.size()-1){
      
        if(parseDouble(rdb.getBalancesSheet(level5Le.get(zLe),sheetDate,c).get(level5Le.get(zLe)).toString())!=0.0){
            if(!sharesThere){
      table.addCell(cellEmpty);
     PdfPCell cellLeve4Le= new PdfPCell(new Paragraph("Share Capital",font2));
     cellLeve4Le.setColspan(5);
      cellLeve4Le.setFixedHeight(16.2f);
      cellLeve4Le.disableBorderSide(LEFT);
      cellLeve4Le.disableBorderSide(TOP);
      cellLeve4Le.disableBorderSide(RIGHT);
      cellLeve4Le.disableBorderSide(BOTTOM);
      cellLeve4Le.setPadding(.5f);
      cellLeve4Le.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4Le.setBorderColorTop(BaseColor.WHITE);
      cellLeve4Le.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4Le.setBorderColorRight(BaseColor.WHITE);
      cellLeve4Le.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4Le);
       sharesThere=true;
            }
        
      table.addCell(cellEmpty);
      PdfPCell cellLeve5Le= new PdfPCell(new Paragraph(level5Le.get(zLe),font1));
      cellLeve5Le.setFixedHeight(16.2f);
      cellLeve5Le.disableBorderSide(LEFT);
      cellLeve5Le.disableBorderSide(TOP);
      cellLeve5Le.disableBorderSide(RIGHT);
      cellLeve5Le.disableBorderSide(BOTTOM);
      cellLeve5Le.setPadding(.5f);
      cellLeve5Le.setBorderColorBottom(BaseColor.WHITE);
      cellLeve5Le.setBorderColorTop(BaseColor.WHITE);
      cellLeve5Le.setBorderColorLeft(BaseColor.WHITE);
      cellLeve5Le.setBorderColorRight(BaseColor.WHITE);
      cellLeve5Le.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cellLeve5Le);
      table.addCell(cellEmpty);
   
      PdfPCell balAmountLe=null;
      double amountLe=parseDouble(rdb.getBalancesSheet(level5Le.get(zLe),sheetDate,c).get(level5Le.get(zLe)).toString());
      if(amountLe<0.0){
       balAmountLe= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(amountLe+"")+")",font1));
      } else{
      
       balAmountLe= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(amountLe+""),font1));
      
      }
     
      balAmountLe.setFixedHeight(16.2f);
      balAmountLe.disableBorderSide(LEFT);
      balAmountLe.disableBorderSide(TOP);
      balAmountLe.disableBorderSide(RIGHT);
      balAmountLe.disableBorderSide(BOTTOM);
      balAmountLe.setPadding(.5f);
      balAmountLe.setBorderColorBottom(BaseColor.WHITE);
      balAmountLe.setBorderColorTop(BaseColor.WHITE);
      balAmountLe.setBorderColorLeft(BaseColor.WHITE);
      balAmountLe.setBorderColorRight(BaseColor.WHITE);
      balAmountLe.setHorizontalAlignment(Element.ALIGN_CENTER);
     
     table.addCell(balAmountLe);
      table.addCell(cellEmpty);
     
      table.addCell(cellEmpty);
       
       sumLe= sumLe+(parseDouble(rdb.getBalancesSheet(level5Le.get(zLe),sheetDate,c).get(level5Le.get(zLe)).toString()));     
       currentLiabCreds=true; 
        }
      
           zLe++; 
                 }
              if(currentLiabCreds){
             TotalHolder.put(22, sumLe);
             table.addCell(cellEmpty);
          PdfPCell totalValue= new PdfPCell(new Paragraph("Total Share Capital",font3));
      totalValue.setFixedHeight(16.2f);
      totalValue.disableBorderSide(LEFT);
      totalValue.disableBorderSide(TOP);
      totalValue.disableBorderSide(RIGHT);
      totalValue.disableBorderSide(BOTTOM);
      totalValue.setPadding(.5f);
      totalValue.setBorderColorBottom(BaseColor.WHITE);
      totalValue.setBorderColorTop(BaseColor.WHITE);
      totalValue.setBorderColorLeft(BaseColor.WHITE);
      totalValue.setBorderColorRight(BaseColor.WHITE);
      totalValue.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(totalValue);  
      table.addCell(cellEmpty);
      
        PdfPCell totalAmount= null;
     if(TotalHolder.get(22)<0.0)
     {totalAmount= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(22)+"")+")",font1));
     } else{totalAmount= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(22)+""),font1));}
        totalAmount.setFixedHeight(16.2f);
      totalAmount.disableBorderSide(LEFT);
      totalAmount.disableBorderSide(RIGHT);
      totalAmount.setPadding(.5f);
      totalAmount.setBorderColorBottom(BaseColor.BLACK);
      totalAmount.setBorderColorTop(BaseColor.BLACK);
      totalAmount.setBorderColorLeft(BaseColor.WHITE);
      totalAmount.setBorderColorRight(BaseColor.WHITE);
      totalAmount.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(totalAmount);
      table.addCell(cellEmpty); 
       table.addCell(cellEmpty);
              }
              break;
            
    
      case   "Reserves And Surplus" :
          
         int z1Le=0; double sum1Le=0.0,amountLe=0.0,finalT=0.0;boolean reservesThere=false,isretain=false;   PdfPCell balAmountLe=null;
         
       Map<Integer, String> level5aL=rdb.getLevel5Items("Reserves And Surplus"); 
        while(z1Le<=level5aL.size()-1){
       
        if(parseDouble(rdb.getBalancesSheet(level5aL.get(z1Le),sheetDate,c).get(level5aL.get(z1Le)).toString())!=0.0){
        
            if(!reservesThere){
      table.addCell(cellEmpty);
     PdfPCell cellLeve4aLe= new PdfPCell(new Paragraph("Reserves And Surplus",font2));
     cellLeve4aLe.setColspan(5);
      cellLeve4aLe.setFixedHeight(16.2f);
      cellLeve4aLe.disableBorderSide(LEFT);
      cellLeve4aLe.disableBorderSide(TOP);
      cellLeve4aLe.disableBorderSide(RIGHT);
      cellLeve4aLe.disableBorderSide(BOTTOM);
      cellLeve4aLe.setPadding(.5f);
      cellLeve4aLe.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4aLe.setBorderColorTop(BaseColor.WHITE);
      cellLeve4aLe.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4aLe.setBorderColorRight(BaseColor.WHITE);
      cellLeve4aLe.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4aLe);
       reservesThere=true;
            }  
            
      
     
  
      if(level5aL.get(z1Le).equalsIgnoreCase("Retained Earnings")){
            
          
     amountLe=parseDouble(rdb.getBalancesSheet(level5aL.get(z1Le),sheetDate,c).get(level5aL.get(z1Le)).toString())+parseDouble(netprofits.theNetProfits(ffm.forDatabaseWithFullYearBeginningWithDate(fios.stringFileReader(fios.createFileName("persistence", "operatingCycle", "YEARstartDate.txt"))),sheetDate,c));
//         sum1Le=amountLe; 
     isretain=true;
          table.addCell(cellEmpty);
      PdfPCell cellLeve5L= new PdfPCell(new Paragraph(level5aL.get(z1Le),font1));
      cellLeve5L.setFixedHeight(16.2f);
      cellLeve5L.disableBorderSide(LEFT);
      cellLeve5L.disableBorderSide(TOP);
      cellLeve5L.disableBorderSide(RIGHT);
      cellLeve5L.disableBorderSide(BOTTOM);
      cellLeve5L.setPadding(.5f);
      cellLeve5L.setBorderColorBottom(BaseColor.WHITE);
      cellLeve5L.setBorderColorTop(BaseColor.WHITE);
      cellLeve5L.setBorderColorLeft(BaseColor.WHITE);
      cellLeve5L.setBorderColorRight(BaseColor.WHITE);
      cellLeve5L.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cellLeve5L);
    
      table.addCell(cellEmpty);

      if(amountLe<0.0){
       balAmountLe= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(amountLe+"")+")",font1));
      } else{
      
       balAmountLe= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(amountLe+""),font1));
      }
      balAmountLe.setFixedHeight(16.2f);
      balAmountLe.disableBorderSide(LEFT);
      balAmountLe.disableBorderSide(TOP);
      balAmountLe.disableBorderSide(RIGHT);
      balAmountLe.disableBorderSide(BOTTOM);
      balAmountLe.setPadding(.5f);
      balAmountLe.setBorderColorBottom(BaseColor.WHITE);
      balAmountLe.setBorderColorTop(BaseColor.WHITE);
      balAmountLe.setBorderColorLeft(BaseColor.WHITE);
      balAmountLe.setBorderColorRight(BaseColor.WHITE);
      balAmountLe.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(balAmountLe);
     
      table.addCell(cellEmpty);
     
      table.addCell(cellEmpty);
        provisions=true;

      }else{
               
     
      table.addCell(cellEmpty);
      PdfPCell cellLeve5L= new PdfPCell(new Paragraph(level5aL.get(z1Le),font1));
      cellLeve5L.setFixedHeight(16.2f);
      cellLeve5L.disableBorderSide(LEFT);
      cellLeve5L.disableBorderSide(TOP);
      cellLeve5L.disableBorderSide(RIGHT);
      cellLeve5L.disableBorderSide(BOTTOM);
      cellLeve5L.setPadding(.5f);
      cellLeve5L.setBorderColorBottom(BaseColor.WHITE);
      cellLeve5L.setBorderColorTop(BaseColor.WHITE);
      cellLeve5L.setBorderColorLeft(BaseColor.WHITE);
      cellLeve5L.setBorderColorRight(BaseColor.WHITE);
      cellLeve5L.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cellLeve5L);
    
      table.addCell(cellEmpty);
    amountLe=parseDouble(rdb.getBalancesSheet(level5aL.get(z1Le),sheetDate,c).get(level5aL.get(z1Le)).toString());
     
     
      if(amountLe<0.0){
       balAmountLe= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(amountLe+"")+")",font1));
      } else{
      
       balAmountLe= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(amountLe+""),font1));
      }
      balAmountLe.setFixedHeight(16.2f);
      balAmountLe.disableBorderSide(LEFT);
      balAmountLe.disableBorderSide(TOP);
      balAmountLe.disableBorderSide(RIGHT);
      balAmountLe.disableBorderSide(BOTTOM);
      balAmountLe.setPadding(.5f);
      balAmountLe.setBorderColorBottom(BaseColor.WHITE);
      balAmountLe.setBorderColorTop(BaseColor.WHITE);
      balAmountLe.setBorderColorLeft(BaseColor.WHITE);
      balAmountLe.setBorderColorRight(BaseColor.WHITE);
      balAmountLe.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(balAmountLe);
     
      table.addCell(cellEmpty);
     
      table.addCell(cellEmpty);
        provisions=true;
         }
        } 
//         if(parseDouble(netprofits.theNetProfits(ffm.dateForDatabaseWithFullYearBeginningWithDate(fios.stringFileReader(fios.createFileName("persistence", "operatingCycle", "YEARstartDate.txt"))),sheetDate))!=0.0)
        else{
            if(!isretain){
       if(!reservesThere){
      table.addCell(cellEmpty);
     PdfPCell cellLeve4aLe= new PdfPCell(new Paragraph("Reserves And Surplus",font2));
     cellLeve4aLe.setColspan(5);
      cellLeve4aLe.setFixedHeight(16.2f);
      cellLeve4aLe.disableBorderSide(LEFT);
      cellLeve4aLe.disableBorderSide(TOP);
      cellLeve4aLe.disableBorderSide(RIGHT);
      cellLeve4aLe.disableBorderSide(BOTTOM);
      cellLeve4aLe.setPadding(.5f);
      cellLeve4aLe.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4aLe.setBorderColorTop(BaseColor.WHITE);
      cellLeve4aLe.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4aLe.setBorderColorRight(BaseColor.WHITE);
      cellLeve4aLe.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4aLe);
       reservesThere=true;
       }
        if(!secondRetained){   
     
      table.addCell(cellEmpty);
      PdfPCell cellLeve5L= new PdfPCell(new Paragraph("Retained Earnings",font1));
      cellLeve5L.setFixedHeight(16.2f);
      cellLeve5L.disableBorderSide(LEFT);
      cellLeve5L.disableBorderSide(TOP);
      cellLeve5L.disableBorderSide(RIGHT);
      cellLeve5L.disableBorderSide(BOTTOM);
      cellLeve5L.setPadding(.5f);
      cellLeve5L.setBorderColorBottom(BaseColor.WHITE);
      cellLeve5L.setBorderColorTop(BaseColor.WHITE);
      cellLeve5L.setBorderColorLeft(BaseColor.WHITE);
      cellLeve5L.setBorderColorRight(BaseColor.WHITE);
      cellLeve5L.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cellLeve5L);
    
      table.addCell(cellEmpty);
     
//     PdfPCell balAmountLe=null; double amountLe=0.0;
   
            
  amountLe=parseDouble(netprofits.theNetProfits(ffm.forDatabaseWithFullYearBeginningWithDate(fios.stringFileReader(fios.createFileName("persistence", "operatingCycle", "YEARstartDate.txt"))),sheetDate,c));
  
     
      if(amountLe<0.0){
       balAmountLe= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(amountLe+"")+")",font1));
      } else{
      
       balAmountLe= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(amountLe+""),font1));
      }
      balAmountLe.setFixedHeight(16.2f);
      balAmountLe.disableBorderSide(LEFT);
      balAmountLe.disableBorderSide(TOP);
      balAmountLe.disableBorderSide(RIGHT);
      balAmountLe.disableBorderSide(BOTTOM);
      balAmountLe.setPadding(.5f);
      balAmountLe.setBorderColorBottom(BaseColor.WHITE);
      balAmountLe.setBorderColorTop(BaseColor.WHITE);
      balAmountLe.setBorderColorLeft(BaseColor.WHITE);
      balAmountLe.setBorderColorRight(BaseColor.WHITE);
      balAmountLe.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(balAmountLe);
     
      table.addCell(cellEmpty);
     
      table.addCell(cellEmpty);
        provisions=true;
        secondRetained=true;
//        sum1Le=amountLe;
        }
        }
        isretain=true;
        }
        
        if(level5aL.get(z1Le).equalsIgnoreCase("Retained Earnings")){
        finalT=amountLe;
        }else{
        
        finalT=(parseDouble(rdb.getBalancesSheet(level5aL.get(z1Le),sheetDate,c).get(level5aL.get(z1Le)).toString()));
        }
        
       sum1Le= sum1Le+finalT;   
        z1Le++;
        
        
        }
              if(provisions){
               TotalHolder.put(23, sum1Le);
             table.addCell(cellEmpty);
          PdfPCell totalValueInvetmentsPe= new PdfPCell(new Paragraph("Total Reserves And Surplus",font3));
      totalValueInvetmentsPe.setFixedHeight(16.2f);
      totalValueInvetmentsPe.disableBorderSide(LEFT);
      totalValueInvetmentsPe.disableBorderSide(TOP);
      totalValueInvetmentsPe.disableBorderSide(RIGHT);
      totalValueInvetmentsPe.disableBorderSide(BOTTOM);
      totalValueInvetmentsPe.setPadding(.5f);
      totalValueInvetmentsPe.setBorderColorBottom(BaseColor.WHITE);
      totalValueInvetmentsPe.setBorderColorTop(BaseColor.WHITE);
      totalValueInvetmentsPe.setBorderColorLeft(BaseColor.WHITE);
      totalValueInvetmentsPe.setBorderColorRight(BaseColor.WHITE);
      totalValueInvetmentsPe.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(totalValueInvetmentsPe);  
      table.addCell(cellEmpty);
      
      PdfPCell totalAmountInvestP=null;
              double totalAmountInvP=0.0;
              totalAmountInvP=TotalHolder.get(23);
              if(totalAmountInvP<0.0){
            totalAmountInvestP   = new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(totalAmountInvP+"")+")",font1));
              
              } else{
          totalAmountInvestP   = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(totalAmountInvP+""),font1));    
              }
     
      
      totalAmountInvestP.setFixedHeight(16.2f);
      totalAmountInvestP.disableBorderSide(LEFT);
      totalAmountInvestP.disableBorderSide(RIGHT);
      totalAmountInvestP.setPadding(.5f);
      totalAmountInvestP.setBorderColorBottom(BaseColor.BLACK);
      totalAmountInvestP.setBorderColorTop(BaseColor.BLACK);
      totalAmountInvestP.setBorderColorLeft(BaseColor.WHITE);
      totalAmountInvestP.setBorderColorRight(BaseColor.WHITE);
      totalAmountInvestP.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(totalAmountInvestP);
      table.addCell(cellEmpty); 
       table.addCell(cellEmpty);
              }
              break;

           }
          
          yLe++; 
           }
           if(currentLiabCreds||provisions){
           double totalShareHolders=0.0;
        totalShareHolders=TotalHolder.get(22)+TotalHolder.get(23);
       
       
       TotalHolder.put(24, totalShareHolders);
       table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
       table.addCell(cellEmpty);
     PdfPCell cellLeve4a1= new PdfPCell(new Paragraph("Total Share Holder's Funds",font4));
      cellLeve4a1.setFixedHeight(16.2f);
      cellLeve4a1.disableBorderSide(LEFT);
      cellLeve4a1.disableBorderSide(TOP);
      cellLeve4a1.disableBorderSide(RIGHT);
      cellLeve4a1.disableBorderSide(BOTTOM);
      cellLeve4a1.setPadding(.5f);
      cellLeve4a1.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4a1.setBorderColorTop(BaseColor.WHITE);
      cellLeve4a1.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4a1.setBorderColorRight(BaseColor.WHITE);
      cellLeve4a1.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4a1);  
      
       table.addCell(cellEmpty); 
       
       PdfPCell totalAmountInvests= null;
        if(TotalHolder.get(24)<0.0){ totalAmountInvests= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(24)+"")+")",font4)); }else{
         totalAmountInvests= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(24)+""),font4)); }
       totalAmountInvests.setFixedHeight(16.2f);
      totalAmountInvests.disableBorderSide(LEFT);
      totalAmountInvests.disableBorderSide(RIGHT);
      totalAmountInvests.disableBorderSide(TOP);
      totalAmountInvests.setPadding(.5f);
      totalAmountInvests.setBorderColorBottom(BaseColor.BLACK);
      totalAmountInvests.setBorderColorTop(BaseColor.WHITE);
      totalAmountInvests.setBorderColorLeft(BaseColor.WHITE);
      totalAmountInvests.setBorderColorRight(BaseColor.WHITE);
      totalAmountInvests.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(totalAmountInvests);
      table.addCell(cellEmpty);
       table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
           }
           
      if(noncursecLiab||noncurunseclian||currentLiabCreds||provisions){
        double totalCurrentLiabilityAndEquity=0.0;
        totalCurrentLiabilityAndEquity=MAth.add(TotalHolder.get(24),MAth.add(TotalHolder.get(17),TotalHolder.get(18)));
       
       
       TotalHolder.put(25,  totalCurrentLiabilityAndEquity);
          table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
         table.addCell(cellEmpty); 
       table.addCell(cellEmpty);
        table.addCell(celd);
        table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
       table.addCell(cellEmpty);
       PdfPCell cellLeve4a1L= new PdfPCell(new Paragraph("Total Equity And Long Term Liabilities",font2));
      cellLeve4a1L.setFixedHeight(16.2f);
      cellLeve4a1L.disableBorderSide(LEFT);
      cellLeve4a1L.disableBorderSide(TOP);
      cellLeve4a1L.disableBorderSide(RIGHT);
      cellLeve4a1L.disableBorderSide(BOTTOM);
      cellLeve4a1L.setPadding(.5f);
      cellLeve4a1L.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4a1L.setBorderColorTop(BaseColor.WHITE);
      cellLeve4a1L.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4a1L.setBorderColorRight(BaseColor.WHITE);
      cellLeve4a1L.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4a1L);  
       table.addCell(cellEmpty);
    
       
       PdfPCell totalAmountInvestsN= null;
       
       if(TotalHolder.get(25)<0.0){totalAmountInvestsN= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(25)+"")+")",font2)); }
       
     else {totalAmountInvestsN= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(25)+""),font2)); }
      
       totalAmountInvestsN.setFixedHeight(16.2f);
      totalAmountInvestsN.disableBorderSide(LEFT);
      totalAmountInvestsN.disableBorderSide(RIGHT);
      totalAmountInvestsN.disableBorderSide(TOP);
      totalAmountInvestsN.disableBorderSide(BOTTOM);
      totalAmountInvestsN.setPadding(.5f);
      totalAmountInvestsN.setBorderColorBottom(BaseColor.BLACK);
      totalAmountInvestsN.setBorderColorTop(BaseColor.WHITE);
      totalAmountInvestsN.setBorderColorLeft(BaseColor.WHITE);
      totalAmountInvestsN.setBorderColorRight(BaseColor.WHITE);
      totalAmountInvestsN.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(totalAmountInvestsN);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
       table.addCell(celd);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
         table.addCell(cellEmpty); 
       table.addCell(cellEmpty); 
     }
       break;     
   
        }
        xLe++;
        }  


  
  
  
      return table;

       }
      
    public  PdfPTable  createPastBalanceSheet(String SheetDate){
    PdfPTable tbl=null;
    return tbl;
    
    
    }
  

private Paragraph createHeading(String sheetDate) {
  Paragraph ss=null;
  
  Font font1 = new Font(Font.FontFamily.COURIER  , 14, Font.NORMAL);
  ss= new Paragraph("BALANCE SHEET STATEMENT"+"\n"+"AS AT"+" "+ffm.fromDatabaseWithDashSeperatorBeginningWithYear(sheetDate),font1);
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
  
        public List<List>balanceSheetAggregator(String startDate,String sheetDate,Component c){
        List<List> aggregate= new ArrayList<>();

        Map<Integer,Double> TotalHolder= new HashMap<>();
        int i=0;boolean indicator=false; boolean fixedIndicator=false;boolean indicatorInvest=false; boolean indicatorCash=false,indicatorLoan=false,indicatorReceivable=false,indicatorInvento=false;boolean currentLiabCred=false,provision=false, noncursecLiab=false,noncurunseclian=false;boolean taxLiabi=false;   boolean currentLiabCreds=false,provisions=false,secondRetained=false;
        while (i<=30){
        TotalHolder.put(i, 0.0);
        i++;
        }

        List title= new ArrayList();
        title.add("BALANCE SHEET STATEMENT"+"\n"+"AS AT"+" "+ffm.fromDatabaseWithDashSeperatorBeginningWithYear(sheetDate));
        aggregate.add(title);

        List topLevelTitleAssets= new ArrayList();
        topLevelTitleAssets.add("Assets");
        aggregate.add(topLevelTitleAssets);

        int x=0;
        Map<Integer, String> level3=rdb. getLevel3Items("Assets");

        while(x<=level3.size()-1){
        switch(level3.get(x)){
        case "Fixed Assets&Investments":

        List topLevelTitleFixedAssetsInvestments= new ArrayList();
        topLevelTitleFixedAssetsInvestments.add("Fixed Assets And Investments");
        aggregate.add(topLevelTitleFixedAssetsInvestments);
        int y=0;
        Map<Integer, String> level4=rdb.getLevel4Items("Fixed Assets&Investments");
        while(y<=level4.size()-1){
        switch(level4.get(y)){
        case "Fixed Assets/Non-Current Assets":
        int z=0; double sum=0.0; boolean fixedAExists=false;
        Map<Integer, String> level5=rdb.getLevel5Items("Fixed Assets/Non-Current Assets"); 
        while(z<=level5.size()-1){

        if(parseDouble(rdb.getBalancesSheet(level5.get(z),sheetDate,c).get(level5.get(z)).toString())!=0.0){
        if(!fixedAExists){
        List topLevelTitleFixedAssets= new ArrayList();
        topLevelTitleFixedAssets.add("Fixed Assets/Non-Current Assets");
        aggregate.add(topLevelTitleFixedAssets);
        fixedAExists=true;
        }
        if(!(level5.get(z).equalsIgnoreCase("Accumulated Depreciation/Amortization"))){

        List Accumulated= new ArrayList();
        Accumulated.add(level5.get(z));
        double amount=parseDouble(rdb.getBalancesSheet(level5.get(z),sheetDate,c).get(level5.get(z)).toString());
        if(amount<0.0){
        Accumulated.add("("+ffm.formatForStatementNumbers(amount+"")+")");
        } else{

        Accumulated.add(ffm.formatForStatementNumbers(amount+""));

        }
        aggregate.add(Accumulated);


        sum= sum+(parseDouble(rdb.getBalancesSheet(level5.get(z),sheetDate,c).get(level5.get(z)).toString()));    
        fixedIndicator=true;
        }
        if(level5.get(z).equalsIgnoreCase("Accumulated Depreciation/Amortization")){
        indicator=true;
        TotalHolder.put(0, parseDouble(rdb.getBalancesSheet(level5.get(z),sheetDate,c).get(level5.get(z)).toString()));
        }
        }
        z++; 
        }
        if(fixedIndicator){
        TotalHolder.put(1, sum);

        List totalFixed= new ArrayList();
        totalFixed.add("Total Fixed Assets");
        if(TotalHolder.get(1)<0.0){
        totalFixed.add("("+ffm.formatForStatementNumbers(TotalHolder.get(1)+"")+")");
        }else{

        totalFixed.add(ffm.formatForStatementNumbers(TotalHolder.get(1)+"")); 
        }
        aggregate.add(totalFixed);
        }
        if(indicator){
        List Lessd= new ArrayList();

        Lessd.add("Less Accumulated Depreciation/Amortization");
        Lessd.add("("+ffm.formatForStatementNumbers(TotalHolder.get(0)+"")+")");
        aggregate.add(Lessd);

        List netBookValue= new ArrayList();
         netBookValue.add("Net Book Value of Fixed Assets");
        double net =MAth.subtract(TotalHolder.get(1), TotalHolder.get(0));
        TotalHolder.put(2, net);
        if(net<0){netBookValue.add("("+ffm.formatForStatementNumbers(net+"")+")");} else{
        netBookValue.add(ffm.formatForStatementNumbers(net+""));
        }
        aggregate.add(netBookValue); 

        List empt= new ArrayList();
        empt.add("");
        empt.add("");
        empt.add("");
        empt.add("");
        aggregate.add(empt); 
        }
        break;


        case   "Investments" :
        int z1=0; double sum1=0.0;boolean investExists=false;
        Map<Integer, String> level5a=rdb.getLevel5Items("Investments"); 
        while(z1<=level5a.size()-1){

        if(parseDouble(rdb.getBalancesSheet(level5a.get(z1),sheetDate,c).get(level5a.get(z1)).toString())!=0.0){

        if(!investExists){

        List investments= new ArrayList();
        investments.add("Investments");
        aggregate.add(investments); 
        investExists=false;  
        }

        List investmentsin= new ArrayList();
        investmentsin.add(level5a.get(z1));
        double amount=parseDouble(rdb.getBalancesSheet(level5a.get(z1),sheetDate,c).get(level5a.get(z1)).toString());
        if(amount<0.0){
        investmentsin.add("("+ffm.formatForStatementNumbers(amount+"")+")");
        } else{

        investmentsin.add(ffm.formatForStatementNumbers(amount+""));
        }
        aggregate.add(investmentsin);  
        indicatorInvest=true;
        }
        sum1= sum1+(parseDouble(rdb.getBalancesSheet(level5a.get(z1),sheetDate,c).get(level5a.get(z1)).toString()));   
        z1++; 



        }
        if(indicatorInvest){
        TotalHolder.put(3, sum1);

        List investmentsinT= new ArrayList();
        investmentsinT.add("Total Investments");

        double totalAmountInv=0.0;
        totalAmountInv=TotalHolder.get(3);
        if(totalAmountInv<0.0){
        investmentsinT.add("("+ffm.formatForStatementNumbers(totalAmountInv+"")+")");

        } else{
        investmentsinT.add(ffm.formatForStatementNumbers(totalAmountInv+""));    
        }
        }             

        break; 

        }

        y++; 
        }
        if(fixedIndicator||indicatorInvest){
        double totalFixedAndInvestments=0.0;

        if(indicator){
        totalFixedAndInvestments=TotalHolder.get(2)+TotalHolder.get(3);
        }else{

        totalFixedAndInvestments=TotalHolder.get(1)+TotalHolder.get(3);
        }


        TotalHolder.put(4, totalFixedAndInvestments);
        List empt= new ArrayList();
        empt.add("");
        empt.add("");
        empt.add("");
        empt.add("");
        aggregate.add(empt); 


        List totalFixedAssets= new ArrayList();
        totalFixedAssets.add("Total Fixed Assets And Investments");
        if(TotalHolder.get(4)<0.0){
        totalFixedAssets.add("("+ffm.formatForStatementNumbers(TotalHolder.get(4)+"")+")");
        }else{ totalFixedAssets.add(ffm.formatForStatementNumbers(TotalHolder.get(4)+""));}
        aggregate.add(totalFixedAssets);

        List empt2= new ArrayList();
        empt2.add("");
        empt2.add("");
        empt2.add("");
        empt2.add("");
        aggregate.add(empt2); 
        }
        break;
        case "Current Assets, Loans And Advances":
        int yb=0;
        Map<Integer, String> level4b=rdb.getLevel4Items("Current Assets, Loans And Advances"); 

        List currentAssets= new ArrayList();
        currentAssets.add("Current Assets, Loans And Advances");
        aggregate.add(currentAssets); 
        List empt21= new ArrayList();
        empt21.add("");
        empt21.add("");
        empt21.add("");
        empt21.add("");
        aggregate.add(empt21); 

        while(yb<=level4b.size()-1){
        switch(level4b.get(yb)){
        case "Inventories":
        int zb=0; double sumb=0.0; boolean inventoExists=false;
        Map<Integer, String> level5b=rdb.getLevel5Items("Inventories"); 
        while(zb<=level5b.size()-1){

        if(parseDouble(rdb.getBalancesSheet(level5b.get(zb),sheetDate,c).get(level5b.get(zb)).toString())!=0.0){

        if(!inventoExists){
        List inventories= new ArrayList();
        inventories.add("Inventories");
        aggregate.add(inventories); 
        }
        List inventoriesin= new ArrayList();
        inventoriesin.add(level5b.get(zb));


        double amountb=parseDouble(rdb.getBalancesSheet(level5b.get(zb),sheetDate,c).get(level5b.get(zb)).toString());
        if(amountb<0.0){
        inventoriesin.add("("+ffm.formatForStatementNumbers(amountb+"")+")");
        } else{

        inventoriesin.add(ffm.formatForStatementNumbers(amountb+""));

        }
        aggregate.add(inventoriesin);  
        sumb= sumb+(parseDouble(rdb.getBalancesSheet(level5b.get(zb),sheetDate,c).get(level5b.get(zb)).toString()));   
        indicatorInvento=true;
        }

        zb++; 
        }
        if(indicatorInvento){
        TotalHolder.put(5, sumb);

        List inventoriesT= new ArrayList();
        inventoriesT.add("Total Inventory Holdings");
        double sumTotal= TotalHolder.get(5);
        if(sumTotal<0){
        inventoriesT.add("("+ffm.formatForStatementNumbers(sumTotal+"")+")");
        } else{
        inventoriesT.add(ffm.formatForStatementNumbers(sumTotal+""));
        } 
        aggregate.add(inventoriesT);  
        List emptX= new ArrayList();
        emptX.add("");
        emptX.add("");
        emptX.add("");
        emptX.add("");
        aggregate.add(emptX); 
        }
        break;


        case   "Accounts Receivables/Debtors":
        int z1b=0; double sum1b=0.0;boolean accountsRExists=false;
        Map<Integer, String> level5ab=rdb.getLevel5Items("Accounts Receivables/Debtors"); 
        while(z1b<=level5ab.size()-1){

        if(parseDouble(rdb.getBalancesSheet(level5ab.get(z1b),sheetDate,c).get(level5ab.get(z1b)).toString())!=0.0){
        if(!accountsRExists){

        List accountsReceivables= new ArrayList();
        accountsReceivables.add("Accounts Receivables/Debtors");
        aggregate.add(accountsReceivables); 
        accountsRExists=true;
        }
        List accountsReceivablesin= new ArrayList();
        accountsReceivablesin.add(level5ab.get(z1b));
        double amountb=parseDouble(rdb.getBalancesSheet(level5ab.get(z1b),sheetDate,c).get(level5ab.get(z1b)).toString());
        if(amountb<0.0){
        accountsReceivablesin.add("("+ffm.formatForStatementNumbers(amountb+"")+")");
        } else{

        accountsReceivablesin.add(ffm.formatForStatementNumbers(amountb+""));
        }
        aggregate.add(accountsReceivablesin);  
        indicatorReceivable=true;
        }
        sum1b= sum1b+(parseDouble(rdb.getBalancesSheet(level5ab.get(z1b),sheetDate,c).get(level5ab.get(z1b)).toString()));   
        z1b++; }
        if(indicatorReceivable){
        TotalHolder.put(6, sum1b);
        List totalAccountsRece= new ArrayList();
        totalAccountsRece.add("Total Value of Accounts Receivable");
        double totalAmountInv=0.0;
        totalAmountInv= TotalHolder.get(6);
        if(totalAmountInv<0.0){
        totalAccountsRece.add("("+ffm.formatForStatementNumbers(totalAmountInv+"")+")");

        } else{
        totalAccountsRece.add(ffm.formatForStatementNumbers(totalAmountInv+""));    
        }
        aggregate.add(totalAccountsRece);  
        List emptt= new ArrayList();
        emptt.add("");
        emptt.add("");
        emptt.add("");
        emptt.add("");
        aggregate.add(emptt);            
        }             
        break; 

        case   "Cash And Bank Balance":
        int z1bc=0; double sum1bc=0.0;boolean cashBankBal=false;
        Map<Integer, String> level5abc=rdb.getLevel5Items("Cash And Bank Balance"); 
        while(z1bc<=level5abc.size()-1){

        if(parseDouble(rdb.getBalancesSheet(level5abc.get(z1bc),sheetDate,c).get(level5abc.get(z1bc)).toString())!=0.0){
        if(!cashBankBal){

        List cashAtBank= new ArrayList();
        cashAtBank.add("Cash And Bank Balance");
        aggregate.add(cashAtBank); 
        cashBankBal=true;
        }


        List cashAtBankin= new ArrayList();
        cashAtBankin.add(level5abc.get(z1bc));
        double amountbc=parseDouble(rdb.getBalancesSheet(level5abc.get(z1bc),sheetDate,c).get(level5abc.get(z1bc)).toString());
        if(amountbc<0.0){
        cashAtBankin.add("("+ffm.formatForStatementNumbers(amountbc+"")+")");
        } else{

        cashAtBankin.add(ffm.formatForStatementNumbers(amountbc+""));
        }
        aggregate.add(cashAtBankin);   
        indicatorCash=true;
        }

        sum1bc= sum1bc+(parseDouble(rdb.getBalancesSheet(level5abc.get(z1bc),sheetDate,c).get(level5abc.get(z1bc)).toString()));   
        z1bc++; }
        if(indicatorCash){
        TotalHolder.put(7, sum1bc);
        List totalCash= new ArrayList();
        totalCash.add("Total Value of Cash And Bank Balances");

        double totalAmountInvc=0.0;
        totalAmountInvc= TotalHolder.get(7);
        if(totalAmountInvc<0.0){
        totalCash.add("("+ffm.formatForStatementNumbers(totalAmountInvc+"")+")");

        } else{
        totalCash.add(ffm.formatForStatementNumbers(totalAmountInvc+""));    
        }
        aggregate.add(totalCash);  
        List em= new ArrayList();
        em.add("");
        em.add("");
        em.add("");
        em.add("");
        aggregate.add(em);              
        }              
        break;  

        case   "Loans And Advances":
        int z1bA1=0; double sum1bA1=0.0;boolean loanThere=false;
        Map<Integer, String> level5abA1=rdb.getLevel5Items("Loans And Advances"); 
        while(z1bA1<=level5abA1.size()-1){

        if(parseDouble(rdb.getBalancesSheet(level5abA1.get(z1bA1),sheetDate,c).get(level5abA1.get(z1bA1)).toString())!=0.0){
        if(!loanThere){

        List loansAd= new ArrayList();
        loansAd.add("Loans And Advances");
        aggregate.add(loansAd); 
        loanThere=true;
        }


        List loansAdin= new ArrayList();
        loansAdin.add(level5abA1.get(z1bA1));
 
        double amountbA1=parseDouble(rdb.getBalancesSheet(level5abA1.get(z1bA1),sheetDate,c).get(level5abA1.get(z1bA1)).toString());
        if(amountbA1<0.0){
        loansAdin.add("("+ffm.formatForStatementNumbers(amountbA1+"")+")");
        } else{

        loansAdin.add(ffm.formatForStatementNumbers(amountbA1+""));
        }
        aggregate.add(loansAdin); 
        indicatorLoan=true;
        }

        sum1bA1= sum1bA1+(parseDouble(rdb.getBalancesSheet(level5abA1.get(z1bA1),sheetDate,c).get(level5abA1.get(z1bA1)).toString()));   
        z1bA1++; }
        if(indicatorLoan){ 
        TotalHolder.put(8, sum1bA1);
        List loansAdT= new ArrayList();
        loansAdT.add("Total Value of Loans And Advances");

        double totalAmountInvA1=0.0;
        totalAmountInvA1= TotalHolder.get(8);
        if(totalAmountInvA1<0.0){
        loansAdT.add("("+ffm.formatForStatementNumbers(totalAmountInvA1+"")+")");

        } else{
        loansAdT.add(ffm.formatForStatementNumbers(totalAmountInvA1+""));    
        }
        aggregate.add(loansAdT);                
        }              
        break;
        }

        yb++; 
        }

        double totalCurrentAssets=0.0;
        if(indicatorCash||indicatorLoan||indicatorReceivable||indicatorInvento){
        totalCurrentAssets= TotalHolder.get(5)+ TotalHolder.get(6)+ TotalHolder.get(7)+ TotalHolder.get(8);
        TotalHolder.put(9, totalCurrentAssets);

        List emJ= new ArrayList();
        emJ.add("");
        emJ.add("");
        emJ.add("");
        emJ.add("");
        aggregate.add(emJ);

        List loansAdTT= new ArrayList();
        loansAdTT.add("Total Current Assets,Loans And Advances");

        if(TotalHolder.get(9)<0){

        loansAdTT.add("("+ffm.formatForStatementNumbers(TotalHolder.get(9)+"")+")"); 

        }else{  loansAdTT.add(ffm.formatForStatementNumbers(TotalHolder.get(9)+"")); 
        }
        aggregate.add(loansAdTT);

        List emJC= new ArrayList();
        emJC.add("");
        emJC.add("");
        emJC.add("");
        emJC.add("");
        aggregate.add(emJC);
        }
        break;

        case   "Deferred Taxes Assets" :

        boolean defferedTaxa=false;

        int zX=0; double sumX=0.0; 
        Map<Integer, String> level5=rdb.getLevel5Items("Deferred Tax Assets"); 


        if(parseDouble(rdb.getBalancesSheet(level5.get(zX),sheetDate,c).get(level5.get(zX)).toString())!=0.0){

        List defferedTax= new ArrayList();

        defferedTax.add("Deferred Tax Assets");
        aggregate.add(defferedTax);
        List emJCd= new ArrayList();
        emJCd.add("");
        emJCd.add("");
        emJCd.add("");
        emJCd.add("");
        aggregate.add(emJCd);

        List defferedTaxas= new ArrayList();
        defferedTaxas.add("Deferred Tax Assets");
        aggregate.add(defferedTaxas);

        List defferedTaxasin= new ArrayList();
        defferedTaxasin.add(level5.get(zX));


        double amountX=parseDouble(rdb.getBalancesSheet(level5.get(zX),sheetDate,c).get(level5.get(zX)).toString());

        if(amountX<0.0){
        defferedTaxasin.add("("+ffm.formatForStatementNumbers(amountX+"")+")");
        } else{

        defferedTaxasin.add(ffm.formatForStatementNumbers(amountX+""));

        }
        aggregate.add(defferedTaxasin);
        sumX= sumX+(parseDouble(rdb.getBalancesSheet(level5.get(zX),sheetDate,c).get(level5.get(zX)).toString()));  
        defferedTaxa=true;
        }


        if(defferedTaxa){   

        List defferedTaxasT= new ArrayList();
        defferedTaxasT.add("Total Deferred Tax Assets");

        TotalHolder.put(10, sumX);
        if(TotalHolder.get(10)<0.0){
        defferedTaxasT.add("("+ffm.formatForStatementNumbers(TotalHolder.get(10)+"")+")");

        }else{
        defferedTaxasT.add(ffm.formatForStatementNumbers(TotalHolder.get(10)+""));}
        aggregate.add(defferedTaxasT);
        }




        if(defferedTaxa){
        double totalDefered=0.0;

        totalDefered=TotalHolder.get(10);


        TotalHolder.put(11, totalDefered);

        List eV= new ArrayList();
        eV.add("");
        eV.add("");
        eV.add("");
        eV.add("");
        eV.add("");
        aggregate.add(eV);
        List taxAssets= new ArrayList();
        taxAssets.add("Total Deferred Tax Assets");
        taxAssets.add(ffm.formatForStatementNumbers(TotalHolder.get(11)+"")); 
        aggregate.add(taxAssets);
        List ep= new ArrayList();
        ep.add("");
        ep.add("");
        ep.add("");
        ep.add("");
        ep.add("");
        aggregate.add(ep);  
        }
        break;     

        }
        x++;
        }  

        int xL=0; 
        Map<Integer, String> level3L=rdb. getLevel3Items("Liabilities");
        List liabilities= new ArrayList();
        liabilities.add("Liabilities");
        aggregate.add(liabilities);


        while(xL<=level3L.size()-1){
        switch(level3L.get(xL)){
        case "Current Liabilities & Provisions":
        int yL=0;
        Map<Integer, String> level4L=rdb.getLevel4Items("Current Liabilities & Provisions");  

        List curLiabilitiesP= new ArrayList();
        curLiabilitiesP.add("Current Liabilities And Provisions");
        aggregate.add(curLiabilitiesP);
        List epS= new ArrayList();
        epS.add("");
        epS.add("");
        epS.add("");
        epS.add("");
        epS.add("");
        aggregate.add(epS);  

        while(yL<=level4L.size()-1){
        switch(level4L.get(yL)){
        case "Current Liabilities":
        int zL=0; double sumL=0.0; boolean currentThere=false;
        Map<Integer, String> level5L=rdb.getLevel5Items("Current Liabilities"); 
        while(zL<=level5L.size()-1){

        if(parseDouble(rdb.getBalancesSheet(level5L.get(zL),sheetDate,c).get(level5L.get(zL)).toString())!=0.0){
        if(!currentThere){
        List cLiabilities= new ArrayList();
        cLiabilities.add("Current Liabilities");
        aggregate.add(cLiabilities);
        currentThere=true;
        }

        List cLiabilitiesIn= new ArrayList();
        cLiabilitiesIn.add(level5L.get(zL));


        double amountL=parseDouble(rdb.getBalancesSheet(level5L.get(zL),sheetDate,c).get(level5L.get(zL)).toString());
        if(amountL<0.0){
        cLiabilitiesIn.add("("+ffm.formatForStatementNumbers(amountL+"")+")");
        } else{

        cLiabilitiesIn.add(ffm.formatForStatementNumbers(amountL+""));

        }
        aggregate.add(cLiabilitiesIn);
        sumL= sumL+(parseDouble(rdb.getBalancesSheet(level5L.get(zL),sheetDate,c).get(level5L.get(zL)).toString()));     
        currentLiabCred=true; 
        }

        zL++; 
        }
        if(currentLiabCred){
        TotalHolder.put(12, sumL);
        List cLiabilitiesInT= new ArrayList();
        cLiabilitiesInT.add("Total Current Liabilities");

        if(TotalHolder.get(12)<0.0)
        {
        cLiabilitiesInT.add("("+ffm.formatForStatementNumbers(TotalHolder.get(12)+"")+")");
        } else{

        cLiabilitiesInT.add(ffm.formatForStatementNumbers(TotalHolder.get(12)+""));}

        aggregate.add(cLiabilitiesInT);
        }
        break;


        case   "Provisions" :
        int z1L=0; double sum1L=0.0;boolean provisionsThere=false;
        Map<Integer, String> level5aL=rdb.getLevel5Items("Provisions"); 
        while(z1L<=level5aL.size()-1){

        if(parseDouble(rdb.getBalancesSheet(level5aL.get(z1L),sheetDate,c).get(level5aL.get(z1L)).toString())!=0.0){
        if(!provisionsThere){
        List provisionsy= new ArrayList();
        provisionsy.add("Provisions");
        aggregate.add(provisionsy);
        provisionsThere=true;
        }
        List provisionsyIn= new ArrayList();
        provisionsyIn.add(level5aL.get(z1L));


        double amountL=parseDouble(rdb.getBalancesSheet(level5aL.get(z1L),sheetDate,c).get(level5aL.get(z1L)).toString());
        if(amountL<0.0){
        provisionsyIn.add("("+ffm.formatForStatementNumbers(amountL+"")+")");
        } else{

        provisionsyIn.add(ffm.formatForStatementNumbers(amountL+""));
        }
        aggregate.add(provisionsyIn);
        provision=true;

        }
        sum1L= sum1L+(parseDouble(rdb.getBalancesSheet(level5aL.get(z1L),sheetDate,c).get(level5aL.get(z1L)).toString()));   
        z1L++; }
        if(provision){
        TotalHolder.put(13, sum1L);

        List provisionsyInT= new ArrayList();
        provisionsyInT.add("Total Provisions");

        double totalAmountInvP=0.0;
        totalAmountInvP=TotalHolder.get(13);
        if(totalAmountInvP<0.0){
        provisionsyInT.add("("+ffm.formatForStatementNumbers(totalAmountInvP+"")+")");

        } else{
        provisionsyInT.add(ffm.formatForStatementNumbers(totalAmountInvP+""));    
        }

        aggregate.add(provisionsyInT);   
        }          

        break; 

        }

        yL++; 
        }
        if(currentLiabCred||provision){
        double totalCurrentLiabilities=0.0;
        totalCurrentLiabilities=TotalHolder.get(12)+TotalHolder.get(13);


        TotalHolder.put(14, totalCurrentLiabilities);
        List epS3= new ArrayList();
        epS3.add("");
        epS3.add("");
        epS3.add("");
        epS3.add("");
        epS3.add("");
        aggregate.add(epS3);  
        List provisionsyIntt= new ArrayList();
        provisionsyIntt.add("Total Current Liabilities And Provisions");


        if(TotalHolder.get(14)<0.0){ 
        provisionsyIntt.add("("+ffm.formatForStatementNumbers(TotalHolder.get(14)+"")+")"); }else{
        provisionsyIntt.add(ffm.formatForStatementNumbers(TotalHolder.get(14)+"")); 

        }
        aggregate.add(provisionsyIntt); 

        List ep4= new ArrayList();
        ep4.add("");
        ep4.add("");
        ep4.add("");
        ep4.add("");
        ep4.add("");
        aggregate.add(ep4); 
        }
        if(indicatorCash||indicatorLoan||indicatorReceivable||indicatorInvento||currentLiabCred||provision){
        double netCurrentAssets=0.0;
        netCurrentAssets=MAth.subtract(TotalHolder.get(9),TotalHolder.get(14));


        TotalHolder.put(20, netCurrentAssets);
        List ep4c= new ArrayList();
        ep4c.add("");
        ep4c.add("");
        ep4c.add("");
        ep4c.add("");
        ep4c.add("");
        aggregate.add(ep4c);

        List provisionsyInTo= new ArrayList();
        provisionsyInTo.add("Net Value of Current Assets");

        if(TotalHolder.get(20)<0.0){
        provisionsyInTo.add("("+ffm.formatForStatementNumbers(TotalHolder.get(20)+"")+")"); }
        else{
        provisionsyInTo.add(ffm.formatForStatementNumbers(TotalHolder.get(20)+"")); 
        }
        aggregate.add(provisionsyInTo);

        List epr= new ArrayList();
        epr.add("");
        epr.add("");
        epr.add("");
        epr.add("");
        epr.add("");
        aggregate.add(epr);
        double totalAssets=0.0;
        totalAssets=MAth.add(TotalHolder.get(11),MAth.add(TotalHolder.get(20),TotalHolder.get(4)));


        TotalHolder.put(21, totalAssets);
        List epr1= new ArrayList();
        epr1.add("");
        epr1.add("");
        epr1.add("");
        epr1.add("");
        epr1.add("");
        aggregate.add(epr1);
    
        List TAValue= new ArrayList();
        TAValue.add("Total Assets");
        if(TotalHolder.get(21)<0.0){

        TAValue.add("("+ffm.formatForStatementNumbers(TotalHolder.get(21)+"")+")"); }
        else{
        TAValue.add(ffm.formatForStatementNumbers(TotalHolder.get(21)+"")); }
        aggregate.add(TAValue);
        List epr22= new ArrayList();
        epr22.add("");
        epr22.add("");
        epr22.add("");
        epr22.add("");
        epr22.add("");
        aggregate.add(epr22);
        }
        break;



        case "Non-Current Liabilities/Long Term Liabilities":
        int ybL=0;
        Map<Integer, String> level4bL=rdb.getLevel4Items("Non-Current Liabilities/Long Term Liabilities"); 
        List nonCurrentL= new ArrayList();

        nonCurrentL.add("Non-Current Liabilities/Long Term Liabilities");
        aggregate.add(nonCurrentL);
        List eprl= new ArrayList();
        eprl.add("");
        eprl.add("");
        eprl.add("");
        eprl.add("");
        eprl.add("");
        aggregate.add(eprl);
        while(ybL<=level4bL.size()-1){
        switch(level4bL.get(ybL)){
        case "Secured Liabilities":
        int zbL=0; double sumbL=0.0; boolean suredThere=false;
        Map<Integer, String> level5bL=rdb.getLevel5Items("Secured Liabilities");
        while(zbL<=level5bL.size()-1){

        if(parseDouble(rdb.getBalancesSheet(level5bL.get(zbL),sheetDate,c).get(level5bL.get(zbL)).toString())!=0.0){
        if(!suredThere){
        List securedLiab= new ArrayList();
        securedLiab.add("Secured Liabilities");
        aggregate.add(securedLiab);

        suredThere=false;
        }

        List securedLiabIn= new ArrayList();
        securedLiabIn.add(level5bL.get(zbL));
        double amountbL=parseDouble(rdb.getBalancesSheet(level5bL.get(zbL),sheetDate,c).get(level5bL.get(zbL)).toString());
        if(amountbL<0.0){
        securedLiabIn.add("("+ffm.formatForStatementNumbers(amountbL+"")+")");
        } else{

        securedLiabIn.add(ffm.formatForStatementNumbers(amountbL+""));

        }
        aggregate.add(securedLiabIn);
        noncursecLiab=true;
        sumbL= sumbL+(parseDouble(rdb.getBalancesSheet(level5bL.get(zbL),sheetDate,c).get(level5bL.get(zbL)).toString()));     
        }

        zbL++; 
        }

        if(noncursecLiab){
        TotalHolder.put(15, sumbL);
        List securedLiabInT= new ArrayList();
        securedLiabInT.add("Total Secured Liabilities");

        double sumTotalA= TotalHolder.get(15);
        if(sumTotalA<0){
        securedLiabInT.add("("+ffm.formatForStatementNumbers(sumTotalA+"")+")");
        } else{
        securedLiabInT.add(ffm.formatForStatementNumbers(sumTotalA+""));
        }

        aggregate.add(securedLiabInT);
        List eprlY= new ArrayList();
        eprlY.add("");
        eprlY.add("");
        eprlY.add("");
        eprlY.add("");
        eprlY.add("");
        aggregate.add(eprlY);
        }
        break;


        case   "Unsecured Liabilities":
        int z1bL=0; double sum1bL=0.0; boolean unsecuredLthere=false;
        Map<Integer, String> level5abL=rdb.getLevel5Items("Unsecured Liabilities"); 
        while(z1bL<=level5abL.size()-1){

        if(parseDouble(rdb.getBalancesSheet(level5abL.get(z1bL),sheetDate,c).get(level5abL.get(z1bL)).toString())!=0.0){
        if(!unsecuredLthere){
        List UsecuredLiabInG= new ArrayList();
        UsecuredLiabInG.add("Unsecured Liabilities");
        aggregate.add(UsecuredLiabInG);
        unsecuredLthere=true;
        }


        List UsecuredLiabInGIn= new ArrayList();
        UsecuredLiabInGIn.add(level5abL.get(z1bL));


        double amountbL=parseDouble(rdb.getBalancesSheet(level5abL.get(z1bL),sheetDate,c).get(level5abL.get(z1bL)).toString());
        if(amountbL<0.0){
        UsecuredLiabInGIn.add("("+ffm.formatForStatementNumbers(amountbL+"")+")");
        } else{

        UsecuredLiabInGIn.add(ffm.formatForStatementNumbers(amountbL+""));
        }
        aggregate.add(UsecuredLiabInGIn);
        noncurunseclian=true; 

        }
        sum1bL= sum1bL+(parseDouble(rdb.getBalancesSheet(level5abL.get(z1bL),sheetDate,c).get(level5abL.get(z1bL)).toString()));   
        z1bL++; }
        if(noncurunseclian){
        TotalHolder.put(16, sum1bL);
        List TUsecuredLiabInGIn= new ArrayList();
        TUsecuredLiabInGIn.add("Total Unsecured Liabilities");

        double totalAmountInv=0.0;
        totalAmountInv= TotalHolder.get(16);
        if(totalAmountInv<0.0){
        TUsecuredLiabInGIn.add("("+ffm.formatForStatementNumbers(totalAmountInv+"")+")");

        } else{
        TUsecuredLiabInGIn.add(ffm.formatForStatementNumbers(totalAmountInv+""));    
        }

        aggregate.add(TUsecuredLiabInGIn);
        List eprlYR= new ArrayList();
        eprlYR.add("");
        eprlYR.add("");
        eprlYR.add("");
        eprlYR.add("");
        eprlYR.add("");
        aggregate.add(eprlYR);                
        }              
        break; 


        }

        ybL++; 
        }

        if(noncursecLiab||noncurunseclian){
        double totalNonCurrentLiabi=0.0;

        totalNonCurrentLiabi= TotalHolder.get(15)+ TotalHolder.get(16);
        TotalHolder.put(17, totalNonCurrentLiabi);
        List totalNonCL= new ArrayList();
        totalNonCL.add("Total Non Current Liabilities");
 


        if(TotalHolder.get(17)<0.0){
        totalNonCL.add("("+ffm.formatForStatementNumbers(TotalHolder.get(17)+"")+")"); 
        }else{
        totalNonCL.add(ffm.formatForStatementNumbers(TotalHolder.get(17)+"")); 
        }

        aggregate.add(totalNonCL);  
        List epW= new ArrayList();
        epW.add("");
        epW.add("");
        epW.add("");
        epW.add("");
        epW.add("");
        aggregate.add(epW); 
        }
        break;

        case   "Deferred Taxes Liabilities" :
        int yXL=0;
        Map<Integer, String> level4XL=rdb.getLevel4Items("Deferred Taxes Liabilities");  

        List dTLiability= new ArrayList();
        dTLiability.add("Deferred Taxes Liabilities");
        aggregate.add(dTLiability); 
        List emt= new ArrayList();
        emt.add("");
        emt.add("");
        emt.add("");
        emt.add("");
        emt.add("");
        aggregate.add(emt); 
        while(yXL<=level4XL.size()-1){
        switch(level4XL.get(yXL)){

        case "Deferred Tax Liabilities":
        int zXL=0; double sumXL=0.0; 
        Map<Integer, String> level5L=rdb.getLevel5Items("Deferred Tax Liabilities"); 


        List dTLiabiliies= new ArrayList();
        dTLiabiliies.add("Deferred Tax Liabilities");
        aggregate.add(dTLiabiliies);
        while(zXL<=level5L.size()-1){

        if(parseDouble(rdb.getBalancesSheet(level5L.get(zXL),sheetDate,c).get(level5L.get(zXL)).toString())!=0.0){
        List dTLiabiliiesIn= new ArrayList();
        dTLiabiliiesIn.add(level5L.get(zXL));

        double amountXL=parseDouble(rdb.getBalancesSheet(level5L.get(zXL),sheetDate,c).get(level5L.get(zXL)).toString());
        if(amountXL<0.0){
        dTLiabiliiesIn.add("("+ffm.formatForStatementNumbers(amountXL+"")+")");
        } else{

        dTLiabiliiesIn.add(ffm.formatForStatementNumbers(amountXL+""));

        }

        aggregate.add(dTLiabiliiesIn);
        sumXL= sumXL+(parseDouble(rdb.getBalancesSheet(level5L.get(zXL),sheetDate,c).get(level5L.get(zXL)).toString()));     
        taxLiabi=true;
        }


        zXL++; 
        }
        if(taxLiabi){
        TotalHolder.put(18, sumXL);

        List sdTLiabiliiesIn= new ArrayList();
        sdTLiabiliiesIn.add("Sub Total Deferred Tax Liabilities");


        if(TotalHolder.get(18)<0.0){
        sdTLiabiliiesIn.add("("+ffm.formatForStatementNumbers(TotalHolder.get(18)+"")+")");
        }else{
        sdTLiabiliiesIn.add(ffm.formatForStatementNumbers(TotalHolder.get(18)+""));
        }


        aggregate.add(sdTLiabiliiesIn);
        }
        break;

        }

        yXL++; 
        }

        if(taxLiabi){
        double totalDefered=0.0;

        totalDefered=TotalHolder.get(18);


        TotalHolder.put(19, totalDefered);
        List emt2= new ArrayList();
        emt2.add("");
        emt2.add("");
        emt2.add("");
        emt2.add("");
        emt2.add("");
        aggregate.add(emt2); 
        List tsdTLiabiliiesIn= new ArrayList();

        tsdTLiabiliiesIn.add("Total Deferred Tax Liabilities");


        if(TotalHolder.get(19)<0.0){

        tsdTLiabiliiesIn.add("("+ffm.formatForStatementNumbers(TotalHolder.get(19)+"")+")"); 
        }else{
        tsdTLiabiliiesIn.add(ffm.formatForStatementNumbers(TotalHolder.get(19)+"")); 
        }
       
        aggregate.add(tsdTLiabiliiesIn); 
        List emt2w= new ArrayList();
        emt2w.add("");
        emt2w.add("");
        emt2w.add("");
        emt2w.add("");
        emt2w.add("");
        aggregate.add(emt2w);  
        }
        break;     

        }
        xL++;
        }  

        int xLe=0; 
        Map<Integer, String> level3Le=rdb. getLevel3Items("Equity/Capital");

        List equityC= new ArrayList();

        equityC.add("Equity/Capital");
        aggregate.add(equityC);  
        while(xLe<=level3Le.size()-1){
        switch(level3Le.get(xLe)){
        case "Equity":
        int yLe=0;
        Map<Integer, String> level4Le=rdb.getLevel4Items("Equity");  

        List equityCV= new ArrayList();

        equityCV.add("Equity");
        aggregate.add(equityCV); 
        List ese= new ArrayList();
        ese.add("");
        ese.add("");
        ese.add("");
        ese.add("");
        ese.add("");
        aggregate.add(ese);

        while(yLe<=level4Le.size()-1){
        switch(level4Le.get(yLe)){
        case "Share Capital":
        int zLe=0; double sumLe=0.0; boolean sharesThere=false;
        Map<Integer, String> level5Le=rdb.getLevel5Items("Share Capital"); 
        while(zLe<=level5Le.size()-1){

        if(parseDouble(rdb.getBalancesSheet(level5Le.get(zLe),sheetDate,c).get(level5Le.get(zLe)).toString())!=0.0){
        if(!sharesThere){
        List shareC= new ArrayList();
        shareC.add("Share Capital");
        aggregate.add(shareC);
        sharesThere=true;
        }


        List shareCin= new ArrayList();
        shareCin.add(level5Le.get(zLe));


        double amountLe=parseDouble(rdb.getBalancesSheet(level5Le.get(zLe),sheetDate,c).get(level5Le.get(zLe)).toString());
        if(amountLe<0.0){
        shareCin.add("("+ffm.formatForStatementNumbers(amountLe+"")+")");
        } else{

        shareCin.add(ffm.formatForStatementNumbers(amountLe+""));

        }

        aggregate.add(shareCin);
        sumLe= sumLe+(parseDouble(rdb.getBalancesSheet(level5Le.get(zLe),sheetDate,c).get(level5Le.get(zLe)).toString()));     
        currentLiabCreds=true; 
        }

        zLe++; 
        }
        if(currentLiabCreds){
        TotalHolder.put(22, sumLe);
        List tshareCin= new ArrayList();
        tshareCin.add("Total Share Capital");

        if(TotalHolder.get(22)<0.0)
        { tshareCin.add("("+ffm.formatForStatementNumbers(TotalHolder.get(22)+"")+")");
        } else{ tshareCin.add(ffm.formatForStatementNumbers(TotalHolder.get(22)+""));}

        aggregate.add(tshareCin);
        }
        break;


        case   "Reserves And Surplus" :

        int z1Le=0; double sum1Le=0.0,amountLe=0.0,finalT=0.0;boolean reservesThere=false,isretain=false;   PdfPCell balAmountLe=null;

        Map<Integer, String> level5aL=rdb.getLevel5Items("Reserves And Surplus"); 
        while(z1Le<=level5aL.size()-1){

        if(parseDouble(rdb.getBalancesSheet(level5aL.get(z1Le),sheetDate,c).get(level5aL.get(z1Le)).toString())!=0.0){

        if(!reservesThere){
        List reservews= new ArrayList();
        reservews.add("Reserves And Surplus");
        aggregate.add(reservews);
        reservesThere=true;
        }  




        if(level5aL.get(z1Le).equalsIgnoreCase("Retained Earnings")){


        amountLe=parseDouble(rdb.getBalancesSheet(level5aL.get(z1Le),sheetDate,c).get(level5aL.get(z1Le)).toString())+parseDouble(netprofits.theNetProfits(ffm.forDatabaseWithFullYearBeginningWithDate(fios.stringFileReader(fios.createFileName("persistence", "operatingCycle", "YEARstartDate.txt"))),sheetDate,c));
        //         sum1Le=amountLe; 
        isretain=true;
        List reservewsin= new ArrayList();
        reservewsin.add(level5aL.get(z1Le));

        if(amountLe<0.0){
        reservewsin.add("("+ffm.formatForStatementNumbers(amountLe+"")+")");
        } else{

        reservewsin.add(ffm.formatForStatementNumbers(amountLe+""));
        }
        aggregate.add(reservewsin);
        provisions=true;

        }else{

        List reservewsinh= new ArrayList();
        reservewsinh.add(level5aL.get(z1Le));

        amountLe=parseDouble(rdb.getBalancesSheet(level5aL.get(z1Le),sheetDate,c).get(level5aL.get(z1Le)).toString());


        if(amountLe<0.0){
        reservewsinh.add("("+ffm.formatForStatementNumbers(amountLe+"")+")");
        } else{

        reservewsinh.add(ffm.formatForStatementNumbers(amountLe+""));
        }

        aggregate.add(reservewsinh);
        provisions=true;
        }
        } 
        //         if(parseDouble(netprofits.theNetProfits(ffm.dateForDatabaseWithFullYearBeginningWithDate(fios.stringFileReader(fios.createFileName("persistence", "operatingCycle", "YEARstartDate.txt"))),sheetDate))!=0.0)
        else{
        if(!isretain){
        if(!reservesThere){

        List reserveSurpl= new ArrayList();
        reserveSurpl.add("Reserves And Surplus");
        reservesThere=true;
        }
        if(!secondRetained){   

        List reserveSurplr= new ArrayList();
        reserveSurplr.add("Retained Earnings");



        amountLe=parseDouble(netprofits.theNetProfits(ffm.forDatabaseWithFullYearBeginningWithDate(fios.stringFileReader(fios.createFileName("persistence", "operatingCycle", "YEARstartDate.txt"))),sheetDate,c));


        if(amountLe<0.0){
        reserveSurplr.add("("+ffm.formatForStatementNumbers(amountLe+"")+")");
        } else{

        reserveSurplr.add(ffm.formatForStatementNumbers(amountLe+""));
        }
 
        aggregate.add(reserveSurplr);
        provisions=true;
        secondRetained=true;
        //        sum1Le=amountLe;
        }
        }
        isretain=true;
        }

        if(level5aL.get(z1Le).equalsIgnoreCase("Retained Earnings")){
        finalT=amountLe;
        }else{

        finalT=(parseDouble(rdb.getBalancesSheet(level5aL.get(z1Le),sheetDate,c).get(level5aL.get(z1Le)).toString()));
        }

        sum1Le= sum1Le+finalT;   
        z1Le++;


        }
        if(provisions){
        TotalHolder.put(23, sum1Le);

        List TreserveSurplr= new ArrayList();
        TreserveSurplr.add("Total Reserves And Surplus");

        double totalAmountInvP=0.0;
        totalAmountInvP=TotalHolder.get(23);
        if(totalAmountInvP<0.0){
        TreserveSurplr.add("("+ffm.formatForStatementNumbers(totalAmountInvP+"")+")");

        } else{
        TreserveSurplr.add(ffm.formatForStatementNumbers(totalAmountInvP+""));    
        }
        aggregate.add(TreserveSurplr);
        }
        break;

        }

        yLe++; 
        }
        if(currentLiabCreds||provisions){
        double totalShareHolders=0.0;
        totalShareHolders=TotalHolder.get(22)+TotalHolder.get(23);


        TotalHolder.put(24, totalShareHolders);
        List eseY= new ArrayList();
        eseY.add("");
        eseY.add("");
        eseY.add("");
        eseY.add("");
        eseY.add("");
        aggregate.add(eseY);

        List TreserveSur= new ArrayList();
        TreserveSur.add("Total Share Holder's Funds");



        if(TotalHolder.get(24)<0.0){ TreserveSur.add("("+ffm.formatForStatementNumbers(TotalHolder.get(24)+"")+")"); }else{
        TreserveSur.add(ffm.formatForStatementNumbers(TotalHolder.get(24)+"")); }
        aggregate.add(TreserveSur);
        List eseYH= new ArrayList();
        eseYH.add("");
        eseYH.add("");
        eseYH.add("");
        eseYH.add("");
        eseYH.add("");
        aggregate.add(eseYH);
        }

        if(noncursecLiab||noncurunseclian||currentLiabCreds||provisions){
        double totalCurrentLiabilityAndEquity=0.0;
        totalCurrentLiabilityAndEquity=MAth.add(TotalHolder.get(24),MAth.add(TotalHolder.get(17),TotalHolder.get(18)));


        TotalHolder.put(25,  totalCurrentLiabilityAndEquity);
 
        List feseYH= new ArrayList();
        feseYH.add("");
        feseYH.add("");
        feseYH.add("");
        feseYH.add("");
        feseYH.add("");
        aggregate.add(feseYH);

        List tELL= new ArrayList();

        tELL.add("Total Equity And Long Term Liabilities");;


        if(TotalHolder.get(25)<0.0){tELL.add("("+ffm.formatForStatementNumbers(TotalHolder.get(25)+"")+")"); }

        else {tELL.add(ffm.formatForStatementNumbers(TotalHolder.get(25)+"")); }
  
        aggregate.add(tELL);
        List eJYH1= new ArrayList();
        eJYH1.add("");
        eJYH1.add("");
        eJYH1.add("");
        eJYH1.add("");
        eJYH1.add("");
        aggregate.add(eJYH1);
      
        }
        break;     

        }
        xLe++;
        }  


        return aggregate;


}




}
