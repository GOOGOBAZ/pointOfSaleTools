/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.databases;
import googo.pmms.project2.databaseConnectors.JdbcConnector;
import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.loanHelper.MaxmumAmountBorrowedFormulas;
import googo.pmms.project2.frameHelper.MyTableModel;
import googo.pmms.project2.frameHelper.ObjectTableModel;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databaseConnectors.JdbcConnector1;
import googo.pmms.project2.frameHelper.ReportsModelData;
import java.awt.Component;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Stanchart
 */
public class ReportsDatabase {
    Map<Integer, String> level1m;
    Map<String, Object> level2m;

     MaxmumAmountBorrowedFormulas formulas= new  MaxmumAmountBorrowedFormulas();
    fileInputOutPutStreams fios= new fileInputOutPutStreams();
 Formartter fmt = new Formartter();
    Date Trndate,valuedate;
   ArrayList<String> column1;
   ArrayList<Object> data4;
 ArrayList<ArrayList<Object>> data5;
  ReportsModelData reportsLikeTable;
          JOptionPane p;
     MyTableModel model;
     ObjectTableModel model1;
    Date date;
  SimpleDateFormat df;
  String text;
  int realMonth, otherMonth;
   String dates, dates2,getFieldValue,actualFieldValue,  jTFuserId1mt,today,thistime,today1,newDate1,jTFuserId1mt1,newDate11,today2;
   Integer Value,Value1;
   double newbalance,ledgerBalance,creditamount;
    GregorianCalendar cal = new GregorianCalendar(); 
loanDatabaseQuaries loan=new loanDatabaseQuaries();
    DatabaseQuaries dbq =new DatabaseQuaries();
JdbcConnector quaryObj = new JdbcConnector(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "AccountDBDetails.txt"))); ; 
 JdbcConnector1 loancon= new   JdbcConnector1(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "LoanDetails.txt"))); 
    DecimalFormat df2 = new DecimalFormat("#");

 DecimalFormat NumberFormat =new DecimalFormat("#,###.##");

  SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
  SimpleDateFormat sdf2 =new SimpleDateFormat("yyyy/MM/dd");   
    String userId;
//     netProfits netprofits= new netProfits();
  public void setUserID(String userid){
this.userId=userid;
  
}
  public boolean newLedgerCreated(){
  
        boolean  accountCreated=false;int theN=0;
        
     try {
         
         Connection cq=quaryObj.createConnection();
               cq.setAutoCommit(false);
               String query = "SELECT  COUNT(trn_id) AS counts FROM account_created_store WHERE account_status='New'";
               PreparedStatement ps =  cq.prepareStatement(query);
               
               ResultSet rs = ps.executeQuery();
            
               while (rs.next()) {
                   
                   theN= rs.getInt("counts");
                 
                   
               }
                 cq.setAutoCommit(true);
          quaryObj.closeConnection(cq);
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
     
     if(theN>0){
     
     accountCreated=true;
     }
 
 return accountCreated;
  
  }
  
   public String selectAccountName(String accountNumber){
 
        String accountName=""; 
     try {
         
         Connection cq=quaryObj.createConnection();
               cq.setAutoCommit(false);
               String query = "SELECT account_name FROM account_created_store WHERE account_number="+accountNumber;
               PreparedStatement ps =  cq.prepareStatement(query);
               
               ResultSet rs = ps.executeQuery();
            
               while (rs.next()) {
                   
                   accountName= rs.getString("account_name");
                 
                   
               }
                 cq.setAutoCommit(true);
          quaryObj.closeConnection(cq);
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
 
 return accountName;
     }
  public String staffName(String staffId){
 
        String accountName=""; 
     try {
         
         Connection cq=quaryObj.createConnection();
               cq.setAutoCommit(false);
               String query = "SELECT account_name FROM log_in WHERE user_id="+staffId;
               PreparedStatement ps =  cq.prepareStatement(query);
               
               ResultSet rs = ps.executeQuery();
            
               while (rs.next()) {
                   
                   accountName= rs.getString("account_name");
                 
                   
               }
                 cq.setAutoCommit(true);
          quaryObj.closeConnection(cq);
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
 
 return accountName;
     }
      
    @SuppressWarnings("ConvertToTryWithResources")
    public Map<Integer, List<Object>> statementEntries(String accountNumber, String startDate, String endDate){
 List OpeningBalance=OpeningBalance(accountNumber, startDate, endDate);
     List ClosingBalance=   ClosingBalance(accountNumber, startDate, endDate);     
     Map<Integer, List<Object>> data1=null;List<Object> data2=null; Integer n=0;

data1= new HashMap<>();
      try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
   
     
          String query = "SELECT trn_date,value_date,narration,debit,credit,ledger_balance FROM"+" "+"BSANCA"+accountNumber+" "+"WHERE((trn_date>="+"'"+startDate+"'"+" "+"AND trn_date<="+"'"+endDate+"'"+")"+" "+"AND NOT narration="+"'"+"Opening Balance"+"'"+")";
            
                                                          
           PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
        if(n==0){
         data1.put(n, OpeningBalance);
         n=n+1;
         }
        
        data2.add(0, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("trn_date")));
         data2.add(1, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("value_date")));
         data2.add(2, rsxmt.getString("narration"));
     
          data2 .add(3, rsxmt.getString("debit"));
          
          data2.add(4, rsxmt.getString("credit"));
          
         data2 .add(5, rsxmt.getString("ledger_balance"));
         
        data1.put(n, data2);
        
         n++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      if(data1.isEmpty()){
       data1.put(0, OpeningBalance); 
        data1.put(1, ClosingBalance); 
      }else{
   data1.put(n, ClosingBalance);  
      }
return data1;
        
        
     
        
        }
    
    
    
    public Map<Integer, List<Object>> statementEntriesJT(String accountNumber, String startDate, String endDate){
 List OpeningBalance=OpeningBalanceJT(accountNumber, startDate, endDate);
     List ClosingBalance=   ClosingBalanceJT(accountNumber, startDate, endDate);     
     Map<Integer, List<Object>> data1=null;List<Object> data2=null; Integer n=0;

data1= new HashMap<>();
      try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
   
     
          String query = "SELECT trn_id, trn_date,value_date,narration,debit,credit,ledger_balance,chq_number FROM"+" "+"BSANCA"+accountNumber+" "+"WHERE((trn_date>="+"'"+startDate+"'"+" "+"AND trn_date<="+"'"+endDate+"'"+")"+" "+"AND NOT narration="+"'"+"Opening Balance"+"'"+")";
            
                                                          
           PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
        if(n==0){
         data1.put(n, OpeningBalance);
         n=n+1;
         }
        
        data2.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("trn_date")));
        
         data2.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("value_date")));
         
         data2.add(rsxmt.getString("narration"));
     
          data2.add(rsxmt.getString("debit"));
          
          data2.add(rsxmt.getString("credit"));
          
         data2.add(rsxmt.getString("ledger_balance"));
         
          data2.add(rsxmt.getString("chq_number"));
          
        data1.put(n, data2);
        
         n++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      if(data1.isEmpty()){
       data1.put(0, OpeningBalance); 
        data1.put(1, ClosingBalance); 
      }else{
   data1.put(n, ClosingBalance);  
      }
return data1;
        
        
     
        
        }
    
    
    
    public Map<Integer, List<Object>> statementEntriesJTX(String accountNumber, String startDate, String endDate){
        
// List OpeningBalance=OpeningBalanceJT(accountNumber, startDate, endDate);
//     List ClosingBalance=   ClosingBalanceJT(accountNumber, startDate, endDate);     
     Map<Integer, List<Object>> data1=null;List<Object> data2=null; Integer n=0;

data1= new HashMap<>();
      try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
   
     
          String query = "SELECT trn_id, chq_number,trn_date,value_date,narration,debit,credit,ledger_balance,chq_number FROM"+" "+"BSANCA"+accountNumber+" "+"WHERE((trn_date>="+"'"+startDate+"'"+" "+"AND trn_date<="+"'"+endDate+"'"+")"+" "+"AND NOT narration="+"'"+"Opening Balance"+"'"+")";
            
                                                          
           PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
//        if(n==0){
//         data1.put(n, OpeningBalance);
//         n=n+1;
//         }
           data2.add(rsxmt.getString("trn_id"));
           
              data2.add(rsxmt.getString("chq_number"));
              
        data2.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("trn_date")));
        
         data2.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("value_date")));
         
         data2.add(rsxmt.getString("narration"));
     
          data2.add(rsxmt.getString("debit"));
          
          data2.add(rsxmt.getString("credit"));
          
         data2.add(rsxmt.getString("ledger_balance"));
         
//          data2.add(rsxmt.getString("chq_number"));
          
        data1.put(n, data2);
        
         n++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
//      if(data1.isEmpty()){
//       data1.put(0, OpeningBalance); 
//        data1.put(1, ClosingBalance); 
//      }else{
//   data1.put(n, ClosingBalance);  
//      }
return data1;
        
        
     
        
        }
    
    
     public Map<Integer, List<Object>> statementEntriesRecon(String accountNumber, String startDate, String endDate){

         List OpeningBalance=OpeningBalanceJTc(accountNumber, startDate, endDate);
         
     List ClosingBalance=   ClosingBalanceJTc(accountNumber, startDate, endDate);    
     
     Map<Integer, List<Object>> data1=null;List<Object> data2=null; Integer n=0;

data1= new HashMap<>();
      try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
   
     
          String query = "SELECT trn_id, trn_date,value_date,narration,debit,credit,ledger_balance, staff_id FROM"+" "+"BSANCA"+accountNumber+" "+"WHERE((trn_date>="+"'"+startDate+"'"+" "+"AND trn_date<="+"'"+endDate+"'"+")"+" "+"AND NOT narration="+"'"+"Opening Balance"+"'"+")";
            
                                                          
           PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
        if(n==0){
         data1.put(n, OpeningBalance);
         n=n+1;
         }
         data2.add(rsxmt.getString("trn_id"));
        
        
        data2.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("trn_date")));
        
         data2.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("value_date")));
         
        
         
         data2.add(rsxmt.getString("narration"));
     
          data2.add(rsxmt.getString("debit"));
          
          data2.add(rsxmt.getString("credit"));
          
         data2.add(rsxmt.getString("ledger_balance"));
         
          data2.add(this.staffName(rsxmt.getString("staff_id")));
          
          data2.add(false);
          
        data1.put(n, data2);
        
         n++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      if(data1.isEmpty()){
       data1.put(0, OpeningBalance); 
        data1.put(1, ClosingBalance); 
      }else{
   data1.put(n, ClosingBalance);  
      }
return data1;
        
        
     
        
        }
    
    
    
    
    
    
    
    public Map<Integer, List<Object>> statementEntriesAnalysis(String trnCode){
   
     Map<Integer, List<Object>> data1=null;List<Object> data2=null; Integer n=0;

data1= new HashMap<>();
      try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);

          String query = "SELECT trn_date, value_date,narration,debit,credit,debit_account_no,credit_account_no FROM general_ledger WHERE chq_number="+"'"+trnCode+"'";
            
                                                          
           PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
        data2=new ArrayList<>();
        data2.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("trn_date")));
        
         data2.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("value_date")));
         
         data2.add(rsxmt.getString("narration"));
     
          data2.add(rsxmt.getString("debit"));
          
          data2.add(rsxmt.getString("credit"));
          
         data2.add(AccountName(rsxmt.getString("debit_account_no")));
  
        data1.put(n, data2);
        
         n++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      
return data1;
        
        
     
        
        }
    
    
     public Map<Integer, List<Object>> depSchedule(String trnCode){
   
     Map<Integer, List<Object>> data1=null;List<Object> data2=null; Integer n=0;

data1= new HashMap<>();
      try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);

          String query = "SELECT TrnId, DepMonth,DepYear,OpeningAssetValue,Depreciation,AcccumulatedDepreciation,ClosingAssetValue,DepreciationStatus FROM depreciationschedule WHERE AssetId="+trnCode;
            
                                                          
           PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
        
        data2.add(rsxmt.getString("TrnId"));
        
         data2.add(rsxmt.getString("DepMonth"));
         
         data2.add(rsxmt.getString("DepYear"));
     
          data2.add(rsxmt.getString("OpeningAssetValue"));
          
          data2.add(rsxmt.getString("Depreciation"));
          
         data2.add(rsxmt.getString("AcccumulatedDepreciation"));
  
         data2.add(rsxmt.getString("ClosingAssetValue"));
         
         data2.add(rsxmt.getString("DepreciationStatus"));
         
        data1.put(n, data2);
        
//        this.getb
        
         n++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      
return data1;
        
        
     
        
        }
    
    
 public Map<Integer, List<Object>> statementEntriesAmount(String accountNumber, String startDate, String endDate,String amnt){

     List OpeningBalance=OpeningBalance(accountNumber, startDate, endDate);
     
     List ClosingBalance=   ClosingBalance(accountNumber, startDate, endDate); 
     
     Map<Integer, List<Object>> data1=null;List<Object> data2=null; Integer n=0;

data1= new HashMap<>();
      try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);                                                                                                                                              
     
          String query = "SELECT trn_date,value_date,narration,debit,credit,ledger_balance FROM"+" "+"BSANCA"+accountNumber+" "+"WHERE ((trn_date>="+"'"+startDate+"'"+" "+"AND trn_date<="+"'"+endDate+"'"+")"+" "+"AND"+" "+"(debit LIKE"+"'"+amnt+"%" +"'"+"OR"+" "+"credit LIKE"+"'"+amnt+"%"+"'"+")"+")";
       
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
         if(n==0){
         data1.put(n, OpeningBalance);
         
         n=n+1;
         }
        data2.add(0, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("trn_date")));
        data2.add(1, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("value_date")));
         data2.add(2, rsxmt.getString("narration"));
     
          data2 .add(3, rsxmt.getString("debit"));
          
          data2.add(4, rsxmt.getString("credit"));
          
         data2 .add(5, rsxmt.getString("ledger_balance"));
        
        data1.put(n, data2);
        
         n++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
   if(data1.isEmpty()){
       data1.put(0, OpeningBalance); 
        data1.put(1, ClosingBalance); 
      }else{
   data1.put(n, ClosingBalance);  
      } 
return data1;
        
        
        
        }
 
 public Map<Integer, List<Object>> statementEntriesAmountJT(String accountNumber, String startDate, String endDate,String amnt){

     List OpeningBalance=OpeningBalanceJT(accountNumber, startDate, endDate);
     
     List ClosingBalance=   ClosingBalanceJT(accountNumber, startDate, endDate); 
     
     Map<Integer, List<Object>> data1=null;List<Object> data2=null; Integer n=0;

data1= new HashMap<>();
      try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);                                                                                                                                              
     
          String query = "SELECT trn_id, trn_date,value_date,narration,debit,credit,ledger_balance,chq_number FROM"+" "+"BSANCA"+accountNumber+" "+"WHERE ((trn_date>="+"'"+startDate+"'"+" "+"AND trn_date<="+"'"+endDate+"'"+")"+" "+"AND"+" "+"(debit LIKE"+"'"+amnt+"%" +"'"+"OR"+" "+"credit LIKE"+"'"+amnt+"%"+"'"+")"+")";
       
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
         if(n==0){
         data1.put(n, OpeningBalance);
         
         n=n+1;
         }
        data2.add(0, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("trn_date")));
        
        data2.add(1, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("value_date")));
        
         data2.add(2, rsxmt.getString("narration"));
     
          data2 .add(3, rsxmt.getString("debit"));
          
          data2.add(4, rsxmt.getString("credit"));
          
         data2 .add(5, rsxmt.getString("ledger_balance"));
         
         data2 .add(6, rsxmt.getString("chq_number"));
        
        data1.put(n, data2);
        
         n++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
   if(data1.isEmpty()){
       data1.put(0, OpeningBalance); 
        data1.put(1, ClosingBalance); 
      }else{
   data1.put(n, ClosingBalance);  
      } 
return data1;
        
        
        
        }
 
 
 private List OpeningBalance(String accountNumber, String startDate, String endDate){

 List<Object> data=null;
 data=new ArrayList<>();
// String dayBefore=fmt.formatDateToDatabaseDate(fmt.getExDateDecreamented(fmt.convertTdate(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate)), "1"));
      try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
     
          String query = "SELECT trn_date,ledger_balance FROM"+" "+"BSANCA"+accountNumber+" "+"WHERE trn_date<"+"'"+startDate+"'";
       
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          if (rsxmt.last()){
              
       
        
        data.add(0, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate));
        data.add(1, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate));
         data.add(2, "Opening Balance");
     
          data .add(3, "");
          
          data.add(4, "");
          
         data .add(5, rsxmt.getString("ledger_balance"));
         
        
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      if(data.isEmpty()){
      
      
        
        data.add(0, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate));
         data.add(1, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate));
         data.add(2, "Opening Balance");
     
          data .add(3, "");
          
          data.add(4, "");
          
         data .add(5, "0.0");
      }
return data;
        
        

}
 private List OpeningBalanceJT(String accountNumber, String startDate, String endDate){

 List<Object> data=null;
 data=new ArrayList<>();
// String dayBefore=fmt.formatDateToDatabaseDate(fmt.getExDateDecreamented(fmt.convertTdate(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate)), "1"));
      try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
     
          String query = "SELECT trn_date,ledger_balance,trn_id FROM"+" "+"BSANCA"+accountNumber+" "+"WHERE trn_date<"+"'"+startDate+"'";
       
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          if (rsxmt.last()){
              
       
        
        data.add(0, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate));
        
        data.add(1, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate));
        
         data.add(2, "Opening Balance");
     
          data .add(3, "");
          
          data.add(4, "");
          
         data .add(5, rsxmt.getString("ledger_balance"));
         
        data.add(6, rsxmt.getString("trn_id"));
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      if(data.isEmpty()){
      
      
        
        data.add(0, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate));
        
         data.add(1, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate));
         
         data.add(2, "Opening Balance");
     
          data .add(3, "");
          
          data.add(4, "");
          
         data .add(5, "0.0");
         
         data .add(6, "0001x");
         
      }
return data;
        
        

}
 
 private List OpeningBalanceJTc(String accountNumber, String startDate, String endDate){

 List<Object> data=null;
 data=new ArrayList<>();
// String dayBefore=fmt.formatDateToDatabaseDate(fmt.getExDateDecreamented(fmt.convertTdate(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate)), "1"));
      try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
     
          String query = "SELECT trn_id,trn_date,ledger_balance FROM"+" "+"BSANCA"+accountNumber+" "+"WHERE trn_date<"+"'"+startDate+"'";
       
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          if (rsxmt.last()){
              
       
        data.add(0, rsxmt.getString("trn_id"));
        
        data.add(1, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate));
        
        data.add(2, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate));
        
         data.add(3, "Opening Balance");
     
          data .add(4, "-");
          
          data.add(5, "-");
          
         data .add(6, rsxmt.getString("ledger_balance"));
         
        data.add(7, "SYSTEM");
        
        data.add(8, false);
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      if(data.isEmpty()){
      
         data .add(0, "0001x");
        
        data.add(1, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate));
        
         data.add(2, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(startDate));
         
         data.add(3, "Opening Balance");
     
          data .add(4, "-");
          
          data.add(5, "-");
          
         data .add(6, "0.0");
         data.add(7, "SYSTEM");
          
         data .add(8, false);
      
         
      }
return data;
        
        

}
 
 
private List ClosingBalance(String accountNumber, String startDate, String endDateDate){

List<Object> data=null;
   data=new ArrayList<>();
 
      try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
     
          String query = "SELECT trn_date, ledger_balance FROM"+" "+"BSANCA"+accountNumber+" "+"WHERE trn_date<="+"'"+endDateDate+"'";
       
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          if (rsxmt.last()){
              
     
        
        data.add(0, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDateDate));
         data .add(1, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDateDate));
         data.add(2, "Closing Balance");
     
          data .add(3, "-");
          
          data.add(4, "-");
          
         data .add(5, rsxmt.getString("ledger_balance"));
         
        
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
          if(data.isEmpty()){
      
      
        
        data.add(0, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDateDate));
         data .add(1, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDateDate));
         data.add(2, "Closing Balance");
     
          data .add(3, "-");
          
          data.add(4, "-");
          
         data .add(5, "0.0");
      }
return data;
        
}

private List ClosingBalanceJT(String accountNumber, String startDate, String endDateDate){

List<Object> data=null;
   data=new ArrayList<>();
 
      try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
     
          String query = "SELECT trn_date, ledger_balance,trn_id FROM"+" "+"BSANCA"+accountNumber+" "+"WHERE trn_date<="+"'"+endDateDate+"'";
       
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          if (rsxmt.last()){
              
     
        
        data.add(0, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDateDate));
         data .add(1, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDateDate));
         data.add(2, "Closing Balance");
     
          data .add(3, "");
          
          data.add(4, "");
          
         data .add(5, rsxmt.getString("ledger_balance"));
         
         data .add(6, rsxmt.getString("trn_id"));
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
          if(data.isEmpty()){
      
      
        
        data.add(0, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDateDate));
         data .add(1, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDateDate));
         data.add(2, "Closing Balance");
     
          data .add(3, "");
          
          data.add(4, "");
          
         data .add(5, "0.0");
         
          data .add(5, "0002x");
      }
return data;
        
}

private List ClosingBalanceJTc(String accountNumber, String startDate, String endDateDate){

List<Object> data=null;
   data=new ArrayList<>();
 
      try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
     
          String query = "SELECT trn_date, ledger_balance,trn_id FROM"+" "+"BSANCA"+accountNumber+" "+"WHERE trn_date<="+"'"+endDateDate+"'";
       
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          if (rsxmt.last()){
              
        data .add(0, rsxmt.getString("trn_id"));
        
        data.add(1, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDateDate));
         data .add(2, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDateDate));
         data.add(3, "Closing Balance");
     
          data .add(4, "-");
          
          data.add(5, "-");
          
         data .add(6, rsxmt.getString("ledger_balance"));
         
      data .add(7, "SYSTEM");
      data .add(8, false);
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
          if(data.isEmpty()){
      
      
         data .add(0, "0002x");
        data.add(1, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDateDate));
         data .add(2, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(endDateDate));
         data.add(3, "Closing Balance");
     
          data .add(4, "-");
          
          data.add(5, "-");
          
         data .add(6, "0.0");
          data .add(7, "SYSTEM");
      data .add(8, false);
         
      }
return data;
        
}
public void populateTree(JTree tree,String root){
    
 DefaultMutableTreeNode top = new DefaultMutableTreeNode(root);
        
 try {
            
             Connection cq=quaryObj.createConnection();
            cq.setAutoCommit(false);
            
            String query = "SELECT DISTINCT account_l2 FROM account_created_store WHERE account_l1="+"'"+root+"'";
            PreparedStatement psxmt = cq.prepareStatement(query);
            ResultSet rsxmt = psxmt.executeQuery();
            while(rsxmt.next()){
    
           top.add(makeLeve2(rsxmt.getString("account_l2"))); 
                
            }
           
            cq.setAutoCommit(true);
//           quaryObj.closeConnection(cq);
        } catch (SQLException ex) {
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

  DefaultTreeModel treeModel = new DefaultTreeModel(top);
     
     tree.setModel(treeModel);

}
          


 public DefaultMutableTreeNode makeLeve2(String masterName){
     
  DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(masterName); 
  
  try {
        Connection cq=quaryObj.createConnection();
//            cq.setAutoCommit(false);
            
            String query = "SELECT DISTINCT account_l3 FROM account_created_store WHERE account_l2="+"'"+masterName+"'";
            PreparedStatement psxmt = cq.prepareStatement(query);
            ResultSet rsxmt = psxmt.executeQuery();
            while (rsxmt.next()){
            level2.add(makeLeve3(rsxmt.getString("account_l3")));     
            }
//          
//            cq.setAutoCommit(true);
            quaryObj.closeConnection(cq);
        } catch (SQLException ex) {
          
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      
        }
 
return level2;
 }

 public DefaultMutableTreeNode makeLeve3(String masterName){
     
  DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(masterName);    
  try {
           Connection cq=quaryObj.createConnection();
      cq.setAutoCommit(false);
            
            String query = "SELECT DISTINCT account_l4 FROM account_created_store WHERE account_l3="+"'"+masterName+"'";
            PreparedStatement psxmt = cq.prepareStatement(query);
            ResultSet rsxmt = psxmt.executeQuery();
            while (rsxmt.next()){
            level2.add(makeLeve4(rsxmt.getString("account_l4")));     
            }
    
            cq.setAutoCommit(true);
//            quaryObj.closeConnection(cq);
        } catch (SQLException ex) {
          
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      
        }
 
return level2;
 }

public DefaultMutableTreeNode makeLeve4(String masterName){
     
  DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(masterName);    
  try {
       Connection cq=quaryObj.createConnection();
         cq.setAutoCommit(false);
            
            String query = "SELECT DISTINCT account_l5 FROM account_created_store WHERE account_l4="+"'"+masterName+"'";
            PreparedStatement psxmt = cq.prepareStatement(query);
            ResultSet rsxmt = psxmt.executeQuery();
            while (rsxmt.next()){
                
            level2.add(makeLeve5(rsxmt.getString("account_l5")));     
           
            }
         
            cq.setAutoCommit(true);
//           quaryObj.closeConnection(cq);
        } catch (SQLException ex) {
          
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      
        }
 
return level2;
 }
  
 public DefaultMutableTreeNode makeLeve5(String masterName){
     
  DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(masterName); 
  
    masterName=masterName.replace(" ", "&"); 
    masterName=masterName.replace("/", "&");
    masterName=masterName.replace("-", "&");
    masterName=masterName.replace("\\", "&");
    masterName=masterName.replace("(", "&");
    masterName=masterName.replace(")", "&");
     masterName=masterName.replace("&&&&", "&");  
     masterName=masterName.replace("&&&", "&"); 
     masterName=masterName.replace("&&", "&");
      masterName=masterName.replace("&", "_");

  try {
         Connection cq=quaryObj.createConnection();
      cq.setAutoCommit(false);
            
            String query = "SELECT DISTINCT account_number FROM account_master_store WHERE account_name="+"'"+masterName+"'";
            PreparedStatement psxmt = cq.prepareStatement(query);
            ResultSet rsxmt = psxmt.executeQuery();
            while (rsxmt.next()){
            level2.add(makeLeve6(rsxmt.getString("account_number")));     
            }
            rsxmt.close();
            psxmt.close();
            cq.setAutoCommit(true);
//          quaryObj.closeConnection(cq);
        } catch (SQLException ex) {
          
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      
        }
 
return level2;
 } 
  
 public DefaultMutableTreeNode makeLeve6(String masterName){    
  DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(masterName);    

return level2;
 } 
  
  
  
 public void  populateMaster(JTable jTable3,String account){
 
 String accountNames= fios.stringFileReader(fios.createFileName("accountManagement", "masterAccounts", "masterName"+account+".txt"));
 double bal=0.0;String accountName="",accountNumber="";
 
     data5= new ArrayList<>();
      column1= new ArrayList<>();
      column1.add("Account Name");
      column1.add("Account Number");
      column1.add("Ledger Balance");

     try {
     Connection cq=quaryObj.createConnection();
      cq.setAutoCommit(false);
            String query = "SELECT account_name,account_number,running_balance FROM account_created_store WHERE account_l5="+"'"+accountNames+"'";
            PreparedStatement ps =  cq.prepareStatement(query);
            ResultSet rst3 = ps.executeQuery();
            int k=0;
           while(rst3.next()){
              data4 =new ArrayList();  
              
              data4.add(0,rst3.getString("account_name"));
              data4.add(1, rst3.getString("account_number"));
             data4.add(2,parseDouble(df2.format(rst3.getDouble("running_balance"))));
           data5.add(k, data4);
           k++;
            }
        
            model1= new ObjectTableModel( data5, column1);
           jTable3.setModel(model1);
 
            cq.setAutoCommit(true);
           quaryObj.closeConnection( cq);
        } catch (SQLException ex) { 
            Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
        }

 
 }
  
  
  
  public void populateAccounts(JTable jTable3,String accountNumber){
 
 
     data5= new ArrayList<>();
      column1= new ArrayList<>();

      column1.add("Entry Date");
      column1.add("Description");
      column1.add("Value Date");
      column1.add("Debits");
      column1.add("Credits");
      column1.add("Balance");
     try {
  Connection cq=quaryObj.createConnection();
    cq.setAutoCommit(false);
           String query = "SELECT trn_date,narration, value_date,debit,credit,ledger_balance FROM"+" "+"BSANCA"+accountNumber;
            PreparedStatement ps = cq.prepareStatement(query);
            ResultSet rst3 = ps.executeQuery();
            int k=0;
           while(rst3.next()){
              data4 =new ArrayList();  
               data4.add(0, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("trn_date")));
        
         data4.add(1, rst3.getString("narration"));
         
          data4.add(2, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("value_date")));
          
          data4 .add(3, rst3.getString("debit"));
          
         data4.add(4, rst3.getString("credit"));
          
         data4 .add(5, parseDouble(df2.format(rst3.getDouble("ledger_balance"))));
              
           data5.add(k, data4);
           k++;
            }
        
            model1= new ObjectTableModel( data5, column1);
           jTable3.setModel(model1);
 
           cq.setAutoCommit(true);
           quaryObj.closeConnection(cq);
        } catch (SQLException ex) { 
            Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
        }

 
 }
  
  
  
  
  
 Map<Integer, String> getDebitsAndCredits(){
 
     level1m= new HashMap<>();
  
        try {
              Connection cq=quaryObj.createConnection();
           cq.setAutoCommit(false);
            
            String query = "SELECT DISTINCT account_l1 FROM account_created_store";
            PreparedStatement psxmt = cq.prepareStatement(query);
            ResultSet rsxmt = psxmt.executeQuery();
           
            while(rsxmt.next()){
    if("Debits".equalsIgnoreCase(rsxmt.getString("account_l1"))){
    
    level1m.put(0, rsxmt.getString("account_l1"));
    
    }else if("Credits".equalsIgnoreCase(rsxmt.getString("account_l1"))){
    
    level1m.put(1, rsxmt.getString("account_l1"));
    
    }
            }
           
            rsxmt.close();
            psxmt.close();
          cq.setAutoCommit(true);
            quaryObj.closeConnection(cq);
            
        } catch (SQLException ex) {
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
  
        
   
           
return level1m; 

  }
  

 
  public  Map<Integer, String>getLevel2Items(){
     
 level1m= new HashMap<>();
  try {
       Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
            
            String query = "SELECT DISTINCT account_l2 FROM account_created_store";
            PreparedStatement psxmt = cq.prepareStatement(query);
            ResultSet rsxmt = psxmt.executeQuery();
            int s=0;
            while (rsxmt.next()){
            level1m.put(s, rsxmt.getString("account_l2"));
            s++;
            }
            rsxmt.close();
            psxmt.close();
           cq.setAutoCommit(true);
            quaryObj.closeConnection(cq);
        } catch (SQLException ex) {
          
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      
        }
 
return level1m;
 }
  
  public  Map<Integer, String>getLevel3Items(String level2Item){
     

  level1m= new HashMap<>() ;
  try {
             Connection cq=quaryObj.createConnection();
            cq.setAutoCommit(false);
            
            String query = "SELECT DISTINCT account_l3 FROM account_created_store WHERE account_l2="+"'"+level2Item+"'";
            PreparedStatement psxmt = cq.prepareStatement(query);
            ResultSet rsxmt = psxmt.executeQuery();
            int x=0;
            while (rsxmt.next()){
          level1m.put(x, rsxmt.getString("account_l3"));
      x++;
            }
            rsxmt.close();
            psxmt.close();
            cq.setAutoCommit(true);
             quaryObj.closeConnection(cq);
        } catch (SQLException ex) {
          
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      
        }
 
return level1m;
 } 
  
  
 public  Map<Integer, String> getLevel4Items(String level3Item){
     
  level1m= new HashMap<>() ;
  try {
        Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
            
            String query = "SELECT DISTINCT account_l4 FROM account_created_store WHERE account_l3="+"'"+level3Item+"'";
            PreparedStatement psxmt = cq.prepareStatement(query);
            ResultSet rsxmt = psxmt.executeQuery();
            int z=0;
            while (rsxmt.next()){
         
          level1m.put(z, rsxmt.getString("account_l4"));        
                
              z++;  
            }
            rsxmt.close();
            psxmt.close();
            cq.setAutoCommit(true);
             quaryObj.closeConnection(cq);
        } catch (SQLException ex) {
          
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      
        }
 
return level1m;
 } 
 
 
 public  Map<Integer, String> getLevel5Items(String level4Item){
     
  level1m= new HashMap<>() ;
  try {
      Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
            
            String query = "SELECT DISTINCT account_l5 FROM account_created_store WHERE account_l4="+"'"+level4Item+"'";
            PreparedStatement psxmt = cq.prepareStatement(query);
            ResultSet rsxmt = psxmt.executeQuery();
            int k=0;
            while (rsxmt.next()){   
             level1m.put(k, rsxmt.getString("account_l5"));
             k++;
          }
            
            rsxmt.close();
            psxmt.close();
            cq.setAutoCommit(true);
             quaryObj.closeConnection(cq);
        } catch (SQLException ex) {
          
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      
        }
 
return level1m;
 } 
 
 
  
  public   Map<String, Object> getBalances(String Level5Items,String startDate, String endDate,Component c){
//     JOptionPane.showMessageDialog(c, Level5Items);
    String accountNameOriginal= Level5Items,masterAccount=""; 
  level2m= new HashMap<>() ;
   Level5Items=Level5Items.replace(" ", "&"); 
    Level5Items=Level5Items.replace("/", "&");
    Level5Items=Level5Items.replace("-", "&");
   Level5Items=Level5Items.replace("\\", "&");
   Level5Items=Level5Items.replace("(", "&");
    Level5Items=Level5Items.replace(")", "&");
    Level5Items=Level5Items.replace("&&&&", "&");  
     Level5Items=Level5Items.replace("&&&", "&"); 
     Level5Items=Level5Items.replace("&&", "&");
    Level5Items=Level5Items.replace("&", "_");
    
    masterAccount=masterAccountUnique(Level5Items,c);
  
    String startingBal=balancepAndLFstart(masterAccount,startDate,c);
      String endBal=balancepAndLEnd(masterAccount,endDate);
    
      Double diff=formulas.subtract(parseDouble(endBal), parseDouble(startingBal));
      if(diff>0){
  level2m.put(accountNameOriginal,diff); 
      }
if(level2m.isEmpty()){
level2m.put(accountNameOriginal, "0.0");

}
 
return level2m;

 }    
  
  
    private String getPandLBalances(String startDate, String endDate,String Level5Items,Component c){
     
    String masterAccount="";  String bal="";
  
   Level5Items=Level5Items.replace(" ", "&"); 
    Level5Items=Level5Items.replace("/", "&");
    Level5Items=Level5Items.replace("-", "&");
   Level5Items=Level5Items.replace("\\", "&");
   Level5Items=Level5Items.replace("(", "&");
    Level5Items=Level5Items.replace(")", "&");
    Level5Items=Level5Items.replace("&&&&", "&");  
     Level5Items=Level5Items.replace("&&&", "&"); 
     Level5Items=Level5Items.replace("&&", "&");
    Level5Items=Level5Items.replace("&", "_");
    
    masterAccount=masterAccountUnique(Level5Items,c);
   
    String startingBal=balancepAndLFstart(masterAccount,startDate,c);
    
      String endBal=balancepAndLEnd(masterAccount,endDate);
   
      Double diff=formulas.subtract(parseDouble(endBal), parseDouble(startingBal));
      
      if(diff>0){
 bal=diff+""; 
      }
if(bal.equalsIgnoreCase("")){
bal="0.0";

}
 
return bal;

 }  
  
  
  
  
  private String balancepAndLFstart(String masterAccount,String date,Component c){
   String panslbal="",finals=""; boolean check=false;
    
 
      try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
     
          String query = "SELECT master_balance FROM"+" "+"BSANCA"+masterAccount+" "+"WHERE trn_date<"+"'"+date+"'";
       
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
  
       if (rsxmt.last()){
                
          panslbal=rsxmt.getString("master_balance");   
             check=true;   
            }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
          JOptionPane.showMessageDialog(c,  ex.getMessage());
           JOptionPane.showMessageDialog(c,  masterAccount); 
      }
   if(check){
      finals=panslbal;
      }else{
      finals="0.0";
      }
  return finals;
  
 
  }
 
   private String balancepAndLEnd(String masterAccount,String date){
  String panslbal="",finals=""; boolean check=false;
    
 
      try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
     
          String query = "SELECT master_balance FROM"+" "+"BSANCA"+masterAccount+" "+"WHERE trn_date<="+"'"+date+"'";
       
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
  
        if (rsxmt.last()){
                
          panslbal=rsxmt.getString("master_balance");   
             check=true;   
            }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      if(check){
      finals=panslbal;
      }else{
      finals="0.0";
      }
  return finals;
  }
private String  masterAccountUnique(String accountName,Component c){
    
    
    
    String accountNumber="";
 try {
        Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
            
            String query = "SELECT account_number FROM account_master_store WHERE account_name="+"'"+accountName+"'";
            PreparedStatement psxmt = cq.prepareStatement(query);
            ResultSet rsxmt = psxmt.executeQuery();
            while (rsxmt.next()){
                
            accountNumber=rsxmt.getString("account_number");     
            }
            rsxmt.close();
            psxmt.close();
           cq.setAutoCommit(true);
            quaryObj.closeConnection(cq);
        } catch (SQLException ex) {
          
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      
        }
//   fios.stringFileWriter(fios.createFileName("test", "testit", accountName+"trdyr.txt"),accountNumber);
//JOptionPane.showMessageDialog(c, accountNumber);
return accountNumber;
}
  
  public   Map<String, Object> getBalancesSheet(String Level5Items,String sheetDate,Component c){
     
    String accountNameOriginal= Level5Items,masterAccount=""; 
  level2m= new HashMap<>() ;
   Level5Items=Level5Items.replace(" ", "&"); 
    Level5Items=Level5Items.replace("/", "&");
    Level5Items=Level5Items.replace("-", "&");
   Level5Items=Level5Items.replace("\\", "&");
   Level5Items=Level5Items.replace("(", "&");
    Level5Items=Level5Items.replace(")", "&");
    Level5Items=Level5Items.replace("&&&&", "&");  
     Level5Items=Level5Items.replace("&&&", "&"); 
     Level5Items=Level5Items.replace("&&", "&");
    Level5Items=Level5Items.replace("&", "_");
    
    masterAccount=masterAccountUnique(Level5Items,c);
    
  try {
        Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
            
            String query = "SELECT master_balance FROM"+" "+"bsanca"+masterAccount+" "+" "+"WHERE trn_date<="+"'"+sheetDate+"'";
            PreparedStatement psxmt = cq.prepareStatement(query);
            ResultSet rsxmt = psxmt.executeQuery();
            if (rsxmt.last()){
                
            level2m.put(accountNameOriginal,parseDouble(df2.format(rsxmt.getDouble("master_balance"))));     
            }
            rsxmt.close();
            psxmt.close();
           cq.setAutoCommit(true);
            quaryObj.closeConnection(cq);
        } catch (SQLException ex) {
          
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      
        }
 
if(level2m.isEmpty()){
level2m.put(accountNameOriginal, "0.0");

}
 
return level2m;

 }   
 private String getBalanceSheetBalances(String Level5Items,String sheetDate,Component c){
     
    String masterAccount=""; String bal="";

   Level5Items=Level5Items.replace(" ", "&"); 
    Level5Items=Level5Items.replace("/", "&");
    Level5Items=Level5Items.replace("-", "&");
   Level5Items=Level5Items.replace("\\", "&");
   Level5Items=Level5Items.replace("(", "&");
    Level5Items=Level5Items.replace(")", "&");
    Level5Items=Level5Items.replace("&&&&", "&");  
     Level5Items=Level5Items.replace("&&&", "&"); 
     Level5Items=Level5Items.replace("&&", "&");
    Level5Items=Level5Items.replace("&", "_");
    
    masterAccount=masterAccountUnique(Level5Items,c);
    
  try {
        Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
            
            String query = "SELECT master_balance FROM"+" "+"bsanca"+masterAccount+" "+" "+"WHERE trn_date<="+"'"+sheetDate+"'";
            PreparedStatement psxmt = cq.prepareStatement(query);
            ResultSet rsxmt = psxmt.executeQuery();
            if (rsxmt.last()){
                
            bal=rsxmt.getString("master_balance");     
            }
            rsxmt.close();
            psxmt.close();
           cq.setAutoCommit(true);
            quaryObj.closeConnection(cq);
        } catch (SQLException ex) {
          
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      
        }
  if(bal.equalsIgnoreCase("")){
bal="0.0";

}
 if(Level5Items.equalsIgnoreCase("Retained_Earnings")){
 fios.forceFileExistanceZero(fios.createFileName("PMMS_Statements", "reports", "netprofit.txt"));
     bal=(parseDouble(bal)+parseDouble(fios.stringFileReader(fios.createFileName("PMMS_Statements", "reports", "netprofit.txt"))))+"";

 }

 
return bal;

 } 
  public Map<Integer, List<Object>> staffMembers(){
  
  Map<Integer, List<Object>> data1=null;List<Object> data2=null; Integer n=0;

data1= new HashMap<>();
      try {
           Connection cq=quaryObj.createConnection();
           cq.setAutoCommit(false);
 

          String query = "SELECT title,first_name, last_name,recruitement_date,role,user_id,line_manager FROM"+" "+"log_in";
          PreparedStatement psxmt =  cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
        
        data2.add(0, rsxmt.getString("title"));
        
         data2.add(1, rsxmt.getString("first_name"));
         
          data2.add(2, rsxmt.getString("last_name"));
          
          data2 .add(3, fmt.ageCalYears(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("recruitement_date"))));
          
          data2.add(4, rsxmt.getString("role"));
          
         data2 .add(5, rsxmt.getString("user_id"));
         data2 .add(6, rsxmt.getString("line_manager"));
         data1.put(n, data2);
         n++;
          }
        rsxmt.close();
        psxmt.close();
           cq.setAutoCommit(true);
           quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
return data1;
        
  
  }
  
  public Map<Integer, List<Object>>  loanSavingsReport(int startTime,int endTime){
  
  Map<Integer, List<Object>> data1=null;List<Object> data2=null,data3=null; Integer n=0,t=1;
  
  Double TotalSavings=0.0,TotalLoan=0.0,TotalAmount=0.0;String savings="",loanAmount="",TotalAmountR="";

data1= new HashMap<>();
      try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
 
          String query = "SELECT account_number, account_name,savings,loan_amount,last_date FROM loan_savings_shares WHERE  MonthYearNumber>="+startTime+" AND MonthYearNumber<="+endTime;
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
           savings=rsxmt.getString("savings");
             loanAmount   =rsxmt.getString("loan_amount");  
             TotalAmountR=(parseDouble(savings)+parseDouble(loanAmount))+"";
        data2=new ArrayList<>();
        
        data2.add(0, t);
        
         data2.add(1, rsxmt.getString("account_number"));
         
         
          data2.add(2, rsxmt.getString("account_name"));
          
          data2.add(3,  parseDouble(savings));
          
          data2 .add(4, parseDouble(loanAmount));
          
          data2.add(5,parseDouble(TotalAmountR));
          
         data2 .add(6, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("last_date")));
       

         data1.put(n, data2);
         
          TotalSavings=TotalSavings+parseDouble(savings);
          
         TotalLoan= TotalLoan+parseDouble(loanAmount);
         
         TotalAmount=TotalAmount+parseDouble(TotalAmountR);
         
         n++;t++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
             quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
    data3=new ArrayList<>(); 
    data3.add(0, "Total");
     data3.add(1, "-");
       data3.add(2, "-");
       data3.add(3, TotalSavings);
       data3.add(4, TotalLoan);
       data3.add(5, TotalAmount);
       data3.add(6, "-");
        data1.put(n, data3);
return data1;
    
 
  }
  public List loanPaymentReport(int startTime,int endTime){
  
  List data1=null;List data2=null,data3=null; Integer n=0,t=1;
  
  Double TotalSavings=0.0,TotalLoan=0.0,TotalAmount=0.0;String savings="",loanAmount="",TotalAmountR="";

data1= new ArrayList<>();
      try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
 
          String query = "SELECT account_number, account_name,loan_amount,last_date FROM loan_savings_shares WHERE  MonthYearNumber>="+startTime+" AND MonthYearNumber<="+endTime;
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
             loanAmount   =rsxmt.getString("loan_amount");  
             TotalAmountR=loanAmount;
        data2=new ArrayList<>();
        
        data2.add(t);
        
         data2.add(rsxmt.getString("account_number"));
         
         
          data2.add(rsxmt.getString("account_name"));
          
          
          data2 .add(parseDouble(loanAmount));
          
          data2.add(parseDouble(TotalAmountR));
          
         data2 .add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("last_date")));
       

         data1.add(data2);
         
          
         TotalLoan= TotalLoan+parseDouble(loanAmount);
         
         TotalAmount=TotalAmount+parseDouble(TotalAmountR);
         
         n++;t++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
             quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
    data3=new ArrayList<>(); 
    data3.add("Total");
     data3.add("-");
       data3.add( "-");
       data3.add(TotalLoan);
       data3.add(TotalAmount);
       data3.add("-");
        data1.add(data3);
return data1;
    
 
  }
  
   public List savingsPaymentReport(int startTime,int endTime){
  
  List data1=null;List data2=null,data3=null; Integer n=0,t=1;
  
  Double TotalSavings=0.0,TotalLoan=0.0,TotalAmount=0.0;String savings="",loanAmount="",TotalAmountR="";

data1= new ArrayList<>();
      try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
 
          String query = "SELECT account_number, account_name,savings,last_date FROM loan_savings_shares WHERE  ((MonthYearNumber>="+startTime+" AND MonthYearNumber<="+endTime+") AND account_number LIKE '%10')";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
             loanAmount   =rsxmt.getString("savings");  
             TotalAmountR=loanAmount;
        data2=new ArrayList<>();
        
        data2.add(t);
        
         data2.add(rsxmt.getString("account_number"));
         
         
          data2.add(rsxmt.getString("account_name"));
          
          
          data2 .add(parseDouble(loanAmount));
          
          data2.add(parseDouble(TotalAmountR));
          
         data2 .add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("last_date")));
       

         data1.add(data2);
         
          
         TotalLoan= TotalLoan+parseDouble(loanAmount);
         
         TotalAmount=TotalAmount+parseDouble(TotalAmountR);
         
         n++;t++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
             quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
    data3=new ArrayList<>(); 
    data3.add("Total");
     data3.add("-");
       data3.add( "-");
       data3.add(TotalLoan);
       data3.add(TotalAmount);
       data3.add("-");
        data1.add(data3);
return data1;
    
 
  }
  
  public String customRef(String accountNumber){
  String SelectedDate="";
       try {
            Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);

          String query = "SELECT intro_first_name FROM master where account_number="+accountNumber;
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
       
        
      SelectedDate =rsxmt.getString("intro_first_name");
        
         
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
           quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
       
       if(SelectedDate.isEmpty()){
       SelectedDate="-";
       }
return SelectedDate;

  }
  
  
  public Map<Integer, List<Object>> logIn(String startDate,String endDate){
  
  Map<Integer, List<Object>> data1=null;List<Object> data2=null; Integer n=0;

data1= new HashMap<>();
      try {
            Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);

          String query = "SELECT user_id, log_date,log_time FROM"+" "+"logged_in WHERE(log_date>="+"'"+startDate+"'"+"AND log_date<="+"'"+endDate+"'"+")";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
        
        data2.add(0, rsxmt.getString("user_id"));
        
         data2.add(1, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("log_date")));
         
          data2.add(2, rsxmt.getString("log_time"));
 
         data1.put(n, data2);
         n++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
           quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
return data1;
        
  
  }
  
  
  public Map<Integer, List<Object>> customerDemog(){
  
  Map<Integer, List<Object>> data1=null;List<Object> data2=null; Integer n=0;

data1= new HashMap<>();
      try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
 
          String query = "SELECT title,first_name,sir_name,sex,birth_date,marital_status,highest_educ_level FROM"+" "+"master";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
        
        data2.add(0, rsxmt.getString("title"));
        
         data2.add(1, rsxmt.getString("first_name"));
         
          data2.add(2, rsxmt.getString("sir_name"));
          
          data2 .add(3, rsxmt.getString("sex"));
          
          data2.add(4,fmt.ageCalYears(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("birth_date"))));
          
         data2 .add(5, rsxmt.getString("marital_status"));
         data2 .add(6, rsxmt.getString("highest_educ_level"));
         data1.put(n, data2);
         n++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
             quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
return data1;
        
  
  }
  
 public List customerAccounts(){
 
List data2=new ArrayList<>();int k=0;

      try {
            Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);

          String query = "select account_number from account_created_store where account_l5='Customer Deposits'";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
        data2.add(rsxmt.getString("account_number"));

          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
           quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      

return data2;
        
 
 
 
 }
  
  
 
  public List boardMembers(){
 
List data2=new ArrayList<>();int k=0;

      try {
            Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);

          String query = "select account_number from specialgroups";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
        data2.add(rsxmt.getString("account_number"));

          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
           quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      

return data2;
        
 
 
 
 }
  
   public Map<Integer, List<Object>> customerLoanSave(){
  
   Map<Integer, List<Object>> data1= new HashMap<>();List<Object> data2=null; Integer n=0,k=0;double totalP=0.0;
   
List accountNumbers=customerAccounts();

for(int u=0;u<accountNumbers.size();u++){
  n=n+1;
  data2=new ArrayList(); 
  
  
  
  
  
  data2.add(0, n);
  data2.add(1, getStaffid(accountNumbers.get(u).toString()));
  
  data2.add(2, AccountName(accountNumbers.get(u).toString()));
   data2.add(3, dbq.lastSavings(accountNumbers.get(u).toString()));
    data2.add(4, nextInstalment(accountNumbers.get(u).toString()));
     data2.add(5, parseDouble(nextInstalment(accountNumbers.get(u).toString()))+parseDouble(dbq.lastSavings(accountNumbers.get(u).toString())));
      data2.add(6, startingDate(accountNumbers.get(u).toString()));
      data1.put(k, data2);
       k++;
}
//   List data3=new ArrayList<>();    
//      data3.add(0, "Total Value");
//        data3.add(1, "-");
//        data3.add(2, totalP);
//    data1.put(k, data3);  

return data1;
        
  
  }
   
   String getStaffid(String accountNumber){
   
   String id="";
  

      try {
            Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);

          String query = " select intro_first_name from master where account_number="+"'"+accountNumber+"'";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
       id= rsxmt.getString("intro_first_name");

          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
           quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      

return id;
 
   }
   
   
   
   
 String  startingDate(String accountNumber){
 
 
 
 
  String dated="";
  

      try {
            Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);

          String query = " select trn_date from master where account_number="+"'"+accountNumber+"'";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
       dated= rsxmt.getString("trn_date");

          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
           quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      

return dated;
 
 }
   
   
   String nextInstalment(String accountNumber){
   
   String lastInstal="";
   
   
  
  

      try {
            Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);

          String query = " select instalment_amount FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND not instalment_status="+"'"+"P"+"'";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          if (rsxmt.first()){
       lastInstal= rsxmt.getString("instalment_amount");

          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
           loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }

   
   
   
   return lastInstal;
   }
   
  public Map<Integer, List<Object>> custBalance(){
  
  Map<Integer, List<Object>> data1=null;List<Object> data2=null; Integer n=0;

data1= new HashMap<>();
      try {
            Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);

          String query = "SELECT account_name,account_number,running_balance FROM account_created_store WHERE(account_master>"+"'"+"5020000"+"'"+"AND account_master<"+"'"+"5029999"+"'"+")";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
        
        data2.add(0, rsxmt.getString("account_name"));
        
         data2.add(1, rsxmt.getString("account_number"));
         
          data2.add(2, parseDouble(df2.format(rsxmt.getDouble("running_balance"))));
 
         data1.put(n, data2);
         n++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
           quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
return data1;
        
  
  } 
  
   public Map<Integer, List<Object>> customerContacts(){
  
  Map<Integer, List<Object>> data1=null;List<Object> data2=null; Integer n=0;

data1= new HashMap<>();
      try {
            Connection cq=quaryObj.createConnection();
         cq.setAutoCommit(false);
 

          String query = "SELECT first_name,sir_name,mobile1,email,kin_sir_name,kin_first_name,kin_mobile_1,kin_email FROM"+" "+"master";
          PreparedStatement psxmt =cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
          
        data2=new ArrayList<>();
       
        data2.add(0, rsxmt.getString("sir_name")+" "+rsxmt.getString("first_name"));
        
         data2.add(1,rsxmt.getString("mobile1"));
         
          data2.add(2,rsxmt.getString("email"));
          
          data2 .add(3, rsxmt.getString("kin_sir_name")+" "+rsxmt.getString("kin_first_name"));
    
         data2 .add(4, rsxmt.getString("kin_mobile_1"));
        
         data1.put(n, data2);
         n++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
           quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
return data1;
        
  
  }
  
  
  
  	
  
   public Map<Integer, List<Object>> assetAccounts(){
  
  Map<Integer, List<Object>> data1=null;List<Object> data2=null; Integer n=0;

data1= new HashMap<>();
      try {
             Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);

          String query = "SELECT account_name,account_number,running_balance,account_status,account_l4,account_l5 from account_created_store WHERE(account_master>"+"'"+"1000000"+"'"+"AND account_master<"+"'"+"1309999"+"'"+")";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
        
        data2.add(0, rsxmt.getString("account_name"));
        
         data2.add(1, rsxmt.getString("account_number"));
         
          data2.add(2, parseDouble(df2.format(rsxmt.getDouble("running_balance"))));
          data2.add(3, rsxmt.getString("account_status"));
        
         data2.add(4, rsxmt.getString("account_l4"));
          data2.add(5, rsxmt.getString("account_l5"));
          
          
          
 
         data1.put(n, data2);
         n++;
          }
        rsxmt.close();
        psxmt.close();
          cq.setAutoCommit(true);
             quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
return data1;
        
  
  } 
  
  public Map<Integer, List<Object>> liabilitiesAccounts(){
  
  Map<Integer, List<Object>> data1=null;List<Object> data2=null; Integer n=0;

data1= new HashMap<>();	
      try {
               Connection cq=quaryObj.createConnection();
         cq.setAutoCommit(false);

          String query = "SELECT account_name,account_number,running_balance,account_status,account_l4,account_l5 from account_created_store WHERE(account_master>"+"'"+"5000000"+"'"+"AND account_master<"+"'"+"5219999"+"'"+")";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
        
        data2.add(0, rsxmt.getString("account_name"));
        
         data2.add(1, rsxmt.getString("account_number"));
         
          data2.add(2, parseDouble(df2.format(rsxmt.getDouble("running_balance"))));
          data2.add(3, rsxmt.getString("account_status"));
        
         data2.add(4, rsxmt.getString("account_l4"));
          data2.add(5, rsxmt.getString("account_l5"));
          
          
          
 
         data1.put(n, data2);
         n++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
         quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
return data1;
        
  
  }  
  
   public Map<Integer, List<Object>> equityAccounts(){
  
  Map<Integer, List<Object>> data1=null;List<Object> data2=null; Integer n=0;
data1= new HashMap<>();	
      try {
           Connection cq=quaryObj.createConnection();
         cq.setAutoCommit(false);

          String query = "SELECT account_name,account_number,running_balance,account_status,account_l4,account_l5 from account_created_store WHERE(account_master>"+"'"+"4000000"+"'"+"AND account_master<"+"'"+"4109999"+"'"+")";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
        
        data2.add(0, rsxmt.getString("account_name"));
        
         data2.add(1, rsxmt.getString("account_number"));
         
          data2.add(2, parseDouble(df2.format(rsxmt.getDouble("running_balance"))));
          data2.add(3, rsxmt.getString("account_status"));
        
         data2.add(4, rsxmt.getString("account_l4"));
          data2.add(5, rsxmt.getString("account_l5"));
       
         data1.put(n, data2);
         n++;
          }
        rsxmt.close();
        psxmt.close();
          cq.setAutoCommit(true);
          quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
return data1;
        
  
  } 
  
  
  public Map<Integer, List<Object>> revenueAccounts(){
  
  Map<Integer, List<Object>> data1=null;List<Object> data2=null; Integer n=0;

data1= new HashMap<>();		
      try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);

          String query = "SELECT account_name,account_number,running_balance,account_status,account_l4,account_l5 from account_created_store WHERE(account_master>"+"'"+"3000000"+"'"+"AND account_master<"+"'"+"3239999"+"'"+")";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
        
        data2.add(0, rsxmt.getString("account_name"));
        
         data2.add(1, rsxmt.getString("account_number"));
         
          data2.add(2, parseDouble(df2.format(rsxmt.getDouble("running_balance"))));
          data2.add(3, rsxmt.getString("account_status"));
        
         data2.add(4, rsxmt.getString("account_l4"));
          data2.add(5, rsxmt.getString("account_l5"));
          
          
          
 
         data1.put(n, data2);
         n++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
          quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
return data1;
        
  
  } 
  
    public Map<Integer, List<Object>> expenseAccounts(){
  
  Map<Integer, List<Object>> data1=null;List<Object> data2=null; Integer n=0;

data1= new HashMap<>();			
      try {
           Connection cq=quaryObj.createConnection();
         cq.setAutoCommit(false);

          String query = "SELECT account_name,account_number,running_balance,account_status,account_l4,account_l5 from account_created_store WHERE(account_master>"+"'"+"2000000"+"'"+"AND account_master<"+"'"+"2769999"+"'"+")";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
        
        data2.add(0, rsxmt.getString("account_name"));
        
         data2.add(1, rsxmt.getString("account_number"));
         
          data2.add(2, parseDouble(df2.format(rsxmt.getDouble("running_balance"))));
          data2.add(3, rsxmt.getString("account_status"));
        
         data2.add(4, rsxmt.getString("account_l4"));
          data2.add(5, rsxmt.getString("account_l5"));
          
          
          
 
         data1.put(n, data2);
         n++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
          quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
return data1;
        
  
  }
  
    
             public Map<Integer, List<Object>> custShares(){
  
  Map<Integer, List<Object>> data1=null;List<Object> data2=null; Integer n=0;

data1= new HashMap<>();
      try {
          Connection cq=quaryObj.createConnection();
         cq.setAutoCommit(false);
 
          String query = "SELECT first_name,sir_name,value_of_shares,number_of_shares FROM"+" "+"master";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
        
        data2.add(0,  rsxmt.getString("sir_name")+" "+rsxmt.getString("first_name"));
        
         data2.add(1,  rsxmt.getString("number_of_shares"));
         
          data2.add(2, rsxmt.getString("value_of_shares"));
          
         data1.put(n, data2);
         n++;
          }
        rsxmt.close();
        psxmt.close();
          cq.setAutoCommit(true);
         quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
return data1;
        
  
  }
  
  
    public Map<Integer, List<Object>> overDrawnAccounts(){
  
  Map<Integer, List<Object>> data1=null;List<Object> data2=null; Integer n=0;

data1= new HashMap<>();			
      try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);

          String query = "SELECT account_name,account_number,running_balance,account_status,account_l4,account_l5 from account_created_store WHERE(running_balance<"+"'"+"0"+"'"+")";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
        
        data2.add(0, rsxmt.getString("account_name"));
        
         data2.add(1, rsxmt.getString("account_number"));
         
          data2.add(2, parseDouble(df2.format(rsxmt.getDouble("running_balance"))));
          
          data2.add(3, rsxmt.getString("account_status"));
        
         data2.add(4, rsxmt.getString("account_l4"));
         
          data2.add(5, rsxmt.getString("account_l5"));
          
          
          
 
         data1.put(n, data2);
         n++;
          }
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
          quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
return data1;
        
  
  }
  
   public Map<Integer, List<Object>> trialBalanceItems(String startDate,String EndDate,Component c){
       
   String accountName="",accountType="";
   
  Map<Integer, List<Object>> data1=null;List<Object> data2=null; Integer n=0;

  
  List theAccountTypse=getTheUniqueAccountTypes();   
  
data1= new HashMap<>();	



for(Object accountL:theAccountTypse){

      try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);

         String query = "SELECT DISTINCT account_l1,account_l2,account_l5 FROM account_created_store WHERE account_l2="+"'"+accountL.toString()+"'";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
        
         
        accountName=rsxmt.getString("account_l5");
        accountType=rsxmt.getString("account_l1");
       
        if(accountName.equalsIgnoreCase("Accumulated Depreciation/Amortization")){
        
        accountType="Credits";
        }
        
        data2.add(0, accountName);
        data2.add(1, rsxmt.getString("account_l2"));
        data2.add(2,accountBalanceAccounti(startDate,EndDate,rsxmt.getString("account_l5"),c));
        data2.add(3,accountType);
       
         data1.put(n, data2);
         
         n++;
         
          }
          
        rsxmt.close();
        psxmt.close();
          cq.setAutoCommit(true);
          quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      
   }
   return data1;
   }
   public List getTheUniqueAccountTypes(){
   List thedata2=new ArrayList<>();
       
       try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);

         String query = "SELECT DISTINCT account_l2 FROM account_created_store";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
             
        thedata2.add(rsxmt.getString("account_l2"));
       
          }
          
        rsxmt.close();
        psxmt.close();
          cq.setAutoCommit(true);
          quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
   return thedata2;
   
   }
   
   
   
   
   public Map<Integer, List<Object>> trialBalanceItemsv(Component c){
  String accountName="",accountType="";
  Map<Integer, List<Object>> data1=null;List<Object> data2=null; Integer n=0;

data1= new HashMap<>();	

      try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);

         String query = "SELECT DISTINCT account_l1,account_l5 FROM account_created_store";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
        
        accountName=rsxmt.getString("account_l5");
        accountType=rsxmt.getString("account_l1");
       
        if(accountName.equalsIgnoreCase("Accumulated Depreciation/Amortization")){
        
        accountType="Credits";
        }
        data2.add(0, accountName);
        data2.add(1,accountBalanceAccounti(this.getDBCurrentDate(),this.getDBCurrentDate(),rsxmt.getString("account_l5"),c));
        data2.add(2,accountType);
         data1.put(n, data2);
         n++;
          }
          
        rsxmt.close();
        psxmt.close();
          cq.setAutoCommit(true);
          quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
   return data1;
   }
   
   
   private Double accountBalanceAccounti(String startDate,String endDate,String leve5Item,Component c){
       
       
//       JOptionPane.showMessageDialog(c, leve5Item);
       
   String accountBalance="";
   
   if(isBalanceSheetItem(leve5Item,c)){
   
   accountBalance=getBalanceSheetBalances(leve5Item,endDate,c);
   
   }else if(isPandLItem(leve5Item,c)){
   
   
   accountBalance=getPandLBalances(startDate,endDate,leve5Item,c);
  
   }
   
   

   
   return parseDouble(accountBalance);
   }
   
 
   private boolean isBalanceSheetItem(String Level5Items,Component c){
   String masterAccount="",master=""; boolean theItem=false;

   Level5Items=Level5Items.replace(" ", "&"); 
    Level5Items=Level5Items.replace("/", "&");
    Level5Items=Level5Items.replace("-", "&");
   Level5Items=Level5Items.replace("\\", "&");
   Level5Items=Level5Items.replace("(", "&");
    Level5Items=Level5Items.replace(")", "&");
    Level5Items=Level5Items.replace("&&&&", "&");  
     Level5Items=Level5Items.replace("&&&", "&"); 
     Level5Items=Level5Items.replace("&&", "&");
    Level5Items=Level5Items.replace("&", "_");
//      JOptionPane.showMessageDialog(c, Level5Items);
    masterAccount=masterAccountUnique(Level5Items,c);
//  JOptionPane.showMessageDialog(c, masterAccount);
//        fios.stringFileWriter(fios.createFileName("test", "testit", masterAccount+"trdyr.txt"),Level5Items); 
    master=fmt.getMasterAccountCode(fmt.putSeparatorsOnNormaAccount(masterAccount));
  if((parseInt(master)>=5000000&&parseInt(master)<=5999999)||(parseInt(master)>=1000000&&parseInt(master)<=1999999)||(parseInt(master)>=4000000&&parseInt(master)<=4999999)){
  theItem=true;
  }
return theItem; 
}
   
   private boolean isPandLItem(String Level5Items,Component c){
   String masterAccount="",master=""; boolean theItem=false;

   Level5Items=Level5Items.replace(" ", "&"); 
    Level5Items=Level5Items.replace("/", "&");
    Level5Items=Level5Items.replace("-", "&");
   Level5Items=Level5Items.replace("\\", "&");
   Level5Items=Level5Items.replace("(", "&");
    Level5Items=Level5Items.replace(")", "&");
    Level5Items=Level5Items.replace("&&&&", "&");  
     Level5Items=Level5Items.replace("&&&", "&"); 
     Level5Items=Level5Items.replace("&&", "&");
    Level5Items=Level5Items.replace("&", "_");
    
    masterAccount=masterAccountUnique(Level5Items,c);
    master=fmt.getMasterAccountCode(fmt.putSeparatorsOnNormaAccount(masterAccount));
  if((parseInt(master)>=3000000&&parseInt(master)<=3999999)||(parseInt(master)>=2000000&&parseInt(master)<=2999999)){
  theItem=true;
  }
return theItem; 
}
//  public   String accountBalanceAccount(String Level5Items){
//     
//    String balance=""; 
//  level2m= new HashMap<>() ;
//   Level5Items=Level5Items.replace(" ", "&"); 
//    Level5Items=Level5Items.replace("/", "&");
//    Level5Items=Level5Items.replace("-", "&");
//   Level5Items=Level5Items.replace("\\", "&");
//   Level5Items=Level5Items.replace("(", "&");
//    Level5Items=Level5Items.replace(")", "&");
//    Level5Items=Level5Items.replace("&&&&", "&");  
//     Level5Items=Level5Items.replace("&&&", "&"); 
//     Level5Items=Level5Items.replace("&&", "&");
//    Level5Items=Level5Items.replace("&", "_");
//  try {
//          Connection cq=quaryObj.createConnection();   
//      cq.setAutoCommit(false);
//            
//            String query = "SELECT running_balance FROM account_master_store WHERE account_name="+"'"+Level5Items+"'";
//            PreparedStatement psxmt = cq.prepareStatement(query);
//            ResultSet rsxmt = psxmt.executeQuery();
//            while (rsxmt.next()){
//                
//           balance=df2.format(rsxmt.getDouble("running_balance"));     
//            }
//            rsxmt.close();
//            psxmt.close();
//             cq.setAutoCommit(true);
//            quaryObj.closeConnection(cq);
//        } catch (SQLException ex) {
//          
//            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
//      
//        }
// 
//
// 
//return balance;
//
// }  
  
  
   public Map<Integer, Object> loanTopItems(String accountNumber){
  
  Map<Integer, Object> data1=null;

data1= new HashMap<>();	

      try {
            Connection cq=loancon.createConnection();  
         cq.setAutoCommit(false);

         String query = "SELECT total_instalments,total_loanAmount,instalment_start_date,princimpal_amount,instalment_next_due_date,total_interest,instalment_end_date,instalment_amount,remaining_instalments,balance_due,TotalLoanPenaltyRemaining FROM new_loan_appstore WHERE applicant_account_number="+accountNumber;
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
         
         if (rsxmt.last()){
            
                data1.put(0, rsxmt.getObject("total_instalments"));   
              
                data1.put(1, parseDouble(df2.format(rsxmt.getDouble("total_loanAmount")+rsxmt.getDouble("TotalLoanPenaltyRemaining"))));    
              
                data1.put(2, rsxmt.getObject("instalment_start_date"));
             
                data1.put(3, parseDouble(df2.format(rsxmt.getDouble("princimpal_amount")))); 
               
                data1.put(4, rsxmt.getObject("instalment_next_due_date"));  
               
                data1.put(5, parseDouble(df2.format(rsxmt.getDouble("total_interest"))));  
                
                data1.put(6, rsxmt.getObject("instalment_end_date"));  
               
                data1.put(7, parseDouble(df2.format(rsxmt.getDouble("instalment_amount"))));  
               
                data1.put(8, rsxmt.getObject("remaining_instalments")); 
               
                data1.put(9, parseDouble(df2.format(rsxmt.getDouble("balance_due"))));
               
       
                data1.put(10, parseDouble(df2.format(rsxmt.getDouble("TotalLoanPenaltyRemaining"))));
   
          }
          
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
          loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
   return data1;
   }
    public Map<Integer, Object> loanTopItemsclosed(String loanid){
  
  Map<Integer, Object> data1=null;

data1= new HashMap<>();	

      try {
            Connection cq=loancon.createConnection();  
         cq.setAutoCommit(false);

         String query = "SELECT total_instalments,total_loanAmount,instalment_start_date,princimpal_amount,instalment_next_due_date,total_interest,instalment_end_date,instalment_amount,remaining_instalments,balance_due,TotalLoanPenaltyPaid FROM new_loan_appstore WHERE loan_id="+"'"+loanid+"'";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
         
         if (rsxmt.last()){
            
                data1.put(0, rsxmt.getObject("total_instalments"));   
              
                data1.put(1, parseDouble(df2.format(rsxmt.getDouble("total_loanAmount")+rsxmt.getDouble("TotalLoanPenaltyPaid"))));    
              
                data1.put(2, rsxmt.getObject("instalment_start_date"));
             
                data1.put(3, parseDouble(df2.format(rsxmt.getDouble("princimpal_amount")))); 
               
                data1.put(4, rsxmt.getObject("instalment_next_due_date"));  
               
                data1.put(5, parseDouble(df2.format(rsxmt.getDouble("total_interest"))));  
                
                data1.put(6, rsxmt.getObject("instalment_end_date"));  
               
                data1.put(7, parseDouble(df2.format(rsxmt.getDouble("instalment_amount"))));  
               
                data1.put(8, rsxmt.getObject("remaining_instalments")); 
               
                data1.put(9, parseDouble(df2.format(rsxmt.getDouble("balance_due"))));
               
         data1.put(10, parseDouble(df2.format(rsxmt.getDouble("TotalLoanPenaltyPaid"))));
              
   
          }
          
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
          loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
   return data1;
   }
  public Map<Integer, List<Object>> loanEntries(String accountNumber){
      
Map<Integer, List<Object>> data1=null;List<Object> data2=null; Integer n=0;

Double totalInstalmentsAmount=0.0,totalInstalmentAmountRemain=0.0,totalInstalmentAmountPaid=0.0;List finale=null;

data1= new HashMap<>();

      try {
          Connection cq=loancon.createConnection(); 
         cq.setAutoCommit(false);
     
          String query = "SELECT instalment_no,instalment_amount,InstalmentRemaining,instalment_paid,closing_bal,instalment_due_date,instalment_status,instalment_paid_date,LoanPenalty,LoanPenaltyRemaining FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'";

          PreparedStatement psxmt =  cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
        
        data2.add(0, rsxmt.getString("instalment_no"));
        
         data2.add(1, rsxmt.getDouble("instalment_amount")+rsxmt.getDouble("LoanPenalty"));
         
          data2.add(2, rsxmt.getDouble("InstalmentRemaining"));
          
          data2 .add(3, rsxmt.getDouble("instalment_paid"));

         data2 .add(4, rsxmt.getString("instalment_status"));
         
        data2.add(5, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("instalment_due_date")));
        
        data2.add(6, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("instalment_paid_date")));
         
         data1.put(n, data2);
         
         totalInstalmentsAmount+=parseDouble(data2.get(1).toString());
totalInstalmentAmountRemain+=parseDouble(data2.get(2).toString());
totalInstalmentAmountPaid+=parseDouble(data2.get(3).toString());
         n++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
          loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
   finale=new ArrayList();
  finale.add(0,"Total");
    finale.add(1,totalInstalmentsAmount+"");
    finale.add(2,totalInstalmentAmountRemain+"");
      finale.add(3,totalInstalmentAmountPaid+"");
      finale.add(4,"-");
 finale.add(5,"-");
 finale.add(6,"-");
data1.put(n,finale);  
    
return data1;
        
        
        
        }
  
   public Map<Integer, List<Object>> loanEntriesclosed(String loanid){
Map<Integer, List<Object>> data1=null;List<Object> data2=null; Integer n=0;

data1= new HashMap<>();
      try {
          Connection cq=loancon.createConnection(); 
         cq.setAutoCommit(false);
     
          String query = "SELECT instalment_amount,princimpal_amount,interest_amount,closing_bal,instalment_due_date,instalment_status,instalment_paid_date,instalment_paid_variance FROM"+" "+loanid;

          PreparedStatement psxmt =  cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
        
        data2.add(0, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("instalment_due_date")));
        
         data2.add(1, rsxmt.getString("instalment_amount"));
         
          data2.add(2, rsxmt.getString("princimpal_amount"));
          
          data2 .add(3, rsxmt.getString("interest_amount"));
          
          data2.add(4, rsxmt.getString("closing_bal"));
          
         data2 .add(5, rsxmt.getString("instalment_status"));
          data2.add(6, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("instalment_paid_date")));
          data2.add(7, rsxmt.getString("instalment_paid_variance"));
         data1.put(n, data2);
         n++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
          loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
return data1;
        
        
        
        }
  
  
  
  public Map<Integer, List<Object>> bogaEntries(){
Map<Integer, List<Object>> data1=null;List<Object> data2=null; Integer n=0;

 
data1= new HashMap<>();
      try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
     
          String query = "SELECT s_id,account_name,number_shares,value_shares,borrow_qalified,guarantee_qalified,borrowing_status FROM borrow_guarantee";
            
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
        
        data2.add(0, rsxmt.getString("s_id"));
        
         data2.add(1, rsxmt.getString("account_name"));
     
          data2 .add(2, rsxmt.getString("number_shares"));
          
          data2.add(3, rsxmt.getString("value_shares"));
          
         data2 .add(4, rsxmt.getString("borrow_qalified"));
         
         data2 .add(5, rsxmt.getString("guarantee_qalified"));
         
         data2 .add(6, rsxmt.getString("borrowing_status"));
         
         data1.put(n, data2);
         n++;
          }
        rsxmt.close();
        psxmt.close();
          cq.setAutoCommit(true);
          quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
return data1;
        
        
        
        }
  
  
  
  public Map<Integer, List<Object>> loanAging(String thisAgingId,Component c){
      
Map<Integer, List<Object>> data1=null;

List<Object> data2=null; 

Integer n=0;

 
data1= new HashMap<>();

      try {
          
           Connection cq=loancon.createConnection();
           
          cq.setAutoCommit(false);

   String query ="CALL agingAnalysisStaff("+thisAgingId+")";
                                           
//     JOptionPane.showMessageDialog(c, query);    
     
          PreparedStatement psxmt = cq.prepareStatement(query);
          
          ResultSet rsxmt = psxmt.executeQuery();
          
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
        
        data2.add(0, (parseInt(rsxmt.getString("id_7"))-1));
//        JOptionPane.showMessageDialog(c, (parseInt(rsxmt.getString("id_7"))-1));
         data2.add(1, rsxmt.getString("customer_name"));
//      JOptionPane.showMessageDialog(c, rsxmt.getString("customer_name"));
          data2 .add(2, rsxmt.getString("customer_contact"));
//             JOptionPane.showMessageDialog(c, rsxmt.getString("customer_contact"));
               data2 .add(3, rsxmt.getString("date_taken"));
//                  JOptionPane.showMessageDialog(c, rsxmt.getString("date_taken"));
                    data2 .add(4, rsxmt.getString("due_date"));
//           JOptionPane.showMessageDialog(c, rsxmt.getString("due_date"));
          data2.add(5, rsxmt.getString("loans_remaining"));
          
         data2 .add(6, rsxmt.getString("principal_remaining"));
         
         data2 .add(7, rsxmt.getString("interest_remaining"));
         
         data2 .add(8, rsxmt.getString("principal_inarrears"));
         
               data2.add(9, rsxmt.getString("interest_inarrears"));
          
         data2 .add(10, rsxmt.getString("number_of_days_in_arrears")+" DAYS");
         
         
         data1.put(n, data2);
         n++;
          }
        rsxmt.close();
        psxmt.close();
          cq.setAutoCommit(true);
          loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
return data1;
        
        
        
        }
  
     public Map<Integer, List<Object>> loanAgingG(String thisAgingId,Component c){
      
Map<Integer, List<Object>> data1=null;

List<Object> data2=null; 

Integer n=0;

 
data1= new HashMap<>();

      try {
          
           Connection cq=loancon.createConnection();
           
          cq.setAutoCommit(false);

   String query ="CALL agingAnalysisStaffG("+thisAgingId+")";
                                           
//     JOptionPane.showMessageDialog(c, query);    
     
          PreparedStatement psxmt = cq.prepareStatement(query);
          
          ResultSet rsxmt = psxmt.executeQuery();
          
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
        
         data2.add(0, (parseInt(rsxmt.getString("id_7"))-1));
//        JOptionPane.showMessageDialog(c, (parseInt(rsxmt.getString("id_7"))-1));
         data2.add(1, rsxmt.getString("customer_name"));
//      JOptionPane.showMessageDialog(c, rsxmt.getString("customer_name"));
          data2 .add(2, rsxmt.getString("customer_contact"));
          
             data2.add(3, rsxmt.getString("gaurantor1_name"));
//      JOptionPane.showMessageDialog(c, rsxmt.getString("customer_name"));
          data2 .add(4, rsxmt.getString("gaurantor1_contact"));
          
              data2.add(5, rsxmt.getString("gaurantor2_name"));
//      JOptionPane.showMessageDialog(c, rsxmt.getString("customer_name"));
          data2 .add(6, rsxmt.getString("gaurantor2_contact"));
          
//             JOptionPane.showMessageDialog(c, rsxmt.getString("customer_contact"));
               data2 .add(7, rsxmt.getString("date_taken"));
//                  JOptionPane.showMessageDialog(c, rsxmt.getString("date_taken"));
                    data2 .add(8, rsxmt.getString("due_date"));
//           JOptionPane.showMessageDialog(c, rsxmt.getString("due_date"));
          data2.add(9, rsxmt.getString("loans_remaining"));
          
         data2 .add(10, rsxmt.getString("principal_remaining"));
         
         data2 .add(11, rsxmt.getString("interest_remaining"));
         
         data2 .add(12, rsxmt.getString("principal_inarrears"));
         
               data2.add(13, rsxmt.getString("interest_inarrears"));
          
         data2 .add(14, rsxmt.getString("number_of_days_in_arrears")+" DAYS");
         
         
         data1.put(n, data2);
         n++;
          }
        rsxmt.close();
        psxmt.close();
          cq.setAutoCommit(true);
          loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
return data1;
        
        
        
        }
       
  
  
  public Map<Integer, List<Object>> loanAging(Component c){
      
Map<Integer, List<Object>> data1=null;

List<Object> data2=null; 

Integer n=0;

 
data1= new HashMap<>();

      try {
          
           Connection cq=loancon.createConnection();
           
          cq.setAutoCommit(false);

   String query ="CALL agingAnalysis()";
                                           
//     JOptionPane.showMessageDialog(c, query);    
     
          PreparedStatement psxmt = cq.prepareStatement(query);
          
          ResultSet rsxmt = psxmt.executeQuery();
          
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
        
        data2.add(0, (parseInt(rsxmt.getString("id_7"))-1));
//        JOptionPane.showMessageDialog(c, (parseInt(rsxmt.getString("id_7"))-1));
         data2.add(1, rsxmt.getString("customer_name"));
//      JOptionPane.showMessageDialog(c, rsxmt.getString("customer_name"));
          data2 .add(2, rsxmt.getString("customer_contact"));
//             JOptionPane.showMessageDialog(c, rsxmt.getString("customer_contact"));
               data2 .add(3, rsxmt.getString("date_taken"));
//                  JOptionPane.showMessageDialog(c, rsxmt.getString("date_taken"));
                    data2 .add(4, rsxmt.getString("due_date"));
//           JOptionPane.showMessageDialog(c, rsxmt.getString("due_date"));
          data2.add(5, rsxmt.getString("loans_remaining"));
          
         data2 .add(6, rsxmt.getString("principal_remaining"));
         
         data2 .add(7, rsxmt.getString("interest_remaining"));
         
         data2 .add(8, rsxmt.getString("principal_inarrears"));
         
               data2.add(9, rsxmt.getString("interest_inarrears"));
          
         data2 .add(10, rsxmt.getString("number_of_days_in_arrears")+" DAYS");
         
         
         data1.put(n, data2);
         n++;
          }
        rsxmt.close();
        psxmt.close();
          cq.setAutoCommit(true);
          loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
return data1;
        
        
        
        }
  
  
  
  
  public Map<Integer, List<Object>> loanAgingG(Component c){
      
Map<Integer, List<Object>> data1=null;

List<Object> data2=null; 

Integer n=0;

 
data1= new HashMap<>();

      try {
          
           Connection cq=loancon.createConnection();
           
          cq.setAutoCommit(false);

   String query ="CALL agingAnalysisG()";
                                           
//     JOptionPane.showMessageDialog(c, query);    
     
          PreparedStatement psxmt = cq.prepareStatement(query);
          
          ResultSet rsxmt = psxmt.executeQuery();
          
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
        
        data2.add(0, (parseInt(rsxmt.getString("id_7"))-1));
//        JOptionPane.showMessageDialog(c, (parseInt(rsxmt.getString("id_7"))-1));
         data2.add(1, rsxmt.getString("customer_name"));
//      JOptionPane.showMessageDialog(c, rsxmt.getString("customer_name"));
          data2 .add(2, rsxmt.getString("customer_contact"));
          
             data2.add(3, rsxmt.getString("gaurantor1_name"));
//      JOptionPane.showMessageDialog(c, rsxmt.getString("customer_name"));
          data2 .add(4, rsxmt.getString("gaurantor1_contact"));
          
              data2.add(5, rsxmt.getString("gaurantor2_name"));
//      JOptionPane.showMessageDialog(c, rsxmt.getString("customer_name"));
          data2 .add(6, rsxmt.getString("gaurantor2_contact"));
          
//             JOptionPane.showMessageDialog(c, rsxmt.getString("customer_contact"));
               data2 .add(7, rsxmt.getString("date_taken"));
//                  JOptionPane.showMessageDialog(c, rsxmt.getString("date_taken"));
                    data2 .add(8, rsxmt.getString("due_date"));
//           JOptionPane.showMessageDialog(c, rsxmt.getString("due_date"));
          data2.add(9, rsxmt.getString("loans_remaining"));
          
         data2 .add(10, rsxmt.getString("principal_remaining"));
         
         data2 .add(11, rsxmt.getString("interest_remaining"));
         
         data2 .add(12, rsxmt.getString("principal_inarrears"));
         
               data2.add(13, rsxmt.getString("interest_inarrears"));
          
         data2 .add(14, rsxmt.getString("number_of_days_in_arrears")+" DAYS");
         
         
         data1.put(n, data2);
         n++;
          }
        rsxmt.close();
        psxmt.close();
          cq.setAutoCommit(true);
          loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
return data1;
        
        
        
        }
  
  
  
  
  public Map<Integer, List<Object>> loanAnalysisOfficers(){
      
Map<Integer, List<Object>> data1=null;

List<Object> data2=null; 

Integer n=0;

 
data1= new HashMap<>();

      try {
          
           Connection cq=loancon.createConnection();
           
          cq.setAutoCommit(false);

   String query ="CALL officerPortfolio()";
                                           
//     JOptionPane.showMessageDialog(c, query);    
     
          PreparedStatement psxmt = cq.prepareStatement(query);
          
          ResultSet rsxmt = psxmt.executeQuery();
          
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
        
        data2.add(0, rsxmt.getString("id_2"));
        
         data2.add(1, rsxmt.getString("officer_id"));
     
          data2 .add(2, rsxmt.getString("officer_name"));
          
          data2.add(3, rsxmt.getString("number_customers"));
          
         data2 .add(4, rsxmt.getString("loan_porfolio"));
         
         data2 .add(5, rsxmt.getString("loans_paid"));
         
         data2 .add(6, rsxmt.getString("loans_remaining"));
         
               data2.add(7, rsxmt.getString("loans_0_30"));
          
         data2 .add(8, rsxmt.getString("loans_31_60"));
         
         data2 .add(9, rsxmt.getString("loans_61_90"));
         
         data2 .add(10, rsxmt.getString("loans_91_360"));
         
           data2 .add(11, rsxmt.getString("above_360"));
         
         data1.put(n, data2);
         n++;
          }
        rsxmt.close();
        psxmt.close();
          cq.setAutoCommit(true);
          loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
return data1;
        
        
        
        }
  
 public  Map<Integer, List<Object>>agingReport(){
     
  
int n=0,k=0;

Double loanAmount=0.0,totalPrincipal=0.0,totalInterest=0.0,totalAccumulatedInt=0.0,totalLoanPenalty=0.0,totalDeposit=0.0;List finale=null;

       Map<Integer, List<Object>> agingReport=new HashMap();
       
        List accountsInArrears=this.accountsInArrearsW();
    

       
       while(k<accountsInArrears.size()){
           
      try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
     
         
          
   String query = "SELECT instalment_due_date  FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountsInArrears.get(k)+"'"+"AND NOT" +"(instalment_status="+"'P')";
          
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        if(rsxmt.first()){
             List loanDetails=new ArrayList();
            n=n+1;
    loanDetails.add(n);          
loanDetails.add(this.AccountName(accountsInArrears.get(k).toString()));
loanDetails.add(firstContactNumber(accountsInArrears.get(k).toString()));
loanDetails.add(dbq.accountBalance(accountsInArrears.get(k).toString(), this.getDBCurrentDate()));
loanDetails.add(loan.remainingTotalPrincipal(accountsInArrears.get(k).toString()));
loanDetails.add(loan.remainingTotalInterest(accountsInArrears.get(k).toString()));
loanDetails.add(loan.remainingTotalAccumulatedInterest(accountsInArrears.get(k).toString()));
loanDetails.add(loan.remainingTotalLoanPenalty(accountsInArrears.get(k).toString()));
loanDetails.add(loan.remainingTotalLoanBalance(accountsInArrears.get(k).toString()));
//loanDetails.add("1 years");
loanDetails.add(Math.abs(fmt.diffDays(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("instalment_due_date")), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate())))+" "+"Days");
agingReport.put(k,  loanDetails);

totalDeposit+=parseDouble(loanDetails.get(3).toString());
totalPrincipal+=parseDouble(loanDetails.get(4).toString());
totalInterest+=parseDouble(loanDetails.get(5).toString());
totalAccumulatedInt+=parseDouble(loanDetails.get(6).toString());
totalLoanPenalty+=parseDouble(loanDetails.get(7).toString());
loanAmount+=parseDouble(loanDetails.get(8).toString());

          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
      k++;
       }
              finale=new ArrayList();
  finale.add("Total");
  finale.add("-");
   finale.add("-");
    finale.add(totalDeposit+"");
    finale.add(totalPrincipal+"");
    finale.add(totalInterest+"");
      finale.add(totalAccumulatedInt+"");
    finale.add(totalLoanPenalty+"");
      finale.add(loanAmount+"");
 finale.add("-");

agingReport.put(k,finale);  
    
return agingReport;

}

 
 public  Map<Integer, List<Object>>loanStatmentReport(String loanId){
     
  
int n=0,k=0;

       Map<Integer, List<Object>> agingReport=new HashMap();

           
      try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);


   String query = "SELECT TrnDate,BatchCode,MonthPaid,YearPaid,AmountDisbursed,ExpectedInterest,ExpectedTotalAmount,InterestRate"
           + ",AmountPaid,PrincipalPaid,InterestPaid,AccumulatedInterestPaid,LoanPenaltyPaid,PrincipalBalance,InterestBalance,"
           + "AccumulatedInterestBalance,LoanPenaltyBalance,LoanBalance FROM loandisburserepaystatement WHERE loanTrnId="+"'"+loanId+"'";
                                                   

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
       while(rsxmt.next()){
           
             List loanDetails=new ArrayList();
                 
loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("TrnDate")));
//loanDetails.add(rsxmt.getString("BatchCode"));
loanDetails.add(rsxmt.getString("MonthPaid"));
loanDetails.add(rsxmt.getString("YearPaid"));
loanDetails.add(rsxmt.getString("AmountDisbursed"));
loanDetails.add(rsxmt.getString("ExpectedInterest"));
loanDetails.add(rsxmt.getString("ExpectedTotalAmount"));
loanDetails.add(rsxmt.getString("InterestRate").replace(".0", "")+"%");
loanDetails.add(rsxmt.getString("AmountPaid"));
loanDetails.add(rsxmt.getString("PrincipalPaid"));
loanDetails.add(rsxmt.getString("InterestPaid"));
loanDetails.add(rsxmt.getString("AccumulatedInterestPaid"));
loanDetails.add(rsxmt.getString("LoanPenaltyPaid"));
//loanDetails.add(rsxmt.getString("PrincipalBalance"));
//
//loanDetails.add(rsxmt.getString("InterestBalance"));
//loanDetails.add(rsxmt.getString("AccumulatedInterestBalance"));
//loanDetails.add(rsxmt.getString("LoanPenaltyBalance"));
loanDetails.add(rsxmt.getString("LoanBalance"));

agingReport.put(k,  loanDetails);

k++;
          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }

 
    
return agingReport;

}
 
 
 public String instalmentDueDate(String aAccountNumber){
 
 String theDueDate="";
 
 if(duedateIsThere(aAccountNumber)){
 
  try {
           Connection cq=loancon.createConnection();
           
          cq.setAutoCommit(false);
     
          
          
   String query = "SELECT instalment_due_date FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+aAccountNumber+"'"+"AND  NOT" +"(instalment_status="+"'P')";
          
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        if(rsxmt.first()){
            
theDueDate=rsxmt.getString("instalment_due_date");

          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
 
 }else{
 
 theDueDate=AinstalmentDueDate(aAccountNumber);
 }
 
 return theDueDate;
 
 
 }
 
 
  public String AinstalmentDueDate(String aAccountNumber){
  
      String theDueDate="";
      
      try {
           Connection cq=loancon.createConnection();
           
          cq.setAutoCommit(false);
     
          
          
   String query = "SELECT instalment_due_date FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+aAccountNumber+"'";
          
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        if(rsxmt.first()){
            
theDueDate=rsxmt.getString("instalment_due_date");

          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
  return theDueDate;
  }
 
 
 
 public boolean duedateIsThere(String aAccountNumber){
 boolean theDueDate=false;int theDa=0;
 
 if(loan.loanExists(aAccountNumber)){
// 
//  try {
//           Connection cq=loancon.createConnection();
//           
//          cq.setAutoCommit(false);
//     
//          
//          
//   String query = "SELECT COUNT(instalment_due_date) FROM newloan"+aAccountNumber+" "+"WHERE NOT" +"(instalment_status="+"'P')";
//          
//                                                          
//          
//          PreparedStatement psxmt = cq.prepareStatement(query);
//          ResultSet rsxmt = psxmt.executeQuery();
//        if(rsxmt.first()){
//            
//theDa=rsxmt.getInt("instalment_due_date");
//
//          }
//   
//  
//        rsxmt.close();
//        psxmt.close();
//        cq.setAutoCommit(true);
//         loancon.closeConnection(cq);
//      } catch (SQLException ex) {
//          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
//      }
// if(theDa>0){
 theDueDate=true;
// }
 }
 
 return theDueDate;
 
 }
 
 
 
  public  Map<Integer, List<Object>>loanStatusReport(String sepa,Component c){
     
  
int n=1,k=0;
List finale=null;

       Map<Integer, List<Object>> agingReport=new HashMap();
       
       
//       if(sepa.equalsIgnoreCase("accumm")){
           
      try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
     
          
          
   String query = "SELECT trn_id, remaining_instalments, princimpal_amount, total_interest, total_loanAmount,"
           + "balance_due, TotalAccruedInterestPaid,TotalAccruedInterestRemaining,TotalAccumulatedInterestPaid,"
           + "TotalAccumulatedInterestRemaining,TotalInterestPaid,TotalInterestRemaining,TotalLoanPenaltyPaid,"
           + "TotalLoanPenaltyRemaining,TotalPrincipalPaid,TotalPrincipalRemaining,instalments_paid,instalment_next_due_date,trn_date,applicant_account_number,applicant_account_name FROM "
           + "new_loan_appstore WHERE  loan_cycle_status='Disbursed'";
          
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          
          ResultSet rsxmt = psxmt.executeQuery();
      while(rsxmt.next()){
         if(this.duedateIsThere(rsxmt.getString("applicant_account_number"))){   
             List loanDetails=new ArrayList();
//            n=n+1;
            
    loanDetails.add(n);
//    loanDetails.add(rsxmt.getString("trn_id"));
loanDetails.add(rsxmt.getString("applicant_account_name"));
loanDetails.add(rsxmt.getString("princimpal_amount"));
loanDetails.add(rsxmt.getString("TotalPrincipalPaid"));
loanDetails.add(rsxmt.getString("TotalPrincipalRemaining"));

loanDetails.add(rsxmt.getString("total_interest"));
loanDetails.add(rsxmt.getString("TotalInterestPaid"));
loanDetails.add(rsxmt.getString("TotalInterestRemaining"));

loanDetails.add(loan.totalAccumulatedInterest(rsxmt.getString("applicant_account_number")));
loanDetails.add(rsxmt.getString("TotalAccumulatedInterestPaid"));
loanDetails.add(rsxmt.getString("TotalAccruedInterestRemaining"));

loanDetails.add(loan.totalLoanPenalty(rsxmt.getString("applicant_account_number")));
loanDetails.add(rsxmt.getString("TotalLoanPenaltyRemaining"));
loanDetails.add(rsxmt.getString("TotalLoanPenaltyPaid"));
//loanDetails.add(rsxmt.getString("total_loanAmount"));
//loanDetails.add(rsxmt.getString("balance_due"));
//loanDetails.add(rsxmt.getString("instalments_paid"));
//  loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("trn_date"))); 
//  JOptionPane.showMessageDialog(c, rsxmt.getString("applicant_account_number")+"="+ instalmentDueDate(rsxmt.getString("applicant_account_number")));
//loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(instalmentDueDate(rsxmt.getString("applicant_account_number"))));
agingReport.put(k,  loanDetails);

n++;k++;
         }}
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
//       } else  if(sepa.equalsIgnoreCase("penal")){
//         try {
//           Connection cq=loancon.createConnection();
//          cq.setAutoCommit(false);
//     
//          
//          
//   String query = "SELECT trn_id, remaining_instalments, princimpal_amount, total_interest, total_loanAmount,"
//           + "balance_due, TotalAccruedInterestPaid,TotalAccruedInterestRemaining,TotalAccumulatedInterestPaid,"
//           + "TotalAccumulatedInterestRemaining,TotalInterestPaid,TotalInterestRemaining,TotalLoanPenaltyPaid,"
//           + "TotalLoanPenaltyRemaining,TotalPrincipalPaid,TotalPrincipalRemaining,instalments_paid,instalment_next_due_date,trn_date,applicant_account_number,applicant_account_name FROM "
//           + "new_loan_appstore WHERE  loan_cycle_status='Disbursed'";
//          
//                                                          
//          
//          PreparedStatement psxmt = cq.prepareStatement(query);
//          ResultSet rsxmt = psxmt.executeQuery();
//      while(rsxmt.next()){
//          
//             List loanDetails=new ArrayList();
//            n=n+1;
//      if(this.duedateIsThere(rsxmt.getString("applicant_account_number"))){      
//    loanDetails.add(n);
////    loanDetails.add(rsxmt.getString("trn_id"));
//loanDetails.add(rsxmt.getString("applicant_account_name"));
//loanDetails.add(rsxmt.getString("princimpal_amount"));
//loanDetails.add(rsxmt.getString("TotalPrincipalPaid"));
//loanDetails.add(rsxmt.getString("TotalPrincipalRemaining"));
//loanDetails.add(rsxmt.getString("total_interest"));
//loanDetails.add(rsxmt.getString("TotalInterestPaid"));
//loanDetails.add(rsxmt.getString("TotalInterestRemaining"));
//
////loanDetails.add(loan.totalAccumulatedInterest(rsxmt.getString("applicant_account_number")));
////loanDetails.add(rsxmt.getString("TotalAccumulatedInterestPaid"));
////loanDetails.add(rsxmt.getString("TotalAccruedInterestRemaining"));
//
//loanDetails.add(loan.totalLoanPenalty(rsxmt.getString("applicant_account_number")));
//loanDetails.add(rsxmt.getString("TotalLoanPenaltyPaid"));
//loanDetails.add(rsxmt.getString("TotalLoanPenaltyRemaining"));
//
////loanDetails.add(rsxmt.getString("total_loanAmount"));
////loanDetails.add(rsxmt.getString("balance_due"));
////loanDetails.add(rsxmt.getString("instalments_paid"));
//  loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("trn_date")));     
////    JOptionPane.showMessageDialog(c, rsxmt.getString("applicant_account_number")+"="+ instalmentDueDate(rsxmt.getString("applicant_account_number")));
//loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(instalmentDueDate(rsxmt.getString("applicant_account_number"))));
//agingReport.put(k,  loanDetails);
//
//n++;k++;
//          }}
//   
//  
//        rsxmt.close();
//        psxmt.close();
//        cq.setAutoCommit(true);
//         loancon.closeConnection(cq);
//      } catch (SQLException ex) {
//          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
//      }
//       
       
//       }  
    
return agingReport;

}

  
  public  Map<Integer, List<Object>>depreciationStore(Component c){
     
  
int n=0,k=0;
List finale=null;

       Map<Integer, List<Object>> agingReport=new HashMap();
       
       

           
      try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);

          
   String query = "SELECT TrnId, AssetAccountNumber, PurchaseDate, NextDepriDate, UsefulLife,"
           + "DepriMethod, PurchasePrice,accumDep,SalvageValue,DepreciationStatus from depreciationstore";
          
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
      while(rsxmt.next()){
          
             List depriStoreDetails=new ArrayList();
         
depriStoreDetails.add(rsxmt.getString("TrnId"));//0
depriStoreDetails.add(dbq.AccountName(rsxmt.getString("AssetAccountNumber")));//1
depriStoreDetails.add(dbq.AccountMasterName(rsxmt.getString("AssetAccountNumber")));//2
depriStoreDetails.add(rsxmt.getString("PurchaseDate"));//3
depriStoreDetails.add(rsxmt.getString("NextDepriDate"));//4
depriStoreDetails.add(rsxmt.getString("UsefulLife"));//5
depriStoreDetails.add(rsxmt.getString("DepriMethod"));//6
depriStoreDetails.add(rsxmt.getString("PurchasePrice"));//7
depriStoreDetails.add(rsxmt.getString("accumDep"));//8
depriStoreDetails.add(rsxmt.getString("SalvageValue"));//9
depriStoreDetails.add(rsxmt.getString("DepreciationStatus"));//10 

//JOptionPane.showMessageDialog(c, depriStoreDetails.get(0));
//JOptionPane.showMessageDialog(c, depriStoreDetails.get(1));
//JOptionPane.showMessageDialog(c, depriStoreDetails.get(2));
//JOptionPane.showMessageDialog(c, depriStoreDetails.get(3));
//JOptionPane.showMessageDialog(c, depriStoreDetails.get(7));
//JOptionPane.showMessageDialog(c, depriStoreDetails.get(8));
//JOptionPane.showMessageDialog(c, depriStoreDetails.get(9));
agingReport.put(k,  depriStoreDetails);
k++;
          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
       
   
   
    
return agingReport;

}
 
 public synchronized  Map<Integer, List<Object>>returnOI(String accountNumber,String startDate,String endDate){
     
  
int n=0,k=0;

double totalShars=0.0,totalRtun=0.0,totalRun=0.0;List finale=null;

       Map<Integer, List<Object>> overAllRoi=new HashMap();
       
      
  
 
      try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);

   String query = "SELECT value_date,value_month,Balance_Shares,Daily_Return,Return_Run_Bal FROM sharesreturnoninvestment WHERE  ((account_number="+accountNumber+") AND (value_date>="+"'"+startDate+"'"+"AND value_date<="+"'"+endDate+"'"+"))";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
     while(rsxmt.next()){
        
             List rioDetails=new ArrayList();
            n+=1;
    rioDetails.add(n);          
rioDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("value_date")));
rioDetails.add(rsxmt.getString("value_month"));
rioDetails.add(rsxmt.getString("Balance_Shares"));
rioDetails.add(rsxmt.getString("Daily_Return"));
rioDetails.add(rsxmt.getString("Return_Run_Bal"));
//   
overAllRoi.put(k,  rioDetails);

//totalShars+=parseDouble(rioDetails.get(3).toString());
totalRtun+=parseDouble(rioDetails.get(4).toString());
//totalRun+=parseDouble(rioDetails.get(5).toString());

  k++;
          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
      
       
       
//    y {
//        wait(2000);
//    } catch (InterruptedException ex) {
//        Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
//    }

       
              finale=new ArrayList();
  finale.add("Total");
  finale.add("-");
   finale.add("-");
    finale.add("-");
    finale.add(totalRtun+"");
      finale.add("-");

overAllRoi.put(k,finale);  
 
return overAllRoi;

}
      public synchronized  Map<Integer, List<Object>>returnOIGroup(String startDate,String endDate){
     
  
int n=0,k=0;

double totalShars=0.0,totalRtun=0.0,totalRun=0.0;List finale=null;

       Map<Integer, List<Object>> overAllRoi=new HashMap();
       
      
  
 
      try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);

   String query = "SELECT value_date,account_name,Balance_Shares,Daily_Return,Return_Run_Bal FROM sharesreturnoninvestment WHERE  (value_date>="+"'"+startDate+"'"+"AND value_date<="+"'"+endDate+"'"+")";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
     while(rsxmt.next()){
        
             List rioDetails=new ArrayList();
            n+=1;
    rioDetails.add(n);          
rioDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("value_date")));
rioDetails.add(rsxmt.getString("account_name"));
rioDetails.add(rsxmt.getString("Balance_Shares"));
rioDetails.add(rsxmt.getString("Daily_Return"));
rioDetails.add(rsxmt.getString("Return_Run_Bal"));
//   
overAllRoi.put(k,  rioDetails);

//totalShars+=parseDouble(rioDetails.get(3).toString());
totalRtun+=parseDouble(rioDetails.get(4).toString());
//totalRun+=parseDouble(rioDetails.get(5).toString());

  k++;
          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
      
       
       
//    y {
//        wait(2000);
//    } catch (InterruptedException ex) {
//        Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
//    }

       
              finale=new ArrayList();
  finale.add("Total");
  finale.add("-");
   finale.add("-");
    finale.add("-");
    finale.add(totalRtun+"");
      finale.add("-");

overAllRoi.put(k,finale);  
 
return overAllRoi;

}
      
  public synchronized  Map<Integer, List<Object>>customersByBalances(String startDate){
     
  List accoutnNumbers=this.customerAccounts();
  
int n=0,k=0;

double totalShars=0.0,totalRtun=0.0,totalRun=0.0;List finale=null;

       Map<Integer, List<Object>> overAllRoi=new HashMap();
       
      
  for(Object account:accoutnNumbers){
 
      try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);

   String query = "SELECT ledger_balance FROM bsanca"+account.toString()+" "+"WHERE  trn_date<="+"'"+startDate+"'";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
     if(rsxmt.last()){
        
             List rioDetails=new ArrayList();
            n+=1;
    rioDetails.add(n);          
rioDetails.add(this.AccountName(account.toString()));
rioDetails.add(account.toString());
rioDetails.add(rsxmt.getString("ledger_balance"));
//   
overAllRoi.put(k,  rioDetails);

//totalShars+=parseDouble(rioDetails.get(3).toString());
totalRtun+=parseDouble(rioDetails.get(3).toString());
//totalRun+=parseDouble(rioDetails.get(5).toString());

  k++;
          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
      

              finale=new ArrayList();
  finale.add("Total");
   finale.add("-");
    finale.add("-");
    finale.add(totalRtun+"");

overAllRoi.put(k,finale);  
  }
return overAllRoi;

}   
      
      
   public synchronized  Map<Integer, List<Object>>budgetEstimates(List details,int teypeNumber,Component c){
    
       int theSize=getnumberOfBudgetPeriods1();
       
//      JOptionPane.showMessageDialog(c, "another IN");
       
  int n=1;

       Map<Integer, List<Object>> overAllRoi=new HashMap();
       
       Map<Integer, List<Object>> returnedMap=null;
       
        List firstItems=new ArrayList();
        
        
      
        
       switch(details.get(2).toString()){
           
           case "Entire Period":
           
               switch(teypeNumber){
                   case 100:
//                JOptionPane.showMessageDialog(c, "details.get(2).toString()="+details.get(2).toString()+" teypeNumber="+teypeNumber+"theSize= "+theSize);           
                       
                       switch(theSize){
                          
                           case 1:
                            
                              firstItems.add("Expense Account");
                              
                              firstItems.add("0.0");
                              
                               firstItems.add("0.0");
                              
                       overAllRoi.put(0, firstItems);
                       
                       returnedMap=this.pbudgetEstimates(overAllRoi,"Expense Account");
                       
                               break;
                  case 2:
                              
                              firstItems.add("Expense Account");
                              
                              firstItems.add("0.0");
                              
                               firstItems.add("0.0");
                               
                                firstItems.add("0.0");
                               
                       overAllRoi.put(0, firstItems);
                       
                       returnedMap=this.pbudgetEstimates(overAllRoi,"Expense Account");
                       
                               break;
                          case 3:
                              
                              firstItems.add("Expense Account");
                              firstItems.add("0.0");
                               firstItems.add("0.0");
                                 firstItems.add("0.0");
                                  firstItems.add("0.0");
                       overAllRoi.put(0, firstItems);
                       returnedMap=this.pbudgetEstimates(overAllRoi,"Expense Account");
                               break;       
                       case 4:
                              
                              firstItems.add("Expense Account");
                              firstItems.add("0.0");
                               firstItems.add("0.0");
                                 firstItems.add("0.0");
                                   firstItems.add("0.0");
                                    firstItems.add("0.0");
                       overAllRoi.put(0, firstItems);
                       returnedMap=this.pbudgetEstimates(overAllRoi,"Expense Account");
                               break;
                               
                                 case 5:
                              
                              firstItems.add("Expense Account");
                              firstItems.add("0.0");
                               firstItems.add("0.0");
                                 firstItems.add("0.0");
                                   firstItems.add("0.0");
                                     firstItems.add("0.0");
                                      firstItems.add("0.0");
                       overAllRoi.put(0, firstItems);
                       returnedMap=this.pbudgetEstimates(overAllRoi,"Expense Account");
                               break;
                               
                               case 6:
                              
                              firstItems.add("Expense Account");
                              firstItems.add("0.0");
                               firstItems.add("0.0");
                                 firstItems.add("0.0");
                                   firstItems.add("0.0");
                                     firstItems.add("0.0");
                                      firstItems.add("0.0");
                                       firstItems.add("0.0");
                       overAllRoi.put(0, firstItems);
                       returnedMap=this.pbudgetEstimates(overAllRoi,"Expense Account");
                               break;
                               
                                   case 7:
                              
                              firstItems.add("Expense Account");
                              firstItems.add("0.0");
                               firstItems.add("0.0");
                                 firstItems.add("0.0");
                                   firstItems.add("0.0");
                                     firstItems.add("0.0");
                                      firstItems.add("0.0");
                                       firstItems.add("0.0");
                                        firstItems.add("0.0");
                       overAllRoi.put(0, firstItems);
                       returnedMap=this.pbudgetEstimates(overAllRoi,"Expense Account");
                               break;
                               
                                 case 8:

                            firstItems.add("Expense Account");
                            firstItems.add("0.0");
                            firstItems.add("0.0");
                            firstItems.add("0.0");
                            firstItems.add("0.0");
                            firstItems.add("0.0");
                            firstItems.add("0.0");
                            firstItems.add("0.0");
                            firstItems.add("0.0");
                             firstItems.add("0.0");
                            overAllRoi.put(0, firstItems);
                            returnedMap=this.pbudgetEstimates(overAllRoi,"Expense Account");
                               break;
                               
                                       case 9:

                               firstItems.add("Expense Account");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               overAllRoi.put(0, firstItems);
                               returnedMap = this.pbudgetEstimates(overAllRoi,"Expense Account");
                               break;
                               
                               case 10:

                               firstItems.add("Expense Account");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               overAllRoi.put(0, firstItems);
                                returnedMap = computeTotals(pbudgetEstimates(overAllRoi,"Expense Account"),10,c);
                               break;
                               
                                    
                               case 11:

                               firstItems.add("Expense Account");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               overAllRoi.put(0, firstItems);
                                returnedMap = computeTotals(pbudgetEstimates(overAllRoi,"Expense Account"),11,c);
                               break;
                               
                               case 12:

                               firstItems.add("Expense Account");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               overAllRoi.put(0, firstItems);
                          
                                returnedMap = computeTotals(pbudgetEstimates(overAllRoi,"Expense Account"),12,c);
                               break;
                       }
                       
                       break;
               
               case 200:
//               JOptionPane.showMessageDialog(c, "details.get(2).toString()="+details.get(2).toString()+" teypeNumber="+teypeNumber+"theSize= "+theSize); 
                       
                       switch(theSize){
                          
                           case 1:
                            
                              firstItems.add("Revenue Account");
                              firstItems.add("0.0");
                              firstItems.add("0.0");
                       overAllRoi.put(0, firstItems);
                       returnedMap=this.pbudgetEstimates(overAllRoi,"Revenue Account");
                               break;
                  case 2:
                              
                              firstItems.add("Revenue Account");
                              firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                       overAllRoi.put(0, firstItems);
                       returnedMap=this.pbudgetEstimates(overAllRoi,"Revenue Account");
                               break;
                          case 3:
                              
                              firstItems.add("Revenue Account");
                              firstItems.add("0.0");
                               firstItems.add("0.0");
                                 firstItems.add("0.0");
                                 firstItems.add("0.0");
                       overAllRoi.put(0, firstItems);
                       returnedMap=this.pbudgetEstimates(overAllRoi,"Revenue Account");
                               break;       
                       case 4:
                              
                              firstItems.add("Revenue Account");
                              firstItems.add("0.0");
                               firstItems.add("0.0");
                                 firstItems.add("0.0");
                                   firstItems.add("0.0");
                                   firstItems.add("0.0");
                       overAllRoi.put(0, firstItems);
                       returnedMap=this.pbudgetEstimates(overAllRoi,"Revenue Account");
                               break;
                               
                                 case 5:
                              
                              firstItems.add("Revenue Account");
                              firstItems.add("0.0");
                               firstItems.add("0.0");
                                 firstItems.add("0.0");
                                   firstItems.add("0.0");
                                     firstItems.add("0.0");
                                     firstItems.add("0.0");
                       overAllRoi.put(0, firstItems);
                       returnedMap=this.pbudgetEstimates(overAllRoi,"Revenue Account");
                               break;
                               
                               case 6:
                              
                              firstItems.add("Revenue Account");
                              firstItems.add("0.0");
                               firstItems.add("0.0");
                                 firstItems.add("0.0");
                                   firstItems.add("0.0");
                                     firstItems.add("0.0");
                                      firstItems.add("0.0");
                                      firstItems.add("0.0");
                       overAllRoi.put(0, firstItems);
                       returnedMap=this.pbudgetEstimates(overAllRoi,"Revenue Account");
                               break;
                               
                                   case 7:
                              
                              firstItems.add("Revenue Account");
                              firstItems.add("0.0");
                               firstItems.add("0.0");
                                 firstItems.add("0.0");
                                   firstItems.add("0.0");
                                     firstItems.add("0.0");
                                      firstItems.add("0.0");
                                       firstItems.add("0.0");
                                       firstItems.add("0.0");
                       overAllRoi.put(0, firstItems);
                       returnedMap=this.pbudgetEstimates(overAllRoi,"Revenue Account");
                               break;
                               
                                 case 8:

                            firstItems.add("Revenue Account");
                            firstItems.add("0.0");
                            firstItems.add("0.0");
                            firstItems.add("0.0");
                            firstItems.add("0.0");
                            firstItems.add("0.0");
                            firstItems.add("0.0");
                            firstItems.add("0.0");
                            firstItems.add("0.0");
                            firstItems.add("0.0");
                            overAllRoi.put(0, firstItems);
                            returnedMap=this.pbudgetEstimates(overAllRoi,"Revenue Account");
                               break;
                               
                                       case 9:

                               firstItems.add("Revenue Account");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               overAllRoi.put(0, firstItems);
                               returnedMap = this.pbudgetEstimates(overAllRoi,"Revenue Account");
                               break;
                               
                               case 10:

                               firstItems.add("Revenue Account");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               overAllRoi.put(0, firstItems);
                               returnedMap = computeTotals(pbudgetEstimates(overAllRoi,"Revenue Account"),10,c);
                               break;
                               
                                    
                               case 11:

                               firstItems.add("Revenue Account");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               overAllRoi.put(0, firstItems);
                            returnedMap = computeTotals(pbudgetEstimates(overAllRoi,"Revenue Account"),11,c);
                               break;
                               
                               case 12:

                               firstItems.add("Revenue Account");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               firstItems.add("0.0");
                               overAllRoi.put(0, firstItems);
                               returnedMap = computeTotals(pbudgetEstimates(overAllRoi,"Revenue Account"),12,c);
                               break;
                       }
                           
                   
                   
                   
                   break;
               
               }
               
               
               
    
               break;
        case "Date Range":
           
               switch(teypeNumber){
                   case 100: break;
               
               case 200: break;
               
               } 
            
            break;
       
       }
      
  
return returnedMap;

}  
 
   
   
   
   
       public synchronized  Map<Integer, List<Object>>computeTotals(Map<Integer, List<Object>> theMap, int accountType,Component c){
        
           List theAccounts=new ArrayList();
           
         int n=1; double t1=0.0,t2=0.0,t3=0.0,t4=0.0,t5=0.0,t6=0.0,t7=0.0,t8=0.0,t9=0.0,t10=0.0,t11=0.0,t12=0.0,t13=0.0;
           
//            JOptionPane.showMessageDialog(c, theMap.size());
          
       switch(accountType){
        
           case 1:
          while(n<theMap.size()) {
                   int z=0;  
          List realData=theMap.get(n); 
                while(z<realData.size()){
    if(z==0){
 
    
    }
    if(z==1){
 t1+=parseDouble(realData.get(1).toString());
    }
    
      if(z==2){
 t2+=parseDouble(realData.get(2).toString());
    
    }
    z++;   
    } 
       n++;   }      
             
     theAccounts.add("Total");
     theAccounts.add(t1);
     theAccounts.add(t2);
            break;
        case 2:
           while(n<theMap.size()) {
                  int z=0;   
          List realData=theMap.get(n); 
                while(z<realData.size()){
    if(z==0){
 
    
    }
    if(z==1){
 t1+=parseDouble(realData.get(1).toString());
    }
    
      if(z==2){
 t2+=parseDouble(realData.get(2).toString());
    
    }
          if(z==3){
 t3+=parseDouble(realData.get(3).toString());
    
    }
    z++;   
    } 
       n++;   }      
             
     theAccounts.add("Total");
     theAccounts.add(t1);
     theAccounts.add(t2);  
      theAccounts.add(t3);      
            
            break;
        case 3:
          while(n<theMap.size()) {
                    int z=0; 
          List realData=theMap.get(n); 
                while(z<realData.size()){
    if(z==0){
 
    
    }
    if(z==1){
 t1+=parseDouble(realData.get(1).toString());
    }
    
      if(z==2){
 t2+=parseDouble(realData.get(2).toString());
    
    }
          if(z==3){
 t3+=parseDouble(realData.get(3).toString());
    
    }
                   if(z==4){
 t4+=parseDouble(realData.get(4).toString());
    
    }
    z++;   
    } 
       n++;   }      
             
     theAccounts.add("Total");
     theAccounts.add(t1);
     theAccounts.add(t2);  
      theAccounts.add(t3);      
        theAccounts.add(t4);     
            
            
            
            break;
        case 4:
          while(n<theMap.size()) {
                     int z=0;
          List realData=theMap.get(n); 
                while(z<realData.size()){
    if(z==0){
 
    
    }
    if(z==1){
 t1+=parseDouble(realData.get(1).toString());
    }
    
      if(z==2){
 t2+=parseDouble(realData.get(2).toString());
    
    }
          if(z==3){
 t3+=parseDouble(realData.get(3).toString());
    
    }
                   if(z==4){
 t4+=parseDouble(realData.get(4).toString());
    
    }
                                      if(z==5){
 t5+=parseDouble(realData.get(5).toString());
    
    }
    z++;   
    } 
       n++;   }      
             
     theAccounts.add("Total");
     theAccounts.add(t1);
     theAccounts.add(t2);  
      theAccounts.add(t3);      
        theAccounts.add(t4);    
          theAccounts.add(t5);      
            break;
        case 5:
         while(n<theMap.size()) {
                 int z=0;    
          List realData=theMap.get(n); 
                while(z<realData.size()){
    if(z==0){
 
    
    }
    if(z==1){
 t1+=parseDouble(realData.get(1).toString());
    }
    
      if(z==2){
 t2+=parseDouble(realData.get(2).toString());
    
    }
          if(z==3){
 t3+=parseDouble(realData.get(3).toString());
    
    }
                   if(z==4){
 t4+=parseDouble(realData.get(4).toString());
    
    }
                                      if(z==5){
 t5+=parseDouble(realData.get(5).toString());
    
    }
                                        if(z==6){
 t6+=parseDouble(realData.get(6).toString());
    
    }                                    
    z++;   
    } 
       n++;   }      
             
     theAccounts.add("Total");
     theAccounts.add(t1);
     theAccounts.add(t2);  
      theAccounts.add(t3);      
        theAccounts.add(t4);    
          theAccounts.add(t5);         
          theAccounts.add(t6);     
            
            break;
        case 6:
           while(n<theMap.size()) {
                     int z=0;
          List realData=theMap.get(n); 
                while(z<realData.size()){
    if(z==0){
 
    
    }
    if(z==1){
 t1+=parseDouble(realData.get(1).toString());
    }
    
      if(z==2){
 t2+=parseDouble(realData.get(2).toString());
    
    }
          if(z==3){
 t3+=parseDouble(realData.get(3).toString());
    
    }
                   if(z==4){
 t4+=parseDouble(realData.get(4).toString());
    
    }
                                      if(z==5){
 t5+=parseDouble(realData.get(5).toString());
    
    }
                                        if(z==6){
 t6+=parseDouble(realData.get(6).toString());
    
    } 
                                 if(z==7){
 t7+=parseDouble(realData.get(7).toString());
    
    }                                        
                                        
    z++;   
    } 
       n++;   }      
             
     theAccounts.add("Total");
     theAccounts.add(t1);
     theAccounts.add(t2);  
      theAccounts.add(t3);      
        theAccounts.add(t4);    
          theAccounts.add(t5);         
          theAccounts.add(t6);     
              theAccounts.add(t7);   
            
            break;
        case 7:
           while(n<theMap.size()) {
                  int z=0;   
          List realData=theMap.get(n); 
                while(z<realData.size()){
    if(z==0){
 
    
    }
    if(z==1){
 t1+=parseDouble(realData.get(1).toString());
    }
    
      if(z==2){
 t2+=parseDouble(realData.get(2).toString());
    
    }
          if(z==3){
 t3+=parseDouble(realData.get(3).toString());
    
    }
                   if(z==4){
 t4+=parseDouble(realData.get(4).toString());
    
    }
                                      if(z==5){
 t5+=parseDouble(realData.get(5).toString());
    
    }
                                        if(z==6){
 t6+=parseDouble(realData.get(6).toString());
    
    } 
                                 if(z==7){
 t7+=parseDouble(realData.get(7).toString());
    
    }                                        
                                     if(z==8){
 t8+=parseDouble(realData.get(8).toString());
    
    }                                     
    z++;   
    } 
       n++;   }      
             
     theAccounts.add("Total");
     theAccounts.add(t1);
     theAccounts.add(t2);  
      theAccounts.add(t3);      
        theAccounts.add(t4);    
          theAccounts.add(t5);         
          theAccounts.add(t6);     
              theAccounts.add(t7);     
              theAccounts.add(t8);  
            break;
        case 8:
          while(n<theMap.size()) {
                     int z=0;
          List realData=theMap.get(n); 
                while(z<realData.size()){
    if(z==0){
 
    
    }
    if(z==1){
 t1+=parseDouble(realData.get(1).toString());
    }
    
      if(z==2){
 t2+=parseDouble(realData.get(2).toString());
    
    }
          if(z==3){
 t3+=parseDouble(realData.get(3).toString());
    
    }
                   if(z==4){
 t4+=parseDouble(realData.get(4).toString());
    
    }
                                      if(z==5){
 t5+=parseDouble(realData.get(5).toString());
    
    }
                                        if(z==6){
 t6+=parseDouble(realData.get(6).toString());
    
    } 
                                 if(z==7){
 t7+=parseDouble(realData.get(7).toString());
    
    }                                        
                                     if(z==8){
 t8+=parseDouble(realData.get(8).toString());
    
    }  
                                      if(z==9){
 t9+=parseDouble(realData.get(9).toString());
    
    }                                     
                                     
    z++;   
    } 
       n++;   }      
             
     theAccounts.add("Total");
     theAccounts.add(t1);
     theAccounts.add(t2);  
      theAccounts.add(t3);      
        theAccounts.add(t4);    
          theAccounts.add(t5);         
          theAccounts.add(t6);     
              theAccounts.add(t7);     
              theAccounts.add(t8);       
             theAccounts.add(t9); 
            break;
        case 9:
        while(n<theMap.size()) {
                 int z=0;    
          List realData=theMap.get(n); 
                while(z<realData.size()){
    if(z==0){
 
    
    }
    if(z==1){
 t1+=parseDouble(realData.get(1).toString());
    }
    
      if(z==2){
 t2+=parseDouble(realData.get(2).toString());
    
    }
          if(z==3){
 t3+=parseDouble(realData.get(3).toString());
    
    }
                   if(z==4){
 t4+=parseDouble(realData.get(4).toString());
    
    }
                                      if(z==5){
 t5+=parseDouble(realData.get(5).toString());
    
    }
                                        if(z==6){
 t6+=parseDouble(realData.get(6).toString());
    
    } 
                                 if(z==7){
 t7+=parseDouble(realData.get(7).toString());
    
    }                                        
                                     if(z==8){
 t8+=parseDouble(realData.get(8).toString());
    
    }  
                                      if(z==9){
 t9+=parseDouble(realData.get(9).toString());
    
    }                                     
                                          if(z==10){
 t10+=parseDouble(realData.get(10).toString());
    
    }                                  
    z++;   
    } 
       n++;   }      
             
     theAccounts.add("Total");
     theAccounts.add(t1);
     theAccounts.add(t2);  
      theAccounts.add(t3);      
        theAccounts.add(t4);    
          theAccounts.add(t5);         
          theAccounts.add(t6);     
              theAccounts.add(t7);     
              theAccounts.add(t8);       
             theAccounts.add(t9);     
               theAccounts.add(t10); 
            
            break;
        case 10:
            
         while(n<theMap.size()) {
                int z=0;
          List realData=theMap.get(n); 
//     JOptionPane.showMessageDialog(c, "realData.get(n)"+n);
                while(z<realData.size()){
    if(z==0){
 
    
    }
    if(z==1){
 t1+=parseDouble(realData.get(1).toString());
// JOptionPane.showMessageDialog(c, t1);
    }
    
      if(z==2){
 t2+=parseDouble(realData.get(2).toString());
    
    }
          if(z==3){
 t3+=parseDouble(realData.get(3).toString());
    
    }
                   if(z==4){
 t4+=parseDouble(realData.get(4).toString());
    
    }
                                      if(z==5){
 t5+=parseDouble(realData.get(5).toString());
    
    }
                                        if(z==6){
 t6+=parseDouble(realData.get(6).toString());
    
    } 
                                 if(z==7){
 t7+=parseDouble(realData.get(7).toString());
    
    }                                        
                                     if(z==8){
 t8+=parseDouble(realData.get(8).toString());
    
    }  
                                      if(z==9){
 t9+=parseDouble(realData.get(9).toString());
    
    }                                     
                                          if(z==10){
 t10+=parseDouble(realData.get(10).toString());
    
    }  
                                             if(z==11){
 t11+=parseDouble(realData.get(11).toString());
    
    }                                       
    z++;   
    } 
       n++;   }      
//     JOptionPane.showMessageDialog(c, t1);        
     theAccounts.add("Total");
     theAccounts.add(t1);
     theAccounts.add(t2);  
      theAccounts.add(t3);      
        theAccounts.add(t4);    
          theAccounts.add(t5);         
          theAccounts.add(t6);     
              theAccounts.add(t7);     
              theAccounts.add(t8);       
             theAccounts.add(t9);     
               theAccounts.add(t10);       
              theAccounts.add(t11); 
            break;
        case 11:
            
          while(n<theMap.size()) {
                     int z=0;
          List realData=theMap.get(n); 
                while(z<realData.size()){
    if(z==0){
 
    
    }
    if(z==1){
 t1+=parseDouble(realData.get(1).toString());
    }
    
      if(z==2){
 t2+=parseDouble(realData.get(2).toString());
    
    }
          if(z==3){
 t3+=parseDouble(realData.get(3).toString());
    
    }
                   if(z==4){
 t4+=parseDouble(realData.get(4).toString());
    
    }
                                      if(z==5){
 t5+=parseDouble(realData.get(5).toString());
    
    }
                                        if(z==6){
 t6+=parseDouble(realData.get(6).toString());
    
    } 
                                 if(z==7){
 t7+=parseDouble(realData.get(7).toString());
    
    }                                        
                                     if(z==8){
 t8+=parseDouble(realData.get(8).toString());
    
    }  
                                      if(z==9){
 t9+=parseDouble(realData.get(9).toString());
    
    }                                     
                                          if(z==10){
 t10+=parseDouble(realData.get(10).toString());
    
    }  
                                             if(z==11){
 t11+=parseDouble(realData.get(11).toString());
    
    }   
                                                                                          if(z==12){
 t12+=parseDouble(realData.get(12).toString());
    
    }
    z++;   
    } 
       n++;   }      
             
     theAccounts.add("Total");
     theAccounts.add(t1);
     theAccounts.add(t2);  
      theAccounts.add(t3);      
        theAccounts.add(t4);    
          theAccounts.add(t5);         
          theAccounts.add(t6);     
              theAccounts.add(t7);     
              theAccounts.add(t8);       
             theAccounts.add(t9);     
               theAccounts.add(t10);       
              theAccounts.add(t11);    
            
             theAccounts.add(t12);
            break;
        case 12:
            
            while(n<theMap.size()) {
                   int z=0;  
          List realData=theMap.get(n); 
                while(z<realData.size()){
    if(z==0){
 
    
    }
    if(z==1){
 t1+=parseDouble(realData.get(1).toString());
    }
    
      if(z==2){
 t2+=parseDouble(realData.get(2).toString());
    
    }
          if(z==3){
 t3+=parseDouble(realData.get(3).toString());
    
    }
                   if(z==4){
 t4+=parseDouble(realData.get(4).toString());
    
    }
                                      if(z==5){
 t5+=parseDouble(realData.get(5).toString());
    
    }
                                        if(z==6){
 t6+=parseDouble(realData.get(6).toString());
    
    } 
                                 if(z==7){
 t7+=parseDouble(realData.get(7).toString());
    
    }                                        
                                     if(z==8){
 t8+=parseDouble(realData.get(8).toString());
    
    }  
                                      if(z==9){
 t9+=parseDouble(realData.get(9).toString());
    
    }                                     
                                          if(z==10){
 t10+=parseDouble(realData.get(10).toString());
    
    }  
                                             if(z==11){
 t11+=parseDouble(realData.get(11).toString());
    
    }   
                                                                                          if(z==13){
 t13+=parseDouble(realData.get(13).toString());
    
    }
    z++;   
    } 
       n++;   }      
             
     theAccounts.add("Total");
     theAccounts.add(t1);
     theAccounts.add(t2);  
      theAccounts.add(t3);      
        theAccounts.add(t4);    
          theAccounts.add(t5);         
          theAccounts.add(t6);     
              theAccounts.add(t7);     
              theAccounts.add(t8);       
             theAccounts.add(t9);     
               theAccounts.add(t10);       
              theAccounts.add(t11);    
            
             theAccounts.add(t12);  
            theAccounts.add(t13);  
            break;
       
       }
       theMap.put(theMap.size(), theAccounts);
           
       return theMap;    
       }     
           
 public synchronized  Map<Integer, List<Object>>pbudgetEstimates(Map<Integer, List<Object>> theMap,String accountType){
 
 List TheAccounts=getTheActualAccounts(accountType); 
 
        int n=1;                  
                  
        
              
            
//                            List theMonths=getTheActualMonths();
                            
                           List theBudgetAmount=null;  
                           
////                   for(Object months:theMonths){
                       
                        for(Object account:TheAccounts){
                            
                      theBudgetAmount=new ArrayList();  
       
                  theBudgetAmount.add(account.toString());        
      
       try {
           
             Connection cq=quaryObj.createConnection(); 
             
          cq.setAutoCommit(false); 
          
                   String query = "SELECT BAmount  FROM  budgetstore WHERE AccountMaster="+"'"+account.toString()+"'";
             
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
              
             
              while (rs.next()) {
                  
                 
                  
            theBudgetAmount.add(rs.getString("BAmount"));
                      
                  
                    }
             
                     cq.setAutoCommit(true);
             
           quaryObj.closeConnection(cq); 
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
  
    theBudgetAmount.add(theTotalValue(theBudgetAmount));
                       
            theMap.put(n, theBudgetAmount);            
                 n++;
                                    }    
      
                
//                }  
 
 return theMap;
 }  
   
   
   public String theTotalValue(List allTheAmounts){
   int n=1;String total="";double theAmounts=0.0;
   
   while(n<allTheAmounts.size()){
   
   theAmounts+=parseDouble(allTheAmounts.get(n).toString());
   
   n++;
   }
  total= theAmounts+"";
   
   return total;
   }
   
   
   
   
   public List getTheActualMonths(){
   
    List theMonths=new ArrayList();
       
       try {
             Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
                   String query = "SELECT DISTINCT BMonthWord  FROM  budgetstore";
             
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
              
             
              while (rs.next()) {
                       
            theMonths.add(rs.getString("BMonthWord"));
                      
                  
                    }
             
                     cq.setAutoCommit(true);
             
           quaryObj.closeConnection(cq); 
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
   return theMonths;
   
   
   
   
   }
   
    public List<String> getTheActualMonthsHeaders(){
   
    List<String> theMonths=new ArrayList();
       theMonths.add("Months/Accounts");
       try {
             Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
                   String query = "SELECT DISTINCT BMonthWord  FROM  budgetstore";
             
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
              
             
              while (rs.next()) {
                       
            theMonths.add(rs.getString("BMonthWord"));
                      
                  
                    }
             
                     cq.setAutoCommit(true);
             
           quaryObj.closeConnection(cq); 
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
    theMonths.add("Total");
       
   return theMonths;
   }
   
   
   public List getTheActualAccounts(String accountType){
       
     List theAccounts=new ArrayList();
       
       try {
             Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
          
                   String query = "SELECT DISTINCT AccountMaster FROM  budgetstore WHERE AccountType="+"'"+accountType+"'";
             
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
              
             
              while (rs.next()) {
                       
            theAccounts.add(rs.getString("AccountMaster"));
                      
                  
                    }
             
                     cq.setAutoCommit(true);
             
           quaryObj.closeConnection(cq); 
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
   return theAccounts;
   
   
   }
  
   public int getnumberOfBudgetPeriods(){
   
       int numberOfPeriods=0;
       
       try {
             Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
                   String query = "SELECT COUNT(DISTINCT BMonthWord) AS months FROM  budgetstore";
             
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
              
             
              while (rs.next()) {
                       
            numberOfPeriods=rs.getInt("months");
                      
                  
                    }
             
                     cq.setAutoCommit(true);
             
           quaryObj.closeConnection(cq); 
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
   return numberOfPeriods;
   
   
   }
      public synchronized int getnumberOfBudgetPeriods1(){
   
       return fios.intFileReader(fios.createFileName("postingEntry", "generalTrn", "budgetEstimateNo.txt"));
   
   
   }
      
  public synchronized  Map<Integer, List<Object>>generalLedgerAll(String startDate,String endDate){
     
  
int n=0,k=0;

double totalShars=0.0,totalRtun=0.0,totalRun=0.0;List finale=null;

       Map<Integer, List<Object>> overAllRoi=new HashMap();
       
//       tableHeaders.add("S/N");
//    tableHeaders.add("TXN DATE");

//      tableHeaders.add("VALUE DATE");
//    tableHeaders.add("DR");
//    tableHeaders.add("CR");
//    tableHeaders.add("DR LEDGER");
//    tableHeaders.add("CR LEDGER");
//      tableHeaders.add("FOLIO");
  
 
      try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);

   String query = "SELECT  trn_id,trn_date, tra_ref_number,debit,credit,debit_account_no, credit_account_no,narration FROM general_ledger WHERE  (trn_date>="+"'"+startDate+"'"+"AND trn_date<="+"'"+endDate+"'"+")";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
     while(rsxmt.next()){
     String debit1=rsxmt.getString("debit");
             String credit1=rsxmt.getString("credit");
             List rioDetails=new ArrayList();
             
             
    rioDetails.add(rsxmt.getString("trn_id"));          
rioDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("trn_date")));
rioDetails.add("-");
rioDetails.add(debit1);
rioDetails.add(credit1);

//if(debit1.equalsIgnoreCase("-")){
   


//}else{
if(debit1.equalsIgnoreCase("-")){
      rioDetails.add("");
    rioDetails.add(this.AccountName(rsxmt.getString("debit_account_no")));
  


}else{
 rioDetails.add(this.AccountName(rsxmt.getString("debit_account_no")));
    rioDetails.add("");

}

rioDetails.add(rsxmt.getString("narration"));
//   
overAllRoi.put(k,  rioDetails);
String debit=rioDetails.get(3).toString();
String credit=rioDetails.get(4).toString();

if(credit.equalsIgnoreCase("-")){
    
   credit="0.0" ;
}

if(debit.equalsIgnoreCase("-")){
    
    debit="0.0";
}
totalShars+=parseDouble(debit);

totalRtun+=parseDouble(credit);
//totalRun+=parseDouble(rioDetails.get(5).toString());

  k++;
          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
      
       
       
//    y {
//        wait(2000);
//    } catch (InterruptedException ex) {
//        Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
//    }

       
              finale=new ArrayList();
  finale.add("Total");
  finale.add("-");
   finale.add("-");
    finale.add(totalShars+"");
    finale.add(totalRtun+"");
      finale.add("-");
  finale.add("-");
    finale.add("-");
overAllRoi.put(k,finale);  
 
return overAllRoi;

}
 
 
 public synchronized  Map<Integer, List<Object>>generalLedgerAccount(String accountnUmber,String startDate,String endDate){
     
  
int n=0,k=0;

double totalShars=0.0,totalRtun=0.0,totalRun=0.0;List finale=null;

       Map<Integer, List<Object>> overAllRoi=new HashMap();
       
//       tableHeaders.add("S/N");
//    tableHeaders.add("TXN DATE");

//      tableHeaders.add("VALUE DATE");
//    tableHeaders.add("DR");
//    tableHeaders.add("CR");
//    tableHeaders.add("DR LEDGER");
//    tableHeaders.add("CR LEDGER");
//      tableHeaders.add("FOLIO");
  
 
      try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);

   String query = "SELECT  trn_id,trn_date, tra_ref_number,debit,credit,debit_account_no, credit_account_no,narration FROM general_ledger WHERE  ((trn_date>="+"'"+startDate+"'"+"AND trn_date<="+"'"+endDate+"'"+") AND (debit_account_no="+"'"+accountnUmber+"' OR credit_account_no="+"'"+accountnUmber+"'))";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
     while(rsxmt.next()){
     String debit1=rsxmt.getString("debit");
             String credit1=rsxmt.getString("credit");
             List rioDetails=new ArrayList();
             
             
    rioDetails.add(rsxmt.getString("trn_id"));          
rioDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("trn_date")));
rioDetails.add("-");
rioDetails.add(debit1);
rioDetails.add(credit1);

//if(debit1.equalsIgnoreCase("-")){
   


//}else{
if(debit1.equalsIgnoreCase("-")){
      rioDetails.add("");
    rioDetails.add(this.AccountName(rsxmt.getString("debit_account_no")));
  


}else{
 rioDetails.add(this.AccountName(rsxmt.getString("debit_account_no")));
    rioDetails.add("");

}

rioDetails.add(rsxmt.getString("narration"));
//   
overAllRoi.put(k,  rioDetails);
String debit=rioDetails.get(3).toString();
String credit=rioDetails.get(4).toString();

if(credit.equalsIgnoreCase("-")){
    
   credit="0.0" ;
}

if(debit.equalsIgnoreCase("-")){
    
    debit="0.0";
}
totalShars+=parseDouble(debit);

totalRtun+=parseDouble(credit);
//totalRun+=parseDouble(rioDetails.get(5).toString());

  k++;
          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
      
       
       
//    y {
//        wait(2000);
//    } catch (InterruptedException ex) {
//        Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
//    }

       
              finale=new ArrayList();
  finale.add("Total");
  finale.add("-");
   finale.add("-");
    finale.add(totalShars+"");
    finale.add(totalRtun+"");
      finale.add("-");
  finale.add("-");
    finale.add("-");
overAllRoi.put(k,finale);  
 
return overAllRoi;

}
 
 
 
  public synchronized  Map<Integer, List<Object>>sharesStatemnt(String accountNumber,String startDate,String endDate){
   
      
      List OpeningBalanceShares=OpeningBalanceShares(accountNumber, startDate, endDate);
    
     Map<Integer, List<Object>> data1=null;List data2=null; Integer n=0,t=1;

data1= new HashMap<>();
      try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
   
     
          String query = "SELECT trn_date,value_shares_removed,value_shares_contributed,running_balance_n_shares,running_balance_v_shares FROM shares_run_bal WHERE (trn_date<="+"'"+endDate+"'"+" "+" AND account_number="+accountNumber+")";
                                                          
           PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();
        
//        if(n==0){
//            if(!OpeningBalanceShares.isEmpty()){
//         data1.put(n, OpeningBalanceShares);
//         n=n+1;
//            }else{
//            t=1;
//            }
//         }
        
        data2.add(t);
        data2.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("trn_date")));
         data2.add(rsxmt.getString("value_shares_removed"));
     
          data2 .add(rsxmt.getString("value_shares_contributed"));
          
          data2.add(rsxmt.getString("running_balance_n_shares"));
          
         data2 .add(rsxmt.getString("running_balance_v_shares"));
         
        data1.put(n, data2);
        
         n++;t++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      
     
return data1;
        
        
     

}
  
  public boolean savingsExists(String accountNumber,String startDate,String endDate){
      
      boolean savingsExis=false;int thesA=0;
  try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
   
     
          String query = "SELECT COUNT(TrnId) AS SaveCount FROM newsavingsmembers"+" "+"WHERE ((TrnDate>="+"'"+startDate+"'"+" "+"AND TrnDate<="+"'"+endDate+"'"+")"+" "+"AND"+" "+"(AccountNumber="+"'"+accountNumber+"'"+"))";
                                                          
           PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
     
          
         thesA=rsxmt.getInt("SaveCount");
          
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      if(thesA>0){
      savingsExis=true;
      }
  return savingsExis;
  
  }
  
public boolean indiSharesDailyExists(String accountNumber,String startDate,String endDate){
      
      boolean savingsExis=false;int thesA=0;
  try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
   
     
          String query = "SELECT COUNT(TrnId) AS SaveCount FROM sharesinterestpaymentdaily"+" "+"WHERE ((TrnDate>="+"'"+startDate+"'"+" "+"AND TrnDate<="+"'"+endDate+"'"+")"+" "+"AND"+" "+"(AccountNumber="+"'"+accountNumber+"'"+"))";
                                                          
           PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
     
          
         thesA=rsxmt.getInt("SaveCount");
          
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      if(thesA>0){
      savingsExis=true;
      }
  return savingsExis;
  
  }
  
  
  
  public synchronized  Map<Integer, List<Object>>savingsStatement(String accountNumber,String startDate,String endDate){
   
      
//      List OpeningBalanceShares=OpeningBalanceShares(accountNumber, startDate, endDate);
    
     Map<Integer, List<Object>> data1=null;List data2=null; Integer n=0,t=1;

data1= new HashMap<>();

if(savingsExists(accountNumber,startDate,endDate)){

      try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
   
     
          String query = "SELECT OtherOne,SavingsAdded,SavingsRemoved,SavingsRunningBalance,DATE_FORMAT(TrnDate, \"%d/%m/%Y\") AS theDate FROM newsavingsmembers"+" "+"WHERE ((TrnDate>="+"'"+startDate+"'"+" "+"AND TrnDate<="+"'"+endDate+"'"+")"+" "+"AND"+" "+"(AccountNumber="+"'"+accountNumber+"'"+"))";
                                                          
           PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();

        data2.add(t);
        data2.add(rsxmt.getString("OtherOne"));
     
          data2 .add(rsxmt.getString("SavingsRemoved"));
          
          data2.add(rsxmt.getString("SavingsAdded"));
          
         data2 .add(rsxmt.getString("SavingsRunningBalance"));
         
         data2 .add(rsxmt.getString("theDate"));
         
        data1.put(n, data2);
        
         n++;t++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
}
     
return data1;
        
        
     

}   

  public synchronized  Map<Integer, List<Object>>sharesIndivROIDaily(String accountNumber,String startDate,String endDate,Component c){
   
      double totalLedgerBalance=0.0,interestComputed=0.0;
      
//      List OpeningBalanceShares=OpeningBalanceShares(accountNumber, startDate, endDate);
    
     Map<Integer, List<Object>> data1=null;List data2=null; Integer n=0,t=1;

data1= new HashMap<>();

//if(indiSharesDailyExists(accountNumber,startDate,endDate)){
 
      try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);

     
          String query = "SELECT TrnDate,TxnMonth,TxnYear,AccountNmae,LedgerBalance,InterestComputed,InterestComputedRuningBalance FROM sharesinterestpaymentdaily"+" "+"WHERE ((TrnDate>="+"'"+startDate+"'"+" "+"AND TrnDate<="+"'"+endDate+"'"+")"+" "+"AND"+" "+"(AccountNumber="+"'"+accountNumber+"'"+"))";
                                                          
           PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();

        data2.add(t);
         data2.add(rsxmt.getString("TrnDate"));
        data2.add(rsxmt.getString("TxnMonth")+" "+rsxmt.getString("TxnYear"));
     
          data2 .add(rsxmt.getString("AccountNmae"));
          
          data2.add(rsxmt.getString("LedgerBalance"));
          
         data2 .add(rsxmt.getString("InterestComputed"));
         
          data2 .add(rsxmt.getString("InterestComputedRuningBalance"));
         
        data1.put(n, data2);
       
//        totalLedgerBalance=totalLedgerBalance+parseDouble(data2.get(4).toString());
//        interestComputed=totalLedgerBalance+parseDouble(data2.get(5).toString());
         n++;t++;
          }
//          JOptionPane.showMessageDialog(c, n);
//          JOptionPane.showMessageDialog(c, totalLedgerBalance);
//            JOptionPane.showMessageDialog(c, totalLedgerBalance);
     
//List data2x=new ArrayList<>();
//
//data2x.add("");
//        data2x.add("Total");
//        data2x.add("");
//                data2x.add("");
//                data2x.add(totalLedgerBalance);
//                        data2x.add(interestComputed);
//                        data2x.add("");
//                               data1.put(n, data2);
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
//}

 

return data1;
        
        
     

}   
  
  
  public synchronized  Map<Integer, List<Object>>savingsIndivROIDaily(String accountNumber,String startDate,String endDate){
   
      
//      List OpeningBalanceShares=OpeningBalanceShares(accountNumber, startDate, endDate);
    
     Map<Integer, List<Object>> data1=null;List data2=null; Integer n=0,t=1;

data1= new HashMap<>();

//if(indiSharesDailyExists(accountNumber,startDate,endDate)){
 
      try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
//                    ((TrnDate>="+"'"+startDate+"'"+" "+"AND TrnDate<="+"'"+endDate+"'"+")"+" "+"AND"+" "+"(LedgerBalance>0))
     
          String query = "SELECT TrnDate,TxnMonth,TxnYear,AccountNmae,LedgerBalance,InterestComputed,InterestComputedRuningBalance FROM savingsinterestpaymentdaily"+" "+"WHERE (((TrnDate>="+"'"+startDate+"'"+" "+"AND TrnDate<="+"'"+endDate+"'"+")"+" "+"AND"+" "+"(LedgerBalance>0))"+" "+"AND"+" "+"(AccountNumber="+"'"+accountNumber+"'"+"))";
                                                          
           PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();

        data2.add(t);
        
         data2.add(rsxmt.getString("TrnDate"));
         
        data2.add(rsxmt.getString("TxnMonth")+" "+rsxmt.getString("TxnYear"));
     
          data2 .add(rsxmt.getString("AccountNmae"));
          
          data2.add(rsxmt.getString("LedgerBalance"));
          
         data2 .add(rsxmt.getString("InterestComputed"));
         
          data2 .add(rsxmt.getString("InterestComputedRuningBalance"));
         
        data1.put(n, data2);
        
         n++;t++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
//}
     
return data1;
        
        
     

}    
  
  
  public synchronized  Map<Integer, List<Object>>sharesIndivROIGrouped(String startDate,String endDate,Component c){
   
      double totalRunningBal=0.0,totalReturn=0.0;
//      List OpeningBalanceShares=OpeningBalanceShares(accountNumber, startDate, endDate);
    
     Map<Integer, List<Object>> data1=null;List data2=null; Integer n=0,t=1;

data1= new HashMap<>();

//if(indiSharesDailyExists(accountNumber,startDate,endDate)){
 
      try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
   
     
          String query = "SELECT TxnYear,AccountNmae,AccountNumber,LedgerBalance,InterestComputed FROM sharesinterestpaymentannually"+" "+"WHERE (TrnDate>="+"'"+startDate+"'"+ "AND"+" "+"InterestComputed>0)";
                                                          
           PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>();

        data2.add(t);
        data2.add(rsxmt.getString("TxnYear"));
     
          data2 .add(rsxmt.getString("AccountNmae"));
           data2 .add(rsxmt.getString("AccountNumber"));
          data2.add(rsxmt.getString("LedgerBalance"));
          
         data2 .add(rsxmt.getString("InterestComputed"));
         
         
        data1.put(n, data2);
        totalRunningBal=totalRunningBal+parseDouble(data2.get(4).toString());
         totalReturn=totalReturn+parseDouble(data2.get(5).toString());
         n++;t++;
          }
//          JOptionPane.showMessageDialog(c, n+1);
//          JOptionPane.showMessageDialog(c, totalRunningBal);
//          JOptionPane.showMessageDialog(c, totalReturn);
          
          List totals=new ArrayList();
          totals.add("");
                   totals.add("Total");
                    totals.add("");
                     totals.add("");
                     totals.add(totalRunningBal);
                      totals.add(totalReturn);
                       data1.put(n, totals);
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
//}
//      JOptionPane.showMessageDialog(c, data1.size());
return data1;
        
        
   

}    
 
  
  
  public synchronized  Map<Integer, List<Object>>savingsIndivROIGrouped(String startDate,String endDate,Component c,JTextField f){
      
     double totalRunningBal=0.0,totalReturn=0.0;
//      JOptionPane.showMessageDialog(c, endDate);
//      List OpeningBalanceShares=OpeningBalanceShares(accountNumber, startDate, endDate);
    
     Map<Integer, List<Object>> data1x=null;List data2x=null; Integer n=0,t=1;

data1x= new HashMap<>();

//if(indiSharesDailyExists(accountNumber,startDate,endDate)){
 
      try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
  
     
          String query = "SELECT TxnYear,AccountNmae,AccountNumber,LedgerBalance,InterestComputed FROM savingsinterestpaymentannually  WHERE (TrnDate>="+"'"+startDate+"'"+" "+"AND"+" "+"(InterestComputed>0))";
                            f.setText(query);
           PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2x=new ArrayList<>();

        data2x.add(t);
         data2x.add(rsxmt.getString("TxnYear"));
  
     
          data2x .add(rsxmt.getString("AccountNmae"));
          data2x .add(rsxmt.getString("AccountNumber"));
          data2x.add(rsxmt.getInt("LedgerBalance"));
          
         data2x .add(rsxmt.getInt("InterestComputed"));
//          JOptionPane.showMessageDialog(c, data2x.get(0).toString());   
//           JOptionPane.showMessageDialog(c, data2x.get(1).toString());
//            JOptionPane.showMessageDialog(c, data2x.get(2).toString());
//             JOptionPane.showMessageDialog(c, data2x.get(3).toString());
//              JOptionPane.showMessageDialog(c, data2x.get(4).toString());
         totalRunningBal=totalRunningBal+parseDouble(data2x.get(4).toString());
         totalReturn=totalReturn+parseDouble(data2x.get(5).toString());  
        data1x.put(n, data2x);
        
         n++;t++;
          }
          
          
//          JOptionPane.showMessageDialog(c, data1x.size()+"");
           List totals=new ArrayList();
          totals.add("");
                   totals.add("Total");
                    totals.add("");
                    totals.add("");
                     totals.add(totalRunningBal);
                      totals.add(totalReturn);
                       data1x.put(n, totals);
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
 
return data1x;
        
        
     

}    
  
  
  
  public synchronized  Map<Integer, List<Object>>listGroupSharesStatemnt(String startDate,String endDate){
   
      double totalSharsRemoved=0.0,totalSharsAdded=0.0,totalNumberShars=0.0,totalValueShars=0.0;List finale=null;
      
      List accounts=dbq.accountNumbersCustomers();
    
     Map<Integer, List<Object>> data1=null;List data2=null; Integer n=0,t=1;

data1= new HashMap<>();

for(Object theAccount:accounts){
    
    if(sharesExist(theAccount.toString(),startDate,endDate)){
    
      try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
   
     
          String query = "SELECT SUM(value_shares_removed) AS SRemoved,SUM(value_shares_contributed) AS SAdded FROM shares_run_bal WHERE (trn_date<="+"'"+endDate+"'"+" "+" AND account_number="+theAccount.toString()+")";
                                                          
           PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>(); 
        
        data2.add(t);
        
        data2.add(theAccount.toString());
        
        data2.add(dbq.AccountName(theAccount.toString()));
        
         data2.add(rsxmt.getString("SRemoved"));
     
          data2 .add(rsxmt.getString("SAdded"));
          
          data2.add(numberOfShares(theAccount.toString(),startDate,endDate));
          
         data2 .add(valueOfShares(theAccount.toString(),startDate,endDate));
         
        data1.put(n, data2);
        
        totalSharsRemoved+=parseDouble(data2.get(3).toString());
totalSharsAdded+=parseDouble(data2.get(4).toString());
totalNumberShars+=parseDouble(data2.get(5).toString());
totalValueShars+=parseDouble(data2.get(6).toString());

         n++;t++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
    }   
}  

 finale=new ArrayList();
  finale.add(0,"Total");
    finale.add(1,"-");
    finale.add(2,"-");
      finale.add(3,totalSharsRemoved+"");
      finale.add(4,totalSharsAdded+"");
 finale.add(5,totalNumberShars+"");
 finale.add(6,totalValueShars+"");
data1.put(n,finale); 

return data1;
        
        
     

}
  
  
   public synchronized  Map<Integer, List<Object>>listGroupSavingsStatemnt(String startDate,String endDate){
   
      double totalSavingsRemoved=0.0,totalSavingsAdded=0.0,totalValueSavings=0.0;List finale=null;
      
      List accounts=dbq.accountNumbersCustomers();
    
     Map<Integer, List<Object>> data1=null;List data2=null; Integer n=0,t=1;

data1= new HashMap<>();

for(Object theAccount:accounts){
    
    if(savingsExists(theAccount.toString(),startDate,endDate)){
    
      try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
   
     
          String query = "SELECT SUM(SavingsRemoved) AS SaRemoved,SUM(SavingsAdded) AS SaAdded FROM newsavingsmembers WHERE ((TrnDate>="+"'"+startDate+"'"+" "+"AND TrnDate<="+"'"+endDate+"'"+")"+" "+"AND"+" "+"(AccountNumber="+"'"+theAccount.toString()+"'"+"))";
                                                          
           PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>(); 
        
        data2.add(t);
        
        data2.add(theAccount.toString());
        
        data2.add(dbq.AccountName(theAccount.toString()));
        
         data2.add(rsxmt.getString("SaRemoved"));
     
          data2 .add(rsxmt.getString("SaAdded"));
          
          
         data2 .add(runninSavings(theAccount.toString(),startDate,endDate));
         
        data1.put(n, data2);
        
        totalSavingsRemoved+=parseDouble(data2.get(3).toString());
totalSavingsAdded+=parseDouble(data2.get(4).toString());
totalValueSavings+=parseDouble(data2.get(5).toString());

         n++;t++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
    }   
}  

 finale=new ArrayList();
  finale.add(0,"Total");
    finale.add(1,"-");
    finale.add(2,"-");
      finale.add(3,totalSavingsRemoved+"");
      finale.add(4,totalSavingsAdded+"");
 finale.add(5,totalValueSavings+"");
 
data1.put(n,finale); 

return data1;
        
        
     

}
  
  public synchronized  Map<Integer, List<Object>>listGroupSavingsStatemntAll(String startDate,String endDate){
   
      double totalSavingsRemoved=0.0,totalSavingsAdded=0.0,totalValueSavings=0.0;List finale=null;
      
      List accounts=dbq.accountNumbersCustomers();
    
     Map<Integer, List<Object>> data1=null;List data2=null; Integer n=0,t=1;

data1= new HashMap<>();

for(Object theAccount:accounts){
    
 
    
      try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
   
     
          String query = "SELECT SUM(SavingsRemoved) AS SaRemoved,SUM(SavingsAdded) AS SaAdded FROM newsavingsmembers WHERE ((TrnDate>="+"'"+startDate+"'"+" "+"AND TrnDate<="+"'"+endDate+"'"+")"+" "+"AND"+" "+"(AccountNumber="+"'"+theAccount.toString()+"'"+"))";
                                                          
           PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>(); 
           if(savingsExists(theAccount.toString(),startDate,endDate)){
        data2.add(t);
        
        data2.add(theAccount.toString());
        
        data2.add(dbq.AccountName(theAccount.toString()));
        
         data2.add(rsxmt.getString("SaRemoved"));
     
          data2 .add(rsxmt.getString("SaAdded"));
          
          
         data2 .add(runninSavings(theAccount.toString(),startDate,endDate));
           }else{
           
           data2.add(t);
        
        data2.add(theAccount.toString());
        
        data2.add(dbq.AccountName(theAccount.toString()));
        
         data2.add("0.0");
     
          data2 .add("0.0");
          
          
         data2 .add("0.0"); 
           
           
           
           
           }
        data1.put(n, data2);
        
        totalSavingsRemoved+=parseDouble(data2.get(3).toString());
totalSavingsAdded+=parseDouble(data2.get(4).toString());
totalValueSavings+=parseDouble(data2.get(5).toString());

         n++;t++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
     
}  

 finale=new ArrayList();
  finale.add(0,"Total");
    finale.add(1,"-");
    finale.add(2,"-");
      finale.add(3,totalSavingsRemoved+"");
      finale.add(4,totalSavingsAdded+"");
 finale.add(5,totalValueSavings+"");
 
data1.put(n,finale); 

return data1;
        
        
     

}
   
 public String runninSavings(String theAccount,String startDate,String endDate){
 String theRunBal="";
 try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
   
     
          String query = "SELECT SavingsRunningBalance FROM newsavingsmembers WHERE ((TrnDate>="+"'"+startDate+"'"+" "+"AND TrnDate<="+"'"+endDate+"'"+")"+" "+"AND"+" "+"(AccountNumber="+"'"+theAccount+"'"+"))";
                                                          
           PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          if (rsxmt.last()){
        
        
         theRunBal=rsxmt.getString("SavingsRunningBalance");
     
        
        
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
    return theRunBal;
}  
 
 
  
   
 public synchronized  Map<Integer, List<Object>>listGroupSharesStatemntAll(String startDate,String endDate){
   
      double totalSharsRemoved=0.0,totalSharsAdded=0.0,totalNumberShars=0.0,totalValueShars=0.0;List finale=null;
      
      List accounts=dbq.accountNumbersCustomers();
    
     Map<Integer, List<Object>> data1=null;List data2=null; Integer n=0,t=1;

data1= new HashMap<>();

for(Object theAccount:accounts){
    
 
    
      try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
   
     
          String query = "SELECT SUM(value_shares_removed) AS SRemoved,SUM(value_shares_contributed) AS SAdded FROM shares_run_bal WHERE (trn_date<="+"'"+endDate+"'"+" "+" AND account_number="+theAccount.toString()+")";
                                                          
           PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
        data2=new ArrayList<>(); 
        
       if(sharesExist(theAccount.toString(),startDate,endDate)){   
        data2.add(t);
        
        data2.add(theAccount.toString());
        
        data2.add(dbq.AccountName(theAccount.toString()));
        
         data2.add(rsxmt.getString("SRemoved"));
     
          data2 .add(rsxmt.getString("SAdded"));
          
          data2.add(numberOfShares(theAccount.toString(),startDate,endDate));
          
         data2 .add(valueOfShares(theAccount.toString(),startDate,endDate));
       }else{
       
         data2.add(t);
        
        data2.add(theAccount.toString());
        
        data2.add(dbq.AccountName(theAccount.toString()));
        
         data2.add("0.0");
     
          data2 .add("0.0");
          
          data2.add("0.0");
          
         data2 .add("0.0");
       
       
       }
        data1.put(n, data2);
        
        totalSharsRemoved+=parseDouble(data2.get(3).toString());
totalSharsAdded+=parseDouble(data2.get(4).toString());
totalNumberShars+=parseDouble(data2.get(5).toString());
totalValueShars+=parseDouble(data2.get(6).toString());

         n++;t++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
    
}  

 finale=new ArrayList();
  finale.add(0,"Total");
    finale.add(1,"-");
    finale.add(2,"-");
      finale.add(3,totalSharsRemoved+"");
      finale.add(4,totalSharsAdded+"");
 finale.add(5,totalNumberShars+"");
 finale.add(6,totalValueShars+"");
data1.put(n,finale); 

return data1;
        
        
     

} 
  
  
  
  
  public String numberOfShares(String accountNumber,String startDate,String endDate){
  String theNumberOfShares="";
  try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
   
     
          String query = "SELECT running_balance_n_shares FROM shares_run_bal WHERE (trn_date<="+"'"+endDate+"'"+" "+" AND account_number="+accountNumber+")";
                                                          
           PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          if (rsxmt.last()){
              
theNumberOfShares=rsxmt.getString("running_balance_n_shares");
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
 return theNumberOfShares; 
  
  }
  
  public String valueOfShares(String accountNumber,String startDate,String endDate){
  String theValueOfShares="";
  try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
   
     
          String query = "SELECT running_balance_v_shares FROM shares_run_bal WHERE (trn_date<="+"'"+endDate+"'"+" "+" AND account_number="+accountNumber+")";
                                                          
           PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          if (rsxmt.last()){
              
theValueOfShares=rsxmt.getString("running_balance_v_shares");
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
 return theValueOfShares; 
  
  }
  
  
  
  

  public boolean sharesExist(String theAccountNumber,String startDate,String endDate){
  boolean sharesExis=false;int theV=0;
  
  try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
   
     
          String query = "SELECT COUNT(trn_id) AS id FROM shares_run_bal WHERE (trn_date<="+"'"+endDate+"'"+" "+" AND account_number="+theAccountNumber+")";
                                                          
           PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
          theV =rsxmt.getInt("id");
          
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      if(theV>0){
      sharesExis=true;
      }
  
  return sharesExis;
  }
  
    public List OpeningBalanceShares(String accountNumber, String startDate, String endDate){
    List openingBal=new ArrayList<>();
     try {
          
          Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
   
     
          String query = "SELECT trn_date,value_shares_removed,value_shares_contributed,running_balance_n_shares,running_balance_v_shares FROM shares_run_bal WHERE trn_date<="+"'"+startDate+"'";
                                                          
           PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          
          if (rsxmt.last()){
       
        openingBal.add("1");
        
        openingBal.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("trn_date")));
        
         openingBal.add(rsxmt.getString("value_shares_removed"));
     
          openingBal .add(rsxmt.getString("value_shares_contributed"));
          
          openingBal.add(rsxmt.getString("running_balance_n_shares"));
          
         openingBal .add(rsxmt.getString("running_balance_v_shares"));
       
          }
          
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
    return openingBal;
    }  
      
      
      
      
  public synchronized  Map<Integer, List<Object>>montlyReturnOIGroup(String accountNumber,String startDate,String endDate){
//     tableHeaders.add("S/n");
//    tableHeaders.add("Month");
//    tableHeaders.add("Shares");
//    tableHeaders.add("Return");
//    tableHeaders.add("Running Balance");
  
int n=0,k=0;

double totalRtun=0.0;  List finale=null;

       Map<Integer, List<Object>> overAllRoi=new HashMap();
       
    YearMonth theStartDateObject=YearMonth.parse(startDate.substring(0, 7));   
    
     YearMonth theEndDateObject=YearMonth.parse(endDate.substring(0, 7));   
     
     int startMonth=theStartDateObject.getMonthValue();
     
     int numberOfMonths=(theEndDateObject.getMonthValue()-startMonth)+1;
     
    
     
     while(numberOfMonths>0){
         
         List rioDetails=new ArrayList();  
         
  String theMonth=theStartDateObject.withMonth(startMonth).getMonth().toString();
  
  if(theMonthExists(theMonth,accountNumber)){
      try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);

   String query = "SELECT value_month,Balance_Shares,Monthly_return,Return_Run_Bal FROM sharesreturnoninvestment WHERE value_month="+"'"+theMonth+"'"+" AND account_number="+"'"+accountNumber+"'";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
     if(rsxmt.last()){
        
           
            n+=1;
    rioDetails.add(n);          
rioDetails.add(rsxmt.getString("value_month"));
rioDetails.add(rsxmt.getString("Balance_Shares"));
rioDetails.add(rsxmt.getString("Monthly_return"));
rioDetails.add(rsxmt.getString("Return_Run_Bal"));

          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
        overAllRoi.put(k,  rioDetails);
      totalRtun+=parseDouble(rioDetails.get(3).toString());
         k++; 
  }    

    
 
    startMonth++; 
    numberOfMonths--; 
     }   
       
       
              finale=new ArrayList();
  finale.add("Total");
  finale.add("-");
   finale.add("-");
    finale.add(totalRtun+"");
      finale.add("-");

overAllRoi.put(k,finale);  
 
return overAllRoi;

}
  
 public synchronized  Map<Integer, List<Object>>grroupmontlyReturnOIGroup(String startDate,String endDate){
//     tableHeaders.add("S/n");
//    tableHeaders.add("Month");
//    tableHeaders.add("Shares");
//    tableHeaders.add("Return");
//    tableHeaders.add("Running Balance");
  
  List finale=null;double totalRtun=0.0;
 int n=0,k=0;
       Map<Integer, List<Object>> overAllRoi=new HashMap();
       List theAccounts=customerAccounts();
     for(Object account:theAccounts){  
        


    YearMonth theStartDateObject=YearMonth.parse(startDate.substring(0, 7));   
    
     YearMonth theEndDateObject=YearMonth.parse(endDate.substring(0, 7));   
     
     int startMonth=theStartDateObject.getMonthValue();
     
     int numberOfMonths=(theEndDateObject.getMonthValue()-startMonth)+1;

     while(numberOfMonths>0){
         
         List rioDetails=new ArrayList();  
         
  String theMonth=theStartDateObject.withMonth(startMonth).getMonth().toString();
  
  if(theMonthExists(theMonth,account.toString())){
      try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);

   String query = "SELECT value_month,account_name,Balance_Shares,Monthly_return,Return_Run_Bal FROM sharesreturnoninvestment WHERE value_month="+"'"+theMonth+"'"+" AND account_number="+"'"+account.toString()+"'";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
     if(rsxmt.last()){
        
           
            n+=1;
    rioDetails.add(n);          
rioDetails.add(rsxmt.getString("value_month"));
rioDetails.add(rsxmt.getString("account_name"));
rioDetails.add(rsxmt.getString("Balance_Shares"));
rioDetails.add(rsxmt.getString("Monthly_return"));
rioDetails.add(rsxmt.getString("Return_Run_Bal"));

          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
        overAllRoi.put(k,  rioDetails);
      totalRtun+=parseDouble(rioDetails.get(4).toString());
         k++; 
  }    

    
 
    startMonth++; 
    numberOfMonths--; 
     }   
          
     }
     finale=new ArrayList();
  finale.add("Total");
  finale.add("-");
   finale.add("-");
    finale.add("-");
    finale.add(totalRtun+"");
      finale.add("-");

overAllRoi.put(k,finale);     
 
return overAllRoi;

} 
  
  
  private boolean theMonthExists(String theMonth,String accountNumber){
  int thev=0;boolean exiists=false;
       try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);

   String query = "SELECT COUNT(value_month) AS THEmONTH FROM sharesreturnoninvestment WHERE value_month="+"'"+theMonth+"'"+" AND account_number="+"'"+accountNumber+"'";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
    while(rsxmt.next()){
        
//            thev=rsxmt.getInt("THEmONTH");
 if(!rsxmt.getString("THEmONTH").equalsIgnoreCase("0")){
       exiists=true;
       }
          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
       
 
    return exiists;   
  }    
  
  
  
      
      
  
  
  
  public synchronized  Map<Integer, List<Object>>provisionedItems(String startDate,String endDate){
     
  
int n=0,k=0;

double totalShars=0.0,totalRtun=0.0,totalRun=0.0;List finale=null;

       Map<Integer, List<Object>> proItems=new HashMap();

  
 
      try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);

   String query = "SELECT  trn_date, account_name,range_provisioned,percentage_provisioned,days_in_arrears,princimpal_in_arrears,amount_provisioned FROM provisionwriteofftracker WHERE  (trn_date>="+"'"+startDate+"'"+"AND trn_date<="+"'"+endDate+"'"+")";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
     while(rsxmt.next()){
        
             List provisioned=new ArrayList();
            n+=1;
    provisioned.add(n);     
    provisioned.add(rsxmt.getString("trn_date"));
provisioned.add(rsxmt.getString("account_name"));
provisioned.add(rsxmt.getString("range_provisioned"));
provisioned.add(rsxmt.getString("percentage_provisioned"));
provisioned.add(rsxmt.getString("days_in_arrears"));
provisioned.add(rsxmt.getString("princimpal_in_arrears"));
provisioned.add(rsxmt.getString("amount_provisioned"));
//   
proItems.put(k,  provisioned);

totalShars+=parseDouble(provisioned.get(6).toString());
totalRtun+=parseDouble(provisioned.get(7).toString());
//totalRun+=parseDouble(rioDetails.get(5).toString());

  k++;
          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
      
       
       
//    y {
//        wait(2000);
//    } catch (InterruptedException ex) {
//        Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
//    }

       
              finale=new ArrayList();
  finale.add("Total");
  finale.add("-");
   finale.add("-");
    finale.add("-");
      finale.add("-");
       finale.add("-");
    finale.add(totalShars+"");
      finale.add(totalRtun+"");

proItems.put(k,finale);  
 
return proItems;

}
  
  
          
          
  
 public synchronized  Map<Integer, List<Object>>projectPay(String startDate,String endDate){
     
  
int n=0,k=0;

Double loanAmount=0.0,totalPrincipal=0.0,totalInterest=0.0,totalAccumulatedInt=0.0,totalLoanPenalty=0.0;List finale=null;

       Map<Integer, List<Object>> projectedReport=new HashMap();
       
        List accountsProjected=this.disbursedAccountsw();
    

        
       for(Object account:accountsProjected){
//           account=;
           List instalmentsConsidered=getTheInstamentsProject(account.toString(),startDate,endDate);
        if(parseInt(instalmentsConsidered.get(0).toString())!=777){   
    for(Object instalment:instalmentsConsidered){
 
      try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
     
          
          
   String query = "SELECT  PrincipalRemaining,InterestRemaing, AccruedInterestRemaing,LoanPenaltyRemaining,InstalmentRemaining,instalment_due_date FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+account.toString()+"' AND instalment_no="+instalment.toString();
          
                                                        
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
     while(rsxmt.next()){
        
             List loanDetails=new ArrayList();
            n=n+1;
    loanDetails.add(n);          
loanDetails.add(AccountName(account.toString()));
loanDetails.add(account.toString());
loanDetails.add(instalment.toString());
loanDetails.add(rsxmt.getString("PrincipalRemaining"));
loanDetails.add(rsxmt.getString("InterestRemaing"));
loanDetails.add(rsxmt.getString("AccruedInterestRemaing"));
loanDetails.add(rsxmt.getString("LoanPenaltyRemaining"));
loanDetails.add(rsxmt.getString("InstalmentRemaining"));
loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("instalment_due_date")));
//   
projectedReport.put(k,  loanDetails);

totalPrincipal+=parseDouble(loanDetails.get(4).toString());
totalInterest+=parseDouble(loanDetails.get(5).toString());
totalAccumulatedInt+=parseDouble(loanDetails.get(6).toString());
totalLoanPenalty+=parseDouble(loanDetails.get(7).toString());
loanAmount+=parseDouble(loanDetails.get(8).toString());
  k++;
          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
      
       }
       }
//    try {
//        wait(2000);
//    } catch (InterruptedException ex) {
//        Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
//    }

       }
              finale=new ArrayList();
  finale.add("Total");
  finale.add("-");
   finale.add("-");
   finale.add("-");
    finale.add(totalPrincipal+"");
    finale.add(totalInterest+"");
      finale.add(totalAccumulatedInt+"");
    finale.add(totalLoanPenalty+"");
      finale.add(loanAmount+"");
 finale.add("-");

projectedReport.put(k,finale);  
 
return projectedReport;

}
  public synchronized  Map<Integer, List<Object>> listLoansData(){
  
  return  new HashMap();
  }
 
 public synchronized  List quickSummuryRatios(String valueDate,Component c){
     
 

      List projectedReport=new ArrayList();
      
      
        try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
     
          String query = "SELECT `TotalNumberOfAccounts` ,`TotalNumberOfCustomers` ,`TotalNumberOfActiveSavingsCustomers` ,`TotalNumberSavingsWithdraws` ,`TotalNumberOfSavingsMade` ,`TotalValueSavingsWithdraws` ,`TotalValueOfSavings` ,	  `TotalValueOfSavingsMade` ,	  `TotalNumberOfActiveSharesCustomers` ,	  `TotalNumberOfCapitalisations` ,	  `TotalNumberOfDecapitalisations` ,	  `TotalValueOfSharesBought` ,	  `TotalValueOfSharesRemoved` ,	  `TotalValueOfShares` ,	  `TotalNumberOfShares` ,	  `TotalNumberOfCustomersWithDeposits` ,	  `TotalValueOfDeposits` ,	  `TotalNumberOfActiveLoans` ,	  `TotalNumberOfActiveLoansCycle1` ,	  `TotalNumberOfActiveLoansCycle2` ,	  `TotalNumberOfActiveLoansCycle3` ,	  `TotalNumberOfActiveLoansCycle4` ,	  `TotalNumberOfActiveLoansCycle5` ,	  `TotalNumberOfActiveLoansCycle6` ,	  `TotalNumberOfActiveLoansCycle7` ,	  `TotalNumberOfActiveLoansCycleAbove7` ,	  `TotalValueOfActiveLoans` ,	  `TotalValueOfActiveLoansCycle1` ,	  `TotalValueOfActiveLoansCycle2` ,	  `TotalValueOfActiveLoansCycle3` ,	  `TotalValueOfActiveLoansCycle4` ,	  `TotalValueOfActiveLoansCycle5` ,	  `TotalValueOfActiveLoansCycle6` ,	  `TotalValueOfActiveLoansCycle7` ,	  `TotalValueOfActiveLoansCycleAbove7` ,	  `TotalNumberOfActiveLoanCustomers` ,	  `TotalNumberOfActiveLoanCustomersCycle1` ,	  `TotalNumberOfActiveLoanCustomersCycle2` ,	  `TotalNumberOfActiveLoanCustomersCycle3` ,	  `TotalNumberOfActiveLoanCustomersCycle4` ,	  `TotalNumberOfActiveLoanCustomersCycle5` ,	  `TotalNumberOfActiveLoanCustomersCycle6` ,	  `TotalNumberOfActiveLoanCustomersCycle7` ,	  `TotalNumberOfActiveLoanCustomersCycleAboveCycle7` ,	  `TotalValueOfActiveLoanCustomers` ,	  `TotalValueOfActiveLoanCustomersCycle1` ,	  `TotalValueOfActiveLoanCustomersCycle2` ,	  `TotalValueOfActiveLoanCustomersCycle3` ,	  `TotalValueOfActiveLoanCustomersCycle4` ,	  `TotalValueOfActiveLoanCustomersCycle5` ,	  `TotalValueOfActiveLoanCustomersCycle6` ,	  `TotalValueOfActiveLoanCustomersCycle7` ,	  `TotalValueOfActiveLoanCustomersCycleAboveCycle7` ,	  `TotalNumberOfLoansDisbursed` ,	  `TotalNumberOfLoansDisbursedCycle1` ,	  `TotalNumberOfLoansDisbursedCycle2` ,	  `TotalNumberOfLoansDisbursedCycle3` ,	  `TotalNumberOfLoansDisbursedCycle4` ,	  `TotalNumberOfLoansDisbursedCycle5` ,	  `TotalNumberOfLoansDisbursedCycle6` ,	  `TotalNumberOfLoansDisbursedCycle7` ,	  `TotalNumberOfLoansDisbursedCycleAbove7` ,	  `TotalValueOfLoansDisbursed` ,	  `TotalValueOfLoansDisbursedCycle1` ,	  `TotalValueOfLoansDisbursedCycle2` ,	  `TotalValueOfLoansDisbursedCycle3` ,	  `TotalValueOfLoansDisbursedCycle4` ,	  `TotalValueOfLoansDisbursedCycle5` ,	  `TotalValueOfLoansDisbursedCycle6` ,	  `TotalValueOfLoansDisbursedCycle7` ,	  `TotalValueOfLoansDisbursedCycleAbove7` ,	  `TotalNumberOfGroupLoansDisbursed` ,	  `TotalNumberOfGroupLoansDisbursedCycle1` ,	  `TotalNumberOfGroupLoansDisbursedCycle2` ,	  `TotalNumberOfGroupLoansDisbursedCycle3` ,	  `TotalNumberOfGroupLoansDisbursedCycle4` ,	  `TotalNumberOfGroupLoansDisbursedCycle5` ,	  `TotalNumberOfGroupLoansDisbursedCycle6` ,	  `TotalNumberOfGroupLoansDisbursedCycle7` ,	  `TotalNumberOfGroupLoansDisbursedCycleAbove7` ,	  `TotalValueOfGroupLoansDisbursed` ,	  `TotalValueOfGroupLoansDisbursedCycle1` ,	  `TotalValueOfGroupLoansDisbursedCycle2` ,	  `TotalValueOfGroupLoansDisbursedCycle3` ,	  `TotalValueOfGroupLoansDisbursedCycle4` ,	  `TotalValueOfGroupLoansDisbursedCycle5` ,	  `TotalValueOfGroupLoansDisbursedCycle6` ,	  `TotalValueOfGroupLoansDisbursedCycle7` ,	  `TotalValueOfGroupLoansDisbursedCycleAbove7` ,	  `TotalNumberOfIndividualLoansDisbursed` ,	  `TotalNumberOfIndividualLoansDisbursedCycle1` ,	  `TotalNumberOfIndividualLoansDisbursedCycle2` ,	  `TotalNumberOfIndividualLoansDisbursedCycle3` ,	  `TotalNumberOfIndividualLoansDisbursedCycle4` ,	  `TotalNumberOfIndividualLoansDisbursedCycle5` ,	  `TotalNumberOfIndividualLoansDisbursedCycle6` ,	  `TotalNumberOfIndividualLoansDisbursedCycle7` ,	  `TotalNumberOfIndividualLoansDisbursedCycleAbove7` ,	  `TotalValueOfIndividualLoansDisbursed` ,	  `TotalValueOfIndividualLoansDisbursedCycle1` ,	  `TotalValueOfIndividualLoansDisbursedCycle2` ,	  `TotalValueOfIndividualLoansDisbursedCycle3` ,	  `TotalValueOfIndividualLoansDisbursedCycle4` ,	  `TotalValueOfIndividualLoansDisbursedCycle5` ,	  `TotalValueOfIndividualLoansDisbursedCycle6` ,	  `TotalValueOfIndividualLoansDisbursedCycle7` ,	  `TotalValueOfIndividualLoansDisbursedCycleAbove7` ,	  `TotalNumberOfLoansCompleted` ,	  `TotalValueOfLoansCompleted` ,	  `TotalNumberOfLoansWrittenOff` ,	  `TotalValueOfLoansWrittenOff` ,	  `TotalNumberOfAllPrincipalLoanRepayments` ,	  `TotalValueOfAllPrincipalLoanRepayments` ,	  `TotalNumberOfPrincipalLoanRepaymentsDueLoansOnly` ,	  `TotalValueOfPrincipalLoanRepaymentsDueLoansOnly` ,	  `TotalNumberOfEarlyPrincipalLoanRepayments` ,	  `TotalValueOfEarlyPrincipalLoanRepayments` ,	  `TotalNumberOfArrearsPrincipalLoanRepayments` ,	  `TotalValueOfArrearsPrincipalLoanRepayments` ,	  `TotalNumberOfLoanRepaymentsMinusArrears` ,	  `TotalValueOfLoanRepaymentsMinusArrears` ,	  `TotalNumberOfAllInterestPayments` ,	  `TotalValueOfInterestReceived` ,	  `TotalNumberOfInterestPaymentsDueLoansOnly` ,	  `TotalValueOfInterestPaymentsDueLoansOnly` ,	  `TotalNumberOfEarlyInterestPayments` ,	  `TotalValueOfEarlyInterestPayments` ,	  `TotalNumberOfArrearsInterestPayments` ,	  `TotalValueOfArrearsInterestPayments` ,	  `TotalNumberOfAllAccumulatedInterestPayments` ,	  `TotalValueOfAllAccumulatedInterestPayments` ,	  `TotalNumberOfAllLoanPenaltyPayments` ,	  `TotalValueOfAllLoanPenaltyPayments` ,	  `TotalNumberOfAllInterestAndPrincipalLoanRepayments` ,	  `TotalValueOfAllInterestAndPrincipalLoanRepayments` ,	  `TotalValueOfPrincipalOutStandingArrears` ,	  `TotalValueOfInterestOutStandingArrears` ,	  `TotalNumberOfLoansInArrears` ,	  `TotalValueOfPrincipalLoansInArrears` ,	  `TotalValueOfInterestInArrears` ,	  `TotalValueOfLoanBook` ,	  `TotalValueOfCashBalances` ,	  `TotalValueOfBankBalances` ,	  `TotalValueOfAssets` ,	  `TotalValueOfReceivables` ,	  `TotalValueOfPayables` ,	  `TotalValueOfFixedAssets` ,	  `TotalValueOfCurrentAssetsIncludingInterestReceivable` ,	  `TotalValueOfCurrentAssetsMinusInterestReceivable` ,	  `TotalValueOfMainIncome` ,	  `TotalValueOfOtherIncome` ,	  `TotalValueOfIncome` ,	  `TotalValueOfExpenses` ,	  `TotalValueOfLiabilities` ,	  `TotalValueOfCapital`  FROM summurystats WHERE `ItemDate`="+"'"+valueDate+"'";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
             projectedReport.add(rsxmt.getString("TotalNumberOfAccounts"));
projectedReport.add(rsxmt.getString("TotalNumberOfCustomers"));
projectedReport.add(rsxmt.getString("TotalNumberOfActiveSavingsCustomers"));
projectedReport.add(rsxmt.getString("TotalNumberSavingsWithdraws"));
projectedReport.add(rsxmt.getString("TotalNumberOfSavingsMade"));
projectedReport.add(rsxmt.getString("TotalValueSavingsWithdraws"));
projectedReport.add(rsxmt.getString("TotalValueOfSavings"));
projectedReport.add(rsxmt.getString("TotalValueOfSavingsMade"));
projectedReport.add(rsxmt.getString("TotalNumberOfActiveSharesCustomers"));
projectedReport.add(rsxmt.getString("TotalNumberOfCapitalisations"));
projectedReport.add(rsxmt.getString("TotalNumberOfDecapitalisations"));
projectedReport.add(rsxmt.getString("TotalValueOfSharesBought"));
projectedReport.add(rsxmt.getString("TotalValueOfSharesRemoved"));
projectedReport.add(rsxmt.getString("TotalValueOfShares"));
projectedReport.add(rsxmt.getString("TotalNumberOfShares"));
projectedReport.add(rsxmt.getString("TotalNumberOfCustomersWithDeposits"));
projectedReport.add(rsxmt.getString("TotalValueOfDeposits"));
projectedReport.add(rsxmt.getString("TotalNumberOfActiveLoans"));
projectedReport.add(rsxmt.getString("TotalNumberOfActiveLoansCycle1"));
projectedReport.add(rsxmt.getString("TotalNumberOfActiveLoansCycle2"));
projectedReport.add(rsxmt.getString("TotalNumberOfActiveLoansCycle3"));
projectedReport.add(rsxmt.getString("TotalNumberOfActiveLoansCycle4"));
projectedReport.add(rsxmt.getString("TotalNumberOfActiveLoansCycle5"));
projectedReport.add(rsxmt.getString("TotalNumberOfActiveLoansCycle6"));
projectedReport.add(rsxmt.getString("TotalNumberOfActiveLoansCycle7"));
projectedReport.add(rsxmt.getString("TotalNumberOfActiveLoansCycleAbove7"));
projectedReport.add(rsxmt.getString("TotalValueOfActiveLoans"));
projectedReport.add(rsxmt.getString("TotalValueOfActiveLoansCycle1"));
projectedReport.add(rsxmt.getString("TotalValueOfActiveLoansCycle2"));
projectedReport.add(rsxmt.getString("TotalValueOfActiveLoansCycle3"));
projectedReport.add(rsxmt.getString("TotalValueOfActiveLoansCycle4"));
projectedReport.add(rsxmt.getString("TotalValueOfActiveLoansCycle5"));
projectedReport.add(rsxmt.getString("TotalValueOfActiveLoansCycle6"));
projectedReport.add(rsxmt.getString("TotalValueOfActiveLoansCycle7"));
projectedReport.add(rsxmt.getString("TotalValueOfActiveLoansCycleAbove7"));
projectedReport.add(rsxmt.getString("TotalNumberOfActiveLoanCustomers"));
projectedReport.add(rsxmt.getString("TotalNumberOfActiveLoanCustomersCycle1"));
projectedReport.add(rsxmt.getString("TotalNumberOfActiveLoanCustomersCycle2"));
projectedReport.add(rsxmt.getString("TotalNumberOfActiveLoanCustomersCycle3"));
projectedReport.add(rsxmt.getString("TotalNumberOfActiveLoanCustomersCycle4"));
projectedReport.add(rsxmt.getString("TotalNumberOfActiveLoanCustomersCycle5"));
projectedReport.add(rsxmt.getString("TotalNumberOfActiveLoanCustomersCycle6"));
projectedReport.add(rsxmt.getString("TotalNumberOfActiveLoanCustomersCycle7"));
projectedReport.add(rsxmt.getString("TotalNumberOfActiveLoanCustomersCycleAboveCycle7"));
projectedReport.add(rsxmt.getString("TotalValueOfActiveLoanCustomers"));
projectedReport.add(rsxmt.getString("TotalValueOfActiveLoanCustomersCycle1"));
projectedReport.add(rsxmt.getString("TotalValueOfActiveLoanCustomersCycle2"));
projectedReport.add(rsxmt.getString("TotalValueOfActiveLoanCustomersCycle3"));
projectedReport.add(rsxmt.getString("TotalValueOfActiveLoanCustomersCycle4"));
projectedReport.add(rsxmt.getString("TotalValueOfActiveLoanCustomersCycle5"));
projectedReport.add(rsxmt.getString("TotalValueOfActiveLoanCustomersCycle6"));
projectedReport.add(rsxmt.getString("TotalValueOfActiveLoanCustomersCycle7"));
projectedReport.add(rsxmt.getString("TotalValueOfActiveLoanCustomersCycleAboveCycle7"));
projectedReport.add(rsxmt.getString("TotalNumberOfLoansDisbursed"));
projectedReport.add(rsxmt.getString("TotalNumberOfLoansDisbursedCycle1"));
projectedReport.add(rsxmt.getString("TotalNumberOfLoansDisbursedCycle2"));
projectedReport.add(rsxmt.getString("TotalNumberOfLoansDisbursedCycle3"));
projectedReport.add(rsxmt.getString("TotalNumberOfLoansDisbursedCycle4"));
projectedReport.add(rsxmt.getString("TotalNumberOfLoansDisbursedCycle5"));
projectedReport.add(rsxmt.getString("TotalNumberOfLoansDisbursedCycle6"));
projectedReport.add(rsxmt.getString("TotalNumberOfLoansDisbursedCycle7"));
projectedReport.add(rsxmt.getString("TotalNumberOfLoansDisbursedCycleAbove7"));
projectedReport.add(rsxmt.getString("TotalValueOfLoansDisbursed"));
projectedReport.add(rsxmt.getString("TotalValueOfLoansDisbursedCycle1"));
projectedReport.add(rsxmt.getString("TotalValueOfLoansDisbursedCycle2"));
projectedReport.add(rsxmt.getString("TotalValueOfLoansDisbursedCycle3"));
projectedReport.add(rsxmt.getString("TotalValueOfLoansDisbursedCycle4"));
projectedReport.add(rsxmt.getString("TotalValueOfLoansDisbursedCycle5"));
projectedReport.add(rsxmt.getString("TotalValueOfLoansDisbursedCycle6"));
projectedReport.add(rsxmt.getString("TotalValueOfLoansDisbursedCycle7"));
projectedReport.add(rsxmt.getString("TotalValueOfLoansDisbursedCycleAbove7"));
projectedReport.add(rsxmt.getString("TotalNumberOfGroupLoansDisbursed"));
projectedReport.add(rsxmt.getString("TotalNumberOfGroupLoansDisbursedCycle1"));
projectedReport.add(rsxmt.getString("TotalNumberOfGroupLoansDisbursedCycle2"));
projectedReport.add(rsxmt.getString("TotalNumberOfGroupLoansDisbursedCycle3"));
projectedReport.add(rsxmt.getString("TotalNumberOfGroupLoansDisbursedCycle4"));
projectedReport.add(rsxmt.getString("TotalNumberOfGroupLoansDisbursedCycle5"));
projectedReport.add(rsxmt.getString("TotalNumberOfGroupLoansDisbursedCycle6"));
projectedReport.add(rsxmt.getString("TotalNumberOfGroupLoansDisbursedCycle7"));
projectedReport.add(rsxmt.getString("TotalNumberOfGroupLoansDisbursedCycleAbove7"));
projectedReport.add(rsxmt.getString("TotalValueOfGroupLoansDisbursed"));
projectedReport.add(rsxmt.getString("TotalValueOfGroupLoansDisbursedCycle1"));
projectedReport.add(rsxmt.getString("TotalValueOfGroupLoansDisbursedCycle2"));
projectedReport.add(rsxmt.getString("TotalValueOfGroupLoansDisbursedCycle3"));
projectedReport.add(rsxmt.getString("TotalValueOfGroupLoansDisbursedCycle4"));
projectedReport.add(rsxmt.getString("TotalValueOfGroupLoansDisbursedCycle5"));
projectedReport.add(rsxmt.getString("TotalValueOfGroupLoansDisbursedCycle6"));
projectedReport.add(rsxmt.getString("TotalValueOfGroupLoansDisbursedCycle7"));
projectedReport.add(rsxmt.getString("TotalValueOfGroupLoansDisbursedCycleAbove7"));
projectedReport.add(rsxmt.getString("TotalNumberOfIndividualLoansDisbursed"));
projectedReport.add(rsxmt.getString("TotalNumberOfIndividualLoansDisbursedCycle1"));
projectedReport.add(rsxmt.getString("TotalNumberOfIndividualLoansDisbursedCycle2"));
projectedReport.add(rsxmt.getString("TotalNumberOfIndividualLoansDisbursedCycle3"));
projectedReport.add(rsxmt.getString("TotalNumberOfIndividualLoansDisbursedCycle4"));
projectedReport.add(rsxmt.getString("TotalNumberOfIndividualLoansDisbursedCycle5"));
projectedReport.add(rsxmt.getString("TotalNumberOfIndividualLoansDisbursedCycle6"));
projectedReport.add(rsxmt.getString("TotalNumberOfIndividualLoansDisbursedCycle7"));
projectedReport.add(rsxmt.getString("TotalNumberOfIndividualLoansDisbursedCycleAbove7"));
projectedReport.add(rsxmt.getString("TotalValueOfIndividualLoansDisbursed"));
projectedReport.add(rsxmt.getString("TotalValueOfIndividualLoansDisbursedCycle1"));
projectedReport.add(rsxmt.getString("TotalValueOfIndividualLoansDisbursedCycle2"));
projectedReport.add(rsxmt.getString("TotalValueOfIndividualLoansDisbursedCycle3"));
projectedReport.add(rsxmt.getString("TotalValueOfIndividualLoansDisbursedCycle4"));
projectedReport.add(rsxmt.getString("TotalValueOfIndividualLoansDisbursedCycle5"));
projectedReport.add(rsxmt.getString("TotalValueOfIndividualLoansDisbursedCycle6"));
projectedReport.add(rsxmt.getString("TotalValueOfIndividualLoansDisbursedCycle7"));
projectedReport.add(rsxmt.getString("TotalValueOfIndividualLoansDisbursedCycleAbove7"));
projectedReport.add(rsxmt.getString("TotalNumberOfLoansCompleted"));
projectedReport.add(rsxmt.getString("TotalValueOfLoansCompleted"));
projectedReport.add(rsxmt.getString("TotalNumberOfLoansWrittenOff"));
projectedReport.add(rsxmt.getString("TotalValueOfLoansWrittenOff"));
projectedReport.add(rsxmt.getString("TotalNumberOfAllPrincipalLoanRepayments"));
projectedReport.add(rsxmt.getString("TotalValueOfAllPrincipalLoanRepayments"));
projectedReport.add(rsxmt.getString("TotalNumberOfPrincipalLoanRepaymentsDueLoansOnly"));
projectedReport.add(rsxmt.getString("TotalValueOfPrincipalLoanRepaymentsDueLoansOnly"));
projectedReport.add(rsxmt.getString("TotalNumberOfEarlyPrincipalLoanRepayments"));
projectedReport.add(rsxmt.getString("TotalValueOfEarlyPrincipalLoanRepayments"));
projectedReport.add(rsxmt.getString("TotalNumberOfArrearsPrincipalLoanRepayments"));
projectedReport.add(rsxmt.getString("TotalValueOfArrearsPrincipalLoanRepayments"));
projectedReport.add(rsxmt.getString("TotalNumberOfLoanRepaymentsMinusArrears"));
projectedReport.add(rsxmt.getString("TotalValueOfLoanRepaymentsMinusArrears"));
projectedReport.add(rsxmt.getString("TotalNumberOfAllInterestPayments"));
projectedReport.add(rsxmt.getString("TotalValueOfInterestReceived"));
projectedReport.add(rsxmt.getString("TotalNumberOfInterestPaymentsDueLoansOnly"));
projectedReport.add(rsxmt.getString("TotalValueOfInterestPaymentsDueLoansOnly"));
projectedReport.add(rsxmt.getString("TotalNumberOfEarlyInterestPayments"));
projectedReport.add(rsxmt.getString("TotalValueOfEarlyInterestPayments"));
projectedReport.add(rsxmt.getString("TotalNumberOfArrearsInterestPayments"));
projectedReport.add(rsxmt.getString("TotalValueOfArrearsInterestPayments"));
projectedReport.add(rsxmt.getString("TotalNumberOfAllAccumulatedInterestPayments"));
projectedReport.add(rsxmt.getString("TotalValueOfAllAccumulatedInterestPayments"));
projectedReport.add(rsxmt.getString("TotalNumberOfAllLoanPenaltyPayments"));
projectedReport.add(rsxmt.getString("TotalValueOfAllLoanPenaltyPayments"));
projectedReport.add(rsxmt.getString("TotalNumberOfAllInterestAndPrincipalLoanRepayments"));
projectedReport.add(rsxmt.getString("TotalValueOfAllInterestAndPrincipalLoanRepayments"));
projectedReport.add(rsxmt.getString("TotalValueOfPrincipalOutStandingArrears"));
projectedReport.add(rsxmt.getString("TotalValueOfInterestOutStandingArrears"));
projectedReport.add(rsxmt.getString("TotalNumberOfLoansInArrears"));
projectedReport.add(rsxmt.getString("TotalValueOfPrincipalLoansInArrears"));
projectedReport.add(rsxmt.getString("TotalValueOfInterestInArrears"));
projectedReport.add(rsxmt.getString("TotalValueOfLoanBook"));
projectedReport.add(rsxmt.getString("TotalValueOfCashBalances"));
//JOptionPane.showMessageDialog(c, rsxmt.getString("TotalValueOfCashBalances"));
projectedReport.add(rsxmt.getString("TotalValueOfBankBalances"));
projectedReport.add(rsxmt.getString("TotalValueOfAssets"));
projectedReport.add(rsxmt.getString("TotalValueOfReceivables"));
projectedReport.add(rsxmt.getString("TotalValueOfPayables"));
projectedReport.add(rsxmt.getString("TotalValueOfFixedAssets"));
projectedReport.add(rsxmt.getString("TotalValueOfCurrentAssetsIncludingInterestReceivable"));
projectedReport.add(rsxmt.getString("TotalValueOfCurrentAssetsMinusInterestReceivable"));
projectedReport.add(rsxmt.getString("TotalValueOfMainIncome"));
projectedReport.add(rsxmt.getString("TotalValueOfOtherIncome"));
projectedReport.add(rsxmt.getString("TotalValueOfIncome"));
projectedReport.add(rsxmt.getString("TotalValueOfExpenses"));
projectedReport.add(rsxmt.getString("TotalValueOfLiabilities"));
projectedReport.add(rsxmt.getString("TotalValueOfCapital"));
  
          }
          
//     if(  accounts.isEmpty()){ accounts.add("XXXX");}     
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
            quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, null, ex);
      }
      
      
return projectedReport;

}
 
 
 
 
 
  public  Map<Integer, List<Object>>staffLoans(){
      
      int z=0;Double loanAmount=0.0,princimpal=0.0,interest=0.0,amountRemaining=0.0,amountPaid=0.0;List finale=null;
       Map<Integer, List<Object>> staffLoans=new HashMap();int sizeAccounts=0;
  
   
   List<String> thestaffAccounts=null;
   
  thestaffAccounts=this.staffAccounts();
  if(!thestaffAccounts.isEmpty()){
    sizeAccounts  =thestaffAccounts.size();
  while(z<sizeAccounts){
      
  if(this.isStaffBorrowing(thestaffAccounts.get(z))){
      
  List loanDetails=this.borrowingDetails(thestaffAccounts.get(z),z+1);
  staffLoans.put(z,loanDetails );
  
  loanAmount=loanAmount+parseDouble(loanDetails.get(2).toString());
  princimpal=princimpal+parseDouble(loanDetails.get(3).toString());
  interest=interest+parseDouble(loanDetails.get(4).toString());
  amountRemaining=amountRemaining+parseDouble(loanDetails.get(5).toString());
   amountPaid  =  amountPaid+parseDouble(loanDetails.get(6).toString());    

  }else{
      thestaffAccounts.remove(z);
   sizeAccounts=sizeAccounts-1;   
  z--;
  }
  z++;
  }
  
  
  finale=new ArrayList();
  finale.add("Total");
  finale.add(" ");
  finale.add(loanAmount+"");
  finale.add(princimpal+"");
  finale.add(interest+"");
  finale.add(amountRemaining+"");
  finale.add(amountPaid +"");
 
  
  staffLoans.put(z,finale);

  }
  
  return staffLoans;
  }
  
 private List<String> staffAccounts(){
     
 List<String> accounts=new ArrayList();String acount="";
      try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
     
          String query = "SELECT  account_number FROM log_in";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
              
 acount=rsxmt.getString("account_number");
      accounts.add(acount);
          
          }
          
//     if(  accounts.isEmpty()){ accounts.add("XXXX");}     
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
            quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, null, ex);
      }
      
      
return accounts;
 
 
 } 
 
 private boolean isStaffBorrowing(String accountNumber){
 int count=0;boolean isborrow=false;
      try {
          Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
     
          String query = "SELECT  COUNT(trn_id) AS count FROM new_loan_appstore WHERE loan_id="+"'"+"newloan"+accountNumber+"'";
            
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        while(rsxmt.next()){
 count=rsxmt.getInt("count");
          }
          
     if(count>0){isborrow=true;}     
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
      
      
return isborrow;
        
 
 
 
 }
  
   private List borrowingDetails(String accountNumber,int n){
 List borrowDetails=new ArrayList();
      try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
 
          String query = "SELECT  applicant_account_name, total_loanAmount,princimpal_amount,total_interest,balance_due FROM new_loan_appstore WHERE loan_id="+"'"+"newloan"+accountNumber+"'"+"ORDER BY applicant_account_name ASC";
            
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        while(rsxmt.next()){
    borrowDetails.add(n);          
borrowDetails.add(rsxmt.getString("applicant_account_name"));
borrowDetails.add(rsxmt.getString("total_loanAmount"));
borrowDetails.add(rsxmt.getString("princimpal_amount"));
borrowDetails.add(rsxmt.getString("total_interest"));
borrowDetails.add(rsxmt.getString("balance_due"));
borrowDetails.add((parseDouble(rsxmt.getString("total_loanAmount"))-parseDouble(rsxmt.getString("balance_due")))+"");

          }
          
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
      
      
return borrowDetails;

 }
  
  private String princimpalAmount(String accountNumber){
 String principal="";
      try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
 
          String query = "SELECT  princimpal_amount FROM new_loan_appstore WHERE loan_id="+"'"+"newloan"+accountNumber+"'";
            
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        while(rsxmt.next()){
   
principal=(rsxmt.getString("princimpal_amount"));

          }
          
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
      
      
return principal;

 } 
   
   
  private String interestAmount(String accountNumber){
 String principal="";
      try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
 
          String query = "SELECT  total_interest FROM new_loan_appstore WHERE loan_id="+"'"+"newloan"+accountNumber+"'";
            
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        while(rsxmt.next()){
   
principal=(rsxmt.getString("total_interest"));

          }
          
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
      
      
return principal;

 }  

  public Map<Integer, List<Object>> loansInArrears(String startDate,String endDate){
      
  List accountsInArrears=this.accountsInArrearsW();
  int z=0;Double loanAmount=0.0,amountPaidh=0.0,amountRemaining=0.0, amountInArrears=0.0;List finale=null;
  int y=0;int accountInASize=accountsInArrears.size();
 
  Map<Integer, List<Object>>takeMe=new HashMap();
  
  List<Object> part1=null;
  
  while(y<accountInASize){
      
  part1=this.part1DataArrears(accountsInArrears.get(y).toString(), y+1);
  
   List part2=this.part2DataArrears(accountsInArrears.get(y).toString());
   
   List finalO=new ArrayList(part1.size()+part2.size()); 
   
 finalO.addAll(part1);
 
  finalO.addAll(part2);
  
  if(!finalO.isEmpty()){
      
  takeMe.put(y, finalO);
  
   loanAmount=loanAmount+parseDouble(finalO.get(2).toString());
  amountPaidh=amountPaidh+parseDouble(finalO.get(3).toString());
  amountRemaining=amountRemaining+parseDouble(finalO.get(4).toString());
  z=z+parseInt(finalO.get(5).toString());
   amountInArrears =  amountInArrears+parseDouble(finalO.get(6).toString());   
   
  }else{
      finalO.remove(z);
   accountInASize=accountInASize-1;   
  y--;
  }
  y++;
  }
   finale=new ArrayList();
  finale.add("Total");
  finale.add(" ");
  finale.add(loanAmount+"");
  finale.add(amountPaidh+"");
  finale.add(amountRemaining+"");
  finale.add( z+"");
  finale.add(amountInArrears+"");
 
   takeMe.put(y, finale);

 return takeMe;
  
  }
  
  
  private List accountsInArrears(){
 
      List arrearsDetails=new ArrayList();
 if( fios.stringFileReader(fios.createFileName("PMMS_Statements", "reports", "portfolioOwner.txt")).equalsIgnoreCase("All")){
      try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
 
          String query = "SELECT  applicant_account_number FROM new_loan_appstore WHERE    loan_cycle_status="+"'"+"Disbursed"+"'"+"ORDER BY  instalment_next_due_date DESC";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        while(rsxmt.next()){
        String account=rsxmt.getString("applicant_account_number");
        if(loanArrearsUpdated( account)){
            
   arrearsDetails.add( account);
       
        }
          }
          
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
        loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
}else{
 
   try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
 
          String query = "SELECT  applicant_account_number FROM new_loan_appstore WHERE  ((gruop_id="+"'"+fios.stringFileReader(fios.createFileName("PMMS_Statements", "reports", "portfolioOwner.txt")).split("\\s+")[2].trim()+"'"+") && (instalment_next_due_date<"+"'"+this.getDBCurrentDate()+"'"+"&&  loan_cycle_status="+"'"+"Disbursed"+"'"+")) ORDER BY  instalment_next_due_date DESC";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        while(rsxmt.next()){
        String account=rsxmt.getString("applicant_account_number");
        
        if(loanArrearsUpdated( account)){
            
   arrearsDetails.add( account);
       
        }
          }
          
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
        loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
 
 
 }
      
      
return arrearsDetails;
  
  
  
  }
  private List accountsInArrearsW(){
 
      List arrearsDetails=new ArrayList();
       if( fios.stringFileReader(fios.createFileName("PMMS_Statements", "reports", "portfolioOwner.txt")).equalsIgnoreCase("All")){
      try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
 
          String query = "SELECT  applicant_account_number FROM new_loan_appstore WHERE  loan_cycle_status="+"'"+"Disbursed"+"'"+"ORDER BY  instalment_next_due_date ASC";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        while(rsxmt.next()){
        String account=rsxmt.getString("applicant_account_number");
        if(loanArrearsUpdated( account)){
            
   arrearsDetails.add( account);
       
        }
          }
          
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
       }else{
        try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
 
           String query = "SELECT  applicant_account_number FROM new_loan_appstore WHERE  ((gruop_id="+"'"+fios.stringFileReader(fios.createFileName("PMMS_Statements", "reports", "portfolioOwner.txt"))+"'"+") && (instalment_next_due_date<"+"'"+this.getDBCurrentDate()+"'"+"&&  loan_cycle_status="+"'"+"Disbursed"+"'"+")) ORDER BY  instalment_next_due_date DESC";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        while(rsxmt.next()){
        String account=rsxmt.getString("applicant_account_number");
        if(loanArrearsUpdated( account)){
            
   arrearsDetails.add( account);
       
        }
          }
          
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
        
       
       }
      
      
return arrearsDetails;
  
  
  
  }
  private List disbursedAccountsw(){
 
      List theAccounts=new ArrayList();
     
      try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
 
          String query = "SELECT  applicant_account_number FROM new_loan_appstore WHERE  loan_cycle_status="+"'"+"Disbursed"+"'"+"ORDER BY  instalment_next_due_date DESC";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        while(rsxmt.next()){
//        String account=rsxmt.getString("applicant_account_number");
//        if(loanArrearsUpdated( account)){
            
   theAccounts.add( rsxmt.getString("applicant_account_number"));
       
//        }
          }
          
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
      
//        fios.stringFileWriter(fios.createFileName("test", "testit", theAccounts.get(0).toString()+".txt"),"xx");
//      fios.stringFileWriter(fios.createFileName("test", "testit", theAccounts.get(1).toString()+".txt"),"xx");
//      fios.stringFileWriter(fios.createFileName("test", "testit", theAccounts.get(2).toString()+".txt"),"xx");
//      fios.stringFileWriter(fios.createFileName("test", "testit", theAccounts.get(3).toString()+".txt"),"xx");
//      fios.stringFileWriter(fios.createFileName("test", "testit", theAccounts.get(4).toString()+".txt"),"xx");
return theAccounts;
  
  
  
  }
 private boolean loanArrearsUpdated( String account){
boolean updated=false;int updateN=0;
      
      try {
           Connection cq=loancon.createConnection();
        cq.setAutoCommit(false);
 
          String query = "SELECT  COUNT(instalment_paid) AS instalments FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+account+"' AND (instalment_due_date<="+"'"+getDBCurrentDate()+"'"+" "+"AND"+" "+"NOT instalment_status="+"'"+"P"+"'"+")";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        if(rsxmt.next()){
  updateN=rsxmt.getInt("instalments");
        }
        if(updateN>0){
        updated=true;
        }
          
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
              
              
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
      
     
 
return updated;
 
 
 
 }
  
  private List part1DataArrears(String accountNumbers,int n){
 
      List<String> arrearsDetails1=new ArrayList();
      try {
            Connection cq=loancon.createConnection();
         cq.setAutoCommit(false);
 
          String query = "SELECT  applicant_account_name, total_loanAmount, balance_due FROM new_loan_appstore WHERE  loan_id="+"'"+"newloan"+accountNumbers+"'";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        if(rsxmt.next()){
      arrearsDetails1.add(n+"");      
  arrearsDetails1.add(rsxmt.getString("applicant_account_name"));
  arrearsDetails1.add(rsxmt.getString("total_loanAmount"));
 arrearsDetails1.add((parseDouble(rsxmt.getString("total_loanAmount"))-parseDouble(rsxmt.getString("balance_due")))+"");
      arrearsDetails1.add(rsxmt.getString("balance_due"));      }
          
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
      
      
return  arrearsDetails1;
  
  
  
  }
   private List part2DataArrears(String accountNumber){
 
       int n=0;
    
 List arrears2Details=new ArrayList();
      
      try {
              Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
 
          String query = "SELECT  COUNT(instalment_paid) AS instalments, SUM(instalment_amount) AS amountArrears FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND  instalment_status="+"'"+"NP"+"'";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        if(rsxmt.next()){
  arrears2Details.add(rsxmt.getString("instalments"));
  arrears2Details.add(rsxmt.getString("amountArrears"));
         n=n+1;
        }
          
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
      
       fios.stringFileWriterAppend(fios.createFileName("PMMS_Statements", "reports", n+"thisFile.txt") , arrears2Details.get(0).toString()+","+arrears2Details.get(1).toString());
 
return  arrears2Details;
  
  
  }
   
 public Map<Integer, List<Object>> loanArrearsDetails(String account_number){
   
      Map<Integer, List<Object>> theArrearsDetails= new HashMap();
    

    Double instalment=0.0,chargesAccrued=0.0,totalInstalment=0.0,TotalCharges=0.0,amount=0.0,totalAmount=0.0;int n=0,z=0;  
      try {
             Connection cq=loancon.createConnection();
         cq.setAutoCommit(false);
 
          String query = "SELECT   princimpal_amount,interest_amount,  instalment_amount, instalment_due_date FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+account_number+"' AND  instalment_status="+"'"+"NP"+"'";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
       while(rsxmt.next()){
            List arrearsDetails=new ArrayList();
             n=n+1;
              arrearsDetails.add(n);
     instalment=parseDouble(rsxmt.getString("princimpal_amount"))+parseDouble(rsxmt.getString("interest_amount"));
     chargesAccrued=parseDouble(rsxmt.getString("instalment_amount"))-instalment;
     amount=parseDouble(rsxmt.getString("instalment_amount"));
  arrearsDetails.add(instalment+"");
  arrearsDetails.add(chargesAccrued+"");
   arrearsDetails.add(amount+"");  
    arrearsDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("instalment_due_date")));  
   String  aging=(Math.abs(fmt.diffDays(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("instalment_due_date")), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate()))))+" "+"Days";
    arrearsDetails.add(aging);  
   theArrearsDetails.put(z, arrearsDetails);
    
   totalInstalment=totalInstalment+instalment;
   TotalCharges=TotalCharges+chargesAccrued;
   totalAmount=totalAmount+ amount;
   
   
       z++;
        }
    List arrearsDetails1=new ArrayList();     
     arrearsDetails1.add("Total");
      arrearsDetails1.add( totalInstalment+"");
       arrearsDetails1.add( TotalCharges+"");
         arrearsDetails1.add(totalAmount+"");
          arrearsDetails1.add("-");
           arrearsDetails1.add("-");
      theArrearsDetails.put(z, arrearsDetails1);
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
      
   
 
return theArrearsDetails;
   
   
   
   }

    
  



   public  Map<Integer, List<Object>>loanPortfolio(){
int n=0,z=0;
Double loanAmount=0.0,princimpal=0.0,interest=0.0,amountRemaining=0.0,amountPaid=0.0;List finale=null;
       Map<Integer, List<Object>> staffLoans=new HashMap();
       
      try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
 
          String query = "SELECT  applicant_account_name, total_loanAmount,princimpal_amount,total_interest,balance_due FROM new_loan_appstore WHERE  loan_cycle_status="+"'"+"Disbursed"+"'";
            
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        while(rsxmt.next()){
             List loanDetails=new ArrayList();
            n=n+1;
    loanDetails.add(n);          
loanDetails.add(rsxmt.getString("applicant_account_name"));
loanDetails.add(rsxmt.getString("total_loanAmount"));
loanDetails.add(rsxmt.getString("princimpal_amount"));
loanDetails.add(rsxmt.getString("total_interest"));
loanDetails.add(rsxmt.getString("balance_due"));
loanDetails.add((parseDouble(rsxmt.getString("total_loanAmount"))-parseDouble(rsxmt.getString("balance_due")))+"");
staffLoans.put(z,  loanDetails);
 loanAmount=loanAmount+parseDouble(loanDetails.get(2).toString());
  princimpal=princimpal+parseDouble(loanDetails.get(3).toString());
  interest=interest+parseDouble(loanDetails.get(4).toString());
  amountRemaining=amountRemaining+parseDouble(loanDetails.get(5).toString());
   amountPaid  =  amountPaid+parseDouble(loanDetails.get(6).toString()); 
   z++;
          }
        finale=new ArrayList();
  finale.add("Total");
  finale.add(" ");
  finale.add(loanAmount+"");
  finale.add(princimpal+"");
  finale.add(interest+"");
  finale.add(amountRemaining+"");
  finale.add(amountPaid +"");
 
  
  staffLoans.put(z,finale);    
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
      
      
return staffLoans;

 }
   
  public  Map<Integer, List<Object>> loanApplied(){
int n=0,z=0;
Double loanAmount=0.0,princimpal=0.0,interest=0.0;List finale=null;
       Map<Integer, List<Object>> staffLoans=new HashMap();
       
      try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
   String query = "SELECT  applicant_account_name, total_loanAmount,princimpal_amount,total_interest,total_instalments,instalment_start_date,instalment_end_date FROM new_loan_appstore WHERE  loan_cycle_status="+"'"+"Applied"+"'";
            
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        while(rsxmt.next()){
             List loanDetails=new ArrayList();
            n=n+1;
    loanDetails.add(n);          
loanDetails.add(rsxmt.getString("applicant_account_name"));
loanDetails.add(rsxmt.getString("total_loanAmount"));
loanDetails.add(rsxmt.getString("princimpal_amount"));
loanDetails.add(rsxmt.getString("total_interest"));
loanDetails.add(rsxmt.getString("total_instalments"));
loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("instalment_start_date")));
loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("instalment_end_date")));
staffLoans.put(z,  loanDetails);
 loanAmount=loanAmount+parseDouble(loanDetails.get(2).toString());
  princimpal=princimpal+parseDouble(loanDetails.get(3).toString());
  interest=interest+parseDouble(loanDetails.get(4).toString());
   z++;
          }
        finale=new ArrayList();
  finale.add("Total");
  finale.add(" ");
  finale.add(loanAmount+"");
  finale.add(princimpal+"");
  finale.add(interest+"");
  finale.add("-"+"");
 finale.add("-");
  finale.add("-");
  staffLoans.put(z,finale);    
  
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
      
      
return staffLoans;

 } 
   
  public  Map<Integer, List<Object>> loanApproved(){
int n=0,z=0;
Double loanAmount=0.0,princimpal=0.0,interest=0.0;List finale=null;
       Map<Integer, List<Object>> staffLoans=new HashMap();
       
      try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
   String query = "SELECT  applicant_account_name, total_loanAmount,princimpal_amount,total_interest,total_instalments,instalment_start_date,instalment_end_date FROM new_loan_appstore WHERE  loan_cycle_status="+"'"+"Approved"+"'";
            
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        while(rsxmt.next()){
             List loanDetails=new ArrayList();
            n=n+1;
    loanDetails.add(n);          
loanDetails.add(rsxmt.getString("applicant_account_name"));
loanDetails.add(rsxmt.getString("total_loanAmount"));
loanDetails.add(rsxmt.getString("princimpal_amount"));
loanDetails.add(rsxmt.getString("total_interest"));
loanDetails.add(rsxmt.getString("total_instalments"));
loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("instalment_start_date")));
loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("instalment_end_date")));
staffLoans.put(z,  loanDetails);
 loanAmount=loanAmount+parseDouble(loanDetails.get(2).toString());
  princimpal=princimpal+parseDouble(loanDetails.get(3).toString());
  interest=interest+parseDouble(loanDetails.get(4).toString());
   z++;
          }
        finale=new ArrayList();
  finale.add("Total");
  finale.add(" ");
  finale.add(loanAmount+"");
  finale.add(princimpal+"");
  finale.add(interest+"");
  finale.add("-"+"");
 finale.add("-");
  finale.add("-");
  staffLoans.put(z,finale);    
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
      
      
return staffLoans;

 } 
   public  Map<Integer, List<Object>> loanDisbursed(){
int n=0,z=0;
Double loanAmount=0.0,princimpal=0.0,interest=0.0;List finale=null;
       Map<Integer, List<Object>> staffLoans=new HashMap();
       
      try {
             Connection cq=loancon.createConnection();
         cq.setAutoCommit(false);
   String query = "SELECT  applicant_account_name, total_loanAmount,princimpal_amount,total_interest,total_instalments,instalment_start_date,instalment_end_date FROM new_loan_appstore WHERE  loan_cycle_status="+"'"+"Disbursed"+"'";
            
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        while(rsxmt.next()){
             List loanDetails=new ArrayList();
            n=n+1;
    loanDetails.add(n);          
loanDetails.add(rsxmt.getString("applicant_account_name"));
loanDetails.add(rsxmt.getString("total_loanAmount"));
loanDetails.add(rsxmt.getString("princimpal_amount"));
loanDetails.add(rsxmt.getString("total_interest"));
loanDetails.add(rsxmt.getString("total_instalments"));
loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("instalment_start_date")));
loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("instalment_end_date")));
staffLoans.put(z,  loanDetails);
 loanAmount=loanAmount+parseDouble(loanDetails.get(2).toString());
  princimpal=princimpal+parseDouble(loanDetails.get(3).toString());
  interest=interest+parseDouble(loanDetails.get(4).toString());
   z++;
          }
        finale=new ArrayList();
  finale.add("Total");
  finale.add(" ");
  finale.add(loanAmount+"");
  finale.add(princimpal+"");
  finale.add(interest+"");
  finale.add("-"+"");
 finale.add("-");
  finale.add("-");
  staffLoans.put(z,finale);    
  
        rsxmt.close();
        psxmt.close();
      cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
      
      
return staffLoans;

 } 
   
    public  Map<Integer, List<Object>> loansDueReport(){
        
int n=0,z=0,k=0;

Double loanAmount=0.0,princimpal=0.0,interest=0.0,isntalment=0.0,actaualInstalment=0.0,accruedCharges=0.0,interestT=0.0,princimpalT=0.0;List finale=null;
       Map<Integer, List<Object>> loansDue=new HashMap();
       List bdeta=this.borrowDetails();
       while(k<bdeta.size()){
           
      try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
      
          
          
   String query = "SELECT princimpal_amount,interest_amount,  instalment_amount, instalment_due_date FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+bdeta.get(k)+"' AND NOT" +"(instalment_status="+"'P')";
          
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        if(rsxmt.first()){
             List loanDetails=new ArrayList();
            n=n+1;
    loanDetails.add(n);          
loanDetails.add(this.AccountName(bdeta.get(k).toString()));
loanDetails.add(rsxmt.getString("princimpal_amount"));
loanDetails.add(rsxmt.getString("interest_amount"));
isntalment=(parseDouble(rsxmt.getString("princimpal_amount"))+parseDouble(rsxmt.getString("interest_amount")));
actaualInstalment=parseDouble(rsxmt.getString("instalment_amount").replace(",", ""));
if(actaualInstalment>=isntalment){

accruedCharges=actaualInstalment-isntalment;
}else{
    
accruedCharges=0.0;

}
loanDetails.add(isntalment+"");

loanDetails.add(accruedCharges+"");
loanDetails.add(actaualInstalment+"");
loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("instalment_due_date")));
loanDetails.add((fmt.diffDays(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("instalment_due_date")), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate())))+" "+"Days");
loansDue.put(k,  loanDetails);
princimpalT=princimpalT+parseDouble(loanDetails.get(2).toString());
interestT=interestT+parseDouble(loanDetails.get(3).toString());
 loanAmount=loanAmount+parseDouble(loanDetails.get(4).toString());
  princimpal=princimpal+parseDouble(loanDetails.get(5).toString());
  interest=interest+parseDouble(loanDetails.get(6).toString().replace(",", ""));
  
          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection( cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
      k++;
       }
              finale=new ArrayList();
  finale.add("Total");
  finale.add(" ");
    finale.add(princimpalT+"");
  finale.add(interestT+"");
  finale.add(loanAmount+"");
  finale.add(princimpal+"");
  finale.add(interest+"");
  finale.add("-"+"");
 finale.add("-");

loansDue.put(k,finale);  
return loansDue;

 } 
  public  Map<Integer, List<Object>> loansDueReport1(){
        
int n=0,k=0;

Double loanAmount=0.0,princimpal=0.0,interest=0.0,isntalment=0.0,actaualInstalment=0.0,accruedCharges=0.0,interestT=0.0,princimpalT=0.0;List finale=null;
       Map<Integer, List<Object>> loansDue=new HashMap();
       
       List bdeta=this.borrowDetails();
       
     int bda=bdeta.size();
     
     while(k<bda){ 
         
      try {
            Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
     
          
          
   String query = "SELECT princimpal_amount,interest_amount,  instalment_amount, instalment_due_date FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+bdeta.get(k)+"' AND ( NOT instalment_status="+"'"+"P"+"'"+")";  
          
     
          PreparedStatement psxmt =  cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
       if(rsxmt.next()){
           
             List loanDetails=new ArrayList();
            n=n+1;
    loanDetails.add(n);          
loanDetails.add(this.AccountName(bdeta.get(k).toString()));
loanDetails.add(rsxmt.getString("princimpal_amount"));
loanDetails.add(rsxmt.getString("interest_amount"));
isntalment=(parseDouble(rsxmt.getString("princimpal_amount"))+parseDouble(rsxmt.getString("interest_amount")));
actaualInstalment=parseDouble(rsxmt.getString("instalment_amount").replace(",", ""));
if(actaualInstalment>=isntalment){

accruedCharges=actaualInstalment-isntalment;
}else{
    
accruedCharges=0.0;

}
loanDetails.add(isntalment+"");

loanDetails.add(accruedCharges+"");
loanDetails.add(actaualInstalment+"");
loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("instalment_due_date")));
loanDetails.add((fmt.diffDays(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("instalment_due_date")), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate())))+" "+"Days");
if(!loanDetails.isEmpty()){
loansDue.put(k,  loanDetails);

princimpalT=princimpalT+parseDouble(loanDetails.get(2).toString());
interestT=interestT+parseDouble(loanDetails.get(3).toString());
 loanAmount=loanAmount+parseDouble(loanDetails.get(4).toString());
  princimpal=princimpal+parseDouble(loanDetails.get(5).toString());
  interest=interest+parseDouble(loanDetails.get(6).toString().replace(",", ""));
} else{
bda=bda-1;
bdeta.remove(k);
k--;

}

            
          }
   
  
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
         loancon.closeConnection( cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
 
    k++;
       }    
              finale=new ArrayList();
  finale.add("Total");
  finale.add(" ");
    finale.add(princimpalT+"");
  finale.add(interestT+"");
  finale.add(loanAmount+"");
  finale.add(princimpal+"");
  finale.add(interest+"");
  finale.add("-"+"");
 finale.add("-");

loansDue.put(k,finale);  
return loansDue;

 }  
    private List borrowDetails(){
 
      List borrowDetails=new ArrayList();
      try {
            Connection cq=loancon.createConnection();
         cq.setAutoCommit(false);
 
          String query = "SELECT  applicant_account_number FROM new_loan_appstore WHERE loan_cycle_status="+"'"+"Disbursed"+"'";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        while(rsxmt.next()){
              
    borrowDetails.add( rsxmt.getString("applicant_account_number"));
  
        
          }
          
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }

return borrowDetails;

  }
      private Map borrowDetails2(){
 
     Map<Integer, List> det=new HashMap(); int n=0;
      try {
             Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
 
          String query = "SELECT  applicant_account_number,balance_due FROM new_loan_appstore WHERE loan_cycle_status="+"'"+"Disbursed"+"'";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        while(rsxmt.next()){
            
           List borrowDetails=new ArrayList();
           
    borrowDetails.add(0, rsxmt.getString("applicant_account_number"));
  borrowDetails.add(1, rsxmt.getString("balance_due"));
        
        det.put(n, borrowDetails);
        n++;
        }
          
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }

return det;

  }
     private Map borrowDisbursed2(){
 
     Map<Integer, List> det=new HashMap(); int n=0;
      try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
 
          String query = "SELECT  applicant_account_number,princimpal_amount FROM new_loan_appstore WHERE loan_cycle_status="+"'"+"Disbursed"+"'";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        while(rsxmt.next()){
            
           List borrowDetails=new ArrayList();
           
    borrowDetails.add(0, rsxmt.getString("applicant_account_number"));
  borrowDetails.add(1, rsxmt.getString("princimpal_amount"));
        
        det.put(n, borrowDetails);
        n++;
        }
          
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }

return det;

  }  
      
      
     private Map borrowApproved2(){
 
     Map<Integer, List> det=new HashMap(); int n=0;
      try {
            Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
 
          String query = "SELECT  applicant_account_number,princimpal_amount FROM new_loan_appstore WHERE loan_cycle_status="+"'"+"Approved"+"'";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        while(rsxmt.next()){
            
           List borrowDetails=new ArrayList();
           
    borrowDetails.add(0, rsxmt.getString("applicant_account_number"));
  borrowDetails.add(1, rsxmt.getString("princimpal_amount"));
        
        det.put(n, borrowDetails);
        n++;
        }
          
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }

return det;

  } 
     private Map borrowArrears2(){
 
         Map<Integer, List> det=new HashMap(); int n=0;
      try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
 
          String query = "SELECT  applicant_account_number,instalment_amount FROM new_loan_appstore WHERE  instalment_next_due_date<"+"'"+this.getDBCurrentDate()+"'"+"&&  loan_cycle_status="+"'"+"Disbursed"+"'";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        while(rsxmt.next()){
            
           List borrowDetails=new ArrayList();
           
    borrowDetails.add(0, rsxmt.getString("applicant_account_number"));
  borrowDetails.add(1, rsxmt.getString("instalment_amount"));
        
        det.put(n, borrowDetails);
        n++;
        }
          
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }

return det;

  } 
     
     private Map borrowLoansDue2(){
 

           Map<Integer, List> det=new HashMap(); int n=0;
      try {
            Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
 
          String query = "SELECT  applicant_account_number,instalment_amount FROM new_loan_appstore WHERE  instalment_next_due_date="+"'"+this.getDBCurrentDate()+"'"+"&&  loan_cycle_status="+"'"+"Disbursed"+"'";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        while(rsxmt.next()){
            
           List borrowDetails=new ArrayList();
           
    borrowDetails.add(0, rsxmt.getString("applicant_account_number"));
  borrowDetails.add(1, rsxmt.getString("instalment_amount"));
        
        det.put(n, borrowDetails);
        n++;
        }
          
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }

return det;


  } 
       private Map borrowApplied2(){
 
     Map<Integer, List> det=new HashMap(); int n=0;
      try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
 
          String query = "SELECT  applicant_account_number,princimpal_amount FROM new_loan_appstore WHERE loan_cycle_status="+"'"+"Applied"+"'";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        while(rsxmt.next()){
            
           List borrowDetails=new ArrayList();
           
    borrowDetails.add(0, rsxmt.getString("applicant_account_number"));
  borrowDetails.add(1, rsxmt.getString("princimpal_amount"));
        
        det.put(n, borrowDetails);
        n++;
        }
          
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }

return det;

  }
  public String getDBCurrentDate() {
    

    String thistime=" ";
    thistime=fmt.dateConverterForDatabase(System.currentTimeMillis( ));

return thistime;
}
  
   
  public String AccountName(String accountNumber){

//  return   fios.stringFileReader(fios.createFileName("accountManagement", "accountName", "accountName"+accountNumber+".txt"));

  String nam = "";
          try {
           Connection cq=quaryObj.createConnection(); 
            cq.setAutoCommit(false);
              String query = "SELECT  account_name FROM  account_created_store WHERE account_number="+"'"+accountNumber+"'";
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
              
             
              while (rs.next()) {
                       
                    nam= rs.getString("account_name");
                      
                  
                    }
             cq.setAutoCommit(true);     
            quaryObj.closeConnection(cq);  
                    
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }

return nam;
  
  }
  
   public Map<Integer, List<Object>> loansDueWriteOff(){
       int k=0; double princimpalT=0.0,interestT=0.0,princimpalPaidT=0.0, princimpalRemainT=0.0;int n=0;
       Map<Integer, List<Object>> loansDueWriteOff=new HashMap();
       List accounts=accountsInArrears();
       int arrearsSize=accountsInArrears().size();
       if(!accounts.isEmpty()){
       while(k<arrearsSize){
     List loanWriteOff=new ArrayList();      
    int  aging=parseInt(actualAgingTime(accounts.get(k).toString()));
    
    int  cutoffAging=parseInt(cuttOffAging());
    
    if(aging>=cutoffAging){
        
  try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
     
          
          
     String query = "SELECT applicant_account_name,princimpal_amount,total_interest FROM new_loan_appstore WHERE applicant_account_number="+accounts.get(k).toString();
  
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        if(rsxmt.next()){
            n=n+1;
    loanWriteOff.add(n);          
loanWriteOff.add(rsxmt.getString("applicant_account_name"));
loanWriteOff.add(rsxmt.getString("princimpal_amount"));
loanWriteOff.add("total_interest");
loanWriteOff.add(princimpalPaid(accounts.get(k).toString()));
loanWriteOff.add((parseDouble(rsxmt.getString("princimpal_amount"))-parseDouble(princimpalPaid(accounts.get(k).toString())))+"");
loanWriteOff.add(aging+" "+"Days");
loansDueWriteOff.put(k,  loanWriteOff);
          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
  princimpalT=princimpalT+parseDouble(loanWriteOff.get(2).toString());
interestT=interestT+parseDouble(loanWriteOff.get(3).toString());
princimpalPaidT=princimpalPaidT+parseDouble(loanWriteOff.get(4).toString());
princimpalRemainT=princimpalRemainT+parseDouble(loanWriteOff.get(4).toString());
     
       }
    k++;
       }
       
   
     List finalTly= new ArrayList();
     finalTly.add("Total");
     finalTly.add("-");
     finalTly.add(princimpalT+"");
      finalTly.add(interestT+"");
      finalTly.add(princimpalPaidT+"");
      finalTly.add(princimpalRemainT+"");
       finalTly.add("-");
  loansDueWriteOff.put(k, finalTly);
       }
 return   loansDueWriteOff;
   }
  
  private String princimpalPaid(String accountNumber){
  String pp="";
  
      
      try {
              Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
 
          String query = "SELECT   SUM(princimpal_amount) AS principalPaid FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND  instalment_status="+"'"+"P"+"'";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        while(rsxmt.next()){
            if(rsxmt.wasNull()){
            pp="0.0";
            }else{
   pp=rsxmt.getString("principalPaid");
            }
        }
          
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }

 return pp; 
  }

    
  private String cuttOffAging(){
  String days="";
  switch(fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "writeOff.txt")).split("[,]", 2)[1].replace(",", "")){
      case "Days":
        days=  fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "writeOff.txt")).split("[,]", 2)[0];
          
          break;
case "Weeks":
  days=((parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "writeOff.txt")).split("[,]", 2)[0])*7))+"";  
    break;
case "Months":
    
  days=((parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "writeOff.txt")).split("[,]", 2)[0])*30))+"";     
    break;
case "Quaters":
   days=((parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "writeOff.txt")).split("[,]", 2)[0])*90))+"";    
    break;
case "Years":
       days=((parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "writeOff.txt")).split("[,]", 2)[0])*360))+""; 
    break;
  
  }
  
  
  return days;
  }
  public String actualAgingTime(String account_number ){
       String  aging="";
  try {
        Connection cq=loancon.createConnection();   
      cq.setAutoCommit(false);
 
          String query = "SELECT instalment_due_date FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+account_number+"' AND  instalment_status="+"'"+"NP"+"'";

          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        if(rsxmt.first()){
     
   aging=(Math.abs(fmt.diffDays(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("instalment_due_date")), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate()))))+"";

        }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
      
  return aging;
  
  
  }
  
  public  Map<Integer, List<Object>>  summuryLoanP(String startDate, String endDate){
  List infoSum=new ArrayList();Map finaSum=new HashMap();
  
                if(valuesThere(startDate,endDate)){
                    
            infoSum.add(NnewBorrowers(startDate,endDate));
             infoSum.add(NnoLoansAppl(startDate,endDate));
            infoSum.add(VLoansAppl(startDate,endDate));
             infoSum.add(NnoLoansAppr(startDate,endDate));
            infoSum.add(VLoansAppr(startDate,endDate));
            infoSum.add(NnoLoansDis(startDate,endDate));
            infoSum.add(VLoansDis(startDate,endDate));
             infoSum.add(NnoLoansComp(startDate,endDate));
            infoSum.add(VLoansComp(startDate,endDate));
                }else{
                infoSum.add("0");
            infoSum.add("0");
            infoSum.add("0.0");
            infoSum.add("0");
            infoSum.add("0.0");
            infoSum.add("0");
            infoSum.add("0.0");
             infoSum.add("0");
            infoSum.add("0.0");
                }
  finaSum.put(0,infoSum);
  return finaSum;
  
  }
  
  
  private boolean valuesThere(String startDate, String endDate){
  boolean data2=false;String startDa="",endDa="";
      
 
            
          
        try {
            startDa=fmt.forDatabaseWithFullYearBeginningWithDate(sdf.format(fmt.getExDateIncreamented(sdf.parse(startDate),"30")));
        } catch (ParseException ex) {
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            endDa=fmt.forDatabaseWithFullYearBeginningWithDate(sdf.format(fmt.getExDateIncreamented(sdf.parse(endDate),"30")));
        } catch (ParseException ex) {
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
   
                
              Connection cq=loancon.createConnection();     
           
                
                String query = "SELECT COUNT(loan_id) AS new_loans FROM"+" "+"new_loan_appstore WHERE(instalment_start_date>="+"'"+startDa+"'"+"AND instalment_start_date<="+"'"+endDa+"'"+")";
                PreparedStatement psxmt = null;
        try {
            
            cq.setAutoCommit(false); 
            psxmt = loancon.createConnection().prepareStatement(query);
            cq.setAutoCommit(true);
         loancon.closeConnection(cq);
        } catch (SQLException ex) {
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
                ResultSet rsxmt = null;
        try {
            rsxmt = psxmt.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            while (rsxmt.next()){
                if(!rsxmt.getString("new_loans").equalsIgnoreCase("0")){
                    data2=true;
                }
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rsxmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            psxmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            cq.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
                loancon.closeConnection(cq);
          
                
          
        
       
        return data2;
  
  }
  
  private String NnewBorrowers(String startDate, String endDate){
  
            String data2="",startDa="",endDa="";

            try {
                startDa=fmt.forDatabaseWithFullYearBeginningWithDate(sdf.format(fmt.getExDateIncreamented(sdf.parse(startDate),"30")));
            } catch (ParseException ex) {
                Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                endDa=fmt.forDatabaseWithFullYearBeginningWithDate(sdf.format(fmt.getExDateIncreamented(sdf.parse(endDate),"30")));
            } catch (ParseException ex) {
                Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
                try {
            
            Connection cq=loancon.createConnection();
            cq.setAutoCommit(false);   
            String query = "SELECT COUNT(loan_id) AS new_loans FROM"+" "+"new_loan_appstore WHERE(instalment_start_date>="+"'"+startDa+"'"+"AND instalment_start_date<="+"'"+endDa+"'"+")";
            PreparedStatement psxmt = cq.prepareStatement(query);
            ResultSet rsxmt = psxmt.executeQuery();
            while (rsxmt.next()){
                if(rsxmt.getString("new_loans").equalsIgnoreCase("0")){
                    data2="0";
                }else{
                    data2=rsxmt.getString("new_loans");
                }
                
            }
            rsxmt.close();
      
            psxmt.close();
            cq.setAutoCommit(true);
            loancon.closeConnection(cq);
          
        } catch (SQLException ex) {
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
            return data2;
  }
  
  
  private String NnoLoansDis(String startDate, String endDate){
  
            String data2="",startDa="",endDa="";
     
            try {
                startDa=fmt.forDatabaseWithFullYearBeginningWithDate(sdf.format(fmt.getExDateIncreamented(sdf.parse(startDate),"30")));
            } catch (ParseException ex) {
                Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }          
            try {
                endDa=fmt.forDatabaseWithFullYearBeginningWithDate(sdf.format(fmt.getExDateIncreamented(sdf.parse(endDate),"30")));
            } catch (ParseException ex) {
                Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
            
              try {
  
            Connection cq=loancon.createConnection();
            cq.setAutoCommit(false);
            String query = "SELECT COUNT(loan_id) AS new_loans FROM"+" "+"new_loan_appstore WHERE((instalment_start_date>="+"'"+startDa+"'"+"AND instalment_start_date<="+"'"+endDa+"'"+")"+""+"AND"+""+"( loan_cycle_status="+"'"+"Disbursed"+"'"+"))";
             PreparedStatement psxmt = cq.prepareStatement(query);
            ResultSet rsxmt = psxmt.executeQuery();
            while (rsxmt.next()){
                if(rsxmt.getString("new_loans").equalsIgnoreCase("0")){
                    data2="0";
                }else{
                    data2=rsxmt.getString("new_loans");
                }
                
            }
  
            rsxmt.close();

            psxmt.close();
 
            cq.setAutoCommit(true);
            loancon.closeConnection(cq);
 
        } catch (SQLException ex) {
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
      return data2;
  
 
  }
  
  private String VLoansDis(String startDate, String endDate){
  
  
      
            
            
            String data2="",startDa="",endDa="";
            
            
            try {
                startDa=fmt.forDatabaseWithFullYearBeginningWithDate(sdf.format(fmt.getExDateIncreamented(sdf.parse(startDate),"30")));
            } catch (ParseException ex) {
                Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                endDa=fmt.forDatabaseWithFullYearBeginningWithDate(sdf.format(fmt.getExDateIncreamented(sdf.parse(endDate),"30")));
            } catch (ParseException ex) {
                Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
            
              try {
            Connection cq=loancon.createConnection();
             cq.setAutoCommit(false);
            
            String query = "SELECT COUNT(loan_id) AS new_loanss,SUM(total_loanAmount) AS new_loans FROM"+" "+"new_loan_appstore WHERE((instalment_start_date>="+"'"+startDa+"'"+"AND instalment_start_date<="+"'"+endDa+"'"+")"+""+"AND"+""+"( loan_cycle_status="+"'"+"Disbursed"+"'"+"))";
            
            
            PreparedStatement psxmt=cq.prepareStatement(query);
            ResultSet rsxmt  = psxmt.executeQuery();
            while (rsxmt.next()){
                if(rsxmt.getString("new_loanss").equalsIgnoreCase("0")){
                    data2="0.0";
                }else{
                    data2=rsxmt.getString("new_loans");
                }
                
            }
            rsxmt.close();
            
            psxmt.close();

            cq.setAutoCommit(true);
            loancon.closeConnection(cq);
     
        } catch (SQLException ex) {
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
  
   return data2;
  }
  private String NnoLoansAppl(String startDate, String endDate){
  
  
       
            
            
            String data2="",startDa="",endDa="";
            
            
            
            
            try {
                startDa=fmt.forDatabaseWithFullYearBeginningWithDate(sdf.format(fmt.getExDateIncreamented(sdf.parse(startDate),"30")));
            } catch (ParseException ex) {
                Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                endDa=fmt.forDatabaseWithFullYearBeginningWithDate(sdf.format(fmt.getExDateIncreamented(sdf.parse(endDate),"30")));
            } catch (ParseException ex) {
                Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
            
             try {
            Connection cq=loancon.createConnection();
            
             cq.setAutoCommit(false);
            
            String query = "SELECT COUNT(loan_id) AS new_loans FROM"+" "+"new_loan_appstore WHERE((instalment_start_date>="+"'"+startDa+"'"+"AND instalment_start_date<="+"'"+endDa+"'"+")"+""+"AND"+""+"( loan_cycle_status="+"'"+"Applied"+"'"+"))";
            
            PreparedStatement psxmt = cq.prepareStatement(query);
            
            ResultSet rsxmt = psxmt.executeQuery();
            
            while (rsxmt.next()){
                if(rsxmt.getString("new_loans").equalsIgnoreCase("0")){
                    data2="0";
                }else{
                    data2=rsxmt.getString("new_loans");
                }
                
            }

            rsxmt.close();
 
            psxmt.close();

            cq.setAutoCommit(true);
            loancon.closeConnection(cq);
            
 
        } catch (SQLException ex) {
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
  

    return data2;
  
  }
  private String VLoansAppl(String startDate, String endDate){
  
  
             
             
             String data2="",startDa="",endDa="";
             
             try {
                 startDa=fmt.forDatabaseWithFullYearBeginningWithDate(sdf.format(fmt.getExDateIncreamented(sdf.parse(startDate),"30")));
             } catch (ParseException ex) {
                 Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
             }
             try {
                 endDa=fmt.forDatabaseWithFullYearBeginningWithDate(sdf.format(fmt.getExDateIncreamented(sdf.parse(endDate),"30")));
             } catch (ParseException ex) {
                 Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
             }
             
             
         try {
             Connection cq=loancon.createConnection();
             cq.setAutoCommit(false);
             String query = "SELECT COUNT(loan_id) AS new_loanss,SUM(total_loanAmount) AS new_loans FROM"+" "+"new_loan_appstore WHERE((instalment_start_date>="+"'"+startDa+"'"+"AND instalment_start_date<="+"'"+endDa+"'"+")"+""+"AND"+""+"( loan_cycle_status="+"'"+"Applied"+"'"+"))";
             
             PreparedStatement psxmt= cq.prepareStatement(query);
             
             ResultSet rsxmt = psxmt.executeQuery();
             
             while (rsxmt.next()){
                 if(rsxmt.getString("new_loanss").equalsIgnoreCase("0")){
                     data2="0.0";
                 }else{
                     data2=rsxmt.getString("new_loans");
                 }
                 
             }
             
             rsxmt.close();
             
             psxmt.close();
             
             cq.setAutoCommit(true);
             
             loancon.closeConnection(cq);
    
         } catch (SQLException ex) {
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
  
  return data2;
  }
  
  private String NnoLoansAppr(String startDate, String endDate){
  
  
      
            
            
            String data2="",startDa="",endDa="";
            
            
            
            
            try {
                startDa=fmt.forDatabaseWithFullYearBeginningWithDate(sdf.format(fmt.getExDateIncreamented(sdf.parse(startDate),"30")));
            } catch (ParseException ex) {
                Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                endDa=fmt.forDatabaseWithFullYearBeginningWithDate(sdf.format(fmt.getExDateIncreamented(sdf.parse(endDate),"30")));
            } catch (ParseException ex) {
                Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
            
              try {
            Connection cq=loancon.createConnection();
            
            cq.setAutoCommit(false);
            String query = "SELECT COUNT(loan_id) AS new_loans FROM"+" "+"new_loan_appstore WHERE((instalment_start_date>="+"'"+startDa+"'"+"AND instalment_start_date<="+"'"+endDa+"'"+")"+""+"AND"+""+"( loan_cycle_status="+"'"+"Approved"+"'"+"))";
            
            PreparedStatement psxmt= cq.prepareStatement(query);
            
            
            
            ResultSet rsxmt = psxmt.executeQuery();
            
            
            while (rsxmt.next()){
                if(rsxmt.getString("new_loans").equalsIgnoreCase("0")){
                    data2="0";
                }else{
                    data2=rsxmt.getString("new_loans");
                }
                
            }
            
            
            rsxmt.close();
            
            
            psxmt.close();
            
            
            cq.setAutoCommit(true);
            
            loancon.closeConnection(cq);
     
            
        } catch (SQLException ex) {
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

  return data2;

  }
  private String VLoansAppr(String startDate, String endDate){

            String data2="",startDa="",endDa="";
            
            
            try {
                startDa=fmt.forDatabaseWithFullYearBeginningWithDate(sdf.format(fmt.getExDateIncreamented(sdf.parse(startDate),"30")));
            } catch (ParseException ex) {
                Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                endDa=fmt.forDatabaseWithFullYearBeginningWithDate(sdf.format(fmt.getExDateIncreamented(sdf.parse(endDate),"30")));
            } catch (ParseException ex) {
                Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
            
               try {
            Connection cq=loancon.createConnection();
            cq.setAutoCommit(false);
            
            String query = "SELECT COUNT(loan_id) AS new_loanss, SUM(total_loanAmount) AS new_loans FROM"+" "+"new_loan_appstore WHERE((instalment_start_date>="+"'"+startDa+"'"+"AND instalment_start_date<="+"'"+endDa+"'"+")"+""+"AND"+""+"( loan_cycle_status="+"'"+"Approved"+"'"+"))";
            
            PreparedStatement psxmt = cq.prepareStatement(query);
            
            ResultSet rsxmt  = psxmt.executeQuery();
            
            while (rsxmt.next()){
                if(rsxmt.getString("new_loanss").equalsIgnoreCase("0")){
                    data2="0.0";
                }else{
                    data2=rsxmt.getString("new_loans");
                }
                
            }
    
            rsxmt.close();
     
            psxmt.close();
            
            cq.setAutoCommit(true);
            
            loancon.closeConnection(cq);
    
        } catch (SQLException ex) {
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    return data2;
  
  }
  
  private String NnoLoansComp(String startDate, String endDate){
  
  
   
            
            
            String data2="",startDa="",endDa="";
            
            
            
            
            try {
                startDa=fmt.forDatabaseWithFullYearBeginningWithDate(sdf.format(fmt.getExDateIncreamented(sdf.parse(startDate),"30")));
            } catch (ParseException ex) {
                Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                endDa=fmt.forDatabaseWithFullYearBeginningWithDate(sdf.format(fmt.getExDateIncreamented(sdf.parse(endDate),"30")));
            } catch (ParseException ex) {
                Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
            
                 try {
            Connection cq=loancon.createConnection();
            
            cq.setAutoCommit(false);
            String query = "SELECT COUNT(loan_id) AS new_loans FROM"+" "+"new_loan_appstore WHERE((instalment_start_date>="+"'"+startDa+"'"+"AND instalment_start_date<="+"'"+endDa+"'"+")"+""+"AND"+""+"( loan_cycle_status="+"'"+"Completed"+"'"+"))";
            
            PreparedStatement psxmt = cq.prepareStatement(query);
            
            ResultSet rsxmt = psxmt.executeQuery();
            
            while (rsxmt.next()){
                if(rsxmt.getString("new_loans").equalsIgnoreCase("0")){
                    data2="0";
                }else{
                    data2=rsxmt.getString("new_loans");
                }
                
            }
     
            rsxmt.close();
    
            psxmt.close();
            
            cq.setAutoCommit(true);
            
            loancon.closeConnection(cq);
      
        } catch (SQLException ex) {
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
 
  return data2;

  }
  private String VLoansComp(String startDate, String endDate){
  
  
       
            
            
            String data2="",startDa="",endDa="";
            
            
            try {
                startDa=fmt.forDatabaseWithFullYearBeginningWithDate(sdf.format(fmt.getExDateIncreamented(sdf.parse(startDate),"30")));
            } catch (ParseException ex) {
                Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                endDa=fmt.forDatabaseWithFullYearBeginningWithDate(sdf.format(fmt.getExDateIncreamented(sdf.parse(endDate),"30")));
            } catch (ParseException ex) {
                Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
             try {
            Connection cq=loancon.createConnection();
             cq.setAutoCommit(false);
            String query = "SELECT COUNT(loan_id) AS new_loanss, SUM(total_loanAmount) AS new_loans FROM"+" "+"new_loan_appstore WHERE((instalment_start_date>="+"'"+startDa+"'"+"AND instalment_start_date<="+"'"+endDa+"'"+")"+""+"AND"+""+"( loan_cycle_status="+"'"+"Completed"+"'"+"))";
            
            PreparedStatement psxmt = cq.prepareStatement(query);
            
            ResultSet rsxmt = psxmt.executeQuery();
            
            while (rsxmt.next()){
                if(rsxmt.getString("new_loanss").equalsIgnoreCase("0")){
                    data2="0.0";
                }else{
                    data2=rsxmt.getString("new_loans");
                }
                
            }
            
            
            rsxmt.close();
      
            psxmt.close();
    
            cq.setAutoCommit(true);
            
            loancon.closeConnection(cq);
 
            
        } catch (SQLException ex) {
            Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
  
   return data2;
  }
  
  public  Map<Integer, List<Object>>  summuryLoanNP(){

  Map finaSum=new HashMap();
  finaSum.put(0, loanTPort());
  finaSum.put(1, loansTApplied());
  finaSum.put(2, loansTApproved());
  finaSum.put(3, loansTDisbursed());
  finaSum.put(4, loansTDue());
  finaSum.put(5, loansTArrears());
//  finaSum.put(6, loansTDueForWriteOff());
//   finaSum.put(7, loansTWrittedOff());
//   finaSum.put(8, loansTCollected());
  return finaSum;
  
  }
  
  
  
  
  private List loanTPort(){
  
  List items= new ArrayList();int lp=0;
  
Map<Integer, List> portfolioAccounts=this.borrowDetails2();
  List males= new ArrayList();
  List females= new ArrayList();
   List maleAmounts= new ArrayList();
  List femaleAmounts= new ArrayList();
  List totalAmounts= new ArrayList();
  while(lp<portfolioAccounts.size()){
  

      if(this.isFemaleBorrower(portfolioAccounts.get(lp).get(0).toString())){
      
      females.add(portfolioAccounts.get(lp).get(0).toString());
      femaleAmounts.add(portfolioAccounts.get(lp).get(1).toString());
      }
      if(this.isMaleBorrower(portfolioAccounts.get(lp).get(0).toString())){
      males.add(portfolioAccounts.get(lp).get(0).toString());
      maleAmounts.add(portfolioAccounts.get(lp).get(1).toString());
  }
       totalAmounts.add(portfolioAccounts.get(lp).get(1).toString());
      lp++;
  }
       
  items.add("Loan Portfolio");
  items.add(males.size());
  items.add(sumOfListItems(maleAmounts));
  items.add(females.size());
  items.add(sumOfListItems(femaleAmounts));
  items.add(portfolioAccounts.size());
  items.add(sumOfListItems(totalAmounts));
 return items; 
  }
   private List loansTDue(){
  
  List items= new ArrayList();int lp=0;
  
Map<Integer, List> loansDueAccounts=this.borrowLoansDue2();
  List males= new ArrayList();
  List females= new ArrayList();
   List maleAmounts= new ArrayList();
  List femaleAmounts= new ArrayList();
  List totalAmounts= new ArrayList();
  while(lp<loansDueAccounts.size()){
  

      if(this.isFemaleBorrower(loansDueAccounts.get(lp).get(0).toString())){
      
      females.add(loansDueAccounts.get(lp).get(0).toString());
      femaleAmounts.add(loansDueAccounts.get(lp).get(1).toString());
      }
      if(this.isMaleBorrower(loansDueAccounts.get(lp).get(0).toString())){
      males.add(loansDueAccounts.get(lp).get(0).toString());
      maleAmounts.add(loansDueAccounts.get(lp).get(1).toString());
  }
       totalAmounts.add(loansDueAccounts.get(lp).get(1).toString());
      lp++;
  }
       
  items.add("Loans Due");
  items.add(males.size());
  items.add(sumOfListItems(maleAmounts));
  items.add(females.size());
  items.add(sumOfListItems(femaleAmounts));
  items.add(loansDueAccounts.size());
  items.add(sumOfListItems(totalAmounts));
 return items; 
  }
  
  private List loansTApproved(){
  
  List items= new ArrayList();int lp=0;
  
Map<Integer, List> approvedLoanAccounts=this.borrowApproved2();
  List males= new ArrayList();
  List females= new ArrayList();
   List maleAmounts= new ArrayList();
  List femaleAmounts= new ArrayList();
  List totalAmounts= new ArrayList();
  while(lp<approvedLoanAccounts.size()){
  

      if(this.isFemaleBorrower(approvedLoanAccounts.get(lp).get(0).toString())){
      
      females.add(approvedLoanAccounts.get(lp).get(0).toString());
      femaleAmounts.add(approvedLoanAccounts.get(lp).get(1).toString());
      }
      if(this.isMaleBorrower(approvedLoanAccounts.get(lp).get(0).toString())){
      males.add(approvedLoanAccounts.get(lp).get(0).toString());
      maleAmounts.add(approvedLoanAccounts.get(lp).get(1).toString());
  }
       totalAmounts.add(approvedLoanAccounts.get(lp).get(1).toString());
      lp++;
  }
         
  items.add("Loans Approved");
  items.add(males.size());
  items.add(sumOfListItems(maleAmounts));
  items.add(females.size());
  items.add(sumOfListItems(femaleAmounts));
  items.add(approvedLoanAccounts.size());
  items.add(sumOfListItems(totalAmounts));
 return items; 
  }
  
  
  private List loansTArrears(){
  
  List items= new ArrayList();int lp=0;
  
Map<Integer, List> ArrearsAccounts=this.borrowArrears2();
  List males= new ArrayList();
  List females= new ArrayList();
   List maleAmounts= new ArrayList();
  List femaleAmounts= new ArrayList();
  List totalAmounts= new ArrayList();
  while(lp<ArrearsAccounts.size()){
  

      if(this.isFemaleBorrower(ArrearsAccounts.get(lp).get(0).toString())){
      
      females.add(ArrearsAccounts.get(lp).get(0).toString());
      femaleAmounts.add(ArrearsAccounts.get(lp).get(1).toString());
      }
      if(this.isMaleBorrower(ArrearsAccounts.get(lp).get(0).toString())){
      males.add(ArrearsAccounts.get(lp).get(0).toString());
      maleAmounts.add(ArrearsAccounts.get(lp).get(1).toString());
  }
       totalAmounts.add(ArrearsAccounts.get(lp).get(1).toString());
      lp++;
  }
       
  items.add("Loans Arrears");
  items.add(males.size());
  items.add(sumOfListItems(maleAmounts));
  items.add(females.size());
  items.add(sumOfListItems(femaleAmounts));
  items.add(ArrearsAccounts.size());
  items.add(sumOfListItems(totalAmounts));
 return items; 
  }
  private List loansTApplied(){
  
  List items= new ArrayList();int lp=0;
  
Map<Integer, List> appliedAccounts=this.borrowApplied2();
  List males= new ArrayList();
  List females= new ArrayList();
   List maleAmounts= new ArrayList();
  List femaleAmounts= new ArrayList();
  List totalAmounts= new ArrayList();
  while(lp<appliedAccounts.size()){
  

      if(this.isFemaleBorrower(appliedAccounts.get(lp).get(0).toString())){
      
      females.add(appliedAccounts.get(lp).get(0).toString());
      femaleAmounts.add(appliedAccounts.get(lp).get(1).toString());
      }
      if(this.isMaleBorrower(appliedAccounts.get(lp).get(0).toString())){
      males.add(appliedAccounts.get(lp).get(0).toString());
      maleAmounts.add(appliedAccounts.get(lp).get(1).toString());
  }
       totalAmounts.add(appliedAccounts.get(lp).get(1).toString());
      lp++;
  }
      
  items.add("Loans Applied");
  items.add(males.size());
  items.add(sumOfListItems(maleAmounts));
  items.add(females.size());
  items.add(sumOfListItems(femaleAmounts));
  items.add(appliedAccounts.size());
  items.add(sumOfListItems(totalAmounts));
 return items; 
  }
  
  
   private List loansTDisbursed(){
  
  List items= new ArrayList();int lp=0;
  
Map<Integer, List> disbursedAccounts=this.borrowDisbursed2();
  List males= new ArrayList();
  List females= new ArrayList();
   List maleAmounts= new ArrayList();
  List femaleAmounts= new ArrayList();
  List totalAmounts= new ArrayList();
  while(lp<disbursedAccounts.size()){
  

      if(this.isFemaleBorrower(disbursedAccounts.get(lp).get(0).toString())){
      
      females.add(disbursedAccounts.get(lp).get(0).toString());
      femaleAmounts.add(disbursedAccounts.get(lp).get(1).toString());
      }
      if(this.isMaleBorrower(disbursedAccounts.get(lp).get(0).toString())){
      males.add(disbursedAccounts.get(lp).get(0).toString());
      maleAmounts.add(disbursedAccounts.get(lp).get(1).toString());
  }
       totalAmounts.add(disbursedAccounts.get(lp).get(1).toString());
      lp++;
  }
      
  items.add("Loans Disbursed");
  items.add(males.size());
  items.add(sumOfListItems(maleAmounts));
  items.add(females.size());
  items.add(sumOfListItems(femaleAmounts));
  items.add(disbursedAccounts.size());
  items.add(sumOfListItems(totalAmounts));
 return items; 
  }
  private String firstContactNumber(String accountNumber){
  
 String contact=""; Integer n=0;

      try {
               Connection cq=quaryObj.createConnection();    
          cq.setAutoCommit(false);
     
          String query = "select mobile1  from master where account_number ="+accountNumber;
 
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
        contact=(rsxmt.getString("mobile1"));
          }
        rsxmt.close();
        psxmt.close();
          cq.setAutoCommit(true);
          quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      if(contact.equalsIgnoreCase("")){
      contact="-";
      }
return contact;
        
  
  }
  private List<String> maleBorrowers(){
  
  List<String> data2= new ArrayList<>(); Integer n=0;

      try {
               Connection cq=quaryObj.createConnection();    
          cq.setAutoCommit(false);
     
          String query = "select account_number from master where sex ='Male'";
            
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
        data2.add(rsxmt.getString("account_number"));
          }
        rsxmt.close();
        psxmt.close();
          cq.setAutoCommit(true);
          quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
return data2;
        
  
  }
  
   
  private List<String> femaleBorrowers(){
   List<String> data2= new ArrayList<>(); 

      try {
           Connection cq=quaryObj.createConnection(); 
         cq.setAutoCommit(false);
     
          String query = "select account_number from master where sex ='Female'";
            
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
        data2.add(rsxmt.getString("account_number"));
          }
        rsxmt.close();
        psxmt.close();
          cq.setAutoCommit(true);
           quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
return data2;
  
  
  }
  
  
  private boolean isFemaleBorrower(String accountNumber){
       List<String> femaleAccounts=femaleBorrowers(); int sz=0;
      boolean fmale=false;
      
      while(sz<femaleAccounts.size()){
      
          if(femaleAccounts.get(sz).equalsIgnoreCase(accountNumber)){
          fmale=true;
          break;
          }
          sz++;
          
      }
 
               
  return fmale;
  
  }
  
  
  private boolean isMaleBorrower(String accountNumber){
    int sz=0;
    List<String> maleAccounts= maleBorrowers();
  boolean male=false;
      
      while(sz<maleAccounts.size()){
      
          if(maleAccounts.get(sz).equalsIgnoreCase(accountNumber)){
          male=true;
          break;
          }
          
         sz++; 
      }
      
   return male;   
  }
  

  
  
  private String sumOfListItems(List theItem){
     
  int md=0; Double sum=0.0;
  if(!theItem.isEmpty()){
  
  while(md<theItem.size()){

  sum=sum+parseDouble(theItem.get(md).toString());
   md++;
  }
  
  }
  
  return sum.toString();
  }
  
    public Map<Integer, List<Object>> grossPortfolio(String sdate,String edate){
  
  Map<Integer, List<Object>> data1= new HashMap<>();;List<Object> data2=null; Integer n=0,k=0;double totalP=0.0;
List accountNumbers=uniqueAccountsPortfolio(sdate,edate);
for(int u=0;u<accountNumbers.size();u++){
  n=n+1;
      try {
            Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);

          String query = "SELECT  princimpal_amount, account_name FROM  loan_portfolio WHERE account_number="+accountNumbers.get(u);
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          if (rsxmt.last()){
             
        data2=new ArrayList<>();
        
        
         data2.add(0, n);
        data2.add(1, rsxmt.getString("account_name"));
        
         data2.add(2, rsxmt.getString("princimpal_amount"));
       
         data1.put(k, data2);
         totalP=totalP+parseDouble(rsxmt.getString("princimpal_amount"));
        
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
           loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
       k++;
}
   List data3=new ArrayList<>();    
      data3.add(0, "Total Value");
        data3.add(1, "-");
        data3.add(2, totalP);
    data1.put(k, data3);  

return data1;
        
  
  } 

   public Map<Integer, List<Object>> performingPortfolio(String sdate,String edate){
  
  Map<Integer, List<Object>> data1= new HashMap<>();;List<Object> data2=null; Integer n=0,k=0;double totalP=0.0;
List accountNumbers=uniqueAccountsPerformingPortfolio(sdate,edate);
for(int u=0;u<accountNumbers.size();u++){
  n=n+1;
      try {
            Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);

          String query = "SELECT  princimpal_amount, account_name FROM   performing_loan_portfolio WHERE account_number="+accountNumbers.get(u);
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          if (rsxmt.last()){
             
        data2=new ArrayList<>();
        
        
         data2.add(0, n);
        data2.add(1, rsxmt.getString("account_name"));
        
         data2.add(2, rsxmt.getString("princimpal_amount"));
       
         data1.put(k, data2);
         totalP=totalP+parseDouble(rsxmt.getString("princimpal_amount"));
        
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
           loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
       k++;
}
   List data3=new ArrayList<>();    
      data3.add(0, "Total Value");
        data3.add(1, "-");
        data3.add(2, totalP);
    data1.put(k, data3);  

return data1;
        
  
  } 
    public Map<Integer, List<Object>> arrearR(String sdate,String edate){
  
  Map<Integer, List<Object>> data1= new HashMap<>();List<Object> data2=null; Integer n=0,k=0;double totalP=0.0;
  
List accountNumbers=uniqueAccountsArrearsR(sdate,edate);

for(int u=0;u<accountNumbers.size();u++){
  n=n+1; String amont="";
      try {
            Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);

          String query = "SELECT  princimpal_amount, account_name FROM   loan_arrears_tracker WHERE account_number="+accountNumbers.get(u);
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          if (rsxmt.last()){
             
        data2=new ArrayList<>();
        
        
         data2.add(0, n);
        data2.add(1, rsxmt.getString("account_name"));
        amont=rsxmt.getString("princimpal_amount");
         data2.add(2, amont);
       
         data1.put(k, data2);
         totalP=totalP+parseDouble(amont);
        
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
           loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
       k++;
}
   List data3=new ArrayList<>();    
      data3.add(0, "Total Value");
        data3.add(1, "-");
        data3.add(2, totalP);
    data1.put(k, data3);  
    
    if(data1.isEmpty()){
    
    data1.put(0, data3);
    }

return data1;
        
  
  } 
   
  public Map<Integer, List<Object>> portfolioAtRisk(String sdate,String edate){
  
  Map<Integer, List<Object>> data1= new HashMap<>();
  
  List<Object> data2=null; 
  
  Integer n=0,k=0;
  
  double totalP=0.0;
  
List accountNumbers=uniqueAccountsPortfolioAtRisk(sdate,edate);

for(int u=0;u<accountNumbers.size();u++){
  n=n+1;
      try {
            Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);

          String query = "SELECT  princimpal_amount, account_name FROM   loan_portfolio_atrisk WHERE account_number="+accountNumbers.get(u);
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          if (rsxmt.last()){
             
        data2=new ArrayList<>();
        
        
         data2.add(0, n);
        data2.add(1, rsxmt.getString("account_name"));
        
         data2.add(2, rsxmt.getString("princimpal_amount"));
       
         data1.put(k, data2);
         totalP=totalP+parseDouble(rsxmt.getString("princimpal_amount"));
        
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
           loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
       k++;
}
   List data3=new ArrayList<>();    
      data3.add(0, "Total Value");
        data3.add(1, "-");
        data3.add(2, totalP);
    data1.put(k, data3);  

return data1;
        
  
  } 
    
  public Map<Integer, List<Object>> loansForWriteOff(String sdate,String edate){
  
  Map<Integer, List<Object>> data1= new HashMap<>();;List<Object> data2=null; Integer n=0,k=0;double totalP=0.0,totali=0.0;
List accountNumbers=uniqueAccountsloansWriteOff(sdate,edate);
for(int u=0;u<accountNumbers.size();u++){
  n=n+1;
      try {


          
          Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);

          String query = "SELECT  princimpal_amount, interest_amount,aging,account_name FROM   loan_due_writeoff WHERE account_number="+accountNumbers.get(u);
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          if (rsxmt.last()){
             
        data2=new ArrayList<>();
    
        
         data2.add(0, n);
        data2.add(1, rsxmt.getString("account_name"));
        
         data2.add(2, rsxmt.getString("princimpal_amount"));
        data2.add(3, rsxmt.getString("interest_amount"));
        data2.add(4, rsxmt.getString("aging"));
         data1.put(k, data2);
         totalP=totalP+parseDouble(rsxmt.getString("princimpal_amount"));
        totali=totali+parseDouble(rsxmt.getString("interest_amount"));
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
           loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
       k++;
}
   List data3=new ArrayList<>();    
      data3.add(0, "Total Value");
        data3.add(1, "-");
        data3.add(2, totalP);
         data3.add(3, totali);
         data3.add(4, "-");
    data1.put(k, data3);  

return data1;
        
  
  } 
   public Map<Integer, List<Object>> loansAppliedx(String sdate,String edate){
  
  Map<Integer, List<Object>> data1= new HashMap<>();;List<Object> data2=null; Integer n=0,k=0;double totalP=0.0,totali=0.0;
//List accountNumbers=uniqueAccountsloansWriteOff(sdate,edate);
//for(int u=0;u<accountNumbers.size();u++){
//  n=n+1;
      try {


          
          Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);

          String query = "SELECT  princimpal_amount, interest_amount,instalment_start_date,instalment_end_date,account_name FROM  loanprocessingstore   WHERE ((trn_date>="+"'"+sdate+"'"+"AND trn_date<="+"'"+edate+"'"+")"+" "+"AND"+" "+"(loan_cycle_status="+"'"+"Applied"+"'"+"))";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
         while (rsxmt.next()){
             
        data2=new ArrayList<>();
        n=n+1;
        
         data2.add(0, n);
        data2.add(1, rsxmt.getString("account_name"));
        
         data2.add(2, rsxmt.getString("princimpal_amount"));
        data2.add(3, rsxmt.getString("interest_amount"));
        data2.add(4,fmt.fromDatabaseWithDashSeperatorBeginningWithYear( rsxmt.getString("instalment_start_date")));
        data2.add(5,fmt.fromDatabaseWithDashSeperatorBeginningWithYear( rsxmt.getString("instalment_end_date")));
         data1.put(k, data2);
         totalP=totalP+parseDouble(rsxmt.getString("princimpal_amount"));
        totali=totali+parseDouble(rsxmt.getString("interest_amount"));
          k++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
           loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
//}
   List data3=new ArrayList<>();    
      data3.add(0, "Total Value");
        data3.add(1, "-");
        data3.add(2, totalP);
         data3.add(3, totali);
         data3.add(4, "-");
         data3.add(5, "-");
    data1.put(k, data3);  

return data1;
        
  
  } 
   
   public Map<Integer, List<Object>> loansApprovedx(String sdate,String edate){
  
  Map<Integer, List<Object>> data1= new HashMap<>();;List<Object> data2=null; Integer n=0,k=0;double totalP=0.0,totali=0.0;
//List accountNumbers=uniqueAccountsloansWriteOff(sdate,edate);
//for(int u=0;u<accountNumbers.size();u++){
//  n=n+1;
      try {


          
          Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);

          String query = "SELECT  princimpal_amount, interest_amount,instalment_start_date,instalment_end_date,account_name FROM  loanprocessingstore   WHERE ((trn_date>="+"'"+sdate+"'"+"AND trn_date<="+"'"+edate+"'"+")"+" "+"AND"+" "+"(loan_cycle_status="+"'"+"Approved"+"'"+"))";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
         while (rsxmt.next()){
             
        data2=new ArrayList<>();
        n=n+1;
        
         data2.add(0, n);
        data2.add(1, rsxmt.getString("account_name"));
        
         data2.add(2, rsxmt.getString("princimpal_amount"));
        data2.add(3, rsxmt.getString("interest_amount"));
        data2.add(4,fmt.fromDatabaseWithDashSeperatorBeginningWithYear( rsxmt.getString("instalment_start_date")));
        data2.add(5,fmt.fromDatabaseWithDashSeperatorBeginningWithYear( rsxmt.getString("instalment_end_date")));
         data1.put(k, data2);
         totalP=totalP+parseDouble(rsxmt.getString("princimpal_amount"));
        totali=totali+parseDouble(rsxmt.getString("interest_amount"));
          k++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
           loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
//}
   List data3=new ArrayList<>();    
      data3.add(0, "Total Value");
        data3.add(1, "-");
        data3.add(2, totalP);
         data3.add(3, totali);
         data3.add(4, "-");
         data3.add(5, "-");
    data1.put(k, data3);  

return data1;
        
  
  } 
   public Map<Integer, List<Object>> loansCompletedx(String sdate,String edate){
  
  Map<Integer, List<Object>> data1= new HashMap<>();;List<Object> data2=null; Integer n=0,k=0;double totalP=0.0,totali=0.0;
//List accountNumbers=uniqueAccountsloansWriteOff(sdate,edate);
//for(int u=0;u<accountNumbers.size();u++){
//  n=n+1;
      try {


          
          Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);

          String query = "SELECT  princimpal_amount, total_interest,instalment_start_date,instalment_end_date,applicant_account_name FROM  new_loan_appstore   WHERE ((trn_date>="+"'"+sdate+"'"+"AND trn_date<="+"'"+edate+"'"+")"+" "+"AND"+" "+"(loan_cycle_status="+"'"+"Completed"+"'"+"))";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
         while (rsxmt.next()){
             
        data2=new ArrayList<>();
        n=n+1;
        
         data2.add(0, n);
        data2.add(1, rsxmt.getString("applicant_account_name"));
        
         data2.add(2, rsxmt.getString("princimpal_amount"));
        data2.add(3, rsxmt.getString("total_interest"));
        data2.add(4,fmt.fromDatabaseWithDashSeperatorBeginningWithYear( rsxmt.getString("instalment_start_date")));
        data2.add(5,fmt.fromDatabaseWithDashSeperatorBeginningWithYear( rsxmt.getString("instalment_end_date")));
         data1.put(k, data2);
         totalP=totalP+parseDouble(rsxmt.getString("princimpal_amount"));
        totali=totali+parseDouble(rsxmt.getString("total_interest"));
          k++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
           loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
//}
   List data3=new ArrayList<>();    
      data3.add(0, "Total Value");
        data3.add(1, "-");
        data3.add(2, totalP);
         data3.add(3, totali);
         data3.add(4, "-");
         data3.add(5, "-");
    data1.put(k, data3);  

return data1;
        
  
  } 
    public Map<Integer, List<Object>> loansDisbursedx(String sdate,String edate){
  
  Map<Integer, List<Object>> data1= new HashMap<>();;List<Object> data2=null; Integer n=0,k=0;double totalP=0.0,totali=0.0;
//List accountNumbers=uniqueAccountsloansWriteOff(sdate,edate);
//for(int u=0;u<accountNumbers.size();u++){
//  n=n+1;
      try {


          
          Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);

          String query = "SELECT  princimpal_amount, interest_amount,instalment_start_date,instalment_end_date,account_name FROM  loanprocessingstore   WHERE ((trn_date>="+"'"+sdate+"'"+"AND trn_date<="+"'"+edate+"'"+")"+" "+"AND"+" "+"(loan_cycle_status="+"'"+"Disbursed"+"'"+"))";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
         while (rsxmt.next()){
             
        data2=new ArrayList<>();
        n=n+1;
        
         data2.add(0, n);
        data2.add(1, rsxmt.getString("account_name"));
        
         data2.add(2, rsxmt.getString("princimpal_amount"));
        data2.add(3, rsxmt.getString("interest_amount"));
        data2.add(4,fmt.fromDatabaseWithDashSeperatorBeginningWithYear( rsxmt.getString("instalment_start_date")));
        data2.add(5,fmt.fromDatabaseWithDashSeperatorBeginningWithYear( rsxmt.getString("instalment_end_date")));
         data1.put(k, data2);
         totalP=totalP+parseDouble(rsxmt.getString("princimpal_amount"));
        totali=totali+parseDouble(rsxmt.getString("interest_amount"));
          k++;
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
           loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
      
//}
   List data3=new ArrayList<>();    
      data3.add(0, "Total Value");
        data3.add(1, "-");
        data3.add(2, totalP);
         data3.add(3, totali);
         data3.add(4, "-");
         data3.add(5, "-");
    data1.put(k, data3);  

return data1;
        
  
  } 
  private List<Object>uniqueAccountsPortfolio(String startDate,String endDate){
  
     List<Object> data2=new ArrayList(); 
         try {
            Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);

          String query = "SELECT  DISTINCT account_number FROM  loan_portfolio WHERE(trn_date>="+"'"+startDate+"'"+"AND trn_date<="+"'"+endDate+"'"+")";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
         
        data2.add(rsxmt.getString("account_number"));
     
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
           loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
  
        
  return data2;
  
  }
  
   private List<Object>uniqueAccountsPerformingPortfolio(String startDate,String endDate){
  
     List<Object> data2=new ArrayList(); 
         try {
            Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);

          String query = "SELECT  DISTINCT account_number FROM  performing_loan_portfolio WHERE(trn_date>="+"'"+startDate+"'"+"AND trn_date<="+"'"+endDate+"'"+")";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
         
        data2.add(rsxmt.getString("account_number"));
     
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
           loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
  
        
  return data2;
  
  }
    private List<Object>uniqueAccountsArrearsR(String startDate,String endDate){
  
     List<Object> data2=new ArrayList(); 
         try {
            Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);

          String query = "SELECT  DISTINCT account_number FROM  loan_arrears_tracker WHERE(trn_date>="+"'"+startDate+"'"+"AND trn_date<="+"'"+endDate+"'"+")";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
         
        data2.add(rsxmt.getString("account_number"));
     
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
           loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
  
        
  return data2;
  
  }
   
  private List<Object>uniqueAccountsPortfolioAtRisk(String startDate,String endDate){
  
     List<Object> data2=new ArrayList(); 
         try {
            Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);

          String query = "SELECT  DISTINCT account_number FROM  loan_portfolio_atrisk WHERE(trn_date>="+"'"+startDate+"'"+"AND trn_date<="+"'"+endDate+"'"+")";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
         
        data2.add(rsxmt.getString("account_number"));
     
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
           loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
  
        
  return data2;
  
  }
  
  private List<Object>uniqueAccountsloansWriteOff(String startDate,String endDate){
  
     List<Object> data2=new ArrayList(); 
         try {
            Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);

          String query = "SELECT  DISTINCT account_number FROM  loan_due_writeoff WHERE(trn_date>="+"'"+startDate+"'"+"AND trn_date<="+"'"+endDate+"'"+")";
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
          while (rsxmt.next()){
         
        data2.add(rsxmt.getString("account_number"));
     
          }
        rsxmt.close();
        psxmt.close();
         cq.setAutoCommit(true);
           loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
  
        
  return data2;
  
  }
  
  public List getBudgetRevenueItemsExisting(String startDate,String endDate){
  List busgetItems=new ArrayList();
  
   int startYear=parseInt(fmt.yearOnlyFromDateString(startDate));
   int endYear=parseInt(fmt.yearOnlyFromDateString(endDate));
        int startMonth=parseInt(fmt.monthOnlyFromDateString(startDate));
        int endMonth=parseInt(fmt.monthOnlyFromDateString(endDate));
try {
             Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
                   String query = "SELECT DISTINCT AccountMaster FROM  budgetstore WHERE ( AccountType="+"'"+"Revenue Account"+"'"+" AND ((BYear>="+startYear+" AND BYear<="+endYear+") AND ( BMonthNumber>="+startMonth+" AND BMonthNumber<="+endMonth +")))";
             
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
              
             
              while (rs.next()) {
                       
            busgetItems.add(rs.getString("AccountMaster"));
                      
                  
                    }
             
                     cq.setAutoCommit(true);
             
           quaryObj.closeConnection(cq); 
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }

      
      
      
    return busgetItems;  
  }
  public List getBudgetExpenseItemsExisting(String startDate,String endDate){
  List busgetItems=new ArrayList();
  
   int startYear=parseInt(fmt.yearOnlyFromDateString(startDate));
   int endYear=parseInt(fmt.yearOnlyFromDateString(endDate));
        int startMonth=parseInt(fmt.monthOnlyFromDateString(startDate));
        int endMonth=parseInt(fmt.monthOnlyFromDateString(endDate));
try {
             Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
                   String query = "SELECT DISTINCT AccountMaster FROM  budgetstore  WHERE ( AccountType="+"'"+"Expense Account"+"'"+" AND ((BYear>="+startYear+" AND BYear<="+endYear+") AND ( BMonthNumber>="+startMonth+" AND BMonthNumber<="+endMonth+")))";
             
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
              
             
              while (rs.next()) {
                       
            busgetItems.add(rs.getString("AccountMaster"));
                      
                  
                    }
             
                     cq.setAutoCommit(true);
             
           quaryObj.closeConnection(cq); 
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }

      
      
      
    return busgetItems;  
  }
   public double getbudgetedTotal(String accountItem,String startDate,String endDate){
   
    double budgetAmount=0.0;
  
   int startYear=parseInt(fmt.yearOnlyFromDateString(startDate));
   int endYear=parseInt(fmt.yearOnlyFromDateString(endDate));
        int startMonth=parseInt(fmt.monthOnlyFromDateString(startDate));
        int endMonth=parseInt(fmt.monthOnlyFromDateString(endDate));
try {
             Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
                   String query = "SELECT SUM(BAmount) AS totalAmount FROM  budgetstore WHERE (AccountMaster="+"'"+accountItem+"'"+" "+"AND ((BYear>="+startYear+" AND BYear<="+endYear+") AND (BMonthNumber>="+startMonth+" AND BMonthNumber<="+endMonth+")))";
             
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
              
             
              while (rs.next()) {
                       
        budgetAmount=rs.getDouble("totalAmount");
                      
                  
                    }
             
                     cq.setAutoCommit(true);
             
           quaryObj.closeConnection(cq); 
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }

      
      
      
    return budgetAmount;  
   
   
   }
   
   
   
   
   
  public double getbActualTotal(String accountItem,String startDate,String endDate,Component c){
       String masterAccount="";

   accountItem=accountItem.replace(" ", "&"); 
    accountItem=accountItem.replace("/", "&");
    accountItem=accountItem.replace("-", "&");
   accountItem=accountItem.replace("\\", "&");
   accountItem=accountItem.replace("(", "&");
    accountItem=accountItem.replace(")", "&");
    accountItem=accountItem.replace("&&&&", "&");  
     accountItem=accountItem.replace("&&&", "&"); 
     accountItem=accountItem.replace("&&", "&");
    accountItem=accountItem.replace("&", "_");
    
    masterAccount=masterAccountUnique(accountItem,c);
     double firstbal=parseDouble( balancepAndLFstart(masterAccount,startDate,c));
       double lastbal=parseDouble( balancepAndLEnd(masterAccount,endDate));
 
         double actualAmount=0.0;
   
      actualAmount=lastbal-firstbal;
      
    return actualAmount;  
   
   
   }
  
  public synchronized List getTheInstamentsProject(String theAccount,String startDate,String endDate){
      
   List theInstalments=new ArrayList();
  
 if(isTheInstamentsProject(theAccount,startDate,endDate)){
try {
             Connection cq=loancon.createConnection(); 
          cq.setAutoCommit(false); 
 String query = "SELECT instalment_no FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+theAccount+"' AND ((instalment_status="+"'NP' OR instalment_status="+"'PP' OR instalment_status="+"'NY')"+"  AND"+" "+"(instalment_due_date>="+"'"+startDate+"'"+"AND instalment_due_date<="+"'"+endDate+"'"+"))";
             
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
              
             
              while (rs.next()) {
                       
            theInstalments.add(rs.getString("instalment_no"));
                      
                  
                    }
             
                     cq.setAutoCommit(true);
             
           loancon.closeConnection(cq); 
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }

 } 

else{
 theInstalments.add(777);
 }    
//////     theInstalments.add(1);
      
    return theInstalments;  
  
  
  }
  
   public synchronized boolean isTheInstamentsProject(String theAccount,String startDate,String endDate){
//       fios.stringFileWriter(fios.createFileName("test", "testit", "ppc"+"trdyr.txt"),startDate+";"+endDate);  
   int count=0; boolean isther=false;
  
 
try {
             Connection cq=loancon.createConnection(); 
          cq.setAutoCommit(false); 
 String query = "SELECT COUNT(instalment_no) AS inst FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+theAccount+"' AND (instalment_status="+"'NP' OR instalment_status="+"'PP' OR instalment_status="+"'NY')"+"  AND"+" "+"(instalment_due_date>="+"'"+startDate+"'"+"AND instalment_due_date<="+"'"+endDate+"'"+")";
             
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
              
             
              while (rs.next()) {
                       
             count=rs.getInt("inst");
                      
                  
                    }
             
                     cq.setAutoCommit(true);
             
           loancon.closeConnection(cq); 
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
if(count>0){
isther=true;
}
      
//////     theInstalments.add(1);
      
    return isther;  
  
  
  }
  

   public  Map<Integer, List<Object>>createAllOutstandingLaonPayments(){
 
  
  int n=0,k=0;

Double loanAmount=0.0,totalPrincipal=0.0,totalInterest=0.0,totalAccumulatedInt=0.0,totalLoanPenalty=0.0;List finale=null;

       Map<Integer, List<Object>> agingReport=new HashMap();
       
       
  List accountNumbers=loan.updateOutStandingAcof();
  
  for(Object account:accountNumbers){
  
  
           
      try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
     
          
          
   String query = "SELECT InterestRemaing,AccumulatedInterestRemaining,LoanPenaltyRemaining,PrincipalRemaining,InstalmentRemaining, instalment_due_date FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+account.toString()+"' AND NOT" +"(instalment_status="+"'P')";
          
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        if(rsxmt.first()){
             List loanDetails=new ArrayList();
            n=n+1;
    loanDetails.add(n);          
loanDetails.add(AccountName(account.toString()));
loanDetails.add(account.toString());
loanDetails.add(rsxmt.getString("InterestRemaing"));
loanDetails.add(rsxmt.getString("AccumulatedInterestRemaining"));
loanDetails.add(rsxmt.getString("LoanPenaltyRemaining"));
loanDetails.add(rsxmt.getString("PrincipalRemaining"));
loanDetails.add(rsxmt.getString("InstalmentRemaining"));
loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("instalment_due_date")));
agingReport.put(k,  loanDetails);


totalInterest+=parseDouble(loanDetails.get(3).toString());
totalAccumulatedInt+=parseDouble(loanDetails.get(4).toString());
totalLoanPenalty+=parseDouble(loanDetails.get(5).toString());
totalPrincipal+=parseDouble(loanDetails.get(6).toString());
loanAmount+=parseDouble(loanDetails.get(7).toString());

          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
      k++;
  }
              finale=new ArrayList();
  finale.add("Total");
  finale.add("-");
   finale.add("-");
    finale.add(totalInterest+"");
      finale.add(totalAccumulatedInt+"");
    finale.add(totalLoanPenalty+"");
     finale.add(totalPrincipal+"");
      finale.add(loanAmount+"");
      finale.add("-");
agingReport.put(k,finale);  
    
return agingReport;
  
  

  
  }
   public  Map<Integer, List<Object>>createAllOutstandingSaviePPayments(){
 
  
  int n=0,k=0;

Double TotalSave=0.0,totalSavings=0.0;List finale=null;

       Map<Integer, List<Object>> agingReport=new HashMap();
       
       
  List accountNumbers=dbq.accountNumbersCustomers();
  
  for(Object account:accountNumbers){
  
//  if(isSavingThere(account.toString())){
           
      try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
     
          
          
   String query = "SELECT credit FROM bsanca"+account.toString();
          
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        if(rsxmt.last()){
             List saveDetails=new ArrayList();
            n=n+1;
            String amount=rsxmt.getString("credit");
            if(amount.equals("-")){
            amount="0.0";
            }
    saveDetails.add(n);          
saveDetails.add(AccountName(account.toString()));
saveDetails.add(account.toString());
saveDetails.add(amount);
saveDetails.add(amount);
agingReport.put(k,  saveDetails);


TotalSave+=parseDouble(saveDetails.get(3).toString());
totalSavings+=parseDouble(saveDetails.get(4).toString());

            
          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
//  }else{
//      List saveDetails=new ArrayList();
//            n=n+1;
//           
//        String     amount1="0.0";
//            
//    saveDetails.add(n);          
//saveDetails.add(AccountName(account.toString()));
//saveDetails.add(account.toString());
//saveDetails.add(amount1);
//saveDetails.add(amount1);
//agingReport.put(k,  saveDetails);
//
//
//TotalSave+=parseDouble(saveDetails.get(3).toString());
//totalSavings+=parseDouble(saveDetails.get(4).toString());
//  
//  
//  
//  }
      k++;
  }
  
              finale=new ArrayList();
  finale.add("Total");
  finale.add("-");
   finale.add("-");
    finale.add(TotalSave+"");
      finale.add(totalSavings+"");
agingReport.put(k,finale);  
    
return agingReport;
  
  

  
  }
   
    public  Map<Integer, List<Object>>createAllOutstandingaSharePPayments(){
 
  
  int n=0,k=0;

Double TotalSave=0.0,totalSavings=0.0;List finale=null;

       Map<Integer, List<Object>> agingReport=new HashMap();
       
       
  List accountNumbers=dbq.accountNumbersCustomers();
  
  for(Object account:accountNumbers){
  
//  if(isSavingThere(account.toString())){
           
      try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
     
          
          
   String query = "SELECT debit FROM bsanca"+account.toString();
          
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        if(rsxmt.last()){
             List saveDetails=new ArrayList();
            n=n+1;
            String amount=rsxmt.getString("debit");
            if(amount.equals("-")){
            amount="0.0";
            }
    saveDetails.add(n);          
saveDetails.add(AccountName(account.toString()));
saveDetails.add(account.toString());
saveDetails.add(amount);
saveDetails.add(amount);
agingReport.put(k,  saveDetails);


TotalSave+=parseDouble(saveDetails.get(3).toString());
totalSavings+=parseDouble(saveDetails.get(4).toString());

            
          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
//  }else{
//      List saveDetails=new ArrayList();
//            n=n+1;
//           
//        String     amount1="0.0";
//            
//    saveDetails.add(n);          
//saveDetails.add(AccountName(account.toString()));
//saveDetails.add(account.toString());
//saveDetails.add(amount1);
//saveDetails.add(amount1);
//agingReport.put(k,  saveDetails);
//
//
//TotalSave+=parseDouble(saveDetails.get(3).toString());
//totalSavings+=parseDouble(saveDetails.get(4).toString());
//  
//  
//  
//  }
      k++;
  }
  
              finale=new ArrayList();
  finale.add("Total");
  finale.add("-");
   finale.add("-");
    finale.add(TotalSave+"");
      finale.add(totalSavings+"");
agingReport.put(k,finale);  
    
return agingReport;
  
  

  
  }
   
   public boolean isSavingThere( String accountNumber){
       boolean test=false;int amount=0;
  
   
   try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
     
          
   String query = "SELECT COUNT(credit) AS number FROM bsanca"+accountNumber+" "+"WHERE narration LIKE 'SA%'";                                             
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
       while(rsxmt.last()){
           
       amount=rsxmt.getInt("number");
          }
             rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
   if(amount>0){test=true;}
   return test;
   }
   
   
   public  Map<Integer, List<Object>>createAllOutstandingLaonPaymentsSpecialX(int batchNu){
 
  
  int n=0,k=0;

Double loanAmount=0.0,totalPrincipal=0.0,totalInterest=0.0,totalAccumulatedInt=0.0,totalLoanPenalty=0.0;List finale=null;

       Map<Integer, List<Object>> batchNubmerItems=new HashMap();
       
       
//  List accountNumbers=loan.updateOutStandingAcof();
  
//  for(Object account:accountNumbers){
  
  
           
      try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
     
      
          
   String query = "SELECT  ItemId,ItemDate,AccountName,AccountNumber,Principal,Interest,AccumulatedInterest, PenaltyLoan,TotalAmount, BatchOther1 FROM batchpostingdetails WHERE BatchId="+batchNu;
 
                              
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
      while(rsxmt.next()){
             List loanDetails=new ArrayList();
            n=n+1;
    loanDetails.add(rsxmt.getString("ItemId"));         
loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("ItemDate")));
loanDetails.add(rsxmt.getString("AccountName"));
loanDetails.add(rsxmt.getString("AccountNumber"));
loanDetails.add(rsxmt.getString("Principal"));
loanDetails.add(rsxmt.getString("Interest"));
loanDetails.add(rsxmt.getString("AccumulatedInterest"));
loanDetails.add(rsxmt.getString("PenaltyLoan"));
loanDetails.add(rsxmt.getString("TotalAmount"));
loanDetails.add(rsxmt.getString("BatchOther1"));
batchNubmerItems.put(k,  loanDetails);

totalPrincipal+=parseDouble(loanDetails.get(4).toString().replace(",", "").trim());
totalInterest+=parseDouble(loanDetails.get(5).toString().replace(",", "").trim());
totalAccumulatedInt+=parseDouble(loanDetails.get(6).toString().replace(",", "").trim());
totalLoanPenalty+=parseDouble(loanDetails.get(7).toString().replace(",", "").trim());
loanAmount+=parseDouble(loanDetails.get(8).toString().replace(",", "").trim());
 k++;
          }
   
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
     
//  }
              finale=new ArrayList();
  finale.add("Total");
  finale.add("-");
   finale.add("-");
    finale.add("-");
    finale.add(totalInterest+"");
      finale.add(totalAccumulatedInt+"");
    finale.add(totalLoanPenalty+"");
     finale.add(totalPrincipal+"");
      finale.add(loanAmount+"");
      finale.add("-");
batchNubmerItems.put(k-1,finale);  
    
return batchNubmerItems;
  
  

  
  }
   public List getExistingAccountNumbers(int batchNu){
   List finale=new ArrayList();

 
  
  
           
      try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
     
          
          
   String query = "SELECT  AccountNumber FROM batchpostingdetails WHERE  BatchId="+batchNu;
          
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
       while(rsxmt.next()){
        
           
finale.add(rsxmt.getString("AccountNumber"));


          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
   

    
return finale;
   
   }
   public List getMissingAccounts(List allAccounts,List existingAccounts){
   
   List newAccountNumbers=new ArrayList();
       for(Object newAccount:allAccounts){
       
           if(!accountExists(newAccount.toString(),existingAccounts)){
           
           newAccountNumbers.add(newAccount);
           }
       
       }
       
      return  newAccountNumbers;
   }
   
   public boolean accountExists(String accountNumber,List existingAccounts){
   boolean accountExis=false;
   for(Object account:existingAccounts){
   if(accountNumber.equals(account.toString())){
   accountExis=true;
   }
   
   }
 return  accountExis ;
   }
   
   public  Map<Integer, List<Object>>amortScheduleWaiveDb(String accountNumber){
 
  
  int n=0,k=0;

Double loanAmount=0.0,totalPrincipal=0.0,totalInterest=0.0,totalAccumulatedInt=0.0,totalLoanPenalty=0.0;List finale=null;

       Map<Integer, List<Object>> interestWriteOff=new HashMap();

           
      try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
     
          
          
   String query = "SELECT instalment_no,PrincipalRemaining,InterestRemaing,AccumulatedInterestRemaining,LoanPenaltyRemaining,InstalmentRemaining, instalment_due_date FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND NOT" +"(instalment_status="+"'P')";
          
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
     while(rsxmt.next()){
             List loanDetails=new ArrayList();
            n=n+1;
    loanDetails.add(n);          
loanDetails.add(rsxmt.getString("instalment_no"));
loanDetails.add(rsxmt.getString("InterestRemaing"));
loanDetails.add(rsxmt.getString("LoanPenaltyRemaining"));
loanDetails.add(rsxmt.getString("AccumulatedInterestRemaining"));
loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("instalment_due_date")));
loanDetails.add(false); 
interestWriteOff.put(k,  loanDetails);

totalInterest+=parseDouble(loanDetails.get(2).toString());
totalLoanPenalty+=parseDouble(loanDetails.get(3).toString());
totalAccumulatedInt+=parseDouble(loanDetails.get(4).toString());
k++;
          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
//      k++;
//  }
              finale=new ArrayList();
  finale.add("Total");
  finale.add("-");
      finale.add(totalInterest+"");
        finale.add(totalLoanPenalty+"");
    finale.add(totalAccumulatedInt+"");
      finale.add("-");
      finale.add(false);
interestWriteOff.put(k,finale);  
//  }
return interestWriteOff;
  
  
  
  }
    public  Map<Integer, List<Object>>createInterest(String accountNumber){
 
  
  int n=0,k=0;

Double loanAmount=0.0,totalPrincipal=0.0,totalInterest=0.0,totalAccumulatedInt=0.0,totalLoanPenalty=0.0;List finale=null;

       Map<Integer, List<Object>> interestWriteOff=new HashMap();

           
      try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
     
          
          
   String query = "SELECT instalment_no,PrincipalRemaining,InterestRemaing,AccumulatedInterestRemaining,LoanPenaltyRemaining,InstalmentRemaining, instalment_due_date FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND NOT" +"(instalment_status="+"'P')";
          
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
     while(rsxmt.next()){
             List loanDetails=new ArrayList();
            n=n+1;
    loanDetails.add(n);          
loanDetails.add(rsxmt.getString("instalment_no"));
loanDetails.add(rsxmt.getString("InterestRemaing"));
//loanDetails.add(rsxmt.getString("LoanPenaltyRemaining"));
//loanDetails.add(rsxmt.getString("AccumulatedInterestRemaining"));
loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("instalment_due_date")));
loanDetails.add(false); 
interestWriteOff.put(k,  loanDetails);

totalInterest+=parseDouble(loanDetails.get(2).toString());
//totalLoanPenalty+=parseDouble(loanDetails.get(3).toString());
//totalAccumulatedInt+=parseDouble(loanDetails.get(4).toString());
k++;
          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
//      k++;
//  }
              finale=new ArrayList();
  finale.add("Total");
  finale.add("-");
      finale.add(totalInterest+"");
//        finale.add(totalLoanPenalty+"");
//    finale.add(totalAccumulatedInt+"");
      finale.add("-");
      finale.add(false);
interestWriteOff.put(k,finale);  
//  }
return interestWriteOff;
  
  
  
  }
     public  boolean noExistingInterest(String accountNumber){
 
  
  double numberINT=0.0,accumInt=0.0;boolean intIn=false;


      try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
     
          
          
   String query = "SELECT SUM(InterestRemaing) AS inti,SUM(AccumulatedInterestRemaining) AS AccumInt FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND NOT" +"(instalment_status="+"'P')";
          
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
       while(rsxmt.next()){
   numberINT=rsxmt.getDouble("inti");
           accumInt=rsxmt.getDouble("AccumInt");

          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
if(numberINT<1&&accumInt<1){

intIn=true;
}
//  }
return intIn;
  
  
  
  }
   
   
    public  Map<Integer, List<Object>>createBatchPostingLoansSpecialX(int batchNu){
 
  
  int n=0,k=0;

Double loanAmount=0.0,totalPrincipal=0.0,totalInterest=0.0,totalAccumulatedInt=0.0,totalLoanPenalty=0.0;List finale=null;

       Map<Integer, List<Object>> agingReport=new HashMap();
       
  List existingBatch=getExistingAccountNumbers(batchNu);   
  
  List accountNumbers=loan.updateOutStandingAcof();
  
  
  List missingAccountNumber=getMissingAccounts(accountNumbers,existingBatch);
  
  if(!missingAccountNumber.isEmpty()){
  
  for(Object account:missingAccountNumber){
  
  
           
      try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
     
          
          
   String query = "SELECT InterestRemaing,AccumulatedInterestRemaining,LoanPenaltyRemaining,PrincipalRemaining,InstalmentRemaining, instalment_due_date FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+account.toString()+"' AND NOT" +"(instalment_status="+"'P')";
          
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        if(rsxmt.first()){
             List loanDetails=new ArrayList();
            n=n+1;
    loanDetails.add(n);          
loanDetails.add(AccountName(account.toString()));
loanDetails.add(account.toString());
loanDetails.add(rsxmt.getString("InterestRemaing"));
loanDetails.add(rsxmt.getString("AccumulatedInterestRemaining"));
loanDetails.add(rsxmt.getString("LoanPenaltyRemaining"));
loanDetails.add(rsxmt.getString("PrincipalRemaining"));
loanDetails.add(rsxmt.getString("InstalmentRemaining"));
loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("instalment_due_date")));
agingReport.put(k,  loanDetails);


totalInterest+=parseDouble(loanDetails.get(3).toString());
totalAccumulatedInt+=parseDouble(loanDetails.get(4).toString());
totalLoanPenalty+=parseDouble(loanDetails.get(5).toString());
totalPrincipal+=parseDouble(loanDetails.get(6).toString());
loanAmount+=parseDouble(loanDetails.get(7).toString());

          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
      k++;
  }
              finale=new ArrayList();
  finale.add("Total");
  finale.add("-");
   finale.add("-");
    finale.add(totalInterest+"");
      finale.add(totalAccumulatedInt+"");
    finale.add(totalLoanPenalty+"");
     finale.add(totalPrincipal+"");
      finale.add(loanAmount+"");
      finale.add("-");
agingReport.put(k,finale);  
  }
return agingReport;
  
  
  
  }
   
   
   public  Map<Integer, List<Object>>createAllOutstandingSavingsPaymentsSpecialX(int batchNu){
 
  
  int n=0,k=0;

Double totalAmount=0.0,totalSavings=0.0;List finale=null;

       Map<Integer, List<Object>> batchNubmerItems=new HashMap();
       
       
//  List accountNumbers=loan.updateOutStandingAcof();
  
//  for(Object account:accountNumbers){
  
  
           
      try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
     
      
          
   String query = "SELECT  ItemId,ItemDate,AccountName,AccountNumber,Savings,TotalAmount FROM batchpostingdetails WHERE BatchId="+batchNu;
 
                              
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
      while(rsxmt.next()){
             List loanDetails=new ArrayList();
            n=n+1;
    loanDetails.add(rsxmt.getString("ItemId"));         
loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("ItemDate")));
loanDetails.add(rsxmt.getString("AccountName"));
loanDetails.add(rsxmt.getString("AccountNumber"));
loanDetails.add(rsxmt.getString("Savings"));
loanDetails.add(rsxmt.getString("TotalAmount"));
batchNubmerItems.put(k,  loanDetails);

totalSavings+=parseDouble(loanDetails.get(4).toString().replace(",", "").trim());
totalAmount+=parseDouble(loanDetails.get(5).toString().replace(",", "").trim());
 k++;
          }
   
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
     
//  }
              finale=new ArrayList();
  finale.add("Total");
  finale.add("-");
   finale.add("-");
    finale.add("-");
    finale.add(totalSavings+"");
      finale.add(totalAmount+"");
   
batchNubmerItems.put(k-1,finale);  
    
return batchNubmerItems;
  
  

  
  }
   
    public  Map<Integer, List<Object>>createAllOutstandingSharesPaymentsSpecialX(int batchNu){
 
  
  int n=0,k=0;

Double totalAmount=0.0,totalSavings=0.0;List finale=null;

       Map<Integer, List<Object>> batchNubmerItems=new HashMap();
       
       
//  List accountNumbers=loan.updateOutStandingAcof();
  
//  for(Object account:accountNumbers){
  
  
           
      try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
     
      
          
   String query = "SELECT  ItemId,ItemDate,AccountName,AccountNumber,Shares,TotalAmount FROM batchpostingdetails WHERE BatchId="+batchNu;
 
                              
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
      while(rsxmt.next()){
             List loanDetails=new ArrayList();
            n=n+1;
    loanDetails.add(rsxmt.getString("ItemId"));         
loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("ItemDate")));
loanDetails.add(rsxmt.getString("AccountName"));
loanDetails.add(rsxmt.getString("AccountNumber"));
loanDetails.add(rsxmt.getString("Shares"));
loanDetails.add(rsxmt.getString("TotalAmount"));
batchNubmerItems.put(k,  loanDetails);

totalSavings+=parseDouble(loanDetails.get(4).toString().replace(",", "").trim());
totalAmount+=parseDouble(loanDetails.get(5).toString().replace(",", "").trim());
 k++;
          }
   
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
     
//  }
              finale=new ArrayList();
  finale.add("Total");
  finale.add("-");
   finale.add("-");
    finale.add("-");
    finale.add(totalSavings+"");
      finale.add(totalAmount+"");
   
batchNubmerItems.put(k-1,finale);  
    
return batchNubmerItems;
  
  

  
  }
   public  Map<Integer, List<Object>>createAllOutstandingLaonPaymentsDum(){
 
  int n=0,k=0;

       Map<Integer, List<Object>> agingReport=new HashMap();

      try {
           Connection cq=quaryObj.createConnection();
           
          cq.setAutoCommit(false);
     
   String query = "SELECT TrnDate,BatchId,BatchType,NumberEntries,TotalAmount, BatchStatus FROM batchpostingsummury";
          
                           
          PreparedStatement psxmt = cq.prepareStatement(query);
          
          ResultSet rsxmt = psxmt.executeQuery();
          
       while(rsxmt.next()){
        List loanDetails=new ArrayList();
        n=n+1;
        
        loanDetails.add(n);          
        loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("TrnDate")));
        loanDetails.add(rsxmt.getString("BatchId"));
        loanDetails.add(rsxmt.getString("BatchType"));
        loanDetails.add(rsxmt.getString("NumberEntries"));
        loanDetails.add(rsxmt.getString("TotalAmount"));
         loanDetails.add(rsxmt.getString("BatchStatus"));
         
        agingReport.put(k,  loanDetails);
        k++;
          }

        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
    

return agingReport;

  }
  
   public  Map<Integer, List<Object>>createAllOutstandingLaonPaymentsDumSub(){
 
  int n=0,k=0;

       Map<Integer, List<Object>> agingReport=new HashMap();

      try {
           Connection cq=quaryObj.createConnection();
          cq.setAutoCommit(false);
     
   String query = "SELECT TrnDate,BatchId,BatchType,NumberEntries,TotalAmount, BatchStatus FROM batchpostingsummury WHERE BatchStatus='Submitted'";
          
                           
          PreparedStatement psxmt = cq.prepareStatement(query);
          
          ResultSet rsxmt = psxmt.executeQuery();
          
       while(rsxmt.next()){
        List loanDetails=new ArrayList();
        n=n+1;
        
        loanDetails.add(n);          
        loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("TrnDate")));
        loanDetails.add(rsxmt.getString("BatchId"));
        loanDetails.add(rsxmt.getString("BatchType"));
        loanDetails.add(rsxmt.getString("NumberEntries"));
        loanDetails.add(rsxmt.getString("TotalAmount"));
         loanDetails.add(rsxmt.getString("BatchStatus"));
         
        agingReport.put(k,  loanDetails);
        k++;
          }

        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         quaryObj.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
    

return agingReport;

  }
  
    public void dailyCashLedger(JTable table){

           Map<Integer, List<Object>> dataBody=	statementEntries("01123000110",this.getDBCurrentDate(),this.getDBCurrentDate());
		
      List   tableHeaders=new ArrayList();
      
            tableHeaders.add("EntryDate");
            
            tableHeaders.add("ValueDate");
            
            tableHeaders.add("Description");
            
            tableHeaders.add("Debits");
            
            tableHeaders.add("Credits");
            
            tableHeaders.add("Balance");
            
//            tableHeaders.add("Trn_id");
  
            reportsLikeTable= new ReportsModelData(dataBody, tableHeaders);
            
           table.setModel(reportsLikeTable);
       




} 
  
  
  
//
 public  Map<Integer, List<Object>>dailyCollectionTotal(List details,Component c){
     
//  JOptionPane.showMessageDialog(c, details.get(0)+";"+details.get(1));
  
int n=1,k=0;

       Map<Integer, List<Object>> dailySales=new HashMap();
    
           
      try {
          
           Connection cq=loancon.createConnection();
           
          cq.setAutoCommit(false);
     
          
          
  String query ="CALL dailyCollectionInstalmentStatement("+"'"+details.get(0)+"'"+")";
                                           
//     JOptionPane.showMessageDialog(c, query);    
     
          PreparedStatement psxmt = cq.prepareStatement(query);
          
          ResultSet rsxmt = psxmt.executeQuery();
          
      while(rsxmt.next()){
       
             List loanDetails=new ArrayList();
             
         
////            
    loanDetails.add(rsxmt.getString("id"));
//    JOptionPane.showMessageDialog(c, rsxmt.getString("id"));
loanDetails.add(rsxmt.getString("temp_NarrationC"));
loanDetails.add(rsxmt.getString("temp_ExpectedCollection"));
loanDetails.add(rsxmt.getString("temp_ActualCollection"));
loanDetails.add(rsxmt.getString("temp_BalColl"));
loanDetails.add(rsxmt.getString("temp_Variance"));
//JOptionPane.showMessageDialog(c, loanDetails.size());

dailySales.put(k,  loanDetails);

n++;k++;
         }
   
 
        cq.setAutoCommit(true);
        
         loancon.closeConnection(cq);
         
      } catch (SQLException ex) {
          JOptionPane.showMessageDialog(c, ex.toString());
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
//    JOptionPane.showMessageDialog(c, dailySales.size());   
    
return dailySales;

}
    



 public  Map<Integer, List<Object>>grossPortfolioReport(Component c){
     
//  JOptionPane.showMessageDialog(c, details.get(0)+";"+details.get(1));
  
int n=1,k=0;

       Map<Integer, List<Object>> dailySales=new HashMap();
    
           
      try {
          
           Connection cq=loancon.createConnection();
           
          cq.setAutoCommit(false);
     
          
          
  String query ="CALL grossLoanPortfolio()";
                                           
//     JOptionPane.showMessageDialog(c, query);    
     
          PreparedStatement psxmt = cq.prepareStatement(query);
          
          ResultSet rsxmt = psxmt.executeQuery();
          
      while(rsxmt.next()){
       
             List loanDetails=new ArrayList();
             
         
////            
    loanDetails.add(rsxmt.getString("id"));
//    JOptionPane.showMessageDialog(c, rsxmt.getString("id"));
loanDetails.add(rsxmt.getString("temp_Borrower"));
loanDetails.add(rsxmt.getString("temp_outStandingPrici"));
loanDetails.add(rsxmt.getString("temp_OutStandingInterst"));
loanDetails.add(rsxmt.getString("temp_OutStandingAccum"));
loanDetails.add(rsxmt.getString("temp_OutStandingPenalty"));
loanDetails.add(rsxmt.getString("temp_OutStandingTotal"));
//JOptionPane.showMessageDialog(c, loanDetails.size());

dailySales.put(k,  loanDetails);

n++;k++;
         }
   
 
        cq.setAutoCommit(true);
        
         loancon.closeConnection(cq);
         
      } catch (SQLException ex) {
          JOptionPane.showMessageDialog(c, ex.toString());
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
//    JOptionPane.showMessageDialog(c, dailySales.size());   
    
return dailySales;

}
 
 
 
 public  Map<Integer, List<Object>>grossPortfolioReportPdf(Component c){
     
//  JOptionPane.showMessageDialog(c, details.get(0)+";"+details.get(1));
  
int n=1,k=0;

       Map<Integer, List<Object>> dailySales=new HashMap();
    
           
      try {
          
           Connection cq=loancon.createConnection();
           
          cq.setAutoCommit(false);
     
          
          
  String query ="CALL grossLoanPortfolioPdf()";
                                           
//     JOptionPane.showMessageDialog(c, query);    
     
          PreparedStatement psxmt = cq.prepareStatement(query);
          
          ResultSet rsxmt = psxmt.executeQuery();
          
      while(rsxmt.next()){
       
             List loanDetails=new ArrayList();
             
         
////            
    loanDetails.add(rsxmt.getString("id"));
//    JOptionPane.showMessageDialog(c, rsxmt.getString("id"));
loanDetails.add(rsxmt.getString("temp_Borrower"));
loanDetails.add(rsxmt.getString("temp_outStandingPrici"));
loanDetails.add(rsxmt.getString("temp_OutStandingInterst"));
loanDetails.add(rsxmt.getString("temp_OutStandingAccum"));
loanDetails.add(rsxmt.getString("temp_OutStandingPenalty"));
loanDetails.add(rsxmt.getString("temp_OutStandingTotal"));
//JOptionPane.showMessageDialog(c, loanDetails.size());

dailySales.put(k,  loanDetails);

n++;k++;
         }
   
 
        cq.setAutoCommit(true);
        
         loancon.closeConnection(cq);
         
      } catch (SQLException ex) {
          JOptionPane.showMessageDialog(c, ex.toString());
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
//    JOptionPane.showMessageDialog(c, dailySales.size());   
    
return dailySales;

}
 
 public  Map<Integer, List<Object>>moneyLInt(List accDe,Component c){
     
//  JOptionPane.showMessageDialog(c, accDe.get(0));
  
int n=1,k=0;

       Map<Integer, List<Object>> dailySales=new HashMap();
    
           
      try {
          
           Connection cq=loancon.createConnection();
           
          cq.setAutoCommit(false);
     
          
          
  String query ="CALL selectctionInterestComputed("+"'"+"newloan"+accDe.get(0).toString()+"'"+")";
                                           
//     JOptionPane.showMessageDialog(c, query);    
     
          PreparedStatement psxmt = cq.prepareStatement(query);
          
          ResultSet rsxmt = psxmt.executeQuery();
          
      while(rsxmt.next()){
       
             List loanDetails=new ArrayList();
             
         
////            
    loanDetails.add(rsxmt.getString("id"));
//    JOptionPane.showMessageDialog(c, rsxmt.getString("id"));
loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("temp_DueDate")));
loanDetails.add(rsxmt.getString("temp_princimpalInvolved"));
loanDetails.add(rsxmt.getString("temp_InterestInvo"));
loanDetails.add(rsxmt.getString("temp_InterestPaid"));
loanDetails.add(rsxmt.getString("temp_InterestRemain"));
loanDetails.add(rsxmt.getString("temp_RuningBalInterest"));

loanDetails.add(rsxmt.getString("temp_RuningBalLoanAmount"));
//JOptionPane.showMessageDialog(c, loanDetails.size());

dailySales.put(k,  loanDetails);

n++;k++;
         }
   
 
        cq.setAutoCommit(true);
        
         loancon.closeConnection(cq);
         
      } catch (SQLException ex) {
          JOptionPane.showMessageDialog(c, ex.toString());
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
//    JOptionPane.showMessageDialog(c, dailySales.size());   
    
return dailySales;

} 
 
 
   public  Map<Integer, List<Object>>dailyCollectionTotalArrears(List details,Component c){
     
//  JOptionPane.showMessageDialog(c, details.get(0)+";"+details.get(1));
  
int n=1,k=0;

       Map<Integer, List<Object>> dailySales=new HashMap();
    
           
      try {
          
           Connection cq=loancon.createConnection();
           
          cq.setAutoCommit(false);
     
          
          
  String query ="CALL dailyCollectionInstalmentStatementArrears("+"'"+details.get(0)+"'"+")";
                                           
//     JOptionPane.showMessageDialog(c, query);    
     
          PreparedStatement psxmt = cq.prepareStatement(query);
          
          ResultSet rsxmt = psxmt.executeQuery();
          
      while(rsxmt.next()){
       
             List loanDetails=new ArrayList();
             
         
////            
    loanDetails.add(rsxmt.getString("id"));
//    JOptionPane.showMessageDialog(c, rsxmt.getString("id"));
loanDetails.add(rsxmt.getString("temp_NarrationC"));
loanDetails.add(rsxmt.getString("temp_ExpectedCollection"));
loanDetails.add(rsxmt.getString("temp_ActualCollection"));
loanDetails.add(rsxmt.getString("temp_BalColl"));
loanDetails.add(rsxmt.getString("temp_Variance"));
//JOptionPane.showMessageDialog(c, loanDetails.size());

dailySales.put(k,  loanDetails);

n++;k++;
         }
   
 
        cq.setAutoCommit(true);
        
         loancon.closeConnection(cq);
         
      } catch (SQLException ex) {
          JOptionPane.showMessageDialog(c, ex.toString());
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
//    JOptionPane.showMessageDialog(c, dailySales.size());   
    
return dailySales;

}
  

   public  Map<Integer, List<Object>>dailyCollectionPrincipal(List details,Component c){
     
//  JOptionPane.showMessageDialog(c, details.get(0)+";"+details.get(1));
  
int n=1,k=0;

       Map<Integer, List<Object>> dailySales=new HashMap();
    
           
      try {
          
           Connection cq=loancon.createConnection();
           
          cq.setAutoCommit(false);
     
          
          
  String query ="CALL dailyCollectionPrincipal("+"'"+details.get(0)+"'"+")";
                                           
//     JOptionPane.showMessageDialog(c, query);    
     
          PreparedStatement psxmt = cq.prepareStatement(query);
          
          ResultSet rsxmt = psxmt.executeQuery();
          
      while(rsxmt.next()){
       
             List loanDetails=new ArrayList();
             
         
////            
    loanDetails.add(rsxmt.getString("id"));
//    JOptionPane.showMessageDialog(c, rsxmt.getString("id"));
loanDetails.add(rsxmt.getString("temp_NarrationC"));
loanDetails.add(rsxmt.getString("temp_ExpectedCollection"));
loanDetails.add(rsxmt.getString("temp_ActualCollection"));
loanDetails.add(rsxmt.getString("temp_BalColl"));
loanDetails.add(rsxmt.getString("temp_Variance"));
//JOptionPane.showMessageDialog(c, loanDetails.size());

dailySales.put(k,  loanDetails);

n++;k++;
         }
   
 
        cq.setAutoCommit(true);
        
         loancon.closeConnection(cq);
         
      } catch (SQLException ex) {
          JOptionPane.showMessageDialog(c, ex.toString());
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
//    JOptionPane.showMessageDialog(c, dailySales.size());   
    
return dailySales;

}
    
  
   public  Map<Integer, List<Object>>dailyCollectionPrincipalArrears(List details,Component c){
     
//  JOptionPane.showMessageDialog(c, details.get(0)+";"+details.get(1));
  
int n=1,k=0;

       Map<Integer, List<Object>> dailySales=new HashMap();
    
           
      try {
          
           Connection cq=loancon.createConnection();
           
          cq.setAutoCommit(false);
     
          
          
  String query ="CALL dailyCollectionPrincipalArrears("+"'"+details.get(0)+"'"+")";
                                           
//     JOptionPane.showMessageDialog(c, query);    
     
          PreparedStatement psxmt = cq.prepareStatement(query);
          
          ResultSet rsxmt = psxmt.executeQuery();
          
      while(rsxmt.next()){
       
             List loanDetails=new ArrayList();
             
         
////            
    loanDetails.add(rsxmt.getString("id"));
//    JOptionPane.showMessageDialog(c, rsxmt.getString("id"));
loanDetails.add(rsxmt.getString("temp_NarrationC"));
loanDetails.add(rsxmt.getString("temp_ExpectedCollection"));
loanDetails.add(rsxmt.getString("temp_ActualCollection"));
loanDetails.add(rsxmt.getString("temp_BalColl"));
loanDetails.add(rsxmt.getString("temp_Variance"));
//JOptionPane.showMessageDialog(c, loanDetails.size());

dailySales.put(k,  loanDetails);

n++;k++;
         }
   
 
        cq.setAutoCommit(true);
        
         loancon.closeConnection(cq);
         
      } catch (SQLException ex) {
          JOptionPane.showMessageDialog(c, ex.toString());
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
//    JOptionPane.showMessageDialog(c, dailySales.size());   
    
return dailySales;

}
  
  
   public  Map<Integer, List<Object>>dailyCollectionInterest(List details,Component c){
     
//  JOptionPane.showMessageDialog(c, details.get(0)+";"+details.get(1));
  
int n=1,k=0;

       Map<Integer, List<Object>> dailySales=new HashMap();
    
           
      try {
          
           Connection cq=loancon.createConnection();
           
          cq.setAutoCommit(false);
     
          
          
  String query ="CALL dailyCollectionInterest("+"'"+details.get(0)+"'"+")";
                                           
//     JOptionPane.showMessageDialog(c, query);    
     
          PreparedStatement psxmt = cq.prepareStatement(query);
          
          ResultSet rsxmt = psxmt.executeQuery();
          
      while(rsxmt.next()){
       
             List loanDetails=new ArrayList();
             
         
////            
    loanDetails.add(rsxmt.getString("id"));
//    JOptionPane.showMessageDialog(c, rsxmt.getString("id"));
loanDetails.add(rsxmt.getString("temp_NarrationC"));
loanDetails.add(rsxmt.getString("temp_ExpectedCollection"));
loanDetails.add(rsxmt.getString("temp_ActualCollection"));
loanDetails.add(rsxmt.getString("temp_BalColl"));
loanDetails.add(rsxmt.getString("temp_Variance"));
//JOptionPane.showMessageDialog(c, loanDetails.size());

dailySales.put(k,  loanDetails);

n++;k++;
         }
   
 
        cq.setAutoCommit(true);
        
         loancon.closeConnection(cq);
         
      } catch (SQLException ex) {
          JOptionPane.showMessageDialog(c, ex.toString());
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
//    JOptionPane.showMessageDialog(c, dailySales.size());   
    
return dailySales;

}
  
   public  Map<Integer, List<Object>>dailyCollectionInterestArrears(List details,Component c){
     
//  JOptionPane.showMessageDialog(c, details.get(0)+";"+details.get(1));
  
int n=1,k=0;

       Map<Integer, List<Object>> dailySales=new HashMap();
    
           
      try {
          
           Connection cq=loancon.createConnection();
           
          cq.setAutoCommit(false);
     
          
          
  String query ="CALL dailyCollectionInterestArrears("+"'"+details.get(0)+"'"+")";
                                           
//     JOptionPane.showMessageDialog(c, query);    
     
          PreparedStatement psxmt = cq.prepareStatement(query);
          
          ResultSet rsxmt = psxmt.executeQuery();
          
      while(rsxmt.next()){
       
             List loanDetails=new ArrayList();
             
         
////            
    loanDetails.add(rsxmt.getString("id"));
//    JOptionPane.showMessageDialog(c, rsxmt.getString("id"));
loanDetails.add(rsxmt.getString("temp_NarrationC"));
loanDetails.add(rsxmt.getString("temp_ExpectedCollection"));
loanDetails.add(rsxmt.getString("temp_ActualCollection"));
loanDetails.add(rsxmt.getString("temp_BalColl"));
loanDetails.add(rsxmt.getString("temp_Variance"));
//JOptionPane.showMessageDialog(c, loanDetails.size());

dailySales.put(k,  loanDetails);

n++;k++;
         }
   
 
        cq.setAutoCommit(true);
        
         loancon.closeConnection(cq);
         
      } catch (SQLException ex) {
          JOptionPane.showMessageDialog(c, ex.toString());
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
//    JOptionPane.showMessageDialog(c, dailySales.size());   
    
return dailySales;

}
  
   public  Map<Integer, List<Object>>summuOneByOneRepo(List details,Component c,JTextField j){
     
//  JOptionPane.showMessageDialog(c, details.get(0)+";"+details.get(1));
  
int n=1,k=0;

       Map<Integer, List<Object>> dailySales=new HashMap();
    
           
      try {
          
           Connection cq=quaryObj.createConnection();
           
          cq.setAutoCommit(false);
     
//          JOptionPane.showMessageDialog(c, details.get(0)+"\n"+details.get(1)+"\n"+details.get(2)+"\n"+details.get(3));
          
  String query ="CALL SummuryReportGenerator("+"'"+details.get(0)+"'"+","+"'"+details.get(1)+"'"+","+"'"+details.get(2)+"'"+","+details.get(3)+")";
//                           j.setText(query);
//     JOptionPane.showMessageDialog(c, query);    
     
          PreparedStatement psxmt = cq.prepareStatement(query);
          
          ResultSet rsxmt = psxmt.executeQuery();
          
      while(rsxmt.next()){
       
             List loanDetails=new ArrayList();
             
         
////            
    loanDetails.add(rsxmt.getString("temp_id"));
//    JOptionPane.showMessageDialog(c, rsxmt.getString("temp_id"));
loanDetails.add(rsxmt.getString("temp_NarrationC"));
loanDetails.add(rsxmt.getString("temp_StartinFigure"));
loanDetails.add(rsxmt.getString("temp_Difference"));
loanDetails.add(rsxmt.getString("temp_FinalFigure"));
loanDetails.add(rsxmt.getString("temp_Comment"));
//JOptionPane.showMessageDialog(c, loanDetails.size());

dailySales.put(k,  loanDetails);

n++;k++;
         }
   
 
        cq.setAutoCommit(true);
        
         quaryObj.closeConnection(cq);
         
      } catch (SQLException ex) {
          JOptionPane.showMessageDialog(c, ex.toString());
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
//    JOptionPane.showMessageDialog(c, dailySales.size());   
    
return dailySales;

}
  
  
  
//
 public  Map<Integer, List<Object>>dailySummuryReport(List details,Component c){
     
//  JOptionPane.showMessageDialog(c, details.get(0)+";"+details.get(1));
  
int n=1,k=0;

       Map<Integer, List<Object>> dailySales=new HashMap();
    
           
      try {
          
           Connection cq=loancon.createConnection();
           
          cq.setAutoCommit(false);
     
          
          
  String query ="CALL grandSummryDailyReport("+"'"+details.get(0)+"'"+")";
                                           
//     JOptionPane.showMessageDialog(c, query);    
     
          PreparedStatement psxmt = cq.prepareStatement(query);
          
          ResultSet rsxmt = psxmt.executeQuery();
          
      while(rsxmt.next()){
       
             List loanDetails=new ArrayList();
             
         
////            
//    loanDetails.add(rsxmt.getString("id"));
//    JOptionPane.showMessageDialog(c, rsxmt.getString("id"));
loanDetails.add(rsxmt.getString("temp_NarrationC"));
loanDetails.add(rsxmt.getString("temp_ExpectedCollection"));
loanDetails.add(rsxmt.getString("temp_ActualCollection"));
loanDetails.add(rsxmt.getString("temp_BalColl"));
loanDetails.add(rsxmt.getString("temp_Variance"));
//JOptionPane.showMessageDialog(c, loanDetails.size());

dailySales.put(k,  loanDetails);

n++;k++;
         }
   
 
        cq.setAutoCommit(true);
        
         loancon.closeConnection(cq);
         
      } catch (SQLException ex) {
          JOptionPane.showMessageDialog(c, ex.toString());
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
//    JOptionPane.showMessageDialog(c, dailySales.size());   
    
return dailySales;

}
    
  
  
  
  
  
  
  
  
  
  
  
  
//
 public List<List>smsDailyReport(Component c){
     
//  JOptionPane.showMessageDialog(c, details.get(0)+";"+details.get(1));
  
int n=1,k=0;

      List<List> dailySales=new ArrayList();
    
           
      try {
          
           Connection cq=loancon.createConnection();
           
          cq.setAutoCommit(false);
     
          
          
  String query ="CALL smsSummuryReport()";
                                           
//     JOptionPane.showMessageDialog(c, query);    
     
          PreparedStatement psxmt = cq.prepareStatement(query);
          
          ResultSet rsxmt = psxmt.executeQuery();
          
      while(rsxmt.next()){
       
             List loanDetails=new ArrayList();
             
         
////            
//    loanDetails.add(rsxmt.getString("id"));
//    JOptionPane.showMessageDialog(c, rsxmt.getString("id"));
loanDetails.add(rsxmt.getString("itemName"));
loanDetails.add(rsxmt.getString("itemValue"));


dailySales.add(loanDetails);


         }
   
 
        cq.setAutoCommit(true);
        
         loancon.closeConnection(cq);
         
      } catch (SQLException ex) {
          JOptionPane.showMessageDialog(c, ex.toString());
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
//    JOptionPane.showMessageDialog(c, dailySales.size());   
    
return dailySales;

}
    
 
  
  
          
 public synchronized  Map<Integer, List<Object>>statementOfLoanS(String startDate,String endDate,Component c){
     
  int n=1,k=0;

       Map<Integer, List<Object>> statementData=new HashMap();
    
           
      try {
          
           Connection cq=quaryObj.createConnection();
           
          cq.setAutoCommit(false);
     
          
          
  String query ="CALL getStatementOfLoanStatus("+"'"+startDate+"'"+","+"'"+endDate+"'"+")";
                                           
//     JOptionPane.showMessageDialog(c, query);    
     
          PreparedStatement psxmt = cq.prepareStatement(query);
          
          ResultSet rsxmt = psxmt.executeQuery();
          
      while(rsxmt.next()){
       
             List loanDetails=new ArrayList();     
    loanDetails.add(rsxmt.getString("id"));
//    JOptionPane.showMessageDialog(c, rsxmt.getString("id"));
loanDetails.add(rsxmt.getString("borrower"));
loanDetails.add(rsxmt.getString("account_number"));
loanDetails.add(rsxmt.getString("princimcipal_openingBal"));
loanDetails.add(rsxmt.getString("princincipal_disbursed"));
loanDetails.add(rsxmt.getString("principal_paid"));
loanDetails.add(rsxmt.getString("closing_princiBal"));
loanDetails.add(rsxmt.getString("interest_openingBal"));
loanDetails.add(rsxmt.getString("expected_interest"));
loanDetails.add(rsxmt.getString("interest_paid"));
loanDetails.add(rsxmt.getString("closing_InterestBal"));

statementData.put(k,  loanDetails);

n++;k++;
         }
   
 
        cq.setAutoCommit(true);
        
         quaryObj.closeConnection(cq);
         
      } catch (SQLException ex) {
          JOptionPane.showMessageDialog(c, ex.toString());
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
//    JOptionPane.showMessageDialog(c, dailySales.size());   
    
return statementData;

}
  




  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
}
