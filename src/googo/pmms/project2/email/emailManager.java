/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.email;

import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databases.DatabaseQuaries;
import googo.pmms.project2.databases.ReportsDatabase;
import googo.pmms.project2.databases.loanDatabaseQuaries;
import googo.pmms.project2.reportsHelper.BalanceSheet;
import googo.pmms.project2.reportsHelper.OtherLoanReports;
import googo.pmms.project2.reportsHelper.ProfitAndLoss;
import googo.pmms.project2.reportsHelper.ReportsPDFFormulars;
import googo.pmms.project2.reportsHelper.loanStatement;
import googo.pmms.project2.reportsHelper.trialBalance;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

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
      
      loanStatement loanS=new loanStatement();
      loanDatabaseQuaries loan=new loanDatabaseQuaries();
      ProfitAndLoss PAndL=new ProfitAndLoss();
      ReportsPDFFormulars statement= new ReportsPDFFormulars();
      BalanceSheet balSheet= new BalanceSheet();
      
      trialBalance trialBal=new trialBalance();
      
        public synchronized boolean sendFinalEmailReports(List<List> emailDetails,List dateRange,String RecepientGroup,Component c){

        boolean successfull=false;

        

        


        if(RecepientGroup.equalsIgnoreCase("Customer/Member")){
            
            
            
         List recipientsAccountsCustomers=dbq.accountNumbersCustomers();
          
         for(Object  accountNumberCustomer:recipientsAccountsCustomers){
            
//             JOptionPane.showMessageDialog(c, accountNumberCustomer);
            sendFinalEmailReportsh(emailDetails,dateRange,accountNumberCustomer.toString(),c); 
             
        
  try {
        //   successfull=SendMail.sendEmail(recipients, from, dateRange.get(2).toString(), dateRange.get(3).toString(), attachements, "smtp.gmail.com", userName, password, SendMail.SendMethod.HTTP);
        wait(20000);
        } catch (InterruptedException ex) {
        Logger.getLogger(emailManager.class.getName()).log(Level.SEVERE, null, ex);
        }
         }
        //      
        }else if(RecepientGroup.equalsIgnoreCase("BoardMembers/Staff")){

         List recipientsAccountsBoardAndStaff=dbq.boardMembersAndStaff(); 

 for(Object  accountNumberStaff:recipientsAccountsBoardAndStaff){
           
      sendFinalEmailReportsh(emailDetails,dateRange,accountNumberStaff.toString(),c); 
     
     
     
          try {
        //   successfull=SendMail.sendEmail(recipients, from, dateRange.get(2).toString(), dateRange.get(3).toString(), attachements, "smtp.gmail.com", userName, password, SendMail.SendMethod.HTTP);
        wait(20000);
        } catch (InterruptedException ex) {
        Logger.getLogger(emailManager.class.getName()).log(Level.SEVERE, null, ex);
        }

         }
        }

      






        return successfull;  


        }
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
 public synchronized boolean sendFinalEmailReportshX(List<List> emailDetails,List messageBody,String recipientsEmail,Component c){
             
   boolean successfull=false;

//   JOptionPane.showMessageDialog(c, emailDetails.size());
 
  List    attachements=new ArrayList();    
     
      for(List report:emailDetails){
        
       
          List account=dbq.accountNumberByEmail(recipientsEmail);
          
          
          for(Object accountNumber:account){
              
//        JOptionPane.showMessageDialog(c, report.get(0).toString()+","+report.get(1).toString()+","+report.get(2).toString());
          
     switch(report.get(0).toString()){
         
   
        
              case "Daily Group ROI":
          if(oLoanR.groupDailyReturnOnInvestemnt(form.forDatabaseWithFullYearBeginningWithDate(report.get(1).toString()),form.forDatabaseWithFullYearBeginningWithDate(report.get(2).toString()), "DailyGroupReturnOnInvestment"+dbq.AccountName(accountNumber.toString()))){
            attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "DailyGroupReturnOnInvestment"+dbq.AccountName(accountNumber.toString())+".pdf"));   
          }        
        
             break;
          
               case "CashLedger":
                   
             accountNumber="01123000110" ;     
          if(statement.createStatement(accountNumber.toString(),form.forDatabaseWithFullYearBeginningWithDate(report.get(1).toString()),form.forDatabaseWithFullYearBeginningWithDate(report.get(2).toString()), "Ledger-"+dbq.AccountName(accountNumber.toString()))){
            attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "Ledger-"+dbq.AccountName(accountNumber.toString())+".pdf"));   
          }        
        
             break;
          
             
 case "Monthly Group ROI":
      
                 if(oLoanR.createReturnOnInvestmentMonthlyGroup(form.forDatabaseWithFullYearBeginningWithDate(report.get(1).toString()),form.forDatabaseWithFullYearBeginningWithDate(report.get(2).toString()), "MonthlyGroupReturnOnInvestment"+dbq.AccountName(accountNumber.toString()))){
            attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "MonthlyGroupReturnOnInvestment"+dbq.AccountName(accountNumber.toString())+".pdf"));   
          }       
             
             break;


             
              case "Aging Statement":
                  
if(oLoanR.createAgingReport("ArrearsReport")){
    
            attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1","ArrearsReport.pdf"));   
          }       
             
             break;
      
                    
                          case "Savings Statement":
                              JOptionPane.showConfirmDialog(c, accountNumber);
if(oLoanR.individualSavingsContributions(accountNumber.toString(),form.forDatabaseWithFullYearBeginningWithDate(report.get(1).toString()),form.forDatabaseWithFullYearBeginningWithDate(report.get(2).toString()), "MemberSavingsStatement"+dbq.AccountName(accountNumber.toString()))){
            attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "MemberSavingsStatement"+dbq.AccountName(accountNumber.toString())+".pdf"));   
          }       
             
             break;
                              
                              
                              
                              
                              
            case "Daily Individual ROI":
           
          if(oLoanR.createReturnOnInvestmentDailyIndividual(accountNumber.toString(),form.forDatabaseWithFullYearBeginningWithDate(report.get(1).toString()),form.forDatabaseWithFullYearBeginningWithDate(report.get(2).toString()), "DailyReturnOnInvestment"+dbq.AccountName(accountNumber.toString()))){
          attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "DailyReturnOnInvestment"+dbq.AccountName(accountNumber.toString())+".pdf"));  
          }   
             
             
             break;
           
                case "Monthly Individual ROI":
                 if(oLoanR.individualMontlyROIpdf(accountNumber.toString(),form.forDatabaseWithFullYearBeginningWithDate(report.get(1).toString()),form.forDatabaseWithFullYearBeginningWithDate(report.get(2).toString()), "MonthlyReturnOnInvestment"+dbq.AccountName(accountNumber.toString()))){
            attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "MonthlyReturnOnInvestment"+dbq.AccountName(accountNumber.toString())+".pdf"));   
          }    
             
             break;


 case "Ledger Statement":
if(pdf.createStatement(accountNumber.toString(),form.forDatabaseWithFullYearBeginningWithDate(report.get(1).toString()),form.forDatabaseWithFullYearBeginningWithDate(report.get(2).toString()), "MemberLedgerStatement"+dbq.AccountName(accountNumber.toString()))){
            attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "MemberLedgerStatement"+dbq.AccountName(accountNumber.toString())+".pdf"));   
          }       
             
             break;
             
             
             
                case "Shares Statement":
                        JOptionPane.showMessageDialog(c, "accounts");
if(oLoanR.individualSharesContributions(accountNumber.toString(),form.forDatabaseWithFullYearBeginningWithDate(report.get(1).toString()),form.forDatabaseWithFullYearBeginningWithDate(report.get(2).toString()), "MemberSharesStatement"+dbq.AccountName(accountNumber.toString()))){
            attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "MemberSharesStatement"+dbq.AccountName(accountNumber.toString())+".pdf"));   
          }       
             
             break;   
             
                case "Loan Statement":
                    JOptionPane.showMessageDialog(c, "accounts");
                    if(loan.testTable("newloan"+accountNumber)){
if(loanS.createRunningLoanStatement(accountNumber.toString(),"LoanStatement"+dbq.AccountName(accountNumber.toString()))){
            attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "LoanStatement"+dbq.AccountName(accountNumber.toString())+".pdf"));   
}
          }       
             
             break; 
             
                 case "ProfitAndLoss":
if(PAndL.createPAndL(form.forDatabaseWithFullYearBeginningWithDate(report.get(1).toString()),form.forDatabaseWithFullYearBeginningWithDate(report.get(2).toString()), "ProfitAndLossStatement",c)){
            attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "ProfitAndLossStatement.pdf"));   
          }       
             
             break; 
                 case "BalanceSheet":
if(balSheet.createBalanceSheet(form.forDatabaseWithFullYearBeginningWithDate(report.get(2).toString()), "BalanceSheetStatement",c)){
            attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "BalanceSheetStatement.pdf"));   
          }       
             
             break; 
                 case "TrialBalance":
if(trialBal.createTrialBalance(form.forDatabaseWithFullYearBeginningWithDate(report.get(1).toString()),form.forDatabaseWithFullYearBeginningWithDate(report.get(2).toString()), "trialBalanceStatement",c)){
            attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "trialBalanceStatement.pdf"));   
          }       
             
             break; 
             
             case "Other External":
        attachements.add(report.get(2).toString());   
//                JOptionPane.showMessageDialog(c, report.get(2).toString());
             break; 
       
      }
       
 
          }
          
       
       }
       
    String from=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail","authenticateId.txt"));
   
   String userName=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail","authenticateId.txt"));
   
      String password=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "authenticatePW.txt"));
      
       String smtpServer=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "authenticateSMTP.txt"));
      
      if(!attachements.isEmpty()){
//          JOptionPane.showMessageDialog(c, "recipientsEmail="+recipientsEmail+"\n"+"from="+from+"\n"+"messageBody.get(0).toString()="+messageBody.get(1).toString()+"\n"+"messageBody.get(2).toString()="+messageBody.get(2).toString()+"smtpServer="+smtpServer+"\n"+"password="+password);
   successfull= emailMe.sendEmail(recipientsEmail, from, messageBody.get(1).toString(), messageBody.get(2).toString(), attachements, smtpServer, userName, password, SendMail.SendMethod.TLS,c);
      }else{
      
    successfull=false;  
//      }    
       
       
       }


    
   return successfull;  
    
    
}

public synchronized boolean sendFinalEmailReportsh(List<List> emailDetails,List messageBody,String accountNumber,Component c){
//             JOptionPane.showMessageDialog(c, messageBody); 
   boolean successfull=false;

 
  List    attachements=new ArrayList();    
      
      String recipientsEmail=dbq.customerEmail(accountNumber); 
      

      for(List report:emailDetails){
        
//         JOptionPane.showMessageDialog(c, report.get(0).toString()); 
     switch(report.get(0).toString()){
       
         
              case "Daily Group ROI":
//                   JOptionPane.showMessageDialog(c,"Daily Group ROI"); 
          if(oLoanR.groupDailyReturnOnInvestemnt(form.forDatabaseWithFullYearBeginningWithDate(report.get(1).toString()),form.forDatabaseWithFullYearBeginningWithDate(report.get(2).toString()), "DailyGroupReturnOnInvestment"+dbq.AccountName(accountNumber))){
            attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "DailyGroupReturnOnInvestment"+dbq.AccountName(accountNumber)+".pdf"));   
          }        
        
             break;
             
              case "CashLedger":
//                   JOptionPane.showMessageDialog(c,"CashLedger");     
             accountNumber="01123000110" ;     
          if(statement.createStatement(accountNumber,form.forDatabaseWithFullYearBeginningWithDate(report.get(1).toString()),form.forDatabaseWithFullYearBeginningWithDate(report.get(2).toString()), "Ledger-"+dbq.AccountName(accountNumber))){
            attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "Ledger-"+dbq.AccountName(accountNumber)+".pdf"));   
          }        
        
             break;
          
 case "Monthly Group ROI":
//          JOptionPane.showMessageDialog(c,"\"Monthly Group ROI\"");  
                 if(oLoanR.createReturnOnInvestmentMonthlyGroup(form.forDatabaseWithFullYearBeginningWithDate(report.get(1).toString()),form.forDatabaseWithFullYearBeginningWithDate(report.get(2).toString()), "MonthlyGroupReturnOnInvestment"+dbq.AccountName(accountNumber))){
            attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "MonthlyGroupReturnOnInvestment"+dbq.AccountName(accountNumber)+".pdf"));   
          }       
             
             break;


             
              case "Aging Statement":
//                     JOptionPane.showMessageDialog(c,"Aging Statement");  
if(oLoanR.createAgingReport("ArrearsReport"+dbq.AccountName(accountNumber))){
    
            attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1","ArrearsReport"+dbq.AccountName(accountNumber)+".pdf"));   
          }       
             
             break;
      
                    
                          case "Savings Statement":
//                                 JOptionPane.showMessageDialog(c,"Savings Statement");  
if(oLoanR.individualSavingsContributions(accountNumber,form.forDatabaseWithFullYearBeginningWithDate(report.get(1).toString()),form.forDatabaseWithFullYearBeginningWithDate(report.get(2).toString()), "MemberSavingsStatement"+dbq.AccountName(accountNumber))){
            attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "MemberSavingsStatement"+dbq.AccountName(accountNumber)+".pdf"));   
          }       
             
             break;
                              
                              
                              
                              
                              
            case "Daily Individual ROI":
//                  JOptionPane.showMessageDialog(c,"Daily Individual ROI");  
          if(oLoanR.createReturnOnInvestmentDailyIndividual(accountNumber,form.forDatabaseWithFullYearBeginningWithDate(report.get(1).toString()),form.forDatabaseWithFullYearBeginningWithDate(report.get(2).toString()), "DailyReturnOnInvestment"+dbq.AccountName(accountNumber))){
          attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "DailyReturnOnInvestment"+dbq.AccountName(accountNumber)+".pdf"));  
          }   
             
             
             break;
           
                case "Monthly Individual ROI":
//                       JOptionPane.showMessageDialog(c,"Monthly Individual ROI");  
                 if(oLoanR.individualMontlyROIpdf(accountNumber,form.forDatabaseWithFullYearBeginningWithDate(report.get(1).toString()),form.forDatabaseWithFullYearBeginningWithDate(report.get(2).toString()), "MonthlyReturnOnInvestment"+dbq.AccountName(accountNumber))){
            attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "MonthlyReturnOnInvestment"+dbq.AccountName(accountNumber)+".pdf"));   
          }    
             
             break;


       case "Loan Statement":
//              JOptionPane.showMessageDialog(c,"Loan Statement"+";"+loan.testTable("newloan"+accountNumber)+";"+"newloan"+accountNumber);  
           
            if(loan.loanExists(accountNumber)){
if(loanS.createRunningLoanStatement(accountNumber,"LoanStatement"+dbq.AccountName(accountNumber))){
            attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "LoanStatement"+dbq.AccountName(accountNumber)+".pdf"));   
          }  
            }
break;
             
             
             
                case "Shares Statement":
//                       JOptionPane.showMessageDialog(c,"Shares Statement");  
if(oLoanR.individualSharesContributions(accountNumber,form.forDatabaseWithFullYearBeginningWithDate(report.get(1).toString()),form.forDatabaseWithFullYearBeginningWithDate(report.get(2).toString()), "MemberSharesStatement"+dbq.AccountName(accountNumber))){
            attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "MemberSharesStatement"+dbq.AccountName(accountNumber)+".pdf"));   
          }       
             
             break;   
             
        
           case "ProfitAndLoss":
if(PAndL.createPAndL(form.forDatabaseWithFullYearBeginningWithDate(report.get(1).toString()),form.forDatabaseWithFullYearBeginningWithDate(report.get(2).toString()), "ProfitAndLossStatement",c)){
            attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "ProfitAndLossStatement.pdf"));   
          }       
             
             break; 
             
             
                case "BalanceSheet":
if(balSheet.createBalanceSheet(form.forDatabaseWithFullYearBeginningWithDate(report.get(2).toString()), "BalanceSheetStatement",c)){
            attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "BalanceSheetStatement.pdf"));   
          }       
             
             break; 
             
             
                 case "TrialBalance":
if(oLoanR.individualSharesContributions(accountNumber,form.forDatabaseWithFullYearBeginningWithDate(report.get(1).toString()),form.forDatabaseWithFullYearBeginningWithDate(report.get(2).toString()), "MemberSharesStatement")){
            attachements.add(fios.createFileName("PMMS_Statements", "PMMS_Statements1", "MemberSharesStatement.pdf"));   
          }       
             break; 
             
                case "Other External":
 attachements.add(report.get(2).toString());        
             break; 
      }
       
 
 
          
       
       }
       
    String from=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail","authenticateId.txt"));
   
   String userName=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail","authenticateId.txt"));
   
      String password=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "authenticatePW.txt"));
      
       String smtpServer=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "authenticateSMTP.txt"));
      
      if(!attachements.isEmpty()){
//          JOptionPane.showMessageDialog(c, "recipientsEmail="+recipientsEmail+"\n"+"from="+from+"\n"+"messageBody.get(0).toString()="+messageBody.get(0).toString()+"\n"+"messageBody.get(2).toString()="+messageBody.get(2).toString()+"smtpServer="+smtpServer+"\n"+"password="+password);
   successfull= emailMe.sendEmail(recipientsEmail, from, messageBody.get(0).toString(), messageBody.get(1).toString(), attachements, smtpServer, userName, password, SendMail.SendMethod.TLS,c);
      }else{
      
    successfull=false;  
//      }    
       
       
       }


    
   return successfull;  
    
    
}
 
 public synchronized boolean processRecepientsEmailMany(List emailDetails,List attachements,Component c ){
 
boolean successfull=false;
       
       String[] theEmails=emailDetails.get(0).toString().split("[;]");
       
       List theNewEmails=Arrays.asList(theEmails);
       
       for(Object em: theNewEmails){
           
       
    String from=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail","authenticateId.txt"));
   
   String userName=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail","authenticateId.txt"));
   
      String password=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "authenticatePW.txt"));
      
       String smtpServer=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "authenticateSMTP.txt"));
      
      if(!emailDetails.isEmpty()){
      
   successfull= emailMe.sendEmail(em.toString(), from, emailDetails.get(1).toString(), emailDetails.get(2).toString(), attachements, smtpServer, userName, password, SendMail.SendMethod.TLS,c);
    
      }else{
      
    successfull=false;  
   
       
       
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

public synchronized boolean processRecepientsEmailManyWithReports(List<List> emailDetailsX,List dateRange,Component c){
 
boolean successfull=false;
       
       String[] theEmails=dateRange.get(0).toString().split("[;]");
       
       List theNewEmails=Arrays.asList(theEmails);
       
       for(Object em: theNewEmails){
           
sendFinalEmailReportshX(emailDetailsX,dateRange,em.toString(),c);
      
        try {
        //   successfull=SendMail.sendEmail(recipients, from, dateRange.get(2).toString(), dateRange.get(3).toString(), attachements, "smtp.gmail.com", userName, password, SendMail.SendMethod.HTTP);
        wait(20000);
        } catch (InterruptedException ex) {
        Logger.getLogger(emailManager.class.getName()).log(Level.SEVERE, null, ex);
        }
      
       }
 return successfull;
 
 }
public synchronized boolean processRecepientsEmailIndividualWithReports(List<List> emailDetailsX,List dateRange,Component c){
 
boolean successfull=false;
       
       String theEmails=dateRange.get(0).toString();
  
       

           
sendFinalEmailReportshX(emailDetailsX,dateRange,theEmails,c);
      
       
 return successfull;
 
 }
 
 
  public boolean processRecepientsEmailIndividual(List emailDetails,List attachements,Component c ){
 
boolean successfull=false;
       
       
       
       
    String from=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail","authenticateId.txt"));
   
   String userName=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail","authenticateId.txt"));
   
      String password=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "authenticatePW.txt"));
      
       String smtpServer=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "authenticateSMTP.txt"));
      
      if(!emailDetails.isEmpty()){
      
   successfull= emailMe.sendEmail(emailDetails.get(0).toString(), from, emailDetails.get(1).toString(), emailDetails.get(2).toString(), attachements, smtpServer, userName, password, SendMail.SendMethod.TLS,c);
      }else{
      
    successfull=false;  
   
       
       
       }

 return successfull;
 
 }
 
 
}
