/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.accountsHelper;

import googo.pmms.project2.databaseConnectors.JdbcConnector;
import googo.pmms.project2.databases.AccountNumberCreationDataBase;
import googo.pmms.project2.databases.BackUpRestoreDB;
import googo.pmms.project2.databases.DatabaseQuaries;
import googo.pmms.project2.databases.ReportsDatabase;
import googo.pmms.project2.databases.loanDatabaseQuaries;
import googo.pmms.project2.email.SmsUtility;
import googo.pmms.project2.email.TestConnectivity;
import googo.pmms.project2.frameHelper.ListDataModel;
import googo.pmms.project2.frameHelper.ListDataModel_11;
import googo.pmms.project2.frameHelper.listComboModel;
import googo.pmms.project2.frames.LoanManagmentWindow;
import googo.pmms.project2.frames.mainPrinter;
import googo.pmms.project2.loanHelper.Amortization;
import googo.pmms.project2.loanHelper.amortizeLoan;
import googo.pmms.project2.reportsHelper.BalanceSheet;
import googo.pmms.project2.reportsHelper.LoanSavingsSharesOthers;
import googo.pmms.project2.reportsHelper.OtherLoanReports;
import java.awt.Component;
import static java.lang.Double.parseDouble;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

/**
 *
 * @author STAT SOLUTIONS
 */
public class BatchPosting2 {
    
  
    String userId,closingNotes="1;General Comments;Not Specified:2;Payment promptness;Not Specified:3;Self Reminding;Not Specified:4;Top Ups;Not Specified:5;Others (Please Specify);Not Specified";
    
    List debitLP =null,creditLP=null;
    
JFrame reflection;
BiodataHelperRenderer theBio= new BiodataHelperRenderer();
    List amortDetails,accountDetails, customerDetails;
    SmsUtility sms= new SmsUtility();
    DecimalFormat NumberFormat =new DecimalFormat("#,###");
        SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
    fileInputOutPutStreams fios= new fileInputOutPutStreams();
    Formartter fmt = new Formartter();
    Date Trndate,valuedate;
   ArrayList<String> data4, column1;
List data5;
  List<List> uniqueListPost;
    String text,position;
 Integer Value,Value1;
    GregorianCalendar cal = new GregorianCalendar(); 
      ListDataModel_11 modelxxxxx;
     SendSms sendsms= new SendSms();
     int banks=0;
         BatchPostingHelper bPost=new BatchPostingHelper(); 
 Component c;
     JdbcConnector csx = new JdbcConnector(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "AccountDBDetails.txt"))); ; 
    DatabaseQuaries dbq =new DatabaseQuaries();
    loanDatabaseQuaries loan=new loanDatabaseQuaries();
    AccountNumberCreationDataBase ancdb=new AccountNumberCreationDataBase();
   ReportsDatabase rdb =new ReportsDatabase();
    BalanceSheet bsheet= new BalanceSheet();
    PostingMain post= new PostingMain(c);
    BackUpRestoreDB dbBackUp= new BackUpRestoreDB();
    CreatingFolders filesW= new CreatingFolders();
  String completeStatus="Not Complete",flag="Not Flagged"; int temper=0;
    String batchNumber,accountN;
    OtherLoanReports otherLoans=new  OtherLoanReports();
     LoanSavingsSharesOthers loanSaveShare=new LoanSavingsSharesOthers();
         ListDataModel model1;
         ListDataModel result;
         String theAccount="";
         
listComboModel modelComboBox;
     
    List loanPaymentOrder;

          JOptionPane p;
    PostingModal model ;
    JFrame fi, f,fa,fb,fc;
    Date date;
  SimpleDateFormat df;
TestConnectivity conn=new TestConnectivity();
 SendSms sendS=new SendSms();
  private java.util.Timer timer = null,timer1 = null;
  private java.util.TimerTask  myNewTask = null,myNewTask1 = null; 
  
     mainPrinter printReceipt=new mainPrinter();
  
    public BatchPosting2(Component c1,String userId){
        c=c1;
        this.userId=userId;
        }
        
  
  
  
    
public synchronized boolean fromMainPost(List theDetails,List LoanDetails,JTextField field,Component c){
        boolean postStatus=true;
// JOptionPane.showMessageDialog(c, userId);
String periodSubType = null, batch=null,DateFetched=null,amortizationDate=null,Narration=null,txnType=null,txnCode=null,officerInCharge=null,DrAccountNumber=null,CrAccountNumber=null,tnxId=null;

double amount = 0.0,tenure=0,interestRate=0.0,periodType=0,interestRegime=0;

List debit=null,credit=null;   
                 
List<List>  d=new ArrayList();  

List    amortDetailsx =null; List  amortDetailsx1=null;      


batch=theDetails.get(0).toString();

tnxId =theDetails.get(1).toString(); 

txnType=theDetails.get(2).toString();  

txnCode=theDetails.get(3).toString();

DrAccountNumber=theDetails.get(4).toString();

CrAccountNumber=theDetails.get(5).toString();

Narration=theDetails.get(6).toString();

amount=parseDouble(theDetails.get(7).toString());

interestRate=parseDouble(theDetails.get(8).toString());

tenure=parseDouble(theDetails.get(9).toString());

DateFetched=theDetails.get(10).toString();

periodType=parseDouble(theDetails.get(11).toString());

periodSubType=theDetails.get(12).toString();

officerInCharge=theDetails.get(13).toString();

interestRegime=parseDouble(theDetails.get(14).toString());


amortizationDate=theDetails.get(15).toString();


YearMonth thePostedDate=(YearMonth)theDetails.get(16);


       switch(txnType.trim()){

case "Withdrawal":
 
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 

    post.generalPosting(debit, credit);
}
   
    
    break;
    
    
    
    
case "Deposit":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 

    post.generalPosting(debit, credit);
    
    }

    break;
         
               
     
case "MobileMoneyWithdraw":
 
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 

    post.generalPosting(debit, credit);
}
   
    
    break;
    
    
    
    
case "MobileMoneyDeposit":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 

    post.generalPosting(debit, credit);
    
    }

    break;
    
    
case "HEFROI":
 
    if(amount>0.0){
        
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
    
    dbq.captureSavingsIndividual(credit,c);
    
    post.generalPosting(debit, credit);
    }
    break;
    
    
    case "MembershipFees":
 
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
    post.debitCashFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));
    post.generalPosting(debit, credit);
    }
    break;
    
    case "AnnualSubscriptionFees":
 
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
    post.debitCashFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));
    post.generalPosting(debit, credit);
    }
    break;
    
    case "FinesAndCharges":
 
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
    post.debitCashFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));
    post.generalPosting(debit, credit);
    }
    break;
    
    
     case "LedgerFees":
 
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
    post.debitCashFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));
    post.generalPosting(debit, credit);
    }
    break;
    case "AdminCosts":
 
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
    post.debitCashFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));
    post.generalPosting(debit, credit);
    }
    break;
    
    
    
     case "MembershipFeesSavings":
 
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//    post.debitCashFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));
 dbq.reduceSavings(debit);      
post.generalPosting(debit, credit);
    }
    break;
    
    
    case "FinesAndChargesSavings":
 
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//    post.debitCashFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));
 dbq.reduceSavings(debit);      
post.generalPosting(debit, credit);
    }
    break;
    
    case "LedgerFeesSavings":
 
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//    post.debitCashFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));
 dbq.reduceSavings(debit);      
post.generalPosting(debit, credit);
    }
    break;
    
    
    case "AdminCostsSavings":
 
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//    post.debitCashFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));
 dbq.reduceSavings(debit);      
post.generalPosting(debit, credit);
    }
    break;
    case "FinesAndChargesBank":
 
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
    post.debitBankFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())),tnxId);
    post.generalPosting(debit, credit);
    }
    break;
    
     case "AdminCostsBank":
 
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
    post.debitBankFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())),tnxId);
    post.generalPosting(debit, credit);
    }
    break;
    
    
    
    case "MembershipFeesBank":
 
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
    post.debitBankFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())),tnxId);
    post.generalPosting(debit, credit);
    }
    break;
    
    case "AnnualSubsFeesBank":
 
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
    post.debitBankFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())),tnxId);
    post.generalPosting(debit, credit);
    }
    break;
    
    
    case "LedgerFeesBank":
 
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
    post.debitBankFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())),tnxId);
    post.generalPosting(debit, credit);
    }
    break;
    
    
    case "LProcessFees":
 
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
    post.debitCashFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));
    post.generalPosting(debit, credit);
    }
    break;
    case "LInsuranceFees":
 
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
    post.debitCashFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));
    post.generalPosting(debit, credit);
    }
    break;
     case "LProcessFeesBank":
 
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
    post.debitBankFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())),tnxId);
    post.generalPosting(debit, credit);
    }
    break;
    
    case "LInsuranceFeesBank":
 
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
    post.debitBankFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())),tnxId);
    post.generalPosting(debit, credit);
    }
    break;
            case "SavingPenalties":
 
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//    post.debitCashFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));
 dbq.reduceSavings(debit);

post.generalPosting(debit, credit);
    }
    break;
    
            
            
    case "Payable":
 
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//    post.debitCashFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));
    post.generalPosting(debit, credit);
    }
    break;
    
    
    case "PayableBank":
 
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//    post.debitBankFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())),tnxId);
    post.generalPosting(debit, credit);
    }
    break;
    
    
    case "ClearReceivableBank":
 
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//    post.debitBankFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())),tnxId);
    post.generalPosting(debit, credit);
    }
    break;
    
    
    
    
   case "ReceivableBank":
 
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11,dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
    
    post.generalPosting(debit, credit);
//     post.creditBankFirst(CrAccountNumber, debit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())),tnxId);
    }   
   

    break;
       case  "Drawing":
  if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
    
    post.generalPosting(debit, credit);
    post.creditCashFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));
    }     
    break;     
            
      case  "DrawingBank":
  if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
    
    post.generalPosting(debit, credit);
   post.creditBankFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())),tnxId);
    }     
    break; 
                 
case "Receivable":
  if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
    
    post.generalPosting(debit, credit);
//    post.creditCashFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));
    }     
    break;
     



case "ClearPayable":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 

    post.generalPosting(debit, credit);
//    post.creditCashFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));
    }
    
    break;
    
    


case "Prepayments":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 

    post.generalPosting(debit, credit);
//    post.creditCashFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));
    }
    
    break;
    
    

case "PrepaymentsBank":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 

    post.generalPosting(debit, credit);
//    post.creditCashFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));
    }
    
    break;
    
case "ClearPayableBank":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 

    post.generalPosting(debit, credit);
//    post.creditBankFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())),tnxId);
    }
    
    break;
case "ClearReceivable":

if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//    post.debitCashFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));
    post.generalPosting(debit, credit);
    
    }   
break;
    case "General":
  if(amount>0.0){
   

    if(interestRate==3){
     debit=new ArrayList();   
//JOptionPane.showMessageDialog(c, "2");
    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "GenXX");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "GenXX");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
       post.generalPosting1xx(debit, credit,field);
    
    }else{
         debit=new ArrayList();   
//JOptionPane.showMessageDialog(c, "1");
    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
    post.generalPosting(debit, credit);
    }
    
    }
    break;
    

case "Salary":
   if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 

    post.salaryPosting(debit, credit);
//    post.creditCashFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+sdf.fmt(new Date(System.currentTimeMillis())));
    } 
    
    break;
case "Savings":
    
    if(amount>0.0){
        
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, "Regular Savings for "+Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Save2");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, "Regular Savings for "+Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Save2");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
      
     dbq.captureSavingsIndividual(credit,c);
    
    post.savingsPosting(debit, credit);
    
    
            if(dbq.getNumberFromTo()==333){
                
        try {
            
    wait(3000);
    
//    JOptionPane.showMessageDialog(c, batch);

         List printingDetails=new ArrayList();
         printingDetails.add("Savings Receipt"); // batchNumber
        printingDetails.add(CrAccountNumber); // batchNumber
        printingDetails.add(this.userId);// account Number
        printReceipt.printTheReceipt(printingDetails,c);
        
    } catch (InterruptedException ex) {
    Logger.getLogger(LoanManagmentWindow.class.getName()).log(Level.SEVERE, null, ex);
    }
        }else if(dbq.getNumberFromTo()==777){
        
//         jCheckBox39PostAnAdjustment1.setSelected(false);
        }
           
            
    
    
    }
    break;
     case "ROIPay":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Save2");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Save2");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
      
     dbq.captureSavingsIndividual(credit,c);
    
    post.savingsPosting(debit, credit);
    }
    break;
       
    
    case "SavingsTransfer":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Save2");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Save2");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
      
     dbq.reduceSavings(debit);
 dbq.captureSavingsIndividual(credit,c);
    
    post.savingsPosting(debit, credit);
    }
    break;
case "SavingsBank":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, "Regular Savings for "+Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Save2");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, "Regular Savings for "+Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Save2");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
      
     dbq.captureSavingsIndividual(credit,c);
    
    post.savingsPosting(debit, credit);
    
      if(dbq.getNumberFromTo()==333){
                
        try {
            
    wait(3000);
    
//    JOptionPane.showMessageDialog(c, batch);

         List printingDetails=new ArrayList();
         printingDetails.add("Savings Receipt"); // batchNumber
        printingDetails.add(CrAccountNumber); // batchNumber
        printingDetails.add(this.userId);// account Number
        printReceipt.printTheReceipt(printingDetails,c);
        
    } catch (InterruptedException ex) {
    Logger.getLogger(LoanManagmentWindow.class.getName()).log(Level.SEVERE, null, ex);
    }
        }else if(dbq.getNumberFromTo()==777){
        
//         jCheckBox39PostAnAdjustment1.setSelected(false);
        }
           
            
    }
    break;
case "WithdrawSavings":
//       JOptionPane.showMessageDialog(c, "ffff");
   if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "WdrawS2");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "WdrawS2");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
       
     Double runninBal=parseDouble(dbq.accountBalance(DrAccountNumber,debit.get(4).toString()));   
 
 if(runninBal<amount){

Object[] optionsx = {"Yes",  "No"};
    int nx = JOptionPane.showOptionDialog(c,  dbq.AccountName(DrAccountNumber)+"'s"+"  Account Balance is NOT enough!!!\n"+"Do you want to  continue ?",
    "CHECK ACCOUNT BALANCE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsx, optionsx[0]);
    if(nx==JOptionPane.YES_OPTION){
           dbq.reduceSavings(debit); 
//              JOptionPane.showMessageDialog(c, "ffff");
  post.cashWithdrawPosting(debit, credit);
                
    }
    else if (nx==JOptionPane.NO_OPTION){    
        
//       continue;
    
    
    }



}else{
      dbq.reduceSavings(debit);
post.cashWithdrawPosting(debit,credit);
                



}
   
   if(dbq.getNumberFromTo()==333){
                
        try {
            
    wait(3000);
    
//    JOptionPane.showMessageDialog(c, batch);

         List printingDetails=new ArrayList();
         printingDetails.add("Savings Withdraw Receipt"); // batchNumber
        printingDetails.add(DrAccountNumber); // batchNumber
        printingDetails.add(this.userId);// account Number
        printReceipt.printTheReceipt(printingDetails,c);
        
    } catch (InterruptedException ex) {
    Logger.getLogger(LoanManagmentWindow.class.getName()).log(Level.SEVERE, null, ex);
    }
        }else if(dbq.getNumberFromTo()==777){
        
//         jCheckBox39PostAnAdjustment1.setSelected(false);
        }
           
            
    }
   
   break;
  case "WithdrawSavingsBank":
   if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
       
     Double runninBal=parseDouble(dbq.accountBalance(DrAccountNumber,debit.get(4).toString()));   
 
 if(runninBal<amount){

Object[] optionsx = {"Yes",  "No"};
    int nx = JOptionPane.showOptionDialog(c,  dbq.AccountName(DrAccountNumber)+"'s"+"  Account Balance is NOT enough!!!\n"+"Do you want to  continue ?",
    "CHECK ACCOUNT BALANCE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsx, optionsx[0]);
    if(nx==JOptionPane.YES_OPTION){
           dbq.reduceSavings(debit); 
  post.cashWithdrawPosting(debit, credit);
                
    }
    else if (nx==JOptionPane.NO_OPTION){    
        
//       continue;
    
    
    }



}else{
      dbq.reduceSavings(debit);
post.cashWithdrawPosting(debit,credit);
                



}
    if(dbq.getNumberFromTo()==333){
                
        try {
            
    wait(3000);
    
//    JOptionPane.showMessageDialog(c, batch);

         List printingDetails=new ArrayList();
         printingDetails.add("Savings Withdraw Receipt"); // batchNumber
        printingDetails.add(DrAccountNumber); // batchNumber
        printingDetails.add(this.userId);// account Number
        printReceipt.printTheReceipt(printingDetails,c);
        
    } catch (InterruptedException ex) {
    Logger.getLogger(LoanManagmentWindow.class.getName()).log(Level.SEVERE, null, ex);
    }
        }else if(dbq.getNumberFromTo()==777){
        
//         jCheckBox39PostAnAdjustment1.setSelected(false);
        }
           
         
 
    }
   
   break;
  
    case "DepositPartPay":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Save");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Save");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
      
//     dbq.captureSavingsIndividual(credit);
    
    post.savingsPosting(debit, credit);
    }
    
    break;
 case "DepositPartPayBank":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Save");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Save");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
      
//     dbq.captureSavingsIndividual(credit);
    
    post.savingsPosting(debit, credit);
    }
    
    break;
case "WithdrawDepositPartPay":
   if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
       
     Double runninBal=parseDouble(dbq.accountBalance(DrAccountNumber,debit.get(4).toString()));   
 
 if(runninBal<amount){

Object[] optionsx = {"Yes",  "No"};
    int nx = JOptionPane.showOptionDialog(c,  dbq.AccountName(DrAccountNumber)+"'s"+"  Account Balance is NOT enough!!!\n"+"Do you want to  continue ?",
    "CHECK ACCOUNT BALANCE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsx, optionsx[0]);
    if(nx==JOptionPane.YES_OPTION){
//           dbq.reduceSavings(debit); 
  post.cashWithdrawPosting(debit, credit);
                
    }
    else if (nx==JOptionPane.NO_OPTION){    
        
//       continue;
    
    
    }



}else{
//      dbq.reduceSavings(debit);
post.cashWithdrawPosting(debit,credit);
                



}
   
 
    } 
    break;
    
    case "WithdrawDepositPartPayBank":
   if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
       
     Double runninBal=parseDouble(dbq.accountBalance(DrAccountNumber,debit.get(4).toString()));   
 
 if(runninBal<amount){

Object[] optionsx = {"Yes",  "No"};
    int nx = JOptionPane.showOptionDialog(c,  dbq.AccountName(DrAccountNumber)+"'s"+"  Account Balance is NOT enough!!!\n"+"Do you want to  continue ?",
    "CHECK ACCOUNT BALANCE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsx, optionsx[0]);
    if(nx==JOptionPane.YES_OPTION){
//           dbq.reduceSavings(debit); 
  post.cashWithdrawPosting(debit, credit);
                
    }
    else if (nx==JOptionPane.NO_OPTION){    
        
//       continue;
    
    
    }



}else{
//      dbq.reduceSavings(debit);
post.cashWithdrawPosting(debit,credit);
                



}
   
 
    } 
    break;
    
case "Capitalisation":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "CapR");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "CapR");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
fios.intFileWriterReplace(fios.createFileName("sharesAvailable","shareValues", "usedWindow"+DrAccountNumber+".txt"), "365");
    post.regularShareContribution(debit, credit);
    }
    break;
    
   case "CapitalisationSavings":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "CapRD");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "CapRD");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
fios.intFileWriterReplace(fios.createFileName("sharesAvailable","shareValues", "usedWindow"+DrAccountNumber+".txt"), "365");
    post.regularShareContribution(debit, credit);
    }
    break; 
    
    
    case "CapitalisationBank":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "CapR");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "CapR");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
fios.intFileWriterReplace(fios.createFileName("sharesAvailable","shareValues", "usedWindow"+DrAccountNumber+".txt"), "365");
    post.regularShareContributionBank(debit, credit,tnxId);
    }
    
    break;
    
case "Decaptalisation":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "DCap");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "DCap");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 

     fios.intFileWriterReplace(fios.createFileName("sharesAvailable","shareValues", "usedWindow"+CrAccountNumber+".txt"),"365");
     
    post.decapitalise(debit, credit);
    
    }
    break;
    case "DecaptalisationSavings":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "DCapD");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "DCapD");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 

     fios.intFileWriterReplace(fios.createFileName("sharesAvailable","shareValues", "usedWindow"+CrAccountNumber+".txt"),"365");
     
    post.decapitalise(debit, credit);
    
    }
    break;
    
    case "CapitalisationBank1":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "CapR");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "CapR");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
fios.intFileWriterReplace(fios.createFileName("sharesAvailable","shareValues", "usedWindow"+DrAccountNumber+".txt"), "365");
    post.regularShareContributionBank(debit, credit,tnxId);
    }
    
    break;
    
    
         
   

case "WriteOffInterest":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 

    
    post.generalPosting(debit, credit);
 post.creditCashFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));    
    }
    
    break;
    case "WriteOffProcessingFees":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 

    
    post.generalPosting(debit, credit);
 post.creditCashFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));    
    }
    
    break;
         
    case "WriteOffInterestDepositPartPay":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 

    
    post.generalPosting(debit, credit);
// post.creditCashFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));    
    }
    
    break;
    
     case "WriteOffAccumulatedInterestDepositPartPay":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 

    
    post.generalPosting(debit, credit);
// post.creditCashFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));    
    }
    
    break;
    
    
    case "WriteOffAccumulatedInterestSavings":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 

     dbq.captureSavingsIndividual(credit,c);
    post.generalPosting(debit, credit);
// post.creditCashFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));    
    }
    
    break;
    
     case "WriteOffInterestSavings":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 

 dbq.captureSavingsIndividual(credit,c);
    post.generalPosting(debit, credit);
// post.creditCashFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));    
    }
    
    break;
      case "WriteOffPenaltySavings":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 

 dbq.captureSavingsIndividual(credit,c);
    post.generalPosting(debit, credit);
// post.creditCashFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));    
    }
    
    break;
    
    
    
    case "WriteOffInterestBank":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 

    
    post.generalPosting(debit, credit);
  post.creditBankFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())),tnxId);  
    }
    
    break;
    
    
    case "WriteOffProcessingFeesBank":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 

    
    post.generalPosting(debit, credit);
  post.creditBankFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())),tnxId);  
    }
    
    break;
  
    
    
case "WriteOffPenalty":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 

    post.generalPosting(debit, credit);
     post.creditCashFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));   
    }
    
    
    break;
    
    
    case "WriteOffPenaltyBank":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11,dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 

    post.generalPosting(debit, credit);
    post.creditBankFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())),tnxId);    
    }
    
    
    break;
    

    
    
    
case "WriteOffAccumulatedInterest":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 

    post.generalPosting(debit, credit);
         post.creditCashFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));  
    }
break;
    case "WriteOffAccumulatedInterestBank":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 

    post.generalPosting(debit, credit);
      post.creditBankFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())),tnxId); 
    }
    break;
    
case "BadLoansRecovered":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
post.debitCashFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis()))); 
    post.generalPosting(debit, credit);
    }
    
    break;
    
   case "BadLoansRecoveredBank":
    if(amount>0.0){
    debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

    credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
post.debitBankFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())),tnxId);  
    post.generalPosting(debit, credit);
    }
    
    break; 
    
    }  
       
       return postStatus;
}     
 
  
//
//public void f1(){
//
//switch(txnType.trim()){
//   
//case "Expense":
//    int theIndex=theCorrespondingTxnCode(txnCode.trim());
//    
//    if(theIndex!=999){
//    List theDatails=uniqueListPost.get(theIndex);
//
//    if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, theDatails.get(2).toString());//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(2).toString())));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, theDatails.get(3).toString());//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(3).toString())));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, this.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  theDatails.get(3).toString());//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
//    credit .add(3, "On"+" "+dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(3).toString())));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, theDatails.get(2).toString());//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(2).toString())));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11, this.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//
//    post.generalPosting(debit, credit);
//}
//    }else{
// JOptionPane.showMessageDialog(this, "The Expense Txn Narration:"+Narration+" "+"Amount:"+amount+" "+"Dated:"+DateFetched+" "+"has no corresponding txn Code defined\n\nPlease click ok to skip this txn");
//    break;
//    
//    }
//    
//    break;
//case "Loan":
//  
//    amortDetailsx = new ArrayList();
//    
//    amortDetailsx1 = new ArrayList(); 
//  
//  amortizeLoan amorty =new amortizeLoan(userId);  
//  
//    int theIndexN=theCorrespondingTxnCode(txnCode.trim());
//    if(theIndexN!=999){
//    List theDatails=uniqueListPost.get(theIndexN);
//
//    if(amount>0.0){
//        
//         fios.deleteFileExistance(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"));   
//        
//          SwingWorker<Void,Void>createNewLoan=new SwingWorker(){
//  
//
//            @Override
//            protected Object doInBackground() throws Exception {
//                
//                loan.createNewLoanAmort(fmt.formatAccountWithSeperators(theDatails.get(3).toString()));
//                
//                return null;
//            }
//        };
//        createNewLoan.execute();
//        
//        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"),fmt.formatAccountWithSeperators(theDatails.get(3).toString()));
//        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");
//        
//        
//        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(3).toString())));
//        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");   
//        
//      
//   fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), tenure+"");
//        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");
//
//          fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), "MONTHS");
//        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");  
//        
//         amortDetailsx.add(amount);//PRINCIPAL :0
//   fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), amount+"");
//        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");
//
//          fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), "Mothly Income");
//        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");
//        
//        
//          fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), "BUSINESS PURPOSE");
//        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");
//
//        amortDetailsx.add(interestRate);//INTEREST RATE:1
//
//        amortDetailsx.add(tenure);//LOAN TENURE
//
//        amortDetailsx.add(DateFetched);//START DATE
//
//        amortDetailsx.add(periodType);//TENURE TYPE
//
//        String accountName=dbq.getUserName(officerInCharge);
//        
//        amortDetailsx.add(accountName+" "+officerInCharge);
//
//        amortDetailsx.add(interestRegime);//INTEREST REGIME
//   
//        
////       JOptionPane.showMessageDialog(this, amortDetailsx.get(0).toString()+";"+amortDetailsx.get(1).toString()+";"+amortDetailsx.get(2).toString()+";"+amortDetailsx.get(3).toString()+";"+amortDetailsx.get(4).toString()+";"+amortDetailsx.get(5).toString());  
//        
//        
////JOptionPane.showConfirmDialog(this, loandetails.size()+"");
//            amorty.createTheLoan(amorty.amortizeme(amortDetailsx), amortDetailsx);
//            
//  loan.updateSubmited(dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(3).toString())),"Approved"); 
//       
//    fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(3).toString()))+ ".txt"), "4");
//        
//   fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "activeTrnId"+userId+".txt"), loan.getActiveId(fmt.formatAccountWithSeperators(theDatails.get(3).toString())));      
//        
//               String trnId=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeTrnId"+userId+".txt"));
//              List loandetails = loan.getLoanDetails(trnId); 
//  
//    if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "LoanApproved.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllLoanTransactions.txt"))==7){
//    
//        
//         SwingWorker<Void,Void>updatesendSms=new SwingWorker(){
//         @Override
//         protected Object doInBackground() throws Exception {
//        sendsms.createLoanApprovedSMS(dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(3).toString())), loandetails.get(4).toString());
//             return null; } };
//     updatesendSms.execute();     
//}
//
//  
//      Integer zxso=4;
//    fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+fmt.formatAccountWithSeperators(theDatails.get(3).toString())+".txt"),zxso.toString());   
// 
//  if(  fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+fmt.formatAccountWithSeperators(theDatails.get(3).toString())+".txt"))==4){
//     SwingWorker<Void,Void>updateLoanStore=new SwingWorker(){
//         @Override
//         protected Object doInBackground() throws Exception {
//          loan.updateLoanStoreAll(userId+"", fmt.formatAccountWithSeperators(theDatails.get(3).toString()),"Approved","newloan"+fmt.formatAccountWithSeperators(theDatails.get(3).toString()),trnId);   
//             
//             return null;  } };
//     updateLoanStore.execute();
//     }   
//  
//        List loanDetails= new ArrayList();
//       loanDetails.add(loandetails.get(4).toString());//Princimpal amount
//         loanDetails.add(loandetails.get(3).toString());//interest amount
//  loanDetails.add(loandetails.get(6).toString());//instalment start date
//  loanDetails.add(loandetails.get(7).toString());//instalment end date
//    loanDetails.add(loandetails.get(1).toString());//Loan Tenure
//   loanDetails.add("Approved");//loan cycle status
//   
//    loanDetails.add(dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(3).toString())));//Account Name
//    
//    loanDetails.add(fmt.formatAccountWithSeperators(theDatails.get(3).toString()));//Account Number
//    
//    
//      SwingWorker<Void,Void>updateReportATable=new SwingWorker(){
//         @Override
//         protected Object doInBackground() throws Exception {
//           loan.updateLoanReportATable(loanDetails); 
//             
//             return null;
//         }
//     
//     };
//     updateReportATable.execute();   
//        
//       
//        
// List   debitn=new ArrayList();   
//
//    debitn .add(0, theDatails.get(2).toString());//Debit Account Number:0
//    debitn .add(1, DateFetched);//Transaction Date:1
//    debitn .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
//    debitn .add(3, "From"+" "+dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(2).toString())));//Narrative 2:3
//    debitn .add(4, DateFetched);//Value Date:4
//    debitn .add(5, amount);//Debit Amount:5
//    debitn .add(6, theDatails.get(3).toString());//Credit Account Number:6  
//    debitn .add(7, dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(3).toString())));//Credit Account Name:7 
//    debitn .add(8, "0002");//Debit Reference Number:8
//    debitn .add(9, batch);//Batch Number:9
//    debitn .add(10, "Gen");//Transaction Type:10
//    debitn .add(11, this.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//   List creditn=new ArrayList();    
//
//    creditn .add(0,  theDatails.get(3).toString());//Debit Account Number:0
//    creditn .add(1, DateFetched);//Transaction Date:1
//    creditn .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
//    creditn .add(3, "On"+" "+dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(3).toString())));//Narrative 2:3
//    creditn .add(4, DateFetched);//Value Date:4
//    creditn .add(5, amount);//Debit Amount:5
//    creditn .add(6, theDatails.get(2).toString());//Credit Account Number:6  
//    creditn .add(7, dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(2).toString())));//Credit Account Name:7 
//    creditn .add(8, "0002");//Debit Reference Number:8
//    creditn .add(9, batch);//Batch Number:9
//    creditn .add(10, "Gen");//Transaction Type:10
//    creditn .add(11, this.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//
//      SwingWorker<Void,Void>genLoan=new SwingWorker(){
//            @Override
//            protected Object doInBackground() throws Exception {
//            post.loanDisbursement(debitn, creditn);
//                return null;
//            } };
//      genLoan.execute();  
//
// String loandetailsNew =fios.stringFileReader(fios.createFileName("loanApplication", "amortValues","loanStore"+fmt.formatAccountWithSeperators(theDatails.get(3).toString())+".txt"));       
//     
// List loanDetailsNew= new ArrayList();
// 
//       loanDetailsNew.add(loandetailsNew.split("[,]", 16)[1]);//Princimpal amount
//         loanDetailsNew.add(loandetailsNew.split("[,]", 16)[2]);//interest amount
//  loanDetailsNew.add(loandetailsNew.split("[,]", 16)[4]);//instalment start date
//  loanDetailsNew.add(loandetailsNew.split("[,]", 16)[5]);//instalment start date
//    loanDetailsNew.add(loandetailsNew.split("[,]", 16)[0]);//instalment start date
//   loanDetailsNew.add("Disbursed");//loan cycle status
//    loanDetailsNew.add(dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(3).toString())));//loan cycle status
//    loanDetailsNew.add(fmt.formatAccountWithSeperators(theDatails.get(3).toString()));//loan cycle status
//     SwingWorker<Void,Void>updateLoanReportATable=new SwingWorker(){
//            @Override
//            protected Object doInBackground() throws Exception {
//             loan.updateLoanReportATable(loanDetailsNew); 
//                return null;
//            } };
//      updateLoanReportATable.execute(); 
//   
//       
//
//        
//
//        Integer xxy=5;
//
//        fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+fmt.formatAccountWithSeperators(theDatails.get(3).toString())+ ".txt"), xxy.toString());
//       
//        fios.forceFileExistanceAppend(fios.createFileName("loanApplication", "amortValues", "fromLoanManagementTable.txt"));
//        Integer xl=0;
//        fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "fromLoanManagementTable.txt"),xl.toString());
//
//        loan.updateLoanStoreAll(userId, fmt.formatAccountWithSeperators(theDatails.get(3).toString()),"Disbursed","newloan"+fmt.formatAccountWithSeperators(theDatails.get(3).toString()),fios.stringFileReader(fios.createFileName("loanApplication", "amortValues","loanStore"+fmt.formatAccountWithSeperators(theDatails.get(3).toString())+".txt")).split("[,]", 16)[14].replaceAll("[,]", " "));
//        
//}
//    
//      SwingWorker<Void,Void>submittedSecurity=new SwingWorker(){
//       @Override
//       protected Object doInBackground() throws Exception {
//      loan. processSecurity(fmt.formatAccountWithSeperators(theDatails.get(3).toString()),"1;Land;Not Specified");
//      return null;
//       }
//};
// submittedSecurity.execute();
//   
//    }else{
// JOptionPane.showMessageDialog(this, "The Expense Txn Narration:"+Narration+" "+"Amount:"+amount+" "+"Dated:"+DateFetched+" "+"has no corresponding txn Code defined\n\nPlease click ok to skip this txn");
//    break;
//    
//    } 
//    
//    
//    break;
//case "LoanP":
////    JOptionPane.showMessageDialog(this, txnCode);
//
//    int theIndexLP=theCorrespondingTxnCode(txnCode);
//    if(theIndexLP!=999){
//    List theDatails=uniqueListPost.get(theIndexLP);
//
//    if(amount>0.0){
//        
//    if((loan.loanExists(fmt.formatAccountWithSeperators(theDatails.get(2).toString())))){
//               
//            loanPaymentOrder=new ArrayList(); 
//           
//            int size=txnCode.length();
//            if(size==4){
//            txnCode=txnCode.substring(0, 1);
//            }else{
//            
//            txnCode=txnCode.substring(0, 3);
//            
//            }
//            
////       JOptionPane.showMessageDialog(this, txnCode);     
//            switch(txnCode){
//                case "I":
//                     if(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "CompanyType.txt")).equalsIgnoreCase("Money Lender")){
////                  JOptionPane.showMessageDialog(this, "in");  
//                     if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useAccumulatedInterest.txt"))==1){    
//                         if(loan.accumulatedLessInterest(fmt.formatAccountWithSeperators(theDatails.get(2).toString()),amount)){
//                           
//                 loan.createAccumulatedInterest(fmt.formatAccountWithSeperators(theDatails.get(2).toString()),amount);
//                     }
//                     }
//        }   
////             JOptionPane.showMessageDialog(this, amount);        
//        loanPaymentOrder.add(0,"Accumulated Interest");          
//        loanPaymentOrder.add(1,"Interest");
//        loanPaymentOrder.add(2,"Loan Penalty");
//        loanPaymentOrder.add(3,"Principal");           
//
//        break;
//        case "P&I":
//            double remainPI= loan.piRemain(fmt.formatAccountWithSeperators(theDatails.get(2).toString()));
//            
//            if(amount>remainPI){
//         double diff=   amount-remainPI;
//             if(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "CompanyType.txt")).equalsIgnoreCase("Money Lender")){
////                  JOptionPane.showMessageDialog(this, "in");  
//                     if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useAccumulatedInterest.txt"))==1){    
//                         if(loan.accumulatedLessInterest(fmt.formatAccountWithSeperators(theDatails.get(2).toString()),diff)){
//                           
//                 loan.createAccumulatedInterest(fmt.formatAccountWithSeperators(theDatails.get(2).toString()),diff);
//                     }
//                     }
//        }   
//            }
//        loanPaymentOrder.add(0,"Interest");
//        loanPaymentOrder.add(1,"Principal");
//        loanPaymentOrder.add(2,"Accumulated Interest");          
//        loanPaymentOrder.add(3,"Loan Penalty");
//
//        break;
//        case "P":
//        loanPaymentOrder.add(0,"Principal");            
//        loanPaymentOrder.add(1,"Interest");
//        loanPaymentOrder.add(2,"Accumulated Interest");          
//        loanPaymentOrder.add(3,"Loan Penalty");  
//                     
//                     break;
//          
//                    
//            }
//      double remainingInstalments=parseDouble(loan.remainingTotalLoanBalance(fmt.formatAccountWithSeperators(theDatails.get(2).toString())));
//   
//
//              if(amount>=remainingInstalments){
//
//               
//                  if(amount>remainingInstalments){
//
//        amount=remainingInstalments;
//
//                  }
//            completeStatus="Complete";
//             
//                  } 
//              
//              
//       
//   debitLP=new ArrayList();   
////            debit .add(0, this.getDrAccountNumber());//Debit Account Number:0
////            debit .add(1, this.getTransactionDate());//Transaction Date:1
////            debit .add(2, this.getDrNarrative1());//Narrative 1:2
////            debit .add(3, this.getDrNarrative2());//Narrative 2:3
////            debit .add(4, this.getValueDate());//Value Date:4
////            debit .add(5, this.getDrAmount());//Debit Amount:5
////            debit .add(6, this.getCrAccountNumber());//Credit Account Number:6
////            debit .add(7, this.getCrAccountName());//Credit Account Name:7
////            debit .add(8, this.getDrReferenceNumber());//Debit Reference Number:8
////            debit .add(9, this.getBatchNumber());//Batch Number:9
////            debit .add(10, this.getTransactionType());//Transaction Type:10
////            debit .add(11, this.getTransactionSequenceNumber());//Transaction Sequence Number:11
//        
//          debitLP .add(0, theDatails.get(2).toString());//Debit Account Number:0
//           debitLP .add(1, DateFetched);//Transaction Date:1
//           debitLP .add(2, dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(2).toString()))+"'s LOAN PAYMENT "+"DATED"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
//           debitLP .add(3, "LOAN PAYMENT");//Narrative 2:3
//           debitLP .add(4, DateFetched);//Value Date:4
//          debitLP .add(5, amount);//Debit Amount:5
//         debitLP .add(6, theDatails.get(3).toString());//Credit Account Number:6  
//           debitLP .add(7, dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(3).toString())));//Credit Account Name:7 
//            debitLP .add(8, "0002");//Debit Reference Number:8
//            debitLP .add(9, batch);//Batch Number:9
//           debitLP .add(10, "LoanR");//Transaction Type:10
//            debitLP .add(11, this.getTransactionSequenceNumber());//Transaction Sequence Number:11
//           
//             creditLP=new ArrayList();    
//            
//            creditLP .add(0, theDatails.get(3).toString());//Debit Account Number:0
//           creditLP .add(1, DateFetched);//Transaction Date:1
//           creditLP .add(2, dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(2).toString()))+"'S LOAN PAYMENT"+"DATED"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
//           creditLP .add(3, "LOAN PAYMENT");//Narrative 2:3
//           creditLP .add(4, DateFetched);//Value Date:4
//          creditLP .add(5, amount);//Debit Amount:5
//         creditLP .add(6,  theDatails.get(2).toString());//Credit Account Number:6  
//           creditLP .add(7, dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(2).toString())));//Credit Account Name:7 
//            creditLP .add(8, "0002");//Debit Reference Number:8
//            creditLP .add(9, batch);//Batch Number:9
//           creditLP .add(10, "LoanR");//Transaction Type:10
//            creditLP .add(11, this.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//            
//// if(post.accountsAreFineLR(debit, credit)){
//
//   
////         SwingWorker<Void,Void> postTheEntry;
////            postTheEntry = new SwingWorker() {
////                @Override
////                protected Object doInBackground() throws Exception {
//                    
//                     post.loanRepaymentsNew(debitLP, creditLP,loanPaymentOrder,completeStatus);
//                    
////                    return null;
////                }};
////
////        postTheEntry.execute();
//
//    }else{
//           
//         JOptionPane.showMessageDialog(this, dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(2).toString()))+"'s"+" "+"Loan does not exist Please click ok to continue!!");              
//              break;
//           }
//    
//    
//    
//    
//    
//    
//    }
//  
//    
//    
//    
//    
//     completeStatus="Not Complete";   
//    
//    
//    }else{
//        
// JOptionPane.showMessageDialog(this, "The Loan Payment Txn Narration: "+Narration+" "+"Amount: "+amount+" "+"Dated: "+DateFetched+" "+"has no corresponding txn Code defined\n\nPlease click ok to skip this txn");
//    break;
//    
//    }
//
//    
//  
//    
//    
//    
//    
//    break;
//case "Withdrawal":
//  int theIndexW=theCorrespondingTxnCode(txnCode);
//    if(theIndexW!=999){
//    List theDatails=uniqueListPost.get(theIndexW);
//
//    if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, theDatails.get(2).toString());//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(2).toString())));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, theDatails.get(3).toString());//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(3).toString())));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, this.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  theDatails.get(3).toString());//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
//    credit .add(3, "On"+" "+dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(3).toString())));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, theDatails.get(2).toString());//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(2).toString())));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11, this.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//
//    post.generalPosting(debit, credit);
//}
//    }else{
// JOptionPane.showMessageDialog(this, "The Cash Withdraw Txn Narration:"+Narration+" "+"Amount:"+amount+" "+"Dated:"+DateFetched+" "+"has no corresponding txn Code defined\n\nPlease click ok to skip this txn");
//    break;
//    
//    }
//    
//    
//    break;
//case "Deposit":
//   int theIndexD=theCorrespondingTxnCode(txnCode);
//    if(theIndexD!=999){
//    List theDatails=uniqueListPost.get(theIndexD);
//
//    if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, theDatails.get(2).toString());//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(2).toString())));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, theDatails.get(3).toString());//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(3).toString())));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, this.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  theDatails.get(3).toString());//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
//    credit .add(3, "On"+" "+dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(3).toString())));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, theDatails.get(2).toString());//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(2).toString())));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11, this.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//
//    post.generalPosting(debit, credit);
//}
//    }else{
// JOptionPane.showMessageDialog(this, "The Cash Deposit Txn Narration:"+Narration+" "+"Amount:"+amount+" "+"Dated:"+DateFetched+" "+"has no corresponding txn Code defined\n\nPlease click ok to skip this txn");
//    break;
//    
//    }
//
//    break;
//case "Payable":
// 
//    int theIndexP=theCorrespondingTxnCode(txnCode);
//    if(theIndexP!=999){
//    List theDatails=uniqueListPost.get(theIndexP);
//
//    if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, theDatails.get(2).toString());//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(2).toString())));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, theDatails.get(3).toString());//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(3).toString())));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, this.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  theDatails.get(3).toString());//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
//    credit .add(3, "On"+" "+dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(3).toString())));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, theDatails.get(2).toString());//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(2).toString())));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11, this.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//
//    post.generalPosting(debit, credit);
//}
//    }else{
// JOptionPane.showMessageDialog(this, "The Accounts Payable Txn Narration:"+Narration+" "+"Amount:"+amount+" "+"Dated:"+DateFetched+" "+"has no corresponding txn Code defined\n\nPlease click ok to skip this txn");
//    break;
//    
//    }   
//    
//    
//    break;
//    
//    
//    
//    
//    
//case "Receivable":
//  int theIndexR=theCorrespondingTxnCode(txnCode);
//    if(theIndexR!=999){
//    List theDatails=uniqueListPost.get(theIndexR);
//
//    if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, theDatails.get(2).toString());//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(2).toString())));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, theDatails.get(3).toString());//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(3).toString())));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, this.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  theDatails.get(3).toString());//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
//    credit .add(3, "On"+" "+dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(3).toString())));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, theDatails.get(2).toString());//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(2).toString())));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11, this.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//
//    post.generalPosting(debit, credit);
//}
//    }else{
// JOptionPane.showMessageDialog(this, "The Accounts Receivable Txn Narration:"+Narration+" "+"Amount:"+amount+" "+"Dated:"+DateFetched+" "+"has no corresponding txn Code defined\n\nPlease click ok to skip this txn");
//    break;
//    
//    }      
//    break;
//    case "General":
//  int theIndexG=theCorrespondingTxnCode(txnCode);
//    if(theIndexG!=999){
//    List theDatails=uniqueListPost.get(theIndexG);
//
//    if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, theDatails.get(2).toString());//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(2).toString())));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, theDatails.get(3).toString());//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(3).toString())));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, this.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  theDatails.get(3).toString());//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
//    credit .add(3, "On"+" "+dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(3).toString())));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, theDatails.get(2).toString());//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(fmt.formatAccountWithSeperators(theDatails.get(2).toString())));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11, this.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//
//    post.generalPosting(debit, credit);
//}
//    }else{
// JOptionPane.showMessageDialog(this, "The General Txn Narration:"+Narration+" "+"Amount:"+amount+" "+"Dated:"+DateFetched+" "+"has no corresponding txn Code defined\n\nPlease click ok to skip this txn");
//    break;
//    
//    } 
//    
//    break;
//
//   }
//
//
//
//}

//
//public void bulkPostingPost(List thePostedDetails,List LoanDetails){
//    
//    String batch=null,DateFetched=null,amortizationDate=null,Narration=null,txnType=null,txnCode=null,officerInCharge=null,DrAccountNumber=null,CrAccountNumber=null;
//
//double amount = 0.0,debitAmnt= 0.0,creditAmnt= 0.0,tenure=0,interestRate=0.0,periodType=0,interestRegime=0,trid=0;
//
//List debit=null,credit=null;   
//                 
//List<List>  d=new ArrayList();  
//
//
//List    amortDetailsx =null; List  amortDetailsx1=null;
//
//
//batch=thePostedDetails.get(0).toString();
//trid=parseDouble(thePostedDetails.get(2).toString());
//DateFetched=thePostedDetails.get(3).toString();
//Narration=thePostedDetails.get(4).toString();
//txnType=thePostedDetails.get(5).toString();
//txnCode=thePostedDetails.get(6).toString();
//amount=parseDouble(thePostedDetails.get(6).toString());
//interestRate=parseDouble(thePostedDetails.get(7).toString());
//tenure=parseDouble(thePostedDetails.get(8).toString());
//interestRegime=parseDouble(thePostedDetails.get(9).toString());
//DrAccountNumber=thePostedDetails.get(10).toString();
//CrAccountNumber=thePostedDetails.get(11).toString();
//YearMonth thePostedDate =(YearMonth)thePostedDetails.get(12);
//switch(txnType.trim()){
//
//       
//case "Expense":
//
//    if(amount>0.0){
//        
//    debit=new ArrayList();   
//
//    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(DrAccountNumber));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
//    credit .add(3, "On"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//
//    post.generalPosting(debit, credit);
//    
//   dbq. updateBulkIteM(trid+"");
//}
//  
//    
//    break;
//case "NewLoan":
//  
//    amortDetailsx = new ArrayList();
//    
//    amortDetailsx1 = new ArrayList(); 
//  
//  amortizeLoan amorty =new amortizeLoan(userId);  
//  fios.intFileWriterReplace(fios.createFileName("amortization", "allowUsage", "showNecessaryAmorts.txt"), "1");  
////    int theIndexN=theCorrespondingTxnCode(txnCode.trim());
////    if(theIndexN!=999){
////    List theDatails=uniqueListPost.get(theIndexN);
//
//    if(amount>0.0){
//        
//         fios.deleteFileExistance(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"));   
//        fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "creditLedger"+this.userId+".txt"), CrAccountNumber);
////          SwingWorker<Void,Void>createNewLoan=new SwingWorker(){
////  
////
////            @Override
////            protected Object doInBackground() throws Exception {
////                
////                loan.createNewLoanAmort(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "creditLedger"+userId+".txt")),c);
////                
////                return null;
////            }
////        };
////        createNewLoan.execute();
//        
//        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"),CrAccountNumber);
//        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");
//        
//        
//        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), dbq.AccountName(CrAccountNumber));
//        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");   
//        
//      
//   fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), tenure+"");
//        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");
//
//          fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), "MONTHS");
//        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");  
//        
//         amortDetailsx.add(amount);//PRINCIPAL :0
//   fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), amount+"");
//        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");
//
//          fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), "Mothly Income");
//        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");
//        
//        
//          fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), "BUSINESS PURPOSE");
//        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");
////   amortDetailsx.add(amount);//PRINCIPAL :0
//        amortDetailsx.add(interestRate);//INTEREST RATE:1
//
//        amortDetailsx.add(tenure);//LOAN TENURE:2
//
//        amortDetailsx.add(DateFetched);//START DATE:3
//
//        amortDetailsx.add(periodType);//TENURE TYPE:4
//        
//           amortDetailsx.add(interestRegime);//INTEREST REGIME:5
//
////        String accountName=dbq.getUserName(officerInCharge);
//        
//        amortDetailsx.add(officerInCharge);
//
//     
//   
//        
////       JOptionPane.showMessageDialog(this, amortDetailsx.get(0).toString()+";"+amortDetailsx.get(1).toString()+";"+amortDetailsx.get(2).toString()+";"+amortDetailsx.get(3).toString()+";"+amortDetailsx.get(4).toString()+";"+amortDetailsx.get(5).toString());  
//        
//        
////JOptionPane.showConfirmDialog(this, loandetails.size()+"");
////            amorty.createTheLoan(amorty.amortizeme(amortDetailsx,c), amortDetailsx,LoanDetails,c);
//            
////  loan.updateSubmited(dbq.AccountName(CrAccountNumber),"Approved"); 
//       
//    fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+dbq.AccountName(CrAccountNumber)+ ".txt"), "4");
//        
//   fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "activeTrnId"+userId+".txt"), loan.getActiveId(CrAccountNumber,c));      
//        
////               String trnId=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeTrnId"+userId+".txt"));
////              List loandetails = loan.getLoanDetails(trnId,c); 
//  
////    if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "LoanApproved.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllLoanTransactions.txt"))==7){
////    
////        
////         SwingWorker<Void,Void>updatesendSms=new SwingWorker(){
////         @Override
////         protected Object doInBackground() throws Exception {
////        sendsms.createLoanApprovedSMS(dbq.AccountName(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "creditLedger"+userId+".txt"))), loandetails.get(4).toString(),c);
////             return null; } };
////     updatesendSms.execute();     
////}
//
//  
//      Integer zxso=4;
//    fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+CrAccountNumber+".txt"),zxso.toString());   
// 
////  if(  fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+CrAccountNumber+".txt"))==4){
////     SwingWorker<Void,Void>updateLoanStore=new SwingWorker(){
////         @Override
////         protected Object doInBackground() throws Exception {
////          loan.updateLoanStoreAll(userId+"", fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "creditLedger"+userId+".txt")),"Approved","newloan"+fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "creditLedger"+userId+".txt")),trnId);   
////             
////             return null;  } };
////     updateLoanStore.execute();
////     }   
//  
////        List loanDetails= new ArrayList();
////       loanDetails.add(loandetails.get(4).toString());//Princimpal amount
////         loanDetails.add(loandetails.get(3).toString());//interest amount
////  loanDetails.add(loandetails.get(6).toString());//instalment start date
////  loanDetails.add(loandetails.get(7).toString());//instalment end date
////    loanDetails.add(loandetails.get(1).toString());//Loan Tenure
////   loanDetails.add("Approved");//loan cycle status
//   
////    loanDetails.add(dbq.AccountName(CrAccountNumber));//Account Name
////    
////    loanDetails.add(CrAccountNumber);//Account Number
////    
////    
////      SwingWorker<Void,Void>updateReportATable=new SwingWorker(){
////         @Override
////         protected Object doInBackground() throws Exception {
////           loan.updateLoanReportATable(loanDetails); 
////             
////             return null;
////         }
////     
////     };
////     updateReportATable.execute();   
//        
//       loan.createNewLoan(amortDetailsx,c);
//        
// List   debitn=new ArrayList();   
//
//    debitn .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
//    debitn .add(1, DateFetched);//Transaction Date:1
//    debitn .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
//    debitn .add(3, "From"+" "+dbq.AccountName(DrAccountNumber));//Narrative 2:3
//    debitn .add(4, DateFetched);//Value Date:4
//    debitn .add(5, amount);//Debit Amount:5
//    debitn .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
//    debitn .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
//    debitn .add(8, "0002");//Debit Reference Number:8
//    debitn .add(9, batch);//Batch Number:9
//    debitn .add(10, "Gen");//Transaction Type:10
//    debitn .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//   List creditn=new ArrayList();    
//
//    creditn .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
//    creditn .add(1, DateFetched);//Transaction Date:1
//    creditn .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
//    creditn .add(3, "On"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    creditn .add(4, DateFetched);//Value Date:4
//    creditn .add(5, amount);//Debit Amount:5
//    creditn .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
//    creditn .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
//    creditn .add(8, "0002");//Debit Reference Number:8
//    creditn .add(9, batch);//Batch Number:9
//    creditn .add(10, "Gen");//Transaction Type:10
//    creditn .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
////
////      SwingWorker<Void,Void>genLoan=new SwingWorker(){
////            @Override
////            protected Object doInBackground() throws Exception {
//            post.loanDisbursement(debitn, creditn);
////               dbq. updateBulkIteM(trid+"");
////
////            String loandetailsNew =fios.stringFileReader(fios.createFileName("loanApplication", "amortValues","loanStore"+CrAccountNumber+".txt"));       
////
////            List loanDetailsNew= new ArrayList();
////
////            loanDetailsNew.add(loandetailsNew.split("[,]", 16)[1]);//Princimpal amount
////            loanDetailsNew.add(loandetailsNew.split("[,]", 16)[2]);//interest amount
////            loanDetailsNew.add(loandetailsNew.split("[,]", 16)[4]);//instalment start date
////            loanDetailsNew.add(loandetailsNew.split("[,]", 16)[5]);//instalment start date
////            loanDetailsNew.add(loandetailsNew.split("[,]", 16)[0]);//instalment start date
////            loanDetailsNew.add("Disbursed");//loan cycle status
////            loanDetailsNew.add(dbq.AccountName(CrAccountNumber));//loan cycle status
////            loanDetailsNew.add(CrAccountNumber);//loan cycle status
////            SwingWorker<Void,Void>updateLoanReportATable=new SwingWorker(){
////            @Override
////            protected Object doInBackground() throws Exception {
////            loan.updateLoanReportATable(loanDetailsNew); 
////            return null;
////            } };
////            updateLoanReportATable.execute(); 
//
//
//accountN=CrAccountNumber;
//
////JOptionPane.showMessageDialog(c, accountN);
//
////            Integer xxy=5;
////
////            fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+CrAccountNumber+ ".txt"), xxy.toString());
////
////            fios.forceFileExistanceAppend(fios.createFileName("loanApplication", "amortValues", "fromLoanManagementTable.txt"));
////            Integer xl=0;
////            fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "fromLoanManagementTable.txt"),xl.toString());
////
////            loan.updateLoanStoreAll(userId, CrAccountNumber,"Disbursed","newloan"+CrAccountNumber,fios.stringFileReader(fios.createFileName("loanApplication", "amortValues","loanStore"+CrAccountNumber+".txt")).split("[,]", 16)[14].replaceAll("[,]", " "));
//
//            }
//
////            SwingWorker<Void,Void>submittedSecurity=new SwingWorker(){
////            @Override
////            protected Object doInBackground() throws Exception {
////            loan. processSecurity(accountN,"1;Land;Not Specified",c);
////            return null;
////            }
////            };
////            submittedSecurity.execute();
//
//            //    }else{
//            // JOptionPane.showMessageDialog(this, "The Expense Txn Narration:"+Narration+" "+"Amount:"+amount+" "+"Dated:"+DateFetched+" "+"has no corresponding txn Code defined\n\nPlease click ok to skip this txn");
//            //    break;
//            //    
//            //    } 
//
//
//            break;
//           
//
//     case  "Drawing":
//  if(amount>0.0)
//  
//  
//  
//  {
//    debit=new ArrayList();   
//
//    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
//    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//    
//    post.generalPosting(debit, credit);
//    post.creditCashFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));
//    }     
//    break;     
//           
//    
//          case  "DrawingBank":
//  if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
//    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//    
//    post.generalPosting(debit, credit);
//    post.creditCashFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));
//    }     
//    break;  
//case "Withdrawal":
// 
//    if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//
//    post.generalPosting(debit, credit);
//    
//       dbq. updateBulkIteM(trid+"");
//}
//   
//    
//    break;
//case "Deposit":
//    if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//
//    post.generalPosting(debit, credit);
//    
//    
//    dbq. updateBulkIteM(trid+"");
//    }
//
//    break;
//    
//    
//case "HEFROI":
// 
//    if(amount>0.0){
//        
//    debit=new ArrayList();   
//
//    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//    
//    dbq.captureSavingsIndividual(credit,c);
//    
//    post.generalPosting(debit, credit);
//    
//    dbq. updateBulkIteM(trid+"");
//    }
//    break;
//    
//    case "Payable":
// 
//    if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//    post.debitCashFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+DateFetched);
//    post.generalPosting(debit, credit);
//    dbq. updateBulkIteM(trid+"");
//    }
//    break;
//case "Receivable":
//  if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//    
//    post.generalPosting(debit, credit);
//    post.creditCashFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+DateFetched);
//    dbq. updateBulkIteM(trid+"");
//    }     
//    break;
//     
//
//
//
//case "ClearPayable":
//    if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11,dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//
//    post.generalPosting(debit, credit);
//    post.creditCashFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+DateFetched);
//    dbq. updateBulkIteM(trid+"");
//    }
//
//    
//    break;
//case "ClearReceivable":
//
//if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//    post.debitCashFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+DateFetched);
//    post.generalPosting(debit, credit);
//    dbq. updateBulkIteM(trid+"");
//    
//    }   
//break;
//    case "General":
//  if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//
//    post.generalPosting(debit, credit);
//    dbq. updateBulkIteM(trid+"");
//    }
//    break;
//    
//
//case "Salary":
//   if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//
//    post.salaryPosting(debit, credit);
////    post.creditCashFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+sdf.fmt(new Date(System.currentTimeMillis())));
//  dbq. updateBulkIteM(trid+"");
//   } 
//    
//    break;
//case "Savings":
//    if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//      
//     dbq.captureSavingsIndividual(credit,c);
//    
//    post.savingsPosting(debit, credit);
//    
//    dbq. updateBulkIteM(trid+"");
//    }
//    
//    break;
//    
//case "WithdrawSavings":
//   if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//       
//     Double runninBal=parseDouble(dbq.accountBalance(DrAccountNumber,debit.get(4).toString()));   
// 
// if(runninBal<amount){
//
//Object[] optionsx = {"Yes",  "No"};
//    int nx = JOptionPane.showOptionDialog(c,  dbq.AccountName(DrAccountNumber)+"'s"+"  Account Balance is NOT enough!!!\n"+"Do you want to  continue ?",
//    "CHECK ACCOUNT BALANCE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsx, optionsx[0]);
//    if(nx==JOptionPane.YES_OPTION){
//           dbq.reduceSavings(debit); 
//  post.cashWithdrawPosting(debit, credit);
//      dbq. updateBulkIteM(trid+"");          
//    }
//    else if (nx==JOptionPane.NO_OPTION){    
//        
////       continue;
//    
//    
//    }
//
//
//
//}else{
//      dbq.reduceSavings(debit);
//post.cashWithdrawPosting(debit,credit);
//  dbq. updateBulkIteM(trid+"");              
//
//
//
//}
//   
// 
//    }
//   
//   break;
//  
//    case "DepositPartPay":
//    if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Save");//Transaction Type:10
//    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Save");//Transaction Type:10
//    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//      
////     dbq.captureSavingsIndividual(credit);
//    
//    post.savingsPosting(debit, credit);
//    dbq. updateBulkIteM(trid+"");
//    }
//    
//    break;
// 
//case "WithdrawDepositPartPay":
//   if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11,dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//       
//     Double runninBal=parseDouble(dbq.accountBalance(DrAccountNumber,debit.get(4).toString()));   
// 
// if(runninBal<amount){
//
//Object[] optionsx = {"Yes",  "No"};
//    int nx = JOptionPane.showOptionDialog(c,  dbq.AccountName(DrAccountNumber)+"'s"+"  Account Balance is NOT enough!!!\n"+"Do you want to  continue ?",
//    "CHECK ACCOUNT BALANCE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsx, optionsx[0]);
//    if(nx==JOptionPane.YES_OPTION){
////           dbq.reduceSavings(debit); 
//  post.cashWithdrawPosting(debit, credit);
//          dbq. updateBulkIteM(trid+"");      
//    }
//    else if (nx==JOptionPane.NO_OPTION){    
//        
////       continue;
//    
//    
//    }
//
//
//
//}else{
////      dbq.reduceSavings(debit);
//post.cashWithdrawPosting(debit,credit);
//                
//dbq. updateBulkIteM(trid+"");
//
//
//}
//   
// 
//    } 
//    break;
//case "Capitalisation":
//    if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "CapR");//Transaction Type:10
//    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "CapR");//Transaction Type:10
//    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//fios.intFileWriterReplace(fios.createFileName("sharesAvailable","shareValues", "usedWindow"+DrAccountNumber+".txt"), "365");
//    post.regularShareContribution(debit, credit);
//    dbq. updateBulkIteM(trid+"");
//    }
//    
//    
//    break;
//case "Decaptalisation":
//    if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "DCap");//Transaction Type:10
//    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "DCap");//Transaction Type:10
//    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//
//     fios.intFileWriterReplace(fios.createFileName("sharesAvailable","shareValues", "usedWindow"+CrAccountNumber+".txt"),"365");
//     
//    post.decapitalise(debit, credit);
//    dbq. updateBulkIteM(trid+"");
//    }
//    
//    break;
//case "RecoverInterest":
//    if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//     
//post.debitCashFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+DateFetched);    
//    post.generalPosting(debit, credit);
//    dbq. updateBulkIteM(trid+"");
//    }
//    
//    break;
//    
//    
//case "WriteOffInterest":
//    if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//
//    
//    post.generalPosting(debit, credit);
// post.creditCashFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+DateFetched); 
// dbq. updateBulkIteM(trid+"");
//    }
//    
//    break;
//    
//case "RecoverPenalty":
//    
//       
//    
//   if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//post.debitCashFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+DateFetched); 
//    post.generalPosting(debit, credit);
//    dbq. updateBulkIteM(trid+"");
//    } 
//    
//    
//    
//    break;
//case "WriteOffPenalty":
//    if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//
//    post.generalPosting(debit, credit);
//     post.creditCashFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+DateFetched);   
//     dbq. updateBulkIteM(trid+"");
//    }
//    
//    
//    break;
//    
//    
//    
//case "RecoverAccumulatedInterest":
//    if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//post.debitCashFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+DateFetched); 
//    post.generalPosting(debit, credit);
//    dbq. updateBulkIteM(trid+"");
//    }
//    
//    break;
//case "WriteOffAccumulatedInterest":
//    if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//
//    post.generalPosting(debit, credit);
//         post.creditCashFirst(CrAccountNumber, credit, Narration+" "+"Processed on"+" "+DateFetched); 
//         dbq. updateBulkIteM(trid+"");
//    }
//    
//    break;
//    
//    
//    
//case "BadLoansRecovered":
//    if(amount>0.0){
//    debit=new ArrayList();   
//
//    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
//    debit .add(1, DateFetched);//Transaction Date:1
//    debit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    debit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    debit .add(4, DateFetched);//Value Date:4
//    debit .add(5, amount);//Debit Amount:5
//    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
//    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
//    debit .add(8, "0002");//Debit Reference Number:8
//    debit .add(9, batch);//Batch Number:9
//    debit .add(10, "Gen");//Transaction Type:10
//    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11
//
//    credit=new ArrayList();    
//
//    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
//    credit .add(1, DateFetched);//Transaction Date:1
//    credit .add(2, Narration+" "+"Processed on"+" "+DateFetched);//Narrative 1:2
//    credit .add(3, "From"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
//    credit .add(4, DateFetched);//Value Date:4
//    credit .add(5, amount);//Debit Amount:5
//    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
//    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
//    credit .add(8, "0002");//Debit Reference Number:8
//    credit .add(9, batch);//Batch Number:9
//    credit .add(10, "Gen");//Transaction Type:10
//    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//post.debitCashFirst(DrAccountNumber, debit, Narration+" "+"Processed on"+" "+DateFetched); 
//    post.generalPosting(debit, credit);
//    dbq. updateBulkIteM(trid+"");
//    }
//    
//    break;
//}     
// 
//
//   
//}

public List updateDefaults(String accountNumber){
 
    List theCollection=new ArrayList();
    
        theCollection.add("Existing Borrower");
        theCollection.add("Individual");
        theCollection.add("Group 1");
        theCollection.add("Group 1");
        theCollection.add("Cycle"+loan.cycleNumber(accountNumber));
        theCollection.add("Salary Loan");
        theCollection.add("Friends");
        theCollection.add("Single Instalment Loan");
        theCollection .add("Business Financing");
        theCollection.add("No History");
        theCollection.add("Can't Tell");
        theCollection.add("No");
        theCollection.add("0"); 
        theCollection.add("Level 1"); 
        theCollection.add("Monthly Income"); 
         theCollection.add("100,000-200,000");
         theCollection.add("Very Low");
        theCollection.add("Self Employment");
        theCollection.add("Agriculture");
         theCollection.add("Male");
           theCollection.add("Single");
           theCollection.add("Minor");
           theCollection.add("No Education");
           
            theCollection.add("Email");
           theCollection.add("augbazi@mail.com");
           theCollection.add("Excellent");
            theCollection.add("Excellent");
              theCollection.add("0792416111");
 
 return theCollection;
 }
 

public void setTheClosingString(String theString){

this.closingNotes=theString;
}


}

    
    

