
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.reportsHelper;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import static com.itextpdf.text.Rectangle.BOTTOM;
import static com.itextpdf.text.Rectangle.LEFT;
import static com.itextpdf.text.Rectangle.RIGHT;
import static com.itextpdf.text.Rectangle.TOP;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databaseConnectors.JdbcConnector;
import googo.pmms.project2.databases.DatabaseQuaries;
import googo.pmms.project2.databases.ReportsDatabase;
import googo.pmms.project2.frames.PostingEntryWindow;
import googo.pmms.project2.loanHelper.MaxmumAmountBorrowedFormulas;
import java.awt.Component;
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

/**
 *
 * @author Stanchart
 */
public class netProfits {
  MaxmumAmountBorrowedFormulas MAth= new  MaxmumAmountBorrowedFormulas();
//     Amortization amortize = new Amortization();

     Calendar calN = Calendar.getInstance();
fileInputOutPutStreams fios= new fileInputOutPutStreams();
    Formartter ffm= new Formartter();
    Date Trndate,valuedate;
   ArrayList<String> data4, column1;
 ArrayList<ArrayList<String>> data5;
          JOptionPane p;
  SimpleDateFormat df;

   double newbalance,ledgerBalance,creditamount;
    GregorianCalendar cal = new GregorianCalendar(); 
   JdbcConnector csx = new JdbcConnector(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "AccountDBDetails.txt"))); 
    DatabaseQuaries quaries =new DatabaseQuaries();
ReportsDatabase rdb = new ReportsDatabase();
 int   pagenumber=0;
   PdfPTable body;
   String returnvalue="";
     String userId;
   public void setUserID(String userid){
this.userId=userid;
}
    public String theNetProfits(String startDate, String endDate,Component c){
     PdfPTable table = new PdfPTable(6);
       table.setWidthPercentage(100);
       float[] columnWidths = {10f, 130f, 3f,30f, 10f,10f};
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
     
Map<Integer,Double> TotalHolder= new HashMap<>();
  
  int i=0;boolean mainIncome=false,indicatorthIncome=false,indicatorInsurance=false,openingStock=false,purchases=false,directcost=false,factoryOver=false,closingStock=false,operatingEXP=false,adminExp=false,employExp=false,Insurance=false,finExp=false,general=false,incomeTax=false, proposedDev=false,balOfProfit=false;
    boolean indReve=false,indReve1=false,indReve2=false,indReve3=false,indReveFirst=false;
    boolean indExpe=false,indExpe1=false,indExpe2=false,indExpe3=false, indExpe4=false,indExpe5=false,indExpe6=false,indExpe7=false,indExpe8=false,indExpe9=false,indExpe10=false;          
  while (i<=30){
  TotalHolder.put(i, 0.0);
  i++;
  }

      
      Map<Integer, String> level3r=rdb. getLevel3Items("Revenues");
      
      
            int xr=0;
        while(xr<=level3r.size()-1){
        switch(level3r.get(xr)){
       case "Revenue/Income":
           
           
       Map<Integer, String> level4r=rdb.getLevel4Items("Revenue/Income");   
              int yr=0;
           while(yr<=level4r.size()-1){
        switch(level4r.get(yr)){
        case "Main Income":
           
           
       Map<Integer, String> level5r=rdb.getLevel5Items("Main Income"); 
                int zr=0; double sumr=0.0; 
              while(zr<=level5r.size()-1){
      
        if(parseDouble(rdb.getBalances(level5r.get(zr),startDate, endDate,c).get(level5r.get(zr)).toString())!=0.0){
             if(!indReveFirst){
            PdfPCell cellLeve2r= new PdfPCell(new Paragraph("Revenues",font2));
       cellLeve2r.setColspan(6);
      cellLeve2r.setFixedHeight(16.2f);
      cellLeve2r.disableBorderSide(LEFT);
      cellLeve2r.disableBorderSide(TOP);
      cellLeve2r.disableBorderSide(RIGHT);
       cellLeve2r.setPadding(.5f);
       cellLeve2r.setBorderColorBottom(BaseColor.BLACK);
       cellLeve2r.setBorderColorTop(BaseColor.WHITE);
       cellLeve2r.setBorderColorLeft(BaseColor.WHITE);
       cellLeve2r.setBorderColorRight(BaseColor.WHITE);
       cellLeve2r.setVerticalAlignment(Element.ALIGN_TOP);
               table.addCell(cellLeve2r);
         indReveFirst=true;
           }
            if(indReve==false){
            PdfPCell cellLeve3r= new PdfPCell(new Paragraph("Revenue/Income",font2));
        cellLeve3r.setColspan(6);
      cellLeve3r.setFixedHeight(16.2f);
      cellLeve3r.disableBorderSide(LEFT);
      cellLeve3r.disableBorderSide(TOP);
      cellLeve3r.disableBorderSide(RIGHT);
      cellLeve3r.disableBorderSide(BOTTOM);
      cellLeve3r.setPadding(.5f);
      cellLeve3r.setBorderColorBottom(BaseColor.WHITE);
      cellLeve3r.setBorderColorTop(BaseColor.WHITE);
      cellLeve3r.setBorderColorLeft(BaseColor.WHITE);
      cellLeve3r.setBorderColorRight(BaseColor.WHITE);
      cellLeve3r.setVerticalAlignment(Element.ALIGN_TOP);
      table.addCell(cellLeve3r);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
        indReve=true;    
            }
            
         if(level5r.size()>0){
            if(!indReve1){
          
         table.addCell(cellEmpty);
     PdfPCell cellLeve4r= new PdfPCell(new Paragraph("Main Income",font2));
     cellLeve4r.setColspan(5);
      cellLeve4r.setFixedHeight(16.2f);
      cellLeve4r.disableBorderSide(LEFT);
      cellLeve4r.disableBorderSide(TOP);
      cellLeve4r.disableBorderSide(RIGHT);
      cellLeve4r.disableBorderSide(BOTTOM);
      cellLeve4r.setPadding(.5f);
      cellLeve4r.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4r.setBorderColorTop(BaseColor.WHITE);
      cellLeve4r.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4r.setBorderColorRight(BaseColor.WHITE);
      cellLeve4r.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cellLeve4r);
            
      indReve1=true;
      }
        }
      table.addCell(cellEmpty);
      PdfPCell cellLeve5= new PdfPCell(new Paragraph(level5r.get(zr),font1));
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
      PdfPCell balAmountr=null;
      double amountr=parseDouble(rdb.getBalances(level5r.get(zr),startDate, endDate,c).get(level5r.get(zr)).toString());
      if(amountr<0.0){
       balAmountr= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(amountr+"")+")",font1));
      } else{
      
       balAmountr= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(amountr+""),font1));
      
      }
     
      balAmountr.setFixedHeight(16.2f);
      balAmountr.disableBorderSide(LEFT);
      balAmountr.disableBorderSide(TOP);
      balAmountr.disableBorderSide(RIGHT);
      balAmountr.disableBorderSide(BOTTOM);
      balAmountr.setPadding(.5f);
      balAmountr.setBorderColorBottom(BaseColor.WHITE);
      balAmountr.setBorderColorTop(BaseColor.WHITE);
      balAmountr.setBorderColorLeft(BaseColor.WHITE);
      balAmountr.setBorderColorRight(BaseColor.WHITE);
      balAmountr.setHorizontalAlignment(Element.ALIGN_CENTER);
     
     table.addCell(balAmountr);
      table.addCell(cellEmpty);
     
      table.addCell(cellEmpty);
       if((level5r.size()<=1)){
       table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
       }
       sumr= sumr+(parseDouble(rdb.getBalances(level5r.get(zr),startDate, endDate,c).get(level5r.get(zr)).toString()));    
       mainIncome=true;
           }
          
           zr++; 
                 }
               TotalHolder.put(0, sumr);
              if(mainIncome){
                  if((level5r.size()>1)){
             table.addCell(cellEmpty);
          PdfPCell totalValue= new PdfPCell(new Paragraph("Total Value of Main Income",font3));
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
       if(TotalHolder.get(0)<0.0){
      totalAmount= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(0)+"")+")",font1));
       }else{
       
      totalAmount= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(0)+""),font1)); 
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
              }
              break;
         case   "Other Income":
             
              
         int z1o=0; double sum1o=0.0;
         
       Map<Integer, String> level5ao=rdb.getLevel5Items("Other Income"); 
      
     
              while(z1o<=level5ao.size()-1){
      
        if(parseDouble(rdb.getBalances(level5ao.get(z1o),startDate, endDate,c).get(level5ao.get(z1o)).toString())!=0.0){
            if(!indReveFirst){
            PdfPCell cellLeve2r= new PdfPCell(new Paragraph("Revenues",font2));
       cellLeve2r.setColspan(6);
      cellLeve2r.setFixedHeight(16.2f);
      cellLeve2r.disableBorderSide(LEFT);
      cellLeve2r.disableBorderSide(TOP);
      cellLeve2r.disableBorderSide(RIGHT);
       cellLeve2r.setPadding(.5f);
       cellLeve2r.setBorderColorBottom(BaseColor.BLACK);
       cellLeve2r.setBorderColorTop(BaseColor.WHITE);
       cellLeve2r.setBorderColorLeft(BaseColor.WHITE);
       cellLeve2r.setBorderColorRight(BaseColor.WHITE);
       cellLeve2r.setVerticalAlignment(Element.ALIGN_TOP);
               table.addCell(cellLeve2r);
         indReveFirst=true;
           }
            if(!indReve){
            PdfPCell cellLeve3r= new PdfPCell(new Paragraph("Revenue/Income",font2));
        cellLeve3r.setColspan(6);
      cellLeve3r.setFixedHeight(16.2f);
      cellLeve3r.disableBorderSide(LEFT);
      cellLeve3r.disableBorderSide(TOP);
      cellLeve3r.disableBorderSide(RIGHT);
      cellLeve3r.disableBorderSide(BOTTOM);
      cellLeve3r.setPadding(.5f);
      cellLeve3r.setBorderColorBottom(BaseColor.WHITE);
      cellLeve3r.setBorderColorTop(BaseColor.WHITE);
      cellLeve3r.setBorderColorLeft(BaseColor.WHITE);
      cellLeve3r.setBorderColorRight(BaseColor.WHITE);
      cellLeve3r.setVerticalAlignment(Element.ALIGN_TOP);
      table.addCell(cellLeve3r);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
        indReve=true;    
            }
     if(!indReve2){
         if(level5ao.size()>1){
      table.addCell(cellEmpty);
     PdfPCell cellLeve4ao= new PdfPCell(new Paragraph("Other Income",font2));
     cellLeve4ao.setColspan(5);
      cellLeve4ao.setFixedHeight(16.2f);
      cellLeve4ao.disableBorderSide(LEFT);
      cellLeve4ao.disableBorderSide(TOP);
      cellLeve4ao.disableBorderSide(RIGHT);
      cellLeve4ao.disableBorderSide(BOTTOM);
      cellLeve4ao.setPadding(.5f);
      cellLeve4ao.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4ao.setBorderColorTop(BaseColor.WHITE);
      cellLeve4ao.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4ao.setBorderColorRight(BaseColor.WHITE);
      cellLeve4ao.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cellLeve4ao);
         }
       indReve2=true;
     }
      table.addCell(cellEmpty);
      PdfPCell cellLeve5= new PdfPCell(new Paragraph(level5ao.get(z1o),font1));
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
     
     PdfPCell balAmounto=null;
      double amounto=parseDouble(rdb.getBalances(level5ao.get(z1o),startDate, endDate,c).get(level5ao.get(z1o)).toString());
      if(amounto<0.0){
       balAmounto= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(amounto+"")+")",font1));
      } else{
      
       balAmounto= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(amounto+""),font1));
      }
      balAmounto.setFixedHeight(16.2f);
      balAmounto.disableBorderSide(LEFT);
      balAmounto.disableBorderSide(TOP);
      balAmounto.disableBorderSide(RIGHT);
      balAmounto.disableBorderSide(BOTTOM);
      balAmounto.setPadding(.5f);
      balAmounto.setBorderColorBottom(BaseColor.WHITE);
      balAmounto.setBorderColorTop(BaseColor.WHITE);
      balAmounto.setBorderColorLeft(BaseColor.WHITE);
      balAmounto.setBorderColorRight(BaseColor.WHITE);
      balAmounto.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(balAmounto);
     
      table.addCell(cellEmpty);
     
      table.addCell(cellEmpty);
             if((level5ao.size()<=1)){
       table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
       }
       indicatorthIncome=true;
        }
       sum1o= sum1o+(parseDouble(rdb.getBalances(level5ao.get(z1o),startDate, endDate,c).get(level5ao.get(z1o)).toString()));   
        z1o++; 
              
              
              
              }
              TotalHolder.put(1, sum1o);
              if(indicatorthIncome){
                   if((level5ao.size()>1)){
               
             table.addCell(cellEmpty);
          PdfPCell totalValueInvetments= new PdfPCell(new Paragraph("Total Value of Other Income",font3));
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
      
      PdfPCell totalAmountInvesto=null;
              double totalAmountInvo=0.0;
              totalAmountInvo=TotalHolder.get(1);
              if(totalAmountInvo<0.0){
            totalAmountInvesto   = new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(totalAmountInvo+"")+")",font1));
              
              } else{
          totalAmountInvesto   = new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(totalAmountInvo+""),font1));    
              }
     
      
      totalAmountInvesto.setFixedHeight(16.2f);
      totalAmountInvesto.disableBorderSide(LEFT);
      totalAmountInvesto.disableBorderSide(RIGHT);
      totalAmountInvesto.setPadding(.5f);
      totalAmountInvesto.setBorderColorBottom(BaseColor.BLACK);
      totalAmountInvesto.setBorderColorTop(BaseColor.BLACK);
      totalAmountInvesto.setBorderColorLeft(BaseColor.WHITE);
      totalAmountInvesto.setBorderColorRight(BaseColor.WHITE);
      totalAmountInvesto.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(totalAmountInvesto);
      table.addCell(cellEmpty); 
       table.addCell(cellEmpty);
        table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
              }             
              }          
         break; 
             
             
        case   "Income From Short Term Insurance Business":
             
         int z1i=0; double sum1i=0.0;
       Map<Integer, String> level5ai=rdb.getLevel5Items("Income From Short Term Insurance Business"); 
      
     
              while(z1i<=level5ai.size()-1){
      
        if(parseDouble(rdb.getBalances(level5ai.get(z1i),startDate, endDate,c).get(level5ai.get(z1i)).toString())!=0.0){
            if(!indReveFirst){
            PdfPCell cellLeve2r= new PdfPCell(new Paragraph("Revenues",font2));
       cellLeve2r.setColspan(6);
      cellLeve2r.setFixedHeight(16.2f);
      cellLeve2r.disableBorderSide(LEFT);
      cellLeve2r.disableBorderSide(TOP);
      cellLeve2r.disableBorderSide(RIGHT);
       cellLeve2r.setPadding(.5f);
       cellLeve2r.setBorderColorBottom(BaseColor.BLACK);
       cellLeve2r.setBorderColorTop(BaseColor.WHITE);
       cellLeve2r.setBorderColorLeft(BaseColor.WHITE);
       cellLeve2r.setBorderColorRight(BaseColor.WHITE);
       cellLeve2r.setVerticalAlignment(Element.ALIGN_TOP);
               table.addCell(cellLeve2r);
         indReveFirst=true;
           }
            if(!indReve){
            PdfPCell cellLeve3r= new PdfPCell(new Paragraph("Revenue/Income",font2));
        cellLeve3r.setColspan(6);
      cellLeve3r.setFixedHeight(16.2f);
      cellLeve3r.disableBorderSide(LEFT);
      cellLeve3r.disableBorderSide(TOP);
      cellLeve3r.disableBorderSide(RIGHT);
      cellLeve3r.disableBorderSide(BOTTOM);
      cellLeve3r.setPadding(.5f);
      cellLeve3r.setBorderColorBottom(BaseColor.WHITE);
      cellLeve3r.setBorderColorTop(BaseColor.WHITE);
      cellLeve3r.setBorderColorLeft(BaseColor.WHITE);
      cellLeve3r.setBorderColorRight(BaseColor.WHITE);
      cellLeve3r.setVerticalAlignment(Element.ALIGN_TOP);
      table.addCell(cellLeve3r);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
        indReve=true;    
            }
     if(!indReve3){
           if(level5ai.size()>1){
      table.addCell(cellEmpty);
     PdfPCell cellLeve4ai= new PdfPCell(new Paragraph("Income From Short Term Insurance Business",font2));
     cellLeve4ai.setColspan(5);
      cellLeve4ai.setFixedHeight(16.2f);
      cellLeve4ai.disableBorderSide(LEFT);
      cellLeve4ai.disableBorderSide(TOP);
      cellLeve4ai.disableBorderSide(RIGHT);
      cellLeve4ai.disableBorderSide(BOTTOM);
      cellLeve4ai.setPadding(.5f);
      cellLeve4ai.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4ai.setBorderColorTop(BaseColor.WHITE);
      cellLeve4ai.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4ai.setBorderColorRight(BaseColor.WHITE);
      cellLeve4ai.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cellLeve4ai);
           } 
       indReve3=true;
         
     }
      table.addCell(cellEmpty);
      PdfPCell cellLeve5= new PdfPCell(new Paragraph(level5ai.get(z1i),font1));
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
     
     PdfPCell balAmounti=null;
      double amounti=parseDouble(rdb.getBalances(level5ai.get(z1i),startDate, endDate,c).get(level5ai.get(z1i)).toString());
      if(amounti<0.0){
       balAmounti= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(amounti+"")+")",font1));
      } else{
      
       balAmounti= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(amounti+""),font1));
      }
      balAmounti.setFixedHeight(16.2f);
      balAmounti.disableBorderSide(LEFT);
      balAmounti.disableBorderSide(TOP);
      balAmounti.disableBorderSide(RIGHT);
      balAmounti.disableBorderSide(BOTTOM);
      balAmounti.setPadding(.5f);
      balAmounti.setBorderColorBottom(BaseColor.WHITE);
      balAmounti.setBorderColorTop(BaseColor.WHITE);
      balAmounti.setBorderColorLeft(BaseColor.WHITE);
      balAmounti.setBorderColorRight(BaseColor.WHITE);
      balAmounti.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(balAmounti);
     
      table.addCell(cellEmpty);
     
      table.addCell(cellEmpty);
          if((level5ai.size()<=1)){
       table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
       }
       indicatorInsurance=true;
        }
       sum1i= sum1i+(parseDouble(rdb.getBalances(level5ai.get(z1i),startDate, endDate,c).get(level5ai.get(z1i)).toString()));   
        z1i++; 
              
              
              
              } TotalHolder.put(2, sum1i);
              if(indicatorInsurance){
                     if((level5ai.size()>1)){
             table.addCell(cellEmpty);
          PdfPCell totalValueInvetments= new PdfPCell(new Paragraph("Total Income from Insurance Business",font3));
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
              totalAmountInv=TotalHolder.get(2);
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
              }   }          
                        
         break; 
             
           
           }
          
          yr++; 
           }
        
           if(mainIncome||indicatorthIncome||indicatorInsurance){
           double grossIncome=0.0;
      
          
           grossIncome=MAth.add(TotalHolder.get(0), MAth.add(TotalHolder.get(1),TotalHolder.get(2)));
    
       TotalHolder.put(3, grossIncome);
       table.addCell(cellEmpty);
     PdfPCell cellLeve4a1= new PdfPCell(new Paragraph("Gross Income",font4));
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
       if(TotalHolder.get(3)<0.0){
        totalAmountInvests= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(3)+"")+")",font4));
       }else{ totalAmountInvests= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(3)+""),font4));}
       
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
   
        }
        xr++;
        }  

      
       
      Map<Integer, String> level3Lr=rdb. getLevel3Items("Expenses");
       int xLr=0; 
        while(xLr<=level3Lr.size()-1){
        switch(level3Lr.get(xLr)){
            
       case "Cost Of Operation":
           
           int yLr=0;
       Map<Integer, String> level4Lr=rdb.getLevel4Items("Cost Of Operation");   
    
           while(yLr<=level4Lr.size()-1){
        switch(level4Lr.get(yLr)){
            
    case "Opening Stock":
       
            int zLr=0; double sumLr=0.0; boolean indExpe25=false;
       Map<Integer, String> level5Lr=rdb.getLevel5Items("Opening Stock"); 

     
              while(zLr<=level5Lr.size()-1){
      
        if(parseDouble(rdb.getBalances(level5Lr.get(zLr),startDate, endDate,c).get(level5Lr.get(zLr)).toString())!=0.0){
            if(!indExpe){
           PdfPCell cellLeve2Lr= new PdfPCell(new Paragraph("Expenses",font2));
       cellLeve2Lr.setColspan(6);
      cellLeve2Lr.setFixedHeight(16.2f);
      cellLeve2Lr.disableBorderSide(LEFT);
      cellLeve2Lr.disableBorderSide(TOP);
      cellLeve2Lr.disableBorderSide(RIGHT);
       cellLeve2Lr.setPadding(.5f);
       cellLeve2Lr.setBorderColorBottom(BaseColor.BLACK);
       cellLeve2Lr.setBorderColorTop(BaseColor.WHITE);
       cellLeve2Lr.setBorderColorLeft(BaseColor.WHITE);
       cellLeve2Lr.setBorderColorRight(BaseColor.WHITE);
       cellLeve2Lr.setVerticalAlignment(Element.ALIGN_TOP);
        table.addCell(cellLeve2Lr);
      indExpe=true;
           }
              if(!indExpe2){
          PdfPCell cellLeve3Lr= new PdfPCell(new Paragraph("Cost Of Operation",font2));
        cellLeve3Lr.setColspan(6);
      cellLeve3Lr.setFixedHeight(16.2f);
      cellLeve3Lr.disableBorderSide(LEFT);
      cellLeve3Lr.disableBorderSide(TOP);
      cellLeve3Lr.disableBorderSide(RIGHT);
      cellLeve3Lr.disableBorderSide(BOTTOM);
      cellLeve3Lr.setPadding(.5f);
      cellLeve3Lr.setBorderColorBottom(BaseColor.WHITE);
      cellLeve3Lr.setBorderColorTop(BaseColor.WHITE);
      cellLeve3Lr.setBorderColorLeft(BaseColor.WHITE);
      cellLeve3Lr.setBorderColorRight(BaseColor.WHITE);
      cellLeve3Lr.setVerticalAlignment(Element.ALIGN_TOP);
      table.addCell(cellLeve3Lr);
  
      indExpe2=true;
           }
            if(!indExpe25){
                if(level5Lr.size()>1){
     table.addCell(cellEmpty);
     PdfPCell cellLeve4Lr= new PdfPCell(new Paragraph("Opening Stock",font2));
     cellLeve4Lr.setColspan(5);
      cellLeve4Lr.setFixedHeight(16.2f);
      cellLeve4Lr.disableBorderSide(LEFT);
      cellLeve4Lr.disableBorderSide(TOP);
      cellLeve4Lr.disableBorderSide(RIGHT);
      cellLeve4Lr.disableBorderSide(BOTTOM);
      cellLeve4Lr.setPadding(.5f);
      cellLeve4Lr.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4Lr.setBorderColorTop(BaseColor.WHITE);
      cellLeve4Lr.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4Lr.setBorderColorRight(BaseColor.WHITE);
      cellLeve4Lr.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4Lr);
                }
      indExpe25=true;
           }
        
      table.addCell(cellEmpty);
      PdfPCell cellLeve5L= new PdfPCell(new Paragraph(level5Lr.get(zLr),font1));
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
   
      PdfPCell balAmountLr=null;
      double amountLr=parseDouble(rdb.getBalances(level5Lr.get(zLr),startDate, endDate,c).get(level5Lr.get(zLr)).toString());
      if(amountLr<0.0){
       balAmountLr= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(amountLr+"")+")",font1));
      } else{
      
       balAmountLr= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(amountLr+""),font1));
      
      }
     
      balAmountLr.setFixedHeight(16.2f);
      balAmountLr.disableBorderSide(LEFT);
      balAmountLr.disableBorderSide(TOP);
      balAmountLr.disableBorderSide(RIGHT);
      balAmountLr.disableBorderSide(BOTTOM);
      balAmountLr.setPadding(.5f);
      balAmountLr.setBorderColorBottom(BaseColor.WHITE);
      balAmountLr.setBorderColorTop(BaseColor.WHITE);
      balAmountLr.setBorderColorLeft(BaseColor.WHITE);
      balAmountLr.setBorderColorRight(BaseColor.WHITE);
      balAmountLr.setHorizontalAlignment(Element.ALIGN_CENTER);
     
     table.addCell(balAmountLr);
      table.addCell(cellEmpty);
     
      table.addCell(cellEmpty);
             if((level5Lr.size()<=1)){
       table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
       }
       sumLr= sumLr+(parseDouble(rdb.getBalances(level5Lr.get(zLr),startDate, endDate,c).get(level5Lr.get(zLr)).toString()));     
       openingStock=true; 
        }
      
           zLr++; 
                 }TotalHolder.put(4, sumLr);
              if(openingStock){
                   if((level5Lr.size()>1)){
             table.addCell(cellEmpty);
          PdfPCell totalValue= new PdfPCell(new Paragraph("Total Value of Opening Stock",font3));
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
     if(TotalHolder.get(4)<0.0)
     {totalAmount= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(4)+"")+")",font1));
     } else{totalAmount= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(4)+""),font1));}
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
              }}
              break;
            
    
      case   "Purchases":
             
        
       Map<Integer, String> level5aLp=rdb.getLevel5Items("Purchases"); 
      
      int z1Lp=0; double sum1Lp=0.0; boolean indExpe24=false;
              while(z1Lp<=level5aLp.size()-1){
      
        if(parseDouble(rdb.getBalances(level5aLp.get(z1Lp),startDate, endDate,c).get(level5aLp.get(z1Lp)).toString())!=0.0){
           if(!indExpe){
           PdfPCell cellLeve2Lr= new PdfPCell(new Paragraph("Expenses",font2));
       cellLeve2Lr.setColspan(6);
      cellLeve2Lr.setFixedHeight(16.2f);
      cellLeve2Lr.disableBorderSide(LEFT);
      cellLeve2Lr.disableBorderSide(TOP);
      cellLeve2Lr.disableBorderSide(RIGHT);
       cellLeve2Lr.setPadding(.5f);
       cellLeve2Lr.setBorderColorBottom(BaseColor.BLACK);
       cellLeve2Lr.setBorderColorTop(BaseColor.WHITE);
       cellLeve2Lr.setBorderColorLeft(BaseColor.WHITE);
       cellLeve2Lr.setBorderColorRight(BaseColor.WHITE);
       cellLeve2Lr.setVerticalAlignment(Element.ALIGN_TOP);
        table.addCell(cellLeve2Lr);
      indExpe=true;
           }
           if(!indExpe2){
          PdfPCell cellLeve3Lr= new PdfPCell(new Paragraph("Cost Of Operation",font2));
        cellLeve3Lr.setColspan(6);
      cellLeve3Lr.setFixedHeight(16.2f);
      cellLeve3Lr.disableBorderSide(LEFT);
      cellLeve3Lr.disableBorderSide(TOP);
      cellLeve3Lr.disableBorderSide(RIGHT);
      cellLeve3Lr.disableBorderSide(BOTTOM);
      cellLeve3Lr.setPadding(.5f);
      cellLeve3Lr.setBorderColorBottom(BaseColor.WHITE);
      cellLeve3Lr.setBorderColorTop(BaseColor.WHITE);
      cellLeve3Lr.setBorderColorLeft(BaseColor.WHITE);
      cellLeve3Lr.setBorderColorRight(BaseColor.WHITE);
      cellLeve3Lr.setVerticalAlignment(Element.ALIGN_TOP);
      table.addCell(cellLeve3Lr);
     
      indExpe2=true;
           }
      if(!indExpe24){
          if(level5aLp.size()>1){
     table.addCell(cellEmpty);
     PdfPCell cellLeve4aLp= new PdfPCell(new Paragraph("Purchases",font2));
     cellLeve4aLp.setColspan(5);
      cellLeve4aLp.setFixedHeight(16.2f);
      cellLeve4aLp.disableBorderSide(LEFT);
      cellLeve4aLp.disableBorderSide(TOP);
      cellLeve4aLp.disableBorderSide(RIGHT);
      cellLeve4aLp.disableBorderSide(BOTTOM);
      cellLeve4aLp.setPadding(.5f);
      cellLeve4aLp.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4aLp.setBorderColorTop(BaseColor.WHITE);
      cellLeve4aLp.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4aLp.setBorderColorRight(BaseColor.WHITE);
      cellLeve4aLp.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4aLp);
          }
      indExpe24=true;
           }
      table.addCell(cellEmpty);
      PdfPCell cellLeve5L= new PdfPCell(new Paragraph(level5aLp.get(z1Lp),font1));
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
     
     PdfPCell balAmountLp=null;
      double amountLp=parseDouble(rdb.getBalances(level5aLp.get(z1Lp),startDate, endDate,c).get(level5aLp.get(z1Lp)).toString());
      if(amountLp<0.0){
       balAmountLp= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(amountLp+"")+")",font1));
      } else{
      
       balAmountLp= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(amountLp+""),font1));
      }
      balAmountLp.setFixedHeight(16.2f);
      balAmountLp.disableBorderSide(LEFT);
      balAmountLp.disableBorderSide(TOP);
      balAmountLp.disableBorderSide(RIGHT);
      balAmountLp.disableBorderSide(BOTTOM);
      balAmountLp.setPadding(.5f);
      balAmountLp.setBorderColorBottom(BaseColor.WHITE);
      balAmountLp.setBorderColorTop(BaseColor.WHITE);
      balAmountLp.setBorderColorLeft(BaseColor.WHITE);
      balAmountLp.setBorderColorRight(BaseColor.WHITE);
      balAmountLp.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(balAmountLp);
     
      table.addCell(cellEmpty);
     
      table.addCell(cellEmpty);
           if((level5aLp.size()<=1)){
       table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
       }
        purchases=true;
       
        }
       sum1Lp= sum1Lp+(parseDouble(rdb.getBalances(level5aLp.get(z1Lp),startDate, endDate,c).get(level5aLp.get(z1Lp)).toString()));   
        z1Lp++; } TotalHolder.put(5, sum1Lp);
              if(purchases){
                    if((level5aLp.size()>1)){
              
             table.addCell(cellEmpty);
          PdfPCell totalValueInvetmentsP= new PdfPCell(new Paragraph("Total Value of Purchases",font3));
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
              totalAmountInvP=TotalHolder.get(5);
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
       table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
              }     }     
                        
         break; 
          case   "Other Direct Costs":
                 
         int z1Ldcr=0; double sum1Ldcr=0.0; boolean indExpe23=false;
       Map<Integer, String> level5aLdcr=rdb.getLevel5Items("Other Direct Costs"); 
      
      
              while(z1Ldcr<=level5aLdcr.size()-1){
      
        if(parseDouble(rdb.getBalances(level5aLdcr.get(z1Ldcr),startDate, endDate,c).get(level5aLdcr.get(z1Ldcr)).toString())!=0.0){
             if(!indExpe){
           PdfPCell cellLeve2Lr= new PdfPCell(new Paragraph("Expenses",font2));
       cellLeve2Lr.setColspan(6);
      cellLeve2Lr.setFixedHeight(16.2f);
      cellLeve2Lr.disableBorderSide(LEFT);
      cellLeve2Lr.disableBorderSide(TOP);
      cellLeve2Lr.disableBorderSide(RIGHT);
       cellLeve2Lr.setPadding(.5f);
       cellLeve2Lr.setBorderColorBottom(BaseColor.BLACK);
       cellLeve2Lr.setBorderColorTop(BaseColor.WHITE);
       cellLeve2Lr.setBorderColorLeft(BaseColor.WHITE);
       cellLeve2Lr.setBorderColorRight(BaseColor.WHITE);
       cellLeve2Lr.setVerticalAlignment(Element.ALIGN_TOP);
        table.addCell(cellLeve2Lr);
      indExpe=true;
           }
               if(!indExpe2){
          PdfPCell cellLeve3Lr= new PdfPCell(new Paragraph("Cost Of Operation",font2));
        cellLeve3Lr.setColspan(6);
      cellLeve3Lr.setFixedHeight(16.2f);
      cellLeve3Lr.disableBorderSide(LEFT);
      cellLeve3Lr.disableBorderSide(TOP);
      cellLeve3Lr.disableBorderSide(RIGHT);
      cellLeve3Lr.disableBorderSide(BOTTOM);
      cellLeve3Lr.setPadding(.5f);
      cellLeve3Lr.setBorderColorBottom(BaseColor.WHITE);
      cellLeve3Lr.setBorderColorTop(BaseColor.WHITE);
      cellLeve3Lr.setBorderColorLeft(BaseColor.WHITE);
      cellLeve3Lr.setBorderColorRight(BaseColor.WHITE);
      cellLeve3Lr.setVerticalAlignment(Element.ALIGN_TOP);
      table.addCell(cellLeve3Lr);

      indExpe2=true;
           }
     if(!indExpe23){
         if(level5aLdcr.size()>1){
     table.addCell(cellEmpty);
     PdfPCell cellLeve4aLdcr= new PdfPCell(new Paragraph("Other Direct Costs",font2));
     cellLeve4aLdcr.setColspan(5);
      cellLeve4aLdcr.setFixedHeight(16.2f);
      cellLeve4aLdcr.disableBorderSide(LEFT);
      cellLeve4aLdcr.disableBorderSide(TOP);
      cellLeve4aLdcr.disableBorderSide(RIGHT);
      cellLeve4aLdcr.disableBorderSide(BOTTOM);
      cellLeve4aLdcr.setPadding(.5f);
      cellLeve4aLdcr.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4aLdcr.setBorderColorTop(BaseColor.WHITE);
      cellLeve4aLdcr.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4aLdcr.setBorderColorRight(BaseColor.WHITE);
      cellLeve4aLdcr.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4aLdcr);
         }
      indExpe23=true;
           }
      table.addCell(cellEmpty);
      PdfPCell cellLeve5L= new PdfPCell(new Paragraph(level5aLdcr.get(z1Ldcr),font1));
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
      double amountL=parseDouble(rdb.getBalances(level5aLdcr.get(z1Ldcr),startDate, endDate,c).get(level5aLdcr.get(z1Ldcr)).toString());
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
      
          if((level5aLdcr.size()<=1)){
       table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
       }
        directcost=true;
       
        }
       sum1Ldcr= sum1Ldcr+(parseDouble(rdb.getBalances(level5aLdcr.get(z1Ldcr),startDate, endDate,c).get(level5aLdcr.get(z1Ldcr)).toString()));   
        z1Ldcr++; } TotalHolder.put(6, sum1Ldcr);
              if(directcost){
                       if((level5aLdcr.size()>1)){
              
             table.addCell(cellEmpty);
          PdfPCell totalValueInvetmentsP= new PdfPCell(new Paragraph("Total Value of Other Direct Cost",font3));
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
              totalAmountInvP=TotalHolder.get(6);
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
            table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
              }    }      
                        
         break;  
        case   "Factory Overheads":
               
         int z1Ldcs=0; double sum1Ldcs=0.0; boolean indExpe22=false;
       Map<Integer, String> level5aLdcs=rdb.getLevel5Items("Factory Overheads"); 
      
     
              while(z1Ldcs<=level5aLdcs.size()-1){
      
        if(parseDouble(rdb.getBalances(level5aLdcs.get(z1Ldcs),startDate, endDate,c).get(level5aLdcs.get(z1Ldcs)).toString())!=0.0){
            if(!indExpe){
           PdfPCell cellLeve2Lr= new PdfPCell(new Paragraph("Expenses",font2));
       cellLeve2Lr.setColspan(6);
      cellLeve2Lr.setFixedHeight(16.2f);
      cellLeve2Lr.disableBorderSide(LEFT);
      cellLeve2Lr.disableBorderSide(TOP);
      cellLeve2Lr.disableBorderSide(RIGHT);
       cellLeve2Lr.setPadding(.5f);
       cellLeve2Lr.setBorderColorBottom(BaseColor.BLACK);
       cellLeve2Lr.setBorderColorTop(BaseColor.WHITE);
       cellLeve2Lr.setBorderColorLeft(BaseColor.WHITE);
       cellLeve2Lr.setBorderColorRight(BaseColor.WHITE);
       cellLeve2Lr.setVerticalAlignment(Element.ALIGN_TOP);
        table.addCell(cellLeve2Lr);
      indExpe=true;
           }
            if(!indExpe2){
          PdfPCell cellLeve3Lr= new PdfPCell(new Paragraph("Cost Of Operation",font2));
        cellLeve3Lr.setColspan(6);
      cellLeve3Lr.setFixedHeight(16.2f);
      cellLeve3Lr.disableBorderSide(LEFT);
      cellLeve3Lr.disableBorderSide(TOP);
      cellLeve3Lr.disableBorderSide(RIGHT);
      cellLeve3Lr.disableBorderSide(BOTTOM);
      cellLeve3Lr.setPadding(.5f);
      cellLeve3Lr.setBorderColorBottom(BaseColor.WHITE);
      cellLeve3Lr.setBorderColorTop(BaseColor.WHITE);
      cellLeve3Lr.setBorderColorLeft(BaseColor.WHITE);
      cellLeve3Lr.setBorderColorRight(BaseColor.WHITE);
      cellLeve3Lr.setVerticalAlignment(Element.ALIGN_TOP);
      table.addCell(cellLeve3Lr);
  
      indExpe2=true;
           }
      if(!indExpe22){
          if(level5aLdcs.size()>1){
      table.addCell(cellEmpty);
     PdfPCell cellLeve4aLdc= new PdfPCell(new Paragraph("Factory Overheads",font2));
     cellLeve4aLdc.setColspan(5);
      cellLeve4aLdc.setFixedHeight(16.2f);
      cellLeve4aLdc.disableBorderSide(LEFT);
      cellLeve4aLdc.disableBorderSide(TOP);
      cellLeve4aLdc.disableBorderSide(RIGHT);
      cellLeve4aLdc.disableBorderSide(BOTTOM);
      cellLeve4aLdc.setPadding(.5f);
      cellLeve4aLdc.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4aLdc.setBorderColorTop(BaseColor.WHITE);
      cellLeve4aLdc.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4aLdc.setBorderColorRight(BaseColor.WHITE);
      cellLeve4aLdc.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4aLdc);
          }
      indExpe22=true;
           }
      table.addCell(cellEmpty);
      PdfPCell cellLeve5L= new PdfPCell(new Paragraph(level5aLdcs.get(z1Ldcs),font1));
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
      double amountL=parseDouble(rdb.getBalances(level5aLdcs.get(z1Ldcs),startDate, endDate,c).get(level5aLdcs.get(z1Ldcs)).toString());
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
      
          if((level5aLdcs.size()<=1)){
       table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
       }
        factoryOver=true;
      
        }
      sum1Ldcs= sum1Ldcs+(parseDouble(rdb.getBalances(level5aLdcs.get(z1Ldcs),startDate, endDate,c).get(level5aLdcs.get(z1Ldcs)).toString()));   
       z1Ldcs++; } TotalHolder.put(7, sum1Ldcs);
              if(factoryOver){
                   if((level5aLdcs.size()>1)){
              
             table.addCell(cellEmpty);
          PdfPCell totalValueInvetmentsP= new PdfPCell(new Paragraph("Total Value of Factory Over Heads",font3));
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
              totalAmountInvP=TotalHolder.get(7);
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
          table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
              }          }
                        
         break;     
           
                   case "Closing Stock": 
                       
                      
         int z1Ldc=0; double sum1Ldc=0.0;boolean indExpe21=false;
       Map<Integer, String> level5aLdc=rdb.getLevel5Items("Closing Stock"); 
      
     
              while(z1Ldc<=level5aLdc.size()-1){
      
        if(parseDouble(rdb.getBalances(level5aLdc.get(z1Ldc),startDate, endDate,c).get(level5aLdc.get(z1Ldc)).toString())!=0.0){
            if(!indExpe){
           PdfPCell cellLeve2Lr= new PdfPCell(new Paragraph("Expenses",font2));
       cellLeve2Lr.setColspan(6);
      cellLeve2Lr.setFixedHeight(16.2f);
      cellLeve2Lr.disableBorderSide(LEFT);
      cellLeve2Lr.disableBorderSide(TOP);
      cellLeve2Lr.disableBorderSide(RIGHT);
       cellLeve2Lr.setPadding(.5f);
       cellLeve2Lr.setBorderColorBottom(BaseColor.BLACK);
       cellLeve2Lr.setBorderColorTop(BaseColor.WHITE);
       cellLeve2Lr.setBorderColorLeft(BaseColor.WHITE);
       cellLeve2Lr.setBorderColorRight(BaseColor.WHITE);
       cellLeve2Lr.setVerticalAlignment(Element.ALIGN_TOP);
        table.addCell(cellLeve2Lr);
      indExpe=true;
           }
            if(!indExpe2){
          PdfPCell cellLeve3Lr= new PdfPCell(new Paragraph("Cost Of Operation",font2));
        cellLeve3Lr.setColspan(6);
      cellLeve3Lr.setFixedHeight(16.2f);
      cellLeve3Lr.disableBorderSide(LEFT);
      cellLeve3Lr.disableBorderSide(TOP);
      cellLeve3Lr.disableBorderSide(RIGHT);
      cellLeve3Lr.disableBorderSide(BOTTOM);
      cellLeve3Lr.setPadding(.5f);
      cellLeve3Lr.setBorderColorBottom(BaseColor.WHITE);
      cellLeve3Lr.setBorderColorTop(BaseColor.WHITE);
      cellLeve3Lr.setBorderColorLeft(BaseColor.WHITE);
      cellLeve3Lr.setBorderColorRight(BaseColor.WHITE);
      cellLeve3Lr.setVerticalAlignment(Element.ALIGN_TOP);
      table.addCell(cellLeve3Lr);
  
      indExpe2=true;
           }
     if(!indExpe21){
         if(level5aLdc.size()>1){
      table.addCell(cellEmpty);
     PdfPCell cellLeve4aLdcy= new PdfPCell(new Paragraph("Closing Stock",font2));
     cellLeve4aLdcy.setColspan(5);
      cellLeve4aLdcy.setFixedHeight(16.2f);
      cellLeve4aLdcy.disableBorderSide(LEFT);
      cellLeve4aLdcy.disableBorderSide(TOP);
      cellLeve4aLdcy.disableBorderSide(RIGHT);
      cellLeve4aLdcy.disableBorderSide(BOTTOM);
      cellLeve4aLdcy.setPadding(.5f);
      cellLeve4aLdcy.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4aLdcy.setBorderColorTop(BaseColor.WHITE);
      cellLeve4aLdcy.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4aLdcy.setBorderColorRight(BaseColor.WHITE);
      cellLeve4aLdcy.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4aLdcy);
         }
      indExpe21=true;
           }
      table.addCell(cellEmpty);
      PdfPCell cellLeve5L= new PdfPCell(new Paragraph(level5aLdc.get(z1Ldc),font1));
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
      double amountL=parseDouble(rdb.getBalances(level5aLdc.get(z1Ldc),startDate, endDate,c).get(level5aLdc.get(z1Ldc)).toString());
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
         if((level5aLdc.size()<=1)){
       table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
       }
        closingStock=true;
        
        }
       sum1Ldc= sum1Ldc+(parseDouble(rdb.getBalances(level5aLdc.get(z1Ldc),startDate, endDate,c).get(level5aLdc.get(z1Ldc)).toString()));   
        z1Ldc++; }TotalHolder.put(8, sum1Ldc);
              if(closingStock){
                      if((level5aLdc.size()>1)){
               
             table.addCell(cellEmpty);
          PdfPCell totalValueInvetmentsP= new PdfPCell(new Paragraph("Total Value of Closing Stock",font3));
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
              totalAmountInvP=TotalHolder.get(8);
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
          table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
             } }            
                        
         break; 
             
           
           }
          
          yLr++; 
           }
           
           
           
           if(openingStock||purchases||directcost||factoryOver||closingStock){
           double grossOperatingCosts=0.0;
        
           grossOperatingCosts=(TotalHolder.get(4)+TotalHolder.get(5)+TotalHolder.get(6)+TotalHolder.get(7))-TotalHolder.get(8);
       
       
       TotalHolder.put(9, grossOperatingCosts);
       table.addCell(cellEmpty);
     PdfPCell cellLeve4a1= new PdfPCell(new Paragraph("Gross Operating Costs",font4));
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
        if(TotalHolder.get(9)<0.0){ totalAmountInvests= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(9)+"")+")",font4)); }else{
         totalAmountInvests= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(9)+""),font4)); }
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
           
           
           
           
           
           
   case "Operating Expenses":
        
        
           
            
            int zLk=0; double sumLk=0.0; boolean indExpe20=false;
       Map<Integer, String> level5Lk=rdb.getLevel5Items("Operating Expense"); 

      
              while(zLk<=level5Lk.size()-1){
      
        if(parseDouble(rdb.getBalances(level5Lk.get(zLk),startDate, endDate,c).get(level5Lk.get(zLk)).toString())!=0.0){
            if(!indExpe){
           PdfPCell cellLeve2Lr= new PdfPCell(new Paragraph("Expenses",font2));
       cellLeve2Lr.setColspan(6);
      cellLeve2Lr.setFixedHeight(16.2f);
      cellLeve2Lr.disableBorderSide(LEFT);
      cellLeve2Lr.disableBorderSide(TOP);
      cellLeve2Lr.disableBorderSide(RIGHT);
       cellLeve2Lr.setPadding(.5f);
       cellLeve2Lr.setBorderColorBottom(BaseColor.BLACK);
       cellLeve2Lr.setBorderColorTop(BaseColor.WHITE);
       cellLeve2Lr.setBorderColorLeft(BaseColor.WHITE);
       cellLeve2Lr.setBorderColorRight(BaseColor.WHITE);
       cellLeve2Lr.setVerticalAlignment(Element.ALIGN_TOP);
        table.addCell(cellLeve2Lr);
      indExpe=true;
           }
             if(!indExpe3){
                
          PdfPCell cellLeve3L= new PdfPCell(new Paragraph("Operating Expenses",font2));
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
                  
     
      indExpe3=true;
           } 
            
         if(!indExpe20){
              if(level5Lk.size()>1){
      table.addCell(cellEmpty);
     PdfPCell cellLeve4Lk= new PdfPCell(new Paragraph("Operating Expense",font2));
     cellLeve4Lk.setColspan(5);
      cellLeve4Lk.setFixedHeight(16.2f);
      cellLeve4Lk.disableBorderSide(LEFT);
      cellLeve4Lk.disableBorderSide(TOP);
      cellLeve4Lk.disableBorderSide(RIGHT);
      cellLeve4Lk.disableBorderSide(BOTTOM);
      cellLeve4Lk.setPadding(.5f);
      cellLeve4Lk.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4Lk.setBorderColorTop(BaseColor.WHITE);
      cellLeve4Lk.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4Lk.setBorderColorRight(BaseColor.WHITE);
      cellLeve4Lk.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4Lk);
              }
      indExpe20=true;
           }
      table.addCell(cellEmpty);
      PdfPCell cellLeve5L= new PdfPCell(new Paragraph(level5Lk.get(zLk),font1));
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
      double amountL=parseDouble(rdb.getBalances(level5Lk.get(zLk),startDate, endDate,c).get(level5Lk.get(zLk)).toString());
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
          if((level5Lk.size()<=1)){
       table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
       }
       sumLk= sumLk+(parseDouble(rdb.getBalances(level5Lk.get(zLk),startDate, endDate,c).get(level5Lk.get(zLk)).toString()));     
       operatingEXP=true; 
        }
      
           zLk++; TotalHolder.put(11, sumLk);
                 }
              if(operatingEXP){
             
               if((level5Lk.size()>1)){
             table.addCell(cellEmpty);
          PdfPCell totalValue= new PdfPCell(new Paragraph("Total Operating Expense",font3));
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
     if(TotalHolder.get(11)<0.0)
     {totalAmount= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(11)+"")+")",font1));
     } else{totalAmount= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(11)+""),font1));}
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
              }}
 
          break; 
          
           case "Financing Expenses":
               
            int zLf=0; double sumLf=0.0; boolean indExpe17=false;
       Map<Integer, String> level5Lf=rdb.getLevel5Items("Financing Expense"); 

     
              while(zLf<=level5Lf.size()-1){
      
        if(parseDouble(rdb.getBalances(level5Lf.get(zLf),startDate, endDate,c).get(level5Lf.get(zLf)).toString())!=0.0){
           if(!indExpe){
           PdfPCell cellLeve2Lr= new PdfPCell(new Paragraph("Expenses",font2));
       cellLeve2Lr.setColspan(6);
      cellLeve2Lr.setFixedHeight(16.2f);
      cellLeve2Lr.disableBorderSide(LEFT);
      cellLeve2Lr.disableBorderSide(TOP);
      cellLeve2Lr.disableBorderSide(RIGHT);
       cellLeve2Lr.setPadding(.5f);
       cellLeve2Lr.setBorderColorBottom(BaseColor.BLACK);
       cellLeve2Lr.setBorderColorTop(BaseColor.WHITE);
       cellLeve2Lr.setBorderColorLeft(BaseColor.WHITE);
       cellLeve2Lr.setBorderColorRight(BaseColor.WHITE);
       cellLeve2Lr.setVerticalAlignment(Element.ALIGN_TOP);
        table.addCell(cellLeve2Lr);
      indExpe=true;
               }
       
              if(!indExpe5){
         PdfPCell cellLeve3Ljf= new PdfPCell(new Paragraph("Financing Expenses",font2));
        cellLeve3Ljf.setColspan(6);
      cellLeve3Ljf.setFixedHeight(16.2f);
      cellLeve3Ljf.disableBorderSide(LEFT);
      cellLeve3Ljf.disableBorderSide(TOP);
      cellLeve3Ljf.disableBorderSide(RIGHT);
      cellLeve3Ljf.disableBorderSide(BOTTOM);
      cellLeve3Ljf.setPadding(.5f);
      cellLeve3Ljf.setBorderColorBottom(BaseColor.WHITE);
      cellLeve3Ljf.setBorderColorTop(BaseColor.WHITE);
      cellLeve3Ljf.setBorderColorLeft(BaseColor.WHITE);
      cellLeve3Ljf.setBorderColorRight(BaseColor.WHITE);
      cellLeve3Ljf.setVerticalAlignment(Element.ALIGN_TOP);
      table.addCell(cellLeve3Ljf);
    
     
     
      indExpe5=true;
           }
        if(!indExpe17){
           if(level5Lf.size()>1){
         table.addCell(cellEmpty);
     PdfPCell cellLeve4Lf= new PdfPCell(new Paragraph("Financing Expense",font2));
     cellLeve4Lf.setColspan(5);
      cellLeve4Lf.setFixedHeight(16.2f);
      cellLeve4Lf.disableBorderSide(LEFT);
      cellLeve4Lf.disableBorderSide(TOP);
      cellLeve4Lf.disableBorderSide(RIGHT);
      cellLeve4Lf.disableBorderSide(BOTTOM);
      cellLeve4Lf.setPadding(.5f);
      cellLeve4Lf.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4Lf.setBorderColorTop(BaseColor.WHITE);
      cellLeve4Lf.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4Lf.setBorderColorRight(BaseColor.WHITE);
      cellLeve4Lf.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4Lf);
           }
      indExpe17=true;
            }
      table.addCell(cellEmpty);
      PdfPCell cellLeve5L= new PdfPCell(new Paragraph(level5Lf.get(zLf),font1));
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
      double amountL=parseDouble(rdb.getBalances(level5Lf.get(zLf),startDate, endDate,c).get(level5Lf.get(zLf)).toString());
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
       if((level5Lf.size()<=1)){
       table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
       }
       sumLf= sumLf+(parseDouble(rdb.getBalances(level5Lf.get(zLf),startDate, endDate,c).get(level5Lf.get(zLf)).toString()));     
       finExp=true; 
        }
      
           zLf++; 
                 }TotalHolder.put(15, sumLf);
                
              if(finExp){   
                    if((level5Lf.size()>1)){
             
             table.addCell(cellEmpty);
          PdfPCell totalValue= new PdfPCell(new Paragraph("Total Financing Expense",font3));
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
     if(TotalHolder.get(15)<0.0)
     {totalAmount= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(15)+"")+")",font1));
     } else{totalAmount= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(15)+""),font1));}
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
              }}
              break;
    
      case "Administrative Expenses":
           
           int yLj=0;
       Map<Integer, String> level4Lj=rdb.getLevel4Items("Administrative Expenses");   
       
           while(yLj<=level4Lj.size()-1){
        switch(level4Lj.get(yLj)){
        case "Administrative Expense":
            
            int zL=0; double sumL=0.0; boolean indExpe19=false;
       Map<Integer, String> level5L=rdb.getLevel5Items("Administrative Expense"); 

     
              while(zL<=level5L.size()-1){
      
        if(parseDouble(rdb.getBalances(level5L.get(zL),startDate, endDate,c).get(level5L.get(zL)).toString())!=0.0){
             if(!indExpe){
           PdfPCell cellLeve2Lr= new PdfPCell(new Paragraph("Expenses",font2));
       cellLeve2Lr.setColspan(6);
      cellLeve2Lr.setFixedHeight(16.2f);
      cellLeve2Lr.disableBorderSide(LEFT);
      cellLeve2Lr.disableBorderSide(TOP);
      cellLeve2Lr.disableBorderSide(RIGHT);
       cellLeve2Lr.setPadding(.5f);
       cellLeve2Lr.setBorderColorBottom(BaseColor.BLACK);
       cellLeve2Lr.setBorderColorTop(BaseColor.WHITE);
       cellLeve2Lr.setBorderColorLeft(BaseColor.WHITE);
       cellLeve2Lr.setBorderColorRight(BaseColor.WHITE);
       cellLeve2Lr.setVerticalAlignment(Element.ALIGN_TOP);
        table.addCell(cellLeve2Lr);
      indExpe=true;
               }
              if(!indExpe4){
         PdfPCell cellLeve3Lj= new PdfPCell(new Paragraph("Administrative Expenses",font2));
        cellLeve3Lj.setColspan(6);
      cellLeve3Lj.setFixedHeight(16.2f);
      cellLeve3Lj.disableBorderSide(LEFT);
      cellLeve3Lj.disableBorderSide(TOP);
      cellLeve3Lj.disableBorderSide(RIGHT);
      cellLeve3Lj.disableBorderSide(BOTTOM);
      cellLeve3Lj.setPadding(.5f);
      cellLeve3Lj.setBorderColorBottom(BaseColor.WHITE);
      cellLeve3Lj.setBorderColorTop(BaseColor.WHITE);
      cellLeve3Lj.setBorderColorLeft(BaseColor.WHITE);
      cellLeve3Lj.setBorderColorRight(BaseColor.WHITE);
      cellLeve3Lj.setVerticalAlignment(Element.ALIGN_TOP);
      table.addCell(cellLeve3Lj);
   
     
      indExpe4=true;
           } 
            
            if(!indExpe){
           PdfPCell cellLeve2Lr= new PdfPCell(new Paragraph("Expenses",font2));
       cellLeve2Lr.setColspan(6);
      cellLeve2Lr.setFixedHeight(16.2f);
      cellLeve2Lr.disableBorderSide(LEFT);
      cellLeve2Lr.disableBorderSide(TOP);
      cellLeve2Lr.disableBorderSide(RIGHT);
       cellLeve2Lr.setPadding(.5f);
       cellLeve2Lr.setBorderColorBottom(BaseColor.BLACK);
       cellLeve2Lr.setBorderColorTop(BaseColor.WHITE);
       cellLeve2Lr.setBorderColorLeft(BaseColor.WHITE);
       cellLeve2Lr.setBorderColorRight(BaseColor.WHITE);
       cellLeve2Lr.setVerticalAlignment(Element.ALIGN_TOP);
        table.addCell(cellLeve2Lr);
      indExpe=true;
           }
         if(!indExpe19){
              if(level5L.size()>1){
       table.addCell(cellEmpty);
     PdfPCell cellLeve4L= new PdfPCell(new Paragraph("Administrative Expense",font2));
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
              }
      indExpe19=true;
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
      double amountL=parseDouble(rdb.getBalances(level5L.get(zL),startDate, endDate,c).get(level5L.get(zL)).toString());
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
           if((level5L.size()<=1)){
       table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
       }
       sumL= sumL+(parseDouble(rdb.getBalances(level5L.get(zL),startDate, endDate,c).get(level5L.get(zL)).toString()));     
       adminExp=true; 
        }
      
           zL++; 
                 } TotalHolder.put(12, sumL);
              if(adminExp){
                  if((level5L.size()>1)){  
            
             table.addCell(cellEmpty);
          PdfPCell totalValue= new PdfPCell(new Paragraph("Total Administrative Expense",font3));
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
         table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
              }}
          break; 
            
        
      case   "Employment Expense" :
           
         int z1Lm=0; double sum1mL=0.0;boolean indExpe18=false;
       Map<Integer, String> level5aLm=rdb.getLevel5Items("Employment Expense"); 
      
     
              while(z1Lm<=level5aLm.size()-1){
      
        if(parseDouble(rdb.getBalances(level5aLm.get(z1Lm),startDate, endDate,c).get(level5aLm.get(z1Lm)).toString())!=0.0){
             if(!indExpe){
           PdfPCell cellLeve2Lr= new PdfPCell(new Paragraph("Expenses",font2));
       cellLeve2Lr.setColspan(6);
      cellLeve2Lr.setFixedHeight(16.2f);
      cellLeve2Lr.disableBorderSide(LEFT);
      cellLeve2Lr.disableBorderSide(TOP);
      cellLeve2Lr.disableBorderSide(RIGHT);
       cellLeve2Lr.setPadding(.5f);
       cellLeve2Lr.setBorderColorBottom(BaseColor.BLACK);
       cellLeve2Lr.setBorderColorTop(BaseColor.WHITE);
       cellLeve2Lr.setBorderColorLeft(BaseColor.WHITE);
       cellLeve2Lr.setBorderColorRight(BaseColor.WHITE);
       cellLeve2Lr.setVerticalAlignment(Element.ALIGN_TOP);
        table.addCell(cellLeve2Lr);
      indExpe=true;
               }
            if(!indExpe4){
         PdfPCell cellLeve3Lj= new PdfPCell(new Paragraph("Administrative Expenses",font2));
        cellLeve3Lj.setColspan(6);
      cellLeve3Lj.setFixedHeight(16.2f);
      cellLeve3Lj.disableBorderSide(LEFT);
      cellLeve3Lj.disableBorderSide(TOP);
      cellLeve3Lj.disableBorderSide(RIGHT);
      cellLeve3Lj.disableBorderSide(BOTTOM);
      cellLeve3Lj.setPadding(.5f);
      cellLeve3Lj.setBorderColorBottom(BaseColor.WHITE);
      cellLeve3Lj.setBorderColorTop(BaseColor.WHITE);
      cellLeve3Lj.setBorderColorLeft(BaseColor.WHITE);
      cellLeve3Lj.setBorderColorRight(BaseColor.WHITE);
      cellLeve3Lj.setVerticalAlignment(Element.ALIGN_TOP);
      table.addCell(cellLeve3Lj);
  
     
      indExpe4=true;
           } 
         
     if(!indExpe18){
           if(level5aLm.size()>1){
       table.addCell(cellEmpty);
     PdfPCell cellLeve4aLm= new PdfPCell(new Paragraph("Employment Expense",font2));
     cellLeve4aLm.setColspan(5);
      cellLeve4aLm.setFixedHeight(16.2f);
      cellLeve4aLm.disableBorderSide(LEFT);
      cellLeve4aLm.disableBorderSide(TOP);
      cellLeve4aLm.disableBorderSide(RIGHT);
      cellLeve4aLm.disableBorderSide(BOTTOM);
      cellLeve4aLm.setPadding(.5f);
      cellLeve4aLm.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4aLm.setBorderColorTop(BaseColor.WHITE);
      cellLeve4aLm.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4aLm.setBorderColorRight(BaseColor.WHITE);
      cellLeve4aLm.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4aLm);   
           }
      indExpe18=true;
           }
      table.addCell(cellEmpty);
      PdfPCell cellLeve5L= new PdfPCell(new Paragraph(level5aLm.get(z1Lm),font1));
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
      double amountL=parseDouble(rdb.getBalances(level5aLm.get(z1Lm),startDate, endDate,c).get(level5aLm.get(z1Lm)).toString());
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
        if((level5aLm.size()<=1)){
       table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
       }
        
       employExp=true;
       sum1mL= sum1mL+(parseDouble(rdb.getBalances(level5aLm.get(z1Lm),startDate, endDate,c).get(level5aLm.get(z1Lm)).toString())); 
        }
          
        z1Lm++; 
              
              } 
          
              TotalHolder.put(13, sum1mL);
             
        if(employExp){
                  
                if((level5aLm.size()>1)){
              
             table.addCell(cellEmpty);
          PdfPCell totalValueInvetmentsP= new PdfPCell(new Paragraph("Total Employment Expenses",font3));
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
          table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
            }  }           
                        
         break; 
             
           
           }
          
          yLj++; 
           }
           
      if(adminExp && employExp){
   
        double adminExpenses=0.0;
        adminExpenses=MAth.add(TotalHolder.get(12),TotalHolder.get(13));
       
       
       TotalHolder.put(14, adminExpenses);
       table.addCell(cellEmpty);
       PdfPCell cellLeve4a1L= new PdfPCell(new Paragraph("Total Value of Admin Expenses",font4));
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
       if(TotalHolder.get(14)<0.0){totalAmountInvestsN= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(14)+"")+")",font4)); }
       else{totalAmountInvestsN= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(14)+""),font4)); }
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
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
       table.addCell(cellEmpty); 
       }
       
          
         
        
       
           
        
         case "Expenses Purely Related To Short Term Insurance Business Income":
              
        
        case "Expense Purely Related To Short Term Insurance Business Income":
             
            int zLfx=0; double sumLfx=0.0; boolean indExpe16=false;
       Map<Integer, String> level5Lfx=rdb.getLevel5Items("Expense Purely Related To Short Term Insurance Business Income"); 

     
              while(zLfx<=level5Lfx.size()-1){
      
        if(parseDouble(rdb.getBalances(level5Lfx.get(zLfx),startDate, endDate,c).get(level5Lfx.get(zLfx)).toString())!=0.0){
            if(!indExpe){
           PdfPCell cellLeve2Lr= new PdfPCell(new Paragraph("Expenses",font2));
       cellLeve2Lr.setColspan(6);
      cellLeve2Lr.setFixedHeight(16.2f);
      cellLeve2Lr.disableBorderSide(LEFT);
      cellLeve2Lr.disableBorderSide(TOP);
      cellLeve2Lr.disableBorderSide(RIGHT);
       cellLeve2Lr.setPadding(.5f);
       cellLeve2Lr.setBorderColorBottom(BaseColor.BLACK);
       cellLeve2Lr.setBorderColorTop(BaseColor.WHITE);
       cellLeve2Lr.setBorderColorLeft(BaseColor.WHITE);
       cellLeve2Lr.setBorderColorRight(BaseColor.WHITE);
       cellLeve2Lr.setVerticalAlignment(Element.ALIGN_TOP);
        table.addCell(cellLeve2Lr);
      indExpe=true;
           }
           if(!indExpe6){
          PdfPCell cellLeve3Ljfx= new PdfPCell(new Paragraph("Expenses Purely Related To Short Term Insurance Business Income",font2));
        cellLeve3Ljfx.setColspan(6);
      cellLeve3Ljfx.setFixedHeight(16.2f);
      cellLeve3Ljfx.disableBorderSide(LEFT);
      cellLeve3Ljfx.disableBorderSide(TOP);
      cellLeve3Ljfx.disableBorderSide(RIGHT);
      cellLeve3Ljfx.disableBorderSide(BOTTOM);
      cellLeve3Ljfx.setPadding(.5f);
      cellLeve3Ljfx.setBorderColorBottom(BaseColor.WHITE);
      cellLeve3Ljfx.setBorderColorTop(BaseColor.WHITE);
      cellLeve3Ljfx.setBorderColorLeft(BaseColor.WHITE);
      cellLeve3Ljfx.setBorderColorRight(BaseColor.WHITE);
      cellLeve3Ljfx.setVerticalAlignment(Element.ALIGN_TOP);
      table.addCell(cellLeve3Ljfx);
   
      indExpe6=true;
           }  
        if(!indExpe16){
            if(level5Lfx.size()>1){
         table.addCell(cellEmpty);
     PdfPCell cellLeve4Lfx= new PdfPCell(new Paragraph("Expense Purely Related To Short Term Insurance Business Income",font2));
     cellLeve4Lfx.setColspan(5);
      cellLeve4Lfx.setFixedHeight(16.2f);
      cellLeve4Lfx.disableBorderSide(LEFT);
      cellLeve4Lfx.disableBorderSide(TOP);
      cellLeve4Lfx.disableBorderSide(RIGHT);
      cellLeve4Lfx.disableBorderSide(BOTTOM);
      cellLeve4Lfx.setPadding(.5f);
      cellLeve4Lfx.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4Lfx.setBorderColorTop(BaseColor.WHITE);
      cellLeve4Lfx.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4Lfx.setBorderColorRight(BaseColor.WHITE);
      cellLeve4Lfx.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4Lfx);
            }
      indExpe16=true;
           }
      table.addCell(cellEmpty);
      PdfPCell cellLeve5Lx= new PdfPCell(new Paragraph(level5Lfx.get(zLfx),font1));
      cellLeve5Lx.setFixedHeight(16.2f);
      cellLeve5Lx.disableBorderSide(LEFT);
      cellLeve5Lx.disableBorderSide(TOP);
      cellLeve5Lx.disableBorderSide(RIGHT);
      cellLeve5Lx.disableBorderSide(BOTTOM);
      cellLeve5Lx.setPadding(.5f);
      cellLeve5Lx.setBorderColorBottom(BaseColor.WHITE);
      cellLeve5Lx.setBorderColorTop(BaseColor.WHITE);
      cellLeve5Lx.setBorderColorLeft(BaseColor.WHITE);
      cellLeve5Lx.setBorderColorRight(BaseColor.WHITE);
      cellLeve5Lx.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cellLeve5Lx);
      table.addCell(cellEmpty);
   
      PdfPCell balAmountL=null;
      double amountL=parseDouble(rdb.getBalances(level5Lfx.get(zLfx),startDate, endDate,c).get(level5Lfx.get(zLfx)).toString());
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
       if((level5Lfx.size()<=1)){
       table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
       }
       sumLfx= sumLfx+(parseDouble(rdb.getBalances(level5Lfx.get(zLfx),startDate, endDate,c).get(level5Lfx.get(zLfx)).toString()));     
       Insurance=true; 
        }
      
           zLfx++; 
                 }TotalHolder.put(16, sumLfx);
                 
              if(Insurance){
             
                     if((level5Lfx.size()>1)){
             table.addCell(cellEmpty);
          PdfPCell totalValue= new PdfPCell(new Paragraph("Total Expense Purely Related To Short Term Insurance",font3));
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
     if(TotalHolder.get(16)<0.0)
     {totalAmount= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(16)+"")+")",font1));
     } else{totalAmount= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(16)+""),font1));}
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
              }}
     
          break;
             
          case "General Expenses":
         
        
        case "General Expensee":
              
            int zhszg=0; double sumhsg=0.0;  boolean indExpe11=false;
       Map<Integer, String> level5hszg=rdb.getLevel5Items("General Expensee"); 

      
              while(zhszg<=level5hszg.size()-1){
      
        if(parseDouble(rdb.getBalances(level5hszg.get(zhszg),startDate, endDate,c).get(level5hszg.get(zhszg)).toString())!=0.0){
             if(!indExpe){
           PdfPCell cellLeve2Lr= new PdfPCell(new Paragraph("Expenses",font2));
       cellLeve2Lr.setColspan(6);
      cellLeve2Lr.setFixedHeight(16.2f);
      cellLeve2Lr.disableBorderSide(LEFT);
      cellLeve2Lr.disableBorderSide(TOP);
      cellLeve2Lr.disableBorderSide(RIGHT);
       cellLeve2Lr.setPadding(.5f);
       cellLeve2Lr.setBorderColorBottom(BaseColor.BLACK);
       cellLeve2Lr.setBorderColorTop(BaseColor.WHITE);
       cellLeve2Lr.setBorderColorLeft(BaseColor.WHITE);
       cellLeve2Lr.setBorderColorRight(BaseColor.WHITE);
       cellLeve2Lr.setVerticalAlignment(Element.ALIGN_TOP);
        table.addCell(cellLeve2Lr);
      indExpe=true;
           }
              if(!indExpe10){
        PdfPCell cellLeve3hszg= new PdfPCell(new Paragraph("General Expenses",font2));
        cellLeve3hszg.setColspan(6);
      cellLeve3hszg.setFixedHeight(16.2f);
      cellLeve3hszg.disableBorderSide(LEFT);
      cellLeve3hszg.disableBorderSide(TOP);
      cellLeve3hszg.disableBorderSide(RIGHT);
      cellLeve3hszg.disableBorderSide(BOTTOM);
      cellLeve3hszg.setPadding(.5f);
      cellLeve3hszg.setBorderColorBottom(BaseColor.WHITE);
      cellLeve3hszg.setBorderColorTop(BaseColor.WHITE);
      cellLeve3hszg.setBorderColorLeft(BaseColor.WHITE);
      cellLeve3hszg.setBorderColorRight(BaseColor.WHITE);
      cellLeve3hszg.setVerticalAlignment(Element.ALIGN_TOP);
      table.addCell(cellLeve3hszg);
    
     
      indExpe10=true;
           } 
          if(!indExpe11){
              if(level5hszg.size()>1){
        table.addCell(cellEmpty);
     PdfPCell cellLeve4Lfxg= new PdfPCell(new Paragraph("General Expensee",font2));
     cellLeve4Lfxg.setColspan(5);
      cellLeve4Lfxg.setFixedHeight(16.2f);
      cellLeve4Lfxg.disableBorderSide(LEFT);
      cellLeve4Lfxg.disableBorderSide(TOP);
      cellLeve4Lfxg.disableBorderSide(RIGHT);
      cellLeve4Lfxg.disableBorderSide(BOTTOM);
      cellLeve4Lfxg.setPadding(.5f);
      cellLeve4Lfxg.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4Lfxg.setBorderColorTop(BaseColor.WHITE);
      cellLeve4Lfxg.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4Lfxg.setBorderColorRight(BaseColor.WHITE);
      cellLeve4Lfxg.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4Lfxg);
              }
      indExpe11=true;
           }
      table.addCell(cellEmpty);
      PdfPCell cellLeve5Lx= new PdfPCell(new Paragraph(level5hszg.get(zhszg),font1));
      cellLeve5Lx.setFixedHeight(16.2f);
      cellLeve5Lx.disableBorderSide(LEFT);
      cellLeve5Lx.disableBorderSide(TOP);
      cellLeve5Lx.disableBorderSide(RIGHT);
      cellLeve5Lx.disableBorderSide(BOTTOM);
      cellLeve5Lx.setPadding(.5f);
      cellLeve5Lx.setBorderColorBottom(BaseColor.WHITE);
      cellLeve5Lx.setBorderColorTop(BaseColor.WHITE);
      cellLeve5Lx.setBorderColorLeft(BaseColor.WHITE);
      cellLeve5Lx.setBorderColorRight(BaseColor.WHITE);
      cellLeve5Lx.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cellLeve5Lx);
      table.addCell(cellEmpty);
   
      PdfPCell balAmountL=null;
      double amountL=parseDouble(rdb.getBalances(level5hszg.get(zhszg),startDate, endDate,c).get(level5hszg.get(zhszg)).toString());
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
       if((level5hszg.size()<=1)){
       table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
       }
       sumhsg= sumhsg+(parseDouble(rdb.getBalances(level5hszg.get(zhszg),startDate, endDate,c).get(level5hszg.get(zhszg)).toString()));     
       general=true; 
        }
      
           zhszg++; 
                 }TotalHolder.put(20, sumhsg);
                  
              if(general){
             
              if((level5hszg.size()>1)){
             table.addCell(cellEmpty);
          PdfPCell totalValue= new PdfPCell(new Paragraph("Total Value of General Expenses",font3));
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
     if(TotalHolder.get(20)<0.0)
     {totalAmount= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(20)+"")+")",font1));
     } else{totalAmount= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(20)+""),font1));}
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
              }}
       
     break;  
              
         
  
        }
        
        
        xLr++;
        
        }
        if(operatingEXP||adminExp|| employExp||finExp||Insurance||general){
               
            double grossExpense=0.0;
        grossExpense=MAth.add(TotalHolder.get(11),MAth.add(TotalHolder.get(13),MAth.add(TotalHolder.get(15),MAth.add(TotalHolder.get(16),MAth.add(TotalHolder.get(20),TotalHolder.get(12))))));
       
       
       TotalHolder.put(25, grossExpense);
       table.addCell(cellEmpty);
       PdfPCell cellLeve4a1L= new PdfPCell(new Paragraph("Gross Expenses",font4));
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
       if(TotalHolder.get(25)<0.0){totalAmountInvestsN= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(25)+"")+")",font4)); }
       else{totalAmountInvestsN= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(25)+""),font4)); }
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
           }
        
        
        if(mainIncome||indicatorthIncome||indicatorInsurance|openingStock||purchases||directcost||factoryOver||closingStock){
        double grossProfit=0.0;
        grossProfit=MAth.subtract(TotalHolder.get(3),TotalHolder.get(9));
       
       
       TotalHolder.put(10, grossProfit);
       table.addCell(cellEmpty);
       PdfPCell cellLeve4a1L= new PdfPCell(new Paragraph("Gross Profits",font2));
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
       if(TotalHolder.get(10)<0.0){totalAmountInvestsN= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(10)+"")+")",font2)); }
       else{totalAmountInvestsN= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(10)+""),font2)); }
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
       }
        
         if(mainIncome||indicatorthIncome||indicatorInsurance|openingStock||purchases||directcost||factoryOver||closingStock||operatingEXP||adminExp||employExp||finExp||Insurance||general){
        double profitBTax=0.0;
        profitBTax=MAth.subtract(TotalHolder.get(10),TotalHolder.get(25));
       
       
       TotalHolder.put(21, profitBTax);
      
       table.addCell(cellEmpty);
        PdfPCell cellLeve4a1L=null;
        if(TotalHolder.get(21)>=0.0){
       cellLeve4a1L= new PdfPCell(new Paragraph("Profit Before Tax",font2));
        }else if(TotalHolder.get(21)<0.0){
        cellLeve4a1L= new PdfPCell(new Paragraph("Loss",font2));
        }
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
       if(TotalHolder.get(21)<0.0){totalAmountInvestsN= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(21)+"")+")",font2)); }
       else{totalAmountInvestsN= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(21)+""),font2)); }
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
       }

          int yh=0;
       Map<Integer, String> level4h=rdb.getLevel4Items("Income Tax Expenses");   
      
     
           while(yh<=level4h.size()-1){
        switch(level4h.get(yh)){
        case "Income Taxes Expense":
            
            int zh=0; double sumh=0.0; boolean indExpe14=false;
       Map<Integer, String> level5h=rdb.getLevel5Items("Income Taxes Expense"); 

     
              while(zh<=level5h.size()-1){
      
        if(parseDouble(rdb.getBalances(level5h.get(zh),startDate, endDate,c).get(level5h.get(zh)).toString())!=0.0){
         if(!indExpe14){
             if(level5h.size()>1){
        table.addCell(cellEmpty);
     PdfPCell cellLeve4Lfx= new PdfPCell(new Paragraph("Income Taxes Expense",font2));
     cellLeve4Lfx.setColspan(5);
      cellLeve4Lfx.setFixedHeight(16.2f);
      cellLeve4Lfx.disableBorderSide(LEFT);
      cellLeve4Lfx.disableBorderSide(TOP);
      cellLeve4Lfx.disableBorderSide(RIGHT);
      cellLeve4Lfx.disableBorderSide(BOTTOM);
      cellLeve4Lfx.setPadding(.5f);
      cellLeve4Lfx.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4Lfx.setBorderColorTop(BaseColor.WHITE);
      cellLeve4Lfx.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4Lfx.setBorderColorRight(BaseColor.WHITE);
      cellLeve4Lfx.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4Lfx);
             }
      indExpe14=true;
           }
      table.addCell(cellEmpty);
      PdfPCell cellLeve5Lx= new PdfPCell(new Paragraph(level5h.get(zh),font1));
      cellLeve5Lx.setFixedHeight(16.2f);
      cellLeve5Lx.disableBorderSide(LEFT);
      cellLeve5Lx.disableBorderSide(TOP);
      cellLeve5Lx.disableBorderSide(RIGHT);
      cellLeve5Lx.disableBorderSide(BOTTOM);
      cellLeve5Lx.setPadding(.5f);
      cellLeve5Lx.setBorderColorBottom(BaseColor.WHITE);
      cellLeve5Lx.setBorderColorTop(BaseColor.WHITE);
      cellLeve5Lx.setBorderColorLeft(BaseColor.WHITE);
      cellLeve5Lx.setBorderColorRight(BaseColor.WHITE);
      cellLeve5Lx.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cellLeve5Lx);
      table.addCell(cellEmpty);
   
      PdfPCell balAmountL=null;
      double amountL=parseDouble(rdb.getBalances(level5h.get(zh),startDate, endDate,c).get(level5h.get(zh)).toString());
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
       if((level5h.size()<=1)){
       table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
      table.addCell(cellEmpty);
       }
       sumh= sumh+(parseDouble(rdb.getBalances(level5h.get(zh),startDate, endDate,c).get(level5h.get(zh)).toString()));     
       incomeTax=true; 
        }
      
           zh++; 
                 }TotalHolder.put(17, sumh);
              if(incomeTax){
             
              if((level5h.size()>1)){
             table.addCell(cellEmpty);
          PdfPCell totalValue= new PdfPCell(new Paragraph("Total Income Tax Expense",font3));
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
     if(TotalHolder.get(15)<0.0)
     {totalAmount= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(17)+"")+")",font1));
     } else{totalAmount= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(17)+""),font1));}
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
              }}
    table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
              break;
        }
           
          yh++; } 
           if(incomeTax||mainIncome||indicatorthIncome||indicatorInsurance|openingStock||purchases||directcost||factoryOver||closingStock||operatingEXP||adminExp||employExp||finExp||Insurance||general){
        
               
               double profitATax=0.0;
        profitATax=MAth.subtract(TotalHolder.get(21),TotalHolder.get(17));
       
       
       TotalHolder.put(22, profitATax);
       table.addCell(cellEmpty);
       PdfPCell cellLeve4a1L= new PdfPCell(new Paragraph("Profit After Tax",font2));
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
       if(TotalHolder.get(22)<0.0){totalAmountInvestsN= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(22)+"")+")",font2)); }
       else{totalAmountInvestsN= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(22)+""),font2)); }
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
       }  
                  

           int yhs=0;
       Map<Integer, String> level4hs=rdb.getLevel4Items("Dividends paid during the period");   
      
           while(yhs<=level4hs.size()-1){
        switch(level4hs.get(yhs)){
        case "Dividend paid during the period":
            
            
            int zhs=0; double sumhs=0.0; boolean indExpe13=false;
       Map<Integer, String> level5hs=rdb.getLevel5Items("Dividend paid during the period"); 

     
              while(zhs<=level5hs.size()-1){
      
        if(parseDouble(rdb.getBalances(level5hs.get(zhs),startDate, endDate,c).get(level5hs.get(zhs)).toString())!=0.0){
          if(!indExpe13){
              if(level5hs.size()>1){
        table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     PdfPCell cellLeve4Lfx= new PdfPCell(new Paragraph("Dividend paid during the period",font2));
     cellLeve4Lfx.setColspan(5);
      cellLeve4Lfx.setFixedHeight(16.2f);
      cellLeve4Lfx.disableBorderSide(LEFT);
      cellLeve4Lfx.disableBorderSide(TOP);
      cellLeve4Lfx.disableBorderSide(RIGHT);
      cellLeve4Lfx.disableBorderSide(BOTTOM);
      cellLeve4Lfx.setPadding(.5f);
      cellLeve4Lfx.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4Lfx.setBorderColorTop(BaseColor.WHITE);
      cellLeve4Lfx.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4Lfx.setBorderColorRight(BaseColor.WHITE);
      cellLeve4Lfx.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4Lfx);
              }
      indExpe13=true;
           }
      table.addCell(cellEmpty);
      PdfPCell cellLeve5Lx= new PdfPCell(new Paragraph(level5hs.get(zhs),font1));
      cellLeve5Lx.setFixedHeight(16.2f);
      cellLeve5Lx.disableBorderSide(LEFT);
      cellLeve5Lx.disableBorderSide(TOP);
      cellLeve5Lx.disableBorderSide(RIGHT);
      cellLeve5Lx.disableBorderSide(BOTTOM);
      cellLeve5Lx.setPadding(.5f);
      cellLeve5Lx.setBorderColorBottom(BaseColor.WHITE);
      cellLeve5Lx.setBorderColorTop(BaseColor.WHITE);
      cellLeve5Lx.setBorderColorLeft(BaseColor.WHITE);
      cellLeve5Lx.setBorderColorRight(BaseColor.WHITE);
      cellLeve5Lx.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cellLeve5Lx);
      table.addCell(cellEmpty);
   
      PdfPCell balAmountL=null;
      double amountL=parseDouble(rdb.getBalances(level5hs.get(zhs),startDate, endDate,c).get(level5hs.get(zhs)).toString());
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
       
       sumhs= sumhs+(parseDouble(rdb.getBalances(level5hs.get(zhs),startDate, endDate,c).get(level5hs.get(zhs)).toString()));     
       proposedDev=true; 
        }
      
           zhs++; 
                 } TotalHolder.put(18, sumhs);
              if(proposedDev){
            
              if((level5hs.size()>1)){
             table.addCell(cellEmpty);
          PdfPCell totalValue= new PdfPCell(new Paragraph("Total Value of Dividends paid",font3));
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
     if(TotalHolder.get(15)<0.0)
     {totalAmount= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(17)+"")+")",font1));
     } else{totalAmount= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(17)+""),font1));}
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
              }}
    table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
              break;
        }
           
          yhs++; 
           
           }
           
           
            if(proposedDev||incomeTax||mainIncome||indicatorthIncome||indicatorInsurance|openingStock||purchases||directcost||factoryOver||closingStock||operatingEXP||adminExp||employExp||finExp||Insurance||general){
        double profitATaxD=0.0;
        profitATaxD=MAth.subtract(TotalHolder.get(22),TotalHolder.get(18));
       
       
       TotalHolder.put(23, profitATaxD);
       table.addCell(cellEmpty);
       PdfPCell cellLeve4a1L= new PdfPCell(new Paragraph("Profit After Tax and Devidends",font2));
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
       if(TotalHolder.get(23)<0.0){totalAmountInvestsN= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(23)+"")+")",font2)); }
       else{totalAmountInvestsN= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(23)+""),font2)); }
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
       }  

           int yhsz=0;
       Map<Integer, String> level4hsz=rdb.getLevel4Items("Retained earnings at start of period");   
     
           while(yhsz<=level4hsz.size()-1){
        switch(level4hsz.get(yhsz)){
        case "Retained earning at start of period":
             
            int zhsz=0; double sumhs=0.0; boolean indExpe12=false;
       Map<Integer, String> level5hsz=rdb.getLevel5Items("Retained earning at start of period"); 

     
              while(zhsz<=level5hsz.size()-1){
      
        if(parseDouble(rdb.getBalances(level5hsz.get(zhsz),startDate, endDate,c).get(level5hsz.get(zhsz)).toString())!=0.0){
          if(!indExpe12){
              if(level5hsz.size()>1){
        table.addCell(cellEmpty);
     PdfPCell cellLeve4Lfx= new PdfPCell(new Paragraph("Retained earning at start of period",font2));
     cellLeve4Lfx.setColspan(5);
      cellLeve4Lfx.setFixedHeight(16.2f);
      cellLeve4Lfx.disableBorderSide(LEFT);
      cellLeve4Lfx.disableBorderSide(TOP);
      cellLeve4Lfx.disableBorderSide(RIGHT);
      cellLeve4Lfx.disableBorderSide(BOTTOM);
      cellLeve4Lfx.setPadding(.5f);
      cellLeve4Lfx.setBorderColorBottom(BaseColor.WHITE);
      cellLeve4Lfx.setBorderColorTop(BaseColor.WHITE);
      cellLeve4Lfx.setBorderColorLeft(BaseColor.WHITE);
      cellLeve4Lfx.setBorderColorRight(BaseColor.WHITE);
      cellLeve4Lfx.setVerticalAlignment(Element.ALIGN_TOP);
   
       table.addCell(cellLeve4Lfx);
              }
      indExpe12=true;
           }
      table.addCell(cellEmpty);
      PdfPCell cellLeve5Lx= new PdfPCell(new Paragraph(level5hsz.get(zhsz),font1));
      cellLeve5Lx.setFixedHeight(16.2f);
      cellLeve5Lx.disableBorderSide(LEFT);
      cellLeve5Lx.disableBorderSide(TOP);
      cellLeve5Lx.disableBorderSide(RIGHT);
      cellLeve5Lx.disableBorderSide(BOTTOM);
      cellLeve5Lx.setPadding(.5f);
      cellLeve5Lx.setBorderColorBottom(BaseColor.WHITE);
      cellLeve5Lx.setBorderColorTop(BaseColor.WHITE);
      cellLeve5Lx.setBorderColorLeft(BaseColor.WHITE);
      cellLeve5Lx.setBorderColorRight(BaseColor.WHITE);
      cellLeve5Lx.setVerticalAlignment(Element.ALIGN_TOP);
       table.addCell(cellLeve5Lx);
      table.addCell(cellEmpty);
   
      PdfPCell balAmountL=null;
      double amountL=parseDouble(rdb.getBalances(level5hsz.get(zhsz),startDate, endDate,c).get(level5hsz.get(zhsz)).toString());
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
       
       sumhs= sumhs+(parseDouble(rdb.getBalances(level5hsz.get(zhsz),startDate, endDate,c).get(level5hsz.get(zhsz)).toString()));     
       balOfProfit=true; 
        }
      
           zhsz++; 
                 }TotalHolder.put(19, sumhs);
              if(balOfProfit){
             
              if((level5hsz.size()>1)){
             table.addCell(cellEmpty);
          PdfPCell totalValue= new PdfPCell(new Paragraph("Total Value of Retained earnings",font3));
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
     if(TotalHolder.get(15)<0.0)
     {totalAmount= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(19)+"")+")",font1));
     } else{totalAmount= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(19)+""),font1));}
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
              }}
      table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
     table.addCell(cellEmpty);
              break;
        }
           
          yhsz++; }  
           
             
            if(balOfProfit||proposedDev||incomeTax||mainIncome||indicatorthIncome||indicatorInsurance|openingStock||purchases||directcost||factoryOver||closingStock||operatingEXP||adminExp||employExp||finExp||Insurance||general){
        double toBalanceSheet=0.0;
        toBalanceSheet=MAth.subtract(TotalHolder.get(23),TotalHolder.get(19));
       
       
       TotalHolder.put(24, toBalanceSheet);
       table.addCell(cellEmpty);
       PdfPCell cellLeve4a1L= new PdfPCell(new Paragraph("Retained Earnings At End Of Period",font2));
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
       if(TotalHolder.get(24)<0.0){totalAmountInvestsN= new PdfPCell(new Paragraph("("+ffm.formatForStatementNumbers(TotalHolder.get(24)+"")+")",font2)); }
       else{totalAmountInvestsN= new PdfPCell(new Paragraph(ffm.formatForStatementNumbers(TotalHolder.get(24)+""),font2)); }
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
       }   
    

       returnvalue= TotalHolder.get(24)+"";
    
  return  returnvalue;
          
     
         
    }
    
    
}
