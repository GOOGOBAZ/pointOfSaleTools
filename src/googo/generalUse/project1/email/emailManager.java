/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.generalUse.project1.email;

import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databases.DatabaseQuaries;
import googo.pmms.project2.databases.ReportsDatabase;
import googo.pmms.project2.reportsHelper.OtherLoanReports;
import googo.pmms.project2.reportsHelper.ReportsPDFFormulars;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author STAT SOLUTIONS
 */
public class emailManager {
     DatabaseQuaries dbq= new DatabaseQuaries();
        fileInputOutPutStreams fios= new fileInputOutPutStreams();
   ReportsDatabase rdb = new ReportsDatabase();
   OtherLoanReports oLoanR=new OtherLoanReports();
      ReportsPDFFormulars pdf = new ReportsPDFFormulars();
        Formartter form= new Formartter();
      SendMail emailMe= new SendMail();
   public synchronized boolean sendFinalEmail(List emailDetails,List dateRange){
             
   boolean successfull=false;
       List allCustomers=rdb.customerAccounts(); 
       
       for(Object customer:allCustomers){
//     String account="05502000110";  
  List    attachements=new ArrayList();    
      
      String recipients=dbq.customerEmail(customer.toString());    
//      String recipients="augbazi@gmail.com";
      for(Object report:emailDetails){
       
     switch(report.toString()){
         
         case "Daily Individual ROI":
           
          if(oLoanR.createReturnOnInvestmentDailyIndividual(customer.toString(),form.forDatabaseWithFullYearBeginningWithDate(dateRange.get(0).toString()),form.forDatabaseWithFullYearBeginningWithDate(dateRange.get(1).toString()), "DailyReturnOnInvestment"+customer.toString())){
          attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "DailyReturnOnInvestment"+customer.toString()+".pdf"));  
          }   
             
             
             break;
              case "Daily Group ROI":
          if(oLoanR.groupDailyReturnOnInvestemnt(form.forDatabaseWithFullYearBeginningWithDate(dateRange.get(0).toString()),form.forDatabaseWithFullYearBeginningWithDate(dateRange.get(1).toString()), "DailyGroupReturnOnInvestment"+customer.toString())){
            attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "DailyGroupReturnOnInvestment"+customer.toString()+".pdf"));   
          }        
        
             break;
                case "Monthly Individual ROI":
                 if(oLoanR.individualMontlyROIpdf(customer.toString(),form.forDatabaseWithFullYearBeginningWithDate(dateRange.get(0).toString()),form.forDatabaseWithFullYearBeginningWithDate(dateRange.get(1).toString()), "MonthlyReturnOnInvestment"+customer.toString())){
            attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "MonthlyReturnOnInvestment"+customer.toString()+".pdf"));   
          }    
             
             break;
 case "Monthly Group ROI":
      
                 if(oLoanR.createReturnOnInvestmentMonthlyGroup(form.forDatabaseWithFullYearBeginningWithDate(dateRange.get(0).toString()),form.forDatabaseWithFullYearBeginningWithDate(dateRange.get(1).toString()), "MonthlyGroupReturnOnInvestment"+customer.toString())){
            attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "MonthlyGroupReturnOnInvestment"+customer.toString()+".pdf"));   
          }       
             
             break;

 case "Ledger Statement":
if(pdf.createStatement(customer.toString(),form.forDatabaseWithFullYearBeginningWithDate(dateRange.get(0).toString()),form.forDatabaseWithFullYearBeginningWithDate(dateRange.get(1).toString()), "MemberLedgerStatement"+customer.toString())){
            attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "MemberLedgerStatement"+customer.toString()+".pdf"));   
          }       
             
             break;
      }
       
      
       
       
       }
       
    String from=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail","authenticateId.txt"));
   
   String userName=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail","authenticateId.txt"));
   
      String password=dateRange.get(4).toString();
      
      if(!attachements.isEmpty()){
   successfull= emailMe.sendEmail(recipients, from, dateRange.get(2).toString(), dateRange.get(3).toString(), attachements, "smtp.gmail.com", userName, password, SendMail.SendMethod.TLS);
      }else{
      
    successfull=false;  
//      }    
       
       
       }

       
 
       try {
           //   successfull=SendMail.sendEmail(recipients, from, dateRange.get(2).toString(), dateRange.get(3).toString(), attachements, "smtp.gmail.com", userName, password, SendMail.SendMethod.HTTP);
           wait(20000);
       } catch (InterruptedException ex) {
           Logger.getLogger(emailManager.class.getName()).log(Level.SEVERE, null, ex);
       }
   
   
  
   }
    
    
    
   return successfull;  
    
    
}


}
