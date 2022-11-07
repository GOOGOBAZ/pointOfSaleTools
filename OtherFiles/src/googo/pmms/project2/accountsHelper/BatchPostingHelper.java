/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.accountsHelper;

import googo.pmms.project2.databases.DatabaseQuaries;
import googo.pmms.project2.databases.loanDatabaseQuaries;
import googo.pmms.project2.excel.CreateExcelWorkBook;
import googo.pmms.project2.frameHelper.ListDataModel;
import googo.pmms.project2.loanHelper.Amortization;
import static java.lang.Integer.parseInt;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.ButtonGroup;

/**
 *
 * @author STAT SOLUTIONS
 */
public class BatchPostingHelper {
    
        List dataCollector,amortDetails;
        loanDatabaseQuaries loan=new loanDatabaseQuaries();
        fileInputOutPutStreams fios= new fileInputOutPutStreams(); 
        DecimalFormat df1 = new DecimalFormat("#.##");
        DecimalFormat df2 = new DecimalFormat("#");
        DecimalFormat NumberFormat =new DecimalFormat("#,###.##");
        Calendar cal = Calendar.getInstance();
        int n,s;
        SimpleDateFormat sdk =new SimpleDateFormat("dd.MM.yyyy");
        CreateExcelWorkBook writeExcel= new CreateExcelWorkBook();
        DatabaseQuaries dbq= new DatabaseQuaries();
        double loanAmount,loanTerm,interestRate, annualInterest=0.0,interest=0.0;
        Calendar startDate;
        Formartter fmt = new Formartter();
            
    public synchronized void createNewPostingItem(ListDataModel model,String identifiere){
   
        String theCode=batchCodex();
        
    switch(identifiere){
    
     case "Loan":
           int i=0,j=0; 

    while(i<model.getRowCount()){

   
   

        
//     tableHeaders.add("S/n");0
//        tableHeaders.add("Borrower");1
//        tableHeaders.add("Account Number");2
//        tableHeaders.add("Interest");3
//        tableHeaders.add("Accum Interest");4
//        tableHeaders.add("Penalty");5
//        tableHeaders.add("Principal");6
//        tableHeaders.add("Loan Amount"); 7
//        tableHeaders.add("Due Date"); 8 

 dataCollector = new ArrayList();
   
     dataCollector.add(theCode);//    //Batch id:0
     
     dataCollector.add(model.getValueAt(i,1).toString());//    //account Name:1
     
     dataCollector.add(model.getValueAt(i,2).toString());//Account number:2
     
        dataCollector.add("0.0"); //Savings :3
        
          dataCollector.add("0.0");    //Shares :4
          
      dataCollector.add(model.getValueAt(i,3).toString());   //Interest :5
      
      dataCollector.add(model.getValueAt(i,4).toString());   //Accumulated Interest :6
      
      dataCollector.add(model.getValueAt(i,5).toString());    //loan penalty 7
      
      dataCollector.add(model.getValueAt(i,6).toString());    //princimpal amount:8
      
             dataCollector.add(model.getValueAt(i,7).toString());   // Total Amount :9
             
                dataCollector.add(model.getValueAt(i,8).toString());       //Other batch1:10
                
                 dataCollector.add("NA");     //Other batch2:11
                
                 dataCollector.add("NA");           //Other batch3:12
                
         dataCollector.add("NA");        //Other batch4:13  
     
    dbq.createFinalBatchPostingDetails(dataCollector); 
  
    i++;
    }
    
    
amortDetails = new ArrayList();

amortDetails.add(theCode);//Batch id:0

amortDetails.add("Loan");//Batch Type:1

amortDetails.add(model.getRowCount());//Number of Entries:2

amortDetails.add(model.getValueAt(model.getRowCount()-1, 7).toString());//Total Amount :3
      
  amortDetails.add("Created");//BatchStatus:4
         
    amortDetails.add("NA");  //Other batch1:5
    
     amortDetails.add("NA"); //Other batch1:6
     
      amortDetails.add("NA");  //Other batch1:7
      
       amortDetails.add("NA");  //Other batch1:8
   
   
 
   dbq.createFinalBatchPostingSummury(amortDetails);
 
 
         
         break;
     case "Savings":
          int is=0,js=0; 

    while(is<model.getRowCount()){

   
   

        
//     tableHeaders.add("S/n");0
//        tableHeaders.add("Borrower");1
//        tableHeaders.add("Account Number");2
//        tableHeaders.add("Interest");3
//        tableHeaders.add("Accum Interest");4
//        tableHeaders.add("Penalty");5
//        tableHeaders.add("Principal");6
//        tableHeaders.add("Loan Amount"); 7
//        tableHeaders.add("Due Date"); 8 

 dataCollector = new ArrayList();
   
     dataCollector.add(theCode);//    //Batch id:0
     
     dataCollector.add(model.getValueAt(is,1).toString());//    //account Name:1
     
     dataCollector.add(model.getValueAt(is,2).toString());//Account number:2
     
        dataCollector.add(model.getValueAt(is,3).toString()); //Savings :3
        
          dataCollector.add("0.0");    //Shares :4
          
      dataCollector.add("0.0");   //Interest :5
      
      dataCollector.add("0.0");   //Accumulated Interest :6
      
      dataCollector.add("0.0");    //loan penalty 7
      
      dataCollector.add("0.0");    //princimpal amount:8
      
             dataCollector.add(model.getValueAt(is,4).toString());   // Total Amount :9
             
                dataCollector.add("NA");       //Other batch1:10
                
                 dataCollector.add("NA");     //Other batch2:11
                
                 dataCollector.add("NA");           //Other batch3:12
                
         dataCollector.add("NA");        //Other batch4:13  
     
    dbq.createFinalBatchPostingDetails(dataCollector); 
  
    is++;
    }
    
    
amortDetails = new ArrayList();

amortDetails.add(theCode);//Batch id:0

amortDetails.add("Savings");//Batch Type:1

amortDetails.add(model.getRowCount());//Number of Entries:2

amortDetails.add(model.getValueAt(model.getRowCount()-1, 4).toString());//Total Amount :3
      
  amortDetails.add("Created");//BatchStatus:4
         
    amortDetails.add("NA");  //Other batch1:5
    
     amortDetails.add("NA"); //Other batch1:6
     
      amortDetails.add("NA");  //Other batch1:7
      
       amortDetails.add("NA");  //Other batch1:8
   
   
 
   dbq.createFinalBatchPostingSummury(amortDetails);
 
 
         
         break;
     case "Shares":
          int iss=0,jss=0; 

    while(iss<model.getRowCount()){

   
   

        
//     tableHeaders.add("S/n");0
//        tableHeaders.add("Borrower");1
//        tableHeaders.add("Account Number");2
//        tableHeaders.add("Interest");3
//        tableHeaders.add("Accum Interest");4
//        tableHeaders.add("Penalty");5
//        tableHeaders.add("Principal");6
//        tableHeaders.add("Loan Amount"); 7
//        tableHeaders.add("Due Date"); 8 

 dataCollector = new ArrayList();
   
     dataCollector.add(theCode);//    //Batch id:0
     
     dataCollector.add(model.getValueAt(iss,1).toString());//    //account Name:1
     
     dataCollector.add(model.getValueAt(iss,2).toString());//Account number:2
     
        dataCollector.add("0.0"); //Savings :3
        
          dataCollector.add(model.getValueAt(iss,3).toString());    //Shares :4
          
      dataCollector.add("0.0");   //Interest :5
      
      dataCollector.add("0.0");   //Accumulated Interest :6
      
      dataCollector.add("0.0");    //loan penalty 7
      
      dataCollector.add("0.0");    //princimpal amount:8
      
             dataCollector.add(model.getValueAt(iss,4).toString());   // Total Amount :9
             
                dataCollector.add("NA");       //Other batch1:10
                
                 dataCollector.add("NA");     //Other batch2:11
                
                 dataCollector.add("NA");           //Other batch3:12
                
         dataCollector.add("NA");        //Other batch4:13  
     
    dbq.createFinalBatchPostingDetails(dataCollector); 
  
    iss++;
    }
    
    
amortDetails = new ArrayList();

amortDetails.add(theCode);//Batch id:0

amortDetails.add("Shares");//Batch Type:1

amortDetails.add(model.getRowCount());//Number of Entries:2

amortDetails.add(model.getValueAt(model.getRowCount()-1, 4).toString());//Total Amount :3
      
  amortDetails.add("Created");//BatchStatus:4
         
    amortDetails.add("NA");  //Other batch1:5
    
     amortDetails.add("NA"); //Other batch1:6
     
      amortDetails.add("NA");  //Other batch1:7
      
       amortDetails.add("NA");  //Other batch1:8
   
   
 
   dbq.createFinalBatchPostingSummury(amortDetails);
         
         
         break;
    
    
    
    
    
    
    }
    
    
    
    }
  
    
    
    
    public String batchCodex(){
        String batch="";int theNumber=0;


        fios.forceFileExistanceZero(fios.createFileName("postingEntry", "generalTrn", "batchNumberx.txt"));   

        String Thebatch= fios.stringFileReader(fios.createFileName("postingEntry", "generalTrn", "batchNumberx.txt"));
        
        theNumber=parseInt(Thebatch)+1;
        
        fios.stringFileWriter(fios.createFileName("postingEntry", "generalTrn", "batchNumberx.txt"), theNumber+"");
        
        batch=""+theNumber+"";
        
        return batch; 
        } 

}
