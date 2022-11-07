/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.reportsHelper;

import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databases.ReportsDatabase;
import googo.pmms.project2.frameHelper.ListDataModel;
import googo.pmms.project2.frameHelper.MyTableModel;
import googo.pmms.project2.frameHelper.ReportsModelData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author SSRN
 */
public class LoanSavingsSharesOthers {
   ArrayList<String> data4, column1;   
    ReportsDatabase rdb = new ReportsDatabase();
       MyTableModel model;ReportsModelData model1;ListDataModel listModel;
    Formartter ffm= new Formartter();
       fileInputOutPutStreams fios= new fileInputOutPutStreams();
       
       public void createLoanSavingsOnOthers(JTable  table,int startTime,int endTime ){
           
          
          column1=new ArrayList();
          
            column1.add("S/N");
            column1.add("Ref No.");
            column1.add("Account Name");
             column1.add("Total Savings");
            column1.add("Total Loan");
            column1.add("Total Amount");
            column1.add("Last Txn Date");
        
          
      Map<Integer, List<Object>> dataBase=rdb. loanSavingsReport(startTime,endTime);
      
        model1= new ReportsModelData( dataBase, column1);
           table.setModel(model1);
           
       TableRowSorter<ReportsModelData> sorter = new TableRowSorter<>(model1);
      table.setRowSorter(sorter);
      
      
      }
       public List<List> createLoanSaveExcel(){
       
       List<List> Total=new ArrayList();
       List Totals=new ArrayList();
   
    Totals.add("S/N");
    Totals.add("Ref No.");
    Totals.add("Account Name");
    Totals.add("Total Savings");
    Totals.add("Total Loan");
    Totals.add("Total Amount");
     Totals.add("Last Txn Date");
    Total.add(Totals);
    
    int n=0; Map<Integer, List<Object>> dataBase=rdb. loanSavingsReport(fios.intFileReader(fios.createFileName("PMMS_Statements", "reports", "dateCarrier1.txt")),fios.intFileReader(fios.createFileName("PMMS_Statements", "reports", "dateCarrier2.txt")));
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
          data1.add(ffm.formatForStatementNumbers(realData.get(3).toString()));
    }
    if(z==4){
 data1.add(ffm.formatForStatementNumbers(realData.get(4).toString()));
   
    }
    if(z==5){ 
      data1.add(ffm.formatForStatementNumbers(realData.get(5).toString()));
    
    }
 if(z==6){ 
      data1.add(realData.get(6).toString());
    
    }

    z++;   
    }
Total.add(data1);
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
      data2.add(ffm.formatForStatementNumbers(realData.get(3).toString()));   
       }
    if(z==4){
data2.add(ffm.formatForStatementNumbers(realData.get(4).toString())); 
  
    }
    if(z==5){
   data2.add(ffm.formatForStatementNumbers(realData.get(5).toString())); 
  

    }
 if(z==6){
 data2.add(realData.get(6).toString());
  

    }
    z++;   
    } 
    Total.add(data2);
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
Total.add(data3);
    }
    return Total;
       
       
       }
       
       
       public void createLoanPaymentsOnOthers(JTable  table,int startTime,int endTime ){
           
          
          column1=new ArrayList();
          
            column1.add("S/N");
            column1.add("Ref No.");
            column1.add("Account Name");
            column1.add("Total Instalment Paid");
            column1.add("Total Amount");
            column1.add("Last Txn Date");
        
          
     List dataBase=rdb. loanPaymentReport(startTime,endTime);
      
        listModel= new ListDataModel( dataBase, column1);
           table.setModel(listModel);
           
       TableRowSorter<ListDataModel> sorter = new TableRowSorter<>(listModel);
      table.setRowSorter(sorter);
      
      
      }
       
           public void createSavingsPaymentsOnOthers(JTable  table,int startTime,int endTime ){
           
          
          column1=new ArrayList();
          
            column1.add("S/N");
            column1.add("Ref No.");
            column1.add("Account Name");
            column1.add("Total Instalment Paid");
            column1.add("Total Amount");
            column1.add("Last Txn Date");
        
          
     List dataBase=rdb. savingsPaymentReport(startTime,endTime);
      
        listModel= new ListDataModel( dataBase, column1);
           table.setModel(listModel);
           
       TableRowSorter<ListDataModel> sorter = new TableRowSorter<>(listModel);
      table.setRowSorter(sorter);
      
      
      }
//       public List<List> createLoanSaveExcel(){
//       
//       List<List> Total=new ArrayList();
//       List Totals=new ArrayList();
//   
//    Totals.add("S/N");
//    Totals.add("Ref No.");
//    Totals.add("Account Name");
//    Totals.add("Total Savings");
//    Totals.add("Total Loan");
//    Totals.add("Total Amount");
//     Totals.add("Last Txn Date");
//    Total.add(Totals);
//    
//    int n=0; Map<Integer, List<Object>> dataBase=rdb. loanSavingsReport(fios.intFileReader(fios.createFileName("PMMS_Statements", "reports", "dateCarrier1.txt")),fios.intFileReader(fios.createFileName("PMMS_Statements", "reports", "dateCarrier2.txt")));
//    if(!dataBase.isEmpty()){
//    while(n<dataBase.size()) {
//    List realData=dataBase.get(n);int z=0;
//    if(n==dataBase.size()-1){
//          List data1=new ArrayList();
//    while(z<realData.size()){
//        
//    if(z==0){
//       
//         data1.add(realData.get(0).toString());
//   
//    }
//    if(z==1){
//  data1.add(realData.get(1).toString());
//    
//    }
//    if(z==2){
//  data1.add(realData.get(2).toString());
//   
//    }
//
//    if(z==3){
//          data1.add(ffm.formatForStatementNumbers(realData.get(3).toString()));
//    }
//    if(z==4){
// data1.add(ffm.formatForStatementNumbers(realData.get(4).toString()));
//   
//    }
//    if(z==5){ 
//      data1.add(ffm.formatForStatementNumbers(realData.get(5).toString()));
//    
//    }
// if(z==6){ 
//      data1.add(realData.get(6).toString());
//    
//    }
//
//    z++;   
//    }
//Total.add(data1);
//    }else{
//          List data2=new ArrayList();
//    while(z<realData.size()){
//    if(z==0){
//    data2.add(realData.get(0).toString());
//    
//    }
//    if(z==1){
// data2.add(realData.get(1).toString());
//    }
//    if(z==2){
// data2.add(realData.get(2).toString());
//   
//    }
//
//    if(z==3){
//      data2.add(ffm.formatForStatementNumbers(realData.get(3).toString()));   
//       }
//    if(z==4){
//data2.add(ffm.formatForStatementNumbers(realData.get(4).toString())); 
//  
//    }
//    if(z==5){
//   data2.add(ffm.formatForStatementNumbers(realData.get(5).toString())); 
//  
//
//    }
// if(z==6){
// data2.add(realData.get(6).toString());
//  
//
//    }
//    z++;   
//    } 
//    Total.add(data2);
//    }
//
//    n++;
//    } 
//    }else{
//          List data3=new ArrayList();
//   data3.add("N/A");
//    data3.add("Empty selection from database");
//     data3.add("N/A");
//     data3.add("N/A");
//    data3.add("N/A");
//     data3.add("N/A");
//     data3.add("N/A");
//Total.add(data3);
//    }
//    return Total;
//       
//       
//       }
//       
//    
}
