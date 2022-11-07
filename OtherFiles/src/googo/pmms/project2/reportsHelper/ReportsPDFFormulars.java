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
import googo.pmms.project2.frameHelper.MyTableModel;
import googo.pmms.project2.frames.PostingEntryWindow;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databaseConnectors.JdbcConnector;
import googo.pmms.project2.frameHelper.ReportsBoolenaModelData;
import googo.pmms.project2.frameHelper.ReportsModelData;
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
import javax.swing.JTable;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Stanchart
 */
public class ReportsPDFFormulars implements PdfPageEvent {
//     Amortization amortize = new Amortization();
 MyTableModel model;ReportsModelData model1;ReportsBoolenaModelData model1b;
     Calendar calN = Calendar.getInstance(); 
fileInputOutPutStreams fios= new fileInputOutPutStreams();
    Formartter ffm= new Formartter();
    Date Trndate,valuedate;
   ArrayList<String> data4, column1;
 ArrayList<ArrayList<String>> data5;
          JOptionPane p;
    Date date;
  SimpleDateFormat df;
  String text;
  int realMonth, otherMonth;
   String dates, dates2,getFieldValue,actualFieldValue,  jTFuserId1mt,today,thistime,today1,newDate1,jTFuserId1mt1,newDate11,today2;
   Integer Value,Value1;
   double newbalance,ledgerBalance,creditamount;
    GregorianCalendar cal = new GregorianCalendar(); 
   JdbcConnector csx = new JdbcConnector(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "AccountDBDetails.txt"))); ; 
    DatabaseQuaries dbq =new DatabaseQuaries();
    
ReportsDatabase rdb = new ReportsDatabase();
 int   pagenumber=0;
   String userId;
   public void setUserID(String userid){
this.userId=userid;
}

   public void createStatementSt(JTable table,String accountNumber, String StartDate,String EndDate){
  
            column1=new ArrayList();
            column1.add("EntryDate");
            column1.add("ValueDate");
            column1.add("Description");
            column1.add("Debits");
            column1.add("Credits");
            column1.add("Balance");
            column1.add("Trn_id");
          
//      Map<Integer, List<Object>> dataBase=rdb. customerDemog();
   Map<Integer, List<Object>> dataBase=rdb.statementEntriesJT(accountNumber, StartDate, EndDate);
        model1= new ReportsModelData( dataBase, column1);
           table.setModel(model1);
           
       TableRowSorter<ReportsModelData> sorter = new TableRowSorter<>(model1);
      table.setRowSorter(sorter);
      
      
      }
   
   
    public void createStatementStXv(JTable table,String accountNumber, String StartDate,String EndDate,Component c){
  
            column1=new ArrayList();
             column1.add("TxnId");
                column1.add("BatchNumber");
            column1.add("EntryDate");
            column1.add("ValueDate");
            column1.add("Description");
            column1.add("Debits");
            column1.add("Credits");
            column1.add("Balance");
//            column1.add("Trn_id");
          
//      Map<Integer, List<Object>> dataBase=rdb. customerDemog();
   Map<Integer, List<Object>> dataBase=rdb.statementEntriesJTX(accountNumber, StartDate, EndDate);
        model1= new ReportsModelData( dataBase, column1);
        JOptionPane.showMessageDialog(c, dataBase.size()+"");
           table.setModel(model1);
           
       TableRowSorter<ReportsModelData> sorter = new TableRowSorter<>(model1);
      table.setRowSorter(sorter);
      
      
      }
   
   
   public void createReconStatement(JTable table,String accountNumber, String StartDate,String EndDate){
  
            column1=new ArrayList();
             column1.add("Trn_id");
            column1.add("EntryDate");
            column1.add("ValueDate");
            column1.add("Description");
            column1.add("Debits");
            column1.add("Credits");
            column1.add("Balance");
           column1.add("Input By");
           column1.add("Check");
          
//      Map<Integer, List<Object>> dataBase=rdb. customerDemog();
   Map<Integer, List<Object>> dataBase=rdb.statementEntriesRecon(accountNumber, StartDate, EndDate);
    
        model1b= new ReportsBoolenaModelData( dataBase, column1);
           
        table.setModel(model1b);
           
       TableRowSorter<ReportsBoolenaModelData> sorter = new TableRowSorter<>(model1b);
     
       
       table.setRowSorter(sorter);
      
      
      }
   
    public void createStatementStAnalysis(JTable table,String trnCode){
 
            column1=new ArrayList();
            column1.add("EntryDate");
            column1.add("ValueDate");
            column1.add("Description");
            column1.add("Debits");
            column1.add("Credits");
            column1.add("Txn Ledger");
         
          
//      Map<Integer, List<Object>> dataBase=rdb. customerDemog();

   Map<Integer, List<Object>> dataBase=rdb.statementEntriesAnalysis(trnCode);

        model1= new ReportsModelData( dataBase, column1);
           table.setModel(model1);
           
       TableRowSorter<ReportsModelData> sorter = new TableRowSorter<>(model1);
      table.setRowSorter(sorter);
      
      
      }
     public void createStatementDepSchedule(JTable table,String trnCode){
 
            column1=new ArrayList();
            column1.add("S/n");
            column1.add("DepMonth");
            column1.add("DepYear");
            column1.add("OpeningAssetValue");
            column1.add("DepInstalment");
           column1.add("AccumDep");
         column1.add("NBV");
           column1.add("DepStatus");
//      Map<Integer, List<Object>> dataBase=rdb. customerDemog();

   Map<Integer, List<Object>> dataBase=rdb.depSchedule(trnCode);

        model1= new ReportsModelData( dataBase, column1);
           table.setModel(model1);
           
       TableRowSorter<ReportsModelData> sorter = new TableRowSorter<>(model1);
      table.setRowSorter(sorter);
      
      
      }
   
      public void createStatementStAmount(JTable table,String accountNumber, String StartDate,String EndDate,String amount){
  
            column1=new ArrayList();
            
            column1.add("EntryDate");
            column1.add("ValueDate");
            column1.add("Description");
            column1.add("Debits");
            column1.add("Credits");
            column1.add("Balance");
            column1.add("Trn_id");
            
          
//      Map<Integer, List<Object>> dataBase=rdb. customerDemog();
   Map<Integer, List<Object>> dataBase=rdb.statementEntriesAmountJT(accountNumber, StartDate, EndDate,amount);
        model1= new ReportsModelData( dataBase, column1);
           table.setModel(model1);
           
       TableRowSorter<ReportsModelData> sorter = new TableRowSorter<>(model1);
      table.setRowSorter(sorter);
      
      
      }
      
   
   public boolean createStatement(String accountNumber, String StartDate, String EndDate,String fileName) { 
     
  boolean Statement=false;
     
  
  if(fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
  if(fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
createActuallyStatement(accountNumber,  StartDate,  EndDate,fileName);
Statement=true;
  } 
    
  }else{
createActuallyStatement(accountNumber,  StartDate,  EndDate,fileName);
Statement=true;
  }
      
      return Statement;
       }  
   
   
  
      private void createActuallyStatement(String accountNumber, String StartDate, String EndDate,String fileName2){   
      
      String fileName="",accountName="";
  accountName=rdb.selectAccountName(accountNumber);
      try {
      
          
          Paragraph headerz =createHeading(accountNumber,accountName,StartDate,EndDate);
          headerz.setIndentationLeft(50);
          headerz.setIndentationRight(50);
         
          
          
          Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
          PdfWriter writer=PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName2+".pdf")));
          PdfPTable body= createCustomerTable(accountNumber, StartDate,  EndDate);
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
          
          String name = accountName;
          String[] words = name.split("\\s");
          Image image1 = Image.getInstance("strawberry3.jpg");
          image1.setAbsolutePosition(49f, 1080f);
          image1.scaleAbsolute(43f, 43f);
          document.add(image1);
          document.add(headerz );
          document.add(new Paragraph("  ") );
          Paragraph currency= new Paragraph("CURRENCY UGANDA SHILLINGS",font1);
          currency.setIndentationLeft(280f);
          currency.setIndentationRight(30f);
          document.add( currency);
          document.add(new Paragraph("  ") );
          document.add( body);
          document.add(new Paragraph("  ") );
          document.add(new Paragraph("  ") );
           document.add(new Paragraph("  ") );
    document.add(new Paragraph("  ") );
    document.add(this.createFooter());
            document.newPage();
          document.close();
          fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName2+".pdf")).close();
          fileName =fileName2; 
         
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
    PdfPCell cell1 = new PdfPCell(new Paragraph("OFFICIALS",font2));
    PdfPCell cell2 = new PdfPCell(new Paragraph("",font2));
    PdfPCell cell3 = new PdfPCell(new Paragraph("CUSTOMER/MEMBER",font2));
   PdfPCell cell4 = new PdfPCell(new Paragraph("NAME:_________________",font1));
    PdfPCell cell5 = new PdfPCell(new Paragraph("",font1));
    PdfPCell cell6 = new PdfPCell(new Paragraph("NAME:_________________",font1));
    PdfPCell cell7 = new PdfPCell(new Paragraph("SIGNATURE:____________",font1));
    PdfPCell cell8 = new PdfPCell(new Paragraph("",font1));
    PdfPCell cell9 = new PdfPCell(new Paragraph("SIGNATURE:____________",font1));
    PdfPCell cell10 = new PdfPCell(new Paragraph("DATE:_________________",font1));
    PdfPCell cell11= new PdfPCell(new Paragraph("",font1));
    PdfPCell cell12= new PdfPCell(new Paragraph("DATE:_________________",font1));

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

       public  List<List> createExcelCustomerTable(String accountNumber, String StartDate, String EndDate){
 
         List<List> custTable=new ArrayList();
           List title= new ArrayList();
           title.add("STATEMENT OF ACCOUNT FOR ACCOUNT NUMBER:"+accountNumber+" "+dbq.AccountName(accountNumber)+" "+"\n"+"FROM"+" "+ffm.fromDatabaseWithDashSeperatorBeginningWithYear(StartDate)+"  TO "+ffm.fromDatabaseWithDashSeperatorBeginningWithYear(EndDate));
        custTable.add(title);
       List realTable=new ArrayList();
    
    realTable.add("EntryDate");
    realTable.add("ValueDate");
    realTable.add("Description");
    realTable.add("Debits");
    realTable.add("Credits");
    realTable.add("Balance");
    custTable.add(realTable);
    int n=0; Map<Integer, List<Object>> dataBase=rdb.statementEntries(accountNumber, StartDate, EndDate);
           if(!dataBase.isEmpty()){
           while(n<dataBase.size()) {
           List realData=dataBase.get(n);int z=0;
           if(n==0){
                  List data1=new ArrayList();
            while(z<realData.size()){
           if(z==0){
              data1.add(realData.get(0).toString());
            
           }
                if(z==1){
              data1.add(realData.get(1).toString());
           
           }
           if(z==2){
         
          data1.add(realData.get(2).toString());
        
            
           }
          
            
             if(z==3){
        data1.add(realData.get(3).toString());
         
             }
              if(z==4){
                 
                  
                 
 
         data1.add(realData.get(4).toString());
      
              }
                if(z==5){
           
           if(realData.get(5).toString().equalsIgnoreCase("-")){
       data1.add(ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", "")));
        } else  if(parseDouble(realData.get(5).toString())<0.0){
        data1.add("("+ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+")");
        }else if(parseDouble(realData.get(5).toString())>=0.0){
        
       data1.add(ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+"");
        }
       
                }
           z++;   
           } 
            custTable.add(data1); 
           }
           if(n==dataBase.size()-1){
                List data2=new ArrayList();
            while(z<realData.size()){
           if(z==0){
              data2.add(realData.get(0).toString());
           
           }
               if(z==1){
              data2.add(realData.get(1).toString());
           
           }
           if(z==2){
         
            data2.add(realData.get(2).toString());
          
            
           }
          
            
             if(z==3){
     
     data2.add(realData.get(3).toString());
            
             }
              if(z==4){
                 
                  
       
 
       data2.add(realData.get(4).toString());
        
     
              }
                if(z==5){
              
           if(realData.get(5).toString().equalsIgnoreCase("-")){
       data2.add(ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+"");
        } else  if(parseDouble(realData.get(5).toString())<0.0){
       data2.add("("+ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+")");
        }else if(parseDouble(realData.get(5).toString())>=0.0){
        
       data2.add(ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+"");
        }
      
                }
           z++;   
           }
            custTable.add(data2);  
           } 
             List data3=new ArrayList();
           while(z<realData.size()){
                 
           if(z==0){
            data3.add(realData.get(0).toString());
         
           }
              if(z==1){
              data3.add(realData.get(1).toString());
           
           }
           if(z==2){
          data3.add(realData.get(2).toString());
         
            
           }
          
            
             if(z==3){
       
        if(realData.get(3).toString().equalsIgnoreCase("-")){
       data3.add(ffm.formatForStatementNumbers(realData.get(3).toString().replaceAll(",", ""))+"");
        }else if(parseDouble(realData.get(3).toString().replaceAll(",", ""))<0.0){
      data3.add("("+ffm.formatForStatementNumbers(realData.get(3).toString().replaceAll(",", ""))+")");
        }else if(parseDouble(realData.get(3).toString().replaceAll(",", ""))>=0.0){
        
       data3.add(ffm.formatForStatementNumbers(realData.get(3).toString().replaceAll(",", ""))+"");
        }
               
             }
              if(z==4){
            
          if(realData.get(4).toString().equalsIgnoreCase("-")){
         data3.add(ffm.formatForStatementNumbers(realData.get(4).toString().replaceAll(",", ""))+"");
        }  else if(parseDouble(realData.get(4).toString().replaceAll(",", ""))<0.0){
        data3.add("("+ffm.formatForStatementNumbers(realData.get(4).toString().replaceAll(",", ""))+")");
        }else if(parseDouble(realData.get(4).toString().replaceAll(",", ""))>=0.0){
        
         data3.add(ffm.formatForStatementNumbers(realData.get(4).toString().replaceAll(",", ""))+"");
        }
        
      
              }
                if(z==5){

           if(realData.get(5).toString().equalsIgnoreCase("-")){
        data3.add(ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+"");
        } else  if(parseDouble(realData.get(5).toString())<0.0){
        data3.add("("+ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+")");
        }else if(parseDouble(realData.get(5).toString())>=0.0){
        
      data3.add(ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+"");
        }
        
      
                }
           z++;   
           } 
         custTable.add(data3);  
           n++;
           } 
           }else{
                 List data4=new ArrayList();
          data4.add("N/A");
          data4.add("N/A");
         data4.add("Empty selection from database");
         data4.add("N/A");
         data4.add("N/A"+"");
         data4.add("N/A"+"");
          data4.add("N/A"); 
             custTable.add(data4); 
           }
      return custTable;

    } 
      public  PdfPTable createCustomerTable(String accountNumber, String StartDate, String EndDate){
       PdfPTable table = new PdfPTable(6);
       table.setWidthPercentage(100);
       float[] columnWidths = {40f,40f, 100f,45f, 45f, 50f};
       /*  table.setFooterRows(5);*/
       Font font1 = new Font(Font.FontFamily.COURIER  , 14, Font.NORMAL);
       Font font2 = new Font(Font.FontFamily.COURIER  , 14, Font.BOLD);
       
    try {
        table.setWidths(columnWidths);
        
    } catch (DocumentException ex) {
        Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
    }
            PdfPCell cell1 = new PdfPCell(new Paragraph("EntryDate",font2));
             PdfPCell cell2 = new PdfPCell(new Paragraph("ValueDate",font2));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Description",font2));
            PdfPCell cell4 = new PdfPCell(new Paragraph("Debits",font2));
            PdfPCell cell5 = new PdfPCell(new Paragraph("Credits",font2));
            PdfPCell cell6 = new PdfPCell(new Paragraph("Balance",font2)); 
            
           cell1.setBackgroundColor(BaseColor.CYAN);   //sets BG color to yellow.
           cell2.setBackgroundColor(BaseColor.CYAN); 
           cell3.setBackgroundColor(BaseColor.CYAN); 
           cell4.setBackgroundColor(BaseColor.CYAN); 
           cell5.setBackgroundColor(BaseColor.CYAN); 
        cell6.setBackgroundColor(BaseColor.CYAN); 
             

           cell1.setBorderWidth (2f);         // sets border width to 3 units
           cell2.setBorderWidth (2f);
           cell3.setBorderWidth (2f);
           cell4.setBorderWidth (2f);
           cell5.setBorderWidth (2f);
           cell6.setBorderWidth (2f);
            
        
           
           
//           cell1 .disableBorderSide(LEFT);         // sets border width to 3 units
//           cell2 .disableBorderSide(LEFT);
//           cell3 .disableBorderSide(LEFT);
//           cell4 .disableBorderSide(LEFT);
//           cell5 .disableBorderSide(LEFT);
       
//               cell6 .disableBorderSide(LEFT);
//           
//           cell1 .disableBorderSide(TOP);       // sets border width to 3 units
//           cell2.disableBorderSide(TOP); 
//           cell3.disableBorderSide(TOP); 
//           cell4.disableBorderSide(TOP); 
//           cell5.disableBorderSide(TOP); 
//          cell6.disableBorderSide(TOP);
//           
//           cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
//           cell2.disableBorderSide(RIGHT); 
//           cell3.disableBorderSide(RIGHT); 
//           cell4.disableBorderSide(RIGHT); 
//           cell5.disableBorderSide(RIGHT); 
//           cell6.disableBorderSide(RIGHT); 
      
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
        table.addCell(cell6);
    
          int n=0; Map<Integer, List<Object>> dataBase=rdb.statementEntries(accountNumber, StartDate, EndDate);
           if(!dataBase.isEmpty()){
           while(n<dataBase.size()) {
           List realData=dataBase.get(n);int z=0;
           if(n==0){
            while(z<realData.size()){
           if(z==0){
               PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(),font1));
             cellx1.setFixedHeight(48.2f);
//               cellx1.disableBorderSide(LEFT);
//             cellx1.disableBorderSide(TOP);
//               cellx1.disableBorderSide(RIGHT);
             cellx1.setPadding(.5f);
               cellx1.setBorderColorBottom(BaseColor.BLACK);
             cellx1.setBorderColorTop(BaseColor.WHITE);
             cellx1.setBorderColorLeft(BaseColor.WHITE);
             cellx1.setBorderColorRight(BaseColor.WHITE);
             cellx1.setVerticalAlignment(Element.ALIGN_TOP);
               table.addCell(cellx1);
           }
                if(z==1){
               PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(1).toString(),font1));
               cellx1.setFixedHeight(48.2f);
//               cellx1.disableBorderSide(LEFT);
//             cellx1.disableBorderSide(TOP);
//               cellx1.disableBorderSide(RIGHT);
             cellx1.setPadding(.5f);
               cellx1.setBorderColorBottom(BaseColor.BLACK);
             cellx1.setBorderColorTop(BaseColor.WHITE);
             cellx1.setBorderColorLeft(BaseColor.WHITE);
             cellx1.setBorderColorRight(BaseColor.WHITE);
             cellx1.setVerticalAlignment(Element.ALIGN_TOP);
               table.addCell(cellx1);
           }
           if(z==2){
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(2).toString(),font1));
//           cellx2.disableBorderSide(LEFT);
//         cellx2.disableBorderSide(TOP);
//         cellx2.disableBorderSide(RIGHT);
               cellx2.setBorderColorBottom(BaseColor.BLACK);
             cellx2.setBorderColorTop(BaseColor.WHITE);
             cellx2.setBorderColorLeft(BaseColor.WHITE);
             cellx2.setBorderColorRight(BaseColor.WHITE);
                cellx2.setFixedHeight(48.2f);
              cellx2.setPadding(.5f);
              cellx2.setVerticalAlignment(Element.ALIGN_TOP);
            table.addCell(cellx2);
            
           }
          
            
             if(z==3){
        PdfPCell cellx4=null;
    
        cellx4 = new PdfPCell(new Paragraph(realData.get(3).toString(),font1));
                cellx4.setFixedHeight(48.2f);
                 cellx4.setPadding(.5f);
//                   cellx4.disableBorderSide(LEFT);
//         cellx4.disableBorderSide(TOP);
//         cellx4.disableBorderSide(RIGHT);
                cellx4.setBorderColorBottom(BaseColor.BLACK);
             cellx4.setBorderColorTop(BaseColor.WHITE);
             cellx4.setBorderColorLeft(BaseColor.WHITE);
             cellx4.setBorderColorRight(BaseColor.WHITE);
              cellx4.setVerticalAlignment(Element.ALIGN_TOP);
            table.addCell(cellx4);
             }
              if(z==4){
                 
                  
                  PdfPCell cellx5=null;
 
        cellx5 = new PdfPCell(new Paragraph(realData.get(4).toString(),font1));
        
        
            cellx5.setFixedHeight(48.2f);
            cellx5.setPadding(.5f);
//            cellx5.disableBorderSide(LEFT);
//         cellx5.disableBorderSide(TOP);
//         cellx5.disableBorderSide(RIGHT);
             cellx5.setBorderColorBottom(BaseColor.BLACK);
             cellx5.setBorderColorTop(BaseColor.WHITE);
             cellx5.setBorderColorLeft(BaseColor.WHITE);
             cellx5.setBorderColorRight(BaseColor.WHITE);
              cellx5.setVerticalAlignment(Element.ALIGN_TOP);
            table.addCell(cellx5 );
              }
                if(z==5){
                  PdfPCell cellx6=null;
           if(realData.get(5).toString().equalsIgnoreCase("-")){
        cellx6 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+"",font1));
        } else  if(parseDouble(realData.get(5).toString())<0.0){
       cellx6 = new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+")",font1));
        }else if(parseDouble(realData.get(5).toString())>=0.0){
        
        cellx6 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+"",font1));
        }
        
           
             cellx6.setFixedHeight(48.2f);
//        cellx6.disableBorderSide(LEFT);
//         cellx6.disableBorderSide(TOP);
//         cellx6.disableBorderSide(RIGHT);
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
            
           }
           if(n==dataBase.size()-1){
            while(z<realData.size()){
           if(z==0){
               PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(),font1));
             cellx1.setFixedHeight(48.2f);
//               cellx1.disableBorderSide(LEFT);
//             cellx1.disableBorderSide(TOP);
//               cellx1.disableBorderSide(RIGHT);
             cellx1.setPadding(.5f);
               cellx1.setBorderColorBottom(BaseColor.BLACK);
             cellx1.setBorderColorTop(BaseColor.WHITE);
             cellx1.setBorderColorLeft(BaseColor.WHITE);
             cellx1.setBorderColorRight(BaseColor.WHITE);
             cellx1.setVerticalAlignment(Element.ALIGN_TOP);
               table.addCell(cellx1);
           }
               if(z==1){
               PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(1).toString(),font1));
             cellx1.setFixedHeight(48.2f);
//               cellx1.disableBorderSide(LEFT);
//             cellx1.disableBorderSide(TOP);
//               cellx1.disableBorderSide(RIGHT);
             cellx1.setPadding(.5f);
               cellx1.setBorderColorBottom(BaseColor.BLACK);
             cellx1.setBorderColorTop(BaseColor.WHITE);
             cellx1.setBorderColorLeft(BaseColor.WHITE);
             cellx1.setBorderColorRight(BaseColor.WHITE);
             cellx1.setVerticalAlignment(Element.ALIGN_TOP);
               table.addCell(cellx1);
           }
           if(z==2){
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(2).toString(),font1));
//           cellx2.disableBorderSide(LEFT);
//         cellx2.disableBorderSide(TOP);
//         cellx2.disableBorderSide(RIGHT);
               cellx2.setBorderColorBottom(BaseColor.BLACK);
             cellx2.setBorderColorTop(BaseColor.WHITE);
             cellx2.setBorderColorLeft(BaseColor.WHITE);
             cellx2.setBorderColorRight(BaseColor.WHITE);
              cellx2.setFixedHeight(48.2f);
              cellx2.setPadding(.5f);
              cellx2.setVerticalAlignment(Element.ALIGN_TOP);
            table.addCell(cellx2);
            
           }
          
            
             if(z==3){
        PdfPCell cellx4=null;
    
        cellx4 = new PdfPCell(new Paragraph(realData.get(3).toString(),font1));
                cellx4.setFixedHeight(48.2f);
                 cellx4.setPadding(.5f);
//                   cellx4.disableBorderSide(LEFT);
//         cellx4.disableBorderSide(TOP);
//         cellx4.disableBorderSide(RIGHT);
                cellx4.setBorderColorBottom(BaseColor.BLACK);
             cellx4.setBorderColorTop(BaseColor.WHITE);
             cellx4.setBorderColorLeft(BaseColor.WHITE);
             cellx4.setBorderColorRight(BaseColor.WHITE);
              cellx4.setVerticalAlignment(Element.ALIGN_TOP);
            table.addCell(cellx4);
             }
              if(z==4){
                 
                  
                  PdfPCell cellx5=null;
 
        cellx5 = new PdfPCell(new Paragraph(realData.get(4).toString(),font1));
        
        
           cellx5.setFixedHeight(48.2f);
            cellx5.setPadding(.5f);
//            cellx5.disableBorderSide(LEFT);
//         cellx5.disableBorderSide(TOP);
//         cellx5.disableBorderSide(RIGHT);
             cellx5.setBorderColorBottom(BaseColor.BLACK);
             cellx5.setBorderColorTop(BaseColor.WHITE);
             cellx5.setBorderColorLeft(BaseColor.WHITE);
             cellx5.setBorderColorRight(BaseColor.WHITE);
              cellx5.setVerticalAlignment(Element.ALIGN_TOP);
            table.addCell(cellx5 );
              }
                if(z==5){
                  PdfPCell cellx6=null;
           if(realData.get(5).toString().equalsIgnoreCase("-")){
        cellx6 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+"",font1));
        } else  if(parseDouble(realData.get(5).toString())<0.0){
       cellx6 = new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+")",font1));
        }else if(parseDouble(realData.get(5).toString())>=0.0){
        
        cellx6 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+"",font1));
        }
        
           
             cellx6.setFixedHeight(48.2f);
//        cellx6.disableBorderSide(LEFT);
//         cellx6.disableBorderSide(TOP);
//         cellx6.disableBorderSide(RIGHT);
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
           
           } 
           while(z<realData.size()){
           if(z==0){
               PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(),font1));
             cellx1.setFixedHeight(48.2f);
//               cellx1.disableBorderSide(LEFT);
//             cellx1.disableBorderSide(TOP);
//               cellx1.disableBorderSide(RIGHT);
             cellx1.setPadding(.5f);
               cellx1.setBorderColorBottom(BaseColor.BLACK);
             cellx1.setBorderColorTop(BaseColor.BLACK);
             cellx1.setBorderColorLeft(BaseColor.BLACK);
             cellx1.setBorderColorRight(BaseColor.BLACK);
             cellx1.setVerticalAlignment(Element.ALIGN_TOP);
             
               table.addCell(cellx1);
           }
              if(z==1){
               PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(1).toString(),font1));
             cellx1.setFixedHeight(48.2f);
//               cellx1.disableBorderSide(LEFT);
//             cellx1.disableBorderSide(TOP);
//               cellx1.disableBorderSide(RIGHT);
             cellx1.setPadding(.5f);
               cellx1.setBorderColorBottom(BaseColor.BLACK);
             cellx1.setBorderColorTop(BaseColor.BLACK);
             cellx1.setBorderColorLeft(BaseColor.BLACK);
             cellx1.setBorderColorRight(BaseColor.BLACK);
             cellx1.setVerticalAlignment(Element.ALIGN_TOP);
               table.addCell(cellx1);
           }
           if(z==2){
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(2).toString(),font1));
//           cellx2.disableBorderSide(LEFT);
//         cellx2.disableBorderSide(TOP);
//         cellx2.disableBorderSide(RIGHT);
               cellx2.setBorderColorBottom(BaseColor.BLACK);
             cellx2.setBorderColorTop(BaseColor.BLACK);
             cellx2.setBorderColorLeft(BaseColor.BLACK);
             cellx2.setBorderColorRight(BaseColor.BLACK);
              cellx2.setFixedHeight(48.2f);
              cellx2.setPadding(.5f);
              cellx2.setVerticalAlignment(Element.ALIGN_TOP);
            table.addCell(cellx2);
            
           }
          
            
             if(z==3){
        PdfPCell cellx4=null;
        if(realData.get(3).toString().equalsIgnoreCase("-")){
        cellx4 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(3).toString().replaceAll(",", ""))+"",font1));
        }else if(parseDouble(realData.get(3).toString().replaceAll(",", ""))<0.0){
       cellx4 = new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(realData.get(3).toString().replaceAll(",", ""))+")",font1));
        }else if(parseDouble(realData.get(3).toString().replaceAll(",", ""))>=0.0){
        
        cellx4 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(3).toString().replaceAll(",", ""))+"",font1));
        }
                cellx4.setFixedHeight(48.2f);
                 cellx4.setPadding(.5f);
//                   cellx4.disableBorderSide(LEFT);
//         cellx4.disableBorderSide(TOP);
//         cellx4.disableBorderSide(RIGHT);
                cellx4.setBorderColorBottom(BaseColor.BLACK);
             cellx4.setBorderColorTop(BaseColor.BLACK);
             cellx4.setBorderColorLeft(BaseColor.BLACK);
             cellx4.setBorderColorRight(BaseColor.BLACK);
              cellx4.setVerticalAlignment(Element.ALIGN_TOP);
            table.addCell(cellx4);
             }
              if(z==4){
                 
                  
                  PdfPCell cellx5=null;
          if(realData.get(4).toString().equalsIgnoreCase("-")){
        cellx5 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(4).toString().replaceAll(",", ""))+"",font1));
        }  else if(parseDouble(realData.get(4).toString().replaceAll(",", ""))<0.0){
       cellx5 = new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(realData.get(4).toString().replaceAll(",", ""))+")",font1));
        }else if(parseDouble(realData.get(4).toString().replaceAll(",", ""))>=0.0){
        
        cellx5 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(4).toString().replaceAll(",", ""))+"",font1));
        }
        
           cellx5.setFixedHeight(48.2f);
            cellx5.setPadding(.5f);
//            cellx5.disableBorderSide(LEFT);
//         cellx5.disableBorderSide(TOP);
//         cellx5.disableBorderSide(RIGHT);
             cellx5.setBorderColorBottom(BaseColor.BLACK);
             cellx5.setBorderColorTop(BaseColor.BLACK);
             cellx5.setBorderColorLeft(BaseColor.BLACK);
             cellx5.setBorderColorRight(BaseColor.BLACK);
              cellx5.setVerticalAlignment(Element.ALIGN_TOP);
            table.addCell(cellx5 );
              }
                if(z==5){
                  PdfPCell cellx6=null;
           if(realData.get(5).toString().equalsIgnoreCase("-")){
        cellx6 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+"",font1));
        } else  if(parseDouble(realData.get(5).toString())<0.0){
       cellx6 = new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+")",font1));
        }else if(parseDouble(realData.get(5).toString())>=0.0){
        
        cellx6 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+"",font1));
        }
        
           
             cellx6.setFixedHeight(48.2f);
//        cellx6.disableBorderSide(LEFT);
//         cellx6.disableBorderSide(TOP);
//         cellx6.disableBorderSide(RIGHT);
            cellx6.setPadding(.5f);
            cellx6.setBorderColorBottom(BaseColor.BLACK);
             cellx6.setBorderColorTop(BaseColor.BLACK);
             cellx6.setBorderColorLeft(BaseColor.BLACK);
             cellx6.setBorderColorRight(BaseColor.BLACK);
              cellx6.setVerticalAlignment(Element.ALIGN_TOP);
            table.addCell(cellx6); 
                }
           z++;   
           } 
      
           n++;
           } 
           }else{
           table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database",font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
           table.addCell(new PdfPCell(new Paragraph("N/A"+"",font1)));
           table.addCell(new PdfPCell(new Paragraph("N/A"+"",font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A",font1))); 
           }
      return table;

       }
       
      
      public boolean createStatementAmount(String accountNumber, String StartDate, String EndDate,String fileName,String amount) { 
     
  boolean Statement=false;
     
  
  if(fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
  if(fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
createActuallyStatementAmount(accountNumber,  StartDate,  EndDate,fileName, amount);
Statement=true;
  } 
    
  }else{
createActuallyStatementAmount(accountNumber,  StartDate,  EndDate,fileName, amount);
Statement=true;


  }
      
      return Statement;
       }
    private void createActuallyStatementAmount(String accountNumber, String StartDate, String EndDate,String fileName2,String amount){   
      
      String fileName="",accountName="";
      
  accountName=rdb.selectAccountName(accountNumber);
  
      try {
          String file="customer_Statement";
          
          Paragraph headerz =createHeading(accountNumber,accountName,StartDate,EndDate);
          
          headerz.setIndentationLeft(50);
          
          headerz.setIndentationRight(50);
         
          
          
          Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
          
          PdfWriter writer=PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", file+".pdf")));
          PdfPTable body= createCustomerTableAmount(accountNumber, StartDate,  EndDate,amount);
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
          
          String name = accountName;
          String[] words = name.split("\\s");
          Image image1 = Image.getInstance("strawberry3.jpg");
          image1.setAbsolutePosition(49f, 1080f);
          image1.scaleAbsolute(43f, 43f);
          document.add(image1);
          document.add(headerz );
          document.add(new Paragraph("  ") );
          Paragraph currency= new Paragraph("CURRENCY UGANDA SHILLINGS",font1);
          currency.setIndentationLeft(280f);
          currency.setIndentationRight(30f);
          document.add( currency);
          document.add(new Paragraph("  ") );
          document.add( body);
          document.add(new Paragraph("  ") );
          document.add(new Paragraph("  ") );
           document.add(new Paragraph("  ") );
    document.add(new Paragraph("  ") );
    document.add(this.createFooter());
            document.newPage();
          document.close();
          fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", file+".pdf")).close();
          fileName =file; 
         
      } catch (BadElementException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException | DocumentException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      }
     
       }
       
      public  PdfPTable createCustomerTableAmount(String accountNumber, String StartDate, String EndDate,String amont){
       PdfPTable table = new PdfPTable(6);
       table.setWidthPercentage(100);
       float[] columnWidths = {30f,30f, 80f,45f, 45f, 60f};
       /*  table.setFooterRows(5);*/
       Font font1 = new Font(Font.FontFamily.COURIER  , 14, Font.NORMAL);
       Font font2 = new Font(Font.FontFamily.COURIER  , 14, Font.BOLD);
       
    try {
        table.setWidths(columnWidths);
        
    } catch (DocumentException ex) {
        Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
    }
            PdfPCell cell1 = new PdfPCell(new Paragraph("EntryDate",font2));
            PdfPCell cell2 = new PdfPCell(new Paragraph("ValueDate",font2));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Description",font2));
            PdfPCell cell4 = new PdfPCell(new Paragraph("Debits",font2));
            PdfPCell cell5 = new PdfPCell(new Paragraph("Credits",font2));
            PdfPCell cell6 = new PdfPCell(new Paragraph("Balance",font2)); 
            
           cell1.setBackgroundColor(BaseColor.CYAN);   //sets BG color to yellow.
           cell2.setBackgroundColor(BaseColor.CYAN); 
           cell3.setBackgroundColor(BaseColor.CYAN); 
           cell4.setBackgroundColor(BaseColor.CYAN); 
           cell5.setBackgroundColor(BaseColor.CYAN); 
           cell6.setBackgroundColor(BaseColor.CYAN); 
             

           cell1.setBorderWidth (2f);         // sets border width to 3 units
           cell2.setBorderWidth (2f);
           cell3.setBorderWidth (2f);
           cell4.setBorderWidth (2f);
           cell5.setBorderWidth (2f);
          cell6.setBorderWidth (2f);
            
        
           
           
           cell1 .disableBorderSide(LEFT);         // sets border width to 3 units
           cell2 .disableBorderSide(LEFT);
           cell3 .disableBorderSide(LEFT);
           cell4 .disableBorderSide(LEFT);
           cell5 .disableBorderSide(LEFT);
        cell6 .disableBorderSide(LEFT);
           
           
           cell1 .disableBorderSide(TOP);       // sets border width to 3 units
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
    
          int n=0; Map<Integer, List<Object>> dataBase=rdb.statementEntriesAmount(accountNumber, StartDate, EndDate,amont);
          
           if(!dataBase.isEmpty()){
           while(n<dataBase.size()) {
           List realData=dataBase.get(n);int z=0;
           if(n==0){
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
               PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(1).toString(),font1));
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
           if(z==2){
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(2).toString(),font1));
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
          
            
             if(z==3){
        PdfPCell cellx4=null;
    
        cellx4 = new PdfPCell(new Paragraph(realData.get(3).toString(),font1));
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
                 
                  
                  PdfPCell cellx5=null;
 
        cellx5 = new PdfPCell(new Paragraph(realData.get(4).toString(),font1));
        
        
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
                  PdfPCell cellx6=null;
           if(realData.get(5).toString().equalsIgnoreCase("-")){
        cellx6 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+"",font1));
        } else  if(parseDouble(realData.get(5).toString())<0.0){
       cellx6 = new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+")",font1));
        }else if(parseDouble(realData.get(5).toString())>=0.0){
        
        cellx6 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+"",font1));
        }
        
           
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
            
           }
           if(n==dataBase.size()-1){
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
               PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(1).toString(),font1));
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
           if(z==2){
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(2).toString(),font1));
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
          
            
             if(z==3){
        PdfPCell cellx4=null;
    
        cellx4 = new PdfPCell(new Paragraph(realData.get(3).toString(),font1));
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
                 
                  
                  PdfPCell cellx5=null;
 
        cellx5 = new PdfPCell(new Paragraph(realData.get(4).toString(),font1));
        
        
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
                  PdfPCell cellx6=null;
           if(realData.get(5).toString().equalsIgnoreCase("-")){
        cellx6 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+"",font1));
        } else  if(parseDouble(realData.get(5).toString())<0.0){
       cellx6 = new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+")",font1));
        }else if(parseDouble(realData.get(5).toString())>=0.0){
        
        cellx6 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+"",font1));
        }
        
           
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
           }} 
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
           } if(z==1){
               PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(1).toString(),font1));
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
           if(z==2){
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(2).toString(),font1));
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
          
            
             if(z==3){
        PdfPCell cellx4=null;
        if(realData.get(3).toString().equalsIgnoreCase("-")){
        cellx4 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(3).toString().replaceAll(",", ""))+"",font1));
        }else if(parseDouble(realData.get(3).toString().replaceAll(",", ""))<0.0){
       cellx4 = new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(realData.get(3).toString().replaceAll(",", ""))+")",font1));
        }else if(parseDouble(realData.get(3).toString().replaceAll(",", ""))>=0.0){
        
        cellx4 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(3).toString().replaceAll(",", ""))+"",font1));
        }
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
                 
                  
                  PdfPCell cellx5=null;
          if(realData.get(4).toString().equalsIgnoreCase("-")){
        cellx5 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(4).toString().replaceAll(",", ""))+"",font1));
        }  else if(parseDouble(realData.get(4).toString().replaceAll(",", ""))<0.0){
       cellx5 = new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(realData.get(4).toString().replaceAll(",", ""))+")",font1));
        }else if(parseDouble(realData.get(4).toString().replaceAll(",", ""))>=0.0){
        
        cellx5 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(4).toString().replaceAll(",", ""))+"",font1));
        }
        
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
                  PdfPCell cellx6=null;
           if(realData.get(5).toString().equalsIgnoreCase("-")){
        cellx6 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+"",font1));
        } else  if(parseDouble(realData.get(5).toString())<0.0){
       cellx6 = new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+")",font1));
        }else if(parseDouble(realData.get(5).toString())>=0.0){
        
        cellx6 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+"",font1));
        }
        
           
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
      
           n++;
           } 
           }else{
           table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database",font1)));
             table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
           table.addCell(new PdfPCell(new Paragraph("N/A"+"",font1)));
           table.addCell(new PdfPCell(new Paragraph("N/A"+"",font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A",font1))); 
           }
      return table;
      

       }   
      
       public  List<List> createExcelCustomerTableAmnt(String accountNumber, String StartDate, String EndDate,String amont){
         List<List> custTable=new ArrayList();
         List title= new ArrayList();
           title.add(" ");
           title.add("CUSTOMER STATEMENT  BETWEEN"+" "+ffm.fromDatabaseWithDashSeperatorBeginningWithYear(StartDate)+"  AND  "+ffm.fromDatabaseWithDashSeperatorBeginningWithYear(EndDate));
        custTable.add(title);
         
         
         List realTable=new ArrayList();
    
    realTable.add("EntryDate");
    realTable.add("ValueDate");
    realTable.add("Description");
    realTable.add("Debits");
    realTable.add("Credits");
    realTable.add("Balance");
    custTable.add(realTable);
            
          int n=0; Map<Integer, List<Object>> dataBase=rdb.statementEntriesAmount(accountNumber, StartDate, EndDate,amont);
           if(!dataBase.isEmpty()){
           while(n<dataBase.size()) {
           List realData=dataBase.get(n);int z=0;
           if(n==0){
                List dat1=new ArrayList();
            while(z<realData.size()){
           if(z==0){
             dat1.add(realData.get(0).toString());
           
           }
            if(z==1){
              dat1.add(realData.get(1).toString());
           
           }
           if(z==2){
         
             dat1.add(realData.get(2).toString());
          
            
           }
          
            
             if(z==3){
      
       dat1.add(realData.get(3).toString());
             
             }
              if(z==4){
         dat1.add(realData.get(4).toString());
     
              }
                if(z==5){
          
           if(realData.get(5).toString().equalsIgnoreCase("-")){
          dat1.add(ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+"");
        } else  if(parseDouble(realData.get(5).toString())<0.0){
         dat1.add("("+ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+")");
        }else if(parseDouble(realData.get(5).toString())>=0.0){
        
         dat1.add(ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+"");
        }
     
                }
           z++;   
           } 
          custTable.add(dat1);   
           }
           if(n==dataBase.size()-1){
                   List dat2=new ArrayList();
            while(z<realData.size()){
           if(z==0){
              dat2.add(realData.get(0).toString());
        
           }
               if(z==1){
               dat2.add(realData.get(1).toString());
           }
           if(z==2){
          dat2.add(realData.get(2).toString());
            
           }
          
            
             if(z==3){
       dat2.add(realData.get(3).toString());
             }
              if(z==4){
            dat2.add(realData.get(4).toString());
              }
                if(z==5){
             
           if(realData.get(5).toString().equalsIgnoreCase("-")){
     dat2.add(ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+"");
        } else  if(parseDouble(realData.get(5).toString())<0.0){
      dat2.add("("+ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+")");
        }else if(parseDouble(realData.get(5).toString())>=0.0){
       dat2.add(ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+"");
        }
       
                }
           z++;   
           }
          custTable.add(dat2);    
           } 
           
            List dat3=new ArrayList();
           while(z<realData.size()){
           if(z==0){
           dat3.add(realData.get(0).toString());
           } if(z==1){
             dat3.add(realData.get(1).toString());
           }
           if(z==2){
         
    dat3.add(realData.get(2).toString());
            
           }
          
            
             if(z==3){
             if(realData.get(3).toString().equalsIgnoreCase("-")){
     dat3.add(ffm.formatForStatementNumbers(realData.get(3).toString().replaceAll(",", ""))+"");
        } else  if(parseDouble(realData.get(3).toString())<0.0){
      dat3.add("("+ffm.formatForStatementNumbers(realData.get(3).toString().replaceAll(",", ""))+")");
        }else if(parseDouble(realData.get(3).toString())>=0.0){
       dat3.add(ffm.formatForStatementNumbers(realData.get(3).toString().replaceAll(",", ""))+"");
        }
        
           
             }
              if(z==4){
                 
                  
                 if(realData.get(4).toString().equalsIgnoreCase("-")){
     dat3.add(ffm.formatForStatementNumbers(realData.get(4).toString().replaceAll(",", ""))+"");
        } else  if(parseDouble(realData.get(4).toString())<0.0){
      dat3.add("("+ffm.formatForStatementNumbers(realData.get(4).toString().replaceAll(",", ""))+")");
        }else if(parseDouble(realData.get(4).toString())>=0.0){
       dat3.add(ffm.formatForStatementNumbers(realData.get(4).toString().replaceAll(",", ""))+"");
        }
              }
                if(z==5){
                               
                 if(realData.get(5).toString().equalsIgnoreCase("-")){
     dat3.add(ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+"");
        } else  if(parseDouble(realData.get(5).toString())<0.0){
      dat3.add("("+ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+")");
        }else if(parseDouble(realData.get(5).toString())>=0.0){
       dat3.add(ffm.formatForStatementNumbers(realData.get(5).toString().replaceAll(",", ""))+"");
        }
        }
        
                
           z++;   
           } 
        custTable.add(dat3); 
           n++;
           } 
           }else{
                  List data4=new ArrayList();
          data4.add("N/A");
          data4.add("N/A");
         data4.add("Empty selection from database");
         data4.add("N/A");
         data4.add("N/A"+"");
         data4.add("N/A"+"");
          data4.add("N/A"); 
             custTable.add(data4); 
           }
      return custTable;
      

       }   
//      public  Paragraph createFooter(String accountNumber3){
//       Paragraph s=null;
//       Font font1 = new Font(Font.FontFamily.COURIER  , 10, Font.BOLDITALIC);
//  s= new Paragraph("POWERED BY: BAZIRAKE AUGUSTINE GOOGO, MOBILE: 0782231039, EMAIL: augbazi@gmail.com",font1);
//   s.setAlignment(Element.ALIGN_CENTER);
//       return s;
//       }

private Paragraph createHeading(String accountNumber2,String accountName3,String startdate,String enddate) {
  Paragraph ss=null;
  
  Font font1 = new Font(Font.FontFamily.COURIER  , 14, Font.NORMAL);
  ss= new Paragraph("STATEMENT OF ACCOUNT"+"\n"+"FOR ACCOUNT NUMBER:"+" "+accountNumber2+"\n"+accountName3+"\n"+"FROM"+" "+ffm.fromDatabaseWithDashSeperatorBeginningWithYear(startdate)+" "+"TO"+" "+ffm.fromDatabaseWithDashSeperatorBeginningWithYear(enddate),font1);
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
                    ( document.right()-document.left())/2+document.leftMargin(), document.bottom()+1, 0);
      
    }
    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
            Font font1 = new Font(Font.FontFamily.COURIER  , 10, Font.BOLDITALIC);
        ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Phrase("POWERED BY: BAZIRAKE AUGUSTINE GOOGO, MOBILE: 0782231039, EMAIL: augbazi@gmail.com",font1),
                    ( document.right()-document.left())/2+(document.leftMargin()), document.bottom()-10, 0);
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
