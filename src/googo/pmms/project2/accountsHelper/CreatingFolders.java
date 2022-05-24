/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.accountsHelper;

import googo.pmms.project2.databases.AccountNumberCreationDataBase;
import googo.pmms.project2.databases.DatabaseQuaries;
import googo.pmms.project2.databaseConnectors.JdbcConnector;
import googo.pmms.project2.databases.ReportsDatabase;
import googo.pmms.project2.databases.loanDatabaseQuaries;
import googo.pmms.project2.loanHelper.MaxBorrowed;
import googo.pmms.project2.reportsHelper.BalanceSheet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Stanchart
 */
public class CreatingFolders {
 
    MaxBorrowed mxb=new MaxBorrowed();
    fileInputOutPutStreams fios= new fileInputOutPutStreams();
    Formartter ffm= new Formartter();
    GregorianCalendar cal = new GregorianCalendar();
    JdbcConnector csx = new JdbcConnector( fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "AccountDBDetails.txt")));
    DatabaseQuaries dbq =new DatabaseQuaries();
    loanDatabaseQuaries loan=new loanDatabaseQuaries();
    AccountNumberCreationDataBase ancdb=new AccountNumberCreationDataBase();
    ReportsDatabase report =new ReportsDatabase();
    BalanceSheet bsheet= new BalanceSheet();
    DecimalFormat numberFormat =new DecimalFormat("#.##");
    DecimalFormat nu =new DecimalFormat("#.#");
    DecimalFormat nuw =new DecimalFormat("#");
    SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");

    DecimalFormat df1 = new DecimalFormat("#,###");
      String userId;
   public void creatTheFoldersAndFiles(){
   
   List<String> accountsAvailable=new ArrayList();
   
   accountsAvailable=dbq.getAllAccountNumbers();
  int n=0;
   while(n< accountsAvailable.size()){
//   createAccountRunningBalnce(accountsAvailable.get(n));
//   createAccountPreviouslyAddedBalance(accountsAvailable.get(n));
   createAccountName(accountsAvailable.get(n));
   createAccountNumber(accountsAvailable.get(n));
     createAccountNumber(accountsAvailable.get(n));

   n++;
   }

   } 
    public void setUserID(String userid){
this.userId=userid;
}
//     private void  createAccountRunningBalnce(String accountNumber){
//  String runningBalance=dbq.runningBalanceDB(accountNumber);
//  fios.stringFileWriter(fios.createFileName("accountManagement", "accountBalances", "accountRunningBalance"+accountNumber+".txt"), runningBalance);
//  
//  }
//   private void createAccountPreviouslyAddedBalance(String accountNumber){
//  
//  String runningBalance=dbq.runningBalanceDB(accountNumber);
//  fios.stringFileWriter(fios.createFileName("accountManagement", "accountBalances", "previouslyAdded"+accountNumber+".txt"), runningBalance);
//  
//  }
//  
   private void createAccountName(String accountNumber){
  

  fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "accountName"+accountNumber+".txt"), dbq.AccountName(accountNumber));
  
  }
  private void  createAccountNumber(String accountNumber){
  
   fios.stringFileWriter(fios.createFileName("accountManagement", "accountNumber", "accountNumber"+dbq.AccountName(accountNumber)+".txt"), accountNumber);
  
  }
 
  
     private void    createMasterAccount(String accountMasterNumber){
  
         
  fios.stringFileWriter(fios.createFileName("accountManagement", "masterAccounts",accountMasterNumber+".txt"), ancdb.AccountMaster(accountMasterNumber));
  
  
     }
    private void     createMasterBalance(String accountMasterNumber){
  
   fios.stringFileWriter(fios.createFileName("accountManagement", "masterBalances","masterBalances"+accountMasterNumber+".txt"), ancdb.masterBalance(accountMasterNumber));
  
  }

     public void creatTheFoldersAndFiles1(){
   
   List<String> accountMastersAvail=new ArrayList();
   
   accountMastersAvail=dbq.getAllAccountMasterNames();
  int n=0;
   while(n<  accountMastersAvail.size()){

        createMasterAccount(accountMastersAvail.get(n));

   n++;
   }

   } 
     
     
     
     
     
public void creatTheFoldersAndFiles2(){
   
   List<String> accountsAvailable=new ArrayList();
   
   accountsAvailable=dbq.getAllAccountMasterAccounts();
  int n=0;
   while(n< accountsAvailable.size()){
        createMasterBalance(accountsAvailable.get(n));
    
   n++;
   }

   } 
}
