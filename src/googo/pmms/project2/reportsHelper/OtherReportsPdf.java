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
import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.frameHelper.MyTableModel;
import googo.pmms.project2.frames.PostingEntryWindow;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databaseConnectors.JdbcConnector;
import googo.pmms.project2.frameHelper.ListDataModel;
import googo.pmms.project2.frameHelper.ListDataModelDailyCollect;
import googo.pmms.project2.frameHelper.ListDataModelDailyCollect1;
import googo.pmms.project2.frameHelper.ListDataModelLoanRegister;
import googo.pmms.project2.frameHelper.ListDataModelPortfolioReport;
import googo.pmms.project2.frameHelper.ListDataModelPortfolioReport1;
import googo.pmms.project2.frameHelper.ListTableModel;
import googo.pmms.project2.frameHelper.ReportsModelData;
import java.awt.Component;
import java.io.IOException;
import java.text.DecimalFormat;
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
import javax.swing.JTextField;
import static javax.swing.SwingConstants.BOTTOM;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Stanchart
 */
public class OtherReportsPdf implements PdfPageEvent {
      
//    Amortization amortize = new Amortization();

     Calendar calN = Calendar.getInstance();
  SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
fileInputOutPutStreams fios= new fileInputOutPutStreams();
    Formartter ffm= new Formartter();
    Date Trndate,valuedate;
   ArrayList<String> data4, column1;
 ArrayList<ArrayList<String>> data5;
          JOptionPane p;
     MyTableModel model;ReportsModelData model1;
    Date date;
  SimpleDateFormat df;
  String text;
  int realMonth, otherMonth;
   String dates, dates2,getFieldValue,actualFieldValue,  jTFuserId1mt,today,thistime,today1,newDate1,jTFuserId1mt1,newDate11,today2;
   Integer Value,Value1;
   double newbalance,ledgerBalance,creditamount;
    GregorianCalendar cal = new GregorianCalendar(); 
      JdbcConnector csx = new JdbcConnector(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "AccountDBDetails.txt"))); 
    DatabaseQuaries quaries =new DatabaseQuaries();
ReportsDatabase rdb = new ReportsDatabase();
 int   pagenumber=0;
   DecimalFormat f =new DecimalFormat("#");
  String userId;
   SimpleDateFormat sdfS =new SimpleDateFormat("yyyy-MM-dd");
//    SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
      ListDataModel model1x;
public void setUserID(String userid){
this.userId=userid;
}
  public boolean createStaffMembers(String fileName) { 
  boolean StaffMembers=false;
     
  
  if(fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
  if(fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
   createActuallyStaffMembers(fileName);
  StaffMembers=true;
  } 
    
  }else{
  createActuallyStaffMembers(fileName);
  StaffMembers=true;
  }
      
      return StaffMembers;
       }
  
      private void createActuallyStaffMembers(String file){
       
  
  try {
         
          
          Paragraph headerz =createHeading("staff","","");
          headerz.setIndentationLeft(50);
          headerz.setIndentationRight(50);
         
          
          
          Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
          PdfWriter writer=PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", file+".pdf")));
          PdfPTable body= createTheStaffMembers();
      
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
          document.add(headerz );
          document.add(new Paragraph("  ") );
          Paragraph currency= new Paragraph("",font1);
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
         
         
      } catch (BadElementException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException | DocumentException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      }
  } 
       
          
      
      public  PdfPTable createTheStaffMembers(){
       PdfPTable table = new PdfPTable(7);
       table.setWidthPercentage(100);
       float[] columnWidths = {35f, 30f, 30f,25f, 40f, 15f,50f};
      
       Font font1 = new Font(Font.FontFamily.COURIER  , 14, Font.NORMAL);
       Font font2 = new Font(Font.FontFamily.COURIER  , 14, Font.BOLD);
       
    try {
        table.setWidths(columnWidths);
        
    } catch (DocumentException ex) {
        Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
    }
            PdfPCell cell1 = new PdfPCell(new Paragraph("Title",font2));
            PdfPCell cell2 = new PdfPCell(new Paragraph("First Name",font2));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Sur Name",font2));
            PdfPCell cell4 = new PdfPCell(new Paragraph("Experience",font2));
            PdfPCell cell5 = new PdfPCell(new Paragraph("Role",font2));
            PdfPCell cell6 = new PdfPCell(new Paragraph("User ID",font2)); 
            PdfPCell cell7 = new PdfPCell(new Paragraph("Line Manager",font2)); 
           cell1.setBackgroundColor(BaseColor.CYAN);   //sets BG color to yellow.
           cell2.setBackgroundColor(BaseColor.CYAN); 
           cell3.setBackgroundColor(BaseColor.CYAN); 
           cell4.setBackgroundColor(BaseColor.CYAN); 
           cell5.setBackgroundColor(BaseColor.CYAN); 
           cell6.setBackgroundColor(BaseColor.CYAN);
            cell7.setBackgroundColor(BaseColor.CYAN); 

           cell1.setBorderWidth (2f);         // sets border width to 3 units
           cell2.setBorderWidth (2f);
           cell3.setBorderWidth (2f);
           cell4.setBorderWidth (2f);
           cell5.setBorderWidth (2f);
           cell6.setBorderWidth (2f);
            cell7.setBorderWidth (2f);
           cell6.setBorderColorBottom(BaseColor.BLACK);
           
           
           cell1 .disableBorderSide(LEFT);         // sets border width to 3 units
           cell2 .disableBorderSide(LEFT);
           cell3 .disableBorderSide(LEFT);
           cell4 .disableBorderSide(LEFT);
           cell5 .disableBorderSide(LEFT);
           cell6 .disableBorderSide(LEFT);
           cell7 .disableBorderSide(LEFT);
           
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
          int n=0; Map<Integer, List<Object>> dataBase=rdb.staffMembers();
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
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(1).toString(),font1));
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
         
            PdfPCell cellx3 = new PdfPCell(new Paragraph(realData.get(2).toString(),font1));
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

            PdfPCell cellx4 = new PdfPCell(new Paragraph(realData.get(3).toString(),font1));
         
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
          
            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(6).toString(),font1)); 
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
           table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A",font1))); 
            table.addCell(new PdfPCell(new Paragraph("N/A",font1))); 
           }
      return table;

       }
       public ListDataModel provisionedItems(List dates){
 
      List<String> tableHeaders=new ArrayList();
      tableHeaders.add("S/n");
      tableHeaders.add("Date");
    tableHeaders.add("Name");
    tableHeaders.add("Range");
    tableHeaders.add("Percentage");
    tableHeaders.add("Day Arrears");
    tableHeaders.add("O/S Principal");
    tableHeaders.add("Amount Provisioned");
     
     List< List<Object>> dataBody=new ArrayList();
     int n=0; Map<Integer, List<Object>> dataBase=rdb. provisionedItems(ffm.forDatabaseWithFullYearBeginningWithDate(dates.get(0).toString()),ffm.forDatabaseWithFullYearBeginningWithDate(dates.get(1).toString()));
    if(!dataBase.isEmpty()){
    while(n<dataBase.size()) {
    List realData=dataBase.get(n);int z=0;
    if(n==dataBase.size()-1){
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
 data1.add(realData.get(5).toString());
   
    }
     if(z==6){
 data1.add(ffm.formatForStatementNumbers(realData.get(6).toString()));
   
    }
  if(z==7){
 data1.add(ffm.formatForStatementNumbers(realData.get(7).toString()));
   
    }
    z++;   
    }
dataBody.add(data1);
    }else{
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
data2.add(realData.get(5).toString()); 
  
    }
     if(z==6){
data2.add(ffm.formatForStatementNumbers(realData.get(6).toString())); 
  
    }
        if(z==7){
data2.add(ffm.formatForStatementNumbers(realData.get(7).toString())); 
  
    }

    z++;   
    } 
    dataBody.add(data2);
    }

    n++;
    } 
    }else{
          List data3=new ArrayList();
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
                            model1x= new ListDataModel(dataBody, tableHeaders);
                           

                       return model1x;
     
     }
       
    
            public ListDataModelDailyCollect dailyTotalCollection(List dates, Component c) {

            ListDataModelDailyCollect statusModel = null;


            List<String> tableHeaders = new ArrayList();
            
            tableHeaders.add("S/n");
            tableHeaders.add("Narration");
            tableHeaders.add("ExpectedInstalmentCollection");
            tableHeaders.add("ActulaInstalmentCollected");
              tableHeaders.add("BalCollected");
            tableHeaders.add("VarianceStatus");


            List<List>dataBody = new ArrayList();

            int n = 0;
            Map<Integer, List<Object>> dataBase = rdb.dailyCollectionTotal(dates, c);
            
//            JOptionPane.showMessageDialog(c, dataBase.size());
            
            if (!dataBase.isEmpty()) {
                
            while (n < dataBase.size()) {
                
            List realData = dataBase.get(n);
            
            dataBody.add(realData);
//   JOptionPane.showMessageDialog(c, realData.size());
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
            dataBody.add(data3);
            }

            statusModel = new ListDataModelDailyCollect(dataBody, tableHeaders);
return statusModel;
            }
   
           
            
            
            
               public ListDataModelDailyCollect1 dailySummuryReport(List dates, Component c) {

            ListDataModelDailyCollect1 statusModel = null;


            List<String> tableHeaders = new ArrayList();
            
//            tableHeaders.add("S/n");
            tableHeaders.add("Transaction Made");
            tableHeaders.add("Debit/Expected");
            tableHeaders.add("Credit/Collected");
              tableHeaders.add("Cash Balance");
            tableHeaders.add("VarianceStatus");


            List<List>dataBody = new ArrayList();

            int n = 0;
            Map<Integer, List<Object>> dataBase = rdb.dailySummuryReport(dates, c);
            
//            JOptionPane.showMessageDialog(c, dataBase.size());
            
            if (!dataBase.isEmpty()) {
                
            while (n < dataBase.size()) {
                
            List realData = dataBase.get(n);
            
            dataBody.add(realData);
//   JOptionPane.showMessageDialog(c, realData.size());
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
            dataBody.add(data3);
            }

            statusModel = new ListDataModelDailyCollect1(dataBody, tableHeaders);
return statusModel;
            }
            
            
//             public ListDataModelLoanRegister loanRegisterReport(Component c) {
//
//            ListDataModelLoanRegister statusModel = null;
//
//
//            List<String> tableHeaders = new ArrayList();
//            
//            tableHeaders.add("S/n");
//            tableHeaders.add("Borrower");
//            tableHeaders.add("Account Number");
//            tableHeaders.add("Loan Tenure");
//              tableHeaders.add("Interest Rate");
//               tableHeaders.add("Date Taken");
//            tableHeaders.add("Due Date");
//            
//               tableHeaders.add("S/n");
//            tableHeaders.add("Borrower");
//            tableHeaders.add("Account Number");
//            tableHeaders.add("Loan Tenure");
//              tableHeaders.add("Interest Rate");
//               tableHeaders.add("Date Taken");
//            tableHeaders.add("Due Date");
//
//
//
//            List<List>dataBody = new ArrayList();
//
//            int n = 0;
//            Map<Integer, List<Object>> dataBase = rdb.grossPortfolioReport(c);
//            
////            JOptionPane.showMessageDialog(c, dataBase.size());
//            
//            if (!dataBase.isEmpty()) {
//                
//            while (n < dataBase.size()) {
//                
//            List realData = dataBase.get(n);
//            
//            dataBody.add(realData);
////   JOptionPane.showMessageDialog(c, realData.size());
//            n++;
//            }
//            } else {
//            List data3 = new ArrayList();
//            data3.add("0.0");
//            data3.add("0.0");
//            data3.add("0.0");
//            data3.add("0.0");
//            data3.add("0.0");
//          data3.add("0.0");
//           data3.add("0.0");
//            dataBody.add(data3);
//            }
//
//            statusModel = new ListDataModelPortfolioReport(dataBody, tableHeaders);
//return statusModel;
//            }
//   
//            
               
               
               
            
             public ListDataModelPortfolioReport grossLoanPorfolio(Component c) {

            ListDataModelPortfolioReport statusModel = null;


            List<String> tableHeaders = new ArrayList();
            
            tableHeaders.add("S/n");
            tableHeaders.add("Borrower");
            tableHeaders.add("OutStandingPricimpal");
            tableHeaders.add("OutstandingInterest");
              tableHeaders.add("OutStandingAccumInterest");
               tableHeaders.add("OutStandingPenalty");
            tableHeaders.add("TotalAmountOutstanding");


            List<List>dataBody = new ArrayList();

            int n = 0;
            Map<Integer, List<Object>> dataBase = rdb.grossPortfolioReport(c);
            
//            JOptionPane.showMessageDialog(c, dataBase.size());
            
            if (!dataBase.isEmpty()) {
                
            while (n < dataBase.size()) {
                
            List realData = dataBase.get(n);
            
            dataBody.add(realData);
//   JOptionPane.showMessageDialog(c, realData.size());
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
            dataBody.add(data3);
            }

            statusModel = new ListDataModelPortfolioReport(dataBody, tableHeaders);
return statusModel;
            }
   
            
            
             public ListDataModelDailyCollect dailyPrincimpalCollection(List dates, Component c) {

            ListDataModelDailyCollect statusModel = null;


            List<String> tableHeaders = new ArrayList();
            
            tableHeaders.add("S/n");
            tableHeaders.add("Narration");
            tableHeaders.add("ExpectedPrincipal");
            tableHeaders.add("ActualCollected");
              tableHeaders.add("BalCollected");
            tableHeaders.add("VarianceStatus");


            List<List>dataBody = new ArrayList();

            int n = 0;
            Map<Integer, List<Object>> dataBase = rdb.dailyCollectionPrincipal(dates, c);
            
//            JOptionPane.showMessageDialog(c, dataBase.size());
            
            if (!dataBase.isEmpty()) {
                
            while (n < dataBase.size()) {
                
            List realData = dataBase.get(n);
            
            dataBody.add(realData);
//   JOptionPane.showMessageDialog(c, realData.size());
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
            dataBody.add(data3);
            }

            statusModel = new ListDataModelDailyCollect(dataBody, tableHeaders);
return statusModel;
            }
             
             
            public ListDataModelPortfolioReport1 moneyLenderIntest(List dete,Component c) {

            ListDataModelPortfolioReport1 statusModel = null;


            List<String> tableHeaders = new ArrayList();
            
            tableHeaders.add("S/n");
            tableHeaders.add("DueDate");
            tableHeaders.add("OutStandingPricimpal");
            tableHeaders.add("InterestInvolved");
              tableHeaders.add("InterestPaid");
               tableHeaders.add("OutstandingInterest");
            tableHeaders.add("RunningBalanceIterest");
             tableHeaders.add("RunningBalanceTotal");

            List<List>dataBody = new ArrayList();

            int n = 0;
            Map<Integer, List<Object>> dataBase = rdb.moneyLInt(dete,c);
            
//            JOptionPane.showMessageDialog(c, dataBase.size());
            
            if (!dataBase.isEmpty()) {
                
            while (n < dataBase.size()) {
                
            List realData = dataBase.get(n);
            
            dataBody.add(realData);
//   JOptionPane.showMessageDialog(c, realData.size());
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
            dataBody.add(data3);
            }

            statusModel = new ListDataModelPortfolioReport1(dataBody, tableHeaders);
return statusModel;
            }
   
            
            
//             public ListDataModelDailyCollect dailyPrincimpalCollection(List dates, Component c) {
//
//            ListDataModelDailyCollect statusModel = null;
//
//
//            List<String> tableHeaders = new ArrayList();
//            
//            tableHeaders.add("S/n");
//            tableHeaders.add("Narration");
//            tableHeaders.add("ExpectedPrincipal");
//            tableHeaders.add("ActualCollected");
//              tableHeaders.add("BalCollected");
//            tableHeaders.add("VarianceStatus");
//
//
//            List<List>dataBody = new ArrayList();
//
//            int n = 0;
//            Map<Integer, List<Object>> dataBase = rdb.dailyCollectionPrincipal(dates, c);
//            
////            JOptionPane.showMessageDialog(c, dataBase.size());
//            
//            if (!dataBase.isEmpty()) {
//                
//            while (n < dataBase.size()) {
//                
//            List realData = dataBase.get(n);
//            
//            dataBody.add(realData);
////   JOptionPane.showMessageDialog(c, realData.size());
//            n++;
//            }
//            } else {
//            List data3 = new ArrayList();
//            data3.add("0.0");
//            data3.add("0.0");
//            data3.add("0.0");
//            data3.add("0.0");
//            data3.add("0.0");
//          data3.add("0.0");
//            dataBody.add(data3);
//            }
//
//            statusModel = new ListDataModelDailyCollect(dataBody, tableHeaders);
//return statusModel;
//            }   
//             
              public ListDataModelDailyCollect dailyPrincimpalCollectionArrears(List dates, Component c) {

            ListDataModelDailyCollect statusModel = null;


            List<String> tableHeaders = new ArrayList();
            
            tableHeaders.add("S/n");
            tableHeaders.add("Narration");
            tableHeaders.add("ExpectedPrincipal");
            tableHeaders.add("ActualCollected");
              tableHeaders.add("BalCollected");
            tableHeaders.add("VarianceStatus");


            List<List>dataBody = new ArrayList();

            int n = 0;
            Map<Integer, List<Object>> dataBase = rdb.dailyCollectionPrincipalArrears(dates, c);
            
//            JOptionPane.showMessageDialog(c, dataBase.size());
            
            if (!dataBase.isEmpty()) {
                
            while (n < dataBase.size()) {
                
            List realData = dataBase.get(n);
            
            dataBody.add(realData);
//   JOptionPane.showMessageDialog(c, realData.size());
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
            dataBody.add(data3);
            }

            statusModel = new ListDataModelDailyCollect(dataBody, tableHeaders);
return statusModel;
            }
             
          public ListDataModelDailyCollect dailyInterestCollection(List dates, Component c) {

            ListDataModelDailyCollect statusModel = null;


            List<String> tableHeaders = new ArrayList();
            
            tableHeaders.add("S/n");
            tableHeaders.add("Narration");
            tableHeaders.add("ExpectedInterest");
            tableHeaders.add("ActualCollected");
              tableHeaders.add("BalCollected");
            tableHeaders.add("VarianceStatus");


            List<List>dataBody = new ArrayList();

            int n = 0;
            Map<Integer, List<Object>> dataBase = rdb.dailyCollectionInterest(dates, c);
            
//            JOptionPane.showMessageDialog(c, dataBase.size());
            
            if (!dataBase.isEmpty()) {
                
            while (n < dataBase.size()) {
                
            List realData = dataBase.get(n);
            
            dataBody.add(realData);
//   JOptionPane.showMessageDialog(c, realData.size());
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
            dataBody.add(data3);
            }

            statusModel = new ListDataModelDailyCollect(dataBody, tableHeaders);
return statusModel;
            }   
          
  
          public ListDataModelDailyCollect dailyInterestCollectionArrears(List dates, Component c) {

            ListDataModelDailyCollect statusModel = null;


            List<String> tableHeaders = new ArrayList();
            
            tableHeaders.add("S/n");
            tableHeaders.add("Narration");
            tableHeaders.add("ExpectedInterest");
            tableHeaders.add("ActualCollected");
              tableHeaders.add("BalCollected");
            tableHeaders.add("VarianceStatus");


            List<List>dataBody = new ArrayList();

            int n = 0;
            Map<Integer, List<Object>> dataBase = rdb.dailyCollectionInterestArrears(dates, c);
            
//            JOptionPane.showMessageDialog(c, dataBase.size());
            
            if (!dataBase.isEmpty()) {
                
            while (n < dataBase.size()) {
                
            List realData = dataBase.get(n);
            
            dataBody.add(realData);
//   JOptionPane.showMessageDialog(c, realData.size());
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
            dataBody.add(data3);
            }

            statusModel = new ListDataModelDailyCollect(dataBody, tableHeaders);
return statusModel;
            }          
          
          
          
      public ListTableModel summuryReportOneByOne(List data, Component c,JTextField j) {

            ListTableModel statusModel = null;


            List<String> tableHeaders = new ArrayList();
            
            tableHeaders.add("S/n");
            tableHeaders.add("ItemName");
            tableHeaders.add("StartingFigure");
            tableHeaders.add("Difference");
              tableHeaders.add("EndingFigure");
            tableHeaders.add("ChangeStatus");


            List<List>dataBody = new ArrayList();

            int n = 0;
            Map<Integer, List<Object>> dataBase = rdb.summuOneByOneRepo(data, c,j);
            
//            JOptionPane.showMessageDialog(c, dataBase.size());
            
            if (!dataBase.isEmpty()) {
                
            while (n < dataBase.size()) {
                
            List realData = dataBase.get(n);
            
            dataBody.add(realData);
//   JOptionPane.showMessageDialog(c, realData.size());
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
            dataBody.add(data3);
            }

            statusModel = new ListTableModel(dataBody, tableHeaders);
return statusModel;
            }          
          
               
          
          
          
         public ListDataModelDailyCollect dailyTotalCollectionWithArrears(List dates, Component c) {

            ListDataModelDailyCollect statusModel = null;


            List<String> tableHeaders = new ArrayList();
            
            tableHeaders.add("S/n");
            tableHeaders.add("Narration");
            tableHeaders.add("ExpectedInstalments");
            tableHeaders.add("ActualCollected");
              tableHeaders.add("BalCollected");
            tableHeaders.add("VarianceStatus");


            List<List>dataBody = new ArrayList();

            int n = 0;
            Map<Integer, List<Object>> dataBase = rdb.dailyCollectionTotalArrears(dates, c);
            
//            JOptionPane.showMessageDialog(c, dataBase.size());
            
            if (!dataBase.isEmpty()) {
                
            while (n < dataBase.size()) {
                
            List realData = dataBase.get(n);
            
            dataBody.add(realData);
//   JOptionPane.showMessageDialog(c, realData.size());
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
            dataBody.add(data3);
            }

            statusModel = new ListDataModelDailyCollect(dataBody, tableHeaders);
return statusModel;
            }
       
       
     
     public boolean createLogin(String startDate, String endDate,String fileName) { 
     
  boolean Login=false;
     
  
  if(fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
  if(fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
   createActuallyLogin(startDate,endDate,fileName);
  Login=true;
  } 
    
  }else{
  createActuallyLogin(startDate,endDate,fileName);
  Login=true;
  }
      
      return Login;
       }
  
      private void createActuallyLogin(String startDate, String endDate,String fileName){
      try {
         
          
          Paragraph headerz =createHeading("log_in",startDate,endDate);
          headerz.setIndentationLeft(50);
          headerz.setIndentationRight(50);
         
          
          
          Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
          PdfWriter writer=PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf")));
          PdfPTable body= createTheLogIns(startDate,endDate);
      
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
          document.add(headerz );
          document.add(new Paragraph("  ") );
          Paragraph currency= new Paragraph("",font1);
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
         
         
      } catch (BadElementException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException | DocumentException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      }

       }
       
      public  PdfPTable createTheLogIns(String startDate,String endDate){
       PdfPTable table = new PdfPTable(3);
       table.setWidthPercentage(100);
       float[] columnWidths = {30f, 30f,30f};
      
       Font font1 = new Font(Font.FontFamily.COURIER  , 14, Font.NORMAL);
       Font font2 = new Font(Font.FontFamily.COURIER  , 14, Font.BOLD);
       
    try {
        table.setWidths(columnWidths);
        
    } catch (DocumentException ex) {
        Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
    }
            PdfPCell cell1 = new PdfPCell(new Paragraph("User ID",font2));
            PdfPCell cell2 = new PdfPCell(new Paragraph("LogIn Date",font2));
            PdfPCell cell3 = new PdfPCell(new Paragraph("LogIn Time",font2));
           
           cell1.setBackgroundColor(BaseColor.CYAN);   //sets BG color to yellow.
           cell2.setBackgroundColor(BaseColor.CYAN); 
           cell3.setBackgroundColor(BaseColor.CYAN); 
           

           cell1.setBorderWidth (2f);         // sets border width to 3 units
           cell2.setBorderWidth (2f);
           cell3.setBorderWidth (2f);
        
           
           
           cell1 .disableBorderSide(LEFT);         // sets border width to 3 units
           cell2 .disableBorderSide(LEFT);
           cell3 .disableBorderSide(LEFT);
       
           
           cell1 .disableBorderSide(TOP);       // sets border width to 3 units
           cell2.disableBorderSide(TOP); 
           cell3.disableBorderSide(TOP); 
     
           
           cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
           cell2.disableBorderSide(RIGHT); 
           cell3.disableBorderSide(RIGHT); 
          
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
         
          int n=0; Map<Integer, List<Object>> dataBase=rdb.logIn(startDate,endDate);
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
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(1).toString(),font1));
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
         
            PdfPCell cellx3 = new PdfPCell(new Paragraph(realData.get(2).toString(),font1));
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
             
           z++;   
           } 
      
           n++;
           } 
           }else{
           table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database",font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
          
           }
      return table;

       }  
      
      public boolean createLoanSavingsReport(String startMonth,String endMonth,int monthYear1,int monthYear2,String fileName) { 
     
  boolean loanSavings=false;
     
  
  if(fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
  if(fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
   createActuallyLoanSavingsReport(startMonth,endMonth,monthYear1,monthYear2,fileName);
  loanSavings=true;
  } 
    
  }else{
  createActuallyLoanSavingsReport(startMonth,endMonth,monthYear1,monthYear2,fileName);
  loanSavings=true;
  }
      
      return loanSavings;
       }
  
      private void createActuallyLoanSavingsReport(String startMonth,String endMonth,int monthYear1,int monthYear2,String fileName){
      try {
         
          
          Paragraph headerz =createHeading("loan_save",startMonth,endMonth);
          headerz.setIndentationLeft(50);
          headerz.setIndentationRight(50);
         
          
          
          Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
          PdfWriter writer=PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf")));
          PdfPTable body= createTheLoanSavingsReport(monthYear1,monthYear2);
      
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
          document.add(headerz );
          document.add(new Paragraph("  ") );
          Paragraph currency= new Paragraph("",font1);
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
         
         
      } catch (BadElementException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException | DocumentException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      }

       }
       
      public  PdfPTable createTheLoanSavingsReport(int startTime,int endTime){
          
       PdfPTable table = new PdfPTable(7);
       
       table.setWidthPercentage(100);
       
       float[] columnWidths = {10f, 20f,60f,40f,40f,40f,40f};
      
       Font font1 = new Font(Font.FontFamily.COURIER  , 14, Font.NORMAL);
       
       Font font2 = new Font(Font.FontFamily.COURIER  , 14, Font.BOLD);
       
    try {
        table.setWidths(columnWidths);
        
    } catch (DocumentException ex) {
        Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
    }
    
       
            PdfPCell cell1 = new PdfPCell(new Paragraph("S/N",font2));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Ref No.",font2));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Account Name",font2));
             PdfPCell cell4 = new PdfPCell(new Paragraph("Total Savings",font2));
            PdfPCell cell5 = new PdfPCell(new Paragraph("Total Loan",font2));
            PdfPCell cell6 = new PdfPCell(new Paragraph("Total Amount",font2));
           PdfPCell cell7 = new PdfPCell(new Paragraph("Last Txn Date",font2));
           cell1.setBackgroundColor(BaseColor.CYAN);   //sets BG color to yellow.
           cell2.setBackgroundColor(BaseColor.CYAN); 
           cell3.setBackgroundColor(BaseColor.CYAN); 
               cell4.setBackgroundColor(BaseColor.CYAN);   //sets BG color to yellow.
           cell5.setBackgroundColor(BaseColor.CYAN); 
           cell6.setBackgroundColor(BaseColor.CYAN); 
 cell7.setBackgroundColor(BaseColor.CYAN);
 
           cell1.setBorderWidth (2f);         // sets border width to 3 units
           cell2.setBorderWidth (2f);
           cell3.setBorderWidth (2f);
        cell4.setBorderWidth (2f);         // sets border width to 3 units
           cell5.setBorderWidth (2f);
           cell6.setBorderWidth (2f);
            cell7.setBorderWidth (2f);
           
           cell1 .disableBorderSide(LEFT);         // sets border width to 3 units
           cell2 .disableBorderSide(LEFT);
           cell3 .disableBorderSide(LEFT);
        cell4 .disableBorderSide(LEFT);         // sets border width to 3 units
           cell5 .disableBorderSide(LEFT);
           cell6 .disableBorderSide(LEFT);
            cell7 .disableBorderSide(LEFT);         // sets border width to 3 units
          
           
           cell1 .disableBorderSide(TOP);       // sets border width to 3 units
           cell2.disableBorderSide(TOP); 
           cell3.disableBorderSide(TOP); 
           cell4 .disableBorderSide(TOP);       // sets border width to 3 units
           cell5.disableBorderSide(TOP); 
           cell6.disableBorderSide(TOP); 
           cell7 .disableBorderSide(TOP);       // sets border width to 3 units
          
           
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
            
         
      
                  
       int n=0; Map<Integer, List<Object>> dataBase=rdb.loanSavingsReport(startTime, endTime);
    if(!dataBase.isEmpty()){
    while(n<dataBase.size()) {
    List realData=dataBase.get(n);int z=0;
    if(n==dataBase.size()-1){
    while(z<realData.size()){
    if(z==0){
    PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(),font2));
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
    if(z==1){

    PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(1).toString(),font2));
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
    if(z==2){

  PdfPCell cellx3 =new PdfPCell(new Paragraph(realData.get(2).toString(),font2));
    cellx3.disableBorderSide(LEFT);
    cellx3.disableBorderSide(TOP);
    cellx3.disableBorderSide(RIGHT);
    cellx3.setBorderColorBottom(BaseColor.BLACK);
    cellx3.setBorderColorTop(BaseColor.WHITE);
    cellx3.setBorderColorLeft(BaseColor.WHITE);
    cellx3.setBorderColorRight(BaseColor.WHITE);
    cellx3.setFixedHeight(20.2f);
    cellx3.setPadding(.5f);
    cellx3.setVerticalAlignment(Element.ALIGN_TOP);
    table.addCell(cellx3);
    }
if(z==3){


    PdfPCell cellx4 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(3).toString()),font2));
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
    table.addCell(cellx4);
    }
if(z==4){


    PdfPCell cellx5 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(4).toString()),font2));
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

if(z==5){


    PdfPCell cellx6 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(5).toString()),font2));
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
  if(z==6){

  PdfPCell cellx7 =new PdfPCell(new Paragraph(realData.get(6).toString(),font2));
    cellx7.disableBorderSide(LEFT);
    cellx7.disableBorderSide(TOP);
    cellx7.disableBorderSide(RIGHT);
    cellx7.setBorderColorBottom(BaseColor.BLACK);
    cellx7.setBorderColorTop(BaseColor.WHITE);
    cellx7.setBorderColorLeft(BaseColor.WHITE);
    cellx7.setBorderColorRight(BaseColor.WHITE);
    cellx7.setFixedHeight(20.2f);
    cellx7.setPadding(.5f);
    cellx7.setVerticalAlignment(Element.ALIGN_TOP);
    table.addCell(cellx7);
    }
    z++;   
    }

    }else{
    while(z<realData.size()){
   if(z==0){
    PdfPCell cellx1 = new PdfPCell(new Paragraph(realData.get(0).toString(),font1));
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
    if(z==1){

    PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(1).toString(),font1));
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
    if(z==2){

  PdfPCell cellx3 =new PdfPCell(new Paragraph(realData.get(2).toString(),font1));
    cellx3.disableBorderSide(LEFT);
    cellx3.disableBorderSide(TOP);
    cellx3.disableBorderSide(RIGHT);
    cellx3.setBorderColorBottom(BaseColor.BLACK);
    cellx3.setBorderColorTop(BaseColor.WHITE);
    cellx3.setBorderColorLeft(BaseColor.WHITE);
    cellx3.setBorderColorRight(BaseColor.WHITE);
    cellx3.setFixedHeight(20.2f);
    cellx3.setPadding(.5f);
    cellx3.setVerticalAlignment(Element.ALIGN_TOP);
    table.addCell(cellx3);
    }
if(z==3){


    PdfPCell cellx4 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(3).toString()),font1));
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
    table.addCell(cellx4);
    }
if(z==4){


    PdfPCell cellx5 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(4).toString()),font1));
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

if(z==5){


    PdfPCell cellx6 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(5).toString()),font1));
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
  if(z==6){

  PdfPCell cellx7 =new PdfPCell(new Paragraph(realData.get(6).toString(),font1));
    cellx7.disableBorderSide(LEFT);
    cellx7.disableBorderSide(TOP);
    cellx7.disableBorderSide(RIGHT);
    cellx7.setBorderColorBottom(BaseColor.BLACK);
    cellx7.setBorderColorTop(BaseColor.WHITE);
    cellx7.setBorderColorLeft(BaseColor.WHITE);
    cellx7.setBorderColorRight(BaseColor.WHITE);
    cellx7.setFixedHeight(20.2f);
    cellx7.setPadding(.5f);
    cellx7.setVerticalAlignment(Element.ALIGN_TOP);
    table.addCell(cellx7);
    }
    z++;  
    } }

    n++;
    } 
    }else{
        table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
        table.addCell(new PdfPCell(new Paragraph("Empty selection from database",font1)));
        table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
        table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
        table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
        table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
        table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
                                 
           }
      return table;

       } 
      
      public void createCustomerDemographReal(JTable  table ){
          column1=new ArrayList();
            column1.add("Title");
            column1.add("First Name");
            column1.add("Sur Name");
            column1.add("Sex");
            column1.add("Age");
            column1.add("Marital Status");
            column1.add("Education Level");
          
      Map<Integer, List<Object>> dataBase=rdb. customerDemog();
      
        model1= new ReportsModelData( dataBase, column1);
           table.setModel(model1);
           
       TableRowSorter<ReportsModelData> sorter = new TableRowSorter<>(model1);
      table.setRowSorter(sorter);
      
      
      }
      
      public boolean createCustomerDemograph(String fileName) { 
    boolean CustomerDemograph=false;
     
  
  if(fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
  if(fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
   createActuallyCustomerDemograph(fileName);
 CustomerDemograph=true;
  } 
    
  }else{
 createActuallyCustomerDemograph(fileName);
  CustomerDemograph=true;
  }
      
      return CustomerDemograph;
       }

      private void createActuallyCustomerDemograph(String file){
      try {
         
          
          Paragraph headerz =createHeading("customer_demo","","");
          headerz.setIndentationLeft(50);
          headerz.setIndentationRight(50);
         
          
          
          Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
          PdfWriter writer=PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", file+".pdf")));
          PdfPTable body= createTheCustomerDemograph();
      
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
          document.add(headerz );
          document.add(new Paragraph("  ") );
          Paragraph currency= new Paragraph("",font1);
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
         
         
      } catch (BadElementException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException | DocumentException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      }
       }
       
      public  PdfPTable createTheCustomerDemograph(){
       PdfPTable table = new PdfPTable(7);
       table.setWidthPercentage(100);
       float[] columnWidths = {15f, 40f, 40f,20f, 30f, 30f,40f};
      
       Font font1 = new Font(Font.FontFamily.COURIER  , 14, Font.NORMAL);
       Font font2 = new Font(Font.FontFamily.COURIER  , 14, Font.BOLD);
       
    try {
        table.setWidths(columnWidths);
        
    } catch (DocumentException ex) {
        Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
    }
            PdfPCell cell1 = new PdfPCell(new Paragraph("Title",font2));
            PdfPCell cell2 = new PdfPCell(new Paragraph("First Name",font2));
             PdfPCell cell3 = new PdfPCell(new Paragraph("Sur Name",font2)); 
            PdfPCell cell4 = new PdfPCell(new Paragraph("Sex",font2));
            PdfPCell cell5 = new PdfPCell(new Paragraph("Age",font2));
            PdfPCell cell6 = new PdfPCell(new Paragraph("Marital Status",font2));
            PdfPCell cell7 = new PdfPCell(new Paragraph("Education Level",font2)); 
           
           cell1.setBackgroundColor(BaseColor.CYAN);   //sets BG color to yellow.
           cell2.setBackgroundColor(BaseColor.CYAN); 
           cell3.setBackgroundColor(BaseColor.CYAN); 
           cell4.setBackgroundColor(BaseColor.CYAN); 
           cell5.setBackgroundColor(BaseColor.CYAN); 
           cell6.setBackgroundColor(BaseColor.CYAN);
            cell7.setBackgroundColor(BaseColor.CYAN); 

           cell1.setBorderWidth (2f);         // sets border width to 3 units
           cell2.setBorderWidth (2f);
           cell3.setBorderWidth (2f);
           cell4.setBorderWidth (2f);
           cell5.setBorderWidth (2f);
           cell6.setBorderWidth (2f);
            cell7.setBorderWidth (2f);
           cell6.setBorderColorBottom(BaseColor.BLACK);
           
           
           cell1 .disableBorderSide(LEFT);         // sets border width to 3 units
           cell2 .disableBorderSide(LEFT);
           cell3 .disableBorderSide(LEFT);
           cell4 .disableBorderSide(LEFT);
           cell5 .disableBorderSide(LEFT);
           cell6 .disableBorderSide(LEFT);
           cell7 .disableBorderSide(LEFT);
           
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
          int n=0; Map<Integer, List<Object>> dataBase=rdb. customerDemog();
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
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(1).toString(),font1));
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
         
            PdfPCell cellx3 = new PdfPCell(new Paragraph(realData.get(2).toString(),font1));
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

            PdfPCell cellx4 = new PdfPCell(new Paragraph(realData.get(3).toString(),font1));
         
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
          
            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(6).toString(),font1)); 
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
           table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A",font1))); 
            table.addCell(new PdfPCell(new Paragraph("N/A",font1))); 
           }
      return table;

       } 
     
      
      public boolean createloanSavings(String fileName) { 
    boolean loanSavings=false;
     
  
  if(fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
  if(fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
   createActuallyloanSavings(fileName);
 loanSavings=true;
  } 
    
  }else{
 createActuallyloanSavings(fileName);
  loanSavings=true;
  }
      
      return loanSavings;
       }

      private void createActuallyloanSavings(String file){
      try {
         
          
          Paragraph headerz =createHeading("loan_savings","","");
          headerz.setIndentationLeft(50);
          headerz.setIndentationRight(50);
         
          
          
          Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
          PdfWriter writer=PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", file+".pdf")));
          PdfPTable body= createTheloanSavings();
      
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
          document.add(headerz );
          document.add(new Paragraph("  ") );
          Paragraph currency= new Paragraph("",font1);
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
         
         
      } catch (BadElementException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException | DocumentException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      }
       }
       
      public  PdfPTable createTheloanSavings(){
       PdfPTable table = new PdfPTable(7);
       table.setWidthPercentage(100);
       float[] columnWidths = {15f, 15f, 40f,30f, 30f, 30f,30f};
      
       Font font1 = new Font(Font.FontFamily.COURIER  , 14, Font.NORMAL);
       Font font2 = new Font(Font.FontFamily.COURIER  , 14, Font.BOLD);
       
    try {
        table.setWidths(columnWidths);
        
    } catch (DocumentException ex) {
        Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
    }
            PdfPCell cell1 = new PdfPCell(new Paragraph("S/N",font2));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Code",font2));
             PdfPCell cell3 = new PdfPCell(new Paragraph("Name",font2)); 
            PdfPCell cell4 = new PdfPCell(new Paragraph("Savings",font2));
            PdfPCell cell5 = new PdfPCell(new Paragraph("Loan",font2));
            PdfPCell cell6 = new PdfPCell(new Paragraph("SaccoFund",font2));
            PdfPCell cell7 = new PdfPCell(new Paragraph("Comment",font2)); 
           
           cell1.setBackgroundColor(BaseColor.ORANGE);   //sets BG color to yellow.
           cell2.setBackgroundColor(BaseColor.ORANGE); 
           cell3.setBackgroundColor(BaseColor.ORANGE); 
           cell4.setBackgroundColor(BaseColor.ORANGE); 
           cell5.setBackgroundColor(BaseColor.ORANGE); 
           cell6.setBackgroundColor(BaseColor.ORANGE);
            cell7.setBackgroundColor(BaseColor.ORANGE); 

           cell1.setBorderWidth (2f);         // sets border width to 3 units
           cell2.setBorderWidth (2f);
           cell3.setBorderWidth (2f);
           cell4.setBorderWidth (2f);
           cell5.setBorderWidth (2f);
           cell6.setBorderWidth (2f);
            cell7.setBorderWidth (2f);
           cell6.setBorderColorBottom(BaseColor.BLACK);
           
           
           cell1 .disableBorderSide(LEFT);         // sets border width to 3 units
           cell2 .disableBorderSide(LEFT);
           cell3 .disableBorderSide(LEFT);
           cell4 .disableBorderSide(LEFT);
           cell5 .disableBorderSide(LEFT);
           cell6 .disableBorderSide(LEFT);
           cell7 .disableBorderSide(LEFT);
           
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
          int n=0; Map<Integer, List<Object>> dataBase=rdb. customerLoanSave();
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
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(1).toString(),font1));
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
         
            PdfPCell cellx3 = new PdfPCell(new Paragraph(realData.get(2).toString(),font1));
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

            PdfPCell cellx4 = new PdfPCell(new Paragraph(realData.get(3).toString(),font1));
         
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
          
            PdfPCell cellx6 = new PdfPCell(new Paragraph(realData.get(6).toString(),font1)); 
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
           table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A",font1))); 
            table.addCell(new PdfPCell(new Paragraph("N/A",font1))); 
           }
      return table;

       } 
   public boolean createCustomerBal(String fileName) { 
     
  boolean CustomerBal=false;
     
  
  if(fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
  if(fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
   createActuallyCustomerBal(fileName);
  CustomerBal=true;
  } 
    
  }else{
  createActuallyCustomerBal(fileName);
 CustomerBal=true;
  }
      
      return CustomerBal;
       }
  
      private void createActuallyCustomerBal(String fileName){
      try {
         
          
          Paragraph headerz =createHeading("cust_bal","","");
          headerz.setIndentationLeft(50);
          headerz.setIndentationRight(50);
         
          
          
          Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
          PdfWriter writer=PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf")));
          PdfPTable body= createTheCustomerBal();
      
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
          document.add(headerz );
          document.add(new Paragraph("  ") );
          Paragraph currency= new Paragraph("CURRENCY:UGANDA SHILLINGS",font1);
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
         
         
      } catch (BadElementException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException | DocumentException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      }

       }
       
      public  PdfPTable createTheCustomerBal(){
       PdfPTable table = new PdfPTable(3);
       table.setWidthPercentage(100);
       float[] columnWidths = {40f, 30f,30f};
      
       Font font1 = new Font(Font.FontFamily.COURIER  , 14, Font.NORMAL);
       Font font2 = new Font(Font.FontFamily.COURIER  , 14, Font.BOLD);
       
    try {
        table.setWidths(columnWidths);
        
    } catch (DocumentException ex) {
        Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
    }
            PdfPCell cell1 = new PdfPCell(new Paragraph("Customer Name",font2));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Account Number",font2));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Account Balance",font2));
           
           cell1.setBackgroundColor(BaseColor.ORANGE);   //sets BG color to yellow.
           cell2.setBackgroundColor(BaseColor.ORANGE); 
           cell3.setBackgroundColor(BaseColor.ORANGE); 
           

           cell1.setBorderWidth (2f);         // sets border width to 3 units
           cell2.setBorderWidth (2f);
           cell3.setBorderWidth (2f);
        
           
           
           cell1 .disableBorderSide(LEFT);         // sets border width to 3 units
           cell2 .disableBorderSide(LEFT);
           cell3 .disableBorderSide(LEFT);
       
           
           cell1 .disableBorderSide(TOP);       // sets border width to 3 units
           cell2.disableBorderSide(TOP); 
           cell3.disableBorderSide(TOP); 
     
           
           cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
           cell2.disableBorderSide(RIGHT); 
           cell3.disableBorderSide(RIGHT); 
          
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
         
          int n=0; Map<Integer, List<Object>> dataBase=rdb.custBalance();
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
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(1).toString(),font1));
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
             
           z++;   
           } 
      
           n++;
           } 
           }else{
           table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database",font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
          
           }
      return table;

       }
       
   public boolean createCustomerShares(String fileName) { 
     
  boolean CustomerShares=false;
     
  
  if(fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
  if(fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
   createActuallyCustomerShares(fileName);
  CustomerShares=true;
  } 
    
  }else{
  createActuallyCustomerShares(fileName);
CustomerShares=true;
  }
      
      return CustomerShares;
       }
  
      private void createActuallyCustomerShares(String fileName){
      try {
         
          
          Paragraph headerz =createHeading("cust_shares","","");
          headerz.setIndentationLeft(50);
          headerz.setIndentationRight(50);
         
          
          
          Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
          PdfWriter writer=PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf")));
          PdfPTable body= createTheCustomerShare();
    
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
          document.add(headerz );
          document.add(new Paragraph("  ") );
          Paragraph currency= new Paragraph("CURRENCY:UGANDA SHILLINGS",font1);
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
         
         
      } catch (BadElementException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException | DocumentException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      }

       }
         
              
      public  PdfPTable createTheCustomerShare(){
       PdfPTable table = new PdfPTable(3);
       table.setWidthPercentage(100);
       float[] columnWidths = {50f, 30f,30f};
      
       Font font1 = new Font(Font.FontFamily.COURIER  , 14, Font.NORMAL);
       Font font2 = new Font(Font.FontFamily.COURIER  , 14, Font.BOLD);
       
    try {
        table.setWidths(columnWidths);
        
    } catch (DocumentException ex) {
        Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
    }
            PdfPCell cell1 = new PdfPCell(new Paragraph("Customer Name",font2));
            PdfPCell cell2 = new PdfPCell(new Paragraph("No. Shares",font2));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Value Shares",font2));
           
           cell1.setBackgroundColor(BaseColor.CYAN);   //sets BG color to yellow.
           cell2.setBackgroundColor(BaseColor.CYAN); 
           cell3.setBackgroundColor(BaseColor.CYAN); 
           

           cell1.setBorderWidth (2f);         // sets border width to 3 units
           cell2.setBorderWidth (2f);
           cell3.setBorderWidth (2f);
        
           
           
           cell1 .disableBorderSide(LEFT);         // sets border width to 3 units
           cell2 .disableBorderSide(LEFT);
           cell3 .disableBorderSide(LEFT);
       
           
           cell1 .disableBorderSide(TOP);       // sets border width to 3 units
           cell2.disableBorderSide(TOP); 
           cell3.disableBorderSide(TOP); 
     
           
           cell1.disableBorderSide(RIGHT);         // sets border width to 3 units
           cell2.disableBorderSide(RIGHT); 
           cell3.disableBorderSide(RIGHT); 
          
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
         
          int n=0; Map<Integer, List<Object>> dataBase=rdb.custShares();
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
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(1).toString(),font1));
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
         
            PdfPCell cellx3 = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(realData.get(2).toString().replace(",", "")),font1));
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
             
           z++;   
           } 
      
           n++;
           } 
           }else{
           table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database",font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
          
           }
      return table;

       }
  
   public boolean createCustomerContact(String fileName) { 
     
  boolean CustomerContact=false;
     
  
  if(fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
  if(fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
   createActuallyCustomerContact(fileName);
  CustomerContact=true;
  } 
    
  }else{
  createActuallyCustomerContact(fileName);
CustomerContact=true;
  }
      
      return CustomerContact;
       }
  
      private void createActuallyCustomerContact(String fileName){
      try {
         
          
          Paragraph headerz =createHeading("contacts","","");
          headerz.setIndentationLeft(50);
          headerz.setIndentationRight(50);
         
          
          
          Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
          PdfWriter writer=PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf")));
          PdfPTable body= createTheCustomerContact();
      
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
          document.add(headerz );
          document.add(new Paragraph("  ") );
          Paragraph currency= new Paragraph("",font1);
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
         
         
      } catch (BadElementException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException | DocumentException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      }
  
       }
       
      public  PdfPTable createTheCustomerContact(){
       PdfPTable table = new PdfPTable(5);
       table.setWidthPercentage(100);
       float[] columnWidths = {40f, 20f, 40f,30f, 20f};
      
       Font font1 = new Font(Font.FontFamily.COURIER  , 14, Font.NORMAL);
       Font font2 = new Font(Font.FontFamily.COURIER  , 14, Font.BOLD);
       
    try {
        table.setWidths(columnWidths);
        
    } catch (DocumentException ex) {
        Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
    }
            PdfPCell cell1 = new PdfPCell(new Paragraph("Name",font2));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Mobile",font2));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Email",font2));
            PdfPCell cell4 = new PdfPCell(new Paragraph("Next Kin",font2));
            PdfPCell cell5 = new PdfPCell(new Paragraph("Mobile",font2));
         
          
           cell1.setBackgroundColor(BaseColor.CYAN);   //sets BG color to yellow.
           cell2.setBackgroundColor(BaseColor.CYAN); 
           cell3.setBackgroundColor(BaseColor.CYAN); 
           cell4.setBackgroundColor(BaseColor.CYAN); 
           cell5.setBackgroundColor(BaseColor.CYAN); 
        
         

           cell1.setBorderWidth (2f);         // sets border width to 3 units
           cell2.setBorderWidth (2f);
           cell3.setBorderWidth (2f);
           cell4.setBorderWidth (2f);
           cell5.setBorderWidth (2f);
         
          
           cell5.setBorderColorBottom(BaseColor.BLACK);
           
           
           cell1 .disableBorderSide(LEFT);         // sets border width to 3 units
           cell2 .disableBorderSide(LEFT);
           cell3 .disableBorderSide(LEFT);
           cell4 .disableBorderSide(LEFT);
           cell5 .disableBorderSide(LEFT);
        
          
           
           cell1 .disableBorderSide(TOP);       // sets border width to 3 units
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
           
        
          int n=0; Map<Integer, List<Object>> dataBase=rdb.customerContacts();
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
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(1).toString(),font1));
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
         
            PdfPCell cellx3 = new PdfPCell(new Paragraph(realData.get(2).toString(),font1));
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

            PdfPCell cellx4 = new PdfPCell(new Paragraph(realData.get(3).toString(),font1));
         
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
                
         
                
           z++;   
           } 
      
           n++;
           } 
           }else{
           table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database",font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
           table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
           table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
           
           }
      return table;

       }
      
    
    public boolean createAssets(String fileName) { 
     
  boolean Assets=false;
     
  
  if(fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
  if(fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
  createActuallyAssets(fileName);
  Assets=true;
  } 
    
  }else{
 createActuallyAssets(fileName);
Assets=true;
  }
      
      return Assets;
       }
  
      private void createActuallyAssets(String fileName){
      try {
         
          
          Paragraph headerz =createHeading("the_assets","","");
          headerz.setIndentationLeft(50);
          headerz.setIndentationRight(50);
         
          
          
          Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
          PdfWriter writer=PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf")));
          PdfPTable body= createTheAssets();
      
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
          document.add(headerz );
          document.add(new Paragraph("  ") );
          Paragraph currency= new Paragraph("CURRENCY:UGANDA SHILLINGS",font1);
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
         
         
      } catch (BadElementException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException | DocumentException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      }

       }
       
      public  PdfPTable createTheAssets(){
       PdfPTable table = new PdfPTable(6);
       table.setWidthPercentage(100);
       float[] columnWidths = {30f, 25f,25f,15f,40f,40f};
      
       Font font1 = new Font(Font.FontFamily.COURIER  , 14, Font.NORMAL);
       Font font2 = new Font(Font.FontFamily.COURIER  , 14, Font.BOLD);
       
    try {
        table.setWidths(columnWidths);
        
    } catch (DocumentException ex) {
        Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
    }
            PdfPCell cell1 = new PdfPCell(new Paragraph("A/C Name",font2));
            PdfPCell cell2 = new PdfPCell(new Paragraph("A/C Number",font2));
            PdfPCell cell3 = new PdfPCell(new Paragraph("A/C Balance",font2));
            PdfPCell cell4 = new PdfPCell(new Paragraph("A/C Status",font2));
            PdfPCell cell5 = new PdfPCell(new Paragraph("A/C CAT1",font2));
            PdfPCell cell6 = new PdfPCell(new Paragraph("A/C CAT2",font2));
          
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
           cell4.disableBorderSide(LEFT);
            cell5.disableBorderSide(LEFT);
            cell6.disableBorderSide(LEFT);
           
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
         
          int n=0; Map<Integer, List<Object>> dataBase=rdb.assetAccounts();
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
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(1).toString(),font1));
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
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(3).toString(),font1));
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
              if(z==4){
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(4).toString(),font1));
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
               if(z==5){
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(5).toString(),font1));
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
            
            
            
            
            
            
           z++;   
           } 
      
           n++;
           } 
           }else{
           table.addCell(new PdfPCell(new Paragraph("today",font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database",font1)));
            table.addCell(new PdfPCell(new Paragraph("tomorrow",font1)));
          
           }
      return table;

       }
 

    public boolean createOverDrawn(String fileName) { 
     
  boolean OverDrawn=false;
     
  
  if(fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
  if(fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
createActuallyOverDrawn(fileName);
OverDrawn=true;
  } 
    
  }else{
createActuallyOverDrawn(fileName);
OverDrawn=true;
  }
      
      return OverDrawn;
       }
  
      private void createActuallyOverDrawn(String fileName){
      try {
         
          
          Paragraph headerz =createHeading("the_overDraw","","");
          headerz.setIndentationLeft(50);
          headerz.setIndentationRight(50);
        
          
          Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
          PdfWriter writer=PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf")));
          PdfPTable body= createTheOverDrawn();
      
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
          document.add(headerz );
          document.add(new Paragraph("  ") );
          Paragraph currency= new Paragraph("CURRENCY:UGANDA SHILLINGS",font1);
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
         
         
      } catch (BadElementException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException | DocumentException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      }

       }
        
          
      public  PdfPTable createTheOverDrawn(){
       PdfPTable table = new PdfPTable(6);
       table.setWidthPercentage(100);
       float[] columnWidths = {25f, 25f,30f,15f,40f,40f};
      
       Font font1 = new Font(Font.FontFamily.COURIER  , 14, Font.NORMAL);
       Font font2 = new Font(Font.FontFamily.COURIER  , 14, Font.BOLD);
       
    try {
        table.setWidths(columnWidths);
        
    } catch (DocumentException ex) {
        Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
    }
            PdfPCell cell1 = new PdfPCell(new Paragraph("A/C Name",font2));
            PdfPCell cell2 = new PdfPCell(new Paragraph("A/C Number",font2));
            PdfPCell cell3 = new PdfPCell(new Paragraph("A/C Balance",font2));
            PdfPCell cell4 = new PdfPCell(new Paragraph("A/C Status",font2));
            PdfPCell cell5 = new PdfPCell(new Paragraph("A/C CAT1",font2));
            PdfPCell cell6 = new PdfPCell(new Paragraph("A/C CAT2",font2));
          
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
           cell4.disableBorderSide(LEFT);
            cell5.disableBorderSide(LEFT);
            cell6.disableBorderSide(LEFT);
           
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
         
          int n=0; Map<Integer, List<Object>> dataBase=rdb.overDrawnAccounts();
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
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(1).toString(),font1));
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
         
            PdfPCell cellx3 = new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(realData.get(2).toString())+")",font1));
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
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(3).toString(),font1));
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
              if(z==4){
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(4).toString(),font1));
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
               if(z==5){
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(5).toString(),font1));
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
            
            
            
            
            
            
           z++;   
           } 
      
           n++;
           } 
           }else{
           table.addCell(new PdfPCell(new Paragraph("today",font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database",font1)));
            table.addCell(new PdfPCell(new Paragraph("tomorrow",font1)));
          
           }
      return table;

       }
     
    public boolean createLiabilities(String fileName) { 
     
  boolean Liabilities=false;
     
  
  if(fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
  if(fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
createActuallyLiabilities(fileName);
  Liabilities=true;
  } 
    
  }else{
createActuallyLiabilities(fileName);
Liabilities=true;
  }
      
      return Liabilities;
       }
  
      private void createActuallyLiabilities(String fileName){
  
      try {
         
          
          Paragraph headerz =createHeading("the_liabilities","","");
          headerz.setIndentationLeft(50);
          headerz.setIndentationRight(50);
         
         
          
          Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
          PdfWriter writer=PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf")));
          PdfPTable body= createTheLiabilities();
      
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
          document.add(headerz );
          document.add(new Paragraph("  ") );
          Paragraph currency= new Paragraph("CURRENCY:UGANDA SHILLINGS",font1);
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
         
         
      } catch (BadElementException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException | DocumentException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      }
   
       }
        
                  
      public  PdfPTable createTheLiabilities(){
       PdfPTable table = new PdfPTable(6);
       table.setWidthPercentage(100);
       float[] columnWidths = {30f, 25f,25f,15f,40f,40f};
      
       Font font1 = new Font(Font.FontFamily.COURIER  , 14, Font.NORMAL);
       Font font2 = new Font(Font.FontFamily.COURIER  , 14, Font.BOLD);
       
    try {
        table.setWidths(columnWidths);
        
    } catch (DocumentException ex) {
        Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
    }
            PdfPCell cell1 = new PdfPCell(new Paragraph("A/C Name",font2));
            PdfPCell cell2 = new PdfPCell(new Paragraph("A/C Number",font2));
            PdfPCell cell3 = new PdfPCell(new Paragraph("A/C Balance",font2));
            PdfPCell cell4 = new PdfPCell(new Paragraph("A/C Status",font2));
            PdfPCell cell5 = new PdfPCell(new Paragraph("A/C CAT1",font2));
            PdfPCell cell6 = new PdfPCell(new Paragraph("A/C CAT2",font2));
          
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
           cell4.disableBorderSide(LEFT);
            cell5.disableBorderSide(LEFT);
            cell6.disableBorderSide(LEFT);
           
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
         
          int n=0; Map<Integer, List<Object>> dataBase=rdb.liabilitiesAccounts();
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
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(1).toString(),font1));
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
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(3).toString(),font1));
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
              if(z==4){
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(4).toString(),font1));
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
               if(z==5){
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(5).toString(),font1));
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
            
            
            
            
            
            
           z++;   
           } 
      
           n++;
           } 
           }else{
           table.addCell(new PdfPCell(new Paragraph("today",font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database",font1)));
            table.addCell(new PdfPCell(new Paragraph("tomorrow",font1)));
          
           }
      return table;

       }
      
 
       
    public boolean createEquity(String fileName) { 
     
  boolean Equity=false;
     
  
  if(fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
  if(fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
createActuallyEquity(fileName);
  Equity=true;
  } 
    
  }else{
createActuallyEquity(fileName);
Equity=true;
  }
      
      return Equity;
       }
  
      private void createActuallyEquity(String fileName){
      try {
         
          
          Paragraph headerz =createHeading("the_equity","","");
          headerz.setIndentationLeft(50);
          headerz.setIndentationRight(50);
         
         
          
          Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
          PdfWriter writer=PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf")));
          PdfPTable body= createTheEquity();
      
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
          document.add(headerz );
          document.add(new Paragraph("  ") );
          Paragraph currency= new Paragraph("CURRENCY:UGANDA SHILLINGS",font1);
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
         
         
      } catch (BadElementException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException | DocumentException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      }

       }
        
                  
      public  PdfPTable createTheEquity(){
       PdfPTable table = new PdfPTable(6);
       table.setWidthPercentage(100);
       float[] columnWidths = {30f, 25f,25f,15f,40f,40f};
      
       Font font1 = new Font(Font.FontFamily.COURIER  , 14, Font.NORMAL);
       Font font2 = new Font(Font.FontFamily.COURIER  , 14, Font.BOLD);
       
    try {
        table.setWidths(columnWidths);
        
    } catch (DocumentException ex) {
        Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
    }
            PdfPCell cell1 = new PdfPCell(new Paragraph("A/C Name",font2));
            PdfPCell cell2 = new PdfPCell(new Paragraph("A/C Number",font2));
            PdfPCell cell3 = new PdfPCell(new Paragraph("A/C Balance",font2));
            PdfPCell cell4 = new PdfPCell(new Paragraph("A/C Status",font2));
            PdfPCell cell5 = new PdfPCell(new Paragraph("A/C CAT1",font2));
            PdfPCell cell6 = new PdfPCell(new Paragraph("A/C CAT2",font2));
          
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
           cell4.disableBorderSide(LEFT);
            cell5.disableBorderSide(LEFT);
            cell6.disableBorderSide(LEFT);
           
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
         
          int n=0; Map<Integer, List<Object>> dataBase=rdb.equityAccounts();
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
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(1).toString(),font1));
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
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(3).toString(),font1));
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
              if(z==4){
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(4).toString(),font1));
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
               if(z==5){
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(5).toString(),font1));
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
            
            
            
            
            
            
           z++;   
           } 
      
           n++;
           } 
           }else{
           table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database",font1)));
            table.addCell(new PdfPCell(new Paragraph("N/A",font1)));
          
           }
      return table;

       }
      
 
    public boolean createRevenue(String fileName) { 
     
  boolean Revenue=false;
     
  
  if(fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
  if(fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
createActuallyRevenue(fileName);
 Revenue=true;
  } 
    
  }else{
createActuallyRevenue(fileName);
Revenue=true;
  }
      
      return Revenue;
       }
  
      private void createActuallyRevenue(String fileName){
      try {
         
          
          Paragraph headerz =createHeading("the_revenue","","");
          headerz.setIndentationLeft(50);
          headerz.setIndentationRight(50);
         
         
          
          Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
          PdfWriter writer=PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf")));
          PdfPTable body= createTheRevenue();
      
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
          document.add(headerz );
          document.add(new Paragraph("  ") );
          Paragraph currency= new Paragraph("CURRENCY:UGANDA SHILLINGS",font1);
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
         
         
      } catch (BadElementException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException | DocumentException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      }

       }
        
                  
      public  PdfPTable createTheRevenue(){
       PdfPTable table = new PdfPTable(6);
       table.setWidthPercentage(100);
       float[] columnWidths = {30f, 25f,25f,15f,40f,40f};
      
       Font font1 = new Font(Font.FontFamily.COURIER  , 14, Font.NORMAL);
       Font font2 = new Font(Font.FontFamily.COURIER  , 14, Font.BOLD);
       
    try {
        table.setWidths(columnWidths);
        
    } catch (DocumentException ex) {
        Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
    }
            PdfPCell cell1 = new PdfPCell(new Paragraph("A/C Name",font2));
            PdfPCell cell2 = new PdfPCell(new Paragraph("A/C Number",font2));
            PdfPCell cell3 = new PdfPCell(new Paragraph("A/C Balance",font2));
            PdfPCell cell4 = new PdfPCell(new Paragraph("A/C Status",font2));
            PdfPCell cell5 = new PdfPCell(new Paragraph("A/C CAT1",font2));
            PdfPCell cell6 = new PdfPCell(new Paragraph("A/C CAT2",font2));
          
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
           cell4.disableBorderSide(LEFT);
            cell5.disableBorderSide(LEFT);
            cell6.disableBorderSide(LEFT);
           
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
         
          int n=0; Map<Integer, List<Object>> dataBase=rdb.revenueAccounts();
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
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(1).toString(),font1));
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
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(3).toString(),font1));
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
              if(z==4){
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(4).toString(),font1));
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
               if(z==5){
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(5).toString(),font1));
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
            
            
            
            
            
            
           z++;   
           } 
      
           n++;
           } 
           }else{
           table.addCell(new PdfPCell(new Paragraph("today",font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database",font1)));
            table.addCell(new PdfPCell(new Paragraph("tomorrow",font1)));
          
           }
      return table;

       }

    public boolean createExpense(String fileName) { 
     
  boolean Expense=false;
     
  
  if(fios.exitsFile(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
  if(fios.deleteFileFirst(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"))){
createActuallyExpense(fileName);
Expense=true;
  } 
    
  }else{
createActuallyExpense(fileName);
Expense=true;
  }
      
      return Expense;
       }
  
      private void createActuallyExpense(String fileName){
      try {
         
         
          Paragraph headerz =createHeading("the_expense","","");
          headerz.setIndentationLeft(50);
          headerz.setIndentationRight(50);
         
         
          
          Document document = new Document(PageSize.A3, 45f, 36f, 54f, 72f);
          PdfWriter writer=PdfWriter.getInstance(document, fios.FileOutputStream(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf")));
          PdfPTable body= createTheExpense();
      
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
          document.add(headerz );
          document.add(new Paragraph("  ") );
          Paragraph currency= new Paragraph("CURRENCY:UGANDA SHILLINGS",font1);
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
         
         
      } catch (BadElementException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException | DocumentException ex) {
          Logger.getLogger(ReportsPDFFormulars.class.getName()).log(Level.SEVERE, null, ex);
      }

       }
        
                  
              
      public  PdfPTable createTheExpense(){
       PdfPTable table = new PdfPTable(6);
       table.setWidthPercentage(100);
       float[] columnWidths = {30f, 25f,25f,15f,40f,40f};
      
       Font font1 = new Font(Font.FontFamily.COURIER  , 14, Font.NORMAL);
       Font font2 = new Font(Font.FontFamily.COURIER  , 14, Font.BOLD);
       
    try {
        table.setWidths(columnWidths);
        
    } catch (DocumentException ex) {
        Logger.getLogger(PostingEntryWindow.class.getName()).log(Level.SEVERE, null, ex);
    }
            PdfPCell cell1 = new PdfPCell(new Paragraph("A/C Name",font2));
            PdfPCell cell2 = new PdfPCell(new Paragraph("A/C Number",font2));
            PdfPCell cell3 = new PdfPCell(new Paragraph("A/C Balance",font2));
            PdfPCell cell4 = new PdfPCell(new Paragraph("A/C Status",font2));
            PdfPCell cell5 = new PdfPCell(new Paragraph("A/C CAT1",font2));
            PdfPCell cell6 = new PdfPCell(new Paragraph("A/C CAT2",font2));
          
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
           cell4.disableBorderSide(LEFT);
            cell5.disableBorderSide(LEFT);
            cell6.disableBorderSide(LEFT);
           
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
         
          int n=0; Map<Integer, List<Object>> dataBase=rdb.expenseAccounts();
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
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(1).toString(),font1));
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
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(3).toString(),font1));
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
              if(z==4){
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(4).toString(),font1));
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
               if(z==5){
         
            PdfPCell cellx2 =new PdfPCell(new Paragraph(realData.get(5).toString(),font1));
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
            
            
            
            
            
            
           z++;   
           } 
      
           n++;
           } 
           }else{
           table.addCell(new PdfPCell(new Paragraph("today",font1)));
            table.addCell(new PdfPCell(new Paragraph("Empty selection from database",font1)));
            table.addCell(new PdfPCell(new Paragraph("tomorrow",font1)));
          
           }
      return table;

       }
      
      
      public  Paragraph createFooter(String accountNumber3 ){
       Paragraph s=null;
       Font font1 = new Font(Font.FontFamily.COURIER  , 10, Font.BOLDITALIC);
  s= new Paragraph("POWERED BY: BAZIRAKE AUGUSTINE GOOGO, MOBILE: 0782231039, EMAIL: augbazi@gmail.com",font1);
   s.setAlignment(Element.ALIGN_CENTER);
       return s;
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

private Paragraph createHeading(String identifier,String startDate,String endDate) {
  Paragraph ss=null; Font fonts = new Font(Font.FontFamily.COURIER  , 14, Font.NORMAL);
  switch(identifier){
      case "staff":
 
  ss= new Paragraph("LIST OF STAFF MEMBERS"+"\n"+"AS AT"+" "+sdf.format(ffm.getTodayDate())+"",fonts);
   ss.setAlignment(Element.ALIGN_CENTER);
          break;
          case "log_in":

  ss= new Paragraph("SYSTEM LOGIN BY USERS"+"\n"+"AS AT"+" "+ffm.getTodayDate()+"\n"+"BETWEEN"+" "+ffm.fromDatabaseWithDashSeperatorBeginningWithYear(startDate)+" "+"AND"+" "+ffm.fromDatabaseWithDashSeperatorBeginningWithYear(endDate),fonts);
   ss.setAlignment(Element.ALIGN_CENTER);
          break;
  
       case "customer_demo":

  ss= new Paragraph("CUSTOMERS BY DEMOGRAPHIC CHARACTERISTICS"+"\n"+"AS AT"+" "+ffm.getTodayDate()+"",fonts);
   ss.setAlignment(Element.ALIGN_CENTER);
          break;
           
          
           case "loan_savings":

  ss= new Paragraph("LOAN AND SAVINGS DEDUCTIONS ADVICE"+"\n"+"AS AT"+" "+ffm.getTodayDate()+"",fonts);
   ss.setAlignment(Element.ALIGN_CENTER);
          break;
           
           case  "cust_bal":

  ss= new Paragraph("CUSTOMERS BY ACCOUNT BALANCE"+"\n"+"AS AT"+" "+ffm.getTodayDate()+"",fonts);
   ss.setAlignment(Element.ALIGN_CENTER);
          break;
                   case  "contacts":

  ss= new Paragraph("CUSTOMERS BY  QUICK CONTACT"+"\n"+"AS AT"+" "+ffm.getTodayDate()+"",fonts);
   ss.setAlignment(Element.ALIGN_CENTER);
          break;
                   case "the_assets":

  ss= new Paragraph("LIST OF ASSET ACCOUNTS"+"\n"+"AS AT"+" "+ffm.getTodayDate()+"",fonts);
   ss.setAlignment(Element.ALIGN_CENTER);
          break;
          case "the_liabilities":

  ss= new Paragraph("LIST OF LIABILITIES ACCOUNTS"+"\n"+"AS AT"+" "+ffm.getTodayDate()+"",fonts);
   ss.setAlignment(Element.ALIGN_CENTER);
          break;
           case "the_equity":

  ss= new Paragraph("LIST OF EQUITY ACCOUNTS"+"\n"+"AS AT"+" "+ffm.getTodayDate()+"",fonts);
   ss.setAlignment(Element.ALIGN_CENTER);
          break;
  case "the_revenue":

  ss= new Paragraph("LIST OF REVENUE ACCOUNTS"+"\n"+"AS AT"+" "+ffm.getTodayDate()+"",fonts);
   ss.setAlignment(Element.ALIGN_CENTER);
          break;
            case "the_expense":
  ss= new Paragraph("LIST OF EXPENSE ACCOUNTS"+"\n"+"AS AT"+" "+ffm.getTodayDate()+"",fonts);
   ss.setAlignment(Element.ALIGN_CENTER);
          break;
           case "cust_shares":
  ss= new Paragraph("CUSTOMER SHARES BY VALUE AND NUMBERS"+"\n"+"AS AT"+" "+ffm.getTodayDate()+"",fonts);
   ss.setAlignment(Element.ALIGN_CENTER);
          break;
               
               case  "the_overDraw":
  ss= new Paragraph("LIST OF ACCOUNTS IN OVERDRAWN POSITION"+"\n"+"AS AT"+" "+ffm.getTodayDate()+"",fonts);
   ss.setAlignment(Element.ALIGN_CENTER);
          break;
         
           case  "loan_save":
  ss= new Paragraph("TOTAL LOAN AND SAVINGS FOR ALL MEMBERS FOR THE MONTHS RANGING "+"\n"+"\t"+"FROM"+" "+fios.stringFileReader(fios.createFileName("PMMS_Statements", "reports", "dateCarrier1A.txt")).toUpperCase()+" "+"TO"+" "+fios.stringFileReader(fios.createFileName("PMMS_Statements", "reports", "dateCarrier2A.txt")).toUpperCase()+"\t"+"\t",fonts);
   ss.setAlignment(Element.ALIGN_CENTER);
          break;
  }     
        
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
