/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.excel;

import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.xssf.usermodel.XSSFDataFormat;

/**
 *
 * @author Stanchart
 */
public class CreateExcelWorkBook {
     
    fileInputOutPutStreams fios= new fileInputOutPutStreams();
       DecimalFormat df2 = new DecimalFormat("#");
     SimpleDateFormat sdfS =new SimpleDateFormat("yyyy-MM-dd");
      SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
    public boolean createBalanceSheetTheBook(List<List> data,String sheetName,String fileName){
        
    boolean finished=true; FileOutputStream out=null;
    
   XSSFWorkbook workbook =new XSSFWorkbook();
   
    XSSFSheet sheet=  workbook.createSheet(sheetName);
    sheet.setColumnWidth(0, 10000);
    XSSFFont f=workbook.createFont();
    f.setBold(true);
    f.setFontHeight(16);
 XSSFCellStyle style1=workbook.createCellStyle();
 
  XSSFCellStyle style2=workbook.createCellStyle();
  
  XSSFCellStyle style3=workbook.createCellStyle();
  
  style1.setFillBackgroundColor(HSSFColor.SEA_GREEN.index);
  
  style3.setFont(f);

   int k=0;
while(k<data.size()){
    
    List value=data.get(k);
    XSSFRow row2= sheet.createRow(k);int t=0;
    
       
    
     Iterator<String> i1=value.iterator();
      while(i1.hasNext()){
   String item=i1.next();
   XSSFCell celli=row2.createCell(t);
   
   if(t==1){
       if(!item.isEmpty()){
    celli.setCellValue(parseDouble(item.replace(",", "").replace("(", "").replace(")", "")));
       }
   }else{
       if(item.equalsIgnoreCase("Assets")){
       celli.setCellValue(item);
       celli.setCellStyle(style3);
       }else{
   celli.setCellValue(item);
       }
   }
   
   if(k%2==0){
       celli.setCellStyle(style1);
    }else{
     celli.setCellStyle(style2);
    }
   t++;
   }
    
  k++;  
}

        try {
             out=new FileOutputStream(new File(fileName+".xlsx"));
        try {
            workbook.write(out);
             workbook.close();
        } catch (IOException ex) {
           finished=false;
        }
        } catch (FileNotFoundException ex) {
          finished=false;
        }
   
  
    return finished;
    }
    
    
    public boolean createSheet2TheBook(List<List> data,String sheetName,String fileName){
   boolean finished=true; FileOutputStream out=null;
    
   XSSFWorkbook workbook =new XSSFWorkbook();
   
    XSSFSheet sheet=  workbook.createSheet(sheetName);
    sheet.setColumnWidth(0, 2000);
     sheet.setColumnWidth(1, 7000);
      sheet.setColumnWidth(2, 6000);
      sheet.setColumnWidth(3, 6000);
      sheet.setColumnWidth(4, 6000);
      sheet.setColumnWidth(6, 6000);
      sheet.setColumnWidth(7, 6000);
      sheet.setColumnWidth(8, 4000);
    XSSFFont f=workbook.createFont();
    f.setBold(true);
    f.setFontHeight(16);
 XSSFCellStyle style1=workbook.createCellStyle();
 
  XSSFCellStyle style2=workbook.createCellStyle();
  
  XSSFCellStyle style3=workbook.createCellStyle();
  
  style1.setFillBackgroundColor(HSSFColor.BRIGHT_GREEN.index);
  style1.setBorderBottom(BorderStyle.THIN);
   style1.setBorderLeft(BorderStyle.THIN);
   style1.setBorderTop(BorderStyle.THIN);
   style1.setBorderRight(BorderStyle.THIN);
  style3.setFont(f);

   int k=0;
while(k<data.size()){
    
    List value=data.get(k);
    XSSFRow row2= sheet.createRow(k);int t=0;
    
       
    
     Iterator<String> i1=value.iterator();
      while(i1.hasNext()){
   String item=i1.next();
   XSSFCell celli=row2.createCell(t);
   
  
   celli.setCellValue(item);
       
 celli.setCellStyle(style1);
   t++;
   }
    
  k++;  
}

        try {
             out=new FileOutputStream(new File(fileName+".xlsx"));
        try {
            workbook.write(out);
             workbook.close();
        } catch (IOException ex) {
           finished=false;
        }
        } catch (FileNotFoundException ex) {
          finished=false;
        }
    return finished;
    }
    public boolean createSheet3TheBook(List<List> data,String sheetName,String fileName){
        
   boolean finished=true; FileOutputStream out=null;
    
   XSSFWorkbook workbook =new XSSFWorkbook();
   
    XSSFSheet sheet=  workbook.createSheet(sheetName);
 sheet.setColumnWidth(0, 3000);
  sheet.setColumnWidth(1, 3000);
      sheet.setColumnWidth(2, 7000);
  sheet.setColumnWidth(3, 3000);
    sheet.setColumnWidth(4, 3000);
     sheet.setColumnWidth(5, 3000);
    XSSFFont f=workbook.createFont();
    f.setBold(true);
    f.setFontHeight(16);
 XSSFCellStyle style1=workbook.createCellStyle();
 
  XSSFCellStyle style2=workbook.createCellStyle();
  
  XSSFCellStyle style3=workbook.createCellStyle();
  
  style1.setFillBackgroundColor(HSSFColor.BRIGHT_GREEN.index);
  style1.setBorderBottom(BorderStyle.THIN);
   style1.setBorderLeft(BorderStyle.THIN);
   style1.setBorderTop(BorderStyle.THIN);
   style1.setBorderRight(BorderStyle.THIN);
  style3.setFont(f);

   int k=0;
while(k<data.size()){
    
    List value=data.get(k);
    XSSFRow row2= sheet.createRow(k);int t=0;
    
       
    
     Iterator<String> i1=value.iterator();
      while(i1.hasNext()){
   String item=i1.next();
   XSSFCell celli=row2.createCell(t);
   
  
  
   celli.setCellValue(item);
       
 celli.setCellStyle(style1);
           
   t++;
   }
    
  k++;  
}

        try {
             out=new FileOutputStream(new File(fileName+".xlsx"));
        try {
            workbook.write(out);
             workbook.close();
        } catch (IOException ex) {
           finished=false;
        }
        } catch (FileNotFoundException ex) {
          finished=false;
        }
    return finished;
    }
    public boolean createSheet4TheBook(List<List> data,String sheetName,String fileName){
   
        boolean finished=true; FileOutputStream out=null;
    
   XSSFWorkbook workbook =new XSSFWorkbook();
   
    XSSFSheet sheet=  workbook.createSheet(sheetName);
    
 sheet.setColumnWidth(0, 3000);
  sheet.setColumnWidth(1, 3000);
      sheet.setColumnWidth(2, 7000);
  sheet.setColumnWidth(3, 3000);
    sheet.setColumnWidth(4, 3000);
     sheet.setColumnWidth(5, 3000);
      sheet.setColumnWidth(6, 3000);
    XSSFFont f=workbook.createFont();
    f.setBold(true);
    f.setFontHeight(16);
 XSSFCellStyle style1=workbook.createCellStyle();
 
  XSSFCellStyle style2=workbook.createCellStyle();
  
  XSSFCellStyle style3=workbook.createCellStyle();
  
  style1.setFillBackgroundColor(HSSFColor.BRIGHT_GREEN.index);
  style1.setBorderBottom(BorderStyle.THIN);
   style1.setBorderLeft(BorderStyle.THIN);
   style1.setBorderTop(BorderStyle.THIN);
   style1.setBorderRight(BorderStyle.THIN);
  style3.setFont(f);

   int k=0;
while(k<data.size()){
    
    List value=data.get(k);
    XSSFRow row2= sheet.createRow(k);int t=0;
    
       
    
     Iterator<String> i1=value.iterator();
      while(i1.hasNext()){
   String item=i1.next();
   XSSFCell celli=row2.createCell(t);
   
  
  
   celli.setCellValue(item);
       
 celli.setCellStyle(style1);
           
   t++;
   }
    
  k++;  
}

        try {
             out=new FileOutputStream(new File(fileName+".xlsx"));
        try {
            workbook.write(out);
             workbook.close();
        } catch (IOException ex) {
           finished=false;
        }
        } catch (FileNotFoundException ex) {
          finished=false;
        }
    return finished;
    }
    
    
    
    public boolean creatExcelFromTable(List<List> data,String sheetName,String fileName){
   
        boolean finished=true; FileOutputStream out=null;
    
   XSSFWorkbook workbook =new XSSFWorkbook();
   
    XSSFSheet sheet=  workbook.createSheet(sheetName);
    


   int k=0;
for(List value:data){

    XSSFRow row= sheet.createRow(k);int t=0;
    
       
for(Object item:value){
   
   XSSFCell cell=row.createCell(t);
   
  if(item!=null){
  
      
      
   cell.setCellValue(item.toString());
 
   
  }
           
   t++;
   }
    
  k++;  
}
sheet.autoSizeColumn(k, finished);
     
        try {
            workbook.write(fios.FileOutputStream(fios.createFileName1("Excel", "Files", fileName+".xlsx")));
             workbook.close();
        } catch (IOException ex) {
           finished=false;
        }
        
    return finished;
    }
    public boolean creatExcelFromTable1(List<List> data,String sheetName,String fileName){
   
        boolean finished=true; FileOutputStream out=null;
    
   XSSFWorkbook workbook =new XSSFWorkbook();
   
    XSSFSheet sheet=  workbook.createSheet(sheetName);
    


   int k=0;
for(List value:data){

    XSSFRow row= sheet.createRow(k);int t=0;
    
       
for(Object item:value){
   
   XSSFCell cell=row.createCell(t);
   
  if(item!=null){
  
      if(t==3){
      
      cell.setCellValue(item.toString());
    
      }else{
      
      
   cell.setCellValue(item.toString());
   
      }
 
   
  }
           
   t++;
   }
    
  k++;  
}
sheet.autoSizeColumn(k, finished);
     
        try {
            workbook.write(fios.FileOutputStream(fios.createFileName1("Excel", "Files", fileName+".xlsx")));
             workbook.close();
        } catch (IOException ex) {
           finished=false;
        }
        
    return finished;
    }
    
//    public boolean createSheet5TheBook(Map data,String fileName){
//    boolean finished=false;
//   
//    
//    
//    
//    return finished;
//    }
//    public boolean createSheet6TheBook(Map data,String fileName){
//    boolean finished=false;
//   
//    
//    
//    
//    return finished;
//    }
//    public boolean createSheet7TheBook(Map data,String fileName){
//    boolean finished=false;
//   
//    
//    
//    
//    return finished;
//    }
//    public boolean createSheet8TheBook(Map data,String fileName){
//    boolean finished=false;
//   
//    
//    
//    
//    return finished;
//    }
//    public boolean createSheet9TheBook(Map data,String fileName){
//    boolean finished=false;
//   
//    
//    
//    
//    return finished;
//    }
//    public boolean createSheet10TheBook(Map data,String fileName){
//    boolean finished=false;
//   
//    
//    
//    
//    return finished;
//    }
//    public boolean createSheet11TheBook(Map data,String fileName){
//    boolean finished=false;
//   
//    
//    
//    
//    return finished;
//    }
//    public boolean createSheet12TheBook(Map data,String fileName){
//    boolean finished=false;
//   
//    
//    
//    
//    return finished;
//    }
//    public boolean createSheet13TheBook(Map data,String fileName){
//    boolean finished=false;
//   
//    
//    
//    
//    return finished;
//    }
//    public boolean createSheet14TheBook(Map data,String fileName){
//    boolean finished=false;
//   
//    
//    
//    
//    return finished;
//    }
//    public boolean createSheet15TheBook(Map data,String fileName){
//    boolean finished=false;
//   
//    
//    
//    
//    return finished;
//    }
//    public boolean createSheet16TheBook(Map data,String fileName){
//    boolean finished=false;
//   
//    
//    
//    
//    return finished;
//    }
//    public boolean createSheet17TheBook(Map data,String fileName){
//    boolean finished=false;
//   
//    
//    
//    
//    return finished;
//    }
 
    public boolean creatSpecialExcelFromTable(List<List> data,String sheetName,String fileName){
   
        boolean finished=true; FileOutputStream out=null;
  
       
        
   XSSFWorkbook workbook =new XSSFWorkbook();
   
    XSSFSheet sheet=  workbook.createSheet(sheetName);
   XSSFCellStyle cellStyle = workbook.createCellStyle();
byte[] rgb = new byte[3];
rgb[0] = (byte) 242; // red
rgb[1] = (byte) 220; // green
rgb[2] = (byte) 219; // blue
XSSFColor myColor = new XSSFColor(rgb); // #f2dcdb
cellStyle.setFillForegroundColor(myColor);
cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);


  XSSFCellStyle cellStyle3 = workbook.createCellStyle();        
XSSFDataFormat format = workbook.createDataFormat();
cellStyle3.setDataFormat(format.getFormat("#,##0"));


   int k=0;
for(List value:data){

    XSSFRow row= sheet.createRow(k);int t=0;
    
       
for(Object item:value){
   
   XSSFCell cell=row.createCell(t);
   
  if(item!=null){
  
      if(k>0){
      
      if(t==9){
      
       cell.setCellValue(parseInt(item.toString().replace(".0", "")));
      }else if(t==5||t==6||t==7||t==55){
      
          try {
              cell.setCellValue(sdf.format(sdfS.parse(item.toString())));
          } catch (ParseException ex) {
              Logger.getLogger(CreateExcelWorkBook.class.getName()).log(Level.SEVERE, null, ex);
          }
      }else if(t>=11&&t<=24){
            cell.setCellValue(parseDouble(df2.format(parseDouble(item.toString()))));
          cell.setCellStyle(cellStyle3);

// Set Cell Type not really needed, the setCellValue does it
//cell.setCellType(Cell.CELL_TYPE_NUMERIC);
      
//   cell.setCellStyle(cellStyle);
      }else{
              
           cell.setCellValue(item.toString());    
              }
      }else{
      
       cell.setCellValue(item.toString()); 
         cell.setCellStyle(cellStyle);
      }
 
   
  }
           
   t++;
   }
sheet.autoSizeColumn(k, finished);
//     sheet.setColumnWidth(k, 7000); 
  k++;  
}

   
        try {
            workbook.write(fios.FileOutputStream(fios.createFileName1("Excel", "Files", fileName+".xlsx")));
             workbook.close();
        } catch (IOException ex) {
           finished=false;
        }
        
    return finished;
    }
}
