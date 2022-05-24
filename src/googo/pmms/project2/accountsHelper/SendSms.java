/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.accountsHelper;


import googo.pmms.project2.databases.DatabaseQuaries;
import googo.pmms.project2.databases.ReportsDatabase;
import googo.pmms.project2.email.UrlEncodeingDecoding;
import java.awt.Component;
import static java.lang.Double.parseDouble;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

/**
 *
 * @author Stanchart
 */
public class SendSms extends UrlEncodeingDecoding {
    
    FormattingUrlData smsForm =new FormattingUrlData();
    
       fileInputOutPutStreams fios= new fileInputOutPutStreams(); 
       
       
          SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
          
       DatabaseQuaries dbq =new DatabaseQuaries();
       
           Formartter fmt= new Formartter();
       
       
       
         DecimalFormat NumberFormat =new DecimalFormat("#,###");
         


        public synchronized void createDisbursementPaymentSMS(String accountNumber,String amount,Component c){
    
List smsDetails=dbq.getSMSDetails(accountNumber,c);

if(smsBalanceEnough()){
    
  int id=dbq.creatNewMessage(smsDetails,"LoanDisbursement");  
    
dbq.updateCreatedMessage(createMessage(smsDetails,amount,"LoanDisbursement",id,accountNumber,c),id,c);

reduceBalance();

}else{

int id=dbq.creatInsufficientFundsInternalSms(smsDetails);

dbq.updateCreatedMessageInsufficient(createMessage(smsDetails,amount,"LoanDisbursement",id,accountNumber,c),id);

}



} 
        
//        public synchronized void createReceiptEmphasisSMS(String accountNumber,String amount,Component c){
//  
//List smsDetails=dbq.getSMSDetails(accountNumber);
//  JOptionPane.showMessageDialog(c, smsDetails.get(0));
//    JOptionPane.showMessageDialog(c, smsDetails.get(1));
////      JOptionPane.showMessageDialog(c, smsDetails.get(2));
//  
//if(smsBalanceEnough()){
//    
//  int id=dbq.creatNewMessage(smsDetails,"ReceiptEmphasis");  
//    JOptionPane.showMessageDialog(c, "id"+id);
//dbq.updateCreatedMessage(createMessage(smsDetails,amount,"ReceiptEmphasis",id,accountNumber,c),id,c);
//
//reduceBalance();
//
//}else{
//
//int id=dbq.creatInsufficientFundsInternalSms(smsDetails);
//
//dbq.updateCreatedMessageInsufficient(createMessage(smsDetails,amount,"LoanDisbursement",id,accountNumber,c),id);
//
//}
//
//
//
//} 
        
public synchronized void createSharesPaymentSMS(String accountNumber,String amount,Component c){
    
List smsDetails=dbq.getSMSDetails(accountNumber,c);

if(smsBalanceEnough()){
    
  int id=dbq.creatNewMessage(smsDetails,"SharesPayment");  
    
dbq.updateCreatedMessage(createMessage(smsDetails,amount,"SharesPayment",id,accountNumber,c),id,c);

reduceBalance();

}else{

int id=dbq.creatInsufficientFundsInternalSms(smsDetails);

dbq.updateCreatedMessageInsufficient(createMessage(smsDetails,amount,"SharesPayment",id,accountNumber,c),id);

}



} 

    public synchronized void createLoanPaymentSMS(String accountNumber,String amount,Component c){
    
List smsDetails=dbq.getSMSDetails(accountNumber,c);

if(smsBalanceEnough()){
    
  int id=dbq.creatNewMessage(smsDetails,"LoanPayment");  
    
dbq.updateCreatedMessage(createMessage(smsDetails,amount,"LoanPayment",id,accountNumber,c),id,c);

reduceBalance();

}else{

int id=dbq.creatInsufficientFundsInternalSms(smsDetails);

dbq.updateCreatedMessageInsufficient(createMessage(smsDetails,amount,"LoanPayment",id,accountNumber,c),id);

}



}    
        
public synchronized void createSavingsSMS(String accountNumber,String amount,Component c){
    
List smsDetails=dbq.getSMSDetails(accountNumber,c);

if(smsBalanceEnough()){
    
  int id=dbq.creatNewMessage(smsDetails,"Savings");  
    
dbq.updateCreatedMessage(createMessage(smsDetails,amount,"Savings",id,accountNumber,c),id,c);

reduceBalance();

}else{

int id=dbq.creatInsufficientFundsInternalSms(smsDetails);

dbq.updateCreatedMessageInsufficient(createMessage(smsDetails,amount,"Savings",id,accountNumber,c),id);

}



}




public synchronized void createwithdrawTransacttSMS(String accountNumber,String amount,Component c){
    
List smsDetails=dbq.getSMSDetails(accountNumber,c);

if(smsBalanceEnough()){
    
  int id=dbq.creatNewMessage(smsDetails,"withdrawFromAccount");  
    
dbq.updateCreatedMessage(createMessage(smsDetails,amount,"withdrawFromAccount",id,accountNumber,c),id,c);

reduceBalance();

}else{

int id=dbq.creatInsufficientFundsInternalSms(smsDetails);

dbq.updateCreatedMessageInsufficient(createMessage(smsDetails,amount,"withdrawFromAccount",id,accountNumber,c),id);

}



}


public synchronized void createLoanApliedSMS(String accountNumber,String amount,Component c){
    
List smsDetails=dbq.getSMSDetails(accountNumber,c);

if(smsBalanceEnough()){
    
  int id=dbq.creatNewMessage(smsDetails,"LoanApplied");  
    
dbq.updateCreatedMessage(createMessage(smsDetails,amount,"LoanApplied",id,accountNumber,c),id,c);

reduceBalance();

}else{

int id=dbq.creatInsufficientFundsInternalSms(smsDetails);

dbq.updateCreatedMessageInsufficient(createMessage(smsDetails,amount,"LoanApplied",id,accountNumber,c),id);

}



}

public synchronized void createLoanApprovedSMS(String accountNumber,String amount,Component c){
    
List smsDetails=dbq.getSMSDetails(accountNumber,c);

if(smsBalanceEnough()){
    
  int id=dbq.creatNewMessage(smsDetails,"LoanApproved");  
    
dbq.updateCreatedMessage(createMessage(smsDetails,amount,"LoanApproved",id,accountNumber,c),id,c);

reduceBalance();

}else{

int id=dbq.creatInsufficientFundsInternalSms(smsDetails);

dbq.updateCreatedMessageInsufficient(createMessage(smsDetails,amount,"LoanApproved",id,accountNumber,c),id);

}



}
public synchronized void createLoanDeclinedSMS(String accountNumber,String amount,Component c){
    
List smsDetails=dbq.getSMSDetails(accountNumber,c);

if(smsBalanceEnough()){
    
  int id=dbq.creatNewMessage(smsDetails,"LoanDeclined");  
    
dbq.updateCreatedMessage(createMessage(smsDetails,amount,"LoanDeclined",id,accountNumber,c),id,c);

reduceBalance();

}else{

int id=dbq.creatInsufficientFundsInternalSms(smsDetails);

dbq.updateCreatedMessageInsufficient(createMessage(smsDetails,amount,"LoanDeclined",id,accountNumber,c),id);

}



}
            
public synchronized void createSmsTomorrowLoanReminder(String accountNumber,String amount,Component c){

List smsDetails=dbq.getSMSDetails(accountNumber,c);

if(smsBalanceEnough()){
    
  int id=dbq.creatNewMessage(smsDetails,"LoanReminderTomorrowPayment");  
    
dbq.updateCreatedMessage(createMessage(smsDetails,amount,"LoanReminderTomorrowPayment",id,accountNumber,c),id,c);

reduceBalance();

}else{

int id=dbq.creatInsufficientFundsInternalSms(smsDetails);

dbq.updateCreatedMessageInsufficient(createMessage(smsDetails,amount,"LoanReminderTomorrowPayment",id,accountNumber,c),id);

}


}

public synchronized void createSmsTodayLoanReminder(String accountNumber,String amount,Component c){

List smsDetails=dbq.getSMSDetails(accountNumber,c);

if(smsBalanceEnough()){
    
  int id=dbq.creatNewMessage(smsDetails,"LoanReminderTodayPayment");  
    
dbq.updateCreatedMessage(createMessage(smsDetails,amount,"LoanReminderTodayPayment",id,accountNumber,c),id,c);

reduceBalance();

}else{

int id=dbq.creatInsufficientFundsInternalSms(smsDetails);

dbq.updateCreatedMessageInsufficient(createMessage(smsDetails,amount,"LoanReminderTodayPayment",id,accountNumber,c),id);

}


}

public synchronized void createSmsArrearsLoanReminder(String accountNumber,String amount,Component c){

List smsDetails=dbq.getSMSDetails(accountNumber,c);

if(smsBalanceEnough()){
    
  int id=dbq.creatNewMessage(smsDetails,"LoanReminderArrearsPayment");  
    
dbq.updateCreatedMessage(createMessage(smsDetails,amount,"LoanReminderArrearsPayment",id,accountNumber,c),id,c);

reduceBalance();

}else{

int id=dbq.creatInsufficientFundsInternalSms(smsDetails);

dbq.updateCreatedMessageInsufficient(createMessage(smsDetails,amount,"LoanReminderArrearsPayment",id,accountNumber,c),id);

}


}
public synchronized void createSmsAccountCreation(String accountNumber,String amount,Component c){

List smsDetails=dbq.getSMSDetails(accountNumber,c);

//JOptionPane.showMessageDialog(c, smsDetails.get(0));
//JOptionPane.showMessageDialog(c, smsDetails.get(1));
//JOptionPane.showMessageDialog(c, smsDetails.get(2));
if(smsBalanceEnough()){
    
  int id=dbq.creatNewMessage(smsDetails,"accountCreatedMember");  
    
dbq.updateCreatedMessage(createMessage(smsDetails,amount,"accountCreatedMember",id,accountNumber,c),id,c);

reduceBalance();

}else{

int id=dbq.creatInsufficientFundsInternalSms(smsDetails);

dbq.updateCreatedMessageInsufficient(createMessage(smsDetails,amount,"accountCreatedMember",id,accountNumber,c),id);

}


}



private List createMessage(List messageParamenters,String amountInvolved,String messageType,int dataBaseId,String accountNumber,Component c){
    
List messageDetails=new ArrayList();

switch(messageType){
 
    case "Savings":
        
         String mynumbers="",Message="", SendId="",entity="", saveBalance="";
         
         YearMonth theStartDateObject=YearMonth.parse(fmt.forDatabaseWithFullYearBeginningWithDate(sdf.format(new Date(System.currentTimeMillis()))).substring(0, 7));  
         String Month=theStartDateObject.getMonth().toString();
         int year=theStartDateObject.getYear();
         String[] CompanyName=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "shortCompanyName.txt")).split("[;]");
      
         saveBalance=dbq.accountBalance(accountNumber, sdf.format(new Date(System.currentTimeMillis())));
         
         if(CompanyName.length==2){
         
         entity=CompanyName[0]+"%20"+CompanyName[1];
         }else if(CompanyName.length>2){
           entity=CompanyName[0]+"%20"+CompanyName[1];
         }else if(CompanyName.length==1){
         entity=CompanyName[0];
         }
         String name=messageParamenters.get(0).toString().split("\\s")[0].trim();
         if(messageParamenters.get(1).toString().isEmpty()){
       if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllNumbers.txt"))==7){
       if(!messageParamenters.get(2).toString().isEmpty()){
        mynumbers="256"+messageParamenters.get(2).toString().substring(1, 10);
       }else{
       
       mynumbers="256782231039";
       }
       }else{
       
       mynumbers="256782231039";
       }
         
         }else{
          mynumbers="256"+messageParamenters.get(1).toString().substring(1, 10);
         }
          messageDetails.add(mynumbers);
       Message="Dear"+"%20"+name+"%2C"+"%0A"+entity+"%20"+"has"+"%20"+"received"+"%20"+Month+"%20"+year+"%20"+"Savings"+"%20"+"worth"+"%20"+"Ugx"+"%20"+NumberFormat.format(parseDouble(amountInvolved)).replace(",", "%2C")+"%20"+"and"+"%20"+"%20"+"your"+"%20"+"balance"+"%20"+"is"+"%20"+"Ugx"+"%20"+NumberFormat.format(parseDouble(saveBalance)).replace(",", "%2C")+"%2E"+"%20"+"%0A"+"Thank"+"%20"+"you"+"%20"+"for"+"%20"+"Saving"+"%20"+"with"+"%20"+"us"+"%2E";
         messageDetails.add(this.decodeString(Message));
      SendId="sacco";
            
for(int x=0;x<5;x++){
    
    if(x==0){
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), mynumbers);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
     if(x==1){
      fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), Message);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
      if(x==2){
         fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), SendId);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
       if(x==3){
             fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), dataBaseId+"");
   
    }

}
        break;
    case "LoanPayment":
   String mynumbersLP="",MessageLP="", SendIdLP="",entityLP="";
         
         String[] CompanyNameLP=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "shortCompanyName.txt")).split("[;]");
      
         if(CompanyNameLP.length==2){
         
         entityLP=CompanyNameLP[0]+"%20"+CompanyNameLP[1];
         
         }else if(CompanyNameLP.length>2){
             
           entityLP=CompanyNameLP[0]+"%20"+CompanyNameLP[1];
           
         }else if(CompanyNameLP.length==1){
             
         entityLP=CompanyNameLP[0];
         
         }
         String nameLP=messageParamenters.get(0).toString().split("\\s")[0].trim();
         if(messageParamenters.get(1).toString().isEmpty()){
       if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllNumbers.txt"))==7){
       if(!messageParamenters.get(2).toString().isEmpty()){
        mynumbersLP="256"+messageParamenters.get(2).toString().substring(1, 10);
       }else{
       
       mynumbersLP="256782231039";
       }
       }else{
       
       mynumbersLP="256782231039";
       }
         
         }else{
          mynumbersLP="256"+messageParamenters.get(1).toString().substring(1, 10);
         }
          messageDetails.add(mynumbersLP);
          MessageLP="Dear"+"%20"+nameLP+"%2C"+"%0A"+"%20"+entityLP+"%20"+"has"+"%20"+"received"+"%20"+"the"+"%20"+"Loan"+"%20"+"instalment"+"%20"+"amount"+"%20"+"worth"+"%20"+"Ugx"+"%20"+NumberFormat.format(parseDouble(amountInvolved.replace(",", ""))).replace(",", "%2C")+"%20"+"on"+"%20"+"your"+"%20"+"loan"+"%20"+"account"+"%2E"+"%20"+"Thank"+"%20"+"you"+"%20"+"for"+"%20"+"honouring"+"%20"+"your"+"%20"+"loan"+"%20"+"obligations"+"%2E";
         messageDetails.add(this.decodeString(MessageLP));
      SendIdLP="sacco";
            
for(int x=0;x<5;x++){
    
    if(x==0){
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), mynumbersLP);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
     if(x==1){
      fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), MessageLP);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
      if(x==2){
         fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), SendIdLP);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
       if(x==3){
             fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), dataBaseId+"");
   
    }

}     
        
        
        
        break;
    case "SharesPayment":
        
         String mynumbersSh="",MessageSh="", SendIdSh="",entitySh="";
         
         String[] CompanyNameSh=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "shortCompanyName.txt")).split("[;]");
      
         if(CompanyNameSh.length==2){
         
         entitySh=CompanyNameSh[0]+"%20"+CompanyNameSh[1];
         
         }else if(CompanyNameSh.length>2){
             
           entitySh=CompanyNameSh[0]+"%20"+CompanyNameSh[1];
           
         }else if(CompanyNameSh.length==1){
             
         entitySh=CompanyNameSh[0];
         
         }
         String nameSh=messageParamenters.get(0).toString().split("\\s")[0].trim();
         if(messageParamenters.get(1).toString().isEmpty()){
       if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllNumbers.txt"))==7){
       if(!messageParamenters.get(2).toString().isEmpty()){
        mynumbersSh="256"+messageParamenters.get(2).toString().substring(1, 10);
       }else{
       
       mynumbersSh="256782231039";
       }
       }else{
       
       mynumbersSh="256782231039";
       }
         
         }else{
          mynumbersSh="256"+messageParamenters.get(1).toString().substring(1, 10);
         }
          messageDetails.add(mynumbersSh);
              MessageSh="Dear"+"%20"+nameSh+"%2C"+"%0A"+"%20"+entitySh+"%20"+"has"+"%20"+"received"+"%20"+"Shares"+"%20"+"Contributions"+"%20"+"worth"+"%20"+"Ugx"+"%20"+NumberFormat.format(parseDouble(amountInvolved)).replace(",", "%2C")+"%20"+"on"+"%20"+"your"+"%20"+"account"+"%2E"+"%20"+"%0A"+"Thank"+"%20"+"you"+"%20"+"for"+"%20"+"Shares"+"%20"+"contribution"+"%2E";
         messageDetails.add(this.decodeString(MessageSh));
      SendIdSh="sacco";
            
for(int x=0;x<5;x++){
    
    if(x==0){
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), mynumbersSh);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
     if(x==1){
      fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), MessageSh);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
      if(x==2){
         fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), SendIdSh);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
       if(x==3){
             fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), dataBaseId+"");
   
    }

}     
        
        
        break;
        
//                case "ReceiptEmphasis":
//       String mynumbersLDX="",MessageLDX="", SendIdLDX="",entityLDX="";
//         
//         String[] CompanyNameLDX=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "shortCompanyName.txt")).split("[;]");
//      
//         if(CompanyNameLDX.length==2){
//         
//         entityLDX=CompanyNameLDX[0]+"%20"+CompanyNameLDX[1];
//         
//         }else if(CompanyNameLDX.length>2){
//             
//           entityLDX=CompanyNameLDX[0]+"%20"+CompanyNameLDX[1];
//           
//         }else if(CompanyNameLDX.length==1){
//             
//         entityLDX=CompanyNameLDX[0];
//         
//         }
//         String nameLDX=messageParamenters.get(0).toString().split("\\s")[0].trim();
//         if(messageParamenters.get(1).toString().isEmpty()){
//       if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllNumbers.txt"))==7){
//       if(!messageParamenters.get(2).toString().isEmpty()){
//        mynumbersLDX="256"+messageParamenters.get(2).toString().substring(1, 10);
//       }else{
//       
//       mynumbersLDX="256782231039";
//       }
//       }else{
//       
//       mynumbersLDX="256782231039";
//       }
//         
//         }else{
//          mynumbersLDX="256"+messageParamenters.get(1).toString().substring(1, 10);
//         }
//          messageDetails.add(mynumbersLDX);
//          MessageLDX="Kasitoma Waffe "+", "+entityLDX+" ekutegeeza nti tewesiga muntu yena sasula zino sente, osayininge ofune ne lisiiti, Bwoba nga tosayininze oba nga tofunye lisiiti awo oba osasudde mpewo.Obuyambi kuba ku simu eno:0706036176/0784097939(Katale Emma).";
//       MessageLDX=smsForm.formSMSMessage(MessageLDX);
////   MessageSh="Dear"+"%20"+nameSh+"%2C"+"%0A"+"%20"+entitySh+"%20"+"has"+"%20"+"received"+"%20"+"Shares"+"%20"+"Contributions"+"%20"+"worth"+"%20"+"Ugx"+"%20"+NumberFormat.format(parseDouble(amountInvolved)).replace(",", "%2C")+"%20"+"on"+"%20"+"your"+"%20"+"account"+"%2E"+"%20"+"%0A"+"Thank"+"%20"+"you"+"%20"+"for"+"%20"+"Shares"+"%20"+"contribution"+"%2E";
//         messageDetails.add(this.decodeString(MessageLDX));
//      SendIdLDX="pinkApple";
//            
//for(int x=0;x<5;x++){
//    
//    if(x==0){
//    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), mynumbersLDX);
//    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
//    }
//     if(x==1){
//      fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), MessageLDX);
//    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
//    }
//      if(x==2){
//         fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), SendIdLDX);
//    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
//    }
//       if(x==3){
//             fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), dataBaseId+"");
//   
//    }
//
//}     
//        
//      
//        
//        
//        
//        break;
    case "LoanDisbursement":
       String mynumbersLD="",MessageLD="", SendIdLD="",entityLD="";
         
         String[] CompanyNameLD=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "shortCompanyName.txt")).split("[;]");
      
         if(CompanyNameLD.length==2){
         
         entityLD=CompanyNameLD[0]+"%20"+CompanyNameLD[1];
         
         }else if(CompanyNameLD.length>2){
             
           entityLD=CompanyNameLD[0]+"%20"+CompanyNameLD[1];
           
         }else if(CompanyNameLD.length==1){
             
         entityLD=CompanyNameLD[0];
         
         }
         String nameLD=messageParamenters.get(0).toString().split("\\s")[0].trim();
         if(messageParamenters.get(1).toString().isEmpty()){
       if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllNumbers.txt"))==7){
       if(!messageParamenters.get(2).toString().isEmpty()){
        mynumbersLD="256"+messageParamenters.get(2).toString().substring(1, 10);
       }else{
       
       mynumbersLD="256782231039";
       }
       }else{
       
       mynumbersLD="256782231039";
       }
         
         }else{
          mynumbersLD="256"+messageParamenters.get(1).toString().substring(1, 10);
//          JOptionPane.showMessageDialog(c, "mynumbersLD"+mynumbersLD);
         }
          messageDetails.add(mynumbersLD);
          MessageLD="Dear"+"%20"+nameLD+"%2C"+"%0A"+"%20"+entityLD+"%20"+"has"+"%20"+"disbursed"+"%20"+"the"+"%20"+"Loan"+"%20"+"amount"+"%20"+"worth"+"%20"+"Ugx"+"%20"+NumberFormat.format(parseDouble(amountInvolved)).replace(",", "%2C")+"%20"+"to"+"%20"+"your"+"%20"+"transaction"+"%20"+"account"+"%2E"+"%20"+"Kindly"+"%20"+"pick"+"%20"+"your"+"%20"+"cash"+"%20"+"or"+"%20"+"cheque"+"%2E";
         messageDetails.add(this.decodeString(MessageLD));
      SendIdLD="sacco";
            
for(int x=0;x<5;x++){
    
    if(x==0){
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), mynumbersLD);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
     if(x==1){
      fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), MessageLD);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
      if(x==2){
         fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), SendIdLD);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
       if(x==3){
             fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), dataBaseId+"");
   
    }

}     
        
      
        
        
        
        break;
    case "withdrawFromAccount":
        
       String mynumbersw="",Messagew="", SendIdw="",entityw="";
         
         String[] CompanyNamew=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "shortCompanyName.txt")).split("[;]");
      
         if(CompanyNamew.length==2){
         
         entityw=CompanyNamew[0]+"%20"+CompanyNamew[1];
         }else if(CompanyNamew.length>2){
           entityw=CompanyNamew[0]+"%20"+CompanyNamew[1];
         }else if(CompanyNamew.length==1){
         entityw=CompanyNamew[0];
         }
         String namew=messageParamenters.get(0).toString().split("\\s")[0].trim();
         if(messageParamenters.get(1).toString().isEmpty()){
       if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllNumbers.txt"))==7){
       if(!messageParamenters.get(2).toString().isEmpty()){
        mynumbersw="256"+messageParamenters.get(2).toString().substring(1, 10);
       }else{
       
       mynumbersw="256782231039";
       }
       }else{
       
       mynumbersw="256782231039";
       }
         
         }else{
          mynumbersw="256"+messageParamenters.get(1).toString().substring(1, 10);
         }
          messageDetails.add(mynumbersw);
       Messagew="Dear"+"%20"+namew+"%2C"+"%0A"+"There"+"%20"+"was"+"%20"+"a"+"%20"+"withdraw"+"%20"+"worth"+"%20"+"Ugx"+"%20"+NumberFormat.format(parseDouble(amountInvolved)).replace(",", "%2C")+"%20"+"on"+"%20"+"your"+"%20"+"account"+"%2E"+"%20"+"%0A"+"Please"+"%20"+"contact"+"%20"+entityw+"%20"+"for"+"%20"+"further"+"%20"+"information"+"%2E";
         messageDetails.add(this.decodeString(Messagew));
      SendIdw="sacco";
            
for(int x=0;x<5;x++){
    
    if(x==0){
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), mynumbersw);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
     if(x==1){
      fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), Messagew);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
      if(x==2){
         fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), SendIdw);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
       if(x==3){
             fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), dataBaseId+"");
   
    }

}
        
        
        break;
    case "LoanApplied":
       String mynumbersLA="",MessageLA="", SendIdLA="",entityLA="";
         
         String[] CompanyNameLA=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "shortCompanyName.txt")).split("[;]");
      
         if(CompanyNameLA.length==2){
         
         entityLA=CompanyNameLA[0]+"%20"+CompanyNameLA[1];
         
         }else if(CompanyNameLA.length>2){
             
           entityLA=CompanyNameLA[0]+"%20"+CompanyNameLA[1];
           
         }else if(CompanyNameLA.length==1){
             
         entityLA=CompanyNameLA[0];
         
         }
         String nameLA=messageParamenters.get(0).toString().split("\\s")[0].trim();
         if(messageParamenters.get(1).toString().isEmpty()){
       if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllNumbers.txt"))==7){
       if(!messageParamenters.get(2).toString().isEmpty()){
        mynumbersLA="256"+messageParamenters.get(2).toString().substring(1, 10);
       }else{
       
       mynumbersLA="256782231039";
       }
       }else{
       
       mynumbersLA="256782231039";
       }
         
         }else{
          mynumbersLA="256"+messageParamenters.get(1).toString().substring(1, 10);
         }
          messageDetails.add(mynumbersLA);
          MessageLA="Dear"+"%20"+nameLA+"%2C"+"%0A"+"Your"+"%20"+"loan"+"%20"+"request"+"%20"+"from"+"%20"+entityLA+"%20"+"worth"+"%20"+"Ugx"+"%20"+NumberFormat.format(parseDouble(amountInvolved)).replace(",", "%2C")+"%20"+"has"+"%20"+"been"+"%20"+"Applied"+"%20"+"and"+"%20"+"is"+"%20"+"waiting"+"%20"+"Approval"+"%2E"+"%20"+"Please"+"%20"+"contact"+"%20"+entityLA+"%20"+"for"+"%20"+"further"+"%20"+"information"+"%20"+"%2E";
         messageDetails.add(this.decodeString(MessageLA));
      SendIdLA="sacco";
            
for(int x=0;x<5;x++){
    
    if(x==0){
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), mynumbersLA);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
     if(x==1){
      fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), MessageLA);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
      if(x==2){
         fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), SendIdLA);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
       if(x==3){
             fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), dataBaseId+"");
   
    }

}     
      
        
        
        break;
        
    case "LoanApproved":
      String mynumbersLAp="",MessageLAp="", SendIdLAp="",entityLAp="";
         
         String[] CompanyNameLAp=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "shortCompanyName.txt")).split("[;]");
      
         if(CompanyNameLAp.length==2){
         
         entityLAp=CompanyNameLAp[0]+"%20"+CompanyNameLAp[1];
         
         }else if(CompanyNameLAp.length>2){
             
           entityLAp=CompanyNameLAp[0]+"%20"+CompanyNameLAp[1];
           
         }else if(CompanyNameLAp.length==1){
             
         entityLAp=CompanyNameLAp[0];
         
         }
         String nameLAp=messageParamenters.get(0).toString().split("\\s")[0].trim();
         if(messageParamenters.get(1).toString().isEmpty()){
       if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllNumbers.txt"))==7){
       if(!messageParamenters.get(2).toString().isEmpty()){
        mynumbersLAp="256"+messageParamenters.get(2).toString().substring(1, 10);
       }else{
       
       mynumbersLAp="256782231039";
       }
       }else{
       
       mynumbersLAp="256782231039";
       }
         
         }else{
          mynumbersLAp="256"+messageParamenters.get(1).toString().substring(1, 10);
         }
          messageDetails.add(mynumbersLAp);
          MessageLAp="Dear"+"%20"+nameLAp+"%2C"+"%0A"+"Your"+"%20"+"loan"+"%20"+"request"+"%20"+"from"+"%20"+entityLAp+"%20"+"worth"+"%20"+"Ugx"+"%20"+NumberFormat.format(parseDouble(amountInvolved)).replace(",", "%2C")+"%20"+"has"+"%20"+"been"+"%20"+"Approved"+"%20"+"and"+"%20"+"is"+"%20"+"waiting"+"%20"+"Disbursement"+"%2E"+"%20"+"Please"+"%20"+"contact"+"%20"+entityLAp+"%20"+"for"+"%20"+"further"+"%20"+"information"+"%20"+"%2E";
         messageDetails.add(this.decodeString(MessageLAp));
      SendIdLAp="sacco";
            
for(int x=0;x<5;x++){
    
    if(x==0){
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), mynumbersLAp);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
     if(x==1){
      fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), MessageLAp);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
      if(x==2){
         fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), SendIdLAp);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
       if(x==3){
             fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), dataBaseId+"");
   
    }

}     
  
        
        break; 
     case "LoanDeclined":
      String mynumbersLAd="",MessageLAd="", SendIdLAd="",entityLAd="";
         
         String[] CompanyNameLAd=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "shortCompanyName.txt")).split("[;]");
      
         if(CompanyNameLAd.length==2){
         
         entityLAd=CompanyNameLAd[0]+"%20"+CompanyNameLAd[1];
         
         }else if(CompanyNameLAd.length>2){
             
           entityLAd=CompanyNameLAd[0]+"%20"+CompanyNameLAd[1];
           
         }else if(CompanyNameLAd.length==1){
             
         entityLAd=CompanyNameLAd[0];
         
         }
         String nameLAd=messageParamenters.get(0).toString().split("\\s")[0].trim();
         if(messageParamenters.get(1).toString().isEmpty()){
       if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllNumbers.txt"))==7){
       if(!messageParamenters.get(2).toString().isEmpty()){
        mynumbersLAd="256"+messageParamenters.get(2).toString().substring(1, 10);
       }else{
       
       mynumbersLAd="256782231039";
       }
       }else{
       
       mynumbersLAd="256782231039";
       }
         
         }else{
          mynumbersLAd="256"+messageParamenters.get(1).toString().substring(1, 10);
         }
          messageDetails.add(mynumbersLAd);
          MessageLAd="Dear"+"%20"+nameLAd+"%2C"+"%0A"+"%20"+"Your"+"%20"+"loan"+"%20"+"request"+"%20"+"from"+"%20"+entityLAd+"%20"+"worth"+"%20"+"Ugx"+"%20"+NumberFormat.format(parseDouble(amountInvolved)).replace(",", "%2C")+"%20"+"has"+"%20"+"been"+"%20"+"Declined"+"%2E"+"%20"+"Please"+"%20"+"contact"+"%20"+entityLAd+"%20"+"for"+"%20"+"further"+"%20"+"information"+"%20"+"%2E";
         messageDetails.add(this.decodeString(MessageLAd));
      SendIdLAd="sacco";
            
for(int x=0;x<5;x++){
    
    if(x==0){
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), mynumbersLAd);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
     if(x==1){
      fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), MessageLAd);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
      if(x==2){
         fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), SendIdLAd);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
       if(x==3){
             fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), dataBaseId+"");
   
    }

}     
   
         
         
         break; 
 
     case "LoanReminderTomorrowPayment":
         
         
        String mynumbersLR="",MessageLR="", SendIdLR="",entityLR="";
         
         String[] CompanyNameLR=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "shortCompanyName.txt")).split("[;]");
      
         if(CompanyNameLR.length==2){
         
         entityLR=CompanyNameLR[0]+"%20"+CompanyNameLR[1];
         
         }else if(CompanyNameLR.length>2){
             
           entityLR=CompanyNameLR[0]+"%20"+CompanyNameLR[1];
           
         }else if(CompanyNameLR.length==1){
             
         entityLR=CompanyNameLR[0];
         
         }
         String nameLR=messageParamenters.get(0).toString().split("\\s")[0].trim();
         if(messageParamenters.get(1).toString().isEmpty()){
       if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllNumbers.txt"))==7){
       if(!messageParamenters.get(2).toString().isEmpty()){
        mynumbersLR="256"+messageParamenters.get(2).toString().substring(1, 10);
       }else{
       
       mynumbersLR="256782231039";
       }
       }else{
       
       mynumbersLR="256782231039";
       }
         
         }else{
          mynumbersLR="256"+messageParamenters.get(1).toString().substring(1, 10);
         }
          messageDetails.add(mynumbersLR);
//          +"worth"+"%20"+"Ugx"+"%20"+NumberFormat.format(parseDouble(amountInvolved)).replace(",", "%2C")+
          MessageLR="Dear"+"%20"+nameLR+"%2C"+"%0A"+"%20"+entityLR+"%20"+"reminds"+"%20"+"you"+"%20"+"that"+"%20"+"your"+"%20"+"loan"+"%20"+"installment"+"%20"+"is"+"%20"+"falling"+"%20"+"due"+"%20"+"tomorrow"+"%20"+fmt.dateConverterForNormalDate((System.currentTimeMillis()+(24*60*60*1000))).replace("/", "%2F")+"%2E"+"%20"+"Please"+"%20"+"prepare"+"%20"+"to"+"%20"+"clear"+"%20"+"it"+"%2E";
         messageDetails.add(this.decodeString(MessageLR));
      SendIdLR="sacco";
            
for(int x=0;x<5;x++){
    
    if(x==0){
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), mynumbersLR);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
     if(x==1){
      fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), MessageLR);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
      if(x==2){
         fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), SendIdLR);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
       if(x==3){
             fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), dataBaseId+"");
   
    }

}     
     
         
         
         
         
         
         break; 
         
     case "LoanReminderTodayPayment":

        String mynumbersLRT="",MessageLRT="", SendIdLRT="",entityLRT="";
         
         String[] CompanyNameLRT=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "shortCompanyName.txt")).split("[;]");
      
         if(CompanyNameLRT.length==2){
         
         entityLRT=CompanyNameLRT[0]+"%20"+CompanyNameLRT[1];
         
         }else if(CompanyNameLRT.length>2){
             
           entityLRT=CompanyNameLRT[0]+"%20"+CompanyNameLRT[1];
           
         }else if(CompanyNameLRT.length==1){
             
         entityLRT=CompanyNameLRT[0];
         
         }
         String nameLRT=messageParamenters.get(0).toString().split("\\s")[0].trim();;
         if(messageParamenters.get(1).toString().isEmpty()){
       if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllNumbers.txt"))==7){
       if(!messageParamenters.get(2).toString().isEmpty()){
        mynumbersLRT="256"+messageParamenters.get(2).toString().substring(1, 10);
       }else{
       
       mynumbersLRT="256782231039";
       }
       }else{
       
       mynumbersLRT="256782231039";
       }
         
         }else{
          mynumbersLRT="256"+messageParamenters.get(1).toString().substring(1, 10);
         }
          messageDetails.add(mynumbersLRT);
//          +"worth"+"%20"+"Ugx"+"%20"+NumberFormat.format(parseDouble(amountInvolved)).replace(",", "%2C")+"%20"
          MessageLRT="Dear"+"%20"+nameLRT+"%2C"+"%0A"+"%20"+entityLRT+"%20"+"reminds"+"%20"+"you"+"%20"+"that"+"%20"+"your"+"%20"+"loan"+"%20"+"installment"+"%20"+"has"+"%20"+"fallen"+"%20"+"due"+"%20"+"today"+"%20"+fmt.dateConverterForNormalDate((System.currentTimeMillis())).replace("/", "%2F")+"%2E"+"%20"+"Please"+"%20"+"prepare"+"%20"+"to"+"%20"+"clear"+"%20"+"it"+"%2E";
         messageDetails.add(this.decodeString(MessageLRT));
      SendIdLRT="sacco";
            
for(int x=0;x<5;x++){
    
    if(x==0){
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), mynumbersLRT);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
     if(x==1){
      fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), MessageLRT);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
      if(x==2){
         fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), SendIdLRT);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
       if(x==3){
             fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), dataBaseId+"");
   
    }

}     
     
         
   
         
         break;
     case "LoanReminderArrearsPayment":

         String days =new ReportsDatabase().actualAgingTime(accountNumber);
         
        String mynumbersLRTA="",MessageLRTA="", SendIdLRTA="",entityLRTA="";
         
         String[] CompanyNameLRTA=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "shortCompanyName.txt")).split("[;]");
      
         if(CompanyNameLRTA.length==2){
         
         entityLRTA=CompanyNameLRTA[0]+"%20"+CompanyNameLRTA[1];
         
         }else if(CompanyNameLRTA.length>2){
             
           entityLRTA=CompanyNameLRTA[0]+"%20"+CompanyNameLRTA[1];
           
         }else if(CompanyNameLRTA.length==1){
             
         entityLRTA=CompanyNameLRTA[0];
         
         }
         String nameLRTA=messageParamenters.get(0).toString().split("\\s")[0].trim();;
         if(messageParamenters.get(1).toString().isEmpty()){
       if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllNumbers.txt"))==7){
       if(!messageParamenters.get(2).toString().isEmpty()){
        mynumbersLRTA="256"+messageParamenters.get(2).toString().substring(1, 10);
       }else{
       
       mynumbersLRTA="256782231039";
       }
       }else{
       
       mynumbersLRTA="256782231039";
       }
         
         }else{
          mynumbersLRTA="256"+messageParamenters.get(1).toString().substring(1, 10);
         }
          messageDetails.add(mynumbersLRTA);
          MessageLRTA="Dear"+"%20"+nameLRTA+"%2C"+"%0A"+"%20"+entityLRTA+"%20"+"informs"+"%20"+"you"+"%20"+"of"+"%20"+"the"+"%20"+"loan"+"%20"+"installments"+"%20"+"in"+"%20"+"arrears"+"%20"+"for"+"%20"+"over"+"%20"+days+"%20"+"days"+"%2E"+"%20"+"Please"+"%20"+"arrange"+"%20"+"to"+"%20"+"have"+"%20"+"them"+"%20"+"cleared"+"%2E";
         messageDetails.add(this.decodeString(MessageLRTA));
      SendIdLRTA="sacco";
            
for(int x=0;x<5;x++){
    
    if(x==0){
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), mynumbersLRTA);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
     if(x==1){
      fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), MessageLRTA);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
      if(x==2){
         fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), SendIdLRTA);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
       if(x==3){
             fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), dataBaseId+"");
   
    }

}     
     
     
         
         
         break;
     case "accountCreatedMember":
        
         
        String mynumbersAC="",MessageAC="", SendIdAC="",entityAC="";
         
         String[] CompanyNameAC=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "shortCompanyName.txt")).split("[;]");
      
         if(CompanyNameAC.length==2){
         
         entityAC=CompanyNameAC[0]+"%20"+CompanyNameAC[1];
         
         }else if(CompanyNameAC.length>2){
             
           entityAC=CompanyNameAC[0]+"%20"+CompanyNameAC[1];
           
         }else if(CompanyNameAC.length==1){
             
         entityAC=CompanyNameAC[0];
         
         }
//         JOptionPane.showMessageDialog(c, messageParamenters.get(0).toString());
//         JOptionPane.showMessageDialog(c, messageParamenters.get(1).toString());
//         JOptionPane.showMessageDialog(c, messageParamenters.get(2).toString());
         String nameAC=messageParamenters.get(0).toString().split("\\s")[0].trim();
         if(messageParamenters.get(1).toString().isEmpty()){
       if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllNumbers.txt"))==7){
       if(!messageParamenters.get(1).toString().isEmpty()){
        mynumbersAC="256"+messageParamenters.get(1).toString().substring(1, 10);
       }else{
       
       mynumbersAC="256782231039";
       }
       }else{
       
       mynumbersAC="256782231039";
       }
         
         }else{
          mynumbersAC="256"+messageParamenters.get(1).toString().substring(1, 10);
         }
          messageDetails.add(mynumbersAC);
          String userName=dbq.AccountName(accountNumber).replace(" ", "%20");
          MessageAC="Dear"+"%20"+nameAC+"%2C"+"%0A"+"%20"+entityAC+"%20"+"has"+"%20"+"created"+"%20"+"your"+"%20"+"Member"+"%20"+"account"+"%20"+"AccountNumber"+"%3A"+accountNumber+"%20"+"AccountName"+"%3A"+userName+"%2E"+"%20"+"Contact"+"%20"+"management"+"%20"+"for"+"%20"+"more"+"%20"+"information"+"%2E";
         messageDetails.add(this.decodeString(MessageAC));
      SendIdAC="sacco";
            
for(int x=0;x<5;x++){
    
    if(x==0){
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), mynumbersAC);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
     if(x==1){
      fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), MessageAC);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
      if(x==2){
         fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), SendIdAC);
    fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), ":");
    }
       if(x==3){
             fios.stringFileWriterAppend(fios.createFileName("emailDetails", "sms", "message"+dataBaseId+".txt"), dataBaseId+"");
   
    }

}     

         
         break;
}
return messageDetails;
}



















public synchronized void reduceBalance(){

     int oldBalance=fios.intFileReader(fios.createFileName("persistence", "loanArrears", "loanAmountNotPaidS.txt"));     
     int newBalance=oldBalance-1;
     fios.intFileWriterReplace(fios.createFileName("persistence", "loanArrears", "loanAmountNotPaidS.txt"), newBalance+"");
}

   
public synchronized void reduceBalanceBulkBalance(int theAmount){

     int oldBalance=fios.intFileReader(fios.createFileName("persistence", "loanArrears", "loanAmountNotPaidS.txt"));     
     int newBalance=oldBalance-theAmount;
     fios.intFileWriterReplace(fios.createFileName("persistence", "loanArrears", "loanAmountNotPaidS.txt"), newBalance+"");
}

   


public String sendTheMessage(String message,String SenderId,String Numbers){
    
         
        HttpClientH smsHelp= new HttpClientH();
        
        

   return smsHelp.sendPostEntity2(this.createUrlSendSms(message, SenderId, Numbers));

    }
    

    
    public String getBalance(){
    
         
        HttpClientH smsHelp= new HttpClientH();
        
        

   return smsHelp.sendPostEntity2(this.createUrlGetBal());

    }
    


private String createUrlSendSms(String message,String SenderId,String Numbers){
//http://caltonmobile.com/calton/api.php?sender=test&contacts=256782231039&message=test&username=augbazi@gmail.com&password=xxxxx 
             StringBuilder sb= new StringBuilder();
    
    String url="http://caltonmobile.com/calton/api.php",userName="username=augbazi@gmail.com",passWord="password=PRINCE?;=2020",
            phoneNumbers="contacts="+Numbers,senderID="sender="+SenderId,messageSent="message="+message;
    
sb.append(url).append("?").append(senderID).append("&").append(phoneNumbers)
        .append("&").append(messageSent).append("&").append(userName)
        .append("&").append(passWord);
return sb.toString().replaceAll("\\s+"," ").trim();         
}


         
         
         
         
         
         
         
         
         
         













private String createUrlGetBal(){

  StringBuilder sb= new StringBuilder();
    
    String url="http://sales.rtl.ug:8866/cgi‐bin/requestbalance",userName="mocean-username=augustine2",passWord="mocean-password=password";
    
sb.append(url).append("?").append(userName).append("&").append(passWord);
return sb.toString().replaceAll("\\s+"," ").trim();      
}










public boolean smsBalanceEnough(){
    
    
boolean enough=false;

if(fios.intFileReader(fios.createFileName("persistence", "loanArrears", "loanAmountNotPaidS.txt"))>0){
enough=true;
}

return enough;
}






















































}
